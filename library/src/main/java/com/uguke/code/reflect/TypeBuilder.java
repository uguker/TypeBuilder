package com.uguke.code.reflect;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：泛型类构造器
 * @author LeiJue
 * @time 2017/08/05
 */
public class TypeBuilder {

    /** 要生成的类 **/
    private Class raw;
    /** 父级泛型类 **/
    private TypeBuilder parent;
    /** 泛型列表 **/
    private List<Type> args = new ArrayList<>();

    private TypeBuilder(Class raw, TypeBuilder parent) {
        assert raw != null;
        this.raw = raw;
        this.parent = parent;
    }

    /**
     * 功能描述：实例化一个Type构造器
     * @param raw     要生成的泛型类
     * @return
     */
    public static TypeBuilder newInstance(Class raw) {
        return new TypeBuilder(raw, null);
    }

    /**
     * 功能描述：实例化一个Type构造器
     * @param raw     要生成的泛型类
     * @param parent  父级构造器
     * @return
     */
    private static TypeBuilder newInstance(Class raw, TypeBuilder parent) {
        return new TypeBuilder(raw, parent);
    }

    /**
     * 功能描述：添加一个参数化泛型
     * @param clazz   指定类类型
     * @return 
     */
    public TypeBuilder addTypeParam(Class clazz) {
        return addTypeParam((Type) clazz);
    }

    /**
     * 功能描述：添加一个参数化类型
     * @param type    指定Type
     * @return
     */
    public TypeBuilder addTypeParam(Type type) {
        if (type == null) {
            throw new NullPointerException("addTypeParam expect not null Type");
        }
        args.add(type);
        return this;
    }

    /**
     * 功能描述：添加下限泛型通配符
     * @param classes 下限通配符
     * @return 
     */
    public TypeBuilder addTypeParamSuper(Class... classes) {
        if (classes == null) {
            throw new NullPointerException("addTypeParamSuper() expect not null Class");
        }

        WildTypeImpl wildcardType = new WildTypeImpl(classes, null);

        return addTypeParam(wildcardType);
    }
    /**
     * 功能描述：添加上限泛型通配符
     * @param classes 上限通配符
     * @return
     */
    public TypeBuilder addTypeParamExtends(Class... classes) {

        if (classes == null) {
            throw new NullPointerException("addTypeParamExtends() expect not null Class");
        }

        WildTypeImpl wildcardType = new WildTypeImpl(null, classes);

        return addTypeParam(wildcardType);
    }

    /**
     * 功能描述：结束一个子级泛型类
     * @param raw     要生成的泛型类
     * @return
     */
    public TypeBuilder beginSubType(Class raw) {
        return newInstance(raw, this);
    }

    /**
     * 功能描述：开始一个子级泛型类
     * @return
     */
    public TypeBuilder endSubType() {
        if (parent == null) {
            throw new TypeException("expect beginSubType() before endSubType()");
        }

        if (args.isEmpty()) {
            parent.addTypeParam(raw);
        }
        parent.addTypeParam(new ParamTypeImpl(raw, args.toArray(new Type[args.size()]), null));

        return parent;
    }

    public Type build() {

        if (parent != null) {
            throw new TypeException("expect endSubType() before build()");
        }

        if (args.isEmpty()) {
            return raw;
        }
        return new ParamTypeImpl(raw, args.toArray(new Type[args.size()]), null);
    }




}
