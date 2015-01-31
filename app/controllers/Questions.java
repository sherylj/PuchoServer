package controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Question;
import models.Skill;
import models.User;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;

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

    @Security.Authenticated(ActionAuthenticator.class)
    @JsonIgnore
    public static Result listQuestionsBySkill(String skillName) {
        //String skillName = request().getQueryString("skillName");
        if (skillName == "") {
            return list();
        }

        List<Question> questions;
        Skill skill = Skill.findByName(skillName);

        if (skill.id == null) {
            return badRequest("Skill does not exist");
        }
        else {
            questions = Question.findBySkill(skill);
        }

        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(questions));
        return ok(result);


    }

    @Security.Authenticated(ActionAuthenticator.class)
    @BodyParser.Of(BodyParser.Json.class)
    @JsonIgnore
    public static Result save() {
        Http.RequestBody body = request().body();
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode.isNull()) {
            return badRequest("Request Body missing");
        }

        String askerName = jsonNode.findPath("askerName").asText();
        String content = jsonNode.findPath("content").asText();
        String skill = jsonNode.findPath("skill").asText();
        String askerId = jsonNode.findPath("askerId").asText();

        Question question = new Question(askerName,content,skill);

        //Now find user and add to question
        User asker = User.findById(askerId);
        question.asker = asker;
        question.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(question));
        return ok(result);
    }

    @Security.Authenticated(ActionAuthenticator.class)
    @JsonIgnore
    public static Result getQuestionById(String id) {
        Question question = Question.findById(id);

        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(question));
        return ok(result);


    }

}
