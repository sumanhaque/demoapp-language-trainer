package demo.multibhashi.com.demoapp.lessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import demo.multibhashi.com.demoapp.lessondetails.ActvityDetails;
import demo.multibhashi.com.demoapp.lessondetails.AdapterLessons;
import demo.multibhashi.com.demoapp.R;
import demo.multibhashi.com.demoapp.network.models.Lesson;
import demo.multibhashi.com.demoapp.network.models.LessonData;
import demo.multibhashi.com.demoapp.utilities.ClickListener;
import demo.multibhashi.com.demoapp.utilities.RecyclerTouchListener;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class ActivityHome extends AppCompatActivity
        implements PresenterLessons.Listener {

    private static final String TAG = "ActivityHome";

    private CoordinatorLayout clRoot;
    private LinearLayout llEmptyView;
    private LinearLayout llErrorView;

    private RecyclerView rvLessons;

    private static AdapterLessons adapterLessons;

    private static LessonData lessonData;
    private static List<Lesson> listLessons;

    private PresenterLessons presenterLessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenterLessons = new PresenterLessons(this);

        clRoot = (CoordinatorLayout) findViewById(R.id.cl_root);
        llEmptyView = (LinearLayout) findViewById(R.id.ll_emptyview);
        llErrorView = (LinearLayout) findViewById(R.id.ll_errorview);
        rvLessons = (RecyclerView) findViewById(R.id.rv_lessons);

        initRecyclerView();
        setUpActionListners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        hideEmptyView();
        hideErrorView();
        presenterLessons.getLessons();
    }

    private void initRecyclerView() {

        listLessons = new ArrayList<Lesson>();
        adapterLessons = new AdapterLessons(listLessons);

        rvLessons.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvLessons.setItemAnimator(new DefaultItemAnimator());
        rvLessons.setAdapter(adapterLessons);
    }

    private void setUpActionListners() {
        rvLessons.addOnItemTouchListener(
                new RecyclerTouchListener(
                        getApplicationContext(),
                        rvLessons,
                        new ClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                displayLessonDetails(position);
                            }

                            @Override
                            public void onLongClick(View view, int position) {
                            }
                        }));
    }

    private void displayLessonDetails(int position) {
        Intent intent =
                ActvityDetails
                        .getIntent(
                                getBaseContext(),
                                lessonData,
                                position);
        startActivity(intent);
    }

    @Override
    public void lessonsReady(LessonData lessonData) {
        this.lessonData = lessonData;
        listLessons = lessonData.getLesson_data();
        adapterLessons.update(listLessons);

        if (listLessons.size() == 0) {
            displayEmptyView();
        }
    }

    @Override
    public void getLessonError(Throwable t) {
        Snackbar
                .make(
                        clRoot,
                        getResources()
                                .getString(R.string.err_get_lessons),
                        Snackbar.LENGTH_LONG)
                .show();
        displayErrorView();
    }

    private void displayEmptyView() {
        llEmptyView.setVisibility(View.VISIBLE);
    }

    private void hideEmptyView() {
        llEmptyView.setVisibility(View.GONE);
    }

    private void displayErrorView() {
        llErrorView.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        llErrorView.setVisibility(View.GONE);
    }
}
