package com.company.components;

public abstract class InputDecorator implements InputComponent {
    protected InputComponent wrappedComponent;

    public InputDecorator(InputComponent component) {
        this.wrappedComponent = component;
    }

    @Override
    public void render() {
        wrappedComponent.render();
    }
}
