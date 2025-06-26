# Trees & Hierarchical Data

## Q: How do I create a tree component with expandable nodes?

**A:** Use `Tree` with `TreeNode` for hierarchical data display:

```java
import com.webforj.component.tree.Tree;
import com.webforj.component.tree.TreeNode;
import static com.webforj.component.tree.Tree.node;

public class FileExplorerView extends Composite<FlexLayout> {
    private Tree fileTree;
    
    public FileExplorerView() {
        fileTree = new Tree();
        buildFileTree();
        setupEventHandlers();
        
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .add(fileTree);
    }
    
    private void buildFileTree() {
        // Create root level folders
        TreeNode documentsFolder = node("Documents").add(
            node("Contracts").add(
                node("contract_2024.pdf"),
                node("nda_template.docx")
            ),
            node("Reports").add(
                node("quarterly_report.xlsx"),
                node("financial_summary.pdf")
            ),
            node("readme.txt")
        );
        
        TreeNode projectsFolder = node("Projects").add(
            node("WebApp").add(
                node("src").add(
                    node("main").add(
                        node("java"),
                        node("resources")
                    ),
                    node("test")
                ),
                node("pom.xml"),
                node("README.md")
            ),
            node("MobileApp").add(
                node("android"),
                node("ios"),
                node("shared")
            )
        );
        
        TreeNode downloadsFolder = node("Downloads").add(
            node("installer.exe"),
            node("backup.zip"),
            node("photos").add(
                node("vacation_2024.jpg"),
                node("family_portrait.png")
            )
        );
        
        // Add to tree
        fileTree.add(documentsFolder, projectsFolder, downloadsFolder);
        
        // Expand some folders by default
        fileTree.expand(documentsFolder);
        fileTree.expand(projectsFolder);
    }
    
    private void setupEventHandlers() {
        // Handle node selection
        fileTree.onSelect(event -> {
            TreeNode selectedNode = event.getNode();
            String fileName = selectedNode.getText();
            
            if (selectedNode.isLeaf()) {
                Toast.show("Selected file: " + fileName, Theme.INFO);
                openFile(fileName);
            } else {
                Toast.show("Selected folder: " + fileName, Theme.PRIMARY);
            }
        });
        
        // Handle node expansion for lazy loading
        fileTree.onExpand(event -> {
            TreeNode expandedNode = event.getNode();
            loadChildrenIfNeeded(expandedNode);
        });
    }
    
    private void loadChildrenIfNeeded(TreeNode node) {
        // Example of lazy loading
        if (node.getText().equals("Large Folder") && node.getChildren().isEmpty()) {
            // Simulate loading delay
            Timer.schedule(500, () -> {
                for (int i = 1; i <= 100; i++) {
                    node.add(node("Item " + i));
                }
                fileTree.refresh(); // Refresh the tree display
            });
        }
    }
}
```

## Q: How do I handle tree selection modes and get selected nodes?

**A:** Configure selection modes and use selection events:

