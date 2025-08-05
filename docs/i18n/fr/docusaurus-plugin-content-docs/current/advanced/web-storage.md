---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Stockage Web <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Le stockage web](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) est un concept fondamental dans le développement web qui permet aux sites web de stocker des données côté client. Cela permet aux applications web de sauvegarder l'état, les préférences et d'autres informations localement dans le navigateur de l'utilisateur. Le stockage web fournit un moyen de persister des données à travers les rechargements de page et les sessions de navigateur, réduisant ainsi le besoin de demandes répétées au serveur et permettant des fonctionnalités hors ligne.

webforJ prend en charge trois mécanismes pour stocker des données client : [**Cookies**](#cookies), [**Stockage de session**](#session-storage), et [**Stockage local**](#local-storage).

:::tip Stockage Web dans les outils de développement
Vous pouvez voir les paires clé-valeur actuelles des cookies, du stockage local et du stockage de session dans les outils de développement de votre navigateur.
:::

## Résumé des différences {#summary-of-differences}
| Fonctionnalité      | Cookies                                      | Stockage de session                        | Stockage local                            |
|---------------------|----------------------------------------------|--------------------------------------------|------------------------------------------|
| **Persistance**     | Date d'expiration configurable                | Durée de la session de la page            | Persistant jusqu'à suppression explicite  |
| **Taille du stockage** | [4 Ko](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) par cookie                             | Environ [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) Mo                           | Environ [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) Mo                           |
| **Cas d'utilisation** | Authentification des utilisateurs, préférences, suivi | Données temporaires, données de formulaire | Paramètres persistants, préférences utilisateur |
| **Sécurité**        | Vulnérable aux XSS, peut être sécurisé avec des drapeaux | Effacée à la fin de la session, moins de risque | Accessible via JavaScript, risque potentiel |

## Utilisation du stockage web {#using-web-storage}
Les classes <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>, et <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> dans webforJ étendent toutes la classe abstraite <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink>. Pour obtenir l'objet approprié, utilisez les méthodes statiques `CookieStorage.getCurrent()`, `SessionStorage.getCurrent()`, ou `LocalStorage.getCurrent()`. Pour ajouter, obtenir et supprimer des paires clé-valeur, utilisez les méthodes `add(key, value)`, `get(key)`, et `remove(key)`.

## Cookies {#cookies}
[Les cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) sont de petites portions de données stockées côté client et envoyées au serveur avec chaque requête HTTP. Ils sont souvent utilisés pour se souvenir des sessions utilisateur, des préférences et des informations d'authentification. En plus des paires clé-valeur, les cookies peuvent également avoir des attributs. Pour définir des attributs pour les cookies, utilisez `add(key, value, attributes)`.

### Fonctionnalités clés : {#key-features}
- Peut stocker des données à travers différents domaines
- Supporte les dates d'expiration
- Taille de stockage petite, généralement limitée à 4 Ko
- Envoyé avec chaque requête HTTP
- Peut avoir des attributs

:::info Expiration des cookies
Par défaut, les cookies dans webforJ expirent après 30 jours. Vous pouvez changer cela avec les attributs `max-age` ou `expires`.
:::

### Utilisation des cookies {#using-cookies}

Le code suivant montre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>.

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
:::info Sécurité des cookies
Certains attributs de cookie, tels que `Secure` et `SameSite=None`, nécessitent un contexte sécurisé utilisant HTTPS. Ces attributs garantissent que les cookies ne sont envoyés que sur des connexions sécurisées, les protégeant d'être interceptés. Pour plus d'informations, consultez la [documentation MDN sur la sécurité des cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Cas d'utilisation {#use-cases}
Les cas d'utilisation suivants sont bien adaptés pour l'utilisation des cookies :

- **Authentification des utilisateurs** : Stocker des tokens de session pour garder les utilisateurs connectés.
- **Préférences** : Sauvegarder les préférences des utilisateurs, comme les paramètres de thème ou de langue.
- **Suivi** : Collecter des informations sur le comportement des utilisateurs pour l'analyse.


## Stockage de session {#session-storage}
Le stockage de session stocke des données pour la durée d'une session de page. Les données ne sont accessibles qu'au sein de la même session et sont effacées lorsque la page ou l'onglet est fermé. Cependant, les données persistent lors des rechargements et des restaurations. Le stockage de session est idéal pour stocker des données temporaires pendant une seule session de page et maintenir l'état à travers différentes pages dans la même session.

### Fonctionnalités clés {#key-features-1}
- Les données ne sont pas envoyées avec chaque requête HTTP
- Taille de stockage plus grande que celle des cookies
- Les données sont effacées lorsque la page ou l'onglet est fermé
- Les données ne sont pas partagées entre les onglets

### Utilisation du stockage de session dans webforJ {#using-session-storage-in-webforj}

Le code suivant montre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>.

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
Les cas d'utilisation suivants sont bien adaptés pour l'utilisation du stockage de session :

- **Stockage de données temporaires** : Stocker des données qui n'ont besoin de persister que pendant que l'utilisateur est sur une page ou une session particulière.
- **Données de formulaire** : Sauvegarder temporairement les données de formulaire pour une utilisation dans la session.

## Stockage local {#local-storage}
Le stockage local stocke des données sans date d'expiration. Il persiste même après la fermeture du navigateur et peut être accessible chaque fois que l'utilisateur revisite le site web. Le stockage local est idéal pour stocker des préférences ou des paramètres utilisateur, mettre en cache des données pour améliorer les performances, et sauvegarder l'état de l'application à travers les sessions.

### Fonctionnalités clés {#key-features-2}

- Les données persistent à travers les sessions
- Les données ne sont pas envoyées avec chaque requête HTTP.
- Taille de stockage plus grande que celle des cookies
- Pas adapté pour des données sensibles
- Vous devez gérer vous-même les données, car le navigateur ne les efface jamais automatiquement

### Utilisation du stockage local dans webforJ {#using-local-storage-in-webforj}

Le code suivant montre l'utilisation de l'objet <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink>.

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
Les cas d'utilisation suivants sont bien adaptés pour l'utilisation du stockage local :

- **Données persistantes** : Stocker des données qui doivent être disponibles à travers plusieurs sessions.
- **Préférences** : Sauvegarder les réglages et préférences des utilisateurs qui persistent dans le temps.
