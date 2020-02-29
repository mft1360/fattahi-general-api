package com.fattahi.general;

import java.io.IOException;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableResourceServer
@EnableFeignClients
@EnableAsync
@EnableAspectJAutoProxy
@EnableSwagger2
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public abstract class Application extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${swagger.paths}")
	private String paths;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/soap/**", "/register/**", "/approve/**", "/forget/**", "/cdn/**", "/cus1/**",
						"/hystrix.stream", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/configuration/ui",
						"/swagger-resources", "/v2/api-docs", "/swagger-resources/configuration/ui",
						"/swagger-resources/configuration/security", "/css/**", "/js/**", "/images/jcaptcha",
						"/templates/doc/**", "/*/*/forgotPassword","/uaa/oauth/token/**")
				.permitAll().anyRequest().authenticated().and().headers().frameOptions().disable();

	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex(paths + "/.*/.*")).build().pathMapping("");
	}

	@Configuration
	public class WebMvcFormDispatcher extends WebMvcConfigurerAdapter {
		@Override
		public void addResourceHandlers(final ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
		}
	}

	@Bean
	public DozerBeanMapperFactoryBean dozer() {
		DozerBeanMapperFactoryBean dozer = new DozerBeanMapperFactoryBean();
		dozer.setMappingFiles(loadResources());
		return dozer;
	}

	public Resource[] loadResources() {
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
					.getResources("classpath*:dozer/**/*.dzr.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resources;
	}

	@Bean
	public TaskExecutor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("spring-async-");
		executor.initialize();
		return executor;
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
	}

}