```java
public class TreeSelectionExample extends Composite<FlexLayout> {
    private Tree categoryTree;
    private RadioButton singleSelectToggle;
    private RadioButton multiSelectToggle;
    private Button showSelectedButton;
    
    public TreeSelectionExample() {
        setupComponents();
        buildCategoryTree();
        setupEventHandlers();
        setupLayout();
    }
    
    private void setupComponents() {
        categoryTree = new Tree();
        
        RadioButtonGroup selectionModeGroup = new RadioButtonGroup();
        singleSelectToggle = new RadioButton("Single Selection", selectionModeGroup);
        multiSelectToggle = new RadioButton("Multi Selection", selectionModeGroup);
        showSelectedButton = new Button("Show Selected Items");
        
        // Default to single selection
        singleSelectToggle.setChecked(true);
        categoryTree.setSelectionMode(Tree.SelectionMode.SINGLE);
    }
    
    private void buildCategoryTree() {
        TreeNode categories = node("Categories").add(
            node("Electronics").add(
                node("Smartphones"),
                node("Laptops"),
                node("Tablets")
            ),
            node("Clothing").add(
                node("Men").add(
                    node("Shirts"),
                    node("Pants"),
                    node("Shoes")
                ),
                node("Women").add(
                    node("Dresses"),
                    node("Blouses"),
                    node("Accessories")
                )
            ),
            node("Books").add(
                node("Fiction"),
                node("Non-Fiction"),
                node("Technical")
            )
        );
        
        categoryTree.add(categories);
        categoryTree.expandFrom(categories); // Expand all descendants
        
        // Pre-select some items for demonstration
        TreeNode electronics = categories.getChildren().get(0);
        categoryTree.selectChildren(electronics);
    }
    
    private void setupEventHandlers() {
        // Selection mode toggle
        singleSelectToggle.addToggleListener(e -> {
            if (e.isToggled()) {
                categoryTree.setSelectionMode(Tree.SelectionMode.SINGLE);
                Toast.show("Switched to single selection", Theme.INFO);
            }
        });
        
        multiSelectToggle.addToggleListener(e -> {
            if (e.isToggled()) {
                categoryTree.setSelectionMode(Tree.SelectionMode.MULTIPLE);
                Toast.show("Switched to multi selection", Theme.INFO);
            }
        });
        
        // Selection events
        categoryTree.onSelect(event -> {
            TreeNode node = event.getNode();
            int selectedCount = categoryTree.getSelectedItems().size();
            
            String message = String.format("Selected: %s (%d total)", 
                node.getText(), selectedCount);
            Toast.show(message, Theme.SUCCESS);
        });
        
        // Show selected button
        showSelectedButton.onClick(e -> showSelectedItems());
    }
    
    private void showSelectedItems() {
        List<TreeNode> selectedNodes = categoryTree.getSelectedItems();
        
        if (selectedNodes.isEmpty()) {
            OptionDialog.showMessageDialog(
                "No items selected",
                "Selection",
                "OK",
                MessageDialog.MessageType.INFO
            );
            return;
        }
        
        String selectedText = selectedNodes.stream()
            .map(TreeNode::getText)
            .collect(Collectors.joining("\n• ", "Selected items:\n• ", ""));
            
        OptionDialog.showMessageDialog(
            selectedText,
            "Selected Items (" + selectedNodes.size() + ")",
            "OK",
            MessageDialog.MessageType.INFO
        );
    }
    
    // Programmatic selection methods
    public void selectByKey(Object key) {
        categoryTree.selectKey(key);
    }
    
    public void selectByNode(TreeNode node) {
        categoryTree.select(node);
    }
    
    public void selectAllChildren(TreeNode parent) {
        categoryTree.selectChildren(parent);
    }
    
    public void deselectAll() {
        categoryTree.deselectAll();
    }
    
    // Query selection state
    public boolean hasSelection() {
        return !categoryTree.getSelectedItems().isEmpty();
    }
    
    public List<String> getSelectedKeys() {
        return categoryTree.getSelectedKeys().stream()
            .map(Object::toString)
            .collect(Collectors.toList());
    }
}
```

## Q: How do I customize tree icons and implement lazy loading?

**A:** Set global and per-node icons, and use expand events for lazy loading:

