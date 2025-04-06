package com.gj.cloud.common.converter;

import org.springframework.core.convert.converter.Converter;

public class IdentityConverter<T, S extends T> implements Converter<S, T> {
    @Override
    public T convert(S source) {
        return source;
    }
}
