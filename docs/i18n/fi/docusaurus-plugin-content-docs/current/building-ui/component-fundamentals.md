---
sidebar_position: 2
title: Understanding Components
_i18n_hash: 9236dac850f1e56f91cbcada9b6d8921
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Ennen kuin rakennat mukautettuja komponentteja webforJ:ssä, on tärkeää ymmärtää perustavanlaatuinen arkkitehtuuri, joka muokkaa komponenttien toimintaa. Tämä artikkeli selittää komponenttihierarkian, komponenttiidentiteetin, elinkaareen liittyvät käsitteet ja kuinka concern-rajapinnat tarjoavat komponenttien ominaisuuksia.

## Ymmärrä komponenttihierarkia {#understanding-the-component-hierarchy}

webforJ järjestää komponentit hierarkiaan, joka koostuu kahdesta ryhmästä: kehys sisäiset luokat, joita et koskaan saa laajentaa, ja luokat, jotka on suunniteltu erityisesti mukautettujen komponenttien rakentamiseen. Tämä osio selittää, miksi webforJ käyttää koostumusta perinnön sijaan ja mitä jokainen hierarkian taso tarjoaa.

### Miksi koostumus sen sijaan, että laajennettaisiin? {#why-composition-instead-of-extension}

webforJ:ssa sisäänrakennetut komponentit, kuten [`Button`](../components/button) ja [`TextField`](../components/fields/textfield), ovat final-luokkia – et voi laajentaa niitä:

```java
// Tämä ei toimi webforJ:ssa
public class MyButton extends Button {
  // Button on final - ei voi laajentaa
}
```

webforJ käyttää **koostumusta perinnön sijaan**. Sen sijaan, että laajentaisit olemassa olevia komponentteja, luot luokan, joka laajentaa `Composite`-luokkaa ja yhdistää komponentit sen sisälle. `Composite` toimii säiliönä, joka käärii yhden komponentin (kutsutaan sidotuksi komponentiksi) ja antaa sinun lisätä omia komponentteja ja käyttäytymistä siihen.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;
  
  public SearchBar() {
    searchField = new TextField("Haku");
    searchButton = new Button("Siirry");
    
    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### Miksi et voi laajentaa sisäänrakennettuja komponentteja {#why-you-cant-extend-built-in-components}

webforJ:n komponentit on merkitty finaliksi, jotta säilytetään perusteellisen asiakaspuolen verkkokomponentin eheys. webforJ:n komponenttiluokkien laajentaminen antaisi hallinnan taustalla olevalle verkkokomponentille, mikä voisi johtaa odottamattomiin seurauksiin ja rikkoa komponenttien käyttäytymisen yhtenäisyyden ja ennustettavuuden.

Yksityiskohtaisen selityksen saat kohdasta [Final-luokat ja laajennusrajoitukset](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) arkkitehtuuridokumentaatiossa.

### Komponenttihierarkia {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Component<br/><small>Abstrakti perusta - kehys sisäinen</small>]
  
  A --> B[DwcComponent<br/><small>Sisäänrakennetut webforJ-komponentit</small>]
  A --> C[Composite<br/><small>Yhdistä webforJ-komponentteja</small>]
  
  B --> E[Button, TextField,<br/>DateField, ComboBox]
  
  C --> D[ElementComposite<br/><small>Kääri verkkokomponentit</small>]
  D --> F[ElementCompositeContainer<br/><small>Komponentit, joilla on slotit</small>]

  classDef internal stroke-dasharray:6 4,stroke-width:1px
  classDef primary stroke-width:3px
  classDef secondary stroke-width:2px,stroke-dasharray:2 2
  class A,B,E internal
  class C primary
  class D,F secondary
```
</div>

Kehittäjille tarkoitetut luokat (käytä näitä):
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Sisäiset kehysluokat (älä laajenna suoraan):
- `Component`
- `DwcComponent`

:::warning[Älä koskaan laajenna `Component` tai `DwcComponent`]
Älä koskaan laajenna `Component` tai `DwcComponent` suoraan. Kaikki sisäänrakennetut komponentit ovat final-luokkia. Käytä aina koostumuspatterneja `Composite` tai `ElementComposite`.

Yritettäessä laajentaa `DwcComponent` heittää virhetilanteen suoritusaikaisesti.
:::

## Concern-rajapinnat {#concern-interfaces}

Concern-rajapinnat ovat Java-rajapintoja, jotka tarjoavat erityisiä ominaisuuksia komponentteillesi. Jokainen rajapinta lisää joukon siihen liittyviä metodeja. Esimerkiksi `HasSize` lisää metodeja leveyden ja korkeuden hallintaan, kun taas `HasFocus` lisää metodeja fokustilan hallitsemiseksi.

Kun toteutat concern-rajapinnan komponentissasi, saat pääsyn näihin ominaisuuksiin ilman, että sinun tarvitsee kirjoittaa toteutuskoodia. Rajapinta tarjoaa oletustoteutuksia, jotka toimivat automaattisesti.

Concern-rajapintojen toteuttaminen antaa mukautetuille komponenteillesi samat API:t kuin sisäänrakennetuilla webforJ-komponenteilla:

```java
// Toteuta HasSize saadaksesi leveys/korkeus-metodit automaattisesti
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();
  
  public SizedCard() {
    self.setText("Kortin sisältö");
  }
  
  // Ei tarvitse toteuttaa näitä - saat ne ilmaiseksi:
  // setWidth(), setHeight(), setSize()
}

