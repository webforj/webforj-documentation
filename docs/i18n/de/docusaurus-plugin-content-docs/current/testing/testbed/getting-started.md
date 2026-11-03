---
sidebar_position: 1
title: Erste Schritte
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# Erste Schritte <DocChip chip='since' label='26.03' />

Das TestBed ermöglicht webforJ-Tests ohne Browser: Es führt Ansichten und Komponenten in einfachen JUnit-Tests aus – ganz ohne Browser oder Server. Auf dieser Seite fügst du die Abhängigkeit hinzu, schreibst deinen ersten Test und führst ihn aus.

Die Annotation `@WebforjTest` richtet für jeden Test einer Klasse einen `WebforjTestbed`-Kontext ein. Innerhalb eines Tests findet `ComponentLocator` Komponenten auf der Seite, und `UserInteraction` führt die Aktionen aus, die auch ein Benutzer mit ihnen ausführt.

<!-- INTRO_END -->

## Abhängigkeit hinzufügen {#adding-the-dependency}

Das TestBed wird von einem separaten Modul bereitgestellt. Füge es mit Test-Scope zu deiner App hinzu:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

Die Version stammt aus der Bill of Materials (BOM) von webforJ, daher wird in der Abhängigkeit keine Version angegeben. Ein Projekt, das mit einem [Archetyp](/docs/introduction/getting-started) erstellt wurde, importiert die BOM bereits. Das Modul bindet außerdem die JUnit-Jupiter-API ein.

:::info Spring-Boot-Apps
Spring-Boot-Apps verwenden stattdessen das Modul `webforj-spring-testbed`. Siehe [Spring-Tests](/docs/integrations/spring/testing).
:::

## Den ersten Test schreiben {#writing-a-first-test}

Das folgende Formular begrüßt den Namen, den der Benutzer eingibt:

```java title="GreetingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class GreetingForm extends Composite<Div> {

  public GreetingForm() {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    Button greet = new Button(
        "Greet", e -> greeting.setText("Hello, " + name.getValue() + "!"));

    getBoundComponent().add(name, greet, greeting);
  }
}
```

Der Test gibt einen Namen ein, klickt auf **Greet** und überprüft die Begrüßung:

```java title="GreetingFormTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.GreetingForm;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class GreetingFormTest {
  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingForm());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}
```

Der Test liest sich wie die einzelnen Schritte eines Benutzers:

1. `@WebforjTest` stellt jeder Testmethode einen `WebforjTestbed` bereit – den Kontext, in dem der Test ausgeführt wird.
2. `context.add()` fügt das Formular zur Seite hinzu und durchläuft dessen gesamten Lebenszyklus, so wie es beim Hinzufügen zu einem Frame in einer laufenden App geschieht.
3. `ComponentLocator` findet das Feld anhand seiner Beschriftung und den Button anhand seines Textes. Siehe [Komponenten finden](./finding-components).
4. `UserInteraction` gibt Text ein und klickt wie ein Benutzer, sodass der Listener des Buttons ausgeführt wird. Siehe [Benutzerinteraktionen](./user-interaction).
5. `verifyThat` prüft den Text, den ein Benutzer lesen würde. Siehe [Assertions](./assertions).

## Was `@WebforjTest` bereitstellt {#what-webforjtest-provides}

Jeder Test einer annotierten Klasse wird in einem eigenen `WebforjTestbed` ausgeführt. Der Kontext wird vor jedem Test eingerichtet und danach wieder abgebaut. Dadurch beginnt jeder Test mit einer leeren Seite, und es wird kein Zustand aus dem vorherigen Test übernommen.

Ein Test erreicht seinen Kontext auf eine von zwei Arten:

- Deklariere einen `WebforjTestbed`-Parameter für die Testmethode, wie im ersten Test.
- Rufe `WebforjTestbed.getCurrent()` auf, zum Beispiel aus einer Methode, die von den Tests einer Klasse gemeinsam verwendet wird.

`context.add()` akzeptiert eine oder mehrere Komponenten und fügt sie dem Root-Frame des Kontexts hinzu. Dieser wird von `context.getFrame()` zurückgegeben.

Tests, die zwischen Routen navigieren, statt Komponenten hinzuzufügen, setzen die Attribute `app`, `routeClasses` oder `routePackages` von `@WebforjTest`. Siehe [Apps testen](./testing-apps).

## Tests ausführen {#running-the-tests}

TestBed-Tests sind einfache JUnit-Tests. Führe sie mit dem Build-Tool oder wie jeden anderen JUnit-Test in der IDE aus:

```bash title="Terminal"
mvn test
```
