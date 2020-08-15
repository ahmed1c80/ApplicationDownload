package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FServers;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Db_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.MainActivity;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;

import java.util.List;

public class MyServiceDownload extends Service {
    Db_Download db_download;
public static final String TAG="MyServiceDownload";

    NotificationManager notificationManager;
    NotificationCompat.Builder notificationBuilder;
    String CHANNEL_ID = "ForegroundServiceChannel";
    Intent intent_notification;
    @Override
    public void onCreate() {
        super.onCreate();
        db_download=new Db_Download(this);

        try {
/*
            Intent inte=new Intent(Intent.ACTION_VIEW);
            Uri parse = Uri.parse(path);
            inte.setAction("android.intent.action.VIEW");
            inte.setDataAndType(parse, typ);*/
             intent_notification = new Intent(this, MainActivity.class);

            intent_notification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent_notification,
                    PendingIntent.FLAG_ONE_SHOT);

             notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "main_notification_channel_name",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(mChannel);
            }
      notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    //.setColor(ContextCompat.getColor(, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_download_gold)
                    // .setLargeIcon(largeIcon(context))

                    //  .setStyle(new NotificationCompat.BigTextStyle().bigText(
                    ///        context.getString(R.string.charging_reminder_notification_body)))
                  //  .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    //.addAction(drinkWaterAction(context))
                    //  .addAction(ignoreReminderAction(context))
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
            }


        } catch (Exception e) {
            Log.e("Notification", e.getMessage());

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       super.onStartCommand(intent, flags, startId);

        Log.e(TAG,"onStartCommand");
        try {
           // if (intent.getAction()) {

                List<ItemDownload> itemD = db_download.getFileDownload(intent.getExtras().getLong("H_Code"));
                if (itemD.size() > 0) {
                    beginDownload big=new beginDownload(this,itemD.get(0));
                big.start();
                }
            //}
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

class beginDownload extends Thread{

        ItemDownload itemDownload;
        Context context;
    public beginDownload(Context cont,ItemDownload item) {
        Log.e(TAG,"beginDownload");
        itemDownload = item;
        context = cont;
    }

    @Override
    public void run() {
        super.run();
    try {

        long Total_Download=0;
        long Byte_Download=0;
        String Type_Download="";
        int State_Download=0;
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            // using query method
            boolean finishDownload = false;
            int progress;
            while (!finishDownload) {
                Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(itemDownload.ID_DM));
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                    try{
                        Total_Download=cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        Byte_Download=cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        Type_Download=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                        State_Download=0;

                    }catch(Exception e){

                    }
                    switch (status) {
                        case DownloadManager.STATUS_FAILED: {
                            Log.e(TAG,"STATUS_FAILED");
                            State_Download=Template.STATUS_FAILED;
                            db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

                            //  ed_src.append("STATUS_FAILED ");
                       //     db_download.UpdateStateDownloadWithFailed(Template.STATUS_FAILED, cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)), itemDownload.H_Code);
                            finishDownload = true;
                            break;
                        }
                        case DownloadManager.STATUS_PAUSED:
                            Log.e(TAG,"STATUS_PAUSED");
                            State_Download=Template.STATUS_PAUSED;
                            db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

                            break;
                        case DownloadManager.STATUS_PENDING:
                            Log.e(TAG,"STATUS_PENDING");
                            State_Download=Template.STATUS_PENDING;
                            db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

                            break;
                        case DownloadManager.STATUS_RUNNING: {
                           // Log.e(TAG,"STATUS_RUNNING");
                            State_Download=Template.STATUS_RUNNING;
                            db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

                            sendNotification(
                                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)),
                                    itemDownload.N_F,
                                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)),
                                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)),

                                    (int) itemDownload.ID_DM,
                                    (int)Byte_Download,
                                    (int) Total_Download);
                            //  prgDialog.setTitle("جاري التنزيل...");
                            final long total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                          Total_Download=total;
                            if (total >= 0) {
                                final long downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                              Byte_Download=downloaded;
                          //      db_download.SetAllsizedown(downloaded, total, itemDownload.H_Code);
                                try {
                                    sendNotification(
                                            cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)),
                                          itemDownload.N_F,
                                          cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)),
                                      cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)),

                                            (int) itemDownload.ID_DM,
                                            (int) cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)),
                                            (int) cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));

                        /*if (prgDialog.isShowing()) {
                            prgDialog.setMax((int) total);
                            prgDialog.setProgress((int) downloaded);
                        }*/
                                } catch (Exception e) {
                                }
                                progress = (int) ((downloaded * 100L) / total);
                                // ed_src.append("progress " + progress);
                                // if you use downloadmanger in async task, here you can use like this to display progress.
                                // Don't forget to do the division in long to get more digits rather than double.
                                //  publishProgress((int) ((downloaded * 100L) / total));
                            }
                            break;
                        }
                        case DownloadManager.STATUS_SUCCESSFUL:
                            Log.e(TAG,"STATUS_SUCCESSFUL");
                            State_Download=Template.STATUS_SUCCESSFUL;
                            db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

                            progress = 100;
                            // if you use aysnc task
                            // publishProgress(100);
                            db_download.SetAllsizedown(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)), cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)), itemDownload.H_Code);

                            finishDownload = true;
                            db_download.UpdateStateDownload(Template.STATUS_SUCCESSFUL, itemDownload.H_Code);
                            //Toast.makeText(this, "Download Completed", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
               // db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,itemDownload.H_Code);

            }


    } catch (Exception e) {
            //     ed_src.append("\nDowm\n"+e.getMessage());
        }


    }

        private void sendNotification (String title,
                                       String nam,
                                       String path,
                                       String typ,

                                       int notificationId,
        int ProgSiz, int ProMax) {
        try{
              notificationBuilder.setContentTitle(nam)
                    .setProgress(ProMax, ProgSiz, false)
                    .setContentText("");
        intent_notification.putExtra("Id_Down", notificationId);
            //  Log.e("Notification",title+"/"+messageBody+"/"+notificationId);
            notificationManager.notify(notificationId, notificationBuilder.build());

        }catch (Exception e){

        }
    }


    private void sendNotificationComplete(String title,
                                          String nam,
                                          String path,
                                          String typ,

                                          int notificationId,
                                          int ProgSiz, int ProMax){
        String CHANNEL_ID = "ForegroundServiceChannel";
        //  Log.e("Notification",title+"/"+messageBody+"/"+notificationId);
        try {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "main_notification_channel_name",
                        NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    //.setColor(ContextCompat.getColor(, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_download_gold)
                    // .setLargeIcon(largeIcon(context))
                    .setContentTitle(ProMax + "/" + ProgSiz)
                    .setProgress(ProMax, ProgSiz, false)
                    .setContentText("")
                    //  .setStyle(new NotificationCompat.BigTextStyle().bigText(
                    ///        context.getString(R.string.charging_reminder_notification_body)))
                    //.setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    //.addAction(drinkWaterAction(context))
                    //  .addAction(ignoreReminderAction(context))
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
            }
            notificationManager.notify(notificationId, notificationBuilder.build());

        } catch (Exception e) {
            Log.e("Notification", e.getMessage());

        }
    }



    }
}
