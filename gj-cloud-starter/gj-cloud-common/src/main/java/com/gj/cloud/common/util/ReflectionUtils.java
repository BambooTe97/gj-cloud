package com.gj.cloud.common.util;

import com.gj.cloud.common.cache.MemoryCacheHelper;
import com.gj.cloud.common.exception.BusinessException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import org.springframework.core.ParameterNameDiscoverer;

public class ReflectionUtils {
    private static final Map<String, Annotation> CLASS_ANNOTATION_CONTAINER = new HashMap<>();

    private static final Map<String, Annotation> METHOD_ANNOTATION_CONTAINER = new HashMap<>();

    /**
     * Test if the given {@code Method} is a read method.
     */
    public static final boolean isReadMethod(Method method) {
        final String methodName = method.getName();

        return (StringUtils.startsWith(methodName, "CGLIB$get") || StringUtils.startsWith(methodName, "get")
                || StringUtils.startsWith(methodName, "CGLIB$is") || StringUtils.startsWith(methodName, "is"))
                && method.getParameterCount() == 0;
    }

    /**
     * Test if the given {@code Method} is a write method.
     */
    public static final boolean isWriteMethod(Method method) {
        final String methodName = method.getName();

        return (StringUtils.startsWith(methodName, "CGLIB$set") || StringUtils.startsWith(methodName, "set"))
                && method.getParameterCount() == 1;
    }

