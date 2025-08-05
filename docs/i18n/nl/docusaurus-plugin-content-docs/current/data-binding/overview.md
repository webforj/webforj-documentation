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

 webforJ bevat een data-binding functie die naadloos UI-componenten integreert met backend datamodellen in Java-toepassingen. Deze functie overbrugt de kloof tussen de UI en de datalaag, en zorgt ervoor dat wijzigingen in de UI zich weerspiegelen in het datamodel en vice versa. Dit verbetert de gebruikerservaring en vermindert de complexiteit van event-handling en datasynchronisatie.

## Concept {#concept}

De volgende demonstratie toont een eenvoudige webforJ-app voor het registreren van superhelden met behulp van webforJ data-binding. De app bestaat uit twee hoofdonderdelen: `HeroRegistration.java` en `Hero.java`. 

In `HeroRegistration.java` configureert de code de gebruikersinterface met een `TextField` voor het invoeren van de naam van de held, een `ComboBox` om een superkracht te selecteren, en een `Button` om de registratie in te dienen.

De `Hero` klasse definieert het datamodel met validatiecriteria voor de naam en kracht van de held, zodat invoer geldig is en voldoet aan specifieke criteria zoals lengte en patroon.

De app maakt gebruik van de `BindingContext` om UI-componenten te binden aan de eigenschappen van het `Hero` object. Wanneer een gebruiker op de verzendknop klikt, schrijft de app de gegevens die in het formulier zijn ingevoerd terug naar de `Hero` bean, indien deze geldig zijn.

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

    // reflecteer de bean-gegevens in het formulier
    context.read(bean);

    submit.onClick(e -> {
      // schrijf de formuliergegevens terug naar de bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // doe iets met de bean
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

  @NotEmpty(message = "Naam kan niet leeg zijn")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Ongespecificeerde kracht")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Ongeldige kracht")
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
    return "Naam: " + name + ", Kracht: " + power;
  }
}
```

</TabItem>
</Tabs>

## Key features {#key-features}

- **Bidirectionele Binding:** Ondersteunt bidirectionele databinding, waardoor wijzigingen in het datamodel de UI bijwerken, en gebruikersinteracties in de UI het datamodel bijwerken.

- **Validatie Ondersteuning:** Integreert uitgebreide validatiemechanismen die je kunt aanpassen en uitbreiden. Ontwikkelaars kunnen hun eigen validatieregels implementeren of bestaande validatiekaders zoals Jakarta Validation gebruiken om de gegevensintegriteit te waarborgen voordat ze het model bijwerken.

- **Uitbreidbaarheid:** Kan eenvoudig worden uitgebreid om verschillende soorten UI-componenten, datatransformaties en complexe validatiescenario's te ondersteunen.

- **Annotatie-gedreven Configuratie:** Maakt gebruik van annotaties om boilerplatecode te minimaliseren, waardoor de bindingen tussen UI-componenten en datamodellen declaratief en eenvoudig te beheren zijn.

# Topics

<DocCardList className="topics-section" />
