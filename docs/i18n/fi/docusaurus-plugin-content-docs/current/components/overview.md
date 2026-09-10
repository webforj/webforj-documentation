---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 200027a33988025dba52cd07c34d2e27
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Käyttöliittymäkomponentit | Käyttöliittymäsovellusten rakennuskomponentit</title>
</Head>

webforJ:ssa sovelluksia luodaan moduulimaisista yksiköistä, joita kutsutaan komponenteiksi, jotka helpottavat nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehyksellä on tarjolla joukko keskeisiä komponentteja, kuten painikkeet, syötekentät ja asetteluastiat. Perusteiden hallinnan jälkeen voit tutustua [JavaDocs](https://javadoc.io/doc/com.webforj) -dokumentaatioon, josta löydät yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnallisuuksista.

## Asettelut {#layouts}

Asettelu komponentit tarjoavat perustan käyttöliittymien jäsentämiseen, mahdollistaen kehittäjien järjestää sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien asettumista, sekä yksinkertaisissa että monimutkaisissa asetteluissa.

Seuraavat asettelu komponentit on suunniteltu käsittelemään laajaa valikoimaa käyttötarkoituksia, aina responsiivisista muotoilusta kehittyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Sisältökomponentti, joka tarjoaa jäsennellyn asettelun ykköstyypin sovelluksen navigointia ja sisällön järjestämistä varten.</p>
  </GalleryCard>

  <GalleryCard header="Työkalurivi" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Vaakasuuntainen sisältökomponentti, joka pitää sisällään joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia, joita käytetään tyypillisesti nykyiseen kontekstiin liittyvien tehtävien suorittamiseen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Asettelu komponentti, joka järjestää lapsensa joustavan laatikon (flexbox) sääntöjen avulla responsiivista muotoilua ja tasausta varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Asettelu komponentti, joka järjestää lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukkomaisen rakenteen luomiseen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Asettelu komponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin kesken, jolloin käyttäjät voivat muuttaa niiden kokoa vetämällä jakotankoa.</p>
  </GalleryCard>

  <GalleryCard header="Laatikko" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Liukuva paneelikomponentti, jota käytetään tyypillisesti sivunavigaation tai lisäsisällön säilyttämiseen, joka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialogi" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön tärkeiden tietojen näyttämiseksi tai käyttäjältä vuorovaikutuksen pyytämiseksi, usein vaaditaan käyttäjän toimintaa sulkeakseen sen.</p>
  </GalleryCard>

  <GalleryCard header="Kirjautuminen" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Komponentti, joka tarjoaa valmiin käyttöliittymän käyttäjäautentikointiin, tyypillisesti sisältäen kentät käyttäjänimensä ja salasanan syöttämiseen sekä lähetyspainikkeen.</p>
  </GalleryCard>

  <GalleryCard header="Yhdistys" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Pystysuunnassa pinottu kokoelma taitettavia paneeleita, joista jokaisella on napsautettava otsikko, joka vaihtaa näkyvyyden sen runkosisällön välillä.</p>
  </GalleryCard>

  <GalleryCard header="Sivupaneeli" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Komponentti, joka järjestää sisällön useisiin välilehtiin, jolloin käyttäjät voivat vaihtaa eri näkymien tai osioiden välillä.</p>
  </GalleryCard>

  <GalleryCard header="Kortti" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Pinta, joka ryhmittelee liittyvää sisältöä ja toimintoja, alueet medialle, otsikoille, runkosisällölle ja alatunnisteille.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttö komponentit tarjoavat keskeisiä työkaluja käyttäjän syötteen tallentamiseen ja vuorovaikutusten hallitsemiseen sovelluksessasi. Nämä komponentit ovat monipuolisia, jolloin voit helposti luoda interaktiivisia lomakkeita ja kerätä erilaisia tietotyyppejä.

<GalleryGrid>
  <GalleryCard header="Tekstikenttä" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Yhden rivin syöttökomponentti tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Tekstikenttä" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Tekstisyöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai kaavaan, tyypillisesti käytetään kentissä kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="NumeroKenttä" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selaimessa käytettävän syöttökentän numeeristen arvojen syöttämistä varten, sisäänrakennetuilla ohjaimilla arvon lisäämiseksi tai vähentämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>NumeroKenttä" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Numeerinen syöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai kaavaan, varmistaen kelvollisen lukutulon, kuten valuutalle, prosenteille tai muille muotoilluille numeroille.</p>
  </GalleryCard>

  <GalleryCard header="SalasanaKenttä" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Yhden rivin syöttökomponentti salasanojen turvalliseen syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräKenttä" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selaimessa käytettävän päivämäärävalitsimen päivämäärän valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>PäivämääräKenttä" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Päivämääräsyöttökomponentti, joka pakottaa tietyn päivämäärämuodon tai kaavan, varmistaen että käyttäjä syöttää kelvollisen päivämäärän määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="AikaKenttä" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selaimessa käytettävän aikavalitsimen aikarajan valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>AikaKenttä" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Aikasyöttökomponentti, joka pakottaa tietyn aikamuodon tai kaavan, varmistaen että käyttäjä syöttää kelvollisen ajan määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräAikaKenttä" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selaimessa käytettävän päivämäärän ja ajan valitsimen sekä päivämäärän että ajan valitsemiseksi yhdellä syöttökentällä.</p>
  </GalleryCard>

  <GalleryCard header="VäriKenttä" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selaimessa käytettävän väri-selectorin, joka sallii käyttäjien valita värin syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="Tekstialue" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Monirivinen tekstisyöttökomponentti, joka sallii käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Komponentti, joka edustaa binäärivaihtoehtoa, sallien käyttäjien vaihtaa valitun (totta) tai ei-valitun (epätosi) tilan välillä.</p>
  </GalleryCard>

  <GalleryCard header="RadioPainike" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Komponentti, joka sallii käyttäjien valita yhden vaihtoehdon keskuudestaan, joka on keskenään poissulkeva.</p>
  </GalleryCard>

  <GalleryCard header="Kytkin" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Vaihtokomponentti, joka sallii käyttäjien vaihtaa kahden tilan, kuten päälle/pois tai totta/epätosi, välillä liukuvan toiminnan avulla.</p>
  </GalleryCard>

  <GalleryCard header="ValintaRuutu" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Komponentti, joka tarjoaa alasvetoluettelon ennalta määritellyistä vaihtoehdoista, sallien käyttäjien valita yhden vaihtoehdon listasta.</p>
  </GalleryCard>

  <GalleryCard header="YhdistelmäLaatikko" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Komponentti, joka yhdistää alasvetoluettelon muokattavan tekstisyöttökentän kanssa, sallien käyttäjien valita joko vaihtoehdon listasta tai syöttää oman arvonsa.</p>
  </GalleryCard>

  <GalleryCard header="ListaLaatikko" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Komponentti, joka näyttää vieritettävän luettelon vaihtoehdoista, sallien käyttäjien valita yksi tai useampi kohde listasta.</p>
  </GalleryCard>

  <GalleryCard header="Lähetä" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Inline-tiedostonvalitsin, joka sallii käyttäjien valita yhden tai useampia tiedostoja paikalliselta koneelta ja ladata ne palvelimelle, vetämällä ja pudottamalla, suodattimini ja per-tiedosto-tapahtumaseurannalla.</p>
  </GalleryCard>
</GalleryGrid>

## Valintalaatikot {#option-dialogs}

Valintalaatikot tarjoavat tavan esittää käyttäjille vaihtoehtoja tai pyytää heiltä vahvistusta ennen toiminnan etenemistä. Nämä komponentit ovat tärkeitä interaktiivisten, päätöksiä ohjaavien työnkulkujen luomisessa, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita erilaisia vaihtoehtoja selkeällä ja jäsennellyllä tavalla.

<GalleryGrid>
  <GalleryCard header="ViestiDialogi" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Dialogikomponentti, jota käytetään näyttämään tiedotuksia tai varoituksia käyttäjälle, tyypillisesti yhdellä `OK`-painikkeella viestin hyväksymiseksi.</p>
  </GalleryCard>

  <GalleryCard header="VahvistusDialogi" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Dialogikomponentti, joka kysyy käyttäjältä, haluaako hän vahvistaa vai peruuttaa toiminnon, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` painikkeet.</p>
  </GalleryCard>

  <GalleryCard header="SyöttöDialogi" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Dialogikomponentti, joka pyytää käyttäjää syöttämään tekstiä tai tietoa, tyypillisesti tarjoamalla syöttökentän sekä toimintopainikkeet, kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonValintaDialogi" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonLähetysDialogi" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien ladata tiedostoja paikallisesta tiedostojärjestelmästä sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonTallennusDialogi" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien tallentaa tiedostoja määriteltyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutuksia ja näyttävät visuaalisesti tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, käynnistämään toimintoja ja ymmärtämään etenemistä tai tuloksia dynaamisten visuaalisten elementtien kautta.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Komponentti, jota käytetään tietojen esittämiseen rakenteellisessa, taulukkotyyppisessä muodossa riveillä ja sarakkeilla, tukien ominaisuuksia kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Komponentti, joka integroituu Google Charts:iin esittääkseen erilaisia kaavioita ja visuaalisia datan esityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Napsautettava komponentti, joka käynnistää toiminnon tai tapahtuman painettaessa.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Kevyt, ei-loisiva ilmoituskomponentti, joka näyttää lyhyesti viestin käyttäjälle ennen kuin se katoaa automaattisesti.</p>
  </GalleryCard>

  <GalleryCard header="Ilmoitus" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia huomattavassa muodossa käyttäjän huomion kiinnittämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Raha" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Pieni tunnistekomponentti, joka esittää laskentoja, tiloja tai lyhyitä metatietoja, tukien teemoja, kokoja ja kuvakkeita.</p>
  </GalleryCard>

  <GalleryCard header="TyöpöydänIlmoitus" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Komponentti, joka hyödyntää selaimen natiivin Ilmoitus-API:ta hälyttämään käyttäjiä mukautetulla työpöydän ilmoituksella.</p>
  </GalleryCard>

  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Mukautettava sivutuksensuunnittelu komponentti datan joukkojen läpi navigoimiseen, tukea ensimmäisen, viimeisen, seuraavan, edellisen painike ja pikakuuden kenttiä.</p>
  </GalleryCard>

  <GalleryCard header="Etenemispalkki" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistystä, tyypillisesti esitettynä vaakasuorana palkkina, joka täyttyy edistymisen myötä.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Komponentti, joka sallii käyttäjien valita arvon määritellyltä alueelta vetämällä kahvaa radalla.</p>
  </GalleryCard>

  <GalleryCard header="TyönKäynnistin" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Sovelluslaajuinen visuaalinen indikaattori, tyypillisesti pyörimispainike, joka ilmoittaa, että yleinen prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Lataaminen" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Rajattu lataamisindikaattori, joka näkyy tietyssä vanhemmassa komponentissa, ilmoittaen että sisältöä tai dataa ollaan lataamassa siinä osassa.</p>
  </GalleryCard>

  <GalleryCard header="Pyörivä" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään indikoimaan, että prosessi tai toiminta on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="SovellusNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Komponentti, joka tarjoaa navigointimenun sovelluksessa, tyypillisesti käytetään linkkien tai navigointikohteiden listaamiseen eri osioiden tai näkymien vaihtamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Kuvake" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein toiminnan, tilan tai kategorian esittämiseen käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Päätelaite" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Komponentti, joka simuloituu komentoriviliittymän (CLI) sovelluksessa, sallien käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>

  <GalleryCard header="ÄärettömänRullaus" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Komponentti, joka lataa lisää kohteita vierittäessä, näyttää latauskuormitteen ja seuraa, milloin kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Päivitys" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Komponentti, joka sallii vetämällä päivitä -vuorovaikutuksen vieritettävissä säilöissä—ihanteellinen dynaamisen datan lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Komponentti, joka näyttää hierarkkista dataa, sallien käyttäjien laajentaa, supistaa ja vuorovaikuttaa syvennettyjen kohteiden kanssa.</p>
  </GalleryCard>

  <GalleryCard header="Profiili" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Komponentti, joka näyttää käyttäjäprofiilikuvia tai alkukirjaimia, tukee erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownKatsoja" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Komponentti, joka näyttää markdown-sisältöä edistyksellisellä merkki merkiltä -renderöinnillä, ihanteellinen tekoäly-chat-käyttöliittymiin ja striimattavaan tekstiin.</p>
  </GalleryCard>

</GalleryGrid>
