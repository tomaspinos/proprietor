package org.jaweze.proprietor;

import java.util.ArrayList;

public final class Proprietor {

    private Proprietor() {
        throw new UnsupportedOperationException();
    }

    public static <ObjectType, PropertyType, OtherPropertyType>
    PropertyPath<ObjectType, OtherPropertyType> compose(PropertyPath<ObjectType, PropertyType> path,
                                                        PropertyPath<PropertyType, OtherPropertyType> otherPath,
                                                        ObjectFactory objectFactory) {

        ArrayList<PropertyMetadata<?, ?>> propertiesOnPath = new ArrayList<>();
        propertiesOnPath.addAll(path.getPropertiesOnPath());
        propertiesOnPath.addAll(otherPath.getPropertiesOnPath());

        return ImmutablePropertyPath.<ObjectType, OtherPropertyType>builder()
                .addAllPropertiesOnPath(propertiesOnPath)
                .reader(objectType -> {
                    if (path.getReader().isPresent() && otherPath.getReader().isPresent()) {
                        return otherPath.getReader().get().apply(path.getReader().get().apply(objectType));
                    } else {
                        return null;
                    }
                })
                .writer((objectType, otherPropertyType) -> {
                    if (path.getWriter().isPresent() && otherPath.getWriter().isPresent()) {
                        PropertyType property = objectFactory.newInstance();
                        path.getWriter().get().apply(objectType, property);
                        otherPath.getWriter().get().apply(property, otherPropertyType);
                    }
                    return null;
                })
                .build();
    }
}
