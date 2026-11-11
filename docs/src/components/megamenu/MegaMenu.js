import React, { useLayoutEffect, useRef, useState } from 'react';
import clsx from 'clsx';
import * as NavigationMenu from '@radix-ui/react-navigation-menu';
import Link from '@docusaurus/Link';
import { Collapsible, useCollapsible } from '@docusaurus/theme-common';
import { FaArrowRight, FaChevronDown } from 'react-icons/fa6';
import { ICONS } from './icons';
import { getMenu } from './menus';
import { translate } from '@docusaurus/Translate';
import MenuArt from './MenuArt';
import styles from './MegaMenu.module.css';

// A link inside a menu: site pages through the router, other sites in a new
// tab. It passes its ref on, as Radix needs it for keyboard focus.
const MenuLink = React.forwardRef(function MenuLink({ item, className, children, ...props }, ref) {
  const external = Boolean(item.href);
  return (
    <Link
      ref={ref}
      className={className}
      {...(external
        ? { href: item.href, target: '_blank', rel: 'noopener noreferrer' }
        : { to: item.to })}
      {...props}>
      {children}
    </Link>
  );
});

function Icon({ name, className }) {
  const Component = ICONS[name];
  return Component ? <Component className={className} aria-hidden="true" /> : null;
}

// --- previews: what a product looks like, shown beside the list -------------

