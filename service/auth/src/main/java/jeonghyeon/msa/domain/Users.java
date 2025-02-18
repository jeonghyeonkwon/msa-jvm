package jeonghyeon.msa.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Entity(name = "users")
@Table
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {
    @Id
    private Long usersId;

    private String username;
    private String password;
    private String nickName;

    @Enumerated(EnumType.STRING)
    private UsersRole usersRole;

    private Users(Long usersId, String username, String password, String nickName, UsersRole usersRole) {
        this.usersId = usersId;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.usersRole = usersRole;
    }

    public static Users createBasic(Long usersId, String username, String password, String nickName) {
        return new Users(usersId, username, password, nickName, UsersRole.BASIC);
    }

    public static Users createAdmin(Long usersId, String username, String password) {
        return new Users(usersId, username, password, "관리자", UsersRole.ADMIN);
    }

}
