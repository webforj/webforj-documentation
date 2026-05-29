---
title: Message
sidebar_position: 30
_i18n_hash: b90d101884ed5ce8f6be2604ec637aee
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Een `MessageDialog` is een modale dialoog ontworpen om een bericht aan de gebruiker weer te geven met een `OK`-knop om de dialoog te sluiten. Het blokkeert de uitvoer van de app totdat de gebruiker ermee interageert of het sluit vanwege een time-out.

<!-- INTRO_END -->

## Gebruiken {#usages}

Gebruik de statische `showMessageDialog`-methode om een basisbericht weer te geven.

```java
OptionDialog.showMessageDialog("Hallo Wereld!");
```

Voor meer controle over het uiterlijk en gedrag van de dialoog, maak je direct een instantie van `MessageDialog`.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hallo Wereld", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Berichttype {#message-type}

De `MessageDialog` ondersteunt de volgende berichttypen. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerp.

1. `PLAIN`: Toont het bericht zonder pictogram, met behulp van het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met behulp van het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een infopictogram naast het bericht, met behulp van het info-thema.

In het volgende voorbeeld configureert de code een berichtdialoog van het type `WARNING` met een aangepaste titel en bericht.

<ComponentDemo
path='/webforj/messagedialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java']}
height='350px'
/>

:::tip Dialoog & Knopthema
Standaard bepaalt de dialoog het thema op basis van het berichttype. Je kunt het thema van de dialoog aanpassen met de methode `setTheme(Theme theme)` en het knopthema onafhankelijk aanpassen met de methode `setButtonTheme(ButtonTheme theme)` om verschillende variaties te creëren.
:::

## Knoptekst {#button-text}

Je kunt de tekst van de dialoogknop configureren met behulp van `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hallo Wereld!", "Titel", "Begrepen");
```

## HTML-verwerking {#html-processing}

Standaard verwerkt de berichtdialoog en rendert HTML-inhoud. Je kunt deze functie uitschakelen door het zo te configureren dat het ruwe tekst weergeeft.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hallo Wereld</b>", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Time-out {#timeout}

De `MessageDialog` stelt je in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke meldingen of informatie die geen onmiddellijke interactie van de gebruiker vereist.

Je kunt de time-out voor de dialoog configureren met de methode `setTimeout(int timeout)`. De time-outduur is in seconden. Als de gespecificeerde tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Deze dialoog zal binnenkort time-outen", "Time-out");
dialog.setTimeout(2);
dialog.show();
```

## Beste praktijken {#best-practices}

1. **Duidelijke en Bondige Berichten**: Houd berichten kort en to the point en vermijd technische jargon; gebruik gebruiksvriendelijke taal.
2. **Geschikte Berichttypen**:
   - Gebruik `ERROR` voor kritieke problemen.
   - Gebruik `WARNING` voor waarschuwingsberichten.
   - Gebruik `INFO` voor algemene informatie.
3. **Consistente Theming**: Stem dialogen en knopthema's af op het ontwerp van je app.
4. **Verstandig Gebruik van Time-out**: Stel time-outs in voor niet-kritieke meldingen en zorg ervoor dat gebruikers voldoende tijd hebben om het bericht te lezen.
5. **Vermijd Overmatig Gebruik**: Gebruik dialoogvensters spaars om frustratie bij gebruikers te voorkomen en reserveer ze voor belangrijke berichten die actie of erkenning van de gebruiker vereisen.
