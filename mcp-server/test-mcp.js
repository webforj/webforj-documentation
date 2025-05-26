#!/usr/bin/env node

/**
 * Test script to demonstrate MCP server communication
 * Run with: node test-mcp.js
 */

import { spawn } from 'child_process';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const __dirname = dirname(fileURLToPath(import.meta.url));
const serverPath = join(__dirname, 'dist', 'index.js');

console.log('Starting MCP server test...\n');

// Spawn the MCP server
const mcp = spawn('node', [serverPath], {
  stdio: ['pipe', 'pipe', 'pipe']
});

// Handle server output
let buffer = '';
mcp.stdout.on('data', (data) => {
  buffer += data.toString();
  
  // Try to parse complete JSON messages
  const lines = buffer.split('\n');
  buffer = lines.pop() || '';
  
  for (const line of lines) {
    if (line.trim()) {
      try {
        const message = JSON.parse(line);
        console.log('Server response:', JSON.stringify(message, null, 2));
      } catch (e) {
        console.log('Server output:', line);
      }
    }
  }
});

mcp.stderr.on('data', (data) => {
  console.error('Server log:', data.toString().trim());
});

mcp.on('error', (error) => {
  console.error('Failed to start server:', error);
  process.exit(1);
});

mcp.on('close', (code) => {
  console.log(`Server exited with code ${code}`);
  process.exit(0);
});

// Send MCP requests
async function sendRequest(request) {
  console.log('\nSending request:', JSON.stringify(request, null, 2));
  mcp.stdin.write(JSON.stringify(request) + '\n');
}

// Wait for server to start
setTimeout(async () => {
  // 1. Initialize
  await sendRequest({
    jsonrpc: '2.0',
    method: 'initialize',
    params: {
      protocolVersion: '2024-11-05',
      capabilities: {},
      clientInfo: {
        name: 'test-client',
        version: '1.0.0'
      }
    },
    id: 1
  });
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  
  // 2. List resources
  await sendRequest({
    jsonrpc: '2.0',
    method: 'resources/list',
    params: {},
    id: 2
  });
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  
  // 3. Read a resource
  await sendRequest({
    jsonrpc: '2.0',
    method: 'resources/read',
    params: {
      uri: 'webforj://components'
    },
    id: 3
  });
  
  await new Promise(resolve => setTimeout(resolve, 2000));
  
  // 4. List tools
  await sendRequest({
    jsonrpc: '2.0',
    method: 'tools/list',
    params: {},
    id: 4
  });
  
  await new Promise(resolve => setTimeout(resolve, 1000));
  
  // Exit
  console.log('\nTest complete. Shutting down...');
  mcp.kill();
}, 1000);