package fr.leftac.listify.api;

import fr.leftac.listify.models.AuthenticationResponse;
import fr.leftac.listify.models.TokenBody;
import fr.leftac.listify.models.TracksPager;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpotifyService {
    String V1 = "v1/";

    @GET(V1 + "search")
    Call<TracksPager> track(@Header("Authorization") String token, @Query("q") String search, @Query("type") String type);

    @POST("api/token")
    Call<AuthenticationResponse> token(@Header("Authorization") String key, @Body TokenBody body);

}
