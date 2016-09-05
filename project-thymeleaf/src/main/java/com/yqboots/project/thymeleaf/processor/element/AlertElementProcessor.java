package com.yqboots.project.thymeleaf.processor.element;

import com.yqboots.project.thymeleaf.i18n.MessageKeys;
import com.yqboots.project.thymeleaf.processor.support.AlertLevel;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.VariablesMap;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * The alert message, which contains a hidden button, title and messages.
 * <p/>
 * <p/>
 * <div class="alert alert-warning fade in" th:if="${not #arrays.isEmpty(messages)}">
 * <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
 * <h4><i class="glyphicon glyphicon-warning-sign"></i>You got an error!</h4>
 * <ul class="list-unstyled">
 * <li th:each="message : ${messages}" th:text="${message}">Input is incorrect</li>
 * </ul>
 * </div>
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class AlertElementProcessor extends AbstractMarkupSubstitutionElementProcessor {
    public static final String DEFAULT_LEVEL = "warning";
    public static final String DEFAULT_ATTRIBUTE = "messages";

    public static final String ATTR_LEVEL = "level";
    public static final String ATTR_ATTRIBUTE = "attribute";

    public AlertElementProcessor() {
        super("alert");
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    protected List<Node> getMarkupSubstitutes(final Arguments arguments, final Element element) {
        final List<Node> nodes = new ArrayList<>();

        String levelAttrValue = element.getAttributeValue(ATTR_LEVEL);
        if (StringUtils.isBlank(levelAttrValue)) {
            levelAttrValue = DEFAULT_LEVEL;
        }

        String attrAttributeValue = element.getAttributeValue(ATTR_ATTRIBUTE);
        if (StringUtils.isBlank(attrAttributeValue)) {
            attrAttributeValue = DEFAULT_ATTRIBUTE;
        }

        VariablesMap<String, Object> variables = arguments.getContext().getVariables();
        Object _messages = variables.get(attrAttributeValue);
        // messages should be String or String[]
        if (_messages == null || (!(_messages instanceof String) && !(_messages instanceof String[]))) {
            return nodes;
        }

        String[] messages;
        if (_messages instanceof String) {
            messages = new String[]{(String) _messages};
        } else {
            messages = (String[]) _messages;
        }

        if (ArrayUtils.isEmpty(messages)) {
            return nodes;
        }

        nodes.add(build(arguments, messages, levelAttrValue));

        return nodes;
    }

    /**
     * Builds the alert node.
     *
     * @param arguments the arguments
     * @param messages the messages displayed
     * @param level    the alert level
     * @return the alert node
     */
    private Node build(final Arguments arguments, final String[] messages, final String level) {
        final Element container = new Element("div");
        container.setAttribute("class", AlertLevel.getStyleClass(level) + " fade in");

        container.addChild(buildHiddenButton());
        container.addChild(buildTitle(arguments));
        container.addChild(buildMessages(arguments, messages));

        return container;
    }

    /**
     * Builds message nodes.
     *
     * @param arguments the arguments
     * @param messages the messages displayed
     * @return the message nodes
     */
    private Node buildMessages(final Arguments arguments, final String[] messages) {
        final Element result = new Element("ul");
        result.setAttribute("class", "list-unstyled");

        Element li;
        for (String message : messages) {
            li = new Element("li");
            // the client should control the nls message?
            li.addChild(new Text(getMessage(arguments, message, new Object[]{})));
            result.addChild(li);
        }

        return result;
    }

    /**
     * Builds the title node.
     *
     * @param arguments the arguments
     * @return the title node
     */
    private Node buildTitle(final Arguments arguments) {
        final Element result = new Element("h4");

        final Element icon = new Element("i");
        icon.setAttribute("class", "glyphicon glyphicon-warning-sign");

        result.addChild(icon);

        result.addChild(new Text(getMessage(arguments, MessageKeys.ALERT_MESSAGE_TITLE, new Object[]{})));

        return result;
    }

    /**
     * Builds the hidden button node.
     *
     * @return the hidden button node
     */
    private static Node buildHiddenButton() {
        final Element result = new Element("button");
        result.setAttribute("type", "button");
        result.setAttribute("class", "close");
        result.setAttribute("data-dismiss", "alert");
        result.setAttribute("aria-hidden", "true");

        result.addChild(new Text("×"));

        return result;
    }
}