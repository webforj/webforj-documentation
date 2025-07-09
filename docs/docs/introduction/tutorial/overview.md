---
title: Overview
hide_giscus_comments: true
---

This tutorial is designed to guide you step by step through the process of creating the app. This app, designed to manage customer information, demonstrates how to use webforJ to build a functional and user-friendly interface with features for viewing, adding, and editing customer data. Each section will build upon the last, but feel free to skip ahead as needed.

Each step in the tutorial will result in a program that compiles into a WAR file, which can be deployed to any Java web app server. For this tutorial, the Maven Jetty plugin will be used to deploy the app locally. This lightweight setup ensures the app can quickly run, and that changes will be seen in real time during development.

## Tutorial app features

 - Working with data in a table.
 - Using the [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) and asset management.
 - [Routing](../../routing/overview) and [navigation](../../routing/route-navigation)
 - [Data Bindings](../../data-binding/overview) and [validation](../../data-binding/validation/overview)

## Prerequisites

To get the most out of this tutorial, it’s assumed that you have a basic understanding of Java programming and are familiar with tools like Maven. If you’re new to webforJ, don’t worry - the framework’s fundamentals will be covered along the way.

The following tools/resources should be present on your development machine

<!-- vale off -->
- Java 17 or higher
- Maven
- A Java IDE
- A web browser
- Git (recommended but not required)
<!-- vale on -->

:::tip webforJ Prerequisites
See [this article](../prerequisites) for a more detailed overview of the required tools.
:::

## Sections

The tutorial is broken into the following sections. Proceed sequentially for a comprehensive walkthrough, or skip ahead for specific information.

:::tip Project setup
For those looking to skip ahead to specific topics, it's recommended to first read the Project Setup section before moving ahead. 
:::

<DocCardList className="topics-section" />