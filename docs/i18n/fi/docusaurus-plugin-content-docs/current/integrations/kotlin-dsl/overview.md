---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Build webforJ UIs declaratively with the Kotlin DSL, a preview feature, using
  type-safe builders, lambdas with receivers, and nested blocks.
_i18n_hash: 5beb40faf3cca20567062bc93ef15e2b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# Kotlin DSL <DocChip chip='since' label='25.12' /> <DocChip chip='experimental' />
<!-- vale on -->

webforJ tarjoaa [Kotlin](https://kotlinlang.org/)-kielen *toimialakohtaisen kielen* eli DSL:n, jonka avulla voit rakentaa käyttöliittymiä tiiviillä ja tyyppiturvallisella syntaksilla. Imperatiivisen Java-koodin sijaan kirjoitat deklaratiivista koodia, joka kuvailee käyttöliittymän rakennetta.

<!-- INTRO_END -->

```java title="Java"
FlexLayout layout = new FlexLayout();
layout.setDirection(FlexDirection.COLUMN);
layout.setSpacing("10px");

TextField name = new TextField();
name.setLabel("Name");
name.setPlaceholder("Your name");
layout.add(name);

Button submit = new Button("Submit", ButtonTheme.PRIMARY);
submit.onClick(e -> handleSubmit());
layout.add(submit);
```

```kotlin title="Kotlin DSL"
flexLayout {
  direction = FlexDirection.COLUMN
  styles["gap"] = "10px"

  textField("Name", placeholder = "Your name")
  button("Submit", ButtonTheme.PRIMARY) {
    onClick { handleSubmit() }
  }
}
```

DSL hyödyntää Kotlinin laajennusfunktioita, vastaanottajan sisältäviä lambdoja ja oletusparametreja luonnollisen rakenninsyntaksin luomiseen. Komponentit sisäkkäistyvät toistensa sisään, määritykset tehdään lohkoissa, ja kääntäjä havaitsee rakenteelliset virheet jo ennen suoritusta.

## Käyttöönotto {#setup}

<ExperimentalWarning />

Erillistä Kotlin-asennusta ei tarvita. Maven hoitaa kääntämisen Kotlin Maven -liitännäisen avulla, joten Kotlin-tuen voi lisätä riippuvuus- ja liitännäismäärityksillä mihin tahansa projektiin, joka käännetään jo Mavenilla.

:::tip Pika-aloitus
Katso [tästä osiosta, miten webforJ Kotlin Starter otetaan käyttöön](#kotlin-starter-project), jos haluat saada webforJ-projektin Kotlinilla käyttöön kaikkine tarvittavine oletusmäärityksineen.
:::

### Riippuvuudet {#dependencies}

Lisää webforJ Kotlin DSL -moduuli ja Kotlinin standardikirjasto tiedostoon `pom.xml`:

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

Jos aiot kirjoittaa testejä Kotlinilla, lisää myös Kotlinin testiriippuvuus. Se integroituu JUnitin kanssa:

```xml
<dependency>
  <groupId>org.jetbrains.kotlin</groupId>
  <artifactId>kotlin-test</artifactId>
  <version>${kotlin.version}</version>
  <scope>test</scope>
</dependency>
```

### Kotlin Maven -liitännäinen {#kotlin-maven-plugin}

Lisää Kotlin Maven -liitännäinen Kotlin- ja Java-lähdekoodin kääntämistä varten. Alla oleva `sourceDirs`-määritys mahdollistaa Kotlin- ja Java-tiedostojen käytön samassa projektissa:

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

Näiden lisäysten jälkeen `mvn compile` kääntää Kotlin-lähdekoodin Java-koodin rinnalla. Kotlin-tiedostot voi sijoittaa hakemistoon `src/main/kotlin` tai `src/main/java`, ja liitännäinen käsittelee molemmat.

:::tip[Java-yhteentoimivuus]
Kotlin kääntyy JVM-tavukoodiksi, joten se toimii olemassa olevan Java-koodin rinnalla. Voit käyttää DSL:llä rakennettuja Kotlin-koostekomponentteja Java-luokissa, sisällyttää tavallisia Java-komponentteja DSL-lohkoihin `add()`-metodilla sekä yhdistää Kotlin- ja Java-tiedostoja samaan projektiin.
:::

### Kotlin Starter -projekti {#kotlin-starter-project}

Jos haluat ohittaa manuaalisen käyttöönoton, [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) -repositorio tarjoaa heti käyttövalmiin projektin, jossa kaikki riippuvuudet ja liitännäismääritykset ovat valmiina. Kloonaa repositorio ja aloita DSL:n käyttö saman tien.

## Aiheet {#topics}

Seuraavissa aiheissa käsitellään DSL:n käyttöä sekä sen laajentamista omilla komponenteilla ja koostekomponenteilla.

<DocCardList className="topics-section" />
