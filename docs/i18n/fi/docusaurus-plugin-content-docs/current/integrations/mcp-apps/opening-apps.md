---
title: Open a view with input
sidebar_position: 15
description: >-
  Accept structured opening input in a routed MCP App and choose its requested
  display mode.
_i18n_hash: 158831b08974dd001c1322c38213e331
---
Avaamisen syöttö antaa tekoälyn valita näkymän alkuperäisen tilan. Esimerkiksi varastonhallintaohjelma voi hyväksyä varastokoodin, kun asiakas avaa sen, ja soveltaa tätä arvoa reitin renderöinnin jälkeen.

## Kuvaile syöttöä {#describe-the-input}

Käytä yhtä objektiota työkalun argumentteina. Jacksonin annotaatiot lisäävät tiedot, joita asiakas käyttää kutsun rakentamiseen ja validoimiseen.

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

record InventoryInput(
    @JsonProperty(required = true)
    @JsonPropertyDescription("Näytettävä varastokoodi")
    String warehouseCode) {
}
```

Generoitu skeema merkitsee `warehouseCode` vaatimukset täyttäväksi ja sisältää sen kuvauksen. Selkeät ominaisuuskuvaukset auttavat tekoälyä tarjoamaan tarkoitetut arvot.

## Sovella syöttöä näkymän avautuessa {#apply-opening-input}

Lisää yksi `@McpAppInput` -metodi reititetyssä näkymässä. Sen on hyväksyttävä yksi objektiparametri.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.McpAppDisplayMode;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.mcp.annotation.McpAppInput;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Näyttää nykyisen varaston tietyn varaston osalta.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @McpAppInput
  void applyOpeningInput(InventoryInput input) {
    warehouse.setText("Varasto: " + input.warehouseCode());
  }
}
```

Asiakas saa generoitu skeeman `inventory` -kutsussa. Kun se kutsuu työkalua, webforJ renderöi `/inventory` ja kutsuu sitten `applyOpeningInput` -metodia kyseiselle näkymän instanssille.

:::tip[Pidä työkalujen nimet vakaina]

Jokaisella `@McpApp` -annotaatiolla on oltava ei-tyhjää kuvaus. Jos `name` jätetään pois, webforJ johdetaan työkalun nimen reitistä: `/inventory` muuttuu `inventory`:ksi, `/sales/inventory` muuttuu `sales_inventory`:ksi ja juurereitti muuttuu `app`:ksi. Aseta `name`, kun integraatiot tarvitsevat vakaata nimeä, joka ei muutu reitin mukana.
:::

:::tip[Valitse yksi syöttömääritystapa]

`@McpAppInput` ei ole ainut skeemalähde. Näkymä voi sen sijaan asettaa `input = InventoryInput.class` tai tarjota JSON Schema -dokumentin `inputSchema` -attribuutilla `@McpApp`:ssa. Valitse täsmälleen yksi muoto. Yhdistämistä hylätään sovelluksen löytämisen aikana. Käytä `@McpAppInput` -attribuuttia, kun näkymän on pakko vastaanottaa ja soveltaa arvoja renderöinnin jälkeen.
:::

Syöttömetodi voi myös sijaita luokassa, joka on lueteltu `@McpApp(actions = InventoryActions.class)`. Tässä tapauksessa sen on hyväksyttävä käynnissä oleva `InventoryView` yhdessä syöttöobjektin kanssa. Ilmoita vain yksi `@McpAppInput` -metodi näkymässä ja sen luetelluissa luokissa.

## Pidä avautuva reitti navigoitavana {#route-parameters}

Generoitava avautuva työkalu navigoi ilman reitti-parametreja. Reitti, jolla on vaatimukset täyttävät parametrin, kuten `/inventory/:warehouse`, ei voi olla suoraan näkyvissä. Käytä parametritöntä reittiä ja avaussyöttöä tai luo erillinen mukautettu MCP-työkalu, joka antaa vaadittavat reitti-parametrit. Valinnaiset parametrin, wildcardit ja asettelusegmentit ovat sallittuja, kun reititin voi generoida URL-osoitteen ilman arvoja.

## Pyydä näyttötilaa {#display-mode}

`displayMode` kysyy asiakkaalta, kuinka näkymä esitetään. `INLINE` pitää varaston keskustelun vieressä, `PIP` pyytää kuva kuvassa -tilaa, ja `FULLSCREEN` pyytää suurinta esitystä. `FULLSCREEN` on webforJ:n oletus. Asiakas voi valita erilaisen tilan sen mukaan, mitä se tukee.

[Toiminnot ja päivitykset](./actions-updates) voivat muuttaa samaa näkymää sen avautumisen jälkeen.
