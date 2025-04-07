import React from 'react';

export default function SurfaceBox({ surface }) {
  return (
    <div
      style={{
        width: '5rem',
        height: '3rem',
        backgroundColor: `var(${surface})`,
        display: 'inline-block',
        borderRadius: '6px',
        border: '1px solid var(--dwc-color-default)',
        margin: '0.5rem'
      }}
      title={surface}
    />
  );
}
