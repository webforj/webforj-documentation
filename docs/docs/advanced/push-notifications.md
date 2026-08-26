---
sidebar_position: 39
sidebar_class_name: new-content
title: Push Notifications
description: Use the Push class, PushSender, and PushMessage to subscribe browsers and send notifications from the server, even when the app isn't open.
---

<DocChip chip='since' label='26.02' />
<JavadocLink type="push" location="com/webforj/push/Push" top='true'/>

Push notifications can reach users even when the app isn't open. The browser subscribes once, the app stores the subscription, and the server uses it to deliver notifications when an event occurs. <JavadocLink type="push" location="com/webforj/push/Push" code='true'>Push</JavadocLink> manages subscribing and unsubscribing in the browser. On the server, <JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> sends a <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> to a stored subscription.

<!-- INTRO_END -->

## Setup and prerequisites {#setup-and-prerequisites}

Push notifications are provided by a separate module. Add it to your app:

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

Push notifications require a servlet deployment, such as Jetty, Spring Boot, or a WAR file. They also require the deployment to sign notifications with the key pair generated below. The first time the app subscribes, the browser asks the user for permission and remembers the decision for that origin.

:::info Secure Origin Requirement
The app must be served from a secure origin, such as `https`, before a browser can subscribe.
Browsers reject subscriptions from insecure origins. The exception is an app served locally from `localhost` during development.

