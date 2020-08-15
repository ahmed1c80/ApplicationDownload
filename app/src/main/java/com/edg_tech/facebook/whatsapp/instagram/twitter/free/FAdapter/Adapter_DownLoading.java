package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;

import java.io.File;
import java.util.List;

public class Adapter_DownLoading extends RecyclerView.Adapter<Adapter_DownLoading.MyViewHolder> {
    Activity context;
    private List<ItemDownload> listD;
    Typeface fontFace;
//= {R.mipmap.g2,R.mipmap.g3,R.mipmap.five,R.mipmap.four,R.mipmap.girl,R.mipmap.three,R.mipmap.two};
int IdRun=0;
    private OnFileListener monImageListener;
    private int Item_Select=0;
    public Adapter_DownLoading(Activity contex, List<ItemDownload> lis, OnFileListener click) {
        context=contex;
        listD=lis;
        monImageListener=click;
        fontFace=Typeface.DEFAULT;
        try{
            fontFace=Typeface.createFromAsset(contex.getAssets(), Template.Font1);
        }catch(Exception e){}
        //   monImageListener=click;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v;
        /*if(viewType==0){

            v= (LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_file_title, parent, false));
        }
        else{*/
            v= (LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_downloading, parent, false));
       // }
        MyViewHolder vh = new MyViewHolder(v,monImageListener);
        return vh;

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {try
    {
        ItemDownload item=listD.get(position);



        holder.txt_name.setText(item.getN_F());//+"\n"+item.getP_F());
        try{
            holder.prog.setMax((int)item.Size_F);
            holder.prog.setProgress((int)item.Size_Down_F);

        }catch(Exception e){}

        try{
            holder.txt_name.setTypeface(fontFace);

            holder.txt_size.setTypeface(fontFace);

        }catch(Exception e){}
        try{

            //holder.txt_size.setText("100/"+(item.getSize_Down_F()/item.Size_F*100)+"%");///"+item.getH_Code());
            holder.txt_size.setText(Template.humanReadableByteCountBin(item.getSize_Down_F())+"/"+Template.humanReadableByteCountBin(item.Size_F));//+"/"+Template.GetStatusDownload(item.Status_D));///"+item.getH_Code());

        }catch(Exception e){}

        try{
if(item.Status_D== Template.STATUS_PENDING){
    holder.prog_wite.setVisibility(View.VISIBLE);
    holder.img_download.setVisibility(View.GONE);
}

else  if(item.Status_D== Template.STATUS_RUNNING){
                holder.prog_wite.setVisibility(View.GONE);
                holder.img_download.setVisibility(View.VISIBLE);
                holder.img_download.setImageResource(R.drawable.ic_pause_button);

}

else  if(item.Status_D== Template.STATUS_PAUSED){
    holder.prog_wite.setVisibility(View.GONE);
    holder.img_download.setVisibility(View.VISIBLE);
    holder.img_download.setImageResource(R.drawable.ic_video_player);
}
else  if(item.Status_D== Template.STATUS_SUCCESSFUL){
                holder.prog_wite.setVisibility(View.GONE);
                holder.img_download.setVisibility(View.VISIBLE);
                holder.img_download.setImageResource(R.drawable.ic_tick);
            }




else  {
    holder.prog_wite.setVisibility(View.GONE);
    holder.img_download.setVisibility(View.VISIBLE);
    holder.img_download.setImageResource(R.drawable.ic_failed);
}
        }catch(Exception e){}


        try{
            if(item.getType_F().length()>2) {
                if (item.getType_F().startsWith("image")) {

                    holder.img.setImageResource(R.drawable.ic_photo);
                }
                else if (item.getType_F().startsWith("video")) {

                    holder.img.setImageResource(R.drawable.ic_video_player);
                }
                else {

                    holder.img_download.setImageResource(R.drawable.ic_launcher_foreground);
                }
            }
        }catch(Exception e){}


        }catch(Exception e){}



    }

    @Override
    public int getItemViewType(int position)
    {
        super.getItemViewType(position);
        return position;//listD.get(position).Type_F;
    }


    public void get_imgVid(ImageView img, String p){
        try{
            File filem=new File(Template.PathMyFolder+"/"+p.hashCode()+".png");
            if(filem.exists()){
                Bitmap btm= BitmapFactory.decodeFile(filem.getAbsolutePath());
                img.setImageBitmap(btm);
            }
            //Bitmap bt=Bitmap.createScaledBitmap(lis_bit.get(1),60,60,true);


            //img.setImageBitmap(bt);
        }catch(Exception e){}
    }


    @Override
    public int getItemCount()
    {

        return listD.size();
    }


    public void UpdateData(List<ItemDownload> lis,int idr){
        IdRun=idr;
        // listD.clear();

        listD=lis;
        //Item_Select=item_select;
        notifyDataSetChanged();

    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public ImageView img_download;
        public    ImageView img;
        public    ImageView img_menu;

        public    TextView txt_name;
    public    TextView txt_size;
        public LinearLayout lay;
        public LinearLayout lay_btn_center;
        public ProgressBar prog;
        public ProgressBar prog_wite;

        public View row;

        OnFileListener lonImageListener;
        public MyViewHolder(View itemView, OnFileListener clicklist)
        {
            super(itemView);
          //  card_img=(CardView)itemView.findViewById(R.id.card_img);
            prog=(ProgressBar) itemView.findViewById(R.id.prog_download);
            prog_wite=(ProgressBar) itemView.findViewById(R.id.prog_wite);

            img_download=(ImageView)itemView.findViewById(R.id.img_download);
            img_menu=(ImageView)itemView.findViewById(R.id.img_menu);




            img=(ImageView)itemView.findViewById(R.id.img);
            txt_name=(TextView)itemView.findViewById(R.id.txt_name);
            txt_size=(TextView)itemView.findViewById(R.id.txt_size);



          //  txt_size=(TextView)itemView.findViewById(R.id.txt_size);
          //  txt_type=(TextView)itemView.findViewById(R.id.txt_type);


            lay=(LinearLayout)itemView.findViewById(R.id.lay_post_color);
            lay_btn_center=(LinearLayout)itemView.findViewById(R.id.lay_btn_center);


            /*txt_state=itemView.findViewById(R.id.txt_state);*/
            row=itemView;
            lonImageListener= clicklist;

            img_download.setOnClickListener(this);
            lay_btn_center.setOnClickListener(this);
            img_menu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.lay_btn_center){
               lonImageListener.OnFileOpen(getAdapterPosition(),listD.get(getAdapterPosition()).N_F);
            }  else if(v.getId()==R.id.img_menu){
               lonImageListener.OnMenuClick(getAdapterPosition());
            }
            else{
                lonImageListener.OnFileClick(getAdapterPosition());
            }
        }
    }

    public interface OnFileListener{
        void OnFileClick(int position);
        void OnMenuClick(int position);
        void OnFileOpen(int position, String pathd);
    }
}
