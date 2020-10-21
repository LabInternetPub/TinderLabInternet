package cat.tecnocampus.tinder.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/profiles/me/**", "/quotes/**").hasRole("USER")
                .antMatchers("/profiles/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()

                .and()
                .httpBasic()

                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        System.out.printf("Password: %s%n", passwordEncoder.encode("password123"));
        UserDetails josep = User.builder()
                .username("josep")
                .password(passwordEncoder.encode("password123"))
                .roles("USER") // ROLE_USER
                .build();

        UserDetails jordi = User.builder()
                .username("jordi")
                .password(passwordEncoder.encode("password123"))
                .roles("ADMIN") // ROLE_ADMIN
                .build();

        UserDetails tom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
                .roles("USER", "ADMIN") // ROLE_ADMIN
                .build();

        return new InMemoryUserDetailsManager(josep, jordi, tom);

    }
}
