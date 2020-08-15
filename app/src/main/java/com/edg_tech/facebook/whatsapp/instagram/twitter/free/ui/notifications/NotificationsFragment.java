package com.edg_tech.facebook.whatsapp.instagram.twitter.free.ui.notifications;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Activity_Player;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Db_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Dialog_Delete_File;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Dialog_Detail_File;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FAdapter.Adapter_DownLoading;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.ThreadSaveImageThem;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment  extends Fragment implements View.OnClickListener, Adapter_DownLoading.OnFileListener {
    PowerMenu powerMenu;
Handler hand;
Runnable runnable;
public static final String TAG="NotificationsFragment";
//int IdRun=0;
    @Override
    public void OnFileClick(int position)
    {
        if(itemD.get(position).ID_DM>0) {
        if(itemD.get(position).Status_D!=Template.STATUS_SUCCESSFUL) {

            pauseDownload(itemD.get(position).ID_DM);
        }
        else{
            resumeDownload(itemD.get(position).ID_DM);
        }
        }
       // Dialog_Detail_File();
        ////FragmentManager mang=getFragmentManager();
       //// Dialog_Detail_File dialog_detail_file= Dialog_Detail_File.newInstance(itemD.get(position));
        ////dialog_detail_file.show(mang,"Details File");
        //db_download.Setsizedownf(20,itemD.get(position).getH_Code());
        //SaveFileDownload savf=new SaveFileDownload(itemF.get(position));
        //savf.start();
        // TODO: Implement this method
    }

    @Override
    public void OnMenuClick(final int position) {

        List<PowerMenuItem> list = new ArrayList<>();
        list.add(new PowerMenuItem("فتح الملف" ,false));
        list.add(new PowerMenuItem("حذف الملف" ));
        list.add(new PowerMenuItem("تفاصيل الملف"));

        powerMenu = new PowerMenu.Builder(getContext()).build();
        powerMenu.addItemList(list); // list has "Novel", "Poerty", "Art"
        //powerMenu.addItem(new PowerMenuItem("Journals", false));
        //powerMenu.addItem(new PowerMenuItem("Travel", false));
        powerMenu.setAnimation(MenuAnimation.SHOWUP_TOP_LEFT); // Animation start point (TOP | LEFT)
        powerMenu.setMenuRadius(0f);
        powerMenu.setMenuShadow(10f);
        powerMenu.setTextColor(getResources().getColor(R.color.colorPrimary));
        powerMenu.setSelectedTextColor(Color.WHITE);
        powerMenu.setMenuColor(Color.WHITE);
        powerMenu.setSelectedMenuColor(getResources().getColor(R.color.colorPrimary));
        powerMenu.setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>(){

                    @Override
                    public void onItemClick(int posit, PowerMenuItem item) {
                        if(posit==0){
                            File f=new File(Template.PathMyFolderDownload+"/"+itemD.get(position).N_F);
                            Intent inte=new Intent(getActivity(), Activity_Player.class);
                            // Uri parse = Uri.parse(f.toString());
                            inte.putExtra("Url_Media","file://"+f.toString());
                            inte.putExtra("TypeF",itemD.get(position).Type_F);
                            getActivity().startActivity(inte);
                        }
                        else if(posit==1){
                            FragmentManager mang=getFragmentManager();
                            Dialog_Delete_File dialog_delete_file= Dialog_Delete_File.newInstance(itemD.get(position));
                            dialog_delete_file.show(mang,"Details File");
                        }
                        else if(posit==2){
                            FragmentManager mang=getFragmentManager();
                            Dialog_Detail_File dialog_detail_file= Dialog_Detail_File.newInstance(itemD.get(position));
                            dialog_detail_file.show(mang,"Details File");
                        }
                      //  Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        powerMenu.setSelectedPosition(posit); // change selected item
                        powerMenu.dismiss();
                    }
                });

       powerMenu.showAtCenter(this.getView());
    }


    @Override
    public void OnFileOpen(int position, String pathd)
    {
        File f=new File(Template.PathMyFolderDownload+"/"+pathd);
        Intent inte=new Intent(getActivity(), Activity_Player.class);
        // Uri parse = Uri.parse(f.toString());
        inte.putExtra("Url_Media","file://"+f.toString());
        inte.putExtra("TypeF",itemD.get(position).Type_F);
        getActivity().startActivity(inte);
        /*

        Intent inte=new Intent(Intent.ACTION_VIEW);
        Uri parse = Uri.parse("file://"+f.toString());
        inte.setAction("android.intent.action.VIEW");
     inte.setDataAndType(parse, itemD.get(position).Type_F);
        //inte.setClassName("com.riush.myplayer", "com.riush.myplayer.player2");
        getActivity().startActivity(inte);
      *///Toast.makeText(getActivity(),f.toString(),Toast.LENGTH_LONG).show();
        // TODO: Implement this method
    }

    ThreadSaveImageThem thredImage;


    List<ItemDownload> itemD;
    Adapter_DownLoading adb_down;
    RecyclerView recy;
    Typeface fontFace=Typeface.DEFAULT;
