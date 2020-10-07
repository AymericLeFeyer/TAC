package fr.leftac.listify.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SpotifyService {
    String V1 = "v1/";

    @GET(V1 + "search")
    Call<ResponseBody> track(@Header("Authorization") String token, @Query("q") String search, @Query("type") String type);
}
