---
title: Avatar
sidebar_position: 7
description: >-
  Represent users with the Avatar component, showing profile images,
  auto-computed initials, custom initials, or icons for identification.
_i18n_hash: a19b6cefc7a422d075f42ddedfcddfce
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Die `Avatar`-Komponente bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Sie kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarfeldern, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

<!-- INTRO_END -->

## Erstellung von Avataren {#creating-avatars}

Um einen `Avatar` zu erstellen, übergeben Sie ein Label, das als zugänglicher Name dient. Die Komponente berechnet automatisch die Initialen, indem sie den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt einen Avatar, der "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen bereitstellen, wenn Sie mehr Kontrolle darüber wünschen, was angezeigt wird:

```java
// Erstellt einen Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Team-Panels. Jeder `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Namen des Benutzers an. Ein Klick auf einen `Avatar` öffnet einen Dialog mit einer vergrößerten Ansicht.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/frontend/css/avatar/avatar.css',
]}
height='500px'
/>

## Anzeige von Bildern {#displaying-images}

Die `Avatar`-Komponente kann ein Bild anstelle von Initialen anzeigen, indem eine `Img`-Komponente als Kind eingefügt wird. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor den Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgröße
Das Bild wird automatisch so skaliert, dass es in die Abmessungen des Avatars passt, basierend auf der aktuellen Größeinstellung.
:::

## Anzeige von Symbolen {#displaying-icons}

Sie können ein Symbol innerhalb des `Avatar` anzeigen, indem Sie eine `Icon`-Komponente als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gastbenutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Die `Avatar`-Komponente verwendet das Label für Barrierefreiheit und Tooltip-Generierung. Die Methoden `setLabel()` und `setText()` sind Aliase, die beide das zugängliche Label für den `Avatar` festlegen.

:::info Automatisch berechnete Initialen
Wenn Sie einen `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem der erste Buchstabe jedes Wortes genommen wird. Ein `Avatar` mit dem Label "John Doe" zeigt beispielsweise automatisch "JD" in der Benutzeroberfläche an.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt Label und generiert automatisch Tooltip
avatar.setInitials("JS");       // Überschreibt automatisch berechnete Initialen
```

:::tip Automatischer Tooltip
Die Komponente erzeugt automatisch einen Tooltip aus dem Label, wodurch der volle Name beim Hover leicht angezeigt werden kann. Dieses Verhalten ist deaktiviert, wenn das Standardlabel `"Avatar"` verwendet wird.
:::

## Klickereignisse {#click-events}

Die `Avatar`-Komponente implementiert `HasElementClickListener`, sodass Sie auf Benutzerklicks reagieren können. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Verarbeitet den Avatar-Klick - z.B. öffne Benutzerprofil
  System.out.println("Avatar angeklickt!");
});
```

## Formen {#shapes}

Avatare können als Kreise oder Quadrate angezeigt werden. Die Standardform ist `CIRCLE`, die standardmäßig für Benutzeravatare verwendet wird. Verwenden Sie `SQUARE` für Entitäten wie Teams, Unternehmen oder Anwendungen.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Themen {#themes}

Themen vermitteln Bedeutung oder Status; Sie können sie verwenden, um die Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer App anzupassen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standardaussehen
- `GRAY`: Neutrales, gedämpftes Aussehen
- `PRIMARY`: Betont primäre Aktionen oder Benutzer
- `SUCCESS`: Zeigt positiven Status an (z.B. online)
- `WARNING`: Weist auf Vorsicht hin (z.B. abwesend)
- `DANGER`: Weist auf Fehler- oder Beschäftigungsstatus hin
- `INFO`: Bietet informativen Kontext

Jedes Thema hat auch eine umrandete Variante für eine leichtere visuelle Behandlung:

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Größen {#expanses}

Steuern Sie die Avatar-Größe mithilfe der Methode `setExpanse()`. Die Komponente unterstützt neun Größenoptionen, die von `XXXSMALL` bis `XXXLARGE` reichen.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>


## Styling {#styling}

<TableBuilder name="Avatar" />
