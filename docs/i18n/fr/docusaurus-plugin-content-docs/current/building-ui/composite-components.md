---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Les développeurs voudront souvent créer des composants contenant des composants constitutifs pour un usage au niveau de l'application. Le composant `Composite` fournit aux développeurs les outils nécessaires pour créer leurs propres composants tout en conservant le contrôle sur ce qu'ils choisissent d'exposer aux utilisateurs.

Il permet aux développeurs de gérer un type spécifique d'instance de `Component`, offrant un moyen d'encapsuler son comportement. Il est nécessaire que toute sous-classe étendant spécifie le type de `Component` qu'elle entend gérer, garantissant qu'une sous-classe de `Composite` est intrinsèquement liée à son `Component` sous-jacent.

:::tip
Il est fortement recommandé de créer des composants personnalisés en utilisant le composant `Composite`, plutôt que d'étendre le composant de base `Component`.
:::

Pour utiliser le composant `Composite`, commencez par créer une nouvelle classe Java qui étend le composant `Composite`. Spécifiez le type de composant que vous souhaitez gérer en tant que paramètre de type générique.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementation
}
```

## Liaison de composants {#component-binding}

La classe `Composite` exige que les développeurs spécifient le type de `Component` qu'elle gère. Cette forte association garantit qu'un composant `Composite` est intrinsèquement lié à son `Component` sous-jacent. Cela offre également des avantages par rapport à l'héritage traditionnel, car cela permet au développeur de décider exactement quelles fonctionnalités exposer à l'API publique.

Par défaut, le composant `Composite` utilise le paramètre de type générique de sa sous-classe pour identifier et instancier le type de composant lié. Cela est basé sur l'hypothèse que la classe de composant a un constructeur sans paramètre. Les développeurs peuvent personnaliser le processus d'initialisation du composant en surchargeant la méthode `initBoundComponent()`. Cela permet une plus grande flexibilité dans la création et la gestion du composant lié, y compris l'invocation de constructeurs paramétrés.

Le fragment suivant surcharge la méthode initBoundComponent pour utiliser un constructeur paramétré pour la classe [FlexLayout](../components/flex-layout.md) :

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Soumettre");
		return new FlexLayout(nameField, submit);
	}
}
```

## Gestion du cycle de vie {#lifecycle-management}

Contrairement au `Component`, les développeurs n'ont pas besoin d'implémenter les méthodes `onCreate()` et `onDestroy()` lorsqu'ils travaillent avec le composant `Composite`. Le composant `Composite` s'occupe de ces aspects pour vous.

Si vous devez accéder aux composants liés aux différentes étapes de leur cycle de vie, les hooks `onDidCreate()` et `onDidDestroy()` permettent aux développeurs d'accéder à ces étapes pour exécuter des fonctionnalités supplémentaires. L'utilisation de ces hooks est optionnelle.

La méthode `onDidCreate()` est appelée immédiatement après que le composant lié a été créé et ajouté à une fenêtre. Utilisez cette méthode pour configurer votre composant, modifier toute configuration nécessaire et ajouter des composants enfants si applicable. Alors que la méthode `onCreate()` de la classe `Component` prend une instance de [Window](#), la méthode `onDidCreate()` prend plutôt le composant lié, supprimant le besoin d'appeler directement la méthode `getBoundComponent()`. Par exemple :

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

De même, la méthode `onDidDestroy()` se déclenche une fois que le composant lié a été détruit, et permet d'exécuter un comportement supplémentaire en cas de destruction, si telle est le souhait.

### Exemple de composant `Composite` {#example-composite-component}

Dans l'exemple suivant, une simple application ToDo a été créée, où chaque élément ajouté à la liste est un composant `Composite`, constitué d'un [`RadioButton`](../components/radio-button.md) stylé comme un interrupteur et d'un [`Div`](#) avec du texte.

La logique de ce composant est configurée dans le constructeur, qui définit le style et ajoute des composants constitutifs au composant lié en utilisant la méthode `getBoundComponent`, et ajoute la logique des événements.

:::tip
Cela pourrait également être implémenté dans la méthode `onDidCreate()`, ce qui donnerait un accès direct au composant lié [`FlexLayout`](../components/flex-layout.md).
:::

Ce composant est ensuite instancié et utilisé dans une Application, et permet son utilisation dans divers endroits, ce qui en fait un outil puissant pour la création de composants personnalisés.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
