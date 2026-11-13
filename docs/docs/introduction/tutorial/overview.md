---
title: Overview
description: Follow a six-step tutorial to build a customer management app with webforJ and Spring Boot, adding routing, data binding, and layout.
hide_giscus_comments: true
---

This step-by-step tutorial guides you through the process of building a customer management app using webforJ and Spring Boot. It teaches you how to create a modern, user-friendly interface for viewing, adding, and editing customer data.

Each step introduces new concepts and results in a runnable Spring Boot app (JAR). You can launch your app locally using Maven and interact with it in a web browser. With this setup, you get a fast development cycle with frontend bundling and a production-ready deployment model, using Spring Boot’s embedded server.

No prior Spring Boot or webforJ experience is needed, but you should have a basic understanding of Java and Maven to get the most out of this tutorial. This tutorial will cover Spring concepts as they come up, but those interested in an in-depth understanding of Spring can go to [Spring's main documentation](https://spring.io/learn) and Spring's documentation about [Spring Boot](https://docs.spring.io/spring-boot/index.html).

You can also follow optional checkpoints using Claude Code and [craftforJ](/docs/craftforj). Claude Code helps you understand and work with the project source, while craftforJ lets you inspect and refine the running interface. Start with the [optional tool setup](/docs/introduction/tutorial/project-setup#optional-ai-and-craftforj-setup); each lesson explains what to try and how to check the result.

## Tutorial concepts {#tutorial-concepts}

The first part of the tutorial is dedicated to [project setup](/docs/introduction/tutorial/project-setup) to prepare your Spring Boot + webforJ environment. Then, the following steps introduce new features and advance your project. By following along, you’ll gain a clear understanding of how an app evolves as you implement features.

Each step has a corresponding runnable app available on GitHub:

| Step | Documentation | GitHub |
| ----- | ----- | ----- |
| 1 | [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app)                               | [Step 1 app](https://github.com/webforj/webforj-tutorial/tree/main/1-creating-a-basic-app)
| 2 | [Working with Data](/docs/introduction/tutorial/working-with-data)                                     | [Step 2 app](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)
| 3 | [Routing and Composites](/docs/introduction/tutorial/routing-and-composites)                           | [Step 3 app](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)
| 4 | [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters)           | [Step 4 app](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)
| 5 | [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data)                 | [Step 5 app](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)
| 6 | [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout)                     | [Step 6 app](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)

## Testing as you go {#testing-as-you-go}

Each step also introduces a [TestBed](/docs/testing/testbed/overview) test for the behavior you just built. You'll start by clicking a button and checking a dialog, then test customer data, form submission, route parameters, validation, and navigation within the app layout. TestBed runs these checks with JUnit and Spring without starting a browser or HTTP server.

The testing examples require webforJ **26.03 or higher** and are available on the tutorial's [testbed branch](https://github.com/webforj/webforj-tutorial/tree/testbed). Follow the [testing setup](/docs/introduction/tutorial/project-setup#testbed-setup) before running them. Continue checking appearance and responsive layout in a browser alongside these behavior tests.

## Prerequisites {#prerequisites}

You should have the following tools/resources on your development machine:

- Java 21 or 25
- Maven
- A Java IDE
- Git (recommended but not required)

:::info webforJ prerequisites
Check the [prerequisites article](/docs/introduction/prerequisites) for a more detailed overview of the required tools for your development environment.
:::

<DocCardList className="topics-section" />
