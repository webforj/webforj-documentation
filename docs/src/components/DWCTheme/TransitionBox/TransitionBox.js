import React from 'react';

export default function TransitionBox({ speed, easing }) {
  const transition = speed
    ? `transform var(${speed}) ease-out`
    : `transform 1s var(${easing})`;

  return (
    <div
      style={{
        display: 'inline-block',
        width: '3rem',
        height: '3rem',
        margin: '0.5rem',
        backgroundColor: 'var(--dwc-color-primary)',
        border: '2px solid var(--dwc-color-primary-dark)',
        borderRadius: '0.75rem',
        transition,
        transform: 'scale(1)',
        boxShadow: '0 6px 14px rgba(0, 0, 0, 0.2)',
        cursor: 'pointer'
      }}
      onMouseEnter={(e) => {
        e.currentTarget.style.transform = 'scale(1.2)';
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = 'scale(1)';
      }}
      title={speed || easing}
    />
  );
}
