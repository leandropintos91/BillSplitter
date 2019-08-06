package com.lrp.billsplitter.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitInstance {

    Bill bill;
    List<Participant> participantList = new ArrayList<>();
    Double total;
    Double totalPerCapita;

    public void addParticipant(Participant participant) {
        participantList.add(participant);
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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
}
