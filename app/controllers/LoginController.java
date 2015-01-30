package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by sherylj on 1/23/15.
 */
public class LoginController extends Controller {
    public static final String AUTH_TOKEN = "authToken";
    public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";


    public static User getUser() {
        return (User) Http.Context.current().args.get("user");
    }


    public static Result login() {
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }
        String email = jsonNode.findPath("email").asText();
        String password = jsonNode.findPath("password").asText();
        User user = User.findByEmailAddressAndPassword(email, password);

        if (user == null) {
            return unauthorized();
        }
        else {
            String authToken = user.createToken();
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(AUTH_TOKEN, authToken);
            authTokenJson.put("userId",user.id);
            response().setCookie(AUTH_TOKEN, authToken);
            ObjectNode result = Json.newObject();
            result.put("data", Json.toJson(authTokenJson));
            return ok(result);
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public static Result logout() {
        response().discardCookie(AUTH_TOKEN);
        getUser().deleteToken();
        //TODO: redirect to another enpoint?
        return redirect("/");
    }

}
