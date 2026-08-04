---
title: Dialog
sidebar_position: 30
description: >-
  Open modal popups with the Dialog component, including header, content, and
  footer sections, backdrop blur, and configurable close behavior.
_i18n_hash: 901c54134f4c21092deb23457747a29b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-dialog" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="dialog" location="com/webforj/component/dialog/Dialog" top='true'/>

`Dialog`-komponentti näyttää ponnahdusikkunan, joka peittää nykyisen näkymän ja kiinnittää huomiota keskittyneeseen sisältöön, kuten lomakkeisiin, vahvistuksiin tai tiedotusviesteihin.

<!-- INTRO_END -->

## `Dialog`-rakenne {#dialog-structure}

`Dialog` on järjestetty kolmeen osioon: otsikkoon, sisältöalueeseen ja alatunnisteeseen. Komponentteja voidaan lisätä kuhunkin osioon käyttäen `addToHeader()`, `addToContent()` ja `addToFooter()`.

<ComponentDemo
path='/webforj/dialogsections'
files={['src/main/java/com/webforj/samples/views/dialog/DialogSectionsView.java']}
height='375px'
/>

## Käyttötarkoitukset {#usages}

1. **Käyttäjäpalaute ja vahvistus**: `Dialog`-komponentteja käytetään usein palautteen antamiseen tai käyttäjän vahvistuksen kysymiseen. Ne voivat näyttää erilaisia tärkeitä palautteita käyttäjälle, kuten:

  >- Onnistumisviestit
  >- Virheilmoitukset
  >- Vahvistuslähetykset

2. **Lomake syöttö ja muokkaus**: Voit käyttää dialogeja käyttäjäsyötteen keräämiseen tai sallia heidän muokata tietoja hallitusti ja keskittyneesti. Esimerkiksi, dialogi voi ilmestyä käyttäjän profiilitietojen muokkaamiseksi tai monivaiheisen lomakkeen täydentämiseksi.

3. **Kontekstuaalinen tieto**: Lisäkontekstuaalisen tiedon tai työkaluvihjeiden näyttäminen dialogissa voi auttaa käyttäjiä ymmärtämään monimutkaisia ominaisuuksia tai tietoja. Dialogit voivat tarjota syvällisiä selityksiä, kaavioita tai ohjedokumentaatiota.

4. **Kuva- ja mediakatselut**: Kun käyttäjät tarvitsevat mediatiedostojen katsomista, `Dialog`-komponenttia voidaan käyttää näyttämään suurempia esikatseluja tai gallerioita, kuten vuorovaikutuksessa:
  >- Kuvien
  >- Videoiden
  >- Muiden medioiden

## Tausta ja blur {#backdrop-and-blur}

Auki oleva `Dialog`-komponentti omaa himmeän taustan, joka hienovaraisesti kiinnittää huomiota sen sisältöön. Käyttämällä `setBackdrop()` ja `setBlurred()`, voit muuttaa sitä, miten webforJ näyttää (tai peittää) sisällön `Dialog`-komponentin takana. Näiden attribuuttien muuttaminen voi auttaa käyttäjiä tarjoamalla syvyyttä ja visuaalista hierarkiaa.

<ComponentDemo
path='/webforj/dialogbackdropblur'
files={['src/main/java/com/webforj/samples/views/dialog/DialogBackdropBlurView.java']}
height='600px'
/>

## `Dialog`-komponentin avaaminen ja sulkeminen {#opening-and-closing-the-dialog}

Uuden `Dialog`-objektin luomisen jälkeen käytä `open()`-metodia näyttämään dialogi. Tämän jälkeen `Dialog`-komponentti voi sulkeutua yhdellä seuraavista tavoista:
- Käyttämällä `close()`-metodia
- Paina <kbd>ESC</kbd> -näppäintä
- Napsauttamalla dialogin ulkopuolella

Kehittäjät voivat valita, mitkä vuorovaikutukset sulkevat `Dialog`-komponentin käyttäen `setCancelOnEscKey()` ja `setCancelOnOutsideClick()`. Lisäksi `setClosable()`-metodi voi estää tai sallia sekä <kbd>ESC</kbd> -näppäimen painamisen että napsauttamisen ulkopuolella sulkeaksesi komponentin.

<ComponentDemo
path='/webforj/dialogclose'
files={['src/main/java/com/webforj/samples/views/dialog/DialogCloseView.java']}
height='375px'
/>

## Automaattinen kohdistus {#auto-focus}

Kun automaattinen kohdistus on käytössä, se antaa automaattisesti keskittymisen ensimmäiselle fokusoitavalle elementille dialogissa. Tämä on hyödyllistä käyttäjien huomion ohjaamiseksi ja sitä voidaan mukauttaa `setAutoFocus()`-metodilla.

<ComponentDemo
path='/webforj/dialogautofocus'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoFocusView.java']}
height='400px'
/>

## Vedettävä {#draggable}

`Dialog`-komponentilla on sisäänrakennettu toiminnallisuus olla vedettävä, jolloin käyttäjä voi siirtää `Dialog`-ikkunan klikkaamalla ja vetämällä. `Dialog`-ikkunan sijaintia voidaan muuttaa miltä tahansa sen kentältä: otsikko, sisältö tai alatunniste.

