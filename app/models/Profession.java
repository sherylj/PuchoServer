package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sherylj on 1/25/15.
 */
@Entity
public class Profession extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String name;


    public Profession() {
    }

    public Profession(String name) {
        this.name = name.toLowerCase();
    }

    public String toString() {
        return String.format("%s - %s", id, name);
    }

    public static Finder<Long, Profession> find = new Finder<Long, Profession>(
            Long.class, Profession.class);

    public static Profession findByName(String name) {
        return find.where().eq("name", name.toLowerCase()).findUnique();
    }
}


