package controllers;

import models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTests {
    private UserController uc;
    private Map<String, User> users;

    @Before
    public void setUp() {
        this.uc = new UserController();
        User user = new User("login", "password");
        this.users = new HashMap<>();
        users.put("login", user);
    }

    @Test
    public void shouldBeTrueForPresentLogin() {
        boolean actual = uc.checkIfLoginPresent("login", users);

        assertThat(actual).isTrue();
    }

    @Test
    public void shouldBeFalseForNotPresentLogin() {
        boolean actual = uc.checkIfLoginPresent("", users);

        assertThat(actual).isFalse();
    }

    @Test
    public void shouldBeTrueForMatchingPassword() {
        boolean actual = uc.checkIfLoginAndPasswordAreConnected(
                "login", "password", users);

        assertThat(actual).isTrue();
    }

    @Test
    public void shouldBeFalseForWrongPassword() {
        boolean actual = uc.checkIfLoginAndPasswordAreConnected(
                "login", "wrongPassword", users);

        assertThat(actual).isFalse();
    }

    @Test
    public void shouldBeFalseForNotPresentLogin2(){
        boolean actual = uc.checkIfLoginAndPasswordAreConnected(
                "","password",users);

        assertThat(actual).isFalse();
    }
}
