import com.source.bean.User;
import com.source.dao.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/27/14:37
 * @Description:
 */

public class testUser {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll(){
        List<User> users = userMapper.selectAll();
        users.forEach(s -> System.out.println(s));
    }

}
