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

webforJ bevat een functie voor gegevensbinding die UI-componenten integreert met backend datamodellen in Java-toepassingen. Deze functie overbrugt de kloof tussen de UI en de datalaag, zodat wijzigingen in de UI worden weerspiegeld in het datamodel en vice versa, waardoor de complexiteit van gebeurtenisafhandeling en gegevenssynchronisatie wordt verminderd.

## Concept {#concept}

De volgende demonstratie toont een eenvoudige webforJ-app voor het registreren van superhelden met behulp van webforJ-gegevensbinding. De app bestaat uit twee hoofdonderdelen: `HeroRegistration.java` en `Hero.java`. 

In `HeroRegistration.java` configureert de code de gebruikersinterface met een `TextField` voor het invoeren van de naam van de held, een `ComboBox` om een superkracht te selecteren en een `Button` om de registratie in te dienen.

De `Hero`-klasse definieert het datamodel met validatiebeperkingen voor de naam en kracht van de held. Invoeren moeten geldig zijn en voldoen aan gespecificeerde criteria zoals lengte en patroon.

De app gebruikt de `BindingContext` om UI-componenten te koppelen aan de eigenschappen van het `Hero`-object. Wanneer een gebruiker op de verzendknop klikt, schrijft de app de ingevoerde gegevens in het formulier terug naar de `Hero`-bean als ze geldig zijn.

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

## Belangrijkste functies {#key-features}

- **Bidirectionele Binding:** Ondersteunt bidirectionele gegevensbinding, waardoor wijzigingen in het datamodel de UI kunnen bijwerken, en gebruikersinteracties in de UI het datamodel kunnen bijwerken.

- **Ondersteuning voor Validatie:** Integreert uitgebreide validatiemechanismen die u kunt aanpassen en uitbreiden. Ontwikkelaars kunnen hun eigen validatieregels implementeren of bestaande validatiekaders zoals Jakarta Validation gebruiken om de gegevensintegriteit te verifiëren vóór het bijwerken van het model.

- **Uitbreidbaarheid:** Kan eenvoudig worden uitgebreid om verschillende soorten UI-componenten, gegevens-transformaties en complexe validatiescenario's te ondersteunen.

- **Annotatie-gedreven Configuratie:** Maakt gebruik van annotaties om de boilerplate-code te minimaliseren, waardoor de bindingen tussen UI-componenten en datamodellen declaratief en eenvoudig te beheren zijn.

# Onderwerpen

<DocCardList className="topics-section" />
