package com.github.hypfvieh.config.xml;

public class ConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String _message, Throwable _cause, boolean _enableSuppression,
            boolean _writableStackTrace) {
        super(_message, _cause, _enableSuppression, _writableStackTrace);
    }

    public ConfigurationException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

    public ConfigurationException(String _message) {
        super(_message);
    }

    public ConfigurationException(Throwable _cause) {
        super(_cause);
    }

}
