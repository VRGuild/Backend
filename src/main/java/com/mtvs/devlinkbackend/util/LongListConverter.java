package com.mtvs.devlinkbackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert list to JSON", e);
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        try {
            return Arrays.asList(objectMapper.readValue(dbData, Long[].class));
        } catch (IOException e) {
            throw new RuntimeException("Could not convert JSON to list", e);
        }
    }
}