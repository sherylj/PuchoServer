package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by sherylj on 1/27/15.
 */

@Entity
public class QuestionSkills extends Model {

    @ManyToOne
    public Question question;

    @ManyToOne
    public Skill skill;

    public static Finder<Long, QuestionSkills> find =
            new Finder<Long, QuestionSkills>(Long.class, QuestionSkills.class);

    /*
    public QuestionSkills(String questionId, String skillName) {
        Question q = Question.findById(questionId);
        Skill s = Skill.findByName(skillName);
        this.question = q;
        this.skill = s;
    }*/

    public static List<QuestionSkills> findQuestionSkillsBySkill(String skillId) {
        return find.where().eq("skill_id", Long.parseLong(skillId)).findList();
    }
}
