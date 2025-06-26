# Dialogs & User Feedback

## Q: How do I show different types of message dialogs to users?

**A:** Use `MessageDialog` and `OptionDialog` for various message types:

```java
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.component.optiondialog.OptionDialog;

public class MessageDialogExamples {
    
    public void showInfoMessage() {
        OptionDialog.showMessageDialog(
            "Operation completed successfully!", 
            "Success", 
            "Got it", 
            MessageDialog.MessageType.INFO
        );
    }
    
    public void showWarningMessage() {
        MessageDialog dialog = new MessageDialog(
            "This action cannot be undone",
            "Warning",
            MessageDialog.MessageType.WARNING
        );
        dialog.setButtonText("I Understand");
        dialog.show();
    }
    
    public void showErrorMessage() {
        OptionDialog.showMessageDialog(
            "Failed to save the file. Please check permissions.",
            "Error",
            "Close",
            MessageDialog.MessageType.ERROR
        );
    }
    
    public void showCustomStyledMessage() {
        MessageDialog dialog = new MessageDialog(
            "Welcome to the application!",
            "Welcome",
            MessageDialog.MessageType.PLAIN
        );
        dialog.setBlurred(true);
        dialog.setAlignment(MessageDialog.Alignment.TOP);
        dialog.setTheme(Theme.PRIMARY);
        dialog.setButtonTheme(ButtonTheme.PRIMARY);
        dialog.show();
    }
    
    public void showTimedMessage() {
        MessageDialog dialog = new MessageDialog(
            "This message will disappear in 3 seconds",
            "Auto-close"
        );
        dialog.setTimeout(3); // 3 seconds
        dialog.show();
    }
}
```

## Q: How do I create confirm dialogs with different options and handle responses?

**A:** Use `ConfirmDialog` with various option types and result handling:

```java
import com.webforj.component.optiondialog.ConfirmDialog;

public class ConfirmDialogExamples {
    
    public void showYesNoConfirmation() {
        ConfirmDialog dialog = new ConfirmDialog(
            "Are you sure you want to delete this item?",
            "Confirm Deletion",
            ConfirmDialog.OptionType.YES_NO,
            ConfirmDialog.MessageType.QUESTION
        );
        
        dialog.setDefaultButton(ConfirmDialog.Button.SECOND); // Default to "No"
        
        ConfirmDialog.Result result = dialog.show();
        switch (result) {
            case YES:
                deleteItem();
                Toast.show("Item deleted", Theme.SUCCESS);
                break;
            case NO:
                Toast.show("Deletion cancelled", Theme.INFO);
                break;
            default:
                // Handle other cases
                break;
        }
    }
    
    public void showOkCancelConfirmation() {
        ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
            "Do you want to save your changes?",
            "Unsaved Changes",
            ConfirmDialog.OptionType.OK_CANCEL,
            ConfirmDialog.MessageType.WARNING
        );
        
        if (result == ConfirmDialog.Result.OK) {
            saveChanges();
        }
    }
    
    public void showCustomButtonConfirmation() {
        ConfirmDialog dialog = new ConfirmDialog(
            "Choose how to handle the conflict",
            "Merge Conflict",
            ConfirmDialog.OptionType.CUSTOM
        );
        
        dialog.setButtonText(ConfirmDialog.Button.FIRST, "Keep Mine");
        dialog.setButtonText(ConfirmDialog.Button.SECOND, "Keep Theirs");
        dialog.setButtonText(ConfirmDialog.Button.THIRD, "Merge Both");
        
        ConfirmDialog.Result result = dialog.show();
        switch (result) {
            case FIRST_CUSTOM_BUTTON:
                keepLocalChanges();
                break;
            case SECOND_CUSTOM_BUTTON:
                keepRemoteChanges();
                break;
            case THIRD_CUSTOM_BUTTON:
                mergeBothChanges();
                break;
            default:
                // Handle timeout or unknown result
                break;
        }
    }
    
    public void showTimeoutConfirmation() {
        ConfirmDialog dialog = new ConfirmDialog(
            "Session will expire soon. Continue working?",
            "Session Timeout",
            ConfirmDialog.OptionType.YES_NO
        );
        
        dialog.setTimeout(10); // 10 seconds
        dialog.setDefaultButton(ConfirmDialog.Button.FIRST); // Default to Yes
        
        ConfirmDialog.Result result = dialog.show();
        if (result == ConfirmDialog.Result.TIMEOUT) {
            logoutUser();
            Toast.show("Session expired", Theme.WARNING);
        } else if (result == ConfirmDialog.Result.YES) {
            extendSession();
        }
    }
    
    public void showAbortRetryIgnoreDialog() {
        ConfirmDialog dialog = new ConfirmDialog(
            "Network connection failed. What would you like to do?",
            "Connection Error",
            ConfirmDialog.OptionType.ABORT_RETRY_IGNORE,
            ConfirmDialog.MessageType.ERROR
        );
        
        ConfirmDialog.Result result = dialog.show();
        switch (result) {
            case ABORT:
                cancelOperation();
                break;
            case RETRY:
                retryConnection();
                break;
            case IGNORE:
                continueOffline();
                break;
        }
    }
}
```

