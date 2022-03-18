package com.jsm.studymoa.domain.account.converter;

import com.jsm.studymoa.domain.account.enums.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        return attribute.getCode();
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.ofCode(dbData);
    }
}
