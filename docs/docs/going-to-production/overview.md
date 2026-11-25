---
sidebar_position: 1
title: Going to Production
hide_table_of_contents: true
hide_giscus_comments: true
description: Take a webforJ app from development to production, from the production build and packaging to containers, cloud platforms, and servlet containers.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

A webforJ app ships as one Java artifact. There is no separate front end to build or host, so going to production means building that artifact for production and running it on a server, a container platform, or a cloud service.

This section walks through the path in order:

1. **Prepare the app**: build it for production, check it locally, and learn what the host must provide.
2. **Pick a packaging**: an executable JAR, a WAR for a servlet container, or a deployment to BBjServices.
3. **Containerize**: build a Docker image that runs on any container platform.
4. **Deploy to the cloud**: step-by-step guides for AWS, Azure, Google Cloud, and Heroku.
5. **Deploy to traditional servers**: run the WAR on Tomcat or Jetty.

## What a host must provide {#what-a-host-must-provide}

Most hosting choices come down to five properties of a webforJ app. The [Preparing for Production](./preparing-for-production#production-requirements) article explains each one.

| Requirement | Why |
|---|---|
| Java 21 or higher | webforJ 26 requires Java 21 or 25 |
| Requests that stay open for about a minute | The browser keeps one request open to receive UI updates |
| Sticky sessions when more than one instance runs | The UI of each user lives in the memory of one server |
| An always-on instance | Stopping the instance ends the sessions on it |
| Outbound access to the license server | The app keeps a connection to the license server open |

## Topics {#topics}

<DocCardList className="topics-section" />
