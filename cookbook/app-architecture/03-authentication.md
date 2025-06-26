# Authentication & Security

## Q: How do I implement a complete authentication system with session management?

**A:** Use SessionStorage for web-safe authentication state management:

```java
import com.webforj.webstorage.SessionStorage;

public class AuthService {
    private static final String AUTH_KEY = "authenticated";
    private static final String USER_KEY = "currentUser";
    private static final String ROLE_KEY = "userRole";

    public boolean login(String username, String password) {
        // Validate credentials (replace with real authentication)
        if ("admin".equals(username) && "admin123".equals(password)) {
            SessionStorage.getCurrent().setItem(AUTH_KEY, "true");
            SessionStorage.getCurrent().setItem(USER_KEY, username);
            SessionStorage.getCurrent().setItem(ROLE_KEY, "ADMIN");
            return true;
        } else if ("user".equals(username) && "user123".equals(password)) {
            SessionStorage.getCurrent().setItem(AUTH_KEY, "true");
            SessionStorage.getCurrent().setItem(USER_KEY, username);
            SessionStorage.getCurrent().setItem(ROLE_KEY, "USER");
            return true;
        }
        return false;
    }

    public void logout() {
        SessionStorage.getCurrent().removeItem(AUTH_KEY);
        SessionStorage.getCurrent().removeItem(USER_KEY);
        SessionStorage.getCurrent().removeItem(ROLE_KEY);
    }

    public boolean isAuthenticated() {
        String authenticated = SessionStorage.getCurrent().getItem(AUTH_KEY);
        return "true".equals(authenticated);
    }

    public String getCurrentUser() {
        return SessionStorage.getCurrent().getItem(USER_KEY);
    }

    public boolean hasRole(String role) {
        String userRole = SessionStorage.getCurrent().getItem(ROLE_KEY);
        return role.equals(userRole);
    }

    // Token-based login for "Remember Me" functionality
    public boolean loginWithToken(String username, String token) {
        if (isValidRememberToken(username, token)) {
            SessionStorage.getCurrent().setItem(AUTH_KEY, "true");
            SessionStorage.getCurrent().setItem(USER_KEY, username);
            // Load role from persistent storage or database
            String role = getUserRoleFromDatabase(username);
            SessionStorage.getCurrent().setItem(ROLE_KEY, role);
            return true;
        }
        return false;
    }

    private boolean isValidRememberToken(String username, String token) {
        // Implement token validation logic
        // Check against database or secure token store
        return token != null && token.startsWith("valid_token_" + username);
    }

    private String getUserRoleFromDatabase(String username) {
        // Simulate database lookup
        return "admin".equals(username) ? "ADMIN" : "USER";
    }
}
```

## Q: Why should I use SessionStorage instead of static variables for authentication?

**A:** SessionStorage is web-safe and prevents cross-session contamination:

**DANGEROUS - Static variables can cause security issues:**
```java
// DON'T DO THIS - Dangerous in web environments
public class BadAuthService {
    private static boolean authenticated = false; // Shared across all users!
    private static String currentUser = null;     // Security risk!
    
    // This affects ALL users in the application server
    public static void login(String user) {
        authenticated = true;  // User A login affects User B!
        currentUser = user;    // User B sees User A's data!
    }
}
```

** CORRECT - SessionStorage is session-specific:**
```java
// DO THIS - Safe for web applications
public class AuthService {
    public boolean isAuthenticated() {
        String authenticated = SessionStorage.getCurrent().getItem(AUTH_KEY);
        return "true".equals(authenticated);
    }
    
    public String getCurrentUser() {
        return SessionStorage.getCurrent().getItem(USER_KEY);
    }
}
```

**Benefits of SessionStorage:**
- **Session Isolation**: Each browser session has separate storage
- **Automatic Cleanup**: Cleared when session ends
- **Web-Safe**: No cross-contamination between users
- **Persistent**: Survives page refreshes within session

## Q: How do I implement route guards to protect authenticated routes?

