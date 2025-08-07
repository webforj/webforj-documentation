---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: dfbf68fb580d6fb1622f513be8983739
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

webforJ toimittaa kattavan suunnittelujärjestelmän nimeltä **DWC**. Se on enemmän kuin pelkkä teema, se on jäsennetty, laajennettavissa oleva järjestelmä, joka hallitsee sovelluksesi visuaalista kieltä. DWC on rakennettu auttamaan kehittäjiä ja suunnittelijoita luomaan johdonmukaisia, brändin mukaisia käyttöliittymiä nopeasti ja varmasti.

DWC:n ytimessä on joukko huolellisesti suunniteltuja CSS-muuttujia (suunnittelutunnisteita), jotka kattavat keskeiset visuaaliset elementit kuten värit, typografian, reunat ja välin. Nämä tunnisteet toimivat kaikkien komponenttityylien perustavanlaatuisina rakennuspalikoina ja mahdollistavat globaalin mukauttamisen vähällä vaivalla.

Tuodakseen tukea kehittyneempään tyylittelyyn, webforJ hyödyntää CSS Shadow Partsia, jolloin komponenttien sisäosia voidaan muokata valikoivasti rikkomatta kapselointia. Tämä antaa tiimeille tarkkaahallintaa siitä, miltä komponentit näyttävät, jopa laajemmissa sovelluksissa.

DWC sisältää myös mukautettavan väri-paletin ja oletuksena on siisti, vaalea visuaalinen teema, mutta jokainen osa voidaan mukauttaa brändisi tai tuotetyylisi mukaan.

## Figma Design Kit {#figma-design-kit}

[DWC Figma -kirjasto](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) on virallinen suunnitteluresurssi modernien, yritystasojen verkkosovellusten luomiseen. Se sisältää kattavan joukon komponentteja, typografiatyylejä ja väritunnisteita, jotka ovat yhteensopivia DWC-suunnittelujärjestelmän kanssa. Tämän kirjaston avulla suunnittelijat ja kehittäjät voivat rakentaa visuaalisesti johdonmukaisia, käyttäjäystävällisiä käyttöliittymiä, jotka tasapainottavat toimivuutta hienostuneen käyttäjäkokemuksen kanssa.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Aiheet {#topics}

<DocCardList className="topics-section" />
