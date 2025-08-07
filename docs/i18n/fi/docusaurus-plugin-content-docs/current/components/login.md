---
title: Login
sidebar_position: 70
_i18n_hash: f2f1f96cfde1dbbede5bfdaafd3f0a92
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-login" />
<DocChip chip='since' label='24.01' />
<JavadocLink type="login" location="com/webforj/component/login/Login" top='true'/>

Login-komponentti on suunniteltu tarjoamaan käyttäjäystävällinen käyttöliittymä autentikointia varten, mikä mahdollistaa käyttäjien kirjautumisen käyttäjätunnuksella ja salasanalla. Se tukee erilaisia mukautuksia käyttäjäkokemuksen parantamiseksi eri laitteilla ja kielialueilla.

<ComponentDemo 
path='/webforj/loginbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginBasicView.java'
height = '450px'
/>

## Käytöt {#usages}

Login-komponentti tarjoaa käyttäjäystävällisen kirjautumislomakkeen käyttöliittymän dialogissa autentikointitietojen syöttämiseen. Se parantaa käyttäjäkokemusta tarjoamalla:
   >- Selkeät syöttökentät käyttäjätunnukselle ja salasanalle.
   >- Salasanasalasanan näkyvyyden vaihto syötteen tarkistamiseksi.
   >- Syöttövalidointipalautteen oikean muodon varmistamiseksi ennen lähettämistä.

## Kirjautumisen lähettäminen {#login-submission}

Kun käyttäjät syöttävät käyttäjätunnuksensa ja salasanansa, kirjautumiskomponentti validoi nämä syötteet pakollisina kenttinä. Kun validointi onnistuu, lomakkeen lähettämistapahtuma laukaistaan, jolloin syötetyt tiedot toimitetaan. Useiden lähettämisten estämiseksi `Signin`-painike disableerataan heti.

Alla oleva demonstraatio havainnollistaa yksinkertaista lomakkeen lähettämisprosessia. Jos käyttäjätunnus ja salasana on molemmat asetettu arvoon `"admin"`, kirjautumisdialogi sulkeutuu ja uloskirjautumispainike ilmestyy. Jos todisteet eivät täsmää, ilmestyy lomakkeen oletusvirheilmoitus.

<ComponentDemo 
path='/webforj/loginsubmission?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginSubmissionView.java'
height = '450px'
/>

:::info Kirjautumispainikkeen poistaminen käytöstä
Oletuksena kirjautumislomake poistaa heti käytöstä `Signin`-painikkeen heti, kun komponentti validoi kirjautumistiedot oikein, estääkseen useita lähetyksiä. Voit aktivoida `Signin`-painikkeen uudelleen käyttämällä `setEnabled(true)`-metodia.
:::

:::tip Tyhjien salasanojen salliminen
Tietyissä tilanteissa tyhjät salasanat voivat olla sallittuja, jolloin käyttäjät voivat kirjautua sisään vain käyttäjätunnuksella. Kirjautumisdialogia voidaan konfiguroida hyväksymään tyhjät salasanat asettamalla `setEmptyPassword(true)`.
:::

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Kirjautumiskomponentin otsikot, kuvaukset, etiketit ja viestit ovat täysin mukautettavissa `LoginI18n`-luokan avulla. Tämä joustavuus mahdollistaa sen, että voit räätälöidä kirjautumisliittymän vastaamaan erityisiä lokalisointivaatimuksia tai henkilökohtaisia mieltymyksiä.

Alla oleva demonstraatio havainnollistaa, miten kirjautumisdialogille voidaan tarjota saksankielinen käännös, varmistaen, että kaikki käyttöliittymän elementit mukautuvat saksan kieleen parantaen käyttäjäkokemusta saksankielisille käyttäjille.

<ComponentDemo 
path='/webforj/logininternationalization?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginInternationalizationView.java'
height = '500px'
/>

