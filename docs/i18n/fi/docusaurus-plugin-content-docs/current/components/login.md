---
title: Login
sidebar_position: 70
_i18n_hash: d5724547e5173f77895c401018612328
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjäautentikointia tarjoamalla valmiiksi käytettävän kirjautumisdialogin, jossa on käyttäjänimi- ja salasanakentät. Se sisältää ominaisuuksia, kuten syötteen validoinnin, muokattavat etiketit ja viestit, salasanan näkyvyyden hallinnan sekä tuen lisämuokattaville kentille.

<!-- INTRO_END -->

## Luodaan `Login`-dialogi {#creating-a-login-dialog}

Luodaan `Login`-dialogi instanssoimalla komponentti ja kutsumalla `open()` sen näyttämiseksi. Dialogissa on oletuksena käyttäjänimi- ja salasanakentät, syötteen validointi ja kirjautumisnappi.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Kirjautumisen lähettäminen {#login-submission}

Kun käyttäjät syöttävät käyttäjänimensä ja salasanansa, `Login`-komponentti validoi nämä syötteet vaadituiksi kentiksi. Kun validointi on onnistunut, lomakkeen lähettämistapahtuma laukaistaan, jolloin syötetyt tunnistetiedot toimitetaan. Estääkseen useita lähetyksiä, [Kirjaudu sisään] -painike on heti poistettu käytöstä.

Seuraavassa esitetään perus `Login`-komponentti. Jos käyttäjänimi ja salasana on asetettu molemmat arvoon `"admin"`, kirjautumisdialogi sulkeutuu ja [Kirjaudu ulos] -painike tulee näkyviin. Jos tunnistetiedot eivät täsmää, oletusvirheviesti näytetään.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info [Kirjaudu sisään] -painikkeen poistaminen käytöstä
Oletuksena `Login` poistaa heti [Kirjaudu sisään] -painikkeen käytöstä, kun komponentti validoi kirjautumistiedot oikein, estääkseen useita lähetyksiä. Voit ottaa [Kirjaudu sisään] -painikkeen uudelleen käyttöön käyttämällä `setEnabled(true)`-metodia.
:::

:::tip Tyhjien salasanojen salliminen
Voit sallia käyttäjien kirjautuvan vain käyttäjänimen avulla käyttämällä `setEmptyPassword(true)`-metodia.
:::

## Lomakkeen toiminto <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomakedataa suoraan määriteltyyn URL-osoitteeseen sen sijaan, että käsittelisi lähettämistä lomakkeen lähettämistapahtuman kautta. Kun toimint URL on asetettu, lomake suorittaa tavanomaisen POST-pyynnön käyttäjänimen ja salasanan lomakeparametreina.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Kun käytät `setAction()`, lomakkeen lähettäminen ohittaa `LoginSubmitEvent`-tapahtuman ja suorittaa sen sijaan perinteisen HTTP POST -pyynnön määritettyyn päätepisteeseen. Käyttäjänimi ja salasana lähetetään lomakeparametreina, joiden nimet ovat `"username"` ja `"password"`, vastaavasti. Mukautetut kentät, joilla on nimen attribuutti, sisältyvät myös POST-pyyntöön.

:::tip 
Jos toimint URL -osoitetta ei ole asetettu, lomakkeen lähettäminen käsitellään `LoginSubmitEvent`-tapahtuman kautta, mikä mahdollistaa tunnistetietojen käsittelyn ohjelmallisesti palvelinpuolella.
:::

## Kansainvälistäminen (i18n) {#internationalization-i18n}

`Login`-komponentin sisällä olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin muokattavissa käyttämällä `LoginI18n`-luokkaa. Tämä joustavuus antaa sinun räätälöidä kirjautumisliittymän vastaamaan erityisiä lokalisaatiovaatimuksia tai personointipreferenssejä.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Mukautetut kentät {#custom-fields}

`Login`-komponentti sisältää useita slotteja, jotka mahdollistavat lisäkenttien lisäämisen tarpeen mukaan. Mukautetut kentät kerätään automaattisesti, kun lomake lähetetään ja ne voidaan käsitellä lomakkeen lähettämistapahtuman datakartassa.

Seuraavassa kirjautumisdialogissa on lisätty mukautettu kenttä asiakastunnusta varten. Tämä voi auttaa hallitsemaan yrityksiä tai osastoja, joilla on yhteistä sisältöä useiden käyttäjien kesken.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nimen asettaminen pakolliseksi
Mukautettujen kenttien on oltava nimettyjä käyttämällä `setName()`-metodia, jotta ne sisällytetään lomakkeen lähettämiseen. Nimi käytetään avaimena kentän arvon hakemiseksi `event.getData()`:sta.
:::

## Peruuta-painike {#cancel-button}

`Login` sisältää [Peruuta] -painikkeen, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää päästä sovelluksen rajoitettuun alueeseen ja tarvitsee mahdollisuuden palata edelliseen sijaintiinsa ilman kirjautumisen loppuun saattamista.

Saadaksesi peruuta-painikkeen näkyviin, anna sille etiketti. Voit myös kuunnella peruuttamistapahtumia käsitelläksesi peruuttamista asianmukaisesti.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementtien piilottaminen
Voit piilottaa elementin asettamalla sen etiketin tyhjään merkkijonoon. Tämä mahdollistaa näkyvyyden vaihtamisen ilman komponentin poistamista koodistasi.
:::

## Salasananhallintaohjelmat {#password-managers}

Tämä komponentti toimii selaimessa toimivien salasananhallintaohjelmien kanssa yksinkertaistaakseen kirjautumisprosessia. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:iin, joka tarjoaa:

- **Automaattinen täyttö**: Selaimen voi automaattisesti täyttää käyttäjänimi- ja salasanakentät, jos käyttäjän tallennetut tunnistetiedot ovat saatavilla sivustolle.
- **Tunnistetietojen hallinta**: Kirjautumisen jälkeen selain voi pyytää käyttäjää tallentamaan uusia tunnistetietoja, mikä tekee tulevasta kirjautumisesta nopeampaa ja helpompaa.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettu, selain voi tarjota käyttäjälle valinnan tallennettujen kokonaisuuksien joukosta.

## Tyylittely {#styling}

<TableBuilder name="Login" />
