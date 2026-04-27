---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Een `Badge` is een compact, visueel onderscheidend label dat wordt gebruikt om status, tellingen of korte stukjes contextuele informatie over te brengen. Of je nu een notificatietelling wilt markeren, een item wilt aanduiden als "Nieuw" of aandacht wilt vragen voor een waarschuwing, badges bieden je een lichte manier om die informatie rechtstreeks in de gebruikersinterface weer te geven.

<!-- INTRO_END -->

:::tip Gebruik van een `Badge`
Badges werken goed voor notificatietellingen, statuslabels en korte metadata zoals versie-tags of release-staten. Houd de badge-tekst tot één of twee woorden zodat het label in één oogopslag leesbaar is.
:::

## Een badge aanmaken {#creating-a-badge}

De eenvoudigste `Badge` neemt een tekststring. Je kunt ook een `BadgeTheme` direct in de constructor doorgeven om de visuele stijl onmiddellijk in te stellen. De constructor zonder argumenten is beschikbaar wanneer je een badge dynamisch wilt maken en deze na de creatie wilt configureren.

```java
Badge badge = new Badge("Nieuw");

// Met een thema
Badge primary = new Badge("Uitgelicht", BadgeTheme.SUCCESS);

// Dynamisch gebouwd
Badge status = new Badge();
status.setLabel("In afwachting");
status.setTheme(BadgeTheme.WARNING);
```

## Label {#label}

Je kunt de tekstinhoud van een badge op elk moment instellen of bijwerken met `setLabel()`. De `setText()`-methode is een alias voor dezelfde bewerking; gebruik wat het natuurlijkst leest in de context. Beide hebben overeenkomstige getters, `getLabel()` en `getText()`, als je de huidige waarde wilt lezen.

```java
Badge badge = new Badge();
badge.setLabel("Bijgewerkt");

// Equivalent
badge.setText("Bijgewerkt");

// Lees de waarde terug
String current = badge.getLabel();
```

## Iconen {#icons}

Soms is een meer visuele benadering nuttig bij het overbrengen van informatie met een `Badge`. Badges ondersteunen in sloticonen. Geef een pictogram door naast de tekst met behulp van de `Badge(String, Component...)` constructor, of geef alleen een pictogram door om een pictogram-only badge te maken. In combinatie met tekst wordt het pictogram aan de linkerkant van het label weergegeven.

Pictogram-only badges werken vooral goed voor compacte statusindicatoren in dichte lay-outs waar een kort woord rommelig zou aanvoelen. Een pictogram combineren met tekst is een goede middenweg wanneer het pictogram op zichzelf ambigu kan zijn. Een statussymbool is algemeen begrepen op zichzelf, maar het toevoegen van een kort tekstlabel verwijdert de gissing voor nieuwe gebruikers. Je kunt meerdere componenten doorgeven aan de constructor als je een rijkere prefix wilt samenstellen, hoewel in de praktijk een enkel pictogram de meest voorkomende patroon is.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
/>
<!-- vale on -->

```java
// Pictogram met tekst
Badge done = new Badge("Klaar", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Alleen pictogram
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Gebruik in andere componenten {#usage-in-other-components}

### Knoppen {#buttons}

Koppel een `Badge` aan een `Button` met `setBadge()`. De badge verschijnt in de rechterbovenhoek van de knop, overlappend aan de rand van de knop. Dit is een veelvoorkomend patroon voor notificatietellingen bij toolbar-acties of pictogramknoppen. Omdat de badge een zelfstandige component is, is deze volledig onafhankelijk van het thema en de grootte van de knop. Je kunt een primaire knop koppelen aan een gevaarlijke badge, of een ghost-knop aan een succesvolle badge, en elke kant van de combinatie stylet zichzelf zonder conflicten. Het bijwerken van de telling later is even eenvoudig als het aanroepen van `badge.setLabel()` met een nieuwe waarde; de knop hoeft niet te worden aangeraakt.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### Tabblad-paneel {#tabbed-pane}

Voeg een `Badge` als suffix toe aan een `Tab` met `setSuffixComponent()`. Dit is een natuurlijke optie voor inboxstijl tellingen of statusindicatoren op elk tabblad. Het is het soort patroon dat je ziet in e-mailclients of taakbeheerders waar het belangrijk is om activiteit op elk deel in één oogopslag te signaleren. De badge bevindt zich aan de achterkant van het tablabel, na eventuele prefixinhoud, en blijft zichtbaar ongeacht welk tabblad momenteel actief is. Deze persistentie is opzettelijk: het verbergen van de badge op inactieve tabbladen zou het moeilijker maken om te weten welke secties aandacht nodig hebben zonder naar elk tabblad te schakelen.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## Stijlen {#styling}

Badges ondersteunen verschillende stijlparameters: themakleuren om betekenis over te brengen, een expanse-schaal om de grootte te regelen, en CSS-eigenschappen voor fijne afstemming.

### Thema's {#themes}

Net als bij veel componenten in webforJ, komt de `Badge` in veertien thema's: zeven gevulde en zeven omlijnde varianten.

Gevulde thema's gebruiken een solide achtergrond en berekenen automatisch een tekstkleur die voldoet aan de contrastvereisten. Omlijnde varianten gebruiken in plaats daarvan een getinte achtergrond met een gekleurde rand, wat ze een subtielere optie maakt wanneer je wilt dat de badge de omringende inhoud aanvult in plaats van deze te domineren.

Pas een thema toe met `setTheme()` of via de constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Aangepaste kleur {#custom-color}

Als de ingebouwde thema's niet bij je palet passen, stel dan een aangepaste basis kleur in met de CSS-eigenschap `--dwc-badge-seed`. Vanuit deze enkele waarde derivéert de badge automatisch de achtergrond-, tekst- en randkleuren, zodat elke combinatie leesbaar blijft zonder dat je elke afzonderlijke kleur zelf hoeft op te geven. Dit betekent dat je een badge aan elke kleur in je ontwerpsysteem kunt aanpassen met vertrouwen. Hue, Saturatie en Lichtheid (HSL) waarden zijn hier bijzonder handig; het alleen wisselen van de tint is al genoeg om een compleet andere kleurfamilie te produceren terwijl het contrast intact blijft.

```java
Badge badge = new Badge("Aangepast");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Grootte {#sizing}

Gebruik `setExpanse()` om de badge-grootte te regelen. Er zijn negen maten beschikbaar, variërend van `XXXSMALL` tot `XXXLARGE`, en de standaard is `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### Onderdelen en CSS-variabelen {#parts-and-css-variables}

<TableBuilder name="Badge" />
