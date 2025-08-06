---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Entwickler möchten oft Komponenten erstellen, die konstituierende Komponenten für die Anwendungsebene enthalten. Die `Composite`-Komponente gibt Entwicklern die Werkzeuge, die sie benötigen, um ihre eigenen Komponenten zu erstellen und gleichzeitig die Kontrolle darüber zu behalten, was sie den Benutzern zur Verfügung stellen.

Sie ermöglicht es Entwicklern, eine spezifische Art von `Component`-Instanz zu verwalten und bietet eine Möglichkeit, ihr Verhalten zu kapseln. Es erfordert, dass jede ableitende Unterklasse den Typ von `Component` angibt, den sie verwalten möchte, was sicherstellt, dass eine Unterklasse von `Composite` intrinsisch mit ihrem zugrunde liegenden `Component` verbunden ist.

:::tip
Es wird dringend empfohlen, benutzerdefinierte Komponenten durch die Nutzung der `Composite`-Komponente zu erstellen, anstatt die Basiskomponente `Component` zu erweitern.
:::

Um die `Composite`-Komponente zu nutzen, beginnen Sie damit, eine neue Java-Klasse zu erstellen, die die `Composite`-Komponente erweitert. Geben Sie den Typ der Komponente an, die Sie als generischen Typparameter verwalten möchten.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementierung
}
```

## Komponenteneinbindung {#component-binding}

Die `Composite`-Klasse erfordert von Entwicklern, den Typ der `Component`, die sie verwalten, anzugeben. Diese starke Assoziation stellt sicher, dass eine `Composite`-Komponente intrinsisch mit ihrem zugrunde liegenden Component verbunden ist. Dies bietet auch Vorteile gegenüber traditioneller Vererbung, da es dem Entwickler ermöglicht, genau zu entscheiden, welche Funktionalitäten der öffentlichen API zur Verfügung gestellt werden sollen.

Standardmäßig nutzt die `Composite`-Komponente den generischen Typparameter ihrer Unterklasse, um den gebundenen Komponententyp zu identifizieren und zu instanziieren. Dies basiert auf der Annahme, dass die Komponentenkasse einen parameterlosen Konstruktor hat. Entwickler können den Initialisierungsprozess der Komponente anpassen, indem sie die Methode `initBoundComponent()` überschreiben. Dies ermöglicht eine größere Flexibilität bei der Erstellung und Verwaltung der gebundenen Komponente, einschließlich des Aufrufs von parametrisierten Konstruktoren.

Der folgende Snippet überschreibt die initBoundComponent-Methode, um einen parametrisierten Konstruktor für die [FlexLayout](../components/flex-layout.md)-Klasse zu verwenden:

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Absenden");
		return new FlexLayout(nameField, submit);
	}
}
```

## Lebenszyklusmanagement {#lifecycle-management}

Im Gegensatz zur `Component` müssen Entwickler beim Arbeiten mit der `Composite`-Komponente die Methoden `onCreate()` und `onDestroy()` nicht implementieren. Die `Composite`-Komponente kümmert sich um diese Aspekte für Sie.

Sollten Sie Zugriff auf die gebundenen Komponenten in den verschiedenen Phasen ihres Lebenszyklus benötigen, ermöglichen es die Hooks `onDidCreate()` und `onDidDestroy()` den Entwicklern, auf diese Lebenszyklusphasen zuzugreifen, um zusätzliche Funktionalitäten durchzuführen. Die Nutzung dieser Hooks ist optional.

Die Methode `onDidCreate()` wird unmittelbar nach der Erstellung und Hinzufügung der gebundenen Komponente zu einem Fenster aufgerufen. Verwenden Sie diese Methode, um Ihre Komponente einzurichten, notwendige Konfigurationen zu ändern und gegebenenfalls untergeordnete Komponenten hinzuzufügen. Während die Methode `onCreate()` der `Component`-Klasse eine [Window](#)-Instanz erwartet, erhält die Methode `onDidCreate()` stattdessen die gebundene Komponente, wodurch die Notwendigkeit entfällt, die Methode `getBoundComponent()` direkt aufzurufen. Zum Beispiel:

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Fügen Sie untergeordnete Komponenten zum Container hinzu
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Diese Logik kann auch im Konstruktor implementiert werden, indem `getBoundComponent()` aufgerufen wird.
:::

In ähnlicher Weise wird die Methode `onDidDestroy()` ausgelöst, sobald die gebundene Komponente zerstört wurde, und ermöglicht es, zusätzliches Verhalten bei der Zerstörung auszulösen, falls gewünscht.

### Beispiel `Composite`-Komponente {#example-composite-component}

Im folgenden Beispiel wurde eine einfache ToDo-Anwendung erstellt, bei der jeder hinzugefügte Punkt in der Liste eine `Composite`-Komponente ist, die aus einem [`RadioButton`](../components/radio-button.md) besteht, der als Schalter gestaltet ist, und einem [`Div`](#) mit Text.

Die Logik für diese Komponente wird im Konstruktor eingerichtet, der Styling setzt und konstituierende Komponenten zur gebundenen Komponente mithilfe der Methode `getBoundComponent` hinzufügt und Ereignislogik hinzufügt.

:::tip
Dies könnte auch in der Methode `onDidCreate()` implementiert werden, was direkten Zugriff auf die gebundene [`FlexLayout`](../components/flex-layout.md)-Komponente bieten würde.
:::

Diese Komponente wird dann instanziiert und in einer Anwendung verwendet, was ihre Nutzung an verschiedenen Stellen ermöglicht und sie zu einem leistungsstarken Werkzeug bei der Erstellung von benutzerdefinierten Komponenten macht.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
