---
title: AppNav
sidebar_position: 6
_i18n_hash: 859da44bd50a1b3e985139da624ed4d4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav`-komponentti luo sivuvalikon `AppNavItem`-kirjentryksistä. Kirjentrykset voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, olla vanhempien kirjentryksien alla muodostaen hierarkkisia valikoita, ja niissä voi olla ikoneita, merkejä tai muita komponentteja, jotka antavat käyttäjille enemmän kontekstia yhdellä silmäyksellä.

<!-- INTRO_END -->

## Lisääminen ja sisäkkäin asettaminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kirjentrykset voivat olla yksinkertaisia linkkejä tai sisäkkäisiä ryhmän otsikoita, jotka sisältävät lapsikirjentryksiä. Linkittömät ryhmän otsikot toimivat laajennettavina säiliöinä.

Käytä `addItem()`-metodia lisätäksesi kirjentryksiä valikkoon:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Ryhmän kirjentryksien linkittäminen
Ylimmän tason kirjentrykset navigaatiohierarkiassa on yleensä tarkoitettu laajennettaviksi — eivät klikattaviksi linkeiksi. `path`-asetuksen antaminen tällaisille kirjentryksille voi hämmentää käyttäjiä, jotka odottavat, että niiden pitäisi paljastaa alakirjentryksiä sen sijaan, että navigoisivat muualle.

Jos haluat, että ryhmän otsikko käynnistää mukautetun toiminnon (kuten avata ulkoisia asiakirjoja), jätä ryhmän polku tyhjäksi ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons), kirjentryksen liitännäiseen. Tämä pitää käyttökokemuksen johdonmukaisena ja siistinä.
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

## Kirjentryksien linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkymäluokkia.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkkejä suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimelle](../routing/overview), voit välittää luokan kovakoodatun URL-osoitteen sijaan:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos anotettu reittisi tukee [reitittämisen parametreja](../routing/route-patterns#named-parameters), voit myös välittää `ParametersBag`:in:

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

## Kohdekäyttäytyminen {#target-behavior}

Säädä, miten linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisten linkkien tai pop-out-näkymien kohdalla.

- **`SELF`** (oletus): Avaa nykyisessä näkymässä.
- **`BLANK`**: Avaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avaa vanhempaan selauskontekstiin.
- **`TOP`**: Avaa huipputason selauskontekstiin.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja liite {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja liitekomponentteja. Käytä näitä, jotta tarjoat visuaalista selkeyttä ikoneille, merkeille tai painikkeille.

- **Etuliite**: ilmestyy ennen etikettiä, hyödyllinen ikoneille.
- **Liite**: ilmestyy etikettin jälkeen, loistava merkeille tai toimille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattinen laajentaminen ryhmille {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav` -komponentissa laajentaaksesi automaattisesti sisäkkäiset ryhmät, kun sovellus käynnistetään uudelleen.

```java
nav.setAutoOpen(true);
```

## `AppNavItem`-komponentin tyylitys {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
