package com.webforj.samples.views.appnav

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.html.elements.Paragraph
import com.webforj.kotlin.dsl.component.html.elements.h1
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.router.Router
import com.webforj.router.annotation.Route
import com.webforj.router.event.NavigateEvent

@Route(value = ":id", outlet = AppNavKotlinView::class)
class AppNavPageKotlinView: Composite<Div>() {
  private val self = boundComponent
  private val paragraph: Paragraph

  init {
    self.apply {
      h1("Application Title")
      paragraph = paragraph()
    }
    Router.getCurrent().onNavigate(::onNavigate)
  }

  private fun onNavigate(ev: NavigateEvent) {
    ev.context.routeParameters["id"]?.let {
      paragraph.html = "Content for <strong>$it</strong> goes here"
    }
  }

  override fun onDestroy() {
    Router.getCurrent().removeAllListeners()
    super.onDestroy()
}

}
