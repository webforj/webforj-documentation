---
sidebar_position: 4
title: Managing Secrets
description: >-
  Keep database passwords, API keys, and other secrets out of your webforJ
  source tree and configuration files by resolving them at runtime.
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
De regel achter elk geheim, een databasewachtwoord, een API-sleutel, een ondertekeningssleutel, is dat de werkelijke waarde nooit in je code, je `webforj.conf` of je repository leeft. Los het in plaats daarvan tijdens runtime op, zodat dezelfde build in elke omgeving draait en een gelekte repository niets prijsgeeft.

## Lees geheimen uit de omgeving {#read-secrets-from-the-environment}

De meest draagbare benadering is om elk geheim op te slaan als een omgevingsvariabele op de machine of container die de app uitvoert, en deze bij het opstarten te lezen in plaats van het ergens in te committeren.

```bash
# stel in waar de app draait, nooit in een gevolgd bestand
export DB_PASSWORD=…
```

Houd deze waarden uit `webforj.conf` en elk ander bestand dat je commit, en zorg ervoor dat je implementatie ze instelt voordat de app begint.

## Op Spring Boot {#on-spring-boot}

Als je app op Spring Boot draait, maak dan gebruik van de externe configuratie in plaats van je eigen versie uit te vinden. Je kunt een omgevingsvariabele in `application.properties` verwijzen met een `${...}` placeholder, en een geheimenbestand dat buiten het project (en buiten versiebeheer) leeft, binnenhalen met `spring.config.import`.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Dit zijn functies van Spring Boot, niet van webforJ; zie de [externe configuratie](https://docs.spring.io/spring-boot/reference/features/external-config.html) documentatie van Spring Boot voor de volledige reeks opties.

:::tip Een gelekt geheim is een verbrand geheim
Voeg bestanden zoals `secrets.properties` toe aan `.gitignore`, controleer je geschiedenis op waarden die zijn binnengeslopen, en draai alles wat ooit is blootgesteld. Geheimen buiten nieuwe commits houden, ongedaan maken wat al is gepusht, doet dit niet.
:::
