---
sidebar_position: 20
title: Rendering
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 314a210c06d9b920038308ed7c357f97
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Renderointi valvoo, kuinka jokainen sarakkeen solu näytetään. Sen sijaan, että näyttäisit raakatiedon, renderointi muuttaa kunkin solun tiedot tyylitetyksi tekstiksi, kuvakkeiksi, merkinnöiksi, linkeiksi, toimintopainikkeiksi tai muuksi visuaaliksi, joka tekee tiedon lukemisesta nopeampaa ja toimimisen helpompaa.

Renderointi tapahtuu kokonaan selaimessa. Palvelin lähettää raakatiedot ja asiakas käsittelee esityksen, jolloin 'Taulukko' on nopea riippumatta rivimäärästä.

Määritä renderointi sarakkeelle käyttämällä `setRenderer()`. Renderointi soveltuu kaikille kyseisen sarakkeen soluille:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderointifunktiot vs. arvon tarjoajat
Jos tarvitset vain muuttaa tai muotoilla soluarvon ilman DOM-rakenteen tuottamista, käytä [arvon tarjoajaa](/docs/components/table/columns#value-providers) sen sijaan. Renderoijat luovat ylimääräisiä DOM-elementtejä jokaiselle renderöidylle riville, mikä aiheuttaa kustannuksia renderöintihetkellä. Varaa renderointi visuaaliselle ulostulolle, kuten kuvakkeille, merkinnöille, painikkeille tai muille HTML-pohjaisille esityksille.
:::

webforJ sisältää valmiita renderoijia yleisimmille käyttötapauksille. Jos jokin on erityistä sovelluksellesi, laajenna `Renderer` ja käytä `build()` palauttaaksesi lodash-mallin merkkijonon, joka toimii selaimessa jokaiselle solulle.

## Yleisimmät renderoijat {#common-renderers}

Seuraavat esimerkit käyvät läpi neljä usein käytettyä renderoijaa ja näyttävät `setRenderer()`-mallin käytännössä.

### TextRenderer {#text-renderer}

Näyttää solun sisällön yksinkertaisena tai tyyliteltynä tekstinä. Voit soveltaa teeman väriä tai tekstin muotoilua sarakkeeseen ilman sen rakenteen muuttamista, esimerkiksi korostamalla prioriteettikenttää punaiseksi tai tekemällä avaintunnisteen lihavoiduksi.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Suojaa solun arvon merkintäelementtiin. Tukee teemoja, laajuuksia, väri-istutusta (automaattiset erilaiset värit jokaiselle ainutlaatuiselle arvolla) ja valinnaista etukuvaketta. Käytä sitä kategorisille arvoille, kuten tageille, tyypeille tai tunnisteille, joissa erityiset visuaaliset komponentit auttavat käyttäjiä skannaamaan ja vertaamaan rivejä nopeasti.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Korvataan `true`, `false` ja `null` kuvakkeilla. Käytä sitä kaikille tosi/epätosi -sarakkeille, joissa kuvake välittää arvon nopeammin kuin teksti, kuten ominaisuuksien lipuissa, aktiivisessa/epäkiitollisessa tilassa tai opt-in-kentissä.

```java
// Oletuskirjaimet
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Mukautetut kuvakkeet
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Muotoilee numeerisen arvon valuuttamääräksi käyttäen annettua `Locale`-sääntöä. Käytä sitä kaikille rahasarakkeille, joissa paikannettavalla muotoilulla (symboli, erottimet, desimaalipaikat) on merkitystä.

```java
// Yhdysvaltain dollarit
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Eurot saksalaisella paikalla
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Ehdollinen renderointi {#conditional-rendering}

`ConditionalRenderer` valitsee eri renderoijan per solun sen arvon perusteella. Ehtoja arvioidaan järjestyksessä; ensimmäinen osuma voittaa. Yleinen varmuus voidaan asettaa `otherwise()`-menetelmällä.

Seuraava esimerkki näyttää ehdollisen renderoinnin sovellettuna laskun tila-sarakkeeseen, vaihtaen `BadgeRenderer`-variantteihin sen arvon perusteella: 

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

Se toimii myös hyvin numeeristen kynnysten kanssa. Tämä palvelin kojelauta käyttää `ConditionalRenderer`-menetelmää vaihtaakseen `ProgressBarRenderer`-teemoja CPU- ja muistin käyttöasteiden mukaan: 

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

### Ehto API {#condition-api}

Ehdot rakennetaan staattisilla tehdasmenetelmillä ja niitä voidaan yhdistää `and()`, `or()` ja `negate()`.

```java
// Arvon yhtä suuri
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numeeriset vertailut
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Boolean / tyhjyyttä
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Merkkijonojen vastaavuus
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Koosteen yhdistelmä
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Ristilokero tarkistus
Condition.column("status").equalTo("active")

// Raaka JavaScript-ilmaisu
Condition.expression("cell.value % 2 === 0")
```

## Koostava renderointi {#composite-rendering}

`CompositeRenderer` yhdistää useita renderoijia vierekkäin yhdessä solussa joustavan asettelun avulla. Käytä sitä ikonin parittamiseen tekstin kanssa, näyttääksesi avatarin nimen vieressä tai pinotaksesi merkinnän tilan indikaattorin viereen.

Alempana oleva työntekijäluettelo käyttää `CompositeRenderer`-toimintoa *Työntekijä* -sarakkeessa näyttääkseen automaattisesti luodun avatarin kunkin työntekijän nimen vieressä:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## Mukautetut renderoijat {#custom-renderers}

Kun mikään valmiista renderoijista ei sovi tarpeeseesi, laajenna `Renderer` ja toteuta `build()`. Tämä menetelmä palauttaa lodash-mallin merkkijonon, joka toimii selaimessa jokaiselle sarakkeelle, ilmaistuna HTML:n ja JavaScriptin seoksena.

### Mukautetun renderoijan luominen {#creating-a-custom-renderer}

**Vaihe 1:** Laajenna `Renderer` oman rivitietotyyppisi kanssa.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Vaihe 2:** Ylikirjoita `build()` ja palauta lodash-mallin merkkijono.

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

**Vaihe 3:** Määritä renderoija sarakkeelle.

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
Lisätietoja Lodash-syntaksista, jota käytetään solutietojen hakemiseen ja informatiivisten renderöijien luomiseen, katso [tämä viittausosio](#template-reference).
:::

### Useiden sarakkeiden pääsy {#accessing-multiple-columns}

Käytä `cell.row.getValue("columnId")` lukeaksesi sisarsarakkeita mallissa. Tämä on hyödyllistä yhdistämiseen, deltajen laskemiseen tai liittyvien tietojen ristiinviittaamiseen.

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

### Klikkaustapahtumat {#click-events}

`IconButtonRenderer` ja `ButtonRenderer` tarjoavat `addClickListener()` automaattisesti. Klikkaustapahtuma antaa pääsyn rivin tietoesineeseen `e.getItem()`-menetelmällä.

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

## Suorituskyky: laiska renderointi <DocChip chip='since' label='25.12' /> {#lazy-rendering}

Sarakkeille, jotka käyttävät visuaalisesti kalliita renderoijia, kuten merkintöjä, edistymispalkkeja, avatareita tai verkkokomponentteja, ota käyttöön laiska renderointi parantaaksesi vierityksen suorituskykyä.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Kun `setLazyRender(true)` on asetettu sarakkeelle, solut näyttävät kevyen animaation paikan pitävän elementin, kun käyttäjä vierittää. Varsinaiset solun sisällöt renderoidaan, kun vieritys pysähtyy. Tämä on sarakekohtainen asetus, joten voit ottaa sen käyttöön vain niille sarakkeille, jotka hyötyvät siitä.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip Milloin ottaa käyttöön laiska renderointi
Solun renderoijat luovat enemmän entiteettejä DOM:ssa, mikä tarkoittaa enemmän CPU-työtä renderöinnin aikana, riippumatta siitä, mikä renderoija sen luo.

Laiska renderointi voi auttaa vähentämään suorituskykyvaikutuksia, jos renderointi on todella tarpeen. Jos sinun tarvitsee vain muuttaa tai muotoilla arvoa, etkä luo monimutkaista DOM:ia, käytä sen sijaan arvon tarjoajaa arvon muuttamiseksi.
:::

## Valmiiden renderoijien viite {#built-in-renderers}

webforJ tuottaa kattavan joukon renderoijia yleisimmille käyttötapauksille. Määritä mikä tahansa niistä sarakkeeseen käyttämällä `column.setRenderer(renderer)`.

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

### Tekstit ja etiketti {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  tyylitelty teksti, jossa valinnainen teema ja koristeet
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää solun sisällön yksinkertaisena tai tyyliteltynä tekstinä. Tukee teeman värejä ja tekstimuotoiluja, kuten lihavointi, kursivointi ja alleviivaus.

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
<strong>BadgeRenderer</strong>  -  arvo, joka näytetään merkintäelementissä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Suojaa solun arvon merkintäelementtiin. Tukee teemoja, laajuuksia, väri-istutusta (automaattiset erilaiset värit jokaiselle ainutlaatuiselle arvolla) ja valinnaista etukuvaketta.

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

Renderoi konfiguroitavan palautusmerkkijonon, kun solun arvo on `null` tai tyhjää; muuten renderoi arvon sellaisenaan.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Tila ja indikaattorit {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null esitetään kuvakkeina
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Korvataan `true`, `false` ja `null` arvot kuvakkeilla. Oletuksena shakki, risti ja viiva.

```java
// Oletuskirjaimet
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Mukautetut kuvakkeet
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
<strong>StatusDotRenderer</strong>  -  värillinen indikaattori piste solutekstin vieressä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderoi pieni värillinen piste solun arvon vasemmalle puolelle. Karttaa yksittäisiä arvoja teemoihin, CSS-värijonoksi tai `java.awt.Color`-instansseihin.

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

### Numerot, valuutta ja päivämäärät {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  sijaintitietoista valuutan muotoilua
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon valuuttamääräksi käyttäen annettua `Locale`-sääntöä.

```java
// Yhdysvaltain dollarit
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Eurot saksalaisella paikalla
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  prosentti, jossa valinnainen mini edistymispalkki
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää numeerisen arvon prosenttina. Aseta toinen konstruktorin argumentti `false`:ksi estääksesi ohuen edistymispalkin renderoinnin tekstin alle.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  täysi edistymispalkki numeerisille arvoille
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderoi koko leveydeltä edistymispalkki, jossa on konfiguroitavat minimiset ja maksimaaliset rajat, määrittämättömässä tilassa, ja juovikkaassa tai animoidussa näyttötilassa. Käytä `setText()`-menetelmää yhdessä lodash-ilmaisun kanssa peittääksesi mukautetun tekstin palkin päälle.

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
<strong>MaskedTextRenderer</strong>  -  merkkijonon muotoilu tekstimaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Soveltuu merkintämaskia merkkijonon arvoon. `#` vastaa mitä tahansa numeroa; kirjaimelliset merkit säilytetään. Katso [tekstimaskin säännöt](/docs/components/fields/masked/textfield#mask-rules) kaikista tuetuista maskimerkeistä.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numeerinen arvo, joka on muotoiltu numeromaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon mallimerkkijonolla, jossa on sijainnista riippuvaisia erottimia. `0` pakottaa numeron; `#` on valinnainen. Katso [numeromaskin säännöt](/docs/components/fields/masked/numberfield#mask-rules) kaikista tuetuista maskimerkeistä.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  päivämäärä/aika-arvo päivämäärämaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee päivämäärä- tai aikarvoa käyttäen mallimerkkejä: `%Mz` (kuukausi), `%Dz` (päivä), `%Yz` (vuosi) ja muut. Katso [päivämäärämaskin säännöt](/docs/components/fields/masked/datefield#mask-rules) kaikista saatavilla olevista tokeneista.

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
<strong>EmailRenderer</strong>  -  sähköpostiosoite klikkautuvana mailto-linkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Suojaa solun arvon `mailto:`-linkkiä. Oletuksena on ensisijaisesti teemoitettu sähköposti-ikoni visuaalisena vihjeenä.

```java
// Oletussähköposti-ikoni
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Mukautettu ikoni
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  puhelinnumero klikkautuvana tel-linkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Suojaa solun arvon `tel:`-linkkiä. Mobiililaitteissa napautus avaa valintaikkunan. Oletuksena on ensisijaisesti teemoitu puhelin-ikoni.

```java
// Oletuspuhelin-ikoni
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Mukautettu ikoni
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  solun arvo konfiguroitavana hyperlinkkinä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderoi klikkautuva ankkurielementti. `href` tukee lodash-mallin lausekkeita, joten voit luoda URL-osoitteita dynaamisesti solun arvosta.

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
<strong>ImageRenderer</strong>  -  sisäinen kuva solussa
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää kuvan. `src`-attribuutti tukee lodash-mallilausekkeita, joten jokainen rivi voi näyttää erilaisen kuvan.

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

### Ihmiset ja avatarit {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  avatar automaattisesti luoduilla alkuperäiskirjaimilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderoi avatar-komponentti. Alkuperäiskirjaimet johdetaan automaattisesti solun arvosta. Tukee teemoja ja varakohtaa.

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
<strong>IconRenderer</strong>  -  itsenäinen kuvake, valinnaisesti klikattavissa
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderoi yksittäinen kuvake. Liitä klikkakuuntelija vuorovaikutteiseen käyttäytymiseen.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  toiminnallinen kuvakepainike rivitietoilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderoi klikattava kuvakepainike. Klikkaustapahtuma paljastaa rivin objektin `e.getItem()`, mikä tekee siitä ihanteellisen rivikohtaisille toimille.

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
<strong>ButtonRenderer</strong>  -  teemoitettu painike solussa
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderoi täysi `Button`-komponentti solun sisällä.

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
<strong>ElementRenderer</strong>  -  raaka HTML-elementti lodash-sisällöllä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderoi mikä tahansa HTML-elementti lodash-mallin sisältömerkkijonolla. Tämä on vaihtoehto tilanteissa, joissa mikään valmiista renderoijista ei sovi.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Malliviite {#template-reference}

Renderoijat tarjoavat tehokkaan mekanismin räätälöidä dataa, joka esitetään `Taulukossa`. Pääluokka `Renderer` on suunniteltu laajennettavaksi, jotta voidaan luoda mukautettu renderoija perustuen lodash-malleihin, mahdollistamalla dynaamisen ja vuorovaikutteisen sisällön renderoinnin.

Lodash-mallit mahdollistavat HTML:n suoran lisäämisen taulukon soluihin, mikä tekee niistä erittäin tehokkaita monimutkaisten solutietojen renderoimiseen `Taulukossa`. Tämä lähestymistapa sallii HTML:n dynaamisen luomisen solutietojen perusteella, helpottaen rikkaita ja vuorovaikutteisia taulukon solunsisältöjä.

### Lodash-syntaksi {#lodash-syntax}

Seuraavassa osassa hahmotellaan Lodash-syntaksin perusteita. Vaikka tämä ei ole kattava tai yksityiskohtainen yleiskatsaus, sitä voidaan käyttää auttamaan Lodashin käytössä `Taulukko`-komponentissa.

#### Syntaksin yleiskatsaus lodash-malleille: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvot, lisäten JavaScript-koodin tuloksen malliin.
- `<% ... %>` - Suorittaa JavaScript-koodin, salliaksesi silmukoita, ehtoja jne.
- `<%- ... %>` - Pakenee HTML-sisältöä, varmistaen, että interpoloidut tiedot ovat turvallisia HTML-injektiohyökkäyksiltä.

#### Esimerkit solutiedon käytöstä: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: näytä suoraan solun arvo.

`<%= cell.value %>`

**2. Ehdollinen renderointi**: käytä JavaScript-logiikkaa ehdolliseen sisällön renderointeeseen.

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. Tietokenttien yhdistäminen**: renderoi sisältöä käyttämällä useita tietokenttiä solusta.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön pakottaminen**: renderoi turvallisesti käyttäjän luomaa sisältöä.

Renderoijalla on pääsy yksityiskohtaisiin soluihin, riveihin ja sarakeominaisuuksiin asiakaspinnan puolella:

**TableCell Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|column|`TableColumn`|Liittyvä sarakeobjekti.|
|first|`boolean`|Ilmaisee, onko solu ensimmäinen rivissä.|
|id|`String`|Solu ID.|
|index|`int`|Solun indeksi sen rivissä.|
|last|`boolean`|Ilmaisee, onko solu viimeinen rivissä.|
|row|`TableRow`|Liittyvä riviobjekti solulle.|
|value|`Object`|Solun raaka arvo, suoraan tietolähteestä. |

**TableRow Ominaisuudet:**

|Ominaisuus|Tyyppi|Kuvaus|
|-|-|-|
|cells|`TableCell[]`|Rivissä olevat solut.|
|data|`Object`|Sovelluksen tarjoama data riville.|
|even|`boolean`|Ilmaisee, onko rivi parillinen (tyylitarkoituksiin).|
|first|`boolean`|Ilmaisee, onko rivi ensimmäinen taulukossa.|
|id|`String`|Rivin uniikki ID.|
|index|`int`|Rivin indeksi.|
|last|`boolean`|Ilmaisee, onko rivi viimeinen taulukossa.|
|odd|`boolean`|Ilmaisee, onko rivi pariton (tyylitarkoituksiin).|

**TableColumn Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|align|ColumnAlignment|Sarakkeen tasapaino (vasen, keski, oikea).|
|id|String|Rivin objektista saatavan solun datan kenttä.|
|label|String|Nimi, joka renderoidaan sarakkeen otsikossa.|
|pinned|ColumnPinDirection|Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen).|
|sortable|boolean|Jos true, sarake voidaan lajitella.|
|sort|SortDirection|Sarakkeen lajittelujärjestys.|
|type|ColumnType|Sarakkeen tyyppi (teksti, numero, boolean jne.).|
|minWidth|number|Sarakkeen vähimmäisleveys pikseleinä.
