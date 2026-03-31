---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 42e1e3270076a584d052295db1602298
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

De `ColorField` component laat gebruikers een kleur selecteren via de ingebouwde kleurkiezer van de browser. Omdat het afhankelijk is van de ingebouwde implementatie van de browser, varieert het uiterlijk tussen browsers en platforms. Het kan worden weergegeven als een eenvoudige tekstinvoerveld, een op het platform gestandaardiseerde kleurkiezer, of een aangepaste picker-interface. Deze variatie werkt in het voordeel van de gebruiker, omdat de besturingselementen overeenkomen met wat ze al kennen.

<!-- INTRO_END -->

## Gebruik van `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld laat de gebruiker een kleur kiezen en toont de tetradische complementen ervan.

<ComponentDemo 
path='/webforj/colorfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java'
cssURL='/css/fields/colorfield/colorFieldDemo.css'
height='300px'
/>

De `ColorField` is het beste te gebruiken in scenario's waar kleurselectie een cruciaal onderdeel van de gebruikersinterface of app-interface is. Hier zijn enkele scenario's waarin je een `ColorField` effectief kunt gebruiken:

1. **Grafisch Ontwerp en Afbeeldingsbewerkingshulpmiddelen**: Kleurenvelden zijn essentieel in apps die aanpassing via kleurselectie vereisen.

2. **Thema Personalizatie**: Als je app gebruikers toestaat om thema's aan te passen, stelt een kleurenveld hen in staat om kleuren voor verschillende UI-elementen te kiezen, zoals achtergronden, tekst, knoppen, enz.

3. **Gegevensvisualisatie**: Bied gebruikers een kleurenveld om kleuren te selecteren voor grafieken, diagrammen, warmtemappen en andere visuele representaties.

## Waarde {#value}

De `ColorField` gebruikt de [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) klasse om kleuren in te stellen en op te halen via de methoden `setValue()` en `getValue()`. Terwijl de client-side component exclusief volledig ondeugdelijke RGB-kleuren in hexadecimale notatie verwerkt, stroomlijnt webforJ het proces door `Color` waarden automatisch om te zetten in het juiste formaat.

:::tip Hexadecimale parsing
Bij gebruik van de `setText()` methode om een waarde toe te wijzen, zal de `ColorField` proberen de invoer te parseren als een hexadecimale kleur. Als de parsing mislukt, wordt er een `IllegalArgumentException` opgegooid.
:::

## Statische hulpprogramma's {#static-utilities}

De `ColorField` klasse biedt ook de volgende statische hulpprogramma's:

- `fromHex(String hex)`: Zet een kleurstring in hex-formaat om naar een `Color` object dat vervolgens kan worden gebruikt met deze klasse, of elders.

- `toHex(Color color)`: Zet de opgegeven waarde om naar de bijbehorende hex-representatie.

- `isValidHexColor(String hex)`: Controleer of de opgegeven waarde een geldige 7-cijferige hex-kleur is.

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `ColorField` component, overweeg dan de volgende beste praktijken:

- **Contexte hulp**: Bied contextuele hulp, zoals tooltips of een label, om te verduidelijken dat gebruikers een kleur kunnen selecteren en het doel ervan te begrijpen.

- **Bied een Standaardkleur**: Heb een standaardkleur die logisch is in de context van je app.

- **Bied Vooraf Gedefinieerde Kleuren Aan**: Inclusief een palet van vaak gebruikte of merkgerichte kleuren naast het kleurenveld voor snelle selectie.
