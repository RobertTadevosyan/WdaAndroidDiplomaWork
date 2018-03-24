package wda.com.diplomawork.core.requets;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wedoapps on 3/17/18.
 */
public class RetrofitHelper {
    static GitHubService gitHubService;
    static Retrofit retrofit;


    public static GitHubService gitHubService(Context context) {
        if (gitHubService == null){
            Gson gson = new GsonBuilder().setExclusionStrategies(new  ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(Object.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).setLenient().create();

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//            httpClientBuilder.sslSocketFactory( getBuilder(context));

            retrofit =  new Retrofit.Builder()
                    .baseUrl("http://dev3.wedoapps.eu/frontend/web/v1/users/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .callFactory(httpClientBuilder.build())
                    .build();
            gitHubService = retrofit.create(GitHubService.class);
        }
        return gitHubService;
    }
}
