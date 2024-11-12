package com.mtvs.devlinkbackend.common.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Long> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "[]"; // 빈 리스트를 JSON 배열로 변환
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert list to JSON", e);
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환
        }
        try {
            List<Long> resultList = objectMapper.readValue(dbData, List.class);
            if (resultList == null || resultList.contains(null)) {
                return Collections.emptyList(); // null이거나 null이 포함된 경우 빈 리스트 반환
            }
            return resultList;
        } catch (IOException e) {
            throw new RuntimeException("Could not convert JSON to list", e);
        }
    }
}