import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit.api.MiniMarketApi;
import retrofit.model.CategoryDto;
import retrofit.model.ProductDto;
import retrofit.RetrofitHelper;
import retrofit2.Response;


import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
//import static org.junit.jupiter.api.AssertEquals.assertEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.reflectionassert.ReflectionAssertions.assertReflectiveThat;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestMarket {

    private static MiniMarketApi service;
    static RetrofitHelper retrofit;
    ProductDto productDto;
    CategoryDto categoryDto;
    int id;

    @BeforeAll
    static void beforeAll() {
        service = RetrofitHelper.getService();
    }

    @Test
    @DisplayName("Получение информации о продукте")
    @Order(1)
    void testGetProductById() throws IOException {
        ProductDto actually = service.getProduct(1)
                .execute()
                .body();
        ProductDto expected = ProductDto.builder()
                .id(1)
                .title("Milk")
                .price(95)
                .categoryTitle("Food")
                .build();
        assertReflectiveThat(actually).isEqualTo(expected);
    }

    @Test
    @DisplayName("Создание нового продукта")
    @Order(2)
    void testCreateNewProduct() throws IOException {
        productDto = ProductDto.builder()
                .title("LapTop")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price(200000)
                .build();

        Response<ProductDto> responsePostProduct = service
                .createProduct(productDto)
                .execute();

        assertReflectiveThat(responsePostProduct.body().getCategoryTitle()).isEqualTo(productDto.getCategoryTitle());
        assertReflectiveThat(responsePostProduct.body().getTitle()).isEqualTo(productDto.getTitle());
        assertReflectiveThat(responsePostProduct.body().getPrice()).isEqualTo(productDto.getPrice());
    }

    @Test
    @DisplayName("Получение созданного продукта")
    @Order(3)
    void testGetProduct() throws IOException {
        productDto = ProductDto.builder()
                .title("LapTop")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price(200000)
                .build();
        Response<ProductDto> responseGetProduct = service
                .getProduct(productDto.getId())
                .execute();
        assertReflectiveThat(responseGetProduct.body().getCategoryTitle()).isEqualTo(productDto.getCategoryTitle());
        assertReflectiveThat(responseGetProduct.body().getTitle()).isEqualTo(productDto.getTitle());
        assertReflectiveThat(responseGetProduct.body().getPrice()).isEqualTo(productDto.getPrice());
    }

    @Test
    @DisplayName("Создание продукта с уже существующим id")
    @Order(4)
    void testCreateWithSameIdProduct() throws IOException {
        productDto = ProductDto.builder()
                .id(1)
                .title("Sony TV")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price(200000)
                .build();

        Response<ProductDto> responsePostProduct = service
                .createProduct(productDto)
                .execute();

        assertThat(responsePostProduct.code(), equalTo(400));
    }

    @Test
    @DisplayName("Получение продукта с несуществующим ID")
    @Order(6)
    void testGetProductByIdNegative() throws IOException {

        productDto = ProductDto.builder()
                .title("LapTop")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price(200000)
                .build();

        Response<ProductDto> response = service
                .getProduct(productDto.getId())
                .execute();

        assertThat(response.code(), equalTo(404));
    }

    @Test
    @DisplayName("Получение списка всех продуктов")
    @Order(7)
    void testGetListAllProducts() throws IOException {
        Response<List<ProductDto>> response = service
                .getProducts()
                .execute();
        assertThat(response.raw(), CoreMatchers.not(equalTo("0")));
    }

    @Test
    @DisplayName("Создание пустого продукта")
    @Order(5)
    void testCreateNewProductEmptyFields() throws IOException {
        ProductDto dto = ProductDto.builder()
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .build();
        Response<ProductDto> response = service
                .createProduct(new ProductDto())
                .execute();
        assertThat(response.code(), equalTo(400));
    }

    @Test
    @DisplayName("Удаление продукта")
    @Order(8)
    void testDeleteProduct() throws IOException {
        productDto = ProductDto.builder()
                .title("LapTop")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price(200000)
                .build();
        Response<ResponseBody> response = service
                .deleteProduct(productDto.getId())
                .execute();
        assertThat(response.errorBody());
    }

    @Test
    @DisplayName("Удаление продукта с несуществующим ID")
    @Order(9)
    void testDeleteWrongProduct() throws IOException {
        Response<ResponseBody> response = service
                .deleteProduct(1000)
                .execute();
        assertThat(response.code(), equalTo(500));
    }

    @Test
    @DisplayName("Изменение продукта")
    @Order(10)
    void testUpdateProduct() throws IOException {
        ProductDto newProduct = new ProductDto();
        newProduct = ProductDto.builder()
                .title("DVD Player")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price((int) (Math.random() * 1000 + 1))
                .build();
        Response<ProductDto> response = service
                .updateProduct(newProduct)
                .execute();
        assertThat(response.body().getId(), equalTo(id));
        assertThat(response.body().getPrice(), equalTo(newProduct.getPrice()));
        assertThat(response.body().getTitle(), equalTo(newProduct.getTitle()));
        assertThat(response.body().getCategoryTitle(), equalTo(newProduct.getCategoryTitle()));
    }

    @Test
    @DisplayName("Изменение продукта с неправильным ID")
    @Order(11)
    void testUpdateProductWrongId() throws IOException {
        ProductDto newProduct = new ProductDto();
        newProduct = ProductDto.builder()
                .id(100000)
                .title("LapTop")
                .categoryTitle(CategoryDto.ELECTRONIC.getTitle())
                .price((int) (Math.random() * 1000 + 1))
                .build();
        Response<ProductDto> response = service
                .updateProduct(newProduct)
                .execute();
        assertThat(response.code(), equalTo(400));
    }

    @Test
    @DisplayName("Получение информации о категории по id")
    @Order(12)
    void testGetCategory() throws IOException {

        Response<CategoryDto> categoryDto = service
                .getCategory(2)
                .execute();
        assertReflectiveThat(categoryDto.body().getTitle()).isEqualTo("Electronic");
        assertThat(categoryDto.body().getId(), equalTo(id));

    }
}