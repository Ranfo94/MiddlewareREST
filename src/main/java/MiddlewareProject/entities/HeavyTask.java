package MiddlewareProject.entities;

import java.math.BigInteger;

public class HeavyTask extends Task {

    private int n;
    private BigInteger partial;
    private BigInteger response;
    private int last = 0;

    public BigInteger getResponse() {
        return response;
    }

    public void setResponse(BigInteger response) {
        this.response = response;
    }

    public int getN(){ return n; }

    public void setN(int n){
        this.n = n;
    }

    public BigInteger getPartial() {
        return partial;
    }

    public void setPartial(BigInteger partial) {
        this.partial = partial;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}
