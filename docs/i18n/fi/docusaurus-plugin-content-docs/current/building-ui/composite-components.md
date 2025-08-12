---
sidebar_position: 2
title: Composite Components
draft: false
_i18n_hash: 864d51bda31fc239bb58f5886ca7eeb4
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/Composite" top='true'/>

Kehittäjät haluavat usein luoda komponentteja, jotka sisältävät osakomponentteja sovellustason käyttöä varten. `Composite`-komponentti tarjoaa kehittäjille työkalut omien komponenttien luomiseen pitäen samalla hallinnan siitä, mitä he valitsevat tarjota käyttäjille.

Se mahdollistaa kehittäjille tietyn tyyppisen `Component`-instanssin hallinnan, tarjoten tavan kapseloida sen käyttäytymistä. Se vaatii, että kaikki laajentavat alaluokat määrittävät sen `Component`-tyypin, jota ne aikovat hallita, varmistaen, että `Composite`-alaluokka on intrinsiikisti sidottu sen taustalla olevaan `Component`-komponenttiin.

:::tip
On erittäin suositeltavaa luoda mukautettuja komponentteja hyödyntämällä `Composite`-komponenttia sen sijaan, että laajennettaisiin perus `Component`-komponenttia.
:::

Käyttääksesi `Composite`-komponenttia, aloita luomalla uusi Java-luokka, joka laajentaa `Composite`-komponenttia. Määritä hallittavan komponentin tyyppi yleisen tyyppiparametrina.

```java
public class ApplicationComponent extends Composite<Div> {
	// Toteutus
}
```

## Komponentin sidonta {#component-binding}

`Composite`-luokka vaatii kehittäjiä määrittämään hallittavan `Component`-tyypin. Tämä vahva yhteys varmistaa, että `Composite`-komponentti on intrinsiikisesti sidottu sen taustalla olevaan komponenttiin. Tämä myös tarjoaa etuja perinteiseen perimiseen verrattuna, koska se antaa kehittäjän päättää tarkalleen, mitä toiminnallisuutta tarjotaan julkisessa API:ssa.

Oletuksena `Composite`-komponentti hyödyntää alaluokkansa yleistä tyyppiparametria tunnistaakseen ja instansioidakseen sidotun komponenttityypin. Tämä perustuu oletukseen, että komponenttijoukolla on parametria vaatimaton konstruktori. Kehittäjät voivat mukauttaa komponentin alustamisprosessia ylikirjoittamalla `initBoundComponent()`-metodin. Tämä mahdollistaa suuremman joustavuuden sidotun komponentin luomisessa ja hallinnassa, mukaan lukien parametrillisten konstruktorien kutsuminen.

Seuraava koodinpätkä ylikirjoittaa `initBoundComponent`-metodin käyttäen parametria vaativaa konstruktoria [`FlexLayout`](../components/flex-layout.md)-luokalle:

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

Toisin kuin `Component`-komponentin kanssa, kehittäjien ei tarvitse toteuttaa `onCreate()` ja `onDestroy()` menetelmiä työskennellessään `Composite`-komponentin kanssa. `Composite`-komponentti hoitaa nämä asiat puolestasi.

Jos sinun on tarpeen käyttää sidottuja komponentteja elinkaarensa eri vaiheissa, `onDidCreate()` ja `onDidDestroy()` koukut antavat kehittäjille pääsyn näihin elinkaarivaiheisiin lisätoiminnallisuuden suorittamiseksi. Näiden koukkujen käyttäminen on valinnaista.

`onDidCreate()`-metodia kutsutaan heti sen jälkeen, kun sidottu komponentti on luotu ja lisätty ikkunaan. Käytä tätä metodia komponenttisi asettamiseen, tarvittavien asetusten muokkaamiseen ja lapsikomponenttien lisäämiseen, jos mahdollista. Vaikka `Component`-luokan `onCreate()`-metodi ottaa vastaan [Window](#) instanssin, `onDidCreate()`-metodi sen sijaan ottaa vastaan sidotun komponentin, jolloin tarpeettomaksi jää suoraan kutsua `getBoundComponent()`-metodia. Esimerkiksi:

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
Tätä logiikkaa voidaan myös toteuttaa konstruktorissa kutsumalla `getBoundComponent()`.
:::

Samoin `onDidDestroy()`-metodi laukaisee kerran, kun sidottu komponentti on tuhoutunut, ja mahdollistaa lisäkäyttäytymisen laukeamisen tuhoutumisen yhteydessä, jos se on tarpeen.

### Esimerkki `Composite`-komponentista {#example-composite-component}

Seuraavassa esimerkissä on luotu yksinkertainen ToDo-sovellus, jossa jokainen luetteloon lisätty kohde on `Composite`-komponentti, joka koostuu [`RadioButton`](../components/radio-button.md) -komponentista, joka on tyylitelty kytkimeksi, ja [`Div`](#) elementistä, jossa on tekstiä.

Tämän komponentin logiikka on asetettu konstruktorissa, joka määrittää tyylit ja lisää osakomponentit sidottuun komponenttiin `getBoundComponent`-metodilla sekä lisää tapahtumalogiikan.

:::tip
Tämä voisi myös toteuttaa `onDidCreate()`-metodissa, mikä antaisi suoran pääsyn sidottuun [`FlexLayout`](../components/flex-layout.md) -komponenttiin.
:::

Tätä komponenttia käytetään sovelluksessa, ja se mahdollistaa sen käytön useissa paikoissa, mikä tekee siitä tehokkaan työkalun mukautettujen komponenttien luomisessa.

<ComponentDemo 
path='/webforj/composite?' 
cssURL='/css/composite.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/CompositeView.java'
height='550px'
/>
