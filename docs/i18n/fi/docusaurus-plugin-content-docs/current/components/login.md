---
title: Login
sidebar_position: 70
_i18n_hash: b95b5a072de318071d9d7ecae890a883
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Login-komponentti on suunniteltu tarjoamaan käyttäjäystävällinen käyttöliittymä todennusta varten, jolloin käyttäjät voivat kirjautua sisään käyttäjätunnuksen ja salasanan avulla. Se tukee erilaisia mukautuksia käyttäjäkokemuksen parantamiseksi eri laitteilla ja kielillä.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Käytöt {#usages}

Login-komponentti tarjoaa käyttäjäystävällisen kirjautumislomakkeen käyttöliittymän dialogissa todennusasiakirjojen syöttämistä varten. Se parantaa käyttäjäkokemusta tarjoamalla:
   >- Selkeät syöttökentät käyttäjätunnukselle ja salasanalle.
   >- Näkyvyyden vaihto salasanalle syötteen vahvistamiseksi.
   >- Syöttövalidointipalautetta oikean muotoilun varmistamiseksi ennen lähettämistä.

## Kirjautumisen lähettäminen {#login-submission}

Kun käyttäjät syöttävät käyttäjätunnuksensa ja salasanansa, kirjautumis komponentti validoi nämä syötteet pakollisina kenttinä. Kun validointi on läpäisty, lomakkeen lähettämistapahtuma laukaistaan, ja syötetyt asiakirjat toimitetaan. Estääkseen useita lähetyksiä `Signin`-painike poistetaan välittömästi käytöstä.

Alla oleva demo havainnollistaa perustavanlaatuista lomakkeen lähettämisprosessia. Jos käyttäjätunnus ja salasana on asetettu molemmat arvoksi `"admin"`, kirjautumisdialogi sulkeutuu ja kirjautumisnappi tulee näkyviin. Jos asiakirjat eivät sovi yhteen, kirjautumislomakkeen oletusvirheilmoitus näytetään.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Signin-painikkeen poistaminen käytöstä
Oletuksena kirjautumislomake poistaa välittömästi käytöstä `Signin`-painikkeen, kun komponentti validoi kirjautumissyötteet oikein useiden lähetyksen estämiseksi. Voit ottaa `Signin`-painikkeen uudelleen käyttöön käyttämällä menetelmää `setEnabled(true)`.
:::

:::tip Tyhjien salasanojen salliminen
Tietyissä tilanteissa tyhjät salasanat voivat olla sallittuja, jolloin käyttäjät voivat kirjautua sisään vain käyttäjätunnuksella. Kirjautumisdialogia voidaan määrittää hyväksymään tyhjät salasanat asettamalla `setEmptyPassword(true)`.
:::

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Kirjautumiskomponentin otsikot, kuvastot, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `LoginI18n`-luokkaa. Tämä joustavuus antaa sinulle mahdollisuuden räätälöidä kirjautumisen käyttöliittymä vastaamaan erityisiä lokalisointi vaatimuksia tai personointitoiveita.

Alla oleva demo havainnollistaa, kuinka kirjautumisdialogille voidaan antaa saksankielinen käännös varmistaen, että kaikki käyttöliittymäelementit mukautetaan saksan kielelle parantaakseen käyttäjäkokemusta saksankielisille käyttäjille.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Mukautetut kentät {#custom-fields}

Kirjautumiskomponentti sisältää useita slotteja, jotka antavat sinun lisätä ylimääräisiä kenttiä tarpeen mukaan. Tämä ominaisuus tarjoaa enemmän hallintaa tarvittavasta tiedosta onnistuneen todennuksen varmistamiseksi.

Alla olevassa esimerkissä asiakas-ID-kenttä lisätään kirjautumislomakkeeseen. Käyttäjien on annettava voimassa oleva ID todentamisen suorittamiseksi parantaen turvallisuutta ja varmistaen, että pääsy myönnetään vain kaikkien vaadittavien asiakirjojen vahvistamisen jälkeen.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Lähetyksen kuorma
Huomaa, että kirjautumiskomponentti ei automaattisesti tunnista tai sisällä ylimääräisiä kenttiä, jotka on lisätty lomakkeeseen sen lähetyskuormassa. Tämä tarkoittaa, että kehittäjien on nimenomaisesti haettava lisäkenttien arvot asiakassivulta ja käsiteltävä niitä sovelluksen vaatimusten mukaisesti todentamisprosessin loppuunsaattamiseksi.
:::

## Peruuta-painike {#cancel-button}

Tietyissä tilanteissa voi olla toivottavaa lisätä peruuta-painike `Signin`-painikkeen rinnalle. Tämä ominaisuus on erityisen hyödyllinen, kun käyttäjä yrittää päästä rajoitettuun sovelluksen alueeseen ja tarvitsee mahdollisuuden peruuttaa toiminto ja palata edelliseen sijaintiinsa. Kirjautumislomakkeella on oletuksena peruuta-painike, mutta se on piilotettu näkyvistä.

Jotta peruuta-painike tulee näkyviin, sinun on annettava sille etiketti - kun se on nimetty, se näkyy näytöllä. Voit myös kuunnella peruutustapahtumia vastataksesi käyttäjän toimintaan varmistaaksesi sujuvan ja käyttäjäystävällisen kokemuksen sovelluksessa navigoimisessa.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementtien piilottaminen
Jotta voit piilottaa elementin kirjautumissivulta, aseta sen etiketti vain tyhjään merkkijonoon. Tämä lähestymistapa on erityisen hyödyllinen käyttöliittymäkomponenttien tilapäiseen poistamiseen ilman, että koodipohjaa tarvitsisi pysyvästi muuttaa.
:::

## Salasananhallintajärjestelmät {#password-managers}

Kirjautumiskomponentti on suunniteltu yhteensopivaksi selainpohjaisten salasananhallintajärjestelmien kanssa, parantaakseen käyttäjäkokemusta yksinkertaistamalla kirjautumisprosessia. Chromium-pohjaisten selainten käyttäjille komponentti integroituu saumattomasti [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) API:n kanssa. Tämä integraatio mahdollistaa useita käteviä ominaisuuksia:

- **Automaattinen täyttö**: Selaimessa voi automaattisesti täyttää käyttäjätunnus- ja salasanakentät, jos käyttäjä on tallentanut asiakirjat sivustolle.
- **Asiakirjojen hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia asiakirjoja, jolloin tulevat kirjautumiset ovat nopeampia ja helpompia.
- **Asiakirjojen valinta**: Jos useita asiakirjoja on tallennettuna, selain voi tarjota käyttäjälle mahdollisuuden valita yhdestä tallennetusta asiakirjasta.

## Tyylittely {#styling}

<TableBuilder name="Login" />
