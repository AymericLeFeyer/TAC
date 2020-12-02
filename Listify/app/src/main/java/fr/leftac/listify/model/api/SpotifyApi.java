package fr.leftac.listify.model.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyApi {
    String V1 = "v1/";

    @GET(V1 + "search")
    Call<Object> searchTracks(@Header("Authorization") String token, @Query("q") String search, @Query("type") String type);

    @FormUrlEncoded
    @POST("token")
    Call<Object> token(@Field("grant_type") String grant_type, @Header("Authorization") String authorization);

    @GET(V1 + "artists/{id}")
    Call<Object> getArtist(@Header("Authorization") String token, @Path("id") String id);


}
