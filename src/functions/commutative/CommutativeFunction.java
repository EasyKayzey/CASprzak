package functions.commutative;
import core.ArrLib;
import functions.Function;
import functions.special.Constant;

import java.util.Arrays;

public abstract class CommutativeFunction extends Function {

    /**
     * The identity of the {@link CommutativeFunction} (e.g. 1 for * and 0 for +)
     */
    protected double identityValue;

    /**
     * Array of {@link Function}s operated on by the {@link CommutativeFunction}
     */
    protected final Function[] functions;

    public CommutativeFunction(Function... functions) {
        this.functions = functions;
        Arrays.sort(this.functions);
    }


    public Function simplify() {
        return this.simplifyInternal().simplifyOneElement();
    }

    public CommutativeFunction simplifyInternal() {
        CommutativeFunction current = this;
        current = current.simplifyElements();
        current = current.simplifyPull();
        current = current.simplifyIdentity();
        current = current.simplifyConstants();
        return current;
    }

    public abstract CommutativeFunction simplifyElements();

    public CommutativeFunction simplifyIdentity() {
        Function[] toPut = getFunctions();
        for (int i = 0; i < toPut.length; i++) {
            if (toPut[i] instanceof Constant) {
                if (((Constant) toPut[i]).constant == identityValue) {
                    toPut = ArrLib.removeFunctionAt(toPut, i);
                    i--;
                }
            }
        }
        return me(toPut);
    }

    public abstract CommutativeFunction simplifyConstants();

    public CommutativeFunction simplifyPull() {
        for (int i = 0; i < functions.length; i++) {
            if (this.getClass().equals(functions[i].getClass())) {
                return (me(ArrLib.pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i))).simplifyInternal();
            }
        }
        return this;
    }

    public Function simplifyOneElement() {
        if (functions.length == 0)
            return new Constant(identityValue);
        if (functions.length == 1)
            return functions[0].simplify();
        return this;
    }


    public Function[] getFunctions() {
        return ArrLib.deepClone(functions);
    }

    public int getFunctionsLength() {
        return functions.length;
    }


    public abstract CommutativeFunction me(Function... functions);

    public Function substitute(int varID, Function toReplace) {
        Function[] newFunctions = new Function[functions.length];
        for (int i = 0; i < functions.length; i ++)
            newFunctions[i] = functions[i].substitute(varID, toReplace);
        return me(newFunctions);
    }


    public boolean equals(Function that) {
        if (this.getClass().equals(that.getClass()))
            return ArrLib.deepEquals(functions, ((CommutativeFunction)that).getFunctions());
        return false;
    }

    public int compareSelf(Function that) {
        if (functions.length != ((CommutativeFunction) that).getFunctionsLength())
            return functions.length - ((CommutativeFunction) that).getFunctionsLength();
        Function[] thisFunctions = functions;
        Function[] thatFunctions = ((CommutativeFunction)that).getFunctions();
        for (int i = 0; i < thisFunctions.length; i++) {
            if (!thisFunctions[i].equals(thatFunctions[i]))
                return thisFunctions[i].compareTo(thatFunctions[i]);
        }
        System.out.println("This isn't supposed to happen. Check CompareSelf of CommutativeFunction and Function.compareTo");
        return 0;
    }
}
