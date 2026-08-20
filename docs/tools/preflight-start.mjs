#!/usr/bin/env node
/**
 * preflight-start.mjs
 *
 * Ensures a truly clean state before `docusaurus start` boots.
 *
 * Motivation: `docusaurus clear` (and plain `rimraf`) sometimes report success
 * on Windows without actually removing `.docusaurus/` when a prior node
 * process, antivirus, or the search indexer is still holding chunk files.
 * If a stale `.docusaurus/i18n.json` survives, `docusaurus start` inherits
 * its `currentLocale`, causing pages to load hydrated in the wrong language
 * and fall through to a "Page not found" that has no way back.
 *
 * fs.rm with maxRetries retries transient locks instead of silently skipping.
 */

import { rm, stat } from 'node:fs/promises';
import { execSync } from 'node:child_process';
import { fileURLToPath } from 'node:url';
import { dirname, resolve } from 'node:path';

const siteDir = resolve(dirname(fileURLToPath(import.meta.url)), '..');
const PORT = Number(process.env.PORT ?? 3000);
const TARGETS = ['.docusaurus', 'node_modules/.cache', 'build'];

const log = (m) => process.stdout.write(`[preflight] ${m}\n`);
const warn = (m) => process.stderr.write(`[preflight] ${m}\n`);

async function exists(p) {
  try { await stat(p); return true; } catch { return false; }
}

async function forceRemove(rel) {
  const abs = resolve(siteDir, rel);
  if (!(await exists(abs))) return 'skipped';
  await rm(abs, { recursive: true, force: true, maxRetries: 10, retryDelay: 300 });
  if (await exists(abs)) throw new Error(`still exists after retries: ${abs}`);
  return 'removed';
}

// Ground truth for "who is listening on this port" — netstat/lsof beats trying
// to bind, which is unreliable across dual-stack IPv4/IPv6 on Windows (a
// process listening on ::1 won't collide with a bind attempt on 0.0.0.0).
function findPortHolder(port) {
  try {
    if (process.platform === 'win32') {
      // `-p tcp` on some Windows netstat builds filters out IPv6 rows entirely
      // (e.g. a docusaurus dev-server on [::1]:3000 would be missed). Use the
      // unfiltered listing and match TCP lines in JS.
      const out = execSync('netstat -ano', { encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] });
      // Only match rows where PORT is the local port and state is LISTENING.
      // \b prevents :3000 from matching :30000.
      const re = new RegExp(`^\\s*TCP\\s+\\S+:${port}\\b\\s+\\S+\\s+LISTENING\\s+(\\d+)`);
      for (const line of out.split(/\r?\n/)) {
        const m = re.exec(line);
        if (m) return m[1];
      }
      return null;
    }
    const out = execSync(`lsof -tiTCP:${port} -sTCP:LISTEN`, { encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] });
    return out.trim().split('\n')[0] || null;
  } catch { return null; }
}

// Returns the process command line, or '' if the PID is dead (ghost socket).
function getCommandLine(pid) {
  try {
    if (process.platform === 'win32') {
      const out = execSync(
        `powershell -NoProfile -Command "(Get-CimInstance Win32_Process -Filter 'ProcessId=${pid}').CommandLine"`,
        { encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] },
      );
      return out.trim();
    }
    return execSync(`ps -p ${pid} -o command=`, { encoding: 'utf8', stdio: ['ignore', 'pipe', 'ignore'] }).trim();
  } catch { return ''; }
}

function killPid(pid) {
  try {
    if (process.platform === 'win32') {
      // /T kills child processes too — orphan docusaurus may have spawned workers
      execSync(`taskkill /F /T /PID ${pid}`, { stdio: 'ignore' });
    } else {
      execSync(`kill -9 ${pid}`, { stdio: 'ignore' });
    }
    return true;
  } catch { return false; }
}

const delay = (ms) => new Promise((r) => setTimeout(r, ms));

// Reclaim port 3000 if an orphan docusaurus is still holding it. Runs BEFORE
// the file wipes so the orphan doesn't have open handles inside .docusaurus/
// when we try to delete.
async function reclaimPort() {
  const pid = findPortHolder(PORT);
  if (!pid) {
    log(`ok port ${PORT} free`);
    return;
  }
  const cmdline = getCommandLine(pid);

  // Ghost socket (dead process still shows in netstat, FinWait2 or similar):
  // a new listener can bind despite it, and there's nothing to kill.
  if (pid && !cmdline) {
    log(`port ${PORT} shows PID ${pid} but no live process — proceeding (ghost socket)`);
    return;
  }

  // Narrow auto-kill: orphan docusaurus dev-server the user tried to stop
  // but that survived (npm/shell wrapper doesn't propagate the signal on Windows).
  if (cmdline && /docusaurus[^\s]*\s+start\b/.test(cmdline)) {
    log(`port ${PORT} held by orphan docusaurus (PID ${pid}) — killing`);
    if (!killPid(pid)) {
      warn(`FAILED to kill PID ${pid}. Run manually:`);
      warn(process.platform === 'win32' ? `  taskkill /F /T /PID ${pid}` : `  kill -9 ${pid}`);
      process.exit(1);
    }
    await delay(500);
    log(`ok port ${PORT} reclaimed`);
    return;
  }

  // Anything else on port 3000: not our problem, don't auto-kill.
  warn(`port ${PORT} is IN USE by PID ${pid} (${cmdline.slice(0, 100) || 'unknown process'})`);
  warn(`Not a docusaurus dev-server — leaving it alone.`);
  warn(`docusaurus will fall back to another port; to reclaim ${PORT} run:`);
  warn(process.platform === 'win32'
    ? `  taskkill /F /PID ${pid ?? '<pid>'}`
    : `  kill -9 ${pid ?? '<pid>'}`);
}

async function main() {
  // Port first: killing an orphan dev-server releases its handles on files
  // inside .docusaurus/ before we try to delete them.
  await reclaimPort();

  for (const t of TARGETS) {
    try {
      const status = await forceRemove(t);
      log(`${status === 'skipped' ? 'ok (already gone)' : 'removed'} ${t}`);
    } catch (err) {
      warn(`FAILED to remove docs/${t}: ${err.message}`);
      warn('This is the "wrong locale / page not found" bug in the making —');
      warn('another process (leftover node, antivirus, or search indexer)');
      warn('is holding files inside that folder. Free it and retry.');
      process.exit(1);
    }
  }
}

main().catch((err) => { warn(err.stack ?? err.message); process.exit(2); });
