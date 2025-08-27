import React from 'react';
import './ColorPalette.css';

const ColorPalette = () => {
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

export default ColorPalette;
