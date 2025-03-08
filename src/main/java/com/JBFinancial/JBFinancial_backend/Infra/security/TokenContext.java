// src/main/java/com/JBFinancial/JBFinancial_backend/Infra/security/TokenContext.java

package com.JBFinancial.JBFinancial_backend.Infra.security;

public class TokenContext {
    private static final ThreadLocal<Boolean> shouldExpire = ThreadLocal.withInitial(() -> Boolean.FALSE);

    public static Boolean getShouldExpire() {
        return shouldExpire.get();
    }

    public static void setShouldExpire(Boolean value) {
        shouldExpire.set(value);
    }

    public static void clear() {
        shouldExpire.remove();
    }
}