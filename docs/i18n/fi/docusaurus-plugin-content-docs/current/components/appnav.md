---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

`AppNav`-komponentti luo sivuvalikon `AppNavItem`-kirjaimista. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, pesiytyä vanhempien kohteiden alle muodostaen hierarkkisia valikoita ja sisältää kuvakkeita, merkkejä tai muita komponentteja antaakseen käyttäjille enemmän kontekstia yhdellä silmäyksellä.

<!-- INTRO_END -->

## Kohteiden lisääminen ja pesiminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai pesittyjä ryhmäotsikoita, jotka sisältävät lapsikohteita. Linkittömät ryhmäotsikot toimivat laajennettavina säiliöinä.

Käytä `addItem()`-metodia sisällyttääksesi kohteita valikkoon:

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
Ylimmän tason kohteet navigaatiopuun sisällä tarkoitetaan yleensä laajennettaviksi – eivät napsautettaviksi linkeiksi. Tällaisille kohteille asetettu `path` voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alikohteita sen sijaan, että navigoisivat muualle.

Jos haluat, että ryhmäotsikko laukaisee mukautetun toiminnon (kuten ulkoisten asiakirjojen avaamisen), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons), kohteen loppuun. Tämä pitää käyttökokemuksen johdonmukaisena ja siistinä.
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

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkymiä.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkkejä suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymät ovat rekisteröityjä [reittiin](../routing/overview), voit välittää luokan sen sijaan, että käytät kiinteää URL-osoitetta:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos annotoitu reittisi tukee [reitityksen parametrejä](../routing/route-patterns#named-parameters), voit myös välittää `ParametersBag`-objektin:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Välitä `ParametersBag` sisältääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteiden käytös {#target-behavior}

Ohjaa, kuinka linkit avautuvat käyttämällä `setTarget()`-metodia. Tämä on erityisen hyödyllistä ulkoisille linkeille tai ponnahdusikkunoille.

- **`SELF`** (oletus): Avaa nykyisessä näkymässä.
- **`BLANK`**: Avaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avaa pääselauskontekstissa.
- **`TOP`**: Avaa ylimmässä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja jälkiliite {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja jälkiliitekomponentteja. Käytä näitä visuaalisen selkeyden tarjoamiseksi kuvakkeilla, merkeillä tai painikkeilla.

- **Etuliite**: näkyy ennen etikettiä, hyödyllinen kuvakkeille.
- **Jälkiliite**: näkyy etikettien jälkeen, loistava merkeille tai toiminnoille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattisesti avautuvat ryhmät {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav`-komponentilla laajentaaksesi automaattisesti pesityt ryhmät, kun sovellus päivitetään.

```java
nav.setAutoOpen(true);
```

## Osion etiketit <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` on ei-interaktiivinen otsikko, joka nimeää joukon kohteita. Otsikko koskee kaikkia sen jälkeen tulevia kohteita, seuraavaan otsikkoon tai valikon loppuun asti, mikä antaa pitkän luettelon ylimmän tason kohteista luettavaksi muutamilla nimetyillä ryhmillä ilman niiden pesimistä.

Otsikot lisätään `add()`-metodilla, ei `addItem()`-metodilla, ja kutsujen järjestys määrittelee osiot:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

Valikko piilottaa etiketin automaattisesti, kun sen osiossa ei ole näkyviä kohteita, joten etiketti katoaa, kun [haku](#search) suodattaa sen kohteet tai kun kaikki ne on [kiinnitetty](#pinning) valikon yläosaan.

### Etuliite- ja jälkiliite-otsikot {#label-prefix-and-suffix}

Kuten `AppNavItem`, myös etiketti tukee etuliite- ja jälkiliitekomponentteja. Anna etuliite konstruktorille tai määritä kumpikin jälkeenpäin:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Alla olevassa esimerkissä on ryhmitelty valikko kolmen otsikon alle, joista ensimmäinen kantaa [`Icon`](./icon) etuliitteenä ja [`Badge`](./badge) jälkiliitteenä. Dashboard on ensimmäisen etiketin yläpuolella, joten se ei kuulu mihinkään osioon.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Kiinnittäminen <DocChip chip='since' label='26.01' /> {#pinning}

Kiinnittäminen mahdollistaa käyttäjän nostaa yhteyksien etsimäänsä kohteet ryhmään valikon yläosassa, jotta syvällä oleva valikko pystyy silti säilyttämään lyhyen suosikkilistan yhdellä napsautuksella. Se on oletuksena pois päältä. Ota se käyttöön kiinnittämisen asetusten kautta:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Kun se on käytössä, jokainen navigoitava lehtikohde näyttää kiinnityskytkimen. Kytkin paljastuu hiiren yli ja näppäimistön fokuksessa, joten se pysyy saatavilla ilman hiirtä. Sen aktivoiminen siirtää kohteen kiinnitettyyn ryhmään valikon yläosassa.

Muutama sääntö ohjaa, mitä voidaan kiinnittää ja kuinka ryhmä käyttäytyy:

- Vain navigoitavat lehtikohteet voidaan kiinnittää. Ryhmäotsikoita (kohteita, joilla on lapsia) ei koskaan voida kiinnittää.
- Kiinnitetty ryhmä näkyy vain, kun jokin on kiinnitetty, ja katoaa jälleen, kun viimeinen kohde on irrotettu.
- Irrottaminen palauttaa kohteen sen alkuperäiseen paikkaansa, mukaan lukien kohteet, jotka on pesitty useita tasoja syvälle ryhmiin.
- Kohde siirretään, ei kopioida, joten kaikki etuliite- tai jälkiliitein sisältö ja kaikki siihen liitetyt kuuntelijat jatkavat toimintaansa, kun se on kiinnitettyyn ryhmään.

Alla olevassa demonstaatiossa on kiinnitys käytössä, mukautetulla ryhmän nimellä ja Dashboard kiinnitetty latauksen aikana. Vie hiiren osoitin lehtikohteen päälle tai siirrä siihen keskittyminen, niin näet kiinnityskytkimen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Kohteen aloitus kiinnitettynä {#starting-an-item-pinned}

Aloita kohde kiinnitettynä asettamalla sen kiinnitystila. Käytä `isPinned()`-metodia lukeaksesi nykyisen tilan.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Kiinnittäminen on otettava käyttöön
`setPinned(true)` vaikuttaa vain, kun kiinnitys on otettu käyttöön `AppNav`:ssa `getPinning().setEnabled(true)` kautta. Ilman sitä kutsu ei vaikuta.
:::

### Kiinnitetyn ryhmän otsikko {#pinned-group-title}

Kiinnitetty ryhmä on oletusarvoisesti nimetty `Pinned`. Vaihda se sovelluksellesi sopivaksi:

```java
nav.getPinning().setTitle("Favorites");
```

### Kiinnitysnäppäimet {#pin-keys}

Jokaisella kiinnitettävällä kohteella on avain, joka tunnistaa sen pysyvyyden ja [kiinnitystapahtuman](#reacting-to-pin-changes). Kun et määritä yhtä, avain palauttaa kohteen polun, joten `getPinKey()` palauttaa aina käytettävän arvon.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Määritä erillinen avain, kun polku voi muuttua ajon aikana. Vakaa avain pitää kiinnityksen oikeassa kohteessa osoitteen latausten välillä, vaikka sen URL-osoite muuttuisikin.

### Automaattinen tallennus paikalliseen tallennukseen {#autosave}

Kiinnitykset elävät vain nykyisen sivun näkymässä, ellei niitä tallenneta. Automaattinen tallennus on yksinkertaisin vaihtoehto: se tallentaa kiinnitettyjen kohteiden joukon selaimen paikalliseen tallennukseen ja palauttaa ne uudelleenlatauksessa. Se on oletuksena pois päältä. Se tarvitsee vakaan `id` (tai nimen) komponentille tallennusavaimeksi, ja `AppNav(String id)` -konstruktoripara on kätevä tapa asettaa yksi:

```java
AppNav nav = new AppNav("main-nav"); // antaa automaattisesti tallennukselle vakaan tallennusavaimen
nav.getPinning().setAutosave(true);
```

:::info Automaattinen tallennus tarvitsee id:n
Ilman `id` (tai nimeä) komponentilla, automaattinen tallennus ei tee mitään hiljaa, koska sillä ei ole vakaata avainta, jota tallentaa. Pysyvyyys on selaimen mukaan, joten kiinnitykset eivät seuraa käyttäjää toiseen laitteeseen tai selaimeen.
:::

### Mukautettu pysyvyys {#custom-persistence}

Jos haluat pysyvyyden, jonka voit hallita, esimerkiksi käyttäjä per palvelin, kytke automaattinen tallennus pois ja käytä itse [kiinnitystapahtumaa](#reacting-to-pin-changes) ja `setPinned`:

```java
nav.getPinning().setAutosave(false);

// tallenna nykyinen joukko kiinnitettyjä avaimia aina kun se muuttuu
nav.onPin(event -> savePins(event.getKeys()));

// latauksen yhteydessä, palauttaa jokainen tallennettu avain
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reaktio kiinnitysmuutoksiin {#reacting-to-pin-changes}

Kiinnitystapahtuma laukaisee aina, kun kohde kiinnitetään tai irrotetaan. Se sisältää muuttuneen kohteen, sen avaimen, uuden kiinnitystilan ja täydellisen järjestetyn joukon kiinnitettyjä avaimia:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // muuttunut kohde, tai null, jos se ei ole enää valikossa
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // kaikki kiinnitetyt avaimet kiinnitettyjä järjestyksessä
});
```

`getItem()` ratkaisee kohteen vertaamalla sen kiinnitysavainta, ja palauttaa `null`, kun kohde ei ole enää osa navigaatiota.

### Kiinnityssymbolit {#pin-icons}

Kytkin käyttää sisäänrakennettua `dwc:pin` kuvaketta, kun kohde on irrotettu ja `dwc:pinned-off`, kun se on kiinnitetty. Vaihda omaan `setUnpinnedIcon` ja `setPinnedIcon`, jotka hyväksyvät mikä tahansa `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Kiinnityskytkin kosketusnäytöillä {#pin-toggle-on-touchscreens}

Kosketusnäytöillä ei ole hiiren yli näytettävää kiinnityskytkintä, joten kytkin on oletuksena piilotettu. Pidä se näkyvissä ja helposti painettavana kosketusnäytöillä `setTouchVisible(true)`-metodilla:

```java
nav.getPinning().setTouchVisible(true);
```

## Haku <DocChip chip='since' label='26.01' /> {#search}

Hakukenttä suodattaa valikkoa kohteen etiketin mukaan käyttäjän kirjoittaessa. Se on oletuksena pois päältä. Voit näyttää sen ja antaa sille paikkamerkin hakukonfiguraation kautta:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Hae");
```

Kun käyttäjä kirjoittaa, valikko suodattaa kohteita etiketin mukaan, avaa minkä tahansa ryhmän, joka sisältää osuman, ja näyttää tyhjää viestiä, kun mitään ei löydy. Kiinnitetyt oikopolut pysyvät näkyvissä haun aikana, joten käyttäjän suosikit pysyvät yhden napsautuksen päässä jopa suodatuksen aikana.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Tyhjät viestit {#search-empty-message}

Määritä viesti, joka näytetään, kun haku ei palauta tuloksia. Pelkkä teksti renderöidään tekstinä:

```java
nav.getSearch().setEmptyMessage("Ei kohteita löytynyt");
```

### Haun ajaminen omasta kentästäsi {#custom-search-box}

Piilota sisäänrakennettu kenttä ja syötä suodatus omasta syötteestäsi. Työnnä nykyinen termi `setTerm`-metodin avulla:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Reagoi käyttäjän kirjoittamiin sisäänrakennettuun kenttään hakutapahtuman avulla:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## `AppNavItem`in tyylitys {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
