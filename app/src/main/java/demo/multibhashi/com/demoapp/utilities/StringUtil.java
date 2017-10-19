package demo.multibhashi.com.demoapp.utilities;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class StringUtil {

    private static final String TAG = "StringUtil";

    public static int getMatchPercent(String src, String test) {

        final int DIFF_SUB_FACTOR = 5;

        int lengthSrc = src.length();
        int lengthTest = test.length();

        char[] charsSrc = src.toCharArray();
        char[] charsTest = test.toCharArray();

        int matchCount = 0;
        for (int i = 0; i < lengthSrc; i++) {
            if (charsSrc[i] == charsTest[i]) {
                matchCount++;
            } else {
                break;
            }
        }
        int percent = (int) ((matchCount * 100) / lengthSrc);
        if (matchCount == lengthSrc) {
            if (lengthSrc < lengthTest) {
                int diff = lengthTest - lengthSrc;
                percent = percent - diff * DIFF_SUB_FACTOR;
                if (percent < 50)
                    percent = 50;
            }
        }
        return percent;
    }
}
