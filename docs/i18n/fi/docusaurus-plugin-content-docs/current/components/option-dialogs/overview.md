---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Valintapainikkeet
<!-- vale on -->

Valintapainikkeet tarjoavat sovellukselle tavan kommunikoida käyttäjien kanssa ja kerätä heidän palautettaan. Nämä painikkeet ovat modaalisia, mikä tarkoittaa, että ne estävät sovelluksen suorituksen, kunnes käyttäjä on vuorovaikutuksessa niiden kanssa, varmistaen, että tärkeitä viestejä käsitellään ennen jatkamista.

Valintapainikkeet webforJ:ssä ovat samankaltaisia kuin `JOptionPane` Swingissä, ratkaisten perusongelman esteiden käsittelyssä web-sovelluksissa.

:::tip Modaalisuus
Kun käytät valintapainikkeita luodaksesi modaalisia painikkeita webforJ:ssä, painike estää käyttäjäsyötteen muilta osilta sovellusta ja käsittelee tapahtumia ainoastaan modaalin painikkeen osalta. Tämä varmistaa, että painike pysyy responsiivisena ja estää vuorovaikutukset muiden osien kanssa, parantaen käyttäjäkokemusta ja ylläpitäen sovelluksen kulkua. Palvelin lopettaa muiden pyyntöjen käsittelyn, kunnes painike hylätään tai siitä palautuu arvo.
:::

## Aihealueet {#topics}

<DocCardList className="topics-section" />
