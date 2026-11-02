---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 8f78bdc280006c73955232e7f52ed365
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän ja kiinnittää huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai informatiivisiin viesteihin.

## `Dialog`-rakenne {#dialog-structure}

`Dialog` on jaettu kolmeen osioon: otsikkoon, sisältöalueeseen ja alatunnisteeseen. Komponentteja voidaan lisätä kuhunkin osioon käyttämällä `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Käyttötarkoitukset {#usages}

1. **Käyttäjäpalautteet ja vahvistukset**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen pyytämiseen. Ne voivat näyttää erilaisia tärkeitä palautetietoja käyttäjälle, kuten:

  >- Onnistumisviestit
  >- Virhevaroitukset
  >- Vahvistuslähetykset

2. **Lomakekirjaukset ja muokkaaminen**: Voit käyttää dialogeja käyttäjäsyötteen keräämiseen tai antaa heidän muokata tietoja hallitulla ja keskittyneellä tavalla. Esimerkiksi dialogi voi avautua muokkaamaan käyttäjäprofiilin tietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen tieto**: Lisäkontextuaalisten tietojen tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisia ominaisuuksia tai tietoja. Dialogit voivat tarjota syventäviä selityksiä, kaavioita tai ohjedokumentaatiota.

4. **Kuva- ja mediasuojat**: Kun käyttäjät tarvitsevat median katsomista, `Dialog` voi näyttää suurempia esikatseluja tai gallerioita, esimerkiksi vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Taustakuva ja sumennus {#backdrop-and-blur}

Avoin `Dialog`-komponentti sisältää himmennetyn taustakuva, joka hienovaraisesti kiinnittää huomiota sen sisältöön. Käyttämällä `setBackdrop()` ja `setBlurred()`, voit muuttaa, miten webforJ näyttää (tai peittää) sisällön `Dialog`in takana. Näiden attribuuttien muokkaaminen voi auttaa käyttäjiä tarjoamalla syvyyttä ja visuaalista hierarkiaa.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## `Dialog`in avaus ja sulkeminen {#opening-and-closing-the-dialog}

Kun olet luonut uuden `Dialog`-olion, käytä `open()`-metodia näyttääksesi dialogin. Sulkemiseen ohjelmallisesti käytä `close()`-metodia. Voit myös hallita, mitkä käyttäjäinteraktiot sulkevat dialogin:

- **Klikkaaminen ulkopuolelle** sulkee `Dialog`in oletusarvoisesti, koska `cancelOnOutsideClick` on oletusarvoisesti `true`. Käytä `setCancelOnOutsideClick(false)`, jotta tämä toiminto poistetaan käytöstä.
- **Paina <kbd>ESC</kbd>** ei sulje `Dialog`ia oletusarvoisesti, koska `cancelOnEscKey` on oletusarvoisesti `false`. Käytä `setCancelOnEscKey(true)`, jotta tämä toiminto otetaan käyttöön.

Käytä `setCloseable(true)`, jotta molemmat interaktiot ovat käytettävissä, tai `setCloseable(false)`, jotta molemmat poistetaan käytöstä. Seuraava demo mahdollistaa molemmat interaktiot.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Automaattinen tarkennus {#auto-focus}

Kun automaattinen tarkennus on käytössä, se antaa automaattisesti tarkennuksen ensimmäiselle keskitettävälle elementille dialogissa. Tämä auttaa käyttäjien huomion suuntaamisessa ja on mukautettavissa `setAutoFocus()`-metodin kautta.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Voidaan vetää {#draggable}

`Dialog`-komponentissa on sisäänrakennettu toiminnallisuus, joka mahdollistaa sen vetämisen, jolloin käyttäjä voi siirtää `Dialog`-ikkunaa napsauttamalla ja vetämällä. `Dialog`-ikkunan sijaintia voidaan muuttaa minkä tahansa sen kentän kautta: otsikosta, sisällöstä tai alatunnisteesta.

### Reunaan napsauttaminen {#snap-to-edge}
On myös mahdollista kalibroida tämä toiminto niin, että se napsahtaa näytön reunaan, mikä tarkoittaa, että `Dialog` asettuu automaattisesti näkyvyyden reunalle, kun se vapautetaan vetämisestä ja pudottamisesta. Napsautus voidaan muuttaa `setSnapToEdge()`-metodilla. `setSnapThreshold()` ottaa vastaan pikseliluvun, joka määrittää, kuinka kauas `Dialog`in täytyy olla näytön reunoista, ennen kuin se yksinkertaisesti napsahtaa reunalle.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Sijoittaminen {#positioning}

Dialogin sijaintia voidaan muuttaa käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()`-metodeja. Nämä menetelmät saavat merkkijonoargumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Luettelon näistä mitoista [löydät tältä linkiltä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Pystysuora kohdistus {#vertical-alignment}

Lisäksi dialogin X- ja Y-asennon manuaalisen määrittämisen lisäksi voidaan käyttää dialogin sisäänrakennettua enum-luokkaa kohdistamaan `Dialog`. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Koko näyttö ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa koko näyttö -tilaan. Kun koko näyttö on käytössä, `Dialog` ei voi siirtyä tai sijoittua. Tämä tila voidaan vaihtaa dialogin katkaisupisteattribuutilla. Katkaisupiste on mediaquery, joka määrää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely täsmää, `Dialog` muuttuu koko näyttö -tilaan - muuten se sijoitetaan.

### Automaattinen leveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletusarvoisesti `Dialog` venyy täyttämään saatavilla olevan vaakasuoran tilan. Kun automaattinen leveys on käytössä `setAutoWidth(true)`, `Dialog` määrittää koon sen sisältöleveyden perusteella.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat mukana <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillistä teemaa </JavadocLink>, jotka mahdollistavat nopean tyylittelyn ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkonäköä koko sovelluksessa.

Vaikka jokaiselle eri teeman käytölle on monia käyttötarkoituksia, tässä on joitain esimerkkejä:

  - **Vaara**: Toiminnot, joilla on vakavia seurauksia, kuten täytetyn tiedon poistaminen tai tilin/tietojen pysyvä poistaminen, edustavat hyvää käyttötarkoitusta vaara-teemalla varustetuille dialogeille.
  - **Oletus**: Oletusteema on sopiva toimintoihin koko sovelluksessa, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetusten vaihtaminen.
  - **Pääteema**: Tämä teema on sopiva pää "toimintakutsuna" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Onnistumisteemalla varustetut dialogit ovat erinomaisia visualisoimaan elementin onnistunutta valmistumista sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin loppuunsaattamista. Onnistumisteema voidaan ohjelmallisesti soveltaa, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittaakseen käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymässä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, jotka käyttäisivät vaarateemaa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän sivutoimisia sivulle, eivätkä osa päätoimintoja.
  - **Tieto**: Tietoteema on hyvä valinta tarjoamaan selventävää, lisätietoa käyttäjälle, kun painetaan.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
