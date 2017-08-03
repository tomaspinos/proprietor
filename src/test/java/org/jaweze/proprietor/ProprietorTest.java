package org.jaweze.proprietor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProprietorTest {

    public static class BeanA {

        private final String name;
        private final BeanA nestedBean;

        public BeanA(String name, BeanA nestedBean) {
            this.name = name;
            this.nestedBean = nestedBean;
        }

        public String getName() {
            return name;
        }

        public BeanA getNestedBean() {
            return nestedBean;
        }

        @Override
        public String toString() {
            return "BeanA{" +
                    "name='" + name + '\'' +
                    ", nestedBean=" + nestedBean +
                    '}';
        }
    }

    public static class BeanA_ {

        public static final PropertyMetadata<BeanA, String> name = new SimplePropertyMetadata<>("name", String.class, BeanA::getName, null);

        public static final PropertyMetadata<BeanA, BeanA> nestedBean = new SimplePropertyMetadata<>("nestedBean", BeanA.class, BeanA::getNestedBean, null);
    }

    @Test
    public void shouldGetOwnPropertyValue() {
        SimplePropertyPath<BeanA, String> path = SimplePropertyPath.create(BeanA_.name);

        BeanA obj = new BeanA("a", null);

        assertEquals("a", Proprietor.get(path, obj));
    }

    @Test
    public void shouldGetNestedPropertyValue_level1() {
        SimplePropertyPath<BeanA, String> path = SimplePropertyPath.create(BeanA_.nestedBean, BeanA_.name);

        BeanA obj = new BeanA("a", new BeanA("aa", null));

        assertEquals("aa", Proprietor.get(path, obj));
    }

    @Test
    public void shouldGetNestedPropertyValue_level2() {
        SimplePropertyPath<BeanA, String> path = SimplePropertyPath.create(BeanA_.nestedBean, BeanA_.nestedBean, BeanA_.name);

        BeanA obj = new BeanA("a", new BeanA("aa", new BeanA("aaa", null)));

        assertEquals("aaa", Proprietor.get(path, obj));
    }
}
