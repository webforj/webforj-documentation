---
title: Dialog
sidebar_position: 30
_i18n_hash: d0087974ac244db9b082133be7966a3e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

webforJ-dialog-komponentti on rakennettu mahdollistamaan kehittäjän nopeasti ja helposti näyttää dialogi sovelluksessaan, esimerkiksi kirjautumisvalikossa tai tietoruudussa.

Komponentti koostuu kolmesta osasta, joista jokainen on `Panel`-komponentti: **otsikko**, **sisältö** ja **alatunniste**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Käytöt {#usages}

1. **Käyttäjäpalautteet ja vahvistukset**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen kysymiseen. Ne voivat näyttää käyttäjälle erilaisia tärkeitä palautteita, kuten:

  >- Onnistumisviestit
  >- Virheilmoitukset
  >- Vahvistuslähetykset

2. **Lomakeinputti ja muokkaaminen**: Voit käyttää dialogeja kerätäksesi käyttäjätietoja tai sallimaan heidän muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi dialogi voi avautua muokkaamaan käyttäjäprofiilin tietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen tieto**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisempia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai käyttöohjeita.

4. **Kuvan ja median esikatselut**: Kun käyttäjät tarvitsevat median katsomista, `Dialog`-komponenttia voidaan käyttää suurempien esikatselujen tai gallerioiden näyttämiseen, esimerkiksi vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muun median

## Taustakuva ja sumea {#backdrop-and-blur}

Otamalla käyttöön webforJ `Dialog`-komponentin taustakuva-attribuutin, taustakuva näkyy `Dialogin` takana. Lisäksi, kun se on käytössä, dialogin sumea attribuutti sumentaa dialogin taustakuvaa. Näiden asetusten muokkaaminen voi auttaa käyttäjiä tarjoamalla syvyyksiä, visuaalista hierarkiaa ja kontekstia, mikä johtaa käyttäjälle selkeämpään ohjaukseen.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## `Dialogin` avaaminen ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia dialogin näyttämiseen. Tämän jälkeen `Dialog`-komponentti voidaan sulkea yhdellä näistä toimista:
- Käyttämällä `close()`-metodia
- Painamalla <kbd>ESC</kbd> näppäintä
- Klikkaamalla dialogin ulkopuolella

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialogin` käyttämällä `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd> näppäimen painamisen että dialogin ulkopuolelle klikkaamisen sulkemaan komponentin.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Automaattinen fokusoiminen {#auto-focus}

Kun se on käytössä, automaattinen fokus antaa automaattisesti fokus ensimmäiselle elementille dialogissa, joka voi saada fokuksen. Tämä on hyödyllistä käyttäjien huomion ohjaamisessa, ja se on muokattavissa `setAutoFocus()`-metodin kautta.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentissa on sisäänrakennettu toiminnallisuus olevan vedettävä, mikä mahdollistaa käyttäjän siirtää `Dialog`-ikkunaa klikkaamalla ja vetämällä. `Dialog`-ikkunan sijaintia voidaan muokata mistä tahansa sen kentästä: otsikosta, sisällöstä tai alatunnisteesta.

### Reunalle napsauttaminen {#snap-to-edge}
On myös mahdollista kalibroida tämä käyttäytyminen siten, että se napsauttaa näytön reunalle, jolloin `Dialog` asettuu automaattisesti näytön reunaan, kun se vapautetaan vetämisestä. Napsauttaminen voidaan muuttaa `setSnapToEdge()`-metodin kautta. `setSnapThreshold()` ottaa kuvapisteiden määrän, joka asettaa, kuinka lähellä `Dialog`-ikkunan tulee olla näytön reunoista ennen kuin se napsauttaa automaattisesti reunoille.  

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Sijoittaminen {#positioning}

Dialogin sijaintia voidaan muokata käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()` -metodeja. Nämä metodit ottavat merkkijonon argumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymäkorkeutta/leveyttä. Luettelo näistä mittauksista [löytyy tältä linkiltä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### VERTICAL ALINEERAUS {#vertical-alignment}

Manuaalisen asignoinnin lisäksi dialogin X- ja Y-sijainnille, on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa `Dialogin` kohdistamiseen. On kolme mahdollista arvoa: `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa. 

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Koko näyttö ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asetaa siirtymään koko näyttö -tilaan. Kun koko näyttö on otettu käyttöön, `Dialog` ei voi olla siirrettävissä tai asetettavissa. Tätä tilaa voidaan manipuloida `Dialog`-komponentin katkaisupisteattribuutilla. Katkaisupiste on media-kysely, joka määrittää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely täsmää, `Dialog` muuttuu koko näyttöön - muuten se sijoitetaan.

## Tyylit {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillisen teeman</JavadocLink> kanssa, jotka on rakennettu nopeaksi tyylittelyksi ilman CSS:n käyttöä. Nämä teemaat ovat esimäärättyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ulkonäköä koko sovelluksessa. 

Vaikka jokaiselle erilaiselle teeman käytölle on monia käyttötarkoituksia, esimerkkeinä ovat:

  - **Vaara**: Teemaa "Vaara" voidaan käyttää toimille, joilla on vakavia seurauksia, kuten täytetyn tiedon tyhjentäminen tai tilin/tietojen pysyvä poistaminen.
  - **Oletus**: Oletusteema sopii käyttötoimille, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen kytkeminen päälle tai pois päältä.
  - **Pääteema**: Tätä teemaa käytetään päätoimena toimena sivulla, kuten rekisteröitymisessä, muutosten tallentamisessa tai siirtymisessä toiselle sivulle.
  - **Onnistuminen**: Onnistuvaiheisesti tyylitellyt dialogit ovat erinomaisia visualisoimaan jonkin sovelluksen osan onnistunutta loppua, kuten lomakkeen lähettämistä tai rekisteröintiprosessin päättämistä. Onnistuminen-teeman voi ohjelmallisesti soveltaa, kun onnistunut toiminta on loppu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittamaan käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikuttavia kuin ne, jotka käyttävät Vaara-teemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten pienille asetuksille tai toimille, jotka ovat enemmän tukevia sivulle eikä osa päätoiminnallisuudesta.
  - **Tieto**: Tietoteema on hyvä valinta, kun halutaan tarjota selventävää, lisätietoa käyttäjälle.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
