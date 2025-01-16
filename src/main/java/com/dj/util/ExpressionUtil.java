package com.dj.util;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author longjie
 */
public class ExpressionUtil {
    /**
     * 定义spel表达式解析器
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();
    /**
     * 定义spel解析模版
     */
    private static final ParserContext PARSER_CONTEXT = new TemplateParserContext();
    /**
     * 定义spel上下文对象进行解析
     */
    private static final EvaluationContext CONTEXT = new StandardEvaluationContext();
    /**
     * 方法参数解析器
     */
    private static final ParameterNameDiscoverer PND = new DefaultParameterNameDiscoverer();


    /**
     *
     * 根据spring el表达式获取参数的值，
     * @param key 为表达式内容
     * @param method 代理的方法
     * @param args 该方法的入参
     * @return el表达式解析的值
     */
    public static String getValue(String key, Method method, Object[] args){
        // 判断是否是spel格式
        if (StringUtils.containsAny(key, "#")) {
            // 获取方法上参数的名称
            String[] parameterNames = PND.getParameterNames(method);
            if (ArrayUtil.isEmpty(parameterNames)) {
                throw new RuntimeException("key解析异常!请联系管理员!");
            }
            for (int i = 0; i < parameterNames.length; i++) {
                CONTEXT.setVariable(parameterNames[i], args[i]);
            }
            // 解析返回给key
            try {
                Expression expression;
                if (StringUtils.startsWith(key, PARSER_CONTEXT.getExpressionPrefix())
                        && StringUtils.endsWith(key, PARSER_CONTEXT.getExpressionSuffix())) {
                    expression = PARSER.parseExpression(key, PARSER_CONTEXT);
                } else {
                    expression = PARSER.parseExpression(key);
                }
                key = expression.getValue(CONTEXT, String.class) + ":";
            } catch (Exception e) {
                throw new RuntimeException("key解析异常!请联系管理员!");
            }
        }
        return key;
    }
}
