package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.Html;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Activity_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanUrl extends AsyncTask<String,Integer,String> {
    public static final String TAG="ScanUrl";
    ItemDownload itemDownload=new ItemDownload();
    String res_web = "";
    String NameF ="";
    InputStream is_web;
    private Activity activity_download;
    OnResutDownload ResDown;
    public ScanUrl(Activity load_Html, OnResutDownload onRes) {
        activity_download= load_Html;
        itemDownload=new ItemDownload();
        ResDown=onRes;
        ResDown.OnStartDown("Start Scan");
    }



    /* Access modifiers changed, original: protected */
    @Override
    public void onPreExecute() {

        super.onPreExecute();
    }

    /* Access modifiers changed, original: protected */
    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String doInBackground(String[] strArr) {
        Exception exception;
        String[] strArr2 = strArr;

        ResDown.OnStartDown("doInBackground");
        InputStream fileInputStream;
        if (strArr2[0].startsWith("http")) {
             /*   StringBuffer stringBuffer;
                StringBuffer stringBuffer2;
                StringBuffer stringBuffer3;*/
            try {
                URL url2 = new URL(strArr2[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
                httpURLConnection.connect();
              //  httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                is_web = httpURLConnection.getInputStream();
long lenghtOfFile=httpURLConnection.getContentLength();
ResDown.OnSizeFileDown((int)lenghtOfFile);
try {
   // ResDown.OnMessageDown("getResponseCode "+httpURLConnection.getResponseCode());
  //  ResDown.OnMessageDown("getConnectTimeout "+httpURLConnection.getConnectTimeout());
   // ResDown.OnMessageDown("getContentLength "+httpURLConnection.getContentLength());
 //   ResDown.OnErroreDown("getContentLengthLong "+httpURLConnection.getContentLengthLong());
 //   ResDown.OnMessageDown("getDate "+httpURLConnection.getDate());
  //  ResDown.OnMessageDown("getRequestProperty  "+httpURLConnection.getRequestProperty("Accept-Encoding"));
  //  ResDown.OnMessageDown("getRequestProperty 'Content-Length'  "+httpURLConnection.getRequestProperty("Content-Length"));
  //  ResDown.OnErroreDown("getRequestProperties   "+httpURLConnection.getRequestProperties().toString());//("Content-Length"));
    ResDown.OnMessageDown("getHost   "+httpURLConnection.getURL().getHost());//("Content-Length"));
    itemDownload.setHost_F(httpURLConnection.getURL().getHost());
}catch (Exception e){
    ResDown.OnErroreDown("Err  "+e.getMessage());
}

                byte[] byt=new byte[1024];//inp.available()];
                // long total = 0;
                //int progress = 0;
                int count;
                  /*  while ((count = is_web.read(byt)) != -1)
                    {
                        total += count;
                        String str=new String(byt+"");
                        activity_download.AddText(str);
                       // int progress_temp = (int) total * 100 / ((int)lenghtOfFile);

                    //    out.write(byt, 0, count);
                        try{
                          //  sleep(100);
                        }catch(Exception e){
                            //ResDown.OnErroreDown("dd "+e.getMessage());
                        }
                    }*/

                NameF=strArr2[0].replace("/","").replace(".","").replace("\\","")

                        +".html";
                //NameF=strArr2[0].hashCode()+".html";
                FileOutputStream out=new FileOutputStream(Template.PathMyFolderHtml+"/"+NameF);
                itemDownload.setF_N_S(NameF);
                //  byte[] byt=new byte[1024];//inp.available()];
                long total = 0;
                int progress = 0;
                while ((count = is_web.read(byt)) != -1)
                {
                    total += count;
                    int progress_temp = (int) total * 100 / ((int)lenghtOfFile);
ResDown.OnProgressDown((int)total);
                    out.write(byt, 0, count);

                    try{
                        // sleep(100);
                    }catch(Exception e){
                  ResDown.OnErroreDown("dd "+e.getMessage());
                    }
                }
                try{
                  return "ok";
                }catch(Exception e){
                    ResDown.OnErroreDown("dd "+e.getMessage());
                }
            } catch (Exception e) {
                ResDown.OnErroreDown("dd "+e.getMessage());
                exception = e;
            }


        }
        return null;
    }
   /* @Override
    public void onPostExecute(String str) {
        try{

            ResDown.OnCompleteDown("dd "+str);
    } catch (Exception e) {
        ResDown.OnErroreDown("dd "+e.getMessage());

    }
    }
    */
        @Override
        public void onPostExecute(String str) {
if(str!=null) {
    StringBuffer stringBuffer;
    // StringBuffer stringBuffer2;
    StringBuilder stringBuilder = new StringBuilder();
    String str2 = str;

    try {
        ResDown.OnMessageDown(NameF + "\n\n");
        int count;
        FileInputStream inp = new FileInputStream(Template.PathMyFolderHtml + "/" + NameF);
        long lenghtOfFile = new File(Template.PathMyFolderHtml + "/" + NameF).length();
        ResDown.OnSizeFileDown((int) lenghtOfFile);

        byte[] byt = new byte[1024];//inp.available()];
        long total = 0;
        int progress = 0;
        while ((count = inp.read(byt)) != -1) {
            String stbyte = new String(byt);
            stringBuilder.append(stbyte + "\n");
        }
        res_web = stringBuilder.toString();
        ResDown.OnMessageDown("res_web " + stringBuilder.toString().length() + "");
        //   ResDown.OnErroreDown("res_web " +stringBuilder.toString()+"");
        is_web.close();
    } catch (Exception e2) {
        ResDown.OnMessageDown("Read " + e2.getMessage());
    }
    Matcher matcher = Pattern.compile("data-store=\"&#123;&quot;videoID&quot").matcher(res_web);
    //  TextView textView;
    if (matcher.find()) {
        int lastIndexOf = res_web.lastIndexOf("data-store=\"&#123;&quot;videoID&quot");
        res_web = res_web.substring(lastIndexOf + 12);

        itemDownload.setCode_F(lastIndexOf + "\n" + Html.fromHtml(this.res_web.substring(0, this.res_web.indexOf("\""))));
        try {
            String jsonFor = Html.fromHtml(this.res_web.substring(0, this.res_web.indexOf("\""))).toString();
            //ResDown.OnMessageDown("\n\n" + jsonFor.replace(",", ",\n") + "\n\n\n");

            JSONObject jSONObject = new JSONObject(jsonFor);
            // JSONObject jSONObject = new JSONObject(Html.fromHtml(this.res_web.substring(0, this.res_web.indexOf("\"")).replace("& quot;","\"").replace(";","")).toString());
            String srcv = jSONObject.getString("src");
            String IdV = jSONObject.getString("videoID");
            ResDown.OnMessageDown(srcv);
            itemDownload.setP_F(srcv);
            itemDownload.setN_F("Facebook_"+IdV+".mp4");
            ResDown.OnFindUrl(itemDownload);

            //this.this$0.txt_result.setText(string);
            //  StringBuffer stringBuffer6 = r21;
            // stringBuffer = new StringBuffer();
            //  this.this$0.db_files.insert(string, stringBuffer6.append(string.hashCode()).append("").toString(), "0", "0");


            //Intent intent2 = new Intent(activity_download, Class.forName("com.riush.riush_download.DownloadService"));
            //  ComponentName startService = activity_download.startService(intent2);
               /* } catch (Exception e3) {
                    ResDown.OnErroreDown(e3.getMessage());*/
            //  Throwable th = e3;
            // NoClassDefFoundError noClassDefFoundError = r21;
            // NoClassDefFoundError noClassDefFoundError2 = new NoClassDefFoundError(th.getMessage());
            // throw noClassDefFoundError;
        } catch (Exception e22) {
            itemDownload.setER_DM(e22.getMessage());
            ResDown.OnMessageDown(TAG + "e22" + e22.getMessage());
            //  Exception exception2 = e22;
        }

    } else {
        // textView = this.this$0.txt_result;

        //  stringBuffer = new StringBuffer();
        ResDown.OnErroreDown(TAG + matcher.find() + "فشل الحصول على رابط الفيديو");
        //textView.setText(stringBuffer.append(matcher.find()).append("").toString());
    }
}else{
    ResDown.OnErroreDown("تأكد من اتصال الإنترنت");
}
            super.onPostExecute(str);
        }





}
