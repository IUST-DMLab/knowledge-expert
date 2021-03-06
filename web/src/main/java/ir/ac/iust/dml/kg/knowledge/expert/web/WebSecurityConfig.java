package ir.ac.iust.dml.kg.knowledge.expert.web;

import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.BasicAuthDefinition;
import io.swagger.models.auth.In;
import ir.ac.iust.dml.kg.knowledge.expert.web.security.SmartHttpSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.data.mongo.JdkMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 *
 * Configuration for spring security
 */
@Configuration
@EnableWebSecurity
@EnableMongoHttpSession
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/services/rs/v1/experts/login").permitAll()
                .antMatchers("/services/rs/v1/users/login").permitAll()
                .antMatchers("/services/rs/v1/users/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/services/rs/v1/reports/login").permitAll()
                .antMatchers("/services/rs/v1/reports/**").hasAnyAuthority("ROLE_REPORT")
                .antMatchers("/services/rs/v1/**").authenticated()
                .antMatchers("/services/rs/hello/**").authenticated()
                .antMatchers("/services/**").permitAll()
                .anyRequest().authenticated()
                .and().requestCache().requestCache(new NullRequestCache())
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().httpBasic()
                .and().cors()
                .and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {
        return new JdkMongoSessionConverter();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new SmartHttpSessionStrategy();
    }

    @Bean
    @DependsOn("jaxRsServer")
    public ServletContextInitializer initializer() {
        return servletContext -> {
            final BeanConfig scanner = (BeanConfig) ScannerFactory.getScanner();
            final Swagger swagger = scanner.getSwagger();
            swagger.securityDefinition("basic", new BasicAuthDefinition());
            swagger.securityDefinition("session", new ApiKeyAuthDefinition("x-auth-token", In.HEADER));
            servletContext.setAttribute("swagger", swagger);
        };
    }
}
