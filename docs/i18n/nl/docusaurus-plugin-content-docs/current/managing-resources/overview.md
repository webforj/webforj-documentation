---
sidebar_position: 1
title: Bronnen beheren
description: >-
  Beheer JavaScript, CSS en andere assets in webforJ-apps met declaratieve
  annotaties, runtime-injectie-API's en aangepaste URL-protocollen.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_title_hash: a71e8aa6e3aa0dbb165119f7b68682a3
_i18n_description_hash: 1c0c8794c1ad64d279c287d021118800
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
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

Toepassingen zijn afhankelijk van verschillende soorten middelen, zoals JavaScript, CSS en afbeeldingen. Dit document biedt een uitgebreide technische verkenning van de middelenbeheersmechanismen van webforJ, met inbegrip van declaratieve annotaties, programmatic API-methoden en het gebruik van aangepaste protocollen.

webforJ hanteert een modulaire benadering van middelenbeheer en biedt meerdere mechanismen om aan verschillende behoeften van de app te voldoen:

- **Frontend-bundelaar**: Breng npm-pakketten, componentframeworks en stylesheettalen in de app via een gecompileerde invoer. Dit is het standaardpad voor frontend-assets en het doet alles wat de annotaties doen.
- **Declaratieve Annotaties**: Voeg JavaScript- en CSS-middelen toe op component- of app-niveau, zonder bouwstap.
- **API-gebaseerde Dynamische Injectie**: Injecteer middelen tijdens runtime om dynamisch app-gedrag mogelijk te maken.
- **Aangepaste Protocollen**: Bied gestandaardiseerde methodologieën voor toegang tot middelen.
- **Bestandsstreaming en gecontroleerde downloads**: Vergemakkelijk het beheerde ophalen en verzenden van middelenbestanden.

## Topics {#topics}

<DocCardList className="topics-section" />
