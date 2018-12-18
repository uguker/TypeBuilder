package com.uguke.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * 功能描述：泛型通配符
 * @author LeiJue
 * @date 2018/12/18
 */
public class WildTypeImpl implements WildcardType {

    private Class[] mUpper;
    private Class[] mLower;

    public WildTypeImpl(Class[] lower, Class[] upper) {

        this.mLower = lower != null ? lower : new Class[0];
        this.mUpper = upper != null ? upper : new Class[0];

        checkArgs();
    }

    private void checkArgs() {
        if (mLower.length == 0 && mUpper.length == 0) {
            throw new IllegalArgumentException("lower or upper can't be null");
        }

        checkArgs(mLower);
        checkArgs(mUpper);
    }

    private void checkArgs(Class[] args) {
        for (int i = 1; i < args.length; i++) {
            Class clazz = args[i];
            if (!clazz.isInterface()) {
                throw new IllegalArgumentException(clazz.getName() + " not a interface!");
            }
        }
    }

    private String getTypeString(String prefix, Class[] type) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        for (int i = 0; i < type.length; i++) {
            if (i != 0) {
                sb.append(" & ");
            }
            sb.append(type[i].getName());
        }

        return sb.toString();

    }

    @Override
    public Type[] getUpperBounds() {
        return mUpper;
    }

    @Override
    public Type[] getLowerBounds() {
        return mLower;
    }

    @Override
    public String toString() {
        if (mUpper.length > 0) {
            if (mUpper[0] == Object.class) {
                return "?";
            }
            return getTypeString("? extends ", mUpper);
        } else {
            return getTypeString("? super ", mLower);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WildTypeImpl that = (WildTypeImpl) o;

        return Arrays.equals(mUpper, that.mUpper) &&
                Arrays.equals(mLower, that.mLower);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(mUpper);
        result = 31 * result + Arrays.hashCode(mLower);
        return result;
    }

}
