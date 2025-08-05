---
sidebar_position: 30
title: Message
_i18n_hash: 575bdfd5364ffdbd911ac0ebe0628359
---
# Berichtdialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Een `MessageDialog` is een modale dialoog ontworpen om een bericht aan de gebruiker weer te geven met een `OK`-knop om de dialoog te sluiten. Het blokkeert de uitvoering van de app totdat de gebruiker ermee interacteert of het sluit door een time-out.

```java
OptionDialog.showMessageDialog("Hallo Wereld!");
```

## Toepassingen {#usages}

De Berichtdialoog biedt een manier om informatieve waarschuwingen weer te geven, zoals meldingen, updates of eenvoudige berichten die alleen vereisen dat de gebruiker deze erkent zonder enige invoer te geven.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hallo Wereld", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Berichttype {#message-type}

De `MessageDialog` ondersteunt de volgende berichttypes. Wanneer je een type configureert, toont de dialoog een pictogram naast het bericht, en het thema van de dialoog wordt bijgewerkt volgens de regels van het webforJ-ontwerp systeem.

1. `PLAIN`: Toont het bericht zonder een pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een info-pictogram naast het bericht, met het info-thema.

In het volgende voorbeeld configureert de code een berichtdialoog van type `WARNING`. met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialoog & Knopthema
Standaard bepaalt de dialoog het thema op basis van het berichttype. Je kunt het thema van de dialoog aanpassen met de `setTheme(Theme theme)`-methode en het knopthema onafhankelijk aanpassen met de `setButtonTheme(ButtonTheme theme)`-methode om verschillende variaties te creëren.
:::

## Knoptekst {#button-text}

Je kunt de tekst van de dialoogknop configureren met de `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hallo Wereld!", "Titel", "Begrijp het");
```

## HTML-verwerking {#html-processing}

Standaard verwerkt de berichtdialoog HTML-inhoud en rendert deze. Je kunt deze functie uitschakelen door te configureren dat alleen platte tekst moet worden weergegeven.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Wereld</b>", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Time-out {#timeout}

De `MessageDialog` stelt je in staat om een time-outduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritische meldingen of informatie die geen directe interactie van de gebruiker vereist.

Je kunt de time-out voor de dialoog configureren met de `setTimeout(int timeout)`-methode. De time-outduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Deze dialoog zal binnenkort tijdslimiet hebben", "Time-out");
dialog.setTimeout(2);
dialog.show();
```

## Beste praktijken {#best-practices}

1. **Duidelijke en Bondige Berichten**: Houd berichten kort en to the point en vermijd technische jargon; gebruik gebruiksvriendelijke taal.
2. **Geschikte Berichttypes**:
   - Gebruik `ERROR` voor kritieke problemen.
   - Gebruik `WARNING` voor waarschuwingsmeldingen.
   - Gebruik `INFO` voor algemene informatie.
3. **Consistente Thematisering**: Stem de thema's van de dialoog en knoppen af op het ontwerp van je apps.
4. **Zorgvuldig Gebruik van Time-out**: Stel time-outs in voor niet-kritische meldingen en zorg ervoor dat gebruikers voldoende tijd hebben om het bericht te lezen.
5. **Vermijd Overmatig Gebruik**: Gebruik dialoogvensters spaarzaam om frustratie bij de gebruiker te voorkomen en reserveer ze voor belangrijke berichten die actie of erkenning van de gebruiker vereisen.
