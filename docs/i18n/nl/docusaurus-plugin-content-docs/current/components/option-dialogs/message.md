---
title: Message
sidebar_position: 30
_i18n_hash: 4540b0f4317acc598d4970d0f16ae757
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Een `MessageDialog` is een modale dialoog die is ontworpen om een bericht aan de gebruiker weer te geven met een `OK` knop om de dialoog te sluiten. Het blokkeert de uitvoering van de app totdat de gebruiker ermee interactie heeft of het sluit vanwege een timeout.

<!-- INTRO_END -->

## Usages {#usages}

Gebruik de statische `showMessageDialog` methode om een basisbericht weer te geven.

```java
OptionDialog.showMessageDialog("Hallo wereld!");
```

Voor meer controle over het uiterlijk en gedrag van de dialoog, maak je rechtstreeks een `MessageDialog` instantie aan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hallo wereld", "Hallo wereld", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Message type {#message-type}

De `MessageDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ ontwerpsysteem.

1. `PLAIN`: Toont het bericht zonder pictogram, met gebruik van het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met gebruik van het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een informatiepictogram naast het bericht, met gebruik van het informatiethema.

In het onderstaande voorbeeld configureert de code een messagedialoog van type `WARNING` met een aangepast titel en bericht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialoog & Knop Themas
Standaard bepaalt de dialoog het thema op basis van het berichttype. Je kunt het thema van de dialoog aanpassen met de `setTheme(Theme theme)` methode en het knopthema onafhankelijk aanpassen met de `setButtonTheme(ButtonTheme theme)` methode om verschillende variaties te creëren.
:::

## Button text {#button-text}

Je kunt de tekst van de dialoogknop configureren met de `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hallo wereld!", "Titel", "Snap het");
```

## HTML processing {#html-processing}

Standaard verwerkt de messagedialoog HTML-inhoud en rendert deze. Je kunt deze functie uitschakelen door het zo te configureren dat het ruwe tekst weergeeft.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hallo wereld</b>", "Hallo wereld", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

De `MessageDialog` stelt je in staat om een timeoutduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritische meldingen of informatie die geen directe interactie van de gebruiker vereist.

Je kunt de timeout voor de dialoog configureren met de `setTimeout(int timeout)` methode. De timeoutduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Deze dialoog sluit binnenkort automatisch", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## Best practices {#best-practices}

1. **Duidelijke en Bondige Berichten**: Houd berichten kort en to the point en vermijd technische jargon; gebruik gebruikersvriendelijke taal.
2. **Geschikte Berichttypes**:
   - Gebruik `ERROR` voor kritieke problemen.
   - Gebruik `WARNING` voor voorzichtigheidswaarschuwingen.
   - Gebruik `INFO` voor algemene informatie.
3. **Consistente Theming**: Stem de thema's van dialoog en knoppen af op het ontwerp van je app.
4. **Voorzichtig Gebruik van Timeout**: Stel timeouts in voor niet-kritische meldingen en zorg ervoor dat gebruikers voldoende tijd hebben om het bericht te lezen.
5. **Vermijd Overmatig Gebruik**: Gebruik dialoogsparend om frustratie bij gebruikers te voorkomen en bewaar ze voor belangrijke berichten die gebruikersactie of erkenning vereisen.
