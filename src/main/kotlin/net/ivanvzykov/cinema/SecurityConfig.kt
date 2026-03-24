package net.ivanvzykov.cinema

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun createPasswordEncoder(): PasswordEncoder = BCryptPasswordEncoder(7)

    @Bean
    fun createUserDetailsService(@Autowired passwordEncoder: PasswordEncoder): UserDetailsService {
        val user = User.withUsername("user1")
            .password(passwordEncoder.encode("super_secret"))
            .build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun createSecurityFilterChain(@Autowired http: HttpSecurity): SecurityFilterChain = http
        .authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers(HttpMethod.GET, "/stats").authenticated()
                .requestMatchers("/*").permitAll()
                .anyRequest().denyAll()
        }
        .httpBasic(Customizer.withDefaults())
        .csrf { it.disable() }
        .build()
}
