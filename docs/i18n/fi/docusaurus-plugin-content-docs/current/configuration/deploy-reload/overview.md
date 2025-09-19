---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: 2e8bf7fded04e11ec6bab6d8a7c1c2b5
---
Tehokkaat kehitysprosessit riippuvat työkaluista, jotka havaitsevat koodimuutokset ja päivittävät sovelluksen automaattisesti reaaliaikaisesti. Jatkuva käyttöönottaminen ja dynaaminen uudelleenlataus toimivat yhdessä yksinkertaistaen kehitysprosessia vähentämällä manuaalisia vaiheita, jolloin voit nähdä muutoksesi nopeasti ilman, että palvelinta tarvitsee käynnistää manuaalisesti uudelleen.

## Uudelleen käyttöönotto {#redeployment}

Uudelleen käyttöönotto Java-kehityksessä tarkoittaa koodimuutosten automaattista havaitsemista ja käyttöönottoa, jotta päivitykset näkyvät sovelluksessa ilman manuaalista palvelimen uudelleenkäynnistystä. Tämä prosessi sisältää yleensä Java-luokkien ja verkkoresurssien päivittämisen lennossa.

WebforJ-sovelluksessa tämä tarkoittaa WAR-tiedoston uudelleenluomista aina kun koodiin tehdään muutoksia.

Muutoksia Java-luokissa ja resurssit, jotka ovat luokkatiessä, seurataan yleensä IDE:n toimesta. Kun Java-luokkaa muutetaan ja tiedosto tallennetaan, joko IDE:n automaattisesti tai kehittäjän toimesta, nämä työkalut aktivoituvat kääntämään ja sijoittamaan päivitetyt luokkafileet kohdekansioon, jotta muutokset voidaan toteuttaa.

Työkaluja ja asetuksia, jotka automatisoivat tai optimoivat selaimen uudelleenlataamista, voidaan lisätä sujuvamman kokemuksen saamiseksi.

## Reaaliaikainen lataus {#live-reload}

Reaaliaikainen lataus varmistaa, että kun muutokset on otettu käyttöön, selain heijastaa näitä päivityksiä reaaliajassa ilman manuaalista selaimen virkistystä.

WebforJ-sovelluksessa reaaliaikainen lataus voi automaattisesti päivittää näkymän, piirtäen komponentit uudelleen näyttämään sovelluksen uusimman tilan tai jopa korjaamaan muutoksia tarpeen mukaan.

## Aiheet {#topics}

<DocCardList className="topics-section" />
