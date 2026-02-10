---
title: Agent Skills
sidebar_position: 2.6
---

<!-- Draft outline for review — sections to be filled in after approval -->

## What are agent skills?

<!--
- Portable instruction packages for AI coding assistants
- Follow the open Agent Skills specification (agentskills.io)
- Each skill = SKILL.md + references/ + scripts/
- AI loads them automatically when it detects a relevant task
-->

### How skills differ from MCP

<!--
- Table comparing MCP (live tools) vs. skills (static knowledge/workflows)
- Recommendation to use both together
-->

## Available skills

<!--
- Table of skills (currently just webforj-design)
- Brief description of each
-->

## Installation

<!--
- Tabbed setup instructions for Claude Code, VS Code Copilot, Cursor
- Project scope vs. personal scope paths
- Tip admonition about when to use each scope
-->

## `webforj-design` skill

<!--
- What it covers: palettes, tokens, component styling, themes, tables, Google Charts
- Core principle: all visual values use --dwc-* CSS custom properties
-->

### How it works

<!--
- The 5-step workflow: classify → scan → look up → write CSS → validate
- Brief explanation of each step
-->

### The `component_styles.py` script

<!--
- Python script that fetches live component data from dwc.style
- Usage examples (by Java class, by DWC tag, --list, --map)
- Prerequisites: Python 3, network access, 24-hour cache
-->

### Reference files

<!--
- Table of reference docs: colors.md, tokens.md, themes.md, component-styling.md, table.md, google-charts.md
- Brief description of what each contains
-->

## Using skills with MCP

<!--
- Example workflow: generate theme via MCP → refine with skill → validate
- Tip admonition to pair both for best results
- Cross-reference to the MCP page
-->
