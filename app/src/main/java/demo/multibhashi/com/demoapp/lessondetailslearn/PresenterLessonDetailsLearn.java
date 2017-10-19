package demo.multibhashi.com.demoapp.lessondetailslearn;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.File;

import demo.multibhashi.com.demoapp.network.models.Lesson;
import demo.multibhashi.com.demoapp.utilities.FileUtil;
import demo.multibhashi.com.demoapp.utilities.TaskDownload;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class PresenterLessonDetailsLearn {

    private static final String TAG_HANDER_NAME = "DOWNLOAD_AUDIO_FILE";

    public interface Listener {
        public void audioFileReady(boolean status);

        public void fileStatus(boolean isAvailable);
    }

    private Context context;
    private Listener listener;
    private HandlerThread handlerThread;
    private Handler handler;
    private MediaPlayer mp;

    public PresenterLessonDetailsLearn(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void downloadAudioFile(final Lesson lesson) {
        handlerThread = new HandlerThread(TAG_HANDER_NAME);
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String url = (String) msg.getData().get(TaskDownload.BUNDLE_KEYS.URL);
                final boolean status = (boolean) msg.getData().get(TaskDownload.BUNDLE_KEYS.STATUS);
                if (url.equals(lesson.getAudio_url())) {
                    if (listener != null) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.audioFileReady(status);
                            }
                        });
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    handlerThread.quitSafely();
                } else {
                    handlerThread.quit();
                }
            }
        };

        String path = FileUtil.getDownloadPath(context, lesson.getAudio_url());
        TaskDownload taskDownload = new TaskDownload(handler, lesson.getAudio_url(), path);
        handler.post(taskDownload);
    }

    public void playAudioFile(Lesson lesson) {
        String path = FileUtil.getDownloadPath(context, lesson.getAudio_url());
        File file = new File(path);
        if (file.exists()) {
            mp = new MediaPlayer();
            try {
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopMediaIfPlaying() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.stop();
            }
        }
    }

    public void checkForAudioFile(Lesson lesson) {
        AsyncTask<String, Integer, Boolean> taskFileCheck =
                new AsyncTask<String, Integer, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        String url = params[0];
                        String path = FileUtil.getDownloadPath(context, url);
                        File file = new File(path);
                        if (file.exists())
                            return true;
                        else
                            return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean res) {
                        super.onPostExecute(res);
                        if (listener != null) {
                            listener.fileStatus(res);
                        }
                    }
                };

        String url = lesson.getAudio_url();
        if (!url.isEmpty())
            taskFileCheck.execute(url);
    }
}
