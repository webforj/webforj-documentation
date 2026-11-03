---
sidebar_position: 1
title: Tests
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 58ac266506a221d71e22c723189aa08f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Tests webforJ

Les tests dans les applications webforJ combinent les tests unitaires, frontend et de bout en bout (E2E). Chacun remplit un objectif distinct pour assurer la stabilité et la fiabilité de l’application.

## Tests unitaires {#unit-testing}

Les tests unitaires visent à vérifier individuellement les composants ou la logique du backend, de façon isolée. En suivant les pratiques habituelles de test en Java, comme l’utilisation de [JUnit](https://junit.org/junit5/), les développeurs peuvent valider la logique spécifique de l’application et vérifier que chaque « unité » fonctionne comme prévu.

Pour les vues et les composants, le [TestBed](./testbed/overview) permet d’effectuer des tests sans navigateur dans de simples tests JUnit, sans navigateur ni serveur. Un test ajoute une vue à la page, interagit avec elle comme le ferait un utilisateur, puis vérifie le résultat depuis Java.

## Tests frontend {#frontend-testing}

Les tests frontend couvrent le code source écrit par un projet avec le [bundler frontend](/docs/managing-resources/bundler/overview). Le lanceur de tests [Bun](https://bun.sh/) exécute ces tests dans le cadre de la même compilation que celle qui lance les tests Java. Ainsi, un composant TypeScript ou une partie de la logique côté client est vérifié de la même manière que le backend. Consultez la page [Tests frontend](./frontend-testing).

## Tests de bout en bout (E2E) {#end-to-end-e2e-testing}

Les tests de bout en bout valident l’expérience des utilisateurs dans les applications webforJ, qui génèrent des interfaces web dynamiques à page unique. Ces tests simulent les interactions des utilisateurs et vérifient les fonctionnalités de l’application dans son ensemble.

À l’aide d’outils comme [**Selenium**](https://www.selenium.dev/) et [**Playwright**](https://playwright.dev/java/docs/intro), vous pouvez :

- Automatiser les interactions avec le navigateur en simulant des clics sur des boutons et l’envoi de formulaires.
- Vérifier le rendu cohérent et l’interactivité des composants d’interface utilisateur dynamiques.
- Confirmer que le comportement reste cohérent d’un navigateur et d’un appareil à l’autre.

## Combiner les stratégies de test {#combining-testing-strategies}

En combinant les tests unitaires et les tests E2E :

1. **Isoler les problèmes** : détecter et corriger rapidement les bogues au niveau des composants grâce aux tests unitaires.
2. **Confirmer la fiabilité** : valider les parcours utilisateur complets et les intégrations système grâce aux tests E2E.

## Sujets {#topics}

<DocCardList className="topics-section" />
