package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Area;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/18/15.
 */
public class Areas extends Controller {
    public static Result list() {
        List<Area> areas = new Model.Finder<Long , Area>(Long.class, Area.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(areas));
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
        Area area = Area.findByName(name);
        if (area != null) {
            return found("Area already exists!");
        }
        area = new Area(name);
        area.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(area));
        return created(result);
    }

    public static Result delete(String name) {
        Area area = Area.findByName(name);
        if (area == null) {
            return notFound(String.format("Area %s does not exist.", name));
        }

        area.delete();
        return redirect(routes.Areas.list());
    }
}
