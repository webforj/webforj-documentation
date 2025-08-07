---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 0942852e0373dae7b5539f612ede0709
---
Riippuvuuksien injektointi (DI) on prosessi, jossa objektit määrittävät riippuvuutensa konstruktorin argumenttien kautta sen sijaan, että ne luovat tai etsivät riippuvuuksia itse. Spring-kontti injektoi nämä riippuvuudet objektia luodessaan, mikä johtaa siistimpään koodiin ja parempaan irrotteisuuteen komponenttien välillä.

Kun käytät Spring Bootia webforJ:n kanssa, framework tunnistaa Spring-kontekstin ja mukauttaa objektin luontia hyödyntämään Springin riippuvuuksien injektointia. Tämä tarkoittaa, että reittiluokkasi voivat ilmoittaa riippuvuuksia palveluista, tietovarastoista ja muista Springin komponenteista konstruktoriparametrien kautta.

:::tip[Opettele lisää riippuvuuksien injektoinnista]
Laajan ymmärryksen saamiseksi riippuvuuksien injektointimalleista ja Springin IoC-kontista, katso [Springin virallinen riippuvuuksien injektoinnin dokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Kuinka webforJ luo reittejä Springin avulla {#how-webforj-creates-routes-with-spring}

webforJ käsittelee reittien luomista eri tavalla, kun Spring on läsnä. Frameworkin objektin luontimekanismi tunnistaa Spring Bootin luokkatiessä ja delegoi Springin `AutowireCapableBeanFactory`:lle riippuvuuksien injektoinnilla instanssien luomisen.

Luokille, jotka on löydetty `@Routify`-skannaamisen kautta, webforJ luo aina uusia instansseja eikä koskaan käytä olemassa olevia Springin komponenteja. Jokaiselle käyttäjän navigoinnille annetaan puhdas reitti-instanssi, jossa ei ole jaettua tilaa. Luontiprosessi:

1. Käyttäjä navigoi reitille
2. webforJ pyytää uutta instanssia (huomioimatta olemassa olevia Spring-komponenttien määritelmiä)
3. Springin tehdas luo instanssin ja injektoi konstruktorin riippuvuudet
4. Reitti alustuu kaikki riippuvuudet tyydyttävinä

Tämä käyttäytyminen on tahallista ja eroaa tyypillisistä Spring-komponenteista. Vaikka rekisteröisit reitin Spring-komponenttina käyttämällä `@Component`, webforJ ohittaa kyseisen komponentin määritelmän ja luo puhtaan instanssin jokaiselle navigoinnille.

## Springin komponenttien käyttäminen reiteissä {#using-spring-beans-in-routes}

Reittiluokkasi eivät vaadi Spring-annotaatioita. `@Route`-annotaatio yksin mahdollistaa sekä reittien löydön että riippuvuuksien injektoinnin:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Hyödynnä injektoituja palveluja käyttöliittymän rakentamiseksi
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Konstruktorit voivat vastaanottaa:
- Palveluita, joilla on `@Service`, `@Repository` tai `@Component` annotaatio
- Spring Data -tietovarastoja (`JpaRepository`, `CrudRepository`)
- Konfigurointiarvoja `@Value`-annotaatioiden kautta
- Springin infrastruktuurikomponentteja (`ApplicationContext`, `Environment`)
- Mitä tahansa komponenttia, joka on rekisteröity Spring-kontekstiin

## Toimiva esimerkki {#working-example}

Tämä esimerkki osoittaa täydellisen riippuvuuksien injektointitilanteen, jossa Spring-palvelu injektoidaan webforJ-reittiin. Palvelu hallitsee liiketoimintalogiikkaa, kun taas reitti käsittelee käyttöliittymän esitystä.

Ensinnäkin, määritä standardi Spring-palvelu `@Service`-annotaatiolla. Tämä palvelu hallitaan Springin kontissa ja se on saatavilla injektoitavaksi:

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

  private FlexLayout self = getBoundComponent();
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

Tässä esimerkissä `RandomNumberService` on standardi Spring-palvelukomponentti. `HelloWorldView`-reitti ilmoittaa sen konstruktoriparametrina, ja Spring toimittaa automaattisesti palveluinstanssin, kun webforJ luo reitin.

Huomaa, että reittiluokka käyttää vain `@Route`-annotaatiota - ei tarvita mitään Springin stereotypioita, kuten `@Component` tai `@Controller`. Kun käyttäjä navigoi juuriin `/`, webforJ:

1. Luo uuden instanssin `HelloWorldView`
2. Pyytää Springiltä `RandomNumberService`-riippuvuuden ratkaisemista
3. Siirtää palvelun konstruktorille
4. Reitti hyödyntää injektoitua palvelua painikkeen klikkauksen käsittelemiseksi

Tämä malli toimii minkä tahansa Spring-komponentin kanssa - palvelut, tietovarastot, konfigurointiluokat tai mukautetut komponentit. Riippuvuuksien injektointi tapahtuu huomaamattomasti, jolloin voit keskittyä käyttöliittymäsi rakentamiseen samalla kun Spring huolehtii johdotuksesta.
