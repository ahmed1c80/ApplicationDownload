package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown;

import com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem.ItemDownload;

public interface OnResutDownload {
    void OnProgressDown(int prog);
    void OnErroreDown(String prog);
    void OnMessageDown(String prog);
    void OnCompleteDown(String prog);
    void OnStartDown(String prog);
    void OnSizeFileDown(int prog);
    void OnFindUrl(ItemDownload itemDownload);
}
