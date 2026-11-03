package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.WillEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.WillEnterObserver;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Verification of the snippets in the jsf-alternative post. */
public class VerifyBlogJsfAlternativeSnippets extends Composite<Div> {

  public VerifyBlogJsfAlternativeSnippets() {
    // Snippets are compiled as nested classes.
  }

  // In the post this class is a Spring service (Spring is not a dependency here).
  public static class CustomerService {
    public void save(Customer customer) {}

    public Customer find(int id) {
      return new Customer();
    }
  }

  @Route("/customers")
  public static class CustomerListView extends Composite<Div> {}

  public static class Customer {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 40, message = "Name must be 3 to 40 characters")
    private String name;

    @Email(message = "Enter a valid email")
    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
  }

  @Route("/customers/new")
  @FrameTitle("New customer")
  public static class CustomerFormView extends Composite<FlexLayout> {
    private final FlexLayout self = getBoundComponent();
    private final TextField name = new TextField("Name");
    private final TextField email = new TextField("Email");
    private final Button save = new Button("Save", ButtonTheme.PRIMARY);
    private final BindingContext<Customer> context;
    private final Customer customer = new Customer();

    public CustomerFormView(CustomerService service) {
      self.setDirection(FlexDirection.COLUMN).setSpacing("1em").add(name, email, save);

      context = BindingContext.of(this, Customer.class, true);

      save.onClick(
          e -> {
            if (context.write(customer).isValid()) {
              service.save(customer);
              Router.getCurrent().navigate(CustomerListView.class);
            }
          });
    }

    void navigateSnippet() {
      Router.getCurrent().navigate(CustomerListView.class);
    }

    void validatorSnippet() {
      context.bind(email, "email").useValidator(new NoTestAccountValidator()).add();
    }
  }

  public static class NoTestAccountValidator implements Validator<String> {
    @Override
    public ValidationResult validate(String value) {
      if (value != null && value.endsWith("@example.test")) {
        return ValidationResult.invalid("Use a real email address");
      }
      return ValidationResult.valid();
    }
  }

  @Route("customer/:id<[0-9]+>")
  public static class CustomerEditView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService service;

    public CustomerEditView(CustomerService service) {
      this.service = service;
    }

    @Override
    public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
      int id = parameters.getInt("id").orElse(0);
      Customer customer = service.find(id);
      // read the customer into the bound fields
    }
  }
}
