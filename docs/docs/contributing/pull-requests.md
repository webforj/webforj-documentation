---
sidebar_position: 4
title: Pull Requests
description: Prepare focused webforJ framework or documentation pull requests with clear scope, validation, and review follow-up.
hide_giscus_comments: true
---

Pull requests are easiest to review when they're focused, linked to the correct issue, and include the validation that proves the change works.

## Start with an issue {#start-with-an-issue}

Open or find an issue before working on:

- Framework behavior or public API changes
- New components, integrations, or build features
- New documentation sections or major rewrites
- New demos, cookbook recipes, or sample applications
- Broad navigation, repository structure, or tooling changes

Small typo fixes, broken link fixes, and isolated corrections can go straight to a pull request. Submit the issue and pull request to the repository that owns the change. Cross-link companion framework and documentation work when both repositories are affected.

## Fork and clone the repository {#fork-and-clone-the-repository}

Fork the repository on GitHub, then clone your fork and add the official repository as `upstream`.

### Framework repository {#framework-repository}

```bash
git clone https://github.com/<username>/webforj.git
cd webforj
git remote add upstream https://github.com/webforj/webforj.git
```

### Documentation repository {#documentation-repository}

```bash
git clone https://github.com/<username>/webforj-documentation.git
cd webforj-documentation
git remote add upstream https://github.com/webforj/webforj-documentation.git
```

Confirm that `origin` points to your fork and `upstream` points to the official repository:

```bash
git remote -v
```

## Create a branch {#create-a-branch}

Update your fork from the upstream `main` branch, then create a topic branch:

```bash
git switch main
git fetch upstream
git merge --ff-only upstream/main
git push origin main
git switch -c fix/short-description
```

Use a branch name that describes the work. Examples include `feat/add-component-api`, `fix/table-selection`, `docs/add-contributing-guide`, and `test/login-view-it`.

## Keep the scope reviewable {#keep-the-scope-reviewable}

Keep unrelated cleanup out of the pull request. A focused framework fix shouldn't reorganize unrelated modules, and a focused documentation change shouldn't reformat unrelated sample code.

If the work becomes larger than expected, split it into smaller pull requests or leave follow-up work in the issue. Framework and documentation changes must use separate pull requests because they belong to separate repositories.

## Commit and push {#commit-and-push}

The framework contribution policy requires [Conventional Commits](https://www.conventionalcommits.org/). Use the same format for documentation changes so the history remains consistent:

```bash
git add <changed-files>
git commit -m "fix: describe the corrected behavior"
git push -u origin fix/short-description
```

Common commit types include `feat`, `fix`, `docs`, `test`, `refactor`, `build`, and `chore`. Add a scope when it makes the affected area clearer, such as `fix(table): preserve selection`.

## Open the pull request {#open-the-pull-request}

Open the pull request against the official repository's `main` branch and allow maintainer edits. Include:

- What changed and why
- The user-visible behavior before and after the change
- Screenshots or short recordings for visible UI or docs site changes
- The tests, builds, and linters you ran
- Any validation you couldn't run and the reason
- Compatibility, migration, or dependency considerations
- Links to companion framework or documentation work
- `Closes #<issue-number>` when the pull request resolves an issue in the same repository

For framework changes, call out public API changes and new dependencies explicitly. For documentation changes, identify the pages and demo routes reviewers should inspect.

## Respond to review {#respond-to-review}

Push follow-up commits to the same branch, answer questions directly, and mark conversations resolved only when the requested change or explanation is complete.

If a requested change would alter the pull request scope, say so and suggest a follow-up issue. Don't open a replacement pull request unless a maintainer asks for one.

## Checklist {#checklist}

Before submitting:

- The change is scoped to one issue or outcome.
- The branch is current enough with upstream `main` to validate the change.
- The implementation follows the repository's adjacent patterns.
- Public API documentation or user documentation is updated when behavior changes.
- New behavior and bug fixes have appropriate tests.
- Relevant formatters, linters, tests, and builds pass.
- The pull request links its issue and any companion work.
- Maintainer edits are allowed.
