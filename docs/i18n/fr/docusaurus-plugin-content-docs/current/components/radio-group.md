---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Le `RadioButtonGroup` gère une collection de composants [`RadioButton`](/docs/components/radiobutton). Un seul `RadioButton` peut être sélectionné dans un `RadioButtonGroup`. Lorsqu'un utilisateur coche un nouveau bouton radio, l'ancien sélectionné dans le groupe est automatiquement décoché.

<!-- INTRO_END -->

## Création d'un `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Rendu du `RadioButtonGroup`
Le composant `RadioButtonGroup` ne rend pas un élément HTML. Il fournit uniquement la logique pour faire en sorte que les composants `RadioButton` se comportent comme un groupe plutôt qu'individuellement.
:::

Créez des composants `RadioButton` individuels et passez-les au constructeur `RadioButtonGroup`. Un seul bouton dans le groupe peut être sélectionné à la fois.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>


## Ajout et suppression de composants `RadioButton` {#adding-and-removing-radiobuttons}

Vous pouvez inclure des composants `RadioButton` dans le constructeur `RadioButtonGroup` pour créer un groupe à partir des composants fournis. 
Pour ajouter ou supprimer un `RadioButton` d'un `RadioButtonGroup` existant, utilisez les méthodes `add()` ou `remove()`.

:::tip Obtenir le groupe d'un `RadioButton`
Le composant `RadioButton` a la méthode `getButtonGroup()`, qui renvoie le `RadioButtonGroup` auquel il appartient, ou `null` s'il n'a pas de groupe.
:::

## Imbrication <DocChip chip='since' label='25.11' /> {#nesting}

Comme d'autres composants, vous pouvez imbriquer un `RadioButtonGroup` au sein d'un conteneur, de sorte que vous n'ayez pas à ajouter directement chaque `RadioButton` individuel.

```java
RadioButton agree = new RadioButton("D'accord");
RadioButton neutral = new RadioButton("Neutre");
RadioButton disagree = new RadioButton("Pas d'accord");

RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Options");
fieldset.add(group);
```

## Utilisation de `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Chaque `RadioButton` peut avoir son propre écouteur d'événements pour détecter quand un utilisateur le bascule. Cependant, un avantage de l'utilisation d'un `RadioButtonGroup` est que vous pouvez utiliser un seul écouteur d'événements qui répond à tous les boutons radio du groupe avec le [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html).

**Ajout d'écouteurs d'événements à chaque `RadioButton`**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Ajout d'un seul écouteur d'événements au `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choix", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

L'exemple suivant provenant de [Placement de tiroir](/docs/components/drawer#placement) utilise le `RadioButtonGroupChangeEvent` pour changer automatiquement le placement du composant `Drawer` :

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Nommage {#naming}

L'attribut `name` dans un `RadioButtonGroup` regroupe les `RadioButton` liés ensemble, permettant aux utilisateurs de faire un choix unique parmi les options fournies et faisant respecter l'exclusivité entre les `RadioButton`. Le nom d'un groupe n'est cependant pas reflété dans le DOM et constitue un outil de commodité pour le développeur Java.
