---
title: Swing-uiterlijk en -gevoel
sidebar_position: 5
description: >-
  Geef een ingebedde Swing-app de kleuren, lettertypen, knoppen en focusranden
  van webforJ met DWC Look and Feel for Swing, in licht of donker.
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
Wanneer `WebswingConnector` een Swing-app insluit in een webforJ-app, zien gebruikers Swing-schermen naast webforJ-schermen. Met de standaardlook-and-feel van Swing lijken de twee onderdelen op verschillende apps. De DWC Look and Feel voor Swing geeft de Swing-app de lettertypen, kleuren, knoppen en focusranden van de DWC-componenten die webforJ gebruikt. De look-and-feel wordt geleverd met een licht en een donker palet.

Het is een standaardlook-and-feel voor Swing in één JAR. Voeg deze toe aan het classpath van de Swing-app en selecteer de look-and-feel bij het opstarten. De rest van de Swing-code hoeft niet te worden aangepast.

:::info Beste benadering
Swing en DWC tekenen hun bedieningselementen op verschillende manieren. Beide kunnen hetzelfde lichte of donkere palet gebruiken, maar bij het wisselen tussen een Swing-scherm en een webforJ-scherm kunnen nog kleine verschillen zichtbaar zijn.
:::

## Probeer de demo {#try-the-demo}

Order Desk is een Swing-app waarin het thema is geïnstalleerd. [Download de Order Desk-demo](https://dwc.style/docs/#/swing/downloads) en voer deze uit met Java 21 of hoger:

```bash
java -jar dwc-swing-laf-demo.jar
```

Kies **View → DWC light** of **View → DWC dark** en vervolgens **View → Primary color**. Vergelijk daarna de geselecteerde order, het actieve tabblad en de knop Save order.

Elk bedieningselement in Order Desk is een standaard Swing-component. Wissel het palet en de primaire kleur onder de schermafbeelding en selecteer de afbeelding om deze te vergroten.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## Het thema installeren {#install-the-theme}

[Download de thema-JAR](https://dwc.style/docs/#/swing/downloads) en voeg deze toe aan het classpath van de Swing-app. Java 21 of hoger is vereist. De JAR bevat beide paletten en het lettertype Inter.

Selecteer de look-and-feel op de Swing-eventdispatchthread, voordat de app het eerste venster maakt:

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

Gebruik voor het donkere palet in plaats daarvan `DwcDarkLookAndFeel`. Beide klassen staan in het pakket `com.basis.dwc.swing.laf`. Als de app de look-and-feel selecteert op basis van de klassenaam, gebruik dan `com.basis.dwc.swing.laf.DwcLookAndFeel` of `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`.

Als de app Java-modules gebruikt, plaats de JAR dan op het modulepad en vereis `com.basis.dwc.swing.laf`.

## Je webforJ-thema afstemmen {#match-your-webforj-theme}

Het thema leest dezelfde tok namen als [webforJ-thema's](/docs/styling/themes). Stel in de Swing-app een token in op de waarde die je webforJ-app gebruikt.

Als je de primaire kleur van je webforJ-app wilt gebruiken, stel je de primaire seed in nadat je de look-and-feel hebt geïnstalleerd:

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Primaire knoppen, selecties en focusranden krijgen die kleur. Andere semantische kleuren, zoals de kleur voor waarschuwingen, behouden hun eigen palet. Geef `null` als waarde door om terug te keren naar de primaire kleur van het palet.

Het thema gebruikt standaard Inter. webforJ gebruikt het platformlettertype via [`--dwc-font-family`](/docs/styling/typography). Stel de token in op `system-ui` om hetzelfde lettertype in de Swing-app te gebruiken:

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

Een ander lettertype kan de breedte van labels en de hoogte van velden veranderen. Pas daarom de vensters aan nadat je de inhoud hebt opgebouwd.

## Eén bedieningselement stylen {#style-one-control}

Met een client property wijzig je één bedieningselement. In dit formulier krijgt een veld een label en wordt het als verplicht gemarkeerd; de knop Save wordt ingesteld als primaire knop:

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

Stel de ongeldige status en de hulptekst in om een validatiemelding onder een veld weer te geven:

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

De [referentie voor client properties](https://dwc.style/docs/#/swing/client-properties) bevat de sleutels voor alle bedieningselementen, waaronder tabellen, bomen, tabbladen, schuifregelaars en dialoogvensters.

De [volledige themagids](https://dwc.style/docs/#/swing/) behandelt dialoogvensters, toetsenbordfocus en beweging.
