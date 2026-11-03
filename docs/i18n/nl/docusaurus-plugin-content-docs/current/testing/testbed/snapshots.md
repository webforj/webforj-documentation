---
sidebar_position: 5
title: Momentopnamen
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
Een weergave stap voor stap controleren vereist één assertion voor elk label, elke knop en elk veld. Met een snapshot controleer je alles in één keer. Die legt alles vast wat een gebruiker in de weergave zou zien, met één regel per component, en bewaart die beschrijving bij de test. Bij elke volgende uitvoering wordt de weergave opnieuw beschreven en vergeleken. Elke wijziging in wat de pagina toont, laat de test dus mislukken. De wijziging is bovendien als een gewone tekstdiff te zien tijdens code review.

<!-- INTRO_END -->

## Een snapshot vergelijken {#matching-a-snapshot}

De volgende samenvatting toont een ordernummer, een notitieveld, een verborgen kortingsregel, een knop **Pay** met een pictogram en een uitgeschakelde knop **Cancel**:

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

De test voegt de samenvatting toe en controleert deze met `matchesSnapshot()`:

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

Bij de eerste uitvoering wordt de beschrijving naar een bestand geschreven en slaagt de test. Bij elke volgende uitvoering mislukt de test als de weergave niet meer overeenkomt met het opgeslagen bestand:

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` is een controle van `verifyThat`, net als de controles op de pagina [Assertions](./assertions). Je kunt deze dus op elke component gebruiken, niet alleen op een weergave.

:::tip Wanneer gebruik je snapshots?
Een snapshot legt een volledige weergave vast. Gebruik deze voor weergaven waarvan de inhoud niet hoort te veranderen. Controleer afzonderlijke feiten, zoals de tekst van een knop na een klik, met [assertions](./assertions).
:::

## Een snapshot lezen {#reading-a-snapshot}

Elke regel van de [opgeslagen snapshot](#matching-a-snapshot) beschrijft één component. De componenten die deze bevat, staan eronder, met twee spaties inspringing. Een regel die andere componenten bevat, eindigt op een dubbele punt. Een regel wordt als volgt opgebouwd:

1. Het type van de component, zoals `Button`.
2. De tekst tussen dubbele aanhalingstekens, als de component tekst toont, zoals `"Pay"`.
3. Attributen als `[key=value]`, zoals `[theme=primary]` of `[label=Note]`.
4. Vlaggen als `[key]`, zoals `[disabled]` of `[hidden]`.

Een regel toont alleen waarden die afwijken van de standaardwaarden van de component, zodat je de samenvatting in één oogopslag kunt lezen. **Cancel** heeft `[disabled]`, terwijl de ingeschakelde knop **Pay** geen status vermeldt. **Pay** heeft `[theme=primary]`; bij een knop met het standaardthema wordt geen thema vermeld. Grootte, stijl en klassennamen worden nooit afgedrukt.

Sommige attributen geven aan waar een component zich bevindt:

- `slot` benoemt het deel van de bovenliggende component dat een component vult. Het pictogram van **Pay** staat vóór de tekst en wordt daarom weergegeven als `[slot=prefix]`.
- `composite` benoemt de component waaraan een `Composite` is gekoppeld, en `node` het element dat deze rendert. De samenvatting is gekoppeld aan een `Div` en wordt daarom weergegeven als `[composite=Div] [node=div]`.
- `route` benoemt de route waaronder een weergave is geregistreerd.
- `hidden` markeert een verborgen component, zoals de kortingsregel. Een component verbergen of tonen verandert de snapshot.

Tekst staat altijd tussen aanhalingstekens. Een attribuutwaarde staat tussen aanhalingstekens als deze andere tekens bevat dan letters, cijfers en `.`, `_`, `+`, `/` of `-`, zoals in `[placeholder="Delivery instructions"]`.

## Lange lijsten {#long-lists}

Een lange lijst zou de rest van de weergave aan het zicht onttrekken. Daarom worden van de items in een lijst, de rijen in een tabel en de knooppunten in een boom maximaal tien regels onder de bovenliggende component weergegeven. De volgende lijst bevat twaalf landen:

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

De opgeslagen snapshot toont de eerste tien landen en één regel voor de drie resterende landen:

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

Als je meer items wilt opslaan, geef je de limiet door via de [snapshotopties](#snapshot-options) met `setMaxWidth`. `matchesSnapshot(name, options)` rendert met precies de opties die het ontvangt. Daarom schakelt de test ook de drie andere instellingen van een opgeslagen snapshot in en geeft deze een naam:

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

De opgeslagen snapshot bevat alle twaalf landen en staat in een bestand met de naam van de snapshot:

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip Tabellen en bomen
Een tabel telt de items in de repository niet mee en eindigt daarom met een kale `- …` in plaats van een aantal. De limiet geldt afzonderlijk voor elke bovenliggende component, dus een boom kapt elk niveau af na tien knooppunten.
:::

## Waar snapshots worden opgeslagen {#where-snapshots-are-stored}

Snapshotbestanden staan onder `src/test/resources/webforj-snapshots`, in de mappen van het package van de testklasse. Elk bestand krijgt de naam van de testklasse, gevolgd door de snapshotnaam en `.yml`:

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

De bestanden horen naast de tests in versiebeheer te staan. De eerste regel van elk bestand vermeldt de formaatversie. Een bestand dat met een andere versie is geschreven, laat de test mislukken met een bericht waarin beide versies worden vermeld, in plaats van met een diff.

## Snapshots een naam geven {#naming-snapshots}

`matchesSnapshot()` geeft de snapshot de naam van de testmethode die wordt uitgevoerd. De snapshot van `shouldMatchTheSnapshot` in `OrderSummaryTest` komt dus terecht in `OrderSummaryTest.shouldMatchTheSnapshot.yml`. Een test die meer dan één snapshot controleert, geeft elke snapshot een naam met `matchesSnapshot(name)`. Die naam komt in de bestandsnaam in plaats van de methodenaam. Een naam mag letters, cijfers, spaties, punten, streepjes en het teken `_` bevatten. Elk ander teken wordt vervangen door een streepje. Zo wordt `save/form:v2` opgeslagen als `save-form-v2`.

Met `matchesSnapshot(name, options)` kun je ook [`SnapshotOptions`](#snapshot-options) meegeven om mee te renderen, bijvoorbeeld om meer tabelrijen dan standaard op te slaan.

## Als een snapshot niet overeenkomt {#when-a-snapshot-doesnt-match}

Als de weergave niet meer overeenkomt met het opgeslagen bestand, mislukt de controle en wordt afgedrukt wat er is veranderd. Nadat de tekst van de knop **Pay** in de [ordersamenvatting](#matching-a-snapshot) is gewijzigd in **Pay now**, rapporteert `mvn test` het volgende:

```diff title="Terminal"
java.lang.AssertionError:
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## Snapshots bijwerken {#updating-snapshots}

