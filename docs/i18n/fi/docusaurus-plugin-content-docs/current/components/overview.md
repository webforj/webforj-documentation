---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 5533b760c7585442c917bc506c2dd763
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

WebforJ:ssä sovelluksia rakennetaan modulaarisista yksiköistä, joita kutsutaan komponenteiksi, jotka mahdollistavat nopean ja tehokkaan käyttöliittymän kehittämisen. Kehys tarjoaa joukon olennaisia komponentteja, kuten painikkeita, syöte-elementtejä ja asettelukontteja. Perusasioiden hallinnan jälkeen voit tutustua [JavaDocs](https://javadoc.io/doc/com.webforj) -asiakirjaan saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnallisuuksista.

## Asettelu {#layouts}

Asettelukomponentit tarjoavat perustan käyttöliittymien rakenteelle, mahdollistaen kehittäjien järjestää sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestystä, olipa kyseessä yksinkertainen tai monimutkainen asettelu.

Seuraavat asettelukomponentit on suunniteltu käsittelemään laajaa valikoimaa käyttötapauksia, aina responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Konttikomponentti, joka tarjoaa rakenteellisen asettelun yläkerran sovelluksen navigaatiolle ja sisällön järjestämiselle.</p>
  </GalleryCard>

  <GalleryCard header="Työkalupalkki" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Vaakasuuntainen konttikomponentti, joka pitää koossa joukon toimintonäppäimiä, ikoneita tai muita ohjaimia, joita käytetään yleensä tehtävien suorittamiseen nykyisestä kontekstista riippuen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Asettelukomponentti, joka järjestää lapsensa joustavien laatikoiden (flexbox) sääntöjen mukaisesti responsiivista suunnittelua ja kohdistusta varten.</p>
  </GalleryCard>

  <GalleryCard header="SarakkeetLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Asettelukomponentti, joka järjestää lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukon kaltaisten rakenteiden luomiseen.</p>
  </GalleryCard>

  <GalleryCard header="Jakaja" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Asettelukomponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin kesken, mahdollistaen käyttäjien muuttaa niiden kokoa vetämällä jakolaitaa.</p>
  </GalleryCard>

  <GalleryCard header="Laatikko" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Liukuva paneelikomponentti, jota käytetään tyypillisesti sivunavigaatioon tai ylimääräisen sisällön vuokraamiseen, joka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Vuoropuhelu" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Modaalinen ikkuna, joka peittää sisällön näyttää tärkeää tietoa tai kehottaa käyttäjää vuorovaikutukseen, usein vaaditaan käyttäjän toimintaa sulkemiseen.</p>
  </GalleryCard>

  <GalleryCard header="Kirjautuminen" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Komponentti, joka tarjoaa valmiin käyttöliittymän käyttäjätodennukseen, tyypillisesti sisältäen kentät käyttäjänimeä ja salasanaa sekä lähetysnäppäimen.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Pystysuunnassa pinottu kokoelma taitettavia paneeleja, joista jokaisessa on napsautettava otsikko, joka vaihtaa sen sisällön näkyvyyttä.</p>
  </GalleryCard>

  <GalleryCard header="Välilehtipaneeli" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Konttikomponentti, joka järjestää sisällön useisiin välilehtiin, mahdollistaen käyttäjien vaihtaa eri näkymien tai osioiden välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttökomponentit tarjoavat olennaiset työkalut käyttäjätietojen tallentamiseen ja vuorovaikutusten hallintaan sovelluksessasi. Nämä komponentit ovat monipuolisia, mikä helpottaa vuorovaikutteisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="Tekstikenttä" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Yhdellä rivillä oleva syöttökomponentti tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Tekstikenttä" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Tekstisyöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai kaavaan, tyypillisesti käytettävä kentissä kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="NumeroKenttä" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen syöttökentän numeroarvojen syöttämiseen, sisäänrakennetuilla ohjaimilla arvon suurentamiseen tai pienentämiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>NumeroKenttä" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Numeraalinen syöttökomponentti, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai kaavaan, varmistaen kelvollisen numeroiden syötön, kuten valuutta, prosentit tai muut muotoillut numerot.</p>
  </GalleryCard>

  <GalleryCard header="SalasanaKenttä" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Yhdellä rivillä oleva syöttökomponentti salasanapoissa syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräKenttä" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärävalitsimen päivämäärän valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>PäivämääräKenttä" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Päivämääräsyöttökomponentti, joka pakottaa tietyn päivämäärämuodon tai -kaavan, varmistaen, että käyttäjä syöttää kelvollisen päivämäärän määritellyn maskin mukaisesti.</p>
  </GalleryCard>

  <GalleryCard header="AikaKenttä" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen aikavalitsimen aikavälin valitsemiseksi syöttökentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>AikaKenttä" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Aikasyöttökomponentti, joka pakottaa tietyn aikamuodon tai -kaavan, varmistaen, että käyttäjä syöttää kelvollisen ajan määritellyn maskin mukaisesti.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräAikaKenttä" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen päivämäärä- ja aikavalitsimen, jonka avulla voidaan valita sekä päivämäärä että aika yhdestä syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="VäriKenttä" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Komponentti, joka tarjoaa oletusarvoisen selainpohjaisen värivalitsimen, jonka avulla käyttäjät voivat valita värin syöttökentästä.</p>
  </GalleryCard>

  <GalleryCard header="Tekstialue" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Monirivinen tekstisyöttökomponentti, joka mahdollistaa käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Komponentti, joka edustaa binäärivaihtoehtoa, mahdollistaen käyttäjien vaihtaa valitun (tosi) tai valitsemattoman (epätosi) tilan välillä.</p>
  </GalleryCard>

  <GalleryCard header="Radiopainike" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Komponentti, joka mahdollistaa käyttäjien valita yhden vaihtoehdon keskinäisten valintojen joukosta.</p>
  </GalleryCard>

  <GalleryCard header="Kytkin" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Vaihtoehtokomponentti, joka mahdollistaa käyttäjille vaihtaa kahden tilan, kuten päällä/pois tai tosi/epätosi, välillä liukuvan toiminnon avulla.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Komponentti, joka tarjoaa pudotusvalikon ennalta määritetyistä vaihtoehdoista, mahdollistaen käyttäjien valita yhden vaihtoehdon luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="Yhdistelmälaatikko" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Komponentti, joka yhdistää pudotuslistan muokattavaan tekstisyöttöön, mahdollistaen käyttäjien joko valita vaihtoehdon listalta tai syöttää mukautetun arvon.</p>
  </GalleryCard>

  <GalleryCard header="Luettelo" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Komponentti, joka näyttää vieritettävän luettelon vaihtoehdoista, mahdollistaen käyttäjien valita yhden tai useamman kohteen luettelosta.</p>
  </GalleryCard>

  <GalleryCard header="Lataa" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Rivivaihtoehtokomponentti, joka mahdollistaa käyttäjien valita yhden tai useamman tiedoston omalta koneeltaan ja ladata ne palvelimelle, tukien drag-and-drop -toimintoa, suodattimia ja tiedostokohtaisia tapahtumaseurantaa.</p>
  </GalleryCard>
</GalleryGrid>

## Valintaikkunat {#option-dialogs}

Valintaikkunat tarjoavat tavan esittää käyttäjille vaihtoehtoja tai pyytää heitä vahvistamaan ennen toimenpiteen jatkamista. Nämä komponentit ovat olennaisia vuorovaikutteisten, päätöksentekoon perustuvien työnkulkujen luomiseksi, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita erilaisista vaihtoehdoista selkeällä ja rakenteellisella tavalla.

<GalleryGrid>
  <GalleryCard header="ViestiIkkuna" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Dialogikomponentti, jota käytetään esittämään käyttäjälle informatiivisia viestejä tai hälytyksiä, tyypillisesti yhdellä `OK` -painikkeella viestin vahvistamiseksi.</p>
  </GalleryCard>

  <GalleryCard header="VahvistusIkkuna" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Dialogikomponentti, joka kysyy käyttäjältä vahvistusta tai peruutusta toiminnolle, tyypillisesti tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` -painikkeita.</p>
  </GalleryCard>

  <GalleryCard header="SyöttöIkkuna" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Dialogikomponentti, joka kehottaa käyttäjää syöttämään tekstiä tai tietoa, tyypillisesti tarjoamalla syöttökentän sekä toimintopainikkeita kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="TiedostovalintaIkkuna" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonLatausIkkuna" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien ladata tiedostoja omasta tiedostojärjestelmästään sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonTallennusIkkuna" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjille tallentaa tiedoston tiettyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja esitys {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjän vuorovaikutuksia ja visuaalisesti näyttävät dataa tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, laukaisevat toimintoja ja ymmärtävät edistymistä tai tuloksia dynaamisten visuaalisten elementtien avulla.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Komponentti, jota käytetään näyttämään dataa rakenteellisessa, taulukoivassa muodossa riveillä ja sarakkeilla, tukien ominaisuuksia kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleDiagrammit" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Komponentti, joka integroituu Google Diagrammien kanssa näyttämään erilaisia kaavioita ja visuaalisia datan esityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Napsautettava komponentti, joka laukaisee toiminnon tai tapahtuman, kun sitä painetaan.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka lyhyesti näyttää viestin käyttäjälle ennen kuin se automaattisesti häviää.</p>
  </GalleryCard>

  <GalleryCard header="Ilmoitus" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia näkyvässä muodossa käyttäjän huomion kiinnittämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Merkki" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Pieni etiketti-komponentti, joka näyttää laskentaa, tiloja tai lyhyitä metatietoja, tukien teemoja, kokoja ja ikoneita.</p>
  </GalleryCard>

  <GalleryCard header="TyöpöytäIlmoitus" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Komponentti, joka hyödyntää selaimen natiivia Ilmoitus-API:a varoittaakseen käyttäjiä mukautetuilla työpöytäilmoituksilla.</p>
  </GalleryCard>

  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Mukautettava sivutuskomponentti datan läpikäymiseen, tukien asetteluja, joissa on ensimmäinen, viimeinen, seuraava, edellinen painiket ja nopeita hyppyjä.</p>
  </GalleryCard>

  <GalleryCard header="Edistyspalkki" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistymistä, tyypillisesti esitettynä vaakasuorana palkkina, joka täyttyy edistymisen myötä.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Komponentti, joka mahdollistaa käyttäjän valita arvon määritellyltä alueelta vetämällä kahvaa pitkin rataa.</p>
  </GalleryCard>

  <GalleryCard header="ToiminnassaOsa" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Sovelluksen laajuinen visuaalinen indikaattori, tyypillisesti pyörijä, joka osoittaa, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Lataus" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Skaalautuva latausindikaattori, joka näyttää tietyn vanhemman komponentin sisällä, osoittaen, että sisältöä tai dataa ladataan kyseisessä osassa.</p>
  </GalleryCard>

  <GalleryCard header="Pyörimisnäyttö" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Komponentti, joka näyttää pyörivän animaation, tyypillisesti käytetään osoittamaan, että prosessi tai toimenpide on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="SovellusNavigaatiO" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Komponentti, joka tarjoaa navigaatiovalikon sovellukselle, tyypillisesti käytetään linkkien tai navigointielementtien luetteloimiseen, jotta voidaan vaihtaa eri osioiden tai näkymien välillä.</p>
  </GalleryCard>

  <GalleryCard header="Ikoni" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein toimintojen, tilojen tai kategorioiden edustamiseen käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Pääte" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Komponentti, joka simuloi komentoriviliittymää (CLI) sovelluksessa, mahdollistaen käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>

  <GalleryCard header="PäättymätönVieritys" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Komponentti, joka lataa lisää kohteita vierittäessä, näyttää latauskuvakkeen ja seuraa, milloin kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Päivitys" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Komponentti, joka mahdollistaa vetämisen päivityksiin vieritettävissä säiliöissä — ihanteellinen dynaamiseen datan lataamiseen.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Komponentti, joka näyttää hierarkkista dataa, mahdollistaen käyttäjien laajentaa, supistaa ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>

  <GalleryCard header="Profiilikuva" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Komponentti, joka näyttää käyttäjän profiilikuvia tai alkukirjaimia, tukien erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownKatsoja" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Komponentti, joka näyttää markdown-sisältöä progressiivisella merkeittäin renderöinnillä, ihanteellinen AI-keskusteluliittymille ja virtaavalle tekstille.</p>
  </GalleryCard>
</GalleryGrid>
