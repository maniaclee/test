package maniac.lee.test.userrpc;

import com.alibaba.fastjson.JSON;
import com.lvbby.user.api.dto.UserDTO;
import com.lvbby.user.api.request.UserRegisterRequest;
import com.lvbby.user.api.response.UserRegisterResponse;
import com.lvbby.user.api.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAspectJAutoProxy
@ImportResource("userrpc/client.xml")
@ComponentScan("maniac.lee.test.userrpc")
@ContextConfiguration(classes = {Consumer.class})
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class Consumer {

    @Autowired
    UserService userService;

    @Test
    public void esdf() {
        UserDTO entity = new UserDTO();
        entity.setName("sdsfsdfxxx");
        entity.setAddTime(new Date());
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserDTO(entity);
        UserRegisterResponse re = userService.add(userRegisterRequest);
        System.out.println("add : ===> " + JSON.toJSONString(re));
    }


    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"userrpc/client.xml"});
        context.start();

        UserService userService = (UserService) context.getBean("userService");// 获取远程服务代理
        //        UserService userService = context.getBean(UserService.class);// 获取远程服务代理
        UserDTO entity = new UserDTO();
        entity.setName("sdsfsdf");
        entity.setAddTime(new Date());
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserDTO(entity);
        UserRegisterResponse re = userService.add(userRegisterRequest);
        System.out.println("add : ===> " + JSON.toJSONString(re));

    }

}