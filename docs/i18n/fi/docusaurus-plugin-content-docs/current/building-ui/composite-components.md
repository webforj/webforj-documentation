---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: c133bfa392bbd2705acdc71cea3fdd68
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Kehittäjät haluavat usein luoda komponentteja, jotka sisältävät koostuvia komponentteja sovellusnäkymän käyttöä varten. `Composite`-komponentti antaa kehittäjille tarvittavat työkalut omien komponenttien luomiseen samalla, kun he hallitsevat mitä he päättävät paljastaa käyttäjille. 

Se mahdollistaa kehittäjille tietyn tyyppisen `Component`-instanssin hallinnan, tarjoten tavan kapseloida sen käyttäytyminen. Se vaatii jokaiselta laajentavalta aliluokalta määrittelemään sen hallitseman `Component`-tyypin, varmistaen, että `Composite`-aliluokka on intrinsiikisesti sidoksissa sen taustalla olevaan `Component`:iin.

:::tip
On erittäin suositeltavaa luoda mukautettuja komponentteja hyödyntämällä `Composite`-komponenttia sen sijaan, että laajennettaisiin perus `Component`-komponenttia.
:::

Voit käyttää `Composite`-komponenttia aloittamalla uuden Java-luokan luomisen, joka laajentaa `Composite`-komponenttia. Määritä hallittavan komponentin tyyppi geneerisen tyyppiparametrina.

```java
public class ApplicationComponent extends Composite<Div> {
	//Implementaatio
}
```

## Komponentin sidonta {#component-binding}

`Composite`-luokka vaatii kehittäjiä määrittelemään sen hallitseman `Component`-tyypin. Tämä vahva yhteys varmistaa, että `Composite`-komponentti on intrinsiikisesti sidoksissa sen taustalla olevaan komponenttiin. Tämä tarjoaa myös etuja perinteiseen perimiseen verrattuna, sillä se antaa kehittäjälle mahdollisuuden päättää tarkalleen, mitä toiminnallisuutta julkaistaan julkisessa API:ssa. 

Oletusarvoisesti `Composite`-komponentti käyttää aliluokkansa geneeristä tyyppiparametria tunnistaakseen ja instansioidakseen sidotun komponenttityypin. Tämä perustuu oletukseen, että komponenttiluokalla on parametrivapaa konstruktorin. Kehittäjät voivat mukauttaa komponentin alustamisprosessia ylittämällä `initBoundComponent()`-menetelmän. Tämä mahdollistaa suuremman joustavuuden sidotun komponentin luomisessa ja hallinnassa, mukaan lukien parametrisoitujen konstruktorien kutsumisen.

Seuraava koodi ylittää `initBoundComponent`-menetelmän käyttääkseen parametrisoitua konstruktorista [FlexLayout](../components/flex-layout.md) -luokassa:

```java
public class OverrideComposite extends Composite<FlexLayout> {
	
	TextField nameField;
	Button submit;

	@Override
	protected FlexLayout initBoundComponent() {
		nameField = new TextField();
		submit = new Button("Lähetä");
		return new FlexLayout(nameField, submit);
	}
}
```

## Elinkaaren hallinta {#lifecycle-management}

Toisin kuin `Component`-luokan kanssa, kehittäjien ei tarvitse toteuttaa `onCreate()` ja `onDestroy()` -menetelmiä työskennellessään `Composite`-komponentin kanssa. `Composite`-komponentti huolehtii näistä asioista puolestasi.

Jos sinun tarvitsee käyttää sidottuja komponentteja elinkaaren eri vaiheissa, `onDidCreate()` ja `onDidDestroy()` -koukut antavat kehittäjille pääsyn näihin elinkaaren vaiheisiin lisätoimintojen suorittamista varten. Näiden koukkujen käyttö on valinnaista.

`onDidCreate()`-menetelmää kutsutaan heti, kun sidottu komponentti on luotu ja lisätty ikkunaan. Käytä tätä menetelmää komponenttisi alustamiseen, kaiken tarvittavan konfiguroinnin muokkaamiseen ja lapsikomponenttien lisäämiseen, jos tarpeen. Vaikka `Component`-luokan `onCreate()`-menetelmä ottaa [Window](#) -instanssin, `onDidCreate()`-menetelmä ottaa sen sijaan sidotun komponentin, jolloin `getBoundComponent()`-menetelmän kutsumista ei tarvita suoraan. Esimerkiksi:

```java
public class ApplicationComponent extends Composite<Div> {
	@Override
	protected void onDidCreate(Div container) {
		// Lisää lapsikomponentit säiliöön
		container.add(new CheckBox());
		container.add(new Paragraph());
		// ...
	}
}
```

:::tip
Tämä logiikka voidaan myös toteuttaa konstruktorissa kutsumalla `getBoundComponent()`.
:::

Samoin `onDidDestroy()`-menetelmä aktivoituu, kun sidottu komponentti on tuhottu, ja mahdollistaa lisäkäyttäytymisen, jos niin halutaan.

### Esimerkkikomponentti `Composite` {#example-composite-component}

Seuraavassa esimerkissä on luotu yksinkertainen ToDo-sovellus, jossa jokainen listalle lisätty kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radio-button.md) -komponentista, joka on tyylitelty kytkimeksi, ja [`Div`](#) -komponentista, jossa on tekstiä.

Tämän komponentin logiikka on määritelty konstruktorissa, joka asettaa tyylit ja lisää koostuvia komponentteja sidottuun komponenttiin käyttäen `getBoundComponent` -menetelmää, ja lisää tapahtumalogikasta.

:::tip
Tämä voitaisiin myös toteuttaa `onDidCreate()`-menetelmässä, mikä antaisi suoran pääsyn sidottuun [`FlexLayout`](../components/flex-layout.md) -komponenttiin.
:::

Tätä komponenttia käytetään sitten instansioimalla ja hyödyntämällä sovelluksessa, ja se mahdollistaa sen käytön eri paikoissa, mikä tekee siitä tehokkaan työkalun mukautettujen komponenttien luomisessa.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
