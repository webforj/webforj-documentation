---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Een `MessageDialog` is een modale dialoog ontworpen om een bericht aan de gebruiker weer te geven met een `OK`-knop om de dialoog te sluiten. Het blokkeert de app-uitvoering totdat de gebruiker erop reageert of het sluit vanwege een time-out.

<!-- INTRO_END -->

## Usages {#usages}

Gebruik de statische `showMessageDialog`-methode om een basisbericht weer te geven.

```java
OptionDialog.showMessageDialog("Hallo Wereld!");
```

Voor meer controle over het uiterlijk en gedrag van de dialoog, maak je rechtstreeks een `MessageDialog`-instant.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hallo Wereld", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Message type {#message-type}

De `MessageDialog` ondersteunt de volgende berichttypen. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een infopictogram naast het bericht, met het informatiethema.

In het volgende voorbeeld configureert de code een gemessage dialoog van het type `WARNING`, met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialoog & Knop Thema
Standaard bepaalt het thema van de dialoog het thema op basis van het berichttype. Je kunt het thema van de dialoog aanpassen met de methode `setTheme(Theme theme)` en onafhankelijk het knopthema aanpassen met de methode `setButtonTheme(ButtonTheme theme)` om verschillende variaties te creëren.
:::

## Button text {#button-text}

Je kunt de tekst van de dialoogknop configureren met de `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hallo Wereld!", "Titel", "Snap het");
```

## HTML processing {#html-processing}

Standaard verwerkt de message dialog HTML-inhoud en rendert deze. Je kunt deze functie uitschakelen door deze in te stellen om rauwe tekst weer te geven.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Wereld</b>", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

De `MessageDialog` staat je toe om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke meldingen of informatie die de onmiddellijke interactie van de gebruiker niet vereist.

Je kunt de time-out voor de dialoog configureren met de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Deze dialoog sluit binnenkort automatisch", "Time-out");
dialog.setTimeout(2);
dialog.show();
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Berichten**: Houd berichten kort en to the point en vermijd technische jargon; gebruik gebruikersvriendelijke taal.
2. **Geschikte Berichttypen**:
   - Gebruik `ERROR` voor kritieke problemen.
   - Gebruik `WARNING` voor voorzichtigheidsmeldingen.
   - Gebruik `INFO` voor algemene informatie.
3. **Consistente Theming**: Stem de thema's van de dialoog en knoppen af op het ontwerp van je applicaties.
4. **Voorzichtig Gebruik van Time-out**: Stel time-outs in voor niet-kritieke meldingen en zorg ervoor dat gebruikers voldoende tijd hebben om het bericht te lezen.
5. **Vermijd Overmatig Gebruik**: Gebruik dialoogvensters spaarzaam om frustratie bij gebruikers te voorkomen en voor belangrijke berichten die actie of bevestiging van de gebruiker vereisen.
