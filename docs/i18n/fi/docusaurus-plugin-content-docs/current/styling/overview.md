---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 4e709dc1db793a4ae1ed6f944375b512
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

webforJ toimittaa kattavan suunnittelujärjestelmän nimeltä **DWC**. Se on enemmän kuin vain teema, se on rakenteellinen, laajennettavissa oleva järjestelmä, joka hallitsee sovelluksesi visuaalista kieltä. DWC on suunniteltu auttamaan kehittäjiä ja suunnittelijoita luomaan nopeasti ja varmasti johdonmukaisia, brändiin sidottuja käyttöliittymiä.

DWC:n ytimessä on joukko huolellisesti suunniteltuja CSS-muuttujia (suunnittelutokeneita), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunat ja välistykset. Nämä tokenit toimivat kaikille komponenttityyleille perustavanlaatuisina rakennuspalikoina ja mahdollistavat globaalin mukauttamisen vähällä vaivalla.

Tukeakseen edistyneempää tyylittelyä, webforJ hyödyntää CSS Shadow Parts -tekniikkaa, mikä mahdollistaa komponenttien sisäisten osien valikoivan tyylittelyn ilman kapseloinnin rikkomista. Tämä antaa tiimeille tarkkaa hallintaa siitä, miltä komponentit näyttävät, jopa suuremmissa sovelluksissa.

DWC sisältää myös mukautettavan väriportfolion ja perustuu puhtaaseen, vaaleaan visuaaliseen teemaan, mutta jokainen osa voidaan mukauttaa brändisi tai tuotteesi tyyliin.

## Figma Design Kit {#figma-design-kit}

[DWC Figma -kirjasto](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) on virallinen suunnitteluresurssi nykyaikaisten, yritysluokan web-sovellusten luomiseen. Se sisältää kattavan joukon komponentteja, typografiastylejä ja väritokeneita, jotka ovat linjassa DWC-suunnittelujärjestelmän kanssa. Tämän kirjaston avulla suunnittelijat ja kehittäjät voivat rakentaa visuaalisesti johdonmukaisia, käyttäjäystävällisiä käyttöliittymiä, jotka tasapainottavat toiminnallisuuden hienostuneen käyttäjäkokemuksen kanssa.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

## Aihealueet {#topics}

<DocCardList className="topics-section" />
