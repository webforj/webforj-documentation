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

webforJ sisältää tiedon sitomisen ominaisuuden, joka yhdistää käyttöliittymäkomponentit taustatietomalleihin Java-sovelluksissa. Tämä ominaisuus kaventaa kuilua käyttöliittymän ja tietokerroksen välillä siten, että muutokset käyttöliittymässä heijastuvat tietomalliin ja päinvastoin, vähentäen tapahtumakäsittelyn ja tietojen synkronoinnin monimutkaisuutta.

<AISkillTip skill="webforj-building-forms" />

## Käsite {#concept}

Seuraava esitys näyttää yksinkertaisen webforJ-sovelluksen supersankareiden rekisteröimiseksi webforJ-tiedon sitomisen avulla. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`.

`HeroRegistration.java`-tiedostossa koodi konfiguroi käyttöliittymän `TextField`:llä, johon syötetään sankarin nimi, `ComboBox`:lla supervoiman valitsemiseksi, ja `Button`:illa rekisteröinnin lähettämiseksi.

`Hero`-luokka määrittelee tietomallin validointirajoitteilla sankarin nimen ja voiman osalta. Syötteiden tulee olla päteviä ja noudattaa määriteltyjä kriteerejä, kuten pituus ja malli.

Sovellus käyttää `BindingContext`:ia sitomaan käyttöliittymäkomponentit `Hero`-objektin ominaisuuksiin. Kun käyttäjä napsauttaa lähetyspainiketta, sovellus kirjoittaa lomakkeeseen syötetyt tiedot takaisin `Hero`-bean:iin, jos ne ovat päteviä.

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

    // heijasta bean-tiedot lomakkeeseen
    context.read(bean);

    submit.onClick(e -> {
      // kirjoita lomakedata takaisin beaniin
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

## Avainominaisuudet {#key-features}

- **Kaksisuuntainen sitominen:** Tukee kaksisuuntaista tiedon sitomista, mikä mahdollistaa tietomallissa tapahtuvien muutosten päivittävän käyttöliittymää ja käyttöliittymän käyttäjävuorovaikutusten päivittävän tietomallia.

- **Validointituki:** Integroi kattavat validointimekanismit, joita voit mukauttaa ja laajentaa. Kehittäjät voivat toteuttaa omia validointisääntöjään tai käyttää olemassa olevia validointikehyksiä, kuten Jakarta Validointia, varmistaakseen tietojen eheyden ennen mallin päivitystä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia käyttöliittymäkomponentteja, tiedon muunnoksia ja monimutkaisempia validointitapauksia.

- **Annotaatio-ohjattu konfigurointi:** Käyttää annotaatioita vähentääkseen koodin määrää, mikä tekee käyttöliittymäkomponenttien ja tietomallien väliset sidokset deklaratiivisiksi ja helposti hallittaviksi.

# Aihealueet

<DocCardList className="topics-section" />
