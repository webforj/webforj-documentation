---
sidebar_position: 39
title: 推送通知
description: >-
  Use the Push class, PushSender, and PushMessage to subscribe browsers and send
  notifications from the server, even when the app isn't open.
_i18n_hash: be5e6f5947bf38824dde0289d6415971
---
<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

即使应用未打开，推送通知也能触达用户。浏览器只需订阅一次，应用保存该订阅，服务器便可在事件发生时通过它发送通知。<JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink>负责在浏览器中订阅和取消订阅。在服务器端，<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink>会向已保存的订阅发送 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>。

<!-- INTRO_END -->

<VideoPlayer video="push-notifications/push" width={1920} height={1072} title="Push notifications" />

## 设置和前置条件 {#setup-and-prerequisites}

推送通知由单独的模块提供。将其添加到应用中：

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

推送通知需要满足以下条件：

- 使用 servlet 部署方式，例如 Jetty、Spring Boot 或 WAR 文件。
- 生成下文所述的密钥对，供部署用于签署通知。
- 使用安全来源。浏览器会拒绝通过 `https` 以外的来源提供的订阅；开发期间的 `localhost` 除外。

:::info 安全来源
<!-- vale off -->
有关安全上下文及其重要性的更多信息，请参阅 [MDN 安全上下文文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

### 生成密钥 {#generating-the-keys}

推送服务只接受由浏览器所订阅的部署签署的通知。针对每次部署运行一次[构建插件](/docs/configuration/build-plugin)，以生成其密钥对：

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

该命令会输出三行配置。去掉引号后，将它们粘贴到 `application.properties` 中；或者按原样复制到 `webforj.conf` 中。将 subject 替换为部署的联系地址。它必须是推送服务可用于联系运营方的 `mailto:` 或 `https://` 地址。

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| 属性 | 说明 |
|----------|-------------|
| `webforj.push.public-key` | 部署用于签署通知的密钥对中的公钥 |
| `webforj.push.private-key` | 密钥对中的私钥。与其他机密一样，请勿将其提交到源代码管理中 |
| `webforj.push.subject` | 部署的联系地址。它必须是推送服务可用于联系运营方的 `mailto:` 或 `https://` 地址 |

应用会在启动时读取这些属性。如果配置中只包含其中一部分，启动就会失败，并报告缺少哪些属性。

:::warning 轮换密钥
每个浏览器都会订阅一个密钥对。如果密钥发生变化，推送服务就会拒绝现有订阅。每个浏览器下次调用 `subscribe()` 时，都会替换其订阅。
:::

## 工作原理 {#how-it-works}

整个流程分为三个步骤：

1. **订阅。** 在视图中调用 `Push.getCurrent().subscribe()`，请求用户授权，并返回用于标识浏览器地址的 `PushSubscription`。
2. **存储。** 应用将订阅及其数据一并保存，并关联到相应用户。
3. **发送。** 之后，可在任意线程中调用 `PushSender.send(subscription, message)`，将消息传递给浏览器厂商的推送服务。无论应用是否打开，该服务都会显示通知。

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

以下各节将说明浏览器会显示什么，以及如何处理每个步骤中的失败。

## 实例 {#instance}

获取当前环境的推送实例：

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

响应用户操作时调用 `subscribe()`，例如用户点击“启用通知”按钮时。返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 会在操作完成后返回浏览器的 <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>。如果浏览器无法订阅，操作会以 <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink> 异常结束。

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

如果浏览器已经订阅，再次调用 `subscribe()` 会返回现有订阅。因此，可以在每次访问时安全地调用它。

:::info 浏览器权限
首次调用 `subscribe()` 时，浏览器会提示用户授予权限。此提示由浏览器显示，并非应用 UI 的一部分。由于浏览器只会在用户操作后显示此提示，因此应从点击监听器中调用 `subscribe()`，而不是在视图构造函数中调用。

如果用户拒绝该提示，应用就无法再次针对该来源请求权限。
:::

### 存储订阅 {#storing-subscriptions}

订阅代表一个浏览器的地址，应存储在服务器上。将其与应用数据一同保存，并使用其 endpoint 作为键。还应包含应用稍后选择合适浏览器所需的信息，例如关联用户。每个订阅都包含三个文本值：

| 值 | 含义 |
|-------|---------|
| `getEndpoint()` | 浏览器厂商的推送服务分配的投递 URL |
| `getP256dh()` | 浏览器的公钥 |
| `getAuth()` | 浏览器的身份验证密钥 |

用户从两个浏览器订阅时，会有两个订阅。当浏览器取消订阅，或发送操作报告订阅已过期时，应删除相应订阅。请参阅[失败状态](#failure-status)。

### 恢复订阅 {#restoring-a-subscription}

`getSubscription()` 会返回浏览器当前的订阅；如果没有订阅，则返回空结果。可用它来同步服务器端副本，例如应用的存储已被重置时：

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

通过 <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>，`getPermission()` 会报告用户已授予权限、已拒绝权限，还是尚未对通知提示作出回应。可根据此结果，在点击“启用通知”按钮不会产生效果时将其隐藏。

### 取消订阅 {#unsubscribing}

`unsubscribe()` 会取消浏览器的订阅。操作完成后会返回已移除的订阅，以便应用删除已存储的副本；如果浏览器原本没有订阅，则返回空结果。

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## 发送通知 {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink>会向已存储的订阅发送 <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink>。它使用部署的密钥签署消息，并将消息传递给浏览器厂商的推送服务。该服务会唤醒浏览器并显示通知。由于此操作不会阻塞调用线程，因此可以从点击监听器、定时任务或请求处理程序中调用它。

配置好属性后，sender 会作为 bean 提供，可注入到视图、服务和定时任务中。如需替换它，请定义自己的 `PushSender` bean。

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

不使用 Spring 时，`new PushSender()` 会从应用配置中读取密钥。在应用线程中创建 sender，例如在视图或 `App.run()` 中创建，然后即可从任意线程使用它。所有 sender 共用一个连接池来连接推送服务，因此按需创建 sender 不会产生额外开销。

如果通知需要稍后发送，或需要在用户离开后发送，请使用服务器端定时器，例如 Spring 的 `TaskScheduler`。不要使用页面定时器（例如 `Interval`），因为它会在标签页关闭时停止。

### 撰写消息 {#composing-a-message}

创建消息时先指定标题，然后通过 builder 配置其他所有选项：

```java
PushMessage message = PushMessage.create("Order shipped")
    .setBody("Order #42 is on its way")
    .setIcon("icons://icon-192x192.png")
    .setUrl("/orders/42")
    .setActions(List.of(new PushAction("track", "Track", "/orders/42/tracking")))
    .build();

PendingResult<Void> sent = sender.send(subscription, message);
sent.thenAccept(v -> status.setText("Sent"));
sent.exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  status.setText(error.getStatus() + ": " + error.getMessage());

  return null;
});
```

`send()` 会立即返回。当推送服务接受消息时，<JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 会完成；如果服务未接受消息，则会以异常结束。如果从应用线程（例如监听器）调用 `send()`，其回调也会在该线程上运行，并可更新组件。如果发起 `send()` 的会话在响应到达前结束，回调就不会运行，但通知仍会送达。

如果推送服务在 30 秒内没有响应，发送操作会失败并返回 `UNREACHABLE`。可使用 `setTimeout(Duration)` 为每个 sender 更改超时时间。

| 选项 | 效果 |
|--------|--------|
| `setBody` | 设置标题下方显示的文本 |
| `setIcon` | 设置通知中显示的图片。接受绝对 URL，以及 `icons://` 和 `ws://` 协议。请参阅[资源](/docs/managing-resources/assets-protocols)。由于推送服务将消息限制为 4 KB，因此不接受 `context://` 协议 |
| `setUrl` | 设置用户点击通知时打开的页面。相对 URL 会以应用根路径为基准解析。如果未设置 URL，则打开应用根路径 |
| `setActions` | 设置通知上显示的按钮，并为每个按钮指定单独的 URL。请参阅[浏览器支持](#browser-support) |
| `setTag` | 设置用于标识通知的标签。如果已有显示中的通知带有相同标签，新通知会替换它 |
| `setSilent` | 显示通知时不播放声音或振动 |
| `setTimeToLive` | 设置推送服务为离线设备保留消息的时长，最长为四周 |
| `setUrgency` | 使用 <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink>，让设备延迟处理低优先级消息以节省电量 |
| `setTopic` | 如果两条消息的 topic 相同，则替换仍在推送服务中等待发送的消息。topic 最多可包含 32 个适用于 URL 的字符 |

如果某个标签页已在显示该页面，点击通知会让应用获得焦点；否则，页面会在新标签页中打开。点击通知按钮也会以相同方式打开其 URL。

:::info 每条消息都会显示一个通知
每条消息都会显示通知。由于浏览器不会唤醒一个不显示任何内容的页面，因此推送不能用于静默数据更新。
:::

## 失败状态 {#failure-status}

当 `subscribe()` 或 `send()` 失败时，其 `PendingResult` 会报告 `WebforjPushException`。<JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink>用于标识失败原因：

| 状态 | 发生情况 | 处理方式 |
|--------|------------|------------|
| `PERMISSION_DENIED` | 用户已在浏览器中屏蔽此应用的通知 | 说明用户如何在浏览器设置中允许通知 |
| `UNSUPPORTED` | 浏览器不支持推送、页面不处于安全上下文中，或应用不是以 servlet 方式部署 | 隐藏此功能 |
| `NOT_CONFIGURED` | 至少有一个 `webforj.push.*` 属性缺失或不完整 | 生成密钥并配置全部三个属性 |
| `SUBSCRIPTION_EXPIRED` | 用户取消订阅或重新安装浏览器后，推送服务不再识别该订阅 | 删除已存储的订阅 |
| `REJECTED` | 推送服务拒绝了消息；`getStatusCode()` 会包含其响应 | 检查密钥和消息大小 |
| `UNREACHABLE` | 推送服务在超时时间内未响应 | 稍后重试 |
| `UNKNOWN` | 已存储的 endpoint 不是有效 URL，或无法对订阅或消息进行编码 | 检查已存储的订阅 |

每次发送时移除已过期的订阅：

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip 过期状态会晚一条消息才报告
推送服务会延迟注销订阅。用户取消订阅后，服务仍会接受第一条消息，但消息不会送达。下一条消息才会报告 `SUBSCRIPTION_EXPIRED`。发送操作被接受，只表示消息已到达推送服务，并不代表用户看到了它。
:::

## 浏览器支持 {#browser-support}

所有主流桌面和移动浏览器在完成订阅后都能显示推送通知。请注意以下限制：

- 在 iPhone 和 iPad 上，推送功能仅适用于添加到主屏幕、运行 iOS 16.4 或更高版本的 Web 应用。在 Safari 标签页中，`subscribe()` 会报告 `UNSUPPORTED`。有关所需的应用清单，请参阅[可安装应用](/docs/configuration/installable-apps)。
- Safari 不会显示通知按钮。带有操作按钮的消息仍会显示，但不会显示这些按钮；点击通知仍会打开消息 URL。
- Android 和 iOS WebView 不会显示通知。

有关各浏览器的详细信息，请参阅 MDN 的 [showNotification 兼容性表](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility)。

## 完整示例 {#complete-example}

以下视图可订阅和取消订阅浏览器，在内存中存储订阅，并向每个已存储的订阅发送消息。它可以立即发送，也可以通过 Spring 的 `TaskScheduler` 等待八秒后发送，让标签页在通知到达前关闭。应用类使用 `@EnableScheduling` 来启用调度器。

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
  private final Paragraph status = new Paragraph("Checking subscription…");
  private final TextField message = new TextField("Message", "Order #42 is on its way");
  private final Button subscribe =
      new Button("Enable notifications", ButtonTheme.PRIMARY);
  private final Button unsubscribe = new Button("Disable notifications");
  private final Button sendNow = new Button("Send now");
  private final Button sendLater = new Button("Send in 8 seconds");

  public PushView(PushSubscriptions subscriptions, PushSender sender, TaskScheduler scheduler) {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    subscribe.onClick(ev -> Push.getCurrent().subscribe()
        .thenAccept(subscription -> {
          subscriptions.save(subscription);
          status.setText("Subscribed");
        })
        .exceptionally(throwable -> {
          WebforjPushException error = (WebforjPushException) throwable.getCause();
          status.setText(error.getStatus() == PushStatus.PERMISSION_DENIED
              ? "Notifications are blocked in this browser"
              : error.getMessage());

          return null;
        }));

    unsubscribe.onClick(ev -> Push.getCurrent().unsubscribe().thenAccept(removed -> {
      removed.ifPresent(subscriptions::delete);
      status.setText(removed.isPresent() ? "Unsubscribed" : "There was no subscription");
    }));

    sendNow.onClick(ev -> sendToAll(subscriptions, sender, message.getValue(), status::setText));

    sendLater.onClick(ev -> {
      String text = message.getValue();
      status.setText("Sending in 8 seconds, close the tab now");
      scheduler.schedule(() -> sendToAll(subscriptions, sender, text, outcome -> {
      }), Instant.now().plusSeconds(8));
    });

    Push.getCurrent().getSubscription().thenAccept(existing -> {
      existing.ifPresent(subscriptions::save);
      status.setText(existing.isPresent() ? "Subscribed" : "Not subscribed");
    });

    self.add(status, message, subscribe, unsubscribe, sendNow, sendLater);
  }

  private static void sendToAll(PushSubscriptions subscriptions, PushSender sender, String text,
      Consumer<String> report) {
    report.accept("Sending to " + subscriptions.findAll().size() + " subscriptions");

    for (PushSubscription subscription : subscriptions.findAll()) {
      PendingResult<Void> sent = sender.send(subscription, PushMessage.create("Orders")
          .setBody(text)
          .setIcon("icons://icon-192x192.png")
          .setUrl("/push")
          .setActions(List.of(new PushAction("home", "Open home", "/")))
          .build());
      sent.thenAccept(v -> report.accept("Delivered"));
      sent.exceptionally(throwable -> {
        WebforjPushException error = (WebforjPushException) throwable;
        if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
          subscriptions.delete(subscription);
          report.accept("A subscription expired and was removed");
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
