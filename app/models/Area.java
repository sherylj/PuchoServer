package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Area extends Model {
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @OneToMany(mappedBy = "area")
    public List<UserEducation> educationDegree = new ArrayList<UserEducation>();

    public Area() {}

    public Area(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s - %s", id, name);
    }

    public static Finder<Long,Area> find = new Finder<Long,Area>(
            Long.class, Area.class);

    public static Area findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }


}
