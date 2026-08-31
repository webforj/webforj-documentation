---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: 3e487693f1f11322be81f1c5a93c1ad0
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

推送通知可以在应用未打开时依然到达用户。浏览器只需订阅一次，应用保存订阅，服务器当事件发生时利用这个订阅发送通知。<JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> 负责在浏览器中管理订阅和退订。在服务器上，<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> 向已存储的订阅发送 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>。

<!-- INTRO_END -->

## 配置与先决条件 {#setup-and-prerequisites}

推送通知由一个单独的模块提供。将其添加到您的应用中：

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

- 一个 servlet 部署，如 Jetty、Spring Boot 或 WAR 文件。
- 一对密钥，下面生成，部署用于对通知进行签名。
- 安全的来源。浏览器拒绝通过非 `https` 的任何内容提供的订阅，开发时除了来自 `localhost` 的订阅。

:::info 安全来源
<!-- vale off -->
有关安全上下文及其重要性的更多信息，请参阅 [安全上下文 MDN 文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

### 生成密钥 {#generating-the-keys}

推送服务只接受由浏览器已订阅的部署签名的通知。每个部署运行一次 [构建插件](/docs/configuration/build-plugin) 以生成其密钥对：

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

命令输出三行配置。复制它们到 `application.properties` 中，不带引号，或将其原样复制到 `webforj.conf` 中。用部署的联系地址替换主题。它必须是推送服务可用来联系操作员的 `mailto:` 或 `https://` 地址。

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| 属性 | 说明 |
|----------|-------------|
| `webforj.push.public-key` | 部署用于签名通知的密钥对的公钥部分 |
| `webforj.push.private-key` | 密钥对的私钥部分。像其他任何秘密一样，请将其排除在源代码控制之外 |
| `webforj.push.subject` | 部署的联系地址。必须是推送服务可以联系操作员的 `mailto:` 或 `https://` 地址 |

应用在启动时读取这些属性。如果配置只包含其中一些，启动将失败并报告缺失的属性。

:::warning 旋转密钥
每个浏览器订阅一对密钥。如果密钥更改，推送服务将拒绝现有订阅。每个浏览器中的下一个 `subscribe()` 调用将替换其订阅。
:::

## 工作原理 {#how-it-works}

过程分为三个步骤：

1. **订阅。** 从视图中，`Push.getCurrent().subscribe()` 请求用户的权限并返回一个识别浏览器地址的 `PushSubscription`。
2. **存储。** 应用使用其数据保存订阅，并将其与相应的用户关联。
3. **发送。** 稍后，从任何线程中，`PushSender.send(subscription, message)` 将消息传递给浏览器供应商的推送服务。该服务显示通知，无论应用是否打开。

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("订单已发货").setUrl("/orders/42").build());
```

以下部分将说明浏览器显示的内容以及如何处理每个步骤中的失败。

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

在响应用户操作时调用 `subscribe()`，例如点击“启用通知”按钮。返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 以浏览器的 <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink> 完成。如果浏览器无法订阅，则以 <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> 异常完成。

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

如果浏览器已经订阅，调用 `subscribe()` 再次返回现有订阅。因此，可以在每次访问时安全地调用它。

:::info 浏览器权限
第一次调用 `subscribe()` 会提示用户授权。浏览器显示此提示，它不是应用 UI 的一部分。由于浏览器仅在响应用户操作时显示提示，因此应在点击监听器中调用 `subscribe()`，而不是在视图构造函数中。

如果用户阻止了提示，应用将无法再次对此来源发出提示。
:::

### 存储订阅 {#storing-subscriptions}

订阅表示一个浏览器的地址，并属于服务器。使用其端点作为键将其与应用数据一起存储。包含应用需要在以后的适当浏览器中选择的信息，例如关联的用户。每个订阅包含三个文本值：

| 值 | 意义 |
|-------|---------|
| `getEndpoint()` | 由浏览器供应商的推送服务分配的交付 URL |
| `getP256dh()` | 浏览器的公钥 |
| `getAuth()` | 浏览器的身份验证密钥 |

从两个浏览器订阅的用户有两个订阅。当其浏览器退订时或发送报告其已过期时，删除订阅。请参见 [失败状态](#failure-status)。

### 恢复订阅 {#restoring-a-subscription}

`getSubscription()` 返回浏览器的当前订阅，若不存在则返回空结果。可用于同步服务器的副本，例如在应用的存储被重置后：

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

通过 <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>，`getPermission()` 报告用户是否授予、拒绝或尚未回答通知提示。使用此结果在点击时隐藏“启用通知”按钮，这是无效的。

### 退订 {#unsubscribing}

`unsubscribe()` 取消浏览器的订阅。它以已删除的订阅完成，以便应用可以删除其存储的副本，或者如果浏览器没有订阅，则以空结果完成。

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## 发送通知 {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> 向存储的订阅发送 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>。它使用部署的密钥对消息进行签名，并将其传递给浏览器供应商的推送服务。该服务唤醒浏览器并显示通知。因为该操作永远不会阻塞调用线程，所以您可以在点击监听器、计划作业或请求处理程序中调用它。

配置好属性后，发送器作为一个 bean 可用于注入到视图、服务和计划作业中。要替换它，请定义您自己的 `PushSender` bean。

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

如果没有 Spring，`new PushSender()` 将从应用的配置中读取密钥。在线程中创建发送器，无论是视图中还是在 `App.run()` 中，然后可以从任何线程使用它。所有发送器共享一个连接池，以便推送服务，因此在需要时创建一个是不需要成本的。

对于必须稍后发送或在用户离开后发送的通知，使用服务器上的计时器，如 Spring 的 `TaskScheduler`。不要使用页面计时器，例如 `Interval`，因为它在标签关闭时停止。

### 构建消息 {#composing-a-message}

创建一个消息并设置其标题，然后配置构建器上的每个其他选项：

```java
PushMessage message = PushMessage.create("订单已发货")
    .setBody("订单 #42 正在路上")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "跟踪", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("已发送"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` 立即返回。<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 在推送服务接受消息时完成，或者在服务不接受消息时异常完成。如果在应用线程上调用 `send()`，例如从监听器中，它的回调在该线程上运行并可以更新组件。如果调用 `send()` 的会话在响应到达之前结束，则回调不运行，但通知仍然会发送。

发送等待推送服务最多 30 秒，然后失败并返回 `UNREACHABLE`。使用 `setTimeout(Duration)` 更改每个发送器的超时。

| 选项 | 效果 |
|--------|--------|
| `setBody` | 设置显示在标题下方的文本 |
| `setIcon` | 设置与通知一起显示的图像。接受绝对 URL 以及 `icons://` 和 `ws://` 协议。请参见 [资源](/docs/managing-resources/assets-protocols)。不接受 `context://` 协议，因为推送服务将消息限制在 4 KB |
| `setUrl` | 设置用户点击通知时打开的页面。相对 URL 是相对于应用根解析的。如果未设置 URL，则打开应用根 |
| `setActions` | 设置在通知上显示的按钮，每个按钮具有单独的 URL。请参见 [浏览器支持](#browser-support) |
| `setTag` | 设置标识标签。如果显示的通知具有相同的标签，则新通知将替换它 |
| `setSilent` | 无声或无振动地显示通知 |
| `setTimeToLive` | 设置推送服务在离线设备上保留消息的时间，最长为四周 |
| `setUrgency` | 使用 <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> 让设备延迟低优先级消息并节省电池 |
| `setTopic` | 替换仍在推送服务中等待的消息，当两条消息具有相同主题时。主题最多可以包含 32 个在 URL 中安全的字符 |

当标签已显示页面时，单击通知将焦点移到应用上。否则，页面将在新标签中打开。单击通知按钮以相同方式打开其 URL。

:::info 每条消息一个通知
每条消息都会显示通知。由于浏览器不会因显示为空的消息而唤醒页面，因此无法将推送用于静默数据更新。
:::

## 失败状态 {#failure-status}

当 `subscribe()` 或 `send()` 失败时，其 `PendingResult` 会报告 `WebforjPushException`。<JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> 确定原因：

| 状态 | 何时 | 应该做什么 |
|--------|------|------------|
| `PERMISSION_DENIED` | 用户已阻止该应用的通知 | 解释用户可以在浏览器设置中允许通知的位置 |
| `UNSUPPORTED` | 浏览器不支持推送，页面不在安全上下文中，或应用未作为 servlet 部署 | 隐藏功能 |
| `NOT_CONFIGURED` | 至少缺少或不完整一个 `webforj.push.*` 属性 | 生成密钥并配置所有三个属性 |
| `SUBSCRIPTION_EXPIRED` | 推送服务不再识别订阅，因为用户退订或重新安装浏览器 | 删除存储的订阅 |
| `REJECTED` | 推送服务拒绝了消息； `getStatusCode()` 包含其响应 | 验证密钥和消息大小 |
| `UNREACHABLE` | 推送服务未在超时之前响应 | 稍后重试 |
| `UNKNOWN` | 存储的端点不是有效的 URL，或订阅或消息无法编码 | 验证存储的订阅 |

在每次发送时删除过期的订阅：

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip 过期到达晚一条消息
推送服务懒惰地注销订阅。它们仍然接受用户退订后的第一条消息，但这条消息将无处可去。下一条消息报告 `SUBSCRIPTION_EXPIRED`。接受的发送表示消息已到达推送服务，而不是用户已看到。
:::

## 浏览器支持 {#browser-support}

所有主要桌面和移动浏览器在订阅后显示推送通知。请牢记这些限制：

- 在 iPhone 和 iPad 上，推送仅适用于在 iOS 16.4 或更高版本上添加到主屏幕的网络应用。在 Safari 标签中，`subscribe()` 报告 `UNSUPPORTED`。请参阅 [可安装应用](/docs/configuration/installable-apps) 以了解所需的应用清单。
- Safari 不显示通知按钮。它不带按钮地显示带有操作的消息，但单击通知仍会打开消息 URL。
- Android 和 iOS WebViews 不显示通知。

有关每个浏览器的详细信息，请参阅 MDN [showNotification 兼容性表](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility)。

## 完整示例 {#complete-example}

以下视图订阅和退订浏览器，将订阅存储在内存中，并向每个存储的订阅发送消息。可以立即发送，也可以使用 Spring 的 `TaskScheduler` 等待八秒，允许标签在通知到达之前关闭。应用类使用 `@EnableScheduling` 使调度器可用。

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
  private final Paragraph status = new Paragraph("检查订阅中...");
  private final TextField message = new TextField("消息", "订单 #42 正在路上");
  private final Button subscribe =
      new Button("启用通知", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("禁用通知");
  private final Button sendNow = new Button("立即发送");
  private final Button sendLater = new Button("8秒后发送");

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
              ? "此浏览器已阻止通知"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "已退订" : "没有任何订阅");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("将在8秒后发送，现在可以关闭标签");
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
    report.accept("正在发送给 " + subscriptions.findAll().size() + " 个订阅");

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
          report.accept("一个订阅过期并已被删除");
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
