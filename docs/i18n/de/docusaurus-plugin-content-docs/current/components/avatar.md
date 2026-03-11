---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 928db2bff36515d2d9a41aeca9a233e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Die `Avatar`-Komponente bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Sie kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarsektionen, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

<!-- INTRO_END -->

## Avatare erstellen {#creating-avatars}

Um einen `Avatar` zu erstellen, übergeben Sie ein Label, das als zugänglicher Name dient. Die Komponente berechnet automatisch die Initialen, indem sie den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt einen Avatar, der "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen angeben, wenn Sie mehr Kontrolle über die Anzeige wünschen:

```java
// Erstellt einen Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Team-Panels. Jeder `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Benutzernamen. Ein Klick auf einen `Avatar` öffnet einen Dialog mit einer vergrößerten Ansicht.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Bilder anzeigen {#displaying-images}

Die `Avatar`-Komponente kann anstelle von Initialen ein Bild anzeigen, indem sie eine `Img`-Komponente als Kind einfügt. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor den Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgröße
Das Bild wird automatisch skaliert, um in die Dimensionen des Avatars basierend auf der aktuellen Expanse-Einstellung zu passen.
:::

## Symbole anzeigen {#displaying-icons}

Sie können ein Symbol im `Avatar` anzeigen, indem Sie eine `Icon`-Komponente als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gastbenutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Die `Avatar`-Komponente verwendet das Label für die Barrierefreiheit und die Tooltip-Generierung. Die Methoden `setLabel()` und `setText()` sind Aliase, die beide das zugängliche Label für den `Avatar` festlegen.

:::info Automatisch berechnete Initialen
Wenn Sie einen `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem der erste Buchstabe jedes Wortes genommen wird. Ein `Avatar` mit dem Label "John Doe" zeigt automatisch "JD" in der Benutzeroberfläche an.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt das Label und generiert automatisch den Tooltip
avatar.setInitials("JS");       // Überschreibt die automatisch berechneten Initialen
```

:::tip Automatischer Tooltip
Die Komponente generiert automatisch einen Tooltip aus dem Label, sodass der vollständige Name beim Überfahren angezeigt wird. Dieses Verhalten ist deaktiviert, wenn das Standardlabel `"Avatar"` verwendet wird.
:::

## Klickereignisse {#click-events}

Die `Avatar`-Komponente implementiert `HasElementClickListener`, was es Ihnen ermöglicht, auf Benutzerklicks zu reagieren. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Behandelt den Klick auf den Avatar - z.B. Benutzerprofil öffnen
  System.out.println("Avatar angeklickt!");
});
```

## Formen {#shapes}

Avatare können als Kreise oder Quadrate angezeigt werden. Die Standardform ist `CIRCLE`, die für Benutzeravatare üblich ist. Verwenden Sie `SQUARE` für Entitäten wie Teams, Unternehmen oder Anwendungen.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Themen {#themes}

Themen vermitteln Bedeutung oder Status; Sie können sie verwenden, um die Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer Anwendung abzustimmen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standardaussehen
- `GRAY`: Neutrales, zurückhaltendes Aussehen
- `PRIMARY`: Betont primäre Aktionen oder Benutzer
- `SUCCESS`: Gibt einen positiven Status an (z. B. online)
- `WARNING`: Weist auf Vorsicht hin (z. B. abwesend)
- `DANGER`: Weist auf Fehler oder beschäftigten Status hin
- `INFO`: Bietet informativen Kontext

Jedes Thema hat auch eine umrandete Variante für eine hellere visuelle Darstellung:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expanse {#expanses}

Steuern Sie die Größe des Avatars mit der Methode `setExpanse()`. Die Komponente unterstützt neun Größenoptionen von `XXXSMALL` bis `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Styling {#styling}

<TableBuilder name="Avatar" />
