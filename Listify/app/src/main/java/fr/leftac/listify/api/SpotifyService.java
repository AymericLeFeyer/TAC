package fr.leftac.listify.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyService {
    @FormUrlEncoded
    @POST("api/token")
    Call<Object> token(@Field("grant_type") String grant_type, @Header("Authorization") String authorization);
}
