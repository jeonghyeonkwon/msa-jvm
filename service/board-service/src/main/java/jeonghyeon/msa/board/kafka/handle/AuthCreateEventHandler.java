package jeonghyeon.msa.board.kafka.handle;

import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.repository.UsersRepository;
import jeonghyeon.msa.common.event.Event;
import jeonghyeon.msa.common.event.EventType;
import jeonghyeon.msa.common.event.payload.AuthCreateEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthCreateEventHandler implements EventHandler<AuthCreateEventPayload> {
    private final UsersRepository usersRepository;

    @Override
    public void handle(Event<AuthCreateEventPayload> event) {
        AuthCreateEventPayload payload = event.getPayload();

        usersRepository.findById(Long.valueOf(payload.getUsersId())).ifPresent(
                notUsed -> {
                    throw new IllegalArgumentException("이미 가입된 회원입니다");
                }
        );
        usersRepository.save(new Users(Long.valueOf(payload.getUsersId()), payload.getUsername()));
    }

    @Override
    public boolean supports(Event<AuthCreateEventPayload> event) {
        return event.getType() == EventType.AUTH_CREATE;
    }
}
