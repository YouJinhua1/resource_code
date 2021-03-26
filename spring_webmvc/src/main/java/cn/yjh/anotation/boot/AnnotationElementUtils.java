package cn.yjh.anotation.boot;



import cn.yjh.anotation.AliasFor;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;


/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-03-06 13:16
 */
public class AnnotationElementUtils<A> {

    public static void main(String[] args) {
      /*  Class<UserService> classz = UserService.class;
        long l = System.currentTimeMillis();
        Service annotation = AnnotationElementUtils.getMergeAnnotation(classz, Service.class);
        System.out.println("查找耗时：" + (System.currentTimeMillis() - l) + "ms,查找结果为：" + annotation);*/
    }


    /**
     * 获取指定元素的注解类型（若没有直接注解，则从元素其他注解的元注解上查找）
     *
     * @param element 可以包含注解的元素，如Field, Method等
     * @param annotationType   注解类型
     * @param <A>
     * @return
     */
    public static <A extends Annotation> A getMergeAnnotation(AnnotatedElement element, Class<A> annotationType) {
        A target = null;
        // 元注解访问链
        List<Annotation> visited = new ArrayList<>();
        // 遍历查找该元素的其他注解上的元注解
        for (Annotation anno : element.getAnnotations()) {
            visited.add(anno);
            if (anno.annotationType() == annotationType) {
                target = (A) anno;
                Map<String, Object> attributes = AliasForProcesscor.doProcessor(element, target, visited);
                setAnnotationAttribute(target, attributes);
                return target;
            }
            // 如果从该元注解没有找到指定注解，则清空该访问链，继续该元素其他注解上查找
            if ((target = findMetaAnnotation(anno, annotationType, visited)) == null) {
                visited.clear();
                continue;
            }
            break;
        }
        // 如果注解不存在，直接return
        if (target == null) {
            return null;
        }
        Class<? extends Annotation> targetType = target.annotationType();
        Map<String, Object> attributes = new LinkedHashMap<>(8);
        for (Annotation a : visited) {
            List<AliasForDescriptor> aliasForDescriptors = getAliasForDescriptors(a);
            if (aliasForDescriptors.isEmpty()) {
                continue;
            }
            // 遍历该注解上的所有别名描述器，并判断目标注解的属性是否有被其他注解覆盖
            for (AliasForDescriptor descriptor : aliasForDescriptors) {
                if (descriptor.aliasAnnotationType == targetType) {
                    String targetAttributeName = descriptor.aliasAttributeName;
                    if (!attributes.containsKey(targetAttributeName)) {
                        attributes.put(targetAttributeName, invokeMethod(descriptor.sourceAttribute,
                                AnnotationElementUtils.getMergeAnnotation(element, descriptor.sourceAnnotationType)));
                    }
                }
            }
        }
        setAnnotationAttribute(target, attributes);
        attributes = AliasForProcesscor.doProcessor(element, target, visited);
        setAnnotationAttribute(target, attributes);
        return target;
    }

