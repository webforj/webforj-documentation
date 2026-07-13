---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Connect webforJ UI components to Java backend models with two-way
  synchronization, validation, and transformation through BindingContext.
_i18n_hash: 75d09e2278ebe54cb17f4dbc69444449
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

webforJ sisältää tietosidontatoiminnon, joka integroi käyttöliittymäkomponentit taustapalvelinmallien kanssa Java-sovelluksissa. Tämä ominaisuus yhdistää käyttöliittymän ja tietokerroksen niin, että käyttöliittymässä tapahtuvat muutokset heijastuvat tietomalliin ja päinvastoin, vähentäen tapahtumankäsittelyn ja tietosynkronoinnin monimutkaisuutta.

<AISkillTip skill="webforj-building-forms" />

## Konsepti {#concept}

Seuraava esittely esittelee yksinkertaisen webforJ-sovelluksen, jonka avulla rekisteröidään supersankareita käyttäen webforJ:n tietosidontaa. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`.

`HeroRegistration.java`-koodissa konfiguroidaan käyttöliittymä `TextFieldin` avulla sankarin nimen syöttämiseen, `ComboBox` supervoiman valitsemiseen ja `Button` rekisteröinnin lähettämiseen.

`Hero`-luokka määrittelee tietomallin, jossa on käytettävissä sankarin nimen ja voiman validointirajoituksia. Syötteiden täytyy olla voimassa ja noudattaa määriteltyjä kriteerejä, kuten pituus ja malli.

Sovellus käyttää `BindingContext`-luokkaa sitomaan käyttöliittymäkomponentit `Hero`-olion ominaisuuksiin. Kun käyttäjä napsauttaa lähetysnappia, sovellus kirjoittaa lomakkeelle syötetyt tiedot takaisin `Hero`-bean-olioon, jos ne ovat voimassa.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {

  private TextField name = new TextField("Tekstikenttä");
  private ComboBox power = new ComboBox("Voima");
  private Button submit = new Button("Lähetä hakemus");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Lennä", "Näkyvä", "Lasersäde", "Nopeus", "Teleportaatiot");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Teräsmies", "Lennä");

    // heijasta bean-data lomakkeeseen
    context.read(bean);

    submit.onClick(e -> {
      // kirjoita lomaketiedot takaisin beaniin
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // tee jotain beanin kanssa
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

  @NotEmpty(message = "Nimi ei voi olla tyhjää")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Määrittelemätön voima")
  @Pattern(regexp = "Lennä|Näkyvä|Lasersäde|Nopeus|Teleportaatiot", message = "Virheellinen voima")
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
    return "Nimi: " + name + ", Voima: " + power;
  }
}
```

</TabItem>
</Tabs>

## Avainominaisuudet {#key-features}

- **Kaksisuuntainen sidonta:** Tukee kaksisuuntaista tietosidontaa, jonka avulla tietomallissa tapahtuvat muutokset päivittävät käyttöliittymän ja käyttöliittymässä tapahtuvat käyttäjävuorovaikutukset päivittävät tietomallia.

- **Validointituki:** Integroi kattavat validointimekanismit, joita voit mukauttaa ja laajentaa. Kehittäjät voivat toteuttaa omia validointisääntöjään tai käyttää olemassa olevia validointikehyksiä, kuten Jakarta Validation, varmistaakseen tietojen eheyden ennen mallin päivittämistä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia käyttöliittymäkomponentteja, datamuunnoksia ja monimutkaisia validointitilanteita.

- **Annotaatioihin perustuva konfigurointi:** Käyttää annotaatioita vähentämään boilerplate-koodia, jolloin sidokset käyttöliittymäkomponenttien ja tietomallien välillä ovat deklaratiivisia ja helppoja hallita.

## Aiheet {#topics}

<DocCardList className="topics-section" />
