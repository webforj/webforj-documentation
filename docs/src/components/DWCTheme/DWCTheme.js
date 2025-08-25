import React from 'react';
import './ColorPalette.css';

export function ColorPalette() {
  const palettes = [
    'primary', 'success', 'warning', 'danger', 'info', 'gray', 'default', 'blackWhite'
  ];

  const getPaletteColors = (palette) => {
    if (palette === 'blackWhite') {
      return [
        { label: 'Black', backgroundColor: 'var(--dwc-color-black)', textColor: 'var(--dwc-color-white)', tooltip: '--dwc-color-black', },
        { label: 'White', backgroundColor: 'var(--dwc-color-white)', textColor: 'var(--dwc-color-black)', tooltip: '--dwc-color-white' }
      ];
    } else {
      const colors = [];
      for (let i = 5; i <= 95; i += 5) {
        colors.push({
          label: `${i}`,
          backgroundColor: `var(--dwc-color-${palette}-${i})`,
          textColor: `var(--dwc-color-${palette}-text-${i})`,
          tooltip: `--dwc-color-${palette}-${i}`
        });
      }
      return colors;
    }
  };

  return (
    <div className="color-palettes-container">
      {palettes.map((palette) => {
        const colors = getPaletteColors(palette);
        return (
          <div key={palette} className="color-palette">
            <h3 className="palette-name">{palette.charAt(0).toUpperCase() + palette.slice(1)}</h3>
            <div className="colors">
              {colors.map((color) => (
                <div
                  key={color.label}
                  className={`color-box ${color.label.toLowerCase()}`}
                  style={{ backgroundColor: color.backgroundColor }}
                  title={color.tooltip}
                >
                  <span
                    className="color-label"
                    style={{ color: color.textColor }}
                  >
                    {color.label}
                  </span>
                </div>
              ))}
            </div>
          </div>
        );
      })}
    </div>
  );
};

const baseBoxStyle = {
  display: 'inline-block',
  margin: '0.5rem',
  borderRadius: '6px',
  border: '1px solid var(--dwc-color-primary-dark)',
  width: '3rem',
  height: '3rem',
  backgroundColor: 'var(--dwc-color-primary)',
};

export function RadiusBox({ radius }) {
  return (
    <div style={{
      ...baseBoxStyle,
      width: radius.includes('pill') ? '6rem' : '3rem',
      borderRadius: `var(${radius})`,
    }} title={radius} />
  );
}

export function ShadowBox({ shadow }) {
  return (
    <div style={{
      ...baseBoxStyle,
      backgroundColor: 'var(--dwc-surface-3)',
      boxShadow: `var(${shadow})`,
      border: '1px solid var(--dwc-color-default)'
    }} title={shadow} />
  );
}

export function SizingBox({ size }) {
  return (
    <div style={{
      ...baseBoxStyle,
      width: `var(${size})`,
      height: `var(${size})`,
    }} title={size} />
  );
}

export function SpacingBox({ space }) {
  return (
    <div style={{
      ...baseBoxStyle,
      width: 'auto',
      height: 'auto',
      border: 'none',
      backgroundColor: 'var(--dwc-color-default)',
      padding: `var(${space})`,
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

export function SurfaceBox({ surface }) {
  return (
    <div
      style={{
        ...baseBoxStyle,
        width: '5rem',
        backgroundColor: `var(${surface})`,
        border: '1px solid var(--dwc-color-default)',
      }}
      title={surface}
    />
  );
}

export function TransitionBox({ speed, easing }) {
  const transition = speed
    ? `transform var(${speed}) ease-out`
    : `transform 1s var(${easing})`;

  return (
    <div
      style={{
        ...baseBoxStyle,
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

