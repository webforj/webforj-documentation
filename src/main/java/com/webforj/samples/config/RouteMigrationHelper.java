package com.webforj.samples.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Helper utility to migrate hardcoded routes to use RouteConfig constants.
 * This tool can be run to automatically update View classes and Test pages.
 */
public class RouteMigrationHelper {

    private static final Map<String, String> ROUTE_MAPPING = new HashMap<>();

    static {
        // Alert routes
        ROUTE_MAPPING.put("\"alert\"", "RouteConfig.ALERT");
        ROUTE_MAPPING.put("\"alertthemes\"", "RouteConfig.ALERT_THEMES");
        ROUTE_MAPPING.put("\"alertexpanses\"", "RouteConfig.ALERT_EXPANSES");
        ROUTE_MAPPING.put("\"closablealert\"", "RouteConfig.CLOSABLE_ALERT");

        // Button routes
        ROUTE_MAPPING.put("\"buttondemo\"", "RouteConfig.BUTTON_DEMO");
        ROUTE_MAPPING.put("\"buttonthemes\"", "RouteConfig.BUTTON_THEMES");
        ROUTE_MAPPING.put("\"buttonicon\"", "RouteConfig.BUTTON_ICON");
        ROUTE_MAPPING.put("\"buttonexpanses\"", "RouteConfig.BUTTON_EXPANSES");
        ROUTE_MAPPING.put("\"buttonevent\"", "RouteConfig.BUTTON_EVENT");
        ROUTE_MAPPING.put("\"buttondisable\"", "RouteConfig.BUTTON_DISABLE");

        // Checkbox routes
        ROUTE_MAPPING.put("\"checkboxindeterminate\"", "RouteConfig.CHECKBOX_INDETERMINATE");
        ROUTE_MAPPING.put("\"checkboxexpanse\"", "RouteConfig.CHECKBOX_EXPANSE");
        ROUTE_MAPPING.put("\"checkboxhorizontaltext\"", "RouteConfig.CHECKBOX_HORIZONTAL_TEXT");

        // Dialog routes
        ROUTE_MAPPING.put("\"dialogdraggableview\"", "RouteConfig.DIALOG_DRAGGABLE");
        ROUTE_MAPPING.put("\"dialogpositioningview\"", "RouteConfig.DIALOG_POSITIONING");
        ROUTE_MAPPING.put("\"dialogsectionsview\"", "RouteConfig.DIALOG_SECTIONS");
        ROUTE_MAPPING.put("\"dialogthemesview\"", "RouteConfig.DIALOG_THEMES");
        ROUTE_MAPPING.put("\"dialogcloseview\"", "RouteConfig.DIALOG_CLOSE");
        ROUTE_MAPPING.put("\"dialogbackdropblurview\"", "RouteConfig.DIALOG_BACKDROP_BLUR");
        ROUTE_MAPPING.put("\"dialogalignmentsview\"", "RouteConfig.DIALOG_ALIGNMENTS");
        ROUTE_MAPPING.put("\"dialogautofocusview\"", "RouteConfig.DIALOG_AUTO_FOCUS");

        // Drawer routes
        ROUTE_MAPPING.put("\"drawerdemo\"", "RouteConfig.DRAWER_DEMO");
        ROUTE_MAPPING.put("\"drawerwelcome\"", "RouteConfig.DRAWER_WELCOME");
        ROUTE_MAPPING.put("\"drawerplacement\"", "RouteConfig.DRAWER_PLACEMENT");
        ROUTE_MAPPING.put("\"drawerautofocus\"", "RouteConfig.DRAWER_AUTO_FOCUS");

        // ColumnsLayout routes
        ROUTE_MAPPING.put("\"columnslayout\"", "RouteConfig.COLUMNS_LAYOUT");
        ROUTE_MAPPING.put("\"columnslayoutspancolumn\"", "RouteConfig.COLUMNS_LAYOUT_SPAN_COLUMN");
        ROUTE_MAPPING.put("\"columnslayoutbreakpoints\"", "RouteConfig.COLUMNS_LAYOUT_BREAKPOINTS");
        ROUTE_MAPPING.put("\"columnslayoutform\"", "RouteConfig.COLUMNS_LAYOUT_FORM");
        ROUTE_MAPPING.put("\"columnslayoutalignment\"", "RouteConfig.COLUMNS_LAYOUT_ALIGNMENT");

        // BusyIndicator routes
        ROUTE_MAPPING.put("\"busydemo\"", "RouteConfig.BUSY_DEMO");
        ROUTE_MAPPING.put("\"busyindicator\"", "RouteConfig.BUSY_INDICATOR");
        ROUTE_MAPPING.put("\"busyspinners\"", "RouteConfig.BUSY_SPINNERS");

        // Other routes
        ROUTE_MAPPING.put("\"appnav\"", "RouteConfig.APP_NAV");
        ROUTE_MAPPING.put("\"composite\"", "RouteConfig.COMPOSITE");
        ROUTE_MAPPING.put("\"/\"", "RouteConfig.HELLO_WORLD");
    }

