package demo.multibhashi.com.demoapp.lessons;

import demo.multibhashi.com.demoapp.network.NetworkClient;
import demo.multibhashi.com.demoapp.network.apis.ApiLesson;
import demo.multibhashi.com.demoapp.network.models.LessonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sumanhaque on 10/19/2017.
 */

public class PresenterLessons {

    public interface Listener {
        public void lessonsReady(LessonData lessonData);

        public void getLessonError(Throwable t);
    }

    private Listener listener;

    public PresenterLessons(Listener listener) {
        this.listener = listener;
    }

    public void getLessons() {
        ApiLesson client = NetworkClient.Build().create(ApiLesson.class);
        client.getLessons().enqueue(new Callback<LessonData>() {
            @Override
            public void onResponse(
                    Call<LessonData> call,
                    Response<LessonData> response) {
                LessonData lessonData = response.body();

                if (lessonData != null) {
                    if (listener != null) {
                        listener.lessonsReady(lessonData);
                    }
                } else {
                    if (listener != null) {
                        listener.getLessonError(new Throwable());
                    }
                }
            }

            @Override
            public void onFailure(
                    Call<LessonData> call,
                    Throwable t) {
                if (listener != null) {
                    listener.getLessonError(t);
                }
            }
        });
    }
}
