package com.peak.ggy_blog;

import com.peak.pojo.Blog;
import com.peak.pojo.Type;
import com.peak.pojo.User;
import com.peak.service.BlogService;
import com.peak.service.TypeService;
import com.peak.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GgyBlogApplicationTests {

    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
     User user = userService.cheakUser("admin","123456");
        System.out.println(user);
    }

}
