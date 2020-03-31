package ch.fhnw.labeling_tool.admin.document;

import ch.fhnw.labeling_tool.config.LabelingToolConfig;
import ch.fhnw.labeling_tool.jooq.tables.pojos.Excerpt;
import ch.fhnw.labeling_tool.jooq.tables.pojos.OriginalText;
import ch.fhnw.labeling_tool.jooq.tables.records.OriginalTextRecord;
import ch.fhnw.labeling_tool.user.CustomUserDetailsService;
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

import static ch.fhnw.labeling_tool.jooq.Tables.EXCERPT;
import static ch.fhnw.labeling_tool.jooq.Tables.ORIGINAL_TEXT;

@Service
public class DocumentService {
    private final CustomUserDetailsService customUserDetailsService;
    private final DSLContext dslContext;
    private final LabelingToolConfig labelingToolConfig;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DocumentService(CustomUserDetailsService customUserDetailsService, DSLContext dslContext, LabelingToolConfig labelingToolConfig) {
        this.customUserDetailsService = customUserDetailsService;
        this.dslContext = dslContext;
        this.labelingToolConfig = labelingToolConfig;
    }

    public void postOriginalText(long groupId, long domainId, MultipartFile[] files, String documentLicence) {
        isAllowed(groupId);
        var parser = new AutoDetectParser(TikaConfig.getDefaultConfig());
        var path = labelingToolConfig.getBasePath().resolve("extracted_text");
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
                OriginalText originalText = new OriginalText(null, groupId, domainId, customUserDetailsService.getLoggedInUserId(), null, documentLicence);
                OriginalTextRecord textRecord = dslContext.newRecord(ORIGINAL_TEXT, originalText);
                textRecord.store();
                Long id = textRecord.getId();
                Files.writeString(path.resolve(id + ".txt"), text, StandardCharsets.UTF_8);
                Files.write(labelingToolConfig.getBasePath().resolve("original_text/" + id + ".bin"), file.getBytes());
                return Optional.of(id);
            } catch (IOException | TikaException | SAXException ex) {
                logger.error("failed to parse original text: ", ex);
                return Optional.empty();
            }
        }).flatMap(Optional::stream).map(Object::toString).collect(Collectors.joining(","));
        try {
            Process process = Runtime.getRuntime().exec(labelingToolConfig.getCondaExec() + " 1 " + collect);
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
        dslContext.delete(EXCERPT)
                .where(EXCERPT.ID.eq(excerptId).and(EXCERPT.ORIGINAL_TEXT_ID.eq(originalTextId)))
                .execute();
    }

    public List<Excerpt> getExcerpt(long groupId, long originalTextId) {
        isAllowed(groupId);
        return dslContext.selectFrom(EXCERPT)
                .where(EXCERPT.ORIGINAL_TEXT_ID.eq(originalTextId))
                .fetchInto(Excerpt.class);
    }

    public void deleteOriginalText(long groupId, long originalTextId) {
        isAllowed(groupId);
        dslContext.delete(ORIGINAL_TEXT)
                .where(ORIGINAL_TEXT.ID.eq(originalTextId))
                .execute();
    }

    public List<OriginalText> getOriginalText(long groupId) {
        isAllowed(groupId);
        return dslContext.selectFrom(ORIGINAL_TEXT)
                .where(ORIGINAL_TEXT.USER_GROUP_ID.eq(groupId))
                .fetchInto(OriginalText.class);
    }

    private void isAllowed(long groupId) {
        if (!customUserDetailsService.isAllowedOnProject(groupId, true))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
