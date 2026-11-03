---
sidebar_position: 5
title: Tilannevedokset
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
Näkymän tarkistaminen kohta kohdalta vaatii yhden väitteen jokaista otsikkoa, painiketta ja kenttää kohti. Tilannekuva tarkistaa ne kaikki kerralla. Se kirjaa ylös kaiken, minkä käyttäjä näkisi näkymässä, yhden rivin kutakin komponenttia kohti, ja tallentaa kuvauksen testin viereen. Jokaisella myöhemmällä suorituskerralla näkymä kuvataan uudelleen ja sitä verrataan tallennettuun kuvaukseen. Näin kaikki muutokset sivun näkymässä saavat testin epäonnistumaan, ja muutos näkyy koodikatselmoinnissa pelkkänä tekstierona.

<!-- INTRO_END -->

## Tilannekuvan täsmäyttäminen {#matching-a-snapshot}

Seuraavassa yhteenvedossa näytetään tilausnumero, huomautuskenttä, piilotettu alennusrivi, **Pay**-painike kuvakkeella ja käytöstä poistettu **Cancel**-painike:

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

Testi lisää yhteenvedon ja tarkistaa sen `matchesSnapshot()`-metodilla:

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

Ensimmäisellä suorituskerralla kuvaus kirjoitetaan tiedostoon, ja testi läpäistään. Jokaisella myöhemmällä suorituskerralla testi epäonnistuu, jos näkymä ei enää vastaa tallennettua tiedostoa:

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` on `verifyThat`-tarkistus, kuten [Assertions](./assertions)-sivulla kuvatut tarkistukset, joten se toimii millä tahansa komponentilla, ei vain näkymällä.

:::tip Milloin tilannekuvia kannattaa käyttää
Tilannekuva lukitsee koko näkymän. Käytä sitä näkymissä, joiden sisällön ei pitäisi muuttua, ja tarkista yksittäisiä asioita, kuten painikkeen tekstiä napsautuksen jälkeen, [väitteillä](./assertions).
:::

## Tilannekuvan lukeminen {#reading-a-snapshot}

Jokainen [tallennetun tilannekuvan](#matching-a-snapshot) rivi kuvaa yhtä komponenttia. Sen sisältämät komponentit luetellaan alla kahdella välilyönnillä sisennettyinä. Muita komponentteja sisältävän rivin lopussa on kaksoispiste. Rivin osat tulostetaan tässä järjestyksessä:

1. Komponentin tyyppi, kuten `Button`.
2. Teksti lainausmerkeissä, jos komponentti näyttää tekstiä, kuten `"Pay"`.
3. Määritteet muodossa `[key=value]`, kuten `[theme=primary]` tai `[label=Note]`.
4. Liput muodossa `[key]`, kuten `[disabled]` tai `[hidden]`.

Riville tulostetaan vain komponentin oletusarvosta poikkeavat tiedot, joten yhteenvedon näkee yhdellä silmäyksellä. **Cancel**-painikkeella on `[disabled]`, eikä käytössä olevan **Pay**-painikkeen tilasta tulosteta mitään. **Pay**-painikkeella on `[theme=primary]`, eikä oletusteemaa käyttävän painikkeen teemaa tulosteta. Kokoja, tyylejä ja luokkien nimiä ei koskaan tulosteta.

Jotkin määritteet kertovat, missä komponentti sijaitsee:

- `slot` nimeää sen emokomponentin osan, jonka komponentti täyttää. **Pay**-painikkeen kuvake on tekstin edellä, joten sen kohdalla lukee `[slot=prefix]`.
- `composite` nimeää komponentin, johon `Composite` sitoo näkymän, ja `node` sen elementin, jonka se renderöi. Yhteenveto sitoo `Div`-komponentin, joten siinä lukee `[composite=Div] [node=div]`.
- `route` nimeää reitin, johon näkymä on rekisteröity.
- `hidden` merkitsee piilotettua komponenttia, kuten alennusriviä. Komponentin piilottaminen tai näyttäminen muuttaa tilannekuvaa.

Teksti on aina lainausmerkeissä. Määritteen arvo merkitään lainausmerkkeihin, jos se sisältää jotain muuta kuin kirjaimia, numeroita tai merkkejä `.`, `_`, `+`, `/` tai `-`, kuten kohdassa `[placeholder="Delivery instructions"]`.

## Pitkät luettelot {#long-lists}

Pitkä luettelo peittäisi näkymän muun sisällön alleen, joten luettelon alkiot, taulukon rivit ja puun solmut tulostetaan enintään kymmenelle riville emokomponentin alle. Seuraavassa luettelossa on kaksitoista maata:

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

Tallennetussa tilannekuvassa tulostetaan kymmenen ensimmäistä maata ja niiden perään rivi, joka kertoo kahdesta seuraavasta:

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

Jos haluat tallentaa enemmän alkioita, välitä raja [tilannekuvan asetuksissa](#snapshot-options) `setMaxWidth`-metodilla. `matchesSnapshot(name, options)` renderöi näkymän täsmälleen sille välitetyillä asetuksilla, joten testi ottaa käyttöön myös tallennetun tilannekuvan kolme muuta asetusta ja antaa tilannekuvalle nimen:

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

Tallennettu tilannekuva sisältää kaikki kaksitoista maata, ja tiedoston nimi muodostetaan tilannekuvan nimen perusteella:

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip Taulukot ja puut
Taulukko ei laske tietovarastonsa alkioita, joten sen lopussa on pelkkä `- …` ilman lukumäärää. Raja koskee kutakin emokomponenttia erikseen, joten puun jokaisella tasolla tulostetaan enintään kymmenen solmua.
:::

## Tilannekuvien tallennuspaikka {#where-snapshots-are-stored}

Tilannekuvatiedostot sijaitsevat hakemistossa `src/test/resources/webforj-snapshots`, testiluokan pakettia vastaavissa kansioissa. Tiedoston nimenä on testiluokan nimi, jota seuraavat tilannekuvan nimi ja `.yml`:

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

Tiedostot kuuluvat versionhallintaan testien viereen. Kunkin tiedoston ensimmäinen rivi kertoo muotoversion. Jos tiedosto on kirjoitettu eri versiolla, testi epäonnistuu ja ilmoittaa molempien versioiden nimet eron sijaan.

## Tilannekuvien nimeäminen {#naming-snapshots}

`matchesSnapshot()` nimeää tilannekuvan suoritettavan testimetodin mukaan, joten `OrderSummaryTest`-luokan `shouldMatchTheSnapshot`-metodin tilannekuva tallennetaan tiedostoon `OrderSummaryTest.shouldMatchTheSnapshot.yml`. Jos testi tarkistaa useamman kuin yhden tilannekuvan, kullekin annetaan nimi `matchesSnapshot(name)`-metodilla, ja nimi korvaa tiedostonimessä metodin nimen. Nimessä voi käyttää kirjaimia, numeroita, välilyöntejä, pisteitä, yhdysmerkkejä ja `_`-merkkiä. Kaikki muut merkit korvataan yhdysmerkillä, joten `save/form:v2` tallennetaan muodossa `save-form-v2`.

`matchesSnapshot(name, options)` ottaa vastaan myös renderöintiin käytettävät [`SnapshotOptions`](#snapshot-options)-asetukset, esimerkiksi jos haluat tallentaa taulukosta oletusta enemmän rivejä.

## Kun tilannekuva ei täsmää {#when-a-snapshot-doesnt-match}

Jos näkymä ei enää vastaa tallennettua tiedostoa, tarkistus epäonnistuu ja näyttää, mikä muuttui. Kun **Pay**-painikkeen teksti [tilausyhteenvedossa](#matching-a-snapshot) muuttuu muotoon **Pay now**, komento `mvn test` ilmoittaa seuraavaa:

```diff title="Terminal"
java.lang.AssertionError:
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## Tilannekuvien päivittäminen {#updating-snapshots}

