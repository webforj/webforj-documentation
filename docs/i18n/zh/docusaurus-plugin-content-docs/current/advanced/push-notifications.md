---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 47adf06762f8af67111f20937368723c
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

推送通知即使在应用未打开时也能触达用户。浏览器一次性订阅，应用保存该订阅，而服务器则在事件发生时利用该订阅发送通知。<JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> 管理浏览器中的订阅和退订。在服务器上，<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> 将 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> 发送到已存储的订阅。

<!-- INTRO_END -->

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/push-notifications/push.mp4" type="video/mp4"/>
  </video>
</div>

## 设置和先决条件 {#setup-and-prerequisites}

推送通知是由一个独立模块提供的。将其添加到你的应用中：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-push</artifactId>
</dependency>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
dependencies {
  implementation 'com.webforj:webforj-push'
}
```

</TabItem>
</Tabs>

推送通知需要：

- 一种 servlet 部署， 如 Jetty、Spring Boot 或 WAR 文件。
- 一个密钥对，下面生成，该部署用来签署通知。
- 一个安全来源。浏览器拒绝通过非 `https` 的方式提供的订阅，除了在开发期间的 `localhost`。

:::info 安全来源
<!-- vale off -->
欲了解有关安全上下文的更多信息及其重要性，请参阅 [安全上下文 MDN 文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

### 生成密钥 {#generating-the-keys}

推送服务只接受由浏览器订阅的部署签署的通知。对于每个部署，运行一次 [构建插件](/docs/configuration/build-plugin) 以生成其密钥对：

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn webforj:push-keys
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew webforjPushKeys
```

</TabItem>
</Tabs>

该命令输出三行配置行。将它们粘贴到 `application.properties` 中，不带引号，或按打印内容复制到 `webforj.conf`。将主题替换为部署的联系地址。它必须是推送服务可以用于联系操作员的 `mailto:` 或 `https://` 地址。

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| 属性 | 说明 |
|----------|-------------|
| `webforj.push.public-key` | 部署用于签署通知所使用的密钥对的公钥部分 |
| `webforj.push.private-key` | 密钥对的私钥部分。与其他秘密一样，保持它不在源代码控制中 |
| `webforj.push.subject` | 部署的联系地址。它必须是推送服务可以联系操作员的 `mailto:` 或 `https://` 地址 |

应用在启动时读取这些属性。如果配置中仅包含部分属性，启动将失败并报告缺失的属性。

:::warning 更换密钥
每个浏览器订阅一个密钥对。如果密钥更改，推送服务将拒绝现有订阅。每个浏览器中的下一次 `subscribe()` 调用将替换其订阅。
:::

## 工作原理 {#how-it-works}

该过程有三个步骤：

1. **订阅。** 从视图中，`Push.getCurrent().subscribe()` 请求用户的权限，并返回一个识别浏览器地址的 `PushSubscription`。
2. **存储。** 应用将订阅及其数据保存并与相应用户关联。
3. **发送。** 稍后，从任何线程，`PushSender.send(subscription, message)` 将消息传递给浏览器供应商的推送服务。该服务显示通知，无论应用是否打开。

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("订单已发货").setUrl("/orders/42").build());
```

以下部分解释浏览器显示的内容以及如何处理每个步骤的失败。

## 实例 {#instance}

检索当前环境的推送实例：

```java
import com.webforj.push.Push;

Push push = Push.getCurrent();

if (Push.isPresent()) {
  // ...
}

Push.ifPresent(p -> {
  // ...
});
```

## 订阅浏览器 {#subscribing-the-browser}

根据用户操作（例如点击“启用通知”按钮）调用 `subscribe()`。返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 会完成浏览器的 <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>。如果浏览器无法订阅，它会以 <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> 异常完成。

```java
PendingResult<PushSubscription> request = Push.getCurrent().subscribe();
request.thenAccept(subscription -> {
  subscriptions.save(subscription);
});
request.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable.getCause();
  PushStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

如果浏览器已经订阅，再次调用 `subscribe()` 会返回现有订阅。因此可以在每次访问时安全地调用它。

:::info 浏览器权限
第一次调用 `subscribe()` 会提示用户授予权限。浏览器显示此提示，它不是应用 UI 的一部分。由于浏览器仅在响应用户操作时显示提示，因此应在点击监听器中调用 `subscribe()` 而不是在视图构造函数中调用。

如果用户阻止提示，应用无法再次提示该来源。
:::

### 存储订阅 {#storing-subscriptions}

