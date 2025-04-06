package com.gj.cloud.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gj.cloud.common.converter.IdentityConverter;
import com.gj.cloud.common.converter.exception.ConverterNotFoundException;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class ConvertUtils {
    private static final List<Converter<?, ?>> CONVERTERS = new ArrayList<Converter<?, ?>>();
    static {
        List<Class<?>> converterClazzList = ClassUtils.getPredicatedClasses("com.sunwayworld",
                t -> Converter.class.isAssignableFrom(t));

        for (Class<?> converterClazz : converterClazzList) {
            if (converterClazz.isEnum()) {
                CONVERTERS.add((Converter<?, ?>) ((Class<Enum<?>>) converterClazz).getEnumConstants()[0]);
            }
        }
    }

    private static Map<String, Converter<?, ?>> CONVERTER_CACHE = new HashMap<>();

    private static final List<ConverterFactory<?, ?>> CONVERTER_FACTORY = new ArrayList<ConverterFactory<?, ?>>();
    static {
        List<Class<?>> converterFactoryClazzList = ClassUtils.getPredicatedClasses("com.sunwayworld",
                t -> ConverterFactory.class.isAssignableFrom(t));

        for (Class<?> converterFactoryClazz : converterFactoryClazzList) {
            if (converterFactoryClazz.isEnum()) {
                CONVERTER_FACTORY.add((ConverterFactory<?, ?>) ((Class<Enum<?>>) converterFactoryClazz).getEnumConstants()[0]);
            }
        }
    }

    public static final <T, S> T convert(final S source, final Class<T> targetType) {
        return convert(source, targetType, (T) ClassUtils.getPrimitiveDefaultValue(targetType));
    }

    public static final <T, S> boolean canConvert(final S source, final Class<T> targetType) {
        if (source == null || Object.class == targetType) {
            return true;
        }

        Converter<S, T> converter = getConverterIfPresent((Class<S>) source.getClass(), targetType);

        if (converter == null) {
            return false;
        } else {
            try {
                converter.convert(source);

                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public static final <T, S> T convert(final S source, final Class<T> targetType, final T defaultValue) {
        if (source == null) {
            return defaultValue;
        }

        Class<S> sourceType = (Class<S>) source.getClass();

        Converter<S, T> converter = getConverterIfPresent(sourceType, targetType);

        if (converter != null) {
            T value = (T) converter.convert(source);

            if (value == null) {
                return defaultValue;
            }

            return value;
        }

        // throw exception
        throw new ConverterNotFoundException(sourceType, targetType);
    }

    public static final <S> Class<S> getSourceGenericType(Class<?> converterOrConverterFactoryType) {
        ResolvableType resolvableType = getRootResolvableType(converterOrConverterFactoryType);

        return (Class<S>) resolvableType.getGeneric(0).resolve();
    }

    public static final <T> Class<T> getTargetGenericType(Class<?> converterOrConverterFactoryType) {
        ResolvableType resolvableType = getRootResolvableType(converterOrConverterFactoryType);

        return (Class<T>) resolvableType.getGeneric(1).resolve();
    }

    // -------------------------------------------------------------------------
    // private methods
    // -------------------------------------------------------------------------
    @SuppressWarnings("rawtypes")
    private static final <T, S> Converter<S, T> getConverterIfPresent(Class<S> sourceType, Class<T> targetType) {
        Class<?> resolvedSourceType = ClassUtils.resolvePrimitiveIfNecessary(sourceType);
        Class<?> resolvedTargetType = ClassUtils.resolvePrimitiveIfNecessary(targetType);

        String cacheKey = new StringBuilder(resolvedSourceType.getName()).append("-").append(resolvedTargetType.getName()).toString();

        if (!CONVERTER_CACHE.containsKey(cacheKey)) {
            if (Object.class.equals(resolvedTargetType)
                    || resolvedTargetType.isAssignableFrom(resolvedSourceType)) {
                return (Converter<S, T>) new IdentityConverter();
            }

            Converter<S, T> converter = (Converter<S, T>) getConverter(resolvedSourceType, resolvedTargetType);
            if (converter == null) {
                final ConverterFactory<S, T> converterFactory = (ConverterFactory<S, T>) getConverterFactory(resolvedSourceType, resolvedTargetType);

                if (converterFactory != null) {
                    converter = converterFactory.getConverter((Class<T>) resolvedTargetType);
                }
            }

            CONVERTER_CACHE.put(cacheKey, converter);
        }

        return(Converter<S, T>) CONVERTER_CACHE.get(cacheKey);
    }

    private static final <T, S> Converter<S, T> getConverter(Class<S> sourceType, Class<T> targetType) {
        for (Converter<?, ?> converter : CONVERTERS) {
            Class<?> converterSourceType = getSourceGenericType(converter.getClass());

            if (!converterSourceType.isAssignableFrom(sourceType)) {
                continue;
            }

            Class<?> converterTargetType = getTargetGenericType(converter.getClass());

            if (!converterTargetType.isAssignableFrom(targetType)) {
                continue;
            }

            return (Converter<S, T>) converter;
        }

        return null;
    }

    private static final <T, S> ConverterFactory<S, T> getConverterFactory(Class<S> sourceType, Class<T> targetType) {
        for (ConverterFactory<?, ?> converterFactory : CONVERTER_FACTORY) {
            Class<?> converterSourceType = getSourceGenericType(converterFactory.getClass());

            if (!converterSourceType.isAssignableFrom(sourceType)) {
                continue;
            }

            Class<?> converterTargetType = getTargetGenericType(converterFactory.getClass());

            if (!converterTargetType.isAssignableFrom(targetType)) {
                continue;
            }

            return (ConverterFactory<S, T>) converterFactory;
        }

        return null;
    }

    private static final ResolvableType getRootResolvableType(Class<?> type) {
        if (Converter.class.isAssignableFrom(type)) {
            return ResolvableType.forClass(Converter.class, type);
        } else {
            return ResolvableType.forClass(ConverterFactory.class, type);
        }
    }
}