### Reunaan napsautus {#snap-to-edge}
Tätä käyttäytymistä voidaan myös kalibroida niin, että se napsauttaa näytön reunaan, mikä tarkoittaa, että `Dialog` asettuu automaattisesti näyttöruudun reunaan, kun se vapautetaan vetämisestä ja pudottamisesta. Napsauttamista voidaan muuttaa `setSnapToEdge()`-metodilla. `setSnapThreshold()` ottaa vastaan pikselin määrän, joka määrittää, kuinka kaukana `Dialog`-ikkunan tulisi olla näyttöruudun reunoista ennen kuin se napsauttaa automaattisesti reunoihin.

<ComponentDemo
path='/webforj/dialogdraggable'
files={['src/main/java/com/webforj/samples/views/dialog/DialogDraggableView.java']}
height='325px'
/>

## Sijoittaminen {#positioning}

Dialogin sijaintia voidaan muuttaa käyttämällä sisäänrakennettuja `setPosx()` ja `setPosy()` -metodeja. Nämä metodit ottavat vastaan merkkijonoargumentin, joka voi edustaa mitä tahansa soveltuvaa CSS-pituusyksikköä, kuten pikseleitä tai näkymän korkeutta/leveyttä. Luettelo näistä mittauksista [löytyy tältä linkiltä](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units#numbers_lengths_and_percentages).

<ComponentDemo
path='/webforj/dialogpositioning'
files={['src/main/java/com/webforj/samples/views/dialog/DialogPositioningView.java']}
height='400px'
/>

### Vaaka allas {#vertical-alignment}

Lisäksi dialogin X- ja Y-sijainnin manuaalisen määrittämisen lisäksi on mahdollista käyttää dialogin sisäänrakennettua enum-luokkaa `Dialog`-komponentin kohdistamiseen. Käytettävissä on kolme mahdollista arvoa: `TOP`, `CENTER` ja `BOTTOM`, joita voidaan käyttää `setAlignment()`-metodissa.

<ComponentDemo
path='/webforj/dialogalignments'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAlignmentsView.java']}
height='450px'
/>

### Koko näyttö ja katkaisupisteet {#full-screen-and-breakpoints}

`Dialog` voidaan asettaa koko näyttö -tilaan. Kun koko näyttö on käytössä, `Dialog`-ikkunaa ei voi siirtää tai asemoida. Tämän tilan voi manipuloida `Dialogin` katkaisupisteattribuutilla. Katkaisupiste on media kysely, joka määrittää, milloin `Dialog` siirtyy automaattisesti koko näyttö -tilaan. Kun kysely vastaa, `Dialog` muuttuu koko näyttöön - muuten se on sijoitettu.

### Automaattinen leveys <DocChip chip='since' label='26.00' /> {#auto-width}

Oletusarvoisesti `Dialog` venyy täyttämään saatavilla olevan vaaka-alueen. Kun automaattinen leveys on käytössä `setAutoWidth(true)`-metodin kautta, `Dialog`-ikkuna määrittää itsensä sisältönsä leveyden perusteella.

<ComponentDemo
path='/webforj/dialogautowidth'
files={['src/main/java/com/webforj/samples/views/dialog/DialogAutoWidthView.java']}
height='350px'
/>

## Tyylitys {#styling}

### Teemat {#themes}

`Dialog`-komponenteissa on <JavadocLink type="foundation" location="com/webforj/component/dialog/Dialog.Theme.html">7 erillistä teemaa</JavadocLink> valmiina nopeaa tyylitusta varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja yhdenmukaisen tavan mukauttaa painikkeiden ulkonäköä koko sovelluksessa.

Vaikka kaikilla eri teemoilla on monia käyttötarkoituksia, tässä on joitakin esimerkkejä:

  - **Vaarallinen**: Toiminnot, joilla on vakavia seurauksia, kuten täytettyjen tietojen tyhjentäminen tai tilin/tietojen pysyvä poistaminen, ovat hyviä käyttötarkoituksia vaarallisen teeman dialogeille.
  - **Oletus**: Oletusteema on sopiva toimille sovelluksessa, jotka eivät vaadi erityistä huomiota ja jotka ovat yleisiä, kuten asetuksen vaihtaminen.
  - **Pääasiallinen**: Tämä teema on sopiva pää "toimintakehotteelle" sivulla, kuten rekisteröitymiselle, muutosten tallentamiselle tai siirtymiselle toiselle sivulle.
  - **Onnistuminen**: Onnistumisteemaiset dialogit ovat erinomaisia visualisoimaan sovelluksessa tapahtuvan elementin onnistunutta suorittamista, kuten lomakkeen lähettämistä tai rekisteröintiprosessin loppuunsaattamista. Onnistumisteema voidaan soveltaa ohjelmallisesti, kun onnistunut toimi on suoritettu.
  - **Varoitus**: Varoitusdialogit ovat hyödyllisiä ilmoittamaan käyttäjille, että he ovat suorittamassa potentiaalisesti riskialtista toiminta, kuten siirtyminen pois sivulta tallentamattomilla muutoksilla. Nämä toiminnot ovat usein vähemmän vaikuttavia kuin ne, joita käytetään vaarallisen teeman kanssa.
  - **Harmaa**: Hyvä hienovaraisille toiminnoille, kuten vähäisille asetuksille tai toiminnoille, jotka ovat enemmän lisäyksiä sivulle, eivätkä osa päätoiminnallisuutta.
  - **Tieto**: Tieto-teema on hyvä valinta tarjoamaan selventävää, lisätietoa käyttäjälle, kun sitä painetaan.

<ComponentDemo
path='/webforj/dialogthemes'
files={['src/main/java/com/webforj/samples/views/dialog/DialogThemesView.java']}
height='375px'
/>

<TableBuilder name="Dialog" />