    /**
     * Attempt to find a get {@link Method} on the supplied class with the supplied
     * property name. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findReadMethod(Class<?> clazz, String propertyName) {
        Method readMethod = findMethod(clazz, "get" + StringUtils.capitalize(propertyName), new Class<?>[0]);

        if (readMethod == null) {
            return findMethod(clazz, "is" + StringUtils.capitalize(propertyName), new Class<?>[0]);
        }

        return readMethod;
    }

    /**
     * Attempt to find a write {@link Method} on the supplied class with the
     * supplied property name. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findWriteMethod(Class<?> clazz, String propertyName) {
        return findMethodByName(clazz, "set" + StringUtils.capitalize(propertyName));
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied
     * name. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findMethodByName(Class<?> clazz, String name) {
        Class<?> searchClazz = clazz;

        // search all super class first
        while (searchClazz != null) {
            final Method method = Arrays.asList(searchClazz.getDeclaredMethods()).stream()
                    .filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

            if (method != null) {
                return method;
            }

            searchClazz = searchClazz.getSuperclass();
        }

        // search interfaces
        for (Class<?> iClazz : clazz.getInterfaces()) {
            final Method method = Arrays.asList(iClazz.getMethods()).stream()
                    .filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

            if (method != null) {
                return method;
            }
        }

        return null;
    }

    public static List<Method> getMethodList(Class<?> clazz, int... modifiers) {
        List<Method> methodList = new ArrayList<>();

        Class<?> searchClazz = clazz;

        // search all super class first
        while (searchClazz != null) {
            Method[] methods = searchClazz.getDeclaredMethods();

            for (Method method : methods) {
                if (methodList.stream().noneMatch(f -> f.equals(method))) {
                    if (modifiers == null
                            || Arrays.stream(modifiers).allMatch(m -> ((method.getModifiers() & m) != 0))) {
                        methodList.add(method);
                    }
                }
            }

            searchClazz = searchClazz.getSuperclass();
        }

        return methodList;
    }

    public static List<Method> getAnnotatedMethodList(Class<?> clazz, Class<? extends Annotation> annotationClazz) {
        List<Method> methodList = new ArrayList<>();

        Class<?> searchClazz = clazz;
        // search all super class first
        while (searchClazz != null) {
            Method[] methods = searchClazz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(annotationClazz)
                        && methodList.stream().noneMatch(m -> sameNameAndParameterTypes(m, method))) {
                    methodList.add(method);
                }
            }

            searchClazz = searchClazz.getSuperclass();
        }

        searchClazz = clazz;
        // search all interfaces
        while (searchClazz != null) {
            for (Class<?> iClazz : searchClazz.getInterfaces()) {
                Method[] interfaceMethods = iClazz.getMethods();

                for (Method interfaceMethod : interfaceMethods) {
                    if (interfaceMethod.isAnnotationPresent(annotationClazz)
                            && methodList.stream().noneMatch(m -> sameNameAndParameterTypes(m, interfaceMethod))) {
                        methodList.add(interfaceMethod);
                    }
                }
            }

            searchClazz = searchClazz.getSuperclass();
        }

        return methodList;
    }

    public static <A extends Annotation> A getMethodAnnotation(Class<?> clazz, Method method,
                                                               Class<A> annotationClazz) {
        Class<?> searchClazz = clazz;
        // search all super class first
        while (searchClazz != null) {
            Method matchMethod = Arrays.stream(searchClazz.getDeclaredMethods())
                    .filter(m -> sameNameAndParameterTypes(m, method) && m.isAnnotationPresent(annotationClazz))
                    .findFirst().orElse(null);

            if (matchMethod != null) {
                return matchMethod.getAnnotation(annotationClazz);
            }

            searchClazz = searchClazz.getSuperclass();
        }

        // search all interfaces
        for (Class<?> iClazz : getAllInterfaces(clazz)) {
            Method matchMethod = Arrays.stream(iClazz.getDeclaredMethods())
                    .filter(m -> sameNameAndParameterTypes(m, method) && m.isAnnotationPresent(annotationClazz))
                    .findFirst().orElse(null);

            if (matchMethod != null) {
                return matchMethod.getAnnotation(annotationClazz);
            }
        }

        return null;
    }

    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        if (clazz == null || Object.class.equals(clazz)) {
            return Collections.emptyList();
        }

        List<Class<?>> interfaceList = new ArrayList<>();
        Class<?> searchClazz = clazz;
        // search all super class first
        while (searchClazz != null) {
            for (Class<?> iClass : searchClazz.getInterfaces()) {
                interfaceList.add(iClass);
                interfaceList.addAll(getAllInterfaces(iClass));
            }

            searchClazz = searchClazz.getSuperclass();
        }

        return interfaceList;
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and no parameters. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class<?>[0]);
    }

    /**
     * Attempt to find a @link Method} on the supplied class with the supplied name
     * and parameter types. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Class<?> searchClazz = clazz;

        // search all super class first
        while (searchClazz != null) {
            final Method method = Arrays.asList(searchClazz.getDeclaredMethods()).stream()
                    .filter(m -> m.getName().equalsIgnoreCase(name)
                            && (parameterTypes == null || Arrays.equals(m.getParameterTypes(), parameterTypes)))
                    .findFirst().orElse(null);

            if (method != null) {
                return method;
            }

            searchClazz = searchClazz.getSuperclass();
        }

        // search interfaces
        for (Class<?> iClazz : clazz.getInterfaces()) {
            final Method method = Arrays.asList(iClazz.getMethods()).stream()
                    .filter(m -> m.getName().equalsIgnoreCase(name)
                            && (parameterTypes == null || Arrays.equals(m.getParameterTypes(), parameterTypes)))
                    .findFirst().orElse(null);

            if (method != null) {
                return method;
            }
        }

        return null;
    }

    /**
     * Attempt to find a @link Method} on the supplied class with the supplied name
     * and the given return type. Searches all superclasses up to {@code Object}.
     * <p>
     * Returns {@code null} if no {@link Method} can be found.
     */
    public static final Method findMethodWithReturnType(Class<?> clazz, String name, Type returnType) {
        Class<?> searchClazz = clazz;

        // search all super class first
        while (searchClazz != null) {
            Method method = Arrays.asList(searchClazz.getDeclaredMethods()).stream()
                    .filter(m -> m.getName().equals(name) && m.getReturnType().equals(returnType)).findFirst()
                    .orElse(null);

            if (method != null) {
                return method;
            }

            searchClazz = searchClazz.getSuperclass();
        }

        // search interfaces
        for (Class<?> iClazz : clazz.getInterfaces()) {
            final Method method = Arrays.asList(iClazz.getMethods()).stream()
                    .filter(m -> m.getName().equals(name) && m.getReturnType().equals(returnType)).findFirst()
                    .orElse(null);

            if (method != null) {
                return method;
            }
        }

        return null;
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object with
     * no arguments. The target object can be {@code null} when invoking a static
     * {@link Method}.
     */
    public static final Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * Invoke the specified {@link Method} against the supplied target object with
     * the supplied arguments. The target object can be {@code null} when invoking a
     * static {@link Method}.
     */
    public static final Object invokeMethod(Method method, Object target, Object... args) {
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            return method.invoke(target, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * Invoke the specified property public write {@link Method} against the
     * supplied target object with the supplied arguments.
     */
    public static final void invokeWriteMethod(Object target, String propertyName, Object arg) {
        Method writeMethod = findWriteMethod(target.getClass(), propertyName);

        if (writeMethod != null) {
            invokeMethod(writeMethod, target, arg);
        } else {
            throw new BusinessException(
                    "No [" + propertyName + "] set method of [" + target.getClass().getName() + "].");
        }
    }

    /**
     * Invoke the specified property public read {@link Method} against the supplied
     * target object with the supplied arguments.
     */
    public static final Object invokeReadMethod(Object target, String propertyName) {
        final Method readMethod = findReadMethod(target.getClass(), propertyName);

        if (readMethod != null) {
            return invokeMethod(readMethod, target);
        } else {
            throw new BusinessException(
                    "No [" + propertyName + "] get method of [" + target.getClass().getName() + "].");
        }
    }

    public static List<Field> getFieldList(Class<?> clazz, int... modifiers) {
        List<Field> fieldList = new ArrayList<>();

        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();

            for (Field field : fields) {
                if (fieldList.stream().noneMatch(f -> f.getName().equals(field.getName()))) {
                    if (modifiers == null
                            || Arrays.stream(modifiers).allMatch(m -> ((field.getModifiers() & m) != 0))) {
                        fieldList.add(field);
                    }
                }
            }

            searchType = searchType.getSuperclass();
        }

        return fieldList;
    }

    /**
     * Attempt to find a {@link Field} on the supplied {@link Class} with the
     * supplied {@code name}. Searches all superclasses up to {@link Object}.
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name} and/or {@link Class type}. Searches all superclasses up
     * to {@link Object}.
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        return MemoryCacheHelper.get(new StringBuilder("REFLECTION_FIND_FIELD:").append(clazz.getName()).append("$")
                .append(name).append("$").append(type == null ? null : type.getName()).toString(), () -> {
            Class<?> searchType = clazz;

            while (Object.class != searchType && searchType != null) {
                final Field field = Arrays.asList(searchType.getDeclaredFields()).stream().filter(
                                f -> f.getName().equalsIgnoreCase(name) && (type == null || f.getType().equals(type)))
                        .findFirst().orElse(null);

                if (field != null) {
                    return field;
                }

                searchType = searchType.getSuperclass();
            }

            return null;
        });
    }

    /**
     * Attempt to find a {@link Field} of the specified read {@link Method}
     */
    public static final Field findFieldOfReadMethod(Method readMethod) {
        if (!ReflectionUtils.isReadMethod(readMethod)) {
            throw new BusinessException("Method [" + readMethod + "] is not a get method.");
        }

        Class<?> clazz = readMethod.getDeclaringClass();

        if (ClassUtils.isCglibProxyClass(clazz)) {
            clazz = clazz.getSuperclass();
        }

        String methodName = readMethod.getName();

        String propertyName = null;

        // read method name starts with is or get
        if (StringUtils.startsWith(methodName, "is")) {
            propertyName = methodName.substring(2);
        } else {
            propertyName = methodName.substring(3);
        }

        return findField(clazz, StringUtils.uncapitalize(propertyName), readMethod.getReturnType());
    }

    /**
     * Attempt to find a {@link Field} of the specified write {@link Method}
     */
    public static final Field findFieldOfWriteMethod(Method writeMethod) {
        if (!ReflectionUtils.isWriteMethod(writeMethod)) {
            throw new BusinessException("Method [" + writeMethod + "] is not a set method.");
        }

        Class<?> clazz = writeMethod.getDeclaringClass();

        if (ClassUtils.isCglibProxyClass(clazz)) {
            clazz = clazz.getSuperclass();
        }

        // write method name starts with set
        final String propertyName = writeMethod.getName().substring(3);

        return findField(clazz, StringUtils.uncapitalize(propertyName), writeMethod.getParameterTypes()[0]);
    }

    public static final Object getFieldValue(Object target, String propertyName) {
        Field property = findField(ClassUtils.getRawType(target.getClass()), propertyName);

        if (property == null) {
            return null;
        }

        return getFieldValue(target, property);
    }

    public static final Object getFieldValue(Object target, Field propertyField) {
        try {
            makeAccesible(propertyField);

            return propertyField.get(target);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new BusinessException(ex);
        }
    }

    public static final void setFieldValue(Object target, String propertyName, Object value) {
        Field property = findField(ClassUtils.getRawType(target.getClass()), propertyName);

        if (property == null) {
            return;
        }

        try {
            makeAccesible(property);

            property.set(target, value);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new BusinessException(ex);
        }
    }

    public static final <T extends Annotation> T getAnnotation(Class<?> targetClass, Class<T> annotationClass) {
        Class<?> clazz = ClassUtils.getRawType(targetClass);

        String key = clazz.getName() + "-" + annotationClass.getName();

        @SuppressWarnings("unchecked")
        T cacheAnnotation = (T) CLASS_ANNOTATION_CONTAINER.get(key);

        if (cacheAnnotation == null) {
            Class<?> searchClazz = clazz;

            while (searchClazz != null) {
                cacheAnnotation = searchClazz.getAnnotation(annotationClass);

                if (cacheAnnotation != null) {
                    break;
                }

                for (Class<?> iClazz : searchClazz.getInterfaces()) {
                    T interfaceAnnotation = iClazz.getAnnotation(annotationClass);

                    if (interfaceAnnotation != null) {
                        return interfaceAnnotation;
                    }
                }

                searchClazz = searchClazz.getSuperclass();
            }

            if (cacheAnnotation != null) {
                CLASS_ANNOTATION_CONTAINER.put(key, cacheAnnotation);
            }
        }

        return cacheAnnotation;
    }

    public static final <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        Class<?> clazz = ClassUtils.getRawType(method.getDeclaringClass());

        String key = method.toGenericString() + "-" + annotationClass.getName();

        @SuppressWarnings("unchecked")
        T cacheAnnotation = (T) METHOD_ANNOTATION_CONTAINER.get(key);

        if (cacheAnnotation == null) {
            Class<?> searchClazz = clazz;

            while (searchClazz != null) {
                try {
                    Method searchMethod = searchClazz.getMethod(method.getName(), method.getParameterTypes());

                    if (searchMethod != null) {
                        cacheAnnotation = searchMethod.getAnnotation(annotationClass);

                        if (cacheAnnotation != null) {
                            break;
                        }
                    }

                    for (Class<?> iClazz : searchClazz.getInterfaces()) {
                        Method searchInterfaceMethod = iClazz.getMethod(method.getName(), method.getParameterTypes());

                        if (searchInterfaceMethod != null) {
                            cacheAnnotation = searchInterfaceMethod.getAnnotation(annotationClass);

                            if (cacheAnnotation != null) {
                                break;
                            }
                        }
                    }
                } catch (NoSuchMethodException | SecurityException e) {
                    /* ignore */
                }

                if (cacheAnnotation != null) {
                    break;
                }
                searchClazz = searchClazz.getSuperclass();
            }

            if (cacheAnnotation != null) {
                METHOD_ANNOTATION_CONTAINER.put(key, cacheAnnotation);
            }
        }

        return cacheAnnotation;
    }

    public static final boolean isValidBeanProperty(Class<?> type, Field property) {
        return !property.getName().equalsIgnoreCase("readonly") && findReadMethod(type, property.getName()) != null
                && findWriteMethod(type, property.getName()) != null;
    }

//    public static String[] getParameterNames(Method method) {
//        ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
//
//        return parameterNameDiscoverer.getParameterNames(method);
//    }

    public static String getSimpleSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        appendType(sb, method.getReturnType());
        sb.append(" ");

        appendType(sb, method.getDeclaringClass());
        sb.append(".").append(method.getName()).append("(");
        Class<?>[] parametersTypes = method.getParameterTypes();
        appendTypes(sb, parametersTypes);
        sb.append(")");

        return sb.toString();
    }

