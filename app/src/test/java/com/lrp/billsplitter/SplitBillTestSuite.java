package com.lrp.billsplitter;

import com.lrp.billsplitter.model.Bill;
import com.lrp.billsplitter.model.Item;
import com.lrp.billsplitter.model.Participant;
import com.lrp.billsplitter.model.SplitInstance;

import org.junit.Assert;
import org.junit.Test;

public class SplitBillTestSuite {

    public static double DEFAULT_DELTA_FOR_PRICE = 0.01;

    @Test
    public void manyPeopleOneItem() {
        SplitInstance instance = new SplitInstance();

        Participant participant1 = new Participant();
        participant1.setName("Wilfredo");

        Participant participant2 = new Participant();
        participant2.setName("Antonieta");

        Participant participant3 = new Participant();
        participant2.setName("Alfajor");

        instance.addParticipant(participant1);
        instance.addParticipant(participant2);
        instance.addParticipant(participant3);

        Bill bill = new Bill();
        bill.setTitle("Test bill");

        Item item = new Item();
        item.setTitle("Kiwi");
        item.setPrice(379.24);
        item.setParticipant(participant1);

        bill.addItem(item);

        instance.setBill(bill);

        instance.splitBill();

        Assert.assertEquals(0, participant1.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(252.82, participant1.getToReceive(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant1.getPaid(), DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(126.41, participant2.getToPay(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(126.41, participant3.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getPaid(),  DEFAULT_DELTA_FOR_PRICE);
    }

    @Test
    public void OnePersonOneItem() {
        SplitInstance instance = new SplitInstance();

        Participant participant1 = new Participant();
        participant1.setName("Wilfredo");

        instance.addParticipant(participant1);


        Item item = new Item();
        item.setTitle("Kiwi");
        item.setPrice(379.24);
        item.setParticipant(participant1);

        Bill bill = new Bill();
        bill.setTitle("Test bill");
        bill.addItem(item);

        instance.setBill(bill);

        instance.splitBill();

        Assert.assertEquals(0, participant1.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant1.getToReceive(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant1.getPaid(), DEFAULT_DELTA_FOR_PRICE);
    }


    @Test
    public void manyPeopleManyItems() {
        SplitInstance instance = new SplitInstance();

        Participant participant1 = new Participant();
        participant1.setName("Wilfredo");

        Participant participant2 = new Participant();
        participant2.setName("Antonieta");

        Participant participant3 = new Participant();
        participant2.setName("Alfajor");

        instance.addParticipant(participant1);
        instance.addParticipant(participant2);
        instance.addParticipant(participant3);


        Item item1 = new Item();
        item1.setTitle("Kiwi");
        item1.setPrice(379.24);
        item1.setParticipant(participant1);

        Item item2 = new Item();
        item2.setTitle("Mango");
        item2.setPrice(145.28);
        item2.setParticipant(participant2);

        Item item3 = new Item();
        item3.setTitle("Banana");
        item3.setPrice(233.98);
        item3.setParticipant(participant3);

        Bill bill = new Bill();
        bill.setTitle("Test bill");
        bill.addItem(item1);
        bill.addItem(item2);
        bill.addItem(item3);

        instance.setBill(bill);

        instance.splitBill();

        Assert.assertEquals(0, participant1.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(126.41, participant1.getToReceive(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant1.getPaid(), DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(107.55, participant2.getToPay(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(145.28, participant2.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(18.85, participant3.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(233.98, participant3.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(758.50, instance.getTotal(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(252.83, instance.getTotalPerCapita(),  DEFAULT_DELTA_FOR_PRICE);
    }

    @Test
    public void manyPeopleOneItemOfJustOnePerson() {
        SplitInstance instance = new SplitInstance();

        Participant participant1 = new Participant();
        participant1.setName("Wilfredo");

        Participant participant2 = new Participant();
        participant2.setName("Antonieta");

        Participant participant3 = new Participant();
        participant2.setName("Alfajor");

        instance.addParticipant(participant1);
        instance.addParticipant(participant2);
        instance.addParticipant(participant3);

        Item item1 = new Item();
        item1.setTitle("Kiwi");
        item1.setPrice(379.24);
        item1.setParticipant(participant1);

        Item item2 = new Item();
        item2.setTitle("Mango");
        item2.setPrice(145.28);
        item2.setParticipant(participant1);

        Item item3 = new Item();
        item3.setTitle("Banana");
        item3.setPrice(233.98);
        item3.setParticipant(participant1);

        Bill bill = new Bill();
        bill.setTitle("Test bill");
        bill.addItem(item1);
        bill.addItem(item2);
        bill.addItem(item3);

        instance.setBill(bill);

        instance.splitBill();

        Assert.assertEquals(0, participant1.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(505.66, participant1.getToReceive(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(758.50, participant1.getPaid(), DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(252.83, participant2.getToPay(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(252.83, participant3.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(758.50, instance.getTotal(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(252.83, instance.getTotalPerCapita(),  DEFAULT_DELTA_FOR_PRICE);
    }

    @Test
    public void manyPeopleWithSameItems() {
        SplitInstance instance = new SplitInstance();

        Participant participant1 = new Participant();
        participant1.setName("Wilfredo");

        Participant participant2 = new Participant();
        participant2.setName("Antonieta");

        Participant participant3 = new Participant();
        participant2.setName("Alfajor");

        instance.addParticipant(participant1);
        instance.addParticipant(participant2);
        instance.addParticipant(participant3);

        Item item1 = new Item();
        item1.setTitle("Kiwi");
        item1.setPrice(379.24);
        item1.setParticipant(participant1);

        Item item2 = new Item();
        item2.setTitle("Mango");
        item2.setPrice(379.24);
        item2.setParticipant(participant2);

        Item item3 = new Item();
        item3.setTitle("Banana");
        item3.setPrice(379.24);
        item3.setParticipant(participant3);

        Bill bill = new Bill();
        bill.setTitle("Test bill");
        bill.addItem(item1);
        bill.addItem(item2);
        bill.addItem(item3);

        instance.setBill(bill);

        instance.splitBill();

        Assert.assertEquals(0, participant1.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant1.getToReceive(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant1.getPaid(), DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(0, participant2.getToPay(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant2.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant2.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(0, participant3.getToPay(), DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(0, participant3.getToReceive(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, participant3.getPaid(),  DEFAULT_DELTA_FOR_PRICE);

        Assert.assertEquals(1137.72, instance.getTotal(),  DEFAULT_DELTA_FOR_PRICE);
        Assert.assertEquals(379.24, instance.getTotalPerCapita(),  DEFAULT_DELTA_FOR_PRICE);
    }

}
