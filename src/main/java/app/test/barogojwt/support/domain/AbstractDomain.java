package app.test.barogojwt.support.domain;

public abstract class AbstractDomain {

    protected void assertFalse(boolean value, ExplainableMessage explainableMessage) {
        if (value) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertTrue(boolean value, ExplainableMessage explainableMessage) {
        if (!value) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertMinLength(String value, int minimum, ExplainableMessage explainableMessage) {
        int length = value.trim().length();
        if (length < minimum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertMaxLength(String value, int maximum, ExplainableMessage explainableMessage) {
        int length = value.trim().length();
        if (length > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertLength(String value, int minimum, int maximum, ExplainableMessage explainableMessage) {
        int length = value.trim().length();
        if (length < minimum || length > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertNotEmpty(String value, ExplainableMessage explainableMessage) {
        if (value == null || value.trim().isEmpty()) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertNotEquals(Object value1, Object value2, ExplainableMessage explainableMessage) {
        if (value1.equals(value2)) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertNotNull(Object value, ExplainableMessage explainableMessage) {
        if (value == null) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertRange(double value, double minimum, double maximum, ExplainableMessage explainableMessage) {
        if (value < minimum || value > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertRange(float value, float minimum, float maximum, ExplainableMessage explainableMessage) {
        if (value < minimum || value > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertRange(int value, int minimum, int maximum, ExplainableMessage explainableMessage) {
        if (value < minimum || value > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertRange(long value, long minimum, long maximum, ExplainableMessage explainableMessage) {
        if (value < minimum || value > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }

    protected void assertRange(String value, long minimum, long maximum, ExplainableMessage explainableMessage) {
        if (value.length() < minimum || value.length() > maximum) {
            throw new DomainValidationException(explainableMessage);
        }
    }
}
