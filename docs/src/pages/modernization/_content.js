import { translate } from '@docusaurus/Translate';

// Facts and code for the Swing modernization page. Every snippet is copied
// from the Webswing integration docs or the published Webswing posts.

export const PHASE_CODE = {
  // docs/integrations/webswing/setup.md, "Basic implementation"
  embed: `@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialize the connector with your Webswing application URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Set the display dimensions
    connector.setSize("100%", "600px");

    // Add to the view container
    self.add(connector);
  }
}`,
  // docs/integrations/webswing/tutorial.md, "Handling events from Swing"
  hybrid: `connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});`,
};

// docs/integrations/webswing/tutorial.md, steps 1 to 3
export const SWING_ANNOUNCES = `private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}`;

// blog/2026-06-14-swing-web-communication, "Receive events from Swing"
export const WEB_RECEIVES = `connector.onAction(e -> {
  switch (e.getActionName()) {
    case "select-customer" -> e.getActionData().ifPresent(this::openEditor);
    case "export-ready" -> e.getActionBinaryData()
        .ifPresent(d -> status.setText("Got " + d.length() + " characters"));
    default -> { }
  }
});`;

// docs/integrations/webswing/tutorial.md, "Sending updates to Swing"
export const WEB_REPLIES = `dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});`;

// docs/integrations/webswing/tutorial.md, "Processing updates in Swing"
export const SWING_UPDATES = `private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}`;

// blog/2026-06-14-swing-web-communication, "Know when Swing is ready"
export const LIFECYCLE = `connector.onInitialize(e -> status.setText("Connected"));
connector.onStart(e -> status.setText("Swing app running"));`;

// docs/integrations/webswing/communication.md, "Sending actions to Swing"
export const PERFORM = `// Trigger a refresh in the Swing application from webforJ
connector.performAction("refresh");

// Send a command with data from webforJ
connector.performAction("selectRecord", "12345");`;

// docs/integrations/webswing/setup.md
export const START_SCRIPTS = [
  { os: 'Linux', command: 'webswing.sh' },
  { os: 'macOS', command: 'webswing.command' },
  { os: 'Windows', command: 'webswing.bat' },
];

export const DEPENDENCY = `<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>\${webforj.version}</version>
</dependency>`;

export const OPTIONS = `WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
  .setDisableLogout(true);  // Hide the logout button

connector.setOptions(options);`;

// Rows for the drawn desktop and web screens. Names and cities are sample
// data; only the team is a label and goes through translate(). Called while
// rendering, so translate() sees the page's language.
export function getPeople() {
  const sales = translate({ id: 'page.modernization.mock.teamSales', message: 'Sales' });
  const support = translate({ id: 'page.modernization.mock.teamSupport', message: 'Support' });
  const finance = translate({ id: 'page.modernization.mock.teamFinance', message: 'Finance' });
  return [
    { name: 'Hyyan Abo Fakher', team: sales, city: 'Berlin' },
    { name: 'Matthew Hawkins', team: support, city: 'Boston' },
    { name: 'Eric Handtke', team: finance, city: 'Hamburg' },
    { name: 'Ben Brennan', team: sales, city: 'Denver' },
    { name: 'Garrison Osteen', team: support, city: 'Austin' },
    { name: 'Lauren Alamo', team: finance, city: 'Phoenix' },
  ];
}
