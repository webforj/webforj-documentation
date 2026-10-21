package com.webforj.samples.views.tree;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.tree.Tree;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class TreeIconsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TreeIconsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheGlobalFolderIconsOnTheGroups(WebforjTestbed context) {
    context.add(new TreeIconsView());

    var tree = ComponentLocator.of(Tree.class).locate();

    assertEquals("feather:folder-plus", tree.getCollapsedIcon());
    assertEquals("feather:folder-minus", tree.getExpandedIcon());
  }

  @ParameterizedTest
  @CsvSource({
    "January.pdf, feather:file, feather:file-text",
    "Mountains.png, feather:image, feather:image"
  })
  void shouldOverrideTheGlobalIconsOnEachLeaf(
      String key, String icon, String selectedIcon, WebforjTestbed context) {
    context.add(new TreeIconsView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var leaf = Tree.findByKey(tree.getRoot(), key).orElseThrow();

    assertEquals(icon, leaf.getIcon());
    assertEquals(selectedIcon, leaf.getSelectedIcon());
  }
}
