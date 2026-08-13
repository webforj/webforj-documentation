---
title: Namespaces
sidebar_position: 40
description: >-
  Share thread-safe key-value state across sessions, thread groups, or the
  entire JVM using Private, Group, and Global namespaces.
_i18n_hash: fb5d7a0ef2a65790f0692612c07d9044
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Les espaces de noms dans webforJ fournissent un mécanisme pour stocker et récupérer des données partagées à travers différents scopes dans une application web. Ils permettent la communication de données entre composants et entre sessions sans dépendre des techniques de stockage traditionnelles comme les attributs de session ou les champs statiques. Cette abstraction permet aux développeurs d'encapsuler et d'accéder à l'état de manière contrôlée et sûre pour les threads. Les espaces de noms sont idéaux pour construire des outils de collaboration multi-utilisateur ou simplement maintenir des paramètres globaux cohérents, et vous permettent de coordonner les données en toute sécurité et efficacement.

## Qu'est-ce qu'un espace de noms ? {#whats-a-namespace}

Un espace de noms est un conteneur nommé qui stocke des paires clé-valeur. Ces valeurs peuvent être accédées et modifiées dans différentes parties de votre application selon le type d’espace de noms que vous utilisez. Pensez à cela comme à une carte distribuée, sûre pour les threads, avec des mécanismes de gestion d'événements et de verrouillage intégrés.

### Quand utiliser des espaces de noms {#when-to-use-namespaces}

Utilisez des espaces de noms lorsque :

- Vous devez partager des valeurs entre les sessions utilisateur ou les composants d'application.
- Vous souhaitez réagir aux changements de valeur via des écouteurs.
- Vous avez besoin d'un verrouillage fin pour des sections critiques.
- Vous devez persister et récupérer efficacement l'état dans votre application.

### Types d'espaces de noms {#types-of-namespaces}

webforJ propose trois types d'espaces de noms :

| Type        | Portée                                                                                                             | Utilisation typique                          |
|-------------|-------------------------------------------------------------------------------------------------------------------|---------------------------------------------|
| **Privé**   | Partagé entre les clients utilisant le même préfixe et le même nom. La mémoire est libérée automatiquement lorsqu'il n'y a plus de références. | État partagé entre des sessions utilisateur liées. |
| **Groupe**  | Partagé par tous les threads générés à partir du même thread parent.                                              | Coordination de l'état au sein d'un groupe de threads.  |
| **Global**  | Accessible à travers tous les threads du serveur (étendue JVM). La mémoire est retenue jusqu'à ce que les clés soient explicitement supprimées. | État partagé à l'échelle de l'application. |

:::tip Choisir un par défaut - Privilégier `PrivateNamespace`
En cas de doute, utilisez un `PrivateNamespace`. Il offre un partage sûr et adapté entre des sessions liées sans affecter l'état global ou à l'échelle du serveur. Cela en fait un choix de base fiable pour la plupart des applications.
:::

## Créer et utiliser un espace de noms {#creating-and-using-a-namespace}

Les espaces de noms sont créés en instaurant l'un des types disponibles. Chaque type définit comment et où les données sont partagées. Les exemples ci-dessous démontrent comment créer un espace de noms et interagir avec ses valeurs.

### Espace de noms `Privé` {#private-namespace}

Le nom de l'espace de noms privé se compose de deux parties :

- **Préfixe** : Un identifiant défini par le développeur qui doit être unique à votre application ou module pour éviter les conflits.
- **Nom de base** : Le nom spécifique pour le contexte ou les données partagées que vous souhaitez gérer.

Ensemble, ils forment le nom complet de l'espace de noms en utilisant le format :

```text
prefix + "." + baseName
```

Par exemple, `"myApp.sharedState"`.

Les espaces de noms créés avec le même préfixe et le même nom de base font toujours référence à la _même instance sous-jacente_. Cela garantit un accès partagé cohérent dans tous les appels à `PrivateNamespace` utilisant les mêmes identifiants.

```java
// Créer ou récupérer un espace de noms privé
PrivateNamespace ns = new PrivateNamespace("myApp", "sharedState");
```

Vous pouvez vérifier l'existence avant la création :

```java
if (PrivateNamespace.isPresent("myApp.sharedState")) {
  PrivateNamespace ns = PrivateNamespace.ofExisting("myApp.sharedState");
}
```

:::tip Directives de nommage
Lors de la nomination d'un `PrivateNamespace`, suivez ces règles :

- Les deux parties doivent être non vides.
- Chacune doit commencer par une lettre.
- Seuls des caractères imprimables sont autorisés.
- Les espaces ne sont pas permis.

Exemples :
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (trop générique, susceptible de conflit)
:::

### Espaces de noms `Groupe` et `Global` {#group-and-global-namespaces}

En plus de `PrivateNamespace`, webforJ fournit deux autres types pour des contextes de partage plus larges. Ceux-ci sont utiles lorsque l'état doit persister au-delà d'une seule session ou d'un groupe de threads.

- **Espace de noms global** : Accessible à travers tous les threads du serveur (étendue JVM).
- **Espace de noms de groupe** : Partagé parmi les threads qui proviennent du même parent.

