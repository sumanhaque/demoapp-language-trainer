package demo.multibhashi.com.demoapp.lessondetails;

import android.content.Context;
import android.support.v4.app.Fragment;

import demo.multibhashi.com.demoapp.lessondetailslearn.FragmentLearn;
import demo.multibhashi.com.demoapp.lessondetailsquestion.FragmentQuestion;
import demo.multibhashi.com.demoapp.network.models.Lesson;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class PresenterLessonDetails {

    public interface Listener {
        public void fragmentReady(Fragment fragment);
    }

    private Context context;
    private Listener listener;

    public PresenterLessonDetails(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getFragment(Lesson lesson) {
        switch (lesson.getType()) {
            case LessonType.LEARN:
                FragmentLearn fragmentLearn =
                        FragmentLearn.getInstance(lesson, (FragmentLearn.Listener) context);
                if (listener != null) {
                    listener.fragmentReady(fragmentLearn);
                }

                break;
            case LessonType.QUESTION:
                FragmentQuestion fragmentQuestion =
                        FragmentQuestion.getInstance(lesson, (FragmentQuestion.Listener) context);
                if (listener != null) {
                    listener.fragmentReady(fragmentQuestion);
                }
                break;
            case LessonType.QUIZ:
                FragmentQuestion fragmentQuiz =
                        FragmentQuestion.getInstance(lesson, (FragmentQuestion.Listener) context);
                if (listener != null) {
                    listener.fragmentReady(fragmentQuiz);
                }
                break;
            default:
                if (listener != null) {
                    listener.fragmentReady(null);
                }
        }
    }
}
