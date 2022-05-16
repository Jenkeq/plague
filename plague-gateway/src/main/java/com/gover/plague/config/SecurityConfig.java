//package com.gover.plague.config;
//
////import com.gover.plague.config.auth.CustomUserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Autowired
//    TokenFilter tokenFilter;
//
//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//                .formLogin()
//                .and()
//                //.authorizeExchange().anyExchange().authenticated()
//                .csrf().disable()
//                .httpBasic(withDefaults());
//        return http.build();
//    }
//
//    /**
//     * 使用 BCryptPasswordEncoder
//     * @return
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * Token 过滤器
//     * @return
//     */
//    @Bean
//    public TokenFilter tokenFilter(){
//        return new TokenFilter();
//    }
//}