int idr=0;
    String combine;
Db_Download db_download;
TextView ed_url;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        ;
        View root = inflater.inflate(R.layout.fragment_downloaded, container, false);
        hand = new Handler();
        db_download = new Db_Download(getActivity());
        recy = (RecyclerView) root.findViewById(R.id.rcy);
        ed_url = (TextView) root.findViewById(R.id.txt_res);
        ed_url.setMovementMethod(new ScrollingMovementMethod());
        // final TextView textView = root.findViewById(R.id.text_home);
        /*notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });*/

        itemD = db_download.getFilesDownloading();
        //itemF.add(new ItemFiles(0,7,"ahmed","ysy","",""));

        for (int x = 0; x < itemD.size(); x++) {
    /*if(itemD.get(x).getStatus_D()!=Template.STATUS_SUCCESSFUL){
        itemD.remove(x);
       // break;
    }
    else{*/
            if (itemD.get(x).ID_DM == 0) {

            } else {
                getStarDown(itemD.get(x));
                //itemD.set(x,
                //    getStarDown(itemD.get(x));//);
            }

        }
    //}

        recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adb_down=new Adapter_DownLoading(getActivity(),itemD,this);
        recy.setAdapter(adb_down);
        GetDexterPermission();

        runnable=new Runnable() {
            @Override
            public void run() {
                UpdateData();
                hand.postDelayed(runnable,3000);
            }
        };
        return root;
    }

    public void  GetDexterPermission(){
        Dexter.withContext(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if(multiplePermissionsReport.areAllPermissionsGranted()){
                 //   LoadFiles(Template.PathMyFolderDownload);
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

       //hand.postDelayed(runnable,2000);
    }


public void getStarDown(ItemDownload item){

        long Total_Download=0;
        long Byte_Download=0;
        String Type_Download="";
        int State_Download=0;
   // Log.e(TAG,"getStarDown"+item);
        try{
    DownloadManager downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

    // using query method
    boolean finishDownload = false;
    int progress;
   // while (!finishDownload) {
        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(item.ID_DM));
        if (cursor.moveToFirst()) {
      //     String uptf= db_download.UpdateTypeFile(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)),item.H_Code);
         // Log.e(TAG,uptf);
           // ed_url.append("star Down\n " + cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)));
         //   Log.e("URL ",cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)));
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
          //  item.setStatus_D(2);
            try{
                 Total_Download=cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                 Byte_Download=cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                 Type_Download=cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                 State_Download=0;

            }catch(Exception e){

            }
            switch (status) {
                case DownloadManager.STATUS_FAILED: {
                    State_Download=Template.STATUS_FAILED;
//db_download.UpdateStateDownload(Template.STATUS_FAILED,item.H_Code);
                   // ed_src.append("STATUS_FAILED ");
                    finishDownload = true;
                    break;
                }
                case DownloadManager.STATUS_PAUSED:
                    State_Download=Template.STATUS_PAUSED;
                   // item.setStatus_D(Template.STATUS_PAUSED);
                 //   item.setSize_Down_F(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                 //   item.setSize_F(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));
                  //  db_download.UpdateStateDownloadWithSize(Template.STATUS_PAUSED,item.H_Code,,cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                   //  ed_url.append("STATUS_PAUSED ");
                    break;
                case DownloadManager.STATUS_PENDING:
State_Download=Template.STATUS_PENDING;
                    break;
                case DownloadManager.STATUS_RUNNING: {
                    State_Download=Template.STATUS_RUNNING;
                    final long total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                   Total_Download=total;
                    if (total >= 0) {
                        final long downloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
Byte_Download=downloaded;
                        progress = (int) ((downloaded * 100L) / total);
                     // ed_url.append("progress " + progress);
                        // if you use downloadmanger in async task, here you can use like this to display progress.
                        // Don't forget to do the division in long to get more digits rather than double.
                        //  publishProgress((int) ((downloaded * 100L) / total));
                    }
                 //   break;
                }
                case DownloadManager.STATUS_SUCCESSFUL: {
                    State_Download=Template.STATUS_SUCCESSFUL;
                   // item.setStatus_D(Template.STATUS_SUCCESSFUL);
                   // item.setSize_Down_F(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                   // item.setSize_F(cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)));
                  //  db_download.UpdateStateDownloadWithSize(Template.STATUS_SUCCESSFUL,item.H_Code,cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)),cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
                    progress = 100;
                    // if you use aysnc task
                    // publishProgress(100);
                    finishDownload = true;
                   // Toast.makeText(getActivity(), "Download Completed", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,item.H_Code);
        item.setStatus_D(State_Download);
        item.setSize_F(Total_Download);
        item.setSize_Down_F(Byte_Download);
        item.setType_F(Type_Download);
    //}
}catch (Exception e){
            Log.e(TAG,e.getMessage());
       // ed_src.append("\nDowm\n"+e.getMessage());
    }
        //return item;
}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_download:
                GetDexterPermission();
                // return;
        }
    }
    public  void UpdateData(){
        try {
            idr += 1;
            Log.e(TAG, idr + " UpdateData");
            itemD = db_download.getFilesDownloading();
            for (int x = 0; x < itemD.size(); x++) {
                /*     if (itemD.get(x).getStatus_D() != Template.STATUS_SUCCESSFUL) {
                 */
                if (itemD.get(x).ID_DM == 0) {

                } else {
                    //if(itemD.get(x).Status_D!=Template.STATUS_SUCCESSFUL) {
                        getStarDown(itemD.get(x));
                    //}
                    //itemD.set(x,
                    //    getStarDown(itemD.get(x));//);
                }
                // break;
           /* } else {
                itemD.remove(x);
            }*/
            }
            adb_down.UpdateData(itemD, idr);
        }catch (Exception e){}
        }
    @Override
    public void onPause(){
        super.onPause();
        Log.e(TAG,"onPause");
        //Toast.makeText(getActivity(),"onPause",Toast.LENGTH_LONG).show();
    try{
        hand.removeCallbacks(runnable);
    }catch (Exception e){}
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG,"onResume");
        try{
            hand.postDelayed(runnable,1000);
        }catch (Exception e){}
        //Toast.makeText(getActivity(),"onResume",Toast.LENGTH_LONG).show();
    }





    private boolean resumeDownload( long downloadid) {
        int updatedRows = 0;

        ContentValues resumeDownload = new ContentValues();
      //  resumeDownload.put(DownloadManager.COLUMN_STATUS, Template.STATUS_PENDING); // Resume Control Value
        resumeDownload.put("control", 0);
        try {
          //  DownloadManager downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

            //Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(item.ID_DM));

            updatedRows = getActivity()
                    .getContentResolver()
                    .update(Uri.parse("content://downloads/my_downloads"),
                            resumeDownload,
                            DownloadManager.EXTRA_DOWNLOAD_ID+"="+downloadid,
                            new String[]{  });
        } catch (Exception e) {
            Log.e(TAG, "Failed to update control for downloading video");
        }

        return 0 < updatedRows;
    }

    private boolean pauseDownload( long downloadid) {
        int updatedRows = 0;

        ContentValues pauseDownload = new ContentValues();
        pauseDownload.put("control", 1); // Pause Control Value

        try {
            updatedRows = getActivity()
                    .getContentResolver()
                    .update(Uri.parse("content://downloads/my_downloads"),
                            pauseDownload,

                            DownloadManager.EXTRA_DOWNLOAD_ID+"="+downloadid,
                            new String[]{  });
        } catch (Exception e) {
            Log.e(TAG, "Failed to update control for downloading video");
        }

        return 0 < updatedRows;
    }
}