```java
// État partagé global, accessible à l'échelle de l'application
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// État spécifique au groupe, limité aux threads partageant un parent commun
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Travailler avec des valeurs {#working-with-values}

Les espaces de noms fournissent une interface cohérente pour gérer les données partagées à travers des paires clé-valeur. Cela inclut la définition, la récupération, la suppression de valeurs, la synchronisation d'accès et l'observation des changements en temps réel.

### Définir et supprimer des valeurs {#setting-and-removing-values}

Utilisez `put()` pour stocker une valeur sous une clé spécifique. Si la clé est actuellement verrouillée, la méthode attend jusqu'à ce que le verrou soit libéré ou que le délai expire.

```java
// Attend jusqu'à 20 ms (par défaut) pour définir la valeur
ns.put("username", "admin");

// Spécifiez un délai d'attente personnalisé en millisecondes
ns.put("config", configObject, 100);
```

Pour supprimer une clé de l'espace de noms :

```java
ns.remove("username");
```

Les deux opérations `put()` et `remove()` sont bloquantes si la clé cible est verrouillée. Si le délai expire avant que le verrou soit libéré, une `NamespaceLockedException` est levée.

Pour des mises à jour concurrentes sûres où vous devez seulement écraser la valeur, utilisez `atomicPut()`. Cela verrouille la clé, écrit la valeur et libère le verrou en une seule étape :

```java
ns.atomicPut("counter", 42);
```

Cela empêche les conditions de course et évite la nécessité d'un verrouillage manuel dans des scénarios de mise à jour simples.

### Récupérer des valeurs {#getting-values}

Pour récupérer une valeur, utilisez `get()` :

```java
Object value = ns.get("username");
```

Si la clé n'existe pas, cela lève une `NoSuchElementException`. Pour éviter les exceptions, utilisez `getOrDefault()` :

```java
Object value = ns.getOrDefault("username", "guest");
```

Pour vérifier si une clé est définie :

```java
if (ns.contains("username")) {
  // la clé existe
}
```

Si vous souhaitez initialiser paresseusement une valeur uniquement lorsqu'elle est manquante, utilisez `computeIfAbsent()` :

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Ceci est utile pour les valeurs partagées qui sont créées une fois et réutilisées, telles que les jetons de session, les blocs de configuration ou les données mises en cache.

### Verrouillage manuel {#manual-locking}

Si vous devez effectuer plusieurs opérations sur la même clé ou coordonner plusieurs clés, utilisez le verrouillage manuel.

```java
ns.setLock("flag", 500); // Attendre jusqu'à 500 ms pour le verrou

// La section critique commence
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// La section critique se termine

ns.removeLock("flag");
```

Utilisez ce modèle lorsque une séquence d'opérations doit être effectuée de manière atomique entre lectures et écritures. Assurez-vous toujours que le verrou est libéré pour éviter de bloquer d'autres threads.

### Écoute des changements {#listening-for-changes}

Les espaces de noms prennent en charge des écouteurs d'événements qui vous permettent de réagir à l'accès ou à la modification des valeurs. Ceci est utile dans des scénarios tels que :

- Journaliser ou auditer l'accès à des clés sensibles
- Déclencher des mises à jour lorsque la valeur de configuration change
- Surveiller les changements d'état partagé dans des applications multi-utilisateurs

#### Méthodes d'écouteur disponibles {#available-listener-methods}

| Méthode                    | Déclenchement                   | Portée             |
|----------------------------|---------------------------------|--------------------|
| `onAccess`                 | Toute clé est lue               | Tout l'espace de noms |
| `onChange`                 | Toute clé est modifiée          | Tout l'espace de noms |
| `onKeyAccess("key")`      | Une clé spécifique est lue      | Par clé            |
| `onKeyChange("key")`      | Une clé spécifique est modifiée  | Par clé            |

Chaque écouteur reçoit un objet d'événement contenant :
- Le nom de la clé
- La vieille valeur
- La nouvelle valeur
- Une référence à l'espace de noms

#### Exemple : Réagir à tout changement de clé {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("Clé changée : " + event.getVariableName());
  System.out.println("Ancienne valeur : " + event.getOldValue());
  System.out.println("Nouvelle valeur : " + event.getNewValue());
});
```

#### Exemple : Suivre l'accès à une clé spécifique {#example-track-access-to-a-specific-key}

```java
ns.onKeyAccess("sessionToken", event -> {
  System.out.println("Le jeton a été accédé : " + event.getNewValue());
});
```

Les écouteurs retournent un objet `ListenerRegistration` que vous pouvez utiliser pour désinscrire l'écouteur ultérieurement :

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logique
});
reg.remove();
```

## Exemple : Partager l'état du jeu dans Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

La [démo Tic-Tac-Toe de webforJ](https://github.com/webforj/webforj-tictactoe) fournit un simple jeu à deux joueurs où les tours sont partagés entre les utilisateurs. Le projet démontre comment `Namespace` peut être utilisé pour coordonner l'état sans dépendre d'outils externes comme des bases de données ou des API.

Dans cet exemple, un objet de jeu Java partagé est stocké dans un `PrivateNamespace`, permettant à plusieurs clients d'interagir avec la même logique de jeu. L'espace de noms sert de conteneur central pour l'état du jeu, garantissant que :

- Les deux joueurs voient des mises à jour cohérentes du plateau
- Les tours sont synchronisés
- La logique du jeu est partagée entre les sessions

Aucun service externe (comme REST ou WebSockets) n'est nécessaire. Toute la coordination se fait à travers des espaces de noms, soulignant leur capacité à gérer un état partagé en temps réel avec une infrastructure minimale.

Explorez le code : [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
