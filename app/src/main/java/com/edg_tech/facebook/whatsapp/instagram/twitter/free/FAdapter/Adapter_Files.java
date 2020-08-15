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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemFiles;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.Template;

import java.io.File;
import java.util.List;


public class Adapter_Files extends RecyclerView.Adapter<Adapter_Files.MyViewHolder> {
	Activity context;
	private List<ItemFiles> listD;
	Typeface fontFace;
//= {R.mipmap.g2,R.mipmap.g3,R.mipmap.five,R.mipmap.four,R.mipmap.girl,R.mipmap.three,R.mipmap.two};

	private  OnFileListener    monImageListener;
	private int Item_Select=0;
	public Adapter_Files(Activity contex,List<ItemFiles> lis,OnFileListener click) {
		context=contex;
		listD=lis;
		monImageListener=click;
		fontFace=Typeface.DEFAULT;
		try{
			fontFace=Typeface.createFromAsset(contex.getAssets(),Template.Font1);
		}catch(Exception e){}
		//   monImageListener=click;
	}

	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// create a new view
		View v;

		if(viewType==0){

			v= (LayoutInflater.from(parent.getContext())
					.inflate(R.layout.lay_host_file, parent, false));
		}
		else{
			v= (LayoutInflater.from(parent.getContext())
					.inflate(R.layout.row_file2, parent, false));
		}
		MyViewHolder vh = new MyViewHolder(v,monImageListener);
		return vh;

	}
	@Override
	public void onBindViewHolder(MyViewHolder holder, int position)
	{
		ItemFiles item=listD.get(position);
		try
		{
		int stat=item.Stat_Run+1;
		item.SetStatRun(stat);


		holder.txt_name.setText(item.NameF);
		holder.txt_title.setText(item.PathF);
		//holder.card_img.setVisibility(LinearLayout.VISIBLE);
		holder.txt_size.setText(item.SizeFM);
		holder.txt_type.setText(item.Stat_Run+" "+item.TypeString);//+"\n"+item.NameFD+"\n"+item.NameFTh);
		try{
			holder.txt_name.setTypeface(fontFace);
			holder.txt_title.setTypeface(fontFace);
			holder.txt_size.setTypeface(fontFace);
		}catch(Exception e){}
		if(item.PathF.endsWith(".jpg")||item.PathF.endsWith(".png")){
			holder.img_type.setVisibility(View.GONE);
			loadImage(holder.img,item.PathF);
			holder.img_type.setVisibility(View.VISIBLE);
			holder.img_type.setImageDrawable(context.getDrawable(R.drawable.ic_img));

			try{
				File fild=new File(Template.PathMyFolderDownload+"/"+item.NameF);//.PathF.hashCode()+".png");

				if(fild.exists()){
					holder.btn_open.setVisibility(View.VISIBLE);
					holder.img_download.setVisibility(View.GONE);
					item.PathDownload=fild.getAbsolutePath();

				}
				else{
					holder.btn_open.setVisibility(View.GONE);
					holder.img_download.setVisibility(View.VISIBLE);
				}
			}catch(Exception e){}


		}
		else if(item.PathF.endsWith(".mp4")){
			holder.img_type.setVisibility(View.VISIBLE);
			get_imgVid(holder.img,item.PathF);
			holder.img_type.setImageDrawable(context.getDrawable(R.drawable.ic_play_w));
			try{
				File fild=new File(Template.PathMyFolderDownload+"/"+item.NameF);//.PathF.hashCode()+".mp4");
				if(fild.exists()){
					holder.btn_open.setVisibility(View.VISIBLE);
					holder.img_download.setVisibility(View.GONE);
					item.PathDownload=fild.getAbsolutePath();
				}
				else{
					holder.btn_open.setVisibility(View.GONE);
					holder.img_download.setVisibility(View.VISIBLE);
				}
			}catch(Exception e){}

		}
		else{
			holder.img_type.setVisibility(View.GONE);
			get_iconf(holder.img,item.PathF);
			//  holder.img.setImageResource(R.drawable.background);
		}
	}
	catch (Exception e)
	{}
try{
	holder.ic_type.setImageResource(item.IcType);
	holder.name_type.setText(item.NameType);

	}
	catch (Exception e)
	{}
	}

	@Override
	public int getItemViewType(int position)
	{
		super.getItemViewType(position);
		return listD.get(position).TypeF;
	}


	public void get_imgVid(ImageView img,String p){
		try{
			File filem=new File(Template.PathMyFolderThumbnails+"/"+p.hashCode()+".png");
			if(filem.exists()){
				Bitmap btm=BitmapFactory.decodeFile(filem.getAbsolutePath());
				img.setImageBitmap(btm);
			}
			//Bitmap bt=Bitmap.createScaledBitmap(lis_bit.get(1),60,60,true);


			//img.setImageBitmap(bt);
		}catch(Exception e){}
	}

	public void get_iconf(ImageView img,String p){
		try{
			File filem=new File(p);

			if(filem.exists()){
				if(filem.isDirectory()){
					Bitmap bit=BitmapFactory.decodeResource(context.getResources(),R.drawable.background);

					Bitmap bt=Bitmap.createScaledBitmap(bit,60,60,true);


					img.setImageBitmap(bt);
					if(filem.isHidden()){
						//img.setImageDrawable(context.getDrawable(R.drawable.folder_yellow_full));
						img.setAlpha(0.8f);
					}
					else{
						//img.setImageDrawable(context.getDrawable(R.drawable.folder_yellow_full));
						img.setAlpha(1.8f);
					}
				}
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


	public String getWordName(String namus)
	{
		String nam="";
		String[] sp_n=namus.split(" ");
		for(int x=0;x<sp_n.length;x++){
			nam=nam+sp_n[x].toString().charAt(0);
		}
		return nam;
	}

	public void loadImage(ImageView img,String p){
		try{

			File filem=new File(Template.PathMyFolderThumbnails+"/"+p.hashCode()+".png");
			if(filem.exists()){
				Bitmap btm=BitmapFactory.decodeFile(filem.getAbsolutePath());
				img.setImageBitmap(btm);
			}
     /*Bitmap btm=BitmapFactory.decodeFile(p);

	    Bitmap bt=Bitmap.createScaledBitmap(btm,60,60,true);
     img.setImageBitmap(bt);*/
		}catch(Exception e){}
	}

	public void UpdateData(List<ItemFiles> lis){
		// listD.clear();
		listD=lis;
		//Item_Select=item_select;
		notifyDataSetChanged();

	}
	public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		public TextView name_type;
		public ImageView ic_type;

		public TextView btn_open;
		public ImageView img_download;
		public CardView card_img;
		public    ImageView img;
		public    ImageView img_type;
		public    TextView txt_name;
		public LinearLayout lay;
		public    TextView txt_title;
		public View row;
		public    TextView txt_size;
		public    TextView txt_type;
		OnFileListener lonImageListener;
		public MyViewHolder(View itemView,OnFileListener clicklist)
		{
			super(itemView);
			ic_type=(ImageView)itemView.findViewById(R.id.ic_type);
			name_type=(TextView)itemView.findViewById(R.id.name_type);


			card_img=(CardView)itemView.findViewById(R.id.card_img);

			img_download=(ImageView)itemView.findViewById(R.id.img_download);
			btn_open=(TextView)itemView.findViewById(R.id.btn_open);



			img=(ImageView)itemView.findViewById(R.id.img);
			txt_name=(TextView)itemView.findViewById(R.id.txt_name);
			img_type=(ImageView)itemView.findViewById(R.id.img_type);


			txt_size=(TextView)itemView.findViewById(R.id.txt_size);
			txt_type=(TextView)itemView.findViewById(R.id.txt_type);


			lay=(LinearLayout)itemView.findViewById(R.id.lay_post_color);

			txt_title=(TextView)itemView.findViewById(R.id.txt_title);
			/*txt_state=itemView.findViewById(R.id.txt_state);*/
			row=itemView;
			lonImageListener= clicklist;
			//btn_open.setOnClickListener(this);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
		/*	if(v.getId()==R.id.btn_open){
				lonImageListener.OnFileOpen(getAdapterPosition(),listD.get(getAdapterPosition()).PathDownload);
			}
			else{
				try {*/
					lonImageListener.OnFileClick(getAdapterPosition());
				//}catch (Exception e){}
				//}
		}
	}



	public class MyViewHolderType extends RecyclerView.ViewHolder
	{
		public TextView name_type;
		public ImageView ic_type;

		public MyViewHolderType(View itemView,OnFileListener clicklist)
		{
			super(itemView);


			ic_type=(ImageView)itemView.findViewById(R.id.ic_type);
			name_type=(TextView)itemView.findViewById(R.id.name_type);


		}

	}




	public interface OnFileListener{
		void OnFileClick(int position);
		void OnFileOpen(int position, String pathd);
	}
}

