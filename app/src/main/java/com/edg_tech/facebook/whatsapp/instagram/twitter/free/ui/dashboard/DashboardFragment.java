package com.edg_tech.facebook.whatsapp.instagram.twitter.free.ui.dashboard;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Db_Download;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FAdapter.Adapter_DownLoading;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.ThreadSaveImageThem;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.ui.home.HomeViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment  extends Fragment implements View.OnClickListener, Adapter_DownLoading.OnFileListener {
int id_run=0;

    Handler hand;
    Runnable runnable;
    public static final String TAG="DashboardFragment";
    private HomeViewModel homeViewModel;

    @Override
    public void OnFileClick(int position)
    {
        //SaveFileDownload savf=new SaveFileDownload(itemF.get(position));
        //savf.start();
        // TODO: Implement this method
    }

    @Override
    public void OnMenuClick(int position) {

    }

    @Override
    public void OnFileOpen(int position, String pathd)
    {


        File f=new File(pathd);

        Intent inte=new Intent(Intent.ACTION_VIEW);
        Uri parse = Uri.parse(f.toString());
        inte.setAction("android.intent.action.VIEW");
        inte.setDataAndType(parse, itemD.get(position).Type_F);
        //inte.setClassName("com.riush.myplayer", "com.riush.myplayer.player2");
        getActivity().startActivity(inte);
        //  Toast.makeText(getActivity(),parse.toString(),Toast.LENGTH_LONG).show();
        // TODO: Implement this method
    }

    ThreadSaveImageThem thredImage;


    List<ItemDownload> itemD;
    Adapter_DownLoading adb_down;
    RecyclerView recy;
    Typeface fontFace=Typeface.DEFAULT;

    String combine;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_downloaded, container, false);
        hand=new Handler();

        recy=(RecyclerView)root.findViewById(R.id.rcy);

        // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });




        itemD=new ArrayList<>();
        //itemF.add(new ItemFiles(0,7,"ahmed","ysy","",""));
getStarDown();

        recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        adb_down=new Adapter_DownLoading(getActivity(),itemD,this);
        recy.setAdapter(adb_down);
      GetDexterPermission();
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


        runnable=new Runnable() {
            @Override
            public void run() {
                getStarDown();
                hand.postDelayed(runnable,3000);
            }
        };hand.postDelayed(runnable,4000);
    }


    public void getStarDown(){
        id_run+=1;
itemD=new ArrayList<>();
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
            Cursor cursor = downloadManager.query(new DownloadManager.Query());//.setFilterById(item.ID_DM));
            cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            ItemDownload item=new ItemDownload();
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
                        State_Download=Template.STATUS_FAILED;
//db_download.UpdateStateDownload(Template.STATUS_FAILED,item.H_Code);
                        // ed_src.append("STATUS_FAILED ");
                        finishDownload = true;
                        break;
                    }
                    case DownloadManager.STATUS_PAUSED:
                        State_Download=Template.STATUS_PAUSED;

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

                        }

                    }
                    case DownloadManager.STATUS_SUCCESSFUL: {
                        State_Download=Template.STATUS_SUCCESSFUL;

                        break;
                    }
                }
            itemD.add(new ItemDownload(
                    0,
                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)),
                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI)),
                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE)),

                    Total_Download,
                    Byte_Download,
                    cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)),

                    State_Download,
                    cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID)),
                    "",
                    "",
                    55226655
            ));

            cursor.moveToNext();

            }
            adb_down.UpdateData(itemD,id_run);
        //    db_download.UpdateStateTypeSize(State_Download,Total_Download,Byte_Download,Type_Download,item.H_Code);

            //}
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
            // ed_src.append("\nDowm\n"+e.getMessage());
        }
        //return item;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btn_download:
                GetDexterPermission();
                // return;
        }
    }
}