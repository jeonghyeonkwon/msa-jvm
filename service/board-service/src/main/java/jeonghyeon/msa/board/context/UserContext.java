package jeonghyeon.msa.board.context;

import jeonghyeon.msa.board.domain.Users;

public class UserContext {
    private static final ThreadLocal<Users> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Users users) {
        currentUser.set(users);
    }

    public static Users getCurrentUser() {
        return currentUser.get();
    }

    public static boolean isExistUser() {
        return currentUser.get() != null;
    }

    public static void clear() {
        currentUser.remove();
    }
}
