---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: c463da47b9db7f6619c8723b6105f9bd
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

WebforJ:ssä sovelluksia luodaan moduuliyksiköistä, joita kutsutaan komponenteiksi, jotka helpottavat nopeaa ja tehokasta käyttöliittymäkehitystä. Kehys tarjoaa joukon välttämättömiä komponentteja, kuten painikkeita, syöte-elementtejä ja asettelukontteja. Perusteiden hallinnan jälkeen voit tutustua [JavaDocs](https://javadoc.io/doc/com.webforj) -sivustoon saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnallisuuksista.

## Asettelu {#layouts}

Asettelu-komponentit tarjoavat perustan käyttöliittymien jäsentämiselle, mahdollistaen kehittäjien organisoida sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestystä, yksinkertaisista monimutkaisiin asetteluisiin.

Seuraavat asettelu-komponentit on suunniteltu käsittelemään laajaa valikoimaa käyttötarkoituksia, responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Sisäkkäiskomponentti, joka tarjoaa rakenteellisen asettelun ylimmän tason sovelluksen navigaatiolle ja sisällön organisoinnille.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Vaakasuuntainen kontti, joka sisältää joukon toimintopainikkeita, ikoneita tai muita valvontaelementtejä, joita käytetään tyypillisesti nykyiseen kontekstiin liittyvien tehtävien suorittamiseen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Komponentti, joka järjestää lapset joustavien laatikoiden (flexbox) sääntöjen käyttöön responsiivista suunnittelua ja kohdistamista varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Asettelu-komponentti, joka asettaa lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukkomaisten rakenteiden luomiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Asettelu-komponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin välillä, jolloin käyttäjät voivat muuttaa niiden kokoa vetämällä jakoväliä.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Liuku paneli-komponentti, jota käytetään tyypillisesti sivunavigaation tai lisäsisällön näyttämiseen tai piilottamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Modaalinen ikkuna-komponentti, joka peittää sisällön näyttääksesi tärkeää tietoa tai kehottaaksesi käyttäjää vuorovaikutukseen, usein vaaditaan käyttäjän toimintaa sulkemiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Komponentti, joka tarjoaa valmiiksi rakennetun käyttöliittymän käyttäjätodennukseen, tyypillisesti mukaan lukien kentät käyttäjänimen ja salasanan syöttämiseksi yhdessä lähetyspainikkeen kanssa.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Kontti-komponentti, joka järjestää sisällön useisiin välilehtiin, jolloin käyttäjät voivat vaihtaa eri näkymien tai osien välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttö-komponentit tarjoavat olennaisia työkaluja käyttäjän syötteen tallentamiseen ja vuorovaikutusten hallintaan sovelluksessasi. Nämä komponentit ovat monikäyttöisiä, mikä helpottaa interaktiivisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Yhden rivin syötekomponentti tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Tekstisyötekomponentti, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai kaavaan, tyypillisesti käytetään kentissä kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen syötteen numeron syöttämistä varten, mukana sisäänrakennetut ohjaimet arvon lisäämiseksi tai vähentämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Numerosyötekomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai kaavaan, varmistaen kelvollisen numerotietoisuuden, kuten valuutoille, prosentteille tai muille muotoilluille numeroille.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Yhden rivin syötekomponentti salasanatietojen turvalliseen syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärävalitsimen päivämäärän valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Päivämääräsyötekomponentti, joka pakottaa tiettyyn päivämäärämuotoon tai kaavaan, varmistaen, että käyttäjä syöttää kelvollisen päivämäärän määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen aikavalitsimen aikavälin valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Aikasyötekomponentti, joka pakottaa tiettyyn aikamuotoon tai kaavaan, varmistaen, että käyttäjä syöttää kelvollisen ajan määritellyn maskin mukaan.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärä- ja aikavalitsimen, jonka avulla voidaan valita sekä päivämäärä että aika yhdestä syötekentästä.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen väri-valitsimen, joka sallii käyttäjien valita värin syötekentästä.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Monirivinen tekstisyötekomponentti, joka sallii käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Komponentti, joka edustaa binääristä vaihtoehtoa, sallien käyttäjien vaihtaa valitun (totta) tai valitsemattoman (epätosi) tilan välillä.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Komponentti, joka sallii käyttäjien valita yhden vaihtoehdon joukosta keskenään poissulkevia vaihtoehtoja.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Vaihtokytkin, joka sallii käyttäjille vaihtaa kahden tilan, kuten päälle/poissa tai totta/epätosi, välillä liukuvalla toiminnalla.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Komponentti, joka tarjoaa valmiiksi määriteltyjen vaihtoehtojen alaspäin avautuvan luettelon, jonka avulla käyttäjät voivat valita yhden vaihtoehdon luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Komponentti, joka yhdistää alaspäin avautuvan luettelon muokattavan tekstisyötteen kanssa, jolloin käyttäjät voivat joko valita vaihtoehdon luettelosta tai syöttää oman arvonsa.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Komponentti, joka näyttäytyy vieritettävänä luettelona, sallien käyttäjien valita yhden tai useamman kohteen luettelosta.</p>
  </GalleryCard>
</GalleryGrid>

## Valintaikkunat {#option-dialogs}

Valintaikkunat tarjoavat tavan esittää käyttäjille vaihtoehtoja tai pyytää heiltä vahvistusta ennen toiminnan jatkamista. Nämä komponentit ovat olennaisia interaktiivisten, päätöksentekoon perustuvien työnkulkujen luomiseksi, mahdollistaen käyttäjien vahvistavan, peruvan tai valitsevan eri vaihtoehdoista selkeässä ja rakenteellisessa muodossa.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Dialog-komponentti, jota käytetään näyttämään käyttäjälle tietosuojailmoituksia tai varoituksia, tyypillisesti yhden `OK`-painikkeen kanssa viestin tunnustamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Dialog-komponentti, joka kysyy käyttäjältä vahvistusta tai peruutusta toiminnolle, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` painikkeet.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Dialog-komponentti, joka kehottaa käyttäjää syöttämään tekstiä tai tietoa, tyypillisesti tarjoamalla syötekentän sekä toiminta-painikkeet kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Dialog-komponentti, joka sallii käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Dialog-komponentti, joka mahdollistaa käyttäjien ladata tiedostoja paikalliselta tiedostojärjestelmältä sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Dialog-komponentti, joka sallii käyttäjien tallentaa tiedoston määritettyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutusta ja näyttävät visuaalisesti tietoa tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, laukaisevat toimintoja ja ymmärtämään edistystä tai tuloksia dynaamisten visuaalisten elementtien kautta.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Komponentti, jota käytetään tietojen näyttämiseen rakenteellisessa, taulukkomuotoisessa muodossa riveineen ja sarakkeineen, tukee ominaisuuksia kuten lajittelua ja sivujen jakamista.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Komponentti, joka integroituu Google Charts -palveluun näyttämään erilaisia kaavioita ja visuaalisia tietoesityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Painettava komponentti, joka laukaisee toiminnon tai tapahtuman painettaessa.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>Kevyt, ei-blokkaaava ilmoituskomponentti, joka näyttäytyy lyhyesti viestinä käyttäjälle, ennen kuin se häviää automaattisesti.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia huomattavassa muodossa käyttäjän huomion herättämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Komponentti, joka hyödyntää selaimen natviivia ilmoitus-APIa varoittaakseen käyttäjiä mukautetuilla työpöytäilmoituksilla.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Muokattava sivutuksen komponentti, joka mahdollistaa navigoinnin tietojoukoissa, tukee asetteluja, joissa on ensimmäiset, viimeiset, seuraavat, edelliset painikkeet ja nopea hyppy kentät.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistymistä, tyypillisesti esitettynä vaakasuorana palkkina, joka täyttyy edistymisen mukaan.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Komponentti, joka sallii käyttäjien valita arvon määritellyltä arvoalueelta vetämällä kahvaa radan pitkin.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörivä, joka osoittaa, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Rajattu latausindikaattori, joka näkyy tietyssä vanhempikomponentissa, osoittaen, että sisältöä tai tietoa ladataan kyseisessä osassa.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään osoittamaan, että prosessi tai toiminto on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Komponentti, joka tarjoaa navigointivalikon sovellukselle, tyypillisesti käytetään linkkien tai navigointikohteiden luetteloimiseen eri osien tai näkymien vaihtamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein hyvin toimeksi, tilan tai kategorian edustamiseen käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Komponentti, joka simuloi komentoriviliittymää (CLI) sovelluksessa, mahdollistaen käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Komponentti, joka lataa lisää kohteita vieritettäessä, näyttää lataajaa ja seuraa, kun kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Komponentti, joka mahdollistaa vetämään päivityksen vuorovaikutuksen vieritettävissä konteissa—erinomainen dynaamiseen tietojen lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Komponentti hierarkisten tietojen näyttämiseen, jolloin käyttäjät voivat laajentaa, supistaa ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
</GalleryGrid>
