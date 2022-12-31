package com.project.owlback;


import com.project.owlback.url.repository.UrlRepository;
import com.project.owlback.url.service.UrlServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class UrlServiceTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UrlServiceImpl urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    @DisplayName("Url 실시간 검색")
    public  void getUrlRealtime() throws  Exception {


    }
}

