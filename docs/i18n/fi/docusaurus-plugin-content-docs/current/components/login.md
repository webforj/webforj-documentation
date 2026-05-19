---
title: Login
sidebar_position: 70
_i18n_hash: 929bacbc38791adc906102078bdd6bfa
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjien tunnistautumista tarjoamalla valmiiksi käyttövalmiin kirjautumisdialgin, jossa on käyttäjänimen ja salasanan kentät. Se sisältää ominaisuuksia, kuten syötteen validoinnin, mukautettavat labelit ja viestit, salasanan näkyvyysasetukset sekä tuen lisäkohdistetuille kentille.

<!-- INTRO_END -->

## Creating a `Login` dialog {#creating-a-login-dialog}

Luo `Login`-dialogi instanssoimalla komponentti ja kutsumalla `open()` sen näyttämiseksi. Dialogissa on oletuksena käyttäjänimen ja salasanan kentät, syötteen validointi ja kirjautumispainike.

<ComponentDemo
path='/webforj/loginbasic'
files={['src/main/java/com/webforj/samples/views/login/LoginBasicView.java']}
height='450px'
/>

## Login submission {#login-submission}

Kun käyttäjät syöttävät käyttäjänimensä ja salasanansa, `Login`-komponentti validoi nämä syötteet vaadituiksi kentiksi. Kun validointi onnistuu, lomakkeen lähetystapahtuma laukaistaan, ja syötetyt tunnistetiedot toimitetaan. Useiden lähetyksien estämiseksi [Sign in] -painike poistetaan välittömästi käytöstä.

Seuraavassa on esitelty perus `Login`-komponentti. Jos käyttäjänimi ja salasana on asetettu molemmat `"admin"`-arvoksi, kirjautumisdialgi sulkeutuu ja [Logout] -painike ilmestyy. Jos tunnistetiedot eivät täsmää, oletusvirheviesti näytetään.

<ComponentDemo
path='/webforj/loginsubmission'
files={['src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java']}
height='450px'
/>

:::info Disabling the [Sign in] Button
Oletuksena `Login` heti poistaa [Sign in] -painikkeen käytöstä, kun komponentti validoi kirjautumissyötteet oikein, estääkseen useita lähetyksiä. Voit aktivoida [Sign in] -painikkeen uudelleen käyttämällä `setEnabled(true)`-metodia.
:::

:::tip Allowing Empty Passwords
Voit sallia käyttäjien kirjautuvan sisään pelkällä käyttäjänimellä käyttämällä `setEmptyPassword(true)`-metodia.
:::

## Form action <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomaketiedot suoraan määritettyyn URL-osoitteeseen sen sijaan, että käsittelisi lähetyksen kautta lähetys-tapahtumaa. Kun toimint URL-osoite on asetettu, lomake suorittaa tavallisen POST-pyynnön käyttäjänimen ja salasanan ollessa lomakeparametreja.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Käytettäessä `setAction()`, lomakkeen lähetys ohittaa `LoginSubmitEvent`-tapahtuman ja suorittaa sen sijaan perinteisen HTTP POST -pyynnön määritettyyn päätepisteeseen. Käyttäjänimi ja salasana lähetetään lomakeparametreina nimellä `"username"` ja `"password"`. Mukautetut kentät, joilla on nimeä attribuutti, sisältyvät myös POST-pyyntöön.

:::tip 
Jos toimint URL-osoitetta ei ole asetettu, lomakkeen lähetys käsitellään `LoginSubmitEvent`-tapahtuman kautta, mikä mahdollistaa tunnistetietojen käsittelyn ohjelmallisesti palvelinpuolella.
:::

## Internationalization (i18n) {#internationalization-i18n}

`Login`-komponentin sisällä olevat otsikot, kuvaukset, labelit ja viestit ovat täysin mukautettavissa käyttämällä `LoginI18n`-luokkaa. Tämä joustavuus mahdollistaa kirjautumisliittymän räätälöinnin tiettyjen lokalisointivaatimusten tai henkilökohtaisten mieltymysten mukaisesti.

<ComponentDemo
path='/webforj/logininternationalization'
files={['src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java']}
height='600px'
/>

## Custom fields {#custom-fields}

`Login`-komponentti sisältää useita reikiä, jotka antavat mahdollisuuden lisätä ylimääräisiä kenttiä tarvittaessa. Mukautetut kentät kerätään automaattisesti lomakkeen lähetyksen yhteydessä, ja ne voidaan saada käsittelyn avulla lähetyksen tapahtuman tietokartasta.

Seuraavassa kirjautumisessa on lisätty mukautettu kenttä asiakas-ID:lle. Tämä voi auttaa hallitsemaan yrityksiä tai osastoja, joilla on yhteisiä sisältöjä useiden käyttäjien kesken.

<ComponentDemo
path='/webforj/logincustomfields'
files={[
  'src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java',
  'src/main/resources/static/css/login/loginCustomFields.css',
]}
height='700px'
/>

:::info Name Required
Mukautetuilla kentillä on oltava nimi asetettuna `setName()`-metodin avulla, jotta ne sisältyvät lomakkeen lähetykseen. Nimi käytetään avaimena kentän arvon hakemiseen `event.getData()`-kutsussa.
:::

## Cancel button {#cancel-button}

`Login` sisältää [Cancel] -painikkeen, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää käyttää sovelluksen rajoitettua aluetta ja tarvitsee mahdollisuuden palata edelliseen sijaintiin ilman kirjautumisen viimeistelemistä.

Käytäksesi peruutuspainiketta, tarjoa sille label. Voit myös kuunnella peruutustapahtumia käsitelläksesi peruutuksen asianmukaisesti.

<ComponentDemo
path='/webforj/logincancelbutton'
files={['src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java']}
height='450px'
/>

:::tip Hiding Elements
Piilottaaksesi elementin, aseta sen label tyhjään merkkijonoon. Tämä mahdollistaa näkyvyyden vaihtamisen ilman, että komponenttia poistetaan koodistasi.
:::

## Password managers {#password-managers}

Tämä komponentti toimii selainpohjaisten salasana-managerien kanssa kirjautumisprosessin yksinkertaistamiseksi. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:han, joka tarjoaa:

- **Automaattinen täyttö**: Selain voi automaattisesti täyttää käyttäjänimen ja salasanan kentät, jos käyttäjällä on tallennettuja tunnistetietoja sivustolle.
- **Tunnistetietojen hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia tunnistetietoja, mikä tekee tulevista kirjautumisista nopeampia ja helpompia.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettu, selain voi tarjota käyttäjälle valinnan, josta valita yksi tallennetuista asetuksista.

## Styling {#styling}

<TableBuilder name="Login" />
