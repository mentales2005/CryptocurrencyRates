package ps.foxy.cryptocurrencyrates.LiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// In this class we setting up Retrofit instance and the base URL.
public class Retrofit_instance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.coinranking.com/v2/";

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
