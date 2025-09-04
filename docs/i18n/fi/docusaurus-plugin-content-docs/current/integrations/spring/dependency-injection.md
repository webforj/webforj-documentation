---
title: Dependency Injection
sidebar_position: 15
_i18n_hash: 65cff7dec35cab6a33e0d402512c8f86
---
Dependency injection (DI) on prosessi, jossa objektit määrittelevät riippuvuutensa konstruktorin argumenttien avulla sen sijaan, että ne luovat tai etsivät riippuvuuksia itse. Spring-kontti injektoi nämä riippuvuudet objektia luotaessa, mikä johtaa puhtaampaan koodiin ja parempaan irrotukseen komponenttien välillä.

Kun käytetään Spring Bootia webforJ:n kanssa, kehys havaitsee Spring-kontekstin ja sovittaa objektien luomisen käyttämään Springin riippuvuuden injektointia. Tämä tarkoittaa, että reittiluokkasi voivat julistaa riippuvuuksia palveluista, varastoista ja muista Spring-beaneista konstruktori-parametrien kautta.

:::tip[Opettele lisää riippuvuuden injektoinnista]
Saadaksesi kattavan käsityksen riippuvuuden injektointimalleista ja Springin IoC-kontista, katso [Springin virallinen riippuvuuden injektointidokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Kuinka webforJ luo reittejä Springin kanssa {#how-webforj-creates-routes-with-spring}

webforJ käsittelee reittien luomista eri tavalla, kun Spring on läsnä. Kehyksen objektin luomismekanismi havaitsee Spring Bootin luokkatiessä ja siirtää luomisen Springin `AutowireCapableBeanFactory`:lle riippuvuuden injektointia varten.

Luokille, jotka löydetään `@Routify`-skannaamisen kautta, webforJ luo aina uusia instansseja eikä koskaan käytä olemassa olevia Spring-beaneja. Jokainen käyttäjän navigointi saa puhtaan reittinstanssin ilman jaettua tilaa. Luomusprosessi:

1. Käyttäjä navigoi reitille
2. webforJ pyytää uutta instanssia (ohittaen kaikki olemassa olevat Spring-bean-määritelmät)
3. Springin tehdas luo instanssin ja injektoi konstruktorin riippuvuudet
4. Reitti alustuu kaikki riippuvuudet tyydytettyinä

Tämä käyttäytyminen on tahallista ja poikkeaa tyypillisistä Spring-beaneista. Vaikka rekisteröisit reitin Spring-beanina `@Component`-annotaatiolla, webforJ ohittaa sen bean-määritelmän ja luo uuden instanssin jokaiselle navigoinnille.

## Spring-beanien käyttäminen reiteissä {#using-spring-beans-in-routes}

Reittiluokkasi eivät vaadi Spring-annotaatioita. Pelkkä `@Route`-annotaatio mahdollistaa sekä reittien löytämisen että riippuvuuden injektoinnin:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Käytä injektoituja palveluita rakentamaan käyttöliittymä
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    getBoundComponent().add(metricsPanel);
  }
}
```

Konstruktori voi vastaanottaa:
- Palveluja, jotka on merkitty `@Service`, `@Repository` tai `@Component`
- Spring Data -varastoja (`JpaRepository`, `CrudRepository`)
- Konfiguraatioarvoja `@Value`-annotaatioiden kautta
- Spring-infrastruktuuribeaneja (`ApplicationContext`, `Environment`)
- Mitä tahansa beania, joka on rekisteröity Spring-kontekstiin

## Toimiva esimerkki {#working-example}

Tämä esimerkki havainnollistaa täydellistä riippuvuuden injektointitapausta, jossa Spring-palvelu injektoidaan webforJ-reittiin. Palvelu hallitsee liiketoimintalogiikkaa, kun taas reitti käsittelee käyttöliittymän esitystä.

Ensiksi määritä standardi Spring-palvelu käyttäen `@Service`-annotaatiota. Tämä palvelu hallitaan Springin kontissa ja on saatavilla injektoitavaksi:

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

Tässä esimerkissä `RandomNumberService` on standardi Spring-palvelubeani. `HelloWorldView`-reitti julistaa sen konstruktorin parametrina, ja Spring tarjoaa automaattisesti palveluinstanssin, kun webforJ luo reitin.

Huomaa, että reittiluokka käyttää vain `@Route`-annotaatiota - ei tarvita Springin stereotypeja kuten `@Component` tai `@Controller`. Kun käyttäjä navigoi juuripolkuun `/`, webforJ:

1. Luodaan uusi instanssi `HelloWorldView` 
2. Pyytää Springiltä `RandomNumberService`-riippuvuuden ratkaisemista
3. Välittää palvelun konstruktorille
4. Reitti käyttää injektoitua palvelua painonäppäinten käsittelyyn

Tämä malli toimii minkä tahansa Spring-beanin kanssa - palvelut, varastot, konfiguraatioluokat tai mukautetut komponentit. Riippuvuuden injektointi tapahtuu läpinäkyvästi, mikä mahdollistaa keskittymisen käyttöliittymän rakentamiseen samalla kun Spring hallitsee kytkentöjä.
