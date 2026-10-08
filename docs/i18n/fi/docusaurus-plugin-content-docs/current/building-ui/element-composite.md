---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2a742b2589b096aff73a1fcb67e041c1
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka käärii mukautetun HTML-elementin tai [web-komponentin](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Se sitoo Java-luokkasi taustalla olevaan `Element`-objektiin ja antaa sinun työskennellä kyseisen elementin ominaisuuksien, attribuuttien ja tapahtumien kanssa Java-koodissa. Käytä sitä, kun integroi web-komponentteja webforJ-sovellukseen.

:::tip Milloin käyttää `ElementComposite`
Käytä `ElementComposite`-luokkaa, kun käännät kolmannen osapuolen web-komponenttia, jota webforJ ei jo tarjoa. Jos sisäänrakennettu webforJ-komponentti kattaa käyttötapauksen (`TextField`, `ColorField`, `Button`, jne.), käytä sitä sen sijaan. Yksittäisessä DOM-työssä, jota ei tarvitse käyttää uudelleen, `Element`-luokkaa voidaan käyttää suoraan ilman käärettä.
:::

Tässä oppaassa näytetään, kuinka toteuttaa [Web Awesome suhteellinen-aika web-komponentti](https://webawesome.com/docs/components/relative-time/) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Luokkaannotaatiot {#class-annotations}

Kolme annotaatiota esiintyy yleisesti `ElementComposite`-aliluokan yläosassa: `@NodeName` määrittelee HTML-tagin, jota komponentti käärii, ja `@JavaScript` sekä `@StyleSheet` lataa kaikki asiakaspuolen resurssit, joita taustalla oleva web-komponentti tarvitsee. `@NodeName` on pakollinen ja spesifinen `ElementComposite`-luokalle. `@JavaScript` ja `@StyleSheet` ovat yleisiä webforJ-resurssiantibioita ja toimivat kaikissa luokissa, mukaan lukien näkymät, komponentit tai `App`-luokka.

### `@NodeName` {#nodename}

`@NodeName`-annotaatio määrittelee HTML-tagin, jota komponentti käärii. webforJ käyttää tätä nimeä luodessaan taustalla olevan elementin DOM:ssa.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Tagnimen on vastattava asiakkaalla rekisteröityä mukautettua elementtiä. Ilman tätä annotaatiota kehys ei voi määrittää, mikä elementti luodaan.

Aliluokassa `getNodeName()` lukee ilmoitetun tagin, ja `getElement()` palauttaa taustalla olevan `Element`-objektin, jotta voit suoraan kutsua DOM-tason metodeja sen päällä.

### `@JavaScript` {#javascript}

`@JavaScript`-annotaatio lataa skriptin, joka määrittelee tai rekisteröi taustalla olevan web-komponentin. Aseta se luokkaan, jotta skripti latautuu vain, kun komponenttia käytetään.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Useita `@JavaScript`-annotaatioita on sallittu, ja webforJ poistaa automaattisesti päällekkäiset lataukset. Sama skripti ei lataudu kahdesti, jos useat komponentit riippuvat siitä.

Katso [JavaScript-tiedostojen tuominen](../managing-resources/importing-assets#importing-javascript-files) täydellinen vaihtoehtoja valikoima, mukaan lukien `top`, `attributes` ja latausaika.

### `@StyleSheet` {#stylesheet}

`@StyleSheet`-annotaatio lataa CSS-tiedoston, jota komponentti tarvitsee. Se on hyödyllinen kolmansien osapuolten komponenttien kohdalla, jotka toimittavat erillisen tyylitiedoston, tai komponentti-spesifisen tyylin paketoimiseen kääreen ohessa.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Paikallisesti paketoiduissa resursseissa käytä `ws://` -etuliitettä viitataksesi tiedostoihin `resources/static`-hakemistossa:

```java
@StyleSheet("ws://components/relative-time.css")
```

Katso [CSS-tiedostojen tuominen](../managing-resources/importing-assets#importing-css-files) täydellinen vaihtoehtoja valikoima.

## Ominaisuus- ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit kuvaavat web-komponentin tilaa, joka yleensä pitää sisällään tietoa tai konfiguraatiota. `ElementComposite` altistaa molemmat `PropertyDescriptor` -luokan kautta.

Kaksi tehdastekijää `PropertyDescriptor`-luokassa tuottavat kuvaston itsessään, yksi jokaiselle sitoutumistavoitteelle:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` sitoo JavaScript-ominaisuuden DOM-solmulle. `PropertyDescriptor.attribute()` sitoo HTML-attribuutin. Ensimmäinen argumentti on nimi, jota web-komponentti odottaa. Toinen on oletusarvo, joka myös määrittää kuvaston Java-tyypin.

Määritä kuvasto komponentin yksityiseksi kentäksi ja lue sekä kirjoita sitä `set(PropertyDescriptor<V> property, V value)` ja `get(PropertyDescriptor<V> property)` avulla.

:::info
Ominaisuudet ovat DOM-solmun sisäinen tila eivätkä näy merkinnässä. Attribuutit ovat HTML-merkintää, joka näkyy ulkoisille skripteille ja CSS:lle.
:::

```java
// Esimerkki ominaisuudesta nimeltä "title" ElementComposite-luokassa
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Esimerkki attribuutista nimeltä "value" ElementComposite-luokassa
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Otsikkoni");
set(value, "Arvoni");
```

Edellä olevat kutsut käyttävät suoraan `set()` näytön yksinkertaista muotoa. Käytännössä `set()` ja `get()` ovat `protected`-menetelmiä `ElementComposite`-luokassa. Ne ovat primitiivinen kerros, joka synkronoi Java-arvot taustalla olevan elementin kanssa, eivät julkisen API:n kuluttajia varten. Tarkoituksena on pitää `PropertyDescriptor` yksityisenä ja kirjoittaa julkisia `setX()` ja `getX()`-menetelmiä, jotka delegoivat primitiiveille.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // suojattu primitiivi
    return this;
  }

  public String getHeading() {
    return get(heading);     // suojattu primitiivi
  }
}
```

Yksi kutsu `set(descriptor, value)` tekee kolme asiaa kerralla. Se työntää arvon asiakkaalle `setProperty()`-menettelyä varten ominaisuuksille tai `setAttribute()`-menettelyä varten attribuuteille. Se tallentaa arvon paikalliseen palvelinpuolen välimuistiin, yksi kartta per komponentti-instanssi. Ja se tallentaa ajonaikaisen tyypin arvon ohelle, jotta myöhemmät `get()`-kutsut tietävät, kuinka deserialisoida.

Tuo paikallinen välimuisti on syy siihen, että `get()` voi olla halvempaa oletuksena. `get(descriptor)` palauttaa välimuistissa olevan arvon palvelinpuolen varastosta ilman verkkokutsua, koska jokainen `set()` pitää välimuistin synkronoituna asiakkaan kanssa. Valinnainen `boolean`-toinen argumentti ohjaa, ohitetaanko välimuisti ja luetaanko selain suoraan.

```java
String cached = get(heading);            // luetaan palvelinpuolen välimuistista
String live = get(heading, true);        // pakottaa lukemaan selaimesta
```

Aseta `fromClient` todeksi, kun arvo voi muuttua asiakkaassa ilman palvelimen tietoa, kuten kirjoitettu `<input>`-arvo. Palvelimen ohjaamille ominaisuuksille oletus välttää matkustamisen.

Valinnainen kolmas argumentti on `java.lang.reflect.Type` ja ohjaa, kuinka tulos deserialisoidaan. webforJ selvittää tyypin tässä järjestyksessä: erikseen annettu `Type`-argumentti, jos se on annettu, sitten aikarajattava tyyppi, joka on tallennettu aiemman `set()`-kutsun avulla samalle kuvastolle, sitten `Object.class`. Käytännössä aiemman `set()`-tallennettu tyyppi riittää, joten kolmas argumentti voidaan usein jättää pois. Se on tarpeen, kun tallennettu luokka menettää tietoa, jota deserialisoija tarvitsee, kuten parametrisoitu tyyppi kuten `List<String>`, jonka ajonaikainen luokka on vain `ArrayList`.

Alla oleva demo lisää suhteellisen ajan ominaisuuksia verkkokomponentin asiakirjojen mukaan ja altistaa ne getterien ja setterien kautta. Jokainen rivi aktiviteettisyötteessä käyttää erilaisia `format` ja `numeric` arvoja näyttääkseen, kuinka sama komponentti renderöi eri konfiguraatioiden alla.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Ominaisuudet verrattuna attribuutteihin {#properties-versus-attributes}

Vaikka `PropertyDescriptor.property()` ja `PropertyDescriptor.attribute()` näyttävät olevan vaihdettavissa, ne tähtäävät eri osiin taustalla olevaa elementtiä. Väärän valinta aiheuttaa, että arvot eivät sovellu hiljaa.

Ominaisuudet ovat JavaScript-objektin ominaisuuksia DOM-solmulle. Ne voivat sisältää minkä tahansa tyypin, mukaan lukien merkkijonot, booleanit, numerot, objektit ja taulukot, ja ne edustavat elementin nykyistä ajonaikaista tilaa. Ominaisuuden asettaminen tarkoittaa suoraa JavaScript-assignaatiota.

Attribuutit ovat HTML-merkintää. Ne sijaitsevat elementin avaustagissa, ovat aina merkkijonoja ja edustavat elementin alkuperäistä konfiguraatiota. Attribuutin asettaminen aiheuttaa DOM-muutoksen ja merkkijonon muunnoksen.

Joissakin tapauksissa kaksi pysyy synkronoituina. Toisissa ne poikkeavat. `<input>`-esimerkki on klassinen: `value`-attribuutti on alkuperäinen arvo, kun taas `value`-ominaisuus on nykyinen arvo, jonka käyttäjä on kirjoittanut. Attribuutin lukeminen sen jälkeen, kun käyttäjä on kirjoittanut, palauttaa alkuperäisen merkinnän, mutta ominaisuuden lukeminen palauttaa kentän nykyiset sisällöt.

Käytä **ominaisuuksia** seuraavissa tapauksissa:

- **Usein muuttuva ajonaikainen tila**: laskurit, nykyiset valinnat, kirjoitetut arvot
- **Ei-merkkijonotyypit**: booleanit, numerot, objektit, taulukot
- **Suorituskykyherkät päivitykset**: ominaisuudet ohittavat merkkijonon muunnoksen, joka vaaditaan attribuuteille

Käytä **attribuutteja** seuraavissa tapauksissa:

- **Alkuperäinen konfiguraatio**: asetukset, jotka komponentti lukee kerran yhdistäessä
- **CSS-valitsijat**: arvot, jotka haluat kohdistaa valitsijalla, kuten `[disabled]` tai `[variant="danger"]`
- **Saatavuusviittaukset**: `aria-label`, `role` ja muut ARIA-attribuutit
- **Merkkijono-tyyppiset asetukset, jotka harvoin muuttuvat**

Kun käännät kolmannen osapuolen web-komponenttia, tarkista komponentin asiakirjat varmistaaksesi, mikä nimi vastaa ominaisuutta ja mikä attribuuttia. Käyttämällä `PropertyDescriptor.attribute()`-menetelmää, erityisesti jos komponentti altistaa vain ominaisuuden, ei toimi, ja sama pätee toisin päin. Komponentti sivuuttaa arvon hiljaa.

### Ominaisuuksien tyypitys {#typing-properties}

Kuvasto on parametrisoitu sen arvon Java-tyypillä. Täydellinen ilmoitussyntaksi on:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>` geneerinen parametri ilmoittaa arvon tyypin. Oletusarvon ajonaikainen tyyppi määrittää myös `T`:n, joten geneerinen argumentti harvoin tarvitsee määrittää nimenomaisesti. webforJ käyttää `T`:tä arvojen sarjoittamiseen ja desarjoittamiseen, kun kommunikoidaan asiakkaan kanssa.

```java
private final PropertyDescriptor<String> label =
    PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
    PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
    PropertyDescriptor.property("step", 1.0);
```

Sarjoitus on automaattista primitivi-arvoille, niiden pakattuille vastineille ja `String`-tyypille. Monimutkaisille tyypeille arvo sarjoitetaan JSON-muodossa ennen kuin se määritetään asiakkaan ominaisuuteen.

### Arvojen validointi {#validating-values}

Varmista arvot setterissä ennen `set()`-kutsua. Setter on luonnollinen valvontapiste, koska jokainen muutos kulkee sen läpi.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max must be non-negative");
  }
  set(max, value);
  return this;
}
```

Nullaarvoisten viitteiden osalta käytä `Objects.requireNonNull()`, jotta virhe tulee esille rajalla eikä myöhemmin renderöintiputkessa.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Vältä validointia `get()`-menetelmässä. Lukujen tulisi pysyä halvempina ja johdonmukaisina.

### Enum-tyyliset ominaisuudet {#enum-style-properties}

Useimmat web-komponentit odottavat pienikirjaimisia tai kebab-tapaisia merkkijonoarvoja enum-tyylisille ominaisuuksille (`theme="primary"`, `expanse="xs"`). webforJ käyttää Gsonia sarjoittamaan enum-arvoja, mutta Gsonin oletusesitys on vakion nimi suurilla kirjaimilla. Merkitse jokainen vakio `@SerializedName`-annotaatiolla, jotta sarjoitettu arvo vastaa sitä, mitä web-komponentti odottaa.

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

Ilmoita kuvasto enum-tyypillä ja käytä enumia suoraan setterissä ja getterissä.

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

Tämä on sama malli, jota webforJ:n sisäänrakennetut komponentit käyttävät `Theme`, `Expanse` ja samankaltaisten enumien kohdalla. Julkinen Java-API säilyy tyyppiturvallisena, ja web-komponentti vastaanottaa arvon merkkijonona `@SerializedName`-annotaatiosta.

### Ominaisuuksien testaaminen {#testing-properties}

`PropertyDescriptorTester` varmistaa, että jokainen `PropertyDescriptor` komponentissa on kytketty oikein. Se skannaa luokan kuvauskenttiä, kutsuu kutakin setter-metodia oletusarvoisella arvolla ja vertaa tulosta getterin palauttamaan. Testeri löytää integraatio-virheet ennen kuin ne saavuttavat toimivan sovelluksen: setter, joka kirjoittaa väärään kuvaajaan, getter, joka lukee eri ominaisuuden, oletusarvo, joka ei kulje takaisin, tai puuttuva pääsy ilmoitettuun kuvaajaan.

Perustesti komponentille näyttää tältä:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### Ominaisuuksien jättämisen muualla {#excluding-properties}

Jotkut kuvastot eivät noudata standardia getter- ja setter-käytäntöjä tai ne perustuvat ulkoiseen tilaan, jota testi ei voi tyydyttää. Merkitse ne `@PropertyExclude`-annotaatiolla, jotta ne ohitetaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Mukautetut getter- ja setter-nimet {#custom-getter-and-setter-names}

Jos kuvasto käyttää epästandardeja pääsy-nimiä, ilmoita ne `@PropertyMethods`-annotaatiolla.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target`-parametri hyväksyy luokan, kun pääsyluokat sijaitsevat muualla kuin komponentissa.

Lisätietoja testauspinnasta, katso [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Huolenaiheiden rajapinnat {#concern-interfaces}

Huolenaiheiden rajapinnat antavat `ElementComposite`-aliluokalle komponentin kyvyt ilman, että sinun tarvitsee kirjoittaa toteutusta itse. Rajapinnat välittävät kutsut taustalla olevalle elementille. Toteuta ne, joita komponentin tulisi tukea, parametrisoituna aliluokan tyypillä niin, että ketjutus palauttaa komponentin:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Toteutusta ei tarvita.
}

MyBadge badge = new MyBadge()
    .setText("Uusi")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Yllä olevat kolme rajapintaa kattavat kaiken, mitä `MyBadge` tarvitsee ilman metodeja luokassa. `HasText` altistaa `setText()`-menetelmän ja kirjoittaa elementin tekstisisältöön. `HasClassName` altistaa `addClassName()`, mikä antaa mahdollisuuden kohdistaa badge CSS:stä. `HasStyle` altistaa `setStyle()` inline-tyylille.

Kattavan saatavilla olevan rajapintakannan ja mitä kukin tarjoaa, katso [Huolenaiheiden rajapinnat](./component-fundamentals#concern-interfaces) Ymmärrys komponentteista -artikkelista. Jos oletus siirto ei vastaa sitä, mitä kääritty elementti altistaa, ylikirjoita menetelmä aliluokassa.

## Tapahtumat {#events}

### Tapahtuman rekisteröinti {#event-registration}

Web-komponentit lähettävät DOM-tapahtumia, kun jotain tapahtuu selaimessa. Reagoidaksesi Java:sta kuuntele näitä tapahtumia käyttäen `addEventListener()`. Komponentin lähettämien tapahtumien joukko vaihtelee, joten tarkista komponentin omasta asiakirjasta nimet ja käytettävissä olevat tiedot.

`ElementComposite` tukee debouncetta, throttlingia, suodattamista ja mukautettua tapahtumatietoa rekisteröidyillä kuuntelijoilla.

Rekisteröi tapahtumakuuntelijat käyttäen `addEventListener()`-menetelmää:

```java
// Esimerkki: Lisää klikkaustapahtumakuuntelija
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkauksen tapahtuma
});
```

:::info
`ElementComposite` hyväksyy vain tapahtumaluokkia, jotka on merkitty `@EventName`-annotaatiolla, toisin kuin `Element`, joka hyväksyy mitä tahansa merkkijonoista tapahtuman nimeä.
:::

### Integroitu tapahtumaluokka {#built-in-event-classes}

`ElementClickEvent` on yksi integroitu tapahtumaluokka, jonka `ElementComposite` julkaisee. Se esittää hiiren klikkaustapahtumia taustalla olevassa elementissä tyypitettyä pääsyä koordinaatteihin (`getClientX()`, `getClientY()`), painetietoja (`getButton()`) ja modifier-näppäimiä (`isCtrlKey()`, `isShiftKey()`, jne.).

Altista klikkauskäsittely aliluokan julkisessa API:ssa toteuttamalla `HasElementClickListener<T>`-huolenaihe. Se tarjoaa oletus `onClick()` ja `addClickListener()` menetelmät, jotka delegoivat suojattuun `addEventListener()` primitiiviin.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() ja addClickListener() ovat nyt saatavilla MyBadge-luokassa
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Muille tapahtumille, joita taustalla oleva web-komponentti lähettää, määritä mukautettu tapahtumaluokka. Katso [Mukautetut tapahtumaluokat](#custom-event-classes).

### Tapahtumien kuormitukset {#event-payloads}

Tapahtumat kuljettavat dataa asiakkaalta Java-koodiisi. Pääset tähän dataan `getData()`-menetelmällä raakan tapahtumadatan osalta tai käytä tyypitettyjä menetelmiä, kun ne ovat saatavilla integroituilla tapahtumaluokilla. Katso [Tapahtumat-opas](../building-ui/events) lisätietoja tehokkaasta kuormahallinnasta.

### Mukautetut tapahtumaluokat {#custom-event-classes}

Määritä mukautetut tapahtumaluokat `@EventName` ja `@EventOptions` avulla, jotta voit siepata asiakaspuolen dataa tyypitetyssä Java-tapahtumassa. Käytä tätä, kun Java-käsittelijä tarvitsee arvoja selaimesta.

`@EventName` sitoo Java-luokan niihin tapahtumiin, joita komponentti lähettää selaimessa, joten luokalle, joka on rekisteröity `@EventName("change")`, laukaistaan aina, kun taustalla oleva elementti lähettää `change`. `@EventOptions` määrittää, mitä kulkee mukana tämän tapahtuman kanssa. Jokainen `@EventData` sen sisällä yhdistää avaimen JavaScript-lausekkeeseen, joka arvioidaan DOM-tapahtuman yhteydessä. Tulos on saatavilla Java-tapahtumaluokassa `getData().get(key)` avulla.

Tuotearvostelulomake alla käyttää tätä kaaviota [`wa-rating`](https://webawesome.com/docs/components/rating/). Mukautettu `ChangeEvent` kuljettää arviointiarvon tyypityssä `double`-muodossa, ja kuuntelija käyttää sitä aktivoiakseen lähetyspainikkeen:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Tapahtumavaihtoehdot {#event-options}

`ElementEventOptions` määrittää tapahtumakuormituksen, debounce- tai throttling-ajo, suodatuslausekkeet ja ennakkototeutuskoodin. Alla oleva esimerkki näyttää vaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettu data asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Suorita JavaScript ennen tapahtuman laukaisua
  .setCode("component.classList.add('processing');")

  // Laukaise vain, jos olosuhteet täyttyvät
  .setFilter("component.value.length >= 2")

  // Viivästytä suoritusta, kun käyttäjä lopettaa kirjoittamisen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Ota nämä vaihtoehdot käyttöön rekisteröidessäsi kuuntelijaa mukautetulle tapahtumaluokalle
// (katso mukautettujen tapahtumaluokkien osalta ylle, kuinka määritellä niitä):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` altistaa vain luokkamuotoisen `addEventListener(Class, listener, options)`. Käytä sitä tapahtumaluokalla, joka on merkitty `@EventName`. Rekisteröidäksesi suoraan merkkijonoista tapahtuman nimeä vastaan, kutsu `getElement().addEventListener("input", listener, options)`.
:::

#### Suorituskyvyn hallinta {#performance-control}

**Debouncing** viivästyttää suoritusta, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisestä tapahtumasta
```

Saatavilla olevat debouncivaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaista aikaa, laukaise sitten (oletus)
- `BOTH`: Laukaise heti ja hiljaisen ajan jälkeen

**Throttling** rajoittaa suoritusfrekvenssiä:

```java
options.setThrottle(100); // Laukaise korkeintaan kerran 100ms
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Slotit ovat paikkoja web-komponentin sisällä, jotka käyttäjät täyttävät sisällöllä. Web-komponentti määrittelee slotit kuvassaan `<slot>` tai `<slot name="...">`, ja kääre altistaa menetelmiä, jotka laittavat Java-komponentteja näihin slotteihin.

Lisätäksesi sisältöä slotteihin laajenna `ElementCompositeContainer`-luokkaa `ElementComposite`-luokan sijaan. Säiliö kantaa saman ominaisuus-, attribuutti- ja prosessointikoneen lisäksi menetelmät, joita tarvitaan lasten lisäämiseen. Lapset, jotka lisätään `add()`-menetelmällä, menevät oletusslotin sisään. Lapset, jotka lisätään `getElement().add(slotName, components)`-menetelmällä, menevät nimettyyn slotiin.

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

Alla oleva demo näyttää kaksi hinnoittelukorttia, jotka on rakennettu [`wa-card`](https://webawesome.com/docs/components/card/) ja populoinnin `header`, oletus- ja `footer`-slotit Java:sta:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Slotin sisällön tarkastelu {#inspecting-slot-contents}

Taustalla oleva `Element` (johon pääset `getElement()`-menetelmällä) tarjoaa menetelmiä, joilla voit lukea mitä slotteihin on tällä hetkellä määritetty:

- **`findComponentSlot()`**: etsii kaikista sloteista tiettyä komponenttia ja palauttaa nimen slotista, joka sisältää sen, tai tyhjän merkkijonon, jos komponentti ei ole missään slotissa.
- **`getComponentsInSlot()`**: palauttaa luettelon komponenteista, jotka on määritetty tiettyyn slotiin. Valinnaisesti voi ottaa luokan tyypin suodattaa tuloksia.
- **`getFirstComponentInSlot()`**: palauttaa ensimmäisen komponentin, joka on määritetty slotiin. Valinnaisesti voi ottaa luokan tyypin suodatukseen.
