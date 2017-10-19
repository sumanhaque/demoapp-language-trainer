package demo.multibhashi.com.demoapp.lessondetailslearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import demo.multibhashi.com.demoapp.R;
import demo.multibhashi.com.demoapp.network.models.Lesson;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class FragmentLearn extends Fragment
        implements PresenterLessonDetailsLearn.Listener {

    private static final String TAG = "FragmentLearn";

    private CoordinatorLayout clRoot;

    private FloatingActionButton fabPlay;
    private FloatingActionButton fabNext;

    private TextView tvConceptName;
    private TextView tvPronunciation;

    private Listener listener;

    private PresenterLessonDetailsLearn presenterLessonDetailsLearn;

    private Lesson lesson;

    private static abstract class BUNDLE_KEY {
        public static final String DATA = "lesson_data";
    }

    public interface Listener {
        public void goNext();
    }

    private void setListener(Listener listener) {
        this.listener = listener;
    }

    public FragmentLearn() {
    }

    public static FragmentLearn getInstance(Lesson lesson, Listener listener) {
        FragmentLearn fragmentLesson = new FragmentLearn();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY.DATA, lesson);
        fragmentLesson.setArguments(bundle);
        fragmentLesson.setListener(listener);
        return fragmentLesson;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterLessonDetailsLearn =
                new PresenterLessonDetailsLearn(getActivity(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            lesson = (Lesson) bundle.getSerializable(BUNDLE_KEY.DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clRoot = view.findViewById(R.id.cl_root);

        fabPlay = view.findViewById(R.id.fab_play);
        fabNext = view.findViewById(R.id.fab_next);

        tvConceptName = view.findViewById(R.id.tv_concept_name);
        tvPronunciation = view.findViewById(R.id.tv_pronunciation_data);

        if (lesson != null) {
            if (!lesson.getConceptName().isEmpty()) {
                tvConceptName.setText(lesson.getConceptName());
            }
            if (!lesson.getPronunciation().isEmpty()) {
                tvPronunciation.setText(lesson.getPronunciation());
            }
        }

        presenterLessonDetailsLearn.checkForAudioFile(lesson);

        hideFabs();

        setUpActionListeners();
    }

    private void setUpActionListeners() {
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterLessonDetailsLearn.playAudioFile(lesson);
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    presenterLessonDetailsLearn.stopMediaIfPlaying();
                    listener.goNext();
                }
            }
        });
    }

    private void hideFabs() {
        fabPlay.setVisibility(View.GONE);
        fabNext.setVisibility(View.GONE);
    }

    private void showFabs() {
        fabPlay.setVisibility(View.VISIBLE);
        fabNext.setVisibility(View.VISIBLE);
    }

    private void displayDownloadError() {
        Snackbar
                .make(
                        clRoot,
                        getResources()
                                .getString(R.string.err_download),
                        Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void audioFileReady(boolean status) {
        if (status) {
            showFabs();
        } else {
            displayDownloadError();
        }
    }

    @Override
    public void fileStatus(boolean isAvailable) {
        if (!isAvailable) {
            presenterLessonDetailsLearn.downloadAudioFile(lesson);
        } else {
            showFabs();
        }
    }
}
