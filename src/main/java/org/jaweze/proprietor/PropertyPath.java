package org.jaweze.proprietor;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface PropertyPath<ObjectType, PropertyType> extends PropertyAccessor<ObjectType, PropertyType> {

    List<PropertyMetadata<?, ?>> getPropertiesOnPath();
}
