import React from 'react';
import Admonition from '@theme/Admonition';

export default function ExperimentalWarning() {
  return (
    <Admonition type="caution" title="Experimental feature">
      This feature is experimental and its API may change in future releases.
    </Admonition>
  );
}