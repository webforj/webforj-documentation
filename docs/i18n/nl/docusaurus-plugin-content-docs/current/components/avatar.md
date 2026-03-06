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

De `Avatar` component biedt een visuele representatie van een gebruiker of entiteit. Het kan een afbeelding, automatisch berekende initialen, aangepaste initialen of een pictogram weergeven. Avatars worden vaak gebruikt om gebruikers te identificeren in opmerkingen, navigatiemenu's, chatapplicaties en contactlijsten.

<!-- INTRO_END -->

## Avatars maken {#creating-avatars}

Om een `Avatar` te maken, geef je een label door dat dient als de toegankelijke naam. De component berekent automatisch initialen door de eerste letter van elk woord in het label te extraheren.

```java
// Maakt een avatar die "JD" weergeeft op basis van het label
Avatar avatar = new Avatar("John Doe");
```

Je kunt ook expliciete initialen opgeven als je meer controle wilt over wat er wordt weergegeven:

```java
// Maakt een avatar met aangepaste initialen
Avatar avatar = new Avatar("John Doe", "J");
```

Het onderstaande voorbeeld toont avatars in een teampaneelcontext. Elke `Avatar` toont ofwel een profielfoto of automatisch gegenereerde initialen op basis van de naam van de gebruiker. Klikken op een `Avatar` opent een dialoog met een vergrote weergave.

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## Afbeeldingen weergeven {#displaying-images}

De `Avatar` component kan een afbeelding weergeven in plaats van initialen door een `Img` component als kind toe te voegen. Wanneer een afbeelding wordt opgegeven, krijgt deze de voorkeur boven initialen.

```java
import com.webforj.component.html.elements.Img;

// Avatar met een afbeelding
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip Afbeeldingsgrootte
De afbeelding schaalt automatisch om binnen de dimensies van de avatar te passen op basis van de huidige expanse-instelling.
:::

## Pictogrammen weergeven {#displaying-icons}

Je kunt een pictogram binnen de `Avatar` weergeven door een `Icon` component als kind toe te voegen:

```java
import com.webforj.component.icons.TablerIcon;

// Avatar met een pictogram
Avatar avatar = new Avatar("Gastgebruiker", TablerIcon.create("user"));
```

## Label en initialen {#label-and-initials}

De `Avatar` component gebruikt het label voor toegankelijkheid en het genereren van tooltips. De methoden `setLabel()` en `setText()` zijn aliassen die beide het toegankelijke label voor de `Avatar` instellen.

:::info Automatisch berekende initialen
Wanneer je een `Avatar` maakt met alleen een label, worden de initialen automatisch berekend door het eerste teken van elk woord te nemen. Bijvoorbeeld, een `Avatar` met het label "John Doe" toont automatisch "JD" in de gebruikersinterface.
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // Stelt label in en genereert automatisch tooltip
avatar.setInitials("JS");       // Overtreedt automatisch berekende initialen
```

:::tip Automatische Tooltip
De component genereert automatisch een tooltip uit het label, waardoor het gemakkelijk is om de volledige naam bij hover te zien. Dit gedrag is uitgeschakeld wanneer het standaardlabel `"Avatar"` wordt gebruikt.
:::

## Klikgebeurtenissen {#click-events}

De `Avatar` component implementeert `HasElementClickListener`, waarmee je kunt reageren op gebruikersklikken. Dit is nuttig voor het activeren van acties zoals het openen van een gebruikersprofiel of het weergeven van een menu.

```java
avatar.onClick(event -> {
  // Verwerk avatar klik - bijvoorbeeld, open gebruikersprofiel
  System.out.println("Avatar geklikt!");
});
```

## Vormen {#shapes}

Avatars kunnen worden weergegeven als cirkels of vierkanten. De standaardvorm is `CIRCLE`, wat gebruikelijk is voor gebruikersavatars. Gebruik `SQUARE` voor entiteiten zoals teams, bedrijven of applicaties.

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## Thema's {#themes}

Thema's geven betekenis of status aan, je kunt ze gebruiken om beschikbaarheid aan te geven, belangrijke gebruikers te markeren of aan te passen aan het ontwerp van je app.

De volgende thema's zijn beschikbaar:

- `DEFAULT`: Standaard uiterlijk
- `GRAY`: Neutraal, gedempt uiterlijk
- `PRIMARY`: Benadrukt primaire acties of gebruikers
- `SUCCESS`: Geeft een positieve status aan (bijv. online)
- `WARNING`: Geeft voorzichtigheid aan (bijv. afwezig)
- `DANGER`: Geeft een fout of drukke status aan
- `INFO`: Biedt informatieve context

Elk thema heeft ook een omrande variant voor een lichtere visuele behandeling:

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## Expansies {#expanses}

Beheer de grootte van de avatar met de methode `setExpanse()`. De component ondersteunt negen maat opties variërend van `XXXSMALL` tot `XXXLARGE`.

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## Stijling {#styling}

<TableBuilder name="Avatar" />
