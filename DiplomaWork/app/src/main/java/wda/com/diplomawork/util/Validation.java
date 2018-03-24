package wda.com.diplomawork.util;

public class Validation {
    public static boolean isValidLogin(String login) {
        return login.length() >= 6;
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

}