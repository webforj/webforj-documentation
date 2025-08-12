---
title: AppNav
sidebar_position: 6
_i18n_hash: 1e9ac3fc8372d76faee53a4b9ee2cf88
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav` komponentti webforJ:ssä tarjoaa joustavan ja organisoidun sivuvalikkorakenteen, joka tukee sekä tasaisia että hierarkkisia rakenteita. Jokainen merkintä on `AppNavItem`, joka voi edustaa yksinkertaista linkkiä tai ryhmää, johon kuuluu alakohtia. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, ja niitä voidaan parantaa kuvakkeilla, merkinnöillä tai muilla komponenteilla.

## Lisääminen ja ryhmittely {#adding-and-nesting-items}

`AppNavItem` instansseja käytetään `AppNav` rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai sisäkkäisiä ryhmän otsikoita, joihin kuuluu lapsikohteita. Ryhmän otsikot ilman linkkejä toimivat laajennettavina säilytyspaikkoina.

Käytä `addItem()` lisätäksesi kohteita valikkoon:

```java
AppNavItem dashboard = new AppNavItem("Dashborad", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Käyttäjät", "/admin/users"));
admin.addItem(new AppNavItem("Asetukset", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Ryhmän kohteiden linkittäminen
Ylimmän tason kohteet navigointipuussa on tyypillisesti tarkoitettu laajennettavaksi - eivät klikkattaviksi linkeiksi. `path` asettaminen tällaisille kohteille voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alakohtia sen sijaan, että navigoisivat muualle.

Jos haluat, että ryhmän otsikko laukaisee mukautetun toiminnon (kuten avaa ulkoiset asiakirjat), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons), kohteen liitteeksi. Tämä pitää käyttökokemuksen yhtenäisenä ja selkeänä.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Kohteiden linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttäen staattisia polkuja tai rekisteröityjä näkymäluokkia.

### Staattiset polut {#static-paths}

Käytä merkkijonopoluja määrittääksesi linkkejä suoraan:

```java
AppNavItem docs = new AppNavItem("Asiakirjat", "/docs");
AppNavItem help = new AppNavItem("Apua", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymät on rekisteröity [reitittimelle](../routing/overview), voit välittää luokan sen sijaan, että käyttäisit kovakoodattua URL:ia:

```java
AppNavItem settings = new AppNavItem("Asetukset", SettingsView.class);
```

Jos sinun annottoitu reitti tukee [reittiparametreja](../routing/route-patterns#named-parameters), voit myös välittää `ParametersBag`:in:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Käyttäjä", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Välitä `ParametersBag` sisältääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Edistynyt", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteen käyttäytyminen {#target-behavior}

Säädä, miten linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisille linkeille tai pop-out-näkymille.

- **`SELF`** (oletus): Avataan nykyisessä näkymässä.
- **`BLANK`**: Avataan uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avataan vanhemmassa selauskontekstissa.
- **`TOP`**: Avataan ylimmässä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Apua", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja liite {#prefix-and-suffix}

`AppNavItem` tukee etuliitteitä ja liitteitä. Käytä näitä visuaalisen selkeyden tarjoamiseksi kuvakkeilla, merkinnöillä tai painikkeilla.

- **Etuliite**: ilmestyy ennen merkintää, hyödyllinen kuvakkeille.
- **Liite**: ilmestyy merkinnän jälkeen, loistava merkinnöille tai toiminnoille.

```java
AppNavItem notifications = new AppNavItem("Ilmoitukset");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattisesti avautuvat ryhmät {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav` komponentilla automaattisesti laajentaaksesi sisäkkäisiä ryhmiä, kun sovellus uusitaan.

```java
nav.setAutoOpen(true);
```

## Tyylitys `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
