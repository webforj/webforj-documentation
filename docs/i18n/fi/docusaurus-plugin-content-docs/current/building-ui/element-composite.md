---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 277c6e7e84197ab515cae210cd8207b0
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka käärii mukautetun HTML-elementin tai [web-komponentin](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Se sitoo Java-luokkasi taustalla olevaan `Element`-elementtiin ja antaa sinun työskennellä kyseisen elementin ominaisuuksien, attribuuttien ja tapahtumien kanssa Javassa. Käytä sitä, kun integroi web-komponentteja webforJ-sovellukseen.

:::tip Milloin käyttää `ElementComposite`-luokkaa
Valitse `ElementComposite`, kun käärit kolmannen osapuolen web-komponentin, jota webforJ ei jo tarjoa. Jos sisäänrakennettu webforJ-komponentti kattaa käytön (kuten `TextField`, `ColorField`, `Button` jne.), käytä sitä sen sijaan. Yksittäisille DOM-toimille, joita ei tarvitse käyttää uudelleen, `Element`-luokkaa voidaan käyttää suoraan ilman käärettä.
:::

Tässä oppaassa esitellään, kuinka toteutetaan [Web Awesome suhteellisen ajan web-komponentti](https://webawesome.com/docs/components/relative-time/) käyttämällä `ElementComposite`-luokkaa.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Luokan anotoinnit {#class-annotations}

Kolme annotaatiota esiintyy yleisesti `ElementComposite`-aliluokan yläosassa: `@NodeName` määrittelee HTML-tagin, jota komponentti käärii, ja `@JavaScript` sekä `@StyleSheet` lataavat kaikki asiakaspuolen resurssit, joita taustalla oleva web-komponentti tarvitsee. `@NodeName` on pakollinen ja spesifinen `ElementComposite`:lle. `@JavaScript` ja `@StyleSheet` ovat yleisiä webforJ-resurssianotointeja ja toimivat kaikissa luokissa, mukaan lukien näkymät, komponentit tai `App`-luokka.

### `@NodeName` {#nodename}

`@NodeName`-annotaatio määrittelee HTML-tagin, jota komponentti käärii. WebforJ käyttää tätä nimeä luodessaan taustalla olevaa elementtiä DOM:ssä.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Tagin nimen on vastattava mukautettua elementtiä, joka on rekisteröity asiakaspuolella. Ilman tätä annotaatiota kehys ei voi määrittää, mikä elementti luodaan.

Aliluokassa `getNodeName()` palauttaa ilmoitetun tagin ja `getElement()` palauttaa taustalla olevan `Element`:in, jotta voit kutsua suoraan DOM-tason metodeja.

### `@JavaScript` {#javascript}

`@JavaScript`-annotaatio lataa skriptin, joka määrittelee tai rekisteröi taustalla olevan web-komponentin. Aseta se luokkaan, jotta skripti ladataan vain, kun komponenttia käytetään.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Useita `@JavaScript`-annotaatioita on sallittuja, ja webforJ poistaa duplikaatit automaattisesti. Sama skripti ei lataudu kahdesti, jos useat komponentit riippuvat siitä.

Katso [JavaScript-tiedostojen tuonti](../managing-resources/importing-assets#importing-javascript-files) täydellisestä vaihtoehtojen joukosta, mukaan lukien `top`, `attributes` ja latausaika.

### `@StyleSheet` {#stylesheet}

`@StyleSheet`-annotaatio lataa CSS-tiedoston, jolle komponentti riippuu. Se on hyödyllinen kolmansien osapuolten komponenteille, jotka toimittavat erillisen tyylitiedoston tai komponenttispesifisten tyylien pakettien kokoamiseen kääreen mukana.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Paikallisesti pakattuja resursseja varten käytä `ws://`-etuliitettä viitataksesi tiedostoihin `resources/static`-kansiossa:

```java
@StyleSheet("ws://components/relative-time.css")
```

Katso [CSS-tiedostojen tuonti](../managing-resources/importing-assets#importing-css-files) täydelliselle vaihtoehtojen joukolle.

## Ominaisuudet ja attribuuttideskryptorit {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit edustavat web-komponentin tilaa, yleensä pitäen tietoja tai konfiguraatiota. `ElementComposite` altistaa molemmat `PropertyDescriptor`in kautta.

Kaksi tehdasmetodia `PropertyDescriptor`issa tuottaa itse deskriptorin, yksi per sitomiskohde:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` sitoo JavaScript-ominaisuuteen DOM-solmussa. `PropertyDescriptor.attribute()` sitoo HTML-attribuuttiin. Ensimmäinen argumentti on nimi, jota web-komponentti odottaa. Toinen on oletusarvo, joka myös määrittää deskriptorin Java-tyypin.

Määritä deskriptor komponentin yksityiseksi kentäksi ja lue ja kirjoita sen kautta `set(PropertyDescriptor<V> property, V value)` ja `get(PropertyDescriptor<V> property)` -metodeilla.

:::info
Ominaisuudet ovat sisäistä tilaa DOM-solmussa eivätkä heijastu merkkaustekstiin. Attribuutit ovat HTML-merkkauksia, jotka ovat näkyviä ulkoisille skripteille ja CSS:lle.
:::

```java
// Esimerkki ominaisuudesta nimeltä "title" ElementComposite-luokassa
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Esimerkki attribuutista nimeltä "value" ElementComposite-luokassa
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

Yllä olevat kutsut käyttävät `set()`-metodia suoraan esittelemään primitiivistä muotoa. Käytännössä `set()` ja `get()` ovat `protected`-menetelmiä `ElementComposite`:ssa. Ne ovat primitiivinen kerros, joka synkronoi Java-arvoja taustalla olevan elementin kanssa, ei julkinen API, jota kuluttajat kutsuvat. Tarkoitettu malli on pitää `PropertyDescriptor` yksityisenä ja kirjoittaa julkiset `setX()` ja `getX()` -metodit, jotka delegoivat primitiivisiin kutsuihin.

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

Yksi kutsu `set(descriptor, value)` tekee kolme asiaa kerralla. Se työntää arvon asiakkaalle `setProperty()`-menetelmän kautta ominaisuuksille tai `setAttribute()`-menetelmän kautta attribuuteille. Se tallentaa arvon paikalliseen palvelinpuolen välimuistiin, yksi kartta per komponentti-instanssi. Ja se tallentaa aikarajan tyypin arvon rinnalle, jotta myöhemmät `get()`-kutsut tietävät, kuinka deserialisoida.

Tuo paikallinen välimuisti on syy siihen, että `get()` voi olla edullinen oletusarvoisesti. `get(descriptor)` palauttaa välimuistissa olevan arvon palvelinpuolen tallennuksesta ilman verkko-kutsua, koska jokainen `set()` pitää välimuistin synkronoituna asiakkaan kanssa. Valinnainen `boolean` toinen argumentti hallitsee, ohitetaanko välimuisti ja luetaanko se selaimesta sen sijaan.

```java
String cached = get(heading);            // lukee palvelinpuolen välimuistista
String live = get(heading, true);        // pakottaa lukemaan selaimesta
```

Aseta `fromClient` todeksi, kun arvo voi muuttua asiakkaalla ilman palvelimen tietoa, kuten kirjoitetun `<input>`-arvon yhteydessä. Palvelinohjatuille ominaisuuksille oletus väistää täyttä kierrosta.

Valinnainen kolmas argumentti on `java.lang.reflect.Type` ja hallitsee, miten tulos deserialisoidaan. webforJ ratkaisee tyypin tässä järjestyksessä: eksplisiittinen `Type`-argumentti, jos se on annettu, sitten aikarajan tyyppi, joka tallennettiin aiemmalla `set()`-kutsulla samalla deskriptorilla, sitten `Object.class`. Käytännössä aiemmin tallennettu tyyppi `set()`-kutsusta riittää, joten kolmas argumentti voidaan yleensä jättää pois. Se on tarpeen, kun tallennettu luokka menettää tietoa, jota deserialisoija tarvitsee, kuten parametrisoitu tyyppi kuten `List<String>`, jonka aikaraja on vain `ArrayList`.

Alla oleva demo lisää suhteellisia-aika-ominaisuuksia web-komponentin asiakirjojen mukaan ja altistaa ne gettereiden ja setterien läpi. Jokainen aktiviteettisyötteen rivi käyttää erilaisia `format` ja `numeric` arvoja näyttääkseen, kuinka sama komponentti renderöidään eri konfiguraatioissa.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Ominaisuudet vs attribuutit {#properties-versus-attributes}

Vaikka `PropertyDescriptor.property()` ja `PropertyDescriptor.attribute()` vaikuttavat olevan vaihdettavissa, ne kohdistavat eri osiin taustalla olevaa elementtiä. Väärän valinnan tekeminen johtaa arvoihin, jotka hiljaa epäonnistuvat sovelluksessa.

Ominaisuudet ovat JavaScript-objektin ominaisuuksia DOM-solmussa. Ne voivat pitää minkä tyyppisiä tahansa, mukaan lukien merkkijonot, booleanit, numerot, objektit ja taulukot, ja ne edustavat elementin nykyistä aikarajan tilaa. Ominaisuuden asettaminen on suora JavaScript-määritys.

Attribuutit ovat HTML-merkkauksia. Ne sijaitsevat elementin avautuvassa tagissa, ovat aina merkkijonoja ja edustavat elementin alkukonfiguratia. Attribuutin asettaminen laukaisee DOM-muutoksen ja merkkijonon muunnoksen.

Joissakin tapauksissa kaksi pysyy synkronoituna. Toisissa ne eroavat. `<input>`-elementin `value` on klassinen esimerkki: `value`-attribuutti on alkuperäinen arvo, kun taas `value`-ominaisuus on nykyinen arvo, jonka käyttäjä on kirjoittanut. Attribuutin lukeminen sen jälkeen, kun käyttäjä on kirjoittanut, palauttaa alkuperäisen merkkaustekstin, mutta ominaisuuden lukeminen palauttaa kentän nykyisen sisällön.

Käytä **ominaisuuksia**:

- **Usein vaihtuva aikarajan tila**: laskurit, nykyiset valinnat, kirjoitetut arvot
- **Ei-merkkijonotyypit**: booleanit, numerot, objektit, taulukot
- **Suorituskykyherkät päivitykset**: ominaisuudet ohittavat attribuuttien vaatimien merkkijonomuunnosten

Käytä **attribuutteja**:

- **Alkukonfiguraatio**: asetukset, jotka komponentti lukee kerran, kun se yhdistetään
- **CSS-valitsimet**: arvot, joita haluat kohdistaa valitsimilla kuten `[disabled]` tai `[variant="danger"]`
- **Esteettömyyshookit**: `aria-label`, `role` ja muut ARIA-attribuutit
- **Harvoin muuttuvat merkkijonon kaltaiset asetukset**

Kun käännät kolmannen osapuolen web-komponenttia, tarkista komponentin dokumentaatio varmistaaksesi, mikä nimi liittyy ominaisuuteen ja mikä attribuuttiin. `PropertyDescriptor.attribute()`-kutsun käyttäminen jollekin, jota komponentti altistaa vain ominaisuutena, ei toimi, ja sama pätee käänteisesti. Komponentti hiljaa jättää arvon huomiotta.

### Ominaisuuksien tyypitys {#typing-properties}

Deskriptorin parametrina on arvon Java-tyyppi. Täysi ilmoitussynntaksi on:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>`-geneerinen parametri määrittelee arvon tyypin. Oletusarvon aikarajan tyyppi määrittää myös `T`:n, joten geneeristä argumenttia ei yleensä tarvitse määrittää eksplisiittisesti. webforJ käyttää `T`:tä arvojen serialisoimiseen ja deserialisoimiseen asiakkaan kanssa kommunikoidessa.

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

Serialisointi on automaattista primitiiveille, niiden pakatuille vastineille ja `String`:lle. Monimutkaisille tyypeille arvo serialisoidaan JSON-muodossa ennen kuin se asetetaan asiakkaan puolella olevalle omaisuudelle.

### Arvojen validoiminen {#validating-values}

Vahvista arvot setterissä ennen `set()`-kutsun tekemistä. Setter on luonnollinen valvontakohta, koska jokainen muutos kulkee sen läpi.

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

Nulloitettavien viitteiden yhteydessä käytä `Objects.requireNonNull()`-metodia, jotta epäonnistuminen tulee näkyväksi rajapinnassa sen sijaan, että se tapahtuisi myöhemmin renderöintiputkessa.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Vältä vahvistamista `get()`-kutsussa. Lukujen tulisi pysyä edullisina ja johdonmukaisina.

### Enum-tyyliset ominaisuudet {#enum-style-properties}

Suurin osa web-komponenteista odottaa pienistä tai kebab-käsen merkkijonoarvoista enum-tyylisille ominaisuuksille (`theme="primary"`, `expanse="xs"`). webforJ käyttää Gsonia enumien serialisoimiseen, mutta Gsonin oletusesitys on vakion nimi isoilla kirjaimilla. Merkitse jokainen vakio `@SerializedName`-annotaatiolla, jotta serialisoitu arvo vastaa sitä, mitä web-komponentti odottaa.

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

Määritä deskriptorin enum-tyyppi ja käytä enumia suoraan setterissä ja getterissä.

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

Tämä on sama malli, jota webforJ:n sisäänrakennetut komponentit käyttävät `Theme`, `Expanse` ja vastaavien enum-tyyppien osalta. Julkinen Java-API pysyy tyyppiturvallisena, ja arvo, jonka web-komponentti vastaanottaa, on merkkijono `@SerializedName`-annotaatiosta.

### Ominaisuuksien testaaminen {#testing-properties}

`PropertyDescriptorTester` validoi, että jokainen `PropertyDescriptor` komponentissa on kytketty oikein. Se skannaa luokan deskriptorikentät, kutsuu kutakin setteria oletusarvolla ja vertaa tulosta siihen, mitä getter palauttaa. Testeri löytää yhdistämisvirheitä ennen niiden saavuttamista toimivassa sovelluksessa: setter, joka kirjoittaa väärään deskriptorin, getter, joka lukee eri ominaisuutta, oletusarvo, joka ei palauta takaisin, tai puuttuva pääsy ilmoitetulle deskriptorille.

Perustason testi komponentille näyttää tältä:

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

#### Ominaisuuksien jättämisen {#excluding-properties}

Jotkut deskriptorit eivät noudata standardi getter- ja setter-käytäntöjä, tai ne riippuvat ulkoisesta tilasta, jota testi ei voi tyydyttää. Merkitse ne `@PropertyExclude`-annotaatiolla ohittaaksesi ne.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Mukautetut getter- ja setter-nimet {#custom-getter-and-setter-names}

Jos deskriptorissa käytetään ei-standardin pääsy nimiä, julista ne `@PropertyMethods`-annotaatiolla.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target`-parametri hyväksyy luokan, kun pääsyt sijaitsevat muualla kuin itse komponentissa.

Lisätietoa testauspinnasta, katso [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Huolenaiheiden rajapinnat {#concern-interfaces}

Huolenaiheiden rajapinnat antavat `ElementComposite`-aliluokalle ominaisuuksia ilman, että sinun tarvitsee kirjoittaa toteutusta itse. Rajapinnat välittävät kutsuja taustalla olevalle elementille. Toteuta ne, joita komponentin tulisi tukea, parametrisoituna aliluokan tyyppillä, jotta ketjukutsu palauttaa komponentin:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Ei toteutusta tarvita.
}

MyBadge badge = new MyBadge()
    .setText("New")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Yllä olevat kolme rajapintaa kattavat kaiken, mitä `MyBadge` tarvitsee ilman luokan sisällä mitään menetelmäkehoituksia. `HasText` altistaa `setText()`-kutsun ja kirjoittaa elementin tekstisisältöön. `HasClassName` altistaa `addClassName()`, joka mahdollistaa tarran kohdentamisen CSS:stä. `HasStyle` altistaa `setStyle()`-kutsun inline-tyylille.

Koko saatavilla oleva rajapintojen joukko ja mitä kukin tarjoaa, katso [Huolenaiheiden rajapinnat](./component-fundamentals#concern-interfaces) Ymmärrys komponentit -artikkelissa. Jos oletusarvoinen välitys ei vastaa sitä, mitä kääritty elementti altistaa, voit ylikirjoittaa metodin aliluokassa.

## Tapahtumat {#events}

### Tapahtuman rekisteröinti {#event-registration}

Web-komponentit lähettävät DOM-tapahtumia, kun jotain tapahtuu selaimessa. Jotta voit reagoida Javasta, kuuntele näitä tapahtumia `addEventListener()`-kutsulla. Komponentin lähettämien tapahtumien joukko vaihtelee, joten tarkista komponentin omista asiakirjoista saatavilla olevat nimet ja kuormitukset.

`ElementComposite` tukee debouncingia, throttlingia, suodattamista ja mukautettuja tapahtumatietoja rekisteröidyissä kuuntelijoissa.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-metodia:

```java
// Esimerkki: Lisäämällä klikkitapahtuman kuuntelija
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkitapahtumaa
});
```

:::info
`ElementComposite` hyväksyy vain takaeventtien luokat, jotka on merkitty `@EventName`-annotaatiolla, toisin kuin `Element`, joka hyväksyy minkä tahansa merkitsevät tapahtuman nimen.
:::

### Sisäänrakennetut tapahtumaluokat {#built-in-event-classes}

`ElementClickEvent` on ainoa sisäänrakennettu tapahtumaluokka, joka tulee `ElementComposite`-luokan mukana. Se esittelee hiiren napsautustapahtumia taustalla olevalle elementille tyypitetyillä pääsytavoilla koordinaateissa (`getClientX()`, `getClientY()`), painetietojen (`getButton()`) ja modifier-näppäimien (`isCtrlKey()`, `isShiftKey()` jne.).

Jotta klikkaushallinta on saatavilla julkisessa rajapinnassa aliluokassa, toteuta `HasElementClickListener<T>`-huolenaiheiden rajapinta. Se tarjoaa oletusarvoiset `onClick()`- ja `addClickListener()`-metodit, jotka delegoivat suojatun `addEventListener()`-metodin.

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

### Tapahtumakuormitukset {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaasta Java-koodillesi. Pääset tähän tietoon `getData()`-kutsulla raaka tapahtumatieto tai käytä tyypitettyjä metodeja, kun ne ovat saatavilla sisäänrakennetuissa tapahtumaluokissa. Katso [Tapahtumaopas](../building-ui/events) lisätietoja tehokkaasta kuormituksen käsittelystä.

### Mukautetut tapahtumaluokat {#custom-event-classes}

Määritä mukautetut tapahtumaluokat `@EventName` ja `@EventOptions` -annotaatioilla, jotta voit vangita asiakaspuolen tietoja tyypitettyyn Java-tapahtumaan. Käytä tätä, kun Java-käsittelijä tarvitsee arvoja selaimelta.

`@EventName` sitoo Java-luokan tapahtumaan, jonka komponentti lähettää selaimessa, joten luokka, joka on merkitty `@EventName("change")`, laukaisee, kun taustalla oleva elementti lähettää `change`-tapahtuman. `@EventOptions` hallitsee, mitä kuljetetaan takaisin tämän tapahtuman mukana. Jokainen sen sisällä oleva `@EventData` yhdistää avaimen JavaScript-ilmaisuun, joka arvioidaan DOM-tapahtuman merkintä. Tulos on käytettävissä Java-tapahtumaluokassa `getData().get(key)`-kutsulla.

Tuotearvostelu muodostuma käyttää tätä mallia [`wa-rating`](https://webawesome.com/docs/components/rating/). Mukautettu `ChangeEvent` kuljettaa arvioarvoa tyypitettynä `double`:na, ja kuuntelija käyttää sitä mahdollistaa lähetyspainikkeen:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Tapahtumavaihtoehdot {#event-options}

`ElementEventOptions` konfiguroi tapahtuman kuormitusta, debouncingin tai throttlingin ajoitusta, suodatinilmaisuja ja ennakkosuorituskoodeja. Alla oleva koodi näyttää vaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettuja tietoja asiakkaasta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Suorita JavaScript ennen tapahtuman laukaisua
  .setCode("component.classList.add('processing');")

  // Laadi vain, jos ehdot täyttyvät
  .setFilter("component.value.length >= 2")

  // Viivytetään suoritus, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Käytä näitä vaihtoehtoja rekisteröidessä kuuntelijaa mukautetulle tapahtumaluokalle
// (katso kohta Mukautetut tapahtumaluokat, miten määritellään yksi):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` altistaa vain luokkamuotoisen `addEventListener(Class, listener, options)`. Käytä sitä tapahtumaluokan kanssa, joka on merkitty `@EventName`. Rekisteröidäksesi suoraan merkittyyn tapahtuman nimeen, kutsu `getElement().addEventListener("input", listener, options)`.
:::

#### Suorituskyvyn hallinta {#performance-control}

**Debouncing** viivyttää suoritusta, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Ota 300ms viimeisestä tapahtumasta
```

Saatavilla olevat debounce-vaiheet:

- `LEADING`: Laukaise heti, ja odota sitten
- `TRAILING`: Odota hiljaista aikaa, ja laukaise (oletus)
- `BOTH`: Laukaise heti ja hiljaisena aikana

**Throttling** rajoittaa suorituksen tiheyden:

```java
options.setThrottle(100); // Laukaise enintään kerran 100ms:ssa
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Slotit ovat paikkoja web-komponentissa, jotka käyttäjät täyttävät sisällöllä. Web-komponentti määrittelee slotit mallissaan `<slot>` tai `<slot name="...">`, ja kääre altistaa menetelmät, jotka sijoittavat Java-komponentteja näihin slotteihin.

Lisätäksesi sisältöä slotteihin, laajennetaan `ElementCompositeContainer`-luokkaa `ElementComposite`:n sijaan. Säiliö pitää samat ominaisuus- ja attribuutiomekanismit plus menetelmät, jotka tarvitaan lasten lisäämiseen. Lapsia, jotka on lisätty `add()`-menetelmän kautta, menee oletus-slottiin. Lapsia, jotka lisätään `getElement().add(slotName, components)`-menetelmän kautta, menee nimettyyn slottiin.

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

Alla oleva demo näyttää kaksi hinnoittelukorttia, jotka on rakennettu [`wa-card`](https://webawesome.com/docs/components/card/)-komponentilla, täyttäen `header`, oletus- ja `footer`-slotit Javasta:

<ComponentDemo
path='/webforj/webawesomecard'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Slot-sisällön tarkastelu {#inspecting-slot-contents}

Taustalla oleva `Element` (johon pääsee `getElement()`-kutsulla) tarjoaa menetelmiä, joilla voimme lukea takaisin, mitä tällä hetkellä on nimetty pitkään:

- **`findComponentSlot()`**: etsii kaikki slotit tietystä komponentista ja palauttaa sen nimett kaikkein slotille, tai tyhjän merkkijonon, jos komponentti ei ole missään slotissa.
- **`getComponentsInSlot()`**: palauttaa komponenttilistan, joka on määritetty tiettyyn slotiin. Valinnaisesti ottamatta luokkatyyppiä suodattaa tuloksia.
- **`getFirstComponentInSlot()`**: palauttaa ensimmäisen komponentin, joka on määritetty slottiin. Valinnaisesti ottamalla luokkatyyppi suodatus.
