---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: f66c1b418ace12cb1945ab33fd272362
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI Components | Käyttöliittymä Sovellusrakentamisen Komponentit</title>
</Head>

In webforJ, sovelluksia luodaan modulaaristen yksiköiden, joita kutsutaan Komponenteiksi, avulla, mikä helpottaa nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehys tarjoaa kattavan valikoiman olennaisia komponentteja, kuten painikkeita, syöteelementtejä ja asettelukontteja. Perusteiden hallinnan jälkeen voit konsultoida [JavaDocs](https://javadoc.io/doc/com.webforj) saadaksesi yksityiskohtaisen yleiskuvan kaikista komponenteista ja niiden toiminnallisuuksista.

## Asettelu {#layouts}

Asettelu komponentit tarjoavat perustan käyttöliittymien jäsentämiselle, mahdollistaen kehittäjien organisoida sisältö tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestystä, olipa kyseessä yksinkertaiset tai monimutkaiset asettelut.

Seuraavat asettelu komponentit on suunniteltu käsittelemään laaja valikoima käyttötapauksia, responsiivisesta suunnittelusta edistyneeseen sisällön hallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Konteinerikomponentti, joka tarjoaa rakenteellisen asettelun huipputason sovelluksen navigaatiolle ja sisällön organisoinnille.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Vaakasuuntainen konteinerikomponentti, joka pitää sisällään joukon toimintopainikkeita, kuvakkeita tai muita ohjausvälineitä, joita käytetään tyypillisesti nykyisen kontekstiin liittyvien tehtävien suorittamiseen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Asettelu komponentti, joka järjestää lapsensa joustavan laatikon (flexbox) sääntöjen avulla responsiivista suunnittelua ja kohdistamista varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Asettelu komponentti, joka järjestää lapsensa useaan pystysuoraan sarakkeeseen, hyödyllinen lomakkeiden ja ruudukkomaisen rakenteen luomisessa.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Asettelu komponentti, joka jakaa saatavilla olevan tilan kahden lapsikomponentin välillä, mahdollistaen käyttäjien muuttaa niiden kokoa vetämällä jakajapalkkia.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Liikkuva paneelikomponentti, jota käytetään tyypillisesti sivunavigointiin tai ylimääräisten sisältöjen säilyttämiseen, jotka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön näyttääkseen tärkeitä tietoja tai pyytääkseen käyttäjän vuorovaikutusta, usein edellyttäen käyttäjän toimintaa sulkemiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Komponentti, joka tarjoaa valmiin käyttöliittymän käyttäjätunnistukseen, tyypillisesti sisältäen kentät käyttäjänimelle ja salasanalle yhdessä lähetyspainikkeen kanssa.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Konteinerikomponentti, joka järjestää sisällön useisiin välilehtiin, mahdollistaen käyttäjien vaihtaa eri näkymien tai osioiden välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttö komponentit tarjoavat olennaisia työkaluja käyttäjän syötteen tallentamiseen ja vuorovaikutuksen hallintaan sovelluksessasi. Nämä komponentit ovat monipuolisia, mikä helpottaa interaktiivisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Yksirivinen syötekomponentti tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Tekstisyötekomponentti, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai kuvioon, jota käytetään tyypillisesti kentissä kuten puhelinnumerot, päivämäärät tai luottokortinumerot.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Komponentti, joka tarjoaa selainpohjaisen syötekentän numeeristen arvojen syöttämiseen, mukana sisäänrakennetut ohjausvälineet arvon lisäämiseen tai vähentämiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Numeerinen syötekomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai kuvioon, varmistaen kelvollisen numero syötteen, kuten valuuttasi, prosentit tai muut muotoillut numerot.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Yksirivinen syötekomponentti salasanojen turvalliseen syöttämiseen ja salaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Komponentti, joka tarjoaa selainpohjaisen päivämäärävalitsimen päivämäärän valitsemiseen syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Päivämääräsyötekomponentti, joka pakottaa tiettyyn päivämäärämuotoon tai kuvioon, varmistaen että käyttäjä syöttää kelvollisen päivämäärän määritellyn maskin mukaisesti.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Komponentti, joka tarjoaa selainpohjaisen aikavalitsimen ajan valitsemiseen syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Aikasyötekomponentti, joka pakottaa tiettyyn aikamuotoon tai kuvioon, varmistaen että käyttäjä syöttää kelvollisen ajan määritellyn maskin mukaisesti.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Komponentti, joka tarjoaa selainpohjaisen päivämäärän ja ajan valitsimen sekä päivämäärän että ajan valitsemiseen yhdellä syötekentällä.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Komponentti, joka tarjoaa selainpohjaisen värivalitsimen, mahdollistaen käyttäjien valita värin syötekentästä.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Monirivinen tekstisyötekomponentti, joka mahdollistaa käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Komponentti, joka edustaa binaarivaihtoehtoa, mahdollistaen käyttäjien vaihtaa tarkastetun (totta) tai tarkastamattoman (epätosi) tilan välillä.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Komponentti, joka mahdollistaa käyttäjien valita yhden vaihtoehdon eristyksissä olevasta valintaryhmästä.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Vaihtokomponentti, joka mahdollistaa käyttäjien vaihtaa kahden tilan, kuten päällä/pois tai totta/epätosi, välillä liukuvalla toiminnalla.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Komponentti, joka tarjoaa alasvetoluettelon ennakkoon määritellyistä vaihtoehdoista, mahdollistaen käyttäjien valita yksi vaihtoehto luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Komponentti, joka yhdistää alasvetoluettelon ja muokattavan tekstisyötteen, mahdollistaen käyttäjien joko valita vaihtoehdon luettelosta tai syöttää mukautetun arvon.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Komponentti, joka näyttää vieritettävän luettelon vaihtoehdoista, mahdollistaen käyttäjien valita yhden tai useamman kohteen luettelosta.</p>
  </GalleryCard>
</GalleryGrid>

## Valintadialogit {#option-dialogs}

Valintadialogit tarjoavat tavan esittää käyttäjille valintoja tai pyytää heiltä vahvistusta ennen toiminnan suorittamista. Nämä komponentit ovat olennaisia interaktiivisten, päätöksenteon mukautettujen työnkulkujen luomiseksi, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita erilaisista vaihtoehdoista selkeästi ja jäsennellysti.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Dialogikomponentti, jota käytetään näyttämään informatiivisia viestejä tai hälytyksiä käyttäjälle, tyypillisesti yhdellä `OK`-painikkeella viestin tunnustamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Dialogikomponentti, joka kysyy käyttäjältä vahvistusta tai peruutusta toiminnolle, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` -painikkeet.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Dialogikomponentti, joka pyytää käyttäjää syöttämään tekstiä tai tietoja, tyypillisesti tarjoamalla syötekentän sekä toimintopainikkeet kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien ladata tiedostoja omalta tiedostojärjestelmältään sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille tallentaa tiedoston määriteltyyn sijaintiin palvelimen tiedostojärjestelmään.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutuksia ja visuaalisesti näyttävät tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, käynnistämään toimintoja ja ymmärtämään etenemistä tai tuloksia dynaamisten visuaalisten elementtien avulla.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Komponentti, jota käytetään tietojen näyttämiseen rakenteellisessa, taulukkomuotoisessa muodossa riveillä ja sarakkeilla, tukien ominaisuuksia kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Komponentti, joka integroituu Google Chartsiin esittämään erilaisia kaavioita ja visuaalisia tietoesityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Napsautettava komponentti, joka käynnistää toiminnon tai tapahtuman, kun sitä painetaan.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka näyttää viestin käyttäjälle lyhyesti ennen kuin se häviää automaattisesti.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia huomattavassa muodossa, joka houkuttelee käyttäjän huomiota.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Komponentti, joka hyödyntää selaimen natiivin Ilmoitus API:n ilmoittaakseen käyttäjille mukautetuista työpöytäilmoituksista.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Mukautettava sivutuskomponentti tietoaineistojen navigointiin, tukien asetteluja, joissa on ensimmäinen, viimeinen, seuraava, edellinen painikkeet ja pikahyppy kentät.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistymistä, yleensä esitetään vaakasuorana nauhana, joka täyttyy edistymisen myötä.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Komponentti, joka mahdollistaa käyttäjien valita arvon määritellyltä alueelta vetämällä kahvaa radan varrella.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörivä, joka signaloi että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Rajoitettu latausindikaattori, joka näyttää tietyssä vanhemmassa komponentissa, indikoi että sisältöä tai tietoja ladataan kyseisessä osiossa.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Komponentti, joka näyttää pyörivän animaation, jota käytetään tyypillisesti osoittamaan, että prosessi tai toiminto on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Komponentti, joka tarjoaa navigaatiovalikon sovellukselle, tyypillisesti käytetään linkkien tai navigointikohteiden luetteloimiseen erilaisten osioiden tai näkymien välillä siirtymiseen.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein toiminnan, tilan tai kategorian esittämään käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Komponentti, joka simuloi komentorivikäyttöliittymää (CLI) sovelluksessa, mahdollistaen käyttäjien syöttää ja suorittaa tekstiin perustuvia komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Komponentti, joka lataa lisää kohteita vierittäessä, näyttää latausilmoituksia ja seuraa, kun kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Komponentti, joka mahdollistaa vetämällä päivittää vuorovaikutuksen vieritettävissä konteissa—ihanteellinen dynaamiseen datan lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Komponentti hierarkkisten tietojen näyttämiseen, mahdollistaen käyttäjien laajentaa, supistaa ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
</GalleryGrid>
