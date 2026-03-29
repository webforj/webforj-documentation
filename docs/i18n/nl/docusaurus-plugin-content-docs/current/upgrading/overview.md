---
sidebar_position: 1
title: Upgrading Guides
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5b67f3c7842c20cbef9c77df8f3dd69a
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

De releasecyclus van webforJ volgt een gestructureerd en voorspelbaar model om stabiliteit, prestaties en continue innovatie te waarborgen. Dit document biedt een overzicht van hoe releases worden gepland, welke soorten releases te verwachten zijn en hoe gebruikers op de hoogte kunnen blijven en zich kunnen voorbereiden.

## Soorten webforJ-releases {#types-of-webforj-releases}

webforJ volgt een gestructureerd release model dat de volgende soorten releases omvat:

### 1. Grote releases {#1-major-releases}
- Vinden jaarlijks plaats.
- Introduceren significante nieuwe functies, verbeteringen en optimalisaties.
- Kunnen configuratiewijzigingen of aanpassingen van bestaande apps vereisen.
- Geïdentificeerd met versienummering zoals **webforJ 20.00, webforJ 21.00, enz.**

### 2. Kleine releases {#2-minor-releases}
- Vinden meerdere keren per jaar plaats (ongeveer elke zes tot acht weken).
- Bieden incrementele verbeteringen, optimalisaties en kleine nieuwe functies.
- Geïdentificeerd met versienummering zoals **webforJ 20.01, webforJ 20.02, enz.**

### 3. Patches en bugfix-releases {#3-patches-and-bug-fix-releases}
- Worden vrijgegeven indien nodig.
- Tackelen kritieke bugs, prestatieproblemen en beveiligingskwetsbaarheden.
- Geïdentificeerd met aanvullende nummering zoals **webforJ 20.01.1, webforJ 20.01.2, enz.**

## Wat te verwachten bij iedere release {#what-to-expect-with-each-release}

### Functieverbeteringen {#feature-enhancements}
- Grote en kleine releases introduceren nieuwe mogelijkheden, optimalisaties en integraties.
- Functie-roadmaps worden gedeeld in release-opmerkingen om gebruikers te helpen vooruit te plannen.

:::info Achterwaartse compatibiliteit
Hoewel er pogingen worden gedaan om compatibiliteit te behouden, kunnen grote releases wijzigingen bevatten die aanpassingen aan apps vereisen. Gebruikers worden aangemoedigd om de release-opmerkingen te bekijken voor verouderde functies.
:::

### Beveiligingsupdates {#security-updates}
- Beveiliging heeft prioriteit, en kritieke kwetsbaarheden worden zo snel mogelijk aangepakt in patch-releases.

:::tip Snapshot-bouw
Snapshot-bouw zijn beschikbaar voor de meeste releases. Gebruikers worden aangemoedigd om tegen deze builds te testen om vroegtijdig problemen te identificeren en feedback te geven. Zie het [Snapshots](/docs/configuration/snapshots) artikel om te leren hoe je webforJ-snapshots kunt gebruiken en waar je ze kunt krijgen.
:::

## Hoe up-to-date te blijven {#how-to-stay-updated}

### Release-opmerkingen en aankondigingen {#release-notes-and-announcements}
- Iedere release wordt vergezeld van gedetailleerde [release-opmerkingen](https://github.com/webforj/webforj/releases) die nieuwe functies, bugfixes en eventuele vereiste acties uiteenzetten.
- Gebruikers moeten zich abonneren op de webforJ [blog](../../blog) voor tijdige updates.

:::tip Upgrade-aanbevelingen
Klanten zouden upgrades moeten plannen op basis van zakelijke behoeften en stabiliteitsvereisten. Gebruikers worden aangemoedigd om op de nieuwste release te blijven om te profiteren van prestatieverbeteringen en nieuwe functies.
:::

### Ondersteuning en compatibiliteit {#support-and-compatibility}
- webforJ biedt documentatie en upgradehandleidingen voor grote releases.
- Gemeenschapsforums en klantenondersteuningskanalen zijn beschikbaar voor probleemoplossing en assistentie.

<DocCardList className="topics-section" />
