---
title: Add tools for an open view
sidebar_position: 20
description: Add tools that work with an MCP App already open in the current conversation.
_i18n_hash: 0ad6819ba9550e2ffd2372c09b91a746
---
MCP-sovellus voi julkaista työkaluja sen näkymää avaavan työkalun lisäksi. Käytä toimintoa erilliseen operaatioon, jolla on oma syöte. Toteuta päivitysjännitin, kun sovellus tarvitsee yhden `inventory_update`-työkalun, jolla on sama syöte kuin sen avaavalla työkalulla.

Nämä työkalut eivät avaa sovellusta. Soitto ohjataan renderöityyn `inventory`-näkymään, joka on liitetty samaan MCP-istuntoon. Jos kyseistä näkymää ei ole avattu, soitto palauttaa virheen, joka ohjaa asiakasta soittamaan ensin `inventory`.

## Julkaise toiminto {#publish-an-action}

Lisää `@McpAppAction` näkymämenetelmään. Annoitus julkaisee toisen MCP-työkalun; menetelmä sisältää toiminnan, joka suoritetaan, kun työkalua kutsutaan.

```java
@McpAppAction(description = "Päivittää varastoerät avoimelle varastolle.")
Map<String, Object> refreshStock() {
  warehouse.setText(warehouse.getText() + " - päivitetty");
  return Map.of(
      "warehouse", warehouse.getText(),
      "refreshed", true);
}
```

Sovellukselle, jonka nimi on `inventory`, menetelmän nimi `refreshStock` tuottaa työkalun nimen `inventory_refresh_stock`. Aseta `name`-attribuutti `@McpAppAction`-annotaatiolla valitaksesi osa `inventory_`-alkuisista nimistä nimenomaisesti. Jokaisella toiminnolla on oltava ei-tyhjää kuvaus.

Toimintamenetelmällä voi olla ei-syöteparametria tai yksi objekti-syöteparametri. Objektiominaisuudet muuttuvat työkalun syötekaavioksi. Sen tulos palautetaan menetelmän paluuarvon tyypin mukaisesti:

- `CallToolResult` palautetaan suoraan.
- Mikä tahansa muu ei-`void`-arvo muuttuu jäsennellyksi sisällöksi.
- `void`-menetelmä palauttaa valmistumisviestin.

:::info[Näkymän on oltava auki]

Toiminto näkyy MCP-työkalulistassa, vaikka sovellus ei olisi auki, mutta sen kutsu onnistuu vain silloin, kun vastaava sovellus on renderöity samassa MCP-istunnossa.
:::

Toimintoja voidaan myös julkistaa luokassa, joka on lueteltu `@McpApp(actions = InventoryActions.class)`. Tämän luokan toiminnon on hyväksyttävä renderöity `InventoryView` parametrina, lisättynä sen valinnaisen objektisyötteen lisäksi.

## Julkaise päivitystyökalu {#publish-the-update-tool}

Toteuta `McpAppUpdateObserver` julkaistaksesi yhden päivitystyökalun sovellusta varten. Sovellukselle, jonka nimi on `inventory`, webforJ julkaisee `inventory_update`. Sen syötekaavio on sama kuin mitä `inventory` käyttää.

```java
public class InventoryView extends Composite<FlexLayout>
    implements McpAppUpdateObserver {

  private final FlexLayout self = getBoundComponent();
  private final Paragraph warehouse = new Paragraph();

  public InventoryView() {
    self.add(warehouse);
  }

  @Override
  public CallToolResult onMcpAppUpdate(McpAppUpdateEvent event) {
    String warehouseCode = event.getArguments().path("warehouseCode").asString();
    warehouse.setText("Varasto: " + warehouseCode);
    return CallToolResult.builder()
        .addTextContent("Varastoon on päivitetty.")
        .build();
  }
}
```

Kun `inventory_update` kutsutaan, webforJ siirtää sen argumentit `onMcpAppUpdate`-menetelmälle renderöityssä `InventoryView`-näkymässä. Palautustoiminto päättää, kuinka näitä argumentteja käytetään ja palauttaa työkalun tuloksen. webforJ ei automaattisesti sovella arvoja komponentteihin.

Päivitystyökalulla ei ole käyttöliittymäresursseja. Sen kutsuminen ei avaa reittiä tai renderöi toista näkymää.

:::tip[Valitse työkalun syötteellä]

Käytä toimintoa erilliseen operaatioon, jolla on oma syötekaavio. Käytä päivitysjännitintä yhden `<app-name>_update`-työkalun kohdalla, kun sen syötteen täytyy vastata avaavaa työkalua. Näkymä voi käyttää molempia.
:::

[Isäntä vuorovaikutus](./host-interaction) käsittelee pyyntöjä, joita renderöity näkymä lähettää MCP-isännälle.
