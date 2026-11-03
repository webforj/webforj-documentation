---
title: Kuumavaihto
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# Hotswap <DocChip chip='since' label='26.02' />

Hotswap-työkalu ottaa käännettyjen luokkien muutokset käyttöön käynnissä olevassa sovelluksessa ilman uudelleenkäynnistystä. Sovelluksen tila säilyy päivitysten välillä. Työkalu määritetään [webforJ-koontilaajennuksen](/docs/configuration/build-plugin) asetuksissa, ja se liitetään sovellukseen koonnin käynnistäessä sen. Suorituskomento pysyy samana, eikä projektiin tarvitse määrittää sille riippuvuutta.

Tuettuja työkaluja on kaksi:

- **HotswapAgent** on avoimen lähdekoodin työkalu. Koontilaajennus lataa agentin ensimmäisellä suorituskerralla ja tallentaa sen välimuistiin.
- **JRebel** on kaupallinen tuote. Se edellyttää omaa asennusta ja lisenssiä.

Määritä täsmälleen yksi työkalu. Jos koonti määrittää molemmat, se epäonnistuu ja ilmoittaa virheessä molempien nimet.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Tyhjä elementti on täydellinen määritys:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

Kaksi asetusta tarkentaa liittämistä:

| Asetus | Kuvaus |
|--------|-------------|
| `version` | Tietty agenttiversio laajennuksen valitseman version sijaan. |
| `path` | Levylle tallennettu agentin JAR-tiedosto, jota käytetään suoraan ilman lataamista. Sopii koneille, joilla ei ole verkkoyhteyttä, tai mukautetulle agenttiversiolle. |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### Luokan rakenteen muutokset {#class-structure-changes}

Metodin rungon muokkaukset tulevat käyttöön kaikissa Java-virtuaalikoneissa. Luokan rakenteeseen tehdyt muutokset, kuten uuden kentän tai metodin lisääminen, edellyttävät virtuaalikonetta, joka hyväksyy asetuksen `-XX:+AllowEnhancedClassRedefinition`. Tämän tarjoaa [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Koonti tunnistaa tämän ominaisuuden ja ottaa sen käyttöön. Katso JetBrains Runtimen asennusohjeet kohdasta [Edellytykset](/docs/introduction/prerequisites#java-development-kit-jdk-21).

Jos ominaisuutta ei ole, metodin rungon muokkaukset tulevat silti käyttöön, mutta luokan rakenteen muutos näkyy käynnissä olevassa sovelluksessa vasta uudelleenkäynnistyksen jälkeen. Koontiloki tulostaa vaatimuksen ilmoittavan varoituksen, ja selaimessa näytetään ilmoitus kerran.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) on kaupallinen tuote, jonka lisensoi sen toimittaja. webforJ ei toimita eikä lataa sitä, eikä osallistu sen lisensointiin. Koonti lukee määritetyn polun, tarkistaa, että tiedosto on olemassa, ja liittää sen muuttamattomana.

Osoita määrityksessä JRebel-asennuksesi agenttiin, joka voi olla natiivikirjasto tai JAR-tiedosto:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

Polku on pakollinen. Jos koonti valitsee JRebelin ilman sitä, se epäonnistuu ja ilmoittaa virheessä puuttuvan asetuksen.

JRebelillä kaikki luokkamuutokset, myös rakenteen muutokset, tulevat käyttöön kaikissa Java-ajoaikaympäristöissä.

## Valinta komentoriviltä {#command-line-selection}

`webforj.hotswap`-ominaisuus ohittaa koontitiedoston asetuksen yksittäisen suorituskerran ajaksi. Sallitut arvot ovat `hotswapAgent`, `jrebel` ja `off`. Mikä tahansa muu arvo aiheuttaa koonnin epäonnistumisen, ja virheessä luetellaan sallitut arvot. `jrebel`-arvon valitseminen edellyttää silti, että agentin polku on määritetty.

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## Muutoksen käyttöönotto {#applying-a-change}

Käännä muutos, niin se tulee käyttöön käynnissä olevassa sovelluksessa. Tallenna tiedosto IDE:ssä, joka kääntää tallennuksen yhteydessä, tai suorita käännös toisessa pääteikkunassa.

Jos kaikki muuttuneet luokat kuuluvat nykyisen sivun renderöimiin luokkiin, muuttunut osa rakennetaan uudelleen paikallaan ja sovelluksen tila säilyy. Muussa tapauksessa koko sivu ladataan uudelleen: näin käy sovelluksessa, jossa ei ole reititystä, renderöityjen reittien ulkopuolisen luokan muuttuessa tai jos uudelleenrakennusta ei voida suorittaa. Yksi käännetty muutos tuottaa selaimessa yhden päivityksen.

## Uudelleenlatausten nopeuttaminen IntelliJ IDEA:ssa {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA kääntää tallennuksen yhteydessä vain, kun automaattiset koonnit ovat käytössä, ja se odottaa ennen jokaista vaihetta. Lyhennä odotusaikoja, jotta muutos tulee nopeammin käyttöön käynnissä olevassa sovelluksessa.

### Automaattisten koontien käyttöönotto {#enable-automatic-builds}

Avaa asetukset ja ota käyttöön seuraavat valinnat:

1. Ota kohdassa **Build, Execution, Deployment** > **Compiler** käyttöön **Build project automatically**.
<!-- vale off -->
2. Ota kohdassa **Advanced Settings** > **Compiler** käyttöön **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Ota kohdassa **Appearance & Behavior** > **System Settings**, osiossa **Autosave**, käyttöön **Save files if the IDE is idle for** ja aseta arvoksi `1` sekunti.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Paina <kbd>Ctrl+ALt+S</kbd> tai valitse **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Paina <kbd>Cmd+,</kbd> tai valitse **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Koontiviiveiden lyhentäminen {#shorten-the-build-delays}

Aseta seuraavien Registry-avainten arvoksi `0`:

| Avain | Kuvaus |
|-----|-------------|
| `compiler.automake.trigger.delay` | Tiedoston muutoksen ja koonnin alkamisen välinen viive. |
| `compiler.automake.build.while.idle.timeout` | Joutoaika ennen koonnin alkamista. |
| `compiler.automake.postpone.when.idle.less.than` | Joutoaika, jota lyhyemmän ajan tapauksessa IntelliJ IDEA lykkää koontia. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Avaa **Find Action** painamalla `Ctrl+Shift+A`.
2. Kirjoita `Registry...` ja valitse se.

</TabItem>
<TabItem value="macos" label="macOS">

1. Avaa **Find Action** painamalla `Cmd+Shift+A`.
2. Kirjoita `Registry...` ja valitse se.

</TabItem>
</Tabs>

Voit myös avata **Search Everywhere** -haun painamalla `Shift`-näppäintä kahdesti, siirtyä **Actions**-välilehdelle ja kirjoittaa `Registry`.

Kirjoita **Registry**-valintaikkunassa avaimen nimi suodattaaksesi luetteloa ja aseta sitten sen arvoksi `0`.

:::warning
Registry-merkinnät ovat IntelliJ IDEA:n lisäasetuksia, joita ei tueta. Voit palauttaa ne valitsemalla **Restore Defaults** **Registry**-valintaikkunassa.
:::
