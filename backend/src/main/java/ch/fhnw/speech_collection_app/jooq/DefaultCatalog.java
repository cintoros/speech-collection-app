/*
 * This file is generated by jOOQ.
 */
package ch.fhnw.speech_collection_app.jooq;


import java.util.Arrays;
import java.util.List;

import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;


@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultCatalog extends CatalogImpl {

    private static final long serialVersionUID = 2029479866;

    public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();

    public final SpeechCollectionApp SPEECH_COLLECTION_APP = SpeechCollectionApp.SPEECH_COLLECTION_APP;

    private DefaultCatalog() {
        super("");
    }

    @Override
    public final List<Schema> getSchemas() {
        return Arrays.<Schema>asList(
            SpeechCollectionApp.SPEECH_COLLECTION_APP);
    }
}
