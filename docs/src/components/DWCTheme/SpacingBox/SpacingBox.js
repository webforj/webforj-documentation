import React from 'react';

export default function SpacingBox({ space }) {
  return (
    <div style={{
      display: 'inline-block',
      backgroundColor: 'var(--dwc-color-default-dark)',
      padding: `var(${space})`,
      margin: '0.5rem',
      borderRadius: '6px'
    }} title={space}>
      <div style={{
        width: '1.5rem',
        height: '1.5rem',
        backgroundColor: 'var(--dwc-color-primary)',
        borderRadius: '4px'
      }} />
    </div>
  );
}
