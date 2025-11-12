---
title: Reititys
sidebar_position: 15
_i18n_hash: a5b11fb9cf05e74bd347faae48f167dd
---
Reititys webforJ:ssä Springin kanssa toimii täsmälleen samalla tavalla kuin tavallisissa webforJ-sovelluksissa. Käytät edelleen `@Route`- annotaatiota määrittääksesi reittejä, samoja navigointimalleja ja samaa reitin elinkaarikäyttäytymistä. Ainoa ero on se, että kun Spring on läsnä, reitit voivat myös vastaanottaa Springin osia konstruktori-injektioiden kautta.

Kun navigoit reitille, webforJ luo reitti-instanssin - mutta Springin ollessa luokkatiessä se käyttää Springin säiliötä riippuvuuksien ratkaisemiseen. Tämä tarkoittaa, että reittisi voivat hyödyntää koko Springin ekosysteemin voimaa (palvelut, varastot, mukautetut komponentit) säilyttäen samalla tutun webforJ-reitityskäyttäytymisen.

:::tip[Opettele lisää riippuvuuksien injektoinnista]
Saadaksesi kattavan käsityksen riippuvuuksien injektointimalleista ja Springin IoC-säiliöstä, katso [Springin virallinen riippuvuuksien injektointidokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Kuinka webforJ luo reittejä Springin kanssa {#how-webforj-creates-routes-with-spring}

webforJ käsittelee reittien luomista eri tavalla, kun Spring on läsnä. Kehyksen objektin luontimekanismi tunnistaa Spring Bootin luokkatiestä ja delegoi Springin `AutowireCapableBeanFactory`:lle riippuvuuksien injektoimiseen tarvittavien instanssien luomisen.

Luokkien, jotka löydetään `@Routify`-skannauksella, osalta webforJ luo aina uusia instansseja eikä koskaan käytä olemassa olevia Spring-komponentteja. Jokainen käyttäjän navigointi saa puhtaan reitti-instanssin ilman jaettua tilaa. Luontiprosessi:

1. Käyttäjä navigoi reitille
2. webforJ pyytää uutta instanssia (ohittaen kaikki olemassa olevat Spring-component määritelmät)
3. Springin tehdas luo instanssin ja injektoi konstruktorin riippuvuudet
4. Reitti alustuu kaikki riippuvuudet tyydytettyinä

Tämä käyttäytyminen on tarkoituksellista ja eroaa tyypillisistä Spring-komponenteista. Vaikka rekisteröisit reitin Spring-komponenttina käyttämällä `@Component`, webforJ ohittaa kyseisen komponenttimääritelmän ja luo uuden instanssin jokaiselle navigoinnille.

## Spring-komponenttien käyttäminen reiteissä {#using-spring-beans-in-routes}

Reittiluokkasi eivät vaadi Spring-anotaatioita. Pelkkä `@Route`-annotaatio mahdollistaa sekä reittien löytämisen että riippuvuuksien injektoimisen:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Käytä injektoituja palveluja käyttöliittymän rakentamiseen
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
- Mitä tahansa bean, joka on rekisteröity Spring-kontekstiin

## Toimiva esimerkki {#working-example}

Tämä esimerkki havainnollistaa täydellistä riippuvuuksien injektoinnin skenaariota, jossa Spring-palvelu injektoidaan webforJ-reittiin. Palvelu hallinnoi liiketoimintalogiikkaa, kun taas reitti käsittelee käyttöliittymän esittämistä.

Ensiksi määrittele standardi Spring-palvelu käyttämällä `@Service`-annotaatiota. Tämä palvelu hallinnoi Springin säiliö ja on saatavilla injektoitavaksi:

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

Tässä esimerkissä `RandomNumberService` on standardi Spring-palvelu-bean. `HelloWorldView`-reitti declaree sen konstruktoriparametrina, ja Spring toimittaa automaattisesti palveluinstanssin, kun webforJ luo reitin.

Huomaa, että reittiluokka käyttää vain `@Route`-annotaatiota - ei tarvita Springin stereotypioita kuten `@Component` tai `@Controller`. Kun käyttäjä navigoi juuripolkuun `/`, webforJ:

1. Luo uuden instanssin `HelloWorldView`:stä 
2. Pyytää Springiä ratkaisemaan `RandomNumberService`-riippuvuuden
3. Siirtää palvelun konstruktorille
4. Reitti käyttää injektoitua palvelua painikkeen klikkausten käsittelemiseen

Tämä malli toimii minkä tahansa Spring-beanin kanssa - palvelut, varastot, konfiguraatioluokat tai mukautetut komponentit. Riippuvuuksien injektointi tapahtuu läpinäkyvästi, mikä mahdollistaa sen, että voit keskittyä käyttöliittymäsi rakentamiseen, kun Spring hallinnoi liittämistä.
