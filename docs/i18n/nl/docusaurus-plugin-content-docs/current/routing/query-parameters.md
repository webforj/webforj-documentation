---
sidebar_position: 6
title: Query Parameters
_i18n_hash: c3b57611c46f7cd4fa9946ff704213cc
---
Queryparameters maken het mogelijk om aanvullende gegevens via URL's door te geven, met het formaat `?key1=value1&key2=value2`. Terwijl routeparameters worden gebruikt om vereiste gegevens binnen het URL-pad door te geven, bieden queryparameters een flexibele manier om optionele of aanvullende gegevens door te geven. Ze zijn vooral handig bij het filteren van inhoud, sorteren of het omgaan met meerdere waarden voor dezelfde sleutel.

## Overzicht van queryparameters {#query-parameters-overview}

Queryparameters in webforJ volgen de typische URL-conventie: sleutel-waarde paren gescheiden door `=` en geconcateneerd met `&`. Ze worden aan de URL toegevoegd na een `?` en bieden een flexibele manier om optionele gegevens door te geven, zoals filter- of sorteervoorkeuren.

Bijvoorbeeld:

```
/producten?categorie=elektronica&sorteren=prijs
```

## Queryparameters ophalen {#retrieving-query-parameters}

Queryparameters worden benaderd via het `ParametersBag`-object. Om queryparameters op te halen, gebruik je de `getQueryParameters()`-methode van het `Location`-object.

Hier is hoe je queryparameters van een URL in een weergave kunt ophalen:

```java
@Route(value = "producten")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String categorie = queryParameters.get("categorie").orElse("alle");
    String sorteren = queryParameters.get("sorteren").orElse("standaard");

    console().log("Categorie: " + categorie);
    console().log("Sorteren: " + sorteren);
  }
}
```

In dit voorbeeld:
- De `onDidEnter`-methode haalt queryparameters op uit het `Location`-object dat wordt geleverd door de `DidEnterEvent`.
- Het `ParametersBag` stelt je in staat om specifieke queryparameters op te halen met `get()`, wat een `Optional<String>` retourneert. Je kunt een standaardwaarde opgeven met `orElse()` als de parameter niet aanwezig is.

:::tip `ParametersBag` getters
Het `ParametersBag` biedt verschillende gettervariaties om te helpen bij het casten van de waarde van queryparameters naar specifieke typen en ze te filteren. Hieronder staat de volledige lijst van beschikbare getters:

- **`get(String key)`**: Haalt de waarde van de parameter op als een `String`.
- **`getAlpha(String key)`**: Retourneert alleen alfabetische tekens uit de parameterwaarde.
- **`getAlnum(String key)`**: Retourneert alleen alfanumerieke tekens uit de parameterwaarde.
- **`getDigits(String key)`**: Retourneert alleen de numerieke cijfers uit de parameterwaarde.
- **`getInt(String key)`**: Parseert en retourneert de parameterwaarde als een `Integer`.
- **`getFloat(String key)`**: Parseert en retourneert de parameterwaarde als een `Float`.
- **`getDouble(String key)`**: Parseert en retourneert de parameterwaarde als een `Double`.
- **`getBoolean(String key)`**: Parseert en retourneert de parameterwaarde als een `Boolean`.

Deze methoden helpen je ervoor te zorgen dat de waarden correct zijn opgemaakt en gecast, waardoor de noodzaak voor handmatig parseren of valideren wordt vermeden.
:::

## Omgaan met meerdere waarden voor een queryparameter {#handling-multiple-values-for-a-query-parameter}

Soms kan een queryparameter meerdere waarden voor dezelfde sleutel hebben, zoals in het volgende voorbeeld:

```
/producten?categorie=elektronica,apparatuur&sorteren=prijs
```

Het `ParametersBag` biedt een methode om dit te beheren door waarden als een lijst op te halen:

```java
@Route(value = "producten")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categorieën = queryParameters.getList("categorie").orElse(List.of("alle"));
    String sorteren = queryParameters.get("sorteren").orElse("standaard");

    console().log("Categorieën: " + categorieën);
    console().log("Sorteren: " + sorteren);
  }
}
```

In dit voorbeeld:
- `getList("categorie")` haalt alle waarden op die zijn gekoppeld aan de sleutel `categorie`, en retourneert ze als een lijst.

:::tip Meerdere Waarden Scheidingsteken
Standaard gebruikt de `getList()`-methode een komma (`,`) als scheidingsteken. Je kunt het scheidingsteken aanpassen door een ander teken of een reguliere expressie door te geven als tweede parameter aan de `getList(String key, String regex)`-methode.
:::

## Toepassingsgevallen voor queryparameters {#use-cases-for-query-parameters}

- **Inhoud filteren**: Queryparameters worden vaak gebruikt om filters toe te passen, zoals categorieën of zoekwoorden.
- **Gegevens sorteren**: Je kunt sorteervoorkeuren doorgeven via queryparameters, zoals sorteren op prijs, beoordeling of datum.
- **Omgaan met optionele parameters**: Wanneer je gegevens moet doorgeven die geen deel uitmaken van de vereiste route-structuur, bieden queryparameters flexibiliteit.
- **Meerdere waarden doorgeven**: Queryparameters maken het mogelijk om meerdere waarden voor een enkele sleutel te verzenden, wat nuttig is wanneer gebruikers meerdere opties selecteren, zoals productcategorieën of filters.
