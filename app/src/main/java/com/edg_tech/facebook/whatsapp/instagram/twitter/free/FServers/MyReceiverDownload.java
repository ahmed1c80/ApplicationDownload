package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FServers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiverDownload extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e("MyReceiverDownload",intent.toString());
        //  throw new UnsupportedOperationException("Not yet implemented");
    }
}