**A:** Use MainLayout with WillEnterObserver for centralized route protection:

```java
import com.webforj.router.annotation.Route;
import com.webforj.router.event.WillEnterObserver;
import com.webforj.router.event.WillEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.component.layout.applayout.AppLayout;

@Route
public class MainLayout extends Composite<AppLayout> implements WillEnterObserver {
    private H1 title = new H1();
    private Toolbar toolbar = new Toolbar();
    private AuthService authService = new AuthService();

    public MainLayout() {
        configureHeader();
        configureDrawer();
        Router.getCurrent().onNavigate(this::onNavigate);
    }

    @Override
    public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
        String location = event.getLocation();
        
        // Skip authentication check for public routes
        if (isPublicRoute(location)) {
            return;
        }

        // Check authentication for protected routes
        if (!authService.isAuthenticated()) {
            event.veto(true);
            event.getRouter().navigate(LoginView.class);
            Toast.show("Please log in to access this area", Theme.WARNING);
            return;
        }
        
        // Check role-based authorization
        if (isAdminRoute(location) && !authService.hasRole("ADMIN")) {
            event.veto(true);
            event.getRouter().navigate(HomeView.class);
            Toast.show("Access denied - Administrator privileges required", Theme.DANGER);
        }
    }

    private boolean isPublicRoute(String location) {
        return location.equals("/login") || 
               location.equals("/register") || 
               location.equals("/") ||
               location.equals("/public");
    }

    private boolean isAdminRoute(String location) {
        return location.startsWith("/admin") || 
               location.startsWith("/users") ||
               location.startsWith("/settings");
    }

    private void configureHeader() {
        if (authService.isAuthenticated()) {
            String currentUser = authService.getCurrentUser();
            Button logoutButton = new Button("Logout (" + currentUser + ")", 
                ButtonTheme.OUTLINED_GRAY, e -> {
                authService.logout();
                Router.getCurrent().navigate(LoginView.class);
                Toast.show("Logged out successfully", Theme.SUCCESS);
            });
            
            toolbar.addToStart(new AppDrawerToggle())
                   .addToTitle(title)
                   .addToEnd(logoutButton);
        } else {
            toolbar.addToTitle(title);
        }
        
        getBoundComponent().addToHeader(toolbar);
    }

    private void configureDrawer() {
        if (authService.isAuthenticated()) {
            Drawer drawer = new Drawer();
            
            // Add navigation items based on user role
            drawer.add(createNavItem("Home", HomeView.class, "home"));
            drawer.add(createNavItem("Customers", CustomerListView.class, "people"));
            
            if (authService.hasRole("ADMIN")) {
                drawer.add(new Hr());
                drawer.add(createNavItem("Admin Panel", AdminView.class, "settings"));
                drawer.add(createNavItem("User Management", UserManagementView.class, "user"));
            }
            
            getBoundComponent().addToDrawer(drawer);
        }
    }

    private RouterLink createNavItem(String text, Class<? extends Component> view, String icon) {
        RouterLink link = new RouterLink();
        link.add(new Icon(icon), new Span(text));
        link.setRoute(view);
        return link;
    }

    // CRITICAL: Prevent destroyed object exceptions during logout
    private void onNavigate(NavigateEvent ev) {
        // Safety check: don't update UI if component is destroyed
        if (title == null || !title.isAttached()) {
            return;
        }
        
        Set<Component> components = ev.getContext().getAllComponents();
        Component view = components.stream()
            .filter(c -> c.getClass().getSimpleName().endsWith("View"))
            .findFirst()
            .orElse(null);

        if (view != null) {
            FrameTitle frameTitle = view.getClass().getAnnotation(FrameTitle.class);
            try {
                title.setText(frameTitle != null ? frameTitle.value() : "");
            } catch (Exception e) {
                // Ignore exceptions during component destruction
            }
        }
    }
}
```

## Q: How do I implement role-based access control for specific views?

**A:** Use WillEnterObserver in individual views for fine-grained control:

