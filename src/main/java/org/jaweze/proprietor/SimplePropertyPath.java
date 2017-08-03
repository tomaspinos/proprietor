package org.jaweze.proprietor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimplePropertyPath<ObjectType, PropertyType> implements PropertyPath<ObjectType, PropertyType> {

    private final Class<PropertyType> propertyType;
    private final List<PropertyMetadata<Object, Object>> propertiesOnPath;
    private final Function<ObjectType, PropertyType> reader;
    private final BiFunction<ObjectType, PropertyType, Void> writer;

    public static <ObjectType, PropertyType> Builder<ObjectType, PropertyType> builder(Class<PropertyType> propertyType) {
        return new Builder<>(propertyType);
    }

    public static <ObjectType, PropertyType> SimplePropertyPath<ObjectType, PropertyType> create(PropertyMetadata<ObjectType, PropertyType> property) {
        //noinspection unchecked
        return new SimplePropertyPath<>(property.getType(), Collections.singletonList((PropertyMetadata<Object, Object>) property), null, null);
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB> SimplePropertyPath<ObjectType, PropertyTypeB> create(PropertyMetadata<ObjectType, PropertyTypeA> propertyA, PropertyMetadata<PropertyTypeA, PropertyTypeB> propertyB) {
        //noinspection unchecked
        return new SimplePropertyPath<>(propertyB.getType(), Arrays.asList((PropertyMetadata<Object, Object>) propertyA, (PropertyMetadata<Object, Object>) propertyB), null, null);
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB, PropertyTypeC> SimplePropertyPath<ObjectType, PropertyTypeC> create(PropertyMetadata<ObjectType, PropertyTypeA> propertyA, PropertyMetadata<PropertyTypeA, PropertyTypeB> propertyB, PropertyMetadata<PropertyTypeB, PropertyTypeC> propertyC) {
        //noinspection unchecked
        return new SimplePropertyPath<>(propertyC.getType(), Arrays.asList((PropertyMetadata<Object, Object>) propertyA, (PropertyMetadata<Object, Object>) propertyB, (PropertyMetadata<Object, Object>) propertyC), null, null);
    }

    public SimplePropertyPath(Class<PropertyType> propertyType,
                              List<PropertyMetadata<Object, Object>> propertiesOnPath,
                              Function<ObjectType, PropertyType> reader,
                              BiFunction<ObjectType, PropertyType, Void> writer) {

        this.propertyType = propertyType;
        this.propertiesOnPath = propertiesOnPath;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public Class<PropertyType> getPropertyType() {
        return propertyType;
    }

    @Override
    public List<PropertyMetadata<Object, Object>> getPropertiesOnPath() {
        return propertiesOnPath;
    }

    @Override
    public Optional<Function<ObjectType, PropertyType>> getReader() {
        return Optional.ofNullable(reader);
    }

    @Override
    public Optional<BiFunction<ObjectType, PropertyType, Void>> getWriter() {
        return Optional.ofNullable(writer);
    }

    public static class Builder<ObjectType, PropertyType> {

        private final Class<PropertyType> propertyType;
        private final List<PropertyMetadata<Object, Object>> propertiesOnPath;
        private Function<ObjectType, PropertyType> reader;
        private BiFunction<ObjectType, PropertyType, Void> writer;

        private Builder(Class<PropertyType> propertyType) {
            this.propertyType = propertyType;
            propertiesOnPath = new ArrayList<>();
        }

        public Builder<ObjectType, PropertyType> property(PropertyMetadata<Object, Object> property) {
            propertiesOnPath.add(property);
            return this;
        }

        public Builder<ObjectType, PropertyType> properties(List<PropertyMetadata<Object, Object>> properties) {
            propertiesOnPath.addAll(properties);
            return this;
        }

        public Builder<ObjectType, PropertyType> reader(Function<ObjectType, PropertyType> reader) {
            this.reader = reader;
            return this;
        }

        public Builder<ObjectType, PropertyType> writer(BiFunction<ObjectType, PropertyType, Void> writer) {
            this.writer = writer;
            return this;
        }

        public SimplePropertyPath<ObjectType, PropertyType> build() {
            return new SimplePropertyPath<>(propertyType, propertiesOnPath, reader, writer);
        }
    }
}
