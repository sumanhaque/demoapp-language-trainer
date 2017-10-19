package demo.multibhashi.com.demoapp.lessondetailsquestion;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import demo.multibhashi.com.demoapp.R;
import demo.multibhashi.com.demoapp.network.models.Lesson;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class FragmentQuestion extends Fragment
        implements PresenterLessonDetailsQuestion.Listener {

    private static final String TAG = "FragmentQuestion";

    private Lesson lesson;

    private CoordinatorLayout clRoot;
    private Snackbar snackbarMatchDetails;

    private FloatingActionButton fabPlay;
    private FloatingActionButton fabNext;

    private TextView tvConceptName;
    private TextView tvPronunciation;

    private LinearLayout llRecord;

    private Listener listener;

    private PresenterLessonDetailsQuestion presenterLessonDetailsQuestion;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private static abstract class BUNDLE_KEY {
        public static final String DATA = "lesson_data";
    }

    public interface Listener {
        public void goNext();
    }

    private void setListener(Listener listener) {
        this.listener = listener;
    }

    public FragmentQuestion() {
    }

    public static FragmentQuestion getInstance(Lesson lesson, Listener listener) {
        FragmentQuestion fragmentLesson = new FragmentQuestion();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY.DATA, lesson);
        fragmentLesson.setArguments(bundle);
        fragmentLesson.setListener(listener);
        return fragmentLesson;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterLessonDetailsQuestion =
                new PresenterLessonDetailsQuestion(getActivity(), this);
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
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clRoot = view.findViewById(R.id.cl_root);

        fabPlay = view.findViewById(R.id.fab_play);
        fabNext = view.findViewById(R.id.fab_next);

        tvConceptName = view.findViewById(R.id.tv_concept_name);
        tvPronunciation = view.findViewById(R.id.tv_pronunciation_data);

        llRecord = view.findViewById(R.id.ll_record);

        if (lesson != null) {
            if (!lesson.getConceptName().isEmpty()) {
                tvConceptName.setText(lesson.getConceptName());
            }
            if (!lesson.getPronunciation().isEmpty()) {
                tvPronunciation.setText(lesson.getPronunciation());
            }
        }

        presenterLessonDetailsQuestion.checkForAudioFile(lesson);

        hideFabs();

        setUpActionListeners();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String text = result.get(0);

                    presenterLessonDetailsQuestion
                            .matchSpeechWithText(lesson.getPronunciation(), text);
                }
                break;
            }
        }
    }

    private void setUpActionListeners() {
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterLessonDetailsQuestion.playAudioFile(lesson);
            }
        });

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    presenterLessonDetailsQuestion.stopMediaIfPlaying();
                    listener.goNext();
                }
            }
        });

        llRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void hideFabs() {
        fabPlay.setVisibility(View.GONE);
        fabNext.setVisibility(View.GONE);
    }

    private void showFabs() {
        fabPlay.setVisibility(View.VISIBLE);
        fabNext.setVisibility(View.VISIBLE);
    }

    private void displaymatchDetails(int percent) {
        snackbarMatchDetails = Snackbar
                .make(clRoot, percent + "% match.", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbarMatchDetails.dismiss();
                    }
                });

        snackbarMatchDetails.show();
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
            presenterLessonDetailsQuestion.downloadAudioFile(lesson);
        } else {
            showFabs();
        }
    }

    @Override
    public void speechMatchResult(int percent) {
        displaymatchDetails(percent);
    }
}
