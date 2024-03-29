package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import interceptor.AutoLoginInterceptor;
import interceptor.ChatNotReadCheckInterceptor;
import interceptor.LoggerInterceptor;
import interceptor.LoginCheckInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"com.hb.*"})
public class MvcConfig implements WebMvcConfigurer {
	

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();		
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		registry.enableContentNegotiation(jsonView);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggerInterceptor())
			.addPathPatterns("/**/*.do");
		registry.addInterceptor(autoLoginInterceptor())
			.addPathPatterns("/freeboard/**.do","/faq/**.do")
			.excludePathPatterns("/freeboard/replyEdit.do","/faq/faqWrite.do","/freeboard/delete.do","/freeboard/replyDelete.do","/freeboard/write.do");
		registry.addInterceptor(loginCheckInterceptor())
			.addPathPatterns("/freeboard/**.do","/chat/**.do","/chatting/**.do","/faq/**.do")
			.excludePathPatterns("/freeboard/freeboard.do","/freeboard/detail.do","/faq/faq.do");
		registry.addInterceptor(chatNotReadCheckInterceptor())
			.addPathPatterns("/chat/**.do","/chatting/**.do")
			.excludePathPatterns("/chatting/chatReading.do", "/chatting/chatUserSearch.do");
		
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/freeboard/writePre.do").setViewName("/freeboard/freeboardWrite");
		registry.addViewController("/login/joinPre.do").setViewName("/account/join");
	}

	@Bean(name = "multipartResolver") 
	public CommonsMultipartResolver createMultipartResolver() { 
		CommonsMultipartResolver resolver=new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		resolver.setMaxInMemorySize(1048576);	// 1MB
		resolver.setMaxUploadSize(20971520);	// 20MB
		return resolver;
	}
	
	@Bean
	public AutoLoginInterceptor autoLoginInterceptor() {
		return new AutoLoginInterceptor();
	}
	
	@Bean
	public LoginCheckInterceptor loginCheckInterceptor() {
		return new LoginCheckInterceptor();
	}
	
	@Bean
	public LoggerInterceptor loggerInterceptor() {
		return new LoggerInterceptor();
	}	
	
	@Bean
	public ChatNotReadCheckInterceptor chatNotReadCheckInterceptor() {
		return new ChatNotReadCheckInterceptor();
	}
}
