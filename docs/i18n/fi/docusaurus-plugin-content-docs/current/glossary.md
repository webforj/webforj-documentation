---
sidebar_position: 1000
title: Glossary
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: 5567c28b0575afa1ce7a9fcafcbe429c
---
## DOM {#dom}

DOM (Document Object Model) on ohjelmointirajapinta verkkodokumenteille. Se esittää sivun rakenteen objekteista koostuvana puuna, jossa jokainen solmu vastaa HTML-elementtiä. JavaScript ja verkkokehyksiä käyttävät DOM:ia dynaamisesti sisällön, rakenteen ja tyylin käsittelemiseen ja muokkaamiseen verkkosivuilla.

## Shadow DOM {#shadow-dom}

Shadow DOM on verkkostandardi, joka mahdollistaa DOM:in (Document Object Model) ja CSS:n kapseloinnin tietyn elementin sisälle, jota kutsutaan varjopuuksi. Tämä eristetty DOM ja CSS ovat erillisiä päädokumentin DOM:ista, mikä luo tehokkaasti rajatun alueen komponentille. Shadow DOM auttaa luomaan itsenäisiä, uudelleenkäytettäviä verkkokomponentteja, jotka voidaan lisätä verkkosivulle ilman huolta konflikteista muiden sivun tyylien ja skriptien kanssa.

Se tuo myös mukanaan ominaisuuksia, kuten varjopartit ja slotit, jotka mahdollistavat kehittäjille tietyjen osien altistamisen varjopuusta vanhemman sivun muokattavaksi. Tämä tarjoaa joustavan tavan siirtää sisältöä komponenttiin ja muokata sen ulkonäköä samalla säilyttämällä kapseloinnin.
