package noodlehaven.ui;

import haven.*;

public class NonResizableSimpleDeco extends SimpleDeco {
    public NonResizableSimpleDeco(Window parent) {
        super(parent);
    }

    @Override
    protected boolean isResizable(Window wnd) {
        return false;  // Always non-resizable
    }
}