## Q: How do I create toast notifications with different styles and placements?

**A:** Use `Toast` with various themes, placements, and durations:

```java
import com.webforj.component.toast.Toast;

public class ToastExamples {
    
    public void showSuccessToast() {
        Toast toast = new Toast("Data saved successfully!", Theme.SUCCESS);
        toast.setPlacement(Toast.Placement.TOP_RIGHT);
        toast.setDuration(3000); // 3 seconds
        toast.open();
    }
    
    public void showErrorToast() {
        Toast.show("Failed to upload file", Theme.DANGER, Toast.Placement.TOP);
    }
    
    public void showWarningToast() {
        Toast toast = new Toast(
            "Your session will expire in 5 minutes",
            5000, // 5 seconds
            Theme.WARNING,
            Toast.Placement.BOTTOM_LEFT
        );
        toast.open();
    }
    
    public void showPersistentToast() {
        Toast toast = new Toast("Please complete all required fields", -1); // Persistent
        toast.setTheme(Theme.INFO);
        toast.setPlacement(Toast.Placement.TOP);
        
        // Add close button for persistent toast
        Button closeButton = new Button("×", ButtonTheme.GRAY);
        closeButton.onClick(e -> toast.close());
        
        toast.open();
    }
    
    public void showProgressToast() {
        Toast toast = new Toast("Upload in progress...", -1);
        toast.setTheme(Theme.PRIMARY);
        toast.setPlacement(Toast.Placement.BOTTOM_RIGHT);
        
        // Simulate progress updates
        Interval progressInterval = new Interval(0.5f, e -> {
            updateProgress(toast);
        });
        progressInterval.start();
        
        toast.open();
    }
    
    public void showStackedToasts() {
        // Multiple toasts will stack automatically
        Toast.show("First notification", Theme.INFO, Toast.Placement.BOTTOM_RIGHT);
        
        // Delay second toast slightly
        Timer.schedule(500, () -> {
            Toast.show("Second notification", Theme.SUCCESS, Toast.Placement.BOTTOM_RIGHT);
        });
        
        Timer.schedule(1000, () -> {
            Toast.show("Third notification", Theme.WARNING, Toast.Placement.BOTTOM_RIGHT);
        });
    }
    
    public void showInteractiveToast() {
        Toast toast = new Toast("New message received", -1);
        toast.setTheme(Theme.INFO);
        toast.setPlacement(Toast.Placement.TOP_RIGHT);
        
        // Add action buttons
        FlexLayout buttonContainer = FlexLayout.create()
            .horizontal()
            .spacing("var(--dwc-space-s)")
            .build();
            
        Button viewButton = new Button("View", ButtonTheme.PRIMARY);
        Button dismissButton = new Button("Dismiss", ButtonTheme.OUTLINED_GRAY);
        
        viewButton.onClick(e -> {
            openMessageView();
            toast.close();
        });
        
        dismissButton.onClick(e -> toast.close());
        
        buttonContainer.add(viewButton, dismissButton);
        toast.add(buttonContainer);
        toast.open();
    }
}
```

---

## Navigation

- [← Previous: Checkboxes & Radio Buttons](03-checkboxes-radios.md)
- [Next: Tables →](05-tables.md)
- [Back to Cookbook Index](../00-index.md)