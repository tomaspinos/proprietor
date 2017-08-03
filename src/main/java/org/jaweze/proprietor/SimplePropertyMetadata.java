package org.jaweze.proprietor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimplePropertyMetadata<ObjectType, PropertyType> implements PropertyMetadata<ObjectType, PropertyType> {

    private final String name;
    private final Class<PropertyType> type;
    private final Function<ObjectType, PropertyType> reader;
    private final BiFunction<ObjectType, PropertyType, Void> writer;

    public SimplePropertyMetadata(String name, Class<PropertyType> type, Function<ObjectType, PropertyType> reader, BiFunction<ObjectType, PropertyType, Void> writer) {
        this.name = name;
        this.type = type;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<PropertyType> getType() {
        return type;
    }

    @Override
    public Optional<Function<ObjectType, PropertyType>> getReader() {
        return Optional.ofNullable(reader);
    }

    @Override
    public Optional<BiFunction<ObjectType, PropertyType, Void>> getWriter() {
        return Optional.ofNullable(writer);
    }

    @Override
    public String toString() {
        return "SimplePropertyMetadata{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", reader=" + (reader != null) +
                ", writer=" + (writer != null) +
                '}';
    }
}
