package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class User extends Model {
    @Id
    public Long id;

    public String firstname;
    public String lastname;
    public String profession;
    public String username;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    public String linkedin;

    public String personal_url;

    @OneToOne
    public Country country;

    @OneToMany(mappedBy = "user")
    public List<UserEducation> educationList;


}
