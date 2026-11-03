---
sidebar_position: 4
title: Väitteet
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
Testin tehtyä toiminnon se tarkistaa, mitä käyttäjä näkisi: painikkeen tekstin, voiko kenttää muokata ja mikä luettelon kohde näkyy valittuna. `verifyThat(component)` ilmaisee odotukset samoilla sanoilla, kuten `isReadOnly()` tai `hasText("Save")`. Jokainen tarkistus lukee komponentin tilan eli saman tilan, jonka selain näyttää. Epäonnistunut tarkistus ilmoittaa komponentin ja molemmat arvot.

<!-- INTRO_END -->

## Komponentin tarkistaminen {#checking-a-component}

Seuraavassa editorissa näkyy nimi, jota ei voi muuttaa ennen kuin käyttäjä napsauttaa **Edit**. Napsautus tekee kentästä muokattavan, siirtää kohdistuksen siihen ja muuttaa painikkeen tekstiksi **Save**:

```java title="ProfileEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ProfileEditor extends Composite<Div> {

  public ProfileEditor() {
    TextField name = new TextField("Name", "Ada");
    name.setReadOnly(true);
    Button edit = new Button("Edit");
    edit.onClick(
        e -> {
          name.setReadOnly(false).focus();
          edit.setText("Save");
        });

    getBoundComponent().add(name, edit);
  }
}
```

Testi tarkistaa kentän ennen napsautusta ja sen jälkeen:

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldMakeTheNameEditableWhenTheUserClicksEdit(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
  var edit = ComponentLocator.of(Button.class).byText("Edit").locate();

  verifyThat(name).hasValue("Ada").isReadOnly();

  UserInteraction.click(edit);

  verifyThat(name).isEditable().isFocused();
  verifyThat(edit).hasText("Save");
}
```

`verifyThat` tulee `com.webforj.testbed.assertion.WebforjAssertions`-luokasta ja tuodaan staattisesti. Jokainen tarkistus palauttaa assertion-olion, joten yhden komponentin tarkistukset voi ketjuttaa samaan lausekkeeseen. Ne suoritetaan järjestyksessä, ja ensimmäinen epäonnistuva tarkistus keskeyttää testin.

Jokaiselle tilalle on tarkistus sen kummallekin puolelle, kuten `isReadOnly()` ja `isEditable()` tai `isVisible()` ja `isHidden()`. Näin testi ilmaisee odotetun tilan suoraan.

## Mitä tarkistus lukee {#what-a-check-reads}

Tarkistus lukee komponenttia [concern-rajapinnan](/docs/building-ui/component-fundamentals#concern-interfaces) kautta, joten se toimii kaikissa kyseisen rajapinnan toteuttavissa komponenteissa, myös sovelluksesi komponenteissa. `hasLabel` toimii kaikilla komponenteilla, joilla on selite, ja `isReadOnly` kaikilla komponenteilla, jotka voivat olla vain luku -tilassa. Tarkistukset kattavat seuraavat asiat:

- Mitä käyttäjä lukee: tekstin, HTML-sisällön, selitteen, paikkamerkin, ohjetekstin ja työkaluvihjeen.
- Komponentin tilan: näkyvyyden, käytössä olemisen, vain luku -tilan, pakollisuuden, kelvollisuuden ja kohdistuksen.
- Kentän arvon sekä luetteloiden, taulukoiden ja puiden kohteet ja valinnan.
- Sovelluksen asettamat tiedot: nimen, attribuutit, ominaisuudet, tyylin, teeman ja ekspanssin.
- Säilön sisältämät komponentit sekä sen, onko komponentti liitetty vai tuhottu.

<JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink>-viite luettelee kaikki tarkistukset ja rajapinnan, josta kukin tarkistus lukee.

## Arvot {#values}

Seuraava kenttä laskee summan käyttäjän syöttämän määrän perusteella:

```java title="OrderQuantity.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class OrderQuantity extends Composite<Div> {

  public OrderQuantity() {
    NumberField quantity = new NumberField("Quantity");
    Paragraph total = new Paragraph("Total: 0");
    quantity.onValueChange(
        e -> total.setText("Total: " + (int) (e.getValue() * 5)));

    getBoundComponent().add(quantity, total);
  }
}
```

Testi syöttää määrän ja tarkistaa sitten kentän arvon sekä summan:

```java title="OrderQuantityTest.java" showLineNumbers
@Test
void shouldHoldTheTypedQuantityAsANumber(WebforjTestbed context) {
  context.add(new OrderQuantity());

  var quantity = ComponentLocator.of(NumberField.class)
      .byLabel("Quantity")
      .locate();

  UserInteraction.type(quantity, "3");

  verifyThat(quantity).hasValue(3.0);
  verifyThat(ComponentLocator.of(Paragraph.class).locate())
      .hasText("Total: 15");
}
```

`hasValue` vertaa arvoja `equals`-metodilla, joten odotusarvon tyypin on vastattava kentän arvoa. `NumberField` sisältää `Double`-arvon, joten testissä odotetaan arvoa `3.0`, ei `3`.

:::info Arvotyypit
`DateField` sisältää `LocalDate`-arvon ja `TimeField` `LocalTime`-arvon. Odota kyseistä tyyppiä olevaa arvoa.
:::

## Lapsikomponentit {#child-components}

`hasComponentCount` tarkistaa, kuinka monta komponenttia säilö sisältää, ja `hasComponent` tarkistaa, että säilö sisältää tietyn komponentin. Seuraava editori lisää jokaisen käyttäjän syöttämän tunnisteen tunnisteluetteloon:

```java title="TagEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class TagEditor extends Composite<Div> {

  public TagEditor() {
    TextField tag = new TextField("Tag");
    Div tags = new Div();
    tags.setName("tags");
    Button add = new Button("Add", e -> tags.add(new Span(tag.getValue())));

    getBoundComponent().add(tag, add, tags);
  }
}
```

Testi lisää kaksi tunnistetta ja tarkistaa luettelon:

```java title="TagEditorTest.java" showLineNumbers
@Test
void shouldAddEveryTagTheUserEnters(WebforjTestbed context) {
  context.add(new TagEditor());

  var tag = ComponentLocator.of(TextField.class).byLabel("Tag").locate();
  var add = ComponentLocator.of(Button.class).byText("Add").locate();
  var tags = ComponentLocator.of(Div.class).byName("tags").locate();

  UserInteraction.type(tag, "java");
  UserInteraction.click(add);
  UserInteraction.type(tag, "kotlin");
  UserInteraction.click(add);

  verifyThat(tags)
      .hasComponentCount(2)
      .hasComponent(ComponentLocator.of(Span.class).byText("kotlin").locate());
}
```

## Valinta {#selection}

Valintatarkistukset lukevat, minkä luettelon, taulukon tai puun kohteen näkyy olevan valittuna. Valinta voidaan tarkistaa kohteen, sen indeksin tai avaimen perusteella. Seuraavassa luettelossa on kolme hedelmää:

```java title="FruitPicker.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;

