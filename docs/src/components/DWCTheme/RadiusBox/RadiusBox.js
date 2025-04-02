import React from 'react';

export default function RadiusBox({ radius }) {
  return (
    <div style={{
      width: radius.includes('pill') ? '6rem' : '3rem',
      height: '3rem',
      display: 'inline-block',
      margin: '0.5rem',
      backgroundColor: 'var(--dwc-color-primary)',
      borderRadius: `var(${radius})`,
      border: '1px solid var(--dwc-color-primary-dark)',
    }} title={radius} />
  );
}
