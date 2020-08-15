package com.edg_tech.facebook.whatsapp.instagram.twitter.free;
import android.os.*;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Template
{
    public final static String Font1="font/janna_lt_bold.ttf";
    public final static String SystemFolder="/data/data/com.riush.wserver";
    
    public final static int TypeTitle=0;
    public final static int TypeFolder=1;
    public final static int TypeVideo=2;
    
    public final static int TypeImage=3;
    
    public final static String PathMyFolder=Environment.getExternalStorageDirectory().getAbsolutePath()+"/EdgeDownloaad";
    public final static String PathMyFolderDownload=PathMyFolder+"/Download";
    public final static String PathMyFolderThumbnails=PathMyFolder+"/.thumbnails";
    public final static String PathMyFolderHtml=PathMyFolder+"/html";

   
    public static String getNameFileDownload(String p,String mi){
	String nam="";
	try{
	    String[] sp=mi.split("/");
	    nam=p.hashCode()+"."+sp[1];
	}catch(Exception e){}
	return nam;
    }


    public static String GetStatusDownload(long stat){
        String MsgST=stat+"";
       if(STATUS_FAILED==stat) {
               MsgST = "خطاء في التحميل";
           }
            //   return MsgST;
        else if(STATUS_PAUSED==stat) {
            MsgST = "موستأنف";
        }
        else if(STATUS_PENDING==stat) {
                MsgST = "يبدأ";
            }
               else if(STATUS_RUNNING==stat) {
                    MsgST = "جاري";
                }
               else if(STATUS_SUCCESSFUL==stat) {
               MsgST="اكتمل";

       }
       return MsgST+(int)stat+"";
    }



    public static String humanReadableByteCountBin(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }




    public static final int ERROR_CANNOT_RESUME = 1008;
    public static final int ERROR_DEVICE_NOT_FOUND = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1009;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1006;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int ERROR_UNKNOWN = 1000;
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    public static final int STATUS_FAILED = 16;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUCCESSFUL = 8;

    }