// Käytä sitä kuin mitä tahansa webforJ-komponenttia
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

Koostumus välittää automaattisesti nämä kutsut taustalla olevalle `Div`:lle. Ei tarvitse ylimääräistä koodia.

### Ulkonäkö {#concern-interfaces-appearance}

Nämä rajapinnat hallitsevat komponentin visuaalista esitystä, mukaan lukien sen mitat, näkyvyys, tyyli ja teema.

| Rajapinta | Kuvaus |
|---|---|
| `HasSize` | Hallitsee leveyttä ja korkeutta, mukaan lukien minimi- ja maksirajaukset. Laajentaa `HasWidth`, `HasHeight` ja niiden min/max variantit. |
| `HasVisibility` | Näyttää tai piilottaa komponentin poistamatta sitä asettelusta. |
| `HasClassName` | Hallitsee CSS-luokkien nimiä komponentin juurielementissä. |
| `HasStyle` | Soveltaa ja poistaa sisäisiä CSS-tyylejä. |
| `HasHorizontalAlignment` | Hallitsee, kuinka sisältö on kohdistettu vaakasuunnassa komponentissa. |
| `HasExpanse` | Määrittää komponentin koon variantin käyttäen standardin expanse-tokeneita (`XSMALL` - `XLARGE`). |
| `HasTheme` | Soveltaa teeman varianttia, kuten `DEFAULT`, `PRIMARY` tai `DANGER`. |
| `HasPrefixAndSuffix` | Lisää komponentteja etuliitteen tai jälkiliitteen slotille komponentin sisällä. |

### Sisältö {#concern-interfaces-content}

Nämä rajapinnat hallitsevat sitä, mitä komponentti näyttää, mukaan lukien tekstit, HTML, etiketit, vihjeet ja muu kuvastava sisältö.

| Rajapinta | Kuvaus |
|---|---|
| `HasText` | Asettaa ja noutaa komponentin pelkän tekstisisällön. |
| `HasHtml` | Asettaa ja noutaa komponentin sisäisen HTML:n. |
| `HasLabel` | Lisää kuvailevan etiketin, joka liittyy komponenttiin ja jota käytetään saavutettavuudessa. |
| `HasHelperText` | Näyttää toissijaisen vihjetekstin komponentin alla. |
| `HasPlaceholder` | Asettaa paikkamerkintätekstin, joka näkyy, kun komponentilla ei ole arvoa. |
| `HasTooltip` | Liittää työkaluvihjeen, joka näkyy hiiren ollessa päällä. |

### Tila {#concern-interfaces-state}

Nämä rajapinnat hallitsevat komponentin vuorovaikutteista tilaa, mukaan lukien onko se käytössä, muokattavissa, pakollinen tai keskittyneenä lataamisen aikana.

| Rajapinta | Kuvaus |
|---|---|
| `HasEnablement` | Aktivoi tai deaktivoituu komponentti. |
| `HasReadOnly` | Asettaa komponentin vain luku -tilaan, jossa arvo on näkyvissä, mutta sitä ei voi muuttaa. |
| `HasRequired` | Merkitsee komponentin pakolliseksi, yleensä lomakevalidointia varten. |
| `HasAutoFocus` | Siirtää fokuksen komponenttiin automaattisesti, kun sivu latautuu. |

### Fokus {#concern-interfaces-focus}

