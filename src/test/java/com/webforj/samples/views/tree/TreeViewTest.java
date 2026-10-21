package com.webforj.samples.views.tree;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TreeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new TreeView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldLinkEveryNodeToItsParentUpToTheHiddenRoot(WebforjTestbed context) {
    context.add(new TreeView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var report = Tree.findByKey(tree.getRoot(), "January.pdf").orElseThrow();

    List<String> ancestors = new ArrayList<>();
    TreeNode parent = report.getParent().orElseThrow();
    while (parent != tree.getRoot()) {
      ancestors.add(parent.getText());
      parent = parent.getParent().orElseThrow();
    }

    assertEquals(List.of("2023", "Reports", "Documents"), ancestors);
  }
}
