package com.webforj.samples.views.blogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.webforj.annotation.InlineStyleSheet;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.applayout.AppDrawerToggle;
import com.webforj.component.layout.applayout.AppLayout;
import com.webforj.component.layout.appnav.AppNav;
import com.webforj.component.layout.appnav.AppNavItem;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.H4;
import com.webforj.component.icons.TablerIcon;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/stylesheet/dashboard.css")
@InlineStyleSheet("""
    .modern-dashboard {
        background: linear-gradient(135deg, var(--dwc-surface-1) 0%, var(--dwc-surface-2) 100%);
    }
    
    .page-title {
        background: linear-gradient(135deg, var(--dwc-color-primary-40), var(--dwc-color-primary-30));
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
    }
    
    .metrics-grid {
        grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    }
    
    .charts-grid {
        grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
    }
    
    .actions-grid {
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    }
    
    .chart-container {
        height: 600px;
        border-top: 3px solid var(--dwc-color-primary-40);
    }
""")
@Route
public class ModernDashboardView extends Composite<AppLayout> {
    
    public ModernDashboardView() {
        AppLayout layout = getBoundComponent();
        layout.addClassName("modern-dashboard");
        
        Toolbar header = new Toolbar();
        header.addToStart(new AppDrawerToggle())
              .addToTitle(new H2("Analytics Dashboard"));
        layout.addToHeader(header);
        
        AppNav drawerMenu = new AppNav();
        drawerMenu.addItem(new AppNavItem("Overview", ModernDashboardView.class, TablerIcon.create("dashboard")));
        drawerMenu.addItem(new AppNavItem("Analytics", ModernDashboardView.class, TablerIcon.create("chart-line")));
        drawerMenu.addItem(new AppNavItem("Users", ModernDashboardView.class, TablerIcon.create("users")));
        drawerMenu.addItem(new AppNavItem("Settings", ModernDashboardView.class, TablerIcon.create("settings")));
        layout.addToDrawer(drawerMenu);
        
        createMainContent();
    }
    
    private Div createMainContent() {
        H2 pageTitle = new H2("Overview");
        pageTitle.addClassName("page-title");
        
        Div metricsGrid = createMetricsGrid();
        Div chartsSection = createChartsSection();
        Div actionsSection = createQuickActionsSection();
        
        AppLayout layout = getBoundComponent();
        layout.add(pageTitle, metricsGrid, chartsSection, actionsSection);
        
        return null;
    }
    
    private Div createMetricsGrid() {
        Div grid = new Div();
        grid.addClassName("metrics-grid");
        
        grid.add(
            createMetricCard("Total Revenue", "$124,592", "+12.5%", "up"),
            createMetricCard("Active Users", "8,942", "+5.2%", "up"),
            createMetricCard("Conversion Rate", "3.2%", "-0.8%", "down"),
            createMetricCard("Server Load", "87%", "+15.3%", "neutral")
        );
        
        return grid;
    }
    
    private Div createChartsSection() {
        Div section = new Div();
        section.addClassName("charts-section");
        
        H3 sectionTitle = new H3("Performance Trends");
        sectionTitle.addClassName("section-title");
        
        Div chartsGrid = new Div();
        chartsGrid.addClassName("charts-grid");
        
        GoogleChart revenueChart = createRevenueChart();
        Div revenueContainer = createChartContainer("Monthly Revenue", revenueChart);
        
        GoogleChart activityChart = createActivityChart();
        Div activityContainer = createChartContainer("User Activity", activityChart);
        
        chartsGrid.add(revenueContainer, activityContainer);
        section.add(sectionTitle, chartsGrid);
        return section;
    }
    
    private Div createChartContainer(String title, GoogleChart chart) {
        Div container = new Div();
        container.addClassName("chart-container");
        
        H4 chartTitle = new H4(title);
        chartTitle.addClassName("chart-title");
        
        Div wrapper = new Div();
        wrapper.addClassName("chart-wrapper");
        
        chart.setStyle("width", "100%");
        chart.setStyle("height", "100%");
        chart.setStyle("min-height", "200px");
        
        wrapper.add(chart);
        container.add(chartTitle, wrapper);
        return container;
    }
    
