package com.webforj.samples.views.googlecharts;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InlineStyleSheet("context://css/googlecharts/chartRedraw.css")
@Route
@FrameTitle("Chart Redraw")
public class ChartRedrawView extends Composite<Div> {

  private static final String COLOR = "color";
  private static final String TEXT_STYLE = "textStyle";
  private static final String TITLE = "title";

  private final GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);
  private final Button redrawButton = new Button("Redraw Chart");

  public ChartRedrawView() {
    getBoundComponent().addClassName("window");

    Div chartContainer = new Div();
    chartContainer.addClassName("chart-container");
    chartContainer.add(chart);

    Map<String, Object> options = new HashMap<>();
    options.put(TITLE, "Social Media Following");
    options.put("colors", List.of("#006fe6"));
    options.put("backgroundColor", "transparent");
    options.put("chartArea", Map.of("width", "80%", "height", "70%"));
    options.put("hAxis", Map.of(TEXT_STYLE, Map.of(COLOR, "#333")));
    options.put("vAxis", Map.of("minValue", 0, TEXT_STYLE, Map.of(COLOR, "#333")));
    options.put("legend", Map.of("position", "bottom"));
    chart.setOptions(options);

    List<Object> data = new ArrayList<>();
    List<String> columns = List.of("Category", "Number of Followers in Thousands");
    data.add(columns);

    FlexLayout inputContainer = new FlexLayout();
    inputContainer.addClassName("input-container");
    inputContainer.setJustifyContent(FlexJustifyContent.CENTER);
    inputContainer.setWrap(FlexWrap.WRAP);
    inputContainer.setSpacing("10px");

    String[] categories = {"Instagram", "Twitter", "Facebook", "LinkedIn"};
    Map<String, NumberField> valueFields = new HashMap<>();

    for (String category : categories) {
      List<Object> row = new ArrayList<>();
      row.add(category);
      row.add(100);
      data.add(row);

      NumberField valueField = new NumberField("Value for " + category);
      valueField.setPlaceholder("");
      valueField.setStep(1.0);
      valueField.addClassName("number-field");
      valueField.setText("100");
      inputContainer.add(valueField);
      valueFields.put(category, valueField);
    }

    chart.setData(data);

    Div buttonContainer = new Div();
    buttonContainer.addClassName("redraw-button-container");
    buttonContainer.add(redrawButton.setTheme(ButtonTheme.PRIMARY));

    redrawButton.addClassName("redraw-button");
    redrawButton.addClickListener(e -> {
      List<Object> newData = new ArrayList<>();
      newData.add(columns);
      boolean allValuesValid = true;

      for (String category : categories) {
        NumberField valueField = valueFields.get(category);
        String fieldValue = valueField.getText();

        if (!fieldValue.matches("^[1-9]\\d*$")) { 
          allValuesValid = false;
          break;
        }

        int value = Integer.parseInt(fieldValue);
        newData.add(List.of(category, value));
      }

      if (!allValuesValid) {
        Toast.show("Enter a valid number", 3000);
      } else {
        chart.setData(newData);
        chart.redraw();
      }
    });

    getBoundComponent().add(chartContainer, inputContainer, buttonContainer);
  }
}