package com.lrp.billsplitter;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lrp.billsplitter.model.Item;
import com.lrp.billsplitter.model.Participant;
import com.lrp.billsplitter.model.SplitInstance;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BillSplitterApp extends Application {

    private static BillSplitterApp billSplitterApp;

    private List<SplitInstance> splitInstanceList;

    private int currentSplitInstance;

    private int currentParticipantSelected;

    private int currentItemSelected;

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
        loadModel();
    }

    public BillSplitterApp() {
        splitInstanceList = new ArrayList<>();
        billSplitterApp = this;
    }

    public static BillSplitterApp getInstance() {
        if(billSplitterApp == null) {
            billSplitterApp = new BillSplitterApp();
        }
        return billSplitterApp;
    }

    public List<SplitInstance> getSplitInstanceList() {
        return this.splitInstanceList;
    }

    public void setSplitInstanceList(List<SplitInstance> splitInstanceList) {
        this.splitInstanceList = splitInstanceList;
    }

    public SplitInstance getCurrentSplitInstance() {
        //TODO que sea consistentes los nombres
        if(currentSplitInstance < 0) {
            return null;
        }
        return splitInstanceList.get(currentSplitInstance);
    }

    public void setCurrentSplitInstance(int currentSplitInstance) {
        this.currentSplitInstance = currentSplitInstance;

    }

    public SplitInstance newSplitInstance(String name) {
        SplitInstance newSplitInstance = new SplitInstance();
        newSplitInstance.setTitle(name);
        splitInstanceList.add(newSplitInstance);
        setCurrentSplitInstance(splitInstanceList.size() - 1); //TODO otra chanchada
        saveModel();
        return newSplitInstance;
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences("model", MODE_PRIVATE);
    }

    private void saveModel() {
        SharedPreferences sharedPreferences = getSharedPreferences("model", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(splitInstanceList);
        prefsEditor.putString("splitInstanceList", json);
        prefsEditor.apply();
    }

    private void loadModel() {
        SharedPreferences sharedPreferences = getSharedPreferences("model", MODE_PRIVATE);
        String json = sharedPreferences.getString("splitInstanceList", "");
        Gson gson = new Gson();
        SplitInstance[] splitInstanceArray = gson.fromJson(json, SplitInstance[].class);

        if(splitInstanceArray == null || splitInstanceArray.length <= 0) {
            splitInstanceList = new ArrayList<>();
        } else {
            splitInstanceList = new ArrayList<>(Arrays.asList(splitInstanceArray));
        }

        synchronizeItemsAndParticipants();
    }

    private void synchronizeItemsAndParticipants() {
        for(SplitInstance splitInstance : splitInstanceList) {
            for(Item item : splitInstance.getBill().getItemList()) {
                Participant participant = CollectionUtils.find(splitInstance.getParticipantList(), object -> item.getParticipant().equals(object));
                item.setParticipant(participant);
            }
        }
    }

    public void newParticipant(String name) {
        Participant newParticipant = new Participant();
        newParticipant.setName(name);
        SplitInstance currentInstance = getCurrentSplitInstance();
        currentInstance.getParticipantList().add(newParticipant);
        setCurrentParticipantSelected(currentInstance.getParticipantList().size() -1);
        saveModel();
    }

    public void deleteSplitInstance(int position) {
        splitInstanceList.remove(position);
        if(position > 0) {
            setCurrentSplitInstance(position - 1);
        }
        if(splitInstanceList.isEmpty()) {
            setCurrentSplitInstance(-1);
        }
        saveModel();
    }

    public Participant getCurrentParticipantSelected() {
        //TODO que sea consistentes los nombres
        if(getCurrentSplitInstance() == null) {
            return null;
        }
        return getCurrentSplitInstance().getParticipantList().get(currentParticipantSelected);
    }

    public int getCurrentParticipantSelectedIndex() {
        //TODO que sea consistentes los nombres
        return currentParticipantSelected;
    }

    public void setCurrentParticipantSelected(int currentParticipantSelected) {
        this.currentParticipantSelected = currentParticipantSelected;

    }

    public void addNewItem(String description, Double price) {
        Item item = new Item();
        item.setTitle(description);
        item.setPrice(price);
        item.setParticipant(getCurrentParticipantSelected());
        getCurrentSplitInstance().getBill().getItemList().add(item);
        setCurrentItemSelected(getCurrentSplitInstance().getBill().getItemList().size() - 1);
        saveModel();
    }

    private void setCurrentItemSelected(int index) {
        currentItemSelected = index;
    }

    public Item getCurrentItemSelected() {
        //TODO que sea consistentes los nombres
        if(getCurrentSplitInstance() != null &&
                getCurrentSplitInstance().getBill() != null &&
                getCurrentSplitInstance().getBill().getItemList() != null) {
            Item item = getCurrentSplitInstance().getBill().getItemList().get(currentItemSelected);
            return item;
        }
        return null;
    }

    public int getCurrentItemSelectedIndex() {
        //TODO que sea consistentes los nombres
        return currentItemSelected;
    }

    public List<Item> getItemListOfCurrentParticipant() {
        List<Item> returnList = new ArrayList<>();

        for(Item item : getCurrentSplitInstance().getBill().getItemList()) {
            if(item.getParticipant().equals(getCurrentParticipantSelected())) {
                returnList.add(item);
            }
        }
        return returnList;
    }

    public void splitBill() {
        BillSplitterApp.getInstance().getCurrentSplitInstance().splitBill();
        saveModel();
    }

    public void deleteItem(int position) {
        getCurrentSplitInstance().getBill().getItemList().remove(position);
        setCurrentItemSelected(getNewItemIndexAfterDelete(position));
        saveModel();
    }

    private int getNewItemIndexAfterDelete(int position) {
        int size = getCurrentSplitInstance().getBill().getItemList().size();
        int newPosition = position;
        if(position == 0) {
            newPosition = 0;
        } else {
            newPosition = position - 1;
        }

        if(newPosition >= size) {
            newPosition = -1;
        }
        return newPosition;
    }

    public void deleteParticipant(int position) {
        Iterator<Item> iterator = getCurrentSplitInstance().getBill().getItemList().iterator();
        while(iterator.hasNext()) {
            Item item = iterator.next();
            if(item.getParticipant().equals(getCurrentSplitInstance().getParticipantList().get(position))) {
                iterator.remove();
            }
        }
        getCurrentSplitInstance().getParticipantList().remove(position);
        setCurrentParticipantSelected(getNewParticipantAfterRemove(position));
        if(getCurrentParticipantSelectedIndex() < 0) {
            setCurrentItemSelected(-1);
        } else {
            setCurrentItemSelected(getNewItemIndexAfterDelete(0));
        }
        saveModel();
    }

    private int getNewParticipantAfterRemove(int position) {
        int size = getCurrentSplitInstance().getParticipantList().size();
        int newPosition = position;
        if(position == 0) {
            newPosition = 0;
        } else {
            newPosition = position - 1;
        }

        if(newPosition >= size) {
            newPosition = -1;
        }
        return newPosition;
    }
}
