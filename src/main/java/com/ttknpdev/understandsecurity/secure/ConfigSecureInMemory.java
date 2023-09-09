package com.ttknpdev.understandsecurity.secure;

import com.ttknpdev.understandsecurity.log.MyLogging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ConfigSecureInMemory {
    /* These Beans work for configuration security */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        // Way to define username / password in memory for authenticate (First) */
        /*
        UserDetails normal = User.builder()
                .username("normal")
                .password(passwordEncoder().encode("12345"))
                .roles("NORMAL")
                .build();
        UserDetails admin  = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN","NORMAL")
                .build();
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(normal);
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
        */
        // Way to define username / password in memory for authenticate (Second) */
        User.UserBuilder userBuilder = User.builder(); // withDefaultPasswordEncoder() Deprecated. use instead builder() it's worked
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(userBuilder
                .username("normal")
                .password(passwordEncoder().encode("12345"))
                .roles("NORMAL")
                .build());
        manager.createUser(userBuilder
                .username("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN")
                .build());
        return manager;
    }
    /*
    ---- Multiple HttpSecurity Instances
    */
    @Bean @Order(1)
    /*
    The configuration creates a Servlet Filter known as the springSecurityFilterChain
    which is responsible for all the security (protecting the application URLs,
    validating submitted username and passwords, redirecting to the log in form, and so on)
    ## Basic Authentication DO NOT use cookies
    */
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity)  throws Exception {
        MyLogging.configSecureInMemory.info("users authenticate with HTTP Basic authentication");
        // # (First)
        /*
            httpSecurity.authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(HttpMethod.GET,"/api/test").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,"/api/test/login").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults()); /* this line means allowing login passed Basic auth
         */
        // # (Second)
        httpSecurity.securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/read/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/reads").hasRole("ADMIN")
                        .anyRequest().authenticated() // no need
                        // because login passed Basic auth (it's login passed Postman)
                        // พูดง่าย ๆ ถ้า login ผ่าน Basic auth
                        // มันจะ Return only type text
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity httpSecurity) throws Exception {
        MyLogging.configSecureInMemory.info("users authenticate with form based login");
        httpSecurity.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/read/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/reads").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                /* setting form login on my own*/
                /*
                First way
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/reads",true) // after login it'll go to that path
                .permitAll();
                */
                /*
                Second way
                */
                .formLogin( form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/reads",true)
                        .permitAll()
                );
        return httpSecurity.build();
         /*
            Lets users authenticate with form based login
        */
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
