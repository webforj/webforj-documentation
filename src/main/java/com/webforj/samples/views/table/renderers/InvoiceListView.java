package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.table.Column.SortDirection;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.CompositeRenderer;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.IconButtonRenderer;
import com.webforj.component.table.renderer.MaskedNumberRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Locale;

@Route
@FrameTitle("Invoice List")
public class InvoiceListView extends Composite<Div> {

  private Div self = getBoundComponent();

  public InvoiceListView() {
    Table<Invoice> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table.addColumn("invoiceNumber", Invoice::getInvoiceNumber)
        .setFlex(0.7f)
        .setLabel("Invoice #");

    table.addColumn("customer", Invoice::getCustomer)
        .setFlex(1.5f)
        .setLabel("Customer");

    table.addColumn("issueDate", Invoice::getIssueDate)
        .setFlex(1.0f)
        .setLabel("Issued");

    table.addColumn("dueDate", Invoice::getDueDate)
        .setFlex(1.0f)
        .setLabel("Due");

    table.addColumn("amount", Invoice::getAmount)
        .setFlex(1.0f)
        .setLabel("Amount")
        .setSortDirection(SortDirection.DESC)
        .setRenderer(new MaskedNumberRenderer<>("$###,##0.00", Locale.US));

    table.addColumn("status", Invoice::getStatus)
        .setFlex(1f)
        .setLabel("Status")
        .setRenderer(new ConditionalRenderer<Invoice>()
            .when("Paid",
                new BadgeRenderer<Invoice>(BadgeTheme.SUCCESS)
                    .setIcon(TablerIcon.create("circle-check")))
            .when("Pending",
                new BadgeRenderer<Invoice>(BadgeTheme.WARNING)
                    .setIcon(TablerIcon.create("clock")))
            .when("Overdue",
                new BadgeRenderer<Invoice>(BadgeTheme.DANGER)
                    .setIcon(TablerIcon.create("alert-triangle")))
            .otherwise(new BadgeRenderer<>(BadgeTheme.DEFAULT)));

    table.addColumn("actions", i -> "")
        .setFlex(1f)
        .setLabel("")
        .setSortable(false)
        .setResizable(false)
        .setRenderer(new CompositeRenderer<>(
            new IconButtonRenderer<>(TablerIcon.create("eye")),
            new IconButtonRenderer<>(TablerIcon.create("download"))));


    table.setRepository(InvoiceService.getInvoices());
    self.add(table);
  }
}