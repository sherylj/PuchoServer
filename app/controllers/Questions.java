package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Question;
import models.Skill;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by sherylj on 1/28/15.
 */
public class Questions extends Controller {
    //TODO: When creating a new Question, add skills to list if not exists (QuestionsSkills)

    public static Result list() {
        List<Question> questions = new Model.Finder<Long , Question>(Long.class, Question.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(questions));
        return ok(result);
    }

    public static Result list(String skillName) {
        if (skillName != "") {
            Skill skill = Skill.findByName(skillName);

        }

    }


}
