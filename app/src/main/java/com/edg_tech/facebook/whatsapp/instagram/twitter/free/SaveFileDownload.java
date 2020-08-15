package com.edg_tech.facebook.whatsapp.instagram.twitter.free;


import android.content.Context;
import android.util.*;
import java.io.*;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown.OnResutDownload;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemFiles;

public class SaveFileDownload extends Thread
{

   ItemFiles items;
	OnResutDownload ResDown;
	long Code_Db=0;
 Db_Download db_download;
	Context context;
    int w;
    int h;
    public static final String TAG="SaveFileDownload";
    public SaveFileDownload(Context cont,ItemFiles itf, OnResutDownload onRes,long cod){
	db_download=new Db_Download(cont);
    	items=itf;
	Code_Db=cod;
	context=cont;
		ResDown=onRes;
	Log.e(TAG,"Start");
    }




    @Override
    public void run()
    {
	// TODO: Implement this method
	super.run();
	ResDown.OnStartDown("StartDownload");
	File filem=new File(Template.PathMyFolderDownload);
	if(!filem.exists()){
	    filem.mkdir();
	}
	Log.e(TAG,"Run");
	
	  // ItemFiles item=items;
		Log.e(TAG,items.PathF);
		Log.e(TAG,items.NameF);
	    try{
			long total = 0;
			long lenghtOfFile=0;
			String string_err="";
		    try{

			File fim=new File(Template.PathMyFolderDownload+"/"+items.NameF);
			if(!fim.exists()){

			   /* if(item.NameF.length()>2){

				}*/
				int count;
			   FileInputStream inp=new FileInputStream(items.PathF);
				 lenghtOfFile=new File(items.PathF).length();
				Log.e(TAG,lenghtOfFile+"");
				ResDown.OnSizeFileDown((int)lenghtOfFile);
				//db_download.Setsizedownf(lenghtOfFile,Code_Db);
			   FileOutputStream out=new FileOutputStream(Template.PathMyFolderDownload+"/"+items.NameF);
		byte[] byt=new byte[1024];//inp.available()];

				int progress = 0;
				while ((count = inp.read(byt)) != -1)
				{
					total += count;
					int progress_temp = (int) total * 100 / ((int)lenghtOfFile);

					out.write(byt, 0, count);
					ResDown.OnProgressDown((int)total);
					db_download.Setsizedownf(total,Code_Db, Template.STATUS_RUNNING);
try{
				//	sleep(100);
				}catch(Exception e){
	Log.e(TAG,e.getMessage());
					//ResDown.OnErroreDown("dd "+e.getMessage());
				}
				}


				inp.close();
				out.close();

				//while(inp.read(byt);

				//  out.write(byt);
				//   out.flush();
				//   out.close();
				//img.setImageBitmap(bt);
			}
			}catch(Exception e){
				Log.e(TAG,e.getMessage());
				string_err=e.getMessage();
				ResDown.OnErroreDown("dd "+e.getMessage());
			}
				try{

				ResDown.OnCompleteDown("Complete Download");
			if(total>0){
				if(total==lenghtOfFile){
					db_download.UpdateStateDownload(Template.STATUS_SUCCESSFUL,Code_Db);
				}
				else {
					db_download.UpdateStateDownloadWithFailed(Template.STATUS_FAILED,string_err,Code_Db);

				}
			}
				}catch(Exception e){
					Log.e(TAG,e.getMessage());
					ResDown.OnErroreDown("kk "+e.getMessage());
				}



	    }catch(Exception e){
		Log.e(TAG,e.getMessage());
			ResDown.OnErroreDown("ff "+e.getMessage());
	    }
	}
   
}
