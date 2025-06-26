# Tables & Data Grids

## Q: How do I create and configure data tables with proper column setup?

**A:** Use `Table` with column definitions and data binding:

```java
import com.webforj.component.table.Table;

public class CustomerTable extends Composite<FlexLayout> {
    private Table<Customer> table;
    
    private void setupTable() {
        table = new Table<>(Customer.class);
        
        // Define columns with specific properties
        table.addColumn("firstName", "First Name")
            .setSortable(true);
            
        table.addColumn("lastName", "Last Name")
            .setSortable(true);
            
        table.addColumn("email", "Email");
        
        // Load data
        table.setItems(customerService.getAllCustomers());
        
        getBoundComponent().add(table);
    }
}
```

## Q: How do I handle table selection and row interactions?

**A:** Configure selection mode and handle selection events:

```java
private void setupTableSelection() {
    // Enable single selection
    table.setSelectionMode(Table.SelectionMode.SINGLE);
    
    // Handle selection changes
    table.onSelectionChange(event -> {
        List<Customer> selected = event.getSelectedItems();
        if (!selected.isEmpty()) {
            Customer customer = selected.get(0);
            showCustomerDetails(customer);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    });
    
    // Handle double-click for editing
    table.onItemDoubleClick(event -> {
        Customer customer = event.getItem();
        openEditDialog(customer);
    });
    
    // Multi-selection example
    table.setSelectionMode(Table.SelectionMode.MULTIPLE);
    table.onSelectionChange(event -> {
        List<Customer> selected = event.getSelectedItems();
        bulkActionButton.setEnabled(!selected.isEmpty());
        bulkActionButton.setText("Process " + selected.size() + " customers");
    });
}
```

## Q: How do I implement table pagination with repository support?

**A:** Use `CollectionRepository` for efficient data management:

```java
import com.webforj.data.repository.CollectionRepository;

private void setupPaginatedTable() {
    // Create repository with pagination
    CollectionRepository<Customer> repository = new CollectionRepository<>(allCustomers);
    repository.setPageSize(25);
    
    // Bind table to repository
    table.setRepository(repository);
    
    // Add pagination controls
    FlexLayout paginationControls = FlexLayout.create()
        .horizontal()
        .justify().spaceBetween()
        .spacing("var(--dwc-space-s)")
        .build();
    
    Button prevButton = new Button("Previous");
    Button nextButton = new Button("Next");
    Span pageInfo = new Span();
    
    prevButton.onClick(e -> {
        if (repository.getPage() > 0) {
            repository.setPage(repository.getPage() - 1);
            updatePaginationControls();
        }
    });
    
    nextButton.onClick(e -> {
        if (repository.getPage() < repository.getTotalPages() - 1) {
            repository.setPage(repository.getPage() + 1);
            updatePaginationControls();
        }
    });
    
    paginationControls.add(prevButton, pageInfo, nextButton);
    getBoundComponent().add(table, paginationControls);
    
    updatePaginationControls();
}

private void updatePaginationControls() {
    int currentPage = repository.getPage() + 1;
    int totalPages = repository.getTotalPages();
    pageInfo.setText("Page " + currentPage + " of " + totalPages);
    
    prevButton.setEnabled(repository.getPage() > 0);
    nextButton.setEnabled(repository.getPage() < totalPages - 1);
}
```

---

## Navigation

- [← Previous: Dialogs & Feedback](04-dialogs-feedback.md)
- [Next: Trees →](06-trees.md)
- [Back to Cookbook Index](../00-index.md)