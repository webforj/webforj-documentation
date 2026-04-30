---
title: Avatar
sidebar_position: 7
_i18n_hash: 7974a5de785a24d8b83507dd8c81d03d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

De `Avatar` component biedt een visuele weergave van een gebruiker of entiteit. Het kan een afbeelding, automatisch berekende initialen, aangepaste initialen of een pictogram weergeven. Avatars worden vaak gebruikt om gebruikers te identificeren in opmerkingen, navigatiemenu's, chatapplicaties en contactlijsten.

<!-- INTRO_END -->

## Avatars maken {#creating-avatars}

Om een `Avatar` te maken, geef een label door dat als toegankelijke naam dient. De component berekent automatisch initialen door de eerste letter van elk woord in het label te extraheren.

```java
// Maakt een avatar die "JD" weergeeft van het label
Avatar avatar = new Avatar("John Doe");
```

Je kunt ook expliciete initialen opgeven als je meer controle wilt over wat er wordt weergegeven:

```java
// Maakt een avatar met aangepaste initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Het onderstaande voorbeeld toont avatars in een teampanelen context. Elke `Avatar` weergeeft ofwel een profielfoto of auto-gegenerate initialen op basis van de naam van de gebruiker. Klikken op een `Avatar` opent een dialoogvenster met een vergrote weergave.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## Afbeeldingen weergeven {#displaying-images}

De `Avatar` component kan een afbeelding weergeven in plaats van initialen door een `Img` component als kind toe te voegen. Wanneer er een afbeelding wordt verstrekt, heeft deze voorrang boven de initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar met een afbeelding
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Afbeeldingsgrootte
De afbeelding schaalt automatisch om binnen de afmetingen van de avatar te passen op basis van de huidige expanse-instelling.
:::

## Pictogrammen weergeven {#displaying-icons}

Je kunt een pictogram binnen de `Avatar` weergeven door een `Icon` component als kind toe te voegen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar met een pictogram
Avatar avatar = new Avatar("Gastgebruiker", TablerIcon.create("user"));
```

## Label en initialen {#label-and-initials}

De `Avatar` component gebruikt het label voor toegankelijkheid en tooltip-generatie. De `setLabel()` en `setText()` methoden zijn aliassen die beide het toegankelijke label voor de `Avatar` instellen.

:::info Automatisch berekende initialen
Wanneer je een `Avatar` met alleen een label maakt, worden de initialen automatisch berekend door het eerste teken van elk woord te nemen. Een `Avatar` met het label "John Doe" toont automatisch "JD" in de UI.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Zet label en genereert automatisch tooltip
avatar.setInitials("JS");       // Overschrijft automatisch berekende initialen
```

:::tip Automatische Tooltip
De component genereert automatisch een tooltip van het label, waardoor het gemakkelijk is om de volledige naam bij hover te zien. Dit gedrag is uitgeschakeld bij gebruik van het standaardlabel `"Avatar"`.
:::

## Klikgebeurtenissen {#click-events}

De `Avatar` component implementeert `HasElementClickListener`, waardoor je kunt reageren op gebruikerskliks. Dit is nuttig voor het activeren van acties zoals het openen van een gebruikersprofiel of het weergeven van een menu.

```java
avatar.onClick(event -> {
  // Behandel avatar klik - bijvoorbeeld, open gebruikersprofiel
  System.out.println("Avatar geklikt!");
});
```

## Vormen {#shapes}

Avatars kunnen worden weergegeven als cirkels of vierkanten. De standaardvorm is `CIRCLE`, wat gebruikelijk is voor gebruikersavatars. Gebruik `SQUARE` voor entiteiten zoals teams, bedrijven of applicaties.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Thema's {#themes}

Thema's geven betekenis of status aan; je kunt ze gebruiken om beschikbaarheid aan te geven, belangrijke gebruikers te markeren of het ontwerp van je app te laten overeenkomen.

De volgende thema's zijn beschikbaar:

- `DEFAULT`: Standaard uiterlijk
- `GRAY`: Neutraal, gedempt uiterlijk
- `PRIMARY`: Benadrukt primaire acties of gebruikers
- `SUCCESS`: Geeft een positieve status aan (bijv. online)
- `WARNING`: Geeft voorzichtigheid aan (bijv. afwezig)
- `DANGER`: Geeft een fout of drukke status aan
- `INFO`: Biedt informatieve context

Elk thema heeft ook een omrandde variant voor een lichtere visuele behandeling:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Uitersten {#expanses}

Beheer de grootte van de avatar met de `setExpanse()` methode. De component ondersteunt negen maatopties variërend van `XXXSMALL` tot `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## Stijlen {#styling}

<TableBuilder name="Avatar" />
