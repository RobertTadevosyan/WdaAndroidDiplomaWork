package wda.com.diplomawork.core.realM;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wedoapps on 3/23/18.
 */

public class User {
    private String login;
    private String firstName;
    private String lastName;


    public User() {
    }

    public User(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    //    private String password;

}
