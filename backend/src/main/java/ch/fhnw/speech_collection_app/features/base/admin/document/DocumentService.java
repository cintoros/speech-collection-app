package ch.fhnw.speech_collection_app.features.base.admin.document;

import ch.fhnw.speech_collection_app.config.SpeechCollectionAppConfig;
import ch.fhnw.speech_collection_app.features.base.user.CustomUserDetailsService;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Source;
import ch.fhnw.speech_collection_app.jooq.tables.pojos.Text;
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

    public void postOriginalText(long groupId, long domainId, MultipartFile[] files, String documentLicence) {
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
                var bodyContentHandler = new BodyContentHandler();
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
                var path1 = speechCollectionAppConfig.getBasePath().resolve("original_text/" + id + ".bin");
                Files.write(path1, file.getBytes());
                source.setPathToRawFile(path1.toString());
                source.store();

                Files.writeString(path.resolve(id + ".txt"), text, StandardCharsets.UTF_8);
                return Optional.of(id);
            } catch (IOException | TikaException | SAXException ex) {
                logger.error("failed to parse original text: ", ex);
                return Optional.empty();
            }
        }).flatMap(Optional::stream).map(Object::toString).collect(Collectors.joining(","));
        try {
            Process process = Runtime.getRuntime().exec(speechCollectionAppConfig.getCondaExec() + " 1 " + collect);
            List<String> list = IOUtils.readLines(process.getErrorStream());
            if (!list.isEmpty()) {
                logger.error(String.join("\n", list));
            }
        } catch (Exception e) {
            logger.error("Exception Raised", e);
        }
    }

    public void deleteExcerpt(long groupId, long originalTextId, long excerptId) {
        isAllowed(groupId);
        //TODO check if excerpt is in the right group -> see https://github.com/jOOQ/jOOQ/issues/3266
        dslContext.delete(TEXT)
                .where(TEXT.ID.eq(excerptId))
                .execute();
    }

    public List<Text> getExcerpt(long groupId, long originalTextId) {
        isAllowed(groupId);
        return dslContext.select(TEXT.fields())
                .from(TEXT.join(DATA_ELEMENT).onKey())
                .where(DATA_ELEMENT.SOURCE_ID.eq(originalTextId))
                .fetchInto(Text.class);
    }

    public void deleteOriginalText(long groupId, long originalTextId) {
        isAllowed(groupId);
        dslContext.delete(SOURCE)
                .where(SOURCE.ID.eq(originalTextId))
                .execute();
    }

    public List<Source> getOriginalText(long groupId) {
        isAllowed(groupId);
        return dslContext.selectFrom(SOURCE)
                //TODO not sure if it makes sense to also show the transcript imports? -> probably yes
                .where(SOURCE.USER_GROUP_ID.eq(groupId).and(SOURCE.DOMAIN_ID.isNotNull()))
                .fetchInto(Source.class);
    }

    private void isAllowed(long groupId) {
        if (!customUserDetailsService.isAllowedOnProject(groupId, true))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
