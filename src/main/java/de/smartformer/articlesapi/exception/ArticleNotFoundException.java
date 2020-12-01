package de.smartformer.articlesapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleNotFoundException extends RuntimeException {

    private final Logger log = LoggerFactory.getLogger(ArticleNotFoundException.class);

    private static final long serialVersionUID = 1L;

    public ArticleNotFoundException(Integer id) {
                
        super("Could not find article " + id);

        log.error(String.format("[5000] Could not find article id '%s'",  id));
    }
}