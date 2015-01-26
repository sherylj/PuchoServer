package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Profession;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/25/15.
 */
public class Professions extends Controller {
    public static Result list() {
        List<Profession> countries = new Model.Finder<Long , Profession>(Long.class, Profession.class).all();
        return ok(Json.toJson(countries));
    }

    public static Result save() {
        Http.RequestBody body = request().body();
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }
        String name = jsonNode.findPath("name").asText();
        //Check if name exists in the table else add it
        Profession language = Profession.findByName(name);
        if (language != null) {
            return found("Language already exists!");
        }
        language = new Profession(name);
        language.save();
        return created();
    }

    public static Result delete(String name) {
        Profession language = Profession.findByName(name);
        if (language == null) {
            return notFound(String.format("Language %s does not exist.", name));
        }

        language.delete();
        return redirect(routes.Professions.list());
    }
}
