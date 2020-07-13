package ch.fhnw.speech_collection_app.features.base.admin.document;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.admin.pagination.PaginationResultDto;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Source;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.IOUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ch.fhnw.speech_collection_app.jooq.Tables.*;

@Service
public class DocumentService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final SpeechCollectionAppConfig speechCollectionAppConfig;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DocumentService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext, SpeechCollectionAppConfig speechCollectionAppConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.speechCollectionAppConfig = speechCollectionAppConfig;
    }

    public void postDocumentSource(long groupId, long domainId, MultipartFile[] files, String documentLicence) {
        isAllowed(groupId);
        var parser = new AutoDetectParser(TikaConfig.getDefaultConfig());
        var path = speechCollectionAppConfig.getBasePath().resolve("extracted_text");
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            logger.error("unexpected Exception: ", e);
        }
        String collect = Arrays.stream(files).map(file -> {
            try {
                //NOTE: -1 disables the write limit -> as spring already limit the upload to 1MB -> note ngingx etc. may also have a file limit
                var bodyContentHandler = new BodyContentHandler(-1);
                var metadata = new Metadata();
                metadata.add(Metadata.RESOURCE_NAME_KEY, file.getOriginalFilename());
                metadata.add(Metadata.CONTENT_TYPE, file.getContentType());
                parser.parse(new ByteArrayInputStream(file.getBytes()), bodyContentHandler, metadata);
                var text = bodyContentHandler.toString();

                var source = dslContext.newRecord(SOURCE);
                source.setUserGroupId(groupId);
                source.setDomainId(domainId);
                source.setUserId(customUserDetailsService.getLoggedInUserId());
                source.setLicence(documentLicence);
                source.setName(file.getOriginalFilename());
                source.setPathToRawFile("default");
                source.setUserGroupId(groupId);
                source.store();
                var id = source.getId();
                var rawFilePath = Paths.get("original_text", id + ".bin");
                source.setPathToRawFile(rawFilePath.toString());
                rawFilePath = speechCollectionAppConfig.getBasePath().resolve(rawFilePath);
                Files.write(rawFilePath, file.getBytes());
                source.store();

                Files.writeString(path.resolve(id + ".txt"), text, StandardCharsets.UTF_8);
                return Optional.of(id);
            } catch (IOException | TikaException | SAXException ex) {
                logger.error("failed to parse original text: ", ex);
                return Optional.empty();
            }
        }).flatMap(Optional::stream).map(Object::toString).collect(Collectors.joining(","));
        try {
            var process = Runtime.getRuntime().exec(speechCollectionAppConfig.getCondaExec() + " 1 " + collect);
            List<String> list = IOUtils.readLines(process.getErrorStream());
            if (!list.isEmpty()) {
                logger.error(String.join("\n", list));
            }
        } catch (Exception e) {
            logger.error("Exception Raised", e);
        }
    }

    public void postImageSource(long groupId, long domainId, MultipartFile[] files, String documentLicence) {
        isAllowed(groupId);
        try {
            Files.createDirectories(speechCollectionAppConfig.getBasePath().resolve("images"));
        } catch (IOException e) {
            logger.error("unexpected Exception: ", e);
        }
        var source = dslContext.newRecord(SOURCE);
        source.setUserGroupId(groupId);
        source.setDomainId(domainId);
        source.setUserId(customUserDetailsService.getLoggedInUserId());
        source.setLicence(documentLicence);
        source.setName(documentLicence + " Images");
        source.setUserGroupId(groupId);
        source.store();
        Arrays.stream(files).forEach(file -> {
            try {
                var element = dslContext.newRecord(DATA_ELEMENT);
                element.setUserId(customUserDetailsService.getLoggedInUserId());
                element.setUserGroupId(groupId);
                element.setSourceId(source.getId());
                element.store();

                var image = dslContext.newRecord(IMAGE);
                image.setPath("default");
                image.setLicence(documentLicence);
                image.setDataElementId(element.getId());
                image.store();

                var id = image.getId();
                var rawFilePath = Paths.get("images", id + ".bin");
                image.setPath(rawFilePath.toString());
                image.store();

                rawFilePath = speechCollectionAppConfig.getBasePath().resolve(rawFilePath);
                Files.write(rawFilePath, file.getBytes());
            } catch (IOException ex) {
                logger.error("unexpected Exception: ", ex);
            }
        });
    }

    public void deleteDataElement(long groupId, long sourceId, long dataElementId) {
        isAllowed(groupId);
        dslContext.delete(DATA_ELEMENT)
                .where(DATA_ELEMENT.ID.eq(dataElementId).and(DATA_ELEMENT.SOURCE_ID.eq(sourceId)))
                .execute();
    }

    public PaginationResultDto<TextElementDto> getTextElement(long groupId, long dataElementId, long lastIndex, long pageSize, boolean before) {
        isAllowed(groupId);
        var count = dslContext.fetchCount(DATA_ELEMENT, DATA_ELEMENT.SOURCE_ID.eq(dataElementId));
        var items = dslContext.select(DATA_ELEMENT.ID, DATA_ELEMENT.SOURCE_ID, DATA_ELEMENT.SKIPPED, DATA_ELEMENT.IS_PRIVATE,
                TEXT.IS_SENTENCE_ERROR, TEXT.TEXT_)
                .from(TEXT.join(DATA_ELEMENT).onKey())
                .where(DATA_ELEMENT.SOURCE_ID.eq(dataElementId))
                .orderBy(before ? DATA_ELEMENT.ID.desc() : DATA_ELEMENT.ID.asc())
                .seek(lastIndex)
                .limit(pageSize)
                .fetchInto(TextElementDto.class);
        return new PaginationResultDto<>(items, count);
    }

    public void deleteSource(long groupId, long sourceId) {
        isAllowed(groupId);
        dslContext.delete(SOURCE)
                .where(SOURCE.ID.eq(sourceId))
                .execute();
    }

    public List<Source> getDocumentSource(long groupId) {
        isAllowed(groupId);
        return dslContext.selectFrom(SOURCE)
                .where(SOURCE.USER_GROUP_ID.eq(groupId))
                .fetchInto(Source.class);
    }

    private void isAllowed(long groupId) {
        if (!customUserDetailsService.isAllowedOnProject(groupId, true))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
