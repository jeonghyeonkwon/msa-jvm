package jeonghyeon.msa.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.auth.dto.request.LoginDto;
import jeonghyeon.msa.auth.dto.response.ResponseDto;
import jeonghyeon.msa.auth.exception.ErrorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String ACCESS_TOKEN = "Access-Token";
    public static final String REFRESH_TOKEN = "refresh-token";
    public static final Long ACCESS_EXPIRE = 60000L;
    public static final Long REFRESH_EXPIRE = 1000 * 60 * 60L;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisRepository redisRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        try {
            LoginDto loginDto = om.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword(), null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 1차 jwt
        PrincipalUserDetails userDetails = (PrincipalUserDetails) authResult.getPrincipal();
//        Long userId = userDetails.getUsersId();
//        String username = userDetails.getUsername();
//        GrantedAuthority auth = authResult
//                .getAuthorities()
//                .iterator()
//                .next();
//
//        String role = auth.getAuthority();
//        String token = jwtTokenUtil.createJwt(userId, username, role, 60 * 60 * 10L);
//
//        response.addHeader(AUTHORIZATION, BEARER + token);

        // 2차 access + refresh

        String username = userDetails.getUsername();
        GrantedAuthority auth = authResult
                .getAuthorities()
                .iterator()
                .next();

        String role = auth.getAuthority();
        String access = jwtTokenUtil.createJwtWithAccessAndRefresh(ACCESS_TOKEN,  username, role, ACCESS_EXPIRE);
        String refresh = jwtTokenUtil.createJwtWithAccessAndRefresh(REFRESH_TOKEN,  username, role, REFRESH_EXPIRE);

        response.setHeader(ACCESS_TOKEN, access);
        response.addCookie(createCookie(REFRESH_TOKEN, refresh));
        redisRepository.save(refresh, 24L);
        response.setStatus(HttpStatus.OK.value());

        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();
        response.getWriter().write(om.writeValueAsString(new ResponseDto<>(username)));

    }

    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.error(failed);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();

        if (failed instanceof BadCredentialsException) {
            String result = om.writeValueAsString(new ErrorResult("비밀번호가 맞지 않음"));
            response.getWriter().write(result);
        }
    }
}
