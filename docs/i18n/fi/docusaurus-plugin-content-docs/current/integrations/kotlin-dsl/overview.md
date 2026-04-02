---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 36366a03c9784b451033e5161bdc7359
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='25.12' />
<DocChip chip='experimental' />

webforJ tarjoaa [Kotlin](https://kotlinlang.org/) *Domäänikohtaisen kielin*, eli DSL:n, joka mahdollistaa käyttöliittymien rakentamisen tiiviillä ja tyypitetyllä syntaksilla. Imperatiivisen Java-koodin sijaan kirjoitat deklaratiivista koodia, joka lukee kuin kuvaus käyttöliittymän rakenteesta.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Nimi");
name.setPlaceholder("Nimesi");
layout.add(name);

Button submit = new Button("Lähetä", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Nimi", placeholder = "Nimesi")
  button("Lähetä", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

DSL hyödyntää Kotlinin laajennusfunktioita, vastaanottajalla varustettuja lambdoja ja oletusparametreja luonnollisen rakennussyntaksin luomiseksi. Komponentit upotetaan toisiinsa, konfigurointi tapahtuu lohkoissa, ja kääntäjä tunnistaa rakenteelliset virheet ennen ajonaikaa.

## Setup {#setup}

:::warning experimental feature
Tämä ominaisuus on edelleen aktiivisessa kehityksessä.
API saattaa muuttua tulevissa versioissa, mukaan lukien mahdollisia taaksepäin yhteensopimattomia muutoksia.

Olet tervetullut kokeilemaan sitä ja jakamaan palautetta. Palaute auttaa muokkaamaan lopullista suunnittelua.
:::

Erillistä Kotlin-asennusta ei tarvita. Maven hoitaa käännön Kotlin Maven -laajennuksen kautta, joten mikä tahansa projekti, joka jo kääntää Mavenilla, voi lisätä Kotlin-tuen riippuvuuden ja laajennuksen konfiguroinnin avulla.

:::tip Quick start
Jos haluat saada webforJ-projektin, joka käyttää Kotlinia, käynnistettäväksi kaikkine tarvittavine konfiguraatioineen suoraan, katso [tätä osiota webforJ Kotlin -käynnistyksestä](#kotlin-starter-project).
:::

### Dependencies {#dependencies}

Lisää webforJ Kotlin DSL -moduuli ja Kotlinin standardikirjasto `pom.xml`-tiedostoon:

```xml
<dependency>
  <groupId>com.webforj.kotlin</groupId>
  <artifactId>webforj-kotlin</artifactId>
  <version>${webforj.version}</version>
</dependency>

<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-stdlib-jdk8</artifactId>
  <version>${kotlin.version}</version>
</dependency>
```

Jos aiot kirjoittaa testejä Kotlinilla, lisää myös Kotlin-testiriippuvuus. Se integroituu JUnitin kanssa:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven plugin {#kotlin-maven-plugin}

Lisää Kotlin Maven -laajennus, jotta voit kääntää sekä Kotlin- että Java-lähdekoodisi. Alla oleva `sourceDirs`-konfiguraatio sallii Kotlin- ja Java-tiedostojen esiintyä samassa projektissa:

```xml
<plugin>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-maven-plugin</artifactId>
  <version>${kotlin.version}</version>
  <executions>
    <execution>
      <id>compile</id>
      <phase>compile</phase>
      <goals>
        <goal>compile</goal>
      </goals>
      <configuration>
        <sourceDirs>
          <sourceDir>src/main/java</sourceDir>
          <sourceDir>target/generated-sources/annotations</sourceDir>
          <sourceDir>src/main/kotlin</sourceDir>
        </sourceDirs>
      </configuration>
    </execution>
    <execution>
      <id>test-compile</id>
      <phase>test-compile</phase>
      <goals>
        <goal>test-compile</goal>
      </goals>
      <configuration>
        <sourceDirs>
          <sourceDir>src/test/java</sourceDir>
          <sourceDir>target/generated-test-sources/test-annotations</sourceDir>
          <sourceDir>src/test/kotlin</sourceDir>
        </sourceDirs>
      </configuration>
    </execution>
  </executions>
  <configuration>
    <jvmTarget>${maven.compiler.target}</jvmTarget>
  </configuration>
</plugin>
```

Näiden lisäysten myötä `mvn compile` kääntää Kotlin-lähdekoodit yhdessä Javankaltaisten kanssa. Kotlin-tiedostot voivat olla `src/main/kotlin` tai `src/main/java`, ja laajennus hoitaa molemmat.

:::tip[Java interoperability]
Kotlin käännetään JVM-bittikoodiksi, joten se toimii yhdessä olemassa olevan Java-koodin kanssa. Voit käyttää DSL:llä rakennettuja Kotlin-komposiitteja Java-luokista, upottaa standardi Java-komponentteja DSL-lohkoihin `add()`-menetelmällä ja sekoittaa Kotlin- ja Java-tiedostoja samassa projektissa.
:::

### Kotlin starter project {#kotlin-starter-project}

Jos haluat ohittaa manuaalisen asennuksen, [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) -rekisteri tarjoaa valmiiksi toimivan projektin, jossa kaikki riippuvuudet ja laajennuskokoonpanot on jo paikallaan. Klonaa se ja ala rakentaa DSL:ää heti.

## Topics {#topics}

Seuraavat aiheet käsittelevät DSL:n käyttöä sekä sen laajentamista kaikilla mukautetuilla komponenteilla tai komposiiteilla, joita luot.

<DocCardList className="topics-section" />
