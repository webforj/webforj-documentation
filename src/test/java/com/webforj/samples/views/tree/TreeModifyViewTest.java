package com.webforj.samples.views.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.tree.TreeInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@WebforjTest
class TreeModifyViewTest {
  @Test
  void shouldRenameTheDoubleClickedNodeToTheTextTheUserSaves(WebforjTestbed context) {
    context.add(new TreeModifyView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var projects = Tree.findByKey(tree.getRoot(), "Projects").orElseThrow();
    var node = Tree.findByKey(tree.getRoot(), "Alpha").orElseThrow();
    UserInteraction.perform(tree, TreeInteraction.expand(projects));

    context.answerInputDialog("Launch");
    UserInteraction.doubleClick(tree, node);

    assertEquals("Launch", node.getText());
    assertEquals("Modified: Launch", node.getTooltipText());
    assertEquals(
        List.of("Planning", "Execution", "Review"),
        node.getChildren().stream().map(TreeNode::getText).toList());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldKeepTheLabelWhenTheUserCancelsOrSavesNoText(String answer, WebforjTestbed context) {
    context.add(new TreeModifyView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var projects = Tree.findByKey(tree.getRoot(), "Projects").orElseThrow();
    var node = Tree.findByKey(tree.getRoot(), "Alpha").orElseThrow();
    UserInteraction.perform(tree, TreeInteraction.expand(projects));

    context.answerInputDialog(answer);
    UserInteraction.doubleClick(tree, node);

    assertEquals("Alpha", node.getText());
  }
}
