---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
L'`ObjectTable`, le `SessionObjectTable` et le `StringTable` fournissent un accès statique aux données partagées dans un environnement webforJ. Tous sont accessibles de n'importe où dans votre application et servent des objectifs différents :

- `ObjectTable` : Pour stocker et récupérer des objets Java dans toute votre application.
- `SessionObjectTable` : Pour stocker et récupérer des objets Java dans le scope de session HTTP.
- `StringTable` : Pour travailler avec des paires de chaînes clé-valeur persistantes, souvent utilisées pour des données de configuration ou de type environnement.

Ces tables sont disponibles au niveau de l'environnement et ne nécessitent pas de gestion d'instance.

## `ObjectTable` {#objecttable}

L'`ObjectTable` est une carte clé-valeur globalement accessible pour stocker n'importe quel objet Java. Elle fournit un accès simple à un état partagé sans avoir besoin d'instancier ou de configurer quoi que ce soit. Il n'y a qu'une seule instance d'ObjectTable et elle est effacée lorsque l'application est rafraîchie ou terminée. C'est utile dans les scénarios où vous devez rendre les données disponibles à travers plusieurs composants ou contextes sans maintenir une chaîne de référence.

### Définir et récupérer des objets {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Vérifier la présence {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // La clé existe
}
```

### Supprimer des entrées {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Taille de la table {#table-size}

```java
int total = ObjectTable.size();
```

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

Le `SessionObjectTable` fournit un accès statique aux attributs de session HTTP lors de l'exécution dans un conteneur Jakarta Servlet 6.1+. Contrairement à `ObjectTable` qui est scoped à l'application, le `SessionObjectTable` stocke des données dans la session HTTP de l'utilisateur, les rendant persistantes à travers les requêtes mais uniques à chaque session utilisateur.

Il suit le même modèle d'API que `ObjectTable` pour la cohérence.

:::warning
Les objets stockés dans le `SessionObjectTable` doivent implémenter `Serializable` pour supporter la persistance de session, la réplication et la passivation dans les conteneurs de servlets.
:::

:::warning Disponibilité dans `BBjServices`
Cette fonctionnalité n'est pas encore disponible lors de l'exécution avec BBjServices dans la version 25.03.
:::

### Définir et récupérer des objets de session {#setting-and-retrieving-session-objects}

```java
// ShoppingCart doit implémenter Serializable
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Vérifier la présence {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // La session a le panier
}
```

### Supprimer des entrées de session {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Taille de la table de session {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

Le `StringTable` fournit un accès statique aux variables de chaîne globales. Il est persistant et scoped à l'application actuelle. Les valeurs peuvent être modifiées ou injectées par programmation via la configuration de l'environnement. Ce mécanisme est particulièrement utile pour stocker des valeurs de configuration, des indicateurs et des paramètres qui doivent être accessibles à l'échelle de l'application mais qui n'ont pas besoin de transporter des données complexes.

### Obtenir et définir des valeurs de chaîne {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Valeurs pré-configurées depuis la configuration {#pre-configured-values-from-config}

Vous pouvez définir des clés dans votre fichier [`webforj.conf`](../configuration/properties#configuring-webforjconf) :

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Puis y accéder dans le code :

```java
String val = StringTable.get("COMPANY");
```

### Vérifier la présence {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // La clé est définie
}
```

### Effacer une clé {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
