---
sidebar_position: 6
title: Elementtien koostaminen
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka kääriä mukautetun HTML-elementin tai [web-komponentin](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Se sitoo Java-luokkasi taustalla olevaan `Element`-elementtiin ja antaa sinun työskennellä sen ominaisuuksien, attribuuttien ja tapahtumien kanssa Java-kielellä. Käytä sitä, kun integroidaan web-komponentteja webforJ-sovellukseen.

:::tip Milloin käyttää `ElementComposite`
Käytä `ElementComposite`:a, kun käärit kolmannen osapuolen web-komponenttia, jota webforJ ei vielä tarjoa. Jos webforJ:llä on sisäänrakennettu komponentti, joka kattaa käyttötapauksen (kuten `TextField`, `ColorField`, `Button` jne.), käytä sitä sen sijaan. Yksittäistä DOM-työtä varten, jota ei tarvitse käyttää uudelleen, `Element`-luokkaa voidaan käyttää suoraan ilman käärettä.
:::

Tässä oppaassa näytetään, kuinka implementoidaan [Web Awesome relative-time web component](https://webawesome.com/docs/components/relative-time/) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com.webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Luokan annotaatiot {#class-annotations}

Kolme annotaatiota ilmestyy yleisesti `ElementComposite`-aliluokan alkuun: `@NodeName` määrittelee HTML-tagin, jonka komponentti käärii, ja `@JavaScript` ja `@StyleSheet` lataavat kaikki asiakassivuston resurssit, joita taustalla oleva web-komponentti tarvitsee. `@NodeName` on pakollinen ja erityinen `ElementComposite`:lle. `@JavaScript` ja `@StyleSheet` ovat yleisiä webforJ-resurssiannotaatiota ja toimivat kaikissa luokissa, mukaan lukien näkymät, komponentit tai `App`-luokka.

### `@NodeName` {#nodename}

`@NodeName`-annotaatio määrittelee HTML-tagin, jonka komponentti kääri. webforJ käyttää tätä nimeä luodessaan taustalla olevaa elementtiä DOM:issa.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Tagin nimen on vastattava asiakaspuolella rekisteröityä mukautettua elementtiä. Ilman tätä annotaatiota kehys ei voi määrittää, mitä elementtiä luodaan.

Aliluokassa `getNodeName()` lukee ilmoitetun tagin, ja `getElement()` palauttaa taustalla olevan `Element`-elementin, joten voit kutsua sen DOM-tason metodeja suoraan.

### `@JavaScript` {#javascript}

`@JavaScript`-annotaatio lataa skriptin, joka määrittelee tai rekisteröi taustalla olevan web-komponentin. Aseta se luokalle, jotta skripti latautuu vain, kun komponenttia käytetään.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Useita `@JavaScript`-annotaatioita on sallittu, ja webforJ poistaa automaattisesti päällekkäiset lataukset. Sama skripti ei lataudu kahdesti, jos useat komponentit riippuvat siitä.

Katsou [JavaScript-tiedostojen tuonti](../managing-resources/importing-assets#importing-javascript-files) täydelliseen vaihtoehtovalikoimaan, mukaan lukien `top`, `attributes` ja latausaikataulu.

### `@StyleSheet` {#stylesheet}

`@StyleSheet`-annotaatio lataa CSS-tiedoston, jota komponentti tarvitsee. Se on hyödyllinen kolmannen osapuolen komponenteille, jotka tarjoavat erillisen tyylitiedoston, tai komponenttikohtaisen tyylin pakkaamiseen kääreen mukana.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Paikallisesti pakattujen resurssien osalta käytä `ws://`-etuliitettä viittaamaan tiedostoihin `resources/static`-kansiossa:

```java
@StyleSheet("ws://components/relative-time.css")
```

Katsou [CSS-tiedostojen tuonti](../managing-resources/importing-assets#importing-css-files) täydelliseen vaihtoehtovalikoimaan.

## Ominaisuudet ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit edustavat web-komponentin tilaa, joita yleensä käytetään datan tai konfiguraation säilyttämiseen. `ElementComposite` altistaa molemmat `PropertyDescriptor`:in kautta.

Kaksi tehdasmetodia `PropertyDescriptor`:issä tuottaa itse kuvaston, yksi jokaista sitoutumiskohdetta kohti:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` sitoo JavaScript-ominaisuuden DOM-solmulle. `PropertyDescriptor.attribute()` sitoo HTML-attribuutin. Ensimmäinen argumentti on nimi, jota web-komponentti odottaa. Toinen on oletusarvo, joka myös määrittää kuvaston Java-tyypin.

Ilmoita kuvasto private-kenttänä komponentissa, lue ja kirjoita sitä `set(PropertyDescriptor<V> property, V value)` ja `get(PropertyDescriptor<V> property)` avulla.

:::info
Ominaisuudet ovat sisäistä tilaa DOM-solmussa, eivätkä heijasta merkintöjä. Attribuutit ovat HTML-merkintää, näkyviä ulkoisille skripteille ja CSS:lle.
:::

```java
// Esimerkki "title"-ominaisuudesta ElementComposite-luokassa
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Esimerkki "value"-attribuudista ElementComposite-luokassa
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Otsikkoni");
set(value, "Arvoni");
```

Yllä olevat kutsut käyttävät `set()`-menetelmää suoraan osoittaakseen yksinkertaista muotoa. Käytännössä `set()` ja `get()` ovat `protected`-menetelmiä `ElementComposite`:ssa. Ne ovat yksinkertainen kerros, joka synkronoi Java-arvot taustalla olevan elementin kanssa, ei julkinen API, jota kuluttajat kutsuvat. Tarkoitettu malli on pitää `PropertyDescriptor` yksityisenä ja kirjoittaa julkiset `setX()` ja `getX()` -menetelmät, jotka delegoivat primitiiveihin.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // protected primitiivi
    return this;
  }

  public String getHeading() {
    return get(heading);     // protected primitiivi
  }
}
```

Yksi kutsu `set(descriptor, value)` tekee kolme asiaa kerralla. Se työntää arvon klientille `setProperty()`-menetelmällä ominaisuuksille tai `setAttribute()`-menetelmällä attribuuteille. Se tallentaa arvon paikalliseen palvelinpuolen välimuistiin, yksi kartta per komponenttiversio. Ja se tallentaa ajonaikaisen tyypin yhdessä arvon kanssa, jotta myöhemmät `get()`-kutsut tietävät, kuinka deserialisoidaan.

Tuon paikallisen välimuistin vuoksi `get()` voi olla halpa oletuksena. `get(descriptor)` palauttaa välimuistissa olevan arvon palvelinpuolen kätkosta ilman verkko-kutsua, koska jokainen `set()` pitää välimuistin synkronoituna klientin kanssa. Valinnainen `boolean`-toinen argumentti ohjaa, ohitetaanko välimuisti ja luetaan suoraan selainohjelmasta.

```java
String cached = get(heading);            // luetaan palvelinpuolen välimuorista
String live = get(heading, true);        // pakottaa lukemaan selaimesta
```

Aseta `fromClient` todeksi, kun arvo voi muuttua asiakkaalla ilman palvelimen tietämystä, kuten kirjoitettavan `<input>`-arvon kohdalla. Palvelimelta ohjatuissa ominaisuuksissa oletusarvo välttelee ylimääräistä matkaa.

Valinnainen kolmas argumentti on `java.lang.reflect.Type`, joka ohjaa, miten tulos deserialisoidaan. webforJ ratkaisee tyypin tässä järjestyksessä: eksplisiittinen `Type`-argumentti, jos se on annettu, sitten ajonaikainen tyyppi, joka tallennettiin aikaisemman `set()`-kutsun yhteydessä samalla kuvastolla, sitten `Object.class`. Käytännössä aikaisemman `set()`-kutsun tallennettu tyyppi on riittävä, joten kolmas argumentti voidaan yleensä jättää pois. Se on tarpeen, kun kirjattu luokka menettää tietoa, jota deserialisoija tarvitsee, kuten parametrisoitu tyyppi kuten `List<String>`, jonka ajonaikainen luokka on vain `ArrayList`.

Alla oleva demo lisää ominaisuuksia relative-time web-komponentin asiakirjojen perusteella ja altistaa ne getterien ja setterien kautta. Jokainen rivi aktiivisuusvirrassa käyttää erilaisia `format`- ja `numeric`-arvoja osoittaakseen, kuinka sama komponentti renderöidään vaihtelevilla kokoonpanoilla.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Ominaisuudet ja attribuutit {#properties-versus-attributes}

Vaikka `PropertyDescriptor.property()` ja `PropertyDescriptor.attribute()` näyttävät olevan vaihdettavissa, ne kohdistavat eri osiin taustalla olevaa elementtiä. Väärän valinnan tekeminen aiheuttaa arvoja, jotka eivät sovellu.

Ominaisuudet ovat JavaScript-objektin ominaisuuksia DOM-solmussa. Ne voivat pitää minkä tahansa tyyppisiä arvoja, mukaan lukien merkkijonot, booleanit, numerot, objektit ja taulukot, ja ne edustavat elementin nykyistä ajonaikaista tilaa. Ominaisuuden asettaminen on suora JavaScript-muutos.

Attribuutit ovat HTML-merkintöjä. Ne elävät elementin avausmerkin sisällä, ovat aina merkkijonoja ja edustavat elementin alkuperäistä kokoonpanoa. Attribuutin asettaminen laukaisee DOM-muutoksen ja merkkijono-muunnoksen.

Joissakin tapauksissa molemmat pysyvät synkronoituna. Toisinaan ne eroavat. `<input>`-elementin `value` on klassinen esimerkki: `value`-attribuutti on alkuperäinen arvo, kun taas `value`-ominaisuus on nykyinen arvo, jonka käyttäjä on kirjoittanut. Attribuutin lukeminen sen jälkeen, kun käyttäjä on kirjoittanut, palauttaa alkuperäisen merkintöjen, mutta ominaisuuden lukeminen palauttaa kentän nykyisen sisällön.

Käytä **ominaisuuksia**:

- **Usein muuttuva ajonaikainen tila**: laskurit, nykyiset valinnat, kirjoitetut arvot
- **Ei-merkkijonotyyppisiä**: booleanit, numerot, objektit, taulukot
- **Suorituskykyyn liittyvät päivitykset**: ominaisuudet ohittavat merkkijono-muunnoksen vaatimukset attribuuteille

Käytä **attribuutteja**:

- **Alkuperäinen konfiguraatio**: asetukset, joita komponentti lukee vain kerran, kun se yhdistyy
- **CSS-valitsimet**: arvot, joita haluat kohdistaa valitsimilla, kuten `[disabled]` tai `[variant="danger"]`
- **Saavutettavuuslinkit**: `aria-label`, `role` ja muut ARIA-attribuutit
- **Merkkijonotyyppiset asetukset, jotka harvoin muuttuvat**

Kun käännät kolmannen osapuolen web-komponenttia, tarkista komponentin asiakirjat varmistaaksesi, mikä nimi vastaa ominaisuutta ja mikä attribuuttia. `PropertyDescriptor.attribute()` -kutsuminen tulkintaan, jota komponentti tarjoaa vain ominaisuutena, ei toimi, ja sama pätee päinvastaisessa mielessä. Komponentti huomaamattomasti ohittaa arvon.

### Ominaisuuksien tyypitys {#typing-properties}

Kuvasto on parametrisoitu sen arvon Java-tyypin mukaan. Täydellinen julkaisusyntaksi on:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>`-geneerinen parametri ilmoittaa arvon tyypin. Oletusarvon ajonaikainen tyyppi myös määrittää `T`:n, joten geneeristä argumenttia ei yleensä tarvitse määrittää erikseen. webforJ käyttää `T`:tä arvojen sarjoittamiseen ja deserialisoimiseen kommunikoidessaan klientin kanssa.

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

Sarjoittaminen on automaattista primitiivisten, heidän pakattujen vastineidensa ja `String`-tyyppisten arvojen osalta. Monimutkaisille tyypeille arvo sarjoitetaan JSON-muotoon ennen kuin se asetetaan asiakkaan ominaisuudelle.

### Arvojen validoiminen {#validating-values}

Vahvista arvot setterissä ennen `set()`-kutsua. Setter on luontaisesti pakottava kohta, koska jokainen muutos virtaa sen läpi.

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

Nullable-viittauksille käytä `Objects.requireNonNull()` -metodia, jotta virhe tulee esiin rajapinnassa sen sijaan, että se ilmenisi myöhemmin renderöintiputkessa.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Vältä validointia `get()`-metodissa. Lukuoperaatioiden tulisi pysyä halpoina ja johdonmukaisina.

### Enum-tyyliset ominaisuudet {#enum-style-properties}

Useimmat web-komponentit odottavat pieniä tai kebab-kirjoitusasuja merkkijonon arvoja enum-tyylisille ominaisuuksille (`theme="primary"`, `expanse="xs"`). webforJ käyttää Gsonia sarjoittamiseen enum-tyypeille, mutta Gsonin oletusedustus on vakiotonimi suurilla kirjaimilla. Merkitse jokainen vakio `@SerializedName`-annotaatiolla, jotta sarjoitettu arvo vastaa mitä web-komponentti odottaa.

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

Tämä on sama malli, jota webforJ:n sisäänrakennetut komponentit käyttävät `Theme`, `Expanse` ja vastaavien enumien kohdalla. Julkinen Java-API pysyy tyyppiturvallisena ja web-komponentti vastaanottaa arvon suoraan `@SerializedName`:sta.

### Ominaisuuksien testaaminen {#testing-properties}

`PropertyDescriptorTester` validoi, että jokainen komponentin `PropertyDescriptor` on kytketty oikein. Se skannaa luokkaa kuvastokenttien osalta, kutsuu jokaista setteria oletusarvolla ja vertaa tulosta siihen, mitä getter palauttaa. Testeri löytää integraatiovirheitä ennen niiden saapumista suoraan sovellukseen: setter, joka kirjoittaa väärään kuvastoon, getter, joka lukee eri ominaisuuden, oletusarvo, joka ei kulje läpi tai puuttuva pääsy ilmoitetulle kuvastolle.

Komponentin perustesti näyttää tältä:

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

#### Ominaisuuksien ekskludointi {#excluding-properties}

Jotkut kuvastot eivät noudata vakiopohjaisia getter- ja setter-konventioita tai ne riippuvat ulkoisesta tilasta, jota testi ei voi tyydyttää. Annotoi niitä `@PropertyExclude`-annotaatiolla, jotta ne ohitetaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Mukautetut getter- ja setter-nimet {#custom-getter-and-setter-names}

Jos kuvasto käyttää ei-standardeja pääsynimiä, ilmoita ne `@PropertyMethods`-annotaatiolla.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target`-parametri hyväksyy luokan, kun pääsy ei ole komponentissa itsessään.

Lisätietoja testauspinnasta katsou [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Huolenaiheiden rajapinnat {#concern-interfaces}

Huolenaiheiden rajapinnat antavat `ElementComposite`-aliluokalle kyvykkyksiä ilman, että sinun tarvitsee kirjoittaa toteutusta itse. Rajapinnat välittävät kutsuja taustalla olevalle elementille. Toteuta ne, joita komponentin tulisi tukea, parametrisoitu tällä aliluokan tyypillä, jotta ketjuttaminen palauttaa komponentin:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Toteutusta ei tarvita.
}

MyBadge badge = new MyBadge()
    .setText("Uusi")
    .addClassName("korostettu")
    .setStyle("color", "var(--dwc-color-primary)");
```

Yllä olevat kolme rajapintaa kattaa kaiken, mitä `MyBadge` tarvitsee ilman mitään metodiisi toteutusta. `HasText` altistaa `setText()` -menetelmän ja kirjoittaa elementin tekstisisältöön. `HasClassName` altistaa `addClassName()` -menetelmän, joka mahdollistaa badge-joukon kohdistamisen CSS:stä. `HasStyle` altistaa `setStyle()`-menetelmän inline-tyylittämistä varten.

Saat täydellisen luettelon käytettävissä olevista rajapinnoista ja siitä, mitä kukin tarjoaa, katsou [Huolenaiheiden rajapinnat](./component-fundamentals#concern-interfaces) Komponenttien ymmärtämistä käsittelevästä artikkelista. Jos oletusvälitys ei vastaa mitä kääritty elementti altistaa, ylikirjoita metodi aliluokassa.

## Tapahtumat {#events}

### Tapahtumien rekisteröinti {#event-registration}

Web-komponentit lähettävät DOM-tapahtumia, kun jotain tapahtuu selaimessa. Reagoidaksesi Javalla, kuuntele näitä tapahtumia `addEventListener()`-menetelmällä. Tapahtumien kokonaisuus, joita komponentti lähettää, vaihtelee, joten tarkista komponentin omat asiakirjat käytettävissä olevien nimien ja payloadien osalta.

`ElementComposite` tukee katkaisemista, hidastusta, suodattamista ja mukautettuja tapahtumatietoja rekisteröidyille kuuntelijoille.

Rekisteröi tapahtumakuuntelijat käyttäen `addEventListener()`-menetelmää:

```java
// Esimerkki: Lisäämällä klikkauksen tapahtumakuuntelija
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkauksen tapahtuma
});
```

:::info
`ElementComposite` hyväksyy vain tapahtumaluokkia, jotka on merkitty `@EventName`-annotaatiolla; toisin kuin `Element`, joka hyväksyy minkä tahansa merkin tapahtuman nimen.
:::

### Sisäänrakennetut tapahtumaluokat {#built-in-event-classes}

`ElementClickEvent` on ainoa sisäänrakennettu tapahtumaluokka, joka toimitetaan `ElementComposite`:n mukana. Se altistaa hiiren klikkaustapahtumat taustalla olevalle elementille tyypitettyjen pääsyjen (kuten `getClientX()`, `getClientY()`), napin tiedot (`getButton()`) ja modifier-näppäimien (`isCtrlKey()`, `isShiftKey()` jne.) avulla.

Jotta klikkaustapahtuman käsittely voitaisiin altistaa aliluokan julkiselle rajapinnalle, toteuta `HasElementClickListener<T>` huolenaiheiden rajapinta. Se tarjoaa oletus `onClick()` ja `addClickListener()` -menetelmät, jotka delegoivat suojatun `addEventListener()` primitiiville.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() ja addClickListener() ovat nyt käytettävissä MyBadge:ssa
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Muille tapahtumille, joita taustalla oleva web-komponentti lähettää, määrittele mukautettu tapahtumaluokka. Katsou [Mukautetut tapahtumaluokat](#custom-event-classes).

### Tapahtuman payloadit {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodillesi. Pääset tähän dataan käyttäen `getData()` raakatapahtumatiedolle tai käytä tyypitettyjä menetelmiä, kun ne ovat saatavana sisäänrakennetuissa tapahtumaluokissa. Tiedät enemmän [Tapahtumien oppaasta](../building-ui/events) tehokkaasta payload-käsittelystä.

### Mukautetut tapahtumaluokat {#custom-event-classes}

Määrittele mukautetut tapahtumaluokat `@EventName` ja `@EventOptions` -annotaattioilla, jotta voit kaapata asiakaspuolen tietoja tyypitettyyn Java-tapahtumaan. Käytä tätä, kun Java-käsittelijä tarvitsee arvoja selaimelta.

`@EventName` sitoo Java-luokan komponentin lähettämään tapahtumaan selaimessa, joten luokka, jota merkitään `@EventName("change")`, laukaisee aina, kun taustalla oleva elementti lähettää `change`. `@EventOptions` ohjaa, mitä kulkee takaisin tämän tapahtuman mukana. Jokainen sisällä oleva `@EventData` paristaa avaimen JavaScript-lauseen, joka arvioidaan DOM-tapahtumaa kohtaan. Tulos on saatavilla Java-tapahtumaluokassa `getData().get(key)`-kutsulla.

Alla oleva tuotearvostelulomake käyttää tätä mallia mukautetun `ChangeEvent`:n kanssa, joka välittää arviointiarvon tyypitettynä `double`:na ja kuuntelija käyttää sitä, jotta lähetyspainike aktivoituu:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Tapahtumavaihtoehdot {#event-options}

`ElementEventOptions` konfiguroi tapahtuman payloadin, katkaisun tai hidastamisen ajoituksen, suodatuslausekkeet ja esikoodin. Alla oleva koodi näyttää vaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettuja tietoja asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Suorita JavaScript ennen tapahtuman laukaisemista
  .setCode("component.classList.add('processing');")

  // Laaditaan vain, jos ehdot täyttyvät
  .setFilter("component.value.length >= 2")

  // Viivytä suoritusta, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Käytä näitä vaihtoehtoja rekisteröidessäsi kuuntelijaa mukautetulle tapahtumaluokalle
// (katso Mukautetut tapahtumaluokat -osiota, kuinka määritellä semmoinen):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` tarjoaa vain luokkapohjaisen muodon `addEventListener(Class, listener, options)`. Käytä sitä tapahtumaluokan kanssa, joka on merkitty `@EventName`. Rekisteröidäksesi suoraan merkin tapahtuman nimen avulla, kutsu `getElement().addEventListener("input", listener, options)`.
:::

#### Suorituskyvyn hallinta {#performance-control}

**Katkaisu** viivästyttää suoritusta, kunnes toiminta lakkaa:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisestä tapahtumasta
```

Saatavilla olevat katkaisuvaiheet:

- `LEADING`: Laukaise heti ja odota
- `TRAILING`: Odota hiljaista aikajaksoa, laukaise sitten (oletus)
- `BOTH`: Laukaise heti ja hiljaisessa ajanjaksossa

**Hidastaminen** rajoittaa suoritusten tiheyttä:

```java
options.setThrottle(100); // Laukaise enintään kerran 100ms
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Slotit ovat paikkoja web-komponentin sisällä, joihin käyttäjät voivat täyttää sisältöä. Web-komponentti ilmoittaa slotistaan kaaviossaan `<slot>` tai `<slot name="...">`, ja kääre altistaa menetelmiä, joilla Java-komponentit voidaan sijoittaa näihin slotteihin.

Lisätäksesi sisältöä sloteihin, laajenna `ElementCompositeContainer`-luokkaa sen sijaan, että käyttäisit `ElementComposite`:a. Säiliö sisältää samat ominaisuus- ja attribuuttilait, sekä metodit lasten lisäämiseen. `add()`-metodin avulla lisättyjä lapsia sijoitetaan oletusslotiin. `getElement().add(slotName, components)`-kutsujen avulla lisättyjä lapsia lisätään nimettyyn slotille.

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

Alla oleva demo näyttää kaksi hinnoittelukorttia, jotka on rakennettu käyttäen [`wa-card`](https://webawesome.com/docs/components/card/), ja sijoitettavat `header`, oletus- ja `footer` -slotit Java-koodilla:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Slotin sisältöjen tarkastelu {#inspecting-slot-contents}

Taustalla oleva `Element` (johon pääset käsiksi `getElement()`-kutsun avulla) tarjoaa menetelmiä, joiden avulla voit lukea nykyisin slotteihin määritetyt asiat:

- **`findComponentSlot()`**: etsii kaikki slotit tietyltä komponentilta ja palauttaa sen slotin nimen, joka sisältää sen, tai tyhjän merkkijonon, jos komponentti ei ole missään slotissa.
- **`getComponentsInSlot()`**: palauttaa luettelon komponenteista, jotka on määritetty tiettyyn slotille. Tarvittaessa ottaa tyypillisen argumentin suodatusta varten.
- **`getFirstComponentInSlot()`**: palauttaa ensimmäisen slotille määritetyn komponentin. Tarvittaessa ottaa tyypillisen argumentin suodatusta varten.
