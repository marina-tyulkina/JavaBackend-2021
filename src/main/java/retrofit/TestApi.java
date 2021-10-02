package retrofit;

import org.junit.platform.commons.logging.LoggerFactory;
import retrofit.api.MiniMarketApi;
import retrofit.model.ProductDto;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class TestApi {

    public static void main(String[] args) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8189/market/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        MiniMarketApi marketService = retrofit.create(MiniMarketApi.class);

        ProductDto product = marketService.getProduct(1)
                .execute()
                .body();

        List<ProductDto> products = marketService.getProducts()
                .execute()
                .body();

        System.out.println("product: " + product);
        System.out.println("products: " + products);
        System.out.println();

        marketService.getProduct(5)
                .execute()
                .body();

        System.out.println("product: " + product);


/* @Override
                    public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<ProductDto> call, Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        System.out.println(1);
        System.out.println(2);*//*


        ProductDto dto = ProductDto.builder()
                .title("Coca cola")
                .categoryTitle("Food")
                .price(55)
                .build();

        ProductDto body = marketService.createProduct(dto).execute().body();
        System.out.println(body);

    }
*/

    }
}
