---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: b8099816ab51d246d3a69c2ca8bd9108
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka käärii mukautetun HTML-elementin tai [web-komponentin](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Se sitoo Java-luokkasi taustalla olevaan `Element`-elementtiin ja antaa sinun työskennellä sen ominaisuuksien, attribuuttien ja tapahtumien kanssa Java-koodissa. Käytä sitä integroitaessa web-komponentteja webforJ-sovellukseen.

:::tip Milloin käyttää `ElementComposite`
Käytä `ElementComposite`-luokkaa, kun käärit kolmannen osapuolen web-komponenttia, jota webforJ ei jo tarjoa. Jos jokin sisäänrakennettu webforJ-komponentti kattaa käyttötapauksen (`TextField`, `ColorField`, `Button` jne.), käytä sitä sen sijaan. Yksittäiseen DOM-työhön, jota ei tarvitse käyttää uudelleen, voit käyttää suoraan `Element`-luokkaa ilman käärettä.
:::

Tässä oppaassa näytetään, kuinka toteuttaa [Shoelace relative-time web component](https://shoelace.style/components/relative-time) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Luokan annotaatiot {#class-annotations}

Kolme annotaatiota esiintyy yleisesti `ElementComposite`-aliluokan alussa: `@NodeName` määrittelee HTML-tagin, jota komponentti käärii, ja `@JavaScript` sekä `@StyleSheet` lataavat asiakaspuolen varat, joita taustalla oleva web-komponentti tarvitsee. `@NodeName` on pakollinen ja erityinen `ElementComposite`:lle. `@JavaScript` ja `@StyleSheet` ovat yleisiä webforJ-varaannotaatiota ja toimivat kaikilla luokilla, mukaan lukien näkymät, komponentit tai `App`-luokka.

### `@NodeName` {#nodename}

`@NodeName`-annotaatio määrittelee HTML-tagin, jota komponentti käärii. webforJ käyttää tätä nimeä, kun se luo taustalla olevan elementin DOM:ssa.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Tagin nimen on vastattava mukautettua elementtiä, joka on rekisteröity asiakaspuolelle. Ilman tätä annotaatiota kehys ei voi määrittää, mikä elementti luodaan.

Aliluokassa `getNodeName()` lukee takaisin määritellyn tagin, ja `getElement()` palauttaa taustalla olevan `Element`:in, joten voit kutsua DOM-tason metodeja suoraan sen avulla.

### `@JavaScript` {#javascript}

`@JavaScript`-annotaatio lataa skriptin, joka määrittää tai rekisteröi taustalla olevan web-komponentin. Aseta se luokkaan, jotta skripti latautuu vain, kun komponenttia käytetään.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Useita `@JavaScript`-annotaatioita on sallittu, ja webforJ poistetaan automaattisesti päällekkäiset ladaukset. Sama skripti ei lataudu kahdesti, jos useat komponentit riippuvat siitä.

Katso [JavaScript-tiedostojen tuonti](../managing-resources/importing-assets#importing-javascript-files) täydellistä vaihtoehtojen joukkoa varten, mukaan lukien `top`, `attributes` ja latausajankohdat.

### `@StyleSheet` {#stylesheet}

`@StyleSheet`-annotaatio lataa CSS-tiedoston, jota komponentti tarvitsee. Se on hyödyllinen kolmansien osapuolten komponenteille, jotka tarjoavat erillisen tyylitiedoston, tai komponenttikohtaisen muotoilun pakkaamiseksi kääreen joukkoon.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Paikallisia pakattuja varoja varten käytä `ws://`-etuliitettä viitataksesi tiedostoihin `resources/static`-kansiossa:

```java
@StyleSheet("ws://components/relative-time.css")
```

Katso [CSS-tiedostojen tuonti](../managing-resources/importing-assets#importing-css-files) täydellistä vaihtoehtojen joukkoa varten.

## Ominaisuus- ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit kuvaavat web-komponentin tilaa, ja ne pitävät tyypillisesti tietoa tai konfigurointia. `ElementComposite` altistaa molemmat `PropertyDescriptor`-luokan kautta.

Kaksi tehdasmetodia `PropertyDescriptor`-luokassa tuottaa kuvaston itselleen, yksi per sidontakohde:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` sitoo JavaScript-ominaisuuden DOM-solmulle. `PropertyDescriptor.attribute()` sitoo HTML-attribuutin. Ensimmäinen argumentti on nimi, jonka web-komponentti odottaa. Toinen on oletusarvo, joka myös määrittää kuvaston Java-tyypin.

Määritä kuvasto komponentin yksityisenä kenttänä, ja lue ja kirjoita sen kautta `set(PropertyDescriptor<V> property, V value)` ja `get(PropertyDescriptor<V> property)` -kutsuilla.

:::info
Ominaisuudet ovat sisäistä tilaa DOM-solmussa eivätkä näy merkinnässä. Attribuutit ovat HTML-merkintää, joka on näkyvissä ulkoisille skripteille ja CSS:lle.
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

Yllä olevat kutsut käyttävät `set()`-metodia suoraan yksinkertaisessa muodossa. Käytännössä `set()` ja `get()` ovat `protected`-metodeja `ElementComposite`-luokassa. Ne ovat primitiivinen kerros, joka synkronoi Java-arvoja taustalla olevan elementin kanssa, ei julkinen API, jota käyttäjät kutsuvat. Tavoite on pitää `PropertyDescriptor` yksityisenä ja kirjoittaa julkiset `setX()` ja `getX()` -metodit, jotka delegoivat primitiiveille.

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

Yksi `set(descriptor, value)`-kutsu tekee kolme asiaa kerralla. Se välittää arvon asiakaspuolelle `setProperty()`-metodin kautta ominaisuuksille tai `setAttribute()`-metodille attribuuteille. Se tallentaa arvon paikalliseen palvelinpuolen välimuistiin, yksi kartta per komponentti-instanssi. Ja se tallentaa ajoitusluokan arvon viereen, jotta myöhemmät `get()`-kutsut tietävät, miten deserialisoida.

Se paikallinen välimuisti on syy siihen, miksi `get()` voi olla halpa oletusarvoisesti. `get(descriptor)` palauttaa välimuistissa olevan arvon palvelinpuolen tallennuksesta ilman verkkokutsua, koska jokainen `set()` pitää välimuistin synkronoituna asiakkaan kanssa. Valinnainen `boolean` toinen argumentti määrittelee, ohitetaanko välimuisti ja luetaanko selainpuolelta sen sijaan.

```java
String cached = get(heading);            // lukee palvelinpuolen välimuistista
String live = get(heading, true);        // pakottaa lukemaan selaimelta
```

Aseta `fromClient` todeksi, kun arvo voi muuttua asiakkaalla ilman palvelimen tietoa, kuten kirjoitettu `<input>`-arvo. Palvelinohjatuissa ominaisuuksissa oletus välttää matkustamista.

Valinnainen kolmas argumentti on `java.lang.reflect.Type` ja se määrittää, miten tulos deserialisoidaan. webforJ ratkaisee tyypin tässä järjestyksessä: suorana `Type`-argumentti, jos se on annettu, sitten aikarajan tyyppi, joka on tallennettu aiemmasta `set()`-kutsusta samalle kuvastolle, ja viimeisenä `Object.class`. Käytännössä aikarajan tyyppi, jota tallennettiin aiemman `set()`-kutsun aikana, riittää, joten kolmas argumentti voidaan yleensä jättää pois. Se on tarpeellinen silloin, kun tallennettu luokka menettää informaatiota, jota deserialisoija tarvitsee, kuten parametrisoitu tyyppi kuten `List<String>`, jonka ajonaikainen luokka on vain `ArrayList`.

Alla olevassa demon traadissa lisätään ominaisuudet relative-time tietojen perusteella web-komponentin asiakirjoista ja altistetaan ne getterien ja setterien kautta. Jokainen rivi aktiviteettilistalla käyttää erilaisia `format`- ja `numeric`-arvoja osoittaakseen, miten sama komponentti renderöityy eri kokoonpanoissa.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Ominaisuudet vs. attribuutit {#properties-versus-attributes}

Vaikka `PropertyDescriptor.property()` ja `PropertyDescriptor.attribute()` vaikuttavat vaihdettavilta, ne kohdistuvat eri osiin taustalla olevaa elementtiä. Väärän valinnan tekeminen johtaa siihen, että arvot hiljaa epäonnistuvat.

Ominaisuudet ovat JavaScript-objektin ominaisuuksia DOM-solmulla. Ne voivat pitää mitä tahansa tyyppiä, mukaan lukien merkkijonot, booleanit, numerot, objektit ja taulukot, ja ne edustavat elementin nykyistä ajonaikaista tilaa. Ominaisuuden asettaminen on suora JavaScript-muuttuja.

Attribuutit ovat HTML-merkintää. Ne löytyvät elementin avaus-tägistä, ovat aina merkkijonoja ja edustavat elementin alkuperäistä kokoonpanoa. Attribuutin asettaminen laukaisee DOM:n muutoksen ja merkkijonon muunnoksen.

Joissakin tapauksissa molemmat pysyvät synkronoituna. Toisissa ne poikkeavat. `<input>`-elementin `value` on klassinen esimerkki: `value`-attribuutti on alkuperäinen arvo, kun taas `value`-ominaisuus on nykyinen arvo, jonka käyttäjä on kirjoittanut. Attributesin lukeminen käyttäjän kirjoittamisen jälkeen palauttaa alkuperäisen merkintä, mutta ominaisuuden lukeminen palauttaa kentän nykyiset sisällöt.

Käytä **ominaisuuksia** seuraaville:

- **Usein muuttuva ajonaikainen tila**: laskurit, nykyiset valinnat, kirjoitetut arvot
- **Eri tyyppiset**: booleanit, numerot, objektit, taulukot
- **Suorituskykyherkät päivitykset**: ominaisuudet ohittavat merkkijonon muunnoksen, jota attribuutit vaativat

Käytä **attribuutteja** seuraaville:

- **Alkuperäinen kokoonpano**: asetukset, joita komponentti lukee vain kerran, kun se yhdistetään
- **CSS-valitsimet**: arvot, joita haluat kohdistaa valitsimilla, kuten `[disabled]` tai `[variant="danger"]`
- **Esteettömyysniksit**: `aria-label`, `role` ja muut ARIA-attribuutit
- **Merkkijonon kaltaiset asetukset, jotka harvoin muuttuvat**

Kun käännät kolmannen osapuolen web-komponenttia, tarkista komponentin dokumentaatio varmistaaksesi, mitä nimeä vastaavat ominaisuus ja mikä attribuutti. Käyttämällä `PropertyDescriptor.attribute()`:ta johonkin, jonka komponentti altistaa vain ominaisuutena, ei toimi, ja sama pätee päinvastoin. Komponentti hiljaa sivuuttaa arvon.

### Ominaisuuksien tyypit {#typing-properties}

Kuvasto on parametrisoitu sen arvon Java-tyypillä. Täydellinen ilmoitussyntaksi on:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>`-geneerinen parametri määrittelee arvon tyypin. Oletusarvon ajonaikainen tyyppi fixes `T`, joten geneeristä argumenttia harvoin tarvitsee määrittää erikseen. webforJ käyttää `T`:tä sarjoittaakseen ja desarjoittaakseen arvoja asiakaspuolen kanssa kommunikoidessaan.

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

Sarjoitus tapahtuu automaattisesti primitiiville, niiden pakattuille vastineille ja `String`:ille. Monimutkaisille tyypeille arvo sarjoitetaan JSON-muotoon ennen sen asettamista asiakkaalle.

### Arvojen validoiminen {#validating-values}

Varmista arvot setterissä ennen `set()`-kutsun tekemistä. Setter on luonnollinen valvontakohta, koska jokainen muutos kulkee sen kautta.

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

Nullable-viittauksille käytä `Objects.requireNonNull()`, jotta epäonnistuminen näkyy rajapinnassa sen sijaan, että se tapahtuisi myöhemmin renderointiputkessa.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Vältä validointia `get()`-metodissa. Lukemisen tulisi pysyä halpana ja johdonmukaisena.

### Enum-tyyppiset ominaisuudet {#enum-style-properties}

Useimmat web-komponentit odottavat alhaalla tai kebab-käytä merkkijonon arvoja enum-tyylisille ominaisuuksille (`theme="primary"`, `expanse="xs"`). webforJ käyttää Gsonia sarjoittamiseen enum-tyypeissä, mutta Gsonin oletusesitys on vakionimi isoina kirjaimina. Merkitse jokainen vakio annotaatiolla `@SerializedName`, jotta sarjoitettu arvo vastaa sitä, mitä web-komponentti odottaa.

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

Määritä kuvasto enum-tyypillä ja käytä enumia suoraan setterissä ja getterissä.

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

Tämä on sama malli, jota webforJ:n sisäänrakennetut komponentit käyttävät `Theme`, `Expanse` ja samanlaisista enumsista. Julkinen Java-API pysyy tyypin turvallisena, ja arvo, jonka web-komponentti vastaanottaa, on merkkijono `@SerializedName`:stä.

### Ominaisuuksien testaaminen {#testing-properties}

`PropertyDescriptorTester` validoi, että jokainen `PropertyDescriptor` komponentissa on kytketty oikein. Se skannaa luokan kuvastokentät, kutsuu kutakin setteria oletusarvoilla ja vertaa tulosta siihen, mitä getter palauttaa. Testerillä on mahdollista löytää integraatiovirheitä ennen kuin ne saavuttavat toimivan sovelluksen: setter, joka kirjoittaa väärään kuvastoon, getter, joka lukee eri ominaisuuden, oletusarvo, joka ei pyöri takaisin, tai puuttuva pääsy määriteltyyn kuvastoon.

Alustava testi komponentille näyttää tältä:

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

#### Ominaisuuksien taiottaminen {#excluding-properties}

Jotkut kuvastot eivät noudata standardimuuttujia ja setter-konventioita, tai ne saattavat riippua ulkoisesta tilasta, jota testi ei voi tyydyttää. Merkitse ne `@PropertyExclude`-annotaatiolla, jotta ne ohitetaan.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Mukautetut getter- ja setter-nimet {#custom-getter-and-setter-names}

Jos kuvasto käyttää ei-standardeja käyttöliittymän nimiä, ilmoita ne `@PropertyMethods`-annotaatiolla.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target`-parametri hyväksyy luokan, kun käyttöliittymät sijaitsevat muualla kuin itse komponentissa.

Lisätietoja testauksen pinnasta, katso [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Huolenaiheiden rajapinnat {#concern-interfaces}

Huolenaiheiden rajapinnat antavat `ElementComposite`-aliluokalle komponentin ominaisuuksia ilman, että sinun tarvitsee kirjoittaa toteutusta itse. Rajapinnat välittävät kutsuja taustalla olevalle elementille. Toteuta ne, joita komponentin tulisi tukea, parametrisoituna aliluokan tyypillä, jotta ketjutetut kutsut palauttavat komponentin:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // Toteutusta ei tarvita.
}

MyBadge badge = new MyBadge()
    .setText("New")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Yllä olevat kolme rajapintaa kattavat kaiken, mitä `MyBadge` tarvitsee ilman mitään menetelmärakenteita luokassa. `HasText` altistaa `setText()`-metodin ja kirjoittaa elementin tekstisisältöön. `HasClassName` altistaa `addClassName()`, joka mahdollistaa, että merkki voidaan tavoittaa CSS:stä. `HasStyle` altistaa `setStyle()` inline-tyylitykselle.

Kattavan luettelon saatavilla olevista rajapinnoista ja siitä, mitä kukin tarjoaa, katso [Huolenaiheiden rajapinnat](./component-fundamentals#concern-interfaces) Ymmärtämällä komponentit -artikkelista. Jos oletuslähetys ei vastaa mitä kääritty elementti tarjoaa, ylikirjoita menetelmä alaluokassa.

## Tapahtumat {#events}

### Tapahtumien rekisteröinti {#event-registration}

Web-komponentit lähettävät DOM-tapahtumia, kun selaimessa tapahtuu jotain. Reagoidaksesi Java-koodista, kuuntele näitä tapahtumia `addEventListener()`-metodilla. Komponentin lähettämien tapahtumien joukko vaihtelee, joten tarkista komponentin omasta dokumentaatiosta nimet ja käytettävissä olevat kuormitukset.

`ElementComposite` tukee debouncetta, throttlingia, suodattamista ja mukautettua tapahtumatietoa rekisteröidyillä kuuntelijoilla.

Rekisteröi tapahtumakuuntelijat käyttäen `addEventListener()`-metodia:

```java
// Esimerkki: klikkaustapahtuman kuuntelijan lisääminen
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite` hyväksyy vain tapahtumaluokkia, joita on merkitty `@EventName`, toisin kuin `Element`, joka hyväksyy minkä tahansa merkkijonotapahtuman nimen.
:::

### Sisäänrakennetut tapahtumaluokat {#built-in-event-classes}

`ElementClickEvent` on ainoa sisäänrakennettu tapahtumaluokka, jonka `ElementComposite` tarjoaa. Se tuo esille hiiren klikkaustapahtumat taustalla olevalta elementiltä ja sisältää tyypitetyt käyttöliittymät koordinaateille (`getClientX()`, `getClientY()`), painetietoa (`getButton()`) ja muokkausnäppäimiä (`isCtrlKey()`, `isShiftKey()` jne.).

Jotta klikkauskäsittely saadaan alaluokan julkiselle API:lle, toteuta `HasElementClickListener<T>`-huolenaihe. Se tarjoaa oletusmenetelmiä `onClick()` ja `addClickListener()`, jotka delegoivat suojattuun `addEventListener()`-metodiin.

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

Muille tapahtumille, joita taustalla oleva web-komponentti lähettää, määrittele mukautettu tapahtumaluokka. Katso [Mukautetut tapahtumaluokat](#custom-event-classes).

### Tapahtumakuormitukset {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodiin. Pääset tähän tietoon `getData()`-metodin avulla raakadatasta tai käytä tyypitettyjä metodeja, kun ne ovat saatavilla sisäänrakennetuissa tapahtumaluokissa. Katso [Tapahtumaopas](../building-ui/events) tehokasta kuormituksen käsittelyä varten.

### Mukautetut tapahtumaluokat {#custom-event-classes}

Määrittele mukautettu tapahtumaluokka `@EventName` ja `@EventOptions` -annotaatioiden avulla, jotta voit kaapata asiakaspuolen tietoa tyypitettyyn Java-tapahtumaan. Käytä tätä, kun Java-käsittelijasi tarvitsee arvoja selaimesta.

`@EventName` sitoo Java-luokan tapahtumaan, jonka komponentti lähettää selaimessa, jotta luokka, joka on merkitty `@EventName("sl-change")`, laukaisee aina, kun taustalla oleva elementti lähettää `sl-change`. `@EventOptions` ohjaa, mitä tietoja kulkee takaisin tämän tapahtuman mukana. Jokaisen `@EventData`-huonetilassa yhdistää avaimen JavaScript-lausekkeeseen, joka arvioidaan DOM-tapahtuman osalta. Tulos on käytettävissä Java-tapahtumaluokassa `getData().get(key)`.

Tuotearvostelulomake alla käyttää tätä mallia [`sl-rating`](https://shoelace.style/components/rating). Mukautettu `ChangeEvent` kuljettaa arvioinnin arvon tyypitettynä `double`:na, ja kuuntelija käyttää sitä aktivoinnin mahdollistamiseksi:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Tapahtumavaihtoehdot {#event-options}

`ElementEventOptions` konfiguroi tapahtumakuormaa, debounce tai throttle-aikoja, suodatusilmaisuja ja ennakkokoodia. Alla oleva koodinpätkä näyttää vaihtoehdot:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettua tietoa asiakkaalta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Suorita JavaScript ennen tapahtuman laukaisemista
  .setCode("component.classList.add('processing');")

  // Laadi vain, jos olosuhteet ovat täyttyneet
  .setFilter("component.value.length >= 2")

  // Viivytä suoritusta, kunnes käyttäjä lopettaa kirjoittamisen (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Käytä näitä vaihtoehtoja, kun rekisteröit kuuntelijan mukautetulle tapahtumaluokalle
// (katso Mukautetut tapahtumaluokat -osasta, kuinka yksi määritellään):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` altistaa vain luokkaperustaisen muodon `addEventListener(Class, listener, options)`. Käytä sitä tapahtumaluokassa, jota on merkitty `@EventName`. Rekisteröidäksesi suoraan merkkijonotapahtuman nimeen, kutsu `getElement().addEventListener("input", listener, options)`.
:::

#### Suorituskyvyn hallinta {#performance-control}

**Debouncing** viivyttää suoritusta, kunnes aktiviteetti lopetetaan:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300ms viimeisen tapahtuman jälkeen
```

Saatavilla olevat debounci-vaiheet:

- `LEADING`: Laukaise heti, sitten odota
- `TRAILING`: Odota hiljaista aikaa, laukaiseminen (oletus)
- `BOTH`: Laukaise heti ja hiljaisen jakson jälkeen

**Throttling** rajoittaa suorituksen tiheyttä:

```java
options.setThrottle(100); // Laukaisee korkeintaan kerran 100ms
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Slotit ovat paikkoja web-komponentin sisällä, joihin käyttäjät täyttävät sisällön. Web-komponentti ilmoittaa slotit mallissaan käyttämällä `<slot>` tai `<slot name="...">`, ja kääre altistaa menetelmät, jotka asettavat Java-komponentit näihin slotteihin.

Lisätäksesi sisältöä slotteihin, laajenna `ElementCompositeContainer`-luokkaa `ElementComposite`-luokan sijaan. Kontti sisältää samat ominaisuus- ja attribuuttimekanismit plus menetelmät lapsien lisäämiseksi. Lapsia, jotka on lisätty `add()`-menetelmällä, menee oletusslotille. Slotilla nimen kanssa lisäämällä `getElement().add(slotName, components)` tallennat komponentit nimettyyn slottiin.

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

Alla oleva demo esittelee kaksi hinnoittelukorttia, jotka on rakennettu [`sl-card`](https://shoelace.style/components/card):lla, täyttäen `header`, oletus- ja `footer`-slotteja Javasta:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Slot-sisällön tarkastelu {#inspecting-slot-contents}

Taustalla oleva `Element` (johon pääsee käsiksi `getElement()`-metodilla) tarjoaa menetelmiä nykyisen slot-sisällön lukemiseen:

- **`findComponentSlot()`**: etsii kaikista sloteista tietyn komponentin ja palauttaa sen nimen, joka sisältää sen, tai tyhjän merkkijonon, jos komponenttia ei ole missään slotissa.
- **`getComponentsInSlot()`**: palauttaa listan komponentista, jotka on liitetty tiettyyn slottiin. Valinnaisesti kultakin saadaan suodatettua tuloksia luokkatyypin avulla.
- **`getFirstComponentInSlot()`**: palauttaa ensimmäisen komponentin, joka on liitetty slottiin. Valinnaisesti kultakin saadaan suodatettua tuloksia luokkatyypin avulla.
