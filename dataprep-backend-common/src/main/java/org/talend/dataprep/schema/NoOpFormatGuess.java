package org.talend.dataprep.schema;

import java.io.ByteArrayInputStream;
import java.util.Collections;

class NoOpFormatGuess implements FormatGuess {

    @Override
    public String getMediaType() {
        return "*/*";
    }

    @Override
    public float getConfidence() {
        return 0;
    }

    @Override
    public SchemaParser getSchemaParser() {
        return content -> SchemaParserResult.Builder.parserResult() //
                .columnMetadatas(Collections.emptyList()) //
                .draft(false) //
                .build();
    }

    @Override
    public Serializer getSerializer() {
        return (rawContent, metadata) -> new ByteArrayInputStream(new byte[0]);
    }
}
