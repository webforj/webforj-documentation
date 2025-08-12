---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: 864d51bda31fc239bb58f5886ca7eeb4
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Les développeurs souhaiteront souvent créer des composants contenant des composants constituants pour une utilisation au niveau de l'application. Le composant `Composite` fournit aux développeurs les outils nécessaires pour créer leurs propres composants tout en maintenant le contrôle sur ce qu'ils choisissent d'exposer aux utilisateurs.

Il permet aux développeurs de gérer un type spécifique d'instance `Component`, offrant un moyen d'encapsuler son comportement. Il exige de toute sous-classe étendue qu'elle spécifie le type de `Component` qu'elle entend gérer, garantissant qu'une sous-classe de `Composite` est intrinsèquement liée à son `Component` sous-jacent.

:::tip
Il est fortement recommandé de créer des composants personnalisés en utilisant le composant `Composite`, plutôt qu'en étendant le composant de base `Component`.
:::

Pour utiliser le composant `Composite`, commencez par créer une nouvelle classe Java qui étend le composant `Composite`. Spécifiez le type de composant que vous souhaitez gérer en tant que paramètre de type générique.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

## Liaison de composants {#component-binding}

La classe `Composite` nécessite que les développeurs spécifient le type de `Component` qu'elle gère. Cette association forte garantit qu'un composant `Composite` est intrinsèquement lié à son `Component` sous-jacent. Cela offre également des avantages par rapport à l'héritage traditionnel, car cela permet au développeur de décider exactement quelle fonctionnalité exposer à l'API publique.

Par défaut, le composant `Composite` utilise le paramètre de type générique de sa sous-classe pour identifier et instancier le type de composant lié. Cela se base sur l'hypothèse que la classe de composant possède un constructeur sans paramètre. Les développeurs peuvent personnaliser le processus d'initialisation du composant en substituant la méthode `initBoundComponent()`. Cela permet une plus grande flexibilité dans la création et la gestion du composant lié, y compris l'invocation de constructeurs paramétrés.

Le snippet suivant substitue la méthode initBoundComponent pour utiliser un constructeur paramétré pour la classe [FlexLayout](../components/flex-layout.md) :

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Submit");
		return new FlexLayout(nameField, submit);
	}
}
```

## Gestion du cycle de vie {#lifecycle-management}

Contrairement à `Component`, les développeurs n'ont pas besoin d'implémenter les méthodes `onCreate()` et `onDestroy()` lorsqu'ils travaillent avec le composant `Composite`. Le composant `Composite` s'occupe de ces aspects pour vous.

Si vous devez accéder aux composants liés à différents stades de son cycle de vie, les hooks `onDidCreate()` et `onDidDestroy()` permettent aux développeurs d'accéder à ces étapes du cycle de vie pour effectuer des opérations supplémentaires. L'utilisation de ces hooks est facultative.

La méthode `onDidCreate()` est appelée immédiatement après que le composant lié a été créé et ajouté à une fenêtre. Utilisez cette méthode pour configurer votre composant, modifier toute configuration nécessaire, et ajouter des composants enfants si applicable. Alors que la méthode `onCreate()` de la classe `Component` prend une instance de [Window](#), la méthode `onDidCreate()` prend à la place le composant lié, éliminant ainsi le besoin d'appeler directement la méthode `getBoundComponent()`. Par exemple :

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Ajouter des composants enfants au conteneur
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Cette logique peut également être implémentée dans le constructeur, en appelant `getBoundComponent()`.
:::

De même, la méthode `onDidDestroy()` se déclenche une fois que le composant lié a été détruit, et permet d'exécuter un comportement supplémentaire lors de la destruction si cela est souhaité.

### Exemple de composant `Composite` {#example-composite-component}

Dans l'exemple suivant, une application ToDo simple a été créée, où chaque élément ajouté à la liste est un composant `Composite`, composé d'un [`RadioButton`](../components/radio-button.md) stylé comme un interrupteur, et d'un [`Div`](#) avec du texte.

La logique pour ce composant est configurée dans le constructeur, qui définit le style et ajoute des composants constituants au composant lié en utilisant la méthode `getBoundComponent`, et ajoute la logique d'événements.

:::tip
Cela pourrait également être implémenté dans la méthode `onDidCreate()`, qui donnerait un accès direct au composant lié [`FlexLayout`](../components/flex-layout.md).
:::

Ce composant est ensuite instancié et utilisé dans une Application, permettant son utilisation dans divers emplacements, en faisant un outil puissant pour la création de composants personnalisés.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
