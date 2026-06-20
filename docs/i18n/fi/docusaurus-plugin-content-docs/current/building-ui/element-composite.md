---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

`ElementComposite`-luokka käärii mukautetun HTML-elementin tai [verkkokomponentin](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Se sitoo Java-luokkasi taustalla olevaan `Element`-elementtiin ja antaa sinun työskennellä sen ominaisuuksien, attribuuttien ja tapahtumien kanssa Java-koodissa. Käytä tätä verkkokomponenttien integroimiseen webforJ-sovellukseen.

:::tip Milloin käyttää `ElementComposite`
Käytä `ElementComposite`-luokkaa, kun käärit kolmannen osapuolen verkkokomponentin, jota webforJ ei vielä tarjoa. Jos sisäänrakennettu webforJ-komponentti kattaa käyttötapauksen (kuten `TextField`, `ColorField`, `Button` jne.), käytä sitä sen sijaan. Yksittäiseen DOM-työhön, jota ei tarvitse käyttää uudelleen, voit käyttää `Element`-luokkaa suoraan ilman käärettä.
:::

Tämä opas osoittaa, kuinka toteuttaa [Shoelace relative-time verkkokomponentti](https://shoelace.style/components/relative-time) käyttäen `ElementComposite`-luokkaa.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Luokan annotaatiot {#class-annotations}

Kolme annotaatiota esiintyy tavallisesti `ElementComposite`-aliluokan alussa: `@NodeName` ilmoittaa, mikä HTML-tagi komponentti käärii, ja `@JavaScript` sekä `@StyleSheet` lataavat kaikki asiakaspuolen resurssit, joita taustalla oleva verkkokomponentti tarvitsee. `@NodeName` on pakollinen ja spesifinen `ElementComposite`:lle. `@JavaScript` ja `@StyleSheet` ovat yleisiä webforJ-resurssiannotaatioita, jotka toimivat missä tahansa luokassa, mukaan lukien näkymät, komponentit tai `App`-luokka.

### `@NodeName` {#nodename}

`@NodeName`-annotaatio määrittelee HTML-tagin, jota komponentti käärii. webforJ käyttää tätä nimeä luodakseen taustalla olevan elementin DOM:iin.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Tagin nimen on vastattava asiakaspuolella rekisteröityä mukautettua elementtiä. Ilman tätä annotaatiota kehys ei voi määrittää, mikä elementti luodaan.

Aliluokassa `getNodeName()` lukee ilmoitetun tagin, ja `getElement()` palauttaa taustalla olevan `Element`:in, jotta voit kutsua suoraan sen DOM-tason menetelmiä.

### `@JavaScript` {#javascript}

`@JavaScript`-annotaatio lataa skriptin, joka määrittää tai rekisteröi taustalla olevan verkkokomponentin. Aseta se luokalle, jotta skripti ladataan vain, kun komponenttia käytetään.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Useat `@JavaScript`-annotaatiot ovat sallittuja, ja webforJ poistaa automaattisesti päällekkäiset lataukset. Sama skripti ei lataudu kahdesti, jos useat komponentit riippuvat siitä.

Katso [JavaScript-tiedostojen tuonti](../managing-resources/importing-assets#importing-javascript-files) täysiä vaihtoehtoja varten, mukaan lukien `top`, `attributes` ja latausaikataulu.

### `@StyleSheet` {#stylesheet}

`@StyleSheet`-annotaatio lataa CSS-tiedoston, johon komponentti perustuu. Se on hyödyllinen kolmannen osapuolen komponenttien kanssa, jotka toimittavat erillisen tyylitiedoston, tai komponentti-erityisen tyylin yhdistämiseksi kääreeseen.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Paikallisesti pakattuja resursseja varten käytä `ws://`-etuliitettä viitataksesi tiedostoihin `resources/static`-hakemistossa:

```java
@StyleSheet("ws://components/relative-time.css")
```

Katso [CSS-tiedostojen tuonti](../managing-resources/importing-assets#importing-css-files) täysiä vaihtoehtoja varten.

## Ominaisuudet ja attribuuttikuvastot {#property-and-attribute-descriptors}

Ominaisuudet ja attribuutit edustavat verkkokomponentin tilaa, joka tyypillisesti pitää tietoja tai konfiguraatiota. `ElementComposite` altistaa molemmat käytettäväksi `PropertyDescriptor`-luokan kautta.

Kaksi tehdastehdasta `PropertyDescriptor`:ssä tuottaa itse kuvaston, yksi kutakin sidontakohdetta varten:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` sitoo DOM-solmun JavaScript-ominaisuuteen. `PropertyDescriptor.attribute()` sitoo HTML-attribuuttiin. Ensimmäinen argumentti on nimi, jonka verkkokomponentti odottaa. Toinen on oletusarvo, joka myös kiinnittää kuvaston Java-tyypin.

Määritä kuvasto komponentin yksityisenä kenttänä, ja lue ja kirjoita sen kautta käyttäen `set(PropertyDescriptor<V> property, V value)` ja `get(PropertyDescriptor<V> property)`.

:::info
Ominaisuudet ovat DOM-solmun sisäistä tilaa eivätkä heijastu merkkijonoon. Attribuutit ovat HTML-merkintöjä, jotka ovat näkyviä ulkoisille skripteille ja CSS:lle.
:::

```java
// Esimerkki "title"-ominaisuudesta ElementComposite-luokassa
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Esimerkki "value"-attribuutista ElementComposite-luokassa
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

Yllä olevat kutsut käyttävät `set()`-menetelmää suoraan esittääkseen primitiivisen muodon. Käytännössä `set()` ja `get()` ovat `protected`-menetelmiä `ElementComposite`-luokassa. Ne ovat primitiivinen kerros, joka synkronoi Java-arvot taustalla olevan elementin kanssa, eivät julkista API:ta, jota kuluttajat kutsuvat. Tarkoituksena on pitää `PropertyDescriptor` yksityisenä ja kirjoittaa julkiset `setX()` ja `getX()` -menetelmät, jotka delegoivat primitiiville.

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

Yksi kutsu `set(descriptor, value)` tekee kolme asiaa kerralla. Se työntää arvon asiakkaalle `setProperty()`-menetelmää käyttäen ominaisuuksille tai `setAttribute()`-menetelmää käyttäen attribuuteille. Se tallentaa arvon paikalliseen palvelinpuolen välimuistiin, yksi kartta per komponentti-instanssi. Ja se tallentaa ajonaikaisen tyypin arvon yhteydessä, jotta myöhemmät `get()`-kutsut tietävät, miten deserialisoida.

Tämä paikallinen välimuisti on syy siihen, että `get()` voi olla edullinen oletuksena. `get(descriptor)` palauttaa tallennetun arvon palvelinpuolen tallennuksesta ilman verkko-kutsuja, koska jokainen `set()` pitää välimuistin synkronoituna asiakkaan kanssa. Valinnainen `boolean` toinen argumentti hallitsee, ohitetaanko välimuisti ja luetaanko selaimelta sen sijaan.

```java
String cached = get(heading);            // lukee palvelinpuolen välimuistista
String live = get(heading, true);        // pakottaa lukemaan selaimesta
```

Aseta `fromClient` todeksi, kun arvo voi muuttua asiakkaalla ilman palvelimen tietämystä, kuten kirjoitettuna `<input>`-kenttään. Palvelimelta ohjattaville ominaisuuksille oletus välttää ylimääräisiä pyyntejä.

Valinnainen kolmas argumentti on `java.lang.reflect.Type` ja se hallitsee sitä, miten tulos deserialisoidaan. webforJ ratkaisee tyypin seuraavassa järjestyksessä: eksplisiittinen `Type`-argumentti, jos se on annettu, sitten ajonaikainen tyyppi, joka on rekisteröity aikaisemmalla `set()`-kutsulla samalla kuvastolla, sitten `Object.class`. Käytännössä aiemman `set()`-kutsun rekisteröity tyyppi riittää, joten kolmas argumentti voidaan tyypillisesti jättää pois. Sitä tarvitaan, kun rekisteröity luokka menettää tietoa, jota deserialisoija tarvitsee, kuten parametrisoitu tyyppi, kuten `List<String>`, jonka ajonaikainen luokka on vain `ArrayList`.

Alla oleva demo lisää ominaisuuksia relative-time -perusteella verkkokomponentin asiakirjojen mukaan ja altistaa ne getterien ja setterien kautta. Jokainen rivi aktiviteettisyötteessä käyttää erilaisia `format`- ja `numeric`-arvoja osoittaakseen, kuinka sama komponentti renderöityy eri määritysten alla.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Ominaisuudet vs. attribuutit {#properties-versus-attributes}

Vaikka `PropertyDescriptor.property()` ja `PropertyDescriptor.attribute()` näyttävät olevan toisiaan vastaavia, ne kohdistavat eri osiin taustalla olevaa elementtiä. Väärän valinnan tekeminen johtaa siihen, että arvot eivät sovellu ilman ääntä.

Ominaisuudet ovat JavaScript-objektin ominaisuuksia DOM-solmussa. Ne voivat pitää mitä tahansa tyyppiä, mukaan lukien merkkijonoja, totuusarvoja, numeroita, objekteja ja taulukoita, ja ne kuvaavat elementin nykyistä ajonaikaista tilaa. Ominaisuuden asettaminen on suora JavaScript-määritys.

Attribuutit ovat HTML-merkintöjä. Ne sijaitsevat elementin avaustagissa, ovat aina merkkijonoja ja kuvaavat elementin alkuperäistä konfiguraatiota. Attribuutin asettaminen aktivoi DOM-muutoksen ja merkkijonon konversion.

Joissakin tapauksissa molemmat pysyvät synkronoituna. Toisissa ne poikkeavat toisistaan. `<input>`-kentän `value` on klassinen esimerkki: `value` -attribuutti on alkuperäinen arvo, kun taas `value` -ominaisuus on nykyinen arvo, jonka käyttäjä on kirjoittanut. Attribuutin lukeminen sen jälkeen, kun käyttäjä on kirjoittanut palauttaa alkuperäisen merkinnän, mutta ominaisuuden lukeminen palauttaa kentän nykyisen sisällön.

Käytä **ominaisuuksia**:

- **Usein muuttuva ajonaikainen tila**: laskurit, nykyiset valinnat, kirjoitetut arvot
- **Ei-merkkijono-tyypit**: totuusarvot, numerot, objektit, taulukot
- **Suorituskykytunnevat päivitykset**: ominaisuudet ohittavat attribuuttien vaatiman merkkijonon konversion

Käytä **attribuutteja**:

- **Alkuperäinen konfiguraatio**: asetukset, joita komponentti lukee kerran yhdistämisen aikana
- **CSS-valitsijat**: arvot, joita haluat kohdistaa valitsijilla kuten `[disabled]` tai `[variant="danger"]`
- **Saavutettavuus-koukut**: `aria-label`, `role` ja muut ARIA-attribuutit
- **Merkkijono-tyyppiset asetukset, joita harvoin muutetaan**

Kun käännät kolmannen osapuolen verkkokomponenttia, tarkista komponentin dokumentaatio varmistaaksesi, mikä nimi vastaa ominaisuutta ja mikä attribuuttia. `PropertyDescriptor.attribute()` käyttämisestä jollekulle, jonka komponentti altistaa vain ominaisuutena, ei toimi, ja sama pätee käänteisesti. Komponentti ohittaa arvon hiljaa.

### Ominaisuuksien tyyppisuojaus {#typing-properties}

Kuvasto on parametrisoitu sen arvon Java-tyypillä. Täysi ilmoitussyntaksi on:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

`<T>`-geneerinen parametri ilmoittaa arvon tyypin. Oletusarvon ajonaikainen tyyppi myös kiinnittää `T`:n, joten geneeristä argumenttia harvoin tarvitsee määrittää eksplisiittisesti. webforJ käyttää `T`:tä arvojen sarjaa ja desarvoa, kun se kommunikoi asiakkaan kanssa.

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

Sarja on automaattista primitiiville, niiden pakatuille vastineille ja `String`:lle. Monimutkaisille tyypeille arvo sarjoitetaan JSON-muotoon ennen kuin se liitetään asiakkaan ominaisuuteen.

### Arvojen validoiminen {#validating-values}

Vahvista arvot setterissä ennen `set()`-kutsun tekemistä. Setter on luonnollinen valvontakohta, koska jokainen mutaatio kulkee sen lävitse.

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

Nullable-viittauksille käytä `Objects.requireNonNull()`, jotta virhearvo näkyy rajapinnassa sen sijaan, että se näkyisi myöhemmin renderointiputkessa.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Vältä validoimista `get()`-menetelmässä. Lukujen tulisi pysyä edullisina ja johdonmukaisina.

### Enum-tyyliset ominaisuudet {#enum-style-properties}

Useimmat verkkokomponentit odottavat pienikokoisia tai kebab-kielisiä merkkijonoarvoja enum-tyylisille ominaisuuksille (`theme="primary"`, `expanse="xs"`). webforJ käyttää Gsonia sarjoittamaan enum-arvoja, mutta Gsonin oletusesitys on vakio nimi isoilla kirjaimilla. Merkitse jokainen vakio `@SerializedName`-annotaatiolla, jotta sarjattu arvo vastaa sitä, mitä verkkokomponentti odottaa.

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

Tämä on sama kaava, jota webforJ:n sisäänrakennetut komponentit käyttävät `Theme`, `Expanse` ja samankaltaiset enum-luokat. Julkinen Java-API pysyy tyyppiturvallisena, ja verkkokomponentti saa arvon `@SerializedName`:stä.

### Ominaisuuksien testaaminen {#testing-properties}

`PropertyDescriptorTester` validoi, että jokainen `PropertyDescriptor` komponentissa on kytketty oikein. Se skannaa luokan kuvastokentät, kutsuu kutakin setteria oletusarvolla ja vertailee tulosta sitä vastaan, mitä getter palauttaa. Testeri havaitsee integraatiovirheitä ennen kuin ne saavuttavat suorassa sovelluksessa: setter, joka kirjoittaa väärään kuvastoon, getter, joka lukee eri ominaisuuden, oletusarvo, joka ei käänny, tai puuttuva accessor ilmoitetulle kuvastolle.

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

#### Ominaisuuksien poistaminen {#excluding-properties}

Jotkut kuvastot eivät noudata vakiogetteri- ja setterisääntöjä, tai ne riippuvat ulkoisesta tilasta, jota testi ei voi tyydyttää. Merkitse ne `@PropertyExclude`-annotaatiolla ohittaaksesi ne.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Mukautetut getter- ja setter-nimet {#custom-getter-and-setter-names}

Jos kuvasto käyttää ei-standardeja accessor-nimiä, ilmoita ne `@PropertyMethods`-annotaatiolla.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

`target`-parametri hyväksyy luokan, kun accessorit sijaitsevat muualla kuin komponentissa itsessään.

Lisätietoja testikattavuudesta katso [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Huolenaiheiden rajapinnat {#concern-interfaces}

Huolenaiherajapinnat antavat `ElementComposite`-aliluokan komponentille kykyjä ilman, että sinun tarvitsee kirjoittaa toteutusta itse. Rajapinnat välittävät kutsuja taustalla olevalle elementille. Toteuta ne, joita komponentin tulisi tukea, parametrisoituna aliluokan tyypillä, jotta ketjuttaminen palauttaa komponentin:

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

Yllä olevat kolme rajapintaa kattavat kaiken mitä `MyBadge` tarvitsee ilman mitään metodilohkoja luokassa. `HasText` altistaa `setText()`-metodin ja kirjoittaa elementin tekstisisältöön. `HasClassName` altistaa `addClassName()`-metodin, joka mahdollistaa badgen, jonka CSS voi kohdistaa. `HasStyle` altistaa `setStyle()`-metodin inline-tyylittelyyn.

Täydellinen saatavilla olevien rajapintojen sarja ja mitä kukin tarjoaa, katso [Huolenaiheiden rajapinnat](./component-fundamentals#concern-interfaces) Ymmärtäminen komponentit-artikkelissa. Jos oletuslähetyksesi ei vastaa sitä, mitä kääritty elementti altistaa, ohi.

## Tapahtumat {#events}

### Tapahtumien rekisteröinti {#event-registration}

Verkkokomponentit lähettävät DOM-tapahtumia, kun selaimessa tapahtuu jotain. Reagoidaksesi Java-koodista, kuuntele näitä tapahtumia `addEventListener()`-menetelmällä. Tapahtumien joukko, jota komponentti lähettää, vaihtelee, joten tarkista komponentin omat asiakirjat saatavilla olevista nimistä ja tietosisällöistä.

`ElementComposite` tukee debounce-, throttle-, suodatin- ja mukautettuja tapahtumadatassa rekisteröidyillä kuuntelijoilla.

Rekisteröi tapahtumakuuntelijat käyttämällä `addEventListener()`-menetelmää:

```java
// Esimerkki: lisääminen klikkaustapahtumakuuntelijalla
addEventListener(ElementClickEvent.class, event -> {
  // Käsittele klikkaustapahtuma
});
```

:::info
`ElementComposite` hyväksyy vain tapahtumaluokkia, jotka on merkitty `@EventName`-annotaatiolla, toisin kuin `Element`, joka hyväksyy minkä tahansa merkin tapahtuman nimen.
:::

### Sisäänrakennetut tapahtumaluokat {#built-in-event-classes}

`ElementClickEvent` on ainoa sisäänrakennettu tapahtumaluokka, jonka `ElementComposite` toimittaa. Se nostaa hiiren klikkauksen tapahtumat taustalla olevalta elementiltä typed-yhteensopivilla koordinaattihakemistoilla (`getClientX()`, `getClientY()`), painetietoja (`getButton()`) ja modifier-näppäimillä (`isCtrlKey()`, `isShiftKey()` jne.).

Jotta klikkaustapahtuman käsittely olisi saatavana aliluokan julkisessa API:ssa, toteuta `HasElementClickListener<T>`-huolenaiherajapinta. Se tarjoaa oletusmenetelmiä `onClick()` ja `addClickListener()`, jotka delegoivat suojatulle `addEventListener()`-menetelmälle.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() ja addClickListener() ovat nyt saatavilla MyBadgelle
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Muille tapahtumille, joita taustalla oleva verkkokomponentti lähettää, määritä mukautettu tapahtumaluokka. Katso [Mukautetut tapahtumaluokat](#custom-event-classes).

### Tapahtuman tietosisältö {#event-payloads}

Tapahtumat kuljettavat tietoa asiakkaalta Java-koodillesi. Pääset tähän tietoon `getData()`-menetelmän kautta raakatietoja varten tai käytä tyyppimenetelmiä, kun niitä on saatavilla sisäänrakennetuissa tapahtumaluokissa. Katso [Tapahtumat-opas](../building-ui/events) tehokkaan tietojen käsittelyn osalta.

### Mukautetut tapahtumaluokat {#custom-event-classes}

Määritä mukautetut tapahtumaluokat käyttäen `@EventName` ja `@EventOptions` kaappaamaan asiakaspuolen tietoa tyypitettynä Java-tapahtumana. Käytä tätä, kun Java-käsittelijä tarvitsee arvoja selaimelta.

`@EventName` sitoo Java-luokan tapahtumaan, jonka komponentti lähettää selaimessa, joten luokka, jossa on `@EventName("sl-change")`, laukaisee aina, kun taustalla oleva elementti lähettää `sl-change`. `@EventOptions` hallitsee, mitä kulkee takaisin kyseisen tapahtuman mukana. Jokainen `@EventData`, joka siihen sisältyy, pariutuu avaimen kanssa JavaScript-lauseeseen, joka arvioidaan DOM-tapahtuman yhteydessä. Tulos saatavilla Java-tapahtumaluokassa `getData().get(key)` kautta.

Tuotearvostelulomake alla käyttää tätä kaavaa mukautetun `ChangeEvent`:n kanssa [`sl-rating`](https://shoelace.style/components/rating). Mukautettu `ChangeEvent` kuljettaa arviointiarvon tyypitettynä `double`:na, ja kuuntelija käyttää sitä mahdollistaa lähetyspainikkeen:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Tapahtumavaihtoehdot {#event-options}

`ElementEventOptions` määrittää tapahtumapayloadin, debounce- tai throttle-ajan, suodatuslausekkeet ja esikäsittelykoodin. Alla olevan koodinpätkän osoittaa vaihtoehtoja:

```java
ElementEventOptions options = new ElementEventOptions()
  // Kerää mukautettuja tietoja asiakkailta
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Suorita JavaScriptiä ennen kuin tapahtuma laukaistaan
  .setCode("component.classList.add('processing');")
  
  // Laadi vain, jos ehdot täyttyvät
  .setFilter("component.value.length >= 2")
  
  // Viivästytä suoritusta, kunnes käyttäjä lopettaa kirjoittamisen (300 ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Sovita nämä vaihtoehdot rekisteröidessäsi kuuntelijaa mukautetulle tapahtumaluokalle
// (katso Mukautetut tapahtumaluokat -osio yllä, kuinka määrittää yksi):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` altistaa vain luokkapohjaisen muodon `addEventListener(Class, listener, options)`. Käytä sitä tapahtumaluokalla, joka on merkitty `@EventName`. Rekisteröi suoraan merkin tapahtuman nimen perusteella kutsumalla `getElement().addEventListener("input", listener, options)`.
:::

#### Suorituskyvyn hallinta {#performance-control}

**Debounce** viivästyttää suoritusta, kunnes toiminta loppuu:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Odota 300 ms viimeisestä tapahtumasta
```

Saatavilla olevat debounce-vaiheet:

- `LEADING`: Laukaise heti, odota sitten
- `TRAILING`: Odota hiljaista aikaa, sitten laukaise (oletus)
- `BOTH`: Laukaise heti ja hiljaisten aikojen jälkeen

**Throttle** rajoittaa suorituksen taajuutta:

```java
options.setThrottle(100); // Laukaise enintään kerran 100 ms: n välein
```

## Vuorovaikutus slotien kanssa {#interacting-with-slots}

Slotit ovat paikkoja verkkokomponentin sisällä, joita käyttäjät täyttävät sisällöllä. Verkkokomponentti ilmoittaa slotit malliinsa `<slot>` tai `<slot name="...">`, ja kääre altistaa menetelmät, jotka asettavat Java-komponentteja näihin slotteihin.

Sisällön lisäämiseksi slotteihin, laajenna `ElementCompositeContainer`-luokkaa sijasta `ElementComposite`. Konttaineri sisältää saman ominaisuus- ja attribuuttikoneiston lisäksi menetelmät lasten lisäämiseen. `add()`-kutsulla lisätyt lapset menevät oletusslottiin. `getElement().add(slotName, components)`-kutsulla lisätyt lapset menevät nimettyyn slottiin.

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

Alla oleva demo näyttää kaksi hinnoittelukorttia, jotka on rakennettu [`sl-card`](https://shoelace.style/components/card) käyttäen, joka täyttää `header`, oletus- ja `footer`-slotit Javasta:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Slotin sisältöjen tarkastelu {#inspecting-slot-contents}

Taustalla oleva `Element` (johon pääsee `getElement()`-kutsulla) tarjoaa menetelmiä tarkastellakseen, mitä nykyisin on liitetty slotteihin:

- **`findComponentSlot()`**: etsii kaikista sloteista tietyn komponentin ja palauttaa slotin nimen, joka sisältää sen, tai tyhjät merkkijonot, jos komponentti ei ole missään slotissa.
- **`getComponentsInSlot()`**: palauttaa listan komponenteista, jotka on liitetty tiettyyn slotiin. Vapaasti voi ottaa myös luokkatyypin suodattamaan tuloksia.
- **`getFirstComponentInSlot()`**: palauttaa ensimmäisen komponentin, joka on liitetty slottiin. Vapaasti voi ottaa myös luokkatyypin suodattamaan.
