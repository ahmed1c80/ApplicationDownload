package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;

import java.util.ArrayList;
import java.util.List;

public class Db_Download extends SQLiteOpenHelper {
    public static final String DBNAME="Db_Download.db";
    public static final String DBTABLE="table_download";
    public static final String ID="Id";
    public static final String N_F="N_F";//namef
    public static final String P_F="P_F";//pathf
    public static final String C_F="C_F";//codef
    public static final String T_F="T_F";//typef
    public static final String S_F="S_F";//sizef
    public static final String S_D_F="S_D_F";//size_Download_f
    public static final String H_F="H_F";//hostF
    public static final String ST_D="ST_D";//statueF
    public static final String ID_DM="ID_DM";//ID_DownloadManager
    public static final String E_DM="E_DM";//ID_DownloadManager
    public static final String F_N_S="F_N_S";//FileName_Source
    public static final String H_Code="H_Code";//FileName_Source
    public static final int version=1;
    public Db_Download(Context context){
        super(context,DBNAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE `"+DBTABLE+"`(`"+ID+"` INTEGER PRIMARY KEY ,`"+N_F+"` TEXT," +
              //  "`"+N_F+"` TEXT," +
                "`"+P_F+"` TEXT," +
                "`"+C_F+"` TEXT," +
                "`"+T_F+"` TEXT," +
                "`"+S_F+"` LONG," +
                "`"+S_D_F+"`LONG," +
                "`"+H_F+"` TEXT," +

                "`"+ST_D+"` INTEGER," +
                "`"+ID_DM+"` LONG," +
                "`"+E_DM+"` TEXT," +
                "`"+F_N_S+"` TEXT," +
                "`"+H_Code+"` LONG" +

                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertF(ItemDownload itemd){
        ContentValues val=new ContentValues();
        val.put(N_F,itemd.getN_F());
        val.put(P_F,itemd.getP_F());
        val.put(C_F,itemd.getCode_F());
        val.put(T_F,itemd.getType_F());
        val.put(S_F,itemd.getSize_F());
        val.put(S_D_F,itemd.getSize_Down_F());
        val.put(H_F,itemd.getHost_F());
        val.put(ST_D,itemd.getStatus_D());
        val.put(ID_DM,itemd.getID_DM());
        val.put(E_DM,itemd.getER_DM());
        val.put(F_N_S,itemd.getF_N_S());
        val.put(H_Code,itemd.getH_Code());
SQLiteDatabase db=this.getWritableDatabase();
db.insert(DBTABLE,null,val);
close();
    }


    public List<ItemDownload> getFilesDownloaded(){
        List<ItemDownload> lis=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from `"+DBTABLE+"` ORDER BY `"+ID+"` DESC",null);
        if(res.getCount()>0){
            res.moveToFirst();
            while (!res.isAfterLast()){

                int st= res.getInt(res.getColumnIndex(ST_D));
              //  if(st!=Template.STATUS_SUCCESSFUL) {
                    lis.add(new ItemDownload(
                            res.getInt(res.getColumnIndex(ID)),
                            res.getString(res.getColumnIndex(N_F)),
                            res.getString(res.getColumnIndex(P_F)),
                            res.getString(res.getColumnIndex(C_F)),
                            res.getString(res.getColumnIndex(T_F)),
                            res.getLong(res.getColumnIndex(S_F)),
                            res.getLong(res.getColumnIndex(S_D_F)),
                            res.getString(res.getColumnIndex(H_F)),

                            res.getInt(res.getColumnIndex(ST_D)),
                            res.getLong(res.getColumnIndex(ID_DM)),
                            res.getString(res.getColumnIndex(E_DM)),
                            res.getString(res.getColumnIndex(F_N_S)),
                            res.getLong(res.getColumnIndex(H_Code))
                    ));
                //}
                res.moveToNext();
            }
        }
        res.close();
        close();
        return lis;

    }







    public List<ItemDownload> getFilesDownloading(){

        List<ItemDownload> lis=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from `"+DBTABLE+"`  ORDER BY `"+ID+"` DESC ",new String[]{});
      //  Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+ST_D+"`="+Template.STATUS_SUCCESSFUL+" ORDER BY `"+ID+"` DESC ",new String[]{});
        if(res.getCount()>0){
            res.moveToFirst();
            while (!res.isAfterLast()){
                lis.add(new ItemDownload(
                   res.getInt(res.getColumnIndex(ID)),
                   res.getString(res.getColumnIndex(N_F)),
                   res.getString(res.getColumnIndex(P_F)),
                   res.getString(res.getColumnIndex(C_F)),
                   res.getString(res.getColumnIndex(T_F)),
                   res.getLong(res.getColumnIndex(S_F)),
                   res.getLong(res.getColumnIndex(S_D_F)),
                   res.getString(res.getColumnIndex(H_F)),

                   res.getInt(res.getColumnIndex(ST_D)),
                   res.getLong(res.getColumnIndex(ID_DM)),
                           res.getString(res.getColumnIndex(E_DM)),
                   res.getString(res.getColumnIndex(F_N_S)),
                   res.getLong(res.getColumnIndex(H_Code))
                           ));
                res.moveToNext();
            }
        }
        res.close();
        close();
        return lis;

    }






    public List<ItemDownload> getFileDownload(long cod){
        List<ItemDownload> lis=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"`="+cod,new String[]{});
        if(res.getCount()>0){
            res.moveToFirst();
            while (!res.isAfterLast()){
                lis.add(new ItemDownload(
                        res.getInt(res.getColumnIndex(ID)),
                        res.getString(res.getColumnIndex(N_F)),
                        res.getString(res.getColumnIndex(P_F)),
                        res.getString(res.getColumnIndex(C_F)),
                        res.getString(res.getColumnIndex(T_F)),
                        res.getLong(res.getColumnIndex(S_F)),
                        res.getLong(res.getColumnIndex(S_D_F)),
                        res.getString(res.getColumnIndex(H_F)),

                        res.getInt(res.getColumnIndex(ST_D)),
                        res.getLong(res.getColumnIndex(ID_DM)),
                        res.getString(res.getColumnIndex(E_DM)),
                        res.getString(res.getColumnIndex(F_N_S)),
                        res.getLong(res.getColumnIndex(H_Code))
                ));
                res.moveToNext();
            }
        }
        res.close();
        close();
        return lis;

    }




    public void Setsizef(long lo,long cod){
       try{
        ContentValues val=new ContentValues();
       val.put(S_F,lo);
        SQLiteDatabase db=this.getReadableDatabase();
       db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
        close();
    }catch (Exception e){}
    }

    public void Setsizedownf(long lo,long cod,int st){
        try{
        ContentValues val=new ContentValues();
        val.put(S_D_F,lo);
        val.put(ST_D,st);
        SQLiteDatabase db=this.getReadableDatabase();
        db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
        close();
    }catch (Exception e){}
    }


    public void UpdateStateDownload(int st,long cod){
        try{
        ContentValues val=new ContentValues();

        val.put(ST_D,st);
        SQLiteDatabase db=this.getReadableDatabase();
        db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
        close();
    }catch (Exception e){}
    }


    public void UpdateStateDownloadWithSize(int st,long cod,long size,long sizdown){
        try{
            ContentValues val=new ContentValues();

            val.put(ST_D,st);
            SQLiteDatabase db=this.getReadableDatabase();
            db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            close();
        }catch (Exception e){}
    }

public void UpdateStateDownloadWithFailed(int st,String Filed,long cod){
        try {
            ContentValues val = new ContentValues();

            val.put(ST_D, st);
            val.put(E_DM, Filed);
            SQLiteDatabase db = this.getReadableDatabase();
            db.update(DBTABLE, val, H_Code + "=" + cod, new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            close();
        }catch (Exception e){}
    }


    public void UpdateFailedDownload(String Filed,long cod){
        try {
            ContentValues val = new ContentValues();

            val.put(E_DM, Filed);
            SQLiteDatabase db = this.getReadableDatabase();
            db.update(DBTABLE, val, H_Code + "=" + cod, new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            close();
        }catch (Exception e){}
    }



    public String UpdateTypeFile(String ty,long cod){
        String res="ok";
        try {
            ContentValues val = new ContentValues();

            val.put(T_F, ty);
            SQLiteDatabase db = this.getReadableDatabase();
            db.update(DBTABLE, val, H_Code + "=" + cod, new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            close();
        }catch (Exception e){
            res=e.getMessage();
        }
        return res;
    }

    public String DeleteFile(long cod){
        String res="ok";
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(DBTABLE, H_Code + "=" + cod, new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            res="تم حذف الملف من القائمة";
            close();
        }catch (Exception e){
            res=e.getMessage();
        }
        return res;
    }
    public void SetAllsizedown(long down,long siz,long cod){
        ContentValues val=new ContentValues();
        val.put(S_D_F,down);
        val.put(S_F,siz);
        SQLiteDatabase db=this.getReadableDatabase();
        db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
        close();
    }




    public void UpdateStateTypeSize(int state,long size,long sizdown,String typ,long cod){
        try{
            ContentValues val=new ContentValues();

            val.put(ST_D,state);
            val.put(T_F,typ);
            val.put(S_F,size);
            val.put(S_D_F,sizdown);
            SQLiteDatabase db=this.getReadableDatabase();
            db.update(DBTABLE,val,H_Code+"="+cod,new String[]{});
        /* Cursor res=db.rawQuery("select * from `"+DBTABLE+"` where `"+H_Code+"=`"+cod,new String[]{});
        if(res.getCount()>0){
            res.move(1);

            }*/
            close();
        }catch (Exception e){}
    }
}
