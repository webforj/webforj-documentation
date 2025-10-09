---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
_i18n_hash: 008eef50f8ab27ec3f8a455fb5649f41
---
De `Table` component stelt je in staat om filtering te implementeren om de weergegeven gegevens te verkleinen op basis van specifieke criteria. Filtering kan worden bereikt door een filtercriterium te definiëren met de `setFilter(Predicate<T> filter)`-methode die wordt aangeboden door de `Repository` die aan de tabel is gekoppeld.

In het volgende voorbeeld wordt een door de gebruiker gedefinieerd criterium uit het zoekveld gebruikt en de `setBaseFilter()`-methode om een filter toe te passen op de `CollectionRepository` op basis van de titels van `MusicRecord`. Wanneer de `commit()`-methode wordt geactiveerd, wordt de tabel vernieuwd met de gefilterde gegevens.

:::note
De `setBaseFilter()`-methode behoort tot de `CollectionRepository`-klasse, niet de `Table`-component.
:::
