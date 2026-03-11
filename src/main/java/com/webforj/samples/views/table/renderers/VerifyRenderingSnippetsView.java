package com.webforj.samples.views.table.renderers;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.AnchorRenderer;
import com.webforj.component.table.renderer.AvatarRenderer;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.BooleanRenderer;
import com.webforj.component.table.renderer.ButtonRenderer;
import com.webforj.component.table.renderer.Condition;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.CurrencyRenderer;
import com.webforj.component.table.renderer.ElementRenderer;
import com.webforj.component.table.renderer.EmailRenderer;
import com.webforj.component.table.renderer.IconButtonRenderer;
import com.webforj.component.table.renderer.IconRenderer;
import com.webforj.component.table.renderer.ImageRenderer;
import com.webforj.component.table.renderer.MaskedDateTimeRenderer;
import com.webforj.component.table.renderer.MaskedNumberRenderer;
import com.webforj.component.table.renderer.MaskedTextRenderer;
import com.webforj.component.table.renderer.NullRenderer;
import com.webforj.component.table.renderer.PercentageRenderer;
import com.webforj.component.table.renderer.PhoneRenderer;
import com.webforj.component.table.renderer.ProgressBarRenderer;
import com.webforj.component.table.renderer.Renderer;
import com.webforj.component.table.renderer.StatusDotRenderer;
import com.webforj.component.table.renderer.TextRenderer;
import com.webforj.component.table.renderer.TextRenderer.TextDecoration;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.Service;
import java.util.EnumSet;
import java.util.Locale;

/**
 * Verification file for all code snippets in docs/components/table/table_rendering.md.
 *
 * <p>Each private method corresponds to a section of the article. The file is not intended to
 * produce a meaningful UI — it exists solely to confirm that every snippet compiles and that the
 * APIs used are valid.
 */
@Route
@FrameTitle("Verify: Table Rendering Snippets")
public class VerifyRenderingSnippetsView extends Composite<Div> {

  private final Div self = getBoundComponent();

  public VerifyRenderingSnippetsView() {
    self.setHtml("""
        <h2 style="font-family: sans-serif; padding: 1rem">
          ✅ VerifyRenderingSnippets loaded — all snippets compiled and ran without errors.
        </h2>
        """);

    verifyIntroPattern();
    verifyCommonRenderers();
    verifyConditionAPI();
    verifyCustomRenderers();
    verifyClickEvents();
    verifyLazyRender();
    verifyBuiltInTextAndLabels();
    verifyBuiltInStatusAndIndicators();
    verifyBuiltInNumbersCurrencyDates();
    verifyBuiltInLinksAndMedia();
    verifyBuiltInPeopleAndAvatars();
    verifyBuiltInIconsAndActions();
  }

  // ─── Intro: setRenderer() pattern ────────────────────────────────────────

  private void verifyIntroPattern() {
    Table<MusicRecord> table = new Table<>();
    table.setHeight("300px");
    self.add(table);
    TextRenderer<MusicRecord> renderer = new TextRenderer<>();
    renderer.setTheme(Theme.PRIMARY);

    table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
    table.setRepository(Service.getMusicRecords());
  }

  // ─── Common renderers ────────────────────────────────────────────────────

  private void verifyCommonRenderers() {
    // TextRenderer
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      TextRenderer<MusicRecord> renderer = new TextRenderer<>();
      renderer.setTheme(Theme.PRIMARY);
      renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

      table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // BadgeRenderer
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
      renderer.setTheme(BadgeTheme.PRIMARY);

      table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // BooleanRenderer — default icons
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BooleanRenderer<Product> renderer = new BooleanRenderer<>();
      table.addColumn("inStock", Product::isInStock).setRenderer(renderer);
      table.setRepository(ProductService.getProducts());
    }

