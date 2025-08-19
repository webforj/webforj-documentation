---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 9ec1cde5a7d91d75dfd454741a6e8ee3
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` webforJ:ssä helpottaa kolmannen osapuolen web-komponenttien testaamista, jotka on integroitu sovellukseesi. Se validoi, että `PropertyDescriptor`-luokassa määritellyt ominaisuudet ovat oikein linkitettyjä niiden getter- ja setter-metodeihin ja varmistaa, että oletusarvoiset käyttäytymiset käsitellään johdonmukaisesti. Tämä työkalu on erityisen hyödyllinen kolmansien osapuolten komponenttien tarjoamien ominaisuuksien toiminnallisuuden varmistamiseksi ilman tarpeetonta testauslogiikkaa.

:::warning kokeellinen ominaisuus
webforJ PropertyDescriptorTester -adapteri on tällä hetkellä kokeellinen ominaisuus. Rikkovia muutoksia voidaan tuoda esiin milloin tahansa.
:::

## Yhteenveto {#overview}

Kolmannen osapuolen web-komponenttien kanssa työskennellessä on olennaista varmistaa, että ominaisuudet käyttäytyvät odotetusti. `PropertyDescriptorTester` automatisoi tämän prosessin validoimalla, että ominaisuudet:
- On oikein kartoitettu niiden getter- ja setter-metodeihin.
- Säilyttää odotetut oletusarvot ja mukautetut käyttäytymiset.
- Vältetään yleisiä integraatio-ongelmia, kuten eri nimisiä ominaisuuksia tai epäjohdonmukaisia oletusarvoja.

Työkalu tukee annotaatioita monimutkaisimmille käyttötapauksille, kuten merkityksettömien ominaisuuksien poistamiselle tai mukautettujen getter- ja setter-metodien määrittämiselle, mikä tekee siitä monipuolisen vaihtoehdon integraatiotestaamiseen.

## Kuinka `PropertyDescriptorTester` toimii {#how-propertydescriptortester-works}

Testausprosessi sisältää useita automatisoituja vaiheita:

1. **Luokan skannaus**: 
   `PropertyDescriptorScanner` tunnistaa kaikki `PropertyDescriptor`-kentät komponenttiluokassa, ja jättää automaattisesti huomiotta `@PropertyExclude`-annotaatiolla merkitsemät kentät.

2. **Metodien ratkaisu**:
   Oletus getter- ja setter-metodeja havaitaan nimikäytännön perusteella (`get<OminaisuudenNimi>`/`set<OminaisuudenNimi>`). Ei-standardeille toteutuksille annotaatiot kuten `@PropertyMethods` määrittelevät mukautetut metodin nimet tai kohdeluokat.

3. **Validointi**:
   Oletusarvot määritetään setter-metodilla, haetaan getterillä ja verrataan varmistamisen vuoksi. Mahdollinen epäyhteneväisyys laukaisee `AssertionError`-virheen, joka tuo esiin erityisen ongelman.

4. **Virheraportointi**:
   Testeri antaa yksityiskohtaista palautetta kaikista validointivirheistä, kuten puuttuvista metodeista, epäyhtenäisistä oletusarvoista tai ominaisuuksien vääristä määrityksistä.

## Testien kirjoittaminen `PropertyDescriptorTester`-kanssa {#writing-tests-with-propertydescriptortester}

Tässä on esimerkki, joka osoittaa perusominaisuuksien validointia `AppLayout`-komponentille:

### Esimerkki: Perusvalidointi {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Oletusarvoinen Otsikko");

  // setterit ja getterit
}
```

#### Testitapaus {#test-case}

```java title="MyComponentTest.java"
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class MyComponentTest {

  MyComponent component = new MyComponent();

  @Test
  void validateProperties() {
    try {
      PropertyDescriptorTester.run(MyComponent.class, component);
    } catch (Exception e) {
      fail("PropertyDescriptor testi epäonnistui: " + e.getMessage());
    }
  }
}
```

Tämä testi tarkistaa automaattisesti:
- Että `drawerOpened`-ominaisuudella on voimassa olevat getter- ja setter-metodit.
- Että `headerTitle` oletusarvo on `"Oletusarvoinen Otsikko"`.

## Edistyneet käyttötapaukset annotaatioilla {#advanced-use-cases-with-annotations}

Monimutkaisimmille tilanteille `PropertyDescriptorTester` tukee annotaatioita, joilla voidaan mukauttaa tai poistaa ominaisuuksia testauksesta.

### Poista ominaisuudet `@PropertyExclude`-annotaatiolla {#exclude-properties-with-propertyexclude}

Poista ominaisuudet, jotka riippuvat ulkoisista järjestelmistä tai eivät ole merkityksellisiä testissä. Esimerkiksi:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Poistettu");
```

### Mukauta metodeja `@PropertyMethods`-annotaatiolla {#customize-methods-with-propertymethods}

Määrittele mukautettu getter, setter tai kohdeluokka, kun oletusnimikäytännöt eivät päde:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Oletusarvo");
```
