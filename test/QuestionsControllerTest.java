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
 * Created by sherylj on 1/28/15.
 */
public class QuestionsControllerTest  {
    @Test
    public void saveQuestion() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();
                String authToken = TestData.user1.createToken();

                ObjectNode questionJson = Json.newObject();
                questionJson.put("askerName", "abcd");
                questionJson.put("skill", "Computer Science");
                questionJson.put("content", "How to pick a university?");
                questionJson.put("askerId", TestData.user1.id);

                Result result = callAction(routes.ref.Questions.save(), fakeRequest().withJsonBody(questionJson).withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(OK);
                JsonNode json = Json.parse(contentAsString(result));

                assertThat(json.get("data").get("id")).isNotNull();
            }
        });
    }
    //listQuestionsBySkill
    @Test
    public void listQuestionsBySkill() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();
                String authToken = TestData.user1.createToken();



                Result result = callAction(routes.ref.Questions.listQuestionsBySkill("advice"), fakeRequest("GET","/questions?skillName=Advice")
                        .withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(OK);

                JsonNode json = Json.parse(contentAsString(result));
                assertThat(json.get("data").get(0).get("id")).isNotNull();
            }
        });
    }
}
