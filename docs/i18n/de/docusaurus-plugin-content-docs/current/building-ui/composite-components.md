---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: 864d51bda31fc239bb58f5886ca7eeb4
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Entwickler möchten häufig Komponenten erstellen, die konstitutive Komponenten für den Anwendungsgebrauch enthalten. Die `Composite`-Komponente gibt Entwicklern die Werkzeuge, die sie benötigen, um ihre eigenen Komponenten zu erstellen und gleichzeitig die Kontrolle darüber zu behalten, was sie den Benutzern zur Verfügung stellen möchten.

Sie ermöglicht es Entwicklern, eine spezifische Art von `Component`-Instanz zu verwalten, und bietet eine Möglichkeit, ihr Verhalten zu kapseln. Es erfordert, dass jede abgeleitete Unterklasse den Typ von `Component`, den sie verwalten möchte, spezifiziert, wodurch sichergestellt wird, dass eine Unterklasse von `Composite` intrinsisch mit ihrer zugrunde liegenden `Component` verbunden ist.

:::tip
Es wird dringend empfohlen, benutzerdefinierte Komponenten unter Verwendung der `Composite`-Komponente zu erstellen, anstatt die Basiskomponente `Component` zu erweitern.
:::

Um die `Composite`-Komponente zu nutzen, beginnen Sie damit, eine neue Java-Klasse zu erstellen, die die `Composite`-Komponente erweitert. Geben Sie den Typ der Komponente an, die Sie als generischen Typ verwalten möchten.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementierung
}
```

## Komponentenbindung {#component-binding}

Die `Composite`-Klasse erfordert von Entwicklern, den Typ der `Component`, die sie verwaltet, anzugeben. Diese starke Assoziation stellt sicher, dass eine `Composite`-Komponente intrinsisch mit ihrer zugrunde liegenden Komponente verbunden ist. Dies bietet auch Vorteile gegenüber traditioneller Vererbung, da es dem Entwickler ermöglicht, genau zu entscheiden, welche Funktionalität in die öffentliche API aufgenommen wird.

Standardmäßig verwendet die `Composite`-Komponente den generischen Typ ihrer Unterklasse, um den gebundenen Komponententyp zu identifizieren und zu instanziieren. Dies basiert auf der Annahme, dass die Komponentenklasse einen parameterlosen Konstruktor hat. Entwickler können den Initialisierungsprozess der Komponente anpassen, indem sie die Methode `initBoundComponent()` überschreiben. Dies ermöglicht eine größere Flexibilität bei der Erstellung und Verwaltung der gebundenen Komponente, einschließlich des Aufrufs von parametrierten Konstruktoren.

Der folgende Code überschreibt die Methode `initBoundComponent`, um einen parametrisierten Konstruktor für die [FlexLayout](../components/flex-layout.md)-Klasse zu verwenden:

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

## Lebenszyklusverwaltung {#lifecycle-management}

Im Gegensatz zur `Component` müssen Entwickler die Methoden `onCreate()` und `onDestroy()` beim Arbeiten mit der `Composite`-Komponente nicht implementieren. Die `Composite`-Komponente kümmert sich um diese Aspekte für Sie.

Sollten Sie auf die gebundenen Komponenten in den verschiedenen Phasen ihres Lebenszyklus zugreifen müssen, ermöglichen die `onDidCreate()`- und `onDidDestroy()`-Hooks den Entwicklern den Zugriff auf diese Lebenszyklusphasen, um zusätzliche Funktionalität auszuführen. Die Nutzung dieser Hooks ist optional.

Die Methode `onDidCreate()` wird unmittelbar nach der Erstellung und Hinzufügung der gebundenen Komponente zu einem Fenster aufgerufen. Verwenden Sie diese Methode, um Ihre Komponente einzurichten, erforderliche Konfigurationen zu ändern und gegebenenfalls untergeordnete Komponenten hinzuzufügen. Während die `onCreate()`-Methode der `Component`-Klasse eine [Window](#)-Instanz entgegennimmt, nimmt die Methode `onDidCreate()` stattdessen die gebundene Komponente entgegen, sodass der Aufruf der Methode `getBoundComponent()` nicht mehr erforderlich ist. Zum Beispiel:

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

Ebenso wird die Methode `onDidDestroy()` aufgerufen, sobald die gebundene Komponente zerstört wurde, und ermöglicht es, zusätzliches Verhalten beim Zerstören auszulösen, falls gewünscht.

### Beispiel `Composite`-Komponente {#example-composite-component}

Im folgenden Beispiel wurde eine einfache ToDo-Anwendung erstellt, bei der jeder Punkt in der Liste eine `Composite`-Komponente ist, die aus einem [`RadioButton`](../components/radio-button.md) besteht, der als Schalter gestaltet ist, und einem [`Div`](#) mit Text.

Die Logik für diese Komponente wird im Konstruktor festgelegt, der das Styling festlegt und konstitutive Komponenten zur gebundenen Komponente mithilfe der Methode `getBoundComponent` hinzufügt und Ereignislogik hinzufügt.

:::tip
Dies könnte auch in der Methode `onDidCreate()` implementiert werden, die direkten Zugriff auf die gebundene [`FlexLayout`](../components/flex-layout.md)-Komponente gewährt.
:::

Diese Komponente wird dann instanziiert und in einer Anwendung verwendet und ermöglicht ihre Nutzung an verschiedenen Stellen, was sie zu einem leistungsstarken Instrument bei der Erstellung benutzerdefinierter Komponenten macht.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
