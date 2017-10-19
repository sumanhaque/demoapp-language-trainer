package demo.multibhashi.com.demoapp.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class NetworkClient {

    static String API_BASE_URL = "http://www.akshaycrt2k.com/";

    public static Retrofit Build() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
        return retrofit;
    }
}
