package jeonghyeon.msa.board.aspect;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.board.context.UserContext;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.service.UserService;
import jeonghyeon.msa.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Aspect
@Component
public class AuthAspect {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Before("@annotation(jeonghyeon.msa.board.annotation.AuthUsers)")
    public void beforeAuth(JoinPoint joinPoint){

        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            String username = jwtUtil.getUsername(token);
            Users users = userService.findByUsername(username);
            UserContext.setCurrentUser(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @AfterThrowing("@annotation(jeonghyeon.msa.board.annotation.AuthUsers)")
//    public void afterThrowing(JoinPoint joinPoint, ExpiredJwtException exception){
//        response...
//    }

    @After("@annotation(jeonghyeon.msa.board.annotation.AuthUsers)")
    public void afterAuth(JoinPoint joinPoint){
        UserContext.clear();
    }


}
