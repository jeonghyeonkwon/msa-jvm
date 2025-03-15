package jeonghyeon.msa.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeonghyeon.msa.auth.dto.request.RegisterDto;
import jeonghyeon.msa.auth.dto.response.ResponseDto;
import jeonghyeon.msa.auth.exception.ErrorResult;
import jeonghyeon.msa.auth.security.JwtTokenUtil;
import jeonghyeon.msa.auth.security.RedisRepository;
import jeonghyeon.msa.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static jeonghyeon.msa.auth.security.JwtAuthenticationFilter.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;
    private final RedisRepository redisRepository;
    private final JwtTokenUtil jwtTokenUtil;


    @GetMapping({"", "/"})
    public ResponseEntity certification(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String username = jwtTokenUtil.getUsernameAuthorization(authorization);

        Long usersId = userService.findUsersIdByUsername(username);
        return new ResponseEntity(new ResponseDto<>(String.valueOf(usersId)), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody RegisterDto dto) {
        return new ResponseEntity(new ResponseDto<>(userService.register(dto)), HttpStatus.CREATED);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REFRESH_TOKEN)) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            return new ResponseEntity<>(new ErrorResult("refresh Token이 없습니다"), HttpStatus.BAD_REQUEST);
        }

        try {
            jwtTokenUtil.isExpired(refreshToken);
            if (!redisRepository.isExist(refreshToken)) {

                return new ResponseEntity<>(new ErrorResult("refreshToken이 만료되었습니다."), HttpStatus.BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(new ErrorResult("refreshToken이 만료되었습니다."), HttpStatus.BAD_REQUEST);
        }

        redisRepository.delete(refreshToken);


        String username = jwtTokenUtil.getUsername(refreshToken);
        String role = jwtTokenUtil.getRole(refreshToken);

        String newAccess = jwtTokenUtil.createJwtWithAccessAndRefresh(ACCESS_TOKEN, username, role, ACCESS_EXPIRE);

        String newRefresh = jwtTokenUtil.createJwtWithAccessAndRefresh(REFRESH_TOKEN, username, role, REFRESH_EXPIRE);


        response.setHeader(ACCESS_TOKEN, newAccess);
        response.addCookie(createCookie(REFRESH_TOKEN, newRefresh));
        redisRepository.save(newRefresh, 24L);
        response.setStatus(HttpStatus.OK.value());

        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper om = new ObjectMapper();
        response.getWriter().write(om.writeValueAsString(new ResponseDto<>(username)));
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
            return new ResponseEntity<>(new ErrorResult("refresh Token이 없습니다"), HttpStatus.BAD_REQUEST);
        }

        redisRepository.delete(refreshToken);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
