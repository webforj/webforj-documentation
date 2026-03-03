---
title: Login
sidebar_position: 70
_i18n_hash: e0aded01aee7ef12465b2d7661cc0477
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjän autentikointia tarjoamalla valmiiksi käyttöönottavan sisäänkirjautumisdialogin, jossa on käyttäjänimi- ja salasanakentät. Se sisältää ominaisuuksia kuten syötteen validointi, mukautettavat etiketit ja viestit, salasanan näkyvyyskontrollit sekä tuen lisäkustomikentille.

<!-- INTRO_END -->

## Luodaan `Login`-dialogi {#creating-a-login-dialog}

Luo `Login`-dialogi instansioimalla komponentti ja kutsumalla `open()` sen näyttämiseksi. Dialogissa on oletuksena käyttäjänimi- ja salasanakentät, syötteen validointi ja kirjautumispainike.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Sisäänkirjautuminen {#login-submission}

Kun käyttäjät syöttävät käyttäjänimensä ja salasanansa, `Login`-komponentti validoi nämä syötteet pakollisina kenttinä. Kun validointi onnistuu, lomakkeen lähettämistapahtuma laukaistaan, ja syötetyt tunnistetiedot toimitetaan. Estääkseen useat lähetykset, [Kirjaudu sisään] -painike on heti poistettu käytöstä.

Seuraavassa esitellään perus `Login`-komponenttia. Jos käyttäjänimi ja salasana on molemmat asetettu arvoon `"admin"`, sisäänkirjautumisdialogi sulkeutuu ja [Kirjaudu ulos] -painike ilmestyy. Jos tunnistetiedot eivät täsmää, näkyy oletusvirheviesti.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info [Kirjaudu sisään] -painikkeen poistaminen käytöstä
Oletuksena `Login` poistaa heti käytöstä [Kirjaudu sisään] -painikkeen, kun komponentti validoi kirjautumissyötteet oikein, estääkseen useat lähetykset. Voit palauttaa [Kirjaudu sisään] -painikkeen käyttöön `setEnabled(true)`-metodilla.
:::

:::tip Tyhjien salasanojen salliminen
Voit sallia käyttäjien kirjautua sisään vain käyttäjänimellä käyttämällä `setEmptyPassword(true)` -metodia.
:::

## Lomakeprosessi <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomakedatan suoraan määriteltyyn URL-osoitteeseen sen sijaan, että se käsittelisi lähetystä lomakkeena. Kun toiminton URL on asetettu, lomake suorittaa standardin POST-pyynnön käyttäjänimen ja salasanan muodossa lomakeparametreina.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Kun käytetään `setAction()`, lomakkeen lähetys ohittaa `LoginSubmitEvent`-tapahtuman ja suorittaa sen sijaan perinteisen HTTP POST -pyynnön määritettyyn päätepisteeseen. Käyttäjänimi ja salasana lähetetään muodossa "username" ja "password", vastaavasti. Mukautetut kentät, joilla on nimi-attribuutti, sisällytetään myös POST-pyyntöön.

:::tip 
Jos toiminto-URL:ta ei ole asetettu, lomakkeen lähetys käsitellään `LoginSubmitEvent`-tapahtuman kautta, mikä mahdollistaa tunnistetietojen käsittelyn ohjelmallisesti palvelinpuolella.
:::

## Kansainvälistäminen (i18n) {#internationalization-i18n}

`Login`-komponentissa olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `LoginI18n`-luokkaa. Tämä joustavuus mahdollistaa kirjautumisliittymän räätälöimisen tiettyjen lokalisointivaatimusten tai personointipreferenssien täyttämiseksi.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Mukautetut kentät {#custom-fields}

`Login`-komponentissa on useita slotteja, jotka sallivat lisäkenttien lisäämisen tarpeen mukaan. Mukautetut kentät kerätään automaattisesti lomakkeen lähetyksen yhteydessä ja niitä voidaan käyttää lähetyksen tapahtuman datakartasta.

Seuraavassa kirjautumisessa on lisätty mukautettu kenttä asiakastunnistetta varten. Tämä voi auttaa sinua hallitsemaan yrityksiä tai osastoja, joilla on jaettua sisältöä useiden käyttäjien kesken.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nimi vaaditaan
Mukautettujen kenttien on oltava nimetty `setName()`-metodilla, jotta niitä voidaan sisällyttää lomakkeen lähetykseen. Nimi toimii avaimena kenttäarvon hakemiseksi `event.getData()`:sta.
:::

## Peruuta-painike {#cancel-button}

`Login` sisältää [Peruuta] -painikkeen, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää päästä sovelluksen rajoitettuun alueeseen ja tarvitsee mahdollisuuden palata edelliseen sijaintiinsa ilman sisäänkirjautumisen suorittamista.

Voit tehdä peruuta-painikkeesta näkyvän antamalla sille etiketin. Voit myös kuunnella peruuttamistapahtumia käsitelläksesi peruuttamisen asianmukaisesti.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementtien piilottaminen
Piilottaaksesi elementin, aseta sen etiketti tyhjäksi. Tämä sallii näkyvyyden vaihtamisen ilman, että komponenttia poistetaan koodistasi.
:::

## Salasananhallintatyökalut {#password-managers}

Tämä komponentti toimii selainpohjaisten salasananhallintatyökalujen kanssa helpottaakseen sisäänkirjautumisprosessia. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:in, joka tarjoaa:

- **Automaatt täyttö**: Selaimella voi olla tapana automaattisesti täyttää käyttäjänimi- ja salasanakentät, jos käyttäjä on tallentanut tunnistetietoja sivustolle.
- **Tunnistetietojen hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia tunnistetietoja, jolloin tulevat sisäänkirjautumiset ovat nopeampia ja helpompia.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettu, selain voi tarjota käyttäjälle valinnan tallennettujen settilä välillä.

## Tyylittely {#styling}

<TableBuilder name="Login" />
