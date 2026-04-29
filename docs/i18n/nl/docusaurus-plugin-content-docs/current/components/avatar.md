---
title: Avatar
sidebar_position: 7
_i18n_hash: a09e8f3e668c6818045ca93f0747f100
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

De `Avatar` component biedt een visuele representatie van een gebruiker of entiteit. Het kan een afbeelding, automatisch berekende initialen, aangepaste initialen of een pictogram weergeven. Avatars worden vaak gebruikt om gebruikers te identificeren in commentsecties, navigatiemenu's, chatapplicaties en contactlijsten.

<!-- INTRO_END -->

## Avatars maken {#creating-avatars}

Om een `Avatar` te creëren, geef een label op dat als de toegankelijke naam dient. De component berekent automatisch initialen door de eerste letter van elk woord in het label te extraheren.

```java
// Creëert een avatar die "JD" weergeeft op basis van het label
Avatar avatar = new Avatar("John Doe");
```

Je kunt ook expliciete initialen opgeven als je meer controle wilt over wat er wordt weergegeven:

```java
// Creëert een avatar met aangepaste initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Het onderstaande voorbeeld toont avatars in de context van een teampaneel. Elke `Avatar` toont ofwel een profielafbeelding of automatisch gegenereerde initialen op basis van de naam van de gebruiker. Het klikken op een `Avatar` opent een dialoogvenster met een vergrote weergave.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Afbeeldingen weergeven {#displaying-images}

De `Avatar` component kan een afbeelding weergeven in plaats van initialen door een `Img` component als kind toe te voegen. Wanneer er een afbeelding is opgegeven, heeft deze voorrang boven initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar met een afbeelding
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Afbeelding Grootte
De afbeelding schaaft automatisch om binnen de afmetingen van de avatar te passen op basis van de huidige expanse-instelling.
:::

## Pictogrammen weergeven {#displaying-icons}

Je kunt een pictogram binnen de `Avatar` weergeven door een `Icon` component als kind toe te voegen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar met een pictogram
Avatar avatar = new Avatar("Gast Gebruiker", TablerIcon.create("user"));
```

## Label en initialen {#label-and-initials}

De `Avatar` component gebruikt het label voor toegankelijkheid en tooltipgeneratie. De methoden `setLabel()` en `setText()` zijn aliassen die beide het toegankelijke label voor de `Avatar` instellen.

:::info Automatisch berekende Initialen
Wanneer je een `Avatar` maakt met alleen een label, worden initialen automatisch berekend door het eerste teken van elk woord te nemen. Bijvoorbeeld, een `Avatar` met het label "John Doe" toont automatisch "JD" in de UI.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Stelt label in en genereert automatisch tooltip
avatar.setInitials("JS");       // Overschrijft automatisch berekende initialen
```

:::tip Automatische Tooltip
De component genereert automatisch een tooltip van het label, waardoor het gemakkelijk is om de volledige naam te zien bij het hoveren. Dit gedrag is uitgeschakeld wanneer je het standaardlabel `"Avatar"` gebruikt.
:::

## Klikgebeurtenissen {#click-events}

De `Avatar` component implementeert `HasElementClickListener`, waardoor je kunt reageren op gebruikersklikken. Dit is handig voor het activeren van acties zoals het openen van een gebruikersprofiel of het weergeven van een menu.

```java
avatar.onClick(event -> {
  // Behandel avatar klik - bijvoorbeeld, open gebruikersprofiel
  System.out.println("Avatar geklikt!");
});
```

## Vormen {#shapes}

Avatars kunnen worden weergegeven als cirkels of vierkanten. De standaardvorm is `CIRCLE`, wat standaard is voor gebruikersavatars. Gebruik `SQUARE` voor entiteiten zoals teams, bedrijven of toepassingen.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Thema's {#themes}

Thema's geven betekenis of status aan; je kunt ze gebruiken om beschikbaarheid aan te geven, belangrijke gebruikers te markeren of een overeenstemming met het ontwerp van je app te creëren.

De volgende thema's zijn beschikbaar:

- `DEFAULT`: Standaard uiterlijk
- `GRAY`: Neutraal, gedempt uiterlijk
- `PRIMARY`: Benadrukt primaire acties of gebruikers
- `SUCCESS`: Geeft een positieve status aan (bijv. online)
- `WARNING`: Geeft voorzichtigheid aan (bijv. weg)
- `DANGER`: Geeft een fout- of drukstatus aan
- `INFO`: Biedt informatieve context

Elk thema heeft ook een omtrekvariant voor een lichter visueel effect:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expansies {#expanses}

Beheer de grootte van de avatar met de methode `setExpanse()`. De component ondersteunt negen maatopties, variërend van `XXXSMALL` tot `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Stijl {#styling}

<TableBuilder name="Avatar" />
