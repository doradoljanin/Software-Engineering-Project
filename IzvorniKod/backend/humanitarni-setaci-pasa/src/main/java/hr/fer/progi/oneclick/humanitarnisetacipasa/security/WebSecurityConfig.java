package hr.fer.progi.oneclick.humanitarnisetacipasa.security;

import hr.fer.progi.oneclick.humanitarnisetacipasa.security.jwt.AuthEntryPointJwt;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.jwt.AuthTokenFilter;
import hr.fer.progi.oneclick.humanitarnisetacipasa.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile(value = {"local", "production", "prod"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/places").permitAll()
                .antMatchers("/api/associations").permitAll()
                .antMatchers("/api/image/association/*").permitAll()
                .antMatchers("/api/image/animal/*").permitAll()
                .antMatchers("/api/associations/{id}").permitAll()
                .antMatchers("/api/associations/search**").permitAll()
                .antMatchers("/api/walk/animals").permitAll()
                .antMatchers("/api/statistics/global").permitAll()
                .antMatchers("/api/statistics/rankings").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
