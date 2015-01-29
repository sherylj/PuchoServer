package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Country;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/18/15.
 */
public class Countries extends Controller {

    public static Result list() {
        List<Country> countries = new Model.Finder<Long , Country>(Long.class, Country.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(countries));
        return ok(result);
    }

    public static Result save() {
        Http.RequestBody body = request().body();
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }
        String name = jsonNode.findPath("name").asText();
        //Check if country name exists in the table else add it
        Country country = Country.findByName(name);
        if (country != null) {
            return found("Country already exists!");
        }
        country = new Country(name);
        country.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(country));
        return created(result);
    }

    public static Result delete(String name) {
        Country country = Country.findByName(name);
        if (country == null) {
            return notFound(String.format("Country %s does not exist.", name));
        }

        country.delete();
        return redirect(routes.Countries.list());
    }
}
