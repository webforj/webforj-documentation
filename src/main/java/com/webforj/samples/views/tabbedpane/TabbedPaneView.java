package com.webforj.samples.views.tabbedpane;

import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.alert.Alert;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.progressbar.ProgressBar;
import com.webforj.component.tabbedpane.Tab;
import com.webforj.component.tabbedpane.TabbedPane;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Employee Management System")
public class TabbedPaneView extends Composite<FlexLayout> {

 public TabbedPaneView() {
    getBoundComponent()
     .setDirection(FlexDirection.COLUMN)
        .setMargin("var(--dwc-size-m)");

    TabbedPane employeeManager = new TabbedPane();

    Icon detailsIcon = TablerIcon.create("id");
    FlexLayout detailsForm = new FlexLayout()
        .setDirection(FlexDirection.COLUMN)
        .setStyle("padding-top", "var(--dwc-size-xs)");

    ChoiceBox departmentChoice = new ChoiceBox("Department")
        .setStyle("margin-bottom", "1rem");
    departmentChoice.insert("Marketing", "Engineering", "Sales", "HR");
    departmentChoice.selectIndex(0);

    detailsForm.add(
      new TextField("Employee ID").setValue("EMP001")
        .setStyle("margin-bottom", "var(--dwc-size-xs)")
        .setStyle("margin-right", "var(--dwc-size-s)"),
      new TextField("Full Name").setValue("Sarah Johnson")
        .setStyle("margin-bottom", "var(--dwc-size-xs)")
        .setStyle("margin-right", "var(--dwc-size-s)"),
        departmentChoice,
      new Button("Update Details", ButtonTheme.PRIMARY)
    );
    employeeManager.addTab(new Tab("Details", detailsIcon), detailsForm);

    Icon payrollIcon = TablerIcon.create("cash");
    FlexLayout payrollSection = new FlexLayout()
        .setDirection(FlexDirection.COLUMN)
        .setStyle("padding-top", "var(--dwc-size-xs)");

    payrollSection.add(
      new Alert("Payroll processing scheduled for Friday")
        .setTheme(Theme.INFO)
        .setStyle("margin-bottom", "var(--dwc-size-s)"),
      new NumberField("Annual Salary").setValue(65000.0)
        .setStyle("margin-right", "var(--dwc-size-xs)"),
      new NumberField("Hours This Week").setValue(40.0)
        .setStyle("margin-right", "var(--dwc-size-xs)"),
      new Button("Process Payroll", ButtonTheme.SUCCESS)
    );
    employeeManager.addTab(new Tab("Payroll", payrollIcon), payrollSection);

    Icon performanceIcon = TablerIcon.create("chart-bar");
    FlexLayout performanceSection = new FlexLayout()
        .setDirection(FlexDirection.COLUMN)
        .setStyle("padding-top", "var(--dwc-size-m)")
        .setStyle("overflow", "visible");

    performanceSection.add(
      new ProgressBar("Q1 Goals Progress").setValue(85)
        .setStyle("margin-bottom", "var(--dwc-size-s)"),
      new ProgressBar("Training Completion").setValue(60)
        .setStyle("margin-bottom", "var(--dwc-size-s)"),
      new Button("View Full Report", ButtonTheme.DEFAULT)
    );
    employeeManager.addTab(new Tab("Performance", performanceIcon), performanceSection);

    getBoundComponent().add(
      new H3("Employee Management System"),
      new Paragraph("Select different tabs to manage employee information:"),
        employeeManager
    );
  }
}