// Java and Kotlin keywords and types in a code preview
function highlight(line) {
  // a whole-line comment (# or //) stays one muted color
  if (/^\s*(#|\/\/)/.test(line)) return <span className={styles.tokComment}>{line}</span>;
  const parts = line.split(/("[^"]*"|(?<![\w-])@\w+|\b(?:public|private|final|class|extends|new|return|void|val|var|fun|init)\b|\b[A-Z]\w*\b)/);
  return parts.map((part, index) => {
    if (!part) return null;
    let className;
    if (part.startsWith('"')) className = styles.tokString;
    else if (part.startsWith('@')) className = styles.tokAnnotation;
    else if (/^(public|private|final|class|extends|new|return|void|val|var|fun|init)$/.test(part)) className = styles.tokKeyword;
    else if (/^[A-Z]/.test(part)) className = styles.tokType;
    return className ? (
      <span key={index} className={className}>
        {part}
      </span>
    ) : (
      part
    );
  });
}

function CodePreview({ lines }) {
  return (
    <pre className={styles.code}>
      {lines.map((line, index) => (
        <span key={index} className={styles.codeLine} style={{ '--i': index }}>
          <span className={styles.lineNumber}>{index + 1}</span>
          <span>{highlight(line)}</span>
        </span>
      ))}
    </pre>
  );
}

// What a product shows in the preview: a few lines of code, or a drawing
function Preview({ preview }) {
  switch (preview?.type) {
    case 'code':
      return <CodePreview lines={preview.lines} />;
    case 'drawing':
      return <MenuArt name={preview.name} />;
    default:
      return null;
  }
}

// --- layouts ----------------------------------------------------------------

// Products: the list on the left, the hovered product previewed on the right
function ProductsLayout({ products }) {
  const [active, setActive] = useState(0);
  const product = products[active];
  return (
    <div className={styles.products}>
      <div>
        <ul className={styles.items}>
          {products.map((item, index) => (
            // hovering a product shows it, so a click opens it. Without hover
            // (touch), the first tap shows the product and the second opens it.
            <li key={item.label} style={{ '--mm-tone': item.tone }} onMouseEnter={() => setActive(index)}>
              {/* in Radix's focus group, so arrow keys move through products;
                  selecting one that is not shown yet keeps the menu open */}
              <NavigationMenu.Link
                asChild
                onSelect={(event) => {
                  if (index !== active) event.preventDefault();
                }}>
                <MenuLink
                  item={item}
                  className={clsx(styles.product, index === active && styles.productActive)}
                  aria-current={index === active ? 'true' : undefined}
                  onFocus={() => setActive(index)}
                  onClick={(event) => {
                    if (index !== active) {
                      event.preventDefault();
                      setActive(index);
                    }
                  }}>
                  <span className={styles.tile}>
                    <Icon name={item.icon} />
                  </span>
                  <span className={styles.text}>
                    <span className={styles.label}>{item.label}</span>
                    <span className={styles.tagline}>{item.tagline}</span>
                  </span>
                  <FaArrowRight className={styles.go} aria-hidden="true" />
                </MenuLink>
              </NavigationMenu.Link>
            </li>
          ))}
        </ul>
      </div>

      <div className={styles.preview} style={{ '--mm-tone': product.tone }} aria-live="polite">
        <div className={styles.previewHead}>
          <span className={styles.previewCount}>
            {String(active + 1).padStart(2, '0')} / {String(products.length).padStart(2, '0')}
          </span>
          <span className={styles.previewPath}>~/platform/{product.slug}</span>
        </div>
        <div className={styles.previewBody}>
          <div className={styles.previewArea}>
            <Preview key={product.slug} preview={product.preview} />
          </div>
          <p className={styles.previewTitle}>{product.label}</p>
          <p className={styles.previewText}>{product.description}</p>
          <NavigationMenu.Link asChild>
            <MenuLink item={product} className={styles.previewLink}>
              {product.cta ?? translate({ id: 'component.megaMenu.learnMore', message: 'Learn more' })} <FaArrowRight aria-hidden="true" />
            </MenuLink>
          </NavigationMenu.Link>
        </div>
      </div>
    </div>
  );
}

// Generic: titled columns of links with icon tiles and one-line descriptions
function ColumnsLayout({ columns }) {
  return (
    <div className={styles.columns}>
      {columns.map((column, index) => (
        <div key={column.title ?? index}>
          {column.title && <p className={styles.groupTitle}>{column.title}</p>}
          <ul className={styles.items}>
            {column.items.map((item) => (
              <li key={item.label}>
                <NavigationMenu.Link asChild>
                  <MenuLink item={item} className={styles.compact}>
                    <span className={styles.tile}>
                      <Icon name={item.icon} />
                    </span>
                    <span className={styles.text}>
                      <span className={styles.label}>{item.label}</span>
                      {item.description && (
                        <span className={styles.tagline}>{item.description}</span>
                      )}
                    </span>
                  </MenuLink>
                </NavigationMenu.Link>
              </li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
}

// Every link a menu holds, for the phone menu
function allLinks({ products = [], columns = [] }) {
  return [...products, ...columns.flatMap((column) => column.items)];
}

// On phones the menu becomes a collapsible section of the navbar sidebar
function MobileMegaMenu({ label, links }) {
  const { collapsed, toggleCollapsed } = useCollapsible({ initialState: true });
  return (
    <li className={clsx('menu__list-item', { 'menu__list-item--collapsed': collapsed })}>
      <a
        role="button"
        href="#"
        aria-expanded={!collapsed}
        className="menu__link menu__link--sublist menu__link--sublist-caret"
        onClick={(event) => {
          event.preventDefault();
          toggleCollapsed();
        }}>
        {label}
      </a>
      <Collapsible lazy as="ul" className="menu__list" collapsed={collapsed}>
        {links.map((item) => (
          <li key={item.label} className="menu__list-item">
            <MenuLink item={item} className="menu__link">
              {item.label}
            </MenuLink>
          </li>
        ))}
      </Collapsible>
    </li>
  );
}

// Radix opens and closes menus on hover; preventing these pointer events
// leaves opening and closing to clicks, the keyboard, and Escape
const stopHover = (event) => event.preventDefault();

// The panel drops from its own trigger, aligned to the trigger's left edge,
// and moves left only as far as needed to stay on the screen
const EDGE = 16;

function useAnchoredPanel(open) {
  const triggerRef = useRef(null);
  const panelRef = useRef(null);
  useLayoutEffect(() => {
    if (!open) return undefined;
    const place = () => {
      const trigger = triggerRef.current;
      const panel = panelRef.current;
      const anchor = panel?.offsetParent;
      if (!trigger || !panel || !anchor) return false;
      const viewport = document.documentElement.clientWidth;
      const anchorLeft = anchor.getBoundingClientRect().left;
      const wanted = trigger.getBoundingClientRect().left - EDGE;
      const left = Math.max(EDGE, Math.min(wanted, viewport - panel.offsetWidth - EDGE));
      panel.style.left = `${left - anchorLeft}px`;
      return true;
    };
    // Radix mounts the panel a frame after the menu opens, so placing waits
    // for it
    let frame = 0;
    let tries = 0;
    const placeWhenMounted = () => {
      if (!place() && tries++ < 10) frame = requestAnimationFrame(placeWhenMounted);
    };
    placeWhenMounted();
    window.addEventListener('resize', place);
    return () => {
      cancelAnimationFrame(frame);
      window.removeEventListener('resize', place);
    };
  }, [open]);
  return [triggerRef, panelRef];
}

// A navbar entry that opens a panel. config/navbar-menus.js places it as
// { type: 'custom-megaMenu', label, menu }; its content comes from menus.js:
// - layout 'products': products (with previews)
// - otherwise: columns
export default function MegaMenu({ label, menu, mobile = false, className }) {
  const { layout, products = [], columns = [] } = getMenu(menu) ?? {};
  const [value, setValue] = useState('');
  const [triggerRef, panelRef] = useAnchoredPanel(value === 'menu');

  if (mobile) {
    return <MobileMegaMenu label={label} links={allLinks({ products, columns })} />;
  }

  let body;
  if (layout === 'products')
    body = <ProductsLayout products={products} />;
  else body = <ColumnsLayout columns={columns} />;

  return (
    <NavigationMenu.Root
      className={clsx('navbar__item', styles.root, className)}
      value={value}
      onValueChange={setValue}
      delayDuration={120}>
      <NavigationMenu.List className={styles.list}>
        <NavigationMenu.Item value="menu">
          {/* opens on click only: with hover opening too, the pointer opens the
              menu on its way to the trigger and the click then closes it */}
          <NavigationMenu.Trigger
            ref={triggerRef}
            className={clsx('navbar__link', styles.trigger)}
            onPointerMove={stopHover}
            onPointerLeave={stopHover}>
            {label}
            <FaChevronDown className={styles.chevron} aria-hidden="true" />
          </NavigationMenu.Trigger>
          <NavigationMenu.Content
            ref={panelRef}
            className={styles.panel}
            onPointerMove={stopHover}
            onPointerLeave={stopHover}>
            {body}
          </NavigationMenu.Content>
        </NavigationMenu.Item>
      </NavigationMenu.List>
    </NavigationMenu.Root>
  );
}
