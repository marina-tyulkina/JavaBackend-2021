package mybatis;

import java.util.List;
import db.dao.ProductsMapper;
import db.model.Products;
import db.model.ProductsExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.h2.engine.User;

public class Test {
    public static void main(String[] args) {
        SqlSessionFactory sessionFactory =
                new SqlSessionFactoryBuilder()
                        .build(User.class.getResourceAsStream("mybatis/mybatis-config.xml"));

        SqlSession session = sessionFactory.openSession();

        ProductsMapper mapper = session.getMapper(ProductsMapper.class);
        Products product = mapper.selectByPrimaryKey(1L);
        System.out.println(product);

        ProductsExample example = new ProductsExample();
        example.createCriteria()
                .andTitleIn(List.of("Milk", "Bread"));

        List<Products> example1 = mapper.selectByExample(example);
        System.out.println(example1);
    }
}
