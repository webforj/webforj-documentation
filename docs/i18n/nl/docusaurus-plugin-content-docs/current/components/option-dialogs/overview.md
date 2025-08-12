---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Optiedialoogvensters
<!-- vale on -->

Optiedialoogvensters bieden een manier voor de app om te communiceren met gebruikers en hun input te verzamelen. Deze dialoogvensters zijn modaal, wat betekent dat ze de uitvoering van de app blokkeren totdat de gebruiker ermee interageert, zodat belangrijke berichten worden behandeld voordat de voortgang wordt gemaakt.

Optiedialoogvensters in webforJ zijn vergelijkbaar met de `JOptionPane` in Swing, en lossen een fundamenteel probleem op van het beheren van blokkeringdialoogvensters in webtoepassingen.

:::tip Modaliteit
Bij het gebruik van optiedialoogvensters om modale dialoogvensters in webforJ te creëren, blokkeert de dialoog de gebruikersinvoer naar andere delen van de app en verwerkt ze gebeurtenissen uitsluitend voor de modale dialoog. Dit zorgt ervoor dat de dialoog responsief blijft terwijl interacties met andere delen worden voorkomen, wat de gebruikerservaring verbetert en de stroom van de app behoudt. De server stopt met het verwerken van verdere verzoeken totdat de dialoog wordt gesloten of er een waarde van wordt teruggegeven.
:::

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
