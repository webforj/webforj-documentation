---
sidebar_position: 1
title: Deploying to the Cloud
hide_giscus_comments: true
description: Deploy a webforJ app to AWS, Azure, Google Cloud, or Heroku, with the settings each platform needs for a server-side UI.
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

The guides in this section deploy the Spring Boot JAR from [Preparing for Production](../preparing-for-production), the WAR, or a container image built with the Dockerfile from [Containerization](../containerization). Each guide covers the same four settings, the [production requirements](../preparing-for-production#production-requirements) that decide whether a webforJ app works on a platform:

- A request timeout longer than the heartbeat interval
- Sticky sessions for more than one instance
- At least one instance that keeps running
- Outbound access to the license server

| Platform | Service | Packaging | Guide |
|---|---|---|---|
| AWS | Elastic Beanstalk, ECS on Fargate, Lightsail containers | JAR, WAR, or container image | [Deploying to AWS](./deploying-to-aws) |
| Azure | App Service, Container Apps | JAR, WAR, or container image | [Deploying to Azure](./deploying-to-azure) |
| Google Cloud | Cloud Run, App Engine flexible | Container image or JAR | [Deploying to Google Cloud](./deploying-to-google-cloud) |
| Heroku | Dynos | JAR | [Deploying to Heroku](./deploying-to-heroku) |

<DocCardList className="topics-section" />
