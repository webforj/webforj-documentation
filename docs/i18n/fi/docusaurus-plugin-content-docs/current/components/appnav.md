---
title: AppNav
sidebar_position: 6
_i18n_hash: d4756db6bed23bc005fbcd2be222b4ea
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

`AppNav`-komponentti luo sivuvalikon `AppNavItem`-kohdista. Kohteet voivat linkittää sisäisiin näkymiin tai ulkoisiin resursseihin, pesiytyä vanhempien kohteiden alle muodostaen hierarkkisia valikoita sekä sisältää ikoneita, merkkejä tai muita komponentteja, jotka antavat käyttäjille enemmän kontekstia yhdellä silmäyksellä.

<!-- INTRO_END -->

## Kohteiden lisääminen ja pesiminen {#adding-and-nesting-items}

`AppNavItem`-instansseja käytetään `AppNav`-rakenteen täyttämiseen. Nämä kohteet voivat olla yksinkertaisia linkkejä tai pesityviä ryhmäotsikoita, jotka sisältävät lapsikohteita. Ryhmäotsikot ilman linkkejä toimivat laajennettavina säilytin.

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

:::tip Ryhmän kohteiden linkittäminen
Ylhäällä olevat kohteet navigointipuussa ovat tyypillisesti tarkoitettu laajennettaviksi – eivät klikattaviksi linkeiksi. `path`-asetuksen asettaminen tällaisille kohteille voi hämmentää käyttäjiä, jotka odottavat niiden paljastavan alikohteita sen sijaan, että navigoisivat muualle.

Jos haluat, että ryhmäotsikko laukaisee mukautetun toiminnon (kuten ulkoisten asiakirjojen avaamisen), pidä ryhmän polku tyhjänä ja lisää sen sijaan interaktiivinen ohjaus, kuten [`IconButton`](./icon#icon-buttons), kohteen liitteeksi. Tämä pitää käyttäjäkokemuksen johdonmukaisena ja siistinä.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Kohteiden linkittäminen {#linking-items}

Jokainen `AppNavItem` voi navigoida sisäiseen näkymään tai ulkoiseen linkkiin. Voit määrittää tämän käyttämällä staattisia polkuja tai rekisteröityjä näkaluokkia.

### Staattiset polut {#static-paths}

Käytä merkkijonopolkuja määrittääksesi linkit suoraan:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Rekisteröidyt näkymät {#registered-views}

Jos näkymäsi on rekisteröity [reitittimellä](../routing/overview), voit siirtää luokan sijasta kovakoodattua URL-osoitetta:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Jos annotoitut reitit tukevat [reitin parametreja](../routing/route-patterns#named-parameters), voit myös siirtää `ParametersBag`:in:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Kyselyparametrien kanssa {#with-query-parameters}

Siirrä `ParametersBag` sisältääksesi kyselymerkkijonot:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Kohteen käyttäytyminen {#target-behavior}

Hallitse, miten linkit avautuvat käyttämällä `setTarget()`. Tämä on erityisen hyödyllistä ulkoisille linkeille tai ponnahtaville näkymille.

- **`SELF`** (oletus): Avautuu nykyisessä näkymässä.
- **`BLANK`**: Avautuu uudessa välilehdessä tai ikkunassa.
- **`PARENT`**: Avautuu vanhemmassa selauskontekstissa.
- **`TOP`**: Avautuu ylimmällä selauskontekstissa.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Etuliite ja suffix {#prefix-and-suffix}

`AppNavItem` tukee etuliite- ja suffiksi-komponentteja. Käytä näitä antaaksesi visuaalista selkeyttä ikoneilla, merkeillä tai painikkeilla.

- **Etuliite**: ilmestyy ennen etikettiä, hyödyllinen ikoneille.
- **Suffiksi**: ilmestyy etikettiä jälkeen, loistava merkeille tai toiminnoille.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Ryhmien automaattinen avaus {#auto-opening-groups}

Käytä `setAutoOpen(true)`-asetusta `AppNav`-komponentissa, jotta pesityt ryhmät avautuvat automaattisesti, kun sovellus käynnistetään uudelleen.

```java
nav.setAutoOpen(true);
```

## `AppNavItem`-komponentin muotoilu {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
