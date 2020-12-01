import com.source.boot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/30/14:17
 * @Description:
 */
@SpringBootTest(classes = boot.class)
@RunWith(SpringRunner.class)
public class BCryptTest {

    @Test
    public void testBCryptTest(){
        // BCrypt.gensalt()自动加盐
        // 对密码进行加密
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println(hashpw);

        // 校验密码
        boolean checkpw = BCrypt.checkpw("123", "$2a$10$apWJoMsh1deqjtLeuOwAp.pe08NOglnX1xfMydAvDK6qjlSrkrRt.");
        boolean checkpw1 = BCrypt.checkpw("123", hashpw);
        System.out.println(checkpw);
        System.out.println(checkpw1);


    }


}
