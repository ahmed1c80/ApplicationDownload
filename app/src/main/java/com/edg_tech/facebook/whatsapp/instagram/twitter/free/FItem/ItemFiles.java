package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem;

public class ItemFiles
{
    public String NameType;
    public int IcType;
    public int TypePost=1;
    public int Id;
    public long SizeF;
    public String NameF;

    public String NameFD;
    public String NameFTh;
    public String PathF;
    public String UrlF;
    public int TypeF;
    public String TypeString="";
    public String SizeFM;
    public String PathDownload="";
    public int Stat_Run=0;
    public ItemFiles(){}


    public ItemFiles(int TypePos,int icty,String namety){

        TypePost=TypePos;
        this.NameType=namety;
        this.IcType=icty;

    }


    public ItemFiles(int id,String pf,int ty,String name){
        this.Id=id;

        this.NameF=name;
        this.PathF=pf;
        this.TypeF=ty;



    }
    public ItemFiles(int TypePos,int id,long sizf,
                     String namf,String pf,String urf,
                     int tyf,
                     String TyStr,
                     String sizfm,
                     String nft,
                     String nfd
    ){
        TypePost=TypePos;
        this.Id=id;
        this.SizeF=sizf;
        this.NameF=namf;
        this.PathF=pf;
        this.UrlF=urf;
        this.TypeF=tyf;
        this.SizeFM=sizfm;
        this.TypeString=TyStr;
        this.NameFD=nfd;
        this.NameFTh=nft;
    }
    public void SetNameF(String stat){
        this.NameF=stat;
    }
    public void SetPathF(String stat){
        this.PathF=stat;
    }
    public void SetSizeFM(String stat){
        this.SizeFM=stat;
    }

    public void SetStatRun(int stat){
        this.Stat_Run=stat;
    }
    public void SetPathDownload(String stat){
        this.PathDownload=stat;
    }
}
