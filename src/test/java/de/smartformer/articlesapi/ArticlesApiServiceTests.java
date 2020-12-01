package de.smartformer.articlesapi;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import de.smartformer.articlesapi.controller.ArticleController;
import de.smartformer.articlesapi.service.ArticleService;
import de.smartformer.articlesapi.model.Article;

@WebMvcTest(ArticleController.class)
@ActiveProfiles("test") // override application.yml with application-test.yml
public class ArticlesApiServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService service;

    private final Logger log = LoggerFactory.getLogger(ArticlesApiServiceTests.class);

    @Test
    public void arcticlesShouldReturnEmptyList() throws Exception {

        log.info(String.format("[2001] RETURN EMPTY LIST TEST"));

        final Iterable<Article> articles = Collections.emptyList();

        when(service.getArticles()).thenReturn(articles);

        this.mockMvc.perform(get("/articles")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json("[]"));
    }

    @Test
    public void arcticlesShouldReturnOneItem() throws Exception {

        log.info(String.format("[2002] RETURN ONE ITEM TEST"));

        final ArrayList<Article> articles = new ArrayList<Article>();
        articles.add(new Article("First Article", "This is my very first article with lots of content"));
        
        when(service.getArticles()).thenReturn(articles);

        this.mockMvc.perform(get("/articles")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("This is my very first article with lots of content")));
    }
}
