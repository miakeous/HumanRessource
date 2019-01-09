package com.spring.td1.webMvc;

import com.spring.td1.Service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmployeesService employeesService;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
               /* jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)
                .passwordEncoder(bCryptPasswordEncoder)*/

                //.authoritiesByUsernameQuery(rolesQuery)

               ;
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeesService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                //Permission pour tous
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .and()
                //Permission restreinte
                .authorizeRequests()
                .antMatchers("/home").hasRole("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/allEmployees").hasAuthority("USER")
                .antMatchers("/countries").hasAnyRole("ADMIN","SALES")
                .antMatchers("/locations").hasAnyRole("ADMIN","SALES")
                .antMatchers("/regions").hasAnyRole("ADMIN","SALES")
                .antMatchers("/employeesUpdate").hasAnyRole("ADMIN","SALES")
                .antMatchers("/countriesUpdate").hasRole("ADMIN")
                .antMatchers("/regionsUpdate").hasRole("ADMIN")
                .antMatchers("/update/**").hasRole("ADMIN")
                .antMatchers("/employeesGraph").hasRole("ADMIN")
                .antMatchers("/employeesDecile").hasRole("ADMIN")
                .anyRequest()
                //Authentication
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home",true)
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied")
        ;
    }
}
