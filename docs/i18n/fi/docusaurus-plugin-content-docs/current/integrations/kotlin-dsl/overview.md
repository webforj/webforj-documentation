---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 21ef4feee90e5c4f6827a48ce1755d0b
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

webforJ tarjoaa [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, eli DSL:n, jonka avulla voit rakentaa käyttöliittymiä tiiviillä, tyyppiturvallisella syntaksilla. Imperatiivisen Java-koodin sijaan kirjoitat deklaratiivista koodia, joka luetaan kuin kuvaus käyttöliittymäsi rakenteesta.

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

DSL hyödyntää Kotlinin laajennusfunktioita, vastaanottavia lambdoja ja oletusparametreja luonnollisen rakentajasyntaksin luomiseksi. Komponentit pesiytyvät toisiinsa, konfigurointi tapahtuu lohkoissa, ja kääntäjä havaitsee rakenteelliset virheet ennen ajonaikaa.

## Setup {#setup}

:::warning experimental feature
Tämä ominaisuus on edelleen aktiivisessa kehityksessä.
API saattaa muuttua tulevissa versioissa, mukaan lukien mahdolliset ei-taaksepäin yhteensopivat muutokset.

Olet tervetullut kokeilemaan sitä ja jakamaan palautetta. Palautteesi auttaa muokkaamaan lopullista suunnittelua.
:::

Erillistä Kotlin-asennusta ei tarvita. Maven käsittelee kääntämisen Kotlin Maven -laajennuksen kautta, joten mikä tahansa projekti, joka jo rakennetaan Mavenilla, voi lisätä Kotlin-tuen riippuvuuden ja laajennuksen konfiguroinnilla.

:::tip Quick start
Saat webforJ-projektin, joka käyttää Kotlinia ja on käyttövalmis kaikilla tarvittavilla konfiguraatioilla, katso [tämä osio webforJ Kotlin -aloituksesta](#kotlin-starter-project).
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

Lisää Kotlin Maven -laajennus kääntääksesi sekä Kotlin- että Java-lähteet. `sourceDirs`-konfiguraatio mahdollistaa Kotlin- ja Java-tiedostojen olemassaolon samassa projektissa:

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

Näiden lisäysten myötä `mvn compile` kääntää Kotlin-lähteet yhdessä Java-lähteiden kanssa. Kotlin-tiedostot voivat olla `src/main/kotlin`- tai `src/main/java`-kansiossa, ja laajennus käsittelee molemmat.

:::tip[Java-interoperabiliteetti]
Kotlin kääntyy JVM-bittikoodiksi, joten se toimii rinnakkain olemassa olevan Java-koodin kanssa. Voit käyttää DSL:n avulla rakennettuja Kotlin-kokonaisuuksia Java-luokista, pesiyttää standardeja Java-komponentteja DSL-lohkoihin `add()`-kutsulla ja sekoittaa Kotlin- ja Java-tiedostoja samassa projektissa.
:::

### Kotlin starter project {#kotlin-starter-project}

Jos haluat ohittaa manuaalisen asetuksen, [webforJ Kotlin Starter](https://github.com/webforj/webforj-kotlin-starter) -varasto tarjoaa valmiin projektin, jossa on kaikki riippuvuudet ja laajennuskonfigurointi jo valmiina. Klonaa se ja aloita DSL:n käyttö heti.

## Topics {#topics}

Seuraavat aiheet kattavat DSL:n käytön sekä sen laajentamisen mihin tahansa mukautettuihin komponentteihin tai kokonaisuuksiin, jotka luot.

<DocCardList className="topics-section" />

## Kotlin Java-kehittäjille {#kotlin-for-java-developers}

<details>
<summary>Uusi Kotlinilla? Tässä on joitakin keskeisiä kieliominaisuuksia, joita DSL hyödyntää.</summary>

### Null-turvallisuus {#null-safety}

Kotlin erottelee nullable- ja non-nullable-tyypit käännösaikana:

```kotlin
// Java - mikä tahansa viittaus voi olla null
String name = null;

// Kotlin - eksplisiittinen nullability
var name: String? = null        // Nullable, voi olla null
var safeName: String = "value"  // Non-null, kääntäjä valvoo tätä

// Safe call operator - palauttaa null, jos name on null
println(name?.length)

// Elvis operator - tarjoaa oletusarvon, kun null
println(name ?: "oletus")
```

### Laajennusfunktiot {#extension-functions}

Kotlinin avulla voit lisätä menetelmiä olemassa oleviin luokkiin ilman perintää:

```kotlin
// Java-lähestymistapa - staattinen utilitaariluokka
public class StringUtils {
  public static String addExclamation(String input) {
    return input + "!";
  }
}
String result = StringUtils.addExclamation("Hello");

// Kotlin-lähestymistapa - laajennusfunktio
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // Luettavissa kuin menetelmäkutsu
```

DSL käyttää laajennusfunktioita lisätäkseen rakentajamenetelmiä komponentteihin.

### Lambdat ja jäljellä oleva lambda-syntaksi {#lambdas-and-trailing-lambda-syntax}

Kotlin-lambdat ovat tiiviimpiä kuin Java-lambdat, ja kun lambda on viimeinen parametri, se voi mennä sulkujen ulkopuolelle:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klikattu"));

// Kotlin - lambda viimeisenä parametrina menee sulkujen ulkopuolelle
button.onClick { println("Klikattu") }

// Eksplisiittinen parametri
button.onClick { event -> println("Klikattu: $event") }
```

Tämä jäljellä oleva lambda-syntaksi mahdollistaa DSL-lohkojen käytön.

### Oletusparametrit {#default-parameters}

Kotlinin funktioilla voi olla oletusarvoisia parametreja, mikä vähentää ylikuormitettujen menetelmien tarvetta:

```kotlin
// Java - useita konstruktoreita tarvitaan
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - yksi funktio oletusarvoilla
fun button(
  text: String = "",
  theme: ButtonTheme = ButtonTheme.DEFAULT,
  block: Button.() -> Unit = {}
): Button
```

</details>
