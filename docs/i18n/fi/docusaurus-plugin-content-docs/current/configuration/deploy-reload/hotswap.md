---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# Hotswap <DocChip chip='since' label='26.02' />

Hotswap-työkalu ottaa käännettyjen luokkien muutokset käyttöön käynnissä olevassa sovelluksessa ilman uudelleenkäynnistystä. Sovelluksen tila säilyy päivitysten välillä. Työkalu määritetään [webforJ-koontiliitännäisen](/docs/configuration/build-plugin) asetuksissa, ja se liitetään sovellukseen, kun koonti käynnistää sen. Suorituskomento pysyy samana, eikä projektiin tarvitse lisätä riippuvuutta.

Tuettuja työkaluja on kaksi:

- **HotswapAgent** on avoimen lähdekoodin työkalu. Koontiliitännäinen lataa agentin ensimmäisellä suorituskerralla ja tallentaa sen välimuistiin.
- **JRebel** on kaupallinen tuote. Se vaatii oman asennuksen ja lisenssin.

Määritä täsmälleen yksi työkalu. Jos koonti nimeää molemmat, se epäonnistuu ja ilmoittaa virheessä molempien nimet.

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

Liittämistä voi tarkentaa kahdella asetuksella:

| Asetus | Kuvaus |
|--------|-------------|
| `version` | Tietty agenttiversio liitännäisen valitseman version sijaan. |
| `path` | Levylle tallennettu agentti-JAR, jota käytetään suoraan ilman lataamista. Sopii koneille, joilla ei ole verkkoyhteyttä, tai mukautetulle agenttikoosteelle. |

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

Metodin rungon muokkaukset otetaan käyttöön kaikissa Java-virtuaalikoneissa. Luokan rakenteen muutokset, kuten uuden kentän tai metodin lisääminen, vaativat virtuaalikoneen, joka hyväksyy asetuksen `-XX:+AllowEnhancedClassRedefinition`. Tällaisen ominaisuuden tarjoaa [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Koonti tunnistaa ominaisuuden ja ottaa sen käyttöön. Katso JetBrains Runtimen asennusohjeet kohdasta [Edellytykset](/docs/introduction/prerequisites#java-development-kit-jdk-21).

Ilman tätä ominaisuutta metodin rungon muokkaukset otetaan silti käyttöön, mutta luokan rakenteen muutos ei tule voimaan käynnissä olevassa sovelluksessa ennen uudelleenkäynnistystä. Koontiloki tulostaa vaatimuksen nimeävän varoituksen, ja selain näyttää ilmoituksen kerran.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) on kaupallinen tuote, jonka lisensoi sen toimittaja. webforJ ei toimita sitä eikä lataa sitä, eikä se osallistu lisensointiin. Koonti lukee määritetyn polun, tarkistaa, että tiedosto on olemassa, ja liittää sen muuttamattomana.

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

Polku on pakollinen. Jos koonti valitsee JRebelin ilman polkua, se epäonnistuu ja ilmoittaa virheessä puuttuvan asetuksen.

JRebelin avulla kaikki luokkamuutokset, myös rakenteen muutokset, otetaan käyttöön kaikissa Java-ajoympäristöissä.

## Valinta komentoriviltä {#command-line-selection}

Ominaisuus `webforj.hotswap` ohittaa koontitiedoston asetuksen yksittäisellä suorituskerralla. Sallitut arvot ovat `hotswapAgent`, `jrebel` ja `off`. Mikä tahansa muu arvo aiheuttaa koonnin epäonnistumisen, ja virheessä luetellaan sallitut arvot. `jrebel`-arvon valitseminen edellyttää silti agenttipolun määrittämistä asetuksissa.

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

Käännä muutos, niin se tulee voimaan käynnissä olevassa sovelluksessa. Tallenna tiedosto IDE:ssä, joka kääntää tallennuksen yhteydessä, tai suorita käännös toisessa pääteikkunassa.

Kun kaikki muuttuneet luokat kuuluvat nykyisen sivun näyttämään sisältöön, kyseinen osa rakennetaan uudelleen paikan päällä ja sovelluksen tila säilyy. Muussa tapauksessa koko sivu ladataan uudelleen: näin käy sovelluksissa, joissa ei ole reititystä, kun luokka ei kuulu näytettyihin reitteihin tai kun uudelleenrakennusta ei voida tehdä. Yksi käännetty muutos tuottaa selaimessa yhden päivityksen.

## Latauksen nopeuttaminen IntelliJ IDEA:ssa {#speeding-up-reloads-in-intellij-idea}

IntelliJ IDEA kääntää tallennuksen yhteydessä vain, kun automaattiset koonnit ovat käytössä, ja se odottaa ennen jokaista vaihetta. Lyhennä odotusaikoja, jotta muutos tulee nopeammin voimaan käynnissä olevassa sovelluksessa.

### Automaattisten koontien käyttöönotto {#enable-automatic-builds}

Avaa asetukset ja ota käyttöön seuraavat valinnat:

1. Valitse **Build, Execution, Deployment** > **Compiler** ja ota käyttöön **Build project automatically**.
<!-- vale off -->
2. Valitse **Advanced Settings** > **Compiler** ja ota käyttöön **Allow auto-make to start even if developed application is currently running**.
<!-- vale on -->
3. Valitse **Appearance & Behavior** > **System Settings** ja ota **Autosave**-kohdassa käyttöön **Save files if the IDE is idle for**. Aseta arvoksi `1` sekunti.

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

Paina <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd> tai valitse **File** > **Settings**.

</TabItem>
<TabItem value="macos" label="macOS">

Paina <kbd>Cmd</kbd>+<kbd>+</kbd> tai valitse **IntelliJ IDEA** > **Settings**.

</TabItem>
</Tabs>

### Koontiviiveiden lyhentäminen {#shorten-the-build-delays}

Aseta seuraavien rekisteriavainten arvoksi `0`:

| Avain | Kuvaus |
|-----|-------------|
| `compiler.automake.trigger.delay` | Tiedostomuutoksen ja koonnin käynnistymisen välinen viive. |
| `compiler.automake.build.while.idle.timeout` | Joutoaika ennen koonnin käynnistymistä. |
| `compiler.automake.postpone.when.idle.less.than` | Joutoaika, jota lyhyemmän ajan jälkeen IntelliJ IDEA lykkää koontia. |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. Avaa **Find Action** painamalla <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd>.
2. Kirjoita `Registry...` ja valitse se.

</TabItem>
<TabItem value="macos" label="macOS">

1. Avaa **Find Action** painamalla <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd>.
2. Kirjoita `Registry...` ja valitse se.

</TabItem>
</Tabs>

Voit myös avata **Search Everywhere** -haun painamalla <kbd>Shift</kbd> kahdesti, siirtyä **Actions**-välilehdelle ja kirjoittaa `Registry`.

Kirjoita **Registry**-valintaikkunassa avaimen nimi suodattaaksesi luetteloa ja aseta sen arvoksi `0`.

:::warning
Rekisterimerkinnät ovat IntelliJ IDEA:n edistyneitä, ei-tuettuja asetuksia. Voit palauttaa ne valitsemalla **Registry**-valintaikkunassa **Restore Defaults**.
:::
