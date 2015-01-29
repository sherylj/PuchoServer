package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Entity
public class User extends Model {
    @Id
    public Long id;

    public String fullName;
    public String profession;

    private String authToken;

    @Constraints.Required
    private String username;

    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.Required
    @Constraints.MinLength(6)
    @Constraints.MaxLength(256)
    private String password;

    private byte[] shaPassword;

    public String linkedin;
    public String personal_url;

    public Country country;

    @OneToMany(mappedBy = "user")
    public List<UserEducation> educationList;

    @OneToMany(mappedBy = "asker")
    public List<Question> questionList;


    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setPassword(String password) {
        this.password = password;
        shaPassword = getSha512(password);
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
        setEmail(email);
        setPassword(password);
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

    public String createToken() {
        authToken = UUID.randomUUID().toString();
        save();
        return authToken;
    }

    public void deleteToken() {
        authToken = null;
        save();
    }


    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    public static User findByAuthToken(String authToken) {
        if (authToken == null) {
            return null;
            }

        try  {
               return find.where().eq("authToken", authToken).findUnique();
        }
        catch (Exception e) {
                return null;
        }
    }


    public static User findByEmailAddressAndPassword(String email, String password) {

           return find.where().eq("email", email.toLowerCase())
                   .eq("shaPassword", getSha512(password)).findUnique();
           /*return find.where().conjunction()
                   .add(Expr.eq("email", email.toLowerCase()))
                   .add(Expr.eq("shaPassword", getSha512(password)))
                   .findUnique();   */


    }
}