package com.example.bpp;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import jakarta.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

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
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.parse("10MB")); // 设置最大文件上传大小为10MB
		factory.setMaxRequestSize(DataSize.parse("10MB")); // 设置请求中最大允许的文件大小为10MB
		return factory.createMultipartConfig();
	}
}
