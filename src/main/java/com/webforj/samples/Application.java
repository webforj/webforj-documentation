package com.webforj.samples;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.webforj.App;
import com.webforj.RedirectAction;
import com.webforj.Request;
import com.webforj.annotation.AppTitle;
import com.webforj.annotation.Routify;
import com.webforj.exceptions.WebforjException;

@Routify(packages = "com.webforj.samples.views")
@AppTitle("webforJ Samples")
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
  }

  @Override
  protected void onWillTerminate() {
    String currentUrl = Request.getCurrent().getUrl();
    if (currentUrl.contains("/webforj/")) {
      try {
        URI currentUri = new URI(currentUrl);
        try {
          HttpClient client = HttpClient.newHttpClient();
          HttpRequest request = HttpRequest.newBuilder(currentUri).build();
          HttpResponse<String> respone = client.send(request, HttpResponse.BodyHandlers.ofString());
          if (respone.statusCode() != 404)
          setTerminateAction(new RedirectAction(currentUrl));
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
    }

  }

}
