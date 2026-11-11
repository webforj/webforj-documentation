import React from 'react';

// The line icons the standalone pages share. Each is drawn on a 20 by 20
// grid in the current text colour and is hidden from screen readers, since
// it always sits next to the text it decorates. The page sets the size
// through `className`.

function Icon({ className, strokeWidth = 1.75, d, style }) {
  return (
    <svg className={className} viewBox="0 0 20 20" aria-hidden="true" style={style}>
      <path
        d={d}
        fill="none"
        stroke="currentColor"
        strokeWidth={strokeWidth}
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
}

const ARROW = {
  right: 'M4 10h11m-4.5-4.5L15 10l-4.5 4.5',
  left: 'M16 10H5m4.5-4.5L5 10l4.5 4.5',
};

export function ArrowIcon({ className, direction = 'right' }) {
  return <Icon className={className} d={ARROW[direction]} />;
}

// marks a link that leaves the site
export function ExternalIcon({ className }) {
  return (
    <Icon
      className={className}
      d="M11 4h5v5M16 4l-7 7M14 12v3.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5H8"
    />
  );
}

export function ChevronIcon({ className, direction = 'right' }) {
  return (
    <Icon
      className={className}
      d="M8 4.5 13.5 10 8 15.5"
      style={direction === 'left' ? { transform: 'scaleX(-1)' } : undefined}
    />
  );
}

export function CheckIcon({ className }) {
  return <Icon className={className} strokeWidth={2} d="M4.5 10.5 8.25 14 15.5 6" />;
}
