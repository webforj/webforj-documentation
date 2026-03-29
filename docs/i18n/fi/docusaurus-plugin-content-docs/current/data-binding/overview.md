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

 webforJ sisältää tietosidontatoiminnon, joka integroi käyttöliittymäkomponentit taustatietomalleihin Java-sovelluksissa. Tämä toiminto ylittää kuilun käyttöliittymän ja tietokerroksen välillä, jotta käyttöliittymässä tapahtuvat muutokset heijastuvat tietomalliin ja päinvastoin, vähentäen tapahtumien käsittelyn ja tietosynkronoinnin monimutkaisuutta.

## Konsepti {#concept}

Seuraava esittely näyttää yksinkertaisen webforJ-sovelluksen, joka rekisteröi supersankareita hyödyntäen webforJ-tietosidontaa. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`.

`HeroRegistration.java`-tiedostossa koodi määrittää käyttäjäliittymän, jossa on `TextField` sankarin nimen syöttämiseen, `ComboBox` supervoiman valitsemiseen ja `Button` rekisteröinnin lähettämiseen.

`Hero`-luokka määrittelee tietomallin, johon liittyy vahvistusrajoituksia sankarin nimen ja voiman osalta. Syötteiden on oltava voimassa ja niiden on noudatettava määriteltyjä kriteereitä, kuten pituutta ja kaavaa.

Sovellus käyttää `BindingContext`-konseptia sitomaan käyttöliittymäkomponentit `Hero`-olion ominaisuuksiin. Kun käyttäjä napsauttaa lähetyspainiketta, sovellus kirjoittaa lomakkeeseen syötetyt tiedot takaisin `Hero`-beanille, jos ne ovat voimassa.

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
    power.insert("Lentää", "Näkymättömyys", "Laservisio", "Nopeus", "Teleportaatio");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Lentää");

    // heijastaa beanin tiedot lomakkeeseen
    context.read(bean);

    submit.onClick(e -> {
      // kirjoita lomaketiedot takaisin beanille
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
  @Pattern(regexp = "Lentää|Näkymättömyys|Laservisio|Nopeus|Teleportaatio", message = "Virheellinen voima")
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

## Keskeiset ominaisuudet {#key-features}

- **Kaksisuuntainen sidonta:** Tukee kaksisuuntaista tietosidontaa, jolloin tietomallissa tapahtuvat muutokset päivittävät käyttöliittymän, ja käyttöliittymässä tapahtuvat käyttäjävuorovaikutukset päivittävät tietomallin.

- **Vahvistustuki:** Integroi kattavat vahvistusmekanismit, joita voit mukauttaa ja laajentaa. Kehittäjät voivat toteuttaa omia vahvistussääntöjään tai käyttää olemassa olevia vahvistuskehyksiä, kuten Jakarta Validation, varmistaakseen tietojen eheyden ennen mallin päivittämistä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia käyttöliittymäkomponentteja, tietomuunnoksia ja monimutkaisempia vahvistustilanteita.

- **Annotaatioihin perustuva konfigurointi:** Käyttää annotaatioita vähentääkseen ylikirjoituskoodia, jolloin käyttöliittymäkomponenttien ja tietomallien välinen sidonta on deklaratiivista ja helppoa hallita.

# Aiheet

<DocCardList className="topics-section" />
