---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Le `RadioButtonGroup` gère une collection de composants [`RadioButton`](/docs/components/radiobutton). Un seul `RadioButton` peut être sélectionné dans un `RadioButtonGroup`. Lorsqu'un utilisateur coche un nouveau bouton radio, l'ancienne sélection dans le groupe est automatiquement décochée.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important Rendu du `RadioButtonGroup`
Le composant `RadioButtonGroup` ne rend pas un élément HTML. Il fournit uniquement la logique pour faire en sorte que les composants `RadioButton` se comportent en tant que groupe plutôt qu'individuellement.
:::

## Ajouter et supprimer des composants `RadioButton` {#adding-and-removing-radiobuttons}

Vous pouvez inclure des composants `RadioButton` dans le constructeur du `RadioButtonGroup` pour créer un groupe à partir des composants fournis. Pour ajouter ou supprimer un `RadioButton` d'un `RadioButtonGroup` existant, utilisez les méthodes `add()` ou `remove()`.

:::tip Obtenir le groupe d'un `RadioButton`
Le composant `RadioButton` a la méthode `getButtonGroup()`, qui retourne le `RadioButtonGroup` auquel il appartient, ou `null` s'il n'a pas de groupe.
:::

## Imbriquer <DocChip chip='since' label='25.11' /> {#nesting}

Comme d'autres composants, vous pouvez imbriquer un `RadioButtonGroup` dans un conteneur, afin de ne pas avoir à ajouter directement chaque `RadioButton` individuel.

```java
RadioButton agree = new RadioButton("D'accord");
RadioButton neutral = new RadioButton("Neutre");
RadioButton disagree = new RadioButton("Pas d'accord");

RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Utiliser `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Chaque `RadioButton` peut avoir son propre écouteur d'événements pour détecter lorsque l'utilisateur le bascule. Cependant, un avantage d'utiliser un `RadioButtonGroup` est que vous pouvez utiliser un seul écouteur d'événements qui répond à tous les boutons radio du groupe avec l'[`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Ajouter des écouteurs d'événements à chaque `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Ajouter un seul écouteur d'événements au `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

L'exemple suivant provenant de [Placement du tiroir](/docs/components/drawer#placement) utilise le `RadioButtonGroupChangeEvent` pour changer automatiquement le placement du composant `Drawer` :

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nommer {#naming}

L'attribut `name` dans un `RadioButtonGroup` groupe les RadioButtons associés ensemble, permettant aux utilisateurs de faire un choix unique parmi les options fournies et imposant l'exclusivité parmi les RadioButtons. Le nom d'un groupe n'est pas reflété dans le DOM, cependant, et est un outil de commodité pour le développeur Java.
