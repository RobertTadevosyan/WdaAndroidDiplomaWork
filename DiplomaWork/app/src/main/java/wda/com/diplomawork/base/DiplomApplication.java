package wda.com.diplomawork.base;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wedoapps on 3/24/18.
 */

public class DiplomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("diploma.wda.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
