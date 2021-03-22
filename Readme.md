# Docs

Implementation using TDD and DDD approach.

You can view API definition in OpenAPI 3.0 format in `api_definition.yml` file included in root path. You can use [Swagger Online Editor](https://editor.swagger.io/).

All endpoints return a common data structure:

```
{
    'data': {
       // Here goes all the DTO info in case response was successful
     },
     'error': {
       // Here goes error information in case there was an error executing the request
       'code': 'Example code',
       'message': 'Error message'
     }
}
```

To validate test results are correct, just execute `ProductControllerIT` where there are jUnit5 parameterized tests with all use cases.

### Reference Documentation

For further reference, please consider the following sections:

* [Vavr](https://www.vavr.io/) 
* [Java Money](https://javamoney.github.io/ri.html)
* [Mapstruct](https://mapstruct.org/)
* [Lombok](https://projectlombok.org/)
* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.4/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.4/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)


