package jeonghyeon.msa.board.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.board.context.UserContext;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.service.UserService;
import jeonghyeon.msa.board.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            String username = jwtUtil.getUsername(token);
            Users users = userService.findByUsername(username);
            UserContext.setCurrentUser(users);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();;
    }
}
