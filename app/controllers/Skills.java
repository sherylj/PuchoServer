package controllers;

/**
 * Created by sherylj on 1/26/15.
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Skill;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/18/15.
 */
public class Skills extends Controller {
    public static Result list() {
        List<Skill> skills = new Model.Finder<Long , Skill>(Long.class, Skill.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(skills));
        return ok(result);
    }

    public static Result save() {
        Http.RequestBody body = request().body();
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }
        String name = jsonNode.findPath("name").asText();
        //Check if area exists in the table else add it
        Skill skill = Skill.findByName(name);
        if (skill != null) {
            return found("Skill already exists!");
        }
        skill = new Skill(name);
        skill.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(skill));
        return created(result);
    }

    public static Result delete(String name) {
        Skill skill = Skill.findByName(name);
        if (skill == null) {
            return notFound(String.format("Area %s does not exist.", name));
        }

        skill.delete();
        return redirect(routes.Skills.list());
    }
}

