package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by sherylj on 1/17/15.
 */
@Entity
public class UserEducation extends Model {
    @Id
    public Integer id;

    @ManyToOne
    public User user;

    public String degree;
    public String institution;
    public Date start_year;
    public Date end_year;

    @ManyToOne
    public Area area;
}
