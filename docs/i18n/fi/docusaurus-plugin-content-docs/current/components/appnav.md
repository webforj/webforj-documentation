---
title: AppNav
sidebar_position: 6
_i18n_hash: faa14d827865b1697b369a9787315dcd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav` -komponentti webforJ:ssä tarjoaa joustavan ja järjestelmällisen sivunavigaatiovalikon, joka tukee sekä tasaisia että hierarkisia rakenteita. Jokainen merkintä on `AppNavItem`, joka voi edustaa yksinkertaista linkkiä tai ryhmää, joka sisältää alakohtia. Kohteita voidaan linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, ja ne voidaan rikastuttaa ikoneilla, merkillä tai muilla komponenteilla.

## Lisääminen ja ryhmittely {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai sisäkkäisiä ryhmäotsikoita, jotka sisältävät lapsohjeita. Ryhmäotsikot ilman linkkejä toimivat laajennettavina säiliöinä.

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

:::tip Ryhmien kohteiden linkittäminen
Ylimmän tason kohteet navigaatiopuussa on yleensä tarkoitettu laajennettavaksi — eivät klikattaviksi linkeiksi. Tällaisille kohteille asetettu `path` voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alakohtia sen sijaan, että ne navigoisivat muualle.

Jos haluat, että ryhmäotsikko laukaisee mukautetun toiminnon (kuten avaa ulkoisia asiakirjoja), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons) kohteen liitteenä. Tämä pitää käyttäjäkokemuksen johdonmukaisena ja siistinä.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Kohteiden linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkemyksiä.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkit suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimelle](../routing/overview), voit siirtää luokan sen sijaan, että käyttäisit kiinteää URL:ia:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos merkitty reittisi tukee [reitityksen parametreja](../routing/route-patterns#named-parameters), voit myös siirtää `ParametersBag`:in:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Siirrä `ParametersBag`, jotta voit sisällyttää kyselymerkit:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteen käyttäytyminen {#target-behavior}

Säädä, kuinka linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisille linkeille tai pop-out-näkymille.

- **`SELF`** (oletus): Avaa nykyisessä näkymässä.
- **`BLANK`**: Avaa uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avaa ylätason selauskontekstissa.
- **`TOP`**: Avaa ylimmässä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja jälkiliite {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja jälkiliitekomponentteja. Käytä näitä tarjoamaan visuaalista selkeyttä ikoneilla, merkeillä tai painikkeilla.

- **Etuliite**: ilmestyy ennen merkintää, hyödyllinen ikoneille.
- **Jälkiliite**: ilmestyy merkin jälkeen, loistava merkeille tai toimille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automaattisesti avautuvat ryhmät {#auto-opening-groups}

Käytä `setAutoOpen(true)` `AppNav` -komponentissa, jotta sisäkkäiset ryhmät avautuvat automaattisesti, kun sovellus päivitetään.

```java
nav.setAutoOpen(true);
```

## Tyylitys `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
