package com.lrp.billsplitter.model;

import java.util.ArrayList;
import java.util.List;

public class SplitInstance implements Comparable<SplitInstance> {

    Bill bill;
    List<Participant> participantList = new ArrayList<>();
    Double total;
    Double totalPerCapita;
    private String title;

    public SplitInstance() {
        this.bill = new Bill();
        this.participantList = new ArrayList<>();
        totalPerCapita = 0.0;
        total = 0.0;
        title = "";
    }

    public void addParticipant(Participant participant) {
        participantList.add(participant);
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalPerCapita() {
        return totalPerCapita;
    }

    public void setTotalPerCapita(Double totalPerCapita) {
        this.totalPerCapita = totalPerCapita;
    }

    public void splitBill() {
        List<Item> items = bill.getItemList();
        total = 0.0;
        totalPerCapita = 0.0;

        for(Participant participant : participantList) {
            participant.setPaid(0.0);
        }

        for(Item item : items) {
            item.getParticipant().sumPaid(item.getPrice());
            total += item.getPrice();
        }

        totalPerCapita = total / participantList.size();

        for(Participant participant : participantList) {
            Double totalPaid = participant.getPaid();
            if(totalPaid > totalPerCapita) {
                participant.setToReceive(totalPaid - totalPerCapita);
                participant.setToPay(0.0);
            } else if(totalPaid < totalPerCapita) {
                participant.setToPay(totalPerCapita - totalPaid);
                participant.setToReceive(0.0);
            }
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(SplitInstance o) {
        return this.getTitle().compareTo(o.getTitle());
    }

    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }
}
