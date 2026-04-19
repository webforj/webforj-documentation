package com.webforj.samples.views.table

import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init

@WebforjDsl
fun @WebforjDsl HasComponents.titleEditorComponent(block: @WebforjDsl TitleEditorComponent.() -> Unit = {})
  = init(TitleEditorComponent(), block)
