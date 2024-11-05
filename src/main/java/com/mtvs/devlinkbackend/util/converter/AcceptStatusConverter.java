package com.mtvs.devlinkbackend.util.converter;

import com.mtvs.devlinkbackend.member.entity.AcceptStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AcceptStatusConverter implements AttributeConverter<AcceptStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AcceptStatus status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public AcceptStatus convertToEntityAttribute(Integer value) {
        if (value == null) {
            return null;
        }
        return AcceptStatus.fromValue(value);
    }
}

