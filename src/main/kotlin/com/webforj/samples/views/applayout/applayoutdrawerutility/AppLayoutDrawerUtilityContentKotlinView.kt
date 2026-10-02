package com.webforj.samples.views.applayout.applayoutdrawerutility

import com.webforj.router.annotation.Route
import com.webforj.samples.views.applayout.AbstractContentView

@Route(value = "/content/:name", outlet = AppLayoutDrawerUtilityKotlinView::class)
class AppLayoutDrawerUtilityContentKotlinView : AbstractContentView()
