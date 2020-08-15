
package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

        import android.app.DownloadManager;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.MediaMetadataRetriever;
        import android.os.Bundle;
        import android.text.Html;
        import android.text.method.ScrollingMovementMethod;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.DialogFragment;

        import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
        import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;


public class Dialog_Delete_File extends DialogFragment {
    static ItemDownload itemd=new ItemDownload();
Db_Download db_download;
    public static   Dialog_Delete_File newInstance( ItemDownload item){
        itemd=item;
        Dialog_Delete_File fr=new Dialog_Delete_File();
        Bundle arg=new Bundle();
        fr.setArguments(arg);
        return fr;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_download=new Db_Download(getActivity());
        // setStyle(DialogFragment.STYLE_NORMAL, );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View row=inflater.inflate(R.layout.dialog_delete_file,container);

        return row;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img=(ImageView)view.findViewById(R.id.img);

        ImageView  img_type=(ImageView)view.findViewById(R.id.img_type);
        TextView txt_msg=(TextView)view.findViewById(R.id.txt_msg);
        final CheckBox check_delet=(CheckBox) view.findViewById(R.id.check_delete);
        Button btn_ok=(Button) view.findViewById(R.id.buttonOk);
        Button btn_cancel=(Button) view.findViewById(R.id.buttonCancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File filem=new File(Template.PathMyFolderDownload+"/"+itemd.N_F);

           if(check_delet.isChecked()){
               String strres=db_download.DeleteFile(itemd.H_Code);
               String strres2="فشل حذف الملف المصدر";

               if(filem.delete()){
                   strres2="تم حذف الملف المصدر";
                   if(itemd.getID_DM()>0){
                       strres2=strres2+"\n"+ RemoveDownload(itemd.getID_DM());
                   }
               }
               Toast.makeText(getActivity(),strres+"\n"+strres2,Toast.LENGTH_LONG).show();
dismiss();
           }
           else{

               String strres=db_download.DeleteFile(itemd.H_Code);
               String strres2="فشل حذف الملف المصدر";

               Toast.makeText(getActivity(),strres+"\n"+strres2,Toast.LENGTH_LONG).show();
               dismiss();
          //     Toast.makeText(getActivity(),check_delet.isChecked()+" delete file",Toast.LENGTH_LONG).show();

           }
            }
        });


        try{

        if(itemd.getType_F().startsWith("image")){
      img_type.setVisibility(View.GONE);
            loadImage(img,itemd.N_F);
       img_type.setVisibility(View.VISIBLE);
         img_type.setImageResource(R.drawable.ic_img);

            try{
             //   File fild=new File(Template.PathMyFolderDownload+"/"+itemd.N_F);//.PathF.hashCode()+".png");


            }catch(Exception e){}


        }
        else if(itemd.getType_F().startsWith("video")){
            img_type.setVisibility(View.VISIBLE);
            get_imgVid(img,itemd.N_F);
            img_type.setImageResource(R.drawable.ic_play_w);


        }
        else{
           img_type.setVisibility(View.GONE);
            get_iconf(img,itemd.N_F);
            //  holder.img.setImageResource(R.drawable.background);
        }
    }
	catch (Exception e)
    {}
    }

    public void loadImage(ImageView img,String p){
        try{

            File filem=new File(Template.PathMyFolderDownload+"/"+p);
            if(filem.exists()){
                Bitmap btm=BitmapFactory.decodeFile(filem.getAbsolutePath());
                img.setImageBitmap(btm);
            }
     /*Bitmap btm=BitmapFactory.decodeFile(p);

	    Bitmap bt=Bitmap.createScaledBitmap(btm,60,60,true);
     img.setImageBitmap(bt);*/
        }catch(Exception e){}
    }

    public void get_imgVid(ImageView img,String p){
        try{


            List<Bitmap> lis_bit=new ArrayList<Bitmap>();
            MediaMetadataRetriever med=new MediaMetadataRetriever();
            med.setDataSource(Template.PathMyFolderDownload+"/"+p);
            for(int s=0;s<3;s++){
                lis_bit.add(med.getFrameAtTime(1000*s,MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
            }
img.setImageBitmap(lis_bit.get(0));
/*
            File filem=new File(Template.PathMyFolderThumbnails+"/"+p.hashCode()+".png");
            if(filem.exists()){
                Bitmap btm= BitmapFactory.decodeFile(filem.getAbsolutePath());
                img.setImageBitmap(btm);
            }
            //Bitmap bt=Bitmap.createScaledBitmap(lis_bit.get(1),60,60,true);

*/
            //img.setImageBitmap(bt);
        }catch(Exception e){}
    }

    public void get_iconf(ImageView img,String p){
        try{
            File filem=new File(p);

            if(filem.exists()){
                if(filem.isDirectory()){
                    Bitmap bit=BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.background);

                    Bitmap bt=Bitmap.createScaledBitmap(bit,60,60,true);


                    img.setImageBitmap(bt);
                    if(filem.isHidden()){
                        //img.setImageDrawable(context.getDrawable(R.drawable.folder_yellow_full));
                        img.setAlpha(0.8f);
                    }
                    else{
                        //img.setImageDrawable(context.getDrawable(R.drawable.folder_yellow_full));
                        img.setAlpha(1.f);
                    }
                }
            }
            //Bitmap bt=Bitmap.createScaledBitmap(lis_bit.get(1),60,60,true);


            //img.setImageBitmap(bt);
        }catch(Exception e){}
    }

    public String RemoveDownload(long idDownload){
        String resrem="";
        try{
            DownloadManager downm=(DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            downm.remove(idDownload);
            resrem="تم الحذف DownloadManager";
        }catch(Exception e){
resrem=e.getMessage();
        }
return resrem;
    }
}
