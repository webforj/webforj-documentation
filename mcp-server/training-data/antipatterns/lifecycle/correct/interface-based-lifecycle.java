// CORRECT PATTERN - THIS IS HOW TO DO IT
// This file shows the proper webforJ lifecycle management using interfaces

package com.example.correct;

import com.webforj.App;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.observer.DidEnterObserver;
import com.webforj.router.observer.DidLeaveObserver;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.event.DidLeaveEvent;

/**
 * CORRECT: Proper lifecycle management in webforJ using observer interfaces
 */
public class CorrectLifecycleExample extends App implements DidEnterObserver, DidLeaveObserver {
    
    private Div container;
    private Paragraph statusText;
    private boolean isDataLoaded = false;
    
    @Override
    public void run() {
        // ✅ CORRECT: Initialize UI components in run()
        container = new Div();
        statusText = new Paragraph("Initializing...");
        
        container.add(statusText);
        add(container);
        
        // ✅ CORRECT: Component setup happens here, not in lifecycle events
        setupUI();
    }
    
    @Override
    public void onDidEnter(DidEnterEvent event) {
        // ✅ CORRECT: This runs when the view becomes active/visible
        System.out.println("View entered - loading data");
        statusText.setText("Loading data...");
        
        // Perform initialization tasks
        loadInitialData();
        subscribeToUpdates();
        
        // You can access route parameters here if needed
        if (event.getLocation().getPath().contains("user")) {
            loadUserSpecificData();
        }
    }
    
    @Override
    public void onDidLeave(DidLeaveEvent event) {
        // ✅ CORRECT: This runs when navigating away from the view
        System.out.println("View leaving - cleaning up");
        
        // Perform cleanup tasks
        unsubscribeFromUpdates();
        clearTemporaryData();
    }
    
    private void setupUI() {
        // UI setup that doesn't depend on lifecycle
        container.setStyle("padding", "20px");
        container.setStyle("max-width", "800px");
    }
    
    private void loadInitialData() {
        // Simulate data loading
        CompletableFuture.runAsync(() -> {
            // Fetch data
            try {
                Thread.sleep(1000); // Simulate network delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            // Update UI on completion
            isDataLoaded = true;
            statusText.setText("Data loaded successfully!");
        });
    }
    
    private void subscribeToUpdates() {
        // Set up any event listeners or subscriptions
        System.out.println("Subscribed to updates");
    }
    
    private void unsubscribeFromUpdates() {
        // Clean up subscriptions
        System.out.println("Unsubscribed from updates");
    }
    
    private void clearTemporaryData() {
        // Clear any temporary state
        isDataLoaded = false;
    }
    
    private void loadUserSpecificData() {
        // Load data specific to a user route
        System.out.println("Loading user-specific data");
    }
}

/**
 * Alternative example using parameter routes
 */
@Route("products/:id")
public class ProductView extends App implements DidEnterObserver, ParameterizedRoute {
    
    private String productId;
    
    @Override
    public void run() {
        // Initial setup
    }
    
    @Override
    public void setParameter(BeforeEnterEvent event, @Param("id") String id) {
        // ✅ CORRECT: Handle route parameters
        this.productId = id;
    }
    
    @Override
    public void onDidEnter(DidEnterEvent event) {
        // ✅ CORRECT: Load data when view is entered
        loadProduct(productId);
    }
    
    private void loadProduct(String id) {
        // Load product data
    }
}