Nämä rajapinnat hallitsevat sitä, kuinka komponentti saa ja vastaa näppäimistön fokukseen.

| Rajapinta | Kuvaus |
|---|---|
| `HasFocus` | Hallitsee fokustilaa ja onko komponentti kyky vastaanottaa fokusta. |
| `HasFocusStatus` | Tarkistaa, onko komponentilla tällä hetkellä fokus. Vaatii matkustuksen asiakaspuolelle. |
| `HasHighlightOnFocus` | Hallitsee, korostetaanko komponentin sisältöä, kun se saa fokusta, ja miten (`KEY`, `MOUSE`, `KEY_MOUSE`, `ALL` jne.). |

### Syöttörajoitukset {#concern-interfaces-input-constraints}

Nämä rajapinnat määrittävät, mitä arvoja komponentti hyväksyy, mukaan lukien nykyinen arvo, sallitut arvot, pituusrajoitukset, muotoilumaskit ja aluekohtaiset käyttäytymiset.

| Rajapinta | Kuvaus |
|---|---|
| `HasValue` | Hakee ja asettaa komponentin nykyisen arvon. |
| `HasMin` | Asettaa sallitun vähimmäisarvon. |
| `HasMax` | Asettaa sallitun enimmäisarvon. |
| `HasStep` | Asettaa askelvälin numero- tai aluevanteille. |
| `HasPattern` | Soveltaa säännöllisen lausekkeen kaavaa rajoittamaan hyväksyttyä syötettä. |
| `HasMinLength` | Asettaa komponentin arvon vähimmäismäärän merkit. |
| `HasMaxLength` | Asettaa komponentin arvon enimmäismäärän merkit. |
| `HasMask` | Soveltaa muotoilumaskeja syötteeseen. Käytetään maskeeratuissa kenttäkomponenteissa. |
| `HasTypingMode` | Hallitsee, lisätäänkö kirjoitetut merkit vai korvataanko olemassa olevat merkit (`INSERT` tai `OVERWRITE`). Käytetään maskeeratuissa kentissä ja `TextArea`:ssa. |
| `HasRestoreValue` | Määrittää arvon, johon komponentti palautuu, kun käyttäjä painaa Esc-näppäintä tai kutsuu `restoreValue()`. Käytetään maskeeratuissa kentissä. |
| `HasLocale` | Tallentaa komponentin oman alueen aluekohtaisen muotoilun. Käytetään maskeeratuissa päivämäärä- ja aikakentissä. |
| `HasPredictedText` | Asettaa ennakoidun tai automaattisesti täydentävän tekstin. Käytetään `TextArea`:ssa inline-suggestioiden tukemiseksi. |

### Validointi {#concern-interfaces-validation}

Nämä rajapinnat lisäävät asiakaspuolen validointikäytöksen, mukaan lukien komponenttien merkitseminen virheellisiksi, virheviestien näyttäminen ja milloin validointi suoritetaan.

| Rajapinta | Kuvaus |
|---|---|
| `HasClientValidation` | Merkitsee komponentin virheelliseksi, asettaa virheviestin ja liittää asiakaspuolen validointimenettelyn. |
| `HasClientAutoValidation` | Hallitsee, validoiko komponentti automaattisesti, kun käyttäjä kirjoittaa. |
| `HasClientAutoValidationOnLoad` | Hallitsee, validoiko komponentti, kun se ladataan ensimmäisen kerran. |
| `HasClientValidationStyle` | Hallitsee, kuinka validointiviestit näytetään: `INLINE` (komponentin alla) tai `POPOVER`. |

### DOM-pääsy {#concern-interfaces-dom-access}

Nämä rajapinnat tarjoavat alhaisen tason pääsyn komponentin taustalla olevaan HTML-elementtiin ja asiakaspuolen ominaisuuksiin.

| Rajapinta | Kuvaus |
|---|---|
| `HasAttribute` | Lukee ja kirjoittaa satunnaisia HTML-attribuutteja komponentin elementissä. |
| `HasProperty` | Lukee ja kirjoittaa DWC-komponenttien ominaisuuksia suoraan asiakaspuolen elementille. |

### i18n {#concern-interfaces-i18n}

Tämä rajapinta tarjoaa käännöstuen komponenteille, jotka tarvitsevat paikallistettua tekstiä.

| Rajapinta | Kuvaus |
|---|---|
| `HasTranslation` | Tarjoaa `t()` apuohjelman menetelmän käännösohjeiden ratkaisemiseksi lokalisoituihin merkkijonoihin sovelluksen nykyisen alueen avulla. |

