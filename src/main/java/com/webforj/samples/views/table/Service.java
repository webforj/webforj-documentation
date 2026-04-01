package com.webforj.samples.views.table;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;

/**
 * Service class for loading music record data.
 * Uses static factory pattern and immutable collections.
 */
public final class Service {

  // Suppress instantiation
  private Service() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  // Type token for Gson deserialization
  private static final Type MUSIC_RECORD_LIST_TYPE = 
      new TypeToken<List<MusicRecord>>() {}.getType();

  /**
   * Creates a repository with music record data loaded from JSON.
   *
   * @return a CollectionRepository populated with music records
   */
  public static CollectionRepository<MusicRecord> getMusicRecords() {
    List<MusicRecord> data = new Gson().fromJson(
        Assets.contentOf(Assets.resolveContextUrl("context://data/CDStore.json")),
        MUSIC_RECORD_LIST_TYPE);

    return new CollectionRepository<>(data);
  }
}
