package org.jaweze.proprietor;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SimplePropertyPath<ObjectType, PropertyType> implements PropertyPath<ObjectType, PropertyType> {

    private final PropertyMetadata<ObjectType, Object> firstProperty;
    private final PropertyPath<Object, PropertyType> remainingPath;

    public static <ObjectType, PropertyType> Builder<ObjectType, PropertyType> builder(PropertyMetadata<ObjectType, Object> firstProperty) {
        return new Builder<>(firstProperty);
    }

    public static <ObjectType, PropertyType> SimplePropertyPath<ObjectType, PropertyType> create(
            PropertyMetadata<ObjectType, PropertyType> property) {

        //noinspection unchecked
        return new SimplePropertyPath<>((PropertyMetadata<ObjectType, Object>) property,
                PropertyPath.empty());
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB> SimplePropertyPath<ObjectType, PropertyTypeB> create(
            PropertyMetadata<ObjectType, PropertyTypeA> propertyA,
            PropertyMetadata<PropertyTypeA, PropertyTypeB> propertyB) {

        return new SimplePropertyPath<>((PropertyMetadata<ObjectType, Object>) propertyA,
                new SimplePropertyPath<>((PropertyMetadata<Object, Object>) propertyB,
                        PropertyPath.empty()));
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB, PropertyTypeC> SimplePropertyPath<ObjectType, PropertyTypeC> create(
            PropertyMetadata<ObjectType, PropertyTypeA> propertyA,
            PropertyMetadata<PropertyTypeA, PropertyTypeB> propertyB,
            PropertyMetadata<PropertyTypeB, PropertyTypeC> propertyC) {

        return new SimplePropertyPath<>((PropertyMetadata<ObjectType, Object>) propertyA,
                new SimplePropertyPath<>((PropertyMetadata<Object, Object>) propertyB,
                        new SimplePropertyPath<>((PropertyMetadata<Object, Object>) propertyC,
                                PropertyPath.empty())));
    }

    public SimplePropertyPath(PropertyMetadata<ObjectType, Object> firstProperty, PropertyPath<Object, PropertyType> remainingPath) {
        this.firstProperty = firstProperty;
        this.remainingPath = remainingPath;
    }

    @Override
    public Optional<PropertyMetadata<ObjectType, Object>> getFirstProperty() {
        return Optional.ofNullable(firstProperty);
    }

    @Override
    public PropertyPath<Object, PropertyType> getRemainingPath() {
        return remainingPath;
    }

    @Override
    public String toString() {
        return "SimplePropertyPath{" +
                "firstProperty=" + firstProperty +
                ", remainingPath=" + remainingPath +
                '}';
    }

    public static class Builder<ObjectType, PropertyType> {

        private final PropertyMetadata<ObjectType, Object> firstProperty;
        private final LinkedList<PropertyMetadata<Object, Object>> remainingProperties;

        private Builder(PropertyMetadata<ObjectType, Object> firstProperty) {
            this.firstProperty = firstProperty;
            remainingProperties = new LinkedList<>();
        }

        public Builder<ObjectType, PropertyType> property(PropertyMetadata<Object, Object> property) {
            remainingProperties.add(property);
            return this;
        }

        public Builder<ObjectType, PropertyType> properties(List<PropertyMetadata<Object, Object>> properties) {
            remainingProperties.addAll(properties);
            return this;
        }

        public SimplePropertyPath<ObjectType, PropertyType> build() {
            //noinspection unchecked
            return new SimplePropertyPath<>(firstProperty, (PropertyPath<Object, PropertyType>) createRemainngPath(remainingProperties));
        }

        private PropertyPath<Object, Object> createRemainngPath(List<PropertyMetadata<Object, Object>> remainingProperties) {
            if (remainingProperties.isEmpty()) {
                return PropertyPath.empty();
            } else {
                return new SimplePropertyPath<>(remainingProperties.get(0), createRemainngPath(remainingProperties.subList(1, remainingProperties.size())));
            }
        }
    }
}
