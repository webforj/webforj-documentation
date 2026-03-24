---
title: Dialog
sidebar_position: 30
_i18n_hash: 4896ea2a90b7c5155fe9ef291e69b2ad
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän, kiinnittäen huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedotusviesteihin.

<!-- INTRO_END -->

## `Dialog` rakenne {#dialog-structure}

`Dialog` on järjestetty kolmeen osaan: otsikkoon, sisältöalueeseen ja alatunnisteeseen. Komponentteja voidaan lisätä jokaiselle osalle käyttämällä `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Käyttötarkoitukset {#usages}

1. **Käyttäjäpalaute ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen kysymiseen. Ne voivat näyttää erilaisia tärkeitä palautteita käyttäjälle, kuten:

  >- Onnistumisviestit 
  >- Virhevaroitukset
  >- Vahvistuslähetykset

2. **Lomake syöte ja muokkaus**: Voit käyttää dialogeja kerätäksesi käyttäjäsyötteitä tai salliaksesi heidän muokata tietoja hallitulla ja keskittyneellä tavalla. Esimerkiksi, dialogi voi ilmestyä muokataksesi käyttäjäprofiilin tietoja tai viimeistelläksesi monivaiheista lomaketta.

3. **Kontekstuaalinen tieto**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisempia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai apudokumentaatiota.

4. **Kuvan ja median esikatselut**: Kun käyttäjät tarvitsevat medioiden tarkastelua, `Dialog` voi toimia suurempien esikatselujen tai gallerioiden näyttämiseen, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Taustakuva ja sumeus {#backdrop-and-blur}

Ottamalla käyttöön webforJ `Dialog`-komponentin taustakuvan attribuutin, taustakuva näytetään `Dialog`in taustalla. Lisäksi, kun se on käytössä, Dialogin sumea attribuutti sumentaa `Dialog`in taustakuvaa. Näiden asetusten muuttaminen voi auttaa käyttäjiä tarjoamalla syvyyttä, visuaalista hierarkiaa ja kontekstia, mikä johtaa selkeämmän ohjeistuksen antamiseen käyttäjälle.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## `Dialog`in avaaminen ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia näyttämään dialogi. Sitten `Dialog`-komponentti voidaan sulkea jollakin seuraavista toiminnoista:
- Käyttämällä `close()`-metodia
- Paina <kbd>ESC</kbd> -näppäintä
- Klikkaamalla dialogin ulkopuolella

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialog`in käyttäen `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi, `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd> -näppäimen painamisen että klikkauksen dialogin ulkopuolella sulkemaan komponentin.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Automaattinen tarkennus {#auto-focus}

Kun se on käytössä, automaattinen tarkennus antaa automaattisesti tarkennuksen ensimmäiselle elementille dialogissa, joka voi saada tarkennuksen. Tämä on hyödyllistä käyttäjien huomion ohjaamisessa ja se on mukautettavissa `setAutoFocus()`-metodin avulla.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentilla on sisäänrakennettu toiminnallisuus olla vedettävä, mikä sallii käyttäjän siirtää `Dialog`-ikkunaa klikkaamalla ja vetämällä. `Dialog`in sijaintia voidaan muokata mistä tahansa kentästä sen sisällä: otsikosta, sisällöstä tai alatunnisteesta.

### Reunaan napsauttaminen {#snap-to-edge}
On myös mahdollista kalibroida tämä käyttäytyminen napsauttamaan näytön reunaan, mikä tarkoittaa, että `Dialog` asettuu automaattisesti näyttölaitteen reunalle, kun se vapautetaan vedolta. Napsautus voidaan muuttaa `setSnapToEdge()`-metodin avulla. `setSnapThreshold()` ottaa määrän pikseleitä, mikä määrittää, kuinka kaukana `Dialog`in tulisi olla näytön reunoista ennen kuin se automaattisesti napsauttaa reunoihin.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Sijoittaminen {#positioning}

Dialogin sijaintia voidaan muokata sisäänrakennetuilla `setPosx()` ja `setPosy()` -metodeilla. Nämä metodit ottavat merkkijonon argumenttina, joka voi edustaa mitä tahansa soveltuvaa CSS-mittayksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Lista näistä mittauksista [löytyy tästä linkistä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Pystysuora kohdistus {#vertical-alignment}

Manuaalisen määrityksen lisäksi dialogin X- ja Y-sijainnille, on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa kohdistamaan `Dialog`. On kolme mahdollista arvoa, `TOP`, `CENTER` ja `BOTTOM`, joista kutakin voidaan käyttää `setAlignment()`-metodin kanssa. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Koko näyttö ja murtopisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa siirtymään koko näyttö -tilaan. Kun koko näyttö on käytössä, `Dialog`ia ei voi siirtää tai sijoittaa. Tätä tilaa voidaan muuttaa `Dialog`in murtopisteattribuutilla. Murtopiste on media kysely, joka määrittää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely täsmää, `Dialog` muuttuu koko näyttöön - muuten se on sijoitettu.

## Tyylittely {#styling}

### Teemat {#themes}

`Dialog`-komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erilaista teemaa </JavadocLink>, jotka mahdollistavat nopean tyylittelyn ilman CSS:ää. Nämä teemat ovat ennalta määriteltäviä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan muokata painikkeiden ulkoasua koko sovelluksessa.

Vaikka jokaiselle eri teeman käyttötarkoituksille on monia mahdollisuuksia, joitain esimerkkejä käytöstä ovat:

  - **Vaara**: Toimet, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen, edustavat hyvää käyttötilannetta Vaara-teemalla varustetuille dialogeille.
  - **Oletus**: Oletusteema on sopiva toimiin koko sovelluksessa, jotka eivät vaadi erityistä huomiota ja ovat geneerisiä, kuten asetuksen vaihtaminen.
  - **Pääasiallinen**: Tämä teema on sopiva pää "toimintakehotteeksi" sivulla, kuten rekisteröityminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Onnistumistason dialogit ovat erinomaisia visualisoimaan onnistumisen päätöstä sovelluksessa, kuten lomakkeen lähettämiseen tai rekisteröitymisprosessin täydentämiseen. Onnistumistason voidaan ohjelmallisesti soveltaa, kun onnistunut toimi on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittamaan käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtyessään sivulta, jolla on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikutusvaltaisia kuin ne, jotka käyttävät Vaara-teemaa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten pienille asetuksille tai toiminnoille, jotka ovat enemmän lisäyksiä sivulle eikä päätoiminnallisuudelle.
  - **Tieto**: Tietoteema on hyvä valinta tarjota selventävää, lisätietoa käyttäjälle tarvittaessa.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
