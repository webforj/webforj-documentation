---
sidebar_position: 4
title: PropertyDescriptorTester
_i18n_hash: 663a49d7134273428b9b7648a1fd321e
---
<DocChip chip='since' label='23.06' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/component/element/PropertyDescriptorTester" top='true'/>

`PropertyDescriptorTester` webforJ:ssä helpottaa **kolmannen osapuolen web-komponenttien** testiä, jotka on integroitu sovellukseesi. Se validoi, että `PropertyDescriptor`:illa määritellyt ominaisuudet on oikein liitetty niiden hakija- ja asettajametodeihin, ja varmistaa, että oletus käyttäytyminen käsitellään johdonmukaisesti. Tämä työkalu on erityisen hyödyllinen kolmannen osapuolen komponenttien paljastamien ominaisuuksien toiminnallisuuden vahvistamiseen ilman toistuvaa testilogiikkaa.

<ExperimentalWarning />

## Yleiskatsaus {#overview}

Kun työskentelet kolmannen osapuolen web-komponenttien kanssa, on tärkeää varmistaa, että ominaisuudet käyttäytyvät odotetusti. `PropertyDescriptorTester` automatisoi tämän prosessin validoimalla, että ominaisuudet:
- On oikein liitetty niiden hakija- ja asettajametodeihin.
- Säilyttävät odotetut oletusarvot ja mukautetut käyttäytymiset.
- Vältetään yleisiä integraatio-ongelmia, kuten epäsopivia ominaisuuden nimiä tai epäjohdonmukaisia oletuksia.

Työkalu tukee annotaatioita monimutkaisemmille käyttötilanteille, kuten merkityksettömien ominaisuuksien ulkopuolelle jättämiselle tai mukautettujen hakija- ja asettajametodien määrittämiselle, mikä tekee siitä monipuolisen vaihtoehdon integraatiotesteille.

## Kuinka `PropertyDescriptorTester` toimii {#how-propertydescriptortester-works}

Testausprosessi sisältää useita automatisoituja vaiheita:

1. **Luokan skannaus**: 
   `PropertyDescriptorScanner` tunnistaa kaikki `PropertyDescriptor`-kentät komponenttiluokassa, ja jättää automaattisesti pois kentät, joilla on annotaatio `@PropertyExclude`.

2. **Menetelmien ratkaisu**:
   Vakiokäyttäjä- ja asettajametodit havaitaan nimikäytäntöjen perusteella (`get<PropertyName>`/`set<PropertyName>`). Epästandardeissa toteutuksissa annotaatiot kuten `@PropertyMethods` määrittelevät mukautetut metodin nimet tai kohdeluokat.

3. **Validointi**:
   Oletusarvot asetetaan asettajametodin avulla, haetaan hakijametodilla ja verrataan oikeellisuuden varmistamiseksi. Kaikki epäyhtenäisyys laukaisee `AssertionError`-virheen, joka korostaa erityistä ongelmaa.

4. **Virheiden raportointi**:
   Testeri tarjoaa yksityiskohtaista palautetta kaikista vahvistusvirheistä, kuten puuttuvista metodeista, epäjohdonmukaisista oletuksista tai ominaisuuden vääristä konfiguraatioista.

## Testien kirjoittaminen `PropertyDescriptorTester`-kanssa {#writing-tests-with-propertydescriptortester}

Tässä on esimerkki, joka demonstroi perusominaisuuksien vahvistamista `AppLayout`-komponentille:

### Esimerkki: Perusvalidointi {#example-basic-validation}

```java title="MyComponent.java"
public class MyComponent extends ElementCompositeContainer {
  private final PropertyDescriptor<Boolean> drawerOpened =
      PropertyDescriptor.property("drawerOpened", false);
  private final PropertyDescriptor<String> headerTitle =
      PropertyDescriptor.property("headerTitle", "Oletusotsikko");

  // asetukset ja haku
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

Tämä testi vahvistaa automaattisesti:
- Sen, että `drawerOpened`-ominaisuudella on voimassa olevat hakija- ja asettajametodit.
- Sen, että `headerTitle` oletusarvo on `"Oletusotsikko"`.

## Monimutkaiset käyttötilanteet annotaatioilla {#advanced-use-cases-with-annotations}

Monimutkaisempia skenaarioita varten `PropertyDescriptorTester` tukee annotaatioita ominaisuuksien mukauttamiseen tai testaamisesta sulkemiseen.

### Poista ominaisuudet käytöstä `@PropertyExclude`-annotaatiolla {#exclude-properties-with-propertyexclude}

Poista käytöstä ominaisuudet, jotka riippuvat ulkoisista järjestelmistä tai eivät ole relevantteja testille. Esimerkiksi:

```java
@PropertyExclude
private final PropertyDescriptor<String> excludedProperty =
  PropertyDescriptor.property("excludedProperty", "Poissuljettu");
```

### Mukauta metodeja `@PropertyMethods`-annotaatiolla {#customize-methods-with-propertymethods}

Määritä mukautettu hakija, asettaja tai kohdeluokka, kun oletusnimisäännöt eivät päde:

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue", target = InnerClass.class)
private final PropertyDescriptor<String> customProperty =
  PropertyDescriptor.property("customProperty", "Oletusarvo");
```
