package org.jaweze.proprietor;

import java.util.ArrayList;
import java.util.Iterator;

public final class Proprietor {

    private Proprietor() {
        throw new UnsupportedOperationException();
    }

    public static <ObjectType, PropertyType> PropertyType get(PropertyPath<ObjectType, PropertyType> path,
                                                              ObjectType obj) {

        Object value = obj;

        Iterator<PropertyMetadata<Object, Object>> iterator = path.getPropertiesOnPath().iterator();
        while (iterator.hasNext() && value != null) {
            PropertyMetadata<Object, Object> property = iterator.next();
            Object currentObj = value;
            value = property.getReader().map(reader -> reader.apply(currentObj)).orElse(null);
        }

        //noinspection unchecked
        return (PropertyType) value;
    }

    public static <ObjectType, PropertyType> void set(PropertyPath<ObjectType, PropertyType> path,
                                                      ObjectFactory objectFactory,
                                                      ObjectType obj,
                                                      PropertyType value) {

        Object currentObj = obj;

        Iterator<PropertyMetadata<Object, Object>> iterator = path.getPropertiesOnPath().iterator();
        while (iterator.hasNext()) {
            PropertyMetadata<Object, Object> property = iterator.next();

            if (!property.getWriter().isPresent()) {
                return;
            }

            if (iterator.hasNext()) {
                Object nestedObject = objectFactory.newInstance(property.getType());
                property.getWriter().get().apply(currentObj, nestedObject);
                currentObj = nestedObject;
            } else {
                property.getWriter().get().apply(currentObj, value);
            }
        }
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB>
    PropertyPath<ObjectType, PropertyTypeB> compose(PropertyPath<ObjectType, PropertyTypeA> pathA,
                                                    PropertyPath<PropertyTypeA, PropertyTypeB> pathB,
                                                    ObjectFactory objectFactory) {

        ArrayList<PropertyMetadata<Object, Object>> propertiesOnPath = new ArrayList<>();
        propertiesOnPath.addAll(pathA.getPropertiesOnPath());
        propertiesOnPath.addAll(pathB.getPropertiesOnPath());

        return SimplePropertyPath.<ObjectType, PropertyTypeB>builder(pathB.getPropertyType())
                .properties(propertiesOnPath)
                .reader(objectType -> {
                    if (pathA.getReader().isPresent() && pathB.getReader().isPresent()) {
                        return pathB.getReader().get().apply(pathA.getReader().get().apply(objectType));
                    } else {
                        return null;
                    }
                })
                .writer((objectType, propertyTypeB) -> {
                    if (pathA.getWriter().isPresent() && pathB.getWriter().isPresent()) {
                        PropertyTypeA propertyA = objectFactory.newInstance(pathA.getPropertyType());
                        pathA.getWriter().get().apply(objectType, propertyA);
                        pathB.getWriter().get().apply(propertyA, propertyTypeB);
                    }
                    return null;
                })
                .build();
    }
}
