package com.edg_tech.facebook.whatsapp.instagram.twitter.free;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemFiles;

import java.util.*;
import android.util.*;
import android.graphics.*;
import android.media.*;
import java.io.*;

public class ThreadSaveImageThem extends Thread
{
    List<ItemFiles> items;
    int w;
    int h;
    public static final String TAG="ThreadSaveImageThem";
    public ThreadSaveImageThem(List<ItemFiles> itf, int wid, int hei){
	items=itf;
	w=wid;
	h=hei;
	Log.e(TAG,"Start"+itf.size()+"/"+wid+"/"+hei);
    }

    @Override
    public void run()
    {
	// TODO: Implement this method
	super.run();
	File filem=new File(Template.PathMyFolderThumbnails);
	if(!filem.exists()){
	    filem.mkdirs();
	}
	Log.e(TAG,"Run");
	for(int x=0;x<items.size();x++){
	 ItemFiles item=items.get(x);
	 
	    try{
		
		if(item.PathF.endsWith(".jpg")||item.PathF.endsWith(".png")){
		    String nameimg=item.PathF.hashCode()+".png";
		    File fim=new File(Template.PathMyFolderThumbnails+"/"+nameimg);
		    if(!fim.exists()){
		    Bitmap bit=BitmapFactory.decodeFile(item.PathF);
		    Bitmap bt=Bitmap.createScaledBitmap(bit,w,h,true);
		    ByteArrayOutputStream oup=new ByteArrayOutputStream();
		    bt.compress(Bitmap.CompressFormat.PNG,90,oup);
		    FileOutputStream out=new FileOutputStream(Template.PathMyFolderThumbnails+"/"+nameimg);
		    out.write(oup.toByteArray());
		}
		    }
		else if(item.PathF.endsWith(".mp4")){
		    try{
			String nameimg=item.PathF.hashCode()+".png";
			File fim=new File(Template.PathMyFolderThumbnails+"/"+nameimg);
			if(!fim.exists()){
			List<Bitmap> lis_bit=new ArrayList<Bitmap>();
			MediaMetadataRetriever med=new MediaMetadataRetriever();
			med.setDataSource(item.PathF);
			for(int s=0;s<3;s++){
			    lis_bit.add(med.getFrameAtTime(1000*s,MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
			}

			Bitmap bt=Bitmap.createScaledBitmap(lis_bit.get(1),w,h,true);
			ByteArrayOutputStream oup=new ByteArrayOutputStream();
			bt.compress(Bitmap.CompressFormat.PNG,90,oup);
			FileOutputStream out=new FileOutputStream(Template.PathMyFolderThumbnails+"/"+nameimg);
		out.write(oup.toByteArray());
			//img.setImageBitmap(bt);
		  }
			}catch(Exception e){}
		}
		
	    }catch(Exception e){
		Log.e(TAG,e.getMessage());
		
	    }
	}
	}
    
}
