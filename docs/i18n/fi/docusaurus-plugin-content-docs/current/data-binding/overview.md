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

webforJ sisältää tiedonsidontatoiminnon, joka integroi UI-komponentit saumattomasti taustadatamalleihin Java-sovelluksissa. Tämä ominaisuus ylittää rajan UI:n ja datakerroksen välillä, varmistaen, että muutokset UI:ssa heijastuvat datamalliin ja päinvastoin. Tuloksena paranee käyttäjäkokemus ja vähenee tapahtumankäsittelyn ja datan synkronoinnin monimutkaisuus.

## Käsite {#concept}

Seuraava esittely näyttää yksinkertaisen webforJ-sovelluksen, joka rekisteröi supersankareita käyttäen webforJ:n tiedonsidontatoimintoa. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`.

Tiedostossa `HeroRegistration.java` koodi konfiguroi käyttöliittymän `TextField`:llä sankarin nimen syöttämistä varten, `ComboBox`:illa supervoiman valitsemista varten ja `Button`:illa rekisteröinnin lähettämistä varten.

`Hero`-luokka määrittelee datamallin validointirajoituksilla sankarin nimen ja voiman osalta, varmistaen, että syötteet ovat voimassa ja noudattavat määrättyjä kriteerejä, kuten pituus ja malli.

Sovellus käyttää `BindingContext`-luokkaa sitomaan UI-komponentit `Hero`-olion ominaisuuksiin. Kun käyttäjä napsauttaa lähetyspainiketta, sovellus kirjoittaa lomakkeessa syötetyt tiedot taaksepäin `Hero`-bean:iin, jos ne ovat voimassa.

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("Tekstikenttä");
  private ComboBox power = new ComboBox("Voima");
  private Button submit = new Button("Rekisteröi hakemus");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("Lentää", "Näkymätön", "Lasersilmä", "Nopeus", "Siirtyminen");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Lentää");

    // heijasta bean-data lomakkeeseen
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
  @Pattern(regexp = "Lentää|Näkymätön|Lasersilmä|Nopeus|Siirtyminen", message = "Virheellinen voima")
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

- **Kaksisuuntainen sidonta:** Tukee kaksisuuntaista tiedonsidontaa, jolloin muutokset datamallissa päivittävät UI:ta ja käyttäjäinteraktiot UI:ssa päivittävät datamallia.

- **Validointituki:** Integroi kattavat validointimekanismit, joita voit mukauttaa ja laajentaa. Kehittäjät voivat toteuttaa omia validointisääntöjään tai käyttää olemassa olevia validointikehyksiä, kuten Jakarta Validation, varmistaakseen datan eheyden ennen mallin päivittämistä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia UI-komponentteja, datamuunnoksia ja monimutkaisia validointiskenaarioita.

- **Annotaatio-ohjattu konfigurointi:** Hyödyntää annotaatioita minimoidakseen turhakoodin, jolloin sidokset UI-komponenttien ja datamallien välillä ovat deklaratiivisia ja helppoja hallita.

# Aiheet

<DocCardList className="topics-section" />
