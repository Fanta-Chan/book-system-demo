package com.tecpal.codetest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecpal.codetest.book.BookEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

@SpringBootTest
@AutoConfigureMockMvc
class CodeTestApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void contextLoads() throws Exception {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/book");
        post.content("{\"title\": \"title for test case\",\"description\": \"description for test case\",\"author\": \"author for test case\",\"publishDate\": \"2022-11-20\"}");
        post.accept(MediaType.APPLICATION_JSON);
        post.contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(post)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MockHttpServletRequestBuilder list = MockMvcRequestBuilders.get("/book");
        list.accept(MediaType.APPLICATION_JSON);
        list.contentType(MediaType.APPLICATION_JSON);
        MvcResult listResult = mockMvc.perform(list)
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().string(Matchers.containsString("title"))
                )
                .andReturn();

        List<BookEntity> books = mapper.readValue(listResult.getResponse().getContentAsString(), new TypeReference<List<BookEntity>>() {});

        System.out.println("Check: /book/" + books.get(0).getId());

        MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/book/" + books.get(0).getId());
        get.accept(MediaType.APPLICATION_JSON);
        get.contentType(MediaType.APPLICATION_JSON);
        MvcResult getResult = mockMvc.perform(get)
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().string(Matchers.containsString("title"))
                )
                .andReturn();
        BookEntity book = mapper.readValue(getResult.getResponse().getContentAsString(), BookEntity.class);

        MockHttpServletRequestBuilder delete = MockMvcRequestBuilders.delete("/book/" + book.getId());
        delete.accept(MediaType.APPLICATION_JSON);
        delete.contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(delete)
                .andExpect(
                        MockMvcResultMatchers.status().isAccepted()
                )
                .andReturn();
    }
}
