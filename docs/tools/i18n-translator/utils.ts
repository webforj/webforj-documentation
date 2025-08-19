import fs from 'fs-extra';
import jiti from 'jiti';
import path from 'path';

async function findConfig(siteDir: string) {
  // We could support .mjs, .ts, etc. in the future
  const candidates = ['.ts', '.mts', '.cts', '.js', '.mjs', '.cjs'].map((ext) =>
    path.join(siteDir, 'docusaurus.config' + ext)
  );
  const configPath = candidates.find((path) => {
    return fs.existsSync(path);
  });

  if (!configPath) {
    console.error('No config file found.');
    throw new Error('Not found config file');
  }

  return configPath;
}

export interface SiteConfig {
  i18n?: {
    defaultLocale: string;
    locales: string[];
  };
  // Others is not important
}

export async function loadSiteConfig(siteDir: string): Promise<SiteConfig> {
  const siteConfigPath = await findConfig(siteDir);

  if (!(await fs.pathExists(siteConfigPath))) {
    throw new Error(`Config file at "${siteConfigPath}" not found.`);
  }

  const importedConfig = await loadFreshModule(siteConfigPath);

  const loadedConfig: SiteConfig =
    typeof importedConfig === 'function'
      ? await importedConfig()
      : await importedConfig;

  return loadedConfig;
}

/*
jiti is able to load ESM, CJS, JSON, TS modules
 */
async function loadFreshModule(modulePath: string): Promise<unknown> {
  try {
    if (typeof modulePath !== 'string') {
      throw new Error(`Invalid module path of type name=${modulePath}`);
    }

    const load = jiti(__filename, {
      // Transpilation cache, can be safely enabled
      cache: true,
      // Bypass Node.js runtime require cache
      // Same as "import-fresh" package we used previously
      requireCache: false,
      // We transpile the config, so we need to transpile its dependencies that
      // are provided as source code
      interopDefault: true,
    });

    return load(modulePath);
  } catch (error) {
    throw new Error(`Failed to load config file at path: ${modulePath}. Error: ${error}`);
  }
}