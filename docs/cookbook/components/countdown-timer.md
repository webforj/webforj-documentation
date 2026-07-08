---
title: "Countdown Timer Component"
description: "A custom component that creates a countdown timer for events."
tags: [components]
components: []
difficulty: intermediate
---

This recipe lets you add a countdown timer to your app for deadlines, end-of-day sales, or other time-sensitive events.

```java
import com.webforj.Interval;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;

@StyleSheet("ws://cookbook-static/countdown-timer.css")
public class CountdownTimer extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final String[] timerUnits = {"Days", "Hours", "Minutes", "Seconds"};
  private final Interval interval;
  private Temporal targetTime = LocalDateTime.now();

  public CountdownTimer() {
    setLayout();
    interval = new Interval(1f, e -> updateTimer());
  }

  public CountdownTimer setTimer(ZonedDateTime zonedDateTime) {
    this.targetTime = zonedDateTime;
    interval.start();
    updateTimer();
    return this;
  }

  public CountdownTimer setTimer(LocalDateTime localDateTime) {
    this.targetTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
    interval.start();
    updateTimer();
    return this;
  }

  public void setLayout() {
    self.addClassName("timer-container").setWidth("fit-content").setSpacing("var(--dwc-space-l)");

    for (int i = 0; i < timerUnits.length; i++) {
      String timeUnit = timerUnits[i];
      FlexLayout valueUnitLayout =
          new FlexLayout()
              .setDirection(FlexDirection.COLUMN)
              .setAlignment(FlexAlignment.CENTER)
              .setSpacing("0");
      Paragraph timeValueText = new Paragraph("00");
      timeValueText.addClassName("timer-value-text");
      Paragraph timeUnitText = new Paragraph(timeUnit);
      timeUnitText.addClassName("timer-unit-text");
      valueUnitLayout.add(timeValueText, timeUnitText);
      self.add(valueUnitLayout);
    }
  }

  public void updateTimer() {
    ZonedDateTime now = ZonedDateTime.now();

    Duration duration = Duration.between(now, targetTime);

    if (duration.isZero() || duration.isNegative()) {
      interval.stop();
      Toast toast = new Toast("Countdown finished!");
      toast.setTheme(Theme.SUCCESS).open();
      return;
    }

    long totalSeconds = duration.getSeconds();
    long days = TimeUnit.SECONDS.toDays(totalSeconds);
    long hours = TimeUnit.SECONDS.toHours(totalSeconds) % 24;
    long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
    long seconds = totalSeconds % 60;
    long[] timeValues = {days, hours, minutes, seconds};

    for (int i = 0; i < timeValues.length; i++) {
      FlexLayout valueUnitLayout = (FlexLayout) self.getComponents().get(i);
      Paragraph timeValueText = (Paragraph) valueUnitLayout.getComponents().get(0);
      timeValueText.setText(String.format("%02d", timeValues[i]));
    }
  }
}
```

Pair it with a styling from `src/main/resources/static/cookbook-static/countdown-timer` (a path outside `/cookbook/*` because the docs site reserves that namespace):

```css
.timer-container {
  border: var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color-gray);
  border-radius: var(--dwc-border-radius-3xl);
  padding: var(--dwc-space-m) var(--dwc-space-xl);
  background-color: var(--dwc-surface-3);
}

.timer-value-text {
  font-size: calc(var(--dwc-font-size-3xl)*3);
  margin: 0;
}

.timer-unit-text {
  font-size: var(--dwc-font-size-2xl);
  font-weight: var(--dwc-font-weight-bold);
  color: var(--dwc-color-gray-text-75);
  margin: 0;
}
```

Then, when you’re ready to add it to your UI, use `setTimer()` with a `ZonedDateTime` or `LocalDateTime` to define your endpoint, like so:

```java
ZoneId zone = ZoneId.of("America/New_York");
int nextYear =  Year.now().getValue() + 1;
ZonedDateTime endOfYear = ZonedDateTime.of(nextYear, 1, 1, 0, 0, 0, 0, zone);

CountdownTimer countdown = new CountdownTimer()
    .setTimer(endOfYear);
```
