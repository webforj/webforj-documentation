---
sidebar_position: 5
title: Momentaufnahmen
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
Eine Ansicht Punkt für Punkt zu prüfen, erfordert eine Assertion für jedes Label, jeden Button und jedes Feld. Ein Snapshot prüft alles auf einmal. Er hält fest, was ein Benutzer in der Ansicht sehen würde: eine Zeile pro Komponente. Diese Beschreibung wird zusammen mit dem Test gespeichert. Bei jedem späteren Lauf wird die Ansicht erneut beschrieben und mit dem gespeicherten Snapshot verglichen. Jede Änderung an der sichtbaren Seite lässt den Test fehlschlagen und wird als einfacher Text-Diff im Code-Review angezeigt.

<!-- INTRO_END -->

## Einen Snapshot abgleichen {#matching-a-snapshot}

Die folgende Zusammenfassung zeigt eine Bestellnummer, ein Notizfeld, eine ausgeblendete Rabattzeile, einen **Pay**-Button mit Symbol und einen deaktivierten **Cancel**-Button:

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

Der Test fügt die Zusammenfassung hinzu und prüft sie mit `matchesSnapshot()`:

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

Beim ersten Lauf wird die Beschreibung in eine Datei geschrieben und der Test ist erfolgreich. Bei jedem späteren Lauf schlägt der Test fehl, wenn die Ansicht nicht mehr mit der gespeicherten Datei übereinstimmt:

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

`matchesSnapshot` ist eine Prüfung von `verifyThat`, wie die auf der Seite [Assertions](./assertions). Sie funktioniert daher mit jeder Komponente, nicht nur mit einer Ansicht.

:::tip Wann Snapshots sinnvoll sind
Ein Snapshot fixiert eine ganze Ansicht. Verwende ihn für Ansichten, deren Inhalt sich nicht ändern sollte. Prüfe einzelne Sachverhalte, etwa den Text eines Buttons nach einem Klick, mit [Assertions](./assertions).
:::

## Einen Snapshot lesen {#reading-a-snapshot}

