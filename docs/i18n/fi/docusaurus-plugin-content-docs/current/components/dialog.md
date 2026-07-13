---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 3dcdd5a9a66f2b00229064500da2bb79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän kiinnittäen huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedotettuviin viesteihin.

<!-- INTRO_END -->

## `Dialog`-rakenne {#dialog-structure}

`Dialog` on organisoitu kolmeen osaan: otsikkoon, sisältöalueeseen ja alatukeen. Komponentteja voidaan lisätä kuhunkin osaan käyttäen `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Käyttötilanteet {#usages}

1. **Käyttäjäpalautteet ja vahvistukset**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjävahvistuksen pyytämiseen. Ne voivat näyttää käyttäjälle erilaisia tärkeitä palautetietoja, kuten:

  >- Onnistumisviestit
  >- Virheilmoitukset
  >- Vahvistuslähetykset

2. **Lomake syöte ja muokkaus**: Voit käyttää dialogeja kerätäksesi käyttäjän syötteitä tai antaaksesi heidän muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi, dialogi voi ponnahtaa muokkaamaan käyttäjäprofiilin tietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen tieto**: Lisätiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai käyttöohjeita.

4. **Kuva- ja median esikatselut**: Kun käyttäjät tarvitsevat mediaesitysten tarkastelemista, `Dialog`-komponenttia voidaan käyttää suurempien esikatselujen tai gallerioiden näyttämiseen, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Tausta ja sumentaminen {#backdrop-and-blur}

Olemalla käyttövoimainen webforJ `Dialog` -komponentin taustavarustuksen avulla tausta näytetään `Dialog`-komponentin taakse. Lisäksi, kun sumentaminen on käytössä, `Dialog`-komponentin sumentamisominaisuus sumentaa taustan. Näiden asetusten muuttaminen voi auttaa käyttäjiä tarjoamalla syvyyksiä, visuaalista hierarkiaa ja kontekstia, mikä johtaa selkeämpään oppaaseen käyttäjälle.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## `Dialog`-komponentin avaus ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia näyttämään dialogi. Tämän jälkeen `Dialog`-komponentti voidaan sulkea jollain seuraavista tavoista:
- Käyttämällä `close()`-metodia
- Painamalla <kbd>ESC</kbd> näppäintä
- Napsauttamalla dialogin ulkopuolelle

Kehittäjät voivat valita mitkä vuorovaikutukset sulkevat `Dialog`-komponentin `setCancelOnEscKey()` ja `setCancelOnOutsideClick()` -metodien avulla. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd> näppäimen painamisen että napsauttamisen ulkopuolen sulkemaan komponentin.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automaattinen tarkennus {#auto-focus}

Kun se on käytössä, automaattinen tarkennus antaa automaattisesti tarkennuksen ensimmäiselle dialogin elementille, johon voidaan keskittyä. Tämä on hyödyllistä käyttäjien huomion ohjaamisessa, ja sitä voidaan mukauttaa `setAutoFocus()` -metodin avulla.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Raahattava {#draggable}

`Dialog`-komponentilla on sisäänrakennettu toiminto olla raahattavissa, jolloin käyttäjä voi siirtää `Dialog`-ikkunan napsauttamalla ja raahamalla. `Dialog`-komponentin sijaintia voidaan muuttaa minkä tahansa sen kentän, kuten otsikon, sisällön tai alatuken avulla.

### Kohdistaminen reunaan {#snap-to-edge}
On myös mahdollista kalibroida tätä käyttäytymistä osumaan näytön reunaan, mikä tarkoittaa, että `Dialog`-komponentti kohdistaa itsensä automaattisesti näytön reunaan, kun se vapautetaan sen raahaa ja pudota -tilasta. Kohdistamista voidaan muuttaa `setSnapToEdge()` -metodilla. `setSnapThreshold()` ottaa käyttöön tietyt pikseliarvot, mikä määrittää, kuinka kaukana `Dialog`-komponentin tulee olla näytön sivuista, ennen kuin se osuu automaattisesti reunoihin.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Sijoittelu {#positioning}

Dialogin sijaintia voidaan muuttaa sisäänrakennettujen `setPosx()` ja `setPosy()` -metodien avulla. Nämä metodit ottavat merkkijonon argumentteja, jotka voivat edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkökorkeutta/leveyttä. Näiden mittausten luettelon [löydät tästä linkistä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Vaaka kohdistaminen {#vertical-alignment}

Manuaalisen asignoinnin lisäksi dialogin X- ja Y-koordinaateille, on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa dialogin kohdistamiseen. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Kokoruutu ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog`-komponentti voidaan asettaa käyttämään kokoruudun tilaa. Kun kokoruutu-tila on käytössä, `Dialog`-komponenttia ei voida siirtää tai sijoittaa. Tätä tilaa voidaan muuttaa `Dialog`-komponentin katkaisupisteattribuutilla. Katkaisupiste on mediakysely, jolloin komponentti siirtyy automaattisesti kokoruudun tilaan. Kun kysely vastaa, `Dialog`-komponentti muuttuu kokoruuduksi - muuten se on sijoitettu.

### Autoleveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletuksena `Dialog` venyy täyttämään saatavilla olevan vaakasuoran tilan. Kun autoleveys on käytössä `setAutoWidth(true)` -metodin kautta, `Dialog`-komponentti määrittää itsensä sisältönsä leveyden mukaan.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Tyylit {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erilaista teemaa </JavadocLink>, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määritettyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkoasua koko sovelluksessa.

Vaikka jokaisella eri teemalla on monia käyttötapoja, joitakin esimerkkejä ovat:

  - **Vaara**: Toimet, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen, ovat hyviä käyttötapauksia Vaara-teemalla varustetuille dialogeille.
  - **Oletus**: Oletusteema on sopiva kaikille sovelluksessa, kun ei tarvita erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Pääsy**: Tämä teema on sopiva pääasialliseksi "toiminta" sivulla, kuten ilmoittautuminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Onnistuneet teemalla varustetut dialogit ovat erinomaisia visualisoimaan jonkin sovelluksen elementin onnistunutta loppua, kuten lomakkeen lähettämistä tai ilmoittautumisprosessin viimeistelyä. Onnistuneen teeman voi ohjelmallisesti soveltaa, kun onnistunut toiminta on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittaakseen käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jolla on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikuttavia kuin sellaiset, jotka käyttäisivät Vaara-teemaa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän lisäominaisuuksia sivulle eikä osa päätoiminnallisuutta.
  - **Info**: Info-teema on hyvä valinta tarjotakseen selkeyttävää, lisätietoa käyttäjälle painettaessa.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
