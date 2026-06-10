---
sidebar_position: 35
sidebar_class_name: new-content
title: Geolocation
description: >-
  Request and watch the device's geographic position using the Geolocation
  class, with high-accuracy, timeout, and maximum age controls.
_i18n_hash: 68083cf323f26b69a62bc3147145f4d2
---
# 地理定位 <DocChip chip='since' label='26.01' />

<JavadocLink type="foundation" location="com/webforj/geolocation/Geolocation" code='true'>Geolocation</JavadocLink> 类提供了与浏览器的地理定位子系统的接口。使用它可以一次请求设备的当前位置，或者监视位置以获取持续更新。

<!-- INTRO_END -->

## 设置和先决条件 {#setup-and-prerequisites}

地理定位 API 需要：

- **安全上下文** (HTTPS)。`localhost` 源是豁免的，可以在本地开发中通过 HTTP 工作。
- 用户位置访问权限。浏览器在第一次请求位置时会自动提示，并按来源持久化该选择。

当子系统不可用时，访问它将抛出 `WebforjRuntimeException`。

## 实例 {#instance}

获取当前环境的地理定位实例：

```java
import com.webforj.geolocation.Geolocation;

Geolocation geo = Geolocation.getCurrent();

if (Geolocation.isPresent()) {
  // ...
}

Geolocation.ifPresent(g -> {
  // ...
});
```

## 请求位置 {#requesting-a-position}

调用 `getCurrentPosition()` 来请求设备的当前地理位置。返回的 <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> 将与报告的 <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationPosition" code='true'>GeolocationPosition</JavadocLink> 完成，或者在浏览器无法获取位置时异常收到 <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink>。

```java
PendingResult<GeolocationPosition> request = Geolocation.getCurrent().getCurrentPosition();
request.thenAccept(position -> {
  double lat = position.getLatitude();
  double lng = position.getLongitude();
  double accuracy = position.getAccuracy();
});
request.exceptionally(throwable -> {
  WebforjGeolocationException error = (WebforjGeolocationException) throwable;
  GeolocationStatus status = error.getStatus();
  String message = error.getMessage();

  return null;
});
```

:::info 浏览器权限
浏览器可能会在第一次请求位置时提示用户获得权限。该提示由浏览器自身显示，并不属于应用 UI 的一部分。
:::

## 监视位置 {#watching-the-position}

注册一个监视监听器，以接收设备移动时的位置更新流。

```java
ListenerRegistration<GeolocationWatchEvent> registration =
    Geolocation.getCurrent().onWatch(event -> {
      if (event.isSuccess()) {
        GeolocationPosition position = event.getPosition().orElseThrow();
        // ...
      } else {
        GeolocationStatus status = event.getStatus();
        String message = event.getMessage().orElse("");
      }
    });

// 后来，停止监视：
registration.remove();
```

每次更新，无论成功与否，都会触发一个 <JavadocLink type="foundation" location="com/webforj/geolocation/event/GeolocationWatchEvent" code='true'>GeolocationWatchEvent</JavadocLink>。在读取位置之前，检查 `isSuccess()`。

## 配置请求 {#configuring-requests}

三个 setter 配置后续的地理定位信息请求。

### 高精度 {#high-accuracy}

```java
Geolocation.getCurrent().useHighAccuracy(true);
```

高精度属性提供了一种提示，表明应用希望接收最佳可能的结果。这可能导致响应时间变慢或增加功耗。用户也可能拒绝此功能，或设备可能无法提供比未指定此标志时更精确的结果。该属性的意图是让应用告知浏览器它们不需要高精度的地理定位结果，因此浏览器可以避免使用消耗大量电力的地理定位提供者。这对于运行在电池供电设备上的应用（如手机）尤其有用。

### 超时 {#timeout}

```java
Geolocation.getCurrent().useTimeout(10.0);
```

