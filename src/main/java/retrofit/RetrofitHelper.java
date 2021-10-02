package retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.api.MiniMarketApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitHelper {

    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    static OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY)).build();

    public static MiniMarketApi getService() {

        return new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build()
                .create(MiniMarketApi.class);
    }
}