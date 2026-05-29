package com.webforj.samples.views.splitter

import com.webforj.concern.HasComponents
import com.webforj.kotlin.dsl.WebforjDsl
import com.webforj.kotlin.dsl.init
import com.webforj.samples.components.SplitterBox

@WebforjDsl
fun @WebforjDsl HasComponents.splitterBox(
  label: String,
  theme: SplitterBox.Theme,
  block: @WebforjDsl SplitterBox.() -> Unit = {}): SplitterBox {
  return init(SplitterBox(label, theme), block)
}
