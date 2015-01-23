import models.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created by sherylj on 1/22/15.
 */
public class UserTest {
    @Test
    public void testCreate() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User user = new User("uname", "test@mail.com", "password");
                user.save();
                assertThat(user.id).isNotNull();
                assertThat(user.getEmail()).isEqualTo("test@mail.com");
                assertThat(user.getUsername()).isEqualTo("uname");

                try {
                    // check the private shaPassword
                    Field field = User.class.getDeclaredField("shaPassword");
                    field.setAccessible(true);
                    assertThat(field.get(user)).isEqualTo(User.getSha512("password"));
                    assertThat(((byte[]) field.get(user)).length).isEqualTo(64);

                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }


    @Test
    public void createToken() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User newUser = new User("test@mail.com", "password", "sherylj");
                newUser.save();

                assertThat(newUser.id).isNotNull();

                String token = newUser.createToken();

                assertThat(token).isNotNull();

                User foundUser = User.findByAuthToken(token);

                assertThat(newUser.id).isEqualTo(foundUser.id);
            }
        });
    }

    @Test
    public void findByEmailAddressAndPassword() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User newUser = new User("sherylj", "test@mail.com", "password");
                newUser.save();

                User foundUser = User.findByEmailAddressAndPassword("test@mail.com", "password");

                assertThat(foundUser).isNotNull();
                assertThat(foundUser.getUsername()).isEqualTo("sherylj");
            }
        });
    }

    @Test
    public void findAll() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User user1 = new User("abc","abc@me.com","abcdefgh");
                user1.save();

                User user2 = new User("def", "def@me.com", "fgegef13");
                user2.save();

                List<User> users = User.find.all();

                assertThat(users.size()).isEqualTo(2);
            }
        });
    }



}
