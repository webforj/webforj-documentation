import React from 'react';
import styles from './GettingStarted.module.css';

/**
 * Compositional illustrations for the Getting Started page.
 *
 * All variants use CSS custom properties (--il-line, --il-fill, --il-fill-2,
 * --il-fill-3, --il-muted, --il-shadow, --il-accent) so a single markup set
 * adapts to light or dark automatically.
 *
 * Tile variants: 260×210 viewBox. Hero variant: 420×270 viewBox.
 */
export default function Illo({ variant = 'wizard' }) {
  const svg = variants[variant];
  if (!svg) return null;
  return (
    <div className={styles.illo}>
      {svg}
    </div>
  );
}

const tileSvgProps = {
  viewBox: '0 0 260 210',
  fill: 'none',
  strokeLinejoin: 'round',
  strokeLinecap: 'round',
  style: { width: '100%', height: '100%', display: 'block' },
};

const heroSvgProps = {
  viewBox: '0 0 420 270',
  fill: 'none',
  strokeLinejoin: 'round',
  strokeLinecap: 'round',
  style: { width: '100%', height: '100%', display: 'block' },
};

const variants = {
  wizard: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="180" rx="90" ry="9" fill="var(--il-shadow)" />
      <rect x="40" y="44" width="180" height="124" rx="12" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <line x1="40" y1="66" x2="220" y2="66" stroke="var(--il-muted)" strokeWidth="1.5" />
      <circle cx="54" cy="55" r="2.6" fill="var(--il-muted)" />
      <circle cx="63" cy="55" r="2.6" fill="var(--il-muted)" />
      <circle cx="72" cy="55" r="2.6" fill="var(--il-muted)" />
      <rect x="108" y="51" width="44" height="8" rx="4" fill="var(--il-fill-3)" />
      <polyline points="58,84 63,88 58,92" fill="none" stroke="var(--il-accent)" strokeWidth="2" />
      <rect x="70" y="84" width="114" height="8" rx="3" fill="var(--il-fill-3)" />
      <rect x="60" y="102" width="88" height="7" rx="3" fill="var(--il-fill-3)" />
      <rect x="60" y="116" width="122" height="7" rx="3" fill="var(--il-fill-3)" />
      <rect x="60" y="130" width="96" height="7" rx="3" fill="var(--il-accent)" />
      <polyline points="164,130 167,133.5 173,127" fill="none" stroke="var(--il-accent)" strokeWidth="2" />
      <polyline points="58,144 63,148 58,152" fill="none" stroke="var(--il-accent)" strokeWidth="2" />
      <rect x="70" y="143" width="9" height="11" rx="1.5" fill="var(--il-accent)" />
    </svg>
  ),

  projects: (
    <svg {...tileSvgProps}>
      <ellipse cx="122" cy="192" rx="88" ry="9" fill="var(--il-shadow)" />
      <rect x="118" y="34" width="104" height="92" rx="10" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <line x1="118" y1="50" x2="222" y2="50" stroke="var(--il-muted)" strokeWidth="1.5" />
      <circle cx="128" cy="42" r="2.2" fill="var(--il-muted)" />
      <circle cx="135" cy="42" r="2.2" fill="var(--il-muted)" />
      <rect x="128" y="60" width="84" height="8" rx="3" fill="var(--il-accent)" />
      <rect x="128" y="74" width="84" height="8" rx="3" fill="var(--il-fill-3)" />
      <rect x="128" y="88" width="60" height="8" rx="3" fill="var(--il-fill-3)" />
      <rect x="78" y="56" width="104" height="92" rx="10" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <line x1="78" y1="72" x2="182" y2="72" stroke="var(--il-muted)" strokeWidth="1.5" />
      <circle cx="88" cy="64" r="2.2" fill="var(--il-muted)" />
      <circle cx="95" cy="64" r="2.2" fill="var(--il-muted)" />
      <rect x="88" y="82" width="54" height="12" rx="6" fill="var(--il-fill-3)" />
      <rect x="88" y="100" width="42" height="12" rx="6" fill="var(--il-fill-3)" />
      <rect x="118" y="118" width="48" height="12" rx="6" fill="var(--il-accent)" />
      <rect x="88" y="135" width="84" height="9" rx="4.5" fill="var(--il-fill-2)" stroke="var(--il-muted)" strokeWidth="1" />
      <rect x="38" y="78" width="104" height="92" rx="10" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <line x1="38" y1="94" x2="142" y2="94" stroke="var(--il-muted)" strokeWidth="1.5" />
      <circle cx="48" cy="86" r="2.2" fill="var(--il-muted)" />
      <circle cx="55" cy="86" r="2.2" fill="var(--il-muted)" />
      <circle cx="60" cy="126" r="14" fill="none" stroke="var(--il-fill-3)" strokeWidth="5" />
      <rect x="90" y="132" width="10" height="20" rx="3" fill="var(--il-fill-3)" />
      <rect x="106" y="122" width="10" height="30" rx="3" fill="var(--il-fill-3)" />
      <rect x="122" y="114" width="10" height="38" rx="3" fill="var(--il-accent)" />
    </svg>
  ),

  pages: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="192" rx="74" ry="7" fill="var(--il-shadow)" />
      <path d="M130 62 L64 92" stroke="var(--il-muted)" strokeWidth="1.5" />
      <path d="M130 62 L130 92" stroke="var(--il-accent)" strokeWidth="2.5" />
      <path d="M130 62 L196 92" stroke="var(--il-muted)" strokeWidth="1.5" />
      <path d="M130 118 L104 156" stroke="var(--il-accent)" strokeWidth="2.5" />
      <path d="M130 118 L156 156" stroke="var(--il-accent)" strokeWidth="2.5" />
      <rect x="106" y="36" width="48" height="26" rx="8" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <circle cx="118" cy="49" r="3" fill="var(--il-muted)" />
      <rect x="126" y="46.5" width="20" height="5" rx="2.5" fill="var(--il-fill-3)" />
      <rect x="42" y="92" width="44" height="24" rx="7" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <circle cx="53" cy="104" r="2.6" fill="var(--il-muted)" />
      <rect x="61" y="101.5" width="18" height="4.5" rx="2" fill="var(--il-fill-3)" />
      <rect x="108" y="91" width="44" height="27" rx="8" fill="var(--il-accent)" />
      <circle cx="120" cy="104.5" r="2.6" fill="var(--il-fill)" />
      <rect x="128" y="102" width="18" height="4.5" rx="2" fill="var(--il-fill)" opacity="0.85" />
      <rect x="174" y="92" width="44" height="24" rx="7" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <circle cx="185" cy="104" r="2.6" fill="var(--il-muted)" />
      <rect x="193" y="101.5" width="18" height="4.5" rx="2" fill="var(--il-fill-3)" />
      <rect x="84" y="156" width="40" height="22" rx="6" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <circle cx="94" cy="167" r="2.4" fill="var(--il-muted)" />
      <rect x="101" y="164.5" width="15" height="4" rx="2" fill="var(--il-fill-3)" />
      <rect x="136" y="156" width="40" height="22" rx="6" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <circle cx="146" cy="167" r="2.4" fill="var(--il-muted)" />
      <rect x="153" y="164.5" width="15" height="4" rx="2" fill="var(--il-fill-3)" />
    </svg>
  ),

  forms: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="194" rx="72" ry="8" fill="var(--il-shadow)" />
      <rect x="66" y="26" width="128" height="162" rx="14" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <rect x="82" y="42" width="56" height="9" rx="3" fill="var(--il-fill-3)" />
      <rect x="82" y="62" width="30" height="4" rx="2" fill="var(--il-muted)" />
      <rect x="82" y="69" width="96" height="14" rx="4" fill="var(--il-fill-2)" stroke="var(--il-muted)" strokeWidth="1.5" />
      <rect x="82" y="90" width="36" height="4" rx="2" fill="var(--il-muted)" />
      <rect x="82" y="97" width="96" height="14" rx="4" fill="var(--il-fill-2)" stroke="var(--il-muted)" strokeWidth="1.5" />
      <rect x="82" y="118" width="28" height="4" rx="2" fill="var(--il-muted)" />
      <rect x="82" y="125" width="96" height="14" rx="4" fill="var(--il-fill-2)" stroke="var(--il-muted)" strokeWidth="1.5" />
      <polyline points="164,130 168,134 172,130" fill="none" stroke="var(--il-muted)" strokeWidth="1.5" />
      <rect x="82" y="150" width="13" height="13" rx="3" fill="var(--il-fill-2)" stroke="var(--il-line)" strokeWidth="1.5" />
      <polyline points="85,156.5 88,159.5 93,153.5" fill="none" stroke="var(--il-accent)" strokeWidth="2" />
      <rect x="101" y="154" width="54" height="5" rx="2.5" fill="var(--il-fill-3)" />
      <rect x="82" y="170" width="64" height="15" rx="7.5" fill="var(--il-accent)" />
    </svg>
  ),

  theme: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="178" rx="60" ry="8" fill="var(--il-shadow)" />
      <rect x="62" y="40" width="16" height="16" rx="4" fill="var(--il-fill-3)" />
      <rect x="86" y="40" width="16" height="16" rx="4" fill="var(--il-accent)" />
      <rect x="110" y="40" width="16" height="16" rx="4" fill="var(--il-fill-3)" />
      <rect x="134" y="40" width="16" height="16" rx="4" fill="var(--il-muted)" />
      <rect x="158" y="40" width="16" height="16" rx="4" fill="var(--il-fill-3)" />
      <rect x="182" y="40" width="16" height="16" rx="4" fill="var(--il-muted)" />
      <rect x="86" y="70" width="11" height="11" rx="2" fill="var(--il-line)" />
      <rect x="103" y="72" width="64" height="6" rx="3" fill="var(--il-fill-3)" />
      <rect x="88" y="87" width="9" height="9" rx="2" fill="var(--il-line)" />
      <rect x="103" y="88" width="52" height="5" rx="2.5" fill="var(--il-fill-3)" />
      <rect x="89" y="101" width="7" height="7" rx="2" fill="var(--il-line)" />
      <rect x="103" y="102" width="42" height="4" rx="2" fill="var(--il-fill-3)" />
      <rect x="88" y="116" width="84" height="56" rx="12" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <rect x="100" y="128" width="60" height="6" rx="3" fill="var(--il-fill-3)" />
      <rect x="100" y="139" width="40" height="5" rx="2.5" fill="var(--il-fill-3)" />
      <rect x="100" y="150" width="44" height="13" rx="6.5" fill="var(--il-accent)" />
    </svg>
  ),

  ai: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="190" rx="70" ry="8" fill="var(--il-shadow)" />
      <circle cx="130" cy="84" r="54" fill="none" stroke="var(--il-muted)" strokeWidth="1" strokeDasharray="4 5" opacity="0.55" />
      <circle cx="130" cy="84" r="38" fill="none" stroke="var(--il-muted)" strokeWidth="1.5" opacity="0.7" />
      <circle cx="130" cy="84" r="30" fill="var(--il-accent)" opacity="0.12" />
      <path d="M130 84 L161 58" stroke="var(--il-accent)" strokeWidth="1.5" opacity="0.5" />
      <path d="M130 84 L104 115" stroke="var(--il-accent)" strokeWidth="1.5" opacity="0.5" />
      <path d="M130 84 L92 70" stroke="var(--il-accent)" strokeWidth="1.5" opacity="0.5" />
      <circle cx="161" cy="58" r="4" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="1.5" />
      <circle cx="104" cy="115" r="4" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="1.5" />
      <circle cx="92" cy="70" r="4" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="1.5" />
      <path d="M130 56 Q134 80 158 84 Q134 88 130 112 Q126 88 102 84 Q126 80 130 56 Z" fill="var(--il-accent)" />
      <path d="M168 52 Q170 61 179 63 Q170 65 168 74 Q166 65 157 63 Q166 61 168 52 Z" fill="var(--il-accent)" />
      <rect x="52" y="156" width="136" height="20" rx="10" fill="var(--il-fill-2)" stroke="var(--il-muted)" strokeWidth="1.5" />
      <rect x="64" y="163" width="70" height="6" rx="3" fill="var(--il-fill-3)" />
      <rect x="192" y="156" width="20" height="20" rx="7" fill="var(--il-accent)" />
      <path d="M199 162 L206 166 L199 170" fill="none" stroke="#ffffff" strokeWidth="1.6" opacity="0.9" />
    </svg>
  ),

  spring: (
    <svg {...tileSvgProps}>
      <ellipse cx="128" cy="172" rx="94" ry="9" fill="var(--il-shadow)" />
      <rect x="28" y="58" width="92" height="94" rx="12" fill="var(--il-fill-2)" stroke="var(--il-line)" strokeWidth="2" />
      <rect x="42" y="72" width="40" height="6" rx="3" fill="var(--il-fill-3)" />
      <rect x="42" y="84" width="56" height="6" rx="3" fill="var(--il-fill-3)" />
      <text x="74" y="132" fontFamily="ui-monospace, monospace" fontSize="40" fill="var(--il-line)" textAnchor="middle">{'{}'}</text>
      <path d="M192 66 C 220 82, 220 128, 192 144 C 164 128, 164 82, 192 66 Z" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <path d="M192 74 L 192 138" stroke="var(--il-muted)" strokeWidth="1.5" />
      <path d="M120 105 L 164 105" stroke="var(--il-accent)" strokeWidth="4" />
      <circle cx="120" cy="105" r="3.5" fill="var(--il-accent)" />
      <circle cx="164" cy="105" r="3.5" fill="var(--il-accent)" />
    </svg>
  ),

  ship: (
    <svg {...tileSvgProps}>
      <ellipse cx="130" cy="166" rx="56" ry="16" fill="var(--il-accent)" opacity="0.4" />
      <ellipse cx="130" cy="164" rx="32" ry="9" fill="var(--il-accent)" opacity="0.3" />
      <ellipse cx="130" cy="168" rx="34" ry="6" fill="var(--il-shadow)" />
      <path d="M130 54 L180 84 L130 114 L80 84 Z" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <path d="M80 84 L80 132 L130 162 L130 114 Z" fill="var(--il-fill-3)" stroke="var(--il-line)" strokeWidth="2" />
      <path d="M180 84 L180 132 L130 162 L130 114 Z" fill="var(--il-fill-2)" stroke="var(--il-line)" strokeWidth="2" />
      <polyline points="114,84 126,93 150,73" fill="none" stroke="var(--il-accent)" strokeWidth="4" />
    </svg>
  ),

  hero: (
    <svg {...heroSvgProps}>
      <ellipse cx="210" cy="248" rx="122" ry="11" fill="var(--il-shadow)" />
      <path d="M90 152 V218 A16 16 0 0 0 106 234 H314 A16 16 0 0 0 330 218 V152 Z" fill="var(--il-fill)" opacity="0.32" />
      <path d="M106 34 H314 A16 16 0 0 1 330 50 V152 H90 V50 A16 16 0 0 1 106 34 Z" fill="var(--il-fill)" stroke="var(--il-line)" strokeWidth="2" />
      <line x1="90" y1="64" x2="330" y2="64" stroke="var(--il-muted)" strokeWidth="1.5" />
      <circle cx="108" cy="49" r="3.5" fill="var(--il-muted)" />
      <circle cx="120" cy="49" r="3.5" fill="var(--il-muted)" />
      <circle cx="132" cy="49" r="3.5" fill="var(--il-muted)" />
      <rect x="152" y="42" width="150" height="14" rx="7" fill="var(--il-fill-3)" />
      <rect x="112" y="80" width="196" height="11" rx="4" fill="var(--il-accent)" />
      <rect x="112" y="98" width="196" height="11" rx="4" fill="var(--il-fill-3)" />
      <rect x="112" y="116" width="150" height="11" rx="4" fill="var(--il-fill-3)" />
      <rect x="112" y="134" width="176" height="11" rx="4" fill="var(--il-fill-3)" />
      <path d="M90 152 V218 A16 16 0 0 0 106 234 H314 A16 16 0 0 0 330 218 V152" fill="none" stroke="var(--il-line)" strokeWidth="2" strokeDasharray="6 6" opacity="0.85" />
      <rect x="86" y="147" width="248" height="4" rx="2" fill="var(--il-accent)" />
      <rect x="86" y="140" width="248" height="20" rx="4" fill="var(--il-accent)" opacity="0.12" />
      <rect x="112" y="166" width="196" height="11" rx="4" fill="none" stroke="var(--il-muted)" strokeWidth="1.6" strokeDasharray="5 4" opacity="0.7" />
      <rect x="112" y="186" width="160" height="11" rx="4" fill="none" stroke="var(--il-muted)" strokeWidth="1.6" strokeDasharray="5 4" opacity="0.5" />
      <rect x="112" y="206" width="120" height="11" rx="4" fill="none" stroke="var(--il-muted)" strokeWidth="1.6" strokeDasharray="5 4" opacity="0.32" />
      <circle cx="352" cy="170" r="2.6" fill="var(--il-accent)" opacity="0.5" />
      <circle cx="362" cy="192" r="2" fill="var(--il-accent)" opacity="0.35" />
      <circle cx="68" cy="182" r="2.6" fill="var(--il-accent)" opacity="0.4" />
      <circle cx="60" cy="206" r="2" fill="var(--il-accent)" opacity="0.3" />
      <circle cx="346" cy="212" r="3" fill="var(--il-accent)" opacity="0.3" />
    </svg>
  ),
};
