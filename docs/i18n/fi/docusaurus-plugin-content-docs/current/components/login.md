---
title: Login
sidebar_position: 70
sidebar_class_name: updated-content
_i18n_hash: cdcad4b5ef5d3ba0bd84e4d9deac49b5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

`Login`-komponentti yksinkertaistaa käyttäjän todennusta tarjoamalla valmiin kirjautumisvalintaikkunan, jossa on käyttäjänimi- ja salasana-kentät. Se sisältää ominaisuuksia, kuten syötteen validointi, muokattavat tarrat ja viestit, salasanan näkyvyysohjaukset sekä tukea lisäkohtaisille kentille.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Kirjautumisen lähettäminen {#login-submission}

Kun käyttäjät syöttävät käyttäjänimensä ja salasanansa, `Login`-komponentti validoi nämä syötteet pakollisiksi kentiksi. Kun validointi on onnistunut, lomakkeen lähetys tapahtuu, ja syötetyt tunnistetiedot toimitetaan. Useiden lähetyksien estämiseksi [Kirjaudu sisään] -painike on heti poistettu käytöstä.

Seuraavassa kuvataan yksinkertaista `Login`-komponenttia. Jos käyttäjänimi ja salasana on asetettu molemmat arvoksi `"admin"`, kirjautumisvalintaikkuna sulkeutuu, ja [Kirjaudu ulos] -painike ilmestyy. Jos tunnistetiedot eivät täsmää, oletusvirheilmoitus näytetään.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info [Kirjaudu sisään] -painikkeen poistaminen käytöstä
Oletuksena `Login` poistaa heti käytöstä [Kirjaudu sisään] -painikkeen, kun komponentti validoi kirjautumissyötteet oikein useiden lähetyksien estämiseksi. Voit ottaa [Kirjaudu sisään] -painikkeen uudelleen käyttöön käyttämällä `setEnabled(true)` -metodia.
:::

:::tip Tyhjien salasanojen salliminen
Voit sallia käyttäjien kirjautuvan sisään pelkän käyttäjänimen avulla käyttämällä `setEmptyPassword(true)` -metodia.
:::

## Lomake toiminto <DocChip chip='since' label='25.10' />{#form-action}

`Login`-komponentti voi lähettää lomakedataa suoraan määritettyyn URL-osoitteeseen sen sijaan, että se käsittelisi lähettämistä lomakkeen lähetys tapahtuman kautta. Kun toimint URL on asetettu, lomake suorittaa standardin POST-pyynnön käyttäjänimen ja salasanan muodossa.

```java
Login login = new Login();
login.setAction("/api/auth");
```

Kun käytetään `setAction()`, lomakkeen lähetys ohittaa `LoginSubmitEvent`-tapahtuman ja suorittaa sen sijaan perinteisen HTTP POST -pyynnön määritettyyn päätepisteeseen. Käyttäjänimi ja salasana lähetetään lomakkeen parametreina, jotka on nimetty "username" ja "password", vastaavasti. Mukautetut kentät, joilla on nimeämiselementti, sisällytetään myös POST-pyyntöön.

:::tip 
Jos toimint URL:ää ei ole asetettu, lomakkeen lähetys käsitellään `LoginSubmitEvent`-tapahtuman kautta, jolloin voit käsitellä tunnistetietoja ohjelmallisesti palvelinpuolella.
:::

## Kansainvälisyys (i18n) {#internationalization-i18n}

`Login`-komponentin otsikot, kuvaukset, tarrat ja viestit ovat täysin mukautettavissa `LoginI18n`-luokan avulla. Tämä joustavuus mahdollistaa kirjautumisliittymän räätälöimisen tiettyjen lokalisointi vaatimusten tai henkilökohtaisten mieltymysten mukaan.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Mukautetut kentät {#custom-fields}

`Login`-komponentti sisältää useita slotteja, jotka mahdollistavat ylimääräisten kenttien lisäämisen tarvittaessa. Mukautetut kentät kerätään automaattisesti, kun lomake lähetetään, ja niitä voidaan käsitellä lähetys tapahtuman datakartalla.

Seuraavassa kirjautumisessa on lisätty mukautettu kenttä asiakas-ID:lle. Tämä voi auttaa hallitsemaan yrityksiä tai osastoja, joilla on yhteistä sisältöä useiden käyttäjien kesken.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Nimi vaaditaan
Mukautettujen kenttien on oltava nimettyinä `setName()`-metodilla, jotta ne voitaisiin sisällyttää lomakkeen lähetykseen. Nimi käytetään avaimena kentän arvon hakemiseen `event.getData()` -menetelmällä.
:::

## Peruuta-painike {#cancel-button}

`Login`-komponentti sisältää [Peruuta]-painikkeen, joka on oletuksena piilotettu. Tämä on erityisen hyödyllistä, kun käyttäjä yrittää päästä sovelluksen rajoitetulle alueelle ja tarvitsee vaihtoehdon palata edelliseen sijaintiinsa ilman kirjautumista.

Jotta peruuta-painike olisi näkyvissä, anna sille etiketti. Voit myös kuunnella peruuttamistapahtumia käsitelläksesi peruuttamista asianmukaisesti.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementtien piilottaminen
Jos haluat piilottaa elementin, aseta sen etiketti tyhjään merkkijonoon. Tämä mahdollistaa näkyvyyden vaihtamisen poistamatta komponenttia koodistasi.
:::

## Salasananhallintajärjestelmät {#password-managers}

Tämä komponentti toimii selaimen salasanan hallintajärjestelmien kanssa kirjautumisprosessin yksinkertaistamiseksi. Chromium-pohjaisissa selaimissa se integroituu [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:iin, joka tarjoaa:

- **Automaattinen täyttö**: Selain saattaa automaattisesti täyttää käyttäjänimi- ja salasanakentät, jos käyttäjällä on tallennettuja tunnistetietoja sivustolle.
- **Tunnistetietojen hallinta**: Kirjauduttuaan selain voi kehottaa käyttäjää tallentamaan uusia tunnistetietoja, mikä tekee tulevista kirjautumisista nopeampia ja helpompia.
- **Tunnistetietojen valinta**: Jos useita tunnistetietoja on tallennettu, selain voi tarjota käyttäjälle mahdollisuuden valita yhdestä tallennetuista sarjoista.

## Tyylittely {#styling}

<TableBuilder name="Login" />
