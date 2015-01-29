import models.Question;
import models.Skill;
import models.User;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created by sherylj on 1/28/15.
 */
public class QuestionTest {
    @Test
    public void testCreate() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Skill skill = new Skill("Advice");
                skill.save();
                Question question = new Question("John Doe", "College admission", "How do I pick a university?", "Advice");
                question.save();
                assertThat(question.id).isNotNull();
                assertThat(question.asker_name).isEqualTo("John Doe");
                assertThat(question.title).isEqualTo("College admission");
                assertThat(question.content).isEqualTo("How do I pick a university?");
                assertThat(question.skill.name).isEqualTo("advice");



            }
        });
    }

    @Test
    //public static Question findById(String id)
    public void testFindByTitle() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Skill skill = new Skill("Advice");
                skill.save();
                Question question1 = new Question("John Doe", "College admission", "How do I pick a university?", "Advice");
                question1.save();
                Question question2 = new Question("Jane Doe", "College admission", "How do I pick grad school?", "Advice");
                question2.save();

                List<Question> result = Question.findByTitle("College admission");
                assertThat(result.size()).isEqualTo(2);

                //User user1 = new User("abc","abc@me.com","abcdefgh");
                //user1.save();

            }
        });
    }

    @Test
    public void testFindByAsker() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Skill skill = new Skill("Advice");
                skill.save();
                Question question = new Question("John Doe", "College admission", "How do I pick a university?", "Advice");
                question.save();
                User user = new User("John Doe", "johndoe@me.com","asdsfdf");
                user.save();
                question.asker = user;
                question.update();
                assertThat(question.id).isNotNull();
                assertThat(question.asker_name).isEqualTo("John Doe");
                assertThat(question.title).isEqualTo("College admission");
                assertThat(question.content).isEqualTo("How do I pick a university?");
                assertThat(question.skill.name).isEqualTo("advice");
                assertThat(question.asker.getEmail()).isEqualTo("johndoe@me.com");
                assertThat(question.asker.id).isEqualTo(user.id);

                List<Question> result = Question.findByAsker(user);
                assertThat(result.size()).isEqualTo(1);
                assertThat(result.get(0).asker_name).isEqualTo("John Doe");



            }
        });
    }

    @Test
    public void testFindBySkill() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                Skill skill = new Skill("Advice");
                skill.save();
                Question question = new Question("John Doe", "College admission", "How do I pick a university?", "Advice");
                question.save();
                User user = new User("John Doe", "johndoe@me.com","asdsfdf");
                user.save();
                question.asker = user;


                assertThat(question.id).isNotNull();
                assertThat(question.asker_name).isEqualTo("John Doe");
                assertThat(question.title).isEqualTo("College admission");
                assertThat(question.content).isEqualTo("How do I pick a university?");
                assertThat(question.asker.getEmail()).isEqualTo("johndoe@me.com");
                assertThat(question.asker.id).isEqualTo(user.id);

                List<Question> result = Question.findBySkill(skill);
                assertThat(result.size()).isEqualTo(1);
                assertThat(result.get(0).asker_name).isEqualTo("John Doe");



            }
        });
    }
}

