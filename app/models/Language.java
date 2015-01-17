package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Language extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    public Language() {};

    public Language(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return String.format("%s - %s", id, name);
    }

    public static Finder<Long,Language> find = new Finder<Long,Language>(
            Long.class, Language.class);
}
