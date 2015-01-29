import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.routes;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import utils.TestData;
import controllers.routes;
import controllers.LoginController;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created by sherylj on 1/23/15.
 */

//TODO: Test with sample data
public class LoginControllerTest {
    @Test
    public void login() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();

                ObjectNode loginJson = Json.newObject();
                loginJson.put("email", TestData.user1.getEmail());
                loginJson.put("password", TestData.user1.getPassword());

                Result result = callAction(routes.ref.LoginController.login(), fakeRequest().withJsonBody(loginJson));

                assertThat(status(result)).isEqualTo(OK);
                JsonNode json = Json.parse(contentAsString(result));
                assertThat(json.get("data").get("authToken")).isNotNull();
            }
        });
    }

    @Test
    public void logout() {
        running(fakeApplication(inMemoryDatabase()), new Runnable() {
            public void run() {
                TestData.loadTestData();

                String authToken = TestData.user1.createToken();

                Result result = callAction(routes.ref.LoginController.logout(), fakeRequest().withHeader(LoginController.AUTH_TOKEN_HEADER, authToken));

                assertThat(status(result)).isEqualTo(SEE_OTHER);
            }
        });
    }
}
