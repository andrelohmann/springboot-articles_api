package de.smartformer.articlesapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.smartformer.articlesapi.model.Article;
import de.smartformer.articlesapi.exception.ArticleNotFoundException;
import de.smartformer.articlesapi.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository repository;

    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Article createArticle(Article article) {

        log.info(String.format("[4001] Create new article '%s'",  article.getTitle()));

        return repository.save(article);
    }

    @Override
    public Article updateArticle(Integer id, Article newArticle) {

        return repository.findById(id)
            .map(article -> {
                article.setTitle(newArticle.getTitle());
                article.setContent(newArticle.getContent());

                log.info(String.format("[4003] Update article id '%s'",  newArticle.getId()));

                return repository.save(article);
            })
            .orElseGet(() -> {
                newArticle.setId(id);

                log.info(String.format("[4004] Create new article '%s'",  newArticle.getTitle()));

                return repository.save(newArticle);
            });
    }

   @Override
   public void deleteArticle(Integer id) {
        
        log.info(String.format("[4005] Delete article id '%s'",  id));

        repository.deleteById(id);
   }

   @Override
   public Article getArticle(Integer id) {

        log.info(String.format("[4002] Return article id '%s'",  id));

        return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
   }

   @Override
   public Iterable<Article> getArticles() {

        log.info("[4000] Return all articles");

        Iterable<Article> articles = repository.findAll();

        return articles;
   }

        
    
}
