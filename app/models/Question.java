package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "question")
    public List<QuestionSkills> skillList;

    public String asker_name;
    public String title;
    public String content;

    public int upvotes;
    public int downvotes;
    public Date asked_on;
    public int share_count;
    public String audio_file_url;

    public Question(String asker_name, String title, String content, String skill) {
        this.asker_name = asker_name;
        this.title = title;
        this.content = content;
        //Logic: Check if skill is present in Skills table. If yes, get id and add
    }

    public static Finder<Long, Question> find = new Finder<Long, Question>(Long.class, Question.class);

    public static List<Question> findByTitle(String title) {
        return find.where().eq("title", title).findList();
    }

    public static Question findById(String id) {
        return find.byId(Long.parseLong(id));
    }



}