    private GoogleChart createRevenueChart() {
        GoogleChart chart = new GoogleChart(GoogleChart.Type.LINE);
        
        List<Object> data = new ArrayList<>();
        data.add(Arrays.asList("Month", "Revenue", "Target"));
        data.add(Arrays.asList("Apr", 81000, 75000));
        data.add(Arrays.asList("May", 56000, 80000));
        data.add(Arrays.asList("Jun", 124592, 85000));
        
        chart.setData(data);
        
        Map<String, Object> options = new HashMap<>();
        options.put("backgroundColor", "transparent");
        options.put("legend", Map.of("position", "bottom"));
        options.put("colors", Arrays.asList("#2563eb", "#059669"));
        options.put("responsive", true);
        
        chart.setOptions(options);
        return chart;
    }
    
    private GoogleChart createActivityChart() {
        GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);
        
        List<Object> data = new ArrayList<>();
        data.add(Arrays.asList("Day", "Page Views", "Users"));
        data.add(Arrays.asList("Thu", 1400, 950));
        data.add(Arrays.asList("Fri", 1600, 1100));
        data.add(Arrays.asList("Sat", 900, 600));
        data.add(Arrays.asList("Sun", 800, 550));
        
        chart.setData(data);
        
        Map<String, Object> options = new HashMap<>();
        options.put("backgroundColor", "transparent");
        options.put("legend", Map.of("position", "bottom"));
        options.put("colors", Arrays.asList("#0891b2", "#7c3aed"));
        options.put("responsive", true);
        
        chart.setOptions(options);
        return chart;
    }
    
    private Div createQuickActionsSection() {
        Div section = new Div();
        section.addClassName("actions-section");
        
        H3 sectionTitle = new H3("Quick Actions");
        sectionTitle.addClassName("section-title");
        
        Div actionsGrid = new Div();
        actionsGrid.addClassName("actions-grid");
        
        actionsGrid.add(
            createActionCard("Generate Report", "Create analytics report", "file-text"),
            createActionCard("Export Data", "Download data", "download"),
            createActionCard("User Management", "Manage users", "users"),
            createActionCard("System Settings", "Configure settings", "settings")
        );
        
        section.add(sectionTitle, actionsGrid);
        return section;
    }
    
    private Div createActionCard(String title, String description, String iconName) {
        Div card = new Div();
        card.addClassName("action-card");
        
        Div iconDiv = new Div();
        iconDiv.addClassName("action-icon");
        
        try {
            var icon = TablerIcon.create(iconName);
            icon.setStyle("color", "white");
            icon.setStyle("font-size", "24px");
            iconDiv.add(icon);
        } catch (Exception e) {
            Div fallback = new Div("📄");
            fallback.setStyle("color", "white");
            fallback.setStyle("font-size", "24px");
            iconDiv.add(fallback);
        }
        
        Div content = new Div();
        content.addClassName("action-content");
        
        H4 titleElement = new H4(title);
        titleElement.addClassName("action-title");
        
        Div descElement = new Div(description);
        descElement.addClassName("action-description");
        
        content.add(titleElement, descElement);
        card.add(iconDiv, content);
        
        return card;
    }
    
    private Div createMetricCard(String label, String value, String trend, String direction) {
        Div card = new Div();
        card.addClassName("metric-card");
        
        Div labelDiv = new Div(label);
        labelDiv.addClassName("metric-label");
        
        Div valueDiv = new Div(value);
        valueDiv.addClassName("metric-value");
        
        Div trendDiv = new Div(trend);
        trendDiv.addClassName("metric-trend");
        
        if ("up".equals(direction)) {
            trendDiv.addClassName("trend-positive");
        } else if ("down".equals(direction)) {
            trendDiv.addClassName("trend-negative");
        } else {
            trendDiv.addClassName("trend-neutral");
        }
        
        card.add(labelDiv, valueDiv, trendDiv);
        return card;
    }
}