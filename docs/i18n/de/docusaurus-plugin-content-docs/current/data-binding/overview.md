---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fef9723206ef7122c3ada5503f97edf1
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

 webforJ umfasst eine Datenbindung, die UI-Komponenten nahtlos mit Backend-Datenmodellen in Java-Anwendungen integriert. Diese Funktion überbrückt die Kluft zwischen der Benutzeroberfläche und der Datenschicht und sorgt dafür, dass Änderungen in der Benutzeroberfläche im Datenmodell und umgekehrt widerspiegelt werden. Dadurch wird die Benutzererfahrung verbessert und die Komplexität der Ereignisbehandlung sowie der Datensynchronisierung reduziert.

## Konzept {#concept}

Die folgende Demonstration zeigt eine einfache webforJ-App zur Registrierung von Superhelden mithilfe der Datenbindung von webforJ. Die App besteht aus zwei Hauptteilen: `HeroRegistration.java` und `Hero.java`. 

In `HeroRegistration.java` konfiguriert der Code die Benutzeroberfläche mit einem `TextField` zur Eingabe des Namens des Helden, einem `ComboBox` zur Auswahl einer Superkraft und einem `Button`, um die Registrierung abzusenden.

Die `Hero`-Klasse definiert das Datenmodell mit Validierungsbeschränkungen für den Namen und die Kraft des Helden und stellt sicher, dass die Eingaben gültig sind und den festgelegten Kriterien wie Länge und Muster entsprechen.

Die App nutzt den `BindingContext`, um UI-Komponenten mit den Eigenschaften des `Hero`-Objekts zu verbinden. Wenn der Benutzer auf die Schaltfläche absenden klickt, schreibt die App die im Formular eingegebenen Daten zurück in die `Hero`-Bean, sofern sie gültig sind.

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
    power.insert("Fliegen", "Unsichtbar", "LaserVision", "Geschwindigkeit", "Teleportation");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Fliegen");

    // Daten des Beans im Formular widerspiegeln
    context.read(bean);

    submit.onClick(e -> {
      // Formulardaten zurück in den Bean schreiben
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

  @NotEmpty(message = "Der Name darf nicht leer sein")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Unbestimmte Kraft")
  @Pattern(regexp = "Fliegen|Unsichtbar|LaserVision|Geschwindigkeit|Teleportation", message = "Ungültige Kraft")
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

- **Bidirektionale Bindung:** Unterstützt bidirektionale Datenbindung, sodass Änderungen im Datenmodell die Benutzeroberfläche aktualisieren und Benutzerinteraktionen in der Benutzeroberfläche das Datenmodell aktualisieren.

- **Validierungsunterstützung:** Integriert umfassende Validierungsmechanismen, die individuell angepasst und erweitert werden können. Entwickler können ihre eigenen Validierungsregeln implementieren oder vorhandene Validierungsframeworks wie Jakarta Validation verwenden, um die Datenintegrität vor dem Aktualisieren des Modells sicherzustellen.

- **Erweiterbarkeit:** Kann leicht erweitert werden, um verschiedene Arten von UI-Komponenten, Datenumwandlungen und komplexe Validierungsszenarien zu unterstützen.

- **Annotierte Konfiguration:** Nutzt Annotationen, um Boilerplate-Code zu minimieren und die Bindungen zwischen UI-Komponenten und Datenmodellen deklarativ und einfach zu verwalten.

# Themen

<DocCardList className="topics-section" />
