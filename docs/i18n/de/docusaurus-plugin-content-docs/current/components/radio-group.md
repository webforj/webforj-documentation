---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
description: >-
  Coordinate mutually exclusive RadioButton selections with RadioButtonGroup,
  including nested containers and dynamic membership.
_i18n_hash: a616c60faaf0d58f9d9a1e778318880a
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die `RadioButtonGroup` verwaltet eine Sammlung von [`RadioButton`](/docs/components/radiobutton) Komponenten. Es kann nur ein `RadioButton` in einer `RadioButtonGroup` ausgewählt werden. Wenn ein Benutzer einen neuen Radiobutton auswählt, wird der zuvor ausgewählte automatisch abgewählt.

<!-- INTRO_END -->

## Erstellung einer `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important `RadioButtonGroup` Rendering
Die `RadioButtonGroup` Komponente rendert kein HTML-Element. Sie bietet nur die Logik, um `RadioButton` Komponenten als Gruppe und nicht einzeln zu behandeln.
:::

Erstellen Sie einzelne `RadioButton` Komponenten und übergeben Sie diese dem Konstruktor der `RadioButtonGroup`. Nur ein Button in der Gruppe kann zu einem Zeitpunkt ausgewählt sein.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>


## Hinzufügen und Entfernen von `RadioButton` Komponenten {#adding-and-removing-radiobuttons}

Sie können `RadioButton` Komponenten im Konstruktor der `RadioButtonGroup` einfügen, um eine Gruppe aus den bereitgestellten Komponenten zu erstellen.
Um einen `RadioButton` aus einer bestehenden `RadioButtonGroup` hinzuzufügen oder zu entfernen, verwenden Sie die Methoden `add()` oder `remove()`.

:::tip Abrufen der Gruppe eines `RadioButton`
Die `RadioButton` Komponente hat die Methode `getButtonGroup()`, die die `RadioButtonGroup` zurückgibt, zu der sie gehört, oder `null`, wenn sie keine Gruppe hat.
:::

## Verschachtelung <DocChip chip='since' label='25.11' /> {#nesting}

Wie andere Komponenten können Sie eine `RadioButtonGroup` innerhalb eines Containers verschachteln, sodass Sie nicht direkt jeden einzelnen `RadioButton` hinzufügen müssen.

```java
RadioButton agree = new RadioButton("Zustimmen");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Ablehnen");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Optionen");
fieldset.add(group);
```

## Verwendung von `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Jeder `RadioButton` kann seinen eigenen Ereignislistener haben, um zu erkennen, wenn ein Benutzer ihn umschaltet. Ein Vorteil der Verwendung einer `RadioButtonGroup` ist jedoch, dass Sie einen einzelnen Ereignislistener verwenden können, der auf alle Radiobuttons in der Gruppe mit dem [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) reagiert.

**Hinzufügen von Ereignis-Listener zu jedem `RadioButton`**

```java
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Hinzufügen eines einzelnen Ereignis-Listeners zur `RadioButtonGroup`**

```java
RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Das folgende Beispiel von [Drawer Placement](/docs/components/drawer#placement) verwendet das `RadioButtonGroupChangeEvent`, um automatisch die Platzierung der `Drawer` Komponente zu ändern:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Benennung {#naming}

Das `name` Attribut in einer `RadioButtonGroup` gruppiert verwandte RadioButtons, sodass Benutzer eine einzelne Wahl aus den bereitgestellten Optionen treffen können, und erzwingt die Exklusivität unter den RadioButtons. Der Name einer Gruppe wird jedoch nicht im DOM angezeigt und stellt ein praktisches Hilfsmittel für den Java-Entwickler dar.
