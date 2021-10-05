
import db.dao.CategoriesMapper;
import db.dao.ProductsMapper;
import db.model.Products;
import mybatis.MyBatisDbHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import retrofit.model.CategoryDto;
import retrofit.model.ProductDto;
import retrofit2.Response;

import java.io.IOException;

import static com.github.reflectionassert.ReflectionAssertions.assertReflectiveThat;
import static org.assertj.core.api.Assertions.assertThat;

public class Tests {

    private static MyBatisDbHelper dbHelper;
    private static ProductsMapper productsMapper;
    private static CategoriesMapper categoriesMapper;

    @BeforeAll
    static void beforeAll() {
        dbHelper = new MyBatisDbHelper();
        productsMapper = dbHelper.getProductsMapper();
        categoriesMapper = dbHelper.getCategoriesMapper();
    }

    @Test
    void testGetProductById() {
        Products actually = productsMapper.selectByPrimaryKey(1L);
        Products expected = new Products();
        expected.setId(1L);
        expected.setTitle("Milk");
        expected.setPrice(95);
        expected.setCategoryId(1L);

        assertReflectiveThat(actually).isEqualTo(expected);
    }

    @Test
    void testDeleteProductById() {
        productsMapper.deleteByPrimaryKey(5L);

        Products productDelete = productsMapper.selectByPrimaryKey(5L);
        System.out.println(productDelete);

        assertReflectiveThat(productDelete).isEqualTo(null);
    }
}
