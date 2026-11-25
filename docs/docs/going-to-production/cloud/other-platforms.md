---
sidebar_position: 6
title: Other Platforms
description: Settings for running a webforJ app on OpenShift, Kubernetes, Clever Cloud, Scalingo, virtual machines, Render, Railway, Fly.io, DigitalOcean App Platform, Northflank, Upsun, Coolify, and Dokku.
---

The platforms in this article aren't covered by the AWS, Azure, Google Cloud, and Heroku guides. They run a webforJ app from the container image in [Containerization](../containerization) or from the Spring Boot JAR. Each section lists the settings for the [production requirements](../preparing-for-production#production-requirements).

When a platform builds the JAR itself, set its Java version to the version the project compiles for. Run a Spring Boot app with `SPRING_PROFILES_ACTIVE=prod` and `WEBFORJ_RELOADONSERVERERROR=true`, see [Restarts and scaling](../preparing-for-production#restarts-and-scaling). The Spring Boot image from [Containerization](../containerization) already sets both.

## Platforms with sticky sessions {#platforms-with-sticky-sessions}

These platforms can run more than one instance, because they keep a user on one instance.

### Red Hat OpenShift {#red-hat-openshift}

The OpenShift router sets an affinity cookie by default, so every user stays on one pod. Its timeout of 30 seconds is shorter than the heartbeat, and the router ends the open request with a `504` error, so raise the timeout on the route.

OpenShift can build the image from the Dockerfile in [Containerization](../containerization), from a local folder, without a registry:

```bash
oc new-build --binary --strategy=docker --name my-app
oc start-build my-app --from-dir=. --wait
oc new-app --name my-app --image-stream=my-app:latest
oc set probe deployment/my-app --readiness --get-url=http://:8080/
oc expose service my-app
oc annotate route my-app haproxy.router.openshift.io/timeout=120s
```

To deploy an image from a registry instead, use `oc new-app --name my-app --image <registry>/my-app:1`. Run `oc start-build` again for each new release. The readiness probe keeps new pods out of the router until the app answers, so open pages don't wait on a pod that's still starting during a rollout.

OpenShift runs containers with a random user ID in the root group. The Spring Boot image works without changes. The Jetty image writes to its base folder at startup and fails with `Permission denied`, so give the root group write access in the runtime stage of the Jetty Dockerfile:

```dockerfile
COPY --from=build --chown=jetty:root /src/target/*.war /var/lib/jetty/webapps/ROOT.war
USER root
RUN mkdir -p /tmp/jetty && chgrp -R 0 /var/lib/jetty /tmp/jetty && chmod -R g=u /var/lib/jetty /tmp/jetty
USER jetty
```

### Kubernetes {#kubernetes}

Run the app as a `Deployment` with at least two replicas and a `Service`. The readiness probe keeps a pod out of rotation until the app answers, so a rollout doesn't replace every pod before the new ones serve requests. The memory limit lets `-XX:MaxRAMPercentage` size the heap:

```yaml title="deployment.yaml"
apiVersion: apps/v1
kind: Deployment
metadata:
  name: my-app
spec:
  replicas: 2
  selector:
    matchLabels:
      app: my-app
  template:
    metadata:
      labels:
        app: my-app
    spec:
      terminationGracePeriodSeconds: 60
      containers:
        - name: my-app
          image: registry.example.com/my-app:1.0
          ports:
            - containerPort: 8080
          readinessProbe:
            httpGet:
              path: /
              port: 8080
          resources:
            limits:
              memory: 1Gi
---
apiVersion: v1
kind: Service
metadata:
  name: my-app
spec:
  selector:
    app: my-app
  ports:
    - port: 8080
      targetPort: 8080
```

If you add a liveness probe, also add a startup probe that allows at least 45 seconds, see [License server](../preparing-for-production#license-server). Otherwise Kubernetes can restart a pod that's still waiting for the license server.

Stickiness depends on the ingress or gateway controller. The ingress-nginx controller was retired in March 2026, and the Gateway API has no stable cookie affinity yet, so use a controller that offers cookie stickiness, such as Traefik, HAProxy, or Envoy Gateway. For example, a Traefik `IngressRoute`:

```yaml title="ingressroute.yaml"
apiVersion: traefik.io/v1alpha1
kind: IngressRoute
metadata:
  name: my-app
spec:
  entryPoints:
    - websecure
  routes:
    - match: Host(`app.example.com`)
      kind: Rule
      services:
        - name: my-app
          port: 8080
          sticky:
            cookie:
              name: app_affinity
              httpOnly: true
              secure: true
```

The route answers on HTTPS only. Give it a certificate with a `tls` section, and redirect the `web` entry point to `websecure`. When a pod goes away, Traefik points the cookie to a remaining pod, and the open pages reload there.

Keep `terminationGracePeriodSeconds` longer than the heartbeat interval, and check that network policies allow outbound TCP port `27000`.

### Clever Cloud {#clever-cloud}

Clever Cloud runs Java apps from Maven or a container image. Sticky sessions are off by default:

```bash
clever env set SPRING_PROFILES_ACTIVE prod
clever env set WEBFORJ_RELOADONSERVERERROR true
clever config set sticky-sessions true
clever deploy
```

### Scalingo {#scalingo}

Scalingo builds Java apps with its buildpack and supports sticky sessions. Its router ends a request that sends no data for 59 seconds, so lower the heartbeat:

```bash
scalingo env-set SPRING_PROFILES_ACTIVE=prod WEBFORJ_RELOADONSERVERERROR=true WEBFORJ_CLIENTHEARTBEATRATE=45s
```

Turn on sticky sessions in the routing settings of the app.

### Virtual machines {#virtual-machines}

A virtual machine from any provider runs the JAR as a `systemd` service behind a reverse proxy. You control every timeout and the stickiness of the proxy.

Create a system user for the service and copy the JAR:

```bash
sudo useradd --system --no-create-home --shell /usr/sbin/nologin webforj
sudo mkdir -p /opt/my-app
sudo cp target/my-app-1.0-SNAPSHOT.jar /opt/my-app/my-app.jar
```

Then add the unit. A virtual machine has no container memory limit, so set the heap size with `-Xmx` to fit the memory of the machine and the number of instances:

```ini title="/etc/systemd/system/my-app.service"
[Unit]
Description=my-app
Wants=network-online.target
After=network-online.target

[Service]
User=webforj
Environment=SPRING_PROFILES_ACTIVE=prod
Environment=WEBFORJ_RELOADONSERVERERROR=true
ExecStart=/usr/bin/java -Xmx2g -jar /opt/my-app/my-app.jar
SuccessExitStatus=143
Restart=on-failure

[Install]
WantedBy=multi-user.target
```

Start the service:

```bash
sudo systemctl daemon-reload
sudo systemctl enable --now my-app
```

Put nginx or Caddy in front for TLS. For a second instance, copy the unit as `my-app-2.service` and add `Environment=SERVER_PORT=8081`. Then use the nginx configuration from [Containerization](../containerization#run-more-than-one-container) with the local ports as upstream servers:

```nginx
upstream app {
  ip_hash;
  server 127.0.0.1:8080;
  server 127.0.0.1:8081;
}
```

On Debian and Ubuntu, remove `/etc/nginx/sites-enabled/default`, otherwise the default site answers every request.

## Platforms without sticky sessions {#platforms-without-sticky-sessions}

These platforms send requests to random instances, or keep a user on one instance only by hashing the client IP address. Run exactly one instance on them. The Spring Boot Dockerfile from [Containerization](../containerization) already sets `SPRING_PROFILES_ACTIVE` and `WEBFORJ_RELOADONSERVERERROR`, and a WAR reads `webforj.conf`, so each section only shows how to set other properties.

### Render {#render}

[Render](https://render.com/docs/docker) builds a web service from the Dockerfile in a Git repository, or pulls a [prebuilt image](https://render.com/docs/deploying-an-image) from a registry. It [load-balances requests evenly](https://render.com/docs/scaling) across instances, so keep one. [Free instances](https://render.com/docs/free) spin down after 15 minutes without traffic and have 512 MB of memory, so use a paid instance type.

Define the service in a `render.yaml` [Blueprint](https://render.com/docs/blueprint-spec) in the root folder of the repository:

```yaml title="render.yaml"
services:
  - type: web
    name: my-app
    runtime: docker
    plan: 1c-2g
    numInstances: 1
    envVars:
      - key: PORT
        value: 8080
```

In the Render Dashboard, click **New > Blueprint** and connect the repository, see [Blueprints](https://render.com/docs/infrastructure-as-code).

| Setting | Why |
|---|---|
| `runtime: docker` | Builds the image from `./Dockerfile`. For an image from a registry, use `runtime: image` instead |
| `plan: 1c-2g` | Gives the service 2 GB of memory. The default `0.5c-512mb` is too small for the JVM |
| `PORT` | Render expects the app on port `10000` unless `PORT` names another port, see [port binding](https://render.com/docs/web-services#port-binding) |

Set other properties as more `envVars` entries, for example `WEBFORJ_CLIENTHEARTBEATRATE`.

### Railway {#railway}

[Railway](https://docs.railway.com/builds/dockerfiles) builds the image from the `Dockerfile` in the root folder of the project, or deploys a prebuilt image from Docker Hub, GitHub Container Registry, or Quay, see [Quick start](https://docs.railway.com/quick-start). It [sends requests to random replicas](https://docs.railway.com/deployments/scaling#load-balancing-between-replicas) and has no sticky sessions, so keep one replica. Services on the Free plan sleep when idle and get 0.5 GB of memory, see [plans](https://docs.railway.com/pricing/plans). Use the Hobby plan or higher, and keep [Serverless](https://docs.railway.com/deployments/serverless) turned off in the service settings.

Deploy the project folder with the [Railway CLI](https://docs.railway.com/cli/up):

```bash
railway up --new --name my-app
railway domain --port 8080
```

`railway up` uploads the folder and builds the image on Railway. `railway domain` creates a public `up.railway.app` domain that routes to port `8080`. Run `railway up` again for each new release.

Railway closes a request after five minutes without data, which is longer than the heartbeat, see [specs and limits](https://docs.railway.com/networking/public-networking/specs-and-limits). Set other properties with `railway variable set WEBFORJ_CLIENTHEARTBEATRATE=25s`.

### Fly.io {#fly-io}

[Fly.io](https://fly.io/docs/languages-and-frameworks/dockerfile/) builds the image from the Dockerfile and runs it on Machines. Its proxy has no cookie stickiness, only [request headers](https://fly.io/docs/blueprints/sticky-sessions/) that the app sets itself, so run one Machine. Create the app without deploying it:

```bash
fly launch --no-deploy
```

`fly launch` takes the port from `EXPOSE 8080` and writes `fly.toml`. Edit it so the proxy never stops the Machine, and give the Machine 1 GB of memory, see [configuration](https://fly.io/docs/reference/configuration/):

```toml title="fly.toml"
[http_service]
  internal_port = 8080
  auto_stop_machines = "off"
  min_machines_running = 1

[[vm]]
  memory = "1gb"
```

Then deploy:

```bash
fly deploy --ha=false
```

Without `--ha=false`, the first deployment creates a spare Machine, and the proxy then sends requests to both. Later deployments keep the number of Machines. Run `fly deploy` again for each new release, or `fly deploy --image <registry>/my-app:1` for an image from a registry, see [`fly deploy`](https://fly.io/docs/flyctl/cmd/fly_deploy/).

Set other properties in the `[env]` section of `fly.toml`, for example `WEBFORJ_CLIENTHEARTBEATRATE = "25s"`.

<!-- vale Google.Headings = NO -->
### DigitalOcean App Platform {#digitalocean-app-platform}
<!-- vale Google.Headings = YES -->

[App Platform](https://docs.digitalocean.com/products/app-platform/reference/app-spec/) builds a component from the Dockerfile in a GitHub, GitLab, or Bitbucket repository, or deploys an image from DigitalOcean Container Registry, Docker Hub, or GitHub Container Registry. It has no sticky sessions, see the [feature request](https://ideas.digitalocean.com/app-platform/p/sticky-sessions-for-app-platform), so keep one instance. Describe the app in an app spec:

```yaml title="app.yaml"
name: my-app
services:
  - name: web
    github:
      repo: example/my-app
      branch: main
      deploy_on_push: true
    dockerfile_path: Dockerfile
    http_port: 8080
    instance_count: 1
    instance_size_slug: apps-s-1vcpu-1gb
```

Create the app with [`doctl`](https://docs.digitalocean.com/reference/doctl/reference/apps/create/):

```bash
doctl apps create --spec app.yaml
```

With `deploy_on_push`, each push to the branch deploys a new release. The size `apps-s-1vcpu-1gb` has 1 GiB of memory, see [pricing](https://docs.digitalocean.com/products/app-platform/details/pricing/). For an image from a registry, replace `github` and `dockerfile_path` with an `image` entry that names `registry_type`, `repository`, and `tag`, and deploy a new image with `doctl apps create-deployment <app-id>`, see [Deploy from container images](https://docs.digitalocean.com/products/app-platform/how-to/deploy-from-container-images/).

Set other properties in an `envs` list on the service, with a `key` and a `value` for each variable.

### Northflank {#northflank}

[Northflank](https://northflank.com/docs/v1/application/getting-started/build-and-deploy-your-code) builds and deploys a Git repository in a combined service, or [runs an image](https://northflank.com/docs/v1/application/run/run-an-image-from-a-container-registry) from a registry in a deployment service. By default, its load balancer sends each request to the instance with the fewest open connections. The [consistent hashing](https://northflank.com/docs/v1/application/network/load-balancing) strategy in client IP address mode keeps a user on one instance, but then all users behind one NAT, such as a company network, share an instance. Otherwise, run one instance.

Create a combined service in the dashboard:

1. Select the repository and the branch.
2. Under build options, choose Dockerfile.
3. Under networking, check that Northflank detected port `8080` from `EXPOSE 8080`, and make it public, see [ports](https://northflank.com/docs/v1/application/network/configure-ports).
4. Under resources, choose a compute plan with at least 1 GB of memory, see [Scale CPU and memory](https://northflank.com/docs/v1/application/scale/scale-cpu-and-memory).

The service builds and deploys each new commit to the branch. To use consistent hashing, select it on the networking page of the service. The change takes effect without a new deployment.

Set other properties as [runtime variables](https://northflank.com/docs/v1/application/run/inject-runtime-variables) of the service.

### Upsun {#upsun}

[Upsun](https://developer.upsun.com/docs/add-services/docker-images) runs a prebuilt image as a Docker image app, so push the image from Containerization to a registry first. With more than one instance, its router [sends requests to random instances](https://developer.upsun.com/docs/manage-resources/adjust-resources). The `sticky` option on a route [hashes the client IP address](https://developer.upsun.com/docs/routes#sticky-routing), so all users behind one NAT share an instance, and scaling changes the hash, which moves users to other instances. Otherwise, run one instance.

Add the app and its route to `.upsun/config.yaml`:

```yaml title=".upsun/config.yaml"
applications:
  app:
    type: docker:1
    image:
      name: "docker.io/example/my-app:1.0"
    web:
      upstream:
        socket_family: tcp
      locations:
        "/":
          passthru: true

routes:
  "https://{default}/":
    type: upstream
    upstream: "app:http"
    cache:
      enabled: false
    sticky:
      enabled: true
```

The image keeps its own start command, so the router uses the port from `EXPOSE 8080`. `cache` turns off the [router cache](https://developer.upsun.com/docs/routes/cache) for the app. Connect the repository to the project, push the configuration, and set the number of instances:

```bash
upsun set-remote <project-id>
git add .upsun/config.yaml
git commit -m "Add webforJ app"
upsun push
upsun resources:set --count app:1
```

Choose a CPU size with at least 1 GB of RAM with `upsun resources:set --size`. For each new release, change the tag in `image.name`, then commit and push. Set other properties under `variables.env` of the app. Upsun can also build the JAR from the repository with its [Java runtime](https://developer.upsun.com/docs/languages/java) instead of an image.

### Coolify {#coolify}

[Coolify](https://coolify.io/docs) is a self-hosted platform that runs each app as one container behind its Traefik or Caddy proxy. It doesn't set up sticky sessions, so run one instance. Coolify deploys the image from [Containerization](../containerization), or builds it from a Git repository that contains the Dockerfile, see [Dockerfile build pack](https://coolify.io/docs/applications/build-packs/dockerfile).

The [Coolify CLI](https://github.com/coollabsio/coolify-cli) drives the same steps from the command line. Turn on API access in **Settings → Advanced**, create an API token in **Keys & Tokens**, and connect the CLI:

```bash
coolify context add my-coolify https://coolify.example.com <api-token> --default
coolify server list
coolify project list
```

Create the app from the repository with the UUIDs of the server and the project, then deploy it:

```bash
coolify app create public \
  --server-uuid <server-uuid> --project-uuid <project-uuid> --environment-name production \
  --name my-app --git-repository https://github.com/example/my-app --git-branch main \
  --build-pack dockerfile --ports-exposes 8080 --domains https://app.example.com
coolify deploy name my-app
```

For a private repository, use `coolify app create github` or `coolify app create deploy-key` instead. For an image in a registry, use `coolify app create dockerimage` with `--docker-registry-image-name`. Run `coolify deploy name my-app` again for each new release.

The Spring Boot Dockerfile from Containerization already sets `SPRING_PROFILES_ACTIVE` and `WEBFORJ_RELOADONSERVERERROR`, and a WAR reads `webforj.conf`. Set other properties with `coolify app env create`, for example `--key WEBFORJ_CLIENTHEARTBEATRATE --value 25s`.

Coolify runs [health checks](https://coolify.io/docs/knowledge-base/health-checks) with `curl` or `wget` inside the container. The Java 25 images `eclipse-temurin:25-jre` and `jetty:12.1-jdk25` contain neither, so a health check fails and Coolify rolls the deployment back. Leave health checks off for these images, or install `curl` in the runtime stage of the Dockerfile.

### Dokku {#dokku}

[Dokku](https://dokku.com/docs/getting-started/installation/) is a self-hosted platform that builds and runs apps on one server behind nginx. It sends requests to the containers of an app in turn, so keep one instance, which is the default. Dokku needs ports `80` and `443` for its nginx, so it can't share a server with another proxy on these ports.

Commit the Dockerfile from [Containerization](../containerization) to the repository. Dokku detects it and builds the image with it, see [Dockerfile deployment](https://dokku.com/docs/deployment/builders/dockerfiles/). Create the app on the server, then push the repository:

```bash
dokku apps:create my-app
dokku domains:set my-app app.example.com
dokku ports:set my-app http:80:8080
dokku nginx:set my-app proxy-read-timeout 120s
git remote add dokku dokku@dokku.example.com:my-app
git push dokku main
```

Run the `dokku` commands on the server, or remotely as `ssh dokku@dokku.example.com <command>`. Each later `git push` deploys a new release.

| Command | Why |
|---|---|
| `ports:set my-app http:80:8080` | Dokku takes the port from `EXPOSE 8080` and would otherwise serve the app on port `8080`, see [port management](https://dokku.com/docs/networking/port-management/) |
| `nginx:set my-app proxy-read-timeout 120s` | The default of 60 seconds is only slightly longer than the 50-second heartbeat, see [nginx proxy](https://dokku.com/docs/networking/proxies/nginx/) |

The Spring Boot Dockerfile from Containerization already sets `SPRING_PROFILES_ACTIVE` and `WEBFORJ_RELOADONSERVERERROR`, and a WAR reads `webforj.conf`. Set other properties with `dokku config:set my-app WEBFORJ_CLIENTHEARTBEATRATE=25s`. To deploy a prebuilt image instead of a repository, see [image deployment](https://dokku.com/docs/deployment/methods/image/).
