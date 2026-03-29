---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
sidebar_class_name: updated-content
_i18n_hash: 564d1d0c46ef2395fb2ad2785ba18d53
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die `RadioButtonGroup` verwaltet eine Sammlung von [`RadioButton`](/docs/components/radiobutton) Komponenten. Es kann nur ein `RadioButton` in einer `RadioButtonGroup` ausgewählt werden. Wenn ein Benutzer einen neuen Radiobutton aktiviert, wird der zuvor in der Gruppe ausgewählte automatisch deaktiviert.

<!-- INTRO_END -->

## Erstellung einer `RadioButtonGroup` {#creating-a-radiobuttongroup}

:::important Rendering der `RadioButtonGroup`
Die `RadioButtonGroup` Komponente rendert kein HTML-Element. Sie bietet lediglich die Logik, damit `RadioButton` Komponenten sich als Gruppe und nicht individuell verhalten.
:::

Erstellen Sie einzelne `RadioButton` Komponenten und übergeben Sie diese an den Konstruktor der `RadioButtonGroup`. In der Gruppe kann jeweils nur ein Button ausgewählt werden.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Hinzufügen und Entfernen von `RadioButton` Komponenten {#adding-and-removing-radiobuttons}

Sie können `RadioButton` Komponenten im Konstruktor der `RadioButtonGroup` einfügen, um eine Gruppe aus den bereitgestellten Komponenten zu erstellen.
Um einen `RadioButton` aus einer bestehenden `RadioButtonGroup` hinzuzufügen oder zu entfernen, verwenden Sie die Methoden `add()` oder `remove()`.

:::tip Abrufen der Gruppe eines `RadioButton`
Die `RadioButton` Komponente hat die Methode `getButtonGroup()`, die die `RadioButtonGroup` zurückgibt, zu der sie gehört, oder `null`, wenn sie keine Gruppe hat.
:::

## Verschachtelung <DocChip chip='since' label='25.11' /> {#nesting}

Wie bei anderen Komponenten können Sie eine `RadioButtonGroup` innerhalb eines Containers verschachteln, sodass Sie nicht jeden einzelnen `RadioButton` direkt hinzufügen müssen.

```java
RadioButton agree = new RadioButton("Zustimmen");
RadioButton neutral = new RadioButton("Neutral");
RadioButton disagree = new RadioButton("Ablehnen");

RadioButtonGroup group = new RadioButtonGroup("Auswahlmöglichkeiten", agree, neutral, disagree);

Fieldset fieldset = new Fieldset("Optionen");
fieldset.add(group);
```

## Verwendung von `RadioButtonGroupChangeEvent` {#using-radiobuttongroupchangeevent}

Jeder `RadioButton` kann seinen eigenen Ereignislistener haben, um zu erkennen, wann ein Benutzer ihn umschaltet. Ein Vorteil der Verwendung einer `RadioButtonGroup` besteht jedoch darin, dass Sie einen einzigen Ereignislistener verwenden können, der auf alle Radiobuttons in der Gruppe mit dem [`RadioButtonGroupChangeEvent`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/optioninput/event/RadioButtonGroupChangeEvent.html) reagiert.

**Ereignislistener zu jedem `RadioButton` hinzufügen**

```java 
agree.onValueChange(e -> changeEvent());
neutral.onValueChange(e -> changeEvent());
disagree.onValueChange(e -> changeEvent());
```

**Einen einzelnen Ereignislistener zur `RadioButtonGroup` hinzufügen**

```java
RadioButtonGroup group = new RadioButtonGroup("Auswahlmöglichkeiten", agree, neutral, disagree);
group.onChange(e -> changeEvent());
```

Das folgende Beispiel aus [Drawer Placement](/docs/components/drawer#placement) verwendet das `RadioButtonGroupChangeEvent`, um die Platzierung der `Drawer` Komponente automatisch zu ändern:

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Benennung {#naming}

Das `name` Attribut in einer `RadioButtonGroup` gruppiert verwandte RadioButtons und ermöglicht es Benutzern, eine einzige Wahl aus den angebotenen Optionen zu treffen und Exklusivität unter den RadioButtons durchzusetzen. Der Name einer Gruppe wird jedoch nicht im DOM widergespiegelt, sondern ist ein praktisches Hilfsmittel für den Java-Entwickler.
