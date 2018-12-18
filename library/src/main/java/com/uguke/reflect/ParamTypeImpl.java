package com.uguke.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;

/**
 * 功能描述：参数化的类型
 * @author LeiJue
 * @date 2018/12/18
 */
public class ParamTypeImpl implements ParameterizedType {

    private Type [] mArgs;
    private Type mOwner;
    private Class mRaw;

    public ParamTypeImpl(Class raw, Type[] args, Type owner) {

        this.mRaw = raw;
        this.mArgs = args != null ? args : new Type[0];
        this.mOwner = owner;

        checkArgs();
    }

    private void checkArgs() {

        if (mRaw == null) {
            throw new TypeException("raw class can't be null");
        }

        TypeVariable[] typeParameters = mRaw.getTypeParameters();
        if (mArgs.length != 0 && typeParameters.length != mArgs.length) {
            throw new TypeException(mRaw.getName() +
                    " expect " + typeParameters.length +
                    " arg(s), got " + mArgs.length);
        }
    }

    @Override
    public Type[] getActualTypeArguments() {
        return mArgs;
    }

    @Override
    public Type getRawType() {
        return mRaw;
    }

    @Override
    public Type getOwnerType() {
        return mOwner;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mRaw.getName());
        if (mArgs.length != 0) {
            sb.append('<');
            for (int i = 0; i < mArgs.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                Type type = mArgs[i];
                if (type instanceof Class) {
                    Class clazz = (Class) type;

                    if (clazz.isArray()) {
                        int count = 0;
                        do {
                            count++;
                            clazz = clazz.getComponentType();
                        } while (clazz.isArray());

                        sb.append(clazz.getName());

                        for (int j = count; j > 0; j--) {
                            sb.append("[]");
                        }
                    } else {
                        sb.append(clazz.getName());
                    }
                } else {
                    sb.append(mArgs[i].toString());
                }
            }
            sb.append('>');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamTypeImpl that = (ParamTypeImpl) o;

        return mRaw.equals(that.mRaw) && Arrays.equals(mArgs, that.mArgs) &&
                (mOwner != null ? mOwner.equals(that.mOwner) : that.mOwner == null);

    }

    @Override
    public int hashCode() {
        int result = mRaw.hashCode();
        result = 31 * result + Arrays.hashCode(mArgs);
        result = 31 * result + (mOwner != null ? mOwner.hashCode() : 0);
        return result;
    }

}
