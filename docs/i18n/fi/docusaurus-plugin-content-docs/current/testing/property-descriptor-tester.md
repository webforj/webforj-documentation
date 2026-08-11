---
sidebar_position: 4
title: PropertyDescriptorTester
description: >-
  Validate PropertyDescriptor fields on wrapped web components by checking
  getters, setters, and default values with PropertyDescriptorTester.
_i18n_hash: 5b14fba4a11a4da57a032123bd27be6b
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` webforJ:ssä yksinkertaistaa **kolmannen osapuolen verkkokomponenttien** testaamista, jotka on integroitu sovellukseesi. Se validoi, että `PropertyDescriptor`-luokassa määritellyt ominaisuudet ovat oikein linkitettyjä niiden getter- ja setter-menetelmiin ja varmistaa, että oletuskäyttäytyminen käsitellään johdonmukaisesti. Tämä työkalu on erityisen hyödyllinen kolmansien osapuolten komponenttien tarjoamien ominaisuuksien toiminnan vahvistamiseksi ilman toistuvaa testilogikkaa.

<ExperimentalWarning />

## Yleiskatsaus {#overview}

Työskennellessäsi kolmannen osapuolen verkkokomponenttien kanssa, on olennaista varmistaa, että ominaisuudet toimivat odotetulla tavalla. `PropertyDescriptorTester` automatisoi tämän prosessin validoimalla, että ominaisuudet:
- On oikein määritetty niiden getter- ja setter-menetelmiin.
- Säilyttävät odotetut oletusarvot ja mukautetut käyttäytymiset.
- Vältetään yleisiä integraatio-ongelmia, kuten virheellisiä ominaisuusnimien vastaavuuksia tai epäjohdonmukaisia oletusarvoja.

Työkalu tukee annotaatioita monimutkaisemmissa käyttötapauksissa, kuten merkityksettömien ominaisuuksien sulkemisessa tai mukautettujen getter- ja setter-menetelmien määrittämisessä, mikä tekee siitä monipuolisen vaihtoehdon integraatiotestaukselle.

## Kuinka `PropertyDescriptorTester` toimii {#how-propertydescriptortester-works}

Testausprosessi sisältää useita automatisoituja vaiheita:

1. **Luokan skannaus**:
   `PropertyDescriptorScanner` tunnistaa kaikki `PropertyDescriptor`-kentät komponenttiluokassa, jättäen automaattisesti huomiotta `@PropertyExclude`-annotaatiolla merkityt kentät.

2. **Metodien ratkaisu**:
   Vakiomaiset getter- ja setter-menetelmät havaitaan nimeämiskäytäntöjen perusteella (`get<PropertyName>`/`set<PropertyName>`). Ei-vakiototeutuksille annotaatiot, kuten `@PropertyMethods`, määrittelevät mukautettuja metodin nimiä tai kohdeluokkia.

3. **Validointi**:
   Oletusarvot määritetään setter-menetelmällä, haetaan getterillä ja verrataan oikeellisuuden varmistamiseksi. Kaikki epäjohdonmukaisuudet laukaisevat `AssertionError`-virheen, joka korostaa erityistä ongelmaa.

4. **Virheiden raportointi**:
   Testeri tarjoaa yksityiskohtaista palautetta kaikista validoitumisvirheistä, kuten puuttuvista menetelmistä, epäjohdonmukaisista oletusarvoista tai ominaisuuksien väärästä konfiguroinnista.

## Testien kirjoittaminen `PropertyDescriptorTester`-työkalulle {#writing-tests-with-propertydescriptortester}

Tässä on esimerkki, joka osoittaa perusominaisuuden validoimisen `AppLayout`-komponentille:

### Esimerkki: Perusvalidointi {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Oletusotsikko");

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
      fail("PropertyDescriptor-testissä tapahtui virhe: " + e.getMessage());
    }
  }
}
```

Tämä testi vahvistaa automaattisesti:
- Sen, että `drawerOpened`-ominaisuudella on voimassa olevat getter- ja setter-menetelmät.
- Sen, että `headerTitle`-ominaisuuden oletusarvo on `"Oletusotsikko"`.

## Edistyneet käyttötapaukset annotaatioilla {#advanced-use-cases-with-annotations}

Monimutkaisempia skenaarioita varten `PropertyDescriptorTester` tukee annotaatioita ominaisuuksien mukauttamiseen tai sulkemiseen testauksesta.

### Poista ominaisuuksia `@PropertyExclude`-annotaatiolla {#exclude-properties-with-propertyexclude}

Poista ominaisuudet, jotka riippuvat ulkoisista järjestelmistä tai eivät ole merkityksellisiä testille. Esimerkiksi:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Poistettu");
```

### Mukauta menetelmiä `@PropertyMethods`-annotaatiolla {#customize-methods-with-propertymethods}

Määritä mukautettu getter, setter tai kohdeluokka, kun oletuskäytäntöjä ei voida soveltaa:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Oletusarvo");
```
