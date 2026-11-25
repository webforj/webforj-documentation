---
sidebar_position: 4
title: Deploying to Google Cloud
description: Deploy a webforJ app to Google Cloud Run or App Engine flexible, with session affinity and an always-on instance.
---

Google Cloud offers two services that run a webforJ app:

| Service | Use it for |
|---|---|
| [Cloud Run](#deploy-to-cloud-run) | A container image with session affinity, the first choice on Google Cloud |
| [App Engine flexible](#app-engine-flexible) | An alternative on managed VMs with session affinity |

App Engine standard doesn't suit a webforJ app, because it offers no session affinity and starts new instances for every few concurrent requests.

## Prerequisites {#prerequisites}

- A Google Cloud project with billing turned on
- The [Google Cloud CLI](https://cloud.google.com/sdk/docs/install)
- A project with the Dockerfile from [Containerization](../containerization), with both `FROM` lines on the Java version the project compiles for

Sign in, select the project, and turn on the services Cloud Run uses:

```bash
gcloud auth login
gcloud config set project my-project-id
gcloud services enable run.googleapis.com cloudbuild.googleapis.com artifactregistry.googleapis.com
```

## Deploy to Cloud Run {#deploy-to-cloud-run}

From the project root, deploy the source. Cloud Build builds the image from the `Dockerfile` and Cloud Run starts it:

```bash
gcloud run deploy my-app \
  --source . \
  --region europe-west1 \
  --allow-unauthenticated \
  --port 8080 \
  --min-instances 1 \
  --session-affinity \
  --no-cpu-throttling \
  --concurrency 500 \
  --timeout 3600 \
  --set-env-vars SPRING_PROFILES_ACTIVE=prod
```

The command prints the address of the service. Run it again to deploy a new release. The Spring Boot Dockerfile sets `WEBFORJ_RELOADONSERVERERROR=true`, so open pages reload by themselves when Cloud Run replaces an instance.

| Flag | Why |
|---|---|
| `--min-instances 1` | Cloud Run scales to zero by default, which ends all sessions. One instance keeps running |
| `--session-affinity` | Sends the requests of a user to the same instance, see [sticky sessions](../preparing-for-production#sticky-sessions) |
| `--no-cpu-throttling` | Keeps CPU available between requests, so timers, background work, and the license connection keep running |
| `--concurrency 500` | Each open tab holds one request. The default of 80 would start a new instance after 80 tabs |
| `--timeout 3600` | Allows requests up to one hour, much longer than the 50-second heartbeat |

:::info Users can still move between instances
Cloud Run keeps a user on an instance while that instance runs and has capacity. When the instance stops or reaches its concurrency limit, the next request goes elsewhere and the user reloads into a fresh session. Size `--concurrency` and the instance memory for the tabs you expect.
:::

Cloud Run allows outbound connections, including TCP port `27000` for the license server.

## App Engine flexible {#app-engine-flexible}

App Engine flexible runs the app on managed VMs and supports session affinity. Create the App Engine app in the project once and enable the API of the flexible environment:

```bash
gcloud app create --region=europe-west3
gcloud services enable appengineflex.googleapis.com
```

Configure the app in `app.yaml`, with `runtime_version` set to the Java version the project compiles for, and deploy with `gcloud app deploy`:

```yaml title="app.yaml"
runtime: java
env: flex
runtime_config:
  operating_system: "ubuntu22"
  runtime_version: "21"
network:
  session_affinity: true
automatic_scaling:
  min_num_instances: 1
env_variables:
  SPRING_PROFILES_ACTIVE: "prod"
  WEBFORJ_RELOADONSERVERERROR: "true"
```

Google restarts flexible VMs weekly and drops session affinity when instances change, so users reload into fresh sessions more often than on Cloud Run.

