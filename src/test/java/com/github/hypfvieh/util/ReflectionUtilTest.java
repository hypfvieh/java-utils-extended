package com.github.hypfvieh.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.github.hypfvieh.AbstractBaseUtilTest;

public class ReflectionUtilTest extends AbstractBaseUtilTest {

    @Test
    public void testGetAllDeclaredFields() {
        Set<Field> allDeclaredFields = ReflectionUtil.getAllDeclaredFields(TestClass3.class);
        List<String> cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field1", "field2", "field3", "staticField1");

        allDeclaredFields = ReflectionUtil.getAllDeclaredFields(TestClass2.class);
        cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field1", "field2", "field3");

        allDeclaredFields = ReflectionUtil.getAllDeclaredFields(TestClass1.class);
        cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field1", "field2");

        allDeclaredFields = ReflectionUtil.getAllDeclaredFields(TestClass2.class, "field2");
        cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field1", "field3");
        assertFalse(cll.contains("field2"));
    }

    @Test
    public void testGetAllDeclaredStaticFields() {
        Set<Field> allDeclaredFields = ReflectionUtil.getAllDeclaredStaticFields(TestClass3.class);
        List<String> cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "staticField1");

        allDeclaredFields = ReflectionUtil.getAllDeclaredStaticFields(TestClass1.class, "$jacocoData");
        assertTrue("Expected no declared static fields in " + TestClass1.class + " but got: " + allDeclaredFields, allDeclaredFields.isEmpty());

        allDeclaredFields = ReflectionUtil.getAllDeclaredStaticFields(TestClass2.class, "$jacocoData");
        assertTrue("Expected no declared static fields in " + TestClass2.class + " but got: " + allDeclaredFields, allDeclaredFields.isEmpty());
    }

    @Test
    public void testGetAllDeclaredNonStaticFields() {
        Set<Field> allDeclaredFields = ReflectionUtil.getAllDeclaredNonStaticFields(TestClass3.class);
        List<String> cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertFalse(cll.contains("staticField1"));

        allDeclaredFields = ReflectionUtil.getAllDeclaredNonStaticFields(TestClass1.class);
        assertFalse(allDeclaredFields.isEmpty());

        allDeclaredFields = ReflectionUtil.getAllDeclaredNonStaticFields(TestClass2.class);
        assertFalse(allDeclaredFields.isEmpty());
    }

    @Test
    public void testGetAllDeclaredAnnotatedFields() {
        Set<Field> allDeclaredFields = ReflectionUtil.getAllDeclaredFieldsAnnotatedWithAny(TestClass3.class, DummyAnnotation.class);
        List<String> cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field1", "field3");

        allDeclaredFields = ReflectionUtil.getAllDeclaredNonStaticFields(TestClass3.class);
        assertFalse(allDeclaredFields.isEmpty());
    }

    @Test
    public void testGetAllDeclaredNotAnnotatedFields() {
        Set<Field> allDeclaredFields = ReflectionUtil.getAllDeclaredFieldsNotAnnotatedWithAny(TestClass3.class, DummyAnnotation.class);
        List<String> cll = allDeclaredFields.stream().map(f -> f.getName()).collect(Collectors.toList());
        assertCollection(cll, "field2", "staticField1");
    }

    @SuppressWarnings("unused")
    static class TestClass1 {
        @DummyAnnotation
        private int field1;
        private int field2;
    }

    static class TestClass2 extends TestClass1 {
        @DummyAnnotation
        private int field3;
    }

    @SuppressWarnings("unused")
    static class TestClass3 extends TestClass2 {
        private static int staticField1;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @java.lang.annotation.Target(value = {
            ElementType.FIELD
    })
    public @interface DummyAnnotation {
    }

}
