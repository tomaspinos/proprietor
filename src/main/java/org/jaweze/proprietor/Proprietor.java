package org.jaweze.proprietor;

public final class Proprietor {

    private Proprietor() {
        throw new UnsupportedOperationException();
    }

    public static <ObjectType, PropertyType> PropertyType get(PropertyPath<ObjectType, PropertyType> path,
                                                              ObjectType obj) {

        validatePath(path);

        if (path.isEmpty()) {
            throw new IllegalStateException("Cannot get value by empty property path");
        }

        //noinspection ConstantConditions
        PropertyMetadata<ObjectType, ?> firstProperty = path.getFirstProperty().get();
        Object currentValue = firstProperty.getReader().map(reader -> reader.apply(obj)).orElse(null);
        //noinspection unchecked
        PropertyPath<Object, Object> currentPath = (PropertyPath<Object, Object>) path.getRemainingPath();

        while (currentValue != null && !currentPath.isEmpty()) {
            Object previousValue = currentValue;
            //noinspection ConstantConditions
            currentValue = currentPath.getFirstProperty().get().getReader().map(reader -> reader.apply(previousValue)).orElse(null);
            currentPath = currentPath.getRemainingPath();
        }

        //noinspection unchecked
        return (PropertyType) currentValue;
    }

    public static <ObjectType, PropertyType> void set(PropertyPath<ObjectType, PropertyType> path,
                                                      ObjectFactory objectFactory,
                                                      ObjectType obj,
                                                      PropertyType value) {

//        Object currentObj = obj;
//
//        Iterator<PropertyMetadata<Object, Object>> iterator = path.getPropertiesOnPath().iterator();
//        while (iterator.hasNext()) {
//            PropertyMetadata<Object, Object> property = iterator.next();
//
//            if (!property.getWriter().isPresent()) {
//                return;
//            }
//
//            if (iterator.hasNext()) {
//                Object nestedObject = objectFactory.newInstance(property.getType());
//                property.getWriter().get().apply(currentObj, nestedObject);
//                currentObj = nestedObject;
//            } else {
//                property.getWriter().get().apply(currentObj, value);
//            }
//        }
    }

    public static <ObjectType, PropertyTypeA, PropertyTypeB>
    PropertyPath<ObjectType, PropertyTypeB> compose(PropertyPath<ObjectType, PropertyTypeA> pathA,
                                                    PropertyPath<PropertyTypeA, PropertyTypeB> pathB,
                                                    ObjectFactory objectFactory) {

        return null;
//        ArrayList<PropertyMetadata<Object, Object>> propertiesOnPath = new ArrayList<>();
//        propertiesOnPath.addAll(pathA.getPropertiesOnPath());
//        propertiesOnPath.addAll(pathB.getPropertiesOnPath());
//
//        return SimplePropertyPath.<ObjectType, PropertyTypeB>builder(pathB.getPropertyType())
//                .properties(propertiesOnPath)
//                .reader(objectType -> {
//                    if (pathA.getReader().isPresent() && pathB.getReader().isPresent()) {
//                        return pathB.getReader().get().apply(pathA.getReader().get().apply(objectType));
//                    } else {
//                        return null;
//                    }
//                })
//                .writer((objectType, propertyTypeB) -> {
//                    if (pathA.getWriter().isPresent() && pathB.getWriter().isPresent()) {
//                        PropertyTypeA propertyA = objectFactory.newInstance(pathA.getPropertyType());
//                        pathA.getWriter().get().apply(objectType, propertyA);
//                        pathB.getWriter().get().apply(propertyA, propertyTypeB);
//                    }
//                    return null;
//                })
//                .build();
    }

    public static void validatePath(PropertyPath<?, ?> path) {
        if (path.isEmpty() == path.getFirstProperty().isPresent()) {
            throw new IllegalStateException("Invalid property path: " + path);
        }
    }
}
