---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
Tehokkaat kehitystyökalut luottavat työkaluun, joka havaitsee koodimuutoksia ja päivittää sovelluksen automaattisesti reaaliajassa. Jatkuva julkaisu ja dynaaminen lataus toimivat yhdessä yksinkertaistaakseen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutoksesi nopeasti ilman, että palvelinta tarvitsee käynnistää manuaalisesti uudelleen.

## Uudelleen käyttöönotto {#redeployment}

Uudelleen käyttöönotto Java-kehityksessä tarkoittaa koodimuutosten automaattista havaitsemista ja julkaisemista, jotta päivitykset näkyvät sovelluksessa ilman manuaalista palvelimen uudelleenkäynnistystä. Tämä prosessi sisältää tyypillisesti Java-luokkien ja verkkoresurssien päivittämisen lennossa.

WebforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston uudelleenluomista aina, kun koodiin tehdään muutoksia.

Muutoksia Java-luokkiin ja resurssien luetteloon seurataan tyypillisesti IDE:n toimesta. Kun Java-luokkaa muokataan ja tiedosto tallennetaan, joko IDE:n toimesta automaattisesti tai kehittäjän manuaalisesti, nämä työkalut aktivoituvat kääntämään ja sijoittamaan päivitettyjä luokkatiiedostoja kohdekansioon muutosten soveltamiseksi.

Työkaluja ja asetuksia, jotka automatisoivat tai optimoivat selaimen uudelleen lataamista, voidaan lisätä sujuvampaa käyttökokemusta varten.

## Live-uudelleentoiminta {#live-reload}

Live-uudelleentoiminta varmistaa, että kun muutoksia on julkaistu, selain heijastaa näitä päivityksiä reaaliaikaisesti ilman, että selainta tarvitsee päivittää manuaalisesti.

WebforJ-sovelluksessa live-uudelleentoiminta voi automaattisesti päivittää näkymän, renderöimällä komponentteja näyttämään sovelluksen viimeisimmän tilan, tai jopa patch-muutoksen tarpeen mukaan kysynnästä.

## Aiheet {#topics}

<DocCardList className="topics-section" />
