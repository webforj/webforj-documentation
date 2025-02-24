package com.webforj.samples.views.slider;

import java.util.Map;
import static java.util.Map.entry;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.slider.Slider;
import com.webforj.component.slider.Slider.Orientation;

@Route
@FrameTitle("Slider Orientation")
public class SliderOrientationView extends Composite<FlexLayout> {
  
  FlexLayout layout = getBoundComponent();
  
  public SliderOrientationView() {
    layout.setDirection(FlexDirection.COLUMN).setAlignment(FlexAlignment.CENTER).setHeight("100%");
    
    Map<Integer, String> mapMatch = Map.ofEntries(
        entry(0, "0"),
        entry(50, "50"),
        entry(100, "100"));

    Slider sl1 = new Slider().setMax(100)
        .setMin(0);
    Slider sl2 = new Slider().setMax(100)
        .setMin(0);

    layout.add(sl1, sl2);

    sl1.setWidth("300px")
        .setTicksVisible(true)
        .setMinorTickSpacing(10)
        .setLabelsVisible(true)
        .setLabels(mapMatch);

    sl2.setOrientation(Orientation.VERTICAL)
        .setTicksVisible(true)
        .setMinorTickSpacing(10)
        .setLabelsVisible(true)
        .setLabels(mapMatch);
  }
}
