---
sidebar_position: 40
title: Vuorovaikutteisuus
slug: Interactivity
description: >-
  Tutustu Table-komponentin pikanäppäimiin, joilla siirretään kohdistusta,
  valitaan rivejä ja siirrytään solusta toiseen.
_i18n_title_hash: fcbe6546766fcb51d1197a3d75a7a7f4
_i18n_description_hash: 84575804ecb073661d379d81f9143b66
_i18n_hash: 49377400524e9e1c3a25dd35cfb7750c
---
`Table`-komponentti reagoi käyttäjien erilaisiin näppäimistösyötteisiin ja mahdollistaa näin tarkemman vuorovaikutuksen. Seuraavassa luettelossa kuvataan eri näppäinten painamisen vaikutukset:


## Näppäimistökäyttö {#keyboard-interactions}

|Näppäin|Toiminta|
|:-:|-|
|<kbd>Space</kbd>|Valitsee nykyisen rivin.|
|<kbd>Enter</kbd>|Siirtää kohdistuksen soluun tai kohdistettavaan elementtiin, jos solussa on renderöijä.|
|<kbd>Home</kbd>|Siirtää kohdistuksen ensimmäisen rivin ensimmäiseen soluun.|
|<kbd>End</kbd>|Siirtää kohdistuksen viimeisen rivin viimeiseen soluun.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Jos solu on taulukon rungossa ja painetaan näppäinyhdistelmää Shift + Tab, kohdistus siirtyy otsakkeen ensimmäiseen kohdistettavaan soluun.|
|<kbd>&#8594;</kbd>|Jos Ctrl-näppäintä painetaan, kohdistus siirtyy nykyisen rivin viimeiseen soluun. Muussa tapauksessa kohdistus siirtyy seuraavaan soluun vaakasuunnassa.|
|<kbd>&#8592;</kbd>|Jos Ctrl-näppäintä painetaan, kohdistus siirtyy nykyisen rivin ensimmäiseen soluun. Muussa tapauksessa kohdistus siirtyy edelliseen soluun vaakasuunnassa.|
|<kbd>&#8593;</kbd>|Jos Ctrl-näppäintä painetaan, kohdistus siirtyy nykyisen sarakkeen ensimmäiseen soluun. Muussa tapauksessa kohdistus siirtyy edelliseen soluun pystysuunnassa.|
|<kbd>&#8595;</kbd>|Jos Ctrl-näppäintä painetaan, kohdistus siirtyy nykyisen sarakkeen viimeiseen soluun. Muussa tapauksessa kohdistus siirtyy seuraavaan soluun pystysuunnassa.|
