---
title: Login
sidebar_position: 70
description: >-
  Display an authentication dialog with the Login component, handling
  submission, validation, custom fields, and form action URLs.
_i18n_hash: 5016fc4d15ba24b16c61eed8e6e272ee
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjäntunnistusta tarjoamalla valmiin kirjautumisdialogin, jossa on käyttäjätunnus- ja salasanakentät. Se sisältää ominaisuuksia, kuten syötteen validoinnin, muokattavat nimet ja viestit, salasanan näkyvyysasetukset sekä tuen lisäcustom-kentille.

<!-- INTRO_END -->

## Creating a `Login` dialog {#creating-a-login-dialog}

Luo `Login`-dialogi instoimalla komponentti ja kutsumalla `open()`, jotta se näkyy. Dialogissa on oletuksena käyttäjätunnus- ja salasanakentät, syötteen validointi ja kirjautumispainike.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Login submission {#login-submission}

Kun käyttäjät syöttävät käyttäjätunnuksensa ja salasanansa, `Login`-komponentti validoi nämä syötteet pakollisina kenttinä. Kun validointi onnistuu, lomakkeen lähetyseventti laukaistaan ja syötetyt tunnistetiedot toimitetaan. Useiden lähetyksien estämiseksi [Sign in] -painike on heti poistettu käytöstä.

Seuraavassa on perus `Login`-komponentti. Jos sekä käyttäjätunnus että salasana on asetettu arvoon `"admin"`, kirjautumisdialogi sulkeutuu ja [Logout] -painike tulee näkyviin. Jos tunnistetiedot eivät täsmää, oletusvirhesanoma näytetään.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Disabling the [Sign in] Button
Oletuksena `Login` poistaa heti käytöstä [Sign in] -painikkeen, kun komponentti validoi kirjautumistiedot oikeiksi useiden lähetyksien estämiseksi. Voit ottaa [Sign in] -painikkeen uudelleen käyttöön käyttämällä `setEnabled(true)` -metodia.
:::

:::tip Allowing Empty Passwords
Voit sallia käyttäjien kirjautuvan vain käyttäjätunnuksella käyttämällä `setEmptyPassword(true)` -metodia.
:::

## Form action <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomaketietoja suoraan määriteltyyn URL-osoitteeseen sen sijaan, että se käsittelytään lähetys eventin kautta. Kun toiminta-URL on asetettu, lomake tekee normaali POST-pyynnön käyttäjätunnuksen ja salasanan lomakeparametreina.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Kun käytät `setAction()`, lomakkeen lähetys ohittaa `LoginSubmitEvent`-eventin ja tekee sen sijaan perinteisen HTTP POST -pyynnön määriteltyyn päätepisteeseen. Käyttäjätunnus ja salasana lähetetään lomakeparametreina nimillä `"username"` ja `"password"`. Mukautetut kentät, joilla on nimi-attribuutti, sisällytetään myös POST-pyyntöön.

:::tip
Jos toiminta-URL:ia ei ole asetettu, lomakkeen lähetys käsitellään `LoginSubmitEvent`:in kautta, jolloin voit käsitellä tunnistetietoja ohjelmallisesti palvelinpuolella.
:::

## Internationalization (i18n) {#internationalization-i18n}

`Login`-komponentin otsikot, kuvastot, nimet ja viestit ovat täysin muokattavissa `LoginI18n`-luokan avulla. Tämä joustavuus mahdollistaa kirjautumisen käyttöliittymän räätälöinnin erityisten lokalisaatiovaatimusten tai personointiprofiilien mukaisesti.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Custom fields {#custom-fields}

`Login`-komponentissa on useita slotteja, jotka mahdollistavat ylimääräisten kenttien lisäämisen tarpeen mukaan. Mukautetut kentät kerätään automaattisesti lomakkeen lähetyksen yhteydessä ja niihin pääsee käsiksi lähetys eventin tietokartasta.

Seuraavassa kirjautumisessa on lisätty mukautettu kenttä asiakastunnusta varten. Tämä voi auttaa hallitsemaan yrityksiä tai osastoja, joilla on yhteisiä sisältöjä useiden käyttäjien kesken.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/frontend/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Name Required
Mukautettujen kenttien on oltava nimettyjä käyttämällä `setName()`-metodia, jotta ne sisältyvät lomakkeen lähetykseen. Nimi toimii avaimena kentän arvon hakemiseen `event.getData()`-metodista.
:::

## Cancel button {#cancel-button}

`Login`-komponentissa on [Cancel] -painike, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää päästä rajattuun alueeseen sovelluksessa ja tarvitsee mahdollisuuden palata edelliseen sijaintiin ilman kirjautumisen suorittamista.

Tehdäksesi peruutuspainikkeesta näkyvän, anna sille nimi. Voit myös kuunnella peruutustapahtumia käsitelläksesi peruutusta asianmukaisesti.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Hiding Elements
Piilottaaksesi elementin, aseta sen nimi tyhjäksi merkkijonoksi. Tämä mahdollistaa näkyvyyden vaihdon ilman, että komponenttia poistetaan koodistasi.
:::

## Password managers {#password-managers}

Tämä komponentti toimii selaimessa olevien salasanojen hallintatyökalujen kanssa kirjautumisprosessin yksinkertaistamiseksi. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) -API:in, joka tarjoaa:

- **Automaattinen täyttö**: Selaimen voi automaattisesti täyttää käyttäjätunnus- ja salasanakentät, jos käyttäjällä on tallennettuja tunnistetietoja sivustolle.
- **Tunnistetietojen hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia tunnistetietoja, mikä tekee tulevista kirjautumisista nopeampia ja helpompia.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettuna, selain voi tarjota käyttäjälle mahdollisuuden valita yksi tallennetuista kokonaisuuksista.

## Styling {#styling}

<TableBuilder name="Login" />