public class FruitPicker extends Composite<Div> {

  public FruitPicker() {
    ListBox fruits = new ListBox("Fruit");
    fruits.add("apple", "Apple");
    fruits.add("pear", "Pear");
    fruits.add("plum", "Plum");

    getBoundComponent().add(fruits);
  }
}
```

Testi tarkistaa, ettei mitään ole valittuna, valitsee hedelmän ja tarkistaa valinnan avaimen ja indeksin perusteella:

```java title="FruitPickerTest.java" showLineNumbers
@Test
void shouldSelectTheFruitTheUserPicked(WebforjTestbed context) {
  context.add(new FruitPicker());

  var fruits = ComponentLocator.of(ListBox.class).byLabel("Fruit").locate();

  verifyThat(fruits).hasNoSelection();

  UserInteraction.selectKey(fruits, "pear");

  verifyThat(fruits).hasSelectedKey("pear").hasSelectedIndex(1);
}
```

Komponentissa, joka sallii useiden kohteiden valitsemisen, `hasSelected`, `hasSelectedIndex` ja `hasSelectedKey` läpäisevät tarkistuksen, kun odotettu kohde on niiden joukossa. `hasItemCount` tarkistaa tietovaraston sisältävän komponentin kohteiden määrän, esimerkiksi `Table`-komponentin.

## Elinkaari {#lifecycle}

`isAttached()` tarkistaa, että komponentti on sivulla, ja `isDestroyed()` tarkistaa, että sovellus on tuhonnut sen. Seuraava banneri tuhoaa itsensä, kun käyttäjä sulkee sen:

```java title="WelcomeBanner.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WelcomeBanner extends Composite<Div> {

  public WelcomeBanner() {
    getBoundComponent()
        .add(
            new Paragraph("Welcome back"),
            new Button("Dismiss", e -> destroy()));
  }
}
```

Testi sulkee bannerin ja tarkistaa sen tilan:

```java title="WelcomeBannerTest.java" showLineNumbers
@Test
void shouldRemoveTheBannerWhenTheUserDismissesIt(WebforjTestbed context) {
  context.add(new WelcomeBanner());

  var banner = ComponentLocator.of(WelcomeBanner.class).locate();

  verifyThat(banner).isAttached();

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Dismiss").locate());

  verifyThat(banner).isDestroyed();
}
```

## Virheilmoitukset {#failure-messages}

Epäonnistunut tarkistus heittää `AssertionError`-poikkeuksen, joka ilmoittaa komponentin tyypin, odotetun arvon ja toteutuneen arvon. Seuraava testi saa tarkistuksen tarkoituksella epäonnistumaan ja lukee ilmoituksen:

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldNameTheComponentAndBothValuesOnFailure(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();

  var failure = assertThrows(
      AssertionError.class, () -> verifyThat(name).hasValue("Grace"));

  assertEquals(
      "Expected TextField to have value <Grace> but it has <Ada>.",
      failure.getMessage());
}
```

