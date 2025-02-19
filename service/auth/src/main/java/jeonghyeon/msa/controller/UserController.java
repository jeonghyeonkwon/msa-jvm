package jeonghyeon.msa.controller;

import jeonghyeon.msa.dto.request.RegisterDto;
import jeonghyeon.msa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody RegisterDto dto) {
        return new ResponseEntity(userService.register(dto), HttpStatus.CREATED);
    }
}
