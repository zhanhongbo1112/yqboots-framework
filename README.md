# YQBoots Framework
[YQBoots Framework](http://www.yqboots.com/projects/framework) is a production-ready framework for building modern applications. It is built on [Spring IO Platform](http://spring.io/platform/), and simplifying the initialization of a Spring-powered, production-grade applications and services with minimal effort by using [Spring Boot](https://spring.io/spring-boot).
  
It also contains a fully responsive user interface, built by [Bootstrap](http://getbootstrap.com/), providing an optimal viewing and interaction experience -- easy reading and navigation with a minimum of resizing, panning, and scrolling-across a wide range of devices (from desktop to mobile phones).

# Prerequisites
[JDK 8 update 20 or later](http://www.oracle.com/technetwork/java/javase/downloads)

# Features
## Modern User Interface
A fully responsive, HTML5 + CSS3 user interface, no need to build different user interface for different devices.  

Thanks to the HTML5 nature, it supports almost all modern browsers, such as Internet Explorer 10+, Microsoft Edge, Google Chrome, Mozilla Firefox, etc.

## Rapid Prototyping
It is powered by [Thymeleaf](http://www.thymeleaf.org/), a modern server-side Java template engine, which can bring elegant nature templates to your development flow — HTML that can be correctly displayed in browsers and also work as static prototypes, allowing for stronger collaboration in development teams.

## Quick Start
It is powered by [Spring Boot](https://spring.io/spring-boot), its spring-based and convention-over-configuration feature rapidly decreases the initialization of your project.  

You can launch the application with ```http://localhost``` after you run the command.
```
java -jar yqboots-web-site-[version].jar --server.port=80 --spring.profiles.active=prd
```

## Security
It provides the RBAC (Role-Based Access Control) model to protected your web sites, which has an ACL (Access Control List) strategy to protected your URL, methods, and even domains.

For the audit requirement, it can log any actions you take to the specified domains.

## Modularization and Internationalization
It divides the functionality of your application into independent, interchangeable modules, so that each contains everything necessary to execute only one aspect of the desired functionality.  

It also supports multiple languages, and switch between them.

# License
The [YQBoots Framework](http://www.yqboots.com/projects/framework) is released under version 2.0 of [the Apache License](http://www.apache.org/licenses/LICENSE-2.0).