Als een wijziging aan een weergave de bedoeling is, voer je de tests één keer uit met de systeemeigenschap `webforj.testbed.updateSnapshots` ingesteld op `true`. Elke snapshot die tijdens de uitvoering wordt gecontroleerd, wordt opnieuw geschreven. Snapshotbestanden die door geen enkele test meer worden gecontroleerd, worden verwijderd. Controleer de diff van de bestanden voordat je ze commit.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Verouderde snapshots {#obsolete-snapshots}

Nadat alle tests van een klasse zijn uitgevoerd, zoekt de TestBed naar snapshotbestanden van die klasse die door geen van de tests zijn gecontroleerd, bijvoorbeeld het bestand van een hernoemde test. Als zulke bestanden bestaan, mislukt de testklasse met een bericht waarin ze allemaal worden vermeld. Het bericht vraagt je ze te verwijderen of uit te voeren met `-Dwebforj.testbed.updateSnapshots=true`, zodat ze worden verwijderd.

:::info Afzonderlijke testmethoden
De controle wordt alleen uitgevoerd als alle tests van de klasse zijn uitgevoerd. Als je één testmethode vanuit de IDE start, wordt deze overgeslagen.
:::

## Continue integratie {#continuous-integration}

Snapshots worden lokaal geschreven en gecommit. Als de omgevingsvariabele `CI` is ingesteld op een andere waarde dan `false` of `0`, zoals bij de meeste services voor continue integratie, schrijft de TestBed nooit een snapshotbestand:

- Een ontbrekende snapshot laat de test mislukken met een bericht waarin het verwachte bestand wordt vermeld, in plaats van het bestand te schrijven.
- Een uitvoering met `-Dwebforj.testbed.updateSnapshots=true` mislukt en vraagt je de snapshots lokaal bij te werken.

## Een component afdrukken {#printing-a-component}

`Snapshot.render(component)` retourneert de beschrijving van een component als tekenreeks, zonder bestand. Druk deze af terwijl je een test schrijft om te zien wat de pagina bevat. Browserstatus, zoals de waarde die een gebruiker heeft ingevoerd, en verborgen componenten worden weggelaten, tenzij de meegegeven opties ze opnemen.

## Snapshotopties {#snapshot-options}

`SnapshotOptions` bepaalt hoeveel informatie een beschrijving bevat. Met `setMaxWidth` stel je in hoeveel items er onder een bovenliggende component worden afgedrukt; standaard zijn dat er tien. Met `setBrowserState` voeg je de status toe die de browser bijhoudt, zoals de waarde die een gebruiker heeft ingevoerd en de focus. Met `setHiddenComponents` voeg je verborgen componenten toe met de vlag `hidden`, en met `setComposites` voeg je `composite` en `node` toe.

Een opgeslagen snapshot bevat alles: de browserstatus, de verborgen componenten en de composites. `Snapshot.render` en `new SnapshotOptions()` bevatten geen van deze gegevens. `matchesSnapshot(name, options)` rendert met precies de opties die het ontvangt. Stel daarom voor meer items een grotere `maxWidth` in en schakel de andere drie instellingen in. De <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">referentie voor SnapshotOptions</JavadocLink> beschrijft elke instelling.

## Snapshots voor je eigen componenten {#snapshots-for-your-own-components}

Een component van je app toont zonder extra code het type, de tekst en de status van elke [concern-interface](/docs/building-ui/component-fundamentals#concern-interfaces) die deze implementeert, zoals een label of thema. Voor status die alleen je component kent, heb je een `SnapshotSerializer` nodig. De volgende beoordeling telt de sterren aan waarop de gebruiker klikt en retourneert het aantal via `getStars()`:

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

De serializer geeft in `getContract()` de componentklasse op die deze beschrijft en voegt het aantal sterren in `describe` toe aan de regel van de component:

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

De serializer wordt met zijn volledig gekwalificeerde naam vermeld in een bestand onder de testresources:

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

Elke snapshot van de testuitvoering gebruikt vervolgens deze serializer. De volgende test klikt op vier sterren en controleert de snapshot:

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

De regel van de beoordeling vermeldt het aantal sterren:

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

Naast `setAttribute` kan de `SnapshotNode` die een serializer ontvangt de tekst en vlaggen van de regel instellen, en regels toevoegen voor onderdelen van de component, zoals de kolommen en rijen van een tabel. Een serializer leest browserstatus alleen uit als `isBrowserState()` `true` retourneert. De <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">referentie voor SnapshotNode</JavadocLink> bevat alle methoden.

Een serializer van je app wordt uitgevoerd na de serializers van webforJ. Een attribuut dat deze instelt, vervangt dus de waarde die webforJ voor dezelfde sleutel heeft ingesteld. Als er twee serializers zijn voor dezelfde componentklasse, of als twee serializers hetzelfde attribuut op dezelfde regel schrijven, mislukt de snapshot met een bericht waarin beide klassen worden vermeld.
