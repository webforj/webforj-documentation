---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 7ff00c0abd564956da84fbd20761413e
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# Optiedialoogvensters
<!-- vale on -->

Optiedialoogvensters bieden een manier voor de app om te communiceren met gebruikers en hun input te verzamelen. Deze dialoogvensters zijn modaal, wat betekent dat ze de uitvoer van de app blokkeren totdat de gebruiker ermee interactie heeft, zodat belangrijke berichten worden behandeld voordat ze verdergaan.

Optiedialoogvensters in webforJ zijn vergelijkbaar met de `JOptionPane` in Swing, en lossen een fundamenteel probleem op van het omgaan met blokkeringdialoogvensters in webtoepassingen.

:::tip Modaliteit
Bij het gebruik van optiedialoogvensters om modale dialoogvensters in webforJ te maken, blokkeert de dialoog gebruikersinvoer naar andere delen van de app en verwerkt deze evenementen uitsluitend voor het modale dialoogvenster. Dit zorgt ervoor dat de dialoog responsief blijft terwijl interacties met andere delen worden voorkomen, waardoor de gebruikerservaring wordt verbeterd en de workflow van de app behouden blijft. De server stopt met het verwerken van verdere verzoeken totdat de dialoog is gesloten of een waarde is teruggegeven.
:::

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
