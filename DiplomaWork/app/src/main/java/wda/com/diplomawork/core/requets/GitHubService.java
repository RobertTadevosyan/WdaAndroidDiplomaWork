package wda.com.diplomawork.core.requets;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wedoapps on 3/17/18.
 */

public interface GitHubService {

    @FormUrlEncoded
    @POST("registration")
    Call<RequestResult> registrate(
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("email") String email,
            @Field("city") String city,
            @Field("country") String country,
            @Field("device_token") String device_token,
            @Field("phone") String phone,
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_type") String device_type,
            @Field("lang_id") String languageId);

}
