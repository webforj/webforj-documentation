import React from 'react';

export default function ShadowPreview({ shadow }) {
  return (
    <div style={{
      width: '3rem',
      height: '3rem',
      display: 'inline-block',
      margin: '0.5rem',
      backgroundColor: 'var(--dwc-surface-3)',
      boxShadow: `var(${shadow})`,
      borderRadius: '6px',
      border: '1px solid var(--dwc-color-default)'
    }} title={shadow} />
  );
}
