---
title: Avatar
sidebar_position: 7
_i18n_hash: a09e8f3e668c6818045ca93f0747f100
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Das `Avatar`-Element bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Es kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarsektionen, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

<!-- INTRO_END -->

## Erstellen von Avataren {#creating-avatars}

Um ein `Avatar` zu erstellen, übergeben Sie ein Label, das als zugänglicher Name dient. Das Element berechnet automatisch die Initialen, indem es den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt ein Avatar, das "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen bereitstellen, wenn Sie mehr Kontrolle darüber haben möchten, was angezeigt wird:

```java
// Erstellt ein Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Team-Panels. Jedes `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Namen des Benutzers an. Das Klicken auf ein `Avatar` öffnet einen Dialog mit einer vergrößerten Ansicht.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Anzeigen von Bildern {#displaying-images}

Das `Avatar`-Element kann ein Bild anstelle von Initialen anzeigen, indem es ein `Img`-Element als Kind einfügt. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor den Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgröße
Das Bild wird automatisch skaliert, um in die Dimensionen des Avatars basierend auf der aktuellen Expansions-Einstellung zu passen.
:::

## Anzeigen von Symbolen {#displaying-icons}

Sie können ein Symbol im `Avatar` anzeigen, indem Sie ein `Icon`-Element als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gastbenutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Das `Avatar`-Element verwendet das Label für die Barrierefreiheit und die Tooltip-Generierung. Die Methoden `setLabel()` und `setText()` sind Aliase, die beide das zugängliche Label für das `Avatar` setzen.

:::info Automatisch berechnete Initialen
Wenn Sie ein `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem der erste Buchstabe jedes Wortes genommen wird. Zum Beispiel zeigt ein `Avatar` mit dem Label "John Doe" automatisch "JD" in der Benutzeroberfläche an.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt Label und generiert automatisch Tooltip
avatar.setInitials("JS");       // Überschreibt automatisch berechnete Initialen
```

:::tip Automatischer Tooltip
Das Element generiert automatisch einen Tooltip aus dem Label, sodass es einfach ist, den vollständigen Namen beim Überfahren anzuzeigen. Dieses Verhalten ist deaktiviert, wenn das Standardlabel `"Avatar"` verwendet wird.
:::

## Klickereignisse {#click-events}

Das `Avatar`-Element implementiert `HasElementClickListener`, sodass Sie auf Benutzerklicks reagieren können. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Verarbeitet den Klick auf das Avatar - z. B. Benutzerprofil öffnen
  System.out.println("Avatar geklickt!");
});
```

## Formen {#shapes}

Avatare können als Kreise oder Quadrate angezeigt werden. Die Standardform ist `CIRCLE`, was für Benutzeravatare üblich ist. Verwenden Sie `SQUARE` für Entitäten wie Teams, Unternehmen oder Anwendungen.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Themen {#themes}

Themen vermitteln Bedeutung oder Status; Sie können sie verwenden, um Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer App anzupassen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standardaussehen
- `GRAY`: Neutrales, gedämpftes Aussehen
- `PRIMARY`: Betont primäre Aktionen oder Benutzer
- `SUCCESS`: Zeigt positiven Status an (z. B. online)
- `WARNING`: Zeigt Vorsicht an (z. B. abwesend)
- `DANGER`: Zeigt Fehler- oder Beschäftigungsstatus an
- `INFO`: Bietet informativen Kontext

Jedes Thema hat auch eine umrandete Variante für eine leichtere visuelle Behandlung:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expanses {#expanses}

Steuern Sie die Avatar-Größe mit der Methode `setExpanse()`. Das Element unterstützt neun Größenoptionen, die von `XXXSMALL` bis `XXXLARGE` reichen.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Styling {#styling}

<TableBuilder name="Avatar" />
