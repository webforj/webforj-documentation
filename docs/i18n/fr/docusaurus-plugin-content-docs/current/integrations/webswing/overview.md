---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing est une technologie de serveur web qui permet aux applications desktop Java (Swing, JavaFX, SWT) de fonctionner dans un navigateur web sans aucune modification du code source d'origine. Il rend l'application desktop sur le serveur et diffuse l'interface vers le navigateur en utilisant le canvas HTML5, gérant toutes les interactions utilisateur de manière transparente.

## Ce que Webswing résout

De nombreuses organisations ont des investissements importants dans des applications desktop Java qui contiennent une logique métier critique développée au fil des ans ou des décennies. Ces applications ne peuvent souvent pas être réécrites facilement en raison de :

- Logique de domaine complexe qu'il serait risqué de recréer
- Intégration avec des bibliothèques ou du matériel spécifiques aux desktops
- Contraintes de temps et de coûts d'une réécriture complète
- Besoin de maintenir la parité des fonctionnalités avec la fonctionnalité existante

Webswing permet à ces applications d'être accessibles sur le web sans modification, préservant leur fonctionnalité et leur apparence d'origine.

## Intégration avec webforJ

L'intégration Webswing de webforJ fournit le composant `WebswingConnector`, qui vous permet d'incorporer des applications hébergées par Webswing directement dans votre application webforJ. Cela crée des opportunités pour :

### Modernisation progressive

Au lieu d'une réécriture totale, vous pouvez :

1. Commencer par intégrer votre application Swing existante via `WebswingConnector`
2. Développer de nouvelles fonctionnalités dans webforJ autour de l'application intégrée
3. Remplacer progressivement les composants Swing par leurs équivalents webforJ
4. Éventuellement, supprimer complètement l'application héritée

### Applications hybrides

Combinez une interface utilisateur web moderne construite avec webforJ avec des fonctionnalités desktop spécialisées :

- Utilisez webforJ pour les interfaces utilisateur, tableaux de bord et rapports
- Exploitez Swing pour des visualisations complexes ou des éditeurs spécialisés
- Maintenez une seule expérience d'application intégrée

## Comment ça fonctionne

L'intégration fonctionne à travers trois couches :

1. **Serveur Webswing** : exécute votre application desktop Java, capturant son rendu visuel et traitant les entrées utilisateur
2. **Composant WebswingConnector** : un composant webforJ qui intègre le client Webswing, gérant la connexion et la communication avec le serveur
3. **Protocole de communication** : messagerie bidirectionnelle qui permet à votre application webforJ d'envoyer des commandes à l'application Swing et de recevoir des événements en retour

Lorsqu'un utilisateur accède à votre application webforJ, le `WebswingConnector` établit une connexion avec le serveur Webswing. Le serveur crée ou se reconnecte à une instance d'application et commence à diffuser l'état visuel vers le navigateur. Les interactions utilisateur (souris, clavier) sont capturées et envoyées au serveur, où elles sont rejouées sur l'application Swing réelle.

## Topics {#topics}

<DocCardList className="topics-section" />
