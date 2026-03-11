package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.Condition;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.PercentageRenderer;
import com.webforj.component.table.renderer.ProgressBarRenderer;
import com.webforj.component.table.renderer.StatusDotRenderer;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Lazy Render")
public class LazyRenderView extends Composite<Div> {

  private final Div self = getBoundComponent();

  public LazyRenderView() {
    Table<Server> table = new Table<>();
    table.setWidth("100vw");
    table.setHeight("100vh");
    table.setStriped(true);

    table.addColumn("hostname", Server::getHostname)
        .setFlex(1.5f)
        .setLabel("Hostname");

    table.addColumn("online", Server::isOnline)
        .setFlex(1f)
        .setLabel("Online")
        .setRenderer(new BooleanRenderer<>());

    table.addColumn("status", Server::getStatus)
        .setFlex(1.0f)
        .setLabel("Health")
        .setRenderer(new StatusDotRenderer<Server>()
            .addMapping("Healthy",  Theme.SUCCESS)
            .addMapping("Warning",  Theme.WARNING)
            .addMapping("Critical", Theme.DANGER)
            .addMapping("Offline",  Theme.DEFAULT));

    table.addColumn("cpuUsage", Server::getCpuUsage)
        .setFlex(1.5f)
        .setLabel("CPU %")
        .setLazyRender(true)
        .setRenderer(new ConditionalRenderer<Server>()
            .when(Condition.greaterThanOrEqual(85),
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.DANGER).setTextVisible(true))
            .when(Condition.greaterThanOrEqual(70),
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.WARNING).setTextVisible(true))
            .otherwise(
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.SUCCESS).setTextVisible(true)));

    table.addColumn("memoryUsage", Server::getMemoryUsage)
        .setFlex(1.5f)
        .setLabel("Memory %")
        .setLazyRender(true)
        .setRenderer(new ConditionalRenderer<Server>()
            .when(Condition.greaterThanOrEqual(85),
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.DANGER).setTextVisible(true))
            .when(Condition.greaterThanOrEqual(70),
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.WARNING).setTextVisible(true))
            .otherwise(
                new ProgressBarRenderer<Server>().setMax(100).setTheme(Theme.SUCCESS).setTextVisible(true)));

    table.addColumn("diskUsage", Server::getDiskUsage)
        .setFlex(1.0f)
        .setLabel("Disk %")
        .setLazyRender(true)
        .setRenderer(new PercentageRenderer<>(Theme.INFO));


    table.getColumns().forEach(col -> {
      col.setSortable(true);
      col.setResizable(true);
    });

    table.setRepository(ServerService.getServers());
    self.add(table);
  }
}