    // BooleanRenderer — custom icons (two-arg constructor as shown in article)
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BooleanRenderer<Product> custom = new BooleanRenderer<>(
          TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
          TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
      );
      table.addColumn("inStock", Product::isInStock).setRenderer(custom);
      table.setRepository(ProductService.getProducts());
    }

    // CurrencyRenderer
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("cost", MusicRecord::getCost)
           .setRenderer(new CurrencyRenderer<>(Locale.US));

      table.addColumn("retail", MusicRecord::getRetail)
           .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Condition API ───────────────────────────────────────────────────────

  private void verifyConditionAPI() {
    // Value equality
    Condition.equalTo("active");
    Condition.equalToIgnoreCase("active");
    Condition.in("active", "pending", "new");

    // Numeric comparisons
    Condition.greaterThan(100);
    Condition.lessThanOrEqual(0);
    Condition.between(10, 50);

    // Boolean / emptiness
    Condition.isTrue();
    Condition.isFalse();
    Condition.isEmpty();

    // String matching
    Condition.contains("error");
    Condition.containsIgnoreCase("warn");

    // Composition
    Condition.greaterThan(0).and(Condition.lessThan(100));
    Condition.isEmpty().or(Condition.equalTo("N/A"));
    Condition.isTrue().negate();

    // Cross-column check
    Condition.column("status").equalTo("active");

    // Raw JavaScript expression
    Condition.expression("cell.value % 2 === 0");

    // ConditionalRenderer wiring (matches InvoiceListView pattern)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("musicType", MusicRecord::getMusicType)
          .setRenderer(new ConditionalRenderer<MusicRecord>()
              .when("Rock", new BadgeRenderer<MusicRecord>(BadgeTheme.PRIMARY))
              .when("Jazz", new BadgeRenderer<MusicRecord>(BadgeTheme.SUCCESS))
              .when(Condition.in("Pop", "R&B"), new BadgeRenderer<MusicRecord>(BadgeTheme.WARNING))
              .otherwise(new BadgeRenderer<>(BadgeTheme.DEFAULT)));
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Custom renderers ────────────────────────────────────────────────────

  private void verifyCustomRenderers() {
    // Step 3: assign custom renderer to a column
    // Note: article uses MusicRecord::getRating; adapted to getCost (double) since MusicRecord
    // has no rating field.
    Table<MusicRecord> table1 = new Table<>();
    table1.setHeight("300px");
    self.add(table1);
    table1.addColumn("rating", MusicRecord::getCost)
         .setRenderer(new RatingRenderer());
    table1.setRepository(Service.getMusicRecords());

    // Accessing multiple columns example
    Table<MusicRecord> table2 = new Table<>();
    table2.setHeight("300px");
    self.add(table2);
    table2.addColumn("artist", MusicRecord::getArtist)
         .setRenderer(new ArtistAvatarRenderer());
    table2.setRepository(Service.getMusicRecords());
  }

  // ─── Click events ────────────────────────────────────────────────────────

  private void verifyClickEvents() {
    Table<MusicRecord> table = new Table<>();
    table.setHeight("300px");
    self.add(table);
    IconButtonRenderer<MusicRecord> deleteBtn = new IconButtonRenderer<>(
        TablerIcon.create("trash").setTheme(Theme.DANGER)
    );
    deleteBtn.addClickListener(e -> {
      // repository.delete(e.getItem()); table.refresh();
    });

    table.addColumn("delete", r -> "").setRenderer(deleteBtn);
    table.setRepository(Service.getMusicRecords());
  }

  // ─── Performance: lazy rendering ─────────────────────────────────────────

  private void verifyLazyRender() {
    // Note: article uses Order::getStatus; adapted to MusicRecord::getMusicType.
    Table<MusicRecord> table = new Table<>();
    table.setHeight("300px");
    self.add(table);
    table.addColumn("musicType", MusicRecord::getMusicType)
         .setRenderer(new BadgeRenderer<>())
         .setLazyRender(true);
    table.setRepository(Service.getMusicRecords());
  }

  // ─── Built-in reference: Text and labels ─────────────────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInTextAndLabels() {
    // TextRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      TextRenderer renderer = new TextRenderer<>();
      renderer.setTheme(Theme.PRIMARY);
      renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

      table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // BadgeRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BadgeRenderer renderer = new BadgeRenderer<>();
      renderer.setTheme(BadgeTheme.PRIMARY);

      table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // NullRenderer
    // Note: article uses MusicRecord::getNotes; adapted to getBinLocation (nullable string).
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("notes", MusicRecord::getBinLocation)
           .setRenderer(new NullRenderer<>("N/A"));
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Built-in reference: Status and indicators ───────────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInStatusAndIndicators() {
    // BooleanRenderer — default (raw type as shown in article)
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BooleanRenderer renderer = new BooleanRenderer<>();
      table.addColumn("inStock", Product::isInStock).setRenderer(renderer);
      table.setRepository(ProductService.getProducts());
    }

    // BooleanRenderer — custom icons (raw type as shown in article)
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      BooleanRenderer custom = new BooleanRenderer<>(
          TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
          TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
      );
      table.addColumn("inStock", Product::isInStock).setRenderer(custom);
      table.setRepository(ProductService.getProducts());
    }

    // StatusDotRenderer (raw type as shown in article)
    // Note: article uses Order::getStatus; adapted to Employee::getStatus.
    {
      Table<Employee> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      StatusDotRenderer renderer = new StatusDotRenderer<>();
      renderer.addMapping("Active",    Theme.SUCCESS);
      renderer.addMapping("Pending",   Theme.WARNING);
      renderer.addMapping("Cancelled", Theme.DANGER);

      table.addColumn("status", Employee::getStatus).setRenderer(renderer);
      table.setRepository(EmployeeService.getEmployees());
    }
  }

  // ─── Built-in reference: Numbers, currency, and dates ────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInNumbersCurrencyDates() {
    // CurrencyRenderer
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("cost", MusicRecord::getCost)
           .setRenderer(new CurrencyRenderer<>(Locale.US));

      table.addColumn("retail", MusicRecord::getRetail)
           .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
      table.setRepository(Service.getMusicRecords());
    }

    // PercentageRenderer (raw type as shown in article)
    // Note: article uses Task::getCompletion; adapted to Product::getDiscount (int).
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
      table.addColumn("discount", Product::getDiscount).setRenderer(renderer);
      table.setRepository(ProductService.getProducts());
    }

    // ProgressBarRenderer (raw type as shown in article)
    // Note: article uses Task::getProgress; adapted to Product::getDiscount (int).
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      ProgressBarRenderer renderer = new ProgressBarRenderer<>();
      renderer.setMax(100);
      renderer.setTheme(Theme.SUCCESS);
      renderer.setTextVisible(true);
      renderer.setText("<%= cell.value %>/100");

      table.addColumn("discount", Product::getDiscount).setRenderer(renderer);
      table.setRepository(ProductService.getProducts());
    }

    // MaskedTextRenderer
    // Note: article uses Employee::getSsn; Employee has no SSN — using a lambda stand-in.
    {
      Table<Employee> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("ssn", e -> "123456789")
           .setRenderer(new MaskedTextRenderer<>("###-##-####"));
      table.setRepository(EmployeeService.getEmployees());
    }

    // MaskedNumberRenderer
    {
      Table<Product> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("price", Product::getPrice)
           .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
      table.setRepository(ProductService.getProducts());
    }

    // MaskedDateTimeRenderer
    // Note: article uses MusicRecord::getReleaseDate; MusicRecord has no date field —
    // using a lambda that returns an ISO date string as a stand-in.
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("released", r -> "2024-01-15")
           .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Built-in reference: Links and media ─────────────────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInLinksAndMedia() {
    // EmailRenderer
    // Note: article uses Contact::getEmail; adapted to Employee::getEmail.
    {
      Table<Employee> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("email", Employee::getEmail)
           .setRenderer(new EmailRenderer<>());

      table.addColumn("email2", Employee::getEmail)
           .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
      table.setRepository(EmployeeService.getEmployees());
    }

    // PhoneRenderer
    // Note: article uses Contact::getPhone; adapted to Employee::getPhone.
    {
      Table<Employee> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      table.addColumn("phone", Employee::getPhone)
           .setRenderer(new PhoneRenderer<>());

      table.addColumn("phone2", Employee::getPhone)
           .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
      table.setRepository(EmployeeService.getEmployees());
    }

    // AnchorRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      AnchorRenderer renderer = new AnchorRenderer<>();
      renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
      renderer.setTarget("_blank");

      table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // ImageRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      ImageRenderer renderer = new ImageRenderer<>();
      renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
      renderer.setAlt("Cover");

      table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Built-in reference: People and avatars ──────────────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInPeopleAndAvatars() {
    // AvatarRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      AvatarRenderer renderer = new AvatarRenderer<>();
      renderer.setTheme(AvatarTheme.PRIMARY);
      renderer.setIcon(TablerIcon.create("user"));

      table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Built-in reference: Icons and actions ───────────────────────────────

  @SuppressWarnings({"rawtypes", "unchecked"})
  private void verifyBuiltInIconsAndActions() {
    // IconRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
      table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // IconButtonRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
      renderer.addClickListener(e -> { /* openEditor(e.getItem()) */ });

      table.addColumn("actions", r -> "").setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // ButtonRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      ButtonRenderer renderer = new ButtonRenderer<>("Edit");
      renderer.setTheme(ButtonTheme.PRIMARY);
      renderer.addClickListener(e -> { /* openEditor(e.getItem()) */ });

      table.addColumn("edit", r -> "Edit").setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }

    // ElementRenderer (raw type as shown in article)
    {
      Table<MusicRecord> table = new Table<>();
      table.setHeight("300px");
      self.add(table);
      ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
      table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
      table.setRepository(Service.getMusicRecords());
    }
  }

  // ─── Custom renderer inner classes ───────────────────────────────────────

  /**
   * Custom renderer from the "Creating a custom renderer" section.
   * Displays a star rating derived from a numeric cell value.
   */
  static class RatingRenderer extends Renderer<MusicRecord> {
    @Override
    public String build() {
      return /* html */"""
          <%
            const rating = cell.value;
            const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
            const full   = '★'.repeat(stars);
            const empty  = '☆'.repeat(5 - stars);
          %>
          <span><%= full %><%= empty %></span>
          <span style="color: var(--dwc-color-default-text-muted)">(<%= rating.toFixed(1) %>)</span>
          """;
    }
  }

  /**
   * Custom renderer from the "Accessing multiple columns" section.
   * Renders an avatar with initials and name pulled from a sibling column.
   */
  static class ArtistAvatarRenderer extends Renderer<MusicRecord> {
    @Override
    public String build() {
      return /* html */"""
          <%
            const name     = cell.row.getValue("artist");
            const initials = name
                ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
                : '?';
          %>
          <div style="display: flex; align-items: center; gap: 8px;">
            <div style="width: 28px; height: 28px; border-radius: 50%; background: var(--dwc-color-primary); color: white; display: flex; align-items: center; justify-content: center; font-size: 11px; font-weight: 600;"><%= initials %></div>
            <span><%= name %></span>
          </div>
          """;
    }
  }
}
