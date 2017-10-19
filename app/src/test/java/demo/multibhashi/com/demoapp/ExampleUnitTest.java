package demo.multibhashi.com.demoapp;

import org.junit.Test;

import demo.multibhashi.com.demoapp.utilities.StringUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void string_match_percent() throws Exception {
        assertEquals(100, StringUtil.getMatchPercent("hege", "hege"));
        assertEquals(50, StringUtil.getMatchPercent("hege", "heige"));
        assertEquals(95, StringUtil.getMatchPercent("hege", "hegee"));
        assertEquals(50, StringUtil.getMatchPercent("hege", "hegeeeeeeeeeeeeeeeeeeeeeeeeeeeee"));
    }
}