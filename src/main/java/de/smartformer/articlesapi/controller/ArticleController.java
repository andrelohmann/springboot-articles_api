package de.smartformer.articlesapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.smartformer.articlesapi.model.Article;
import de.smartformer.articlesapi.service.ArticleService;

@RestController
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    public @ResponseBody Iterable<Article> all() {

        return articleService.getArticles();        
    }

    @PostMapping("/articles")
    public Article newArticle(@RequestBody Article newArticle) {

        return articleService.createArticle(newArticle);
    }

    // Single item

    @GetMapping("/articles/{id}")
    public Article one(@PathVariable Integer id) {

        return articleService.getArticle(id);
    }

    @PutMapping("/articles/{id}")
    public Article replaceArticle(@RequestBody Article newArticle, @PathVariable Integer id) {

        return articleService.updateArticle(id, newArticle);
    }

    @DeleteMapping("/articles/{id}")
    void deleteEmployee(@PathVariable Integer id) {

        articleService.deleteArticle(id);
    }
}