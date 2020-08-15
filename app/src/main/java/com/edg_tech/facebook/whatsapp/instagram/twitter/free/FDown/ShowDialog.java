package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FDown;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

class ShowDialog {
    ShowDialog() {
    }

    public void show(Context context, String str, String str2, String str3) {
        Handler handler = new Handler(Looper.getMainLooper());
        final String str4 = str3;
        final Context context2 = context;
        final String str5 = str;
        final String str6 = str2;
        Runnable r2 = new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:18:0x003f  */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x0057  */
            /* JADX WARNING: Removed duplicated region for block: B:25:0x00ab  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r5 = this;
                    java.lang.String r0 = r4
                    int r1 = r0.hashCode()
                    r2 = -1867169789(0xffffffff90b54003, float:-7.149054E-29)
                    r3 = 2
                    r4 = 1
                    if (r1 == r2) goto L_0x002c
                    r2 = 96784904(0x5c4d208, float:1.8508905E-35)
                    if (r1 == r2) goto L_0x0022
                    r2 = 1124446108(0x4305af9c, float:133.68597)
                    if (r1 == r2) goto L_0x0018
                    goto L_0x0036
                L_0x0018:
                    java.lang.String r1 = "warning"
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x0036
                    r0 = 2
                    goto L_0x0037
                L_0x0022:
                    java.lang.String r1 = "error"
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x0036
                    r0 = 0
                    goto L_0x0037
                L_0x002c:
                    java.lang.String r1 = "success"
                    boolean r0 = r0.equals(r1)
                    if (r0 == 0) goto L_0x0036
                    r0 = 1
                    goto L_0x0037
                L_0x0036:
                    r0 = -1
                L_0x0037:
                    r1 = 2131230857(0x7f080089, float:1.8077779E38)
                    r2 = 2131099724(0x7f06004c, float:1.781181E38)
                    if (r0 == 0) goto L_0x0051
                    if (r0 == r4) goto L_0x004b
                    if (r0 == r3) goto L_0x0044
                    goto L_0x0051
                L_0x0044:
                    r2 = 2131099726(0x7f06004e, float:1.7811813E38)
                    r1 = 2131230862(0x7f08008e, float:1.8077789E38)
                    goto L_0x0051
                L_0x004b:
                    r2 = 2131099728(0x7f060050, float:1.7811817E38)
                    r1 = 2131230855(0x7f080087, float:1.8077775E38)
                L_0x0051:
                    int r0 = android.os.Build.VERSION.SDK_INT
                    r3 = 20
                    if (r0 < r3) goto L_0x00ab
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = new com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog
                    android.content.Context r3 = r5
                    r0.<init>(r3)
                    java.lang.String r3 = r6
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeDialogBuilder r0 = r0.setTitle(r3)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = (com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog) r0
                    java.lang.String r3 = r7
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeDialogBuilder r0 = r0.setMessage(r3)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = (com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog) r0
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeDialogBuilder r0 = r0.setColoredCircle(r2)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = (com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog) r0
                    r3 = 2131099813(0x7f0600a5, float:1.781199E38)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeDialogBuilder r0 = r0.setDialogIconAndColor(r1, r3)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = (com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog) r0
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeDialogBuilder r0 = r0.setCancelable(r4)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = (com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog) r0
                    android.content.Context r1 = r5
                    r3 = 2131755124(0x7f100074, float:1.9141118E38)
                    java.lang.String r1 = r1.getString(r3)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = r0.setButtonText(r1)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = r0.setButtonBackgroundColor(r2)
                    android.content.Context r1 = r5
                    java.lang.String r1 = r1.getString(r3)
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = r0.setButtonText(r1)
                    com.videodownloader.twittervideoindir.ShowDialog$1$1 r1 = new com.videodownloader.twittervideoindir.ShowDialog$1$1
                    r1.<init>()
                    com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog r0 = r0.setErrorButtonClick(r1)
                    r0.show()
                    goto L_0x00b6
                L_0x00ab:
                    android.content.Context r0 = r5
                    java.lang.String r1 = r7
                    android.widget.Toast r0 = android.widget.Toast.makeText(r0, r1, r4)
                    r0.show()
                L_0x00b6:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.videodownloader.twittervideoindir.ShowDialog.AnonymousClass1.run():void");
            }
        };
        handler.post(r2);
    }
}