Kun näkymään tehty muutos on tarkoituksellinen, suorita testit kerran siten, että järjestelmäominaisuuden `webforj.testbed.updateSnapshots` arvoksi asetetaan `true`. Kaikki suorituskerralla tarkistetut tilannekuvat kirjoitetaan uudelleen, ja tilannekuvatiedostot, joita mikään testi ei enää tarkista, poistetaan. Tarkista tiedostojen erot ennen niiden toimittamista.

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## Vanhentuneet tilannekuvat {#obsolete-snapshots}

Kun luokan kaikki testit on suoritettu, TestBed etsii luokan tilannekuvatiedostot, joita mikään sen testi ei tarkistanut, esimerkiksi uudelleennimetyn testin tiedoston. Tällainen tiedosto saa testiluokan epäonnistumaan. Virheilmoituksessa luetellaan nämä tiedostot ja pyydetään poistamaan ne tai suorittamaan testit asetuksella `-Dwebforj.testbed.updateSnapshots=true`, joka poistaa ne.

:::info Yksittäiset testimetodit
Tarkistus suoritetaan vain, kun luokan kaikki testit on ajettu. IDE:stä käynnistetty yksittäinen testimetodi ohittaa sen.
:::

## Jatkuva integrointi {#continuous-integration}

Tilannekuvat kirjoitetaan paikallisesti ja toimitetaan versionhallintaan. Kun ympäristömuuttujaksi `CI` on asetettu jokin muu arvo kuin `false` tai `0`, kuten useimmissa jatkuvan integroinnin palveluissa, TestBed ei koskaan kirjoita tilannekuvatiedostoa:

