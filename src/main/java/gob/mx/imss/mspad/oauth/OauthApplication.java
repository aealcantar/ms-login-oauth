package gob.mx.imss.mspad.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date   26 abr. 2022
 * @IMSS
 */
@SpringBootApplication
@ComponentScan(basePackages = "gob.mx.imss")
public class OauthApplication {

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
            }
        };
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
	
//	@Bean
//    public ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//        registration.addUrlMappings("/console/*");
//        return registration;
//    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    CharacterEncodingFilter characterEncodingFilter() {
      CharacterEncodingFilter filter = new CharacterEncodingFilter();
      filter.setEncoding("UTF-8");
      filter.setForceEncoding(true);
      return filter;
    }

}
