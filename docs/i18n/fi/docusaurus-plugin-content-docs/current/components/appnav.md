---
title: AppNav
sidebar_position: 6
_i18n_hash: 47432ed72280efdc4d1b48e72d95b87d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav` -komponentti webforJ:ssä tarjoaa joustavan ja organisoidun sivuvalikkon, joka tukee sekä tasaisia että hierarkkisia rakenteita. Jokainen kohde on `AppNavItem`, joka voi edustaa yksinkertaista linkkiä tai ryhmää, jossa on alakohtia. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, ja niitä voidaan parantaa ikoneilla, merkinnöillä tai muilla komponenteilla.

## Kohteiden lisääminen ja sisäkkäin asettaminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään täyttämään `AppNav`-rakenne. Nämä kohteet voivat olla yksinkertaisia linkkejä tai sisäkkäisiä ryhmäotsikoita, jotka sisältävät lapsikohteita. Linkittömät ryhmäotsikot toimivat laajennettavina säiliöinä.

Käytä `addItem()` lisätäksesi kohteita navigaatioon:

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
Navigaatiopuun ylimmät kohteet on yleensä tarkoitettu laajennettaviksi - ei klikkauslinkeiksi. `path`-asetuksen antaminen tällaisille kohteille voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alakohtia sen sijaan, että ne navigoisivat muualle.

Jos haluat, että ryhmäotsikko laukaisee mukautetun toiminnon (kuten ulkoisten asiakirjojen avaamisen), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen kontrolli, kuten [`IconButton`](./icon#icon-buttons), kohteen liitteeksi. Tämä pitää käyttäjäkokemuksen johdonmukaisena ja siistinä.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Kohteiden linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkökilpyjä.

### Staattiset polut {#static-paths}

Käytä merkkijono-alkuisia polkuja määrittääksesi linkkejä suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimellä](../routing/overview), voit siirtää luokan kovakoodatun URL-osoitteen sijasta:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos merkitty reittisi tukee [reitityssiirtomatkoja](../routing/route-patterns#named-parameters), voit myös siirtää `ParametersBag`-kansion:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien avulla {#with-query-parameters}

Siirrä `ParametersBag` sisältämään kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteiden käyttäytyminen {#target-behavior}

Hallitse, kuinka linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisille linkeille tai pop-out-näkymille.

- **`SELF`** (oletusarvo): Aukeaa nykyisessä näkymässä.
- **`BLANK`**: Aukeaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Aukeaa vanhemmassa selauskontekstissa.
- **`TOP`**: Aukeaa ylimmässä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Prefiksi ja suffiksi {#prefix-and-suffix}

`AppNavItem` tukee prefiksi- ja suffiksi-komponentteja. Käytä näitä visuaalisen selkeyden tarjoamiseksi ikoneilla, merkinnöillä tai painikkeilla.

- **Prefiksi**: ilmestyy ennen merkkiä, hyödyllinen ikoneille.
- **Suffiksi**: ilmestyy merkinnän jälkeen, loistava merkinnöille tai toimille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattinen laajentaminen ryhmiin {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav`-komponentissa laajentaaksesi automaattisesti sisäkkäisiä ryhmiä, kun sovellus päivitetään.

```java
nav.setAutoOpen(true);
```

## `AppNavItem` tyylittely {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
