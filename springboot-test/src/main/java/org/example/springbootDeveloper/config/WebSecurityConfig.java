package org.example.springbootDeveloper.config;

import lombok.RequiredArgsConstructor;
import org.example.springbootDeveloper.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 해당 클래스가 설정 클래스로 사용됨을 명시
@EnableWebSecurity // Spring Security의 웹 보안을 활성화
@RequiredArgsConstructor // final 필드 | @NonNull 필드에 대해 생성자를 자동 생성
public class WebSecurityConfig {
    @Lazy
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

//    @Bean
//    // 정적 리소스나 특정 URL에 대해 Spring Security가 보안 검사를 무시하도록 설정
//    // : 기능 비활성화
//    public WebSecurityCustomizer configure() {
//        return (web) -> web.ignoring()
//                // H2 콘솔에 대한 접근 보안 검사를 무시하도록 설정
////                .requestMatchers(toH2Console())
//                // "/static/**" 경로의 정적 리소스를 보안 검사에서 제외
//                .requestMatchers(new AntPathRequestMatcher("/static/**"));
//    }


    // CORS 정책
    // : Cross Origin Resource Sharing
    // >> 브라우저에서 다른 도메인(서버)으로부터 리소스를 요청할 때 발생하는 보안 정책
    // >> REST API를 사용할 때 다른 출처(도메인)에서 API에 접근할 수 있도록 허용하는 정책

    // CorsFilter
    // : 특정 출처에서 온 HTTP 요청을 허용하거나 거부할 수 있는 필터
    // : CORS 관련 설정을 필터링 해주는 역할
    @Bean   //  해당 메서드에서 생성한 객체는 Spring에 의해 관리되는 Bean으로 등록
    public CorsFilter corsFilter() {
        // 1. UrlBasedCorsConfigurationSource
        // : CORS 정책을 URL 기반으로 "관리하는 객체"
        // - 특정 경로에 따라 CORS 정책을 달리 적용 가능
        // - source를 통해 정책을 사용할 경로 지정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS 관련 "세부 설정을 담는 클래스"
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 쿠키를 허용할지 여부 - 자격 증명을 포함한 요청 허용 여부
        config.addAllowedOriginPattern("*"); // 모든 도메인(출처) 허용 - 어디서든지 요청 가능
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)

        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 CORS 적용

        // CORS 정책을 적용하는 필터를 반환
        return new CorsFilter(source);
    }

    /*
        filterChain 메서드
        : 보안 필터 체인 정의, 특정 HTTP 요청에 대한 웹 기반 보안 구성
        - CSRF 보호를 비활성화, CORS 정책을 활성화

        cf) CSRF(Cross-Site Request Forgery) 공격 : 사용자 대신 웹 애플리케이션에서 악의적인 행동을 하는 공격
            CORS(Corss-Origin Resource Sharing) 정책 : 서로 다른 서버 간의 리소스 상호작용을 위한 정책

        - JWT 필터를 추가하여 인증 요청을 처리
        - 특정 경로에 대한 요청은 인증 없이 접근을 허용, 그 외의 요청은 인증이 필요
        @param : http HttpSecurity - 객체를 통해 보안 설정을 관리
        @return : SecurityFilterChain - 보안 피터 체인을 구성한 결과를 반환
        @throws ; Exception - 설정 중 예외 발생 가능
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF 보호 비활성화 (REST API에서는 보통 비활성화)
                .csrf(AbstractHttpConfigurer::disable) // 비활성화
                .cors(withDefaults()) // CORS 허용
                .authorizeHttpRequests(auth -> auth // 인증, 인가 설정
                        //  요청 인증 및 권한 부여 설정
                        .requestMatchers(
                                // 특정 경로에 대한 엑세스 설정
                                // .requestMatchers()
                                //  : 특정 요청과 일치하는 url에 대한 엑세스
//                                new AntPathRequestMatcher("/api/v1/auth/**"),
//                                new AntPathRequestMatcher("/api/v1/menus/**"),
                                new AntPathRequestMatcher("/api/test/books/**")
                        )
                        // .permitAll()
                        //  : 누구나 접근이 가능하게 설정
                        //  : /login, /singup, /user 요청이 오면 인증, 인가 없이도 접근 가능
                        .permitAll()
                        // .anyRequest()
                        //  : 위에서 설정한 url 이외의 요청에 대해 설정
                        // .authenticated()
                        //  : 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
                        .anyRequest().authenticated())
                // cf) csrf(Cross-Site Request Forgery)
                //      : 사이트 간 요청 위조의 줄임말

                // csrf 공격을 방지하기 위해 활성화 하는 것을 권장
                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // 보안 필터 체인 반환
                .build();
    }


    /*
        AuthenticationManager : Spring Security에서 인증(Authentication)을 담당하는 핵심 인터페이스
        - 인증 과정에서 사용자 자격 증명(EX. username, password)을 확인하고 올바르면 인증 토큰을 반환

        DaoAuthenticationProvider를 사용해 데이터베이스에서 사용자 인증을 처리
            , BCryptPasswordEncoder를 사용하여 비밀번호를 암호화하여 검증

     */
    @Bean
    // 인증 관리자 관련 설정
    public AuthenticationManager authenticationManager(BCryptPasswordEncoder bCryptpasswordEncoder) throws Exception {
        // DaoAuthenticationProvider
        // : DB에서 사용자 인증을 처리
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // 비밀번호 검증을 위한 bCryptpasswordEncoder 사용
        authProvider.setPasswordEncoder(bCryptpasswordEncoder);

        // ProviderManager를 반환 :  DaoAuthenticationProvider 인증 처리
        return new ProviderManager(List.of(authProvider));
    }

    /*
        BCryptPasswordEncoder : 비밀번호 암호화에 사용되는 클래스
        : 비밀번호 암호화
        : Spring 컨텍스트에 Bean으로 등록하여 사용
        : 단방향 해시함수를 사용하여 비밀번호를 암호화 함
        >> 복호화할 수 없음!
     */
    @Bean
    public BCryptPasswordEncoder bCryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
