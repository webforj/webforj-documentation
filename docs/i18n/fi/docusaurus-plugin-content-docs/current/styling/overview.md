---
title: Styling
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 04ddb356576ffb59456111d5b45fd4da
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

webforJ toimittaa kattavan suunnittelujärjestelmän nimeltä **DWC**. Se on enemmän kuin vain teema; se on jäsennelty, laajennettavissa oleva järjestelmä, joka ohjaa sovelluksesi visuaalista kieltä. DWC on rakennettu auttamaan kehittäjiä ja suunnittelijoita luomaan johdonmukaisia, brändin mukaisia käyttöliittymiä nopeasti ja luottavaisesti.

DWC:n ytimessä on kokoelma huolellisesti suunniteltuja CSS-muuttujia (suunnittelutokeneita), jotka kattavat keskeiset visuaaliset elementit, kuten värit, typografian, reunat ja väliin. Nämä tokenit toimivat kaikkien komponenttityylien perustana ja mahdollistavat globaaleja mukautuksia vähäisellä vaivalla.

Tukeakseen edistyneempiä tyylittelyjä webforJ hyödyntää CSS Shadow Parts -ominaisuutta, jonka avulla komponenttien sisäosat voidaan valikoivasti tyylittää ilman kapseloinnin rikkomista. Tämä antaa tiimeille tarkkaa hallintaa siitä, miltä komponentit näyttävät, jopa laajemmissa sovelluksissa.

DWC:ssä on myös mukautettava väripaletti, ja se käyttää oletuksena puhdasta, vaaleaa visuaalista teemaa, mutta jokainen osa voidaan mukauttaa brändisi tai tuotteesi tyyliin.

## Topics {#topics}

<DocCardList className="topics-section" />
