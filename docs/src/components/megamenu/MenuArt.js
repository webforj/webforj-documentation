import React from 'react';
import styles from './MegaMenu.module.css';

// The drawn previews of the products menu. Each one is a flat line drawing
// on the dark stage in its product's colour (currentColor), and each plays
// a short scene on an eight second loop: a pointer works the components,
// a desktop screen becomes a web screen, a component is picked and changed,
// a view opens inside a conversation, a design token lands on a component.
// Only transform and opacity are animated. A drawing is on the page only
// while its product is shown, so the scene starts from the top each time.

const LINE = 'rgb(255 255 255 / 0.24)';
const SOFT = 'rgb(255 255 255 / 0.1)';
const GRID = 'rgb(255 255 255 / 0.045)';
const INK = '#101014';

// a keyframe class from the stylesheet, with an optional start delay
const k = (name, delay) => ({
  className: styles[`k_${name}`],
  style: delay ? { animationDelay: `${delay}s` } : undefined,
});
// the same, for a part that only exists to move (hidden when motion is off)
const ghost = (name, delay) => {
  const props = k(name, delay);
  return { ...props, className: `${props.className} ${styles.ghost}` };
};

const Grid = () => (
  <path
    d="M0 54h400M0 108h400M0 162h400M100 0v216M200 0v216M300 0v216"
    stroke={GRID}
    strokeWidth="1"
  />
);

const Pointer = ({ name }) => (
  <g {...ghost(name)}>
    <path d="M0 0v16l4.4-3.8 3 6.9 2.8-1.2-3-6.8h5.8z" fill="#fff" stroke={INK} strokeWidth="1.2" />
  </g>
);

