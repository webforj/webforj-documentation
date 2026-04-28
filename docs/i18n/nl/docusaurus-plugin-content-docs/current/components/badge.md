---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Een `Badge` is een compacte, visueel onderscheidende label die wordt gebruikt om status, tellingen of korte stukjes contextuele informatie over te brengen. Of je nu een meldingsaantal wilt markeren, een item als "Nieuw" wilt aanduiden of de aandacht op een waarschuwing wilt vestigen, badges bieden je een lichte manier om die informatie direct in de gebruikersinterface naar voren te brengen.

<!-- INTRO_END -->

:::tip Gebruik van een `Badge`
Badges zijn geschikt voor meldingsaantallen, statuslabels en korte metadata zoals versietags of release-statussen. Houd de badge-tekst tot één of twee woorden zodat het label in één oogopslag leesbaar is.
:::

## Een badge maken {#creating-a-badge}

De eenvoudigste `Badge` neemt een tekststring. Je kunt ook een `BadgeTheme` direct in de constructor doorgeven om de visuele stijl meteen in te stellen. De constructor zonder argumenten is beschikbaar wanneer je een badge dynamisch moet bouwen en deze na creatie wilt configureren.

```java
Badge badge = new Badge("Nieuw");

// Met een thema
Badge primary = new Badge("Uitgelicht", BadgeTheme.SUCCESS);

// Dynamisch gebouwd
Badge status = new Badge();
status.setLabel("In behandeling");
status.setTheme(BadgeTheme.WARNING);
```

## Label {#label}

Je kunt op elk moment de tekstinhoud van een badge instellen of bijwerken met `setLabel()`. De `setText()` methode is een alias voor dezelfde bewerking; gebruik welke het meest natuurlijk leest in de context. Beide hebben overeenkomstige getters, `getLabel()` en `getText()`, als je de huidige waarde wilt teruglezen.

```java
Badge badge = new Badge();
badge.setLabel("Bijgewerkt");

// Equivalente
badge.setText("Bijgewerkt");

// Huidige waarde teruglezen
String current = badge.getLabel();
```

## Iconen {#icons}

Soms is een meer visuele benadering nuttig bij het overbrengen van informatie met een `Badge`. Badges ondersteunen iconen als inhoud. Geef een icoon door samen met de tekst met behulp van de `Badge(String, Component...)` constructor, of geef alleen een icoon door om een alleen-icoon badge te maken. Wanneer gecombineerd met tekst, wordt het icoon links van het label weergegeven.

Alleen-icoon badges werken bijzonder goed voor compacte statusindicatoren in dichte lay-outs waar een kort woord rommelig aan zou voelen. Het combineren van een icoon met tekst is een goede middenweg wanneer het icoon alleen mogelijk ambigu kan zijn. Een statussymbool wordt op zichzelf breed begrepen, maar het toevoegen van een kort tekstlabel elimineert giswerk voor gebruikers die het voor het eerst zien. Je kunt meerdere componenten aan de constructor doorgeven als je een rijkere prefix wilt samenstellen, hoewel in de praktijk een enkel icoon het meest gebruikelijke patroon is.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='345px'
/>
<!-- vale on -->

```java
// Icoon met tekst
Badge done = new Badge("Klaar", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Alleen icoon
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Gebruik in andere componenten {#usage-in-other-components}

### Knoppen {#buttons}

Koppel een `Badge` aan een `Button` met `setBadge()`. De badge verschijnt in de rechterbovenhoek van de knop, overlappend met de rand van de knop. Dit is een veelvoorkomend patroon voor meldingsaantallen op toolbar-acties of icoonkoppen. Omdat de badge een zelfstandige component is, is het volledig onafhankelijk van het thema en de grootte van de knop. Je kunt een primaire knop combineren met een gevaarlijke badge, of een ghost-knop met een succesbadge, en elke kant van de combinatie stijlt zichzelf zonder conflicten. Het later bijwerken van het aantal is zo eenvoudig als het aanroepen van `badge.setLabel()` met een nieuwe waarde; de knop hoeft niet te worden aangeraakt.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### Tabbladpaneel {#tabbed-pane}

Voeg een `Badge` toe als een suffix op een `Tab` met `setSuffixComponent()`. Dit is een natuurlijke aanvulling voor inbox-style tellingen of statusindicatoren op elk tabblad. Het is het soort patroon dat je ziet op e-mailclients of taakbeheerders waar het belangrijk is om activiteit op elk sectie in één oogopslag te signaleren. De badge zit aan de achterzijde van het tablabel, na eventuele prefix-inhoud, en blijft zichtbaar ongeacht welk tabblad momenteel actief is. Deze persistentie is opzettelijk: het verbergen van de badge op inactieve tabbladen zou het moeilijker maken om te weten welke secties aandacht nodig hebben zonder naar elk tabblad te schakelen.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## Stijling {#styling}

Badges ondersteunen verschillende stylingdimensies: thema kleuren om betekenis over te brengen, een uitgestrektheidsschaal om de grootte te controleren en CSS-eigenschappen voor gedetailleerde aanpassing.

### Thema's {#themes}

Zoals bij veel componenten in webforJ, komt de `Badge` in veertien thema's: zeven gevuld en zeven omrande varianten.

Vul thema's gebruiken een solide achtergrond en berekenen automatisch een tekstkleur die voldoet aan de contrastvereisten. Omrande varianten gebruiken in plaats daarvan een getinte achtergrond met een gekleurde rand, wat ze een subtiele optie maakt wanneer je wilt dat de badge de omringende inhoud aanvult in plaats van deze te domineren.

Pas een thema toe met `setTheme()` of via de constructor.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Aangepaste kleur {#custom-color}

Als de ingebouwde thema's niet overeenkomen met jouw kleurenpalet, stel dan een aangepaste zaadkleur in met behulp van de `--dwc-badge-seed` CSS-eigenschap. Vanuit deze enkele waarde derivseren de badge automatisch de achtergrond-, tekst-, en randkleuren, zodat elke combinatie leesbaar blijft zonder dat je elke afzonderlijke kleur zelf hoeft op te geven. Dit betekent dat je een badge kunt branden in elke kleur in jouw ontwerpsysteem met vertrouwen. HSL-waarden (Hue, Saturation, and Lightness) zijn hier bijzonder handig; alleen het verwisselen van de tint is voldoende om een volledig andere kleurenfamilie te produceren terwijl het contrast intact blijft.

```java
Badge badge = new Badge("Aangepast");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Grootte {#sizing}

Gebruik `setExpanse()` om de grootte van de badge te regelen. Negen maten zijn beschikbaar, variërend van `XXXSMALL` tot `XXXLARGE`, en de standaard is `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='300px'
/>
<!-- vale on -->

### Onderdelen en CSS-variabelen {#parts-and-css-variables}

<TableBuilder name="Badge" />
