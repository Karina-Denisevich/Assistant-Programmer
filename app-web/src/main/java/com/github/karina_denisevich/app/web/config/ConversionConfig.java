package com.github.karina_denisevich.app.web.config;


import com.github.karina_denisevich.app.web.converter.dto_to_entity.DtoToUser;
import com.github.karina_denisevich.app.web.converter.dto_to_entity.DtoToUserAdmin;
import com.github.karina_denisevich.app.web.converter.entity_to_dto.UserToDto;
import com.github.karina_denisevich.app.web.converter.entity_to_dto.UserToDtoAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@EnableSpringConfigured
public class ConversionConfig{

    @Bean(name = "conversionService")
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        final ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();

        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean;
    }

    private Set<Converter> getConverters() {
        final Set<Converter> converters = new HashSet<>();

        converters.add(new UserToDto());
        converters.add(new DtoToUser());

        converters.add(new UserToDtoAdmin());
        converters.add(new DtoToUserAdmin());

        return converters;
    }
}
