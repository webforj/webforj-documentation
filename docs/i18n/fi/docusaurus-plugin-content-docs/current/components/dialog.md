---
title: Dialog
sidebar_position: 30
sidebar_class_name: new-content
_i18n_hash: 750f3d1f7c1c905274eac22a90b270de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän ja kiinnittää huomiota keskittyvään sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedollisiin viesteihin. 

<!-- INTRO_END -->

## `Dialog` rakenne {#dialog-structure}

`Dialog` on jaettu kolmeen osioon: otsikkoon, sisältöalueeseen ja alatunnisteeseen. Komponentteja voidaan lisätä kuhunkin osioon käyttämällä `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='225px'
/>

## Käyttötapaukset {#usages}

1. **Käyttäjäpalautteet ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen kysymiseen. Ne voivat näyttää erilaisia tärkeitä viestejä käyttäjälle, kuten:

  >- Onnistumisviestit 
  >- Virhevaroitukset
  >- Vahvistuslähetykset

2. **Lomakeinput ja muokkaaminen**: Voit käyttää dialogeja kerätäksesi käyttäjäinputtia tai salliaksesi heidän muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi, dialogi voi nousta muokkaamaan käyttäjäprofiilitietoja tai täydentämään monivaiheista lomaketta.

3. **Kontekstuaalinen informaatio**: Lisäkontekstuaalisen tiedon tai työkalujen näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisempia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai apuasiakirjoja.

4. **Kuvan ja median esikatselut**: Kun käyttäjät tarvitsevat nähdä mediaa, `Dialog` voidaan käyttää isompien esikatselujen tai gallerioiden näyttämiseen, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Tausta ja blurry {#backdrop-and-blur}

Aktivoimalla webforJ `Dialog`-komponentin tausta-attribuutti, tausta tulee näkyviin `Dialog`-ikkunan taakse. Lisäksi, kun se on aktivoitu, dialogin blurry-attribuutti sumentaa dialogin taustan. Nämä asetukset voivat auttaa käyttäjiä tarjoamalla syvyyksiä, visuaalista hierarkiaa ja kontekstia, mikä johtaa selkeämpään ohjaukseen käyttäjälle.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='300px'
/>

## `Dialog`-ikkunan avaaminen ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen, käytä `open()`-metodia näyttääksesi dialogin. Tämän jälkeen `Dialog`-komponentti voidaan sulkea jollakin seuraavista tavoista:
- Käyttämällä `close()`-metodia
- Painamalla <kbd>ESC</kbd> näppäintä
- Napsauttamalla `Dialog`-ikkunan ulkopuolella

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialog`-ikkunan käyttämällä `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd>-näppäimen painamisen että napsauttamisen dialogin ulkopuolella sulkemaan komponentin.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='350px'
/>

## Automaattinen kohdistus {#auto-focus}

Kun automaattinen kohdistus on käytössä, se antaa automaattisesti fokuksen ensimmäiselle elementille dialogissa, johon voidaan keskittyä. Tämä on hyödyllistä käyttäjien huomion ohjaamisessa ja sitä voidaan mukauttaa `setAutoFocus()`-metodin avulla.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='350px'
/>

## Siirrettävä {#draggable}

`Dialog`:lla on sisäänrakennettu toiminnallisuus olla siirrettävä, jolloin käyttäjä voi siirtää `Dialog`-ikkunan napsauttamalla ja vetämällä. `Dialog`-ikkunan sijaintia voidaan muuttaa mistä tahansa sen kentästä: otsikosta, sisällöstä tai alatunnisteesta.

### Reunalle napsautus {#snap-to-edge}
On myös mahdollista kalibroida tämä käytös niin, että se napsauttaa näytön reunalle, mikä tarkoittaa, että `Dialog`-ikkuna linjautuu automaattisesti näytön reunaan, kun se vapautetaan sen raahauksen ja pudotuksen jälkeen. Napsautus voidaan muuttaa `setSnapToEdge()`-metodin avulla. `setSnapThreshold()` ottaa määrän pikseleitä, joka määrittää, kuinka kaukana `Dialog`-ikkunan tulee olla näytön reunoista ennen kuin se automaattisesti napsauttaa reunoihin.  

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='350px'
/>

## Sijoittelu {#positioning}

Dialogin sijaintia voidaan muokata käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()`-metodeja. Nämä metodit ottavat merkkijonoargumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusmittayksikköä, kuten pikseleitä tai näkymäkorkeutta/leveyttä. Luettelo näistä mittayksiköistä [löytyy täältä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='350px'
/>

### Pystysuuntainen kohdistus {#vertical-alignment}

Lisäksi dialogin X- ja Y-sijainnin manuaalisen määrittämisen lisäksi on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa kohdistamaan `Dialog`-ikkuna. Kolme mahdollista arvoa ovat `TOP`, `CENTER` ja `BOTTOM`, joista kukin voidaan käyttää `setAlignment()`-metodin kanssa. 

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='550px'
/>

### Koko näyttö ja katkaisut {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa menemään koko näyttö -tilaan. Kun koko näyttö on aktivoituna, `Dialog`-ikkunaa ei voi siirtää tai sijoittaa. Tätä tilaa voidaan manipuloida `Dialog`-komponentin katkaisupisteattributilla. Katkaisupiste on mediakysely, joka määrittää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely täsmää, `Dialog` muuttuu koko näyttöön - muuten se sijoitetaan normaalisti.

### Autoleveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletusarvoisesti `Dialog` venyy täyttämään saatavilla olevan vaakasuuntaisen tilan. Kun autoleveys on aktivoitu `setAutoWidth(true)`-metodilla, `Dialog` asettaa itsensä sen sisällön leveyden mukaan.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Dialog`-komponentit tulevat varustettuna <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillisellä teemalla </JavadocLink>, jotka mahdollistavat nopean tyylityksen ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa painikkeiden ilmettä sovelluksessa. 

Vaikka jokaiselle eri teeman käyttötapaukselle on monia mahdollisuuksia, joitakin esimerkkejä käytöistä ovat:

  - **Vaarallinen**: Toiminnot, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/datan pysyvä poistaminen, on hyvä käyttötilanne vaarallisessa teemassa.
  - **Oletus**: Oletusteema on sopiva toiminnoille sovelluksessa, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen kytkeminen.
  - **Pääteema**: Tämä teema on sopiva pää "call-to-action" -elementiksi sivulla, kuten ilmoittautumiseksi, muutosten tallentamiseksi tai siirtymiseksi toiselle sivulle.
  - **Onnistuminen**: Onnistuneet teemat dialogit ovat erinomaisia visualisoidessaan onnistunutta elementin valmistumista sovelluksessa, kuten lomakkeen lähettämistä tai rekisteröitymisprosessin loppuunsaattamista. Onnistumisteema voidaan ohjelmallisesti sovittaa, kun onnistunut toiminto on saatu päätökseen.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittamaan käyttäjille, että he ovat tekemässä mahdollisesti riskialtista toimintoa, kuten siirtymistä pois sivulta, jossa on tallentamattomia muutoksia. Nämä toimet ovat usein vähemmän vaikutuksellisia kuin ne, joita käytettäisiin vaarallisessa teemassa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten pienemmille asetuksille tai toiminnoille, jotka ovat enemmän lisäyksiä sivulle eivätkä osa päätoimintoa.
  - **Tietoa**: Tietoteema on hyvä valinta tarjotakseen selventävää, lisätietoa käyttäjälle, kun se on tarpeen.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='500px'
/>

<TableBuilder name="Dialog" />
