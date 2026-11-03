package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.DateField;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ComboBox;
import com.webforj.component.toast.Toast;
import com.webforj.data.binding.BindingContext;
import com.webforj.data.binding.annotation.BindingExclude;
import com.webforj.data.binding.annotation.UseTransformer;
import com.webforj.data.binding.annotation.UseValidator;
import com.webforj.data.concern.ValueChangeModeAware.ValueChangeMode;
import com.webforj.data.transformation.transformer.Transformer;
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/** Verification of the snippets used in the java-form-validation blog post. */
public class VerifyBlogJavaFormValidationSnippets extends Composite<Div> {

  public VerifyBlogJavaFormValidationSnippets() {
    getBoundComponent().add(new SignupForm());
  }

  // Snippet 1
  public static class Signup {
    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 60, message = "Use between 3 and 60 characters")
    private String fullName;

    @NotBlank(message = "Work email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Choose a plan")
    private String plan;

    @NotNull(message = "Seat count is required")
    @Min(value = 1, message = "At least one seat is needed")
    @Max(value = 50, message = "Contact sales for more than 50 seats")
    private Integer seats;

    private String promoCode;

    private LocalDate startDate;

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPlan() {
      return plan;
    }

    public void setPlan(String plan) {
      this.plan = plan;
    }

    public Integer getSeats() {
      return seats;
    }

    public void setSeats(Integer seats) {
      this.seats = seats;
    }

    public String getPromoCode() {
      return promoCode;
    }

    public void setPromoCode(String promoCode) {
      this.promoCode = promoCode;
    }

    public LocalDate getStartDate() {
      return startDate;
    }

    public void setStartDate(LocalDate startDate) {
      this.startDate = startDate;
    }
  }

  // Snippet 2
  public static class PromoCodeValidator implements Validator<String> {
    private static final Set<String> ACTIVE_CODES = Set.of("SPRING25", "TEAMUP");

    @Override
    public ValidationResult validate(String value) {
      if (value == null || value.isBlank()) {
        return ValidationResult.valid();
      }

      return ACTIVE_CODES.contains(value.trim().toUpperCase())
          ? ValidationResult.valid()
          : ValidationResult.invalid("This promo code is not active");
    }
  }

  public static class SeatsTransformer implements Transformer<Double, Integer> {
    @Override
    public Integer transformToModel(Double componentValue) {
      return componentValue == null ? null : (int) Math.round(componentValue);
    }

    @Override
    public Double transformToComponent(Integer modelValue) {
      return modelValue == null ? null : modelValue.doubleValue();
    }
  }

  // Snippet 3 and 4
  public static class SignupForm extends Composite<Div> {
    TextField fullName = new TextField("Full name");
    TextField email = new TextField("Work email");
    ComboBox plan = new ComboBox("Plan");

    @UseTransformer(SeatsTransformer.class)
    NumberField seats = new NumberField("Seats");

    @UseValidator(PromoCodeValidator.class)
    TextField promoCode = new TextField("Promo code");

    @BindingExclude DateField startDate = new DateField("Start date");

    Button save = new Button("Create workspace", ButtonTheme.PRIMARY);
    Button reset = new Button("Reset");

    private final BindingContext<Signup> context;
    private Signup signup = new Signup();

    public SignupForm() {
      plan.add("Starter");
      plan.add("Team");
      plan.add("Business");

      context = BindingContext.of(this, Signup.class, true);
      context.setAutoFocusFirstViolation(true);
      email.setValueChangeMode(ValueChangeMode.ON_BLUR);

      context
          .bind(startDate, "startDate")
          .useValidator(
              date -> date == null || !date.isBefore(LocalDate.now()),
              "Start date can't be in the past")
          .useDefaultReporter(messages -> String.join(" ", messages))
          .add();

      context.onValidate(e -> save.setEnabled(e.isValid()));
      context.read(signup);

      save.onClick(
          e -> {
            ValidationResult result = context.write(signup);
            if (result.isValid()) {
              Toast.show("Workspace created for " + signup.getEmail());
            }
          });

      reset.onClick(
          e -> {
            signup = new Signup();
            context.read(signup);
          });

      getBoundComponent().add(fullName, email, plan, seats, promoCode, startDate, save, reset);
    }
  }
}
