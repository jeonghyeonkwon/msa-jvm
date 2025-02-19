package jeonghyeon.msa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.domain.Users;
import jeonghyeon.msa.dto.response.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jeonghyeon.msa.security.JwtAuthenticationFilter.ACCESS_TOKEN;
import static jeonghyeon.msa.security.JwtTokenUtil.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String DELIMITER = " ";
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1차
//        String authorization = request.getHeader(AUTHORIZATION);
//        if (authorization == null || !authorization.startsWith(BEARER)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorization.split(DELIMITER)[1];
//
//        if (jwtTokenUtil.isExpired(token)) {
//            logger.info("토큰 만료");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        Long usersId = jwtTokenUtil.getUsersId(token);
//        String username = jwtTokenUtil.getUsername(token);
//        String role = jwtTokenUtil.getRole(token);
//
//        Users login = Users.login(usersId, username, role);
//        PrincipalUserDetails userDetails = new PrincipalUserDetails(login);
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        filterChain.doFilter(request, response);

        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtTokenUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            ObjectMapper om = new ObjectMapper();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(om.writeValueAsString(new ErrorMessage("토큰 만료되었습니다.")));
        }

        String category = jwtTokenUtil.getCategory(accessToken);
        if (!category.equals(ACCESS_TOKEN)) {
            ObjectMapper om = new ObjectMapper();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(om.writeValueAsString(new ErrorMessage("유효하지 않은 토큰입니다.")));
        }

        Long usersId = jwtTokenUtil.getUsersId(accessToken);
        String username = jwtTokenUtil.getUsername(accessToken);
        String role = jwtTokenUtil.getRole(accessToken);
        PrincipalUserDetails userDetails = new PrincipalUserDetails(Users.login(usersId, username, role));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);

    }


}
