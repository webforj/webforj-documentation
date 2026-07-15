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

De `Avatar` component biedt een visuele weergave van een gebruiker of entiteit. Het kan een afbeelding, automatisch berekende initialen, aangepaste initialen of een pictogram weergeven. Avatars worden vaak gebruikt om gebruikers te identificeren in commentaarsecties, navigatiemenu's, chattoepassingen en contactlijsten.

<!-- INTRO_END -->

## Avatars maken {#creating-avatars}

Om een `Avatar` te maken, geef een label door dat als de toegankelijke naam dient. De component berekent automatisch initialen door de eerste letter van elk woord in het label te extraheren.

```java
// Creëert een avatar die "JD" weergeeft op basis van het label
Avatar avatar = new Avatar("John Doe");
```

Je kunt ook expliciete initialen opgeven als je liever meer controle hebt over wat er wordt weergegeven:

```java
// Creëert een avatar met aangepaste initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Het onderstaande voorbeeld toont avatars in een team panel context. Elke `Avatar` toont ofwel een profielafbeelding of automatisch gegenereerde initialen op basis van de naam van de gebruiker. Het klikken op een `Avatar` opent een dialoogvenster met een vergrote weergave.

<ComponentDemo
path='/webforj/avatar'
files={[
  'src/main/java/com/webforj/samples/views/avatar/AvatarView.java',
  'src/main/frontend/css/avatar/avatar.css',
]}
height='500px'
/>

## Afbeeldingen weergeven {#displaying-images}

De `Avatar` component kan een afbeelding weergeven in plaats van initialen door een `Img` component als kind te voegen. Wanneer een afbeelding wordt verstrekt, heeft deze voorrang boven initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar met een afbeelding
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Afbeelding formaten
De afbeelding schaalt automatisch om binnen de afmetingen van de avatar te passen op basis van de huidige expanse-instelling.
:::

## Pictogrammen weergeven {#displaying-icons}

Je kunt een pictogram in de `Avatar` weergeven door een `Icon` component als kind toe te voegen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar met een pictogram
Avatar avatar = new Avatar("Gastgebruiker", TablerIcon.create("user"));
```

## Label en initialen {#label-and-initials}

De `Avatar` component gebruikt het label voor toegankelijkheid en tooltip-generatie. De `setLabel()` en `setText()` methoden zijn aliassen die beiden het toegankelijke label voor de `Avatar` instellen.

:::info Automatisch berekende initialen
Wanneer je een `Avatar` maakt met alleen een label, worden initialen automatisch berekend door het eerste teken van elk woord te nemen. Bijvoorbeeld, een `Avatar` met het label "John Doe" toont automatisch "JD" in de UI.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Stelt label in en genereert automatisch tooltip
avatar.setInitials("JS");       // Overschrijf automatisch berekende initialen
```

:::tip Automatische Tooltip
De component genereert automatisch een tooltip op basis van het label, waardoor het eenvoudig is om de volledige naam bij hover te zien. Dit gedrag is uitgeschakeld bij gebruik van het standaardlabel `"Avatar"`.
:::

## Klikgebeurtenissen {#click-events}

De `Avatar` component implementeert `HasElementClickListener`, waarmee je kunt reageren op gebruikersklikken. Dit is handig voor het activeren van acties zoals het openen van een gebruikersprofiel of het weergeven van een menu.

```java
avatar.onClick(event -> {
  // Behandel avatar klik - bijvoorbeeld, open gebruikersprofiel
  System.out.println("Avatar geklikt!");
});
```

## Vormen {#shapes}

Avatars kunnen worden weergegeven als cirkels of vierkanten. De standaardvorm is `CIRCLE`, wat gebruikelijk is voor gebruikersavatars. Gebruik `SQUARE` voor entiteiten zoals teams, bedrijven of applicaties.

<ComponentDemo
path='/webforj/avatarshapes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java']}
height='100px'
/>

## Thema's {#themes}

Thema's geven betekenis of status weer; je kunt ze gebruiken om beschikbaarheid aan te geven, belangrijke gebruikers te markeren, of om te passen bij het ontwerp van je app.

De volgende thema's zijn beschikbaar:

- `DEFAULT`: Standaard uiterlijk
- `GRAY`: Neutraal, gedempt uiterlijk
- `PRIMARY`: Benadrukt primaire acties of gebruikers
- `SUCCESS`: Geeft een positieve status aan (bijv., online)
- `WARNING`: Geeft voorzichtigheid aan (bijv., weg)
- `DANGER`: Geeft een fout of bezette status aan
- `INFO`: Biedt informatieve context

Elk thema heeft ook een omrandde variant voor een lichtere visuele behandeling:

<ComponentDemo
path='/webforj/avatarthemes'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java']}
height='120px'
/>

## Expansies {#expanses}

Beheer de grootte van de avatar met behulp van de `setExpanse()` methode. De component ondersteunt negen maatopties variërend van `XXXSMALL` tot `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses'
files={['src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java']}
height='100px'
/>

## Stijlen {#styling}

<TableBuilder name="Avatar" />