const drawings = {
  // a pointer flips the switch, presses the button and drags the slider
  'ui-components': (
    <>
      <Grid />
      <rect x="28" y="26" width="150" height="12" rx="3" fill={SOFT} stroke="none" />
      <rect x="28" y="46" width="190" height="34" rx="8" stroke={LINE} />
      <path d="M42 63h70" stroke={LINE} />
      <circle cx="302" cy="63" r="26" {...ghost('ring_ui')} />
      <g {...k('press_ui')}>
        <rect x="232" y="46" width="140" height="34" rx="8" fill="currentColor" stroke="none" />
        <path d="M272 63h60" stroke={INK} strokeWidth="3" />
      </g>
      <rect x="28" y="104" width="56" height="28" rx="14" stroke={LINE} />
      <rect
        x="28"
        y="104"
        width="56"
        height="28"
        rx="14"
        fill="currentColor"
        stroke="none"
        {...k('fill_ui')}
      />
      <circle cx="70" cy="118" r="10" fill="#fff" stroke="none" {...k('knob_ui')} />
      <rect x="100" y="104" width="56" height="28" rx="14" stroke={LINE} />
      <circle cx="114" cy="118" r="10" fill={LINE} stroke="none" />
      <rect x="182" y="106" width="24" height="24" rx="6" stroke={LINE} />
      <g {...k('tick_ui')}>
        <rect x="182" y="106" width="24" height="24" rx="6" fill="currentColor" stroke="none" />
        <path d="M188 118l5 5 9-10" stroke={INK} strokeWidth="2.5" />
      </g>
      <path d="M216 118h60" stroke={LINE} />
      <rect x="296" y="106" width="24" height="24" rx="6" stroke={LINE} />
      <path d="M330 118h42" stroke={LINE} />
      <path d="M28 162h344" stroke={LINE} strokeWidth="4" />
      <path d="M28 162h206" strokeWidth="4" {...k('track_ui')} />
      <circle cx="234" cy="162" r="10" fill="#fff" stroke="none" {...k('thumb_ui')} />
      <path d="M112 196h50M186 196h66M276 196h40M28 196h60" stroke={LINE} strokeWidth="3" />
      <path d="M28 196h56" strokeWidth="3" {...k('tab_ui')} />
      <Pointer name="cur_ui" />
    </>
  ),
  // the desktop table is read row by row and comes out as a web screen
  'swing-modernization': (
    <>
      <Grid />
      <rect x="24" y="34" width="150" height="148" rx="4" stroke={LINE} />
      <path d="M24 56h150M24 74h150" stroke={LINE} />
      <path d="M150 45h14M34 45h40" stroke={LINE} />
      <path d="M36 65h16M60 65h16M84 65h16" stroke={LINE} />
      <rect
        x="30"
        y="86"
        width="138"
        height="16"
        rx="3"
        fill="currentColor"
        stroke="none"
        {...ghost('row_sw')}
      />
      <path
        d="M36 94h126M36 112h126M36 130h126M36 148h126M36 166h126M86 84v92M128 84v92"
        stroke={SOFT}
      />
      <path d="M186 108h26m-9-9 9 9-9 9" strokeWidth="2.5" />
      <circle cx="184" cy="108" r="5" fill="#fff" stroke="none" {...ghost('packet_sw')} />
      <rect x="224" y="20" width="152" height="176" rx="12" />
      <path d="M224 46h152" />
      <circle cx="238" cy="33" r="3" fill="currentColor" stroke="none" />
      <circle cx="250" cy="33" r="3" fill="currentColor" stroke="none" />
      <rect x="264" y="27" width="98" height="12" rx="6" stroke={LINE} />
      <g {...k('pop', 4.4)}>
        <rect x="238" y="60" width="124" height="30" rx="8" fill="currentColor" stroke="none" />
        <path d="M252 75h56" stroke={INK} strokeWidth="3" />
      </g>
      <g {...k('pop', 4.75)}>
        <rect x="238" y="100" width="124" height="22" rx="6" stroke={LINE} />
        <path d="M250 111h60" stroke={LINE} />
      </g>
      <g {...k('pop', 5.1)}>
        <rect x="238" y="130" width="124" height="22" rx="6" stroke={LINE} />
        <path d="M250 141h44" stroke={LINE} />
      </g>
      <g {...k('pop', 5.45)}>
        <rect x="238" y="160" width="124" height="22" rx="6" fill="#fff" stroke="none" />
        <path d="M250 171h70" stroke={INK} strokeWidth="2.5" />
      </g>
    </>
  ),
  // the pointer picks one component after another; the tree and the
  // property switch in the inspector follow
  craftforj: (
    <>
      <Grid />
      <rect x="24" y="22" width="206" height="172" rx="10" stroke={LINE} />
      <path d="M24 46h206" stroke={LINE} />
      <path d="M38 34h46" stroke={LINE} />
      <rect x="38" y="60" width="50" height="120" rx="6" fill={SOFT} stroke="none" />
      <path d="M48 76h30M48 92h30M48 108h30" stroke={LINE} />
      <rect x="104" y="64" width="108" height="26" rx="6" stroke={LINE} />
      <path d="M118 77h60" stroke={LINE} />
      <rect x="104" y="107" width="108" height="26" rx="6" fill="currentColor" stroke="none" />
      <path d="M132 120h52" stroke={INK} strokeWidth="3" />
      <rect x="104" y="150" width="108" height="26" rx="6" stroke={LINE} />
      <path d="M118 163h44" stroke={LINE} />
      <g {...k('sel_cj')}>
        <rect x="96" y="100" width="124" height="40" rx="8" strokeDasharray="5 5" />
        <rect x="91" y="95" width="10" height="10" rx="2" fill="#fff" stroke="none" />
        <rect x="215" y="135" width="10" height="10" rx="2" fill="#fff" stroke="none" />
      </g>
      <rect x="244" y="22" width="132" height="172" rx="10" fill={SOFT} stroke={LINE} />
      <path d="M258 40h44" strokeWidth="3" />
      <path d="M258 62h20M270 78h34M270 94h44M270 110h30M282 126h38" stroke={LINE} />
      <path d="M266 94h52" strokeWidth="3" {...k('tree_cj')} />
      <path d="M244 142h132" stroke={LINE} />
      <path d="M258 158h34M258 178h46" stroke={LINE} />
      <rect x="332" y="150" width="30" height="16" rx="8" stroke={LINE} />
      <rect
        x="332"
        y="150"
        width="30"
        height="16"
        rx="8"
        fill="currentColor"
        stroke="none"
        {...k('fill_cj')}
      />
      <circle cx="354" cy="158" r="5" fill="#fff" stroke="none" {...k('knob_cj')} />
      <rect x="332" y="170" width="30" height="16" rx="8" stroke={LINE} />
      <circle cx="340" cy="178" r="5" fill={LINE} stroke="none" />
      <Pointer name="cur_cj" />
    </>
  ),
  // a message is sent, the assistant thinks, a live view opens and is used
  'mcp-apps': (
    <>
      <Grid />
      <g {...k('bubble_mcp')}>
        <rect x="196" y="18" width="180" height="30" rx="15" fill={SOFT} stroke="none" />
        <path d="M214 33h144" stroke={LINE} />
      </g>
      <g {...ghost('dots_mcp')}>
        <circle cx="34" cy="78" r="4" fill="currentColor" stroke="none" {...k('dot', 0)} />
        <circle cx="48" cy="78" r="4" fill="currentColor" stroke="none" {...k('dot', 0.14)} />
        <circle cx="62" cy="78" r="4" fill="currentColor" stroke="none" {...k('dot', 0.28)} />
      </g>
      <g {...k('card_mcp')}>
        <rect x="24" y="62" width="300" height="122" rx="12" fill={INK} />
        <path d="M24 88h300" />
        <circle cx="40" cy="75" r="4" fill="currentColor" stroke="none" />
        <path d="M54 75h70" />
        <path d="M40 106h200M40 126h200M40 146h200" stroke={SOFT} />
        <path d="M40 106h60" stroke={LINE} strokeWidth="3" {...k('line_mcp', 0)} />
        <path d="M40 126h84" stroke={LINE} strokeWidth="3" {...k('line_mcp', 0.2)} />
        <path d="M40 146h48" stroke={LINE} strokeWidth="3" {...k('line_mcp', 0.4)} />
        <rect x="252" y="98" width="56" height="16" rx="8" stroke={LINE} />
        <rect x="252" y="118" width="56" height="16" rx="8" stroke={LINE} />
        <rect x="252" y="138" width="56" height="16" rx="8" stroke={LINE} />
        <rect
          x="252"
          y="138"
          width="56"
          height="16"
          rx="8"
          fill="currentColor"
          stroke="none"
          {...k('pill_mcp')}
        />
        <circle cx="267" cy="168" r="22" {...ghost('ring_mcp')} />
        <g {...k('press_mcp')}>
          <rect x="224" y="160" width="86" height="16" rx="6" fill="#fff" stroke="none" />
          <path d="M244 168h46" stroke={INK} strokeWidth="2.5" />
        </g>
      </g>
      <rect x="24" y="194" width="130" height="14" rx="7" fill={SOFT} stroke="none" />
      <circle cx="356" cy="123" r="16" stroke={LINE} />
      <path d="M350 123h12m-5-5 5 5-5 5" />
    </>
  ),
  // the palette appears, a component is framed, and one colour token flies
  // from its swatch onto the button
  'design-kit': (
    <>
      <Grid />
      <rect
        x="24"
        y="22"
        width="40"
        height="40"
        rx="8"
        fill="currentColor"
        stroke="none"
        {...k('swatch', 0)}
      />
      <rect
        x="72"
        y="22"
        width="40"
        height="40"
        rx="8"
        fill="currentColor"
        fillOpacity="0.7"
        stroke="none"
        {...k('swatch', 0.12)}
      />
      <rect
        x="120"
        y="22"
        width="40"
        height="40"
        rx="8"
        fill="currentColor"
        fillOpacity="0.45"
        stroke="none"
        {...k('swatch', 0.24)}
      />
      <rect
        x="168"
        y="22"
        width="40"
        height="40"
        rx="8"
        fill="currentColor"
        fillOpacity="0.22"
        stroke="none"
        {...k('swatch', 0.36)}
      />
      <rect x="216" y="22" width="40" height="40" rx="8" stroke={LINE} {...k('swatch', 0.48)} />
      <g {...k('swatch', 0.7)}>
        <text
          x="286"
          y="60"
          fill="#fff"
          stroke="none"
          fontSize="46"
          fontWeight="800"
          fontFamily="Inter, system-ui, sans-serif"
          letterSpacing="-2">
          Aa
        </text>
      </g>
      <rect x="24" y="92" width="210" height="76" rx="2" {...k('frame_dk')} />
      <g {...k('handles_dk')}>
        <rect x="20" y="88" width="8" height="8" fill="#fff" stroke="none" />
        <rect x="230" y="88" width="8" height="8" fill="#fff" stroke="none" />
        <rect x="20" y="164" width="8" height="8" fill="#fff" stroke="none" />
        <rect x="230" y="164" width="8" height="8" fill="#fff" stroke="none" />
      </g>
      <g {...k('inner_dk')}>
        <rect x="40" y="108" width="178" height="20" rx="6" stroke={LINE} />
        <rect x="40" y="136" width="84" height="20" rx="6" stroke={LINE} />
        <rect x="134" y="136" width="84" height="20" rx="6" stroke={LINE} />
      </g>
      <rect
        x="40"
        y="136"
        width="84"
        height="20"
        rx="6"
        fill="currentColor"
        stroke="none"
        {...k('fill_dk')}
      />
      <rect
        x="37"
        y="35"
        width="14"
        height="14"
        rx="4"
        fill="#fff"
        stroke="none"
        {...ghost('chip_dk')}
      />
      <path d="M24 188h210M24 182v12M234 182v12" stroke={LINE} {...k('ruler_dk')} />
      <path d="M262 100h114" stroke={LINE} strokeWidth="3" {...k('grow', 0.9)} />
      <path d="M262 122h86" stroke={LINE} strokeWidth="3" {...k('grow', 1.1)} />
      <path d="M262 144h100" stroke={LINE} strokeWidth="3" {...k('grow', 1.3)} />
      <path d="M262 166h64" stroke={LINE} strokeWidth="3" {...k('grow', 1.5)} />
      <path d="M262 100h40" strokeWidth="3" {...k('grow', 1.7)} />
    </>
  ),
};

export default function MenuArt({ name }) {
  const drawing = drawings[name];
  if (!drawing) return null;
  return (
    <svg
      className={styles.art}
      viewBox="0 0 400 216"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden="true">
      {drawing}
    </svg>
  );
}
