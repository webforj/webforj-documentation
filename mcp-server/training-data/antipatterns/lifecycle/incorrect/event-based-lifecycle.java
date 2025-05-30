// INCORRECT PATTERN - DO NOT USE
// This file shows common AI mistakes when generating lifecycle code
// AI often assumes event-based lifecycle from React/Vue patterns

package com.example.incorrect;

import com.webforj.App;
import com.webforj.component.html.elements.Div;

/**
 * INCORRECT: This shows what AI often generates but is wrong for webforJ
 */
public class IncorrectLifecycleExample extends App {
    
    @Override
    public void run() {
        Div container = new Div();
        
        // ❌ WRONG: No onAttach method exists in webforJ
        self.onAttach(e -> {
            System.out.println("Component attached");
            loadInitialData();
        });
        
        // ❌ WRONG: No onDetach method
        self.onDetach(e -> {
            System.out.println("Component detached");
            cleanup();
        });
        
        // ❌ WRONG: No addEventListener for lifecycle events
        this.addEventListener("mount", e -> {
            initializeComponent();
        });
        
        // ❌ WRONG: No lifecycle hooks like React
        this.useEffect(() -> {
            // This is React, not Java!
            subscribeToUpdates();
            return () -> unsubscribeFromUpdates();
        });
        
        // ❌ WRONG: No mounted() method like Vue
        this.mounted(() -> {
            setupEventListeners();
        });
        
        add(container);
    }
    
    // These methods would never be called with the incorrect patterns above
    private void loadInitialData() {}
    private void cleanup() {}
    private void initializeComponent() {}
    private void subscribeToUpdates() {}
    private void unsubscribeFromUpdates() {}
    private void setupEventListeners() {}
}