---
title: Routing
sidebar_position: 15
description: >-
  Inject Spring services and repositories into webforJ @Route classes through
  constructor injection while keeping a fresh instance per navigation.
_i18n_hash: 4bef970301ebc7072162c3dc95b6e544
---
Routing in webforJ with Spring toimii täsmälleen samalla tavalla kuin tavallisissa webforJ-sovelluksissa. Käytät edelleen `@Route`-annotaatiota reittien määrittämiseen, samoja navigointimalleja ja samaa reittielämää. Ainoa ero on, että kun Spring on läsnä, reittisi voivat myös vastaanottaa Spring-beaneja konstruktorin injektoinnin kautta.

Kun navigoit reitille, webforJ luo reittiesimerkin - mutta kun Spring on luokkapolussa, se käyttää Springin konttia riippuvuuksien ratkaisemiseen. Tämä tarkoittaa, että reittisi voivat käyttää kaiken Springin ekosysteemin voimaa (palvelut, varastot, mukautetut beanit) säilyttäen tuttavan webforJ-reittikäyttäytymisen.

:::tip[Opi lisää riippuvuuksien injektoinnista]
Kattavan ymmärryksen saamiseksi riippuvuuksien injektointimalleista ja Springin IoC-kontista, katso [Springin virallinen riippuvuuksien injektoinnin dokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Kuinka webforJ luo reittejä Springin avulla {#how-webforj-creates-routes-with-spring}

webforJ käsittelee reittien luomista eri tavalla, kun Spring on läsnä. Kehitysympäristön objektin luomismekanismi havaitsee Spring Bootin luokkapolussa ja delegoi Springin `AutowireCapableBeanFactory` -toiminnolle instanssien luomisen riippuvuuden injektoinnilla.

Luokille, jotka löydetään `@Routify`-skannauksen kautta, webforJ luo aina uusia instansseja eikä koskaan käytä olemassa olevia Spring-beaneja. Jokainen käyttäjän navigointi saa puhtaan reittiesimerkin ilman jaettua tilaa. Luomisprosessi:

1. Käyttäjä navigoi reitille
2. webforJ pyytää uutta instanssia (ohittaen kaikki olemassa olevat Spring-bean määrittelyt)
3. Springin tehdas luo instanssin ja injektoi konstruktorin riippuvuudet
4. Reitti alustetaan kaikkien riippuvuuksien tyydyttämiseksi

Tämä käyttäytyminen on tarkoituksellista ja eroaa tyypillisistä Spring-beaneista. Vaikka rekisteröisit reitin Spring-beanina `@Component`-annotaatiolla, webforJ ohittaa kyseisen bean-määrittelyn ja luo uuden instanssin jokaiselle navigoinnille.

## Spring-beanien käyttö reiteissä {#using-spring-beans-in-routes}

Reittiluokkasi eivät vaadi Spring-annotaatioita. Pelkkä `@Route`-annotaatio mahdollistaa sekä reittien löytymisen että riippuvuuksien injektoinnin:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {

  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;

  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;

    // Käytä injektoituja palveluja UI:n rakentamiseen
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());

    self.add(metricsPanel);
  }
}
```

Konstruktorin voi vastaanottaa:
- Palvelut, jotka on merkitty `@Service`, `@Repository` tai `@Component`
- Spring Data -varastot (`JpaRepository`, `CrudRepository`)
- Määritysarvot `@Value`-annotaatioiden kautta
- Spring-infrastruktuurin beanit (`ApplicationContext`, `Environment`)
- Mikä tahansa bean, joka on rekisteröity Spring-kontextiin

## Töissä oleva esimerkki {#working-example}

Tämä esimerkki havainnollistaa täydellistä riippuvuuksien injektoinnin skenaariota, jossa Spring-palvelu injektoidaan webforJ-reittiin. Palvelu hallitsee liiketoimintalogiikkaa samalla kun reitti hoitaa UI-esityksen.

Ensiksi, määritä vakio Spring-palvelu käyttäen `@Service`-annotaatiota. Tämä palvelu hallitaan Springin kontissa ja se on saatavilla injektoitavaksi:

```java title="RandomNumberService.java"
@Service
public class RandomNumberService {

  public Integer getRandomNumber() {
    return (int) (Math.random() * 100);
  }
}
```

```java title="HelloWorldView.java"
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private Button btn = new Button("Generoi satunnainen numero");

  public HelloWorldView(RandomNumberService service) {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(TablerIcon.create("dice"))
        .setTheme(ButtonTheme.GRAY)
        .addClickListener(e -> {
          Toast.show("Uusi satunnainen numero on " + service.getRandomNumber(), Theme.SUCCESS);
        });

    self.add(btn);
  }
}
```

Tässä esimerkissä `RandomNumberService` on vakio Spring-palvelubean. `HelloWorldView` -reitti julistaa sen konstruktorin parametrina, ja Spring tarjoaa automaattisesti palveluinstanssin, kun webforJ luo reitin.

Huomaa, että reittiluokka käyttää vain `@Route`-annotaatiota - mitään Springin stereotypioita, kuten `@Component` tai `@Controller`, ei tarvita. Kun käyttäjä navigoi juuripolkuun `/`, webforJ:

1. Luo uuden instanssin `HelloWorldView`
2. Pyytää Springiä ratkaisemaan `RandomNumberService` -riippuvuuden
3. Siirtää palvelun konstruktorille
4. Reitti käyttää injektoitua palvelua nappien klikkausten käsittelyyn

Tämä malli toimii minkä tahansa Spring-beanin kanssa - palvelut, varastot, kokoonpanoluokat tai mukautetut komponentit. Riippuvuuksien injektointi tapahtuu läpinäkyvästi, jolloin voit keskittyä UI:si rakentamiseen, kun Spring hallitsee kytkentää.
