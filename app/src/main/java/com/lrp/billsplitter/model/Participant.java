package com.lrp.billsplitter.model;

public class Participant {

    String name;

    Double toReceive = 0.0;

    Double toPay = 0.0;

    Double paid = 0.0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getToReceive() {
        return toReceive;
    }

    public void setToReceive(Double toReceive) {
        this.toReceive = toReceive;
    }

    public Double getToPay() {
        return toPay;
    }

    public void setToPay(Double toPay) {
        this.toPay = toPay;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Participant other) {
        return this.name.equals(other.getName());
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public void sumPaid(Double price) {
        paid += price;
    }
}
