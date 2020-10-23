package com.bkipmlampung.appikceria.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.bkipmlampung.appikceria.BuildConfig;
import com.bkipmlampung.appikceria.R;

public class DownloadFileUtil {

    public static void StartDownloading(String url, Context context){

        Toast.makeText(context, context.getString(R.string.berkas_didownload), Toast.LENGTH_SHORT).show();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(BuildConfig.BASE_URL + url));
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(context.getString(R.string.download));
        request.setDescription(context.getString(R.string.mengunduh_berkas));
        request.setMimeType("application/pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis()+".pdf");

        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

}
