---
sidebar_position: 2
title: Deploying to AWS
description: Deploy a webforJ app to AWS with Elastic Beanstalk, Amazon ECS on Fargate, or Amazon Lightsail containers, with sticky sessions and load balancer timeouts.
---

AWS offers three services that run a webforJ app:

| Service | Use it for |
|---|---|
| [Elastic Beanstalk](#elastic-beanstalk) | A JAR or WAR on instances behind an Application Load Balancer with sticky sessions |
| [Amazon ECS on Fargate](#amazon-ecs-on-fargate) | A container image behind an Application Load Balancer with sticky sessions |
| [Amazon Lightsail containers](#amazon-lightsail-containers) | A simple setup with a single instance |

## Elastic Beanstalk {#elastic-beanstalk}

AWS Elastic Beanstalk runs the app on EC2 instances behind an Application Load Balancer and handles provisioning, scaling, and deployments. The Java SE platform runs the Spring Boot JAR, the Tomcat platform runs a WAR.

### Prerequisites {#prerequisites}

- An AWS account with permissions for Elastic Beanstalk, EC2, and Elastic Load Balancing
- The [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) and the [EB CLI](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html)
- A Spring Boot app built with `mvn package`, see [Preparing for Production](../preparing-for-production)

### Set up the CLI {#set-up-the-cli}

Store your credentials, then initialize the project from its root folder:

```bash
aws configure
eb init
```

`eb init` asks for the region and the app name. For the platform, choose **Java** and a **Corretto 21** or **Corretto 25** branch for the JAR, or a **Tomcat 11** branch on Corretto 21 or 25 for a WAR. Pick the Java version the project compiles for.

### Configure the environment {#configure-the-environment}

Elastic Beanstalk reads environment settings from `.ebextensions` folders inside the deployed bundle. Create `.ebextensions/webforj.config` in the project root:

```yaml title=".ebextensions/webforj.config"
option_settings:
  aws:elasticbeanstalk:application:environment:
    SERVER_PORT: 5000
    SPRING_PROFILES_ACTIVE: prod
    WEBFORJ_RELOADONSERVERERROR: "true"
  aws:elasticbeanstalk:environment:process:default:
    StickinessEnabled: true
    StickinessType: lb_cookie
    DeregistrationDelay: 60
  aws:elbv2:loadbalancer:
    IdleTimeout: 120
```

| Setting | Why |
|---|---|
| `SERVER_PORT: 5000` | On the Java SE platform, nginx forwards requests to port 5000 |
| `WEBFORJ_RELOADONSERVERERROR: "true"` | Lets open pages reload by themselves after an instance is replaced, see [restarts and scaling](../preparing-for-production#restarts-and-scaling) |
| `StickinessEnabled`, `StickinessType` | Keeps every user on one instance, see [sticky sessions](../preparing-for-production#sticky-sessions) |
| `DeregistrationDelay` | Lets open requests finish before an instance leaves the load balancer |
| `IdleTimeout: 120` | Keeps the open request of the browser alive longer than the 50-second heartbeat |

The nginx proxy on each instance waits 60 seconds for a response by default. That's enough for the default heartbeat of 50 seconds. If you set `webforj.clientHeartbeatRate` to more than 55 seconds, raise the nginx `proxy_read_timeout` as described in [Reverse proxy configuration](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/platforms-linux-extend.proxy.html).

### Build the deployment bundle {#build-the-deployment-bundle}

A bundle for the Java SE platform is a ZIP file with the JAR, a `Procfile` that starts it, and the `.ebextensions` folder. Run these commands in the project root to build it. The `echo` line writes the `Procfile`:

```bash
mvn package
rm -rf target/eb && mkdir -p target/eb
cp target/my-app-1.0-SNAPSHOT.jar target/eb/app.jar
cp -r .ebextensions target/eb/
echo "web: java -XX:MaxRAMPercentage=75 -jar app.jar" > target/eb/Procfile
(cd target/eb && zip -r ../my-app-eb.zip .)
```

Tell the EB CLI to upload the bundle instead of the project folder. Add these lines to the `.elasticbeanstalk/config.yml` file that `eb init` created:

```yaml title=".elasticbeanstalk/config.yml"
deploy:
  artifact: target/my-app-eb.zip
```

### Deploy {#deploy}

Create an environment with an Application Load Balancer, which stickiness requires:

```bash
eb create my-app-prod --elb-type application
```

For later releases, build the bundle again and deploy it:

```bash
eb deploy
```

`eb open` opens the app in the browser, and `eb logs` fetches the logs of the instances.

### Deploy a WAR {#deploy-a-war}

For a WAR built from the `webforj` flavor of the archetype, choose a Tomcat 11 platform in `eb init`. When `eb init` detects Java and asks whether that's correct, answer `n` to see the Tomcat platforms, or pass the platform directly, with the Corretto version that matches the Java version of the project:

```bash
eb init my-app -p tomcat-11-corretto-21
```

Build the WAR with `mvn package -Pprod`. Leave the `aws:elasticbeanstalk:application:environment` settings out of the configuration file, since a WAR reads `webforj.conf` and reloads open pages by default. Then bundle the WAR as `ROOT.war`, which Elastic Beanstalk serves at `/`:

```bash
mvn package -Pprod
rm -rf target/eb && mkdir -p target/eb
cp target/my-app-1.0-SNAPSHOT.war target/eb/ROOT.war
cp -r .ebextensions target/eb/
(cd target/eb && zip -r ../my-app-eb.zip .)
```

A Spring Boot app packaged as a WAR also runs on Tomcat 11, but only at `/` and with the production profile set at startup, see [Spring Boot apps](../traditional-servers#spring-boot-apps). The JAR on the Java SE platform is the simpler choice for Spring Boot.

### Scaling and updates {#scaling-and-updates}

- Elastic Beanstalk doesn't scale to zero, so at least one instance keeps the sessions alive.
- Rolling deployments replace one instance after the other, which ends the sessions on them. Users reload into a fresh session, see [restarts and scaling](../preparing-for-production#restarts-and-scaling). With several instances, an open page can reload twice: first onto an instance that still runs the previous version, then onto the new version when that instance is replaced.
- Instances need outbound access to TCP port `27000` for the license server. The default security group allows all outbound traffic.

## Amazon ECS on Fargate {#amazon-ecs-on-fargate}

ECS on Fargate runs the container image from [Containerization](../containerization) and gives the same stickiness and timeout settings as Elastic Beanstalk. Run the image as an ECS service behind an Application Load Balancer, or let ECS Express Mode create the service and the load balancer. Then set stickiness and timeouts on the load balancer:

```bash
aws elbv2 modify-target-group-attributes --target-group-arn <target-group-arn> --attributes \
  Key=stickiness.enabled,Value=true \
  Key=stickiness.type,Value=lb_cookie \
  Key=deregistration_delay.timeout_seconds,Value=60
aws elbv2 modify-load-balancer-attributes --load-balancer-arn <load-balancer-arn> --attributes \
  Key=idle_timeout.timeout_seconds,Value=120
```

Run a Spring Boot image with `SPRING_PROFILES_ACTIVE=prod` and `WEBFORJ_RELOADONSERVERERROR=true`. The Spring Boot image from [Containerization](../containerization) already sets both.

Tasks in private subnets need a NAT gateway to reach the license server on TCP port `27000`. AWS App Runner is closed to new customers, use ECS instead.

## Amazon Lightsail containers {#amazon-lightsail-containers}

Lightsail container services run the container image from [Containerization](../containerization). They have no sticky sessions, so run exactly one instance:

- Set the scale to 1.
- Choose at least the Micro power, so the JVM has enough memory.
- Lightsail doesn't document the request timeout of its load balancer. If the open request of the browser ends with an error before the heartbeat interval, lower the heartbeat with `WEBFORJ_CLIENTHEARTBEATRATE=25s`, see [requests that stay open](../preparing-for-production#requests-that-stay-open).
