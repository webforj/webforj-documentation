package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.AvatarRenderer;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.CompositeRenderer;
import com.webforj.component.table.renderer.EmailRenderer;
import com.webforj.component.table.renderer.StatusDotRenderer;
import com.webforj.component.table.renderer.TextRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Employee Directory")
public class EmployeeDirectoryView extends Composite<Div> {

  private final Div self = getBoundComponent();

  public EmployeeDirectoryView() {
    Table<Employee> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table
        .addColumn("name", Employee::getName)
        .setFlex(2f)
        .setLabel("Employee")
        .setRenderer(new CompositeRenderer<>(new AvatarRenderer<>(), new TextRenderer<>()));

    table
        .addColumn("department", Employee::getDepartment)
        .setFlex(1.5f)
        .setLabel("Department")
        .setRenderer(new BadgeRenderer<>(BadgeTheme.OUTLINED_DEFAULT));

    table
        .addColumn("status", Employee::getStatus)
        .setFlex(1.5f)
        .setLabel("Status")
        .setRenderer(
            new StatusDotRenderer<Employee>()
                .addMapping("Active", Theme.SUCCESS)
                .addMapping("Remote", Theme.INFO)
                .addMapping("On Leave", Theme.WARNING));

    table
        .addColumn("fullTime", Employee::isFullTime)
        .setFlex(1f)
        .setLabel("Full-time")
        .setRenderer(new BooleanRenderer<>());

    table
        .addColumn("email", Employee::getEmail)
        .setFlex(2f)
        .setLabel("Email")
        .setRenderer(new EmailRenderer<>());

    table
        .getColumns()
        .forEach(
            col -> {
              col.setSortable(true);
              col.setResizable(true);
            });

    table.setRepository(EmployeeService.getEmployees());
    self.add(table);
  }
}
