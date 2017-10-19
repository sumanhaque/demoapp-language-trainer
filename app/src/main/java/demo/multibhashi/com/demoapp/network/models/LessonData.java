package demo.multibhashi.com.demoapp.network.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class LessonData implements Serializable {

    List<Lesson> lesson_data;

    public LessonData() {
    }

    public List<Lesson> getLesson_data() {
        return lesson_data;
    }

    @Override
    public String toString() {
        return "LessonData{" +
                "lesson_data=" + lesson_data +
                '}';
    }
}
