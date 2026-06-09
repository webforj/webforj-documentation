package com.webforj.samples.views.applayout.fullnavbar

import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutFullNavbarKotlinView::class)
class AppLayoutFullNavbarContentKotlinView : AbstractContentView()
