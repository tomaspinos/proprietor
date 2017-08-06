package org.jaweze.proprietor;

import java.util.Optional;

public interface PropertyPath<ObjectType, PropertyType> {

    PropertyPath<?, ?> EMPTY = new PropertyPath<Object, Object>() {
        @Override
        public Optional<PropertyMetadata<Object, Object>> getFirstProperty() {
            return Optional.empty();
        }

        @Override
        public PropertyPath<Object, Object> getRemainingPath() {
            return this;
        }
    };

    static <ObjectType, PropertyType> PropertyPath<ObjectType, PropertyType> empty() {
        //noinspection unchecked
        return (PropertyPath<ObjectType, PropertyType>) EMPTY;
    }

    default boolean isEmpty() {
        return !getFirstProperty().isPresent();
    }

    default int getLength() {
        return getFirstProperty().isPresent() ? 1 + getRemainingPath().getLength() : 0;
    }

    Optional<PropertyMetadata<ObjectType, Object>> getFirstProperty();

    PropertyPath<Object, PropertyType> getRemainingPath();
}