```java
@Route("/admin")
@FrameTitle("Administration")
public class AdminView extends Composite<Div> implements WillEnterObserver {
    
    @Override
    public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
        AuthService authService = new AuthService();
        
        // Check authentication
        if (!authService.isAuthenticated()) {
            event.veto(true);
            event.getRouter().navigate(LoginView.class);
            Toast.show("Please log in to access admin area", Theme.WARNING);
            return;
        }
        
        // Check authorization
        if (!authService.hasRole("ADMIN")) {
            event.veto(true);
            event.getRouter().navigate(HomeView.class);
            Toast.show("Access denied - Administrator privileges required", Theme.DANGER);
            return;
        }

        event.veto(false);
    }
    
    public AdminView() {
        getBoundComponent().add(
            new H1("Administration Panel"),
            new Paragraph("Welcome to the admin area. Only administrators can access this page."),
            createAdminActions()
        );
    }
    
    private Component createAdminActions() {
        FlexLayout actions = FlexLayout.create()
            .vertical()
            .spacing("var(--dwc-space-m)")
            .build();
            
        Button userMgmt = new Button("User Management", ButtonTheme.PRIMARY);
        Button systemSettings = new Button("System Settings", ButtonTheme.OUTLINED_PRIMARY);
        Button reports = new Button("Reports", ButtonTheme.OUTLINED_PRIMARY);
        
        userMgmt.onClick(e -> Router.getCurrent().navigate(UserManagementView.class));
        systemSettings.onClick(e -> Router.getCurrent().navigate(SystemSettingsView.class));
        reports.onClick(e -> Router.getCurrent().navigate(ReportsView.class));
        
        actions.add(userMgmt, systemSettings, reports);
        return actions;
    }
}
```

## Q: How do I handle session expiration and automatic logout?

**A:** Implement session timeout with automatic cleanup:

```java
public class SessionManager {
    private static final String SESSION_START_KEY = "sessionStartTime";
    private static final String LAST_ACTIVITY_KEY = "lastActivityTime";
    private static final long SESSION_TIMEOUT_MS = 30 * 60 * 1000; // 30 minutes
    private static final long WARNING_THRESHOLD_MS = 5 * 60 * 1000; // 5 minutes warning
    
    private Interval sessionCheckInterval;
    private AuthService authService = new AuthService();
    
    public void startSessionMonitoring() {
        // Record session start time
        long currentTime = System.currentTimeMillis();
        SessionStorage.getCurrent().setItem(SESSION_START_KEY, String.valueOf(currentTime));
        updateLastActivity();
        
        // Check session every minute
        sessionCheckInterval = new Interval(60f, e -> checkSessionStatus());
        sessionCheckInterval.start();
    }
    
    public void updateLastActivity() {
        long currentTime = System.currentTimeMillis();
        SessionStorage.getCurrent().setItem(LAST_ACTIVITY_KEY, String.valueOf(currentTime));
    }
    
    private void checkSessionStatus() {
        if (!authService.isAuthenticated()) {
            stopSessionMonitoring();
            return;
        }
        
        long lastActivity = getLastActivityTime();
        long timeSinceActivity = System.currentTimeMillis() - lastActivity;
        
        if (timeSinceActivity >= SESSION_TIMEOUT_MS) {
            // Session expired - automatic logout
            handleSessionExpiration();
        } else if (timeSinceActivity >= (SESSION_TIMEOUT_MS - WARNING_THRESHOLD_MS)) {
            // Show warning
            showSessionWarning();
        }
    }
    
    private void handleSessionExpiration() {
        authService.logout();
        stopSessionMonitoring();
        
        Toast.show("Your session has expired. Please log in again.", Theme.WARNING);
        Router.getCurrent().navigate(LoginView.class);
    }
    
    private void showSessionWarning() {
        long timeRemaining = SESSION_TIMEOUT_MS - (System.currentTimeMillis() - getLastActivityTime());
        int minutesRemaining = (int) (timeRemaining / (60 * 1000));
        
        ConfirmDialog dialog = new ConfirmDialog(
            "Your session will expire in " + minutesRemaining + " minutes. Do you want to continue?",
            "Session Timeout Warning",
            ConfirmDialog.OptionType.YES_NO,
            ConfirmDialog.MessageType.WARNING
        );
        
        ConfirmDialog.Result result = dialog.show();
        if (result == ConfirmDialog.Result.YES) {
            updateLastActivity(); // Extend session
        } else {
            handleSessionExpiration();
        }
    }
    
    private long getLastActivityTime() {
        String lastActivityStr = SessionStorage.getCurrent().getItem(LAST_ACTIVITY_KEY);
        try {
            return Long.parseLong(lastActivityStr);
        } catch (NumberFormatException e) {
            return System.currentTimeMillis();
        }
    }
    
    public void stopSessionMonitoring() {
        if (sessionCheckInterval != null) {
            sessionCheckInterval.stop();
            sessionCheckInterval = null;
        }
    }
}
```