    // --------------------------------------------------------------------
    // 私有方法
    // --------------------------------------------------------------------
    private static void appendTypes(StringBuilder sb, Class<?>[] types) {
        for (int size = types.length, i = 0; i < size; i++) {
            appendType(sb, types[i]);
            if (i < size - 1) {
                sb.append(",");
            }
        }
    }

    private static void appendType(StringBuilder sb, Class<?> type) {
        if (type.isArray()) {
            appendType(sb, type.getComponentType());
            sb.append("[]");
        } else {
            sb.append(type.getSimpleName());
        }
    }

    private static boolean sameNameAndParameterTypes(Method m1, Method m2) {
        if (m1 == null) {
            return m2 == null;
        }

        if (m2 == null) {
            return false;
        }

        if (!m1.getName().equals(m2.getName())) {
            return false;
        }

        Class<?>[] types1 = m1.getParameterTypes();
        Class<?>[] types2 = m2.getParameterTypes();

        if (types1.length != types2.length) {
            return false;
        }

        for (int i = 0, j = types1.length; i < j; i++) {
            if (!types1[i].equals(types2[i])) {
                return false;
            }
        }

        return true;
    }

    private static void makeAccesible(Field field) {
        if (field == null) {
            return;
        }

        if (Modifier.isFinal(field.getModifiers())) {
            try {
                Field modifiersProperty = Field.class.getDeclaredField("modifiers");
                modifiersProperty.setAccessible(true);
                modifiersProperty.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                throw new BusinessException(e);
            }
        }

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }
}
