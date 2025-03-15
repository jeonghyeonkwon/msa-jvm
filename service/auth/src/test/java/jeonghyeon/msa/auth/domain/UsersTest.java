package jeonghyeon.msa.auth.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UsersTest {

    @Test
    void null_or_blank() {

        assertThatThrownBy(() -> Users.createBasic(1L, null, "", "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Users.createBasic(1L, "", null, "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Users.createBasic(1L, "", "", null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Users.createBasic(1L, "", "", "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Users.createBasic(1L, null, null, null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Users.createBasic(1L, " ", " ", " ")).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 띄어쓰기_금지() {
        assertThatThrownBy(() -> Users.createBasic(1L, "k o", "1234", "nickname")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 아이디는_한글_금지() {
        assertThatThrownBy(() -> Users.createBasic(1L, "한글", "1234", "nickname")).isInstanceOf(IllegalArgumentException.class);
    }
}