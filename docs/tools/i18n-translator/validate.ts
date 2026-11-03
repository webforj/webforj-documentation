import { pathToFileURL } from 'url';

type MdxCompile = (source: string) => Promise<unknown>;

let compilePromise: Promise<MdxCompile> | undefined;

// @mdx-js/mdx is ESM-only, so load it lazily from this CommonJS context
function loadCompile(): Promise<MdxCompile> {
  compilePromise ??= import(pathToFileURL(require.resolve('@mdx-js/mdx')).href)
    .then((mdx) => mdx.compile as MdxCompile);
  return compilePromise;
}

// Docusaurus strips HTML comments and `{#heading-id}` markers before handing
// content to MDX, so do the same here to avoid false positives
function preprocess(content: string): string {
  return content
    .replace(/<!--[\s\S]*?-->/g, '')
    .replace(/[ \t]*\{#[^}\s]+\}/g, '');
}

async function compileError(content: string): Promise<string | null> {
  const compile = await loadCompile();
  try {
    await compile(preprocess(content));
    return null;
  } catch (error: any) {
    return error?.reason ?? error?.message ?? String(error);
  }
}

const sourceCompiles = new Map<string, Promise<boolean>>();

/**
 * Checks that a translated document body still compiles as MDX.
 *
 * Returns the compile error, or null when the translation is valid. When the
 * English source itself fails this check, the translation can't be judged
 * against it, so it passes and the full Docusaurus build stays the backstop.
 */
export async function validateTranslatedMdx(
  sourceContent: string,
  translatedContent: string
): Promise<string | null> {
  let sourceOk = sourceCompiles.get(sourceContent);
  if (!sourceOk) {
    sourceOk = compileError(sourceContent).then((error) => error === null);
    sourceCompiles.set(sourceContent, sourceOk);
  }
  if (!(await sourceOk)) {
    return null;
  }
  return compileError(translatedContent);
}
