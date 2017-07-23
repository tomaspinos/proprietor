package org.jaweze.proprietor;

import org.immutables.value.Value;

@Value.Immutable
public abstract class PropertyMetadata<ObjectType, PropertyType>
        implements PropertyAccessor<ObjectType, PropertyType> {

    public abstract String getName();

}