- Puuttuva tilannekuva saa testin epäonnistumaan, ja virheilmoituksessa nimetään odotettu tiedosto sen sijaan, että tiedosto kirjoitettaisiin.
- Suoritus komennolla `-Dwebforj.testbed.updateSnapshots=true` epäonnistuu ja pyytää päivittämään tilannekuvat paikallisesti.

## Komponentin tulostaminen {#printing-a-component}

`Snapshot.render(component)` palauttaa komponentin kuvauksen merkkijonona luomatta tiedostoa. Tulosta se testiä kirjoittaessasi, niin näet, mitä sivu sisältää. Se jättää pois selaimen ylläpitämän tilan, kuten käyttäjän kirjoittaman arvon, ja piilotetut komponentit, elleivät sille välitetyt asetukset sisällä niitä.

## Tilannekuvan asetukset {#snapshot-options}

`SnapshotOptions` määrittää, kuinka paljon kuvaus sisältää. `setMaxWidth` asettaa, kuinka monta alkiota emokomponentin alle tulostetaan; oletusarvo on kymmenen. `setBrowserState` lisää selaimen ylläpitämän tilan, kuten käyttäjän kirjoittaman arvon ja kohdistuksen. `setHiddenComponents` lisää piilotetut komponentit `hidden`-lipulla, ja `setComposites` lisää `composite`- ja `node`-määritteet.

Tallennettu tilannekuva sisältää kaiken: selaimen tilan, piilotetut komponentit ja composite-komponentit. `Snapshot.render` ja `new SnapshotOptions()` eivät sisällä niistä mitään. `matchesSnapshot(name, options)` renderöi näkymän täsmälleen sille välitetyillä asetuksilla, joten voit tallentaa enemmän alkioita asettamalla suuremman `maxWidth`-arvon ja ottamalla kolme muuta asetusta käyttöön. <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink>-viitesivu kuvaa kunkin asetuksen.

## Tilannekuvat omille komponenteille {#snapshots-for-your-own-components}

Sovelluksesi komponentti tulostaa tyyppinsä, tekstinsä ja kaikkien toteuttamiensa [concern-rajapintojen](/docs/building-ui/component-fundamentals#concern-interfaces) tilan, kuten tunnisteen tai teeman, ilman lisäkoodia. Vain komponenttisi tunteman tilan tulostamiseen tarvitaan `SnapshotSerializer`. Seuraava tähtiarvostelu laskee käyttäjän napsauttamat tähdet ja palauttaa niiden määrän `getStars()`-metodilla:

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

Sarjallistaja ilmoittaa `getContract()`-metodissa kuvaamansa komponenttiluokan ja lisää tähtien määrän komponentin riville `describe`-metodissa:

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

Sarjallistaja luetellaan täydellisellä nimellään testiresurssien hakemistossa olevassa tiedostossa:

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

Sen jälkeen sitä käytetään testiajon jokaisessa tilannekuvassa. Seuraava testi napsauttaa neljää tähteä ja tarkistaa tilannekuvan:

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

Arvostelun rivillä näkyy tähtien määrä:

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

`setAttribute`-metodin lisäksi sarjallistajalle välitettävä `SnapshotNode` asettaa rivin tekstin ja liput sekä lisää sen alle rivejä komponentin osista, kuten taulukon sarakkeista ja riveistä. Sarjallistaja lukee selaimen ylläpitämää tilaa vain, jos `isBrowserState()` palauttaa arvon `true`. <JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink>-viitesivulla luetellaan kaikki metodit.

Sovelluksesi sarjallistaja suoritetaan webforJ-sarjallistajien jälkeen, joten sen asettama määrite korvaa webforJ:n saman avaimen kohdalle asettaman arvon. Jos samalle komponenttiluokalle on kaksi sarjallistajaa tai jos kaksi sarjallistajaa kirjoittaa saman määritteen samalle riville, tilannekuvatarkistus epäonnistuu ja virheilmoituksessa nimetään molemmat luokat.
