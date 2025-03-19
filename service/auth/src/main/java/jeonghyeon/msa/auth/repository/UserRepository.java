package jeonghyeon.msa.auth.repository;

import jeonghyeon.msa.auth.domain.Users;
import jeonghyeon.msa.auth.dto.response.UserInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    @Query("SELECT u.usersId FROM users u WHERE u.username = :username")
    Long findUsersIdByUsername(@Param("username") String username);

    @Query("SELECT new jeonghyeon.msa.auth.dto.response.UserInfoResponse(u.usersId,u.username) FROM users u WHERE u.usersId = :usersId")
    UserInfoResponse findByUsersId(@Param("usersId") Long usersId);
}
