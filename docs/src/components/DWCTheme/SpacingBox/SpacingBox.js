import React from 'react';

export default function SpacingBox({ space }) {
  return (
    <div style={{
      display: 'inline-block',
      backgroundColor: 'var(--dwc-color-default)',
      padding: `var(${space})`,
      margin: '0.5rem',
      borderRadius: '6px'
    }} title={space}>
      <div style={{
        width: '1.5rem',
        height: '1.5rem',
        backgroundColor: 'var(--dwc-color-primary)',
        border: '1px solid var(--dwc-color-primary-dark)',
        borderRadius: '4px'
      }} />
    </div>
  );
}
