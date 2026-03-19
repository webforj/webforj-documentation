---
sidebar_position: 2
title: Nested Routes
_i18n_hash: 5324d20d84c35f52067d0ba6d6448b71
---
Sisäkkäiset reitit mahdollistavat lapsireittien renderöimisen vanhempien reittien sisällä, luoden modulaarisen ja uudelleenkäytettävän käyttöliittymän. Vanhempireitit määrittävät jaetut komponentit, kun taas lapsireitit injektoidaan erityisiin ulostuloihin näissä vanhempikomponenteissa.

## Sisäkkäisten reittien määrittäminen {#defining-nested-routes}

Sisäkkäiset reitit luodaan käyttämällä `outlet`-parametria `@Route`-annotaatiossa, joka vahvistaa vanhempi-lapsi-suhteen. `Outlet` määrää, mihin lapsikomponentti renderöidään vanhempireitissä.

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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Dashboard Content"));
  }
}

@Route(outlet = DashboardView.class)
public class SettingsView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public SettingsView() {
    self.add(new H1("Settings Content"));
  }
}
```

Esimerkissä:

- `MainLayout` on **[Layout-reitti](./route-types#layout-routes)**.
- `DashboardView` on **[View-reitti](./route-types#view-routes)**, joka on upotettu `MainLayout`-komponenttiin.
- `SettingsView` on **[View-reitti](./route-types#view-routes)**, joka on upotettu `DashboardView`-komponenttiin.

Kun navigoidaan osoitteeseen `/dashboard/settings`, reititin:
1. Renderöi `MainLayout`.
2. Injektoi `DashboardView` `MainLayout`-ulostuloon.
3. Lopuksi injektoi `SettingsView` `DashboardView`-ulostuloon.

Tämä hierarkkinen rakenne näkyy URL-osoitteessa, jossa jokainen segmentti edustaa tasoa komponenttihierarkiassa:

- **URL**: `/dashboard/settings`
- **Hierarkia**:
  - `MainLayout`: Layout-reitti
  - `DashboardView`: View-reitti
  - `SettingsView`: View-reitti

Tämä rakenne varmistaa johdonmukaiset jaetut käyttöliittymäkomponentit (kuten otsikot tai navigointivalikot), samalla kun antaa mahdollisuuden sisällön vaihtua dynaamisesti näiden asettelujen sisällä.
