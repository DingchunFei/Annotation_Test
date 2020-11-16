package com.fei.bean;

import com.fei.annotation.EnumAnno;
import com.fei.annotation.UnitTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UnitTest(id=1111,msg = "class annotation")
public class TestObject {
    @UnitTest(id=222)
    int num;

    @UnitTest(msg="fiii")
    String str;

    @UnitTest(id=333 , msg = "This is print 1!!")
    private void print(Integer a, String b){
        System.out.println("The value of num is : " + a + " str is: " + b);
    }

    private void paramAnno(@UnitTest(id=444, msg = "This is parameter annotation!")String param1){
        System.out.println("The param1 is: " + param1);
    }

    //https://blog.csdn.net/My_TrueLove/article/details/70519234
    private void paramAnno2(@EnumAnno Integer param1){
        switch (param1){
            case EnumAnno.UN_KNOW:
                System.out.println("EnumAnno.UN_KNOW");
                break;
            case EnumAnno.UN_START:
                System.out.println("EnumAnno.UN_START");
                break;
            case EnumAnno.PROGRESSING:
                System.out.println("EnumAnno.PROGRESSING");
                break;
            case EnumAnno.COMPLETED:
                System.out.println("EnumAnno.COMPLETED");
                break;
        }
    }


    public static void testFieldAnnotation(Object testObject){
        Field[] fields = getAllFields(testObject);
        for (Field field : fields) {
            field.setAccessible(true);
            //获取Field上的注解
            if(field.isAnnotationPresent(UnitTest.class)){
                UnitTest unitTestField = field.getAnnotation(UnitTest.class);
                if ("int".equals(field.getGenericType().toString())) {
                    System.out.println("id is:" + unitTestField.id());
                } else if ("class java.lang.String".equals(field.getGenericType().toString())) {
                    System.out.println("msg is:" + unitTestField.msg());
                }
            }
        }
    }

    public static void testMethodAnnotation(Object testObject){
        try{
            Method[] methods = getAllMethods(testObject);
            for(Method method : methods){
                method.setAccessible(true);
                if(method.isAnnotationPresent(UnitTest.class)){
                    UnitTest unitTestMethod = method.getAnnotation(UnitTest.class);
                    if("print".equals(method.getName())){
                        method.invoke(testObject, unitTestMethod.id(),unitTestMethod.msg());
                    }
                }
            }
        }catch (Exception e){}
    }

    public static void testClassAnnotation(Object testObject){
        Class clazz = testObject.getClass();
        boolean hasAnnotation = clazz.isAnnotationPresent(UnitTest.class);
        if ( hasAnnotation ) {
            UnitTest classAnnotation = (UnitTest) clazz.getAnnotation(UnitTest.class);
            //获取类的注解
            System.out.println("id:"+classAnnotation.id());
            System.out.println("msg:"+classAnnotation.msg());
        }
    }

    public static void testParamAnnotation2(TestObject testObject){
        testObject.paramAnno2(EnumAnno.PROGRESSING);
    }

    public static void testParamAnnotation(Object testObject){
        try {
            Class clazz = testObject.getClass();
            // 获取 "方法参数" 上的注解的值
            Method method = clazz.getDeclaredMethod("paramAnno", String.class);
            String[] parameterNames = getMethodParameterNamesByAnnotation(method);
            System.out.println("\"方法参数\"上的注解值获取到"+Arrays.toString(parameterNames));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取给 "方法参数" 进行注解的值
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    public static String[] getMethodParameterNamesByAnnotation(Method method) {
        //因为每个方法可以有多个入参，所以是一个数组，而每个入参可以有多个注解，因此变成二维数组
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        String[] parameterNames = new String[parameterAnnotations.length];
        int i = 0;
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof UnitTest) {
                    UnitTest param = (UnitTest) annotation;
                    parameterNames[i++] = param.msg();
                }
            }
        }
        return parameterNames;
    }

    public static void main(String[] args) {
        TestObject testObject = new TestObject();
        testClassAnnotation(testObject);
        testFieldAnnotation(testObject);
        testMethodAnnotation(testObject);
        testParamAnnotation(testObject);
        testParamAnnotation2(testObject);
    }

    //将传入对象所有field获取出来
    public static Field [] getAllFields(Object obj){
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = obj.getClass();
        while(tempClass!=null){ //当父类为null的时候说明已经到了Object类
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    //将传入对象的多有Method获取出来
    public static Method [] getAllMethods(Object obj){
        List<Method> methodList = new ArrayList<>();
        Class tempClass = obj.getClass();
        while(tempClass!=null){ //当父类为null的时候说明已经到了Object类
            methodList.addAll(Arrays.asList(tempClass.getDeclaredMethods()));
            tempClass = tempClass.getSuperclass();
        }
        Method[] methods = new Method[methodList.size()];
        methodList.toArray(methods);
        return methods;
    }

}