## Mukautetut kentät {#custom-fields}

Kirjautumiskomponentti sisältää useita paikkoja, jotka mahdollistavat lisäkenttien lisäämisen tarpeen mukaan. Tämä ominaisuus antaa enemmän hallintaa tarvittavista tiedoista onnistuneeseen autentikointiin.

Alla olevassa esimerkissä kirjautumislomakkeeseen on lisätty Asiakas-ID-kenttä. Käyttäjien on annettava voimassa oleva ID täydentääkseen autentikoinnin, parantaen turvallisuutta ja varmistaen, että pääsy myönnetään vain kaikkien pakollisten todisteiden tarkistamisen jälkeen.

<ComponentDemo 
path='/webforj/logincustomfields?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCustomFieldsView.java'
cssURL='/css/login/loginCustomFields.css'
height = '700px'
/>

:::info Lähettämisen käyttökuorma
Huomaa, että kirjautumiskomponentti ei automaattisesti tunnista tai sisällytä lomakkeeseen lisättyjä kenttiä sen lähettämisen kuormaan. Tämä tarkoittaa, että kehittäjien on noudettava eksplisiittisesti lisäkenttien arvo asiakaspinnasta ja käsiteltävä se sovelluksen vaatimusten mukaan autentikointiprosessin loppuun saattamiseksi.
:::

## Peruuta-painike {#cancel-button}

Tietyissä tilanteissa voi olla toivottavaa lisätä peruuta-painike `Signin`-painikkeen rinnalle. Tämä ominaisuus on erityisen hyödyllinen, kun käyttäjä yrittää päästä sovelluksen rajoitettuun alueeseen ja tarvitsee vaihtoehdon peruuttaa toiminto ja palata aikaisempaan sijaintiinsa. Kirjautumislomakkeessa on oletuksena peruuta-painike, mutta se on piilotettu.

Jotta peruuta-painike näkyy, sinun on annettava sille etiketti - vahvistettuasi se näkyy näytöllä. Voit myös kuunnella peruuttamistapahtumia reagoidaksesi käyttäjän toimintoihin asianmukaisesti, varmistaen sujuvan ja käyttäjäystävällisen kokemuksen sovelluksen navigoinnissa.

<ComponentDemo 
path='/webforj/logincancelbutton?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/login/LoginCancelButtonView.java'
height = '450px'
/>

:::tip Elementtien piilottaminen
Piilottaaksesi elementin kirjautumisruudulta, aseta sen etiketti tyhjäksi. Tämä lähestymistapa on erityisen hyödyllinen, kun halutaan poistaa käyttöliittymärakenteet väliaikaisesti ilman, että koodipohjaa muutetaan pysyvästi.
:::

## Salasananhallinta {#password-managers}

Kirjautumiskomponentti on suunniteltu yhteensopivaksi selainpohjaisten salasananhallintaratkaisujen kanssa, parantaen käyttäjäkokemusta yksinkertaistamalla kirjautumisprosessia. Chromium-pohjaisten selaimien käyttäjille komponentti integroituu saumattomasti [`PasswordCredential`](https://developer.mozilla.org/en-US/docs/Web/API/PasswordCredential) APIin. Tämä integraatio mahdollistaa useita käteviä ominaisuuksia:

- **Automaattinen täyttö**: Selain voi automaattisesti täyttää käyttäjätunnus- ja salasanakentät, jos käyttäjällä on tallennettuja todisteita sivustolle.
- **Todistusten hallinta**: Kirjautumisen jälkeen selain voi kehottaa käyttäjää tallentamaan uusia todisteita, mikä tekee tulevista kirjautumisista nopeampia ja helpompia.
- **Todistusten valinta**: Jos useita todisteita on tallennettu, selain voi tarjota käyttäjälle vaihtoehdon valita yhdestä tallennetuista sarjoista.

## Tyylittely {#styling}

<TableBuilder name="Login" />
