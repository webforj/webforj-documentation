---
sidebar_position: 30
title: Message
_i18n_hash: 633e8c1297144da8b39cfd7ca2e77e5c
---
# Berichtdialoog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

Een `MessageDialog` is een modale dialoog ontworpen om een bericht aan de gebruiker weer te geven met een `OK`-knop om de dialoog te sluiten. Het blokkeert de app-executie totdat de gebruiker ermee interacteert of het automatisch sluit vanwege een timeout.

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

1. `PLAIN`: Toont het bericht zonder pictogram, met het standaardthema.
2. `ERROR`: Toont een foutpictogram naast het bericht met het foutthema toegepast.
3. `QUESTION`: Toont een vraagtekenpictogram naast het bericht, met het primaire thema.
4. `WARNING`: Toont een waarschuwingspictogram naast het bericht met het waarschuwings-thema toegepast.
5. `INFO`: Toont een infopictogram naast het bericht, met het infotema.

In het volgende voorbeeld configureert de code een berichtdialoog van type `WARNING` met een aangepaste titel en bericht.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialoog & Knopthema
Standaard bepaalt de dialoog het thema op basis van het berichttype. Je kunt het thema van de dialoog aanpassen met de methode `setTheme(Theme theme)` en het knopfictogram onafhankelijk aanpassen met de methode `setButtonTheme(ButtonTheme theme)` om verschillende variaties te creëren.
:::

## Knoptekst {#button-text}

Je kunt de tekst van de dialoogknop configureren met behulp van `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hallo Wereld!", "Titel", "Begrepen");
```

## HTML-verwerking {#html-processing}

Standaard verwerkt en rendert de berichtdialoog HTML-inhoud. Je kunt deze functie uitschakelen door het zo in te stellen dat het platte tekst weergeeft in plaats van HTML.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hallo Wereld</b>", "Hallo Wereld", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout {#timeout}

De `MessageDialog` stelt je in staat om een timeoutduur in te stellen waarna de dialoog automatisch sluit. Deze functie is nuttig voor niet-kritieke meldingen of informatie die geen onmiddellijke interactie van de gebruiker vereist.

Je kunt de timeout voor de dialoog configureren met de methode `setTimeout(int timeout)`. De timeoutduur is in seconden. Als de opgegeven tijd verstrijkt zonder enige gebruikersinteractie, sluit de dialoog automatisch.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Deze dialoog zal binnenkort time-out zijn", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## Beste praktijken {#best-practices}

1. **Heldere en Beknopte Berichten**: Houd berichten kort en to the point en vermijd technische jargon; gebruik gebruiksvriendelijke taal.
2. **Geschikte Berichttypes**:
   - Gebruik `ERROR` voor kritieke problemen.
   - Gebruik `WARNING` voor waarschuwende melding.
   - Gebruik `INFO` voor algemene informatie.
3. **Consistent Thema**: Stem de dialoog- en knopthema's af op het ontwerp van jouw apps.
4. **Verstandig Gebruik van Timeout**: Stel time-outs in voor niet-kritieke meldingen en zorg ervoor dat gebruikers voldoende tijd hebben om het bericht te lezen.
5. **Vermijd Overmatig Gebruik**: Gebruik dialogen spaarzaam om frustratie van de gebruiker te voorkomen en reserveer ze voor belangrijke berichten die gebruikersacties of erkenning vereisen.
