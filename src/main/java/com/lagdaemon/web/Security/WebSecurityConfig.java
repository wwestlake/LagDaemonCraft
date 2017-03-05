package com.lagdaemon.web.Security;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lagdaemon.service.SecurityService;
import com.lagdaemon.service.SecurityServiceImpl;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
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
    		
    		.usersByUsernameQuery("select email as username, password_hash as password, email_validated as active from user where email=?")
    		.authoritiesByUsernameQuery("SELECT email, role FROM lagdaemon.user join users_roles on users_roles.user_id = user.user_id join role on users_roles.role_id = role.role_id where user.email = ?")
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