超时属性表示从调用 `getCurrentPosition()` 或监视监听器开始到浏览器返回位置之间允许经过的最长时间，以秒为单位。如果浏览器无法在指定的超时到期之前成功获取新位置，并且在此期间没有发生其他错误，则请求报告为 `TIMEOUT` 状态。获取用户权限所花费的时间不包括在超时属性所涵盖的时间内。超时属性仅适用于位置获取操作。

### 最大年龄 {#maximum-age}

```java
Geolocation.getCurrent().useMaximumAge(60.0);
```

最大年龄属性表示应用愿意接受的缓存位置，其年龄不得大于指定的时间（以秒为单位）。如果最大年龄设置为 `0`，则浏览器必须立即尝试获取新位置。如果浏览器没有可用的缓存位置，其年龄不超过指定的最大年龄，则必须获取新位置。在监视的情况下，最大年龄指的是返回的第一个位置。

## 失败状态 {#failure-status}

对于一次性请求，失败作为 <JavadocLink type="foundation" location="com/webforj/geolocation/exception/WebforjGeolocationException" code='true'>WebforjGeolocationException</JavadocLink> 交付在 `PendingResult` 上。对于监视更新，失败作为 `GeolocationWatchEvent` 交付，`isSuccess()` 返回 `false`。结果通过 <JavadocLink type="foundation" location="com/webforj/geolocation/GeolocationStatus" code='true'>GeolocationStatus</JavadocLink> 报告。

## 完整示例 {#complete-example}

下面的视图呈现三行带标签的纬度、经度和高度，以及一个请求当前位置的按钮。结果写入这些行中。状态通过一个重复使用的 toast 进行公告。

```java
package com.example;

import com.webforj.PendingResult;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.geolocation.Geolocation;
import com.webforj.geolocation.GeolocationPosition;
import com.webforj.geolocation.exception.WebforjGeolocationException;
import com.webforj.router.annotation.Route;
import java.util.Locale;

@Route("/")
public class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  private final Span latitudeValue = new Span("—");
  private final Span longitudeValue = new Span("—");
  private final Span altitudeValue = new Span("—");
  private final Button getPositionButton = new Button("获取位置", ButtonTheme.PRIMARY);
  private Toast currentToast;

  public MainView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    self.setMaxWidth("24em").setMargin("4em auto");

    self.add(
        new H2("地理定位"),
        row("纬度", latitudeValue),
        row("经度", longitudeValue),
        row("高度", altitudeValue),
        getPositionButton);

    getPositionButton.onClick(ev -> requestPosition());
  }

  private void requestPosition() {
    Geolocation geo = Geolocation.getCurrent();
    geo.useHighAccuracy(true)
        .useTimeout(10.0)
        .useMaximumAge(0.0);

    showToast("请求位置…", Theme.INFO);
    resetRows();

    PendingResult<GeolocationPosition> request = geo.getCurrentPosition();
    request.thenAccept(position -> {
      latitudeValue.setText(formatDegrees(position.getLatitude()));
      longitudeValue.setText(formatDegrees(position.getLongitude()));
      altitudeValue.setText(position.getAltitude()
          .map(alt -> String.format(Locale.ROOT, "%.0f m", alt))
          .orElse("—"));
      showToast("位置获取成功", Theme.SUCCESS);
    });
    request.exceptionally(throwable -> {
      WebforjGeolocationException error = (WebforjGeolocationException) throwable;
      showToast(error.getStatus() + ": " + error.getMessage(), Theme.DANGER);

      return null;
    });
  }

  private void showToast(String text, Theme theme) {
    if (currentToast != null) {
      currentToast.close();
    }
    currentToast = Toast.show(text, theme);
  }

  private void resetRows() {
    latitudeValue.setText("—");
    longitudeValue.setText("—");
    altitudeValue.setText("—");
  }

  private static String formatDegrees(double degrees) {
    return String.format(Locale.ROOT, "%.6f", degrees);
  }

  private static FlexLayout row(String label, Span value) {
    Span labelSpan = new Span(label);
    labelSpan.setMinWidth("6em");

    return new FlexLayout(labelSpan, value);
  }
}
```
