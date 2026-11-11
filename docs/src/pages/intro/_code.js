// Every snippet on the intro page is copied as is. The Java and Kotlin
// views, the form, the bean, the Spring view and the guarded route come from
// the stageforJ sample app. The theme, the web component and the Webswing
// view come from the docs pages named next to each one.

// stageforJ, JavaKotlinScene
export const HELLO_JAVA = `@Route("/hello")
public class HelloView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField name = new TextField("What is your name?");
  private Button greet = new Button("Say hello");

  public HelloView() {
    greet.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e ->
            Toast.show("Welcome, " + name.getValue() + "!",
                Theme.SUCCESS));
    self.add(name, greet);
  }
}`;

// stageforJ, JavaKotlinScene
export const HELLO_KOTLIN = `@Route("hello")
class HelloView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      val name = textField("What is your name?")
      button("Say hello") {
        theme = ButtonTheme.PRIMARY
        onClick {
          Toast.show("Welcome, \${name.text}!", Theme.SUCCESS)
        }
      }
    }
  }
}`;

// docs/styling/colors.md, Direct seed override
export const THEME_SEED = `:root {
  --dwc-color-primary-seed: #6366f1;
}`;

// stageforJ, BindingScene
export const FORM_VIEW = `public class CustomerFormView extends Composite<FlexLayout> {
  private TextField name = new TextField("Name");
  private TextField email = new TextField("Email");
  private Customer customer = new Customer();

  private BindingContext<Customer> context =
      BindingContext.of(this, Customer.class, true);

  public CustomerFormView() {
    Button save = new Button("Save", ButtonTheme.PRIMARY, e -> {
      ValidationResult result = context.write(customer);
      if (result.isValid()) {
        Toast.show("Saved " + customer.getEmail(), Theme.SUCCESS);
      }
    });

    getBoundComponent().add(name, email, save);
  }
}`;

// stageforJ, BindingScene
export const CUSTOMER_BEAN = `public class Customer {

  @NotEmpty(message = "Name cannot be empty")
  private String name;

  @NotEmpty(message = "Email is required")
  @Email(message = "Not a valid email address")
  private String email;

  // getters and setters
}`;

// stageforJ, SpringBootScene
export const SPRING_VIEW = `@Route("/dashboard")
public class DashboardView extends Composite<FlexLayout> {

  public DashboardView(RandomNumberService service) {
    Button roll = new Button("Generate random number");
    roll.setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e ->
            Toast.show("The new random number is "
                + service.nextNumber(), Theme.SUCCESS));

    getBoundComponent().add(roll);
  }
}`;

// stageforJ, SecurityScene
export const ADMIN_VIEW = `@Route("/admin")
@RolesAllowed("ADMIN")
public class AdminView extends Composite<Div> {

  public AdminView() {
    getBoundComponent().add(new H1("Admin console"));
  }
}`;

// docs/integrations/webswing/setup.md, Basic implementation
export const SWING_VIEW = `@Route
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
}`;

// docs/building-ui/element-composite.md, @JavaScript
export const WEB_COMPONENT = `@NodeName("wa-relative-time")
@JavaScript(
    "https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}`;

// docs/ai-tooling/overview.md, Installation
export const AI_INSTALL = `claude plugin marketplace add webforj/webforj-ai
claude plugin install webforj@webforj-ai`;
