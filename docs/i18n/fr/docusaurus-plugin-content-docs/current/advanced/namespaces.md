---
title: Espaces de noms
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: f932a175d81df4147cae1335991db3d5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Les espaces de noms dans webforJ fournissent un mécanisme permettant de stocker et de récupérer des données partagées dans différentes portées d’une application web. Ils permettent la communication de données entre composants et entre sessions, sans recourir aux techniques de stockage traditionnelles comme les attributs de session ou les champs statiques. Cette abstraction permet aux développeurs d’encapsuler et d’accéder à l’état de manière contrôlée et thread-safe. Les espaces de noms sont idéaux pour créer des outils de collaboration multi-utilisateur ou simplement maintenir des paramètres globaux cohérents, et permettent de coordonner les données de manière sûre et efficace.

## Qu’est-ce qu’un espace de noms ? {#whats-a-namespace}

Un espace de noms est un conteneur nommé qui stocke des paires clé-valeur. Ces valeurs peuvent être consultées et modifiées dans différentes parties de votre application, selon le type d’espace de noms utilisé. Voyez-le comme une map distribuée et thread-safe, dotée de mécanismes intégrés de gestion des événements et de verrouillage.

### Quand utiliser les espaces de noms {#when-to-use-namespaces}

Utilisez les espaces de noms lorsque :

- Vous devez partager des valeurs entre des sessions utilisateur ou des composants de l’application.
- Vous souhaitez réagir aux changements de valeur à l’aide d’écouteurs.
- Vous avez besoin d’un verrouillage précis pour les sections critiques.
- Vous devez conserver et récupérer efficacement l’état dans l’ensemble de votre application.

### Types d’espaces de noms {#types-of-namespaces}

webforJ propose trois types d’espaces de noms :

| Type        | Portée                                                                                                               | Utilisation courante                          |
| ----------- | -------------------------------------------------------------------------------------------------------------------- | -------------------------------------------- |
| **Private** | Partagé entre les clients qui utilisent le même préfixe et le même nom. La mémoire est automatiquement libérée lorsqu’il ne reste plus aucune référence. | État partagé entre des sessions utilisateur associées. |
| **Group**   | Partagé par tous les threads générés à partir du même thread parent.                                                  | Coordination de l’état au sein d’un groupe de threads. |
| **Global**  | Accessible depuis tous les threads du serveur (à l’échelle de la JVM). La mémoire est conservée jusqu’à la suppression explicite des clés. | État partagé à l’échelle de l’application. |

:::tip Choisir une valeur par défaut - Préférez `PrivateNamespace`
En cas de doute, utilisez un `PrivateNamespace`. Il permet un partage sûr et délimité entre des sessions associées, sans affecter l’état global ou celui du serveur. C’est donc une valeur par défaut fiable pour la plupart des applications.
:::

## Créer et utiliser un espace de noms {#creating-and-using-a-namespace}

Les espaces de noms sont créés en instanciant l’un des types disponibles. Chaque type définit la façon dont les données sont partagées et l’endroit où elles le sont. Les exemples ci-dessous montrent comment créer un espace de noms et interagir avec ses valeurs.

### Espace de noms `Private` {#private-namespace}

Le nom d’un espace de noms privé est composé de deux parties :

- **Préfixe** : un identifiant défini par le développeur, qui doit être unique à votre application ou à votre module afin d’éviter les conflits.
- **Nom de base** : le nom spécifique du contexte ou des données partagés que vous souhaitez gérer.

Ensemble, ils forment le nom complet de l’espace de noms selon le format suivant :

```text
prefix + "." + baseName
```

Par exemple, `"myApp.sharedState"`.

Les espaces de noms créés avec le même préfixe et le même nom de base font toujours référence à la _même instance sous-jacente_. Cela garantit un accès partagé cohérent lors de tous les appels à `PrivateNamespace` utilisant les mêmes identifiants.

