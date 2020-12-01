package de.smartformer.articlesapi.service;

import de.smartformer.articlesapi.model.Article;

public interface ArticleService {

    public abstract Article createArticle(Article newArticle);
    public abstract Article updateArticle(Integer id, Article newArticle);
    public abstract void deleteArticle(Integer id);
    public abstract Article getArticle(Integer id);
    public abstract Iterable<Article> getArticles();
    
}
