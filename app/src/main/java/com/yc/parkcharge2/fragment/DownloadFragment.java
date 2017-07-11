package com.yc.parkcharge2.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;
import com.yc.parkcharge2.BuildConfig;
import com.yc.parkcharge2.R;
import com.yc.parkcharge2.common.AbstractCallback;
import com.yc.parkcharge2.common.BaseFragment;
import com.yc.parkcharge2.common.Constants;
import com.yc.parkcharge2.service.FileDownloadService;
import com.yc.parkcharge2.util.HttpRequestUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@EFragment(R.layout.frag_download)
public class DownloadFragment extends BaseFragment {

    int newVersionNo;
    String fileName;
    @ViewById
    ProgressBar download_bar;


    //版本更新
    @AfterViews
    public void init(){
        String versionNo = this.getString(R.string.version_no);
        String url = this.getString(R.string.server_url)+"version/update";
        RequestParams params = new RequestParams();
        params.put("versionNo", versionNo);
        HttpRequestUtil.request(url, params, this.getActivity(), new AbstractCallback(this.getActivity()) {
            @Override
            public void doSuccess(JSONObject response) {
                if(DownloadFragment.this.checkVersion(response)){
                    //有新版本
                    DownloadFragment.this.showDialog();
                }else{
                    //已经是最新版本

                }
            }
        });
    }

    public boolean checkVersion(JSONObject response){
        try {
            JSONObject obj = response.getJSONObject(Constants.DATA);
            newVersionNo = obj.getInt("versionNo");
            int oldVersionNo = Integer.parseInt(this.getString(R.string.version_no));
            if(newVersionNo > oldVersionNo){
                //有新版本
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void showDialog(){
        new AlertDialog.Builder(this.getActivity()).setTitle("操作提示")
                .setMessage("检测到新版本，是否要升级？")
                .setPositiveButton("后台下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //发出通知
                        Intent intent = new Intent(DownloadFragment.this.getActivity(), FileDownloadService.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("newVersionNo", newVersionNo);
                        intent.putExtras(bundle);
                        //动态获得权限
                        int permission = ActivityCompat.checkSelfPermission(DownloadFragment.this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (permission != PackageManager.PERMISSION_GRANTED) {
                            // We don't have permission so prompt the user
                            ActivityCompat.requestPermissions(DownloadFragment.this.getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1
                            );
                        }
                        //DownloadFragment.this.getActivity().startService(intent);
                        DownloadFragment.this.downAPK(newVersionNo);
                    }
                })
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void downAPK(int versionNo){
        RequestParams params = new RequestParams();
        params.put("versionNo", versionNo);
        String url = this.getString(R.string.server_url)+"version/download";
        HttpRequestUtil.download(url, params, this.getActivity(), new AbstractCallback(this.getActivity()) {
            @Override
            public void doSuccess(JSONObject response) {

            }
            @Override
            public void doSuccess(byte[] bytes) {
                //下载完成后，保存apk
                if(saveAPK(bytes)){
                    //安装APK
                    installApp(fileName);
                }
            }
            //下载进度
            @Override
            public void doProgress(long bytesWritten, long totalSize) {
                super.doProgress(bytesWritten, totalSize);
                //RemoteViews contentview = notification.contentView;

                int rate = (int) (bytesWritten*100/totalSize);
                //contentview.setTextViewText(R.id.notice_txt, rate+"%");
                //contentview.setProgressBar(R.id.download_progressBar, 100, rate, false);
                //mBuilder.setContentText("下载完成,请点击安装");
                //mBuilder.setProgress(rate, 100,false);
                //mBuilder.setContentIntent(mPendingIntent);
                //mNotificationManager.notify(NotificationID, mBuilder.build());

                download_bar.setProgress(rate);
            }
        });
    }

    /**
     * 方法说明：保存APK
     */
    public boolean saveAPK(byte[] bytes){
        String appCode = File.separator+this.getString(R.string.app_code);
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        File fileDir = new File(dirPath);
        if(!fileDir.exists()) {
            fileDir.mkdirs();
        }
        this.fileName = dirPath+appCode+".apk";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        OutputStream out =null;
        InputStream in = null;
        try {

            file.createNewFile();
            out = new FileOutputStream(file);
            in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            int len = 0;
            long readedLength = 0l;
            while((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                readedLength += len;
            }
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out !=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void installApp(String fileName) {

        File appFile = new File(fileName);
        if(!appFile.exists()) {
            return;
        }

        // 跳转到新版本应用安装页面
        Intent installIntent = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this.getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", appFile);
            installIntent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else{
            installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.fromFile(appFile), "application/vnd.android.package-archive");
        }
        PendingIntent mPendingIntent = PendingIntent.getActivity(this.getActivity(), 0, installIntent, 0);
        //mBuilder.setContentText("下载完成,请点击安装");
        //mBuilder.setContentIntent(mPendingIntent);
        //mNotificationManager.notify(NotificationID, mBuilder.build());
        startActivity(installIntent);
    }

    @Override
    protected void doSuccess() {

    }
}
