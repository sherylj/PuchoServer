import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.LoginController;
import controllers.routes;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import utils.TestData;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.status;

/**
 * Created by sherylj on 1/30/15.
 */
public class AnswersControllerTest {
    @Test
    public void saveAnswer() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();
                String authToken = TestData.user1.createToken();

                ObjectNode answerJson = Json.newObject();
                answerJson.put("questionId", 1);
                answerJson.put("content", "Check out university site");
                answerJson.put("answererId", TestData.user2.id);


                Result result = callAction(routes.ref.Answers.save(), fakeRequest().withJsonBody(answerJson).withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(OK);
                JsonNode json = Json.parse(contentAsString(result));

                assertThat(json.get("data").get("id")).isNotNull();
            }
        });
    }

    @Test
    public void listAnswersByQuestion() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();
                String authToken = TestData.user1.createToken();

                ObjectNode answerJson = Json.newObject();
                answerJson.put("questionId", 1);
                answerJson.put("content", "Check out university site");
                answerJson.put("answererId", TestData.user2.id);

                callAction(routes.ref.Answers.save(), fakeRequest().withJsonBody(answerJson).withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));
                Result result = callAction(routes.ref.Answers.listAnswersByQuestion("1"), fakeRequest("GET","/answers?questionId=1")
                        .withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(OK);

                JsonNode json = Json.parse(contentAsString(result));
                assertThat(json.get("data").get(0).get("id")).isNotNull();
            }
        });
    }


    @Test
    public void listAnswers() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();
                String authToken = TestData.user1.createToken();

                ObjectNode answerJson = Json.newObject();
                answerJson.put("questionId", 1);
                answerJson.put("content", "Check out university site");
                answerJson.put("answererId", TestData.user2.id);

                callAction(routes.ref.Answers.save(), fakeRequest().withJsonBody(answerJson).withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));
                Result result = callAction(routes.ref.Answers.listAnswersByQuestion(""), fakeRequest("GET","/answers")
                        .withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(OK);

                JsonNode json = Json.parse(contentAsString(result));
                assertThat(json.get("data").get(0).get("id")).isNotNull();
            }
        });
    }
}
