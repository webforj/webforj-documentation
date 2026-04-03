import React from 'react';
import Admonition from '@theme/Admonition';
import Translate, {translate} from '@docusaurus/Translate';

export default function ExperimentalWarning() {
  return (
    <Admonition
      type="caution"
      title={translate({
        id: 'experimentalWarning.title',
        message: 'Experimental feature',
      })}
    >
      <Translate id="experimentalWarning.message">
        This feature is experimental and may change or be removed in a future
        release.
      </Translate>
    </Admonition>
  );
}