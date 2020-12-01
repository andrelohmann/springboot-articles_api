# springboot-articles_api

(c) Andre Lohmann (and others) 2020

## Maintainer Contact
 * Andre Lohmann
   <lohmann.andre (at) gmail (dot) com>

## content

Spring Boot Tutorial, building a mysql based rest api for the react js articles frontend.

## tag

  * 02_rest_service
    * https://spring.io/guides/gs/rest-service/
  * 03_cucumber_test
    * https://www.youtube.com/watch?v=OqhTQSJTeOs

## Rest Test Calls

### List empty articles

```
curl -i http://localhost:8081/articles/
```

### Create article

```
curl -i -H "Content-Type: application/json" -X POST -d '{"title":"Dynamic added Article","content":"This is the content of a dynamically added article"}' http://localhost:8081/articles
```

### List article

```
curl -i http://localhost:8081/articles/1
```

### Update article

```
curl -i -H "Content-Type: application/json" -X PUT -d '{"title":"Dynamic added Article","content":"This is the edited content of a dynamically added article"}' http://localhost:8081/articles/1
curl -i http://localhost:8081/articles/1
curl -i http://localhost:8081/articles/
```

### Delete article

```
curl -i -X DELETE http://localhost:8081/articles/1
curl -i http://localhost:8081/articles/
```
