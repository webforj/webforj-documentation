---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

`AppNav`-komponentti luo sivupalkin navigointivalikon `AppNavItem`-kohdista. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, pesiytyä parenttikohtien alle muodostamaan hierarkkisia valikoita ja sisältää ikoneita, merkkejä tai muita komponentteja, jotka antavat käyttäjille enemmän kontekstia hetkessä.

<!-- INTRO_END -->

## Kohteiden lisääminen ja pesittäminen <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai pesittyjä ryhmäotsikoita, jotka sisältävät lapsikohteita. Ryhmäotsikot ilman linkkejä toimivat laajennettavina säiliöinä.

Käytä `addItem()`-metodia lisätäksesi kohteita navigointiin:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Ryhmäkohteiden linkittäminen
Ylin tason kohteet navigointipuussa tarkoitetaan tyypillisesti laajennettaviksi - eivät napsautettaviksi linkeiksi. Tällaiselle kohteelle `path`-asetuksen määrittäminen voi hämmentää käyttäjiä, jotka odottavat niiden avaavan alakohtia sen sijaan, että navigoivat muualle.

Jos haluat ryhmäotsikon laukaisevan mukautetun toiminnan (kuten avata ulkoiset asiakirjat), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons) kohteen liitteeksi. Tämä pitää käyttäjäkokemuksen johdonmukaisena ja siistinä.
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Kohteiden linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkökuntosia.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkit suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimelle](../routing/overview), voit välittää luokan sen sijaan, että käyttäisit kovakoodattua URL-osoitetta:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos merkitty reittisi tukee [reittiparametreja](../routing/route-patterns#named-parameters), voit myös välittää `ParametersBag`-objektin:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Välitä `ParametersBag` sisällyttääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteiden käyttäytyminen {#target-behavior}

Säädä, miten linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisten linkkien tai erikoisnäkymien kohdalla.

- **`SELF`** (oletus): Avaa nykyisessä näkymässä.
- **`BLANK`**: Avaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avaa vanhempaan selailukontekstiin.
- **`TOP`**: Avaa ylimmän tason selailukontekstiin.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefiksi ja suffiksi {#prefix-and-suffix}

`AppNavItem` tukee prefiksi- ja suffiksi-komponentteja. Käytä näitä antaaksesi visuaalista selkeyttä ikoneilla, merkeillä tai painikkeilla.

- **Prefiksi**: näkyy ennen etikettiä, hyvä ikoneille.
- **Suffiksi**: näkyy etikettiä jälkeen, loistava merkeille tai toiminnoille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattisesti avautuvat ryhmät {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav`-komponentissa laajentaaksesi automaattisesti pesittyjä ryhmiä, kun sovellus käynnistetään uudelleen.

```java
nav.setAutoOpen(true);
```

## Osion etiketit <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` on ei-interaktiivinen otsikko, joka nimeää joukon kohteita. Otsikko koskee jokaista sitä seuraavaa kohdetta, seuraavaan otsikkoon tai valikon loppuun asti, mikä mahdollistaa pitkän luettelon ylimmän tason kohteita lukea muutamaksi nimettyyn ryhmään ilman niiden pesittämistä.

Otsikoita lisätään `add()`-menetelmällä eikä `addItem()`-menetelmällä, ja kutsujen järjestys määrittelee osiot:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

Valikko piilottaa automaattisesti otsikon, kun sen osiossa ei ole näkyviä kohteita, joten otsikko katoaa, kun [haku](#search) suodattaa kohteita tai kun kaikki niistä on [kiinnitetty](#pinning) valikon ylle.

### Otsikon prefiksi ja suffiksi {#label-prefix-and-suffix}

Kuten `AppNavItem`, otsikko tukee prefiksi- ja suffiksikomponentteja. Anna prefiksi konstruktorille tai aseta jompikumpi myöhemmin:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Esimerkki alla ryhmittää valikon kolmen otsikon alle, joista ensimmäinen sisältää [`Icon`](./icon) prefiksin ja [`Badge`](./badge) suffiksin. Dashboard sijaitsee ensimmäisen otsikon yllä, joten se ei kuulu mihinkään osioon.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Kiinnittäminen <DocChip chip='since' label='26.01' /> {#pinning}

Kiinnitys mahdollistaa käyttäjän nostaa eniten tavoitetut kohteet ryhmään navigoinnin yläosaan, jotta syvä valikko säilyttää lyhyen suosikkilistan yhden napsautuksen päässä. Se on oletuksena pois päältä. Ota se käyttöön pinnoitusasetusten kautta:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Kun se on käytössä, jokaisessa navigoitavassa lehtikohteessa on pin-nappi. Nappi ilmestyy hoveriin ja näppäimistön keskittymiseen, joten se pysyy saavutettavissa ilman hiirtä. Sen aktivoiminen siirtää kohteen kiinnitettyyn ryhmään valikon ylle.

Muutama sääntö ohjaa, mitä voidaan kiinnittää ja kuinka ryhmä käyttäytyy:

- Vain navigoitavat lehtikohteet ovat kiinnitettäviä. Ryhmäotsikoita (kohteet, joilla on lapsia) ei koskaan voida kiinnittää.
- Kiinnitetty ryhmä näkyy vain, kun jotain on kiinnitetty, ja katoaa, kun viimeinen kohde on irrotettu.
- Irrottaminen palauttaa kohteen sen tarkkaan alkuperäiseen paikkaan, mukaan lukien kohteet, jotka on pesitetty useita tasoja syvälle ryhmiin.
- Kohde siirretään, ei kopioida, joten kaikki prefiksit tai suffiksit sekä niihin liitetyt kuuntelijat toimivat, kun se on kiinnitetty ryhmään.

Alla oleva demo on aktivoitu kiinnitys mukautetulla ryhmän nimellä ja Dashboard kiinnitettynä latauksen yhteydessä. Hoveroi tai keskity lehden kohteeseen paljastaaksesi sen pin-napin.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Kohteen aloittaminen kiinnitettynä {#starting-an-item-pinned}

Aloita kohde kiinnitettynä asettamalla sen kiinnitetty tila. Käytä `isPinned()` lukeaksesi nykyinen tila.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Kiinnitys on oltava käytössä
`setPinned(true)` -asetus vaikuttaa vain, kun kiinnitys on käytössä `AppNav`-komponentissa `getPinning().setEnabled(true)` -kutsulla. Ilman sitä kutsu ei vaikuta mihinkään.
:::

### Kiinnitetyn ryhmän nimi {#pinned-group-title}

Kiinnitetty ryhmä on oletuksena nimeltään `Pinned`. Muuta se sopimaan sovellukseesi:

```java
nav.getPinning().setTitle("Favorites");
```

### Kiinnitysnäppäimet {#pin-keys}

Jokaisella kiinnitettävällä kohteella on avain, joka tunnistaa sen pysyvyyttä varten sekä [kiinnitystapahtumaa](#reacting-to-pin-changes) varten. Kun et aseta yhtä, avain palaa kohteen polkuun, joten `getPinKey()` palauttaa aina käyttökelpoisen arvon.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Aseta nimenomainen avain, kun polku voi muuttua ajoittain. Vakaa avain pitää kiinnityksen oikean kohteen mukana latauksilla, vaikka URL muuttuu.

### Automaattinen tallennus paikalliseen tallennustilaan {#autosave}

Kiinnitykset elävät vain nykyisessä sivun näkymässä, ellet tallenna niitä. Automaattinen tallennus on yksinkertaisin vaihtoehto: se tallentaa kiinnitettyjen kohteiden joukon selainikkunan paikalliseen tallennustilaan ja palauttaa ne latauksen yhteydessä. Se on oletuksena pois päältä. Se tarvitsee vakaan `id`:n (tai nimen) komponentille tallennusavaimeksi, ja `AppNav(String id)` -konstruktorilla on kätevä tapa asettaa yksi:

```java
AppNav nav = new AppNav("main-nav"); // antaa automaattiselle tallennukselle vakaan tallennusavaimen
nav.getPinning().setAutosave(true);
```

:::info Automaattinen tallennus tarvitsee id:n
Ilman `id`:tä (tai nimeä) komponentissa, automaattinen tallennus ei tee mitään hiljaa, koska sillä ei ole vakaata avainta tallentamiseksi. Pysyvyyttä on per selain, joten kiinnitykset eivät seuraa käyttäjää toiseen laitteeseen tai selaimeen.
:::

### Mukautettu pysyvyys {#custom-persistence}

Jos haluat toteuttaa pysyvyyttä itse, esimerkiksi käyttäjän mukaan palvelimella, kytke automaattinen tallennus pois päältä ja ohjaa sitä itse [kiinnitystapahtuman](#reacting-to-pin-changes) ja `setPinned`-menetelmän avulla:

```java
nav.getPinning().setAutosave(false);

// tallenna nykyinen kiinnitettyjen avaimien joukko aina, kun se muuttuu
nav.onPin(event -> savePins(event.getKeys()));

// latauksen yhteydessä palauta jokainen tallennettu avain
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reaktiot kiinnitysmuutoksiin {#reacting-to-pin-changes}

Kiinnitystapahtuma laukaisee aina, kun kohde on kiinnitetty tai irrotettu. Se sisältää muuttuneen kohteen, sen avaimen, uuden kiinnitettävän tilan ja koko järjestyksen kiinnitettyjen avaimien joukossa:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // muuttunut kohde, tai null, jos se ei ole enää navigaatiossa
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jokainen kiinnitetty avain kiinnitettynä järjestyksessä
});
```

`getItem()` ratkaisee kohteen vastaamalla sen kiinnitysavaimen kanssa ja palauttaa `null`, kun kohde ei enää ole osa navigointia.

### Kiinnityssymbolit {#pin-icons}

Vaihtoehto käyttää sisäänrakennettua `dwc:pin`-ikonia, kun kohde on kiinnittämätön, ja `dwc:pinned-off`, kun se on kiinnitetty. Vaihda omasi `setUnpinnedIcon` ja `setPinnedIcon` -menetelmien avulla, jotka hyväksyvät minkä tahansa `IconDefinition`-tyypin:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Kiinnitysnappi kosketusnäytöillä {#pin-toggle-on-touchscreens}

Kosketusnäytöillä ei ole hover-tilaa paljastaakseen kiinnityksen, joten napsautus on oletuksena piilotettu. Pidä se näkyvissä ja napautettavana kosketusnäytöillä asettamalla `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Haku <DocChip chip='since' label='26.01' /> {#search}

Hakukenttä suodattaa valikon kohteet etiketin mukaan käyttäjän kirjoittaessa. Se on oletuksena pois päältä. Voit näyttää sen ja antaa sille paikan hakukonfiguraation kautta:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Hae");
```

Kun käyttäjä kirjoittaa, navigointi suodattaa kohteita etiketin mukaan, avaa ryhmät, jotka sisältävät osuman, ja näyttää tyhjät viestit, kun mitään ei löydy. Kiinnitetyt pikakuvakkeet pysyvät näkyvissä haun aikana, joten käyttäjän suosikit ovat yhden napsautuksen päässä, jopa filtrausprosessin aikana.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Tyhjät viestit {#search-empty-message}

Aseta viesti, joka näkyy, kun haku ei palauta tuloksia. Pelkkä teksti renderöidään tekstinä:

```java
nav.getSearch().setEmptyMessage("Ei kohteita löytynyt");
```

### Haku omasta kentästäsi {#custom-search-box}

Piilota sisäänrakennettu kenttä ja syötä suodatus omasta syötteestäsi. Työnnä nykyinen termi `setTerm`-menetelmällä:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Kun reagoit, mitä käyttäjä kirjoittaa sisäänrakennettuun kenttään, kuuntele hakutapahtumaa:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## `AppNavItem` tyylitys {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
