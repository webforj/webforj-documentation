import React from 'react';

export default function SizingBox({ size }) {
  return (
    <div style={{
      display: 'inline-block',
      width: `var(${size})`,
      height: `var(${size})`,
      backgroundColor: 'var(--dwc-color-primary)',
      border: '1px solid var(--dwc-color-primary-dark)',
      borderRadius: '6px',
      margin: '0.5rem'
    }} title={size} />
  );
}
