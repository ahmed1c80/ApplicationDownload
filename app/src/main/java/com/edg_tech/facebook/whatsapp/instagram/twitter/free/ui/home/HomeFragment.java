package com.edg_tech.facebook.whatsapp.instagram.twitter.free.ui.home;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Activity_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Activity_Player;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Db_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FAdapter.Adapter_Files;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.OnResutDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemFiles;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.MainActivity;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.SaveFileDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.ThreadSaveImageThem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.riush.rdialog.RssFeedProvider;
import com.riush.rdialog.RssItem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener, Adapter_Files.OnFileListener , OnResutDownload {
    Db_Download db_download;
    int ProgSize=1;
    private HomeViewModel homeViewModel;

    @Override
    public void OnFileClick(int position)
    {
        try {

            File f=new File(itemF.get(position).PathF);
            Intent inte=new Intent(getActivity(), Activity_Player.class);
           // Uri parse = Uri.parse(f.toString());
         inte.putExtra("Url_Media","file://"+f.toString());
            inte.putExtra("TypeF",itemF.get(position).TypeString);
         getActivity().startActivity(inte);
            //inte.setDataAndType(parse, itemF.get(position).TypeString);
            /*ItemDownload itemd = new ItemDownload();
            long cod = System.currentTimeMillis();
            itemd.setP_F(itemF.get(position).PathF);
            itemd.setN_F(itemF.get(position).NameF);
            itemd.setType_F(itemF.get(position).TypeString);
            itemd.setSize_F(itemF.get(position).SizeF);

            itemd.setH_Code(cod);
            db_download.insertF(itemd);
            SaveFileDownload savf = new SaveFileDownload(getActivity(), itemF.get(position), this, cod);
            savf.start();


            prgDialog.show();*/
        }catch (Exception e){}
        // RiushMessageNotification.notify(getContext(),"ahmed",2);
        // TODO: Implement this method
    }

    @Override
    public void OnFileOpen(int position, String pathd)
    {
        File f=new File(itemF.get(position).PathF);
        Intent inte=new Intent(getActivity(), Activity_Player.class);
        // Uri parse = Uri.parse(f.toString());
        inte.putExtra("Url_Media","file://"+f.toString());
        inte.putExtra("TypeF",itemF.get(position).TypeString);
        getActivity().startActivity(inte);
        /*
        File f=new File(pathd);
        Intent inte=new Intent(Intent.ACTION_VIEW);
        Uri parse = Uri.parse(f.toString());
        inte.setAction("android.intent.action.VIEW");
        inte.setDataAndType(parse, itemF.get(position).TypeString);
        //inte.setClassName("com.riush.myplayer", "com.riush.myplayer.player2");
        getActivity().startActivity(inte);*/
        //  Toast.makeText(getActivity(),parse.toString(),Toast.LENGTH_LONG).show();
        // TODO: Implement this method
    }

    ThreadSaveImageThem thredImage;

    ProgressBar prog_down;
    TextView txt_down;
    List<ItemFiles> itemF;
    Adapter_Files adb_file;
    RecyclerView recy;
    Typeface fontFace=Typeface.DEFAULT;
    Button btn_download;
    EditText ed_url;
    String combine;
    ProgressDialog prgDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db_download=new Db_Download(getActivity());
        prgDialog = new ProgressDialog(getActivity());
        prgDialog.setCancelable(true);
        prgDialog.setMessage("جاري تنزيل الملف ...");
        prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.setProgress(0);
        prgDialog.setMax(100);
        prgDialog.setCancelable(false);

            List<RssItem> list = RssFeedProvider
                    .parse("Ahmed");
            String itemListAsString = list.toString();
           Toast.makeText(getActivity(),itemListAsString,Toast.LENGTH_LONG).show();
            //listener.onRssItemSelected(itemListAsString);

//reset progress b
        recy=(RecyclerView)root.findViewById(R.id.rcy);

        //recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        txt_down=(TextView) root.findViewById(R.id.txt_down);
        prog_down=(ProgressBar) root.findViewById(R.id.prog_down);
        ed_url=(EditText) root.findViewById(R.id.search_main);
        btn_download=(Button)root.findViewById(R.id.btn_download);
        btn_download.setOnClickListener(this);
        // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });

        itemF=new ArrayList<>();
        //itemF.add(new ItemFiles(0,7,"ahmed","ysy","",""));
        recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adb_file=new Adapter_Files(getActivity(),itemF,this);
        recy.setAdapter(adb_file);

        GetDexterPermission();
        try{
            //  if(getIntent().getData()!=null){//check if intent is not null
            //Uri data = getIntent().getData();//set a variable for the Intent
            //String scheme = data.getScheme();//get the scheme (http,https)
            //String fullPath = data.getEncodedSchemeSpecificPart();//get the full path -scheme - fragments
            String stringExtra = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);//"android.intent.extra.TEXT");
            ed_url.setText(stringExtra);
            // combine = scheme + "://" + fullPath; //combine to get a full URI
            //  }

            try {

                AdView mAdView =root.findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice("B837E5EC7FBB2D85461DA58004930D28").build();//.asList("B837E5EC7FBB2D85461DA58004930D28"));
                adRequest.isTestDevice(getContext());
                mAdView.loadAd(adRequest);
            }catch (Exception e){}


        }catch(Exception e){}
        return root;
    }

    public void  GetDexterPermission(){
        Dexter.withContext(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if(multiplePermissionsReport.areAllPermissionsGranted()){
                    LoadFiles(Environment.getExternalStorageDirectory().getAbsolutePath());
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();

        MultiplePermissionsListener dialogMultiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(getActivity())
                        .withTitle("Camera & audio permission")
                        .withMessage("Both camera and audio permission are needed to take pictures of your cat")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.ic_launcher)
                        .build();

        PermissionListener snackbarPermissionListener =
                SnackbarOnDeniedPermissionListener.Builder
                        .with(getView(), "Camera access is needed to take pictures of your dog")
                        .withOpenSettingsButton("Settings")
                        .withCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar snackbar) {
                                // Event handler for when the given Snackbar is visible
                            }
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                // Event handler for when the given Snackbar has been dismissed
                            }
                        }).build();
    }


    public void LoadFiles(String p){
        itemF.clear();
        File[] arr_f;
        File PathFolder;
        PathFolder=new File(p);
        if(PathFolder.exists()){
            arr_f=PathFolder.listFiles(new FilenameFilter(){
                public boolean accept(File f,String n){
                    return true;
                }
            });
            if(arr_f.length>0){
                for(int x=0;x<arr_f.length;x++){
                    if(arr_f[x].getName().startsWith("WhatsApp Business")){
                        itemF.add(new ItemFiles(0,R.drawable.ic_whatsapp_business,arr_f[x].getName()));
                        LoadFilesWhats(arr_f[x].getAbsolutePath()+"/Media/.Statuses/");
                    }
                   else if(arr_f[x].getName().startsWith("WhatsApp")){
                        itemF.add(new ItemFiles(0,R.drawable.ic_whatsapp,arr_f[x].getName()));
                        LoadFilesWhats(arr_f[x].getAbsolutePath()+"/Media/.Statuses/");
                    }

                }
                //adb_file.UpdateData(itemF);
            }
        }
    }
    public void LoadFilesWhats(String p){

        File[] arr_f;
        File Pathf;
        Pathf=new File(p);
        if(Pathf.exists()){
            arr_f=Pathf.listFiles(new FilenameFilter(){
                public boolean accept(File f,String n){
                    return true;
                }
            });
            if(arr_f.length>0){
                for(int x=0;x<arr_f.length;x++){
                    if(arr_f[x].isFile() ||arr_f[x].isHidden()){
                        if(!arr_f[x].isHidden()){

                            String fileType=getMimeType(arr_f[x]);
                            String NFD= Template.getNameFileDownload(arr_f[x].getAbsolutePath(),fileType);
                            String NFTH="";
                            try{
                                NFTH=arr_f[x].getAbsolutePath().hashCode()+".png";
                            }catch(Exception e){}
                            //CommonFunctions.getMimeType(arr_f[x], this);
                            itemF.add(new ItemFiles(1,x,arr_f[x].length(),
                                    arr_f[x].getName(),arr_f[x].getAbsolutePath(),"",1,fileType,get_siz_file(arr_f[x].length()),NFTH,NFD));
                        }}
                }
                adb_file.UpdateData(itemF);
                thredImage=new ThreadSaveImageThem(itemF,100,100);
                thredImage.start();
            }
        }
    }

    @NonNull
    static String getMimeType(@NonNull File file) {
        String type = null;
        final String url = file.toString();
        final String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        if (type == null) {
            type = "image/*"; // fallback type. You might set it to */*
        }
        return type;
    }
    public String get_siz_file(long fi_leng){
        int len=(int) fi_leng/1000;
        String si=null;
        int b=0;
        int k=0;
        int m=0;
        int g=0;
        if(len<=0){
            si=fi_leng+",0 Byte";
        }

        else if(len>0||len<1000){

            for(int e=0;e<len;e++){
                k+=1;
                if(k>=999){
                    m+=1;
                    k=0;

                }
                si=k+","+b+" KB";
            }
        }

        else if(len>1000||len<1000000){

            for(int e=0;e<len;e++){
                k+=1;
                if(k>=999){
                    m+=1;
                    k=0;

                }
                si=m+","+k+" MB";
            }
        }

        //	String si=fi_leng/1000+":"+fi_leng;

        return si;
    }

    @Override
    public void onClick(View view) {

        try{
            if(ed_url.getText().toString().length()>3){
                if(ed_url.getText().toString().startsWith("http")){
                    Intent int_down=new Intent(getActivity(), Activity_Download.class);
                    int_down.putExtra(Intent.EXTRA_TEXT,ed_url.getText().toString());
                    getActivity().startActivity(int_down);
                }
                else {
                    ed_url.setError("هذا ليس رابط");
                }
            }
            else {
                ed_url.setError("اضف رابط التحميل");
            }

        }catch (Exception e){}
        //// switch (view.getId()){
        //  case  R.id.btn_download:
        // GetDexterPermission();
        //showNotification();
        //startActivity(new Intent(getActivity(), InAppBillingActivity.class));
        // ProgSize+=10;
        //  RiushMessageNotification.notify(getContext(),"aa",4);
        //   StartNotification();
        // createNotificationChannel();
        //  return;
        // }


        //sendNotification("ahmed","mosid",10,ProgSize,100);
       // Toast.makeText(getActivity(),"Show Not",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnProgressDown(int prog) {
        try{
            prgDialog.setProgress(prog);
            txt_down.setText("SizeF"+prog);
            prog_down.setProgress(prog);
        }catch (Exception e){
            txt_down.setText("kkd"+e.getMessage());
        }
    }







    private void sendNotification(String title, String messageBody, int notificationId,int ProgSiz,int ProMax) {
        String CHANNEL_ID = "ForegroundServiceChannel";
        Log.e("Notification",title+"/"+messageBody+"/"+notificationId);
        try {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0 , intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager = (NotificationManager)
                    getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "main_notification_channel_name",
                        NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(mChannel);
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID)
                    //.setColor(ContextCompat.getColor(, R.color.colorPrimary))
                    .setSmallIcon(R.drawable.ic_download_gold)
                    // .setLargeIcon(largeIcon(context))
                    .setContentTitle(title)
                    .setProgress(ProMax,ProgSiz,false)
                    .setContentText(messageBody)
                    //  .setStyle(new NotificationCompat.BigTextStyle().bigText(
                    ///        context.getString(R.string.charging_reminder_notification_body)))
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pendingIntent)
                    //.addAction(drinkWaterAction(context))
                    //  .addAction(ignoreReminderAction(context))
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            }
            notificationManager.notify(notificationId, notificationBuilder.build());

        }catch (Exception e){
            Log.e("Notification",e.getMessage());

        }
    }





    @Override
    public void OnCompleteDown(String prog) {
        try{
            // Toast.makeText(HomeFragment.this,prog,Toast.LENGTH_LONG).show();
            prgDialog.dismiss();
            txt_down.setText(prog);
        }catch (Exception e){
            txt_down.setText("ssd"+e.getMessage());
        }
        try{
            //  LoadFiles(Environment.getExternalStorageDirectory().getAbsolutePath());
            adb_file.UpdateData(itemF);
        }catch (Exception e){}
        /// adb_file.UpdateData(itemF);
        //prog_down.setMax(prog);

    }

    @Override
    public void OnStartDown(String prog) {
        try{
            txt_down.setText(prog);
            //   prog_down.setMax(prog);
        }catch (Exception e){
            txt_down.setText("lld"+e.getMessage());
        }
    }

    @Override
    public void OnErroreDown(String prog) {
        try{
            txt_down.setText(prog);
            //   prog_down.setMax(prog);
        }catch (Exception e){
            txt_down.setText("00d "+e.getMessage());
        }
    }
    @Override
    public void OnMessageDown(String prog) {
        try{
            txt_down.setText(Html.fromHtml("<font color=#4477cc>"+prog+"</font><br>"));
            //   prog_down.setMax(prog);
        }catch (Exception e){
            txt_down.setText("\n00d "+e.getMessage());
        }
    }


    @Override
    public void OnSizeFileDown(int prog) {
        try{

            prgDialog.setMax(prog);
            txt_down.setText("SizeF"+prog);
            prog_down.setMax(prog);
        }catch (Exception e){
            txt_down.setText("rrd "+e.getMessage());
        }
    }

    @Override
    public void OnFindUrl(ItemDownload itemDownload) {

    }
}