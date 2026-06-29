package com.webforj.samples.views.elementcomposite;

import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.element.ElementComposite;
import com.webforj.component.element.PropertyDescriptor;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.time.Duration;
import java.time.Instant;

@Route
@FrameTitle("Recent Activity")
@StyleSheet("ws://element-composite/activityfeed.css")
public class RelativeTimePropertiesView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public RelativeTimePropertiesView() {
    FlexLayout container = new FlexLayout();
    container.setDirection(FlexDirection.COLUMN);
    container.addClassName("activity-feed");

    H3 heading = new H3("Recent activity");
    heading.addClassName("activity-feed__heading");
    container.add(heading);

    container.add(
        row(
            "Alice Chen",
            AvatarTheme.PRIMARY,
            "commented on Q4 roadmap",
            min(2),
            RelativeTime.Format.LONG,
            RelativeTime.Numeric.AUTO));
    container.add(divider());
    container.add(
        row(
            "Bob Martinez",
            AvatarTheme.SUCCESS,
            "updated design spec",
            hr(3),
            RelativeTime.Format.SHORT,
            RelativeTime.Numeric.AUTO));
    container.add(divider());
    container.add(
        row(
            "Carlos Singh",
            AvatarTheme.WARNING,
            "shared brand guidelines",
            day(1),
            RelativeTime.Format.LONG,
            RelativeTime.Numeric.ALWAYS));
    container.add(divider());
    container.add(
        row(
            "Dana Park",
            AvatarTheme.DANGER,
            "joined the team",
            day(7),
            RelativeTime.Format.NARROW,
            RelativeTime.Numeric.ALWAYS));

    self.setJustifyContent(FlexJustifyContent.CENTER)
        .setMargin("var(--dwc-space-l)")
        .add(container);
  }

  private static Instant min(long minutes) {
    return Instant.now().minus(Duration.ofMinutes(minutes));
  }

  private static Instant hr(long hours) {
    return Instant.now().minus(Duration.ofHours(hours));
  }

  private static Instant day(long days) {
    return Instant.now().minus(Duration.ofDays(days));
  }

  private FlexLayout row(
      String name,
      AvatarTheme theme,
      String action,
      Instant when,
      RelativeTime.Format format,
      RelativeTime.Numeric numeric) {
    Avatar avatar = new Avatar(name);
    avatar.setTheme(theme);

    Span message = new Span();
    message.getElement().setHtml("<strong>" + name + "</strong> " + action);
    message.addClassName("activity-feed__message");

    RelativeTime time = new RelativeTime();
    time.setDate(when);
    time.setFormat(format);
    time.setNumeric(numeric);
    time.addClassName("activity-feed__time");

    FlexLayout textColumn = new FlexLayout(message, time);
    textColumn.setDirection(FlexDirection.COLUMN);
    textColumn.setSpacing("0");
    textColumn.setStyle("flex", "1");

    FlexLayout row = new FlexLayout(avatar, textColumn);
    row.setSpacing("var(--dwc-space-s)");
    row.setAlignment(FlexAlignment.START);
    row.addClassName("activity-feed__row");
    return row;
  }

  private Div divider() {
    Div divider = new Div();
    divider.addClassName("activity-feed__divider");
    return divider;
  }

  /** Wrapper for the Shoelace relative-time web component. */
  @JavaScript(
      value =
          "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js",
      attributes = {@Attribute(name = "type", value = "module")})
  @StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
  @NodeName("sl-relative-time")
  public static final class RelativeTime extends ElementComposite
      implements HasClassName<RelativeTime>, HasStyle<RelativeTime> {

    private final PropertyDescriptor<String> date = PropertyDescriptor.property("date", "");
    private final PropertyDescriptor<String> format = PropertyDescriptor.property("format", "long");
    private final PropertyDescriptor<String> numeric =
        PropertyDescriptor.property("numeric", "auto");
    private final PropertyDescriptor<Boolean> sync = PropertyDescriptor.property("sync", false);

    public RelativeTime setDate(Instant instant) {
      set(date, instant.toString());
      return this;
    }

    public RelativeTime setFormat(Format format) {
      set(this.format, format.value);
      return this;
    }

    public RelativeTime setNumeric(Numeric numeric) {
      set(this.numeric, numeric.value);
      return this;
    }

    public RelativeTime setSync(boolean sync) {
      set(this.sync, sync);
      return this;
    }

    public enum Format {
      LONG("long"),
      SHORT("short"),
      NARROW("narrow");

      final String value;

      Format(String value) {
        this.value = value;
      }
    }

    public enum Numeric {
      ALWAYS("always"),
      AUTO("auto");

      final String value;

      Numeric(String value) {
        this.value = value;
      }
    }
  }
}
