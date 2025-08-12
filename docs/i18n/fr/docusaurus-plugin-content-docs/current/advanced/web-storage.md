---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Stockage Web <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Stockage web](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) est un concept fondamental en développement web qui permet aux sites web de stocker des données côté client. Cela permet aux applications web d'enregistrer l'état, les préférences et d'autres informations localement sur le navigateur de l'utilisateur. Le stockage web fournit un moyen de conserver les données entre les recharges de page et les sessions de navigateur, réduisant ainsi la nécessité de requêtes répétées vers le serveur et permettant des capacités hors ligne.

webforJ prend en charge trois mécanismes de stockage des données client : [**Cookies**](#cookies), [**Stockage de session**](#session-storage) et [**Stockage local**](#local-storage).

:::tip Stockage Web dans les Outils de Développement
Vous pouvez voir les paires clé-valeur actuelles des cookies, du stockage local et du stockage de session dans les outils de développement de votre navigateur.
:::

## Résumé des différences {#summary-of-differences}
| Fonctionnalité      | Cookies                                      | Stockage de session                          | Stockage local                            |
|---------------------|----------------------------------------------|----------------------------------------------|------------------------------------------|
| **Persistance**     | Date d'expiration configurable                | Durée de la session de la page              | Persistant jusqu'à suppression explicite |
| **Taille de stockage**| [4 Ko](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) par cookie                             | Environ [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) Mo                           | Environ [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) Mo                           |
| **Cas d'utilisation**| Authentification utilisateur, préférences, suivi   | Données temporaires, données de formulaire   | Paramètres persistants, préférences utilisateur    |
| **Sécurité**        | Vulnérable aux XSS, peut être sécurisé avec des drapeaux | Effacée à la fin de la session, moins de risque | Accessible via JavaScript, risque potentiel|

## Utilisation du stockage web {#using-web-storage}
Les classes <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> dans webforJ étendent toutes la classe abstraite <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink>. Pour obtenir l'objet approprié, utilisez les méthodes statiques `CookieStorage.getCurrent()`, `SessionStorage.getCurrent()` ou `LocalStorage.getCurrent()`. Pour ajouter, obtenir et supprimer des paires clé-valeur, utilisez les méthodes `add(key, value)`, `get(key)` et `remove(key)`.

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) sont de petits morceaux de données stockés côté client et envoyés au serveur avec chaque requête HTTP. Ils sont souvent utilisés pour mémoriser les sessions utilisateur, les préférences et les informations d'authentification. En plus des paires clé-valeur, les cookies peuvent également avoir des attributs. Pour définir des attributs pour les cookies, utilisez `add(key, value, attributes)`.

### Caractéristiques clés : {#key-features}
- Peut stocker des données à travers différents domaines
- Supporte les dates d'expiration
- Petite taille de stockage, généralement limitée à 4 Ko
- Envoyé avec chaque requête HTTP
- Peut avoir des attributs

:::info Expiration des cookies
Par défaut, les cookies dans webforJ expirent après 30 jours. Vous pouvez modifier cela avec les attributs `max-age` ou `expires`.
:::

### Utilisation des cookies {#using-cookies}

Le code suivant démontre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>.

```java
// Accéder au stockage des cookies
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Ajouter un nouveau cookie ou mettre à jour un cookie existant
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Accéder à un cookie avec une clé donnée
String username = cookieStorage.get("username");

// Supprimer un cookie avec une clé donnée
cookieStorage.remove("username");
```
:::info Sécurité des Cookies
Certains attributs des cookies, comme `Secure` et `SameSite=None`, nécessitent un contexte sécurisé utilisant HTTPS. Ces attributs garantissent que les cookies ne sont envoyés que sur des connexions sécurisées, les protégeant d'éventuelles interceptions. Pour plus d'informations, consultez la [documentation MDN sur la sécurité des cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Cas d'utilisation {#use-cases}
Les cas d'utilisation suivants sont bien adaptés à l'utilisation des cookies :

- **Authentification Utilisateur** : Stocker des jetons de session pour garder les utilisateurs connectés.
- **Préférences** : Enregistrer les préférences des utilisateurs, telles que les paramètres de thème ou la langue.
- **Suivi** : Collecter des informations sur le comportement des utilisateurs pour des analyses.

## Stockage de session {#session-storage}
Le stockage de session stocke des données pour la durée d'une session de page. Les données ne sont accessibles que dans la même session et sont effacées lorsque la page ou l'onglet est fermé. Cependant, les données persistent à travers les rechargements et les restaurations. Le stockage de session est idéal pour stocker des données temporaires pendant une seule session de page et maintenir l'état à travers différentes pages dans la même session.

### Caractéristiques clés {#key-features-1}
- Les données ne sont pas envoyées avec chaque requête HTTP
- Taille de stockage plus grande que celle des cookies
- Les données sont effacées lorsque la page ou l'onglet est fermé
- Les données ne sont pas partagées entre les onglets

### Utilisation du stockage de session dans webforJ {#using-session-storage-in-webforj}

Le code suivant démontre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>.

```java
// Accéder au stockage de session
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Ajouter une nouvelle paire ou mettre à jour une paire de stockage de session existante
sessionStorage.add("currentPage", "3");

// Accéder à une paire de stockage de session avec une clé donnée
String currentPage = sessionStorage.get("currentPage");

// Supprimer une paire de stockage de session avec une clé donnée
sessionStorage.remove("currentPage");
```

### Cas d'utilisation {#use-cases-1}
Les cas d'utilisation suivants sont bien adaptés à l'utilisation du stockage de session :

- **Stockage de Données Temporaire** : Stocker des données qui n'ont besoin de persister que pendant que l'utilisateur est sur une page particulière ou une session.
- **Données de Formulaire** : Sauvegarder temporairement des données de formulaire pour une utilisation au sein de la session.

## Stockage local {#local-storage}
Le stockage local stocke des données sans date d'expiration. Cela persiste même après la fermeture du navigateur et peut être accédé chaque fois que l'utilisateur revisite le site web. Le stockage local est idéal pour stocker les préférences ou les paramètres de l'utilisateur, mettre en cache des données pour améliorer les performances et sauvegarder l'état de l'application à travers les sessions.

### Caractéristiques clés {#key-features-2}

- Les données persistent à travers les sessions
- Les données ne sont pas envoyées avec chaque requête HTTP.
- Taille de stockage plus grande que celle des cookies
- Pas adapté aux données sensibles
- Vous devez gérer les données vous-même, car le navigateur ne les supprime jamais automatiquement

### Utilisation du stockage local dans webforJ {#using-local-storage-in-webforj}

Le code suivant démontre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink>.

```java
// Accéder au stockage local
LocalStorage localStorage = LocalStorage.getCurrent();

// Ajouter une nouvelle paire ou mettre à jour une paire de stockage local existante
localStorage.add("theme", "dark");

// Accéder à une paire de stockage local avec une clé donnée
String theme = localStorage.get("theme");

// Supprimer une paire de stockage local avec une clé donnée
localStorage.remove("theme");
```

### Cas d'utilisation {#use-cases-2}
Les cas d'utilisation suivants sont bien adaptés à l'utilisation du stockage local :

- **Données Persistantes** : Stocker des données qui doivent être disponibles à travers plusieurs sessions.
- **Préférences** : Enregistrer les paramètres et préférences des utilisateurs qui persistent dans le temps.
