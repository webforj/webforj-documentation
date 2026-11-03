package com.webforj.samples.verify;

import com.webforj.Environment;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Verification of the snippets in the Swing to web migration post. */
public class VerifyBlogSwingToWebMigrationSnippets extends Composite<Div> {

  public VerifyBlogSwingToWebMigrationSnippets() {
    // The snippets below are compiled as nested classes.
  }

  // Domain layer shared with the Swing app.
  public static class Customer {
    public String getName() {
      return "";
    }

    public String getCompany() {
      return "";
    }

    public String getEmail() {
      return "";
    }
  }

  public static class CustomerService {
    public List<Customer> findAll() {
      return new ArrayList<>();
    }

    public void delete(Customer customer) {}

    public String exportReport() {
      return "";
    }
  }

  // Example 1 and 2: the screen with a table and a dialog
  @Route("/customers")
  public static class CustomerListView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final CustomerService service = new CustomerService();
    private final Table<Customer> table = new Table<>();
    private final Button delete = new Button("Delete").setTheme(ButtonTheme.DANGER);

    public CustomerListView() {
      self.setDirection(FlexDirection.COLUMN).setSpacing("1em").setMargin("1em");

      table.addColumn("Name", Customer::getName);
      table.addColumn("Company", Customer::getCompany);
      table.addColumn("Email", Customer::getEmail);
      table.setSelectionMode(Table.SelectionMode.SINGLE);
      table.setItems(service.findAll());

      delete.onClick(
          e -> {
            Customer selected =
                table.getSelectedItems().isEmpty() ? null : table.getSelectedItems().get(0);
            if (selected == null) {
              return;
            }

            ConfirmDialog.Result result =
                OptionDialog.showConfirmDialog(
                    "Delete " + selected.getName() + "?",
                    "Delete customer",
                    ConfirmDialog.OptionType.YES_NO,
                    ConfirmDialog.MessageType.QUESTION);

            if (result == ConfirmDialog.Result.YES) {
              service.delete(selected);
              table.setItems(service.findAll());
            }
          });

      self.add(table, delete);
    }
  }

  // Example 3: SwingWorker replacement
  @Route("/report")
  public static class ReportView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final CustomerService service = new CustomerService();
    private final Button run = new Button("Export report");
    private final ProgressBar progress = new ProgressBar();

    public ReportView() {
      progress.setVisible(false);
      run.onClick(
          e -> {
            run.setEnabled(false);
            progress.setVisible(true);

            CompletableFuture.supplyAsync(service::exportReport)
                .thenAccept(
                    path ->
                        Environment.runLater(
                            () -> {
                              progress.setVisible(false);
                              run.setEnabled(true);
                              OptionDialog.showMessageDialog("Saved to " + path, "Export finished");
                            }));
          });

      self.setDirection(FlexDirection.COLUMN).add(run, progress);
    }
  }

  // Example 4 (Webswing) is not compiled here because the webforj-webswing
  // module is not a dependency of this project:
  //
  // @Route("/legacy")
  // public class LegacyScreenView extends Composite<Div> {
  //   private final Div self = getBoundComponent();
  //   public LegacyScreenView() {
  //     WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");
  //     connector.setSize("100%", "600px");
  //     connector.onAction(event -> {
  //       if ("select-customer".equals(event.getActionName())) {
  //         event.getActionData().ifPresent(json -> { /* open a webforJ form */ });
  //       }
  //     });
  //     self.add(connector);
  //   }
  // }
}
