package com.dnastack.dos.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;

public class RestAssuredConfig {

    private static final com.fasterxml.jackson.databind.ObjectMapper DRS_JACKSON_MAPPER =
            new com.fasterxml.jackson.databind.ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    public static final ObjectMapper DRS_MAPPER = new JacksonObjectMapper(DRS_JACKSON_MAPPER);

    @AllArgsConstructor
    private static class JacksonObjectMapper implements ObjectMapper {

        private final com.fasterxml.jackson.databind.ObjectMapper jacksonMapper;

        @Override
        public Object deserialize(ObjectMapperDeserializationContext context) {
            try {
                return jacksonMapper.readValue(context.getDataToDeserialize().asInputStream(), context.getType());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public String serialize(ObjectMapperSerializationContext context) {
            try {
                return jacksonMapper.writeValueAsString(context.getObjectToSerialize());
            } catch (JsonProcessingException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
