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

    @Constraints.Required
    private String username;

    @Constraints.Required
    private String email;

    @Constraints.Required
    private String password;

    public String linkedin;
    public String personal_url;

    @OneToOne
    public Country country;

    @OneToMany(mappedBy = "user")
    public List<UserEducation> educationList;

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User findById(String id) {
        return find.byId(Long.parseLong(id));
    }

    public static User findByUsername(String username) {
        return find.where().eq("username", username).findUnique();
    }

    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }



}
