package demo.multibhashi.com.demoapp.utilities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import demo.multibhashi.com.demoapp.network.NetworkClient;
import demo.multibhashi.com.demoapp.network.apis.ApiDownload;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class TaskDownload implements Runnable {

    private Handler handler;
    private String targetUrl;
    private String downloadPath;

    public abstract class BUNDLE_KEYS {
        public static final String URL = "url";
        public static final String STATUS = "status";
    }

    public TaskDownload(Handler handler, String targetUrl, String downloadPath) {
        this.handler = handler;
        this.targetUrl = targetUrl;
        this.downloadPath = downloadPath;
    }

    @Override
    public void run() {
        ApiDownload apiDownload = NetworkClient.Build().create(ApiDownload.class);
        try {
            Response<ResponseBody> response =
                    apiDownload.downloadAudio(targetUrl).execute();
            boolean res =
                    FileUtil.writeResponseBodyToDisk(response.body(), downloadPath);
            sendStatusReport(res);

        } catch (IOException e) {
            sendStatusReport(false);
        }
    }

    private void sendStatusReport(boolean status) {
        Message mStatus = handler.obtainMessage();
        Bundle downloadStatus = new Bundle();
        downloadStatus.putString(BUNDLE_KEYS.URL, targetUrl);
        downloadStatus.putBoolean(BUNDLE_KEYS.STATUS, status);
        mStatus.setData(downloadStatus);
        handler.dispatchMessage(mStatus);
    }
}
