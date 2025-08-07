---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: f4cd3a02cd03838a015f873a3e5143ef
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` webforJ:ssä yksinkertaistaa kolmansien osapuolten verkkokomponenttien testaamista, jotka on integroitu sovellukseesi. Se validoi, että `PropertyDescriptor`:illa määritellyt ominaisuudet on oikein linkitetty niiden getter- ja setter-menetelmiin ja varmistaa, että oletuskäyttäytymiset käsitellään johdonmukaisesti. Tämä työkalu on erityisen hyödyllinen kolmansien osapuolten komponenttien esittämien ominaisuuksien toimivuuden tarkistamiseen ilman, että tarvitaan toistuvaa testilogiikkaa.

:::warning kokeellinen ominaisuus
webforJ:n PropertyDescriptorTester-sovitin on tällä hetkellä kokeellinen ominaisuus. Rikkovia muutoksia voi tapahtua milloin tahansa.
:::

## Yleiskatsaus {#overview}

Työskentelyssä kolmansien osapuolten verkkokomponenttien kanssa on tärkeää varmistaa, että ominaisuudet käyttäytyvät odotetusti. `PropertyDescriptorTester` automatisoi tämän prosessin validoimalla, että ominaisuudet:
- On oikein kartoitettu niiden getter- ja setter-menetelmiin.
- Säilyttävät odotetut oletusarvot ja mukautetut käyttäytymiset.
- Vältetään yleisiä integrointiongelmia, kuten ristiriitaisia ominaisuusnimiä tai epäjohdonmukaisia oletusarvoja.

Työkalu tukee annotaatioita monimutkaisempia käyttötapauksia varten, kuten epäolennaisten ominaisuuksien poistamista tai mukautettujen getter- ja setter-menetelmien määrittelyä, mikä tekee siitä monipuolisen vaihtoehdon integrointitestaukseen.

## Kuinka `PropertyDescriptorTester` toimii {#how-propertydescriptortester-works}

Testausprosessi sisältää useita automatisoituja vaiheita:

1. **Luokan skannaus**: 
   `PropertyDescriptorScanner` tunnistaa kaikki `PropertyDescriptor`-kentät komponenttiluokassa, ja poistaa automaattisesti kentät, joilla on annotaatio `@PropertyExclude`.

2. **Menetelmien ratkaisu**:
   Standardiset getter- ja setter-menetelmät havaitaan nimeämiskäytännön mukaan (`get<PropertyName>`/`set<PropertyName>`). Ei-standardisten toteutusten osalta anotaatioita, kuten `@PropertyMethods`, käytetään määrittämään mukautetut menetelmän nimet tai kohdeluokat.

3. **Validointi**:
   Oletusarvot määritetään setter-menetelmällä, noudetaan getterillä ja verrataan oikeellisuuden varmistamiseksi. Mahdollinen ristiriita aiheuttaa `AssertionError`-virheen, joka korostaa tiettyä ongelmaa.

4. **Virheraportointi**:
   Testeri antaa yksityiskohtaista palautetta kaikista validointivirheistä, kuten puuttuvista menetelmistä, epäjohdonmukaisista oletusarvoista tai ominaisuuskonfiguraatioiden virheistä.

## Testien kirjoittaminen `PropertyDescriptorTester` :n avulla {#writing-tests-with-propertydescriptortester}

Tässä on esimerkki, joka havainnollistaa perusominaisuuksien validointia `AppLayout`-komponentille:

### Esimerkki: Perusvalidointi {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Oletusotsikko");

  // setters and getters
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
      fail("PropertyDescriptor test failed: " + e.getMessage());
    }
  }
}
```

Tämä testi varmistaa automaattisesti:
- Sen, että `drawerOpened`-ominaisuudella on voimassa olevat getter- ja setter-menetelmät.
- Sen, että `headerTitle` oletusarvo on `"Oletusotsikko"`.

## Edistyneet käyttötapaukset annotaatioiden kanssa {#advanced-use-cases-with-annotations}

Monimutkaisempia tilanteita varten `PropertyDescriptorTester` tukee annotaatioita ominaisuuksien mukauttamiseen tai testaamisesta poistamiseen.

### Poista ominaisuudet `@PropertyExclude` -annotaatiolla {#exclude-properties-with-propertyexclude}

Poista ominaisuudet, jotka riippuvat ulkoisista järjestelmistä tai eivät ole merkityksellisiä testille. Esimerkiksi:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
    PropertyDescriptor.property("excludedProperty", "Poissuljettu");
```

### Mukauttamismenettelyt `@PropertyMethods` -annotaatiolla {#customize-methods-with-propertymethods}

Määritä mukautettu getter, setter tai kohdeluokka, kun oletusnimityskäytännöt eivät päde:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
    PropertyDescriptor.property("customProperty", "Oletusarvo");
```
