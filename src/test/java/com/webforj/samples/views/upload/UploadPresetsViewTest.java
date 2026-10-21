package com.webforj.samples.views.upload;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.UploadedFile;
import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.html.elements.H4;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.upload.Upload;
import com.webforj.component.upload.event.UploadChangeEvent;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@WebforjTest
class UploadPresetsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new UploadPresetsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @ParameterizedTest
  @MethodSource("providePresetParts")
  void shouldShowThePartsTheNamedPresetBundles(
      String heading, Upload.Preset preset, Set<Upload.Part> parts, WebforjTestbed context) {
    context.add(new UploadPresetsView());

    var card =
        ComponentLocator.of(FlexLayout.class)
            .byClassName("upload-presets__subcard")
            .has(ComponentLocator.of(H4.class).byText(heading))
            .locate();
    var upload = ComponentLocator.of(Upload.class).within(card).locate();

    assertEquals(preset, upload.getPreset());
    assertEquals(
        parts,
        Arrays.stream(Upload.Part.values()).filter(upload::isVisible).collect(Collectors.toSet()));
  }

  @Test
  void shouldShowThePickedFileNextToTheInlinePicker(WebforjTestbed context) {
    context.add(new UploadPresetsView());

    var upload =
        ComponentLocator.of(Upload.class)
            .filter(found -> found.getPreset() == Upload.Preset.INLINE)
            .locate();

    UserInteraction.fireEvent(
        new UploadChangeEvent(upload, Map.of("files", List.of(new UploadedFile("resume.pdf")))));

    assertEquals("resume.pdf", upload.getText());
  }

  private static Stream<Arguments> providePresetParts() {
    return Stream.of(
        Arguments.of(
            "FULL",
            Upload.Preset.FULL,
            Set.of(
                Upload.Part.PICKER_BUTTON,
                Upload.Part.DROP_LABEL,
                Upload.Part.LIST,
                Upload.Part.UPLOAD_BUTTON)),
        Arguments.of(
            "INLINE",
            Upload.Preset.INLINE,
            Set.of(Upload.Part.PICKER_BUTTON, Upload.Part.DROP_LABEL)),
        Arguments.of("BUTTON_ONLY", Upload.Preset.BUTTON_ONLY, Set.of(Upload.Part.PICKER_BUTTON)),
        Arguments.of(
            "DROPZONE", Upload.Preset.DROPZONE, Set.of(Upload.Part.DROP_LABEL, Upload.Part.LIST)));
  }
}
