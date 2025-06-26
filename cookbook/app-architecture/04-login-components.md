# Login Components & Forms

## Q: How do I properly use the webforJ Login component in a routed application?

**A:** Use `Composite<Login>` pattern with proper internationalization and routing integration:

```java
import com.webforj.component.Composite;
import com.webforj.component.login.Login;
import com.webforj.component.login.LoginI18n;
import com.webforj.router.Router;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route(value = "/login")
@FrameTitle("Login - My Application")
public class LoginView extends Composite<Login> {

    public LoginView() {
        AuthService authService = new AuthService();
        
        // If already authenticated, redirect to dashboard
        if (authService.isAuthenticated()) {
            Router.getCurrent().navigate(HomeView.class);
            return;
        }
        
        Login login = getBoundComponent();
        
        // Configure login internationalization - CORRECT API
        LoginI18n i18n = new LoginI18n();
        i18n.getError().setTitle("Login Failed");
        i18n.getError().setMessage("Invalid username or password. Please try again.<br><br><strong>Demo Credentials:</strong><br>Username: admin<br>Password: admin123");
        
        login.setI18n(i18n);
        login.open(); // CRITICAL: Must call open() to display the dialog
        
        // Handle login submission
        login.onSubmit(event -> {
            String username = event.getUsername();
            String password = event.getPassword();
            
            if (authService.login(username, password)) {
                // Login successful - redirect to dashboard
                Router.getCurrent().navigate(HomeView.class);
            } else {
                // Login failed - show error
                login.setError(true);
            }
        });
    }
}
```

## Q: What are the correct LoginI18n API methods?

**A:** Use these VERIFIED LoginI18n methods based on working examples:

```java
// CORRECT LoginI18n API
LoginI18n i18n = new LoginI18n();

// Error configuration (confirmed working)
i18n.getError().setTitle("Login Failed");
i18n.getError().setMessage("Invalid credentials message");

// Cancel button configuration (confirmed working)  
i18n.setCancel("Cancel");
```

## Q: How do I implement "Remember Me" functionality with Login component?

**A:** Use the LoginSubmitEvent's isRememberMe() method with LocalStorage:

```java
private void handleLoginWithRememberMe(LoginSubmitEvent event) {
    String username = event.getUsername();
    String password = event.getPassword();
    boolean rememberMe = event.isRememberMe();
    
    AuthService authService = new AuthService();
    
    if (authService.login(username, password)) {
        // Handle remember me functionality
        if (rememberMe) {
            RememberMeService rememberMeService = new RememberMeService();
            rememberMeService.setRememberMe(username);
        } else {
            // Clear remember me data if not selected
            RememberMeService rememberMeService = new RememberMeService();
            rememberMeService.clearRememberMe();
        }
        
        Router.getCurrent().navigate(HomeView.class);
    } else {
        Login login = getBoundComponent();
        login.setError(true);
        login.setEnabled(true);
    }
}

// Check for remembered user on login page load
public LoginView() {
    RememberMeService rememberMeService = new RememberMeService();
    AuthService authService = new AuthService();
    
    if (rememberMeService.attemptAutoLogin(authService)) {
        Router.getCurrent().navigate(HomeView.class);
        return;
    }
    
    // Regular login setup...
    setupLoginComponent();
}

private void setupLoginComponent() {
    Login login = getBoundComponent();
    
    // Pre-fill username if remembered
    RememberMeService rememberMeService = new RememberMeService();
    String rememberedUsername = rememberMeService.getRememberedUsername();
    
    if (rememberedUsername != null) {
        // Note: Login component doesn't have setUsername method
        // So we show it in a helpful message instead
        LoginI18n i18n = new LoginI18n();
        i18n.getError().setTitle("Welcome Back");
        i18n.getError().setMessage("We remember you! Please enter your password for: " + rememberedUsername);
        login.setI18n(i18n);
    }
    
    login.open();
    login.onSubmit(this::handleLoginWithRememberMe);
}
```

---

## Navigation

- [← Previous: Authentication & Security](03-authentication.md)
- [Next: Component Lifecycle Safety →](05-component-lifecycle.md)
- [Back to Cookbook Index](../00-index.md)