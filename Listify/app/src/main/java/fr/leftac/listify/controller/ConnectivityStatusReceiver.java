package fr.leftac.listify.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import fr.leftac.listify.model.api.TokenManager;

public class ConnectivityStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        Log.e("connectivity", activeNetworkInfo == null ? "pas internet" : "internet");
        if (activeNetworkInfo != null) {
            if (!TokenManager.isTokenValid()) TokenManager.generateToken();
        }
    }

}
