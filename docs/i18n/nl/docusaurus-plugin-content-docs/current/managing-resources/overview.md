---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

Toepassingen zijn afhankelijk van verschillende soorten bronnen, zoals JavaScript, CSS en afbeeldingen. Dit document biedt een uitgebreide technische verkenning van de resource-afhandelingsmechanismen van webforJ, met inbegrip van declaratieve annotaties, programmatic API-methoden en het gebruik van aangepaste protocollen.  

webforJ hanteert een modulaire aanpak voor resourcebeheer en biedt meerdere mechanismen om aan verschillende behoeften van apps te voldoen:  

- **Frontend bundelaar**: Breng npm-pakketten, componentenframeworks en stylesheettalen in de app via een gecompileerde ingang. Dit is het standaardpad voor frontend-assets en het doet alles wat de annotaties doen.  
- **Declaratieve Annotaties**: Voeg JavaScript- en CSS-bronnen in op het niveau van componenten of apps, zonder build-stap.  
- **API-gebaseerde Dynamische Injectie**: Injecteer bronnen tijdens runtime om dynamisch app-gedrag mogelijk te maken.  
- **Aangepaste Protocollen**: Bied gestandaardiseerde methodologieën voor toegang tot bronnen.  
- **Bestandstreaming en Gecontroleerde Download**: Faciliteer het beheerde ophalen en verzenden van bronbestanden.  

## Topics {#topics}

<DocCardList className="topics-section" />
