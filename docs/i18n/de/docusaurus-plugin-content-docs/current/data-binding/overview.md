---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: dba8cbb47257595c025bb893bb2b4d39
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

 webforJ umfasst eine Datenbindungsfunktion, die UI-Komponenten mit Backend-Datenmodellen in Java-Anwendungen integriert. Diese Funktion überbrückt die Kluft zwischen der Benutzeroberfläche und der Datenebene, sodass Änderungen in der Benutzeroberfläche im Datenmodell und umgekehrt reflektiert werden, wodurch die Komplexität der Ereignisbehandlung und der Datensynchronisierung verringert wird.

<AISkillTip skill="webforj-building-forms" />

## Konzept {#concept}

Die folgende Demonstration zeigt eine einfache webforJ-App zur Registrierung von Superhelden unter Verwendung der webforJ-Datenbindung. Die App besteht aus zwei Hauptteilen: `HeroRegistration.java` und `Hero.java`. 

In `HeroRegistration.java` konfiguriert der Code die Benutzeroberfläche mit einem `TextField` zum Eingeben des Namens des Helden, einem `ComboBox` zur Auswahl einer Superkraft und einem `Button` zum Übermitteln der Registrierung.

Die Klasse `Hero` definiert das Datenmodell mit Validierungsanforderungen für den Namen und die Kraft des Helden. Die Eingaben müssen gültig und müssen bestimmte Kriterien wie Länge und Muster einhalten.

Die App verwendet den `BindingContext`, um UI-Komponenten an die Eigenschaften des `Hero`-Objekts zu binden. Wenn ein Benutzer auf die Schaltfläche "Absenden" klickt, schreibt die App die im Formular eingegebenen Daten zurück in den `Hero`-Bean, sofern diese gültig sind.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Textfeld");
  private ComboBox power = new ComboBox("Kraft");
  private Button submit = new Button("Antrag einreichen");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Fliegen", "Unsichtbar", "Laserblick", "Geschwindigkeit", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fliegen");

    // spiegeln Sie die Bean-Daten im Formular wider
    context.read(bean);

    submit.onClick(e -> {
      // schreiben Sie die Formulardaten zurück in die Bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // tun Sie etwas mit der Bean
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

  @NotEmpty(message = "Unspecified power")
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

## Schlüsselmerkmale {#key-features}

- **Bidirektionale Bindung:** Unterstützt bidirektionale Datenbindung, die es ermöglicht, dass Änderungen im Datenmodell die Benutzeroberfläche aktualisieren und Benutzerinteraktionen in der Benutzeroberfläche das Datenmodell aktualisieren.

- **Unterstützung der Validierung:** Integriert umfassende Validierungsmechanismen, die Sie anpassen und erweitern können. Entwickler können ihre eigenen Validierungsregeln implementieren oder bestehende Validierungsframeworks wie Jakarta Validation verwenden, um die Datenintegrität vor der Aktualisierung des Modells zu überprüfen.

- **Erweiterbarkeit:** Kann leicht erweitert werden, um verschiedene Arten von UI-Komponenten, Datenumwandlungen und komplexe Validierungsszenarien zu unterstützen.

- **Annotierungsgetriebene Konfiguration:** Verwendet Annotationen, um Boilerplate-Code zu minimieren und die Bindungen zwischen UI-Komponenten und Datenmodellen deklarativ und leicht verwaltbar zu gestalten.

# Themen

<DocCardList className="topics-section" />
