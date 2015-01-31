package utils;

import models.Question;
import models.Skill;
import models.User;
import play.Logger;

/**
 * Created by sherylj on 1/23/15.
 */
public class TestData {

    public static User user1;
    public static User user2;
    public static Skill skill;
    public static Question question;

    public static void loadTestData() {
        Logger.info("Loading Demo Data");

        user1 = new User("abcd","abcd@test.com", "password");
        user1.save();

        user2 = new User("defg","defg@test.com","password");
        user2.save();

        skill = new Skill("Advice");
        skill.save();

        question = new Question("abcd", "College Question", "How to pick a university?","Advice");
        question.save();





    }


}
