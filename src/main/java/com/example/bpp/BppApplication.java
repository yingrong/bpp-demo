package com.example.bpp;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.bpp.*")
@MapperScan("com.example.bpp.mapper")
public class BppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BppApplication.class, args);
	}

	@Bean
	public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlList) {
		return new DdlApplicationRunner(ddlList);
	}
}
