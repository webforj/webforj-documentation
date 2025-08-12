---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 2ce381aec06e45ed4001e7dbfdb22dc0
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

 webforJ umfasst eine Datenbindungsfunktion, die UI-Komponenten nahtlos mit Backend-Datenmodellen in Java-Anwendungen integriert. Diese Funktion überbrückt die Lücke zwischen der Benutzeroberfläche und der Datenschicht und sorgt dafür, dass Änderungen in der Benutzeroberfläche im Datenmodell und umgekehrt angezeigt werden. Dadurch wird das Benutzererlebnis verbessert und die Komplexität der Ereignisbehandlung und Datensynchronisation reduziert.

## Konzept {#concept}

Die folgende Demonstration zeigt eine einfache webforJ-App zur Registrierung von Superhelden mithilfe der Datenbindung von webforJ. Die App besteht aus zwei Hauptteilen: `HeroRegistration.java` und `Hero.java`. 

In `HeroRegistration.java` konfiguriert der Code die Benutzeroberfläche mit einem `TextField`, um den Namen des Helden einzugeben, einer `ComboBox`, um eine Superkraft auszuwählen, und einem `Button`, um die Registrierung abzuschicken.

Die Klasse `Hero` definiert das Datenmodell mit Validierungsbedingungen für den Namen und die Kraft des Helden, um sicherzustellen, dass die Eingaben gültig sind und bestimmten Kriterien wie Länge und Muster entsprechen.

Die App nutzt den `BindingContext`, um UI-Komponenten mit den Eigenschaften des `Hero`-Objekts zu verknüpfen. Wenn ein Benutzer auf die Schaltfläche "Absenden" klickt, schreibt die App die im Formular eingegebenen Daten zurück in das `Hero`-Bean, sofern sie gültig sind.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Textfeld");
  private ComboBox power = new ComboBox("Kraft");
  private Button submit = new Button("Bewerbung absenden");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Fliegen", "Unsichtbar", "Laserblick", "Geschwindigkeit", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fliegen");

    // spiegeln Sie die Beandaten im Formular wider
    context.read(bean);

    submit.onClick(e -> {
      // schreiben Sie die Formulardaten zurück in das Bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // etwas mit dem Bean machen
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

  @NotEmpty(message = "Name darf nicht leer sein")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unbenannte Kraft")
  @Pattern(regexp = "Fliegen|Unsichtbar|Laserblick|Geschwindigkeit|Teleportation", message = "Ungültige Kraft")
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
    return "Name: " + name + ", Kraft: " + power;
  }
}
```

</TabItem>
</Tabs>

## Hauptmerkmale {#key-features}

- **Bidirektionale Bindung:** Unterstützt bidirektionale Datenbindung, die Änderungen im Datenmodell die Benutzeroberfläche aktualisieren lässt und Benutzerinteraktionen in der Benutzeroberfläche das Datenmodell aktualisieren lässt.

- **Validierungsunterstützung:** Integriert umfassende Validierungsmechanismen, die Sie anpassen und erweitern können. Entwickler können ihre eigenen Validierungsregeln implementieren oder bestehende Validierungsframeworks wie Jakarta Validation verwenden, um die Datenintegrität vor der Aktualisierung des Modells sicherzustellen.

- **Erweiterbarkeit:** Kann leicht erweitert werden, um verschiedene Arten von UI-Komponenten, Datentransformationen und komplexen Validierungsszenarien zu unterstützen.

- **Annotation-getriebene Konfiguration:** Nutzt Annotationen, um Boilerplate-Code zu minimieren, wodurch die Bindungen zwischen UI-Komponenten und Datenmodellen deklarativ und einfach zu verwalten sind.

# Themen

<DocCardList className="topics-section" />
