package com.halfhp.template.rest;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.halfhp.template.model.DomainModel;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;

/**
 * Custom message converter that automatically handles wrapping / unwrapping Tilt models in anonymous
 * Json objects where necessary.
 */
public class DomainModelMessageConverter extends MappingJackson2HttpMessageConverter {

    private ObjectMapper unwrappedApiModelMapper;

    {
        setObjectMapper(configureBaseObjectMapper(DomainModel.OBJECT_MAPPER.copy()));

        // create a copy of the defaut ObjectMapper that does not wrap/unwrap roots
        // to be used for dealing with UnwrappedApiModel instances.
        unwrappedApiModelMapper = getObjectMapper().copy();

        // "unwrap" the outer anonymous Json object when parsing API responses:
        getObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        getObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }

    protected ObjectMapper configureBaseObjectMapper(ObjectMapper objectMapper) {
        // does nothing by default
        return objectMapper;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        if (ReadUnwrapped.class.isAssignableFrom(clazz)) {
            JavaType javaType = getJavaType(clazz, null);
            try {
                return unwrappedApiModelMapper.readValue(inputMessage.getBody(), javaType);
            } catch (IOException ex) {
                throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(),
                        ex);
            }
        } else {
            return super.readInternal(clazz, inputMessage);
        }
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        if (object instanceof WriteUnwrapped) {
            JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
            JsonGenerator jsonGenerator =
                    unwrappedApiModelMapper.getFactory()
                            .createJsonGenerator(outputMessage.getBody(), encoding);
            try {
                unwrappedApiModelMapper.writeValue(jsonGenerator, object);
            } catch (IOException ex) {
                throw new HttpMessageNotWritableException(
                        "Could not write JSON: " + ex.getMessage(), ex);
            }
        } else {
            super.writeInternal(object, outputMessage);
        }
    }
}