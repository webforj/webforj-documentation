---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing est une technologie de serveur web qui permet aux applications de bureau Java (Swing, JavaFX, SWT) de fonctionner dans un navigateur web sans aucune modification du code source original. Il rend l'application de bureau sur le serveur et diffuse l'interface vers le navigateur en utilisant le canvas HTML5, gérant toutes les interactions utilisateur de manière transparente.

## Ce que Webswing résout {#what-webswing-solves}

De nombreuses organisations ont des investissements considérables dans des applications de bureau Java contenant une logique métier critique développée au fil des ans ou des décennies. Ces applications ne peuvent souvent pas être facilement réécrites en raison de :

- Logique de domaine complexe qui serait risqué de recréer
- Intégration avec des bibliothèques ou du matériel spécifiques au bureau
- Contraintes de temps et de coûts pour une réécriture complète
- Besoin de maintenir la parité fonctionnelle avec les fonctionnalités existantes

Webswing permet à ces applications d'être accessibles depuis le web sans modification, préservant leur fonctionnalité et apparence d'origine.

## Intégration avec webforJ {#integration-with-webforj}

L'intégration Webswing de webforJ fournit le composant `WebswingConnector`, qui vous permet d'incorporer des applications hébergées par Webswing directement dans votre application webforJ. Cela crée des opportunités pour :

### Modernisation progressive {#progressive-modernization}

Au lieu d'une réécriture totale, vous pouvez :

1. Commencer par intégrer votre application Swing existante via `WebswingConnector`
2. Construire de nouvelles fonctionnalités dans webforJ autour de l'application intégrée
3. Remplacer progressivement les composants Swing par des équivalents webforJ
4. Finalement éliminer complètement l'application héritée

### Applications hybrides {#hybrid-applications}

Combinez une interface utilisateur web moderne construite avec webforJ avec des fonctionnalités de bureau spécialisées :

- Utilisez webforJ pour les interfaces utilisateurs, tableaux de bord et rapports
- Exploitez Swing pour des visualisations complexes ou des éditeurs spécialisés
- Maintenez une expérience d'application intégrée unique

## Comment ça fonctionne {#how-it-works}

L'intégration fonctionne à travers trois couches :

1. **Serveur Webswing** : exécute votre application de bureau Java, capturant sa sortie visuelle et traitant les entrées utilisateur
2. **Composant WebswingConnector** : un composant webforJ qui intègre le client Webswing, gérant la connexion et la communication avec le serveur
3. **Protocole de communication** : messagerie bidirectionnelle qui permet à votre application webforJ d'envoyer des commandes à l'application Swing et de recevoir des événements en retour

Lorsque l'utilisateur accède à votre application webforJ, le `WebswingConnector` établit une connexion au serveur Webswing. Le serveur crée ou se reconnecte à une instance de l'application, et commence à diffuser l'état visuel vers le navigateur. Les interactions utilisateur (souris, clavier) sont capturées et envoyées au serveur, où elles sont rejouées sur l'application Swing réelle.

## Thèmes {#topics}

<DocCardList className="topics-section" />
