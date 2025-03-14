package jeonghyeon.msa.auth.security;

import jeonghyeon.msa.auth.domain.Users;
import jeonghyeon.msa.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        Users users = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다")
                );
        return new PrincipalUserDetails(users);
    }
}
