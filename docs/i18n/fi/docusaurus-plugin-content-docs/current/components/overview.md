---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 3cecf991ebc3086900ecf15b1d0a7b20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Käyttöliittymäkomponentit | Käyttöliittymäsovelluksen rakennuskomponentit</title>
</Head>

WebforJ:ssa sovellukset luodaan modulaaristen yksiköiden, joita kutsutaan komponenteiksi, avulla, jotka helpottavat nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehys tarjoaa useita olennaisia komponentteja, kuten painikkeita, syöttöelementtejä ja asettelukontteja. Perusteiden hallinnan jälkeen voit konsultoida [JavaDocsia](https://javadoc.io/doc/com.webforj) saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnoista.

## Asetelmat {#layouts}

Asetelmakomponentit tarjoavat perustan käyttöliittymien jäsentämiselle, mahdollistaen kehittäjien järjestää sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestystä, olipa kyseessä yksinkertaiset tai monimutkaiset asettelut.

Seuraavat asettelukomponentit on suunniteltu käsittelemään laajaa valikoimaa käyttötilanteita, aina responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Konttipohjainen komponentti, joka tarjoaa jäsennellyn asettelun sovelluksen ylinavigaatiolle ja sisällön organisoinnille.</p>
  </GalleryCard>

  <GalleryCard header="Työkalupalkki" href="toolbar" image="/img/components/Toolbar.png">
    <p>Vaakasuuntainen konttipohjainen komponentti, joka sisältää joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia, käytetään yleensä suorittamaan tehtäviä, jotka liittyvät nykyiseen kontekstiin.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Asetelmakomponentti, joka järjestää lapsensa joustavien laatikoiden (flexbox) sääntöjen mukaan responsiivista suunnittelua ja kohdistusta varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Asetelmakomponentti, joka järjestää lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukkomaisten rakenteiden luomiseen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Asetelmakomponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin välillä, mahdollistaen käyttäjien muokata niitä vetämällä jakopalkkia.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Liukuva paneelikomponentti, jota käytetään tyypillisesti sivunavigaatiossa tai lisäävän sisällön säilyttämiseksi, joka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialogi" href="dialog" image="/img/components/Dialog.png">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön näyttääksesi tärkeitä tietoja tai kehottaakseen käyttäjän vuorovaikutukseen, usein vaatii käyttäjän toimintaa sulkeakseen sen.</p>
  </GalleryCard>

  <GalleryCard header="Kirjautuminen" href="login" image="/img/components/Login.png">
    <p>Komponentti, joka tarjoaa valmiin käyttöliittymän käyttäjätodennukseen, tyypillisesti sisältää kentät käyttäjätunnukselle ja salasanalle sekä lähetäpainikkeen.</p>
  </GalleryCard>

  <GalleryCard header="Välilehtipaneeli" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Konttipohjainen komponentti, joka järjestää sisällön useisiin välilehtiin, mahdollistaen käyttäjien vaihdon eri näkymien tai osioiden välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttökomponentit tarjoavat olennaisia työkaluja käyttäjän syötteen vangitsemiseksi ja vuorovaikutusten hallitsemiseksi sovelluksessasi. Nämä komponentit ovat monipuolisia, mikä helpottaa interaktiivisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="Tekstikenttä" href="fields/textfield" image="/img/components/TextField.png">
    <p>Yhden rivin syöttökomponentti tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Tekstikenttä" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Tekstisyöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai malliin, käytetään tyypillisesti kentissä, kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="Numerokenttä" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Komponentti, joka tarjoaa oletusikkuna-tyyppisen syöttökentän numeeristen arvojen syöttämiseen, jossa on sisäänrakennetut ohjaimet arvon lisäämiseksi tai vähentämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Numerokenttä" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Numeerinen syöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai malliin, varmistaen kelvollisen numeron syöttämisen, kuten valuuttamäärät, prosentit tai muut muotoillut numerot.</p>
  </GalleryCard>

  <GalleryCard header="Salasanakenttä" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Yhden rivin syöttökomponentti salasanatietojen turvalliseen syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="Päivämääräkenttä" href="fields/datefield" image="/img/components/DateField.png">
    <p>Komponentti, joka tarjoaa oletusikkuna-tyyppisen päivämäärävalitsimen päivämäärän valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Päivämääräkenttä" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Päivämääräsyöttökomponentti, joka pakottaa tietyn päivämäärämuodon tai mallin, varmistaen, että käyttäjä syöttää kelvollisen päivämäärän määritetyn maskin mukaan</p>
  </GalleryCard>

  <GalleryCard header="Aikakenttä" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Komponentti, joka tarjoaa oletusikkuna-tyyppisen aikavalitsimen aikarahan valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Aikakenttä" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Aikasyöttökomponentti, joka pakottaa tietyn aikamuodon tai mallin, varmistaen, että käyttäjä syöttää kelvollisen ajan määritetyn maskin mukaan</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräAikaKenttä" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Komponentti, joka tarjoaa oletusikkuna-tyyppisen päivämäärä- ja aikavalitsimen valittaessa sekä päivämäärän että ajan yhdellä syöttökentällä.</p>
  </GalleryCard>

  <GalleryCard header="VäriKenttä" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Komponentti, joka tarjoaa oletusikkuna-tyyppisen väri-valitsimen, mahdollistaen käyttäjien valita väri syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="Tekstialue" href="textarea" image="/img/components/TextArea.png">
    <p>Monirivinen tekstisyöttökomponentti, joka mahdollistaa käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="checkbox" image="/img/components/CheckBox.png">
    <p>Komponentti, joka edustaa binääristä vaihtoehtoa, mahdollistaen käyttäjien siirtyä valitun (totta) tai valitsemattoman (epätosi) tilan välillä.</p>
  </GalleryCard>

  <GalleryCard header="Radiopainike" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Komponentti, joka mahdollistaa käyttäjien valita yhden vaihtoehdon ryhmästä muista vaihtoehdoista.</p>
  </GalleryCard>

  <GalleryCard header="Kytkin" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Vaihtokomponentti, joka mahdollistaa käyttäjien vaihtaa kahden tilan, kuten päälle/pois tai totta/epätosi, välillä liukuvalla toiminnolla.</p>
  </GalleryCard>

  <GalleryCard header="Valintalaatikko" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Komponentti, joka tarjoaa avattavan luettelon ennalta määritetyistä vaihtoehdoista, mahdollistaen käyttäjien valita yksi vaihtoehto luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="Yhdistelmälaatikko" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Komponentti, joka yhdistää avattavan luettelon ja muokattavan tekstisyötteen, mahdollistaen käyttäjien valita joko vaihtoehdon luettelosta tai syöttää mukautettu arvo.</p>
  </GalleryCard>

  <GalleryCard header="Luetteloruudut" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Komponentti, joka näyttää vieritettävän luettelon vaihtoehdoista, mahdollistaen käyttäjien valita yhden tai useamman kohteen luettelosta.</p>
  </GalleryCard>
</GalleryGrid>

## Valintapaneelit {#option-dialogs}

Valintapaneelit tarjoavat tavan esittää käyttäjille vaihtoehtoja tai pyytää heiltä vahvistusta ennen toiminnan jatkamista. Nämä komponentit ovat olennaisia interaktiivisten, päätöksentekoon perustuvien työnkulkujen luomiseksi, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita erilaisista vaihtoehdoista selkeästi ja jäsennellysti.

<GalleryGrid>
  <GalleryCard header="ViestiDialogi" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Dialogikomponentti, jota käytetään tiedottavien viestien tai hälytysten näyttämiseen käyttäjälle, tyypillisesti yhdellä `OK`-painikkeella viestin vahvistamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="VahvistusDialogi" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Dialogikomponentti, joka kysyy käyttäjältä vahvistusta tai peruutusta toiminnolle, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` painikkeita.</p>
  </GalleryCard>
  
  <GalleryCard header="SyöttöDialogi" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Dialogikomponentti, joka kehottaa käyttäjää syöttämään tekstiä tai tietoa, tyypillisesti tarjoamalla syöttökentän sekä toimintonappula kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="TiedostoValintaDialogi" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="TiedostoLähetysDialogi" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille lähettää tiedostoja paikallisesta tiedostojärjestelmästään sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="TiedostoTallennusDialogi" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille tallentaa tiedoston määritettyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjien vuorovaikutuksia ja visuaalisesti näyttävät tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, käynnistämään toimintoja ja ymmärtämään edistystä tai tuloksia dynaamisten visuaalisten elementtien avulla.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/Table.png">
    <p>Komponentti, jota käytetään tietojen näyttämiseen jäsennellyssä, taulukkomuotoisessa muodossa riveineen ja sarakkeineen, tukee ominaisuuksia, kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Komponentti, joka integroituu Google Charts -työkalun kanssa näyttääkseen erilaisia kaavioita ja visuaalisia tietoesityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/Button.png">
    <p>Napautettava komponentti, joka käynnistää toiminnon tai tapahtuman, kun sitä painetaan.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka näyttää lyhyesti viestin käyttäjälle ennen kuin se automaattisesti katoaa.</p>
  </GalleryCard>

  <GalleryCard header="Ilmoitus" href="alert" image="/img/components/Alert.png">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia selkeässä muodossa käyttäjän huomion kiinnittämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="TyöpöydänIlmoitus" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Komponentti, joka hyödyntää selaimen natiivin Ilmoitus API:a ilmoittaakseen käyttäjille mukautetuilla työpöytälmoituksilla.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/Navigator.png">
    <p>Muokattava sivutuskomponentti, joka mahdollistaa tietojoukoissa navigoinnin, tukee asetteluja, joissa on ensimmäinen, viimeinen, seuraava, edellinen painike ja pikavalintakentät.</p>
  </GalleryCard>

  <GalleryCard header="Edistymispalkki" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistymistä, tyypillisesti näytetään vaakasuorana palkkina, joka täyttyy edistymisen myötä.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/Slider.png">
    <p>Komponentti, joka mahdollistaa käyttäjien valita arvon määritetystä alueesta vetämällä kahvaa radalla.</p>
  </GalleryCard>

  <GalleryCard header="KiireinenIndikaattori" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Koko sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörivä, joka signaaloi, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Lataaminen" href="loading" image="/img/components/Loading.png">
    <p>Rajoitettu latausindikaattori, joka näyttää tietyn vanhemman komponentin sisällä, osoittaen, että sisältöä tai tietoja ladataan kyseisessä osassa.</p>
  </GalleryCard>

  <GalleryCard header="Pyörijä" href="spinner" image="/img/components/Spinner.png">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään osoittamaan, että prosessi tai toiminto on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="SovelluksenNavigointi" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Komponentti, joka tarjoaa navigointivalikon sovellukselle, tyypillisesti käytetään luettelona linkkeinä tai navigointikohteina eri osioiden tai näkymien välillä.</p>
  </GalleryCard>

  <GalleryCard header="Ikoni" href="icon" image="/img/components/Icons.png">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, käytetään usein edustamaan toimintoa, tilaa tai luokkaa käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Pääte" href="terminal" image="/img/components/Terminal.png">
    <p>Komponentti, joka simuloi komentorivikäyttöliittymää (CLI) sovelluksessa, mahdollistamalla käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="ÄärettömänSelaaminen" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Komponentti, joka lataa lisää kohteita vierittäessä, näyttää latauksen ja seuraa, kun kaikki sisältö on noudettu.</p>
  </GalleryCard>

  <GalleryCard header="Päivitys" href="refresher" image="/img/components/Refresher.png">
    <p>Komponentti, joka mahdollistaa pull-to-refresh-interaktion vieritettävissä konteissa—ihanteellinen dynaamisten tietojen lataamista varten.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/Tree.png">
    <p>Komponentti hierarkkisten tietojen näyttämiseen, jättäen käyttäjät laajentamaan, kutistamaan ja vuorovaikuttamaan sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Komponentti, joka näyttää käyttäjäprofiilikuvia tai -alkukirjaimia, tukee erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownKatselija" href="markdownviewer" image="/img/components/MarkdownViewer.png">
    <p>Komponentti, joka näyttää markdown-sisältöä progressiivisella kirjainmerkki-merkiltä, ihanteellinen AI-keskusteluversioille ja streamattavalle tekstille.</p>
  </GalleryCard>
  
</GalleryGrid>