Jede Zeile des [gespeicherten Snapshots](#matching-a-snapshot) beschreibt eine Komponente. Die darin enthaltenen Komponenten stehen darunter, um zwei Leerzeichen eingerückt. Eine Zeile, die weitere Komponenten enthält, endet mit einem Doppelpunkt. Eine Zeile ist in folgender Reihenfolge aufgebaut:

1. Der Typ der Komponente, etwa `Button`.
2. Der Text in doppelten Anführungszeichen, sofern die Komponente einen anzeigt, etwa `"Pay"`.
3. Attribute im Format `[key=value]`, etwa `[theme=primary]` oder `[label=Note]`.
4. Flags im Format `[key]`, etwa `[disabled]` oder `[hidden]`.

Eine Zeile gibt nur Werte aus, die vom Standardwert der Komponente abweichen. So lässt sich die Zusammenfassung auf einen Blick erfassen. **Cancel** hat das Flag `[disabled]`, während beim aktivierten **Pay**-Button kein Status angegeben ist. **Pay** hat das Attribut `[theme=primary]`; bei einem Button mit dem Standard-Theme wird kein Theme angegeben. Größen, Stile und Klassennamen werden nie ausgegeben.

Einige Attribute geben an, wo sich eine Komponente befindet:

- `slot` benennt den Teil des übergeordneten Elements, den eine Komponente belegt. Das Symbol von **Pay** steht vor dem Text und wird daher mit `[slot=prefix]` ausgegeben.
- `composite` benennt die Komponente, an die ein `Composite` gebunden ist, und `node` das Element, das es rendert. Die Zusammenfassung ist an ein `Div` gebunden und wird daher mit `[composite=Div] [node=div]` ausgegeben.
- `route` benennt die Route, unter der eine Ansicht registriert ist.
- `hidden` kennzeichnet eine ausgeblendete Komponente, etwa die Rabattzeile. Das Ein- oder Ausblenden einer Komponente ändert den Snapshot.

Text steht immer in Anführungszeichen. Ein Attributwert wird in Anführungszeichen gesetzt, wenn er andere Zeichen als Buchstaben, Ziffern und `.`, `_`, `+`, `/` oder `-` enthält, wie bei `[placeholder="Delivery instructions"]`.

## Lange Listen {#long-lists}

Eine lange Liste würde den Rest der Ansicht verdrängen. Deshalb werden Listenelemente, Tabellenzeilen und Baumknoten unter ihrem übergeordneten Element auf höchstens zehn Zeilen begrenzt. Die folgende Liste enthält zwölf Länder:

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

Der gespeicherte Snapshot gibt die ersten zehn Länder sowie eine Zeile für die drei übrigen aus:

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

Um mehr Elemente zu speichern, übergib das Limit in den [Snapshot-Optionen](#snapshot-options) mit `setMaxWidth`. `matchesSnapshot(name, options)` rendert exakt mit den übergebenen Optionen. Deshalb aktiviert der Test auch die drei anderen Einstellungen des gespeicherten Snapshots und gibt ihm einen Namen:

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

Der gespeicherte Snapshot enthält alle zwölf Länder und wird in einer Datei mit dem Namen des Snapshots abgelegt:

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

:::tip Tabellen und Bäume
Eine Tabelle zählt die Elemente ihres Repositorys nicht und endet daher mit einem einfachen `- …` statt mit einer Anzahl. Das Limit gilt jeweils für jedes übergeordnete Element einzeln. Bei einem Baum wird daher jede Ebene auf zehn Knoten begrenzt.
:::

## Speicherort von Snapshots {#where-snapshots-are-stored}

Snapshot-Dateien liegen unter `src/test/resources/webforj-snapshots`, in den Ordnern des Pakets der Testklasse. Jede Datei wird nach der Testklasse benannt, gefolgt vom Snapshot-Namen und `.yml`:

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

Die Dateien gehören zusammen mit den Tests in die Versionsverwaltung. Die erste Zeile jeder Datei gibt die Formatversion an. Eine Datei, die mit einer anderen Version geschrieben wurde, lässt den Test mit einer Meldung fehlschlagen, die beide Versionen nennt, statt einen Diff anzuzeigen.

## Snapshots benennen {#naming-snapshots}

`matchesSnapshot()` benennt den Snapshot nach der gerade ausgeführten Testmethode. Der Snapshot von `shouldMatchTheSnapshot` in `OrderSummaryTest` wird daher in `OrderSummaryTest.shouldMatchTheSnapshot.yml` abgelegt. Ein Test, der mehr als einen Snapshot prüft, übergibt jedem mit `matchesSnapshot(name)` einen Namen. Dieser Name ersetzt den Methodennamen im Dateinamen. Ein Name darf Buchstaben, Ziffern, Leerzeichen, Punkte, Bindestriche und das Zeichen `_` enthalten. Alle anderen Zeichen werden durch einen Bindestrich ersetzt. So wird `save/form:v2` als `save-form-v2` gespeichert.

`matchesSnapshot(name, options)` akzeptiert außerdem die [`SnapshotOptions`](#snapshot-options), mit denen gerendert werden soll, zum Beispiel um mehr Tabellenzeilen als standardmäßig zu speichern.

## Wenn ein Snapshot nicht übereinstimmt {#when-a-snapshot-doesnt-match}

Wenn die Ansicht nicht mehr mit der gespeicherten Datei übereinstimmt, schlägt die Prüfung fehl und zeigt die Änderungen an. Nachdem der Text des **Pay**-Buttons in der [Bestellzusammenfassung](#matching-a-snapshot) in **Pay now** geändert wurde, meldet `mvn test`:

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

## Snapshots aktualisieren {#updating-snapshots}

Wenn eine Änderung an einer Ansicht beabsichtigt ist, führe die Tests einmal mit der Systemeigenschaft `webforj.testbed.updateSnapshots` auf `true` aus. Jeder Snapshot, den der Lauf prüft, wird neu geschrieben. Snapshot-Dateien, die von keinem Test mehr geprüft werden, werden gelöscht. Überprüfe den Diff der Dateien, bevor du sie commitest.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Veraltete Snapshots {#obsolete-snapshots}

Nachdem alle Tests einer Klasse ausgeführt wurden, sucht das TestBed nach Snapshot-Dateien dieser Klasse, die keiner ihrer Tests geprüft hat, etwa nach der Datei eines umbenannten Tests. Eine solche Datei lässt die Testklasse mit einer Meldung fehlschlagen, die alle diese Dateien auflistet und dazu auffordert, sie zu löschen oder den Lauf mit `-Dwebforj.testbed.updateSnapshots=true` zu starten, wodurch sie gelöscht werden.

:::info Einzelne Testmethoden
Die Prüfung wird nur ausgeführt, wenn alle Tests der Klasse gelaufen sind. Wird eine einzelne Testmethode in der IDE gestartet, wird sie übersprungen.
:::

## Continuous Integration {#continuous-integration}

Snapshots werden lokal geschrieben und committet. Wenn die Umgebungsvariable `CI` auf einen anderen Wert als `false` oder `0` gesetzt ist, wie es bei den meisten Continuous-Integration-Diensten der Fall ist, schreibt das TestBed niemals eine Snapshot-Datei:

- Ein fehlender Snapshot lässt den Test mit einer Meldung fehlschlagen, die die erwartete Datei nennt, statt sie zu schreiben.
- Ein Lauf mit `-Dwebforj.testbed.updateSnapshots=true` schlägt fehl und fordert dazu auf, die Snapshots lokal zu aktualisieren.

## Eine Komponente ausgeben {#printing-a-component}

`Snapshot.render(component)` gibt die Beschreibung einer Komponente als Zeichenfolge zurück, ohne eine Datei zu schreiben. Gib sie während des Schreibens eines Tests aus, um zu sehen, was die Seite enthält. Browserseitiger Zustand, etwa ein von einem Benutzer eingegebener Wert, und ausgeblendete Komponenten werden ausgelassen, sofern die übergebenen Optionen sie nicht einschließen.

## Snapshot-Optionen {#snapshot-options}

`SnapshotOptions` legt fest, wie viele Details eine Beschreibung enthält. `setMaxWidth` bestimmt, wie viele Elemente unter einem übergeordneten Element ausgegeben werden; standardmäßig sind es zehn. `setBrowserState` fügt den browserseitigen Zustand hinzu, etwa einen vom Benutzer eingegebenen Wert und den Fokus. `setHiddenComponents` fügt ausgeblendete Komponenten mit dem Flag `hidden` hinzu, und `setComposites` fügt `composite` und `node` hinzu.

Ein gespeicherter Snapshot enthält alles: den browserseitigen Zustand, die ausgeblendeten Komponenten und die Composites. `Snapshot.render` und `new SnapshotOptions()` enthalten nichts davon. `matchesSnapshot(name, options)` rendert exakt mit den übergebenen Optionen. Um mehr Elemente zu speichern, setze daher einen größeren Wert für `maxWidth` und aktiviere die drei anderen Einstellungen. Die Referenz zu <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink> beschreibt jede Einstellung.

## Snapshots für eigene Komponenten {#snapshots-for-your-own-components}

Eine Komponente deiner App gibt ihren Typ, ihren Text und den Zustand aller von ihr implementierten [Concern-Schnittstellen](/docs/building-ui/component-fundamentals#concern-interfaces) aus, etwa eines Labels oder eines Themes, ohne dass zusätzlicher Code nötig ist. Für den Zustand, den nur deine Komponente kennt, wird ein `SnapshotSerializer` benötigt. Die folgende Sternebewertung zählt die Sterne, auf die der Benutzer klickt, und gibt sie über `getStars()` zurück:

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

Der Serializer gibt in `getContract()` die Komponentenklasse an, die er beschreibt, und fügt in `describe` die Anzahl der Sterne zur Zeile der Komponente hinzu:

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

Der Serializer wird in einer Datei unter den Testressourcen mit seinem voll qualifizierten Namen aufgeführt:

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

Anschließend wird er für jeden Snapshot des Testlaufs verwendet. Der folgende Test klickt auf vier Sterne und prüft den Snapshot:

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

Die Zeile der Sternebewertung enthält die Anzahl der Sterne:

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

Zusätzlich zu `setAttribute` kann der `SnapshotNode`, den ein Serializer erhält, den Text der Zeile und ihre Flags festlegen sowie darunter weitere Zeilen für Teile der Komponente hinzufügen, etwa für die Spalten und Zeilen einer Tabelle. Ein Serializer liest den browserseitigen Zustand nur dann aus, wenn `isBrowserState()` `true` zurückgibt. Die Referenz zu <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink> führt alle Methoden auf.

Ein Serializer deiner App wird nach den Serializern von webforJ ausgeführt. Ein von ihm festgelegtes Attribut ersetzt daher den Wert, den webforJ für denselben Schlüssel gesetzt hat. Zwei Serializer für dieselbe Komponentenklasse oder zwei Serializer, die dasselbe Attribut in derselben Zeile schreiben, lassen den Snapshot mit einer Meldung fehlschlagen, die beide Klassen nennt.
