package com.gj.cloud.common.converter.exception;

import org.springframework.core.convert.ConversionException;

import java.io.Serial;

public class ConverterNotFoundException extends ConversionException {

    @Serial
    private static final long serialVersionUID = -1019680033460221983L;

    public ConverterNotFoundException(Class<?> sourceType, Class<?> targetType) {
        super("No converter found capable of converting from type [" + sourceType.getName() + "] to type ["
                + targetType.getName() + "]");
    }
}
