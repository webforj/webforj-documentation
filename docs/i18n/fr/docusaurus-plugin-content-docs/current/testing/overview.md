---
sidebar_position: 1
title: Testing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 9fb95791bbb594c57b7ff481ed4fe47b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Test de webforJ

Les tests dans les applications webforJ impliquent une combinaison de tests unitaires et de tests de bout en bout (E2E) pour garantir une application stable et fiable. Chaque type de test a un but distinct dans le maintien de la qualité de l'application.

## Tests unitaires {#unit-testing}

Les tests unitaires se concentrent sur la vérification des composants individuels ou de la logique backend de manière isolée. En suivant les pratiques de test Java standard, telles que l'utilisation de [JUnit](https://junit.org/junit5/), les développeurs peuvent valider efficacement la logique spécifique de l'application et s'assurer que chaque "unité" fonctionne comme prévu.

## Tests de bout en bout (E2E) {#end-to-end-e2e-testing}

Les tests de bout en bout sont importants pour valider l'expérience utilisateur dans les applications webforJ, qui génèrent des interfaces web dynamiques à page unique. Ces tests simulent les interactions des utilisateurs et vérifient les fonctionnalités de l'ensemble de l'application.

En utilisant des outils comme [**Selenium**](https://www.selenium.dev/) et [**Playwright**](https://playwright.dev/java/docs/intro), vous pouvez :

- Automatiser les interactions du navigateur, telles que les clics sur les boutons et les soumissions de formulaires.
- Vérifier le rendu cohérent et l'interactivité des composants d'interface utilisateur dynamiques.
- Assurer la cohérence du comportement sur différents navigateurs et appareils.

## Combinaison des stratégies de test {#combining-testing-strategies}

En combinant les tests unitaires et E2E :

1. **Isoler les problèmes** : Détecter et résoudre les bogues au niveau des composants en amont grâce aux tests unitaires.
2. **Assurer la fiabilité** : Valider les parcours utilisateurs complets et les intégrations système avec les tests E2E.

## Sujets {#topics}

<DocCardList className="topics-section" />
