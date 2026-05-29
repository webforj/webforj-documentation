---
title: Avatar
sidebar_position: 7
_i18n_hash: 77ac4a1373803d1d68a45968175050e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Die `Avatar`-Komponente bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Sie kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarsektionen, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

<!-- INTRO_END -->

## Erstellen von Avataren {#creating-avatars}

Um einen `Avatar` zu erstellen, übergeben Sie ein Label, das als barrierefreier Name dient. Die Komponente berechnet automatisch die Initialen, indem sie den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt einen Avatar, der "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen angeben, wenn Sie mehr Kontrolle über das Angezeigte wünschen:

```java
// Erstellt einen Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Team-Panels. Jeder `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Namen des Benutzers an. Ein Klick auf einen `Avatar` öffnet einen Dialog mit einer vergrößerten Ansicht.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/resources/static/css/avatar/avatar.css',
]}
height='500px'
/>

## Anzeigen von Bildern {#displaying-images}

Die `Avatar`-Komponente kann ein Bild anstelle von Initialen anzeigen, indem sie ein `Img`-Komponente als Kind einfügt. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgröße
Das Bild wird automatisch skaliert, um in die Dimensionen des Avatars basierend auf der aktuellen Expansions-Einstellung zu passen.
:::

## Anzeigen von Symbolen {#displaying-icons}

Sie können ein Symbol innerhalb des `Avatar` anzeigen, indem Sie eine `Icon`-Komponente als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gast Benutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Die `Avatar`-Komponente verwendet das Label für Barrierefreiheit und Tooltip-Generierung. Die Methoden `setLabel()` und `setText()` sind Aliase, die beide das barrierefreie Label für den `Avatar` setzen.

:::info Automatisch berechnete Initialen
Wenn Sie einen `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem das erste Zeichen jedes Wortes genommen wird. Ein `Avatar` mit dem Label "John Doe" zeigt automatisch "JD" im UI an.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt das Label und generiert automatisch den Tooltip
avatar.setInitials("JS");       // Überschreibt die automatisch berechneten Initialen
```

:::tip Automatischer Tooltip
Die Komponente generiert automatisch einen Tooltip aus dem Label, sodass es einfach ist, den vollständigen Namen beim Hover anzuzeigen. Dieses Verhalten ist deaktiviert, wenn das Standardlabel „Avatar“ verwendet wird.
:::

## Klickereignisse {#click-events}

Die `Avatar`-Komponente implementiert `HasElementClickListener`, sodass Sie auf Benutzerklicks reagieren können. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Behandeln des Avatar-Klicks - z.B. Benutzerprofil öffnen
  System.out.println("Avatar geklickt!");
});
```

## Formen {#shapes}

Avatare können als Kreise oder Quadrate angezeigt werden. Die Standardform ist `CIRCLE`, die für Benutzeravatare üblich ist. Verwenden Sie `SQUARE` für Entitäten wie Teams, Unternehmen oder Anwendungen.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Themen {#themes}

Themen vermitteln eine Bedeutung oder einen Status; Sie können sie verwenden, um die Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer App anzupassen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standardaussehen
- `GRAY`: Neutrales, gedämpftes Aussehen
- `PRIMARY`: Betont primäre Aktionen oder Benutzer
- `SUCCESS`: Gibt einen positiven Status an (z.B. online)
- `WARNING`: Gibt Vorsicht an (z.B. abwesend)
- `DANGER`: Gibt Fehler- oder Beschäftigungsstatus an
- `INFO`: Bietet informativen Kontext

Jedes Thema hat auch eine umrissene Variante für eine hellere visuelle Behandlung:

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Expansitionen {#expanses}

Steuern Sie die Größe des Avatars mit der Methode `setExpanse()`. Die Komponente unterstützt neun Größenoptionen von `XXXSMALL` bis `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>

## Styling {#styling}

<TableBuilder name="Avatar" />
