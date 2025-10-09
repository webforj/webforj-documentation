---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing est une technologie de serveur web qui permet aux applications de bureau Java (Swing, JavaFX, SWT) de s'exécuter dans un navigateur web sans aucune modification du code source d'origine. Il rend l'application de bureau sur le serveur et diffuse l'interface vers le navigateur en utilisant le canevas HTML5, gérant toutes les interactions utilisateur de manière transparente.

## Ce que Webswing résout

De nombreuses organisations ont des investissements substantiels dans des applications de bureau Java qui contiennent une logique métier critique développée sur des années ou des décennies. Ces applications ne peuvent souvent pas être facilement réécrites en raison de :

- Logique de domaine complexe qui serait risquée à recréer
- Intégration avec des bibliothèques ou du matériel spécifiques au bureau
- Contraintes de temps et de coûts d'une réécriture complète
- Besoin de maintenir la parité fonctionnelle avec la fonctionnalité existante

Webswing permet à ces applications d'être accessibles via le web sans modification, préservant leur fonctionnalité et leur apparence d'origine.

## Intégration avec webforJ

L'intégration Webswing de webforJ fournit le composant `WebswingConnector`, qui vous permet d'incorporer des applications hébergées par Webswing directement dans votre application webforJ. Cela crée des opportunités pour :

### Modernisation progressive

Au lieu d'une réécriture totale, vous pouvez :

1. Commencer par intégrer votre application Swing existante via `WebswingConnector`
2. Construire de nouvelles fonctionnalités dans webforJ autour de l'application intégrée
3. Remplacer progressivement les composants Swing par des équivalents webforJ
4. Éventuellement retirer complètement l'application hérité

### Applications hybrides

Combinez une interface web moderne construite avec webforJ avec une fonctionnalité de bureau spécialisée :

- Utilisez webforJ pour les interfaces orientées utilisateur, les tableaux de bord et les rapports
- Exploitez Swing pour des visualisations complexes ou des éditeurs spécialisés
- Maintenez une expérience d'application intégrée unique

## Comment ça fonctionne

L'intégration fonctionne à travers trois couches :

1. **Serveur Webswing** : exécute votre application de bureau Java, capturant sa sortie visuelle et traitant les entrées des utilisateurs
2. **Composant WebswingConnector** : un composant webforJ qui intègre le client Webswing, gérant la connexion et la communication avec le serveur
3. **Protocole de communication** : messagerie bidirectionnelle qui permet à votre application webforJ d'envoyer des commandes à l'application Swing et de recevoir des événements en retour

Lorsqu'un utilisateur accède à votre application webforJ, le `WebswingConnector` établit une connexion au serveur Webswing. Le serveur crée ou se reconnecte à une instance d'application, et commence à diffuser l'état visuel vers le navigateur. Les interactions utilisateur (souris, clavier) sont capturées et envoyées au serveur, où elles sont rejouées sur la véritable application Swing.

## Topics {#topics}

<DocCardList className="topics-section" />
