package com.Gorkem.WebFlux.model.converter;

import com.Gorkem.WebFlux.model.metadata.CourseMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.io.IOException;


@WritingConverter
@Slf4j
public class CourseMetadatatoJsonConverter implements Converter<CourseMetadata, Json> {

    private final ObjectMapper objectMapper;

    public CourseMetadatatoJsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public Json convert(CourseMetadata source) {
        try {
            return Json.of(objectMapper.writeValueAsBytes(source));
        } catch (IOException e) {
            log.error("Error while converting CourseMetadata to Json", e);
            throw new RuntimeException(e);
        }
    }
}
