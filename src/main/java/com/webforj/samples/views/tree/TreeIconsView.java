package com.webforj.samples.views.tree;

import static com.webforj.component.tree.Tree.node;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.tree.Tree;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@Route(RouteConfig.TREE_ICONS)
@FrameTitle("Tree Icons View")
public class TreeIconsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Tree tree = new Tree();

  public TreeIconsView() {
    self.setDirection(FlexDirection.COLUMN)
        .setHeight("100vh")
        .setStyle("overflow", "auto");

    tree.setCollapsedIcon(FeatherIcon.CHEVRON_RIGHT.create());
    tree.setExpandedIcon(FeatherIcon.CHEVRON_DOWN.create());

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
                                    .setTooltipText("January report")
                                    .setIcon(FeatherIcon.FILE.create())
                                    .setSelectedIcon(FeatherIcon.FILE_TEXT.create()),
                                node("February.pdf")
                                    .setTooltipText("February report")
                                    .setIcon(FeatherIcon.FILE.create())
                                    .setSelectedIcon(FeatherIcon.FILE_TEXT.create())),
                        node("2022")
                            .add(
                                node("Q4.pdf")
                                    .setTooltipText("Quarter 4 report")
                                    .setIcon(FeatherIcon.FILE.create())
                                    .setSelectedIcon(FeatherIcon.FILE_TEXT.create()),
                                node("Q3.pdf")
                                    .setTooltipText("Quarter 3 report")
                                    .setIcon(FeatherIcon.FILE.create())
                                    .setSelectedIcon(FeatherIcon.FILE_TEXT.create()))),
                node("Invoices")
                    .setTooltipText("Invoices and billing")
                    .add(
                        node("ClientA.pdf")
                            .setTooltipText("Invoice for Client A")
                            .setIcon(FeatherIcon.FILE.create())
                            .setSelectedIcon(FeatherIcon.FILE_TEXT.create()),
                        node("ClientB.pdf")
                            .setTooltipText("Invoice for Client B")
                            .setIcon(FeatherIcon.FILE.create())
                            .setSelectedIcon(FeatherIcon.FILE_TEXT.create()))),
        node("Pictures")
            .setTooltipText("Photos and images")
            .add(
                node("Vacations")
                    .add(
                        node("Beach.png")
                            .setTooltipText("Beach photo")
                            .setIcon(FeatherIcon.IMAGE.create())
                            .setSelectedIcon(FeatherIcon.IMAGE.create()),
                        node("Mountains.png")
                            .setTooltipText("Mountain photo")
                            .setIcon(FeatherIcon.IMAGE.create())
                            .setSelectedIcon(FeatherIcon.IMAGE.create())),
                node("Events")
                    .add(
                        node("Birthday.jpg")
                            .setTooltipText("Birthday party")
                            .setIcon(FeatherIcon.IMAGE.create())
                            .setSelectedIcon(FeatherIcon.IMAGE.create()))));

    tree.setStyle("margin", "var(--dwc-space-l)")
        .expand("Documents")
        .expand("Pictures")
        .expand("Vacations")
        .selectKey("Mountains.png");

    self.add(tree);
  }
}
