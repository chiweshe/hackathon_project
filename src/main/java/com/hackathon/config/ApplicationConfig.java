package com.hackathon.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configure the model mapper to be more lenient
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setImplicitMappingEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.LOOSE);

        // Add a custom converter for String to Long
        Converter<String, Long> stringToLongConverter = new Converter<String, Long>() {
            @Override
            public Long convert(MappingContext<String, Long> context) {
                if (context.getSource() == null || context.getSource().isEmpty()) {
                    return null;
                }
                try {
                    return Long.parseLong(context.getSource());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        };
        modelMapper.addConverter(stringToLongConverter);

        return modelMapper;
    }
}
