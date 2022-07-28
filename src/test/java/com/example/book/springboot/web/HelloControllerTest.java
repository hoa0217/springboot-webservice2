package com.example.book.springboot.web;

import com.example.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// TODO Mock객체에 컨트롤러 주입이 제대로 안되기 때문에 강제로 지정해서 넣어줘야함
//1.
//@SpringBootTest
//@AutoConfigureMockMvc
//2. WebMvcTest는 컨트롤러는 읽지만, 레파지토리 서비스 컴포넌트는 스캔대상이 아님. 따라서 CustomOAuth2UserService를 읽을 수 없음. 해결을 위해 스캔대상에서 SecurityConfig제거.
@WebMvcTest(value = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello_리턴() throws Exception{
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void helloDto_리턴() throws Exception{
        String name = "test";
        int amount = 1;

        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
   }
}