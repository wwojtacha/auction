package helpers;

import models.User;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FileHelperTest {
    private String testingDatabaseFilepath = "src/test/resources/testingDb.txt";
    private String filepath = "src/main/resources/test.txt";

    @After
    public void flush() {
        try {
            Files.delete(Paths.get(filepath));
        } catch (IOException ignored) {
        }
    }

    @Test
    public void shouldCreateNewFile() {
        FileHelper.writeToUsersFile("", filepath);

        File file = new File(filepath);
        assertThat(file).exists();
    }

    @Test
    public void shouldContainText() {
        String text = "test";
        FileHelper.writeToUsersFile(text, filepath);

        File file = new File(filepath);
        assertThat(file).hasContent(text);
    }

    @Test
    public void shouldParseToLine() {
        FileHelper fc = new FileHelper();
        User user = new User("login", "password");
        String actual = fc.toLine(user);

        String expected = "login;password";

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldReadFromFile(){
        Map<String,User> map = FileHelper.readFromFile(testingDatabaseFilepath);
        String login1 = "login1";
        Object actual = map.get(login1);

        assertThat(actual).isNotNull();
    }

    @Test
    public void shouldNotReadFromFile(){
        Map<String,User> map = FileHelper.readFromFile(testingDatabaseFilepath);
        String login999 = "login999";
        Object actual = map.get(login999);

        assertThat(actual).isNull();
    }

    @Test
    public void shouldGetCorrectPassword(){
        Map<String,User> map = FileHelper.readFromFile(testingDatabaseFilepath);
        String expected = "password1";

        String actual = map.get("login1").getPassword();

        assertThat(actual).isEqualTo(expected);
    }

}