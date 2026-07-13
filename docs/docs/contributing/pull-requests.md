---
sidebar_position: 3
title: Pull Requests
description: Prepare focused webforJ pull requests with clear issues, branches, descriptions, checks, and review follow-up.
hide_giscus_comments: true
---

Pull requests are easiest to review when they're focused, linked to the right issue, and include the validation that proves the change works.

## Start with an issue {#start-with-an-issue}

Open or find an issue before working on:

- New documentation sections or major rewrites
- New demos, cookbook recipes, or sample applications
- Changes to documented behavior
- Framework or API changes
- Broad navigation, site structure, or tooling changes

Small typo fixes, broken link fixes, and minor wording improvements can go straight to a pull request.

## Fork and clone the repository {#fork-and-clone-the-repository}

Fork the repository on GitHub, then clone your fork. These commands use the documentation repository; replace both repository names when contributing to the framework.

```bash
git clone https://github.com/<username>/webforj-documentation.git
cd webforj-documentation
git remote add upstream https://github.com/webforj/webforj-documentation.git
```

## Create a branch {#create-a-branch}

Update your fork from the upstream `main` branch, then create a topic branch:

```bash
git switch main
git fetch upstream
git merge --ff-only upstream/main
git push origin main
git switch -c docs/short-description
```

Use a branch name that describes the work. Examples include `docs/add-contributing-guide`, `fix/table-sorting-demo`, or `test/login-view-it`.

## Keep the scope reviewable {#keep-the-scope-reviewable}

Keep unrelated cleanup out of the pull request. A focused docs change shouldn't also reformat sample code, rename files, or reorganize sections unless those changes are required for the issue.

If the work becomes larger than expected, split it into smaller pull requests or leave follow-up work in the issue.

## Commit and push {#commit-and-push}

Use a concise conventional commit message, then push the topic branch to your fork:

```bash
git add <changed-files>
git commit -m "docs: describe the change"
git push -u origin docs/short-description
```

Common commit prefixes include `docs:`, `fix:`, `test:`, and `chore:`.

## Open the pull request {#open-the-pull-request}

Include:

- What changed
- Why it changed
- Screenshots or short recordings for visible UI or docs site changes
- The validation you ran
- `Closes #<issue-number>` when the pull request resolves an issue

Open the pull request against the upstream repository's `main` branch and allow maintainer edits. This lets maintainers apply small fixes or update the branch during review.

## Respond to review {#respond-to-review}

Review comments are part of the contribution process. Push follow-up commits to the same branch, answer questions directly, and mark conversations resolved only when the requested change or explanation is complete.

If a requested change would alter the scope of the pull request, say so and suggest a follow-up issue.

## Checklist {#checklist}

Before submitting:

- The change is scoped to one topic.
- New or changed docs have accurate links and headings.
- Code examples compile or are clearly marked as conceptual.
- New demos include the files needed by `ComponentDemo`.
- Relevant tests or builds have been run.
- The pull request links the issue when one exists.
