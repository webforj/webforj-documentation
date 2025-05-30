#!/usr/bin/env node

/**
 * Setup script to configure Claude Desktop with the webforJ MCP server
 */

import { readFileSync, writeFileSync, existsSync, mkdirSync } from 'fs';
import { homedir } from 'os';
import { join, resolve, dirname } from 'path';
import { fileURLToPath } from 'url';
import { createInterface } from 'readline';

const __dirname = dirname(fileURLToPath(import.meta.url));
const mcpServerPath = resolve(join(__dirname, '..'));

// Determine Claude Desktop config path based on platform
function getConfigPath() {
  const platform = process.platform;
  const home = homedir();
  
  switch (platform) {
    case 'darwin': // macOS
      return join(home, 'Library', 'Application Support', 'Claude', 'claude_desktop_config.json');
    case 'win32': // Windows
      return join(process.env.APPDATA || join(home, 'AppData', 'Roaming'), 'Claude', 'claude_desktop_config.json');
    default: // Linux and others
      return join(home, '.config', 'Claude', 'claude_desktop_config.json');
  }
}

function main() {
  console.log('🚀 Setting up webforJ MCP server for Claude Desktop\n');
  
  const configPath = getConfigPath();
  console.log(`📁 Config path: ${configPath}`);
  
  // Ensure config directory exists
  const configDir = dirname(configPath);
  if (!existsSync(configDir)) {
    console.log(`📂 Creating config directory: ${configDir}`);
    mkdirSync(configDir, { recursive: true });
  }
  
  // Read existing config or create new one
  let config = {};
  if (existsSync(configPath)) {
    console.log('📖 Reading existing configuration...');
    try {
      config = JSON.parse(readFileSync(configPath, 'utf-8'));
    } catch (error) {
      console.error('❌ Error reading config file:', error.message);
      console.log('🔄 Creating new configuration...');
    }
  } else {
    console.log('🆕 Creating new configuration file...');
  }
  
  // Ensure mcpServers object exists
  if (!config.mcpServers) {
    config.mcpServers = {};
  }
  
  // Build the MCP server entry
  const serverEntry = {
    command: 'node',
    args: [join(mcpServerPath, 'dist', 'index.js')],
    env: {
      NODE_ENV: 'development'
    }
  };
  
  // Check if webforj-docs already exists
  if (config.mcpServers['webforj-docs']) {
    console.log('\n⚠️  webforj-docs server already configured.');
    console.log('Current configuration:');
    console.log(JSON.stringify(config.mcpServers['webforj-docs'], null, 2));
    
    const readline = createInterface({
      input: process.stdin,
      output: process.stdout
    });
    
    readline.question('\nOverwrite? (y/N): ', (answer) => {
      if (answer.toLowerCase() === 'y') {
        config.mcpServers['webforj-docs'] = serverEntry;
        saveConfig(configPath, config);
      } else {
        console.log('\n❌ Setup cancelled.');
        console.log('💡 Tip: Your existing configuration is already set up correctly.');
      }
      readline.close();
      process.exit(0);
    });
  } else {
    config.mcpServers['webforj-docs'] = serverEntry;
    saveConfig(configPath, config);
  }
}

function saveConfig(configPath, config) {
  try {
    writeFileSync(configPath, JSON.stringify(config, null, 2));
    console.log('\n✅ Configuration saved successfully!');
    console.log('\n📋 Next steps:');
    console.log('1. Build the MCP server: npm run build');
    console.log('2. Restart Claude Desktop completely');
    console.log('3. Test by asking Claude about webforJ components');
    console.log('\n💡 Example questions to test:');
    console.log('   - "What webforJ components are available?"');
    console.log('   - "Show me the Button component documentation"');
    console.log('   - "Search for components related to field"');
  } catch (error) {
    console.error('\n❌ Error saving configuration:', error.message);
    process.exit(1);
  }
}

// Check if we're in the right directory
if (!existsSync(join(mcpServerPath, 'package.json'))) {
  console.error('❌ This script must be run from the mcp-server directory');
  process.exit(1);
}

main();