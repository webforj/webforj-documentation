---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: fb5cec5217d52b4e298c4d886ef95160
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` webforJ:ssä helpottaa **kolmannen osapuolen verkkokomponenttien** testaamista, jotka on integroitu sovellukseesi. Se validoi, että [`PropertyDescriptor`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/component/element/PropertyDescriptor.html):illa määritellyt ominaisuudet on oikein linkitetty niiden getter- ja setter-metodeihin ja varmistaa, että oletusarvoisia käyttäytymisiä käsitellään johdonmukaisesti. Tämä työkalu on erityisesti hyödyllinen kolmannen osapuolen komponenttien tarjoamien ominaisuuksien toimivuuden varmistamiseksi ilman, että vaaditaan toistuvaa testilogiikkaa.

:::warning kokeellinen ominaisuus
webforJ:n PropertyDescriptorTester-adapteri on tällä hetkellä kokeellinen ominaisuus. Rikkovia muutoksia voidaan tuoda milloin tahansa.
:::

## Yhteenveto {#overview}

Työskenneltäessä kolmansien osapuolten verkkokomponenttien kanssa on tärkeää varmistaa, että ominaisuudet käyttäytyvät odotetusti. `PropertyDescriptorTester` automatisoi tämän prosessin validoimalla, että ominaisuudet:
- On oikein mappattu niiden getter- ja setter-metodeihin.
- Säilyttävät odotettuja oletusarvoja ja mukautettuja käyttäytymisiä.
- Välttävät yleisiä integrointiongelmia, kuten epäsopivia ominaisuuden nimiä tai epäjohdonmukaisia oletusarvoja.

Työkalu tukee annotaatioita monimutkaisemmissa käyttötapauksissa, kuten merkityksettömien ominaisuuksien sulkemisessa tai mukautettujen getter- ja setter-metodien määrittämisessä, mikä tekee siitä monipuolisen vaihtoehdon integraatiotestaukseen.

## Kuinka `PropertyDescriptorTester` toimii {#how-propertydescriptortester-works}

Testausprosessi sisältää useita automatisoituja vaiheita:

1. **Luokan skannaus**: 
   `PropertyDescriptorScanner` tunnistaa kaikki `PropertyDescriptor`-kentät komponenttiluokassa ja sulkee automaattisesti pois kentät, joilla on annotaatio `@PropertyExclude`.

2. **Metodin ratkaisu**:
   Vakiot getter- ja setter-metodit havaitaan nimityssäännösten perusteella (`get<PropertyName>`/`set<PropertyName>`). Standardinmukaisille toteutuksille annotaatioita kuten `@PropertyMethods` käytetään määrittämään mukautettujen metodien nimet tai kohdeluokat.

3. **Validointi**:
   Oletusarvot määritetään käyttämällä setter-metodia, saadaan talteen getterin avulla ja verrataan oikeellisuuteen. Mahdollinen erottelu aktivoi `AssertionError`:in, joka korostaa erityistä ongelmaa.

4. **Virheiden raportointi**:
   Tester tarjoaa yksityiskohtaista palautetta kaikista validointivirheistä, kuten puuttuvat menetelmät, epäjohdonmukaiset oletusarvot tai ominaisuuksien väärinkonfiguroinnit.

## Testien kirjoittaminen `PropertyDescriptorTester`:llä {#writing-tests-with-propertydescriptortester}

Tässä esimerkki, joka osoittaa perusominaisuuksien validointia `AppLayout`-komponentille:

### Esimerkki: Perusvalidointi {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Oletus Otsikko");

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
      fail("PropertyDescriptor-testi epäonnistui: " + e.getMessage());
    }
  }
}
```

Tämä testi varmistaa automaattisesti:
- Että `drawerOpened`-ominaisuudella on voimassa olevat getter- ja setter-metodit.
- Että `headerTitle` oletusarvo on `"Oletus Otsikko"`.

## Kehittyneet käyttötapaukset annotaatioilla {#advanced-use-cases-with-annotations}

Monimutkaisemmissa tilanteissa `PropertyDescriptorTester` tukee annotaatioita ominaisuuksien mukauttamiseen tai sulkemiseen testauksesta.

### Sulje ominaisuudet `@PropertyExclude`:lla {#exclude-properties-with-propertyexclude}

Sulje ominaisuudet, jotka riippuvat ulkoisista järjestelmistä tai eivät ole olennaisia testille. Esimerkiksi:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Suljettu");
```

### Mukautettu metodit `@PropertyMethods`:lla {#customize-methods-with-propertymethods}

Määritä mukautettu getter, setter tai kohdeluokka, kun oletusnimityssäännöt eivät sovi:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Oletusarvo");
```
