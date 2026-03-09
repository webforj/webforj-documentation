---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: ba33283588df8722a31ad0c5fb15892a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

 webforJ beinhaltet eine Datenbindung-Funktion, die UI-Komponenten mit Backend-Datenmodellen in Java-Anwendungen integriert. Diese Funktion schließt die Lücke zwischen der UI und der Datenschicht, sodass Änderungen in der UI sich im Datenmodell widerspiegeln und umgekehrt, wodurch die Komplexität der Ereignisverarbeitung und Daten-synchronisierung verringert wird.

## Konzept {#concept}

Die folgende Demonstration zeigt eine einfache webforJ-App zur Registrierung von Superhelden unter Verwendung der webforJ-Datenbindung. Die App besteht aus zwei Hauptteilen: `HeroRegistration.java` und `Hero.java`.

In `HeroRegistration.java` konfiguriert der Code die Benutzeroberfläche mit einem `TextField` zum Eingeben des Namens des Helden, einer `ComboBox` zur Auswahl einer Superkraft und einem `Button`, um die Registrierung abzusenden.

Die Klasse `Hero` definiert das Datenmodell mit Validierungseinschränkungen für den Namen und die Kraft des Helden. Die Eingaben müssen gültig sein und bestimmten Kriterien wie Länge und Muster entsprechen.

Die App verwendet den `BindingContext`, um UI-Komponenten an die Eigenschaften des `Hero`-Objekts zu binden. Wenn ein Benutzer auf den Absenden-Button klickt, schreibt die App die im Formular eingegebenen Daten zurück in das `Hero`-Bean, sofern sie gültig sind.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Text Field");
  private ComboBox power = new ComboBox("Power");
  private Button submit = new Button("Submit Application");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Fly", "Invisible", "LaserVision", "Speed", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fly");

    // reflect the bean data in the form
    context.read(bean);

    submit.onClick(e -> {
      // write the form data back to the bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // do something with the bean
        // repository.persist(bean)
      }
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Hero" label="Hero.java">

```java showLineNumbers
public class Hero {

  @NotEmpty(message = "Name cannot be empty")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unspecified power")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Invalid power")
  private String power;

  public Hero(String name, String power) {
    this.name = name;
    this.power = power;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String toString() {
    return "Name: " + name + ", Power: " + power;
  }
}
```

</TabItem>
</Tabs>

## Hauptmerkmale {#key-features}

- **Bidirektionale Bindung:** Unterstützt bidirektionale Datenbindung, die es Änderungen im Datenmodell ermöglicht, die UI zu aktualisieren, und Benutzereingaben in der UI, das Datenmodell zu aktualisieren.

- **Unterstützung für Validierung:** Integriert umfassende Validierungsmechanismen, die Sie anpassen und erweitern können. Entwickler können ihre eigenen Validierungsregeln implementieren oder vorhandene Validierungsframeworks wie Jakarta Validation verwenden, um die Datenintegrität vor der Aktualisierung des Modells zu überprüfen.

- **Erweiterbarkeit:** Kann leicht erweitert werden, um verschiedene Arten von UI-Komponenten, Datenumwandlungen und komplexe Validierungsszenarien zu unterstützen.

- **Annotierungsgetriebene Konfiguration:** Verwendet Annotations, um Boilerplate-Code zu minimieren und die Bindungen zwischen UI-Komponenten und Datenmodellen deklarativ und leicht zu verwalten.

# Themen

<DocCardList className="topics-section" />
