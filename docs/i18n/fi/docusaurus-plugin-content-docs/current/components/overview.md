---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 80950952d9226a7a35503663c4155da7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-komponentit | Käyttöliittymä Sovellusrakentamisen Komponentit</title>
</Head>

In webforJ, sovelluksia luodaan modulaaristen yksiköiden, eli komponenttien, avulla, jotka helpottavat nopeaa ja tehokasta käyttöliittymän kehittämistä. Kehys tarjoaa joukon olennaisia komponentteja, kuten painikkeita, syötekenttiä ja asettelukontteja. Perusteiden hallinnan jälkeen voit tutustua [JavaDocs](https://javadoc.io/doc/com.webforj) -sivustoon saadaksesi yksityiskohtaisen yleiskatsauksen kaikista komponenteista ja niiden toiminnoista.

## Asettelu {#layouts}

Asettelukomponentit tarjoavat perustan käyttöliittymien rakenteelle, mahdollistaen kehittäjien järjestää sisältöä tehokkaasti. Nämä komponentit tarjoavat erilaisia tapoja hallita lapsikomponenttien järjestystä, olipa kyseessä yksinkertainen tai monimutkainen asettelu.

Seuraavat asettelukomponentit on suunniteltu käsittelemään laaja valikoima käyttötapauksia, responsiivisesta suunnittelusta edistyneeseen sisällönhallintaan.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Konttikomponentti, joka tarjoaa rakenteellisen asettelun ensisijaiselle sovelluksen navigoinnille ja sisällön järjestämiselle.</p>
  </GalleryCard>

  <GalleryCard header="Työkalupalkki" href="toolbar" image="/img/components/Toolbar.png">
    <p>Vaakasuuntainen konttikomponentti, joka sisältää joukon toimintopainikkeita, kuvakkeita tai muita ohjaimia, joita käytetään yleensä suorittamaan tehtäviä nykyisessä kontekstissa.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Asettelukomponentti, joka järjestää lapsensa joustavan laatikon (flexbox) sääntöjen mukaan responsiivista suunnittelua ja kohdistamista varten.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Asettelukomponentti, joka järjestää lapsensa useisiin pystysuoriin sarakkeisiin, hyödyllinen lomakkeiden ja ruudukkojen kaltaisten rakenteiden luomiseen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Asettelukomponentti, joka jakaa käytettävissä olevan tilan kahden lapsikomponentin välillä, mikä sallii käyttäjien muuttaa niiden kokoa vetämällä jakopalkkia.</p>
  </GalleryCard>

  <GalleryCard header="Piirros" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Liukuva paneelikomponentti, jota käytetään yleensä sivunavigaatiossa tai lisäsisällön majoittamisessa, joka voidaan näyttää tai piilottaa.</p>
  </GalleryCard>

  <GalleryCard header="Dialogi" href="dialog" image="/img/components/Dialog.png">
    <p>Modaalinen ikkunakomponentti, joka peittää sisällön näyttääkseen tärkeitä tietoja tai pyytääkseen käyttäjän vuorovaikutusta, usein vaaditaan käyttäjän toimintoa sulkemiseksi.</p>
  </GalleryCard>

  <GalleryCard header="Kirjautuminen" href="login" image="/img/components/Login.png">
    <p>Komponentti, joka tarjoaa valmiiksi rakennettua käyttöliittymää käyttäjäautentikointia varten, sisältäen yleensä kentät käyttäjänimelle ja salasanalle sekä lähetyspainikkeen.</p>
  </GalleryCard>

  <GalleryCard header="TabulaarinenPaneeli" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Konttikomponentti, joka järjestää sisällön useisiin välilehtiin, mahdollistaen käyttäjien siirtyä eri näkymien tai osien välillä.</p>
  </GalleryCard>
</GalleryGrid>

## Tietojen syöttö {#data-entry}

Tietojen syöttökomponentit tarjoavat olennaisia työkaluja käyttäjän syötteen kaappaamiseen ja vuorovaikutusten hallintaan sovelluksessasi. Nämä komponentit ovat monipuolisia, mikä helpottaa interaktiivisten lomakkeiden rakentamista ja erilaisten tietotyyppien keräämistä.

<GalleryGrid>
  <GalleryCard header="Tekstikenttä" href="fields/textfield" image="/img/components/TextField.png">
    <p>Yhden rivin syötekenttä tekstidatan syöttämiseen ja muokkaamiseen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Tekstikenttä" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Tekstisyötekenttä, joka rajoittaa käyttäjän syötteen tiettyyn muotoon tai malliin, käytetään yleensä kentissä kuten puhelinnumerot, päivämäärät tai luottokorttinumerot.</p>
  </GalleryCard>

  <GalleryCard header="Numerokenttä" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Komponentti, joka tarjoaa oletusselaimen syötekentän numerovarojen syöttämiseen, sisäänrakennetuilla ohjaimilla arvon lisäämiseksi tai vähentämiseksi.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Numerokenttä" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Numeroidun syötekenttä, joka rajoittaa käyttäjän syötteen tiettyyn numeeriseen muotoon tai malliin, varmistaen kelvollisen numeron syötön, kuten valuutalle, prosenteille tai muille muotoilluille numeroille.</p>
  </GalleryCard>

  <GalleryCard header="Salasanakenttä" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Yhden rivin syötekenttä salasanadatan turvalliseen syöttämiseen ja peittämiseen.</p>
  </GalleryCard>

  <GalleryCard header="Päivämääräkenttä" href="fields/datefield" image="/img/components/DateField.png">
    <p>Komponentti, joka tarjoaa oletusselaimen päivämäärävalitsimen päivämäärän valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Päivämääräkenttä" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Päivämääräsyötekenttä, joka pakottaa tiettyyn päivämäärämuotoon tai malliin, varmistaen, että käyttäjä syöttää kelvollisen päivämäärän määriteltyjen maskien mukaan.</p>
  </GalleryCard>

  <GalleryCard header="Aikakenttä" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Komponentti, joka tarjoaa oletusselaimen aikavalitsimen ajan arvon valitsemiseksi syötekentän kautta.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Maskattu</span>Aikakenttä" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Aikasyötekenttä, joka pakottaa tiettyyn aikamuotoon tai malliin, varmistaen, että käyttäjä syöttää kelvollisen ajan määriteltyjen maskien mukaan.</p>
  </GalleryCard>

  <GalleryCard header="PäivämääräAikakenttä" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Komponentti, joka tarjoaa oletusselaimen päivämäärä- ja aikavalitsimen sekä päivämäärän että ajan valitsemiseksi yhdellä syötekentällä.</p>
  </GalleryCard>

  <GalleryCard header="VäriKenttä" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Komponentti, joka tarjoaa oletusselaimen väriä valitsevan työkalun, antaen käyttäjien valita väri syötekentästä.</p>
  </GalleryCard>

  <GalleryCard header="Tekstialue" href="textarea" image="/img/components/TextArea.png">
    <p>Monirivinen tekstisyötekenttä, joka sallii käyttäjien syöttää tai muokata suurempia tekstikappaleita.</p>
  </GalleryCard>

  <GalleryCard header="Ruutu" href="checkbox" image="/img/components/CheckBox.png">
    <p>Komponentti, joka edustaa binäärivaihtoehtoa, mahdollistaen käyttäjien vaihtaa valittua (totta) tai valitsematonta (epätotta) tilaa.</p>
  </GalleryCard>

  <GalleryCard header="Radiopainike" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Komponentti, joka sallii käyttäjien valita yhden vaihtoehdon joukosta keskenään poissulkevista valinnoista.</p>
  </GalleryCard>

  <GalleryCard header="Kytkin" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Vaihteen komponentti, joka sallii käyttäjien siirtyä kahden tilan, kuten päällä/pois tai totta/epätotta, välillä liukusäätimen avulla.</p>
  </GalleryCard>

  <GalleryCard header="Valintaruutu" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Komponentti, joka tarjoaa alasvetovalikon ennalta määrättyjä vaihtoehtoja, mahdollistaen käyttäjien valita yhden vaihtoehdon listalta.</p>
  </GalleryCard>

  <GalleryCard header="Yhdistelmäruutu" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Komponentti, joka yhdistää alasvetovalikon muokattavaan tekstisyötekenttään, mahdollistaen käyttäjien valita joko vaihtoehdon listalta tai syöttää mukautetun arvon.</p>
  </GalleryCard>

  <GalleryCard header="Luetteloruutu" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Komponentti, joka näyttää vieritettävän listan vaihtoehdoista, mahdollistaen käyttäjien valita yhden tai useamman kohteen listalta.</p>
  </GalleryCard>
</GalleryGrid>

## Vaihtoehtodialogit {#option-dialogs}

Vaihtoehtodialogit tarjoavat tavan esittää käyttäjille valintoja tai pyytää heitä vahvistamaan ennen kuin toiminta jatkuu. Nämä komponentit ovat olennaisia luotaessa interaktiivista, päätöksentekoon perustuvaa työnkulkua, mahdollistaen käyttäjien vahvistaa, peruuttaa tai valita eri vaihtoehdoista selkeällä ja jäsennellyllä tavalla.

<GalleryGrid>
  <GalleryCard header="ViestiDialogi" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Dialogikomponentti, jota käytetään näyttämään tietoja tai varoituksia käyttäjälle, yleensä yhdellä `OK` -painikkeella, jolla vahvistetaan viesti.</p>
  </GalleryCard>

  <GalleryCard header="VahvistusDialogi" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Dialogikomponentti, joka kysyy käyttäjältä vahvistusta tai peruuttaako toiminnan, yleensä tarjoamalla `Kyllä` ja `Ei` tai `OK` ja `Peruuta` -painikkeita.</p>
  </GalleryCard>
  
  <GalleryCard header="SyöttöDialogi" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Dialogikomponentti, joka kehottaa käyttäjää syöttämään tekstiä tai tietoa, yleensä tarjoten syötekentän sekä toimintopainikkeet kuten `OK` ja `Peruuta`.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonValitsijaDialogi" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Dialogikomponentti, joka sallii käyttäjien selata ja valita tiedostoja palvelimen tiedostojärjestelmästä.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonLatausDialogi" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Dialogikomponentti, joka mahdollistaa käyttäjien ladata tiedostoja heidän paikalliselta tiedostojärjestelmältään sovellukseen.</p>
  </GalleryCard>

  <GalleryCard header="TiedostonTallennusDialogi" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Dialogikomponentti, joka sallii käyttäjien tallentaa tiedoston määritettyyn sijaintiin palvelimen tiedostojärjestelmässä.</p>
  </GalleryCard>
</GalleryGrid>

## Vuorovaikutus ja näyttö {#interaction-and-display}

Tämä kategoria sisältää komponentteja, jotka helpottavat käyttäjävuorovaikutuksia ja visuaalisesti näyttävät tietoja tai sovellustiloja. Nämä komponentit auttavat käyttäjiä navigoimaan sovelluksessa, käynnistämään toimintoja ja ymmärtämään edistymistä tai tuloksia dynaamisten visuaalisten elementtien kautta.

<GalleryGrid>
  <GalleryCard header="Taulukko" href="table/overview" image="/img/components/Table.png">
    <p>Komponentti, jota käytetään tietojen näyttämiseen rakenteellisessa, taulukkomuotoisessa muodossa riveillä ja sarakkeilla, tukien ominaisuuksia kuten lajittelua ja sivutusta.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Komponentti, joka integroituu Google Charts -palvelun kanssa näyttämään erilaisia kaavioita ja visuaalisia tietoesityksiä sovelluksessa.</p>
  </GalleryCard>

  <GalleryCard header="Painike" href="button" image="/img/components/Button.png">
    <p>Klikattava komponentti, joka käynnistää toiminnon tai tapahtuman, kun sitä painetaan.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Kevyt, ei-estävä ilmoituskomponentti, joka näyttää lyhyesti viestin käyttäjälle ennen automaattista häipymistä.</p>
  </GalleryCard>

  <GalleryCard header="Varoitus" href="alert" image="/img/components/Alert.png">
    <p>Komponentti, joka näyttää tärkeitä viestejä tai varoituksia erottuvassa muodossa käyttäjän huomion vangitsemiseksi.</p>
  </GalleryCard>

  <GalleryCard header="TyöpöytäIlmoitus" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Komponentti, joka hyödyntää selaimen natiivia Ilmoitus-API:a varoittaakseen käyttäjiä mukautetuilla työpöytäilmoituksilla.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigaattori" href="navigator" image="/img/components/Navigator.png">
    <p>Muokattava sivutuskomponentti tietojoukkojen selaamiseen, tukien asetteluja, joissa on ensimmäinen, viimeinen, seuraava, edellinen painike ja nopeat hyppy-kentät.</p>
  </GalleryCard>

  <GalleryCard header="Edistymispalkki" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Komponentti, joka visuaalisesti esittää tehtävän tai prosessin edistymistä, yleensä esitetty vaakasuorassa palkissa, joka täyttyy edistymisen mukaan.</p>
  </GalleryCard>

  <GalleryCard header="Liukusäädin" href="slider" image="/img/components/Slider.png">
    <p>Komponentti, joka sallii käyttäjien valita arvon määritellyltä alueelta vetämällä kahvaa pitkin rataa.</p>
  </GalleryCard>

  <GalleryCard header="Kiireindikaattori" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Yleinen visuaalinen indikaattori sovelluksessa, yleensä pyörivä animaatio, joka merkitsee, että globaali prosessi on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="Lataus" href="loading" image="/img/components/Loading.png">
    <p>Rajoitettu latausindikaattori, joka näkyy tietyssä vanhemmassa komponentissa, mikä ilmoittaa, että sisältöä tai tietoja ladataan kyseisellä alueella.</p>
  </GalleryCard>

  <GalleryCard header="Pyörijä" href="spinner" image="/img/components/Spinner.png">
    <p>Komponentti, joka näyttää pyörivän animaation, jota käytetään tyypillisesti osoittamaan, että prosessi tai toiminta on käynnissä.</p>
  </GalleryCard>

  <GalleryCard header="SovellusNavigaatio" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Komponentti, joka tarjoaa navigointivalikon sovellukselle, käytetään tyypillisesti linkkien tai navigointiesineiden luetteloimiseen, jotta voidaan vaihtaa eri osien tai näkymien välillä.</p>
  </GalleryCard>

  <GalleryCard header="Kuvake" href="icon" image="/img/components/Icons.png">
    <p>Komponentti, joka näyttää graafisen symbolin tai kuvan, jota käytetään usein toiminnan, tilan tai kategorian kuvaamiseen käyttöliittymässä.</p>
  </GalleryCard>

  <GalleryCard header="Pääte" href="terminal" image="/img/components/Terminal.png">
    <p>Komponentti, joka simuloi komento-rivi käyttöliittymää (CLI) sovelluksessa, sallien käyttäjien syöttää ja suorittaa tekstipohjaisia komentoja.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Komponentti, joka lataa lisää kohteita vieritettäessä, näyttää latausilmaisimen ja seuraa, kun kaikki sisältö on haettu.</p>
  </GalleryCard>

  <GalleryCard header="Päivittäjä" href="refresher" image="/img/components/Refresher.png">
    <p>Komponentti, joka mahdollistaa pyyhkäisemällä päivän päivitystoiminnon vieritettävissä kontteissa—ihanteellinen dynaamiselle tietojen lataamiselle.</p>
  </GalleryCard>

  <GalleryCard header="Puu" href="tree" image="/img/components/Tree.png">
    <p>Komponentti hierarkkisten tietojen näyttämiseen, antaen käyttäjien laajentaa, supistaa ja vuorovaikuttaa sisäkkäisten kohteiden kanssa.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Komponentti käyttäjäprofiilikuva tai alkukirjainten näyttämiseen, tukien erilaisia kokoja, muotoja ja teemoja.</p>
  </GalleryCard>
  
</GalleryGrid>
