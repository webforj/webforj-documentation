---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 2af867ffb7bb39ed4624efa14b81d452
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Käyttöliittymäkomponentit | Käyttöliittymäohjelmoinnin komponentit</title>
</Head>

webforJ:ssä sovelluksia luodaan moduulimaisista yksiköistä, joita kutsutaan komponenteiksi, jotka helpottavat nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehys tarjoaa valikoiman olennaisia komponentteja, kuten painikkeita, syöteelementtejä ja asettelukontteja. Perusteet hallittuasi voit katsastaa [JavaDocs](https://javadoc.io/doc/com.webforj) saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnallisuudesta.

## Asettelu {#layouts}

Asettelukomponentit tarjoavat perustan käyttöliittymien jäsentämiselle, jolloin kehittäjät voivat organisoida sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestämistä, olipa kyseessä yksinkertaiset tai monimutkaiset asettelut.

Seuraavat asettelukomponentit on suunniteltu käsittelemään laaja valikoima käyttötapauksia, responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Säiliökomponentti, joka tarjoaa jäsennellyn asettelun sovelluksen ylimmän tason navigaatiolle ja sisällön organisoinnille.</p>
  </GalleryCard>

  <GalleryCard header="Työkalupalkki" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Vaakasuuntainen säiliökomponentti, joka sisältää joukon toimintopainikkeita, ikoneita tai muita ohjaimia, joita käytetään tyypillisesti nykyiseen kontekstiin liittyvien tehtävien suorittamiseen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Asettelu komponentti, joka järjestää lapsensa käyttämällä joustavia laatikoita (flexbox) säännöksiä responsiivista suunnittelua ja kohdistusta varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Asettelu komponentti, joka järjestää lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukkomaisen rakenteen luomiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Asettelu komponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin välillä, mahdollistaen käyttäjien koon muuttamisen vetämällä jakopalkkia.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Liukuva paneelikomponentti, jota käytetään yleensä sivu navigaatioon tai lisäsisällön tarjoamiseen, jota voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialogi" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön tärkeän tiedon näyttämiseksi tai käyttäjän vuorovaikutuksen pyytämiseksi, usein vaatimalla käyttäjän toimintaa sulkeakseen.</p>
  </GalleryCard>

  <GalleryCard header="Kirjautuminen" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Komponentti, joka tarjoaa valmiiksi rakennettujen käyttöliittymien käyttäjäntunnistukseen, tyypillisesti sisältäen kentät käyttäjänimen ja salasanan syöttämiseksi yhdessä lähetyspainikkeen kanssa.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Pystysuunnassa pinottu joustava paneelisetti, joissa jokaisessa on napsautettava otsikko, joka vaihtaa näkyvyyden sen sisällön välillä.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Säiliökomponentti, joka organisoi sisällön useisiin välilehtiin, mahdollistaen käyttäjien vaihtaa eri näkymiä tai osioita.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttökomponentit tarjoavat olennaisia työkaluja käyttäjäsyötteen tallentamiseen ja vuorovaikutusten hallintaan sovelluksessasi. Nämä komponentit ovat monikäyttöisiä, mikä helpottaa interaktiivisten lomakkeiden luomista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="Tekstikenttä" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Yhden rivin syöttökomponentti tekstidatan syöttämiseksi ja muokkaamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Tekstikenttä" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Tekstisyöttökomponentti, joka rajoittaa käyttäjän syötettä tiettyyn muotoon tai kaavaan, tyypillisesti käytetään kentissä kuten puhelinnumerot, päivämäärät tai luottokortin numerot.</p>
  </GalleryCard>

  <GalleryCard header="Numerokenttä" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen syöttökentän numerovarojen syöttämiseen, built-in-ohjaimilla arvon lisäämiseksi tai vähentämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Numerokenttä" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Numerosyytekomponentti, joka rajoittaa käyttäjän syötettä tiettyyn numeromuotoon tai kaavaan, varmistaen sallitun numeron syöttämisen kuten valuutoille, prosentuille tai muille muotoilluille numeroille.</p>
  </GalleryCard>

  <GalleryCard header="Salasanakenttä" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Yhden rivin syöttökomponentti salasanadatan turvalliseen syöttämiseen ja naamioimiseen.</p>
  </GalleryCard>

  <GalleryCard header="Päivämääräkenttä" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärävalitsimen, jolla voidaan valita päivämäärä syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Päivämääräkenttä" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Päivämääräsyöttökomponentti, joka pakottaa tiettyyn päivämäärämuotoon tai kaavaan, varmistaen, että käyttäjä syöttää voimassa olevan päivämäärän määritetyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="Aikakenttä" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen aikavalitsimen aikarajan valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Aikakenttä" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Aikasyöttökomponentti, joka pakottaa tiettyyn aikamuotoon tai kaavaan, varmistaen, että käyttäjä syöttää voimassa olevan ajan määritetyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräAikaKenttä" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärä- ja aikavalitsimen, joka mahdollistaa sekä päivämäärän että ajan valitsemisen yhdestä syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="VäriKenttä" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen väri valitsimen, joka mahdollistaa käyttäjien valita väri syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="Tekstialue" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Monirivinen syöttökomponentti, joka mahdollistaa käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Komponentti, joka edustaa binaarista vaihtoehtoa, mahdollistaen käyttäjien kytkeä päälle tai pois (tosi/epätosi) tilan.</p>
  </GalleryCard>

  <GalleryCard header="Radiopainike" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Komponentti, joka mahdollistaa käyttäjien valita yhden vaihtoehdon joukosta toisiinsa liittyviä valintoja.</p>
  </GalleryCard>

  <GalleryCard header="Vaihtoehto" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Kytkin komponentti, joka mahdollistaa käyttäjien vaihtaa kahden tilan, kuten päällä/pois tai tosi/epätosi, liukuvan toiminnon avulla.</p>
  </GalleryCard>

  <GalleryCard header="Valintalaatikko" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Komponentti, joka tarjoaa pudotusvalikon määritellyistä vaihtoehdoista, mahdollistaen käyttäjien valita yksi vaihtoehto listalta.</p>
  </GalleryCard>

  <GalleryCard header="Yhdistelmälaatikko" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Komponentti, joka yhdistää pudotuslistan muokattavaan tekstisyöttöön, mahdollistaen käyttäjien valita joko vaihtoehto listalta tai syöttää mukautettu arvo.</p>
  </GalleryCard>

  <GalleryCard header="Listalaatikko" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Komponentti, joka näyttää vieritettävän luettelon vaihtoehdoista, mahdollistaen käyttäjien valita yksi tai useampi kohde listalta.</p>
  </GalleryCard>
</GalleryGrid>

## Vaihtoehtodialogit {#option-dialogs}

Vaihtoehtodialogit tarjoavat tavan esittää käyttäjille vaihtoehtoja tai pyytää heiltä vahvistusta ennen toiminnan jatkumista. Nämä komponentit ovat olennaisia interaktiivisten, päätöksentekopohjaisten työprosessien luomiseksi, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita erilaisista vaihtoehdoista selkeästi ja jäsennellysti.

<GalleryGrid>
  <GalleryCard header="ViestiDialogi" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Dialogikomponentti, jota käytetään tiedollisten viestien tai ilmoitusten näyttämiseen käyttäjälle, tyypillisesti yhdessä `OK`-painikkeen kanssa viestin hyväksymiseksi.</p>
  </GalleryCard>

  <GalleryCard header="VahvistusDialogi" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Dialogikomponentti, joka pyytää käyttäjältä vahvistusta tai peruutusta toiminnalla, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` -painikkeet.</p>
  </GalleryCard>
  
  <GalleryCard header="SyöttöDialogi" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Dialogikomponentti, joka pyytää käyttäjältä tekstin tai datan syöttämistä, tyypillisesti tarjoamalla syöttökentän yhdessä toimintopainikkeiden, kuten `OK` ja `Peruuta`, kanssa.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonValitsijaDialogi" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonLähetysDialogi" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille tiedostojen lataamisen paikalliselta tiedostojärjestelmältä sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonTallennusDialogi" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille tiedostojen tallentamisen määritettyyn sijaintiin palvelimen tiedostojärjestelmään.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutuksia ja visuaalisesti näyttävät tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, laukaisevat toimintoja ja ymmärtämään edistystä tai tuloksia dynaamisten visuaalisten elementtien avulla.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p> Komponentti, jota käytetään tietojen näyttämiseen jäsennellyssä, taulukkomuodossa riveineen ja sarakkeineen, tukee ominaisuuksia kuten lajittelua ja sivuttamista.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Komponentti, joka integroituu Google Chartsiin näyttämään erilaisia kaavioita ja visuaalisia tietoesityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Napsautettava komponentti, joka laukaisee toiminnon tai tapahtuman painettaessa.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka näyttää viestin käyttäjälle lyhyeksi aikaa ennen automaattista häipymistä.</p>
  </GalleryCard>

  <GalleryCard header="Ilmoitus" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia huomattavassa muodossa käyttäjän huomion saamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Merkkilappu" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Pieni merkki komponentti laskujen, tilojen tai lyhyiden metatietojen näyttämiseksi, tukee teemoja, kokoja ja ikoneita.</p>
  </GalleryCard>

  <GalleryCard header="TyöpöydänIlmoitus" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Komponentti, joka hyödyntää selaimen natiivin Ilmoitus-API:a varoittamaan käyttäjiä mukautetuilla työpöydän ilmoituksilla.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Muokattava sivutuskomponentti datakokonaisuuksien navigoimiseen, tukee asetteluja ensimmäiselle, viimeiselle, seuraavalle, edelliselle painikkeelle ja nopealle hyppykentälle.</p>
  </GalleryCard>

  <GalleryCard header="Edistyspalkki" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistystä, tyypillisesti esitetään vaakasuorana palkkina, joka täyttyy edistyessä.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Komponentti, joka mahdollistaa käyttäjien valita arvon määritellyltä alueelta vetämällä kahvaa radalla.</p>
  </GalleryCard>

  <GalleryCard header="TyössäOlemisenIlmaisin" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p> Sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörivä, joka ilmoittaa, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Lataus" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Rajattu latausilmaisin, joka näkyy tietyssä vanhemmassa komponentissa, ilmoittaen että sisältöä tai dataa ladataan kyseisessä osassa.</p>
  </GalleryCard>

  <GalleryCard header="Pyörijä" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään ilmoittamaan, että prosessi tai toiminta on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="SovellusNavigointi" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Komponentti, joka tarjoaa navigointimenun sovellukselle, tyypillisesti käytetään linkkien tai navigointikohteiden listaamiseen, joilla voidaan vaihtaa eri osioiden tai näkymien välillä.</p>
  </GalleryCard>

  <GalleryCard header="Ikoni" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein toiminnan, tilan tai kategorian esittämiseen käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Pääte" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Komponentti, joka simuloi komentoriviliittymää (CLI) sovelluksessa, mahdollistaen käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="ÄärettömänRullaaminen" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Komponentti, joka lataa lisää kohteita vierittäessä, näyttää lataajan ja seuraa, milloin kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Päivitys" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Komponentti, joka mahdollistaa kaikkeuttamisen vetämällä päivitysarvoa vieritettävissä säiliöissä, ideaalinen dynaamisen datan lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Komponentti hierarkisten tietojen näyttämiseen, mahdollistaen käyttäjille laajentaa, kutistaa ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
  
  <GalleryCard header="Profiilikuva" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Komponentti, joka näyttää käyttäjäprofiilikuvia tai alkukirjaimia, tukee erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownNäkymä" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Komponentti, joka näyttää markdown-sisältöä edistyksellisellä merkki kerrallaan renderoinnilla, ihanteellinen tekoäly-chattiliittymille ja striimattavalle tekstille.</p>
  </GalleryCard>
  
</GalleryGrid>
