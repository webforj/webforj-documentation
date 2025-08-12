---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: 864d51bda31fc239bb58f5886ca7eeb4
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Ontwikkelaars willen vaak componenten creëren die uit samenstellende componenten bestaan voor gebruik op applicatieniveau. De `Composite` component geeft ontwikkelaars de tools die ze nodig hebben om hun eigen componenten te maken, terwijl ze controle behouden over wat ze aan gebruikers willen blootstellen.

Het stelt ontwikkelaars in staat om een specifiek type `Component` instantie te beheren, en biedt een manier om het gedrag ervan te encapsuleren. Het vereist van elke uitbreidende subclass dat deze het type `Component` specificeert dat hij van plan is te beheren, waardoor een subclass van `Composite` intrinsiek gekoppeld is aan zijn onderliggende `Component`.

:::tip
Het wordt ten zeerste aanbevolen om aangepaste componenten te maken door gebruik te maken van de `Composite` component, in plaats van de basis `Component` component uit te breiden.
:::

Om de `Composite` component te gebruiken, begin je met het maken van een nieuwe Java-klasse die de `Composite` component uitbreidt. Specificeer het type Component dat je wilt beheren als de generieke typeparameter.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementatie
}
```

## Componentbinding {#component-binding}

De `Composite` klasse vereist dat ontwikkelaars het type `Component` dat hij beheert, specificeert. Deze sterke associatie zorgt ervoor dat een `Composite` component intrinsiek is gekoppeld aan de onderliggende Component. Dit biedt ook voordelen ten opzichte van traditionele erfelijkheid, omdat het de ontwikkelaar in staat stelt om precies te beslissen welke functionaliteit aan de publieke API moet worden blootgesteld.

Standaard maakt de `Composite` component gebruik van de generieke typeparameter van zijn subclass om het gebonden componenttype te identificeren en te initialiseren. Dit is gebaseerd op de veronderstelling dat de componentklasse een parameterloze constructor heeft. Ontwikkelaars kunnen het componentinitialisatieproces aanpassen door de `initBoundComponent()` methode te overschrijven. Dit biedt meer flexibiliteit bij het maken en beheren van het gebonden component, inclusief het aanroepen van geïndividualiseerde constructors.

De volgende snippet overschrijft de initBoundComponent-methode om een geïndividualiseerde constructor voor de [FlexLayout](../components/flex-layout.md) klasse te gebruiken:

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Verzenden");
		return new FlexLayout(nameField, submit);
	}
}
```

## Levenscyclusbeheer {#lifecycle-management}

In tegenstelling tot bij de `Component` hoeven ontwikkelaars de `onCreate()` en `onDestroy()` methoden niet te implementeren wanneer ze met de `Composite` component werken. De `Composite` component zorgt voor deze aspecten voor jou.

Als je toegang moet krijgen tot de gebonden componenten in de verschillende fasen van zijn levenscyclus, stellen de `onDidCreate()` en `onDidDestroy()` hooks ontwikkelaars in staat om toegang te krijgen tot deze levenscyclusfasen om extra functionaliteit uit te voeren. Het gebruik van deze hooks is optioneel.

De `onDidCreate()` methode wordt onmiddellijk na het aanmaken van de gebonden component en het toevoegen aan een venster aangeroepen. Gebruik deze methode om je component in te stellen, eventuele benodigde configuraties aan te passen en kindercomponenten toe te voegen indien van toepassing. Terwijl de `onCreate()` methode van de `Component` klasse een [Window](#) instantie accepteert, accepteert de `onDidCreate()` methode in plaats daarvan de gebonden component, wat de noodzaak wegneemt om de `getBoundComponent()` methode direct aan te roepen. Bijvoorbeeld:

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Voeg kindercomponenten toe aan de container
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Deze logica kan ook in de constructor worden geïmplementeerd door `getBoundComponent()` aan te roepen.
:::

Evenzo wordt de `onDidDestroy()` methode geactiveerd zodra de gebonden component is vernietigd, en staat deze extra gedrag toe dat bij vernietiging moet worden geactiveerd, indien gewenst.

### Voorbeeld `Composite` component {#example-composite-component}

In het volgende voorbeeld is een eenvoudige ToDo-applicatie gemaakt, waarbij elk item dat aan de lijst is toegevoegd een `Composite` component is, bestaande uit een [`RadioButton`](../components/radio-button.md) gestyled als een schakelaar, en een [`Div`](#) met tekst.

De logica voor deze component is ingesteld in de constructor, die styling instelt en samenstellende componenten toevoegt aan de gebonden component met behulp van de `getBoundComponent` methode, en evenementlogica toevoegt.

:::tip
Dit kan ook worden geïmplementeerd in de `onDidCreate()` methode, waarmee directe toegang tot de gebonden [`FlexLayout`](../components/flex-layout.md) component wordt verkregen.
:::

Deze component wordt vervolgens geïnstalleerd en gebruikt in een Applicatie, en staat het gebruik ervan toe op verschillende locaties, waardoor het een krachtig hulpmiddel is bij het creëren van aangepaste componenten.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
