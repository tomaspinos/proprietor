package org.jaweze.proprietor;

public interface PropertyMetadata<ObjectType, PropertyType> extends PropertyAccessor<ObjectType, PropertyType> {

    String getName();

    Class<PropertyType> getType();
}
