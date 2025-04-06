package com.gj.cloud.common.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.gj.cloud.common.exception.BusinessException;
import com.gj.cloud.common.spring.SpringContextHolder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JacksonUtils {
    private static Map<String, JsonFormat> JSONFORMAT_CONTAINER = new HashMap<>();

    private static ObjectMapper objectMapper = null;
    private static ObjectMapper reducedObjectMapper = null;

//    public static Jackson2ObjectMapperBuilder getEnhancedBuilder(Jackson2ObjectMapperBuilder builder) {
//        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//
//        builder.featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//        builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//        // 用于修正数字序列化时变成科学计数法的问题
//        builder.serializerByType(Double.class, new NumberSerializer());
//        builder.serializerByType(double.class, new NumberSerializer());
//        builder.serializerByType(Long.class, new NumberSerializer());
//        builder.serializerByType(long.class, new NumberSerializer());
//        builder.serializerByType(Integer.class, new NumberSerializer());
//        builder.serializerByType(int.class, new NumberSerializer());
//        builder.serializerByType(Date.class, new DateSerializer());
//        builder.deserializerByType(Date.class, new DateDeserializer());
//        builder.serializerByType(LocalDate.class, new LocalDateSerializer());
//        builder.deserializerByType(LocalDate.class, new LocalDateDeserializer());
//        builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
//        builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
//
//        return builder;
//    }

//    public static <T> String toReducedJson(T item) {
//        try {
//            return getReducedObjectMapper().writeValueAsString(item);
//        } catch (JsonProcessingException jpe) {
//            throw new BusinessException(jpe);
//        }
//    }

    public static <T> String toJson(T item) {
        try {
            return getObjectMapper().writeValueAsString(item);
        } catch (JsonProcessingException jpe) {
            throw new BusinessException(jpe);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return getObjectMapper().readValue(json, type);
        } catch (JsonProcessingException jpe) {
            throw new BusinessException(jpe);
        }
    }

    public static <T> List<T> listFromJson(String json, Class<T> type) {
        try {
            JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(ArrayList.class, type);

            return getObjectMapper().readValue(json, javaType);
        } catch (JsonProcessingException jpe) {
            throw new BusinessException(jpe);
        }
    }

    public static <T> T fromJson(String json, String fieldName, Class<T> type) {
        try {
            JsonNode node = getObjectMapper().readTree(json);

            JsonNode fieldNode = node.get(fieldName);

            if (fieldNode.isTextual()) {
                return fromJson(node.get(fieldName).asText(), type);
            }

            return fromJson(fieldNode.toString(), type);
        } catch (JsonProcessingException jpe) {
            throw new BusinessException(jpe);
        }
    }

    public static <T> List<T> listFromJson(String json, String fieldName, Class<T> type) {
        try {
            JsonNode node = getObjectMapper().readTree(json);

            return listFromJson(node.get(fieldName).toString(), type);
        } catch (JsonProcessingException jpe) {
            throw new BusinessException(jpe);
        }
    }

//    public static HttpMessageConverter<?> getHttpMessageConverter() {
//        return new MappingJackson2HttpMessageConverter(getObjectMapper());
//    }
    //-----------------------------------------------------------------
    // 私有方法
    //-----------------------------------------------------------------
    private static class DateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                JsonFormat jsonFormat = getJsonFormat(gen);

                if (jsonFormat == null
                        || jsonFormat.pattern() == null) {
                    gen.writeString(DateTimeUtils.formatDateTime(value));
                } else {
                    gen.writeString(new SimpleDateFormat(jsonFormat.pattern()).format(value));
                }
            }
        }
    }

    private static class DateDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return DateTimeUtils.parseDate(p.getText());
        }
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                JsonFormat jsonFormat = getJsonFormat(gen);

                if (jsonFormat == null
                        || jsonFormat.pattern() == null) {
                    gen.writeString(DateTimeUtils.formatLocalDateTime(value));
                } else {
                    gen.writeString(DateTimeUtils.formatLocalDateTime(value, DateTimeFormatter.ofPattern(jsonFormat.pattern())));
                }
            }
        }
    }

    private static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return DateTimeUtils.parseLocalDateTime(p.getText());
        }
    }

    private static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                JsonFormat jsonFormat = getJsonFormat(gen);

                if (jsonFormat == null
                        || jsonFormat.pattern() == null) {
                    gen.writeString(DateTimeUtils.formatLocalDate(value));
                } else {
                    gen.writeString(DateTimeUtils.formatLocalDate(value, DateTimeFormatter.ofPattern(jsonFormat.pattern())));
                }
            }
        }
    }

    private static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            return DateTimeUtils.parseLocalDate(p.getText());
        }
    }

    private static class NumberSerializer extends JsonSerializer<Number> {
        @Override
        public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                gen.writeNumber(NumberUtils.formatNumber(value));
            }
        }
    }

    private static class ReducedStringSerializer extends JsonSerializer<String> {
        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                if (value.length() > 128) {
                    gen.writeString(value.subSequence(0, 125) + "...(more)");
                } else {
                    gen.writeString(value);
                }
            }
        }
    }

    private static final ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized(JacksonUtils.class) {
                if (objectMapper == null) {
                    objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
                }
            }
        }

        return objectMapper;
    }

//    private static final ObjectMapper getReducedObjectMapper() {
//        if (reducedObjectMapper == null) {
//            synchronized(JacksonUtils.class) {
//                if (reducedObjectMapper == null) {
//                    Jackson2ObjectMapperBuilder builder = SpringContextHolder.getBean(Jackson2ObjectMapperBuilder.class);
//
//                    Jackson2ObjectMapperBuilder enhancedBuilder = getEnhancedBuilder(builder);
//                    enhancedBuilder.serializerByType(String.class, new ReducedStringSerializer());
//
//                    reducedObjectMapper = enhancedBuilder.createXmlMapper(false).build();;
//                }
//            }
//        }
//
//        return reducedObjectMapper;
//    }

    private static final JsonFormat getJsonFormat(JsonGenerator gen) {
        String cacheKey = gen.getCurrentValue().getClass() + ":" + gen.getOutputContext().getCurrentName();

        if (JSONFORMAT_CONTAINER.containsKey(cacheKey)) {
            return JSONFORMAT_CONTAINER.get(cacheKey);
        } else {
            Field field = ReflectionUtils.findField(gen.getCurrentValue().getClass(), gen.getOutputContext().getCurrentName());

            if (field == null) {
                JSONFORMAT_CONTAINER.put(cacheKey, null);

                return null;
            } else {
                JsonFormat jsonFormat = ReflectionUtils.findField(gen.getCurrentValue().getClass(), gen.getOutputContext().getCurrentName()).getAnnotation(JsonFormat.class);
                JSONFORMAT_CONTAINER.put(cacheKey, jsonFormat);

                return jsonFormat;
            }
        }
    }
}