订阅代表一个浏览器的地址，并属于服务器。使用其端点作为键将其与应用的数据一起存储。包括应用后来选择相应浏览器所需的任何信息，例如相关用户。每个订阅包含三个文本值：

| 值 | 含义 |
|-------|---------|
| `getEndpoint()` | 浏览器供应商的推送服务分配的交付 URL |
| `getP256dh()` | 浏览器的公钥 |
| `getAuth()` | 浏览器的身份验证密钥 |

从两个浏览器订阅的用户有两个订阅。当浏览器退订或发送报告其已过期时，删除订阅。请参阅 [失败状态](#failure-status)。

### 恢复订阅 {#restoring-a-subscription}

`getSubscription()` 返回浏览器的当前订阅，如果没有则返回空结果。使用它来同步服务器的副本，例如在应用存储被重置后：

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

通过 <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>，`getPermission()` 报告用户是授予、拒绝，还是尚未回答通知提示。根据此结果，在点击不会有任何效果的情况下隐藏“启用通知”按钮。

### 退订 {#unsubscribing}

`unsubscribe()` 取消浏览器的订阅。它以已移除的订阅完成，以便应用可以删除其存储副本，或以空结果完成（如果浏览器没有订阅）。

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## 发送通知 {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> 将 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> 发送到已存储的订阅。它用部署的密钥对消息进行签名，并将其传递给浏览器供应商的推送服务。该服务唤醒浏览器并显示通知。由于此操作从不阻塞调用线程，因此可以从单击监听器、定时作业或请求处理程序中调用。

在配置完属性后，发送方可作为 bean 注入到视图、服务和定时作业中。要替换它，请定义自己的 `PushSender` bean。

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

如果没有 Spring，`new PushSender()` 会从应用配置中读取密钥。在应用线程中创建发送者，可以在视图中或 `App.run()` 中进行，然后可以从任何线程使用它。所有发送者共享一个连接池到推送服务，因此在任何需要的地方创建一个都不会产生成本。

对于必须稍后发送或在用户离开后发送的通知，使用服务器上的定时器，如 Spring 的 `TaskScheduler`。不要使用页面定时器，如 `Interval`，因为当选项卡关闭时它会停止。

### 编写消息 {#composing-a-message}

创建一条消息及其标题，然后在构建器上配置其他所有选项：

```java
PushMessage message = PushMessage.create("订单已发货")
    .setBody("订单 #42 正在发货")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "追踪", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("已发送"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` 会立即返回。<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 在推送服务接受消息时完成，或者如果服务不接受它则异常完成。当在应用线程上调用 `send()` 时，例如从监听器中，回调在该线程上运行并可以更新组件。如果调用 `send()` 的会话在响应到达之前结束，则回调不运行，但通知仍会被发送。

发送会在 30 秒内等待推送服务的响应，超时后失败并报告 `UNREACHABLE`。使用 `setTimeout(Duration)` 来更改每个发送者的超时设置。

| 选项 | 效果 |
|--------|--------|
| `setBody` | 设置显示在标题下方的文本 |
| `setIcon` | 设置与通知一起显示的图像。它接受绝对 URL 及 `icons://` 和 `ws://` 协议。请参阅 [资源](/docs/managing-resources/assets-protocols)。它不接受 `context://` 协议，因为推送服务将消息限制为 4 KB |
| `setUrl` | 设置用户点击通知时打开的页面。当没有设置 URL 时，应用根目录会打开 |
| `setActions` | 设置在通知上显示的按钮，每个按钮都有单独的 URL。请参阅 [浏览器支持](#browser-support) |
| `setTag` | 设置一个标识标签。如果显示的通知有相同的标签，新通知将替代它 |
| `setSilent` | 无声或关闭振动地显示通知 |
| `setTimeToLive` | 设置推送服务为离线设备保留消息的时长，最长可达四周 |
| `setUrgency` | 使用 <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> 让设备延迟低紧急性的消息以节省电池 |
| `setTopic` | 替代在推送服务中仍在等待的消息，当两条消息具有相同主题时。主题最多可以包含 32 个 URL 安全字符 |

当选项卡已显示页面时，点击通知会聚焦应用。否则，页面会在新选项卡中打开。点击通知按钮会以相同方式打开其 URL。

:::info 每条消息一通知
每条消息都会显示一个通知。由于浏览器不会因显示为空的消息而唤醒页面，因此推送无法用于静默数据更新。
:::

## 失败状态 {#failure-status}

当 `subscribe()` 或 `send()` 失败时，它的 `PendingResult` 报告一个 `WebforjPushException`。<JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> 识别原因：

| 状态 | 发生条件 | 处理方法 |
|--------|------|------------|
| `PERMISSION_DENIED` | 用户阻止了该应用的通知 | 解释用户如何在浏览器设置中允许通知 |
| `UNSUPPORTED` | 浏览器不支持推送，页面不在安全上下文中，或应用未作为 servlet 部署 | 隐藏该功能 |
| `NOT_CONFIGURED` | 至少缺少或不完整一个 `webforj.push.*` 属性 | 生成密钥并配置三个属性 |
| `SUBSCRIPTION_EXPIRED` | 推送服务不再识别订阅因为用户退订或重新安装浏览器 | 删除已存储的订阅 |
| `REJECTED` | 推送服务拒绝了消息；`getStatusCode()` 包含其响应 | 验证密钥和消息大小 |
| `UNREACHABLE` | 推送服务在超时之前未响应 | 稍后再试 |
| `UNKNOWN` | 存储的端点不是有效的 URL，或订阅或消息无法编码 | 验证存储的订阅 |

在每次发送期间删除过期的订阅：

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip 过期延迟一条消息
推送服务惰性注销订阅。它们仍接受用户退订后的第一条消息，但消息不会到达。下一条消息将报告 `SUBSCRIPTION_EXPIRED`。已接受的发送意味着消息已到达推送服务，并不意味着用户已看到它。
:::

## 浏览器支持 {#browser-support}

所有主要的桌面和移动浏览器在订阅后都显示推送通知。请记住以下限制：

- 在 iPhone 和 iPad 上，推送仅适用于在 iOS 16.4 或更高版本上添加到主屏幕的 Web 应用。在 Safari 选项卡中，`subscribe()` 报告 `UNSUPPORTED`。请参阅 [可安装应用](/docs/configuration/installable-apps) 以获得所需的应用清单。
- Safari 不显示通知按钮。它显示带有操作的消息，但没有按钮，但点击通知仍会打开该消息的 URL。
- Android 和 iOS WebView 不显示通知。

有关每个浏览器的详细信息，请参见 MDN [showNotification 兼容性表](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility)。

## 完整示例 {#complete-example}

以下视图订阅和退订浏览器，将订阅存储在内存中，并向每个存储的订阅发送一条消息。它可以立即发送或使用 Spring 的 `TaskScheduler` 等待八秒，以允许选项卡在通知到达之前关闭。应用类使用 `@EnableScheduling` 使调度器可用。

```java title="PushSubscriptions.java"
package com.example;

import com.webforj.push.PushSubscription;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PushSubscriptions {

  private final Map<String, PushSubscription> byEndpoint = new ConcurrentHashMap<>();

  public void save(PushSubscription subscription) {
    byEndpoint.put(subscription.getEndpoint(), subscription);
  }

  public void delete(PushSubscription subscription) {
    byEndpoint.remove(subscription.getEndpoint());
  }

  public Collection<PushSubscription> findAll() {
    return byEndpoint.values();
  }
}
```

<!-- vale off -->

<ExpandableCode title="PushView.java" language="java" startLine={40} endLine={73}>

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.push.Push;
import com.webforj.push.PushAction;
import com.webforj.push.PushMessage;
import com.webforj.push.PushSender;
import com.webforj.push.PushStatus;
import com.webforj.push.PushSubscription;
import com.webforj.push.exception.WebforjPushException;
import com.webforj.router.annotation.Route;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.scheduling.TaskScheduler;

@Route("/push")
public class PushView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph status = new Paragraph("正在检查订阅…");
  private final TextField message = new TextField("消息", "订单 #42 正在发货");
  private final Button subscribe =
      new Button("启用通知", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("禁用通知");
  private final Button sendNow = new Button("立即发送");
  private final Button sendLater = new Button("8 秒后发送");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("已订阅");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "该浏览器中的通知已被阻止"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "已退订" : "没有订阅");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("将在 8 秒后发送，请立即关闭选项卡");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "已订阅" : "未订阅");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("发送至 " + subscriptions.findAll().size() + " 个订阅");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("订单")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "打开首页", "/")))
          .build());
      sent.thenAccept(v -> report.accept("已送达"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("一个订阅已过期并已被移除");
        } else {
          report.accept(error.getMessage());
        }

        return null;
      });
    }
  }
}
```

</ExpandableCode>

<!-- vale on -->
