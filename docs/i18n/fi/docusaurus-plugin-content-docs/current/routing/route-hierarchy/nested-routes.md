---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5c431e57443e65c98f6f9b2e1098ad99
---
Sisäkkäiset reitit mahdollistavat lapsireittien renderöimisen vanhempien reittien sisällä, luoden modulaarisen ja uudelleenkäytettävän käyttöliittymän. Vanhempien reittien määrittämät yhteiset komponentit, kun taas lapsireitit injektoidaan näiden vanhempien komponenttien tiettyihin uloskäynteihin.

## Sisäkkäiden reittien määrittäminen {#defining-nested-routes}

Sisäkkäiset reitit luodaan käyttämällä `outlet`-parametria `@Route`-annotaatiossa, joka luo vanhempi-lapsisuhteen. `Outlet` määrittää, minne lapsikomponentti renderöidään vanhempaan reittiin.

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Dashboard Content"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Settings Content"));
  }
}
```

Tässä esimerkissä:

- `MainLayout` on **[Layout Reitti](./route-types#layout-routes)**.
- `DashboardView` on **[View Reitti](./route-types#view-routes)**, joka on sisäkkäinen `MainLayout`:ssa.
- `SettingsView` on **[View Reitti](./route-types#view-routes)**, joka on sisäkkäinen `DashboardView`:ssa.

Kun navigoidaan osoitteeseen `/dashboard/settings`, reititin:
1. Renderöi `MainLayout`:n.
2. Injektoi `DashboardView`:n `MainLayout`:n outletiin.
3. Lopuksi injektoi `SettingsView`:n `DashboardView`:n outletiin.

Tämä hierarkkinen rakenne heijastuu URL-osoitteessa, jossa jokainen osa edustaa tasoa komponenttihierarkiassa:

- **URL**: `/dashboard/settings`
- **Hierarkia**:
  - `MainLayout`: Layout Reitti
  - `DashboardView`: View Reitti
  - `SettingsView`: View Reitti

Tämä rakenne varmistaa yhdenmukaiset jaettavat käyttöliittymäkomponentit (kuten otsikot tai navigointivalikot) samalla kun se sallii niiden asetteluiden sisällön muuttua dynaamisesti.
