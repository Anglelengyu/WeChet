package cf.shuhan;

import cf.shuhan.domain.Chengyu;
import cf.shuhan.mapper.ChengyuMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {
    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    @Test
    public void test() throws Exception{
        // sql的唯一标识：statement Unique identifier matching the statement to use.
        // 执行sql要用的参数：parameter A parameter object to pass to the statement.
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            ChengyuMapper chengyuMapper = openSession.getMapper(ChengyuMapper.class);
            List<Chengyu> chengyuByName = chengyuMapper.getChengyuByName("");
            for (Chengyu chengyu : chengyuByName) {
                System.out.println(chengyu);
            }
        } finally {
            openSession.close();
        }
    }
}
