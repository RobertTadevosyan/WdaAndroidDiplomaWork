package wda.com.diplomawork.core.realM;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wedoapps on 3/23/18.
 */

public class UserRM extends RealmObject {
    @PrimaryKey
    private String login;
    private String firstName;
    private String lastName;
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public UserRM() {
    }

    public UserRM(String firstName, String lastName, String login, String uId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
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

    //    private String password;

    public static void saveUserInRealM(User user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        UserRM userRM = new UserRM();
        userRM.setFirstName(user.getFirstName());
        userRM.setLastName(user.getLastName());
        userRM.setLogin(user.getLogin());
        realm.copyToRealmOrUpdate(userRM);
        realm.commitTransaction();
    }

    public static void saveUserInRealM(UserRM user){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public static UserRM getLoggedInUser(){
        return Realm.getDefaultInstance().where(UserRM.class).findFirst();
    }

    public static void removeUserFromDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        getLoggedInUser().deleteFromRealm();
        realm.commitTransaction();
    }
}
