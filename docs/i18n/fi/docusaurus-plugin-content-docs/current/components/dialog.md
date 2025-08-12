---
title: Dialog
sidebar_position: 30
_i18n_hash: e0d440fddf7ad6be7a78958ae1ddde1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

WebforJ-dialogikomponentti on rakennettu mahdollistamaan kehittäjälle nopea ja helppo tapa näyttää dialogi sovelluksessaan, esimerkkeinä kirjautumisvalikko tai tietoruutu.

Komponentti koostuu kolmesta osasta, joista jokainen on `Panel`-komponentti: **otsikko**, **sisältö** ja **alatunniste**.

<ComponentDemo 
path='/webforj/dialogsections?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java'
height = '225px'
/>

## Käyttöesimerkit {#usages}

1. **Käyttäjäpalautteet ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen pyytämiseen. Ne voivat näyttää käyttäjälle erilaisia tärkeitä palautteita, kuten:

  >- Onnistumisviestit 
  >- Virheilmoitukset
  >- Vahvistuslähetykset

2. **Lomake Syöttö ja Muokkaus**: Voit käyttää dialogeja kerätäksesi käyttäjän syötettä tai sallia heidän muokata tietoja hallitussa ja keskittyneessä ympäristössä. Esimerkiksi dialogi voi pompahtaa muokkaamaan käyttäjäprofiilin tietoja tai viimeistelemään monivaiheista lomaketta.

3. **Kontekstuaalinen Tieto**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai ohjeasiakirjoja.

4. **Kuvan ja Mediankatselu**: Kun käyttäjät tarvitsevat mediasisältöjen katsomista, `Dialog`-komponenttia voidaan käyttää näyttämään suurempia esikatseluja tai gallerioita, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Taustakuva ja sumennus {#backdrop-and-blur}

Oma taustakuva-ominaisuus webforJ `Dialog` -komponentissa mahdollistaa taustakuvan näyttämisen dialogin taustalla. Lisäksi, kun se on käytössä, dialogin sumennusominaisuus sumentaa dialogin taustaa. Näiden asetusten muokkaaminen voi auttaa käyttäjiä tarjoamalla syvyyksiä, visuaalista hierarkiaa ja kontekstia, mikä johtaa selvempään ohjaukseen käyttäjälle.

<ComponentDemo 
path='/webforj/dialogbackdropblur?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java'
height = '300px'
/>

## Dialogin avaus ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia dialogin näyttämiseen. Tämän jälkeen `Dialog`-komponentti voidaan sulkea seuraavilla tavoilla:
- Käyttämällä `close()`-metodia
- Painamalla <kbd>ESC</kbd>-näppäintä
- Napsauttamalla dialogin ulkopuolelle

Kehittäjät voivat valita, mitkä toiminnot sulkevat `Dialog`-komponentin käyttäen `setCancelOnEscKey()` ja `setCancelOnOutsideClick()` -metodeja. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd>-näppäimen painamisen että napsauttamisen dialogin ulkopuolella komponentin sulkemiseksi.

<ComponentDemo 
path='/webforj/dialogclose?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java'
height = '350px'
/>

## Automaattinen fokus {#auto-focus}

Kun automaattinen fokus on käytössä, se antaa automaattisesti fokuksen dialogin ensimmäiselle fokusoitavalle elementille. Tämä auttaa ohjaamaan käyttäjien huomion, ja sen voi mukauttaa `setAutoFocus()`-metodin avulla.

<ComponentDemo 
path='/webforj/dialogautofocus?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java'
height = '350px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentti on rakennettu vedettäväksi, mikä mahdollistaa käyttäjän siirtää `Dialog`-ikkunaa napsauttamalla ja vetämällä. Dialogin sijaintia voidaan muuttaa mistä tahansa sen kentistä: otsikosta, sisällöstä tai alatunnisteesta.

### Reunaan tarttuminen {#snap-to-edge}
On myös mahdollista säätää tätä käyttäytymistä siten, että se tarttuu näytön reunoihin, mikä tarkoittaa, että `Dialog` asettuu automaattisesti näytön reunaan, kun se vapautetaan vetämisestä. Tarttumista voidaan muuttaa `setSnapToEdge()`-metodin avulla. `setSnapThreshold()`-metodi ottaa kuvapisteitä, mikä määrittää, kuinka kaukana dialogin pitäisi olla näytön reunoista, ennen kuin se tarttuu automaattisesti reunoihin.

<ComponentDemo 
path='/webforj/dialogdraggable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java'
height = '350px'
/>

## Asettelu {#positioning}

Dialogin sijaintia voidaan muuttaa käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()` -metodeja. Nämä menetelmät ottavat merkkijonon argumenttina, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Luettelo näistä mittasuhteista [löytyy tältä linkiltä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo 
path='/webforj/dialogpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java'
height = '350px'
/>

### Pystysuora kohdistus {#vertical-alignment}

Manuaalisen dialogin X- ja Y-asetuksen lisäksi dialogin sisäänrakennettua enum-luokkaa voidaan käyttää `Dialog`-komponentin kohdistamiseen. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodin kanssa.

<ComponentDemo 
path='/webforj/dialogalignments?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java'
height = '550px'
/>

### Koko näyttö ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa koko näyttötilaan. Kun koko näyttö on käytössä, `Dialog`-komponenttia ei voi siirtää tai asettaa. Tätä tilaa voidaan säätää `Dialog`-komponentin katkaisupisteominaisuudella. Katkaisupiste on media-kysely, joka määrittelee, milloin `Dialog` kääntyy automaattisesti koko näyttöön. Kun kysely vastaa, `Dialog` muuttuu koko näyttötilaan - muuten se on sijoitettu.

## Tyylit {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillisen teeman </JavadocLink> kanssa, jonka avulla voidaan nopeasti muotoilla ilman CSS:n käyttöä. Nämä teemat ovat ennakkoon määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ilmettä koko sovelluksessa.

Vaikka jokaiselle erilaista teemoille on monia käyttötapauksia, joitakin esimerkkejä käyttötapauksista ovat:

  - **Vaarallinen**: Toimet, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen, edustavat hyvää käyttötapausta vaarallisten teemojen dialogeille.
  - **Oletus**: Oletusteema on sopiva sovelluksen toiminnoille, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Pääteema**: Tämä teema on sopiva "kutsu-toimintaan" sivulla, kuten ilmoittautuminen, muutosten tallentaminen tai siirtyminen toiselle sivulle.
  - **Onnistuminen**: Onnistumisteemalla varustetut dialogit ovat erinomaisia visualisoimaan onnistuneita toimintoja sovelluksessa, kuten lomakkeen lähettämistä tai kirjautumisprosessin loppuun saattamista. Onnistumisteemaa voidaan ohjelmallisesti soveltaa heti, kun onnistunut toiminto on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittamaan käyttäjille, että he ovat suorittamassa mahdollisesti riskialtista toimintoa, kuten siirtymissä sivulle, jossa on tallentamattomia muutoksia. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, joille käytetään vaarallista teemaa.
  - **Harmaa**: Hyvä hienovaraisille toimille, kuten pienille asetuksille tai toimille, jotka ovat lisättäviä sivulle, eivätkä osa päätoimintoja.
  - **Tieto**: Tietoteema on hyvä valinta tarjota selventävää, lisätietoa käyttäjälle tarpeen mukaan.

<ComponentDemo 
path='/webforj/dialogthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java'
height = '500px'
/>

<TableBuilder name="Dialog" />