Jos komponentti ei julkaise tarkistettavaa tilaa, tarkistus epäonnistuu ja ilmoittaa puuttuvan rajapinnan. `Div` ei sisällä arvoa, joten `Div`-komponentille tehty `hasValue`-tarkistus epäonnistuu ja ilmoittaa `ValueAware`-rajapinnan.

## Omien komponenttien tarkistukset {#checks-for-your-own-components}

Sovelluksesi komponentti saa concern-rajapintojen tarkistukset käyttöönsä ilman lisäkoodia, jos se toteuttaa kyseiset rajapinnat. Jos komponentilla on tila, jonka vain se itse tuntee, kirjoita assertion-luokka. Seuraava tähtiarviointi laskee käyttäjän napsauttamat tähdet ja palauttaa ne `getStars()`-metodista:

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

Assertion-luokka laajentaa `AbstractComponentAssert`-luokkaa ja lisää tähtien tarkistuksen. Staattinen `verifyThat` luo sen, joten testi kutsuu sitä samalla tavalla kuin sisäänrakennettuja tarkistuksia:

```java title="StarRatingAssert.java" showLineNumbers
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.assertion.AbstractComponentAssert;

public final class StarRatingAssert
    extends AbstractComponentAssert<StarRatingAssert, StarRating> {

  private StarRatingAssert(StarRating actual) {
    super(actual, StarRatingAssert.class);
  }

  public static StarRatingAssert verifyThat(StarRating actual) {
    return new StarRatingAssert(actual);
  }

  public StarRatingAssert hasStars(int stars) {
    isNotNull();
    if (actual.getStars() != stars) {
      failWithMessage(
          "Expected %s to have %s stars but it has %s.",
          componentName(),
          stars,
          actual.getStars());
    }

    return myself;
  }
}
```

Tarkistuksen sisällä `actual` on komponentti, `failWithMessage` epäonnistuttaa tarkistuksen muotoillulla ilmoituksella, `componentName()` nimeää komponentin ja `myself`-olion palauttaminen mahdollistaa tarkistusten ketjuttamisen. Uusi luokka lisää tarkistuksia, mutta ei korvaa mitään. Testi tuo molemmat `verifyThat`-metodit staattisesti, ja Java valitsee argumenttiin sopivan metodin: `StarRating` ohjautuu `StarRatingAssert`-luokalle ja kaikki muut komponentit `WebforjAssertions`-luokalle. Sisäänrakennetut tarkistukset ovat käytettävissä myös tähtiarvioinnissa, esimerkiksi `isAttached()`:

```java title="StarRatingTest.java" showLineNumbers
import static com.webforj.samples.unit.views.testbed.StarRatingAssert.verifyThat;
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingTest {
  @Test
  void shouldHoldTheStarsTheUserClicked(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).hasStars(4).isAttached();
  }

  @Test
  void shouldCheckTheRatingAndTheButtonInOneTest(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();
    var four = ComponentLocator.of(Button.class).byText("4").locate();

    UserInteraction.click(four);

    verifyThat(rating).hasStars(4);
    verifyThat(four).hasText("4").isEnabled();
  }
}
```
