package com.example.patientinfo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 어플리케이션의 보안 설정
 * 예제 13.19
 *
 * @author Flature
 * @version 1.0.0
 */
@Configuration
//@EnableWebSecurity // Spring Security에 대한 디버깅 모드를 사용하기 위한 어노테이션 (default : false)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable() // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화

                .csrf().disable() // REST API는 csrf 보안이 필요 없으므로 비활성화

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화

                .and()
                .authorizeRequests() // 리퀘스트에 대한 사용권한 체크
                .antMatchers("/V3/login/**").permitAll() // 가입 및 로그인 주소는 허용
                .antMatchers(HttpMethod.GET, "/V2/API/**").permitAll()
                .antMatchers(HttpMethod.GET, "/V1/API/**").permitAll()
//                .antMatchers("/V1/API/**").permitAll()
                .antMatchers("/V1/API/**").hasRole("Patient")
                .antMatchers("/V2/API/**").hasRole("Doctor")


                .anyRequest().authenticated()

//                .antMatchers(HttpMethod.GET, "/V1/API/**").permitAll() // product로 시작하는 Get 요청은 허용

//                .antMatchers("**exception**").permitAll()

//                .anyRequest().hasRole("ADMIN") // 나머지 요청은 인증된 ADMIN만 접근 가능

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

//                // 에러 핸들링
//                .exceptionHandling()
//                .accessDeniedHandler(new CustomAccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)throws IOException {
//                        // 권한 문제가 발생했을 때 이 부분을 호출한다.
//                        response.setStatus(403);
//                        response.setCharacterEncoding("utf-8");
//                        response.setContentType("text/html; charset=UTF-8");
//                        response.getWriter().write("권한이 없는 사용자입니다.");
//                    }
//                })
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
//                        // 인증문제가 발생했을 때 이 부분을 호출한다.
//                        response.setStatus(401);
//                        response.setCharacterEncoding("utf-8");
//                        response.setContentType("text/html; charset=UTF-8");
//                        response.getWriter().write("인증되지 않은 사용자입니다.");
//                    }
//                });
    }

    /**
     * Swagger 페이지 접근에 대한 예외 처리
     *
     * @param webSecurity
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/V3/login/exception");
    }
}