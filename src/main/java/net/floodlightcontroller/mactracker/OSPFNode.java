package net.floodlightcontroller.mactracker;

import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;

import java.util.ArrayList;

public class OSPFNode {
    private boolean isSwitch;
    private ArrayList<Triple<IPv4Address, Integer, Integer>> Accesible;     //ip, cost, port
    private ArrayList<Triple<DatapathId, Integer, Integer>> neighbour;       //swid, cost, port

    public OSPFNode() {
        this.isSwitch = true;
        Accesible = new ArrayList<>();
    }
    public boolean addToAccesible(IPv4Address newIP, int cost, int port){
        for (Triple<IPv4Address, Integer, Integer> itr : Accesible){
            if (newIP.equals(itr.getFirst())){
                if (cost < itr.getSecond()){
                    itr.setSecond(cost);
                    itr.setThird(port);
                    return true;
                }
            }
            return Accesible.add(new Triple<>(newIP, cost, port));
        }
        return false;
    }
    public ArrayList<Triple<IPv4Address, Integer, Integer>> getAccesible(){
        return Accesible;
    }
    public boolean isSwitch(){
        return this.isSwitch;
    }

    public void addNeighbour(Triple<DatapathId, Integer, Integer> newNeighbour){
        neighbour.add(newNeighbour);
    }


}








