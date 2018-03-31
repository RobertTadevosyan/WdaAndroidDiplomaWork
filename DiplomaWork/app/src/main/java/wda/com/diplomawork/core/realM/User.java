package wda.com.diplomawork.core.realM;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wedoapps on 3/23/18.
 */

public class User implements Serializable {
    private String login;
    private String firstName;
    private String lastName;
    private String lastSeen;
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public User() {
    }

    public User(String firstName, String lastName, String login, String lastSeen, String uId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.lastSeen = lastSeen;
        this.uId = uId;
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

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }


    //    private String password;

}
