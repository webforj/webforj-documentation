---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 4198ef6392f249bd21d0395c55b5817d
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

webforJ tarjoaa [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, eli DSL:n, jonka avulla voit rakentaa käyttöliittymiä tiiviillä, tyyppi-turvallisella syntaksilla. Sen sijaan, että kirjoitat imperatiivista Java-koodia, kirjoitat deklaraatiokoodia, joka lukee kuin kuvaus käyttöliittymäsi rakenteesta.

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

DSL hyödyntää Kotlinin laajennusfunktioita, vastaanottajalla varustettuja lambdaja ja oletusparametreja luodakseen luonnollisen rakennussyntaksin. Komponentit pesiytyvät toistensa sisälle, konfigurointi tapahtuu lohkossa, ja kääntäjä havaitsee rakenteelliset virheet ennen suorittamista.

## Setup {#setup}

<ExperimentalWarning />

Erillistä Kotlin-asennusta ei tarvita. Maven huolehtii käännöksestä Kotlin Maven -lisäosan kautta, joten mikä tahansa projekti, joka jo kääntää Mavenilla, voi lisätä Kotlin-tuen riippuvuus- ja lisäosasäätöjen avulla.

:::tip Nopea aloitus
Jos haluat käynnistää webforJ-projektin käyttäen Kotlinia kaikilla tarvittavilla kokoonpanoilla valmiiksi, katso [tämä osio webforJ Kotlin -aloitusprojektin käytöstä](#kotlin-starter-project).
:::

### Riippuvuudet {#dependencies}

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

Jos aiot kirjoittaa testejä Kotlinilla, lisää myös Kotlin-testiriippuvuus. Se integroituu JUnit:iin:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven -lisäosa {#kotlin-maven-plugin}

Lisää Kotlin Maven -lisäosa kääntämään sekä Kotlin- että Java-lähdekoodisi. Alla oleva `sourceDirs`-konfiguraatio sallii Kotlin- ja Java-tiedostojen elää samassa projektissa:

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

Näiden lisäysten myötä `mvn compile` kääntää Kotlin-lähteet yhdessä Java-lähteiden kanssa. Kotlin-tiedostot voivat olla `src/main/kotlin` tai `src/main/java` -kansiossa, ja lisäosa huolehtii molemmista.

:::tip[Java-yhteensopivuus]
Kotlin kääntyy JVM-tavukoodiksi, joten se toimii yhdessä olemassa olevan Java-koodin kanssa. Voit käyttää DSL- rakennettuja Kotlin-kokoonpanoja Java-luokista, pesiyttää standardeja Java-komponentteja DSL-lohkoihin `add()`-metodilla, ja sekoittaa Kotlin- ja Java-tiedostoja samassa projektissa.
:::

### Kotlin aloitusprojekti {#kotlin-starter-project}

Jos haluat ohittaa manuaalisen asetuksen, [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) -repo tarjoaa valmiin projektin kaikilla riippuvuuksilla ja lisäosasäätöillä jo paikallaan. Kloonaa se ja ala rakentaa DSL:n kanssa heti.

## Aiheita {#topics}

Seuraavat aiheet käsittelevät DSL:n käyttöä sekä sen laajentamista kaikkiin muokattuihin komponentteihin tai yhdistelmiin, joita luot.

<DocCardList className="topics-section" />
