package fr.leftac.listify.controllers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.leftac.listify.MainActivity;
import fr.leftac.listify.api.services.SpotifyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenManager {
    // TODO: Hide this credientials constant in a git ignored file
    static final String CREDENTIALS_B64 = "M2E0YjNlMzkxYzZjNDU4ZTg1MjNlNzAxYzViZWNiZDg6MzVjZDRmYzA3MDRkNGJmYmJkZTA2MWU3YzdlYTQ4Mzg=";
    public static String token;

    public static void generateToken(MainActivity act) {
        // Create the Retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                // This URL is used only with this API
                // TODO: Hide this URL in a git ignored file
                .baseUrl("https://accounts.spotify.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Our service
        SpotifyService api = retrofit.create(SpotifyService.class);

        // The body we sent
        String basicAuth = "Basic " + CREDENTIALS_B64;

        // Preparing the call
        Call<Object> call = api.token("client_credentials", basicAuth);

        call.enqueue(new Callback() {
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

                Log.i("token", "le token a été généré : " + TokenManager.token);

                // Update the textview
                act.updateTokenField(TokenManager.token);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Log.i("token", "une erreur est survenue");

                // Update the textview
                act.updateTokenField("Aucun token généré");
            }
        });

    }

}
