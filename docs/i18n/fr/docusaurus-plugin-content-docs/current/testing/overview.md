---
sidebar_position: 1
title: Testing
description: >-
  Combine JUnit unit tests with Selenium or Playwright end-to-end tests to
  validate webforJ components, logic, and full user journeys.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 3c566f2e9edf3bf00e984a01e0b2f049
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# webforJ Testing

Les tests dans les applications webforJ combinent des tests unitaires, frontend et de bout en bout (E2E), chacun ayant un objectif distinct pour maintenir l'application stable et fiable.

## Unit testing {#unit-testing}

Les tests unitaires se concentrent sur la vérification des composants individuels ou de la logique backend en isolation. En suivant les pratiques standard de test Java, telles que l'utilisation de [JUnit](https://junit.org/junit5/), les développeurs peuvent valider efficacement une logique d'application spécifique et s'assurer que chaque "unité" fonctionne comme prévu.

## Frontend testing {#frontend-testing}

Les tests frontend couvrent les sources que les auteurs de projet utilisent avec le [frontend bundler](/docs/managing-resources/bundler/overview). Le moteur de test [Bun](https://bun.sh/) les exécute dans le cadre de la même construction qui exécute les tests Java, de sorte qu'un composant TypeScript ou une partie de la logique côté client est vérifiée de la même manière que le backend. Voir [Frontend testing](./frontend-testing).

## End-to-End (E2E) testing {#end-to-end-e2e-testing}

Les tests de bout en bout sont importants pour valider l'expérience utilisateur dans les applications webforJ, qui génèrent des interfaces web dynamiques à page unique. Ces tests simulent les interactions utilisateur et vérifient les fonctionnalités de l'ensemble de l'application.

En utilisant des outils tels que [**Selenium**](https://www.selenium.dev/) et [**Playwright**](https://playwright.dev/java/docs/intro), vous pouvez :

- Automatiser les interactions du navigateur, telles que les clics sur les boutons et les soumissions de formulaires.
- Vérifier le rendu cohérent et l'interactivité des composants d'interface utilisateur dynamiques.
- Assurer la cohérence du comportement à travers différents navigateurs et appareils.

## Combining testing strategies {#combining-testing-strategies}

En combinant les tests unitaires et E2E :

1. **Isoler les problèmes** : Détecter et résoudre les bogues au niveau des composants tôt grâce aux tests unitaires.
2. **Assurer la fiabilité** : Valider les parcours utilisateurs complets et les intégrations système avec les tests E2E.

## Topics {#topics}

<DocCardList className="topics-section" />
