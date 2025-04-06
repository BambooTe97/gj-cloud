package com.gj.cloud.common.spring;

import com.gj.cloud.common.exception.BusinessException;
import com.gj.cloud.common.util.ClassUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SpringContextHolder {
    private static final Map<Class<?>, Object> cache = new HashMap<>(); // 加速获取Bean的速度

    public static ApplicationContext applicationContext;

    public static Environment environment;

    public SpringContextHolder(ApplicationContext applicationContext, Environment environment) {
        SpringContextHolder.applicationContext = applicationContext;
        SpringContextHolder.environment = environment;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanIfPresent(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return (T) applicationContext.getBean(beanName);
        } else {
            return null;
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        @SuppressWarnings("unchecked")
        T bean = (T) cache.get(requiredType);
        if (bean == null) {
            synchronized(cache) {
                bean = applicationContext.getBean(requiredType);

                cache.put(requiredType, bean);
            }
        }
        return bean;
    }

    public static <T> T getBeanIfPresent(Class<T> requiredType) {
        ObjectProvider<T> provider = applicationContext.getBeanProvider(requiredType);
        return provider.getIfAvailable();
    }

    public static final boolean isProfileActivated(String profile) {
        return environment.acceptsProfiles(Profiles.of(profile));
    }

    public static final String getProperty(String key) {
        return environment.getProperty(key, String.class);
    }

    public static final <T> T getProperty(String key, Class<T> type) {
        return environment.getProperty(key, type);
    }

    public static final <T> T getProperty(String key, Class<T> type, T defaultValue) {
        return environment.getProperty(key, type, defaultValue);
    }

    @SuppressWarnings("unchecked")
    public static <T> T register(String name, Class<T> beanType, Object ... args) {
        ApplicationContext applicationContext = SpringContextHolder.applicationContext;

        if (applicationContext.containsBean(name)) {
            Object bean = applicationContext.getBean(name);

            if (!beanType.isAssignableFrom(ClassUtils.getRawType(applicationContext.getBean(name).getClass()))) {
                throw new BusinessException("Unable register bean, a bean with name (" + name + ") exists, but not the type of (" + beanType + ").");
            }
            return (T) bean;
        }

        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanType);
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        beanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);

        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);

        return applicationContext.getBean(name, beanType);
    }
}
