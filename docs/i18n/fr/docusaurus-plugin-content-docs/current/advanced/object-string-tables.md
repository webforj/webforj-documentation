---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
Le `ObjectTable` et le `StringTable` fournissent un accès statique aux données partagées dans un environnement webforJ. Les deux sont accessibles depuis n'importe où dans votre application et servent des objectifs différents :

- `ObjectTable` : Pour stocker et récupérer des objets Java dans votre application.
- `StringTable` : Pour travailler avec des paires clé-valeur de chaînes persistantes, souvent utilisées pour des données de configuration ou de type environnement.

Ces tables sont disponibles au niveau de l'environnement et ne nécessitent pas de gestion d'instance.

## `ObjectTable` {#objecttable}

`ObjectTable` est une carte clé-valeur accessible globalement pour stocker n'importe quel objet Java. Il fournit un accès simple à l'état partagé sans besoin d'instancier ou de configurer quoi que ce soit. Il n'y a qu'une seule instance de `ObjectTable` et elle est effacée lorsque l'application est actualisée ou terminée. C'est utile dans les scénarios où vous devez mettre des données à disposition à travers plusieurs composants ou contextes sans maintenir une chaîne de référence.

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

## `StringTable` {#stringtable}

`StringTable` fournit un accès statique aux variables de chaîne globales. Il est persistant et limité à l'application actuelle. Les valeurs peuvent être modifiées ou injectées par programmation via la configuration de l'environnement. Ce mécanisme est particulièrement utile pour stocker des valeurs de configuration, des indicateurs et des paramètres qui doivent être accessibles dans toute l'application mais ne nécessitent pas de transporter des données complexes.

### Obtenir et définir des valeurs de chaîne {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Valeurs préconfigurées à partir de la configuration {#pre-configured-values-from-config}

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
