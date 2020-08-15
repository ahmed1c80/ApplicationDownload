package com.edg_tech.facebook.whatsapp.instagram.twitter.free;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.R;
import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;


public class Dialog_Detail_File extends DialogFragment {
    static ItemDownload itemd=new ItemDownload();

public static   Dialog_Detail_File newInstance( ItemDownload item){
itemd=item;
    Dialog_Detail_File fr=new Dialog_Detail_File();
    Bundle arg=new Bundle();
   fr.setArguments(arg);
    return fr;
}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setStyle(DialogFragment.STYLE_NORMAL, );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
 View row=inflater.inflate(R.layout.main_download,container);

    return row;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView  ed_src=(TextView)view.findViewById(R.id.ed_source);
        ed_src.setMovementMethod(new ScrollingMovementMethod());

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Name:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getN_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Url:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getP_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Type:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getType_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Host:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getHost_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Size File:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getSize_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Size Download File:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getSize_Down_F()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>Status Download:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getStatus_D()+"</font><br><br>"));

        ed_src.append(Html.fromHtml("<font color=#000000 size=9>ID Download:</font><br>"));
        ed_src.append(Html.fromHtml("<font color=#4477cc>"+itemd.getID_DM()+"</font><br><br>"));

        ed_src.append(DownloadManager.STATUS_RUNNING +"\n");
    }
}
