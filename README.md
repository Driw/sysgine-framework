# System Engine Framework - Sysgine

This framework was build to simplify complex implementation of applications, inspired by Spring Boot Framework.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You need have [Maven](https://maven.apache.org/) to install Scarlet Base and you can found it on [Maven Repository](https://mvnrepository.com/).

### Installing

Specify the maven import as the following example on your [pom.xml](https://maven.apache.org/pom.html) inside of your project.

```xml
<dependency>
    <groupId>br.com.driw</groupId>
    <artifactId>sysgine-framework-{MODULE_NAME}</artifactId>
    <version>0.1.0-ALPHA</version>
</dependency>
```

1. You can use any module with maven without any repository, but will ned install them

``mvn clean validate compile test package verify install --projects
sysgine-framework-application,
sysgine-framework-configurator,
sysgine-framework-context,
sysgine-framework-service,
sysgine-framework-stream,
sysgine-framework-logger,
sysgine-framework-utils``

It's possible install specific modules, but that is the main modules, anyone else not listed on item 2 it's useful by the modules listed up.

2. Choose one or more modules:

- Application - ``sysgine-framework-application``
- Configurator - ``sysgine-framework-configurator``
- Context - ``sysgine-framework-context``
- Service - ``sysgine-framework-service``
- Stream - ``sysgine-framework-stream``
- Logger - ``sysgine-framework-logger``
- Utils - ``sysgine-framework-utils``

2. Look out the modules README to understand more about it one.

## Running the tests

The test can be executed just running the project with JUnit Test 5 or by maven.

```mvn test```

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/driw/sysgine-framework/tags).

## Authors

* **Andrew Mello da Silva** - *Developer* - [Driw](https://github.com/Driw)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* **Billie Thompson** - *[Readme template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)* - [PurpleBooth](https://github.com/PurpleBooth)
