import React from 'react';
import ConnectLauncher from '@site/src/components/connect/ConnectLauncher';

// The "Talk to us" launcher sits in the corner of every page
export default function Root({ children }) {
  return (
    <>
      {children}
      <ConnectLauncher />
    </>
  );
}
