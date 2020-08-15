package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.OnResutDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.ScanUrl;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FServers.MyServiceDownload;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;


public class Activity_Download2  extends AppCompatActivity implements OnResutDownload {
    ProgressBar progbar;
    String url_src="";
    TextView ed_src;
    ProgressDialog prgDialog;
    private long downloadID;
    Db_Download db_download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_download);
        db_download=new Db_Download(this);
        ed_src=(TextView)findViewById(R.id.ed_source);
        progbar=(ProgressBar)findViewById(R.id.progress);
        ed_src.setMovementMethod(new ScrollingMovementMethod());
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(true);
        prgDialog.setMessage("جاري فحص الملف ...");
        prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.setProgress(0);
        prgDialog.setMax(100);
        // prgDialog.setCancelable(false);
        // registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        try{
            url_src= getIntent().getStringExtra(Intent.EXTRA_TEXT);//"android.intent.extra.TEXT");
            ed_src.setText(url_src);
            GetDexterPermission();
        }catch(Exception e){}
        GetDexterPermission();
    }




    public void  LoadFiles(){
        File fil=new File(Template.PathMyFolderHtml);
        if(!fil.exists()){
            fil.mkdirs();
        }
        new ScanUrl(this,this).execute(url_src);
        progbar.setVisibility(View.VISIBLE);
        // prgDialog.show();
    }

    public void  GetDexterPermission(){
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if(multiplePermissionsReport.areAllPermissionsGranted()){
                    LoadFiles();
                    //  LoadFiles(Environment.getExternalStorageDirectory().getAbsolutePath());
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();


    }





    private void AddText(String str) {
        ed_src.append(str);
    }

    @Override
    public void OnProgressDown(int prog) {
        try{
            prgDialog.setProgress(prog);
            // ed_src.append("\nSizeF"+prog);
            //  prog_down.setProgress(prog);
        }catch (Exception e){
            ed_src.append("\nkkd"+e.getMessage());
        }
    }

    @Override
    public void OnCompleteDown(String prog) {
        try{
            // Toast.makeText(HomeFragment.this,prog,Toast.LENGTH_LONG).show();
            prgDialog.dismiss();
            ed_src.append("\n"+prog);
        }catch (Exception e){
            ed_src.append("\nssd"+e.getMessage());
        }
        try{
            //  LoadFiles(Environment.getExternalStorageDirectory().getAbsolutePath());
            //  adb_file.UpdateData(itemF);
        }catch (Exception e){}
        /// adb_file.UpdateData(itemF);
        //prog_down.setMax(prog);

    }

    @Override
    public void OnStartDown(String prog) {
        try{
            ed_src.append(Html.fromHtml("<br><font color=#4477cc>"+prog+"</font><br>"));
            //   prog_down.setMax(prog);
        }catch (Exception e){
            ed_src.append("\nlld"+e.getMessage());
        }
    }

    @Override
    public void OnErroreDown(String prog) {
        try{
            ed_src.append(Html.fromHtml("<font color=#cc7744>"+prog+"</font><br>"));
            //   prog_down.setMax(prog);
        }catch (Exception e){
            ed_src.append("\n00d "+e.getMessage());
        }
    }
    @Override
    public void OnMessageDown(String prog) {
        try{
            ed_src.append(Html.fromHtml("<font color=#4477cc>"+prog+"</font><br>"));
            //   prog_down.setMax(prog);
        }catch (Exception e){
            ed_src.append("\n00d "+e.getMessage());
        }
    }

    @Override
    public void OnSizeFileDown(int prog) {
        try{

            prgDialog.setMax(prog);
            ed_src.append("\nSizeF"+prog);
            // prog_down.setMax(prog);
        }catch (Exception e){
            ed_src.append("\nrrd "+e.getMessage());
        }
    }


    @Override
    public void OnFindUrl(ItemDownload itemDownload) {
        ed_src.append("\n\n\nFile Path "+itemDownload.Code_F);
        // DownVideos(prog);
        beginDownload(itemDownload);
    }

    public  void DownVideos(String PathF){
        File file=new File(getExternalFilesDir(null),"Dummy");
       /*
       Create a DownloadManager.Request with all the information necessary to start the download
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(PathF))
                    .setTitle("Dummy File")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
            DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
            ed_src.append("StartDownload" +
                    "\n" +
                    PathF +
                    "\n\n"+
                    downloadID);
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(Activity_Download2.this, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(onDownloadComplete);
    }
/*
public File createDocumentFile(String nam,Activity ac){

}
*/


    private void beginDownload(ItemDownload itemDownload){
        try {
            long CodeF = System.currentTimeMillis();
            ed_src.append("\nStartDownload ");
            String namf = itemDownload.N_F;
            //String namf = itemDownload.P_F.hashCode() + ".mp4";
            itemDownload.setN_F(namf);

            String url = itemDownload.P_F.replace(" ", "").replace("\\", "");//"http://speedtest.ftp.otenet.gr/files/test10Mb.db";
            itemDownload.setP_F(url);
            //String fileName = url.substring(url.lastIndexOf('/') + 1);
            //fileName = fileName.substring(0,1).toUpperCase() + fileName.substring(1);

            File file = new File(Template.PathMyFolderDownload + "/" + namf);// Util.createDocumentFile(fileName, this);

            DownloadManager.Request request = null;// Set if download is allowed on roaming network
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                request = new DownloadManager.Request(Uri.parse(url))
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)// Visibility of the download Notification
                        .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                        .setTitle(itemDownload.getP_F())// Title of the Download Notification
                        .setDescription("Downloading")// Description of the Download Notification
                        .setRequiresCharging(false)// Set if charging is required to begin the download
                        .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                        .setAllowedOverRoaming(true);
            } else {
                request = new DownloadManager.Request(Uri.parse(url))
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)// Visibility of the download Notification
                        .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                        .setTitle(itemDownload.getP_F())// Title of the Download Notification
                        .setDescription("Downloading")// Description of the Download Notification

                        .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                        .setAllowedOverRoaming(true);
            }
            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
            itemDownload.setID_DM(downloadID);
            itemDownload.setH_Code(CodeF);
            db_download.insertF(itemDownload);
            Intent serv=new Intent(this, MyServiceDownload.class);
            serv.putExtra("H_Code",CodeF);
            startService(serv);
            Toast.makeText(this,"جاري التحميل",Toast.LENGTH_LONG).show();
            finish();

        }catch (Exception e){
            Log.e("Activity_download",e.getMessage());
        }
    }



}
