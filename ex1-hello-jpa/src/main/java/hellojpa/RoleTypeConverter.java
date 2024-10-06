package hellojpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<RoleType, String> {
    @Override
    public String convertToDatabaseColumn(RoleType roleType) {
        return roleType != null ? roleType.name() : null;
    }

    @Override
    public RoleType convertToEntityAttribute(String dbData) {
        return dbData != null ? RoleType.valueOf(dbData) : null;
    }
}