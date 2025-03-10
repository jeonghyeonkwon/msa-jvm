package jeonghyeon.msa.auth.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.regex.Pattern;


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

    public Users(Long usersId, String username, String password, String nickName, UsersRole usersRole) {
        validate(username, password, nickName);
        this.usersId = usersId;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.usersRole = usersRole;
    }

    public static Users createBasic(Long usersId, String username, String password, String nickName) {
        return new Users(usersId, username, password, nickName, UsersRole.BASIC);
    }

    private static void validate(String username, String password, String nickName) {
        notBlank(username);
        notBlank(password);
        notBlank(nickName);
        String regex = "^[a-zA-Z0-9]+$";
        if (!Pattern.matches(regex, username)) throw new IllegalArgumentException("아이디 패턴이 맞지 않습니다");
    }

    private static void notBlank(String text) {
        if (text == null || text.isBlank()) throw new IllegalArgumentException("빈 값을 넣을 수 없습니다.");
    }

    public static Users createAdmin(Long usersId, String username, String password) {
        return new Users(usersId, username, password, "관리자", UsersRole.ADMIN);
    }

    public static Users login(Long usersId, String username, String role) {
        return new Users(
                usersId,
                username,
                "",
                "",
                Arrays.stream(UsersRole.values()).filter(usersRole -> usersRole.name().equals(role)).findFirst().get()
        );
    }
}
