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

 webforJ sisältää tietosidontasominaisuuden, joka integroi UI-komponentit saumattomasti taustadatan malleihin Java-sovelluksissa. Tämä ominaisuus ylittää kuilun UI:n ja datakerroksen välillä, varmistaen, että muutokset UI:ssa heijastuvat datamalliin ja päinvastoin. Tämän seurauksena se parantaa käyttäjäkokemusta ja vähentää tapahtumankäsittelyn ja datan synkronoinnin monimutkaisuutta.

## Konsepti {#concept}

Seuraava esittely esittelee yksinkertaisen webforJ-sovelluksen supersankareiden rekisteröimiseksi webforJ:n tietosidontatoiminnolla. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`. 

`HeroRegistration.java`:ssa koodi määrittää käyttöliittymän, jossa on `TextField` sankarin nimen syöttämiseen, `ComboBox` supervoiman valitsemiseen ja `Button` rekisteröinnin jättämiseen.

`Hero`-luokka määrittää datamallin, jossa on vahvistusrajoituksia sankarin nimen ja voiman osalta, varmistaen, että syötteet ovat voimassa ja noudattavat määriteltyjä kriteerejä, kuten pituus ja malli.

Sovellus hyödyntää `BindingContext`-ominaisuutta sidomaan UI-komponentteja `Hero` objektin ominaisuuksiin. Kun käyttäjä napsauttaa lähetyspainiketta, sovellus kirjoittaa lomakkeeseen syötetyt tiedot takaisin `Hero`-beanille, jos ne ovat voimassa.

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
    power.insert("Lentäminen", "Näkymättömyys", "Laservisio", "Nopeus", "Siirtyminen");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Supermies", "Lentäminen");

    // heijasta bean-data lomakkeeseen
    context.read(bean);

    submit.onClick(e -> {
      // kirjoita lomake-data takaisin beanille
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
  @Pattern(regexp = "Lentäminen|Näkymättömyys|Laservisio|Nopeus|Siirtyminen", message = "Virheellinen voima")
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

- **Kaksisuuntainen sidos:** Tukee kaksisuuntaista tietosidontaa, jolloin muutokset datamallissa päivittävät käyttöliittymän ja käyttäjän toiminnot käyttöliittymässä päivittävät datamallia.

- **Vahvistustuki:** Integroi kattavat vahvistusmekanismit, joita voit mukauttaa ja laajentaa. Kehittäjät voivat toteuttaa omia vahvistussääntöjä tai käyttää olemassa olevia vahvistuskehyksiä, kuten Jakarta Validation, varmistaakseen datan eheyden ennen mallin päivittämistä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia UI-komponentteja, datamuunnoksia ja monimutkaisempia vahvistusskenaarioita.

- **Ominaisuusajurella määrittely:** Käyttää annotaatioita vähentääkseen toistorakennetta, jolloin sidokset käyttöliittymäkomponenttien ja datamallien välillä ovat deklaratiivisia ja helppokäyttöisiä.

# Aiheet

<DocCardList className="topics-section" />
