package kr.co.itcen.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:kr/co/itcen/mysite/config/web/properties/multipart.properties")
public class FileuploadConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(env.getProperty("maxUploadSize",Integer.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("maxInMemorySize",Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("defaultEncoding"));
		
		return multipartResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("resourceMapping")+"/**").addResourceLocations("file:"+env.getProperty("uploadsLocation"));
	}
	
	// fileupload service 에 env autowired 해서 위와같이 path 설정
	
	
	
/*	<!-- 멀티파트 리졸버 -->
<bean id="multipartResolver" 
	class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	<!-- 최대업로드 가능한 바이트크기 -->
	<property name="maxUploadSize" value="52428800" />
	<!-- 디스크에 임시 파일을 생성하기 전에 메모리에 보관할수있는 최대 바이트 크기 -->
	<!-- property name="maxInMemorySize" value="52428800" /-->
	<!-- defaultEncoding -->
	<property name="defaultEncoding" value="utf-8" />
</bean>	*/
}
