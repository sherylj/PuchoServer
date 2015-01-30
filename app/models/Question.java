package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.util.Date;
import java.util.List;

/**
 * Created by sherylj on 1/27/15.
 */
@Entity
public class Question extends Model {
    @Id
    public Long id;

    @ManyToOne
    public User asker;

    @ManyToOne
    public Skill skill;

    public String asker_name;
    public String title;
    public String content;

    public int upvotes;
    public int downvotes;

    @Version
    public Date asked_on;
    public int share_count;
    public String audio_file_url;

    public Question(String asker_name, String title, String content, String skill) {
        this.asker_name = asker_name;
        this.title = title;
        this.content = content;
        this.skill = Skill.findByName(skill);
    }

    public Question(String asker_name, String content, String skill) {
        this.asker_name = asker_name;
        this.content = content;
        this.skill = Skill.findByName(skill);
    }

    public static Finder<Long, Question> find = new Finder<Long, Question>(Long.class, Question.class);

    public static List<Question> findByTitle(String title) {
        return find.where().eq("title", title).findList();
    }

    public static Question findById(String id) {
        return find.byId(Long.parseLong(id));
    }

    public static List<Question> findByAsker(User asker) {
        return find.where().eq("asker.id", asker.id).findList();
    }

    public static List<Question> findBySkill(Skill skill) {
        return find.where().eq("skill.id", skill.id).findList();
    }



}
