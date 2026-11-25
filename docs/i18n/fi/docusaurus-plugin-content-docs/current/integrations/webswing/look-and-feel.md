---
title: Swingin ulkoasu
sidebar_position: 5
description: >-
  Tuo upotettuun Swing-sovellukseen webforJ:n värit, fontit, painikkeet ja
  kohdistusääriviivat DWC Look and Feel for Swingillä, vaaleana tai tummana.
_i18n_title_hash: 3c0ddc5fa92ce0ba34ad9e57d896d42a
_i18n_description_hash: 8f86f1732f1d00ba4e1e87a16ff232f1
_i18n_hash: f7227a823be8a2f867fdf399bc56a1b4
---
Kun `WebswingConnector` upottaa Swing-sovelluksen webforJ-sovellukseen, käyttäjät näkevät Swing-näkymiä webforJ-näkymien rinnalla. Swingin oletusarvoisella ulkoasulla nämä puolet näyttävät eri sovelluksilta. Swingin DWC Look and Feel tuo Swing-sovellukseen webforJ:n käyttämien DWC-komponenttien fontit, värit, painikkeet ja kohdistuksen ääriviivat. Siinä on vaalea ja tumma paletti.

Se on tavallinen Swing-ulkoasu yhdessä JAR-tiedostossa. Lisää se Swing-sovelluksen luokkapolkuun ja valitse se käynnistyksen yhteydessä. Muuhun Swing-koodiin ei tarvitse tehdä muutoksia.

:::info Parhaan mahdollisen vastaavuuden tavoittelu
Swing ja DWC piirtävät ohjauskomponenttinsa eri tavoin. Molemmilla puolilla voi käyttää samaa vaaleaa tai tummaa palettia, mutta Swing-näkymästä webforJ-näkymään siirryttäessä voi silti huomata pieniä eroja.
:::

## Kokeile demoa {#try-the-demo}

Order Desk on Swing-sovellus, johon teema on asennettu. [Lataa Order Desk -demo](https://dwc.style/docs/#/swing/downloads) ja suorita se Java 21:llä tai uudemmalla versiolla:

```bash
java -jar dwc-swing-laf-demo.jar
```

Valitse **View → DWC light** tai **View → DWC dark**, sitten **View → Primary color** ja vertaa valittua tilausta, aktiivista välilehteä ja Save order -painiketta.

Order Deskin kaikki ohjauskomponentit ovat Swingin vakiokomponentteja. Vaihda palettia ja perusväriä kuvakaappauksen alapuolelta ja suurenna kuva valitsemalla se.

<!-- vale off -->
<ThemeGallery alt="The Order Desk Swing demo" items={[
  { label: "Default", color: "#134ddf", light: "/img/swing-laf/order-desk-light-default.webp", dark: "/img/swing-laf/order-desk-dark-default.webp" },
  { label: "Violet", color: "#7c3aed", light: "/img/swing-laf/order-desk-light-violet.webp", dark: "/img/swing-laf/order-desk-dark-violet.webp" },
  { label: "Teal", color: "#0d9488", light: "/img/swing-laf/order-desk-light-teal.webp", dark: "/img/swing-laf/order-desk-dark-teal.webp" },
  { label: "Orange", color: "#ea580c", light: "/img/swing-laf/order-desk-light-orange.webp", dark: "/img/swing-laf/order-desk-dark-orange.webp" }
]} />
<!-- vale on -->

## Asenna teema {#install-the-theme}

[Lataa teeman JAR-tiedosto](https://dwc.style/docs/#/swing/downloads) ja lisää se Swing-sovelluksen luokkapolkuun. Teema edellyttää Java 21:tä tai uudempaa versiota, ja se sisältää molemmat paletit sekä Inter-fontin.

Valitse ulkoasu Swingin tapahtumankäsittelysäikeessä ennen kuin sovellus luo ensimmäisen ikkunansa:

```java
try {
  UIManager.setLookAndFeel(new DwcLookAndFeel());
} catch (UnsupportedLookAndFeelException e) {
  throw new IllegalStateException(e);
}
```

Käytä tummaa palettia varten sen sijaan luokkaa `DwcDarkLookAndFeel`. Molemmat luokat ovat paketissa `com.basis.dwc.swing.laf`. Jos sovellus valitsee ulkoasun luokan nimen perusteella, käytä nimeä `com.basis.dwc.swing.laf.DwcLookAndFeel` tai `com.basis.dwc.swing.laf.DwcDarkLookAndFeel`.

Jos sovellus käyttää Java-moduuleja, lisää JAR-tiedosto moduulipolkuun ja vaadi moduuli `com.basis.dwc.swing.laf`.

## Sovita webforJ-teemaasi {#match-your-webforj-theme}

Teema käyttää samoja tunnisteiden nimiä kuin [webforJ-teemat](/docs/styling/themes). Aseta Swing-sovelluksen tunnisteelle sama arvo kuin webforJ-sovelluksessasi.

Voit käyttää webforJ-sovelluksesi perusväriä asettamalla perusvärin lähtöarvon ulkoasun asentamisen jälkeen:

```java
DwcLookAndFeel.setToken("--dwc-color-primary-seed", new Color(117, 67, 188));
```

Ensisijaiset painikkeet, valinnat ja kohdistuksen ääriviivat saavat tämän värin. Muut semanttiset värit, kuten vaaraväri, säilyttävät oman palettinsa. Voit palauttaa paletin perusvärin antamalla arvoksi `null`.

Teema käyttää oletusarvoisesti Inter-fonttia. webforJ käyttää järjestelmän fonttia tunnisteen [`--dwc-font-family`](/docs/styling/typography) kautta. Jos haluat käyttää Swing-sovelluksessa samaa fonttia, aseta tunnisteen arvoksi `system-ui`:

```java
DwcLookAndFeel.setToken("--dwc-font-family", "system-ui");
```

Toinen fontti voi muuttaa otsikoiden leveyttä ja kenttien korkeutta, joten sovita ikkunat sisältöjen luonnin jälkeen.

## Tyylittele yksittäinen ohjauskomponentti {#style-one-control}

Asiakasominaisuudella voi muuttaa yksittäisen ohjauskomponentin ulkoasua. Tässä lomakkeessa kentälle lisätään otsikko ja pakollisuusmerkintä sekä asetetaan Tallenna-painike ensisijaiseksi:

```java
email.putClientProperty(DwcLookAndFeel.LABEL, "Delivery email");
email.putClientProperty(DwcLookAndFeel.REQUIRED, true);
save.putClientProperty(DwcLookAndFeel.THEME, ButtonTheme.PRIMARY);
```

Voit näyttää kentän alla vahvistusviestin asettamalla virheellisen tilan ja ohjetekstin:

```java
boolean missing = email.getText().isBlank();
email.putClientProperty(DwcLookAndFeel.INVALID, missing);
email.putClientProperty(DwcLookAndFeel.HELPER_TEXT,
    missing ? "Enter a delivery email." : "");
```

[Asiakasominaisuuksien viite](https://dwc.style/docs/#/swing/client-properties) sisältää kaikkien ohjauskomponenttien, kuten taulukoiden, puiden, välilehtien, liukusäätimien ja valintaikkunoiden, avaimet.

[Koko teemaopas](https://dwc.style/docs/#/swing/) käsittelee valintaikkunoita, näppäimistökohdistusta ja liikettä.
