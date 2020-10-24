package fr.leftac.listify.model.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenManager {
    // TODO: Hide this credientials constant in a git ignored file
    static final String CREDENTIALS_B64 = "M2E0YjNlMzkxYzZjNDU4ZTg1MjNlNzAxYzViZWNiZDg6MzVjZDRmYzA3MDRkNGJmYmJkZTA2MWU3YzdlYTQ4Mzg=";
    static final String BASE_URL = "https://accounts.spotify.com/api/";
    private static final String TAG = TokenManager.class.getSimpleName();
    static String token;

    public static void generateToken() {

        // Our service
        SpotifyApi api = new Retrofit.Builder()
                // This URL is used only with this API
                // TODO: Hide this URL in a git ignored file
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SpotifyApi.class);

        // The body we sent
        String basicAuth = "Basic " + CREDENTIALS_B64;

        api.token("client_credentials", basicAuth).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                // Initialize a parser
                JsonParser parser = new JsonParser();
                // Parse the response
                JsonObject responseBody = parser.parse(new Gson().toJson(response.body())).getAsJsonObject();
                // Extract the token
                JsonElement token = responseBody.get("access_token");

                // Update the token value

                TokenManager.token = token.toString().replaceAll("\"", "");

                Log.i(TAG, "le token a été généré : " + TokenManager.getToken());

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "une erreur est survenue");

            }
        });

    }

    public static String getToken() {
        return token;
    }

}
