package org.jaweze.proprietor;

public interface ObjectFactory {

    <ObjectType> ObjectType newInstance();
}
