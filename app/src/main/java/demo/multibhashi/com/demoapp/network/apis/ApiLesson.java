package demo.multibhashi.com.demoapp.network.apis;


import demo.multibhashi.com.demoapp.network.models.LessonData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public interface ApiLesson {

    @GET("/getLessonData.php")
    public Call<LessonData> getLessons();

}
