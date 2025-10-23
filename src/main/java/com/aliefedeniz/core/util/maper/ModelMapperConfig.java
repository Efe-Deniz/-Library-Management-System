package com.aliefedeniz.core.util.maper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Null değerleri atla (güncellemelerde null gelirse mevcut değerleri korur)
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }
}
