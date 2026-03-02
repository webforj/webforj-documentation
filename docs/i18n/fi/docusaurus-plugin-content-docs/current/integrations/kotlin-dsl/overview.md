---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2c5f0dc99b29342a5ae0f1f4774d3a36
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ tarjoaa [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, eli DSL, joka mahdollistaa käyttöliittymien rakentamisen tiiviillä, tyyppiturvallisella syntaksilla. Imperatiivisen Java-koodin sijasta kirjoitat deklaratiivista koodia, joka näyttää kuvaukselta käyttöliittymäsi rakenteesta.

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

DSL hyödyntää Kotlinin laajennustoimintoja, vastaanottajalla varustettuja lambda-lausumia ja oletusparametreja luodakseen luonnollisen rakentajasynnin. Komponentit pesiytyvät toisiinsa, konfigurointi tapahtuu lohkoissa, ja kääntäjä havaitsee rakenteelliset virheet ennen suoritusta.

## Setup {#setup}

Erillistä Kotlin-asennusta ei tarvita. Maven hoitaa käännön Kotlin Maven -liitännäisen kautta, joten mikä tahansa projekti, joka jo rakentaa Mavenilla, voi lisätä Kotlin-tuen pelkästään riippuvuus- ja liitännäiskonfiguraatiolla.

### Riippuvuudet {#dependencies}

Lisää webforJ Kotlin DSL -moduuli ja Kotlinin standardikirjasto `pom.xml`:ään:

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

Jos aiot kirjoittaa testejä Kotlinilla, lisää myös Kotlin testiriippuvuus. Se integroituu JUnitin kanssa:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven -liitännäinen {#kotlin-maven-plugin}

Lisää Kotlin Maven -liitännäinen sekä Kotlin- että Java-lähdekoodin kääntämiseksi. Alla oleva `sourceDirs`-konfiguraatio mahdollistaa Kotlin- ja Java-tiedostojen olemassaolon samassa projektissa:

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

Näiden lisäysten kanssa `mvn compile` kääntää Kotlin-lähdekoodit yhdessä Javasta. Kotlin-tiedostot voivat olla joko `src/main/kotlin`- tai `src/main/java`-kansiossa, ja liitännäinen käsittelee molemmat.

:::tip[Java-yhteensopivuus]
Kotlin kääntyy JVM-bittikoodiksi, joten se toimii olemassa olevien Java-koodien rinnalla. Voit käyttää DSL:n avulla rakennettuja Kotlin-yhdistelmiä Java-luokista, pesiyttää standardi Java -komponentteja DSL-lohkoihin käyttäen `add()`, ja sekoittaa Kotlin- ja Java-tiedostoja samassa projektissa.
:::

## Aiheita {#topics}

Seuraavat aiheet käsittelevät DSL:n käyttöä sekä sen laajentamista kaikkiin mukautettuihin komponentteihin tai yhdistelmiin, jotka luot.

<DocCardList className="topics-section" />

## Kotlin Java-kehittäjille {#kotlin-for-java-developers}

<details>
<summary>Uusi Kotlinin parissa? Tässä ovat joitakin keskeisiä kieliominaisuuksia, joihin DSL perustuu.</summary>

### Null-turvallisuus {#null-safety}

Kotlin erottelee nollattavissa ja ei-nollattavissa tyypeissä käännösaikana:

```kotlin
// Java - mikä tahansa viite voi olla null
String name = null;

// Kotlin - eksplisiittinen null-kyky
var name: String? = null        // Nullattavissa, voi olla null
var safeName: String = "arvo"   // Ei-null, kääntäjä valvoo tätä

// Turvallinen kutsuoperaattori - palauttaa null, jos name on null
println(name?.length)

// Elvis-operaattori - tarjoaa oletusarvon, kun null
println(name ?: "oletusarvo")
```

### Laajennustoiminnot {#extension-functions}

Kotlinissa voit lisätä metodeja olemassa oleviin luokkiin ilman perintää:

```kotlin
// Java-lähestymistapa - staattinen utiliteettiklussi
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hello");

// Kotlin-lähestymistapa - laajennustoiminto
fun String.addExclamation(): String = this + "!"
val result = "Hello".addExclamation()  // Lukee kuin metodikutsu
```

DSL käyttää laajennustoimintoja rakenteiden lisäämiseen komponentteihin.

### Lambdat ja jälkimmäinen lambda-syntaksi {#lambdas-and-trailing-lambda-syntax}

Kotlinin lambdat ovat tiiviimpiä kuin Javassa, ja kun lambda on viimeinen parametri, se voi olla ulkopuolella sulkujen:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klikattu"));

// Kotlin - lambda viimeisenä parametrina menee sulkujen ulkopuolelle
button.onClick { println("Klikattu") }

// Ilman eksplisiittistä parametria
button.onClick { event -> println("Klikattu: $event") }
```

Tämä jälkimmäinen lambda-syntaksi mahdollistaa DSL-lohkot.

### Oletusparametrit {#default-parameters}

Kotlin-funktioilla voi olla oletusarvot parametreille, mikä vähentää ylikuormitettujen metodien tarvetta:

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
