#!/bin/bash

# Script to build MCP index after Maven build

echo "Building MCP server index..."

# Change to MCP server directory
cd "$(dirname "$0")/.."

# Install dependencies if needed
if [ ! -d "node_modules" ]; then
  echo "Installing MCP server dependencies..."
  npm install
fi

# Build TypeScript
npm run build

# Run the index builder (separate script that just builds the index)
node dist/build-index.js

echo "MCP index built successfully"