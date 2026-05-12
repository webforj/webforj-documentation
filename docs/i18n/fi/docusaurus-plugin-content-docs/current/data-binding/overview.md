---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: b05f45d2f2725defb3d5fba7cb0fb622
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

 webforJ sisältää tietosidontatoiminnon, joka integroi käyttöliittymäkomponentit taustakannan datamalleihin Java-sovelluksissa. Tämä toiminto ylittää rajan käyttöliittymän ja datapinnan välillä niin, että käyttöliittymän muutokset heijastuvat datamalliin ja päinvastoin, vähentäen tapahtumien käsittelyn ja datan synkronoinnin monimutkaisuutta.

<AISkillTip skill="webforj-building-forms" />

## Konsepti {#concept}

Seuraava esittely näyttää yksinkertaisen webforJ-sovelluksen supermiehen rekisteröimiseksi käyttäen webforJ:n tietosidontatoimintoa. Sovellus koostuu kahdesta pääosasta: `HeroRegistration.java` ja `Hero.java`.

`HeroRegistration.java`-tiedostossa koodi konfiguroi käyttäjäliittymän, jossa on `TextField` sankarin nimen syöttämistä varten, `ComboBox` supervoiman valitsemiseksi ja `Button` rekisteröinnin lähettämiseksi.

`Hero`-luokka määrittelee datamallin validointirajoitteilla sankarin nimen ja voiman osalta. Syötteiden on oltava voimassa ja noudatettava määriteltyjä kriteerejä, kuten pituus ja malli.

Sovellus käyttää `BindingContext`-luokkaa sitomaan käyttöliittymäkomponentteja `Hero`-objektin ominaisuuksiin. Kun käyttäjä napsauttaa lähetyspainiketta, sovellus kirjoittaa lomakkeeseen syötetyt tiedot takaisin `Hero`-beanille, jos ne ovat voimassa.

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
    power.insert("Lentää", "Näkymätön", "LaserNäkö", "Nopeus", "Teleportaatio");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("Superman", "Lentää");

    // heijasta bean-tiedot lomakkeeseen
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
  @Pattern(regexp = "Lentää|Näkymätön|LaserNäkö|Nopeus|Teleportaatio", message = "Virheellinen voima")
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

- **Kaksisuuntainen sidonta:** Tukee kaksisuuntaista datan sidontaa, jolloin datamallin muutokset päivittävät käyttöliittymää ja käyttöliittymän käyttäjävuorovaikutukset päivittävät datamallia.

- **Validointituki:** Integroi kattavia validointimekanismeja, joita voit räätälöidä ja laajentaa. Kehittäjät voivat toteuttaa omia validointisääntöjään tai käyttää olemassa olevia validointikehyksiä, kuten Jakarta Validointia, vahvistamaan datan eheyttä ennen mallin päivittämistä.

- **Laajennettavuus:** Voidaan helposti laajentaa tukemaan erilaisia käyttöliittymäkomponentteja, datamuunnoksia ja monimutkaisia validointitilanteita.

- **Annotaatioihin perustuva konfigurointi:** Käyttää annotaatioita minimointikoodin määrää, mikä tekee käyttöliittymäkomponenttien ja datamallien välisistä sidoksista deklaratiivisia ja helppoja hallita.

## Aiheet {#topics}

<DocCardList className="topics-section" />
