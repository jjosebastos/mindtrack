package br.com.fiap.mindtrack.config;


import br.com.fiap.mindtrack.service.CustomerDetailsService;
import br.com.fiap.mindtrack.service.OAuth2LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration {

    private final CustomerDetailsService customerDetailsService;
    private final OAuth2LoginService oAuth2LoginService;

    public SecurityConfiguration(CustomerDetailsService customerDetailsService,
                                 OAuth2LoginService oAuth2LoginService) {
        this.customerDetailsService = customerDetailsService;
        this.oAuth2LoginService = oAuth2LoginService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    public UserDetailsService userDetailsService() {
        // Usa o CustomerDetailsService para o formLogin
        return customerDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
        return new CustomOAuth2LoginSuccessHandler(oAuth2LoginService);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.
                authorizeHttpRequests( auth -> auth
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**", "/webjars/**", "/h2-console/**",
                                "/svg/**",
                                "/login", "/health-check", "/register", "/form", "/login?error=true", "/error"
                        ).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin( form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/index")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login( oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        return http.build();

    }
}
