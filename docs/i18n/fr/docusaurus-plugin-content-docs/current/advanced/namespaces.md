---
title: Namespaces
sidebar_position: 30
_i18n_hash: f3d79da01b17871bddf7543682a5e7e5
---
<DocChip chip='since' label='24.22' />
<JavadocLink type="foundation" location="com/webforj/environment/namespace/Namespace" top='true'/>

Les espaces de noms dans webforJ fournissent un mécanisme pour stocker et récupérer des données partagées à travers différents contextes dans une application web. Ils permettent la communication de données entre composants et entre sessions sans dépendre de techniques de stockage traditionnelles comme les attributs de session ou les champs statiques. Cette abstraction permet aux développeurs d'encapsuler et d'accéder à l'état de manière contrôlée et sécurisée par les threads. Les espaces de noms sont idéaux pour construire des outils de collaboration multi-utilisateurs ou simplement maintenir des paramètres globaux cohérents, et vous permettent de coordonner des données en toute sécurité et efficacement.

## Qu'est-ce qu'un espace de noms ? {#whats-a-namespace}

Un espace de noms est un conteneur nommé qui stocke des paires clé-valeur. Ces valeurs peuvent être accessibles et modifiées à travers différentes parties de votre application en fonction du type d'espace de noms que vous utilisez. Vous pouvez le considérer comme une carte distribuée et sécurisée par les threads avec des mécanismes de gestion d'événements et de verrouillage intégrés.

### Quand utiliser des espaces de noms {#when-to-use-namespaces}

Utilisez des espaces de noms lorsque :

- Vous avez besoin de partager des valeurs entre des sessions utilisateur ou des composants d'application.
- Vous souhaitez réagir aux changements de valeur via des écouteurs.
- Vous nécessite un verrouillage précis pour des sections critiques.
- Vous devez conserver et récupérer l'état efficacement à travers votre application.

### Types d'espaces de noms {#types-of-namespaces}

webforJ propose trois types d'espaces de noms :

