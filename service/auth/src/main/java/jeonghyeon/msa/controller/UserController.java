package jeonghyeon.msa.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.dto.request.RegisterDto;
import jeonghyeon.msa.dto.response.ErrorMessage;
import jeonghyeon.msa.security.JwtTokenUtil;
import jeonghyeon.msa.security.RedisRepository;
import jeonghyeon.msa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static jeonghyeon.msa.security.JwtAuthenticationFilter.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final RedisRepository redisRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody RegisterDto dto) {
        return new ResponseEntity(userService.register(dto), HttpStatus.CREATED);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN)) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            return new ResponseEntity<>(new ErrorMessage("refresh Token이 없습니다"), HttpStatus.BAD_REQUEST);
        }

        try {
            jwtTokenUtil.isExpired(refreshToken);
            if (!redisRepository.isExist(refreshToken)) {
                return new ResponseEntity<>(new ErrorMessage("refreshToken이 만료되었습니다."), HttpStatus.BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(new ErrorMessage("refreshToken이 만료되었습니다."), HttpStatus.BAD_REQUEST);
        }

        redisRepository.delete(refreshToken);

        Long usersId = jwtTokenUtil.getUsersId(refreshToken);
        String username = jwtTokenUtil.getUsername(refreshToken);
        String role = jwtTokenUtil.getRole(refreshToken);

        String newAccess = jwtTokenUtil.createJwtWithAccessAndRefresh(ACCESS_TOKEN, usersId, username, role, ACCESS_EXPIRE);
        String newRefresh = jwtTokenUtil.createJwtWithAccessAndRefresh(REFRESH_TOKEN, usersId, username, role, REFRESH_EXPIRE);

        response.setHeader(ACCESS_TOKEN, newAccess);
        response.addCookie(createCookie(REFRESH_TOKEN, newRefresh));
        redisRepository.save(newRefresh, 24L);
        response.setStatus(HttpStatus.OK.value());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN)) {
                refreshToken = cookie.getValue();
            }
        }
        if (refreshToken == null) {
            return new ResponseEntity<>(new ErrorMessage("refresh Token이 없습니다"), HttpStatus.BAD_REQUEST);
        }

        redisRepository.delete(refreshToken);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
