---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 3a915fc4eb3ca5d51dc1909a34eb5bd1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Der `Avatar`-Komponente bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Sie kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarsektionen, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

## Avatare erstellen {#creating-avatars}

Um einen `Avatar` zu erstellen, geben Sie ein Label an, das als zugänglicher Name dient. Die Komponente berechnet automatisch die Initialen, indem sie den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt einen Avatar, der "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen angeben, wenn Sie mehr Kontrolle darüber haben möchten, was angezeigt wird:

```java
// Erstellt einen Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Teampanels. Jeder `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Namen des Benutzers. Durch Klicken auf einen `Avatar` öffnet sich ein Dialog mit einer vergrößerten Ansicht.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Bilder anzeigen {#displaying-images}

Die `Avatar`-Komponente kann ein Bild anstelle von Initialen anzeigen, indem sie ein `Img`-Komponente als Kind einfügt. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor den Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgröße
Das Bild wird automatisch skaliert, um innerhalb der Abmessungen des Avatars basierend auf der aktuellen Expansionseinstellung zu passen.
:::

## Symbole anzeigen {#displaying-icons}

Sie können ein Symbol innerhalb des `Avatar` anzeigen, indem Sie ein `Icon`-Komponente als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gastbenutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Die `Avatar`-Komponente verwendet das Label zur Barrierefreiheit und zur Tooltip-Generierung. Die Methoden `setLabel()` und `setText()` sind Aliase, die beide das zugängliche Label für den `Avatar` setzen.

:::info Automatisch berechnete Initialen
Wenn Sie einen `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem der erste Buchstabe jedes Wortes genommen wird. Zum Beispiel wird "John Doe" zu "JD".
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt das Label und generiert automatisch den Tooltip
avatar.setInitials("JS");       // Überschreibt die automatisch berechneten Initialen
```

:::tip Automatischer Tooltip
Die Komponente generiert automatisch einen Tooltip aus dem Label, wodurch es leicht ist, den vollständigen Namen beim Hover zu sehen. Dieses Verhalten ist deaktiviert, wenn das Standardlabel "Avatar" verwendet wird.
:::

## Klickereignisse {#click-events}

Die `Avatar`-Komponente implementiert `HasElementClickListener`, sodass Sie auf Benutzerklicks reagieren können. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Bearbeite den Avatar-Klick - z. B. öffne das Benutzerprofil
  System.out.println("Avatar geklickt!");
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

Themen vermitteln Bedeutung oder Status; Sie können sie verwenden, um die Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer Anwendung anzupassen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standardansicht
- `GRAY`: Neutrale, gedämpfte Erscheinung
- `PRIMARY`: Betont primäre Aktionen oder Benutzer
- `SUCCESS`: Zeigt positiven Status an (z. B. online)
- `WARNING`: Zeigt Vorsicht an (z. B. abwesend)
- `DANGER`: Zeigt Fehler oder beschäftigten Status an
- `INFO`: Bietet informativen Kontext

Jedes Thema hat auch eine umrissene Variante für eine leichtere visuelle Behandlung:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Größen {#expanses}

Steuern Sie die Größe des Avatars mit der Methode `setExpanse()`. Die Komponente unterstützt neun Größenoptionen von `XXXSMALL` bis `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Styling {#styling}

<TableBuilder name="Avatar" />
