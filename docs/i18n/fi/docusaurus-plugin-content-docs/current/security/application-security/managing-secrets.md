---
sidebar_position: 4
title: Managing Secrets
description: >-
  Keep database passwords, API keys, and other secrets out of your webforJ
  source tree and configuration files by resolving them at runtime.
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
Säännön mukaan jokaisen salaisuuden, kuten tietokannan salasanan, API-avaimen tai allekirjoitusavaimen, todellinen arvo ei koskaan elä koodissasi, `webforj.conf`-tiedostossasi tai varastossasi. Ratkaise se ajon aikana sen sijaan, jotta sama rakennus toimii kaikissa ympäristöissä ja vuotava varasto ei paljasta mitään.

## Lue salaisuudet ympäristöstä {#read-secrets-from-the-environment}

Käytännöllisin lähestymistapa on tallentaa jokainen salaisuus ympäristömuuttujana koneelle tai säiliöön, joka suorittaa sovelluksen, ja lukea se käynnistyksessä sen sijaan, että sitoutaisit sen minnekään.

```bash
# määritä, missä sovellus toimii, koskaan ei seurannassa olevassa tiedostossa
export DB_PASSWORD=…
```

Pidä nämä arvot erossa `webforj.conf`-tiedostosta ja mistä tahansa muusta tiedostosta, jonka sitoutat, ja varmista, että käyttöönotto asettaa ne ennen sovelluksen käynnistämistä.

## Spring Bootin kanssa {#on-spring-boot}

Jos sovelluksesi toimii Spring Bootin päällä, nojaa sen ulkoistettuun konfigurointiin sen sijaan, että keksisit oman. Voit viitata ympäristömuuttujaan `application.properties`-tiedostossa `${...}`-paikkamerkillä ja tuoda salaisuustiedoston, joka sijaitsee projektin ulkopuolella (ja versionhallinnan ulkopuolella), käyttämällä `spring.config.import`.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Nämä ovat Spring Bootin ominaisuuksia, eivät webforJ:n; katso Spring Bootin [ulkoistettuun konfigurointiin](https://docs.spring.io/spring-boot/reference/features/external-config.html) liittyvää dokumentaatiota kattavalle vaihtoehtojen valikoimalle.

:::tip Vuotanut salaisuus on poltettu salaisuus
Lisää tiedostot, kuten `secrets.properties`, `.gitignore`-tiedostoon, tarkista historiassasi arvot, jotka ovat päässeet sisään, ja vaihda kaikki, jotka ovat koskaan olleet paljastettuina. Salaisuuksien pitäminen uusista sitoumuksista ei kumoa niitä, jotka on jo työnnetty.
:::
