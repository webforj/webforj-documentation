---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Ontwikkelaars willen vaak componenten maken die bestaan uit samenstellende componenten voor gebruik op applicatieniveau. De `Composite`-component biedt ontwikkelaars de tools die ze nodig hebben om hun eigen componenten te maken, terwijl ze controle behouden over wat ze aan gebruikers blootstellen.

Het stelt ontwikkelaars in staat om een specifiek type `Component`-instantie te beheren, wat een manier biedt om het gedrag ervan te encapsuleren. Het vereist dat elke uitbreidende subclass het type `Component` opgeeft dat het van plan is te beheren, waardoor een subclass van `Composite` intrinsiek verbonden is met zijn onderliggende `Component`.

:::tip
Het wordt sterk aanbevolen om aangepaste componenten te maken door gebruik te maken van de `Composite`-component, in plaats van de basis `Component`-component uit te breiden.
:::

Om de `Composite`-component te gebruiken, begin je met het maken van een nieuwe Java-klasse die de `Composite`-component uitbreidt. Geef het type Component op dat je wilt beheren als de generieke typeparameter.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementatie
}
```

## Componentbinding {#component-binding}

De `Composite`-klasse vereist dat ontwikkelaars het type `Component` opgeven dat het beheert. Deze sterke associatie zorgt ervoor dat een `Composite`-component intrinsiek verbonden is met zijn onderliggende Component. Dit biedt ook voordelen ten opzichte van traditionele overerving, omdat het de ontwikkelaar in staat stelt om precies te beslissen welke functionaliteit openbaar moet worden gemaakt via de API.

Standaard maakt de `Composite`-component gebruik van de generieke typeparameter van zijn subclass om het gekoppelde componenttype te identificeren en te instantieren. Dit is gebaseerd op de veronderstelling dat de componentklasse een parameterloze constructor heeft. Ontwikkelaars kunnen het initialisatieproces van de component aanpassen door de `initBoundComponent()`-methode te overschrijven. Dit biedt meer flexibiliteit bij het maken en beheren van de gekoppelde component, inclusief het aanroepen van parameterized constructors.

De volgende snippet overschrijft de initBoundComponent-methode om een parameterized constructor voor de [FlexLayout](../components/flex-layout.md)-klasse te gebruiken:

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Indienen");
		return new FlexLayout(nameField, submit);
	}
}
```

## Levenscyclusbeheer {#lifecycle-management}

In tegenstelling tot de `Component` hoeven ontwikkelaars de `onCreate()`- en `onDestroy()`-methoden niet te implementeren wanneer ze met de `Composite`-component werken. De `Composite`-component zorgt voor deze aspecten voor jou.

Als je toegang nodig hebt tot de gekoppelde componenten in de verschillende fasen van de levenscyclus, bieden de `onDidCreate()`- en `onDidDestroy()`-hooks ontwikkelaars toegang tot deze levenscyclusfasen om aanvullende functionaliteit uit te voeren. Het gebruik van deze hooks is optioneel.

De `onDidCreate()`-methode wordt onmiddellijk aangeroepen nadat de gekoppelde component is gemaakt en aan een venster is toegevoegd. Gebruik deze methode om je component in te stellen, eventuele benodigde configuraties aan te passen en, indien van toepassing, subcomponenten toe te voegen. Terwijl de `onCreate()`-methode van de `Component`-klasse een [Window](#) instantie vereist, neemt de `onDidCreate()`-methode in plaats daarvan de gekoppelde component, waardoor de noodzaak om de `getBoundComponent()`-methode rechtstreeks aan te roepen, wordt verwijderd. Bijvoorbeeld:

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Voeg subcomponenten toe aan de container
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Deze logica kan ook in de constructor worden geïmplementeerd, door `getBoundComponent()` aan te roepen.
:::

Evenzo wordt de `onDidDestroy()`-methode geactiveerd zodra de gekoppelde component is vernietigd, en maakt het mogelijk om extra gedrag uit te voeren bij vernietiging, mocht dat gewenst zijn.

### Voorbeeld `Composite`-component {#example-composite-component}

In het volgende voorbeeld is een eenvoudige ToDo-applicatie gemaakt, waarbij elk item dat aan de lijst wordt toegevoegd een `Composite`-component is, bestaande uit een [`RadioButton`](../components/radio-button.md) die is gestyled als een switch, en een [`Div`](#) met tekst.

De logica voor deze component is in de constructor opgezet, die styling instelt en constituent componenten toevoegt aan de gekoppelde component met behulp van de `getBoundComponent`-methode, en eventlogica toevoegt.

:::tip
Dit kan ook worden geïmplementeerd in de `onDidCreate()`-methode, wat directe toegang tot de gekoppelde [`FlexLayout`](../components/flex-layout.md)-component zou geven.
:::

Deze component wordt vervolgens geïnstantieerd en gebruikt in een Applicatie, en maakt het mogelijk om overal in verschillende locaties te worden gebruikt, waardoor het een krachtig hulpmiddel is bij het creëren van aangepaste componenten.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
