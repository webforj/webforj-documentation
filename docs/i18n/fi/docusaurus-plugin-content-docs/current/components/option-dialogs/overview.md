---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 7ff00c0abd564956da84fbd20761413e
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Valikkodit
<!-- vale on -->

Valikkodit tarjoavat tavan sovelluksen kommunikoida käyttäjien kanssa ja kerätä heidän palautettaan. Nämä dialogit ovat modaalisia, mikä tarkoittaa, että ne estävät sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa niiden kanssa, varmistaen, että tärkeät viestit käsitellään ennen jatkamista.

Valikkodit webforJ:ssa ovat samankaltaisia kuin `JOptionPane` Swingissä, ratkaisten perusongelman, joka liittyy estäviin dialogeihin verkkosovelluksissa.

:::tip Modaalisuus
Kun käytetään valikkodien luomiseen modaalisia dialogeja webforJ:ssa, dialogi estää käyttäjän syötteen muihin sovelluksen osiin ja käsittelee tapahtumia ainoastaan modaalille dialogille. Tämä varmistaa, että dialogi pysyy responsiivisena estäen samalla vuorovaikutukset muiden osien kanssa, parantaen käyttäjäkokemusta ja yllä pitäen sovelluksen virtausta. Palvelin lakkaa käsittelemästä muita pyyntöjä, kunnes dialogi suljetaan tai sieltä palautuu arvo.
:::

## Aiheita {#topics}

<DocCardList className="topics-section" />
