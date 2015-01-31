package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.util.Date;
import java.util.List;

/**
 * Created by sherylj on 1/29/15.
 */
@Entity
public class Answer extends Model {

    @Id
    public Long id;

    @ManyToOne
    public User answerer;

    @ManyToOne
    public Question question;

    public String content;

    public int upvotes;
    public int downvotes;

    @Version
    public Date answered_on;
    public int share_count;

    public Answer(String content) {
        this.content = content;
    }

    public static Finder<Long, Answer> find = new Finder<Long, Answer>(Long.class, Answer.class);

    public static Answer findById(String id) {
        return find.byId(Long.parseLong(id));
    }

    public static List<Answer> findAnswersByQuestion(Question question) {
        return find.where().eq("question.id", question.id).findList();
    }

    public static List<Answer> findAnswersByUser(User user) {
        return find.where().eq("answerer.id", user.id).findList();
    }
}
