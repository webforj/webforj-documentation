---
title: Avatar
sidebar_position: 7
_i18n_hash: 7974a5de785a24d8b83507dd8c81d03d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

Die `Avatar`-Komponente bietet eine visuelle Darstellung eines Benutzers oder einer Entität. Sie kann ein Bild, automatisch berechnete Initialen, benutzerdefinierte Initialen oder ein Symbol anzeigen. Avatare werden häufig verwendet, um Benutzer in Kommentarsektionen, Navigationsmenüs, Chat-Anwendungen und Kontaktlisten zu identifizieren.

<!-- INTRO_END -->

## Erstellen von Avataren {#creating-avatars}

Um einen `Avatar` zu erstellen, übergeben Sie ein Label, das als zugänglicher Name dient. Die Komponente berechnet automatisch die Initialen, indem sie den ersten Buchstaben jedes Wortes im Label extrahiert.

```java
// Erstellt einen Avatar, der "JD" aus dem Label anzeigt
Avatar avatar = new Avatar("John Doe");
```

Sie können auch explizite Initialen angeben, wenn Sie mehr Kontrolle über das Anzeigeformat haben möchten:

```java
// Erstellt einen Avatar mit benutzerdefinierten Initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Das folgende Beispiel zeigt Avatare im Kontext eines Team-Panels. Jeder `Avatar` zeigt entweder ein Profilbild oder automatisch generierte Initialen basierend auf dem Namen des Benutzers. Ein Klick auf einen `Avatar` öffnet einen Dialog mit einer vergrößerten Ansicht.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Anzeigen von Bildern {#displaying-images}

Die `Avatar`-Komponente kann ein Bild anstelle von Initialen anzeigen, indem sie ein `Img`-Komponente als Kind einfügt. Wenn ein Bild bereitgestellt wird, hat es Vorrang vor Initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar mit einem Bild
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Bildgrößenanpassung
Das Bild wird automatisch so skaliert, dass es innerhalb der Abmessungen des Avatars basierend auf den aktuellen Expansionseinstellungen passt.
:::

## Anzeigen von Symbolen {#displaying-icons}

Sie können ein Symbol innerhalb des `Avatar` anzeigen, indem Sie ein `Icon`-Komponente als Kind hinzufügen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar mit einem Symbol
Avatar avatar = new Avatar("Gastbenutzer", TablerIcon.create("user"));
```

## Label und Initialen {#label-and-initials}

Die `Avatar`-Komponente verwendet das Label für die Barrierefreiheit und zur Generierung von Tooltips. Die Methoden `setLabel()` und `setText()` sind Alternativen, die beide das zugängliche Label für den `Avatar` festlegen.

:::info Automatisch berechnete Initialen
Wenn Sie einen `Avatar` nur mit einem Label erstellen, werden die Initialen automatisch berechnet, indem der erste Buchstabe jedes Wortes genommen wird. Beispielsweise zeigt ein `Avatar` mit dem Label "John Doe" automatisch "JD" in der Benutzeroberfläche an.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Setzt das Label und generiert automatisch den Tooltip
avatar.setInitials("JS");       // Überschreibt die automatisch berechneten Initialen
```

:::tip Automatischer Tooltip
Die Komponente generiert automatisch einen Tooltip aus dem Label, sodass der volle Name beim Hover angezeigt werden kann. Dieses Verhalten ist deaktiviert, wenn das Standardlabel `"Avatar"` verwendet wird.
:::

## Klickereignisse {#click-events}

Die `Avatar`-Komponente implementiert `HasElementClickListener`, wodurch Sie auf Benutzerklicks reagieren können. Dies ist nützlich, um Aktionen wie das Öffnen eines Benutzerprofils oder das Anzeigen eines Menüs auszulösen.

```java
avatar.onClick(event -> {
  // Verarbeitet den Avatar-Klick - z. B. Benutzerprofil öffnen
  System.out.println("Avatar geklickt!");
});
```

## Formen {#shapes}

Avatare können als Kreise oder Quadrate angezeigt werden. Die Standardform ist `CIRCLE`, die für Benutzeravatare üblich ist. Verwenden Sie `SQUARE` für Entitäten wie Teams, Unternehmen oder Anwendungen.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Themen {#themes}

Themen vermitteln Bedeutung oder Status; Sie können sie verwenden, um Verfügbarkeit anzuzeigen, wichtige Benutzer hervorzuheben oder das Design Ihrer App abzustimmen.

Die folgenden Themen sind verfügbar:

- `DEFAULT`: Standarderscheinung
- `GRAY`: Neutrale, gedämpfte Erscheinung
- `PRIMARY`: Hebt primäre Aktionen oder Benutzer hervor
- `SUCCESS`: Zeigt positiven Status an (z. B. online)
- `WARNING`: Zeigt Vorsicht an (z. B. abwesend)
- `DANGER`: Zeigt einen Fehler oder einen beschäftigten Status an
- `INFO`: Bietet kontextbezogene Informationen

Jedes Thema hat auch eine umrandete Variante für eine hellere visuelle Behandlung:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Größen {#expanses}

Steuern Sie die Avatargröße mit der Methode `setExpanse()`. Die Komponente unterstützt neun Größenoptionen von `XXXSMALL` bis `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Styling {#styling}

<TableBuilder name="Avatar" />
