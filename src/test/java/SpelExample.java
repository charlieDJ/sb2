import com.dj.util.ExpressionUtil;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

public class SpelExample {
    public static void main(String[] args) throws NoSuchMethodException {
//        simpleExample();
        exampleMethod("John", 30);
    }

    private static void simpleExample() {
        // 创建一个表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 创建上下文，存储变量
        StandardEvaluationContext context = new StandardEvaluationContext();

        // 设置一个变量
        context.setVariable("name", "Spring");
        context.setVariable("age", 25);

        // 解析并计算表达式
        String expression = "#name + ' is ' + #age + ' years old.'";
        String result = parser.parseExpression(expression).getValue(context, String.class);
        System.out.println(result);  // 输出: Spring is 25 years old.
    }

    public static void exampleMethod(String userName, int age) throws NoSuchMethodException {
        String expression = "#userName + ' is ' + #age + ' years old.'";
        // 获取当前类的静态方法 'exampleMethod'
        Method method = SpelExample.class.getMethod("exampleMethod", String.class, int.class);
        String result = ExpressionUtil.getValue(expression, method, new Object[] { userName, age });
        System.out.println(result);
    }
}
