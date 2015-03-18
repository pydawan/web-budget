/*
 * Copyright (C) 2015 Arthur Gregorio, AG.Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.webbudget.infraestructure.config;

import br.com.webbudget.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura toda infra de seguranca do sistema atraves do spring security
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.1.0, 07/03/2015
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Registra o contexto global de seguranca
     * 
     * @param auth
     * @throws Exception 
     */
    @Autowired
    public void registerGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

    /**
     * Configura as url's que nao devem ser interceptadas
     *
     * @param web 
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/favicon.ico*")
                .antMatchers("/javax.faces.resource/**");
    }

    /**
     * Configura as url's que devem ser inteceptadas pelo filtro de seguranca e 
     * tambem define a url de logout e login
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
                .disable()
            .formLogin()
                .loginPage("/home.xhtml")
                .loginProcessingUrl("/loginSpring")
                .failureUrl("/home.xhtml?failure=true")
                .defaultSuccessUrl("/main/dashboard.xhtml?faces-redirect=true")
                .permitAll()
            .and()
            .authorizeRequests()
                .antMatchers("/main/entries/cards/**").hasRole("")
                .antMatchers("/main/entries/contacts/**").hasRole("")
                .antMatchers("/main/entries/costCenter/**").hasRole("")
                .antMatchers("/main/entries/movementClass/**").hasRole("")
                .antMatchers("/main/entries/wallets/**").hasRole("")
                .antMatchers("/main/financial/cardInvoice/**").hasRole("")
                .antMatchers("/main/financial/movement/**").hasRole("")
                .antMatchers("/main/financial/transfer/**").hasRole("")
                .antMatchers("/main/miscellany/closing/**").hasRole("")
                .antMatchers("/main/miscellany/financialPeriod/**").hasRole("")
                .antMatchers("/main/tools/privateMessage/**").hasRole("")
                .antMatchers("/main/tools/user/**").hasRole("")
            .anyRequest()
                .authenticated()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .logoutUrl("/main/logout")
                .logoutSuccessUrl("/home.xhtml?logout=true")
                .permitAll();
    }

    /**
     * Disponibiliza a AuthenticationManager para injecao em outras classes
     * 
     * @see AccountService#login(br.com.webbudget.domain.entity.users.User) 
     * 
     * @return
     * @throws Exception 
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
