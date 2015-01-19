package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Country extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToOne(mappedBy = "country")
    public User user;

    public Country() {}

    public Country(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s - %s", id, name);
    }

    public static Finder<Long,Country> find = new Finder<Long,Country>(
            Long.class, Country.class);

    public static Country findByName(String name) {
        return find.where().eq("name",name).findUnique();
    }

}
