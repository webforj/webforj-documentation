#!/usr/bin/env python3
"""
check-indentation.py

Checks that all fenced code blocks in markdown/mdx files use 2-space indentation,
not 4-space or tab indentation.

A code block is flagged if:
  - Every indented line has indentation that is a strict multiple of 4 (pure 4-space block)
  - Or any line uses a tab character

Exit code 0 = all clear
Exit code 1 = violations found

Usage:
  python scripts/check-indentation.py
"""

import os
import sys

FENCE = "```"
DOCS_DIR = os.path.join(os.path.dirname(__file__), "..", "docs", "docs")


def check_file(fpath):
    violations = []
    with open(fpath, "r", encoding="utf-8") as f:
        lines = f.readlines()

    in_code_block = False
    code_block_lines = []
    block_start_line = 0
    lang = ""

    for i, line in enumerate(lines, start=1):
        stripped = line.rstrip("\n")

        if stripped.startswith(FENCE):
            if not in_code_block:
                in_code_block = True
                code_block_lines = []
                block_start_line = i + 1
                lang = stripped[3:].split()[0] if len(stripped) > 3 else ""
            else:
                violations += check_block(fpath, block_start_line, lang, code_block_lines)
                in_code_block = False
                code_block_lines = []
        elif in_code_block:
            code_block_lines.append((i, stripped))

    return violations


def check_block(fpath, block_start, lang, lines):
    violations = []
    tab_lines = [(lineno, l) for lineno, l in lines if l.startswith("\t")]
    indented = [(lineno, l) for lineno, l in lines if l.strip() and (len(l) - len(l.lstrip(" "))) > 0]

    # Flag tab indentation
    for lineno, l in tab_lines:
        violations.append({
            "file": fpath,
            "line": lineno,
            "block_start": block_start,
            "lang": lang,
            "reason": "tab indentation",
            "content": l[:60],
        })

    if not indented or tab_lines:
        return violations

    indent_levels = {len(l) - len(l.lstrip(" ")) for _, l in indented}

    # Flag if all indented lines are pure multiples of 4 (4-space block)
    if all(n % 4 == 0 for n in indent_levels):
        violations.append({
            "file": fpath,
            "line": block_start,
            "block_start": block_start,
            "lang": lang,
            "reason": f"4-space indentation (indent levels: {sorted(indent_levels)})",
            "content": None,
        })

    return violations


def main():
    docs_dir = os.path.abspath(DOCS_DIR)
    all_violations = []

    for root, dirs, files in os.walk(docs_dir):
        for fname in sorted(files):
            if fname.endswith(".md") or fname.endswith(".mdx"):
                fpath = os.path.join(root, fname)
                all_violations += check_file(fpath)

    if not all_violations:
        print("✅ All code blocks use correct 2-space indentation.")
        return 0

    print(f"❌ Found {len(all_violations)} indentation violation(s):\n")
    current_file = None
    for v in all_violations:
        rel = os.path.relpath(v["file"], docs_dir)
        if rel != current_file:
            print(f"  {rel}")
            current_file = rel
        if v["content"]:
            print(f"    line {v['line']:4d} ({v['lang'] or 'no lang'}): {v['reason']}")
            print(f"           {repr(v['content'])}")
        else:
            print(f"    line {v['line']:4d} ({v['lang'] or 'no lang'}): {v['reason']}")

    return 1


if __name__ == "__main__":
    sys.exit(main())
