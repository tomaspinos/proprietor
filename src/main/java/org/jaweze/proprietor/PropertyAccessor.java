package org.jaweze.proprietor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface PropertyAccessor<ObjectType, PropertyType> {

    Optional<Function<ObjectType, PropertyType>> getReader();

    Optional<BiFunction<ObjectType, PropertyType, Void>> getWriter();
}
