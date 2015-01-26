import models.Profession;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created by sherylj on 1/25/15.
 */
public class ProfessionTest {
    @Test
    public void testCreate() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Profession profession = new Profession("Doctor");
                profession.save();
                assertThat(profession.id).isNotNull();
                assertThat(profession.name).isEqualTo("doctor");



            }
        });
    }

    @Test
    public void findAll() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Profession profession1 = new Profession("Professor");
                profession1.save();

                Profession profession2 = new Profession("Manager");
                profession2.save();

                List<Profession> professions = Profession.find.all();

                assertThat(professions.size()).isEqualTo(2);
            }
        });
    }

}
