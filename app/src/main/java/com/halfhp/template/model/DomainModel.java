package com.halfhp.template.model;

import android.text.format.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halfhp.template.rest.ReadUnwrapped;
import com.halfhp.template.rest.WriteUnwrapped;

/**
 * Base class for JSON / XML interoperable domain models. Be sure to add a
 * rule to proguard to prevent obfuscation if you are building a prod app.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class DomainModel {

    /**
     * ObjectMapper for tilt models; should be used anywhere a tilt model is marshalled or unmarshalled.
     */
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        // only include fields annotated with JsonProperty.
        OBJECT_MAPPER = new ObjectMapper()
                .disable(MapperFeature.AUTO_DETECT_CREATORS)
                .disable(MapperFeature.AUTO_DETECT_GETTERS)
                .disable(MapperFeature.AUTO_DETECT_SETTERS)
                .disable(MapperFeature.AUTO_DETECT_FIELDS)
                .disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
    }
}
