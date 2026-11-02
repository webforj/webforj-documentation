package com.webforj.samples.unit.views.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.table.ColumnGroup;
import com.webforj.component.table.Table;
import com.webforj.samples.views.table.MusicRecord;
import com.webforj.samples.views.table.TableHiddenColumnGroupsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class TableHiddenColumnGroupsViewTest {
  @Test
  @SuppressWarnings("unchecked")
  void shouldShowAndHideTheRetailColumnInsideThePricingGroupOnToggle(WebforjTestbed context) {
    context.add(new TableHiddenColumnGroupsView());

    Table<MusicRecord> table = ComponentLocator.of(Table.class).locate();
    var retail = table.getColumnById("Retail");
    var pricing = table.getColumnGroups().get(1);
    var toggle = ComponentLocator.of(Button.class).byText("Toggle Retail Column").locate();

    assertTrue(retail.isHidden());

    UserInteraction.click(toggle);

    assertFalse(retail.isHidden());

    UserInteraction.click(toggle);

    assertTrue(retail.isHidden());
    assertEquals(
        List.of("Cost", "Retail"), pricing.getChildren().stream().map(ColumnGroup::getId).toList());
  }
}
