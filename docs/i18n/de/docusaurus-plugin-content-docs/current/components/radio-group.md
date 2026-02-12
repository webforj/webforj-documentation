---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: da7906128f0d003b9ed8c48c99c3aefc
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die `RadioButtonGroup` verwaltet eine Sammlung von [`RadioButton`](/docs/components/radiobutton) Komponenten. Nur ein `RadioButton` kann in einer `RadioButtonGroup` ausgewählt werden. Wenn ein Benutzer einen neuen Radio-Button auswählt, wird der zuvor in der Gruppe ausgewählte automatisch abgewählt.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important `RadioButtonGroup` Rendering
Die `RadioButtonGroup` Komponente rendert kein HTML-Element. Sie bietet nur die Logik, um `RadioButton` Komponenten als Gruppe zu behandeln, anstatt einzeln.
:::

## Hinzufügen und Entfernen von `RadioButton` Komponenten {#adding-and-removing-radiobuttons}

Sie können `RadioButton` Komponenten im Konstruktor der `RadioButtonGroup` einfügen, um eine Gruppe aus den bereitgestellten Komponenten zu erstellen. Um einen `RadioButton` aus einer vorhandenen `RadioButtonGroup` hinzuzufügen oder zu entfernen, verwenden Sie die Methoden `add()` oder `remove()`.

:::tip Abrufen der Gruppe eines `RadioButton`
Die `RadioButton` Komponente hat die Methode `getButtonGroup()`, die die `RadioButtonGroup` zurückgibt, zu der sie gehört, oder `null`, wenn sie keine Gruppe hat.
:::

## Verschachtelung <DocChip chip='since' label='25.11' /> {#nesting}

Wie andere Komponenten können Sie eine `RadioButtonGroup` in einen Container einfügen, sodass Sie nicht jeden einzelnen `RadioButton` direkt hinzufügen müssen.

```java
RadioButton agree = new RadioButton("Zustimmen");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Ablehnen");

RadioButtonGroup group = new RadioButtonGroup("Auswahlmöglichkeiten", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Optionen");
fieldset.add(group);
```

## Verwendung von `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Jeder `RadioButton` kann seinen eigenen Ereignis-Listener haben, um zu erkennen, wann ein Benutzer ihn umschaltet. Ein Vorteil der Verwendung einer `RadioButtonGroup` ist jedoch, dass Sie einen einzigen Ereignis-Listener verwenden können, der auf alle Radio-Buttons in der Gruppe mit dem [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) reagiert.

**Ereignis-Listener zu jedem `RadioButton` hinzufügen**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Einen einzigen Ereignis-Listener zur `RadioButtonGroup` hinzufügen**

```java
RadioButtonGroup group = new RadioButtonGroup("Auswahlmöglichkeiten", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Das folgende Beispiel von [Drawer Placement](/docs/components/drawer#placement) verwendet das `RadioButtonGroupChangeEvent`, um automatisch die Platzierung der `Drawer` Komponente zu ändern:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Benennung {#naming}

Das `name` Attribut in einer `RadioButtonGroup` gruppiert verwandte RadioButtons zusammen, sodass Benutzer eine einzelne Wahl aus den bereitgestellten Optionen treffen können und die Exklusivität unter den RadioButtons durchgesetzt wird. Der Name einer Gruppe wird jedoch nicht im DOM widergespiegelt und ist ein praktisches Hilfsmittel für den Java-Entwickler.
