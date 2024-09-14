package com.onlinevote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinevote.entity.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlinevoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinevoteApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	public ResponseMessage responseMessage(){
		return new ResponseMessage<>();
	}

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}

}
