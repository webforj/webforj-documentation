---
sidebar_position: 4
title: Managing Secrets
description: >-
  Keep database passwords, API keys, and other secrets out of your webforJ
  source tree and configuration files by resolving them at runtime.
_i18n_hash: 3c20f1e66f7fb00f96c26f0b00d46f07
---
La règle derrière chaque secret, un mot de passe de base de données, une clé API, une clé de signature, est que sa véritable valeur ne vit jamais dans votre code, votre `webforj.conf`, ou votre dépôt. Résolvez-le au moment de l'exécution, afin que le même build fonctionne dans chaque environnement et qu'un dépôt divulgué ne révèle rien.

## Lire les secrets depuis l'environnement {#read-secrets-from-the-environment}

L'approche la plus portable consiste à stocker chaque secret en tant que variable d'environnement sur la machine ou le conteneur qui exécute l'application, et à le lire au démarrage plutôt que de l'engager quelque part.

```bash
# définir où l'application s'exécute, jamais dans un fichier suivi
export DB_PASSWORD=…
```

Gardez ces valeurs en dehors de `webforj.conf` et de tout autre fichier que vous engagez, et assurez-vous que votre déploiement les configure avant le démarrage de l'application.

## Sur Spring Boot {#on-spring-boot}

Si votre application fonctionne sur Spring Boot, appuyez-vous sur sa configuration externalisée plutôt que d'inventer la vôtre. Vous pouvez référencer une variable d'environnement depuis `application.properties` avec un espace réservé `${...}`, et tirer dans un fichier de secrets qui vit en dehors du projet (et en dehors du contrôle de version) avec `spring.config.import`.

```properties title="application.properties"
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Ce sont des fonctionnalités de Spring Boot, pas de webforJ ; consultez la documentation de Spring Boot sur la [configuration externalisée](https://docs.spring.io/spring-boot/reference/features/external-config.html) pour l'ensemble des options.

:::tip Un secret divulgué est un secret brûlé
Ajoutez des fichiers comme `secrets.properties` à `.gitignore`, audit votre historique pour des valeurs qui ont glissé, et faites tourner tout ce qui a été exposé. Garder les secrets hors des nouveaux commits ne corrige pas ceux qui ont déjà été poussés.
:::
