package com.webforj.samples.views.applayout.mobiledrawer

import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutMobileDrawerKotlinView::class)
class AppLayoutMobileDrawerContentKotlinView : AbstractContentView()
