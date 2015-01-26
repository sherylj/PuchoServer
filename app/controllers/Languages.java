package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Language;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/24/15.
 */
public class Languages extends Controller {

    public static Result list() {
        List<Language> countries = new Model.Finder<Long , Language>(Long.class, Language.class).all();
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
        Language language = Language.findByName(name);
        if (language != null) {
            return found("Language already exists!");
        }
        language = new Language(name);
        language.save();
        return created();
    }

    public static Result delete(String name) {
        Language language = Language.findByName(name);
        if (language == null) {
            return notFound(String.format("Language %s does not exist.", name));
        }

        language.delete();
        return redirect(routes.Languages.list());
    }
}
