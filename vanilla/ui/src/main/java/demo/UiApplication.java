package demo;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class UiApplication {

    @GetMapping(value = "/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
    	if (user == null) {
    		user = SecurityContextHolder.getContext().getAuthentication();
    	}
        return user;
    }
    
    @RequestMapping("/error401")
    public ResponseEntity<?> error401() {    	
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }

    @Configuration
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    	
    	UsernamePasswordAuthenticationFilter o;
    	
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .formLogin()
                	.loginPage("/")
                	.loginProcessingUrl("/login")
                	.successForwardUrl("/user")
                	.failureUrl("/error401")
                	.and()
                .logout()
                	.logoutSuccessUrl("/")
                	.and()
                .authorizeRequests()
                    .antMatchers("/login", "/", "/logout", "/error*").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }
        
        @Override
        public void configure(WebSecurity web) {
        	web.ignoring().antMatchers("/*.bundle.*", "/*.js", "/*.map", "/*glyphicons*", "/*.ico");
        }
    }

}
