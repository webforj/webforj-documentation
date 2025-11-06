---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Architecture de sécurité <DocChip chip='since' label='25.10' />

Le système de sécurité webforJ est construit sur une base d'interfaces et de modèles de conception qui permettent une protection des routes flexible et extensible. Cette section explique comment fonctionne le cadre de sécurité fondamental, et comment construire des solutions de sécurité personnalisées en implémentant ces interfaces.

:::tip[Intégration avec Spring]
La plupart des applications devraient utiliser l'[intégration avec Spring Security](/docs/security/getting-started), car elle configure automatiquement tout cela pour vous. Implémentez une sécurité personnalisée uniquement si vous avez des exigences spécifiques ou si vous n'utilisez pas Spring Boot. L'intégration Spring est construite sur cette même architecture de base.
:::

Vous apprendrez sur les interfaces principales, le modèle de chaîne d'évaluateurs, comment la navigation est interceptée et évaluée, et différentes approches pour stocker l'état d'authentification.

:::info[Concentrez-vous sur l'architecture et les points d'extension]
Ces guides expliquent l'architecture fondamentale et les points d'extension, les interfaces que vous implémentez, et comment elles fonctionnent ensemble. Les exemples de code montrent **une approche possible**, sans exigences prescriptives. Votre implémentation peut utiliser différents mécanismes de stockage (JWT, base de données, LDAP), différents modèles de câblage ou différents flux d'authentification en fonction de vos besoins.
:::

## Ce que vous apprendrez {#what-youll-learn}

- **Architecture de base** : Les interfaces principales qui définissent le comportement de sécurité et comment elles fonctionnent ensemble
- **Interception de navigation** : Comment le système de sécurité intercepte les requêtes de navigation et évalue les règles d'accès
- **Modèle de chaîne d'évaluateurs** : Comment les règles de sécurité sont évaluées par ordre de priorité à l'aide du modèle de chaîne de responsabilité
- **Stockage d'authentification** : Différentes approches pour stocker l'état d'authentification des utilisateurs (sessions, JWT, base de données, etc.)
- **Implémentation complète** : Un exemple fonctionnel montrant tous les composants connectés

## À qui cela s'adresse {#who-this-is-for}

Ces guides s'adressent aux développeurs qui souhaitent :

- Construire des implémentations de sécurité personnalisées pour des applications non-Spring
- Comprendre l'architecture fondamentale pour résoudre des problèmes
- Implémenter des flux d'authentification personnalisés ou une logique d'autorisation
- Créer des évaluateurs de sécurité avec une logique spécifique au domaine
- Intégrer des systèmes d'authentification existants (LDAP, OAuth, backends personnalisés)

## Prérequis {#prerequisites}

Avant de plonger dans ces guides, vous devriez :

- Compléter le [guide de démarrage](/docs/security/getting-started) pour comprendre les concepts de sécurité
- Comprendre les annotations de sécurité à partir du [guide des annotations](/docs/security/annotations)
- Être familiarisé avec le modèle de chaîne de responsabilité
- Avoir de l'expérience avec les interfaces Java et l'héritage

## Sujets {#topics}

<DocCardList className="topics-section" />
