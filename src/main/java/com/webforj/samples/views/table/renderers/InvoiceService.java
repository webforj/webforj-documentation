package com.webforj.samples.views.table.renderers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.utilities.Assets;
import java.util.List;

public final class InvoiceService {
  private InvoiceService() {}

  public static CollectionRepository<Invoice> getInvoices() {
    List<Invoice> data =
        new Gson().fromJson(Assets.contentOf(Assets.resolveContextUrl("context://data/invoices.json")),
            new TypeToken<List<Invoice>>() {});
    return new CollectionRepository<>(data);
  }
}