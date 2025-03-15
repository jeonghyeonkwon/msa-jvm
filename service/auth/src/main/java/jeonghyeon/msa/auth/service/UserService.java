package jeonghyeon.msa.auth.service;


import jeonghyeon.msa.auth.domain.Users;
import jeonghyeon.msa.auth.dto.request.RegisterDto;
import jeonghyeon.msa.auth.kafka.OutboxEventPublisher;
import jeonghyeon.msa.auth.repository.UserRepository;
import jeonghyeon.msa.common.Snowflake;
import jeonghyeon.msa.common.event.EventType;
import jeonghyeon.msa.common.event.payload.AuthCreateEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final Snowflake snowflake = new Snowflake();
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public Long register(RegisterDto dto) {
        usersRepository.findByUsername(dto.getUsername()).ifPresent(
                notUsed -> {
                    throw new IllegalArgumentException("해당 아이디는 존재합니다.");
                }
        );


        Users user = Users.createBasic(
                snowflake.nextId(), dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getNickName()
        );
        Users savedUser = usersRepository.save(user);
        outboxEventPublisher.publish(
                EventType.AUTH_CREATE,
                new AuthCreateEventPayload(savedUser.getUsersId(), dto.getUsername())
        );
        return savedUser.getUsersId();
    }

    public Long findUsersIdByUsername(String username) {
        return usersRepository.findUsersIdByUsername(username);
    }
}
