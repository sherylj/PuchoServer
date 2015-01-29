package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by sherylj on 1/26/15.
 */
@Entity
public class Skill extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "skill")
    public List<QuestionSkills> questions;

    public Skill() {}

    public Skill(String name) {
        this.name = name.toLowerCase();
    }

    public String toString() {
        return String.format("%s - %s", id, name);
    }

    public static Finder<Long,Skill> find = new Finder<Long,Skill>(
            Long.class, Skill.class);

    public static Skill findByName(String name) {
        return find.where().eq("name", name.toLowerCase()).findUnique();
    }


}

