package com.webforj.samples.views.avatar;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Arrays;
import java.util.function.Predicate;

@Route
@FrameTitle("Avatar Expanses")
public class AvatarExpansesView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AvatarExpansesView() {
    self.setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    Arrays.asList(AvatarExpanse.values())
            .reversed()
            .stream()
            .filter(Predicate.not(Predicate.isEqual(AvatarExpanse.NONE)))
            .map(expanse -> new Avatar("John Doe")
                    .setExpanse(expanse))
            .forEach(self::add);
  }
}