    /**
     * Main method to run the migration
     */
    public static void main(String[] args) {
        System.out.println("Starting route migration...");

        // Migrate View classes
        migrateFiles("src/main/java/com/webforj/samples/views",
                    "View.java",
                    "@Route\\(",
                    true);

        // Migrate Test pages
        migrateFiles("src/test/java/pages",
                    "Page.java",
                    "ROUTE = ",
                    false);

        System.out.println("Migration completed!");
    }

    /**
     * Migrate files in a directory
     */
    private static void migrateFiles(String directory, String fileSuffix,
                                   String routePattern, boolean isViewClass) {
        try {
            Path dir = Paths.get(directory);
            if (!Files.exists(dir)) {
                System.out.println("Directory not found: " + directory);
                return;
            }

            try (Stream<Path> paths = Files.walk(dir)) {
                paths.filter(Files::isRegularFile)
                     .filter(path -> path.toString().endsWith(fileSuffix))
                     .forEach(path -> migrateFile(path, routePattern, isViewClass));
            }
        } catch (IOException e) {
            System.err.println("Error processing directory: " + e.getMessage());
        }
    }

    /**
     * Migrate a single file
     */
    private static void migrateFile(Path file, String routePattern, boolean isViewClass) {
        try {
            String content = Files.readString(file);
            boolean modified = false;

            boolean needsImport = false;

            for (Map.Entry<String, String> entry : ROUTE_MAPPING.entrySet()) {
                String hardcodedRoute = entry.getKey();
                String constantRef = entry.getValue();

                if (isViewClass) {
                    // For View classes: @Route("alert") -> @Route(RouteConfig.ALERT)
                    String oldPattern = "@Route\\(" + hardcodedRoute + "\\)";
                    String newPattern = "@Route(" + constantRef + ")";
                    if (content.contains("@Route(" + hardcodedRoute + ")")) {
                        content = content.replaceAll(oldPattern, newPattern);
                        modified = true;
                        needsImport = true;
                    }
                } else {
                    // For Test pages: ROUTE = "alert" -> ROUTE = RouteConfig.ALERT
                    String oldPattern = "ROUTE = " + hardcodedRoute;
                    String newPattern = "ROUTE = " + constantRef;
                    if (content.contains(oldPattern)) {
                        content = content.replace(oldPattern, newPattern);
                        modified = true;
                        needsImport = true;
                    }
                }
            }

            if (needsImport && !content.contains("import com.webforj.samples.config.RouteConfig;")) {
                int lastImportIndex = content.lastIndexOf("import ");
                if (lastImportIndex != -1) {
                    int endOfLine = content.indexOf(";", lastImportIndex) + 1;
                    String importStatement = "\nimport com.webforj.samples.config.RouteConfig;";
                    content = content.substring(0, endOfLine) + importStatement +
                             content.substring(endOfLine);
                }
            }

            if (modified) {
                Files.writeString(file, content);
                System.out.println("Migrated: " + file.getFileName());
            }

        } catch (IOException e) {
            System.err.println("Error processing file " + file + ": " + e.getMessage());
        }
    }

    public static void generateRouteReport(String directory) {
        System.out.println("\n=== Route Report ===");
        Map<String, String> foundRoutes = new HashMap<>();

        try {
            Path dir = Paths.get(directory);
            try (Stream<Path> paths = Files.walk(dir)) {
                paths.filter(Files::isRegularFile)
                     .filter(path -> path.toString().endsWith(".java"))
                     .forEach(path -> {
                         try {
                             String content = Files.readString(path);
                             if (content.contains("@Route(")) {
                                 String fileName = path.getFileName().toString();
                                 int routeIndex = content.indexOf("@Route(");
                                 int start = content.indexOf("(", routeIndex) + 1;
                                 int end = content.indexOf(")", start);
                                 if (start > 0 && end > start) {
                                     String route = content.substring(start, end).trim();
                                     foundRoutes.put(fileName, route);
                                 }
                             }
                         } catch (IOException e) {
                             // Ignore
                         }
                     });
            }

            foundRoutes.forEach((file, route) -> {
                System.out.println(file + " -> " + route);
            });

        } catch (IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }
}