```java
// Create or retrieve a private namespace
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Vous pouvez vérifier si l’espace de noms existe avant de le créer :

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Conventions de nommage
Lors de la création d’un nom pour un `PrivateNamespace`, suivez ces règles :

- Les deux parties doivent être non vides.
- Chacune doit commencer par une lettre.
- Seuls les caractères imprimables sont autorisés.
- Les espaces blancs ne sont pas autorisés.

Exemples :
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (trop générique, risque élevé de conflit)
:::

### Espaces de noms `Group` et `Global` {#group-and-global-namespaces}

En plus de `PrivateNamespace`, webforJ propose deux autres types permettant un partage plus large. Ils sont utiles lorsque l’état doit persister au-delà d’une seule session ou d’un seul groupe de threads.

- **Espace de noms global** : accessible depuis tous les threads du serveur (à l’échelle de la JVM).
- **Espace de noms de groupe** : partagé entre les threads issus du même parent.

```java
// Global shared state, accessible application-wide
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// Group-specific state, limited to threads sharing a common parent
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Utiliser les valeurs {#working-with-values}

Les espaces de noms proposent une interface cohérente pour gérer les données partagées sous forme de paires clé-valeur. Cela inclut la définition, la récupération et la suppression des valeurs, la synchronisation des accès et l’observation des changements en temps réel.

### Définir et supprimer des valeurs {#setting-and-removing-values}

Utilisez `put()` pour stocker une valeur sous une clé précise. Si la clé est verrouillée, la méthode attend que le verrou soit libéré ou que le délai d’attente expire.

```java
// Waits up to 20ms (default) to set the value
ns.put("username", "admin");

// Specify custom timeout in milliseconds
ns.put("config", configObject, 100);
```

Pour supprimer une clé de l’espace de noms :

```java
ns.remove("username");
```

Les opérations `put()` et `remove()` sont toutes deux bloquantes si la clé cible est verrouillée. Si le délai d’attente expire avant la libération du verrou, une `NamespaceLockedException` est levée.

Pour effectuer des mises à jour concurrentes de manière sûre lorsque vous souhaitez seulement remplacer la valeur, utilisez `atomicPut()`. Cette méthode verrouille la clé, écrit la valeur et libère le verrou en une seule étape :

```java
ns.atomicPut("counter", 42);
```

Cela évite les conditions de concurrence et la nécessité d’un verrouillage manuel dans les cas simples de mise à jour.

### Récupérer des valeurs {#getting-values}

Pour récupérer une valeur, utilisez `get()` :

```java
Object value = ns.get("username");
```

Si la clé n’existe pas, une `NoSuchElementException` est levée. Pour éviter les exceptions, utilisez `getOrDefault()` :

```java
Object value = ns.getOrDefault("username", "guest");
```

Pour vérifier si une clé est définie :

```java
if (ns.contains("username")) {
  // key exists
}
```

Si vous souhaitez initialiser une valeur de manière différée uniquement lorsqu’elle est absente, utilisez `computeIfAbsent()` :

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Cette méthode est utile pour les valeurs partagées créées une seule fois et réutilisées, comme les jetons de session, les blocs de configuration ou les données mises en cache.

### Verrouillage manuel {#manual-locking}

Si vous devez effectuer plusieurs opérations sur une même clé ou coordonner des opérations sur plusieurs clés, utilisez le verrouillage manuel.

```java
ns.setLock("flag", 500); // Wait up to 500ms for the lock

// Critical section starts
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// Critical section ends

ns.removeLock("flag");
```

Utilisez ce modèle lorsqu’une séquence d’opérations doit être exécutée de manière atomique, à la fois pour les lectures et les écritures. Veillez toujours à libérer le verrou afin d’éviter de bloquer les autres threads.

### Écouter les changements {#listening-for-changes}

Les espaces de noms prennent en charge les écouteurs d’événements, qui permettent de réagir à l’accès aux valeurs ou à leur modification. Cela peut être utile dans des situations telles que :

- Consigner ou auditer l’accès aux clés sensibles
- Déclencher des mises à jour lorsqu’une valeur de configuration change
- Surveiller les changements d’état partagé dans les applications multi-utilisateur

#### Méthodes d’écoute disponibles {#available-listener-methods}

| Méthode                   | Déclencheur                    | Portée            |
|---------------------------|--------------------------------|-------------------|
| `onAccess`                | Lecture d’une clé quelconque   | Tout l’espace de noms |
| `onChange`                | Modification d’une clé quelconque | Tout l’espace de noms |
| `onKeyAccess("key")`      | Lecture d’une clé précise      | Par clé           |
| `onKeyChange("key")`      | Modification d’une clé précise | Par clé           |

Chaque écouteur reçoit un objet événement contenant :
- Le nom de la clé
- L’ancienne valeur
- La nouvelle valeur
- Une référence à l’espace de noms

#### Exemple : réagir au changement de n’importe quelle clé {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Key changed: " + event.getVariableName());
  System.out.println("Old value: " + event.getOldValue());
  System.out.println("New value: " + event.getNewValue());
});
```

#### Exemple : suivre l’accès à une clé précise {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Token was accessed: " + event.getNewValue());
});
```

Les écouteurs renvoient un objet `ListenerRegistration` que vous pouvez utiliser pour désenregistrer l’écouteur ultérieurement :

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logic
});
reg.remove();
```

## Exemple : partager l’état d’un jeu de morpion {#example-sharing-game-state-in-tic-tac-toe}

La [démo webforJ de morpion](https://github.com/webforj/webforj-tictactoe) propose un jeu simple à deux joueurs dans lequel les tours sont partagés entre les utilisateurs. Le projet montre comment `Namespace` peut servir à coordonner l’état sans dépendre d’outils externes tels que des bases de données ou des API.

Dans cet exemple, un objet de jeu Java partagé est stocké dans un `PrivateNamespace`, ce qui permet à plusieurs clients d’interagir avec la même logique de jeu. L’espace de noms sert de conteneur central pour l’état du jeu, garantissant que :

- Les deux joueurs voient des mises à jour cohérentes du plateau
- Les tours sont synchronisés
- La logique du jeu est partagée entre les sessions

Aucun service externe (comme REST ou WebSockets) n’est nécessaire. Toute la coordination s’effectue au moyen des espaces de noms, ce qui met en évidence leur capacité à gérer un état partagé en temps réel avec une infrastructure minimale.

Consultez le code : [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<VideoPlayer video="namespaces/tic-tac-toe" width={1660} height={902} title="Tic tac toe" />
