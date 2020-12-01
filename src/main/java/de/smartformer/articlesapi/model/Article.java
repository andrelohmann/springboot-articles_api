package de.smartformer.articlesapi.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  private String title;
  private String content;

  public Article() {}

  public Article(String title, String content) {

    this.title = title;
    this.content = content;
  }

  public Integer getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Article))
      return false;
    Article article = (Article) o;
    return Objects.equals(this.id, article.id) && Objects.equals(this.title, article.title)
        && Objects.equals(this.content, article.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.title, this.content);
  }

  @Override
  public String toString() {
    return "Article{" + "id=" + this.id + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + '}';
  }
}