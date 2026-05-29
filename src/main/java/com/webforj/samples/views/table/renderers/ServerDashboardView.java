package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.component.table.Table.Border;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.Condition;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.PercentageRenderer;
import com.webforj.component.table.renderer.ProgressBarRenderer;
import com.webforj.component.table.renderer.StatusDotRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.EnumSet;

@Route
@FrameTitle("Server Dashboard")
public class ServerDashboardView extends Composite<Div> {

  private Div self = getBoundComponent();

  public ServerDashboardView() {
    Table<Server> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);
    table.setBordersVisible(EnumSet.of(Border.AROUND, Border.ROWS, Border.COLUMNS));

    table
        .addColumn("hostname", Server::getHostname)
        .setLabel("Hostname")
        .setMinWidth(160f)
        .setFlex(1f);

    table
        .addColumn("online", Server::isOnline)
        .setLabel("Online")
        .setWidth(80f)
        .setRenderer(new BooleanRenderer<>());

    table
        .addColumn("status", Server::getStatus)
        .setLabel("Health")
        .setWidth(100f)
        .setRenderer(
            new StatusDotRenderer<Server>()
                .addMapping("Healthy", Theme.SUCCESS)
                .addMapping("Warning", Theme.WARNING)
                .addMapping("Critical", Theme.DANGER)
                .addMapping("Offline", Theme.DEFAULT));

    table
        .addColumn("cpuUsage", Server::getCpuUsage)
        .setLabel("CPU %")
        .setWidth(140f)
        .setRenderer(
            new ConditionalRenderer<Server>()
                .when(
                    Condition.greaterThanOrEqual(85),
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.DANGER)
                        .setTextVisible(true))
                .when(
                    Condition.greaterThanOrEqual(70),
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.WARNING)
                        .setTextVisible(true))
                .otherwise(
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.SUCCESS)
                        .setTextVisible(true)));

    table
        .addColumn("memoryUsage", Server::getMemoryUsage)
        .setLabel("Memory %")
        .setWidth(140f)
        .setRenderer(
            new ConditionalRenderer<Server>()
                .when(
                    Condition.greaterThanOrEqual(85),
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.DANGER)
                        .setTextVisible(true))
                .when(
                    Condition.greaterThanOrEqual(70),
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.WARNING)
                        .setTextVisible(true))
                .otherwise(
                    new ProgressBarRenderer<Server>()
                        .setMax(100)
                        .setTheme(Theme.SUCCESS)
                        .setTextVisible(true)));

    table
        .addColumn("diskUsage", Server::getDiskUsage)
        .setLabel("Disk %")
        .setWidth(120f)
        .setRenderer(new PercentageRenderer<>(Theme.INFO));

    table
        .getColumns()
        .forEach(
            col -> {
              col.setSortable(true);
              col.setResizable(true);
            });

    table.setRepository(ServerService.getServers());
    self.add(table);
  }
}
