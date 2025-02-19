package jeonghyeon.msa.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static jeonghyeon.msa.security.JwtTokenUtil.BEARER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String DELIMITER = " ";
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
        }
        String token = authorization.split(DELIMITER)[1];

        if (jwtTokenUtil.isExpired(token)) {
            logger.info("토큰 만료");
            filterChain.doFilter(request, response);
        }

        Long usersId = jwtTokenUtil.getUsersId(token);
        String username = jwtTokenUtil.getUsername(token);
        String role = jwtTokenUtil.getRole(token);

        Users login = Users.login(usersId, username, role);
        PrincipalUserDetails userDetails = new PrincipalUserDetails(login);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }


}
