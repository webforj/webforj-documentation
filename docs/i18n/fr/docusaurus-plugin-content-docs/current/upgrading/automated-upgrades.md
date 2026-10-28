---
sidebar_position: 1
title: Mises à niveau automatiques
description: >-
  Migrate webforJ projects between versions automatically with OpenRewrite
  recipes that rename APIs, update dependencies, and flag manual fixes.
_i18n_hash: 8c889d8dea5c90aad2ef224d152e611e
---
# Utilisation d'OpenRewrite {#using-openrewrite}

OpenRewrite est un projet open source conçu pour le refactoring automatisé du code source, vous permettant de migrer de manière efficace et automatique entre les versions de webforJ pour des projets utilisant des API obsolètes ou supprimées.

## Quand utiliser OpenRewrite ? {#when-to-use-openrewrite}

Vous pouvez utiliser OpenRewrite lors de la mise à niveau pour vous éloigner des API obsolètes, pas seulement lors des versions majeures. Cela vous aide à vous éloigner des nouvelles méthodes obsolètes, avant qu'elles ne soient supprimées dans une version ultérieure.

## Configuration {#setup}

Pour mettre à niveau votre projet avec OpenRewrite, ajoutez le plugin Maven OpenRewrite à votre `pom.xml`, avec [`webforj-rewrite`](https://github.com/webforj/webforj/tree/main/webforj-rewrite) comme dépendance :

```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>6.36.0</version>
  <configuration>
    <activeRecipes>
      <recipe>RECIPE_NAME</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-rewrite</artifactId>
      <version>TARGET_VERSION</version>
    </dependency>
  </dependencies>
</plugin>
```

Remplacez `TARGET_VERSION` par la version de webforJ vers laquelle vous procédez à la mise à niveau et `RECIPE_NAME` par l'une des recettes de la section [Recettes disponibles](#available-recipes) de cet article.

## Prévisualisation des changements (optionnel) {#previewing-changes}

Si vous préférez prévisualiser les changements qu'OpenRewrite effectuera avec une recette webforJ, l'exécution de la commande suivante génère un diff dans `target/rewrite/rewrite.patch` sans modifier aucun fichier. Examinez le patch pour voir exactement ce que la recette changera.

```bash
mvn rewrite:dryRun
```

## Exécution d'OpenRewrite {#running-openrewrite}

Lorsque vous êtes prêt à appliquer les modifications avec OpenRewrite, exécutez la commande suivante :

```bash
mvn rewrite:run
```

La recette gère automatiquement la majorité de la mise à niveau en mettant à jour les dépendances, en renommant les méthodes, en changeant les types et en ajustant les types de retour. Pour les quelques cas où un remplacement 1:1 n'existe pas, elle ajoute des commentaires `TODO` dans votre code avec des instructions spécifiques. Recherchez ces `TODO` dans votre projet pour trouver ce qu'il reste à faire.

## Nettoyage {#clean-up}

Après avoir exécuté OpenRewrite avec une recette webforJ et résolu tous les commentaires `TODO`, retirez le plugin de votre `pom.xml`.

## Recettes disponibles {#available-recipes}

| Version | Projets webforJ standards | Projets Spring Boot |
| ------- | ------- | ------- |
| v26.03 | `com.webforj.rewrite.v26.UpgradeWebforj_26_03` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26.01 | `com.webforj.rewrite.v26.UpgradeWebforj_26_01` | `com.webforj.rewrite.v26.UpgradeWebforjSpring_26_01` |
| v26 | `com.webforj.rewrite.v26.UpgradeWebforj` | `com.webforj.rewrite.v26.UpgradeWebforjSpring` |
