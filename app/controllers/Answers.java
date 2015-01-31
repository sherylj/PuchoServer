package controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Answer;
import models.Question;
import models.User;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.*;

import java.util.List;

/**
 * Created by sherylj on 1/29/15.
 */
public class Answers extends Controller {


    public static Result list() {
        List<Answer> answers = new Model.Finder<Long , Answer>(Long.class, Answer.class).all();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(answers));
        return ok(result);
    }


    @Security.Authenticated(ActionAuthenticator.class)
    @JsonIgnore
    public static Result listAnswersByUser() {
        String userId = request().getQueryString("userId");
        if (userId == "") {
            return badRequest("Empty userid skill does not exist");
        }

        List<Answer> answers;
        User user = User.findById(userId);

        if (user.id == null) {
            return badRequest("Skill does not exist");
        }
        else {
            answers = Answer.findAnswersByUser(user);
        }

        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(answers));
        return ok(result);


    }

    @Security.Authenticated(ActionAuthenticator.class)
    @JsonIgnore
    public static Result listAnswersByQuestion(String questionId) {
        //String questionId = request().getQueryString("questionId");
        if (questionId == "") {
            return list();
        }

        List<Answer> answers;
        Question question = Question.findById(questionId);

        if (question.id == null) {
            return badRequest("Question does not exist");
        }
        else {
            answers = Answer.findAnswersByQuestion(question);
        }

        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(answers));
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

        String questionId = jsonNode.findPath("questionId").asText();
        String content = jsonNode.findPath("content").asText();
        String answererId = jsonNode.findPath("answererId").asText();

        Answer answer = new Answer(content);
        //question.save();

        //Now find user and add to question
        Question question = Question.findById(questionId);
        answer.question = question;

        User user = User.findById(answererId);
        answer.answerer = user;
        answer.save();
        ObjectNode result = Json.newObject();
        result.put("data", Json.toJson(answer));
        return ok(result);
    }
}
