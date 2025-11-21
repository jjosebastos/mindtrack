package br.com.fiap.mindtrack.config;

import br.com.fiap.mindtrack.auth.CustomerDetailsService;
import br.com.fiap.mindtrack.auth.OAuth2LoginService;
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

    // REMOVIDO O BEAN 'clientRegistrationRepository' - O Spring agora lerá do application.properties

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/css/**","/js/**","/images/**","/webjars/**",
                                "/h2-console/**","/svg/**",
                                "/login", "/login/**",
                                "/register/**",
                                "/oauth2/**",
                                "/login/oauth2/code/**"
                                ,"/health-check",
                                "/form","/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/perform_login")
                        .defaultSuccessUrl("/index", true)
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(oAuth2LoginSuccessHandler())
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