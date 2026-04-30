---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: abb693dec702e4a253cf4e1228fb2d7e
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

webforJ toimitetaan kattavan suunnittelujärjestelmän kanssa nimeltä **DWC**. Se on enemmän kuin vain teema, se on rakennettu, laajennettavissa oleva järjestelmä, joka hallitsee sovelluksesi visuaalista kieltä. DWC on suunniteltu auttamaan kehittäjiä ja suunnittelijoita luomaan johdonmukaisia, brändin mukaisia käyttöliittymiä nopeasti ja varmasti.

DWC:n ytimessä on joukko huolellisesti suunniteltuja CSS-muuttujia (suunnittelutunnisteita), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunat ja väliyhteydet. Nämä tunnisteet toimivat kaikkien komponenttityylien perustavanlaatuisina rakennuspalikoina ja mahdollistavat globaalin mukauttamisen vähällä vaivalla.

Edistyneemmän tyylittelyn tukemiseksi webforJ hyödyntää CSS Shadow Partsia, mikä mahdollistaa komponenttien sisäisten osien valikoidun tyylittelyn ilman kapseloinnin rikkomista. Tämä antaa tiimeille tarkkaa hallintaa siitä, kuinka komponentit näkyvät, jopa suuremmissa sovelluksissa.

DWC sisältää myös muokattavan väri-kartan ja oletuksena puhtaan, vaalean visuaalisen teeman, mutta jokaista aspektia voidaan mukauttaa brändisi tai tuotteesi tyyliin.

<AISkillTip skill="webforj-styling-apps" />

## Figma design kit {#figma-design-kit}

[DWC Figma -kirjasto](https://www.figma.com/community/file/1144573845612007198/dwc-design-kit) on virallinen suunnitteluresurssi modernien, yritystasojen verkkosovellusten luomiseen. Se sisältää kattavan joukon komponentteja, typografiatyylejä ja väritunnisteita, jotka ovat linjassa DWC:n suunnittelujärjestelmän kanssa. Suunnittelijat ja kehittäjät voivat käyttää tätä kirjastoa rakentaakseen visuaalisesti johdonmukaisia, käyttäjäystävällisiä käyttöliittymiä ennakoitavalla komponenttikäytöksellä, tarkalla väliyhteydellä ja saavutettavalla väri-eron kanssa.

<img src="/img/dwc.png" alt="Figma Design Kit" style={{borderRadius: '8px', border: '1px solid var(--dwc-color-default)'}} />

>  
<!-- > ![Figma Design Kit Screenshot](./path-to-your-screenshot.png) -->

## Topics {#topics}

<DocCardList className="topics-section" />
