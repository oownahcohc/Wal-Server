package server.wal.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity // 웹 보안 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 인터셉터로 요청을 안전하게 보호하는 방법을 설정
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().permitAll() // 토큰을 활용하는 경우 모든 요청에 대해 접근 가능하도록 설정
                .and()
                .httpBasic().and()
                .formLogin().disable() // form 기반 로그인 비활성화
                .logout().disable()
                .csrf().disable()
                // session 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .frameOptions().sameOrigin()
                .cacheControl().and()
                .xssProtection().and()
                .httpStrictTransportSecurity().disable();
    }

}
