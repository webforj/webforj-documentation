---
sidebar_position: 20
title: Renderöinti
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 7d9beff25d6b372fb4309fe491ecad08
---
<!-- vale Google.Headings = NO -->
<!-- vale Google.Colons = NO -->
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Renderöijä määrittää, miten sarakkeen jokainen solu näytetään. Raaka-arvon näyttämisen sijaan renderöijä muuntaa kunkin solun tiedot tyylitellyksi tekstiksi, kuvakkeiksi, tunnisteiksi, linkeiksi, toimintopainikkeiksi tai muuksi visuaaliseksi esitystavaksi, joka auttaa lukemaan tietoja nopeammin ja käyttämään niitä helpommin.

Renderöinti tapahtuu kokonaan selaimessa. Palvelin lähettää raakadataa, ja asiakaspuoli huolehtii esitystavasta, joten `Table` on nopea rivien määrästä riippumatta.

Määritä renderöijä sarakkeelle käyttämällä `setRenderer()`-metodia. Renderöijää käytetään samalla tavalla sarakkeen kaikissa soluissa:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderöijät ja arvontarjoajat
Jos sinun tarvitsee vain muuntaa tai muotoilla solun arvo luomatta DOM-rakennetta, käytä sen sijaan [arvontarjoajaa](/docs/components/table/columns#value-providers). Renderöijät luovat ylimääräisiä DOM-elementtejä jokaiselle renderöitävälle riville, mikä lisää renderöinnin kustannuksia. Käytä renderöijiä visuaalisiin esityksiin, kuten kuvakkeisiin, tunnisteisiin, painikkeisiin tai muuhun HTML-pohjaiseen esitystapaan.
:::

webforJ sisältää valmiita renderöijiä yleisimpiin käyttötarkoituksiin. Jos sovelluksesi tarvitsee jotain erityistä, laajenna `Renderer`-luokkaa ja toteuta `build()`-metodi, joka palauttaa lodash-mallimerkkijonon. Se suoritetaan selaimessa jokaiselle solulle.

## Yleiset renderöijät {#common-renderers}

Seuraavissa esimerkeissä käydään läpi neljä usein käytettyä renderöijää ja näytetään `setRenderer()`-käyttötapa käytännössä.

### TextRenderer {#text-renderer}

Näyttää solun sisällön tavallisena tai tyyliteltynä tekstinä. Käytä sarakkeessa teemaväriä tai tekstikorostusta muuttamatta sen rakennetta. Voit esimerkiksi korostaa prioriteettikentän punaisella tai lihavoida tärkeän tunnisteen.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Näyttää solun arvon tunniste-elementin sisällä. Tukee teemoja, laajuuksia, värien automaattista määritystä (eri väri kullekin yksilölliselle arvolle) ja valinnaista edellä näytettävää kuvaketta. Käytä sitä luokitteleville arvoille, kuten tunnisteille, tyypeille tai nimikkeille, joiden visuaalisesti erottuvat tunnisteet helpottavat rivien nopeaa silmäilyä ja vertailua.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Korvaa arvot `true`, `false` ja `null` kuvakkeilla. Käytä sitä true/false-sarakkeissa, joissa kuvake ilmaisee arvon tekstiä nopeammin, kuten ominaisuuslipuissa, aktiivisuus- ja passiivisuustiloissa tai suostumuskentissä.

```java
// Default icons
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Muotoilee numeerisen arvon valuuttasummaksi annetun `Locale`-olion sääntöjen mukaan. Käytä sitä rahasarakkeissa, joissa tarvitaan alueasetusten mukaista muotoilua (valuuttasymboli, erottimet ja desimaalit).

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Ehdollinen renderöinti {#conditional-rendering}

`ConditionalRenderer` valitsee solulle renderöijän solun arvon perusteella. Ehdot tarkistetaan järjestyksessä, ja ensimmäinen täsmäävä ehto valitaan. `otherwise()`-metodilla voi määrittää oletusvaihtoehdon.


Seuraavassa esimerkissä ehdollista renderöintiä käytetään laskun tilan sarakkeessa, jossa `BadgeRenderer`-versio valitaan arvon perusteella:


<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java',
]}
height='600px'
/>
<!-- vale on -->

Ehdollinen renderöinti sopii myös numeerisiin raja-arvoihin. Tämä palvelimen hallintapaneeli käyttää `ConditionalRenderer`-renderöijää vaihtaakseen `ProgressBarRenderer`-renderöijän teemaa suorittimen ja muistin käytön mukaan:

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

### Ehtojen API {#condition-api}

Ehdot luodaan staattisilla tehdasmetodeilla, ja niitä voidaan yhdistellä metodeilla `and()`, `or()` ja `negate()`.

```java
// Value equality
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numeric comparisons
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / emptiness
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// String matching
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Composition
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Cross-column check
Condition.column("status").equalTo("active")

// Raw JavaScript expression
Condition.expression("cell.value % 2 === 0")
```

## Yhdistelmärenderöinti {#composite-rendering}

`CompositeRenderer` yhdistää useita renderöijiä vierekkäin samaan soluun flex-asettelun avulla. Käytä sitä esimerkiksi kuvakkeen ja tekstin yhdistämiseen, avatarin näyttämiseen nimen vieressä tai tunnisteen sijoittamiseen tilailmaisimen viereen.

Alla oleva työntekijähakemisto käyttää *Employee*-sarakkeessa `CompositeRenderer`-renderöijää, joka näyttää automaattisesti luodun avatarin työntekijän nimen vieressä:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Employee.java',
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeService.java',
]}
height='600px'
/>
<!-- vale on -->

## Mukautetut renderöijät {#custom-renderers}

Jos mikään valmis renderöijä ei sovi käyttötarkoitukseesi, laajenna `Renderer`-luokkaa ja toteuta `build()`-metodi. Metodi palauttaa lodash-mallimerkkijonon, joka suoritetaan selaimessa sarakkeen jokaiselle solulle. Merkkijono sisältää HTML:ää ja JavaScriptiä.

### Mukautetun renderöijän luominen {#creating-a-custom-renderer}

**Vaihe 1:** Laajenna `Renderer`-luokkaa ja määritä rivin tietotyyppi.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Vaihe 2:** Korvaa `build()`-metodi ja palauta lodash-mallimerkkijono.

```java
  @Override
  public String build() {
    return /* html */"""
      <%
        const rating = Number(cell.value);
        const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
        const full   = '★'.repeat(stars);
        const empty  = '☆'.repeat(5 - stars);
      %>
      <span><%= full %><%= empty %></span>
      <span style="color: var(--dwc-color-body-text)">(<%= rating.toFixed(1) %>)</span>
    """;
  }
}
```

**Vaihe 3:** Määritä renderöijä sarakkeelle.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Lisätietoja Lodash-syntaksin käytöstä solun tietojen lukemiseen ja havainnollisten renderöijien luomiseen on [tässä viiteosiossa](#template-reference).
:::

### Useiden sarakkeiden tietojen käyttö {#accessing-multiple-columns}

Lue mallissa vierekkäisten sarakkeiden arvoja käyttämällä `cell.row.getValue("columnId")`-metodia. Tämä on hyödyllistä kenttien yhdistämisessä, erotusten laskemisessa tai toisiinsa liittyvien tietojen vertailussa.

```java
public class ArtistAvatarRenderer extends Renderer<MusicRecord> {
  @Override
  public String build() {
    return /* html */"""
      <%
        const name     = cell.row.getValue("artist");
        const initials = name
          ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
          : '?';
      %>
      <div style="display: flex; align-items: center; gap: 8px;">
        <div style="width: 28px; height: 28px; border-radius: 50%;
          background: var(--dwc-color-primary); color: white;
          display: flex; align-items: center; justify-content: center;
          font-size: 11px; font-weight: 600;">
          <%= initials %>
        </div>
        <span><%= name %></span>
      </div>
    """;
  }
}
```

### Napsautustapahtumat {#click-events}

`IconButtonRenderer` ja `ButtonRenderer` tarjoavat valmiina `addClickListener()`-metodin. Napsautustapahtumasta pääsee rivin tieto-olioon `e.getItem()`-metodilla.

```java
IconButtonRenderer<MusicRecord> deleteBtn = new IconButtonRenderer<>(
  TablerIcon.create("trash").setTheme(Theme.DANGER)
);
deleteBtn.addClickListener(e -> {
  MusicRecord record = e.getItem();
  repository.delete(record);
  table.refresh();
});

