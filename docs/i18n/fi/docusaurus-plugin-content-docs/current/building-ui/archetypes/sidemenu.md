---
title: SideMenu
description: >-
  Luo SideMenu-arkkityypistä webforJ-monisivusovelluksen runko, jossa on
  sivupalkkinavigointi ja reititetty sisältöalue.
sidebar_position: 3
hide_table_of_contents: true
_i18n_title_hash: e4e66895677ea3ae16fd61b90150788e
_i18n_description_hash: c37d5764a23ecbe44c75040d3e64c74d
_i18n_hash: a8150cceeca3327741d5f44c557df167
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# SideMenu -malli
<!-- vale on -->

Projekteille, jotka tarvitsevat rakenteellista navigointijärjestelmää, `sidemenu` -malli on loistava aloituspaikka. Tämä malli sisältää sivupaneelin ja sisältöalueen, ja se on suunniteltu auttamaan sinua luomaan sovelluksia, joissa on selkeä ja intuitiivinen navig structure, jolloin käyttäjien on helpompi löytää ja käyttää sovelluksesi eri osia.

:::tip Käyttäen startforJ
Jos tarvitset enemmän hallintaa mukauttamiseen ja konfigurointiin, voit käyttää [startforJ](https://docs.webforj.com/startforj/) luodaksesi projektisi – valitse vain `SideMenu` -malli, kun valitset konfigurointivaihtoehtoja.
:::

## Käyttäen `sidemenu` -mallia {#using-the-sidemenu-archetype}

<ComponentArchetype
project="sidemenu"
/>
