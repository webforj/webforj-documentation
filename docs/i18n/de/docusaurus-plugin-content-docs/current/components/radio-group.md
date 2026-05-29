---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 5716356b99e40dc53cfdf82a87fd9b3c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die `RadioButtonGroup` verwaltet eine Sammlung von [`RadioButton`](/docs/components/radiobutton) Komponenten. Nur ein `RadioButton` kann in einer `RadioButtonGroup` ausgewählt werden. Wenn ein Benutzer einen neuen Radio-Button auswählt, wird der zuvor ausgewählte im Gruppe automatisch abgewählt.

<!-- INTRO_END -->

## Erstellung einer `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Rendering der `RadioButtonGroup`
Die `RadioButtonGroup`-Komponente rendert kein HTML-Element. Sie bietet nur die Logik, damit `RadioButton`-Komponenten als Gruppe und nicht einzeln agieren.
:::

Erstellen Sie einzelne `RadioButton`-Komponenten und übergeben Sie diese dem Konstruktor der `RadioButtonGroup`. Nur ein Button in der Gruppe kann zu einem Zeitpunkt ausgewählt sein.

<ComponentDemo
path='/webforj/radiobuttongroup'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java']}
height='200px'
/>

## Hinzufügen und Entfernen von `RadioButton`-Komponenten {#adding-and-removing-radiobuttons}

Sie können `RadioButton`-Komponenten im Konstruktor der `RadioButtonGroup` einfügen, um eine Gruppe aus den bereitgestellten Komponenten zu erstellen. Um einen `RadioButton` aus einer bestehenden `RadioButtonGroup` hinzuzufügen oder zu entfernen, verwenden Sie die Methoden `add()` oder `remove()`.

:::tip Die Gruppe eines `RadioButton` abrufen
Die `RadioButton`-Komponente hat die Methode `getButtonGroup()`, die die `RadioButtonGroup` zurückgibt, zu der sie gehört, oder `null`, wenn sie keine Gruppe hat.
:::

## Verschachtelung <DocChip chip='since' label='25.11' /> {#nesting}

Wie andere Komponenten können Sie eine `RadioButtonGroup` innerhalb eines Containers verschachteln, sodass Sie nicht jeden einzelnen `RadioButton` direkt hinzufügen müssen.

```java
RadioButton agree = new RadioButton("Zustimmen");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Ablehnen");

RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Optionen");
fieldset.add(group);
```

## Verwendung von `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Jeder `RadioButton` kann seinen eigenen Ereignislistener haben, um zu erkennen, wann ein Benutzer ihn umschaltet. Ein Vorteil der Verwendung einer `RadioButtonGroup` besteht jedoch darin, dass Sie einen einzelnen Ereignislistener verwenden können, der auf alle Radio-Buttons in der Gruppe mit dem [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) reagiert.

**Ereignislistener zu jedem `RadioButton` hinzufügen**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Einen einzelnen Ereignislistener zur `RadioButtonGroup` hinzufügen**

```java
RadioButtonGroup group = new RadioButtonGroup("choices", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Das folgende Beispiel aus [Drawer Placement](/docs/components/drawer#placement) verwendet das `RadioButtonGroupChangeEvent`, um automatisch die Platzierung der `Drawer`-Komponente zu ändern:

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Benennung {#naming}

Das Attribut `name` in einer `RadioButtonGroup` gruppiert verwandte Radio-Buttons zusammen, wodurch es Benutzern ermöglicht wird, eine einzige Wahl aus den bereitgestellten Optionen zu treffen und die Exklusivität unter den Radio-Buttons durchzusetzen. Der Name einer Gruppe wird jedoch nicht im DOM widergespiegelt und dient als praktische Hilfestellung für den Java-Entwickler.
