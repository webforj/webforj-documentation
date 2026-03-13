---
title: Kotlin DSL
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b27e06c94bdd94dd90f7411523e442f5
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

webforJ tarjoaa [Kotlin](https://kotlinlang.org/) *Domain Specific Language*, eli DSL, jonka avulla voit rakentaa käyttöliittymiä tiiviillä, tyyppiturvatuilla syntaksilla. Imperatiivisen Java-koodin sijaan kirjoitat deklaratiivista koodia, joka lukee kuin kuvaus käyttöliittymäsi rakenteesta.

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

DSL hyödyntää Kotalin laajennusfunktioita, vastaanottajalla varustettuja lambdoja ja oletusparametreja luodakseen luonnollisen rakennussyntaksin. Komponentit pesiytyvät toisiinsa, konfigurointi tapahtuu lohkoissa, ja kääntäjä tunnistaa rakenteelliset virheet ennen ajonaikaa.

## Setup {#setup}

:::warning experimental feature
Tämä ominaisuus on edelleen aktiivisessa kehityksessä.
Rajapinta voi muuttua tulevissa versioissa, mukaan lukien mahdolliset taaksepäin yhteensopimattomat muutokset.

Olet tervetullut kokeilemaan sitä ja jakamaan palautetta. Panoksesi auttaa muokkaamaan lopullista suunnittelua.
:::

Eri Kotlin-asennusta ei vaadita. Maven hoitaa käännöksen Kotlin Maven -laajennuksen kautta, joten mikä tahansa projekti, joka jo rakentuu Mavenilla, voi lisätä Kotlin-tuen pelkästään riippuvuus- ja laajennuskonfiguraation avulla.

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

Jos suunnittelet testien kirjoittamista Kotlinilla, lisää myös Kotlin-testiriippuvuus. Se integroituu JUnitiin:

```xml
<dependency>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-test</artifactId>
    <version>${kotlin.version}</version>
    <scope>test</scope>
</dependency>
```

### Kotlin Maven -laajennus {#kotlin-maven-plugin}

Lisää Kotlin Maven -laajennus kääntämään sekä Kotlin- että Java-lähteesi. Alla oleva `sourceDirs`-konfigurointi mahdollistaa Kotlin- ja Java-tiedostojen rinnakkaisen olemassaolon samassa projektissa:

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

Näiden lisäysten avulla `mvn compile` kääntää Kotlin-lähteet yhdessä Java-lähteiden kanssa. Kotlin-tiedostot voivat sijaita `src/main/kotlin` tai `src/main/java`-kansiossa, ja laajennus huolehtii molemmista.

:::tip[Java-yhteensopivuus]
Kotlin kääntyy JVM-bittikoodiksi, joten se toimii olemassa olevan Java-koodin rinnalla. Voit käyttää DSL:llä rakennettuja Kotlin-yhdistelmiä Java-luokista, pesiyttää standardi Java -komponentteja DSL-lohkojen sisään `add()`-metodilla ja sekoittaa Kotlin- ja Java-tiedostoja samassa projektissa.
:::

## Aiheet {#topics}

Seuraavat aiheet käsittelevät DSL:n käyttöä sekä sen laajentamista kaikkiin mukautettuihin komponentteihin tai yhdistelmiin, joita luot.

<DocCardList className="topics-section" />

## Kotlin Java-kehittäjille {#kotlin-for-java-developers}

<details>
<summary>Uusi Kotlinille? Tässä joitain avainkieliominaisuuksia, joihin DSL perustuu.</summary>

### Null-turvallisuus {#null-safety}

Kotlin erottelee null-referenssit ja ei-null-referenssit käännösaikana:

```kotlin
// Java - mikä tahansa viittaus voi olla null
String name = null;

// Kotlin - eksplisiittinen nullability
var name: String? = null        // Nullable, voi olla null
var safeName: String = "arvo"  // Ei-null, kääntäjä valvoo tätä

// Turvallinen kutsuoperaattori - palauttaa null, jos name on null
println(name?.length)

// Elvis-operaattori - tarjoaa oletusarvon, kun null
println(name ?: "oletus")
```

### Laajennusfunktiot {#extension-functions}

Kotlin sallii metodien lisäämisen olemassa oleviin luokkiin ilman perintöä:

```kotlin
// Java-lähestymistapa - staattinen apuluokka
public class StringUtils {
    public static String addExclamation(String input) {
        return input + "!";
    }
}
String result = StringUtils.addExclamation("Hei");

// Kotlin-lähestymistapa - laajennusfunktio
fun String.addExclamation(): String = this + "!"
val result = "Hei".addExclamation()  // Lukee kuin metodikutsu
```

DSL käyttää laajennusfunktioita lisätäkseen rakennusmetodeja komponentteihin.

### Lamdat ja jälkimmäinen lambda-syntaksi {#lambdas-and-trailing-lambda-syntax}

Kotlin-lamdat ovat tiiviimpiä kuin Java:n, ja kun lambda on viimeinen parametri, se voi mennä ulos sulkeista:

```kotlin
// Java
button.addClickListener(e -> System.out.println("Klikattu"));

// Kotlin - lambda viimeisenä parametrina menee sulkeiden ulkopuolelle
button.onClick { println("Klikattu") }

// Eksplisiittisellä parametrilla
button.onClick { event -> println("Klikattu: $event") }
```

Tämä jälkimmäinen lambda-syntaksi tekee DSL-lohkoista mahdollisia.

### Oletusparametrit {#default-parameters}

Kotlin-funktioilla voi olla oletusarvoisia parametreja, mikä vähentää ylilastausmetodien tarvetta:

```kotlin
// Java - useita konstruktoreita tarvitaan
public Button() {}
public Button(String text) {}
public Button(String text, ButtonTheme theme) {}

// Kotlin - yksi funktio oletuksilla
fun button(
    text: String = "",
    theme: ButtonTheme = ButtonTheme.DEFAULT,
    block: Button.() -> Unit = {}
): Button
```

</details>
