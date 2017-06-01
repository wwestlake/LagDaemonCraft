package com.lagdaemon.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import com.lagdaemon.service.CustomeAuthenticationSuccessHandler;
import com.lagdaemon.service.SecurityService;
import com.lagdaemon.service.SecurityServiceImpl;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler())
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and().requestCache()
            .and().csrf().disable();
     
    }

    @Bean
    CustomeAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
    	return new CustomeAuthenticationSuccessHandler();
    }
    
    @Value("${spring.datasource.url}")
    String dsUrl;

    @Value("${spring.datasource.username}")
    String userName;

    @Value("${spring.datasource.password}")
    String password;
    
    @Value("${spring.datasource.driver-class-name}")
    String driver;
    
    
    
    @Bean
    public DataSource dataSource(AuthenticationManagerBuilder auth) throws Exception {
    	log.info("Setting up the security data source");
    	DataSource ds = new DataSource(); 
    	ds.setDriverClassName(driver);
    	ds.setUrl(dsUrl);
    	ds.setUsername(userName);
    	ds.setPassword(password);
    	
    	auth.jdbcAuthentication().dataSource(ds)
    		.usersByUsernameQuery("{CALL usersByUsernameQuery(?)}")
    		.authoritiesByUsernameQuery("{CALL authoritiesByUsernameQuery(?)}")
    		.passwordEncoder(passwordEncoder());
    	
    	return ds;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean 
    SecurityService securityService() {
    	return new SecurityServiceImpl();
    }

    
    
}