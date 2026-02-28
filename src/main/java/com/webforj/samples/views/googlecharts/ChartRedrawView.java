package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.button.event.ButtonClickEvent;
import com.webforj.component.field.NumberField;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@StyleSheet("ws://css/googlecharts/chartRedraw.css")
@Route
@FrameTitle("Chart Redraw")
public class ChartRedrawView extends Composite<Div> {
  // self field enables fluent method chaining from the bound component
  private final Div self = getBoundComponent();

  private static final String COLOR = "color";
  private static final String TEXT_STYLE = "textStyle";
  private static final String TITLE = "title";
  private static final long MAX_ALLOWED = 1_000L;

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);
  private final Button redrawButton = new Button("Redraw Chart");
  private final List<String> categories = List.of("Instagram", "Twitter", "Facebook", "LinkedIn");
  private final Map<String, NumberField> valueFields;

  public ChartRedrawView() {
    this.valueFields = createValueFields();
    setupLayout();
  }

  private void setupLayout() {
    self.addClassName("window");

    Div chartContainer = createChartContainer();
    FlexLayout inputContainer = createInputContainer();
    Div buttonContainer = createButtonContainer();

    self.add(chartContainer, inputContainer, buttonContainer);
  }

  private Div createChartContainer() {
    Div chartContainer = new Div()
        .addClassName("chart-container");

    chartContainer.add(chart);
    setupChart();

    return chartContainer;
  }

  private void setupChart() {
    Map<String, Object> options = Map.of(
        TITLE, "Social Media Following",
        "colors", List.of("#006fe6"),
        "backgroundColor", "transparent",
        "chartArea", Map.of("width", "80%", "height", "70%"),
        "hAxis", Map.of(TEXT_STYLE, Map.of(COLOR, "#333")),
        "vAxis", Map.of("minValue", 0, TEXT_STYLE, Map.of(COLOR, "#333")),
        "legend", Map.of("position", "bottom")
    );
    chart.setOptions(options);

    List<Object> data = createChartData();
    chart.setData(data);
  }

  private List<Object> createChartData() {
    List<Object> data = new ArrayList<>();
    List<String> columns = List.of("Category", "Number of Followers in Thousands");
    data.add(columns);

    for (String category : categories) {
      List<Object> row = List.of(category, 100);
      data.add(row);
    }

    return data;
  }

  private Map<String, NumberField> createValueFields() {
    Map<String, NumberField> fields = new java.util.HashMap<>();

    FlexLayout inputContainer = new FlexLayout()
        .addClassName("input-container")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setWrap(FlexWrap.WRAP)
        .setSpacing("10px");

    for (String category : categories) {
      NumberField valueField = createNumberField(category);
      inputContainer.add(valueField);
      fields.put(category, valueField);
    }

    return fields;
  }

  private NumberField createNumberField(String category) {
    return new NumberField("Value for " + category)
        .setPlaceholder("")
        .setStep(1.0)
        .setMin(1.0)
        .setMax((double) MAX_ALLOWED)
        .setText("100")
        .addClassName("number-field");
  }

  private FlexLayout createInputContainer() {
    FlexLayout inputContainer = new FlexLayout()
        .addClassName("input-container")
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setWrap(FlexWrap.WRAP)
        .setSpacing("10px");

    for (NumberField field : valueFields.values()) {
      inputContainer.add(field);
    }

    return inputContainer;
  }

  private Div createButtonContainer() {
    Div buttonContainer = new Div()
        .addClassName("redraw-button-container");

    redrawButton.setTheme(ButtonTheme.PRIMARY);
    redrawButton.addClassName("redraw-button");
    redrawButton.onClick(this::onRedrawClick);

    buttonContainer.add(redrawButton);

    return buttonContainer;
  }

  private void onRedrawClick(ButtonClickEvent e) {
    List<Object> newData = new ArrayList<>();
    List<String> columns = List.of("Category", "Number of Followers in Thousands");
    newData.add(columns);
    boolean allValuesValid = true;

    for (String category : categories) {
      NumberField valueField = valueFields.get(category);
      String fieldValue = valueField.getText();

      try {
        long value = Long.parseLong(fieldValue);
        if (value <= 0 || value > MAX_ALLOWED) {
          allValuesValid = false;
          break;
        }
        newData.add(List.of(category, value));
      } catch (NumberFormatException ex) {
        allValuesValid = false;
        break;
      }
    }

    if (!allValuesValid) {
      Toast.show("Enter a valid number between 1 and " + MAX_ALLOWED, 3000, Theme.DANGER);
    } else {
      chart.setData(newData);
      chart.redraw();
    }
  }
}