:::warning
Jos taustalla oleva komponentti ei tue rajapinnan ominaisuutta, saat suoritusaikaisen virhetilan. Tarjoa oma toteutuksesi tällöin.
:::

Saat täydellisen luettelon saatavilla olevista concern-rajapinnoista kohdasta [webforJ JavaDoc](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Komponentti-identifikaattorit {#component-identifiers}

webforJ-komponenteilla on kolme erilaista identifikaatityyppiä, jotka palvelevat erilaisia tarkoituksia:

- **Palvelinpuolen komponentin ID** (`getComponentId()`) - Kehys määrittelee automaattisesti sisäisten komponenttien seuraamiseen. Käytä tätä, kun sinun on kysyttävä tiettyjä komponentteja tai toteutettava mukautettuja komponenttirekistereitä.
- **Asiakaspuolen komponentin ID** (`getClientComponentId()`) - Tarjoaa pääsyn taustalla olevaan verkkokomponenttiin JavaScriptistä. Käytä tätä, kun sinun on kutsuttava alkuperäisiä verkkokomponenttimenettelyjä tai integroitava asiakaspuolen kirjastoihin.
- **HTML `id` -attribuutti** (`setAttribute("id", "...")`) - Standardi DOM-identifikaattori. Käytä tätä CSS-tavoitteiden asettamiseen, testiautomaatiovalitsimien muodostamiseen ja lomaketietojen liittämiseen syötteisiin.

Näiden erojen ymmärtäminen auttaa sinua valitsemaan oikean identifikaattorin käyttötapasi mukaan.

### Palvelinpuolen komponentin ID {#server-side-component-id}

Jokaiselle komponentille annetaan automaattisesti palvelinpuolen tunnus, kun se luodaan. Tätä tunnusta käytetään kehykseen sisäisesti komponenttien seuraamiseen. Hae se `getComponentId()`-menettelyn avulla:

```java
Button button = new Button("Napsauta minua");
String serverId = button.getComponentId();
```

Palvelinpuolen ID on hyödyllinen, kun sinun on kysyttävä tiettyjä komponentteja säiliön sisällä tai toteutettava mukautettuja komponenttien seurantamenettelyjä.

### Asiakaspuolen komponentin ID {#client-side-component-id}

Asiakaspuolen komponentin ID tarjoaa pääsyn taustalla olevaan verkkokomponenttiin JavaScriptistä. Tämä mahdollistaa suoran vuorovaikutuksen asiakaspuolen komponentin kanssa tarvittaessa:

```java
Button btn = new Button("Napsauta minua");
btn.onClick(e -> {
  OptionDialog.showMessageDialog("Nappia napsautettiin", "Tapahtuma tapahtui");
});

btn.whenAttached().thenAccept(e -> {
  Page.getCurrent().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
});
```

Käytä `getClientComponentId()`-menettelyä yhdessä `objects.get()` JavaScriptissä päästäksesi verkkokomponentin instanssiin.

:::important
Asiakaspuolen komponentin ID ei ole HTML `id` -attribuutti DOM-elementillä. Testaamista tai CSS-tavoitteita varten HTML-ID:iden asettamiseen, katso [Komponenttien käyttäminen](using-components).
:::

## Komponentin elinkaari yleiskuvaus {#component-lifecycle-overview}

webforJ hallitsee komponentin elinkaarta automaattisesti. Kehys käsittelee komponenttien luomisen, liittämisen ja tuhoamisen ilman manuaalista väliintuloa.

**Elinkaaren koukut** ovat saatavilla, kun tarvitset niitä:
- `onDidCreate(T container)` - Kutsutaan, kun komponentti on liitetty DOM:iin
- `onDidDestroy()` - Kutsutaan, kun komponentti tuhotaan

Nämä koukut ovat **valinnaisia**. Käytä niitä, kun sinun on:
- Siivottava resursseja (lopetettava aikavälejä, suljettava yhteyksiä)
- Alustaa komponentteja, jotka vaativat DOM:in liittämistä
- Integrointi asiakaspuolen JavaScriptin kanssa

Useimmissa yksinkertaisissa tapauksissa voit alustaa komponentit suoraan konstruktorissa. Käytä elinkaaren koukkuja kuten `onDidCreate()`, kun tarvitset siirtää työtä tarvittaessa.
