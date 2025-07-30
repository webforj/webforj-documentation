package com.webforj.samples.views.stylesheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.webforj.annotation.InlineStyleSheet;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.H4;
import com.webforj.component.icons.TablerIcon;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/stylesheet/dashboard.css")
@InlineStyleSheet("""
    .metric-value {
        font-size: 2.5rem;
        font-weight: 700;
        color: var(--dwc-color-primary-40);
    }
    
    @media (max-width: 768px) {
        .dashboard-header {
            display: flex !important;
            flex-direction: row !important;
            align-items: center !important;
            justify-content: space-between !important;
            padding: var(--dwc-space-s) var(--dwc-space-m) !important;
        }
        
        .dashboard-header h1 {
            margin: 0 !important;
            text-align: left !important;
            flex: 1;
        }
        
        .user-area {
            margin-left: auto !important;
            flex-shrink: 0;
        }
    }
""")
@Route
public class ModernDashboardView extends Composite<Div> {
    
    public ModernDashboardView() {
        Div container = getBoundComponent();
        container.addClassName("modern-dashboard");
       
        Div header = createHeader();
        
        Div sidebar = createSidebar();
        
        Div main = createMainContent();
        
        container.add(header, sidebar, main);
    }
    
    private Div createHeader() {
        Div header = new Div();
        header.addClassName("dashboard-header");
        
        H1 title = new H1("Analytics Dashboard");
        
        Div userArea = new Div("Welcome back, Joe");
        userArea.addClassName("user-area");
        
        header.add(title, userArea);
        return header;
    }
    
    private Div createSidebar() {
        Div sidebar = new Div();
        sidebar.addClassName("dashboard-sidebar");
        
        H3 navTitle = new H3("Dashboard");
        navTitle.addClassName("nav-title");
        
        Div navList = new Div();
        navList.addClassName("nav-list");
        
        navList.add(
            createNavItem("Overview", "dashboard", true),
            createNavItem("Analytics", "chart-line", false),
            createNavItem("Users", "users", false),
            createNavItem("Settings", "settings", false)
        );
        
        sidebar.add(navTitle, navList);
        return sidebar;
    }
    
    private Div createNavItem(String text, String iconName, boolean current) {
        Div item = new Div();
        item.addClassName("nav-item");
        if (current) {
            item.addClassName("nav-current");
        }
        
        try {
            var icon = TablerIcon.create(iconName);
            icon.addClassName("nav-icon");
            item.add(icon);
        } catch (Exception e) {
            Div fallback = new Div("•");
            fallback.addClassName("nav-icon");
            item.add(fallback);
        }
        
        Div label = new Div(text);
        label.addClassName("nav-label");
        item.add(label);
        
        return item;
    }
    
    private Div createMainContent() {
        Div main = new Div();
        main.addClassName("dashboard-main");
        
        H2 pageTitle = new H2("Overview");
        pageTitle.addClassName("page-title");
        
        Div metricsGrid = new Div();
        metricsGrid.addClassName("metrics-grid");
        
        metricsGrid.add(
            createMetricCard("Total Revenue", "$124,592", "+12.5%", "up"),
            createMetricCard("Active Users", "8,942", "+5.2%", "up"),
            createMetricCard("Conversion Rate", "3.2%", "-0.8%", "down"),
            createMetricCard("Server Load", "87%", "+15.3%", "neutral")
        );
        
        Div chartsSection = createChartsSection();
        
        Div actionsSection = createQuickActionsSection();
        
        main.add(pageTitle, metricsGrid, chartsSection, actionsSection);
        return main;
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
        data.add(Arrays.asList("Jan", 65000, 60000));
        data.add(Arrays.asList("Feb", 59000, 65000));
        data.add(Arrays.asList("Mar", 80000, 70000));
        data.add(Arrays.asList("Apr", 81000, 75000));
        data.add(Arrays.asList("May", 56000, 80000));
        data.add(Arrays.asList("Jun", 124592, 85000));
        
        chart.setData(data);
        
        Map<String, Object> options = new HashMap<>();
        options.put("backgroundColor", "transparent");
        options.put("legend", Map.of(
            "position", "bottom",
            "textStyle", Map.of("fontSize", 12)
        ));
        options.put("colors", Arrays.asList("#2563eb", "#059669"));
        options.put("chartArea", Map.of(
            "width", "80%", 
            "height", "65%",
            "top", "10%",
            "left", "15%"
        ));
        options.put("hAxis", Map.of(
            "textStyle", Map.of("fontSize", 10),
            "titleTextStyle", Map.of("fontSize", 11),
            "showTextEvery", 1
        ));
        options.put("vAxis", Map.of(
            "textStyle", Map.of("fontSize", 10),
            "titleTextStyle", Map.of("fontSize", 11)
        ));
        options.put("responsive", true);
        options.put("maintainAspectRatio", false);
        
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
            createActionCard("Generate Report", "Create comprehensive analytics report", "file-text"),
            createActionCard("Export Data", "Download data in various formats", "download"),
            createActionCard("User Management", "Manage users and permissions", "users"),
            createActionCard("System Settings", "Configure system preferences", "settings"),
            createActionCard("Data Import", "Import data from external sources", "upload"),
            createActionCard("API Documentation", "View API reference and guides", "book")
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
    
    private GoogleChart createActivityChart() {
        GoogleChart chart = new GoogleChart(GoogleChart.Type.COLUMN);
        
        List<Object> data = new ArrayList<>();
        data.add(Arrays.asList("Day", "Page Views", "Users"));
        data.add(Arrays.asList("Mon", 1200, 800));
        data.add(Arrays.asList("Tue", 1100, 750));
        data.add(Arrays.asList("Wed", 1300, 900));
        data.add(Arrays.asList("Thu", 1400, 950));
        data.add(Arrays.asList("Fri", 1600, 1100));
        data.add(Arrays.asList("Sat", 900, 600));
        data.add(Arrays.asList("Sun", 800, 550));
        
        chart.setData(data);
        
        Map<String, Object> options = new HashMap<>();
        options.put("backgroundColor", "transparent");
        options.put("legend", Map.of(
            "position", "bottom",
            "textStyle", Map.of("fontSize", 12)
        ));
        options.put("colors", Arrays.asList("#0891b2", "#7c3aed"));
        options.put("chartArea", Map.of(
            "width", "80%", 
            "height", "65%",
            "top", "10%",
            "left", "15%"
        ));
        options.put("hAxis", Map.of(
            "textStyle", Map.of("fontSize", 10),
            "titleTextStyle", Map.of("fontSize", 11)
        ));
        options.put("vAxis", Map.of(
            "textStyle", Map.of("fontSize", 10),
            "titleTextStyle", Map.of("fontSize", 11)
        ));
        options.put("responsive", true);
        options.put("maintainAspectRatio", false);
        
        chart.setOptions(options);
        return chart;
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