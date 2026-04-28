---
title: Login
sidebar_position: 70
_i18n_hash: 59a9ab8cb7ba550b955ab83de0c6d878
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjän todennusta tarjoamalla valmiin kirjautumisdialogin, jossa on käyttäjänimi- ja salasanakentät. Se sisältää ominaisuuksia, kuten syötteen validoinnin, mukautettavat tunnisteet ja viestit, salasanan näkyvyyden hallinnan sekä tuen lisä mukautetuille kentille.

<!-- INTRO_END -->

## Luodaan `Login`-dialogi {#creating-a-login-dialog}

Luo `Login`-dialogi instansoimalla komponentti ja kutsumalla `open()` sen näyttämiseksi. Dialogissa on oletuksena käyttäjänimi- ja salasanakentät, syötteen validointi ja kirjautumispainike.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height='450px'
/>

## Kirjautumisen lähettäminen {#login-submission}

Kun käyttäjät syöttävät käyttäjänimensä ja salasanansa, `Login`-komponentti validoi nämä syötteet pakollisina kenttinä. Kun validointi on onnistunut, lomakkeen lähetystapahtuma laukaistaan, ja syötetyt tiedot toimitetaan. Estääkseen moninkertaiset lähetykset, [Kirjaudu sisään] -painike on heti poistettu käytöstä.

Seuraava esittää perus `Login`-komponentin. Jos käyttäjänimi ja salasana on molemmat asetettu arvoon "admin", kirjautumisdialogi sulkeutuu, ja [Kirjaudu ulos] -painike tulee näkyviin. Jos tiedot eivät täsmää, oletusvirheilmoitus näytetään.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height='450px'
/>

:::info [Kirjaudu sisään] -painikkeen poistaminen käytöstä
Oletuksena `Login` poistaa heti käytöstä [Kirjaudu sisään] -painikkeen, kun komponentti validoi kirjautumissyötteet oikein, estääkseen moninkertaiset lähetykset. Voit ottaa [Kirjaudu sisään] -painikkeen uudelleen käyttöön käyttämällä `setEnabled(true)`-metodia.
:::

:::tip Tyhjien salasanojen salliminen
Voit sallia käyttäjien kirjautuvan sisään vain käyttäjän nimellä käyttämällä `setEmptyPassword(true)` -metodia.
:::

## Lomakkeen toiminto <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomakedatat suoraan määrättyyn URL-osoitteeseen sen sijaan, että se käsittelisi lähettämistä lomakkeen tapahtuman kautta. Kun toimint URL-osoite on asetettu, lomake suorittaa tavanomaisen POST-pyynnön käyttäjänimen ja salasanan muodossa.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Kun käytetään `setAction()`, lomakkeen lähetys ohittaa `LoginSubmitEventin` ja suorittaa sen sijaan perinteisen HTTP POST -pyynnön määrättyyn päätepisteeseen. Käyttäjänimi ja salasana lähetetään lomakeparametreina nimillä "username" ja "password". Mukautetut kentät, joissa on nimen attribuutti, sisältyvät myös POST-pyyntöön.

:::tip 
Jos toimint URL-osoitetta ei ole asetettu, lomakkeen lähetys käsitellään `LoginSubmitEventin` kautta, jolloin voit käsitellä käyttäjätietoja ohjelmallisesti palvelinpuolella.
:::

## Kansainvälistäminen (i18n) {#internationalization-i18n}

`Login`-komponentin otsikot, kuvaukset, tunnisteet ja viestit ovat täysin mukautettavissa käyttämällä `LoginI18n`-luokkaa. Tämä joustavuus mahdollistaa kirjautumisliittymän muokkaamisen tiettyjen lokalisointivaatimusten tai henkilökohtaisten mieltymysten mukaiseksi.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height='600px'
/>

## Mukautetut kentät {#custom-fields}

`Login`-komponentti sisältää useita paikkoja, jotka mahdollistavat lisäkenttien lisäämisen tarpeen mukaan. Mukautetut kentät kerätään automaattisesti, kun lomake lähetetään, ja niihin pääsee käsiksi lähetyseventin datakartasta.

Seuraavassa kirjautumisessa on lisätty mukautettu kenttä asiakastunnukselle. Tämä voi auttaa hallitsemaan yrityksiä tai osastoja, joilla on yhteistä sisältöä useiden käyttäjien kesken.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height='700px'
/>

:::info Nimen vaatiminen
Mukautetuilla kentillä on oltava nimi asetettuna `setName()`-metodilla, jotta ne voidaan sisällyttää lomakkeen lähetykseen. Nimi toimii avaimena kentän arvon hakemiseksi `event.getData()`-kutsusta.
:::

## Peruuta-painike {#cancel-button}

`Login` sisältää [Peruuta] -painikkeen, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää päästä sovelluksen rajoitettuun osaan ja tarvitsee vaihtoehdon palata edelliseen sijaintiin ilman kirjautumisen suorittamista.

Tehdäksesi peruuta-painikkeen näkyväksi, tarjoa sille tunniste. Voit myös kuunnella peruuta-tapahtumia käsitelläksesi peruutuksen asianmukaisesti.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height='450px'
/>

:::tip Elementtien piilottaminen
Piilottaaksesi elementin, aseta sen tunniste tyhjiksi. Tämä mahdollistaa näkyvyyden kytkemisen ilman, että komponenttia poistetaan koodistasi.
:::

## Salasananhallintaohjelmat {#password-managers}

Tämä komponentti toimii selainpohjaisten salasananhallintatyökalujen kanssa helpottaakseen kirjautumisprosessia. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:in, joka tarjoaa:

- **Automaattinen täyttö**: Selain voi automaattisesti täyttää käyttäjänimi- ja salasanakentät, jos käyttäjä on tallentanut tunnistetiedot sivustolle.
- **Tunnistetietojen hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia tunnistetietoja, jolloin tulevat kirjautumiset ovat nopeampia ja helpompia.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettuna, selain voi tarjota käyttäjälle mahdollisuuden valita tallennetuista tietoista.

## Tyylittely {#styling}

<TableBuilder name="Login" />
