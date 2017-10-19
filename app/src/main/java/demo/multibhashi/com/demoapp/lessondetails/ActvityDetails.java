package demo.multibhashi.com.demoapp.lessondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import demo.multibhashi.com.demoapp.R;
import demo.multibhashi.com.demoapp.lessondetailslearn.FragmentLearn;
import demo.multibhashi.com.demoapp.lessondetailsquestion.FragmentQuestion;
import demo.multibhashi.com.demoapp.network.models.Lesson;
import demo.multibhashi.com.demoapp.network.models.LessonData;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class ActvityDetails extends AppCompatActivity
        implements PresenterLessonDetails.Listener,
        FragmentLearn.Listener, FragmentQuestion.Listener {

    private static final String TAG = "ActvityDetails";
    private RelativeLayout rlEmptyView;
    private LessonData lessonData;
    private List<Lesson> listLessons;
    private int position;

    private Fragment currenctFragment;

    private PresenterLessonDetails presenterLessonDetails;

    private abstract class BUNDLE_KEYS {
        public static final String LESSON_DATA = "lesson_data";
        public static final String POSITION = "position";
    }

    public static Intent getIntent(Context context, LessonData lessonData, int position) {
        Intent intent = new Intent(context, ActvityDetails.class);
        intent.putExtra(BUNDLE_KEYS.LESSON_DATA, lessonData);
        intent.putExtra(BUNDLE_KEYS.POSITION, position);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        presenterLessonDetails = new PresenterLessonDetails(this, this);
        rlEmptyView = (RelativeLayout) findViewById(R.id.rl_empty_view);
        lessonData = (LessonData) getIntent().getSerializableExtra(BUNDLE_KEYS.LESSON_DATA);
        position = (int) getIntent().getIntExtra(BUNDLE_KEYS.POSITION, 0);
        if (lessonData != null) {
            listLessons = lessonData.getLesson_data();
            if (position < listLessons.size()) {
                Lesson lesson = listLessons.get(position);
                presenterLessonDetails.getFragment(lesson);
            } else {
                displayEmptyView();
            }
        } else {
            displayEmptyView();
        }
    }

    private void displayEmptyView() {
        rlEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void goNext() {
        position++;
        if (position >= (listLessons.size())) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(currenctFragment)
                    .commit();
            displayEmptyView();
        } else {
            Lesson lesson = listLessons.get(position);
            presenterLessonDetails.getFragment(lesson);
        }
    }

    @Override
    public void fragmentReady(Fragment fragment) {
        if (fragment != null) {
            currenctFragment = fragment;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_fragment, fragment)
                    .commit();
        }
    }
}
