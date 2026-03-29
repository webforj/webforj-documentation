---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 7612411ef90d5344a2bab79b7e221141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

De `ProgressBar` component vertegenwoordigt visueel de voltooiingsstatus van een operatie. Terwijl het werk vordert, vult een rechthoek geleidelijk om het huidige percentage weer te geven. De balk kan ook een tekstuele weergave van zijn waarde tonen en ondersteunt zowel bepaalde als onbepaalde toestanden voor taken met bekende of onbekende duur.

<!-- INTRO_END -->

## Usages {#usages}

De `ProgressBar` component is nuttig voor het visualiseren van de voltooiingsstatus van taken. Het ondersteunt:

- Configureerbare minimum- en maximumwaarden.
- Onbepaalde modus voor doorlopende taken zonder definitief einde.
- Opties voor tekst zichtbaarheid, animatie en gestreepte ontwerpen voor betere visuele feedback.

Het volgende voorbeeld toont een gestreepte, geanimeerde voortgangsbalk met start-, pauze- en resetbesturingselementen:

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Instellen van waarden {#setting-values}

De ProgressBar component staat het instellen en krijgen van de huidige waarde, minimum en maximum limieten toe.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Oriëntatie {#orientation}

De `ProgressBar` kan horizontaal of verticaal worden georiënteerd.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Onbepaalde staat {#indeterminate-state}

De `ProgressBar` ondersteunt een onbepaalde staat voor taken met onbekende voltooiingstijd.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Tekst en tekstzichtbaarheid {#text-and-text-visibility}

Standaard toont de voortgangsbalk bij creatie het percentage voltooid in het formaat `XX%`. Met de `setText()`-methode kunt u de placeholder `{{x}}` gebruiken om de huidige waarde als percentage te krijgen. Daarnaast kunt u de placeholder 
`{{value}}` gebruiken om de ruwe huidige waarde te verkrijgen.

```java
ProgressBar bar = new ProgressBar(15, "Downloading: {{x}}%");
```

## Styling {#styling}

### Thema's {#themes}

De `ProgressBar` component wordt geleverd met <JavadocLink type="foundation" location="com/webforj/component/Theme"> thema's </JavadocLink> die zijn ingebouwd voor snelle styling zonder gebruik van CSS. Deze thema's zijn vooraf gedefinieerde stijlen die kunnen worden toegepast op knoppen om hun uiterlijk en visuele presentatie te veranderen. 
Ze bieden een snelle en consistente manier om de uitstraling van ProgressBars in een app aan te passen. 

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Beste praktijken {#best-practices}

- **Gebruik Geschikte Minimum- en Maximumwaarden**: Stel de minimum- en maximumwaarden in om het bereik van de taak nauwkeurig weer te geven.
- **Werk Voortgang Regelmatig Bij**: Werk continu de voortgangswaarde bij om realtime feedback aan gebruikers te bieden.
- **Gebruik Onbepaalde Staat voor Onbekende Duur**: Gebruik de onbepaalde staat voor taken met onvoorspelbare duur om voortgaande progressie aan te geven.
- **Toon Tekst voor Betere Gebruikersfeedback**: Toon tekst op de voortgangsbalk om aanvullende context over de voortgang van de taak te bieden.
