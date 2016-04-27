package com.yqboots.security.core.audit.annotation;

import com.yqboots.security.core.audit.interceptor.AuditAttribute;
import com.yqboots.security.core.audit.interceptor.AuditAttributeSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Administrator on 2015-12-14.
 */
@Component
public class AnnotationAuditAttributeSource implements AuditAttributeSource, Serializable {
    private final AuditAnnotationParser auditAnnotationParser;

    @Autowired
    public AnnotationAuditAttributeSource(AuditAnnotationParser auditAnnotationParser) {
        super();
        this.auditAnnotationParser = auditAnnotationParser;
    }

    @Override
    public AuditAttribute getAuditAttribute(Method method) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        return this.auditAnnotationParser.parseAuditAnnotation(method);
    }
}