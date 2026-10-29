package com.webforj.samples.unit.views.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.tree.TreeInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import com.webforj.samples.views.tree.TreeSelectionView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

@WebforjTest
class TreeSelectionViewTest {
  @Test
  void shouldListTheSelectedNodesInTheDialog(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Show Selected Nodes").locate());

    var message = context.getShownOptionDialogs().get(0).getMessage();
    assertTrue(message.startsWith("<html> You have selected the following nodes"));
    assertTrue(message.contains("<li>Red</li>"));
    assertTrue(message.contains("<li>Green</li>"));
    assertTrue(message.contains("<li>Blue</li>"));
  }

  @Test
  void shouldReportThatNoNodeIsSelected(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    var tree = ComponentLocator.of(Tree.class).locate();
    for (String key : List.of("Red", "Green", "Blue")) {
      UserInteraction.deselect(tree, Tree.findByKey(tree.getRoot(), key).orElseThrow());
    }

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Show Selected Nodes").locate());

    assertEquals("There are no node selected", context.getShownOptionDialogs().get(0).getMessage());
  }

  @Test
  void shouldAddTheClickedNodeToTheSelectionInMultipleMode(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var shapes = Tree.findByKey(tree.getRoot(), "Shapes").orElseThrow();
    UserInteraction.perform(tree, TreeInteraction.expand(shapes));

    UserInteraction.click(tree, Tree.findByKey(tree.getRoot(), "Circle").orElseThrow());

    assertEquals(
        Set.of("Red", "Green", "Blue", "Circle"),
        tree.getSelectedItems().stream().map(TreeNode::getText).collect(Collectors.toSet()));
  }

  @Test
  void shouldKeepOnlyTheFirstSelectedNodeWhenMultiSelectionIsTurnedOff(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    var tree = ComponentLocator.of(Tree.class).locate();

    UserInteraction.uncheck(ComponentLocator.of(RadioButton.class).locate());

    assertEquals(Tree.SelectionMode.SINGLE, tree.getSelectionMode());
    assertEquals(1, tree.getSelectedItems().size());
    assertTrue(Set.of("Red", "Green", "Blue").contains(tree.getSelected().getText()));
  }

  @Test
  void shouldReplaceTheSelectionWithTheClickedNodeInSingleMode(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var shapes = Tree.findByKey(tree.getRoot(), "Shapes").orElseThrow();
    UserInteraction.uncheck(ComponentLocator.of(RadioButton.class).locate());
    UserInteraction.perform(tree, TreeInteraction.expand(shapes));

    UserInteraction.click(tree, Tree.findByKey(tree.getRoot(), "Circle").orElseThrow());

    assertEquals(
        List.of("Circle"), tree.getSelectedItems().stream().map(TreeNode::getText).toList());
  }

  @Test
  void shouldSwitchBackToMultipleSelection(WebforjTestbed context) {
    context.add(new TreeSelectionView());

    var tree = ComponentLocator.of(Tree.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    UserInteraction.uncheck(toggle);
    UserInteraction.check(toggle);

    assertEquals(Tree.SelectionMode.MULTIPLE, tree.getSelectionMode());
  }
}
