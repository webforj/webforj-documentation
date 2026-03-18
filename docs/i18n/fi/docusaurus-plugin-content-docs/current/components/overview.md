---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 87c3a95cb124fa9d0468e7cfd5d9d8ac
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-komponentit | Käyttöliittymäsovellusten rakennuskomponentit</title>
</Head>

In webforJ, sovelluksia luodaan moduulimaisilla yksiköillä, joita kutsutaan Komponenteiksi, mikä helpottaa nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehys tarjoaa joukon olennaisia komponentteja, kuten painikkeita, syöte-elementtejä ja asettelu-säiliöitä. Perusteiden hallinnan jälkeen voit tarkastella [JavaDocs](https://javadoc.io/doc/com.webforj) saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnallisuudesta.

## Asettelu {#layouts}

Asettelu-komponentit tarjoavat perustan käyttöliittymien rakentamiselle, mahdollistaenkehittäjien organisoida sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestelyä, olipa kyseessä yksinkertaiset tai monimutkaiset asettelut.

Seuraavat asettelukomponentit on suunniteltu käsittelemään laajaa skaalaa käyttötarkoituksia, responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Säilökomponentti, joka tarjoaa rakenteellisen asettelun ylimmäiselle sovelluksen navigoinnille ja sisällön järjestelylle.</p>
  </GalleryCard>

  <GalleryCard header="Työkalupalkki" href="toolbar" image="/img/components/Toolbar.png">
    <p>Vaakasuora säilökomponentti, joka pitää sisällään joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia, joita käytetään tyypillisesti nykyiseen kontekstiin liittyvien tehtävien suorittamiseen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Asettelu-komponentti, joka järjestää lapsensa käyttäen joustavan laatikon (flexbox) sääntöjä responsiivista suunnittelua ja kohdistamista varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Asettelu-komponentti, joka järjestää lapsensa useisiin pystysuuntiin sarakkeisiin, mikä on hyödyllistä lomakkeiden ja ruudukkomaisen rakenteen luomisessa.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Asettelu-komponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin välillä, mahdollistaen käyttäjien muuttaa niiden kokoa vetämällä jakotankoa.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Liukuva paneelikomponentti, jota käytetään tyypillisesti sivu-navigaatioon tai lisäsisällön säilyttämiseen, joka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön tärkeän tiedon näyttämiseksi tai käyttäjän vuorovaikutuksen pyytämiseksi, usein vaaditaan käyttäjän toiminta sulkemiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Komponentti, joka tarjoaa esivalmistellun käyttöliittymän käyttäjän todennukseen, tyypillisesti sisältäen kentät käyttäjänimen ja salasanan syöttämiseen sekä lähetyspainikkeen.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/Accordion.png">
    <p>Pystyyn pinottu kokoelma romautuvia paneeleita, joista jokaisella on napsautettavissa oleva otsikko, joka vaihtaa näkyvyyden sen sisällön välillä.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Säilökomponentti, joka järjestää sisällön useisiin välilehtiin, mahdollistaen käyttäjien vaihtaa eri näkymien tai osien välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttökomponentit tarjoavat olennaisia työkaluja käyttäjän syötteen tallentamiseen ja vuorovaikutusten hallintaan sovelluksessasi. Nämä komponentit ovat monipuolisia, mikä helpottaa interaktiivisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Yhden rivin syötekenttä tekstin syöttämistä ja muokkaamista varten.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Tekstisyötekomponentti, joka rajoittaa käyttäjän syötettä tiettyyn muotoon tai kaavaan, tyypillisesti käytetään kentissä kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Komponentti, joka tarjoaa oletusSelainpohjaisen syötekentän numeeristen arvojen syöttämiseen, built-in-ohjauksilla arvon nostamista tai alenemista varten.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Numeraalinen syötekomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai kaavaan, varmistaen kelvollisten numeroiden syöttämisen, kuten valuutan, prosentit tai muut muotoillut numerot.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Yhden rivin syötekenttä salasanan turvalliseen syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Komponentti, joka tarjoaa oletusSelainpohjaisen päivämäärävalitsimen päivämäärän valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Päivämääräsyötekomponentti, joka pakottaa tietyn päivämäärämuodon tai kaavan, varmistaen, että käyttäjä syöttää kelvollisen päivämäärän määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Komponentti, joka tarjoaa oletusSelainpohjaisen aikavalitsimen ajan valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Aikasyötekomponentti, joka pakottaa tietyn aikamuodon tai kaavan, varmistaen, että käyttäjä syöttää kelvollisen ajan määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Komponentti, joka tarjoaa oletusSelainpohjaisen päivämäärä- ja aikavalitsimen päivämäärän ja ajan valitsemiseksi yhdellä syötekentällä.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Komponentti, joka tarjoaa oletusSelainpohjaisen värivalitsimen, joka mahdollistaa käyttäjien valita värin syötekentästä.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Monta riviä syötekenttä, joka mahdollistaa käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Komponentti, joka edustaa binääristä vaihtoehtoa, sallien käyttäjien vaihtaa valittujen (totta) tai valitsemattomien (epätosi) tilojen välillä.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Komponentti, joka sallii käyttäjien valita yhden vaihtoehdon ryhmästä keskenään poissulkevia valintoja.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Vaihtokomponentti, joka sallii käyttäjien vaihtaa kahden tilan välillä, kuten päällä/pois tai totta/epätosi, liukuvan toiminnan avulla.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Komponentti, joka tarjoaa avattavan luettelon ennalta määritellyistä vaihtoehdoista, mahdollistaen käyttäjien valita yksi vaihtoehto luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Komponentti, joka yhdistää avattavan luettelon muokattavaan tekstisyötteeseen, sallien käyttäjien joko valita vaihtoehto luettelosta tai syöttää mukautettu arvo.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Komponentti, joka näyttää vierittävän listan vaihtoehdoista, mahdollistaen käyttäjien valita yksi tai useampi kohde luettelosta.</p>
  </GalleryCard>
</GalleryGrid>

## Valintaikkunat {#option-dialogs}

Valintaikkunat tarjoavat tavan esittää käyttäjille valintoja tai pyytää heiltä vahvistusta ennen toimintaan ryhtymistä. Nämä komponentit ovat olennaisia interaktiivisten, päätöksentekoon perustuvien työnkulkujen luomisessa, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita eri vaihtoehdoista selkeästi ja rakenteellisesti.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Dialogikomponentti, jota käytetään esittämään informatiivisia viestejä tai varoituksia käyttäjälle, tyypillisesti yhdellä `OK`-painikkeella viestin tunnustamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Dialogikomponentti, joka kysyy käyttäjältä vahvistusta tai peruuttamista toiminnolle, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` painikkeet.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Dialogikomponentti, joka pyytää käyttäjältä tekstin tai tiedon syöttämistä, tyypillisesti tarjoamalla syötekentän sekä toimintopainikkeet kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien ladata tiedostoja paikallisesta tiedostojärjestelmästään sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien tallentaa tiedoston määriteltyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutuksia ja näyttävät visuaalisesti tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, käynnistämään toimintoja ja ymmärtämään edistystä tai tuloksia dynaamisten visuaalisten elementtien avulla.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/Table.png">
    <p>Komponentti, jota käytetään tietojen näyttämiseen rakenteellisessa, taulukkomuotoisessa muodossa riveillä ja sarakkeilla, tukee toimintoja kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Komponentti, joka integroituu Google Chartsiin esittämään erilaisia ​​kaavioita ja visuaalisia tietoanalyysimuotoja sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/Button.png">
    <p>Napsautettava komponentti, joka käynnistää toiminnon tai tapahtuman, kun sitä painetaan.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka näyttää hetkellisesti viestin käyttäjälle ennen automaattista häipymistä.</p>
  </GalleryCard>

  <GalleryCard header="Ilmoitus" href="alert" image="/img/components/Alert.png">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia huomattavassa muodossa käyttäjän huomion kiinnittämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/Badge.png">
    <p>Pieni etiketti-komponentti, joka näyttää laskentamääriä, tiloja tai lyhyitä metatietoja, tukee teemoja, kokoja ja kuvakkeita.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Komponentti, joka käyttää selaimen natiivia Ilmoitus-API:ta varoittaakseen käyttäjiä mukautetuilla työpöytäilmoituksilla.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/Navigator.png">
    <p>Mukautettava sivutuskomponentti tietoaineistojen navigointiin, tukee asetteluja, joissa on ensimmäinen, viimeinen, seuraava, edellinen painike ja nopea hyppykenttä.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Komponentti, joka näytää tehtävän tai prosessin edistymisen visuaalisesti, tyypillisesti esitetty vaakasuuntaisena palkkina, joka täyttyy edistymisen myötä.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/Slider.png">
    <p>Komponentti, joka sallii käyttäjien valita arvon määritellyltä alueelta vetämällä kahvaa pitkin rataa.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p> Sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörivä, joka signaloi, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Rajattu latausindikaattori, joka näyttää tietyssä päärunkoelimestä, osoittaen, että sisältö tai data ladataan siinä osiossa.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään ilmoittamaan, että prosessi tai toiminta on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Komponentti, joka tarjoaa navigointimenun sovellukselle, tyypillisesti käytetään listataksesi linkkejä tai navigointikohteita eri osioiden tai näkymien välillä.</p>
  </GalleryCard>

  <GalleryCard header="Ikoni" href="icon" image="/img/components/Icons.png">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein edustamaan toimintaa, tilaa tai kategoriaa käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Pääte" href="terminal" image="/img/components/Terminal.png">
    <p>Komponentti, joka simuloisi komentoriviliittymää (CLI) sovelluksessa, mahdollistaen käyttäjien syöttää ja suorittaa tekstipohjaisia ​​komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Komponentti, joka lataa lisää kohteita vieritettäessä, näyttää lataustilan ja seuraa, kun kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Komponentti, joka mahdollistaa vetämällä päivitysvuorovaikutuksen vierivissä säiliöissä—ihanteellinen dynaamiseen tietojen lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/Tree.png">
    <p>Komponentti hierarkkisten tietojen näyttämiseen, joka antaa käyttäjille mahdollisuuden laajentaa, tiivistää ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Komponentti käyttäjäprofiilikuvien tai initialien näyttämiseen, tukee erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/MarkdownViewer.png">
    <p>Komponentti markdown-sisällön näyttämiseen progressiivisella merkki merkkiltä rendering, ideaalinen AI-keskusteluissa ja striimaavassa tekstissä.</p>
  </GalleryCard>
  
</GalleryGrid>