table.addColumn("delete", r -> "").setRenderer(deleteBtn);
```

## Suorituskyky: laiska renderöinti <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Jos sarakkeissa käytetään raskaita renderöijiä, kuten tunnisteita, edistymispalkkeja, avatareja tai verkkokomponentteja, laiskan renderöinnin käyttöönotto parantaa vierityksen suorituskykyä.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Kun sarakkeelle asetetaan `setLazyRender(true)`, solussa näkyy kevyt animoitu paikkamerkki käyttäjän vierittäessä. Solun varsinainen sisältö renderöidään, kun vieritys pysähtyy. Asetus koskee yksittäistä saraketta, joten voit ottaa sen käyttöön vain niissä sarakkeissa, jotka siitä hyötyvät.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Milloin laiska renderöinti kannattaa ottaa käyttöön
Solujen renderöijät luovat DOM:iin lisää elementtejä, mikä lisää suorittimen kuormitusta renderöinnin aikana renderöijän tyypistä riippumatta.

Laiska renderöinti voi vähentää suorituskykyhaittoja, kun renderöijää todella tarvitaan. Jos sinun tarvitsee vain muuttaa tai muotoilla arvoa etkä luo monimutkaista DOM-rakennetta, muunna arvo sen sijaan arvontarjoajalla.
:::

## Valmiiden renderöijien viite {#built-in-renderers}

webforJ sisältää kattavan valikoiman renderöijiä yleisimpiin käyttötarkoituksiin. Määritä mikä tahansa niistä sarakkeelle käyttämällä `column.setRenderer(renderer)`-metodia.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Product.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ProductService.java',
]}
height='600px'
/>
<!-- vale on -->

### Teksti ja tunnisteet {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  tyylitelty teksti, valinnainen teema ja tekstikorostukset
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää solun sisällön tavallisena tai tyyliteltynä tekstinä. Tukee teemavärejä ja tekstikorostuksia, kuten lihavointia, kursivointia ja alleviivausta.

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BadgeRenderer</strong>  -  arvo tunnistesirun sisällä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää solun arvon tunniste-elementin sisällä. Tukee teemoja, laajuuksia, värien automaattista määritystä (eri väri kullekin yksilölliselle arvolle) ja valinnaista edellä näytettävää kuvaketta.

```java
BadgeRenderer renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>NullRenderer</strong>  -  paikkamerkki null- tai tyhjille arvoille
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi määritettävän oletustekstin, kun solun arvo on `null` tai tyhjä. Muussa tapauksessa arvo renderöidään sellaisenaan.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Tila ja ilmaisimet {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true-, false- ja null-arvot kuvakkeina
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Korvaa arvot `true`, `false` ja `null` kuvakkeilla. Oletuskuvakkeet ovat valintamerkki, risti ja viiva.


```java
// Default icons
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Custom icons
BooleanRenderer custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>StatusDotRenderer</strong>  -  värillinen ilmaisinpiste solun tekstin vieressä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi pienen värillisen pisteen solun arvon vasemmalle puolelle. Yhdistä yksittäiset arvot teemoihin, CSS-värimerkkijonoihin tai `java.awt.Color`-olioihin.

```java
StatusDotRenderer renderer = new StatusDotRenderer<>();
renderer.addMapping("Active",    Theme.SUCCESS);
renderer.addMapping("Pending",   Theme.WARNING);
renderer.addMapping("Cancelled", Theme.DANGER);

table.addColumn("status", Order::getStatus).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Luvut, valuutat ja päivämäärät {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  alueasetukset huomioiva valuuttamuotoilu
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon valuuttasummaksi annetun `Locale`-olion sääntöjen mukaan.

```java
// US dollars
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Euros with German locale
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  prosenttiosuus ja valinnainen pieni edistymispalkki
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää numeerisen arvon prosenttiosuutena. Aseta konstruktorin toiseksi argumentiksi `false`, jos et halua ohutta edistymispalkkia tekstin alle.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  täysikokoinen edistymispalkki numeerisille arvoille
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi koko levyisen edistymispalkin, jonka pienimmät ja suurimmat raja-arvot voi määrittää. Tukee määrittämätöntä tilaa sekä raidallista tai animoitua esitystapaa. Voit sijoittaa palkin päälle mukautettua tekstiä käyttämällä `setText()`-metodia ja lodash-lauseketta.

```java
ProgressBarRenderer renderer = new ProgressBarRenderer<>();
renderer.setMax(100);
renderer.setTheme(Theme.SUCCESS);
renderer.setTextVisible(true);
renderer.setText("<%= cell.value %>/100");

table.addColumn("progress", Task::getProgress).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedTextRenderer</strong>  -  merkkijono tekstimaskilla muotoiltuna
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Käyttää merkkijonoarvoon merkkimaskia. `#` vastaa mitä tahansa numeroa; kirjaimelliset merkit säilytetään. Katso kaikki tuetut maskimerkit kohdasta [tekstimaskin säännöt](/docs/components/fields/masked/textfield#mask-rules).

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numeerinen arvo lukumaskilla muotoiltuna
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon mallimerkkijonon avulla ja huomioi alueasetusten mukaiset erottimet. `0` pakottaa numeron näkymään; `#` on valinnainen. Katso kaikki tuetut maskimerkit kohdasta [lukumaskin säännöt](/docs/components/fields/masked/numberfield#mask-rules).

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  päivämäärä- tai aika-arvo päivämäärämaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee päivämäärä- tai aika-arvon mallimerkintöjen avulla: `%Mz` (kuukausi), `%Dz` (päivä), `%Yz` (vuosi) ja muita. Katso kaikki käytettävissä olevat merkinnät kohdasta [päivämäärämaskin säännöt](/docs/components/fields/masked/datefield#mask-rules).

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Linkit ja media {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  sähköpostiosoite napsautettavana mailto-linkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Lisää solun arvon ympärille `mailto:`-ankkurin. Oletuksena visuaalisena vihjeenä on pääteeman mukainen sähköpostikuvake.

```java
// Default mail icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Custom icon
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  puhelinnumero napsautettavana tel-linkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Lisää solun arvon ympärille `tel:`-ankkurin. Mobiililaitteessa napautus avaa numerovalitsimen. Oletuksena näytetään pääteeman mukainen puhelinkuvake.

```java
// Default phone icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Custom icon
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  solun arvo mukautettavana hyperlinkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi napsautettavan ankkurielementin. `href` tukee lodash-mallilausekkeita, joten voit muodostaa URL-osoitteita dynaamisesti solun arvosta.

```java
AnchorRenderer renderer = new AnchorRenderer<>();
renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
renderer.setTarget("_blank");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ImageRenderer</strong>  -  solun sisäinen kuva
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää kuvan. `src`-attribuutti tukee lodash-mallilausekkeita, joten kullekin riville voidaan näyttää eri kuva.

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Cover");

table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Henkilöt ja avatarit {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar, jonka nimikirjaimet luodaan automaattisesti
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi avatar-komponentin. Nimikirjaimet johdetaan automaattisesti solun arvosta. Tukee teemoja ja varakuvaketta.

```java
AvatarRenderer renderer = new AvatarRenderer<>();
renderer.setTheme(AvatarTheme.PRIMARY);
renderer.setIcon(TablerIcon.create("user"));

table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Kuvakkeet ja toiminnot {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  yksittäinen kuvake, joka voi olla napsautettava
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderöi yksittäisen kuvakkeen. Lisää napsautuskuuntelija vuorovaikutteista toimintaa varten.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  toimintokuvakepainike, joka voi käyttää rivin tietoja
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi napsautettavan kuvakepainikkeen. Napsautustapahtumasta pääsee rivin alkioon `e.getItem()`-metodilla, joten se sopii erinomaisesti rivikohtaisiin toimintoihin.

```java
IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("actions", r -> "").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ButtonRenderer</strong>  -  teeman mukainen painike solussa
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderöi solun sisälle täysikokoisen `Button`-komponentin.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Edit");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Edit").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  raaka HTML-elementti, jonka sisältö muodostetaan Lodashilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderöi minkä tahansa HTML-elementin, jonka sisältö annetaan lodash-mallimerkkijonona. Tätä voi käyttää tilanteissa, joihin mikään valmis renderöijä ei sovi.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Mallien viite {#template-reference}

Renderöijät tarjoavat tehokkaan tavan mukauttaa tietojen näyttämistä `Table`-komponentissa. Keskeinen `Renderer`-luokka on tarkoitettu laajennettavaksi, jotta voit luoda lodash-malleihin perustuvia mukautettuja renderöijiä. Näin sisältöä voidaan renderöidä dynaamisesti ja vuorovaikutteisesti.

Lodash-mallien avulla HTML:ää voidaan lisätä suoraan taulukon soluihin, joten ne sopivat hyvin monimutkaisten solutietojen esittämiseen `Table`-komponentissa. Tällä menetelmällä HTML voidaan luoda dynaamisesti solun tietojen perusteella, mikä mahdollistaa monipuolisen ja vuorovaikutteisen solusisällön.

### Lodash-syntaksi {#lodash-syntax}

Seuraavassa osiossa esitellään Lodash-syntaksin perusteet. Tämä ei ole kattava yleiskatsaus, mutta sen avulla pääset alkuun Lodashin käytössä `Table`-komponentissa.

#### Lodash-mallien syntaksin yleiskatsaus: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvot lisäämällä malliin JavaScript-koodin tuloksen.
- `<% ... %>` - Suorittaa JavaScript-koodia, mikä mahdollistaa silmukat, ehdot ja muuta.
- `<%- ... %>` - Escapettaa HTML-sisällön ja suojaa näin interpoloidut tiedot HTML-injektiolta.

#### Solutietojen käyttöesimerkkejä: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: näytä solun arvo suoraan.

`<%= cell.value %>`

**2. Ehdollinen renderöinti**: käytä JavaScript-logiikkaa sisällön ehdolliseen renderöintiin.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Tietokenttien yhdistäminen**: renderöi sisältöä käyttämällä solun useita tietokenttiä.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön escapettaminen**: renderöi käyttäjän luoma sisältö turvallisesti.

Renderöijällä on asiakaspuolella käytettävissään solun, rivin ja sarakkeen yksityiskohtaiset ominaisuudet:

**TableCell-ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|column|`TableColumn`|Soluun liittyvä sarakeolio.|
|first|`boolean`|Ilmaisee, onko solu rivin ensimmäinen.|
|id|`String`|Solun tunniste.|
|index|`int`|Solun indeksi rivillään.|
|last|`boolean`|Ilmaisee, onko solu rivin viimeinen.|
|row|`TableRow`|Soluun liittyvä riviolio.|
|value|`Object`|Solun raaka-arvo suoraan tietolähteestä.|

**TableRow-ominaisuudet:**

|Ominaisuus|Tyyppi|Kuvaus|
|-|-|-|
|cells|`TableCell[]`|Rivin solut.|
|data|`Object`|Sovelluksen riville antamat tiedot.|
|even|`boolean`|Ilmaisee, onko rivi parillinen (tyylittelyä varten).|
|first|`boolean`|Ilmaisee, onko rivi taulukon ensimmäinen.|
|id|`String`|Rivin yksilöllinen tunniste.|
|index|`int`|Rivin indeksi.|
|last|`boolean`|Ilmaisee, onko rivi taulukon viimeinen.|
|odd|`boolean`|Ilmaisee, onko rivi pariton (tyylittelyä varten).|

**TableColumn-ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|align|ColumnAlignment|Sarakkeen tasaus (vasen, keskitetty, oikea).|
|id|String|Riviolion kenttä, josta solun tiedot haetaan.|
|label|String|Sarakkeen otsikossa näytettävä nimi.|
|pinned|ColumnPinDirection|Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen).|
|sortable|boolean|Jos arvo on true, sarake voidaan lajitella.|
|sort|SortDirection|Lajittelujärjestys.|
|type|ColumnType|Sarakkeen tyyppi (teksti, numero, totuusarvo jne.).|
|minWidth|number|Sarakkeen vähimmäisleveys pikseleinä.|
