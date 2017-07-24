package org.jaweze.proprietor;

import org.immutables.value.Value;

@Value.Immutable
public interface PropertyMetadata<ObjectType, PropertyType> extends PropertyAccessor<ObjectType, PropertyType> {

    String getName();
}
