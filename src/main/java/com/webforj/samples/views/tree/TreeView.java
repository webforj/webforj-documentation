package com.webforj.samples.views.tree;

import static com.webforj.component.tree.Tree.node;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tree.Tree;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("tree")
@FrameTitle("Tree View")
public class TreeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Tree tree = new Tree();

  public TreeView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSize("min-content", "100vh")
        .setStyle("overflow", "auto");

    tree.add(
        node("Documents")
            .setTooltipText("Work and personal documents")
            .add(
                node("Reports")
                    .setTooltipText("Monthly and annual reports")
                    .add(
                        node("2023")
                            .add(
                                node("January.pdf")
                                    .setTooltipText("January report"),
                                node("February.pdf")
                                    .setTooltipText("February report")),
                        node("2022")
                            .add(
                                node("Q4.pdf")
                                    .setTooltipText("Quarter 4 report"),
                                node("Q3.pdf")
                                    .setTooltipText("Quarter 3 report"))),
                node("Invoices")
                    .setTooltipText("Invoices and billing")
                    .add(
                        node("ClientA.pdf")
                            .setTooltipText("Invoice for Client A"),
                        node("ClientB.pdf")
                            .setTooltipText("Invoice for Client B"))),
        node("Pictures")
            .setTooltipText("Photos and images")
            .add(
                node("Vacations")
                    .add(
                        node("Beach.png")
                            .setTooltipText("Beach photo"),
                        node("Mountains.png")
                            .setTooltipText("Mountain photo")),
                node("Events")
                    .add(
                        node("Birthday.jpg")
                            .setTooltipText("Birthday party"))));

    tree.setStyle("margin", "var(--dwc-space-l)")
        .expand("Documents")
        .expand("Pictures")
        .expand("Vacations")
        .selectKey("Mountains.png");

    self.add(tree);
  }
}
