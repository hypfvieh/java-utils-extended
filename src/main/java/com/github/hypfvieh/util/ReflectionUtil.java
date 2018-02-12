package com.github.hypfvieh.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Utility methods which uses reflection techniques.
 *
 * @author David Michaelis (David.Michaelis@baaderbank.de)
 * @since v1.0.1 - 2018-02-12
 */
public final class ReflectionUtil {

    private ReflectionUtil() {

    }

    /**
     * Extract all {@link Field}s found in the given class recursively.
     * This means, all {@link Field}s are retrieved, even fields which only exists in superclasses.<br>
     * <br>
     * <b>NOTE:</b> Accessibility of {@link Field}s returned in the {@link Set} have not been changed
     * (setAccessable(true) is NOT called explicitly)!
     *
     * @param _class
     * @param _fieldsToIgnore
     * @return null if _class was null, Set otherwise
     */
    public static Set<Field> getAllDeclaredFields(Class<?> _class, String... _fieldsToIgnore) {
        if (_class == null) {
            return null;
        }

        List<String> ignoredFields;
        if (_fieldsToIgnore == null || _fieldsToIgnore.length == 0) {
            ignoredFields = new ArrayList<>();
        } else {
            ignoredFields = Arrays.asList(_fieldsToIgnore);
        }

        Set<Field> allFields = new LinkedHashSet<>();

        Field[] declaredFields = _class.getDeclaredFields();

        List<Field> filteredFields = Arrays.asList(declaredFields).stream()
                    .filter(f -> !ignoredFields.contains(f.getName()))
                    .collect(Collectors.toList());

        allFields.addAll(filteredFields);
        if (_class.getSuperclass() != null) {
            Set<Field> allDeclaredFields = getAllDeclaredFields(_class.getSuperclass(), _fieldsToIgnore);
            if (allDeclaredFields != null) {
                allFields.addAll(allDeclaredFields);
            }
        }
        return allFields;
    }

    /**
     * Does what {@link #getAllDeclaredFields(Class, String...)} does, but filters all non-static fields.
     *
     * @param _class
     * @param _fieldsToIgnore
     * @return
     */
    public static Set<Field> getAllDeclaredStaticFields(Class<?> _class, String... _fieldsToIgnore) {
        Set<Field> allDeclaredFields = getAllDeclaredFields(_class, _fieldsToIgnore);
        return allDeclaredFields.stream()
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Does what {@link #getAllDeclaredFields(Class, String...)} does, but filters all static fields.
     *
     * @param _class
     * @param _fieldsToIgnore
     * @return
     */
    public static Set<Field> getAllDeclaredNonStaticFields(Class<?> _class, String... _fieldsToIgnore) {
        Set<Field> allDeclaredFields = getAllDeclaredFields(_class, _fieldsToIgnore);
        return allDeclaredFields.stream()
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Extract all {@link Field}s with any of the given annotations found in the given class recursively.
     * This means, all {@link Field}s are retrieved, even fields which only exists in superclasses.<br>
     * <br>
     * <b>NOTE:</b> Accessibility of {@link Field}s returned in the {@link Set} have not been changed
     * (setAccessable(true) is NOT called explicitly)!
     *
     * @param _class
     * @param _annotations
     * @return null if _class was null, Set otherwise
     */
    @SafeVarargs
    public static Set<Field> getAllDeclaredFieldsAnnotatedWithAny(Class<?> _class, Class<? extends Annotation>... _annotations) {
        return getAllDeclaredFieldsWithAnnotationAction(_class, (f, a) -> f.isAnnotationPresent(a), _annotations);
    }

    /**
     * Extract all {@link Field}s without any of the given annotations found in the given class recursively.
     * This means, all {@link Field}s are retrieved, even fields which only exists in superclasses.<br>
     * <br>
     * <b>NOTE:</b> Accessibility of {@link Field}s returned in the {@link Set} have not been changed
     * (setAccessable(true) is NOT called explicitly)!
     *
     * @param _class
     * @param _annotations
     * @return null if _class was null, Set otherwise
     */
    @SafeVarargs
    public static Set<Field> getAllDeclaredFieldsNotAnnotatedWithAny(Class<?> _class, Class<? extends Annotation>... _annotations) {
        return getAllDeclaredFieldsWithAnnotationAction(_class, (f, a) -> !f.isAnnotationPresent(a), _annotations);
    }

    /**
     * Extract all {@link Field}s and validates each field against the given {@link BiConsumer} lambda expression.
     * Result will depend on lambda. All positive lambda results (test returns true), will be added to the result Set.
     *
     * @param _class
     * @param _withAnnotation
     * @param _annotations
     * @return null if class was null, Set otherwise
     */
    @SafeVarargs
    private static Set<Field> getAllDeclaredFieldsWithAnnotationAction(Class<?> _class, BiPredicate<Field, Class<? extends Annotation>> _withAnnotation, Class<? extends Annotation>... _annotations) {
        if (_class == null) {
            return null;
        }

        if (_annotations == null || _annotations.length == 0) {
            return new LinkedHashSet<>();
        }
        Set<Field> allDeclaredFields = getAllDeclaredFields(_class);

        Set<Field> result = new LinkedHashSet<>();
        for (Field field : allDeclaredFields) {
            for (Class<? extends Annotation> annot : _annotations) {
                if (_withAnnotation.test(field, annot)) {
                    result.add(field);
                    break;
                }
            }
        }
        return result;
    }
}
