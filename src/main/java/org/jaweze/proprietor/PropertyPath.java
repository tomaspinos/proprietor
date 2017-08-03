package org.jaweze.proprietor;

import java.util.List;

public interface PropertyPath<ObjectType, PropertyType> extends PropertyAccessor<ObjectType, PropertyType> {

    Class<PropertyType> getPropertyType();

    List<PropertyMetadata<Object, Object>> getPropertiesOnPath();
}
