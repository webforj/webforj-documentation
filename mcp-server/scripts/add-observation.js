#!/usr/bin/env node

/**
 * Quick script to add observations to MCP_OBSERVATIONS.md
 * Usage: node scripts/add-observation.js
 */

import { readFileSync, writeFileSync } from 'fs';
import { join, dirname } from 'path';
import { fileURLToPath } from 'url';
import { createInterface } from 'readline';

const __dirname = dirname(fileURLToPath(import.meta.url));
const observationsFile = join(__dirname, '..', 'MCP_OBSERVATIONS.md');

const categories = {
  '1': { name: 'Bugs & Issues', marker: '### 🐛 Bugs & Issues' },
  '2': { name: 'Feature Requests', marker: '### 🚀 Feature Requests' },
  '3': { name: 'Performance', marker: '### 📊 Performance Observations' },
  '4': { name: 'Search & Discovery', marker: '### 🔍 Search & Discovery' },
  '5': { name: 'Documentation Gaps', marker: '### 📝 Documentation Gaps' },
  '6': { name: 'Integration Issues', marker: '### 🤝 Integration Issues' },
  '7': { name: 'UX Improvements', marker: '### 💡 User Experience Improvements' },
  '8': { name: 'Configuration', marker: '### 🔧 Configuration & Setup' }
};

const rl = createInterface({
  input: process.stdin,
  output: process.stdout
});

const question = (prompt) => new Promise(resolve => rl.question(prompt, resolve));

async function main() {
  console.log('🔍 MCP Server Observation Logger\n');
  
  // Show categories
  console.log('Select observation category:');
  Object.entries(categories).forEach(([key, cat]) => {
    console.log(`  ${key}. ${cat.name}`);
  });
  
  const categoryChoice = await question('\nCategory (1-8): ');
  const category = categories[categoryChoice];
  
  if (!category) {
    console.log('❌ Invalid category');
    process.exit(1);
  }
  
  console.log(`\n📝 Adding observation to: ${category.name}\n`);
  
  // Get observation details based on category
  const date = new Date().toISOString().split('T')[0];
  let observation = { date };
  
  switch (categoryChoice) {
    case '1': // Bugs
      observation.issue = await question('Issue description: ');
      observation.steps = await question('Steps to reproduce (use ; to separate): ');
      observation.impact = await question('Impact (High/Medium/Low): ');
      observation.status = 'New';
      observation.resolution = 'Pending';
      break;
      
    case '2': // Features
      observation.feature = await question('Feature description: ');
      observation.useCase = await question('Use case: ');
      observation.priority = await question('Priority (High/Medium/Low): ');
      observation.status = 'Proposed';
      observation.notes = await question('Notes (optional): ') || '';
      break;
      
    case '3': // Performance
      observation.observation = await question('Performance observation: ');
      observation.context = await question('Context: ');
      observation.metrics = await question('Metrics (if any): ') || 'N/A';
      observation.solution = await question('Potential solution: ') || 'TBD';
      observation.status = 'Identified';
      break;
      
    case '4': // Search
      observation.queryType = await question('Query type: ');
      observation.expected = await question('Expected result: ');
      observation.actual = await question('Actual result: ');
      observation.improvement = await question('Improvement needed: ');
      observation.status = 'Open';
      break;
      
    case '5': // Documentation
      observation.component = await question('Component/Feature: ');
      observation.missing = await question('Missing information: ');
      observation.impact = await question('Impact: ');
      observation.status = 'Identified';
      observation.location = 'TBD';
      break;
      
    case '6': // Integration
      observation.integration = await question('Integration (e.g., Claude Desktop): ');
      observation.issue = await question('Issue description: ');
      observation.workaround = await question('Workaround (if any): ') || 'None';
      observation.fix = await question('Permanent fix needed: ') || 'TBD';
      observation.status = 'Open';
      break;
      
    case '7': // UX
      observation.current = await question('Current experience: ');
      observation.suggested = await question('Suggested improvement: ');
      observation.benefit = await question('User benefit: ');
      observation.effort = await question('Implementation effort (High/Medium/Low): ');
      observation.status = 'Proposed';
      break;
      
    case '8': // Configuration
      observation.area = await question('Configuration area: ');
      observation.issue = await question('Issue/Improvement: ');
      observation.solution = await question('Solution: ') || 'TBD';
      observation.docUpdated = 'No';
      observation.status = 'Open';
      break;
  }
  
  // Add observation to file
  const content = readFileSync(observationsFile, 'utf-8');
  const lines = content.split('\n');
  
  // Find the right table
  const markerIndex = lines.findIndex(line => line.includes(category.marker));
  const tableStart = lines.findIndex((line, i) => i > markerIndex && line.includes('|---'));
  
  if (tableStart === -1) {
    console.log('❌ Could not find table in category');
    process.exit(1);
  }
  
  // Build the table row
  let row = '| ' + date + ' | ';
  
  switch (categoryChoice) {
    case '1':
      row += `${observation.issue} | ${observation.steps.replace(/;/g, '<br>')} | ${observation.impact} | ${observation.status} | ${observation.resolution} |`;
      break;
    case '2':
      row += `${observation.feature} | ${observation.useCase} | ${observation.priority} | ${observation.status} | ${observation.notes} |`;
      break;
    case '3':
      row += `${observation.observation} | ${observation.context} | ${observation.metrics} | ${observation.solution} | ${observation.status} |`;
      break;
    case '4':
      row += `${observation.queryType} | ${observation.expected} | ${observation.actual} | ${observation.improvement} | ${observation.status} |`;
      break;
    case '5':
      row += `${observation.component} | ${observation.missing} | ${observation.impact} | ${observation.status} | ${observation.location} |`;
      break;
    case '6':
      row += `${observation.integration} | ${observation.issue} | ${observation.workaround} | ${observation.fix} | ${observation.status} |`;
      break;
    case '7':
      row += `${observation.current} | ${observation.suggested} | ${observation.benefit} | ${observation.effort} | ${observation.status} |`;
      break;
    case '8':
      row += `${observation.area} | ${observation.issue} | ${observation.solution} | ${observation.docUpdated} | ${observation.status} |`;
      break;
  }
  
  // Insert the new row after the table header
  lines.splice(tableStart + 1, 0, row);
  
  // Write back
  writeFileSync(observationsFile, lines.join('\n'));
  
  console.log('\n✅ Observation added successfully!');
  console.log(`📄 View in: ${observationsFile}`);
  
  rl.close();
}

main().catch(console.error);