<!-- vale off -->
For more information about secure contexts and why they matter, see the [Secure Contexts MDN documentation](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

### Generating the keys {#generating-the-keys}

Push services accept only notifications signed by the deployment to which the browser subscribed. Run the [build plugin](/docs/configuration/build-plugin) once for each deployment to generate its key pair:

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

The command outputs three configuration lines. Paste them into `application.properties` without the quotes, or copy them as printed into `webforj.conf`. Replace the subject with the deployment's contact address. It must be a `mailto:` or `https://` address that push services can use to contact the operator.

```Ini title="application.properties"
webforj.push.public-key=...
webforj.push.private-key=...
webforj.push.subject=mailto:ops@example.com
```

| Property | Explanation |
|----------|-------------|
| `webforj.push.public-key` | The public half of the key pair used by the deployment to sign notifications |
| `webforj.push.private-key` | The private half of the key pair. Like any other secret, keep it out of source control |
| `webforj.push.subject` | The deployment's contact address. It must be a `mailto:` or `https://` address through which push services can reach the operator |

The app reads these properties at startup. If the configuration includes only some of them, startup fails and reports which properties are missing.

:::warning Rotating the keys
Each browser subscribes to one key pair. If the keys change, the push service rejects existing subscriptions. The next `subscribe()` call in each browser replaces its subscription.
:::

## How it works {#how-it-works}

The process has three steps, two of which happen in your code:

1. **Subscribe.** From a view, `Push.getCurrent().subscribe()` requests the user's permission and returns a `PushSubscription` that identifies the browser's address.
2. **Store.** The app saves the subscription with its data and associates it with the corresponding user.
3. **Send.** Later, from any thread, `PushSender.send(subscription, message)` passes the message to the browser vendor's push service. The service displays the notification whether or not the app is open.

```java
Push.getCurrent().subscribe().thenAccept(subscriptions::save);

sender.send(subscription,
    PushMessage.create("Order shipped").setUrl("/orders/42").build());
```

The following sections explain what the browser displays and how to handle failures at each step.

## Instance {#instance}

Retrieve the push instance for the current environment:

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

## Subscribing the browser {#subscribing-the-browser}

Call `subscribe()` in response to a user action, such as clicking an "Enable notifications" button. The returned <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> completes with the browser's <JavadocLink type="push" location="com/webforj/push/PushSubscription" code='true'>PushSubscription</JavadocLink>. If the browser can't subscribe, it completes exceptionally with a <JavadocLink type="push" location="com/webforj/push/exception/WebforjPushException" code='true'>WebforjPushException</JavadocLink>.

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

If the browser is already subscribed, calling `subscribe()` again returns the existing subscription. You can therefore call it safely on every visit.

:::info Browser permission
The first call to `subscribe()` prompts the user for permission. The browser displays this prompt, it isn't part of the app UI. Because browsers show the prompt only in response to a user action, call `subscribe()` from a click listener instead of the view constructor.
:::

### Storing subscriptions {#storing-subscriptions}

A subscription represents the address of one browser and belongs on the server. Store it with the app's data, using its endpoint as the key. Include any information the app needs to select the appropriate browsers later, such as the associated user. Each subscription contains three text values:

| Value | Meaning |
|-------|---------|
| `getEndpoint()` | The delivery URL assigned by the browser vendor's push service |
| `getP256dh()` | The browser's public key |
| `getAuth()` | The browser's authentication secret |

A user who subscribes from two browsers has two subscriptions. Delete a subscription when its browser unsubscribes or when a send reports that it has expired. See [Failure status](#failure-status).

### Restoring a subscription {#restoring-a-subscription}

`getSubscription()` returns the browser's current subscription, or an empty result if none exists. Use it to synchronize the server's copy, for example after the app's storage has been reset:

```java
Push.getCurrent().getSubscription().thenAccept(existing -> {
  existing.ifPresent(subscriptions::save);
});
```

Through <JavadocLink type="push" location="com/webforj/push/PushPermission" code='true'>PushPermission</JavadocLink>, `getPermission()` reports whether the user granted, denied, or hasn't yet answered the notification prompt. Use this result to hide the "Enable notifications" button when clicking it would have no effect.

### Unsubscribing {#unsubscribing}

`unsubscribe()` cancels the browser's subscription. It completes with the removed subscription so the app can delete its stored copy, or with an empty result if the browser had no subscription.

```java
Push.getCurrent().unsubscribe().thenAccept(removed -> {
  removed.ifPresent(subscriptions::delete);
});
```

## Sending notifications {#sending-notifications}

<JavadocLink type="push" location="com/webforj/push/PushSender" code='true'>PushSender</JavadocLink> sends a <JavadocLink type="push" location="com/webforj/push/PushMessage" code='true'>PushMessage</JavadocLink> to a stored subscription. It signs the message with the deployment's keys and passes it to the browser vendor's push service. That service wakes the browser and displays the notification. Because the operation never blocks the calling thread, you can invoke it from a click listener, scheduled job, or request handler.

After the properties are configured, the sender is available as a bean that you can inject into views, services, and scheduled jobs. To replace it, define your own `PushSender` bean.

```java
@Route("/orders")
public class OrdersView extends Composite<FlexLayout> {

  public OrdersView(PushSender sender, PushSubscriptions subscriptions) {
    // ...
  }
}
```

Without Spring, `new PushSender()` reads the keys from the app's configuration. Create the sender on an app thread, either in a view or in `App.run()`, and then use it from any thread. All senders share one connection pool to the push services, so there is no cost to creating one wherever needed.

For notifications that must be sent later or after the user leaves, use a timer on the server such as Spring's `TaskScheduler`. Don't use a page timer such as `Interval`, because it stops when the tab closes.

### Composing a message {#composing-a-message}

Create a message with its title, then configure every other option on the builder:

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

`send()` returns immediately. The <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> completes when the push service accepts the message, or completes exceptionally if the service doesn't accept it. If `send()` is called on an app thread, such as from a listener, its callbacks run on that thread and can update components. If the session that called `send()` ends before the response arrives, the callbacks don't run, but the notification is still delivered.

A send waits up to 30 seconds for the push service before failing with `UNREACHABLE`. Use `setTimeout(Duration)` to change the timeout for each sender.

| Option | Effect |
|--------|--------|
| `setBody` | Sets the text displayed below the title |
| `setIcon` | Sets the image displayed with the notification. It accepts absolute URLs and the `icons://` and `ws://` protocols. See [Assets](/docs/managing-resources/assets-protocols). It doesn't accept the `context://` protocol because push services limit a message to 4 KB |
| `setUrl` | Sets the page that opens when the user clicks the notification. Relative URLs are resolved against the app root. If no URL is set, the app root opens |
| `setActions` | Sets the buttons displayed on the notification, with a separate URL for each button. See [Browser support](#browser-support) |
| `setTag` | Sets an identifying tag. If a displayed notification has the same tag, the new notification replaces it |
| `setSilent` | Displays the notification without sound or vibration |
| `setTimeToLive` | Sets how long the push service retains the message for an offline device, up to four weeks |
| `setUrgency` | Uses <JavadocLink type="push" location="com/webforj/push/PushUrgency" code='true'>PushUrgency</JavadocLink> to let the device delay messages of low urgency and save battery |
| `setTopic` | Replaces a message that's still waiting at the push service when both messages have the same topic. Topics can contain at most 32 characters that are safe in a URL |

When a tab already displays the page, clicking the notification focuses the app. Otherwise, the page opens in a new tab. Clicking a notification button opens its URL in the same way.

:::info One notification per message
Every message displays a notification. Because browsers don't wake a page for a message that displays nothing, push can't be used for silent data updates.
:::

## Failure status {#failure-status}

When `subscribe()` or `send()` fails, its `PendingResult` reports a `WebforjPushException`. <JavadocLink type="push" location="com/webforj/push/PushStatus" code='true'>PushStatus</JavadocLink> identifies the reason:

| Status | When | What to do |
|--------|------|------------|
| `PERMISSION_DENIED` | The user has blocked notifications for the app | Explain where the user can allow notifications in the browser settings |
| `UNSUPPORTED` | Push isn't supported by the browser, the page isn't in a secure context, or the app isn't deployed as a servlet | Hide the feature |
| `NOT_CONFIGURED` | At least one `webforj.push.*` property is missing or incomplete | Generate the keys and configure all three properties |
| `SUBSCRIPTION_EXPIRED` | The push service no longer recognizes the subscription because the user unsubscribed or reinstalled the browser | Remove the stored subscription |
| `REJECTED` | The push service rejected the message; `getStatusCode()` contains its response | Verify the keys and message size |
| `UNREACHABLE` | The push service didn't respond before the timeout | Try again later |
| `UNKNOWN` | The stored endpoint isn't a valid URL, or the subscription or message couldn't be encoded | Verify the stored subscription |

Remove expired subscriptions during each send:

```java
sender.send(subscription, message).exceptionally(throwable -> {
  WebforjPushException error = (WebforjPushException) throwable;
  if (error.getStatus() == PushStatus.SUBSCRIPTION_EXPIRED) {
    subscriptions.delete(subscription);
  }

  return null;
});
```

:::tip Expiry arrives one message late
Push services deregister subscriptions lazily. They still accept the first message after a user unsubscribes, but it goes nowhere. The next message reports `SUBSCRIPTION_EXPIRED`. An accepted send means that the message reached the push service, not that the user saw it.
:::

## Browser support {#browser-support}

All major desktop and mobile browsers display push notifications after subscribing. Keep these limitations in mind:

- On iPhone and iPad, push works only for web apps added to the Home Screen on iOS 16.4 or later. In a Safari tab, `subscribe()` reports `UNSUPPORTED`. See [Installable Apps](/docs/configuration/installable-apps) for the required app manifest.
- Safari doesn't display notification buttons. It displays messages with actions without their buttons, but clicking the notification still opens the message URL.
- Android and iOS WebViews don't display notifications.

For the details per browser, see the MDN [showNotification compatibility table](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerRegistration/showNotification#browser_compatibility).

## Complete example {#complete-example}

The following view subscribes and unsubscribes the browser, stores subscriptions in memory, and sends a message to every stored subscription. It can send immediately or wait eight seconds by using Spring's `TaskScheduler`, allowing the tab to close before the notification arrives. The app class uses `@EnableScheduling` to make the scheduler available.

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
