package com.scopic.antiquction.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfiguration {

    public AppConfiguration() {
        super();
    }

    @Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	  return new BCryptPasswordEncoder();
	}
}