    /**
     * 判断注解是否存在于AnnotatedElement上
     * @param element
     * @param type
     * @return
     */
    private static boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> type) {
        boolean result = false;
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getName();
            if (!annotationName.startsWith("java") && !annotationName.startsWith("javax")) {
                if (annotation.annotationType() == type) {
                    result = true;
                } else {
                    result = isAnnotationPresent(annotation.annotationType(), type);
                }
            }
            if (result) break;
        }
        return result;
    }

    /**
     * 根据元注解类型递归查找指定注解的元注解
     *
     * @param annotation 注解
     * @param targetType 元注解类型
     * @param visited    访问链
     * @param <A>
     * @return
     */
    private static <A extends Annotation> A findMetaAnnotation(Annotation annotation, Class<A> targetType, List<Annotation> visited) {
        A a = annotation.annotationType().getAnnotation(targetType);
        if (a == null) {
            for (Annotation metaAnn : annotation.annotationType().getAnnotations()) {
                Class<? extends Annotation> metaAnnType = metaAnn.annotationType();
                // 如果是java自带的元注解，则跳过继续
                if (metaAnnType.getName().startsWith("java")) {
                    continue;
                }
                // 如果注解存在指定元注解，则跳出循环，否则，递归查找
                if ((a = metaAnnType.getAnnotation(targetType)) != null) {
                    visited.add(metaAnn);
                    break;
                }
                // 递归查找
                if ((a = findMetaAnnotation(metaAnn, targetType, visited)) != null) {
                    break;
                }
                // 没有找到则移除访问链中最后一个无用的访问
                visited.remove(visited.size() - 1);
            }
        }

        return a;
    }

    /**
     * 获取指定注解上的所有属性方法含有的别名属性描述
     *
     * @param a
     * @return
     */
    private static List<AliasForDescriptor> getAliasForDescriptors(Annotation a) {
        List<AliasForDescriptor> descriptors = null;
        for (Method attribute : getAttributeMethods(a.annotationType())) {
            AliasForDescriptor des = AliasForDescriptor.from(attribute);
            if (des != null) {
                if (descriptors == null) {
                    descriptors = new ArrayList<>(8);
                }
                descriptors.add(des);
            }
        }
        return Optional.ofNullable(descriptors).orElse(Collections.emptyList());
    }

    /**
     * 获取注解中的所有属性方法
     *
     * @param annotationType 注解类型
     * @return 属性方法列表
     */
    private static List<Method> getAttributeMethods(Class<? extends Annotation> annotationType) {
        List<Method> methods = new ArrayList<>();
        for (Method method : annotationType.getDeclaredMethods()) {
            methods.add(method);
        }
        return methods;
    }

    /**
     * 判断注解的属性值是否相等
     * @param o1
     * @param o2
     * @return
     */
    private static boolean isEquals(Object o1, Object o2) {
        boolean result = false;
        // 先判断两个对象是否同种类型
        if(o1.getClass().isInstance(o2)){
            // 再判断是否是数组类型的，然后决定采用哪种方式比较
            if (o1.getClass().isArray()) {
                Object[] arr1 = (Object[]) o1;
                Object[] arr2 = (Object[]) o2;
                result = Arrays.equals(arr1, arr2);
            } else {
                result = o1.equals(o2);
            }
        }
        return result;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 给某个注解的某个属性赋值
     *
     * @param an
     * @param map
     * @throws Exception
     */
    private static void setAnnotationAttribute(Annotation an, Map<String, Object> map) {
        InvocationHandler handler = Proxy.getInvocationHandler(an);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        try {
            Field handlerField = handler.getClass().getDeclaredField("memberValues");
            // 因为这个字段是 private final 修饰，所以要打开权限
            handlerField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) handlerField.get(handler);
            Set<String> keys = map.keySet();
            for (String key : keys) {
                // 修改对应属性的值
                memberValues.put(key, map.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用无参数方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @return 执行结果
     */
    private static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * 调用指定对象的方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @param args   方法参数
     * @return 执行结果
     */
    private static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            makeAccessible(method);
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("执行%s.%s()方法错误!"
                    , target.getClass().getName(), method.getName()), ex);
        }
    }

    /**
     * 设置方法可见性
     *
     * @param method 方法
     */
    private static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 别名描述器，用于描述{@link AliasFor}细节
     */
    private static class AliasForDescriptor {
        /**
         * 源注解属性方法
         */
        private final Method sourceAttribute;

        /**
         * 源注解类型
         */
        private final Class<? extends Annotation> sourceAnnotationType;

        /**
         * 源注解属性名（即方法名）
         */
        private final String sourceAttributeName;

        /**
         * 别名的注解属性方法
         */
        private final Method aliasAttribute;

        /**
         * 别名的注解类型
         */
        private final Class<? extends Annotation> aliasAnnotationType;

        /**
         * 别名的属性名
         */
        private final String aliasAttributeName;


        public static AliasForDescriptor from(Method attribute) {
            AliasFor aliasFor = attribute.getAnnotation(AliasFor.class);
            if (aliasFor == null) {
                return null;
            }

            return new AliasForDescriptor(attribute, aliasFor);
        }

        private AliasForDescriptor(Method sourceAttribute, AliasFor aliasFor) {
            Class<? extends Annotation> sourceAnnotationType = (Class<? extends Annotation>) sourceAttribute.getDeclaringClass();
            Class<? extends Annotation> aliasAnnotationType = aliasFor.annotation();
            this.sourceAttribute = sourceAttribute;
            this.sourceAnnotationType = sourceAnnotationType;
            this.sourceAttributeName = sourceAttribute.getName();
            this.aliasAttributeName = getAliasForAttributeName(aliasFor, sourceAttribute);
            if (aliasAnnotationType == Annotation.class) {
                this.aliasAnnotationType = sourceAnnotationType;
            } else {
                this.aliasAnnotationType = aliasAnnotationType;
            }
            try {
                this.aliasAttribute = this.aliasAnnotationType.getDeclaredMethod(this.aliasAttributeName);
            } catch (NoSuchMethodException ex) {
                String msg = String.format(
                        "@%s注解中%s属性方法上@AliasFor注解对应的%s别名属性不存在于%s别名注解类中！",
                        this.sourceAnnotationType.getName(), this.sourceAttributeName, this.aliasAttributeName,
                        this.aliasAnnotationType.getName());
                throw new RuntimeException(msg, ex);
            }
        }

        /**
         * 获取属性方法上的被覆盖的属性名
         *
         * @param aliasFor        别名注解
         * @param sourceAttribute 注解属性方法
         * @return 如果 {@link AliasFor} 注解的attribute和value属性值都为空，则反回sourceAttribute的方法名
         */
        private String getAliasForAttributeName(AliasFor aliasFor, Method sourceAttribute) {
            String attribute = aliasFor.attribute();
            String value = aliasFor.value();
            boolean hasAttribute = !isEmpty(attribute);
            boolean hasValue = !isEmpty(value);
            if (hasAttribute && hasValue) {
                String msg = String.format("%s注解中的%s属性方法上的@AliasFor注解的value属性和attribute不能同时存在！",
                        sourceAttribute.getDeclaringClass().getName(), sourceAttribute.getName());
                throw new RuntimeException(msg);
            }

            attribute = hasAttribute ? attribute : value;
            return !isEmpty(attribute) ? attribute : sourceAttribute.getName();
        }
    }

    private static class AliasForProcesscor {

        public static Map<String, Object> doProcessor(AnnotatedElement element, Annotation anno, List<Annotation> visited) {
            Class<? extends Annotation> targetType = anno.annotationType();
            // 注解属性键值集合
            Map<String, Object> attributes = new LinkedHashMap<>(8);
            // 获取该注解每个属性的别名
            Map<String, List<String>> aliasForMap = new LinkedHashMap<>();
            for (Method targetAttribute : getAttributeMethods(targetType)) {
                AliasForDescriptor des = AliasForDescriptor.from(targetAttribute);
                if (des != null) {
                    String key = des.aliasAnnotationType.getSimpleName() + "->" + des.aliasAttributeName;
                    List<String> attributeList = aliasForMap.get(key);
                    if (attributeList != null) {
                        attributeList.add(des.sourceAttributeName);
                    } else {
                        attributeList = new ArrayList<>();
                        attributeList.add(des.sourceAttributeName);
                        aliasForMap.put(key, attributeList);
                    }
                }
                String attributeName = targetAttribute.getName();
                if (!attributes.containsKey(attributeName)) {
                    attributes.put(attributeName, invokeMethod(targetAttribute, anno));
                }
            }
            if (!aliasForMap.isEmpty()) {
                // 获取直接作用在类上的注解，(即：注解访问链上的第一个元素)
                Class<? extends Annotation> clazz = visited.get(0).annotationType();
                validateAliasFor(element, clazz, aliasForMap, attributes);
            }
            return attributes;
        }

        /**
         * 校验互为别名的属性的值是否相等，若不相等抛出异常
         *
         * @param element
         * @param clazz
         * @param aliasForMap
         * @param attributes
         */
        public static void validateAliasFor(AnnotatedElement element, Class<? extends Annotation> clazz, Map<String, List<String>> aliasForMap, Map<String, Object> attributes) {
            Set<String> aliasKeys = aliasForMap.keySet();
            for (String aliasKey : aliasKeys) {
                List<String> attributeList = aliasForMap.get(aliasKey);
                // 判断当前别名是属于本注解中属性的别名，还是其元注解中属性的别名
                if (aliasKey.startsWith(clazz.getSimpleName() + "->")) {
                    if (attributeList.size() == 1) {
                        String attributeName = attributeList.get(0);
                        String aliasAttributeName = aliasKey.substring(aliasKey.indexOf("->") + 2, aliasKey.length());
                        Object defaultValue = null;
                        try {
                            // 获取注解属性的默认值
                            defaultValue = clazz.getDeclaredMethod(aliasAttributeName).getDefaultValue();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        // 获取解析注解时，设置的值
                        Object o1 = attributes.get(attributeName);
                        Object o2 = attributes.get(aliasAttributeName);
                        // 判断设置的值是否与默认值相等
                        if (isEquals(defaultValue, o2)) {
                            attributes.put(attributeName, o1);
                        } else {
                            throwsException(o1, o2, attributeName, aliasAttributeName, clazz.getSimpleName(), ((Class) element).getName());
                        }
                    }
                } else {
                    // 判断别名所对应的属性是否只有一个
                    if (attributeList.size() == 1) {
                        continue;
                    }
                }
                String attributeName = attributeList.get(0);
                Object o1 = attributes.get(attributeName);
                Object o2 = null;
                for (int i = 1; i < attributeList.size(); i++) {
                    o2 = attributes.get(attributeList.get(i));
                    if (!isEquals(o1, o2)) {
                        throwsException(o1, o2, attributeName, attributeList.get(i), clazz.getSimpleName(), ((Class) element).getName());
                    }
                }
            }
        }

        /**
         * 抛出别名属性值不同的异常
         *
         * @param o1
         * @param o2
         * @param attributeName
         * @param aliasAttributeName
         * @param className
         * @param annotationName
         */
        public static void throwsException(Object o1, Object o2, String attributeName, String aliasAttributeName, String className, String annotationName) {
            String v1 = o1.toString();
            String v2 = o2.toString();
            if (o1.getClass().isArray()) {
                v1 = Arrays.toString((Object[]) o1);
                v2 = Arrays.toString((Object[]) o2);
            }
            String msg = String.format(
                    "Different @AliasFor values for annotation 【%s】 declared on class 【%s】; attribute 【'%s'】 and its alias 【'%s'】 are declared with values of 【%s】 and 【%s】.",
                    className,
                    annotationName,
                    attributeName,
                    aliasAttributeName,
                    v1,
                    v2);
            throw new RuntimeException(msg);
        }
    }
}
