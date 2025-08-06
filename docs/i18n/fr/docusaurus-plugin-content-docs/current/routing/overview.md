---
sidebar_position: 1
title: Routing
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 115816519ca0212b84eb27923a74ca53
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

Dans les applications web modernes, **le routage** fait référence au processus de gestion de la navigation entre différentes vues ou composants en fonction de l'URL ou du chemin actuel. Dans webforJ, le routage établit un cadre sophistiqué pour **la navigation côté client**, où les mises à jour de l'interface utilisateur se font de manière dynamique sans nécessiter de rechargements complets de page, améliorant ainsi les performances de votre application.

## Routage traditionnel vs routage côté client {#traditional-vs-client-side-routing}

Dans le routage traditionnel côté serveur, lorsque l'utilisateur clique sur un lien, le navigateur envoie une requête au serveur pour un nouveau document. Le serveur répond en envoyant une nouvelle page HTML, ce qui oblige le navigateur à réévaluer le CSS et le JavaScript, à re-rendre l'ensemble du document et à réinitialiser l'état de l'application. Ce cycle introduit des délais et des inefficacités, car le navigateur doit recharger les ressources et l'état de la page. Le processus implique généralement :

1. **Requête** : L'utilisateur navigue vers une nouvelle URL, déclenchant une requête au serveur.
2. **Réponse** : Le serveur renvoie un nouveau document HTML ainsi que les ressources associées (CSS, JS).
3. **Rendu** : Le navigateur re-rend l'ensemble de la page, perdant souvent l'état des pages précédemment chargées.

Cette approche peut entraîner des goulets d'étranglement en matière de performances et des expériences utilisateur sous-optimales en raison des rechargements complets de page répétés.

**Le routage côté client** dans webforJ résout ce problème en permettant la navigation directement dans le navigateur, en mettant à jour dynamiquement l'interface utilisateur sans envoyer de nouvelle requête au serveur. Voici comment cela fonctionne :

1. **Requête Initiale Unique** : Le navigateur charge l'application une fois, y compris toutes les ressources nécessaires (HTML, CSS, JavaScript).
2. **Gestion des URL** : Le routeur écoute les changements d'URL et met à jour la vue en fonction de la route actuelle.
3. **Rendu Dynamique des Composants** : Le routeur associe l'URL à un composant et le rend de manière dynamique, sans recharger la page.
4. **Préservation de l'État** : L'état de l'application est maintenu entre les navigations, assurant une transition fluide entre les vues.

Ce design permet **le deep linking** et **la gestion d'état basée sur l'URL**, permettant aux utilisateurs de mettre en signet et de partager des pages spécifiques au sein de l'application tout en profitant d'une expérience fluide et monopage.

## Principes fondamentaux {#core-principles}

- **Mappage de Composants basé sur l'URL** : Dans webforJ, les routes sont directement liées aux composants d'interface utilisateur. Un motif d'URL est mappé à un composant spécifique, dictant quel contenu est affiché en fonction du chemin actuel.
- **Routage Déclaratif** : Les routes sont définies de manière déclarative, généralement à l'aide d'annotations. Chaque route correspond à un composant qui est rendu lorsque la route est correspondante.
- **Navigation Dynamique** : Le routeur change dynamiquement entre les vues sans recharger la page, maintenant l'application réactive et rapide.

## Exemple de routage côté client dans webforJ {#example-of-client-side-routing-in-webforj}

Voici un exemple de définition d'une route pour un composant `UserProfileView` afin d'afficher les détails de l'utilisateur en fonction du paramètre `id` dans l'URL :

```java
@Route(value = "user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("");
    refreshProfile(id);
  }
}
```

Dans cette configuration :

- Naviguer vers `/user/john` rendrait le composant `UserProfileView`.
- Le paramètre `id` capturerait `john` depuis l'URL et vous permettrait de l'utiliser dans le composant pour récupérer et afficher les données utilisateur.

## Sujets {#topics}

<DocCardList className="topics-section" />
