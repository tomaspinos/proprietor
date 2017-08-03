package org.jaweze.proprietor;

public interface ObjectFactory {

    <ObjectType> ObjectType newInstance(Class<ObjectType> type);
}
