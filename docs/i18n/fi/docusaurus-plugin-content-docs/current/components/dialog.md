---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 621dc045e979c7513b41ef04c6cd242a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän, kiinnittäen huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedotuksiin.

<!-- INTRO_END -->

## `Dialog`-rakenne {#dialog-structure}

`Dialog` on järjestetty kolmeen osioon: yläosa, sisältöalue ja alatunniste. Komponentteja voidaan lisätä kuhunkin osioon käyttämällä `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Käyttötapaukset {#usages}

1. **Käyttäjäpalaute ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen pyytämiseen. Ne voivat näyttää erilaisia tärkeitä palautteita käyttäjälle, kuten:

  >- Menestysviestit 
  >- Virheilmoitukset
  >- Vahvistuslähetykset

2. **Lomakekenttä ja muokkaaminen**: Voit käyttää dialogeja kerätäksesi käyttäjäsyötteitä tai salliaksesi heidän muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi dialogi voi avautua muokkaamaan käyttäjäprofiilin tietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen informaatio**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisempia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai ohjeasiakirjoja.

4. **Kuva- ja mediapreviisit**: Kun käyttäjien on tarpeen tarkastella mediatietoja, `Dialog`-komponenttia voidaan käyttää suurempien esikatselujen tai gallerioiden näyttämiseen, esimerkiksi vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Tausta ja blurred {#backdrop-and-blur}

Omaamalla webforJ `Dialog`-komponentin taustatoiminnon, tausta näytetään `Dialog`-komponentin taakse. Lisäksi, kun se on käytössä, Dialogin blurred-attribuutti blurraa `Dialog`-komponentin taustan. Näiden asetusten muokkaaminen voi auttaa käyttäjiä tarjoamalla syvyyksiä, visuaalista hierarkiaa ja kontekstia, mikä johtaa selkeämpään ohjaukseen käyttäjälle.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## `Dialog`-komponentin avaus ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia dialogin näyttämiseen. Tämän jälkeen `Dialog`-komponentti voidaan sulkea jollakin seuraavista toimista:
- Käyttämällä `close()`-metodia
- Painaen <kbd>ESC</kbd>-näppäintä
- Napsauttamalla dialogin ulkopuolella

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialog`-komponentin käyttämällä `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd>-näppäimen painamisen että ulkopuolella napsauttamisen dialogin sulkemiseksi.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Automaatiofokus {#auto-focus}

Kun automaatiofokus on käytössä, se antaa automaattisesti fokuksen dialogin ensimmäiselle fokusoidulle elementille. Tämä on hyödyllistä käyttäjien huomion suuntaamisessa ja se on mukautettavissa `setAutoFocus()`-metodin kautta.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentissa on sisäänrakennettu vedettävä toiminto, joka sallii käyttäjän siirtää `Dialog`-ikkunan napsauttamalla ja vetämällä. `Dialog`-komponentin sijaintia voidaan muokata mistä tahansa sen kentistä: yläosasta, sisällöstä tai alatunnisteesta.

### Reunalle napsautus {#snap-to-edge}
On myös mahdollista kalibroida tämä käyttäytyminen napsauttamaan näytön reunaa, mikä tarkoittaa, että `Dialog`-komponentti kohdistuu automaattisesti näytön reunalle, kun se vapautetaan vedon jälkeen. Napsauttamista voidaan muuttaa `setSnapToEdge()`-metodin avulla. `setSnapThreshold()` ottaa vastaan pikselimäärän, joka määrittää, kuinka kaukana `Dialog`-komponentin on oltava näytön reunasta ennen kuin se napsahtaa automaattisesti reunoille.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Asettelu {#positioning}

Dialogin sijaintia voidaan muokata käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()`-metodeja. Nämä metodit ottavat vastaan merkkijonoargumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Lista näistä mittauksista [löytyvät tältä linkiltä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Pystysuuntainen tasaus {#vertical-alignment}

Manuaalisen dialogin X- ja Y-sijainnin määrityksen lisäksi on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa dialogin tasaamiseen. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Koko näyttö ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa täyteen näyttöön. Kun koko näyttö on käytössä, `Dialog`-komponenttia ei voi siirtää tai asettaa. Tämä tila voidaan muokata dialogin katkaisupisteattribuutin kautta. Katkaisupiste on media-kysely, joka määrittää, milloin `Dialog` siirtyy automaattisesti täyteen näyttötilaan. Kun kysely osuu, `Dialog` muuttuu täyteen näyttöön - muuten se on sijoitettu.

### Autoleveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletuksena `Dialog` venyy täyttämään käytettävissä olevan vaaka-alueen. Kun automaattinen leveys on käytössä `setAutoWidth(true)`-metodin avulla, `Dialog` määrittää itsensä sen sisällön leveyden mukaan.

<ComponentDemo 
path='/webforj/dialogautowidth?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java'
height = '350px'
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Dialog`-komponentit sisältävät <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillistä teemaa</JavadocLink>, jotka on rakennettu ihanteelliseen tyylittelyyn ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, jotka voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat kiireisen ja johdonmukaisen tavan mukauttaa painikkeiden ilmettä koko sovelluksessa.

Vaikka jokaiselle eri teemoille on useita käyttötapauksia, tässä on joitakin esimerkkikäyttöjä:

  - **Vaarallinen**: Toimet, joilla on vakavia seurauksia, kuten täytetyn tiedon tyhjentäminen tai tilin/tietojen pysyvä poistaminen, on hyvä käyttää dialogeja Vaarallinen-teemalla.
  - **Oletus**: Oletusteema on sopiva toiminnoille sovelluksessa, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Pääteema**: Tämä teema on sopiva pää "toimintakehotteena" sivulla, kuten rekisteröiminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Menestys**: Menestyksellä teematut dialogit ovat erinomaisia visualisoimaan sovelluksen elementin onnistunutta valmistumista, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin päättymistä. Menestyksellinen teema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittaakseen käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jolla on tallentamattomia muutoksia. Nämä toimet ovat yleensä vähemmän vaikuttavia kuin ne, jotka käyttäisivät Vaarallinen-teemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän täydentäviä sivulle eikä osa päätoiminnallisuutta.
  - **Tieto**: Tieto-teema on hyvä valinta tarjoamaan selventävää, lisätietoa käyttäjälle tarvittaessa.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
