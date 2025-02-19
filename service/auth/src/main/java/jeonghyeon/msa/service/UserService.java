package jeonghyeon.msa.service;

import jeonghyeon.msa.common.snowflake.Snowflake;
import jeonghyeon.msa.domain.Users;
import jeonghyeon.msa.dto.request.RegisterDto;
import jeonghyeon.msa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public Long register(RegisterDto dto) {
        usersRepository.findByUsername(dto.getUsername()).ifPresent(
                notUsed -> new IllegalArgumentException("해당 아이디는 존재합니다.")
        );

        Users user = Users.createBasic(
                snowflake.nextId(), dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getNickName()
        );

        return usersRepository.save(user).getUsersId();
    }

}
