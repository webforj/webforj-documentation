---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

`AppNav`-komponentti luo sivuvalikon `AppNavItem`-merkinnöistä. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, pesiytyä vanhempien kohteiden alle muodostaen hierarkkisia valikoita, ja sisältää ikoneita, merkkejä tai muita komponentteja, jotka antavat käyttäjille enemmän kontekstia silmäyksellä.

<!-- INTRO_END -->

## Kohteiden lisääminen ja pesiminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai pesittyjä ryhmäotsikoita, jotka sisältävät alikohteita. Ryhmäotsikot ilman linkkejä toimivat laajennettavina kontteina.

Käytä `addItem()`-metodia lisätäksesi kohteita valikkoon:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Linkittäminen ryhmäkohtaisiin kohteisiin
Ykkötason kohteet navigaatiopuun sisällä on yleensä tarkoitettu laajennettaviksi - eivät klikkattaviksi linkeiksi. `path`-asetuksen asettaminen tällaisille kohteille voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alikohteita sen sijaan, että navigoivat muualle.

Jos haluat ryhmäotsikon laukaisevan mukautetun toiminnon (kuten avattaessa ulkoista dokumentaatiota), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons), kohteen liitteeseen. Tämä pitää käyttäjäkokemuksen johdonmukaisena ja puhtaana.
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

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkölausekkeita.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkit suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröidyssä [reittilistassa](../routing/overview), voit välittää luokan kovakoodatun URL-osoitteen sijaan:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos annotoitu reittisi tukee [reittiparametreja](../routing/route-patterns#named-parameters), voit myös välittää `ParametersBag`-luokan:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Query-parametrien kanssa {#with-query-parameters}

Välitä `ParametersBag` sisällyttääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteiden käyttäytyminen {#target-behavior}

Hallitse kuinka linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisille linkeille tai eristyksille.

- **`SELF`** (oletus): Avaa nykyisessä näkymässä.
- **`BLANK`**: Avaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avaa vanhemmassa selauskontekstissa.
- **`TOP`**: Avaa ylimmällä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliitteet ja liitteet {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja liitekomponentteja. Käytä näitä antaaksesi visuaalista selkeyttä ikoneilla, merkeillä tai painikkeilla.

- **Etuliite**: näkyy ennen labelia, hyödyllinen ikoneille.
- **Liite**: näkyy labelin jälkeen, loistava merkeille tai toiminnoille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattisesti avautuvat ryhmät {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav`-komponentissa, jotta pesityt ryhmät laajenevat automaattisesti, kun sovellus uudelleenkäynnistetään.

```java
nav.setAutoOpen(true);
```

## Osion etiketit <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` on ei-interaktiivinen otsikko, joka titoloittaa joukon kohteita. Otsikko soveltuu jokaiselle sen jälkeiselle merkinnälle, seuraavaan otsikkoon tai valikon loppuun saakka, jolloin pitkä lista ykköstason kohteista voidaan lukea muutamina nimettyinä ryhminä ilman, että niitä pesitetään.

Otsikoita lisätään `add()`-metodilla eikä `addItem()`-metodilla, ja kutsujen järjestys määrittelee osiot:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

Valikko piilottaa otsikon automaattisesti, kun sen osiossa ei ole näkyviä kohteita, joten otsikko katoaa, kun [haku](#search) suodattaa sen kohteet pois tai kun kaikki niistä on [kiinnitetty](#pinning) valikon yläosaan.

### Otsikon etuliite ja liite {#label-prefix-and-suffix}

Kuten `AppNavItem`, myös otsikko tukee etuliite- ja liitekomponentteja. Anna etuliite konstruktorille tai aseta kumpi tahansa jälkikäteen:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Esimerkki alla ryhmittelee valikon kolmeen otsikkoon, joista ensimmäinen kantaa [`Icon`](./icon) etuliitettä ja [`Badge`](./badge) liitettä. Dashboard istuu ensimmäisen otsikon ylle, joten se ei kuulu mihinkään osioon.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Kiinnitys <DocChip chip='since' label='26.01' /> {#pinning}

Kiinnitys antaa käyttäjän nostaa eniten tavoittamansa kohteet ryhmään, joka on valikon yläosassa, joten syvä valikko pitää silti lyhyen listan suosikkeja yhdessä klikkauksessa. Se on oletuksena pois päältä. Kytke se päälle kiinnityskonfiguraation avulla:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Kun se on aktivoitu, jokaisella navigoitavalla lehtikohteella on kiinnitysohjaus. Ohjaus paljastuu hiiren ylle ja näppäimistön fokuksessa, joten se pysyy tavoitettavissa ilman hiirtä. Aktivoiminen siirtää kohteen kiinnitettyyn ryhmään valikon yläosassa.

Muutamat säännöt säätelevät, mitä voidaan kiinnittää ja miten ryhmä käyttäytyy:

- Vain navigoitavat lehtikohteet ovat kiinnitettäviä. Ryhmäotsikoita (kohteet, joilla on lapsia) ei voi koskaan kiinnittää.
- Kiinnitetty ryhmä näkyy vain silloin, kun jotain on kiinnitetty, ja katoaa taas, kun viimeinen kohde poistetaan kiinnityksestä.
- Kiinnittämättömyys palauttaa kohteen sen tarkkaan alkuperäiseen paikkaan, mukaan lukien usean tason syvälle ryhmiin pesitetyt kohteet.
- Kohde siirtyy, ei kopioidu, joten kaikki etuliite- tai liiteSisältö ja siihen kiinnitetyt kuuntelijat toimivat edelleen, kun se sijaitsee kiinnitetyssä ryhmässä.

Allaoleva demo on aktivoitu kiinnityksellä, mukautetulla ryhmän nimellä ja Dashboard kiinnitettynä latauksessa. Vie hiiri tai keskity lehtikohteeseen paljastaaksesi sen kiinnitsyn.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Kohteiden aloittaminen kiinnitettynä {#starting-an-item-pinned}

Aloita kohde kiinnitetyssä ryhmässä asettamalla sen kiinnitystila. Käytä `isPinned()` lukiaksesi nykyinen tila.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Kiinnityksen on oltava päällä
`setPinned(true)` tulee voimaan vain, kun kiinnitys on aktivoitu `AppNav`-komponentissa `getPinning().setEnabled(true)` avulla. Ilman sitä kutsulla ei ole vaikutusta.
:::

### Kiinnitetyn ryhmän otsikko {#pinned-group-title}

Kiinnitetyn ryhmän oletusotsikkona on `Pinned`. Muuta se sopimaan sovellukseesi:

```java
nav.getPinning().setTitle("Favorites");
```

### Kiinnitysnäppäimet {#pin-keys}

Jokaisella kiinnitettävällä kohteella on avain, joka tunnistaa sen pysyvyydelle ja [kiinnitys-tapahtumalle](#reacting-to-pin-changes). Kun et aseta yhtä, avain perustuu kohteen polkuun, joten `getPinKey()` palauttaa aina käytettävän arvon.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Aseta eksplisiittinen avain silloin, kun polku voi muuttua ajonaikana. Vakaa avain pitää kiinnityksen oikean kohteen kanssa oikean kohdalla uudelleen latauksessa, jopa jos sen URL siirtyy.

### Autosave paikalliseen tallennukseen {#autosave}

Kiinnitykset elävät vain nykyisen sivun katselun ajan, ellei niitä pysyvöitetä. Autosave on yksinkertaisin vaihtoehto: se tallentaa kiinnitettyjen kohteiden joukon selaimen paikalliseen tallennukseen ja palauttaa ne uudelleen latauksen aikana. Se on oletuksena pois päältä. Se vaatii vakaan `id` (tai nimen) komponentille tallennusavaimeksi, ja `AppNav(String id)`-konstruktori on kätevä tapa asettaa se:

```java
AppNav nav = new AppNav("main-nav"); // antaa autosave:lle vakaan tallennusavaimen
nav.getPinning().setAutosave(true);
```

:::info Autosave tarvitsee id:n
Ilman `id` (tai nimeä) komponentilla autosave ei tee mitään hiljaa, koska sillä ei ole vakaata avainta tallennettavaksi. Pysyvyyksien kesto on selainkohtainen, joten kiinnitykset eivät seuraa käyttäjää toiselle laitteelle tai selaimelle.
:::

### Mukautettu pysyvyys {#custom-persistence}

Oman kontrollin kautta pysyvyys, esimerkiksi käyttäjälle palvelimella, käännä autosave pois ja aja se itse [kiinnitys-tapahtuman](#reacting-to-pin-changes) ja `setPinned` avulla:

```java
nav.getPinning().setAutosave(false);

// tallenna nykyinen kiinnitettyjen avaimien joukko aina kun se muuttuu
nav.onPin(event -> savePins(event.getKeys()));

// latauksessa, palauttakaamme jokainen tallennettu avain
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reagointi kiinnitysmuutoksiin {#reacting-to-pin-changes}

Kiinnitystapahtuma laukaistuu aina, kun kohde kiinnitetään tai irrotetaan. Se kuljettaa muuttuneen kohteen, sen avaimen, uuden kiinnitystilan ja koko tilattujen kiinteiden avainten joukon:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // muuttunee kohde, tai null jos se ei ole enää valikossa
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // kaikki kiinnitetyt avaimet, kiinnitetty järjestys
});
```

`getItem()` ratkaisee kohteen vertaamalla sen kiinnitysnäppäimeen ja palauttaa `null`, kun kohde ei enää ole valikossa.

### Kiinnityspainikkeet {#pin-icons}

Vaihtoehto käyttää sisäänrakennettua `dwc:pin`-ikonia silloin, kun kohde on kiinnitetty ja `dwc:pinned-off`, kun se on kiinnitetty. Vaihda tapasi mukaan `setUnpinnedIcon` ja `setPinnedIcon`, jotka hyväksyvät mikä tahansa `IconDefinition`:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Kiinnityksen kytkin kosketusnäytöissä {#pin-toggle-on-touchscreens}

Kosketusnäytöillä ei ole hover-toimintoa paljastaa kiinnitystä, joten kytkin on oletuksena piilossa. Pidä se näkyvissä ja napsautettavissa kosketusnäytöillä asettamalla `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Haku <DocChip chip='since' label='26.01' /> {#search}

Hakukenttä suodattaa valikon kohteita etiketin mukaan käyttäjän kirjoittaessa. Se on oletuksena pois päältä. Voit näyttää sen ja antaa sille paikkamerkin hakukonfiguraation avulla:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Haku");
```

Kun käyttäjä kirjoittaa, navigointi suodattaa kohteita etiketin mukaan, avaa kaikki ryhmät, jotka sisältävät osuman, ja näyttää tyhjää viestiä, kun mitään ei löydy. Kiinnitetyt pikakäynnistyset pysyvät näkyvissä hakuaikana, joten käyttäjän suosikit pysyvät käden ulottuvilla jopa suodattamisen keskellä.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Tyhjät viestit {#search-empty-message}

Aseta viesti, joka näytetään, kun hakutulokset eivät tuota mitään. Pelkkä teksti renderoidaan tekstinä:

```java
nav.getSearch().setEmptyMessage("Ei kohteita löytynyt");
```

### Haitarin hakua omasta kentästä {#custom-search-box}

Piilota sisäänrakennettu kenttä ja syötä suodatus omasta syötteestäsi. Työnnä nykyinen termi `setTerm`-metodin kautta:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Reagoidaksesi käyttäjän teksteihin sisäänrakennetussa kentässä, kuuntele hakutapahtumalle:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## `AppNavItem`-komponentin tyylittely {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
