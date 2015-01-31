package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;


public class Users extends Controller {


    public static Result list(String username) {

        if (username != "") {
            return checkIfUsernameExists(username);
        }
        List<User> users = new Model.Finder<Long , User>(Long.class, User.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(users));
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result save() {
        Http.RequestBody body = request().body();
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }
        String username = jsonNode.findPath("username").asText();
        String email = jsonNode.findPath("email").asText();
        String password = jsonNode.findPath("password").asText();
        User user = new User(username, email, password);
        user.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(user));
        return created(Json.toJson(result));
    }

    public static Result checkIfUsernameExists(String username) {
        User user = User.findByUsername(username);
        ObjectNode bool = Json.newObject();
        ObjectNode result = Json.newObject();
        if (user == null)
            bool.put("boolean", 0);
        else
            bool.put("boolean", 1);

        return ok(Json.toJson(bool));

    }

    public static Result findByEmail(String email) {
        return ok(Json.toJson(User.findByEmail(email)));
    }

    public static Result findById(String id) {
        return ok(Json.toJson(User.findById(id)));
    }

    public static Result findByUsername(String username) {
        return ok(Json.toJson(User.findByUsername(username)));
    }

    public static Result delete(String id) {
        User user = User.findById(id);
        if (user == null) {
            return notFound(String.format("User id %s does not exist.", id));
        }

        user.delete();
        return redirect(routes.Users.list(""));
    }
}
