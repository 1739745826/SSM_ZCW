import cn.cl.love.bean.User;
import cn.cl.love.manager.dao.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/29 - 15:18
 */
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class InsertUser {
    @Autowired
    private SqlSession sqlSession;

    @Test
    public void test() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        for (int i = 1; i <= 200; i++) {
            userMapper.insert(new User(null, "abcdef" + i, "abcdef" + i, "高志红", i + "@qq,com", "2020-1-29"));
        }
        System.out.println("完成");
    }
}
