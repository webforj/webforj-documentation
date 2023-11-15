import {useEffect}  from 'react'

export default function 
() {
  useEffect(() => {
    document.documentElement.style.setProperty('--navbar-color', "white");
    // document.documentElement.style.setProperty('--ifm-navbar-link-hover-color', "var(--ifm-color-success)");
    // document.documentElement.style.setProperty('--ifm-color-primary', "#b242d1");
    document.documentElement.style.setProperty('--ifm-color-emphasis-200', "var(--ifm-color-success-contrast-background)");
    document.documentElement.style.setProperty('--clean-hover', "var(--ifm-color-success)");
    document.documentElement.style.setProperty('--docsearch-searchbox-background', "var(--ifm-background-surface-color");
    return () => {
      // Optionally reset the color when the component is unmounted
      document.documentElement.style.removeProperty('--navbar-color');
      // document.documentElement.style.removeProperty('--ifm-navbar-link-hover-color');
      // document.documentElement.style.removeProperty('--ifm-color-primary');
      document.documentElement.style.removeProperty('--ifm-color-emphasis-200');
      document.documentElement.style.removeProperty('--clean-hover');
      document.documentElement.style.removeProperty('--docsearch-searchbox-background');
    };
  }, []);


  return (
    <div>
      
    </div>
  )
}
