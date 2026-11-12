import React from 'react';
import Admonition from '@theme/Admonition';
import Translate, {translate} from '@docusaurus/Translate';

// A short note at the top of a page about a feature that is still in
// preview. It is an informational note, not a warning: the feature works,
// and only its API may still change.
export default function ExperimentalWarning() {
  return (
    <Admonition
      type="info"
      title={translate({
        id: 'previewNote.title',
        message: 'Preview feature',
      })}
    >
      <Translate id="previewNote.message">
        This feature is in preview. Its API may still change in future
        releases.
      </Translate>
    </Admonition>
  );
}
