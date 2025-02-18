package jeonghyeon.msa.service;

import jeonghyeon.msa.common.snowflake.Snowflake;
import jeonghyeon.msa.domain.Users;
import jeonghyeon.msa.dto.request.UsersDto;
import jeonghyeon.msa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository usersRepository;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public Long register(UsersDto dto) {
        Users user = Users.createBasic(snowflake.nextId(), dto.getUsername(), dto.getPassword(), dto.getNickName());
        Users saved = usersRepository.save(user);

        return saved.getUsersId();
    }

}