```java
import com.webforj.component.icons.TablerIcon;

public class CustomTreeView extends Composite<FlexLayout> {
    private Tree organizationTree;
    
    public CustomTreeView() {
        organizationTree = new Tree();
        setupTreeIcons();
        buildOrganizationTree();
        setupLazyLoading();
        
        getBoundComponent()
            .setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-l)")
            .add(organizationTree);
    }
    
    private void setupTreeIcons() {
        // Set global default icons
        organizationTree.setCollapsedIcon(TablerIcon.create("folder"));
        organizationTree.setExpandedIcon(TablerIcon.create("folder-open"));
        organizationTree.setLeafIcon(TablerIcon.create("user"));
        organizationTree.setLeafSelectedIcon(TablerIcon.create("user-check"));
        
        // Optional: Hide group icons for cleaner look
        // organizationTree.setGroupIconsVisible(false);
    }
    
    private void buildOrganizationTree() {
        // CEO level
        TreeNode ceo = node("John CEO");
        ceo.setIcon(TablerIcon.create("crown"));
        ceo.setSelectedIcon(TablerIcon.create("crown"));
        
        // Department heads
        TreeNode engineering = node("Engineering");
        engineering.setIcon(TablerIcon.create("code"));
        
        TreeNode marketing = node("Marketing");
        marketing.setIcon(TablerIcon.create("speakerphone"));
        
        TreeNode sales = node("Sales");
        sales.setIcon(TablerIcon.create("chart-line"));
        
        TreeNode hr = node("Human Resources");
        hr.setIcon(TablerIcon.create("users"));
        
        // Add placeholder nodes for lazy loading
        engineering.add(createLoadingNode());
        marketing.add(createLoadingNode());
        sales.add(createLoadingNode());
        hr.add(createLoadingNode());
        
        ceo.add(engineering, marketing, sales, hr);
        organizationTree.add(ceo);
        
        // Expand CEO by default
        organizationTree.expand(ceo);
    }
    
    private TreeNode createLoadingNode() {
        TreeNode loading = node("Loading...");
        loading.setIcon(TablerIcon.create("loader"));
        return loading;
    }
    
    private void setupLazyLoading() {
        organizationTree.onExpand(event -> {
            TreeNode expandedNode = event.getNode();
            
            // Check if this node needs lazy loading
            if (hasLoadingPlaceholder(expandedNode)) {
                loadEmployees(expandedNode);
            }
        });
    }
    
    private boolean hasLoadingPlaceholder(TreeNode node) {
        return !node.getChildren().isEmpty() && 
               node.getChildren().get(0).getText().equals("Loading...");
    }
    
    private void loadEmployees(TreeNode department) {
        String deptName = department.getText();
        
        // Simulate API call delay
        Timer.schedule(1000, () -> {
            // Remove loading placeholder
            department.getChildren().clear();
            
            // Add actual employees based on department
            switch (deptName) {
                case "Engineering":
                    addEngineeringTeam(department);
                    break;
                case "Marketing":
                    addMarketingTeam(department);
                    break;
                case "Sales":
                    addSalesTeam(department);
                    break;
                case "Human Resources":
                    addHRTeam(department);
                    break;
            }
            
            // Refresh the tree to show new nodes
            organizationTree.refresh();
            
            Toast.show("Loaded " + deptName + " team members", Theme.SUCCESS);
        });
    }
    
    private void addEngineeringTeam(TreeNode engineering) {
        // Team leads with their teams
        TreeNode frontendLead = node("Alice Frontend Lead");
        frontendLead.setIcon(TablerIcon.create("device-desktop"));
        frontendLead.add(
            node("Bob React Developer"),
            node("Carol Vue Developer"),
            node("Dave UI/UX Designer")
        );
        
        TreeNode backendLead = node("Eve Backend Lead");
        backendLead.setIcon(TablerIcon.create("server"));
        backendLead.add(
            node("Frank Java Developer"),
            node("Grace Python Developer"),
            node("Henry DevOps Engineer")
        );
        
        TreeNode mobileLead = node("Ivy Mobile Lead");
        mobileLead.setIcon(TablerIcon.create("device-mobile"));
        mobileLead.add(
            node("Jack iOS Developer"),
            node("Kate Android Developer"),
            node("Liam React Native Developer")
        );
        
        engineering.add(frontendLead, backendLead, mobileLead);
    }
    
    private void addMarketingTeam(TreeNode marketing) {
        marketing.add(
            node("Maria Content Manager"),
            node("Nick Social Media Specialist"),
            node("Olivia SEO Analyst"),
            node("Paul Graphic Designer")
        );
    }
    
    private void addSalesTeam(TreeNode sales) {
        TreeNode enterpriseSales = node("Enterprise Sales");
        enterpriseSales.add(
            node("Quinn Account Executive"),
            node("Rachel Sales Engineer")
        );
        
        TreeNode retailSales = node("Retail Sales");
        retailSales.add(
            node("Sam Sales Representative"),
            node("Tina Customer Success")
        );
        
        sales.add(enterpriseSales, retailSales);
    }
    
    private void addHRTeam(TreeNode hr) {
        hr.add(
            node("Uma HR Manager"),
            node("Victor Recruiter"),
            node("Wendy Benefits Coordinator")
        );
    }
}
```

---

## Navigation

- [← Previous: Tables](05-tables.md)
- [Next: Layout Systems →](../layout/01-layout-systems.md)
- [Back to Cookbook Index](../00-index.md)