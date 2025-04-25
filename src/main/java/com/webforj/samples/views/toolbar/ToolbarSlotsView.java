package com.webforj.samples.views.toolbar;

import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.component.html.elements.H1;
import com.webforj.component.html.elements.Img;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.IconExpanse;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.toolbar.Toolbar;
import com.webforj.component.list.ChoiceBox;

@Route
@FrameTitle("Toolbar Slots")
public class ToolbarSlotsView extends Composite<FlexLayout> {

  FlexLayout mainLayout = getBoundComponent();
  Toolbar menuToolbar = new Toolbar().setTheme(Theme.PRIMARY);
  IconButton gridMenuIcon = new IconButton(TablerIcon.create("grid-dots"));
  ChoiceBox aboutMenu = new ChoiceBox();
  ChoiceBox productMenu = new ChoiceBox();
  IconButton homeMenuIcon = new IconButton(TablerIcon.create("home"));
  Toolbar basicToolbar = new Toolbar();
  Img logo = new Img("https://docs.webforj.com/img/webforj_icon.svg");
  TextField basicTextField = new TextField(TextField.Type.SEARCH);
  Button loginButton = new Button("Log In", ButtonTheme.OUTLINED_PRIMARY);
  Button signUpButton = new Button("Sign Up", ButtonTheme.PRIMARY);
  Toolbar foodToolbar = new Toolbar().setTheme(Theme.DANGER);
  IconButton historyFoodIcon = new IconButton(TablerIcon.create("invoice"));
  IconButton pointsFoodIcon = new IconButton(TablerIcon.create("star"));
  IconButton orderFoodIcon = new IconButton(TablerIcon.create("tools-kitchen-3"));
  IconButton locationFoodIcon = new IconButton(TablerIcon.create("map-2"));
  IconButton profileFoodIcon = new IconButton(TablerIcon.create("user-circle"));
  FlexLayout foodIconWrapper = new FlexLayout(historyFoodIcon, pointsFoodIcon, orderFoodIcon, locationFoodIcon,
      profileFoodIcon);

  public ToolbarSlotsView() {

    mainLayout.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-xl)")
        .setMargin("var(--dwc-space-xl) 0px");

    gridMenuIcon.setTooltipText("Grid View");
    homeMenuIcon.setTooltipText("Home");

    String[] aboutCategories = { "About Us", "Events", "FAQs", "Careers" };
    String[] productCategories = { "Products", "Best Sellers", "New Arrivals" };

    aboutMenu.insert(aboutCategories)
        .selectIndex(0)
        .setWidth("50%")
        .setMaxWidth("150px");
    productMenu.insert(productCategories)
        .selectIndex(0)
        .setWidth("50%")
        .setMaxWidth("150px");

    menuToolbar.addToStart(homeMenuIcon)
        .addToContent(aboutMenu, productMenu)
        .addToEnd(gridMenuIcon);
    
    basicTextField.setWidth("45vw")
        .setMaxWidth("100%");

    logo.setSize("var(--dwc-size-2xs)", "var(--dwc-size-2xs)");

    basicToolbar.addToStart(logo)
        .addToTitle(new H1("webforJ"))
        .addToContent(basicTextField)
        .addToEnd(loginButton, signUpButton)
        .setStyle("border", "1px solid var(--dwc-color-default)");

    orderFoodIcon.setExpanse(IconExpanse.LARGE)
        .setTooltipText("Order Food");
    historyFoodIcon.setTooltipText("Previous Orders");
    pointsFoodIcon.setTooltipText("Reward Points");
    locationFoodIcon.setTooltipText("Order Location");
    profileFoodIcon.setTooltipText("Profile");

    foodIconWrapper.setJustifyContent(FlexJustifyContent.EVENLY)
        .setMargin("0px var(--dwc-space-xl)");

    foodToolbar.addToStart(new H1("webforJ"))
        .addToContent(foodIconWrapper);

    mainLayout.add(menuToolbar, basicToolbar, foodToolbar);
  }
}