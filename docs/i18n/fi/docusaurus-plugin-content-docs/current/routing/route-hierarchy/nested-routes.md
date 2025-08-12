---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 8c3365b48d048d5bc7c4c47f253acb24
---
Sisäkkäiset reitit mahdollistavat lapsireittien renderöinnin vanhemmissa reiteissä, luoden modulaarisen ja uudelleenkäytettävän käyttöliittymän. Vanhempireitit määrittelevät jaetut komponentit, kun taas lapsireittejä injektoidaan spesifisiin ulosottoihin näissä vanhemmissa komponenteissa.

## Sisäkkäisten reittien määrittäminen {#defining-nested-routes}

Sisäkkäiset reitit luodaan `outlet`-parametrin avulla `@Route`-annotaatiossa, joka perustaa vanhempi-lapsi-suhteen. `Outlet` määrittää, mihin lapsikomponentti renderöidään vanhemman reitin sisällä.

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
    getBoundComponent().add(new H1("Dashboard-sisältö"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  public SettingsView() {
    getBoundComponent().add(new H1("Asetukset-sisältö"));
  }
}
```

Esimerkissä:

- `MainLayout` on **[Layout Route](./route-types#layout-routes)**.
- `DashboardView` on **[View Route](./route-types#view-routes)**, joka on upotettu `MainLayout`-komponenttiin.
- `SettingsView` on **[View Route](./route-types#view-routes)**, joka on upotettu `DashboardView`-komponenttiin.

Kun navigoidaan osoitteeseen `/dashboard/settings`, reititin:
1. Renderöi `MainLayout`.
2. Injektoi `DashboardView`-komponentin `MainLayout`:in ulosottoon.
3. Lopuksi injektoi `SettingsView`-komponentin `DashboardView`:n ulosottoon.

Tämä hierarkkinen rakenne näkyy URL-osoitteessa, jossa jokainen segmentti edustaa tasoa komponenttihierarkiassa:

- **URL**: `/dashboard/settings`
- **Hierarkia**:
  - `MainLayout`: Layout Route
  - `DashboardView`: View Route
  - `SettingsView`: View Route

Tämä rakenne varmistaa johdonmukaiset jaetut UI-komponentit (kuten otsikot tai navigointivalikot), samalla kun mahdollistaa sisällön muuttuvan dynaamisesti näiden ulkoasujen sisällä.
