package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FServers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Activity_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;


public class CBWatcherService extends Service {

    private WindowManager windowManager;
//   private ImageView floatingFaceBubble;
    private View btn_down;
Intent intent_down;
    String ClipboardCacheFolderPath="/data/data/com.edg_tech.facebook.whatsapp.instagram.twitter.free";
    public static final String TAG = "CBWatcherService";
    private OnPrimaryClipChangedListener listener = new OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            performClipboardCheck();
        }
    };

    @Override
    public void onCreate() {

        ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).addPrimaryClipChangedListener(listener);

        // add a floatingfacebubble icon in window
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        File folder = new File(ClipboardCacheFolderPath);
        // ClipboardCacheFolderPath is a predefined constant with the path
        // where the clipboard contents will be written

        if (!folder.exists()) { folder.mkdir(); }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (btn_down != null) {

                windowManager.removeView(btn_down);
                this.stopSelf();
            }
        }catch (Exception e){}
    }



    private void performClipboardCheck() {
        ClipboardManager cb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (cb.hasPrimaryClip()) {
            ClipData cd = cb.getPrimaryClip();
            Log.e(TAG,cd.getDescription().getMimeType(0));
            Log.e(TAG,cd.getItemAt(0).getText()+"");

            if (cd.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                try {
                  /*  File folder = new File(ClipboardCacheFolderPath);
                    if (!folder.exists()) { folder.mkdir(); }
                    Calendar cal = Calendar.getInstance();
                    String newCachedClip =
                            cal.get(Calendar.YEAR) + "-" +
                                    cal.get(Calendar.MONTH) + "-" +
                                    cal.get(Calendar.DAY_OF_MONTH) + "-" +
                                    cal.get(Calendar.HOUR_OF_DAY) + "-" +
                                    cal.get(Calendar.MINUTE) + "-" +
                                    cal.get(Calendar.SECOND);

                    // The name of the file acts as the timestamp (ingenious, uh?)
                    File file = new File(ClipboardCacheFolderPath +"/"+ newCachedClip);
                    file.createNewFile();
                    BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
                    bWriter.write((cd.getItemAt(0).getText()).toString());
                    bWriter.close();
                    Log.e(TAG, file.getAbsolutePath());*/
                    String pathf=cd.getItemAt(0).getText().toString();
                    if(pathf.contains("facebook") ||pathf.contains("fb")) {
                        addViewDownload(pathf);
                    }
                }
                catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void addViewDownload(String path){

        try {
            if (btn_down != null) {

                windowManager.removeView(btn_down);

            }
        }catch (Exception e){}

        intent_down = new Intent(this, Activity_Download.class);
        intent_down.putExtra(Intent.EXTRA_TEXT,path);
        LayoutInflater infalter = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        btn_down = infalter.inflate(R.layout.lay_window_download, null);

        //floatingFaceBubble = new ImageView(this);
        //a face floating bubble as imageView
        //    floatingFaceBubble.setImageResource(R.drawable.ic_download_black);
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        //here is all the science of params
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }
        final WindowManager.LayoutParams myParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        myParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        myParams.x = 0;
        myParams.y = 100;
        try{

            windowManager.addView(btn_down, myParams);
            //for moving the picture on touch and slide

            btn_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        startActivity(intent_down);
                        if (btn_down != null) {

                            windowManager.removeView(btn_down);

                        }
                    }catch (Exception e){}
                }
            });

            btn_down.setOnTouchListener(new View.OnTouchListener() {
                WindowManager.LayoutParams paramsT = myParams;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private long touchStartTime = 0;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //remove face bubble on long press
                    if(System.currentTimeMillis()-touchStartTime> ViewConfiguration.getLongPressTimeout() && initialTouchX== event.getX()){
                        windowManager.removeView(btn_down);
                        stopSelf();
                        return false;
                    }
                    switch(event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            touchStartTime = System.currentTimeMillis();
                            initialX = myParams.x;
                            initialY = myParams.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            myParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                            myParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(v, myParams);
                            break;
                    }
                    return false;
                }
            });

            btn_down.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    try {

                        if (btn_down != null) {

                            windowManager.removeView(btn_down);

                        }
                    }catch (Exception e){}
                    return false;
                }
            });

        } catch (Exception e){
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }


    }
}