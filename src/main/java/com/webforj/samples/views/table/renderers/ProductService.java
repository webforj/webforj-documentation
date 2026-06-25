package com.webforj.samples.views.table.renderers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;
import java.util.List;

public final class ProductService {
  private ProductService() {}

  public static CollectionRepository<Product> getProducts() {
    List<Product> data =
        new Gson()
            .fromJson(
                Assets.contentOf(Assets.resolveContextUrl("context://data/products.json")),
                new TypeToken<List<Product>>() {});
    return new CollectionRepository<>(data);
  }
}