## Q: How do I implement "Remember Me" functionality securely?

**A:** Use LocalStorage with secure tokens and expiration:

```java
import com.webforj.webstorage.LocalStorage;

public class RememberMeService {
    private static final String REMEMBER_USERNAME_KEY = "rememberedUsername";
    private static final String REMEMBER_TOKEN_KEY = "rememberToken";
    private static final String REMEMBER_EXPIRATION_KEY = "rememberExpiration";
    private static final long REMEMBER_DURATION_MS = 30L * 24 * 60 * 60 * 1000; // 30 days
    
    public void setRememberMe(String username) {
        // Generate secure token (in production, use proper token generation)
        String token = generateSecureToken(username);
        long expirationTime = System.currentTimeMillis() + REMEMBER_DURATION_MS;
        
        LocalStorage localStorage = LocalStorage.getCurrent();
        localStorage.setItem(REMEMBER_USERNAME_KEY, username);
        localStorage.setItem(REMEMBER_TOKEN_KEY, token);
        localStorage.setItem(REMEMBER_EXPIRATION_KEY, String.valueOf(expirationTime));
    }
    
    public boolean hasValidRememberMe() {
        LocalStorage localStorage = LocalStorage.getCurrent();
        String token = localStorage.getItem(REMEMBER_TOKEN_KEY);
        String expirationStr = localStorage.getItem(REMEMBER_EXPIRATION_KEY);
        
        if (token == null || expirationStr == null) {
            return false;
        }
        
        try {
            long expiration = Long.parseLong(expirationStr);
            return System.currentTimeMillis() < expiration;
        } catch (NumberFormatException e) {
            clearRememberMe();
            return false;
        }
    }
    
    public String getRememberedUsername() {
        if (hasValidRememberMe()) {
            return LocalStorage.getCurrent().getItem(REMEMBER_USERNAME_KEY);
        }
        return null;
    }
    
    public boolean attemptAutoLogin(AuthService authService) {
        if (!hasValidRememberMe()) {
            return false;
        }
        
        String username = getRememberedUsername();
        String token = LocalStorage.getCurrent().getItem(REMEMBER_TOKEN_KEY);
        
        if (authService.loginWithToken(username, token)) {
            // Refresh the remember me token
            setRememberMe(username);
            return true;
        } else {
            clearRememberMe();
            return false;
        }
    }
    
    public void clearRememberMe() {
        LocalStorage localStorage = LocalStorage.getCurrent();
        localStorage.removeItem(REMEMBER_USERNAME_KEY);
        localStorage.removeItem(REMEMBER_TOKEN_KEY);
        localStorage.removeItem(REMEMBER_EXPIRATION_KEY);
    }
    
    private String generateSecureToken(String username) {
        // In production, use proper cryptographic token generation
        // This is a simplified example
        return "secure_token_" + username + "_" + System.currentTimeMillis();
    }
}
```

---

## Navigation

- [← Previous: Routing & Navigation](02-routing.md)
- [Next: Login Components →](04-login-components.md)
- [Back to Cookbook Index](../00-index.md)