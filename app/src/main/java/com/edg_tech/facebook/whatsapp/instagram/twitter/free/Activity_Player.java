package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.OnResutDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemFiles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class Activity_Player extends AppCompatActivity implements OnResutDownload {
    public static final String TAG="Activity_Player";
    VideoView vid;
   ImageView img;
    String url_media="";
    String Path_File="";
    String Name_File="";
    String TypeF="";
    private int position = 0;
    MediaController mediaController;
long SizeF=0;
ProgressBar prog_down;
    Db_Download db_download;
    FloatingActionButton flot_download;
    public void onCreate(Bundle SavdedInstanse){
        super.onCreate(SavdedInstanse);

        setContentView(R.layout.activity_player);
        prog_down=(ProgressBar)findViewById(R.id.prog_down);
     try{
        getSupportActionBar().hide();
    }catch(Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
        db_download=new Db_Download(this);
     flot_download=(FloatingActionButton)findViewById(R.id.flot_download);
        vid=(VideoView)findViewById(R.id.vid);
        img=(ImageView)findViewById(R.id.img);
try{
        if(getIntent()!=null){
            url_media=getIntent().getExtras().getString("Url_Media");
            if (url_media.startsWith("file://")) {
                Path_File = url_media.replace("file://","");
            }
            else{
                Path_File=url_media;
            }
            File file=new File(Path_File);
            Name_File=file.getName();
            SizeF=file.length();
            IsFindDownloadFile();
            TypeF=getIntent().getExtras().getString("TypeF");
           // Toast.makeText(this,TypeF,Toast.LENGTH_LONG).show();
            if(TypeF.startsWith("video")) {
               getVideo();
            }
            else if(TypeF.startsWith("image")){
getImage();
            }
        }
}catch(Exception e){
    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
}

flot_download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ItemDownload itemd = new ItemDownload();
        long cod = System.currentTimeMillis();
        itemd.setP_F(Path_File);
        itemd.setN_F(Name_File);
        itemd.setType_F(TypeF);
        itemd.setSize_F(SizeF);

        itemd.setH_Code(cod);
        ItemFiles itemf = new ItemFiles(0,Path_File,0,Name_File);


        db_download.insertF(itemd);
        SaveFileDownload savf = new SaveFileDownload(Activity_Player.this, itemf, Activity_Player.this, cod);
        savf.start();
        flot_download.setVisibility(View.GONE);
    }
});
}

    private void IsFindDownloadFile() {
     try{
        File fil=new File(Template.PathMyFolderDownload+"/"+Name_File);
        if(fil.exists()){
            flot_download.setVisibility(View.GONE);
        }
         else{
            flot_download.setVisibility(View.VISIBLE);
        }

    }catch(Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
    }

    public void getVideo() {
        vid.setVisibility(View.VISIBLE);
     try{
         mediaController = new MediaController(this);


             // Set the videoView that acts as the anchor for the MediaController.



        vid.setVideoURI(Uri.parse(url_media));
        // mediaController.setAnchorView(vid);
        vid.setMediaController(mediaController);
        //vid.start();

         this.vid.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

             public void onPrepared(MediaPlayer mediaPlayer) {

                 vid.seekTo(position);
                 if (position == 0) {
                     vid.start();
                 }

                 // When video Screen change size.
                 mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                     @Override
                     public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                         // Re-Set the videoView that acts as the anchor for the MediaController
                    //     mediaController.setAnchorView(vid);
                     }
                 });
             }
         });
        vid.setOnErrorListener(new MediaPlayer.OnErrorListener(){

            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.e(TAG,mediaPlayer.getDuration()+"/"+i+"/"+i1);

                vid.stopPlayback();
                return false;
            }
        });
    }catch(Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
    }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
try {


    // Store current position.
    savedInstanceState.putInt("CurrentPosition", vid.getCurrentPosition());
    vid.pause();
}catch (Exception e){
    Log.e(TAG,e.getMessage());

}
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
try {


    // Get saved position.
    position = savedInstanceState.getInt("CurrentPosition");
    vid.seekTo(position);
}catch (Exception e){
    Log.e(TAG,e.getMessage());

}
    }
    public void getImage(){
        img.setVisibility(View.VISIBLE);
        try{

            File filem=new File(url_media.replace("file://",""));
            if(filem.exists()){
                Bitmap btm= BitmapFactory.decodeFile(filem.getAbsolutePath());
                img.setImageBitmap(btm);
            }
     /*Bitmap btm=BitmapFactory.decodeFile(p);

	    Bitmap bt=Bitmap.createScaledBitmap(btm,60,60,true);
     img.setImageBitmap(bt);*/
        }catch(Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void OnCompleteDown(String prog) {
        try{
            // Toast.makeText(HomeFragment.this,prog,Toast.LENGTH_LONG).show();
            ////prgDialog.dismiss();
            //txt_down.setText(prog);
        }catch (Exception e){
           // txt_down.setText("ssd"+e.getMessage());
        }
        try{
            //  LoadFiles(Environment.getExternalStorageDirectory().getAbsolutePath());
       //     adb_file.UpdateData(itemF);
        }catch (Exception e){}
        /// adb_file.UpdateData(itemF);
        //prog_down.setMax(prog);

    }

    @Override
    public void OnStartDown(String prog) {
        try{
           // txt_down.setText(prog);
            //   prog_down.setMax(prog);
        }catch (Exception e){
        //    txt_down.setText("lld"+e.getMessage());
        }
    }

    @Override
    public void OnErroreDown(String prog) {
        try{
         //   txt_down.setText(prog);
            //   prog_down.setMax(prog);
        }catch (Exception e){
          //  txt_down.setText("00d "+e.getMessage());
        }
    }
    @Override
    public void OnMessageDown(String prog) {
        try{
           // txt_down.setText(Html.fromHtml("<font color=#4477cc>"+prog+"</font><br>"));
            //   prog_down.setMax(prog);
        }catch (Exception e){
           // txt_down.setText("\n00d "+e.getMessage());
        }
    }


    @Override
    public void OnSizeFileDown(int prog) {
        try{

            //prgDialog.setMax(prog);
            //txt_down.setText("SizeF"+prog);
          prog_down.setMax(prog);
        }catch (Exception e){
          //  txt_down.setText("rrd "+e.getMessage());
        }
    }

    @Override
    public void OnFindUrl(ItemDownload itemDownload) {

    }

    @Override
    public void OnProgressDown(int prog) {
        try{
        // prgDialog.setProgress(prog);
           // txt_down.setText("SizeF"+prog);
        prog_down.setProgress(prog);
        }catch (Exception e){
           // txt_down.setText("kkd"+e.getMessage());
        }
    }
}
