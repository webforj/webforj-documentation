---
title: Routing
sidebar_position: 15
_i18n_hash: 8518a84a9c7a543b73cf4de16658f650
---
Routing in webforJ with Spring toimii samalla tavalla kuin tavallisissa webforJ-sovelluksissa. Käytät edelleen `@Route`-annotaatiota reittien määrittämiseen, samoja navigointimalleja ja samaa reittielinkaarta. Ainoa ero on se, että kun Spring on käytössä, reittisi voivat myös vastaanottaa Spring-beaneja konstruktorin injektoinnin kautta.

Kun navigoit reitille, webforJ luo reitti-instanssin - mutta kun Spring on luokkapolussa, se käyttää Springin säiliötä riippuvuuksien ratkaisemiseen. Tämä tarkoittaa, että reittisi voivat hyödyntää koko Springin ekosysteemin (palvelut, repositoryt, mukautetut beanit) samalla säilyttäen tuttua webforJ-reitityskäyttäytymistä.

:::tip[Opettele lisää riippuvuuden injektoimisesta]
Kattavan ymmärryksen saamiseksi riippuvuuden injektoimismalleista ja Springin IoC-säiliöstä, katso [Springin virallinen riippuvuuden injektoinnin dokumentaatio](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html).
:::

## Kuinka webforJ luo reittejä Springin kanssa {#how-webforj-creates-routes-with-spring}

webforJ käsittelee reittien luomista eri tavalla, kun Spring on käytössä. Kehyksen objektin luomismekanismi havaitsee Spring Bootin luokkapolussa ja delegoi Springin `AutowireCapableBeanFactory`:lle instanssien luomiseksi riippuvuuden injektoinnilla.

Luokkien, jotka löydetään `@Routify`-skannauksella, kohdalla webforJ luo aina uusia instansseja eikä koskaan käytä olemassa olevia Spring-beaneja. Jokainen käyttäjän navigointi saa puhtaan reitti-instanssin ilman jaettua tilaa. Luomisprosessi:

1. Käyttäjä navigoi reitille
2. webforJ pyytää uutta instanssia (jättää huomiotta olemassa olevat Spring-beani-määritelmät)
3. Springin tehdas luo instanssin ja injektoi konstruktoririippuvuudet
4. Reitti alustaa kaikki riippuvuudet tyydytettyinä

Tämä käyttäytyminen on tarkoituksellista ja poikkeaa tyypillisistä Spring-beaneista. Vaikka rekisteröisit reitin Spring-beanina `@Component`-annotaatiolla, webforJ jättää kyseisen bean-määritelmän huomiotta ja luo uuden instanssin jokaiselle navigoinnille.

## Spring-beanien käyttäminen reiteissä {#using-spring-beans-in-routes}

Reittisi luokilla ei tarvitse olla Spring-annotaatioita. Pelkkä `@Route`-annotaatio mahdollistaa sekä reittien löytämisen että riippuvuuksien injektoinnin:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  private final Div self = getBoundComponent();
  private final MetricsService metricsService;
  private final UserRepository userRepository;
  
  public DashboardView(MetricsService metricsService, UserRepository userRepository) {
    this.metricsService = metricsService;
    this.userRepository = userRepository;
    
    // Käytä injektoituja palveluita UI:n rakentamiseksi
    Div metricsPanel = new Div();
    metricsPanel.setText(metricsService.getCurrentMetrics());
    
    self.add(metricsPanel);
  }
}
```

Konstruktori voi vastaanottaa:
- Palveluita, jotka on merkitty `@Service`, `@Repository` tai `@Component`
- Spring Datan repositoryt (`JpaRepository`, `CrudRepository`)
- Konfiguraation arvoja `@Value`-annotaatioiden kautta
- Springin infrastruktuuribeaneja (`ApplicationContext`, `Environment`)
- Mitä tahansa beania, joka on rekisteröity Springin kontekstiin

## Työesimerkki {#working-example}

Tämä esimerkki osoittaa täydellisen riippuvuuden injektoimisskenaarion, jossa Spring-palvelu injektoidaan webforJ-reittiin. Palvelu hallitsee liiketoimintalogiikkaa, kun taas reitti huolehtii UI-esityksestä.

Ensinnäkin, määritä standardi Spring-palvelu käyttäen `@Service`-annotaatiota. Tätä palvelua hallitaan Springin säiliössä ja se on saatavilla injektoitavaksi:

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

Tässä esimerkissä `RandomNumberService` on standardi Spring-palvelu-bean. `HelloWorldView`-reitti ilmoittaa sen konstruktoriparametrina, ja Spring toimittaa automaattisesti palveluinstanssin, kun webforJ luo reitin.

Huomaa, että reittiluokka käyttää vain `@Route`-annotaatiota - ei tarvitse Springin stereotypeja, kuten `@Component` tai `@Controller`. Kun käyttäjä navigoi juuripolkuun `/`, webforJ:

1. Luo uuden instanssin `HelloWorldView`:stä 
2. Pyytää Springiä ratkaisemaan `RandomNumberService`-riippuvuuden
3. Siirtää palvelun konstruktorille
4. Reitti käyttää injektoitua palvelua hallitakseen painikkeen klikkauksia

Tämä malli toimii kaikkien Spring-beanien kanssa - palvelut, repositoryt, konfiguraatioluokat tai mukautetut komponentit. Riippuvuuden injektointi tapahtuu läpinäkyvästi, jolloin voit keskittyä UI:n rakentamiseen samalla kun Spring hallitsee johtamista.
