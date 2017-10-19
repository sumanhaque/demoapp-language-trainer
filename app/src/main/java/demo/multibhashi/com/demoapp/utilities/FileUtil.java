package demo.multibhashi.com.demoapp.utilities;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class FileUtil {

    public static String getFileName(String url) {
        String[] bufferUrlParts = url.split("/");
        String filename = bufferUrlParts[bufferUrlParts.length - 1];
        return filename;
    }

    public static String getDefaultDownloadPath(Context context) {
        String defaultDownloadPath = context.getCacheDir().getPath();
        return defaultDownloadPath;
    }

    public static String getDownloadPath(Context context, String url) {
        String downloadPath =
                getDefaultDownloadPath(context) + File.separator + getFileName(url);
        return downloadPath;
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body, String downloadPath) {
        try {
            File audioFile = new File(downloadPath);
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(audioFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
