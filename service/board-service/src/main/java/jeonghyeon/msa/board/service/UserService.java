package jeonghyeon.msa.board.service;

import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.response.UsersResponse;
import jeonghyeon.msa.board.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UsersRepository usersRepository;
    
    @Transactional
    public Users createUser(UsersResponse request) {
        usersRepository.findById(request.getUsersId()).ifPresent(
                notUsed -> {
                    throw new IllegalArgumentException("이미 가입된 회원입니다");
                }
        );
        return usersRepository.save(new Users(request.getUsersId(), request.getUsername()));
    }

    @Transactional
    public Optional<Users> findByUsersId(Long usersId){
        return usersRepository.findById(usersId);
    }

}
