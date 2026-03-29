---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
_i18n_hash: c6f33a66de68ddcd600382bf0dc449f2
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

Renderöri hallitsee, kuinka jokainen solun sisältö sarakkeessa näytetään. Sen sijaan, että näytetään raakaa arvoa, renderöijä muuntaa jokaisen solun datan tyylitetyksi tekstiksi, ikoneiksi, merkinnöiksi, linkeiksi, toimintopainikkeiksi tai muiksi visuaalisiksi elementeiksi, jotka tekevät datasta nopeammin luettavaa ja helpommin käsiteltävää.

Renderöinti tapahtuu kokonaan selaimessa. Palvelin lähettää raakadatan ja asiakas hoitaa esityksen, mikä tekee 'Taulukosta' nopean rivimäärästä riippumatta.

Määritä renderöijä sarakkeelle käyttäen `setRenderer()`. Renderöijä soveltuu tasapuolisesti kaikille sen sarakkeen soluille:

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip Renderöijät vs. arvon tarjoajat
Jos tarvitset vain soluarvon muuntamista tai muotoilua ilman DOM-rakenteen tuottamista, käytä [arvon tarjoajaa](/docs/components/table/columns#value-providers) sen sijaan. Renderöijät luovat lisä-DOM-elementtejä jokaiselle renderöidylle riville, mikä lisää kustannuksia renderöintiaikana. Varaudu renderöijöihin visuaalista tuotantoa varten, kuten ikoneita, merkintöjä, painikkeita tai muuta HTML-pohjaista esitystä.
:::

webforJ sisältää valmiita renderöijöitä yleisimmille käyttötarkoituksille. Jotta voit luoda erityisesti sovelluksellesi, laajenna `Renderer`-luokkaa ja toteuta `build()`, jotta se palauttaa lodash-mallitangan merkkijonon, joka suoritetaan selaimessa jokaisessa solussa.

## Yleiset renderöijät {#common-renderers}

Seuraavat esimerkit esittelevät neljää usein käytettyä renderöijää ja näyttävät `setRenderer()`-mallin käytännössä.

### TextRenderer {#text-renderer}

Näyttää solun sisällön tavallisena tai tyyliteltynä tekstinä. Voit soveltaa teeman väriä tai tekstikoristeita sarakkeeseen muuttamatta sen rakennetta, kuten korostaa prioriteettikenttää punaiseksi tai tehdä avaintunnistajan lihavaksi.

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

Käyttää solun arvoa merkintäelementtiin. Tukee teemoja, laajuuksia, väriteemoja (automaattiset eri värit jokaiselle ainutlaatuiselle arvolle) ja valinnaista johtavaa ikonia. Käytä sitä kategorisille arvoille, kuten tageille, tyypeille tai merkinnöille, missä erottuvat visuaaliset merkit auttavat käyttäjiä skannaamaan ja vertailemaan rivejä nopeasti.

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

Korvataan `true`, `false` ja `null` arvot ikoneilla. Käytä sitä kaikissa totuus/arvo sarakkeissa, joissa ikoni viestii arvosta nopeammin kuin teksti, kuten ominaisuusliput, aktiivinen/inaktiivinen tila tai opt-in kentät.

```java
// Oletusikonit
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Mukautetut ikonit
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

Muotoilee numeerisen arvon valuuttamääräksi käyttäen annettua `Locale`:ä. Käytä sitä rahallisiin sarakkeisiin, joissa paikallisesti oikea muotoilu (symboli, erotinmerkit, desimaalit) on tärkeää.

```java
// Yhdysvaltain dollarit
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Eurot Saksan paikalla
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## Ehdollinen renderöinti {#conditional-rendering}

`ConditionalRenderer` valitsee eri renderöijän solukohtaisesti solun arvon mukaan. Ehdot arvioidaan järjestyksessä; ensimmäinen osuma voittaa. Kaiken kattava vararengas voidaan asettaa `otherwise()`-toiminnolla.


Seuraava esimerkki osoittaa ehdollisen renderöinnin soveltamista laskun tilasarakkeeseen, vaihtaen `BadgeRenderer`-variantteihin arvon mukaan:

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

Se toimii myös hyvin numeeristen kynnysten kanssa. Tämä palvelin kojelauta käyttää `ConditionalRenderer`-toimintoa vaihtaakseen `ProgressBarRenderer`-teemoja CPU- ja muistin käyttöasteiden mukaan:

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
height='600px'
/>
<!-- vale on -->

### Ehto API {#condition-api}

Ehdot rakennetaan staattisten tehdasmenetelmien avulla ja niitä voidaan yhdistää `and()`, `or()`, ja `negate()`.

```java
// Arvon yhtäläisyys
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// Numeeriset vertailut
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// Totuus / tyhjyyden tarkistus
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// Merkkijonon osumat
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// Yhdistelmät
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// Ristiin-saraketarkistus
Condition.column("status").equalTo("active")

// Raaka JavaScript-lauseke
Condition.expression("cell.value % 2 === 0")
```

## Komposiittirenderöinti {#composite-rendering}

`CompositeRenderer` yhdistää useita renderöijiä vierekkäin yhdessä solussa käyttäen flex-asettelua. Käytä sitä yhdistääksesi kuvakkeen tekstin kanssa, näyttääksesi avatarin nimen vieressä tai pinottaksesi merkin statustiedot.

Alla oleva työntekijäluettelo käyttää `CompositeRenderer`-toimintoa *Työntekijä*-sarakeessa, esittääkseen automaattisesti luodun avatarin jokaisen työntekijän nimen vieressä:

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## Mukautetut renderöijät {#custom-renderers}

Kun mikään sisäänrakennetuista renderöijistä ei sovi käyttötarkoitukseesi, laajenna `Renderer`-luokkaa ja toteuta `build()`. Menetelmä palauttaa lodash-mallitangan merkkijonon, joka suoritetaan selaimessa jokaisessa sarakkeen solussa, ilmaisten yhdistelmän HTML:stä ja JavaScriptistä.

### Mukautetun renderöijän luominen {#creating-a-custom-renderer}

**Vaihe 1:** Laajenna `Renderer`-luokkaa rividata tyypisi mukaan.

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**Vaihe 2:** Korvaa `build()` ja palauta lodash-mallitangan merkkijono.

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
Lisätietoja siitä, kuinka käyttää Lodash-syntaksia solutietojen käyttöön ja informatiivisten renderöijien luomiseen, katso [tämä viittausosio](#template-reference).
:::

### Useiden sarakkeiden käyttö {#accessing-multiple-columns}

Käytä `cell.row.getValue("columnId")` lukemaan sisarsarakkeita mallissa. Tämä on hyödyllistä kenttien yhdistämisessä, delta-laskemisessa tai liittyvien tietojen ristiviittaamisessa.

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

`IconButtonRenderer` ja `ButtonRenderer` tarjoavat `addClickListener()` ennen kaikkea. Klikkaustapahtuma tarjoaa pääsyn rivin tietojoukkoon `e.getItem()` kautta.

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

Sarakkeille, jotka käyttävät visuaalisesti raskaita renderöijöitä, kuten merkintöjä, edistyspalkkeja, avatareja tai web-komponentteja, ota käyttöön laiska renderöinti parantaaksesi vieritysnopeutta.

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

Kun `setLazyRender(true)` on asetettu sarakkeelle, solut näyttävät kevyen animaatio-odottajan käyttäjän vierittäessä. Varsinainen solun sisältö renderöidään vasta, kun vieritys pysähtyy. Tämä on sarakekohtainen asetus, joten voit ottaa sen käyttöön valikoivasti vain sarakkeille, joista on hyötyä.

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip Milloin ottaa käyttöön laiska renderöinti
Solujen renderöijät luovat enemmän entiteettejä DOM:iin, mikä tarkoittaa enemmän CPU-työtä renderöintiaikana, riippumatta siitä, mikä renderöijä sen luo. 

Laiska renderöinti voi auttaa vähentämään suorituskykyvaikutusta, jos renderöijä on todella tarpeellinen. Jos tarvitset vain arvon muuttamista tai muotoilua etkä luo monimutkaista DOM:ia, käytä sen sijaan arvon tarjoajaa muuttaa arvot.
:::

## Valmiiden renderöijien viittaus {#built-in-renderers} 

webforJ toimittaa kattavan sarjan renderöijöitä yleisimmille käyttötarkoituksille. Määritä mikä tahansa niistä sarakkeelle käyttäen `column.setRenderer(renderer)`.

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### Tekstit ja merkinnät {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  tyylitelty teksti valinnaisilla teemoilla ja koristeilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää solun sisällön tavallisena tai tyyliteltynä tekstinä. Tukee teeman värejä ja tekstikoristeita, kuten lihavaa, kursiivista ja alleviivausta.

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
<strong>BadgeRenderer</strong>  -  arvo näytetään merkintämerkissä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Käyttää solun arvoa merkintäelementtiin. Tukee teemoja, laajuuksia, väriteemoja (automaattiset eri värit jokaiselle ainutlaatuiselle arvolle) ja valinnaista johtavaa ikonia.

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

Renderöi konfiguroitavan varamerkin, kun soluarvo on `null` tai tyhjät; muuten renderöi arvon sellaisenaan.

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### Tilat ja indikaattorit {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null näytetään ikoneina
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Korvataan `true`, `false` ja `null` arvot ikoneilla. Oletuksena tarkistus, risti ja viiva.


```java
// Oletusikonit
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// Mukautetut ikonit
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
<strong>StatusDotRenderer</strong>  -  värillinen indikaattoripiste solutekstin vieressä
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi pienen värillisen pisteen soluarvon vasemmalle puolelle. Määritä yksittäiset arvot teemoille, CSS-väri-merkkijonoille tai `java.awt.Color`-instansseille.

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

### Numerot, valuutat ja päivämäärät {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  paikallisesti tietoinen valuuttamuotoilu
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon valuuttamääräksi käyttäen annettua `Locale`:ä.

```java
// Yhdysvaltain dollarit
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// Eurot Saksan paikalla
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  prosentti valinnaisella mini edistyspalkilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää numeerisen arvon prosenttina. Aseta toinen konstruktorin argumentti `false`, jotta estetään ohuen edistyspalkin renderöinti tekstin alle.

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  täysleveä edistyspalkki numeerisille arvoille
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi täysleveän edistyspalkin konfiguroitavilla minimillä ja maksimilla, määräämättömällä tilalla ja raidallisella tai animoidulla esityksellä. Käytä `setText()`-toimintoa lodash-lausekkeen kanssa, jotta voit ylittää mukautettua tekstiä palkille.

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
<strong>MaskedTextRenderer</strong>  -  merkkijono muotoiltuna tekstimaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Käyttää merkki-maskia merkkijonoarvoon. `#` vastaa mitä tahansa numeroa; kirjaimellisia merkkejä säilytetään. Katso [tekstimaskisäännöt](/docs/components/fields/masked/textfield#mask-rules) kaikille tuetuille maskimerkeille.

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  numeerinen arvo, joka on muotoiltu numeromaskin avulla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee numeerisen arvon kaavamerenkkijonolla, joka sisältää paikallisesti oikeat erotusmerkit. `0` pakottaa numeron; `#` on valinnainen. Katso [numeromaskisäännöt](/docs/components/fields/masked/numberfield#mask-rules) kaikille tuetuille maskimerkeille.

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  päivämäärä/aika arvo päivämäärämaskilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Muotoilee päivämäärä- tai aikaarvon käyttäen kaavamerkkejä: `%Mz` (kuukausi), `%Dz` (päivä), `%Yz` (vuosi) ja muita. Katso [päivämäärämaskisäännöt](/docs/components/fields/masked/datefield#mask-rules) kaikille saatavilla oleville tokeneille.

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

Käyttää solun arvoa `mailto:`-ankkurissa. Ensisijaisesti teemoitettu postikuvake toimii visuaalisena vihjeenä oletuksena.

```java
// Oletusposti kuvake
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// Mukautettu kuvake
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

Käyttää solun arvoa `tel:`-ankkurissa. Mobiililaitteilla napautus avaa puhelinvalintaohjelman. Ensisijaisesti teemoitettu puhelinkuvake näkyy oletuksena.

```java
// Oletuspuhekuvake
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// Mukautettu kuvake
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

Renderöi napsautettavan ankkuri-elementin. `href` tukee lodash-mallin lausekkeita, joten voit rakentaa URL-osoitteita dynaamisesti soluarvosta.

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
<strong>ImageRenderer</strong>  -  inline kuva solussa
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Näyttää kuvan. `src`-attribuutti tukee lodash-mallin lausekkeita, jotta jokainen rivi voi näyttää eri kuvan.

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Kansi");

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
<strong>AvatarRenderer</strong>  -  avatar automaattisesti luoduilla alkeilla
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi avatar-komponentin. Alustukset on automaattisesti johdettu solun arvosta. Tukee teemoja ja varakuvaketta.

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

### Ikonit ja toiminnot {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  itsenäinen ikoni, valinnaisesti napsautettava
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

Renderöi yksittäisen ikonin. Liitä napsautustapahtuma interaktiiviseen käyttäytymiseen.

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  toiminnallinen ikoni painike rivin käytölle
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

Renderöi napsautettavan ikonipainikkeen. Klikkaustapahtuma tarjoaa rivitiedon `e.getItem()` kautta, mikä tekee siitä ihanteellisen rivitason toimille.

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

Renderöi täydellinen `Button`-komponentti solun sisällä.

```java
ButtonRenderer renderer = new ButtonRenderer<>("Muokkaa");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Muokkaa").setRenderer(renderer);
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

Renderöi mitä tahansa HTML-elementtiä lodash-mallitangan sisällön merkkijonolla. Tämä on pakoreitti tilanteisiin, joissa mikään sisäänrakennetuista renderöijistä ei sovi.

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## Malliviittaus {#template-reference}

Renderöijät tarjoavat tehokkaan mekanismin datan esittämisen mukauttamiseksi `Taulukossa`. Pääluokka, `Renderer`, on suunniteltu laajennettavaksi luomaan mukautettuja renderöijiä lodash-mallin perusteella, mahdollistaen dynaamisen ja interaktiivisen sisällön renderoinnin.

Lodash-mallit mahdollistavat HTML:n suoran lisäämisen taulukon soluihin, mikä tekee niistä erittäin tehokkaita monimutkaisten solutietojen renderoimisessa `Taulukossa`. Tämä lähestymistapa mahdollistaa HTML:n dynaamisen luomisen soludatan perusteella, jotta mahdollistetaan rikkaat ja interaktiiviset taulukon solusisällöt.

### Lodash-syntaksi {#lodash-syntax}

Seuraavassa osiossa käsitellään Lodash-syntaksin perusteet. Vaikka tämä ei ole kattava, se voi auttaa aloittamaan Lodashin käytön `Taulukko`-komponentissa. 

#### Syntaksin yleiskuvaus lodash-malleille: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvot, lisäten JavaScript-koodin tuloksen malliin.
- `<% ... %>` - Suorittaa JavaScript-koodia, mahdollistaen silmukat, ehtolauseet ja muut.
- `<%- ... %>` - Pakottaa HTML-sisällön, varmistaen, että interpoloidut tiedot ovat suojattuja HTML-injektiohyökkäyksiltä.

#### Esimerkkejä soludatasta: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: näytä suoraan solun arvo.

`<%= cell.value %>`

**2. Ehdollinen renderöinti**: käytä JavaScript-logiikkaa ehdollisen sisällön renderoimiseen.

`<% if (cell.value > 100) { %> 'Korkea' <% } else { %> 'Normaali' <% } %>`

**3. Datan kenttien yhdistäminen**: renderöi sisältö käyttäen useita datakenttiä solusta.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön pakottaminen**: renderöi turvallisesti käyttäjältä luotua sisältöä.

Renderöijällä on pääsy yksityiskohtaisiin solun, rivin ja sarakkeen ominaisuuksiin asiakaspinnassa:

**TableCell Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|column|`TableColumn`|Liitetty sarakeobjekti.|
|first|`boolean`|Ilmaisee, onko solu ensimmäinen rivillä.|
|id|`String`|Solun tunnus.|
|index|`int`|Solun indeksi rivillä.|
|last|`boolean`|Ilmaisee, onko solu viimeinen rivillä.|
|row|`TableRow`|Liitetty riviobjekti solulle.|
|value|`Object`|Solun raaka arvo datalähteestä.|

**TableRow Ominaisuudet:**

|Ominaisuus|Tyyppi|Kuvaus|
|-|-|-|
|cells|`TableCell[]`|Rivin solut.
|data|`Object`|Sovelluksen tarjoama data riville.
|even|`boolean`|Ilmaisee, onko rivi parillinen (tyylitarkoituksiin).
|first|`boolean`|Ilmaisee, onko rivi ensimmäinen taulukossa.
|id|`String`|Rivin ainutlaatuinen tunnus.
|index|`int`|Rivin indeksi.
|last|`boolean`|Ilmaisee, onko rivi viimeinen taulukossa.
|odd|`boolean`|Ilmaisee, onko rivi pariton (tyylitarkoituksiin).

**TableColumn Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|align|ColumnAlignment|Sarakkeen kohdistus (vasen, keski, oikea).
|id|String|Rivin objektista saatu solun datan kenttä.
|label|String|Nimi, joka renderöidään sarakeotsikossa.
|pinned|ColumnPinDirection|Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen).
|sortable|boolean|Jos totta, saraketta voidaan lajitella.
|sort|SortDirection|Sarakkeen lajittelujärjestys.
|type|ColumnType|Sarakkeen tyyppi (teksti, numero, boolean, jne.).
|minWidth|number|Sarakkeen vähimmäisleveys pikseleissä.
