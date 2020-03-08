package neo.config;

import neo.domain.User;
import neo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/*.js", "/**/*.css", "/**/*.jpg", "/**/*.ico", "/register/**").permitAll()
                .antMatchers("/h2-console/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and().headers().frameOptions().sameOrigin()
                .and().formLogin().defaultSuccessUrl("/", true).loginPage("/login").permitAll()
                .and().logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            User user = userRepository.findByUsername(username);
            if (user == null || user.getId() == null) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }

            return org.springframework.security.core.userdetails.User.withUsername(username)
                    .password(user.getPassword()).roles(user.getRole()).build();
        }).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
