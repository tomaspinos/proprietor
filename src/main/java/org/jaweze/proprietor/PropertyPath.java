package org.jaweze.proprietor;

import org.immutables.value.Value;

@Value.Immutable
public abstract class PropertyPath<ObjectType, PropertyType>
        implements PropertyAccessor<ObjectType, PropertyType> {

    public abstract <OtherPropertyType>
    PropertyPath<ObjectType, OtherPropertyType> compose(PropertyPath<PropertyType, OtherPropertyType> otherPropertyPath);
}