| Type        | Portée                                                                                                               | Utilisation typique                           |
| ----------- | ------------------------------------------------------------------------------------------------------------------- | --------------------------------------------- |
| **Privé**   | Partagé entre les clients qui utilisent le même préfixe et nom. La mémoire est libérée automatiquement lorsqu'il n'y a plus de références. | État partagé entre les sessions utilisateur liées. |
| **Groupe**  | Partagé par tous les threads générés à partir du même thread parent.                                                | Coordination de l'état au sein d'un groupe de threads. |
| **Global**  | Accessible à travers tous les threads du serveur (à l'échelle de la JVM). La mémoire est conservée jusqu'à ce que les clés soient explicitement supprimées. | État partagé à l'échelle de l'application.    |

:::tip Choisir un défaut - Préférez `PrivateNamespace`
En cas de doute, utilisez un `PrivateNamespace`. Il offre un partage sécurisé et scopie entre les sessions liées sans affecter l'état global ou à l'échelle du serveur. Cela en fait un choix par défaut fiable pour la plupart des applications. 
:::

## Créer et utiliser un espace de noms {#creating-and-using-a-namespace}

Les espaces de noms sont créés en instanciant l'un des types disponibles. Chaque type définit comment et où les données sont partagées. Les exemples ci-dessous démontrent comment créer un espace de noms et interagir avec ses valeurs.

### Espace de noms `Privé` {#private-namespace}

Le nom de l'espace de noms privé est constitué de deux parties :

- **Préfixe** : Un identifiant défini par le développeur qui doit être unique à votre application ou module pour éviter les conflits.
- **Nom de base** : Le nom spécifique du contexte partagé ou des données que vous souhaitez gérer.

Ensemble, ils forment le nom complet de l'espace de noms en utilisant le format :

```text
préfixe + "." + nomDeBase
```

Par exemple, `"myApp.sharedState"`.

Les espaces de noms créés avec le même préfixe et nom de base font toujours référence à la _même instance sous-jacente_. Cela garantit un accès partagé cohérent dans tous les appels à `PrivateNamespace` utilisant les mêmes identifiants.

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
- Seuls les caractères imprimables sont autorisés.
- Les espaces ne sont pas autorisés.

Exemples :
- ✓ mycrm.sessionData
- ✓ acme.analytics
- X shared.data (trop générique, susceptible de conflit)
:::

### Espaces de noms `Groupe` et `Global` {#group-and-global-namespaces}

En plus de PrivateNamespace, webforJ fournit deux autres types pour des contextes de partage plus larges. Ceux-ci sont utiles lorsque l'état doit persister au-delà d'une seule session ou d'un groupe de threads.

- **Espace de noms Global** : Accessible à travers tous les threads du serveur (à l'échelle de la JVM).
- **Espace de noms de Groupe** : Partagé entre les threads qui proviennent du même parent.

```java
// État partagé global, accessible à l'échelle de l'application
GlobalNamespace globalNs = new GlobalNamespace();
globalNs.put("globalTheme", "dark");

// État spécifique au groupe, limité aux threads partageant un parent commun
GroupNamespace groupNs = new GroupNamespace();
groupNs.put("localCache", new HashMap<>());
```

## Travailler avec les valeurs {#working-with-values}

Les espaces de noms fournissent une interface cohérente pour gérer des données partagées via des paires clé-valeur. Cela inclut la définition, la récupération, la suppression de valeurs, la synchronisation d'accès et l'observation des changements en temps réel.

### Définir et retirer des valeurs {#setting-and-removing-values}

Utilisez `put()` pour stocker une valeur sous une clé spécifique. Si la clé est actuellement verrouillée, la méthode attend jusqu'à ce que le verrou soit libéré ou que le délai expire.

```java
// Attend jusqu'à 20ms (par défaut) pour définir la valeur
ns.put("username", "admin");

// Spécifiez un délai personnalisé en millisecondes
ns.put("config", configObject, 100);
```

Pour retirer une clé de l'espace de noms :

```java
ns.remove("username");
```

Les opérations `put()` et `remove()` sont des opérations bloquantes si la clé cible est verrouillée. Si le délai expire avant que le verrou ne soit libéré, une `NamespaceLockedException` est levée.

Pour des mises à jour concurrentes sûres où vous devez simplement écraser la valeur, utilisez `atomicPut()`. Cela verrouille la clé, écrit la valeur et libère le verrou en une seule étape :

```java
ns.atomicPut("counter", 42);
```

Cela empêche les conditions de course et évite le besoin de verrouillage manuel dans des scénarios de mise à jour simples.

### Obtenir des valeurs {#getting-values}

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

Si vous souhaitez initialiser paresseusement une valeur seulement quand elle est manquante, utilisez `computeIfAbsent()` :

```java
Object token = ns.computeIfAbsent("authToken", key -> generateToken());
```

Ceci est utile pour des valeurs partagées qui sont créées une fois et réutilisées, telles que des jetons de session, des blocs de configuration ou des données mises en cache.

### Verrouillage manuel {#manual-locking}

Si vous devez effectuer plusieurs opérations sur la même clé ou coordonner plusieurs clés, utilisez le verrouillage manuel.

```java
ns.setLock("flag", 500); // Attendez jusqu'à 500ms pour le verrou

// La section critique commence
Object existing = ns.get("flag");
ns.put("flag", "in-progress");
// La section critique se termine

ns.removeLock("flag");
```

Utilisez ce modèle lorsque une séquence d'opérations doit être effectuée de manière atomique sur des lectures et des écritures. Assurez-vous toujours que le verrou est libéré pour éviter de bloquer d'autres threads.

### Écouter les changements {#listening-for-changes}

Les espaces de noms prennent en charge les écouteurs d'événements qui vous permettent de réagir à l'accès ou à la modification de valeurs. Cela est utile pour des scénarios tels que :

- Journaliser ou auditer l'accès à des clés sensibles
- Déclencher des mises à jour lorsque la valeur d'une configuration change
- Surveiller les changements d'état partagé dans des applications multi-utilisateurs

#### Méthodes d'écouteur disponibles {#available-listener-methods}

| Méthode                   | Déclencheur                     | Portée              |
|---------------------------|----------------------------------|---------------------|
| `onAccess`                | Toute clé est lue               | Tout l'espace de noms |
| `onChange`                | Toute clé est modifiée          | Tout l'espace de noms |
| `onKeyAccess("key")`      | Une clé spécifique est lue      | Par clé             |
| `onKeyChange("key")`      | Une clé spécifique est modifiée  | Par clé             |

Chaque écouteur reçoit un objet événement contenant :
- Le nom de la clé
- L'ancienne valeur
- La nouvelle valeur
- Une référence à l'espace de noms

#### Exemple : Répondre à tout changement de clé {#example-respond-to-any-key-change}

```java
ns.onChange(event -> {
  System.out.println("La clé a changé : " + event.getVariableName());
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

Les écouteurs retournent un objet `ListenerRegistration` que vous pouvez utiliser pour désinscrire l'écouteur plus tard :

```java
ListenerRegistration<NamespaceKeyChangeEvent> reg = ns.onKeyChange("status", event -> {
  // logique
});
reg.remove();
```

## Exemple : Partager l'état du jeu dans Tic-Tac-Toe {#example-sharing-game-state-in-tic-tac-toe}

La [démo Tic-Tac-Toe de webforJ](https://github.com/webforj/webforj-tictactoe) fournit un simple jeu à deux joueurs où les tours sont partagés entre les utilisateurs. Le projet démontre comment `Namespace` peut être utilisé pour coordonner l'état sans dépendre d'outils externes comme des bases de données ou des API.

Dans cet exemple, un objet de jeu Java partagé est stocké dans un `PrivateNamespace`, permettant à plusieurs clients d'interagir avec la même logique de jeu. L'espace de noms sert de conteneur central pour l'état du jeu, garantissant que :

- Les deux joueurs voient des mises à jour cohérentes du tableau
- Les tours sont synchronisés
- La logique du jeu est partagée entre les sessions

Aucun service externe (comme REST ou WebSockets) n'est nécessaire. Toute la coordination se fait par le biais des espaces de noms, mettant en évidence leur capacité à gérer l'état partagé en temps réel avec une infrastructure minimale.

Explorez le code : [webforj/webforj-tictactoe](https://github.com/webforj/webforj-tictactoe)

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tic-tac-toe.mp4" type="video/mp4"/>
  </video>
</div>
