import models.Answer;
import models.Question;
import models.Skill;
import models.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created by sherylj on 1/29/15.
 */
public class AnswerTest {
    @Test
    public void testCreate() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                User user = new User("sjohnd", "johndoe@test.com", "rg");
                user.save();
                Skill skill = new Skill("Advice");
                skill.save();
                Question question = new Question("John Doe", "College admission", "How do I pick a university?", "Advice");
                question.save();
                Answer answer = new Answer("You can try US News");
                answer.question = question;
                answer.answerer = user;
                answer.save();
                assertThat(answer.id).isNotNull();
                assertThat(answer.content).isEqualTo("How do I pick a university?");
                assertThat(answer.question.skill).isEqualTo("advice");



            }
        });
    }
}
