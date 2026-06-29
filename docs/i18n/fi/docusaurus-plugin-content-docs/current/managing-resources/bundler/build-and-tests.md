---
title: Build and tests
sidebar_position: 40
sidebar_class_name: new-content
description: >-
  What the bundler does across the build, the development watch, running
  frontend tests, tuning a compiler, and producing a minified production bundle.
_i18n_hash: 0fe6e8ed747a106be1fedf5a2506f803
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Käyttäjäliittymän kokoaja toimii osana [webforJ build plugin](/docs/configuration/build-plugin) tavoitteita. Lisää liitin kerran, kuten siellä on osoitettu, ja tavallinen `mvn package` tai `gradle build` tuottaa sovelluksen, jossa sen etupään koodi on käännetty, kun taas `mvn test` suorittaa etupään testit samanaikaisesti Java-täydentymistestien kanssa. Tämä sivu kattaa, mitä kokoaja tekee näiden vaiheiden aikana.

## Kehityksen valvonta {#the-development-watch}

`watch`-vaihe on se, jonka suoritat käsin kehityksen aikana sovelluksen ohella. Se kokoaa etupään kerran, sitten uudelleenrakentaa jokaisen muutoksen yhteydessä ja päivittää selaimen.

```bash
mvn compile webforj:watch spring-boot:run
```

WebforJ-projekti asettaa tämän oletustavoitteekseen, joten `mvn` ilman argumentteja käynnistää valvonnan ja sovelluksen yhdessä. Uudelleenlatauskäyttäytyminen, joka aiheuttaa tyylitiedoston muutoksen, joka sovelletaan paikallaan skriptimuutoksen yhteydessä, joka lataa näkymän uudelleen, käsitellään [Frontend watch](/docs/configuration/deploy-reload/frontend-watch).

## Etupään testit {#frontend-tests}

`test`-vaihe suorittaa Bun-testisuorittajan `src/main/frontend`-hakemistossa testivaiheen aikana, joten `mvn test` suorittaa etupään testit Java-testien kanssa. Kun lähdejuuri ei sisällä testitiedostoja, vaihe ohitetaan, ja epäonnistuva etupään testi epäonnistuttaa käännöksen, joten rikki mennyt etupää pysäyttää julkaisun samalla tavalla kuin rikki mennyt Java-testi. Näiden testien kirjoittamisesta, katso [Frontend testing](/docs/testing/frontend-testing).

## Kääntäjän säätäminen {#tuning-a-compiler}

Kääntäjä lukee asetukset tiedostosta `src/main/frontend/bun.config.ts`, joka on avain laajennustunnukselle, joten asetus saavuttaa oikean kääntäjän ilman lippua käännöksessä. Katso [SCSS ja Sass](/docs/managing-resources/bundler/extensions/scss) toimivasta esimerkistä, joka antaa SCSS-kääntäjälle latauspolun.

## Tuotantopaketin kokoaminen {#the-production-bundle}

`bundle`-vaihe suoritetaan `prepare-package` aikana, joten sovelluksen pakkaaminen kokoaa sen etupään tuotantoa varten. Tuotantokäännös eroaa kehityskäännöksestä kahdella tavalla, jotka ovat tärkeitä, kun sovellus on otettu käyttöön.

- **Hashatut tiedostonimet.** Jokainen lähtötiedosto sisältää sisällön hashin nimessään. Selain voi sitten välimuistittaa tiedoston pitkään, koska sisällön muutos tuottaa uuden nimen, ja uusi nimi pakottaa tuoretta noutoa. Välimuistitus pysyy turvallisena ilman manuaalista versionnostoa.
- **Minimoitu lähtö.** Tyhjät merkit ja kuollut koodi poistetaan, joten selaimen lataamat tiedostot ovat niin pieniä kuin käännös voi ne tehdä.

Kehityskäännös ohittaa molemmat. Se säilyttää vakaat nimet ja luettavan ulostulon, jolloin valvonta voi vaihtaa yhden tiedoston paikallaan ja voit lukea, mitä ladataan, kun vianetsit.

Koska minimointi on osa tätä vaihetta, projekti, joka käyttää kokoajaa, ei tarvitse mitään muuta toimitettaessa minimoituja CSS- ja JavaScript-tiedostoja. Sovelluksessa, joka lataa resursseja [resurssien merkintöjen](/docs/managing-resources/importing-assets) kautta ilman kokoajaa, [minimoija liitin](/docs/configuration/minifier-plugin) kattaa sen tuotantominimoinnin sen sijaan.

## Kiireellinen paketti {#eager-bundle}

Oletuksena jokainen näkymä lataa vain sen etupään, jota se käyttää, kun kyseisen luokan komponentti luodaan, joten näkymä maksaa vain siitä, mitä se renderöi.

Kiireinen tila lataa koko etupään sovelluksen käynnistyessä yhtenä pakettina, sen sijaan että sitä ladataan näkymä kerrallaan. Ota se käyttöön `eager`-valinnalla:

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <eager>true</eager>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  eager = true
}
```

</TabItem>
</Tabs>

Kiireinen on pois päältä oletuksena, ja näkymäkohtainen malli sopii useimmille sovelluksille. Käytä sitä, kun haluat, että koko etupää on paikallaan alusta alkaen sen sijaan, että se ladataan näkymien renderoituessa.
