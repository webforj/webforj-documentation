---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 385730b12eeec91287bcbbf77b4e9c77
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän ja kiinnittää huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedotustietoihin.

<!-- INTRO_END -->

## `Dialog` rakenne {#dialog-structure}

`Dialog` on organisoitu kolmeen osioon: ylätunnisteeseen, sisältöalueeseen ja alatunnisteeseen. Komponentteja voidaan lisätä kuhunkin osioon käyttäen `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Käyttötarkoitukset {#usages}

1. **Käyttäjäpalaute ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen kysymiseen. Ne voivat näyttää erilaisia tärkeitä palautteita käyttäjälle, kuten:

  >- Onnistumisviestit
  >- Virhevaroitukset
  >- Vahvistuslähetykset

2. **Lomakepalaute ja muokkaaminen**: Voit käyttää dialogeja kerätäksesi käyttäjän syötteitä tai antaa heille mahdollisuuden muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi dialogi voi avautua muokkaamaan käyttäjäprofiilin tietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen tieto**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai ohjeasiakirjoja.

4. **Kuva- ja mediaesikatselut**: Kun käyttäjät tarvitsevat mediakappaleiden tarkastelua, `Dialog`-komponenttia voidaan käyttää näyttämään suurempia esikatseluja tai gallerioita, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Tausta ja sumeus {#backdrop-and-blur}

Avoimella `Dialog`-komponentilla on himmennetty tausta, joka hienovaraisesti kiinnittää huomiota sen sisältöön. Käyttämällä `setBackdrop()` ja `setBlurred()`, voit muuttaa, miten webforJ näyttää (tai peittää) sisällön `Dialog`-komponentin takana. Näiden ominaisuuksien muokkaaminen voi auttaa käyttäjiä tarjoamalla syvyyttä ja visuaalista hierarkiaa.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## `Dialog`-komponentin avaaminen ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia dialogin näyttämiseksi. Sitten `Dialog`-komponentti voidaan sulkea yhdestä näistä toimista:
- Käyttämällä `close()`-metodia
- Paina <kbd>ESC</kbd>-näppäintä
- Klikkaamalla dialogin ulkopuolelle

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialog`-komponentin käyttämällä `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd>-näppäimen painamisen että klikkauksen dialogin ulkopuolelle sulkemaan komponentin.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automaattinen tarkennus {#auto-focus}

Kun automaattinen tarkennus on käytössä, se antaa automaattisesti tarkennuksen ensimmäiselle dialogin sisällä olevalle elementille, johon voi keskittyä. Tämä auttaa ohjaamaan käyttäjien huomiota ja on mukautettavissa `setAutoFocus()`-metodin avulla.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentilla on sisäänrakennettu toiminto, joka tekee siitä vedettävän, jolloin käyttäjä voi siirtää `Dialog`-ikkunaa napsauttamalla ja vetämällä. `Dialog`-komponentin sijaintia voidaan manipuloida mistä tahansa sen kentistä: ylätunnisteesta, sisällöstä tai alatunnisteesta.

### Reunaan tarttuminen {#snap-to-edge}
On myös mahdollista kalibroida tätä käyttäytymistä tarttumaan näytön reunaan, mikä tarkoittaa, että `Dialog`-komponentti asettuu automaattisesti näytön reunalle, kun se vapautetaan vedosta ja pudotuksesta. Tarttuminen voidaan muuttaa `setSnapToEdge()`-metodin avulla. `setSnapThreshold()` ottaa vastaan pixel-määrän, joka määrittää, kuinka kaukana `Dialog`-komponentin on oltava näytön sivuista ennen kuin se tarttuu automaattisesti reunoihin.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Sijoittelu {#positioning}

Dialogin sijaintia voidaan manipuloida käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()` -metodeja. Nämä metodit ottavat merkkijonoargumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Luettelo näistä mittauksista [löytyy täältä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Pystysuora kohdistus {#vertical-alignment}

Manuaalisen asignoinnin lisäksi dialogin X- ja Y-sijainnista on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa kohdistamaan `Dialog`-komponentti. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Koko näyttö ja murtopisteet {#full-screen-and-breakpoints}

`Dialog`-komponentti voidaan asettaa koko näyttö -tilaan. Kun koko näyttö on käytössä, `Dialog`-komponenttia ei voi siirtää tai sijoittaa. Tätä tilaa voidaan manipuloida `Dialog`-komponentin murtopisteominaisuudella. Murtopiste on media-kysely, joka määrää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely täsmää, `Dialog` muuttuu koko näyttö -tilaan - muuten se on sijoitettu.

### Automaattinen leveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletusarvoisesti `Dialog` venyy täyttämään käytettävissä olevan vaakasuoran tilan. Kun automaattinen leveys on käytössä `setAutoWidth(true)` -metodin kautta, `Dialog` kooltaan itsensä sisältönsä leveyden mukaan.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat varustettuna <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillisellä teemalla </JavadocLink>, jotka ovat valmiina nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat esimäärättyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua koko sovelluksessa.

Vaikka monia käyttötarkoituksia on olemassa eri teemoille, esimerkkejä ovat:

  - **Vaara**: Toimintojen, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen, on hyvä käyttötarkoitus Vaara-teemalle.
  - **Oletus**: Oletusteema on sopiva sovelluksessa, jossa toimenpiteet eivät vaadi erityistä huomiota ja ovat yleisiä, kuten asetuksen kytkeminen päälle tai pois.
  - **Ensisijainen**: Tämä teema on sopiva pääasiallisena "toimintakehotteena" sivulla, kuten rekisteröitymisessä, muutosten tallentamisessa tai siirtymisessä toiselle sivulle.
  - **Onnistuminen**: Onnistumisteemaiset dialogit ovat erinomaisia visuoimaan onnistuneesti toteutettua elementtiä sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin suorittamista. Onnistumisteeman voi ohjelmallisesti soveltaa, kun onnistunut toimenpide on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä käyttäjille, jotta he tietävät, että he ovat toteuttamassa mahdollisesti riskialtista toimintoa, esimerkiksi navigoidessaan pois sivulta, jolla on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän merkittäviä kuin ne, joita käytetään Vaara-teeman kanssa.
  - **Harmaa**: Hyviä hienovaraisille toiminnoille, kuten pienille asetuksille tai toimille, jotka tukevat sivua, eivätkä ole osa päätarkoitusta.
  - **Tietoa**: Tietoteema on hyvä valinta antamaan selvittävää, lisätietoa käyttäjälle.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
