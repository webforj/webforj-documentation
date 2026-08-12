---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

Hotswap-työkalu soveltaa käännettyjä luokkamuutoksia käynnissä olevaan sovellukseen ilman uudelleenkäynnistystä. Sovellus säilyttää tilansa päivitysten välillä. Työkalun nimi on määritelty [webforJ build plugin](/docs/configuration/build-plugin) -konfiguraatiossa, ja se liitetään, kun build aloittaa sovelluksen. Aja-komento pysyy ennallaan, eikä projekti julista siihen riippuvuutta.

Kaksi työkalua tuetaan:

- **HotswapAgent** on avoimen lähdekoodin. Build-plugin lataa agentin ensimmäisellä suorituskerralla ja välimuistaa sen.
- **JRebel** on kaupallinen tuote. Se vaatii oman asennuksen ja lisenssin.

Määritä tarkalleen yksi. Build, joka nimeää molemmat, epäonnistuu virheellä, joka nimeää molemmat.

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

Tyhjällä elementillä on täydellinen konfiguraatio:

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

Kaksi vaihtoehtoa tarkentaa liittämistä:

| Vaihtoehto | Kuvaus |
|------------|--------|
| `version` | Erityinen agentin versio sen sijaan, että plogiini valitsisi. |
| `path` | Agentin jar- tiedosto levyllä, jota käytetään suoraan ilman latausta. Koneille, joilla ei ole verkkoyhteyttä tai mukautettujen agenttirakennusten käyttöön. |

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

### Luokkastruktuurin muutokset {#class-structure-changes}

Metodin kehykseen tehtävät muokkaukset pätevät kaikilla Java-virtuaalikoneilla. Luokan rakenteeseen liittyvät muutokset, kuten uusi kenttä tai uusi metodi, vaativat virtuaalikoneen, joka hyväksyy `-XX:+AllowEnhancedClassRedefinition` -vaihtoehdon, jonka [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) tarjoaa. Build havaitsee kyvykkyyden ja ottaa sen käyttöön. Katso [Prerequisites](/docs/introduction/prerequisites#java-development-kit-jdk-21) asentaaksesi JetBrains Runtime.

Ilman kyvykkyyttä metodin kehykseen tehdyt muokkaukset pätevät silti, ja luokan rakenteen muutos ei vaikuta käynnissä olevaan sovellukseen ennen uudelleenkäynnistystä. Build-lokissa tulostuu varoitus, joka nimeää vaatimuksen, ja selain näyttää ilmoituksen kerran.

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) on kaupallinen tuote, jonka myyjä myöntää lisenssin. webforJ ei sitä toimita, ei lataa sitä, eikä osallistu sen lisensointiin. Build lukee määritetyn polun, tarkistaa, että tiedosto on olemassa ja liittää sen muuttumattomana.

Osoita konfiguraatio JRebel-asennuksesi agenttiin, natiivikirjastoon tai jar-tiedostoon:

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

Polku on pakollinen. Build, joka valitsee JRebelin ilman sitä, epäonnistuu virheellä, joka nimeää puuttuvan asetuksen.

JRebelin kanssa kaikki luokkamuutokset, mukaan lukien rakenteen muutokset, pätevät kaikilla Java-suoritusympäristöillä.

## Komentorivivalinta {#command-line-selection}

`webforj.hotswap` -ominaisuus ylikirjoittaa build-tiedoston yhden suorituskerran ajaksi. Hyväksytyt arvot ovat `hotswapAgent`, `jrebel` ja `off`. Mikä tahansa muu arvo epäonnistaa buildin virheellä, joka luettelee voimassa olevat. JRebelin valitseminen vaatii silti agenttipolun konfiguraatiossa.

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

## Muutoksen soveltaminen {#applying-a-change}

Käännä muutos ja se saavuttaa käynnissä olevan sovelluksen. Tallenna IDE:ssä, joka kääntää tallennettaessa, tai suorita käännös toisessa terminaalissa.

Kun jokainen muutettu luokka kuuluu siihen, mitä nykyinen sivu renderoi, kyseinen osa rakennetaan uudelleen paikallaan ja sovelluksen tila pysyy. Muuten sivu lataa täydellisesti: sovelluksessa ilman reititystä, luokassa, joka on renderoitujen reittien ulkopuolella, tai kun uudelleenrakennusta ei voida toteuttaa. Yksi käännetty muutos tuottaa yhden päivityksen selaimessa.
