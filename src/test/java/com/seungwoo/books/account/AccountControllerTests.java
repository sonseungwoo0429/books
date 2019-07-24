package com.seungwoo.books.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountController.class, secure = false)
public class AccountControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @SpyBean
    private ModelMapper modelMapper;


    @Test
    public void login_test() throws Exception {
        mvc.perform(get("/login")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("password")));
    }

    @Test
    public void member_join_test() throws Exception {
        mvc.perform(get("/member_join")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("password")));
    }

    @Test
    public void singupUser_test() throws Exception {
        final Account account = new Account("test@test.com", "0000");
        given(accountService.signup(any())).willReturn(account);
        mvc.perform(post("/singup/user")
                .param("username", "test@test.com")
                .param("password", "0000")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }
}
