package com.gj.cloud.common.converter.exception;

import org.springframework.core.convert.ConversionException;

import java.io.Serial;

public class ConversionFailedException  extends ConversionException {

    @Serial
    private static final long serialVersionUID = -1146315293078224188L;

    public ConversionFailedException(Class<?> sourceType, Class<?> targetType, Throwable cause) {
        super("Converting from type [" + sourceType.getName() + "] to type [" + targetType.getName() + "]", cause);
    }

    public ConversionFailedException(Class<?> sourceType, Class<?> targetType, Object source) {
        super("Converting from type [" + sourceType.getName() + "] value [" + source.toString() +"] to type [" + targetType.getName() + "]");
    }
}
