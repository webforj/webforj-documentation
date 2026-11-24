---
title: Roles and Permissions
sidebar_position: 5
description: Restrict routes to BBj security groups with @RolesAllowed and to BBj user permissions with @BBjPermission.
---

A signed in user carries the BBj security groups as roles and the BBj user permissions as authorities. Routes check them with annotations, and views check them in code.

## Roles {#roles}

The roles of a signed in user are the names of the BBj security groups the user is a member of. The app reads them with the token of the user, so it needs no extra account in BBjServices.

`@RolesAllowed` takes the name of a security group. A signed in user who isn't a member of any listed group is redirected to the deny path.

```java title="SalesView.java"
@Route("/sales")
@RolesAllowed("Sales EU")
public class SalesView extends Composite<Div> {

  public SalesView() {
    getBoundComponent().add(new Paragraph("Only members of Sales EU see this page."));
  }
}
```

`BBjSecurity.hasRole(String role)` checks a role in code. It returns `false` when nobody is signed in or the role is `null`. The principal of `BBjSecurity.currentUser()` gives all roles through `getRoles()`.

```java title="Role check"
Button export = new Button("Export");
export.setVisible(BBjSecurity.hasRole("Sales EU"));
```

`@RolesAllowed` and `hasRole` compare the exact group name, case sensitive. `"sales eu"` doesn't match the group `Sales EU`.

The app reads the roles at the sign in and again at every renewal of the token. A change of group membership in BBjServices takes effect no later than the next renewal. A page reload keeps the stored roles until then.

## Permissions {#permissions}

The permissions of a signed in user are the BBj user permissions the user holds. The app reads them with the token of the user at the sign in and again at every renewal of the token, and stores them as the authorities of the principal. A change of the permissions in BBjServices takes effect no later than the next renewal. A page reload keeps the stored permissions until then.

`@BBjPermission` takes one or more constants of `BBjPermissions`. The route requires a signed in user, and a visitor without a sign in is redirected to the authentication path. The user needs any one of the listed permissions. A user who holds none of them, and a route with an empty list, is redirected to the deny path with the reason `insufficient_permissions`. When the user holds a listed permission, the next evaluator decides.

```java title="ServerView.java"
@Route("/server")
@BBjPermission(BBjPermissions.ADMIN_ACCESS)
public class ServerView extends Composite<Div> {

  public ServerView() {
    getBoundComponent().add(new Paragraph("Only users with admin-access see this page."));
  }
}
```

A user without the permission who opens `/server` is redirected to the deny path as in `/denied?reason=insufficient_permissions`.

`BBjSecurity.hasAuthority(BBjPermissions permission)` checks a permission in code. It returns `false` when nobody is signed in, and throws a `NullPointerException` when the permission is `null`. `BBjSecurity.hasAuthority(String authority)` takes the BBj name of the permission, which is the `toString()` value of the constant. It returns `false` when nobody is signed in or the authority is `null`. The principal of `BBjSecurity.currentUser()` gives all permissions through `getAuthorities()`.

```java title="Permission check"
Button restart = new Button("Restart");
restart.setVisible(BBjSecurity.hasAuthority(BBjPermissions.ADMIN_ACCESS));
```

| Constant | BBj name |
|----------|----------|
| `BBjPermissions.USER` | `user` |
| `BBjPermissions.ADD_USER` | `add-user` |
| `BBjPermissions.ATTACH_EXISTING_DB` | `attach-existing-db` |
| `BBjPermissions.SET_PERMISSIONS` | `set-permissions` |
| `BBjPermissions.BBJ_PROCESSES` | `bbj-processes` |
| `BBjPermissions.CREATE_TRIGGER` | `create-trigger` |
| `BBjPermissions.REMOVE_USER` | `remove-user` |
| `BBjPermissions.CREATE_NEW_DB` | `create-new-db` |
| `BBjPermissions.ADMIN_ACCESS` | `admin-access` |
| `BBjPermissions.SET_PASSWORDS` | `set-passwords` |
| `BBjPermissions.DROP_DB` | `drop-db` |
| `BBjPermissions.SQL_CONNECTIONS` | `sql-connections` |
| `BBjPermissions.SERVER_CONFIG` | `server-config` |
| `BBjPermissions.CONNECTION_POOLS` | `connection-pools` |
| `BBjPermissions.COPY_JOBS` | `copy-jobs` |
| `BBjPermissions.TABLE_ANALYSIS_QUEUE` | `table-analysis-queue` |
| `BBjPermissions.FORCE_CLOSE_FILES` | `force-close-files` |
| `BBjPermissions.REPLICATION` | `replication` |
| `BBjPermissions.SCHEDULING` | `scheduling` |
| `BBjPermissions.AUDITING` | `auditing` |
| `BBjPermissions.ASYNCH_TRIGGER` | `asynch-trigger` |
| `BBjPermissions.DOCUMENT_ARCHIVES` | `document-archives` |
| `BBjPermissions.EMAIL_SERVICES` | `email-services` |
| `BBjPermissions.METRICS` | `metrics` |
| `BBjPermissions.FILE_MANAGER` | `file-manager` |
| `BBjPermissions.VIEW_OPEN_FILES` | `view-open-files` |
