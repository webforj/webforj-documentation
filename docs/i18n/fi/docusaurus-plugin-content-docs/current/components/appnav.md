---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

`AppNav`-komponentti luo sivuvalikon `AppNavItem`-merkkien avulla. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, olla vanhempikohteiden alla hierarkkisissa valikoissa ja sisältää ikoneita, merkkejä tai muita komponentteja antaakseen käyttäjille lisää kontekstia yhdellä silmäyksellä.

<!-- INTRO_END -->

## Lisääminen ja sisäkkäin asettaminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai sisäkkäisiä ryhmäotsikoita, jotka sisältävät lapsikohteita. Ryhmäotsikot ilman linkkejä toimivat laajennettavina kontteina.

Käytä `addItem()`-menetelmää lisätäksesi kohteita valikkoon:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Ryhmän kohteiden linkittäminen
Ylimmät kohteet navigointipuussa on yleensä tarkoitettu laajennettaviksi—ei napsautettaviksi linkeiksi. `path`-asettaminen tällaisille kohteille voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alikohteita sen sijaan, että ne navigoivat muualle.

Jos haluat, että ryhmäotsikko laukaisee mukautetun toiminnon (kuten ulkoisten asiakirjojen avaamisen), jätä ryhmän polku tyhjäksi ja lisää sen sijaan interaktiivinen ohjain, kuten [`IconButton`](./icon#icon-buttons), kohteen liitteeksi. Tämä säilyttää käyttökokemuksen johdonmukaisena ja siistinä.
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

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkymäluokkia.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkit suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimelle](../routing/overview), voit siirtää luokan kovakoodatun URL: n sijaan:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos merkitty reittisi tukee [reitittäjiä](../routing/route-patterns#named-parameters), voit myös siirtää `ParametersBag`:n:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Siirrä `ParametersBag` sisällyttääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteen käyttäytyminen {#target-behavior}

Ohjaa, miten linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllinen ulkoisille linkeille tai pop-out-näkymille.

- **`SELF`** (oletusarvo): Avataan nykyisessä näkymässä.
- **`BLANK`**: Avataan uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avataan vanhemmassa selainkontekstissa.
- **`TOP`**: Avataan ylimmässä selainkontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja jälkiliite {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja jälkiliitekomponentteja. Käytä näitä visuaalisen selkeyden tarjoamiseen ikoneilla, merkeillä tai painikkeilla.

- **Etuliite**: näkyy ennen merkintää, hyödyllinen ikoneille.
- **Jälkiliite**: näkyy merkinnän jälkeen, erinomainen merkeille tai toimille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Ryhmien automaattinen avaaminen {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav`-komponentissa automaattiseen sisäkkäisten ryhmien laajentamiseen, kun sovellus päivitetään.

```java
nav.setAutoOpen(true);
```

## Kiinnitys <DocChip chip='since' label='26.01' /> {#pinning}

Kiinnitys antaa käyttäjän nostaa eniten tavoittamansa kohteet ryhmään valikon yläosaan, jotta syvä valikko säilyttää lyhyen suosikkiluettelon yhdellä napsautuksella. Se on pois päältä oletuksena. Ota se käyttöön kiinnityskonfiguraation kautta:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Kun se on otettu käyttöön, jokainen navigoitava lehtikohde näyttää kiinnitysvaihtoehdon. Vaihtoehto paljastuu hiiren yllä ja näppäimistön fokuksessa, joten se on saavutettavissa ilman hiirtä. Aktivointi siirtää kohteen kiinnitettyyn ryhmään valikon yläosassa.

Muutamia sääntöjä hallitsee, mitä voidaan kiinnittää ja miten ryhmä käyttäytyy:

- Vain navigoitavat lehtikohteet voivat olla kiinnitettyjä. Ryhmäotsikoita (kohteet, joilla on lapsia) ei koskaan voida kiinnittää.
- Kiinnitetty ryhmä näkyy vain, kun jotain on kiinnitetty, ja katoaa uudelleen, kun viimeinen kohde on poistettu kiinnityksestä.
- Kiinnityksen poistaminen palauttaa kohteen tarkalleen alkuperäiseen paikkaansa, mukaan lukien kohteet, jotka ovat sisäkkäin useilla tasoilla ryhmässä.
- Kohde siirretään, ei kopioidaan, joten kaikki etuliite- tai jälkiliiteasiat ja siihen liitetyt kuuntelijat toimivat, kun se istuu kiinnitetyssä ryhmässä.

Alla oleva demo on otettu käyttöön kiinnitys mukautetun ryhmän nimen kanssa ja Dashboard kiinnitettyä latauksen yhteydessä. Vie hiiri tai keskity lehtikohteeseen paljastaaksesi sen kiinnitysvalinnan.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Aloittaminen kiinnitettynä {#starting-an-item-pinned}

Aloita kohde kiinnitetyssä ryhmässä asettamalla sen kiinnitys-tila. Käytä `isPinned()` lukeaksesi nykyinen tila.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Kiinnitys on otettava käyttöön
`setPinned(true)` tulee voimaan vain, kun kiinnitys on otettu käyttöön `AppNav`:ssa `getPinning().setEnabled(true)`-menetelmällä. Ilman sitä kutsu ei vaikuta.
:::

### Kiinnitetyn ryhmän nimi {#pinned-group-title}

Kiinnitetty ryhmä on oletusarvoisesti nimeltään `Pinned`. Voit vaihtaa sen sopivaksi sovelluksellesi:

```java
nav.getPinning().setTitle("Favorites");
```

### Kiinnitysavaimet {#pin-keys}

Jokaisella kiinnitettävällä kohteella on avain, joka tunnistaa sen pysyvyyden ja [kiinnitys-tapahtuman](#reacting-to-pin-changes) varten. Kun et aseta yhtä, avain palautuu kohteen polkuun, joten `getPinKey()` palauttaa aina käyttökelpoisen arvon.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Aseta voimassa oleva avain, kun polku voi muuttua ajoituksen aikana. Vakaa avain pitää kiinnityksen oikean kohteen kohdalla latausten välillä, vaikka sen URL siirtyy.

### Automatisoitu tallennus paikallisessa tallennuksessa {#autosave}

Kiinnitykset elävät vain nykyisessä sivunäkymässä, ellei niitä tallenneta. Automatisoitu tallennus on yksinkertaisin vaihtoehto: se tallentaa kiinnitettyjen kohteiden joukon selaimen paikalliseen tallennukseen ja palauttaa ne latauksen yhteydessä. Se on pois päältä oletuksena. Se tarvitsee vakaata `id`:tä (tai nimeä) komponentille tallennusavaimet varten, ja `AppNav(String id)`-konstruktori on kätevä tapa asettaa yksi:

```java
AppNav nav = new AppNav("main-nav"); // antaa automatisoidulle tallennukselle vakaat tallennusavaimet
nav.getPinning().setAutosave(true);
```

:::info Automatisoitu tallennus tarvitsee id:n
Ilman `id`:tä (tai nimeä) komponentilla, automatisoitu tallennus ei tee mitään, koska sillä ei ole vakaata avainta tallennettavaksi. Pysyvyys on selainkohtainen, joten kiinnitykset eivät seuraa käyttäjää toiseen laitteeseen tai selaimeen.
:::

### Mukautettu pysyvyys {#custom-persistence}

Jos suoritat pysyvyyttä, jota hallitset itse, esimerkiksi käyttäjää palvelimella varten, ota automatisoitu tallennus pois päältä ja ohjaa se itse [kiinnitys-tapahtuman](#reacting-to-pin-changes) ja `setPinned`-menetelmän avulla:

```java
nav.getPinning().setAutosave(false);

// tallenna nykyiset kiinnitettyjen avainten joukko aina kun se muuttuu
nav.onPin(event -> savePins(event.getKeys()));

// latauksen aikana, palauta jokainen tallennettu avain
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reagointi kiinnityksen muutoksiin {#reacting-to-pin-changes}

Kiinnitys-tapahtuma laukaisee aina, kun kohde on kiinnitetty tai poistettu kiinnityksestä. Se kuljettaa muuttunutta kohdetta, sen avainta, uutta kiinnitystä ja koko järjestettyä kiinnitettyjen avainten joukkoa:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // muuttunut kohde, tai null, jos se ei enää ole navigaatiossa
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jokainen kiinnitetty avain, kiinnitetty järjestys
});
```

`getItem()` ratkaisee kohteen avainsuhteella, ja palauttaa `null`, kun kohde ei enää ole osa navigaatiota.

### Kiinnitykselle tarkoitetut ikonit {#pin-icons}

Vaihtoehto hyödyntää sisäistä `dwc:pin`-ikonia, kun kohde on kiinnittämätön, ja `dwc:pinned-off`, kun se on kiinnitetty. Voit vaihtaa omasi käyttämällä `setUnpinnedIcon` ja `setPinnedIcon`, jotka hyväksyvät minkä tahansa `IconDefinition`-tyypin:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Kiinnitysohjaus kosketusnäytöillä {#pin-toggle-on-touchscreens}

Kosketusnäytöissä ei ole hiiren yllä kiinnitysohjausta, joten valinta on oletuksena piilotettu. Pidä se näkyvissä ja napattavissa kosketusnäytöillä `setTouchVisible(true)`-menetelmällä:

```java
nav.getPinning().setTouchVisible(true);
```

## Haku <DocChip chip='since' label='26.01' /> {#search}

Hakukenttä suodattaa valikon kohteet merkinnän mukaan, kun käyttäjä kirjoittaa. Se on pois päältä oletuksena. Voit näyttää sen ja antaa sille paikkamerkin hakukonfiguraation kautta:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Hae");
```

Kun käyttäjä kirjoittaa, valikko suodattaa kohteita merkin mukaan, avaa kaikki ryhmät, jotka sisältävät osumia, ja näyttää tyhjää viestiä, kun ei mitään vastaa. Kiinnitetyt pikakuvakkeet pysyvät näkyvissä hakemisen aikana, joten käyttäjän suosikit pysyvät yhdellä napsautuksella saatavilla jopa suodatettaessa.

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
nav.getSearch().setEmptyMessage("Ei löytynyt kohteita");
```

### Haku omasta kentästä {#custom-search-box}

Piilota sisäänrakennettu kenttä ja syötä suodatin omasta syötteestäsi. Työnnä nykyinen termi sisään `setTerm`-menetelmällä:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Reagoi siihen, mitä käyttäjä kirjoittaa sisäänrakennettuun kenttään, kuuntelemalla hakutapahtumaa:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Tyylitys `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
