package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.OnResutDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FServers.MyServiceDownload;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;


public class Activity_Download  extends AppCompatActivity implements OnResutDownload {
    String url_src="";
    ItemDownload itemD;
    TextView ed_src;
    ProgressBar progress;
    //ProgressDialog prgDialog;
    private long downloadID;
    Db_Download db_download;
    public final static  String TAG="Activity_Download";
    TextView btn_download;
    VideoView vid;
    LinearLayout lay_vid;
    MediaController medcontrols;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_download);
        db_download=new Db_Download(this);
        btn_download=(TextView)findViewById(R.id.btn_download);
        vid=(VideoView)findViewById(R.id.vid);
        lay_vid=(LinearLayout)findViewById(R.id.lay_vid);
         medcontrols=new MediaController(this);
        medcontrols.setAnchorView(vid);
        vid.setMediaController(medcontrols);

        ed_src=(TextView)findViewById(R.id.ed_source);
        progress=(ProgressBar) findViewById(R.id.progress);
        ed_src.setMovementMethod(new ScrollingMovementMethod());
       /* prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(true);
        prgDialog.setMessage("ط¬ط§ط±ظٹ ظپط­طµ ط§ظ„ظ…ظ„ظپ ...");
        prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prgDialog.setProgress(0);
        prgDialog.setMax(100);
      */ // prgDialog.setCancelable(false);
        //  registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        try{
            url_src= getIntent().getStringExtra(Intent.EXTRA_TEXT);//"android.intent.extra.TEXT");
            ed_src.setText(url_src);
            // new ScanUrl(this,this).execute(url_src);
            connectWeb();
            progress.setVisibility(View.VISIBLE);//.show();
        }catch(Exception e){}
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               beginDownload(itemD);
            }
        });
    }
    private void AddText(String str) {
        ed_src.append(str);
    }


    @Override
    public void OnProgressDown(int prog) {
        try{
            //prgDialog.setProgress(prog);
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
            progress.setVisibility(View.GONE);
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

            //prgDialog.setMax(prog);
            ed_src.append("\nSizeF"+prog);
            // prog_down.setMax(prog);
        }catch (Exception e){
            ed_src.append("\nrrd "+e.getMessage());
        }
    }


    @Override
    public void OnFindUrl(ItemDownload itemDownload) {
        itemD=itemDownload;
        ed_src.append("\n\n\nFile Path "+itemDownload.Code_F);
        // DownVideos(prog);
      startVideos();
      ed_src.setVisibility(View.GONE);
    }

    private void startVideos() {
        try {
            lay_vid.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            String url = itemD.P_F.replace(" ", "").replace("\\", "");//"http://speedtest.ftp.otenet.gr/files/test10Mb.db";
medcontrols.setAnchorView(vid);
            vid.setVideoPath(url);
            vid.start();
        }catch (Exception e){}

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

    /*  private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
          @Override
          public void onReceive(Context context, Intent intent) {
              //Fetching the download id received with the broadcast
              long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
              //Checking if the received broadcast is for our enqueued download by matching download id
              if (downloadID == id) {
                  Toast.makeText(Activity_Download.this, "Download Completed", Toast.LENGTH_SHORT).show();
              }
          }
      };*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregisterReceiver(onDownloadComplete);
    }
/*
public File createDocumentFile(String nam,Activity ac){

}
*/

    public  void connectWeb(){
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = url_src;//"https://www.google.com";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            ///ed_src.setText("Response is: "+ response.substring(0,500));
                            FindURl(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {


                        ed_src.setText(url_src);
                        ed_src.append("That didn't work!");
                        ed_src.append(error.getMessage().toString());
                        progress.setVisibility(View.GONE);
                    } catch (Exception e) {
                    }
                    // prgDialog.dismiss();
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
        } catch (Exception e) {
        }
    }



    public  void FindURl(String res_web){

        ItemDownload itemDownload=new ItemDownload();
        Matcher matcher = Pattern.compile("data-store=\"&#123;&quot;videoID&quot").matcher(res_web);
        //  TextView textView;
        if (matcher.find()) {
            int lastIndexOf = res_web.lastIndexOf("data-store=\"&#123;&quot;videoID&quot");
            res_web = res_web.substring(lastIndexOf + 12);

            // itemDownload.setCode_F(lastIndexOf + "\n" + Html.fromHtml(res_web.substring(0, res_web.indexOf("\"")).trim()));
            try {
                String jsonFor = Html.fromHtml(res_web.substring(0, res_web.indexOf("\"")).trim()).toString();//.replace("&quot;","\"").replace("& quot;","\"").replace("&quot;","")).toString();
                this.OnMessageDown("\n\n" + jsonFor.replace(",", ",\n").replace("/^\\s+|\\s+$/gm","") + "\n\n\n");

                JSONObject jSONObject = new JSONObject(jsonFor);
                // JSONObject jSONObject = new JSONObject(Html.fromHtml(this.res_web.substring(0, this.res_web.indexOf("\"")).replace("& quot;","\"").replace(";","")).toString());
                String srcv = jSONObject.getString("src");
                String IdV = jSONObject.getString("videoID");
                this.OnMessageDown(srcv);
                itemDownload.setP_F(srcv);
                itemDownload.setN_F("Facebook_"+IdV+".mp4");
                this.OnFindUrl(itemDownload);


            } catch (Exception e22) {
                itemDownload.setER_DM(e22.getMessage());
                this.OnMessageDown(TAG + "e22" + e22.getMessage());
                //  Exception exception2 = e22;
            }

        } else {
            // textView = this.this$0.txt_result;

            //  stringBuffer = new StringBuffer();
            this.OnErroreDown(TAG + matcher.find() + "ظپط´ظ„ ط§ظ„ط­طµظˆظ„ ط¹ظ„ظ‰ ط±ط§ط¨ط· ط§ظ„ظپظٹط¯ظٹظˆ");
            //textView.setText(stringBuffer.append(matcher.find()).append("").toString());
        }
    }





    private void beginDownload(ItemDownload itemDownload){
        try {
            long CodeF=System.currentTimeMillis();
            ed_src.append("\nStartDownload ");
            String namf = itemDownload.N_F;
            //String namf = itemDownload.P_F.hashCode() + ".mp4";
            itemDownload.setN_F(namf);

            String url = itemDownload.P_F.replace(" ", "").replace("\\","");//"http://speedtest.ftp.otenet.gr/files/test10Mb.db";
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
            Log.e(TAG,"StartDownLoad");
            Intent intents=new Intent(this, MyServiceDownload.class);
            intents.putExtra("H_Code",CodeF);
            startService(intents);
            finish();
        }catch (Exception e){
            ed_src.append("\nDowm\n"+e.getMessage());
        }
    }


    public void pasteClick() {
        ClipData clipData;
        int i;
        String str;
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            clipData = clipboardManager.getPrimaryClip();
            i = clipData.getItemCount();
        } else {
            clipData = null;
            i = 0;
        }
        String str2 = "error";
        if (i > 0) {
            try {
                str = clipData.getItemAt(0).getText().toString();
                this.OnMessageDown(str);
            } catch (Exception e) {
                this.OnErroreDown(e.getMessage());
                //new ShowDialog().show(this, getString(R.string.emptyclipboard), getString(R.string.notalink), str2);
                e.printStackTrace();
                str = "";
            }
            String str3 = "http";
            if (str.toLowerCase().contains(str3)) {
                // this.search_main.setText(str.substring(str.indexOf(str3)));
                //new ShowDialog().show(this, getString(R.string.success), getString(R.string.pasted), Param.SUCCESS);
                return;
            }
            // new ShowDialog().show(this, getString(R.string.error), getString(R.string.cantfindtweet), str2);
            return;
        }
        //new ShowDialog().show(this, getString(R.string.error), getString(R.string.nopastedtext), str2);
    }

}
