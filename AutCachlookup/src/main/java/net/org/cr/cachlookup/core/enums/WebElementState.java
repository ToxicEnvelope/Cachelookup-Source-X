package net.org.cr.cachlookup.core.enums;

public enum WebElementState {

    VISIBLE("VISIBLE"),
    INVISIBLE("INVISIBLE"),
    PRESENCE("PRESENCE"),
    CLICKABLE("CLICKABLE");

    private String _value;

    WebElementState(String value) {
        this._value = value;
    }

    public String getValue() {
        return _value;
    }
}
