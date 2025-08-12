---
sidebar_position: 20
title: Fields
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 288d408cb058dbaa417fea651698123a
---
<JavadocLink type="foundation" location="com/webforj/component/field/AbstractField"/>

Le framework webforJ prend en charge sept types différents de composants de champ, chacun ayant des comportements et des implémentations différents qui répondent à divers besoins pour les saisies.
Bien que chacun de ces composants ait des variations dans leurs implémentations, cet article décrit les propriétés partagées parmi toutes les classes de champ.

:::info
Cette section décrit les caractéristiques communes de divers composants de champ dans webforJ et n'est pas elle-même une classe qui peut être instanciée et utilisée.
:::

## Propriétés de champ partagées {#shared-field-properties}

### Label {#label}

Un label de champ est un texte descriptif ou un titre qui est associé au champ et qui peut être défini dans le constructeur ou en utilisant la méthode `setLabel()`. Les labels fournissent une brève explication ou une invite pour aider les utilisateurs à comprendre le but ou l'entrée attendue pour ce champ particulier. Les labels de champ sont importants pour l'utilisabilité et jouent un rôle crucial dans l'accessibilité, car ils permettent aux lecteurs d'écran et aux technologies d'assistance de fournir des informations précises et de faciliter la navigation au clavier.

### Texte d'aide {#helper-text}

Chaque champ peut afficher un texte d'aide sous la saisie en utilisant la méthode `setHelperText()`. Ce texte d'aide offre un contexte ou des explications supplémentaires sur les saisies disponibles, garantissant que les utilisateurs disposent des informations nécessaires pour faire des choix éclairés.

### Requis {#required}

Vous pouvez appeler la méthode `setRequired(true)` pour obliger les utilisateurs à fournir une valeur avant de soumettre un formulaire. Cette propriété fonctionne en tandem avec le label de champ, fournissant une indication visuelle qu'un champ est nécessaire. Cet indice visuel aide les individus à remplir les formulaires avec précision.

:::info
Les composants de champ contiennent une validation visuelle intégrée pour notifier les utilisateurs lorsqu'un champ requis est vide ou si un utilisateur a supprimé une valeur.
:::

### Vérification orthographique {#spellcheck}

En utilisant `setSpellCheck(true)`, vous pouvez permettre au navigateur ou à l'agent utilisateur de vérifier l'orthographe du texte saisi par l'utilisateur et d'identifier toute erreur.

### Préfixe et suffixe {#prefix-and-suffix}

Les emplacements offrent des options flexibles pour améliorer les capacités des composants de champ. Vous pouvez avoir des icônes, des labels, des indicateurs de chargement, une capacité de nettoyage/réinitialisation, des photos de profil/avatar, et d'autres composants bénéfiques imbriqués dans un champ pour clarifier davantage le sens destiné aux utilisateurs.
Les champs ont deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un champ. Voici un exemple utilisant le champ `TextField` :

```java
TextField textField = new TextField();
textField.setPrefixComponent(TablerIcon.create("box"));
textField.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

:::info
Parce que tous les composants de champ sont construits à partir d'un composant web unique, ils partagent tous les
éléments Shadow Parts et les valeurs de propriété CSS suivantes
:::

<TableBuilder name="Field" />

## Sujets {#topics}

<DocCardList className="topics-section" />
