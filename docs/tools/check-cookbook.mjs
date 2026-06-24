#!/usr/bin/env node

import { execFileSync, spawn } from 'child_process';
import {
  closeSync,
  mkdirSync,
  openSync,
} from 'fs';
import { createServer } from 'net';
import { dirname, join, resolve } from 'path';
import { fileURLToPath } from 'url';

const __dirname = dirname(fileURLToPath(import.meta.url));
const docsRoot = resolve(__dirname, '..');
const repoRoot = resolve(docsRoot, '..');
const runtimeRoot = join(repoRoot, 'target', 'cookbook-runtime-tests');

function quoteWindowsCommandArg(arg) {
  return /^[A-Za-z0-9_./:=\\-]+$/.test(arg)
    ? arg
    : `"${arg.replace(/"/g, '""')}"`;
}

function commandSpec(command, args) {
  if (process.platform !== 'win32') {
    return { command, args };
  }

  const commandLine = [command, ...args]
    .map(quoteWindowsCommandArg)
    .join(' ');
  return {
    command: 'cmd.exe',
    args: ['/d', '/s', '/c', commandLine],
  };
}

function run(command, args, options = {}) {
  const spec = commandSpec(command, args);
  return execFileSync(spec.command, spec.args, {
    cwd: options.cwd ?? repoRoot,
    encoding: 'utf8',
    env: options.env ?? process.env,
    stdio: options.stdio ?? 'inherit',
  });
}

function getFreePort() {
  return new Promise((resolvePort, reject) => {
    const server = createServer();
    server.unref();
    server.once('error', reject);
    server.listen(0, '127.0.0.1', () => {
      const address = server.address();
      const port = typeof address === 'object' && address ? address.port : null;
      server.close((error) => {
        if (error) {
          reject(error);
        } else if (port === null) {
          reject(new Error('Unable to allocate a free port'));
        } else {
          resolvePort(port);
        }
      });
    });
  });
}

async function main() {
  mkdirSync(runtimeRoot, { recursive: true });
  const [port, stopPort] = await Promise.all([getFreePort(), getFreePort()]);
  const baseUrl = `http://127.0.0.1:${port}`;
  const environment = {
    ...process.env,
    COOKBOOK_TEST_BASE_URL: baseUrl,
  };

  run('npm.cmd', ['run', 'validate:cookbook'], {
    cwd: docsRoot,
    env: environment,
  });
  run(
    'npm.cmd',
    ['run', 'validate:cookbook-snippets', '--', '--warnings-as-errors'],
    { cwd: docsRoot, env: environment },
  );
  run('npm.cmd', ['run', 'prepare:cookbook-runtime-tests'], {
    cwd: docsRoot,
    env: environment,
  });

  const stdout = openSync(
    join(runtimeRoot, 'isolated-server.stdout.log'),
    'w',
  );
  const stderr = openSync(
    join(runtimeRoot, 'isolated-server.stderr.log'),
    'w',
  );
  const startSpec = commandSpec('mvn.cmd', [
    '-Dskip.npm',
    '-DskipTests',
    `-Dport=${port}`,
    '-Djetty.scan=0',
    `-Djetty.stopPort=${stopPort}`,
    'jetty:run',
  ]);
  const server = spawn(startSpec.command, startSpec.args, {
    cwd: repoRoot,
    env: environment,
    stdio: ['ignore', stdout, stderr],
    windowsHide: true,
  });

  try {
    run('npm.cmd', ['run', 'test:cookbook-runtime'], {
      cwd: docsRoot,
      env: environment,
    });
  } finally {
    try {
      run('mvn.cmd', [
        '-q',
        `-Djetty.stopPort=${stopPort}`,
        'jetty:stop',
      ], {
        cwd: repoRoot,
        env: environment,
        stdio: 'pipe',
      });
    } catch {
      server.kill();
    }
    closeSync(stdout);
    closeSync(stderr);
  }

  console.log(`Cookbook checks passed on ${baseUrl}.`);
}

await main();
