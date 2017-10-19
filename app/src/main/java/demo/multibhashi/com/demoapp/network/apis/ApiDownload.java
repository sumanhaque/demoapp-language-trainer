package demo.multibhashi.com.demoapp.network.apis;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public interface ApiDownload {

    @GET
    Call<ResponseBody> downloadAudio(@Url String url);
}
