package net.floodlightcontroller.mactracker;

import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.OFPort;

import java.util.ArrayList;

public class OSPFNode {
    private boolean isSwitch;
    private DatapathId swid;
    private ArrayList<Triple<IPv4Address, Integer, OFPort>> Accesible;     //ip, cost, port
    private ArrayList<Triple<DatapathId, Integer, OFPort>> neighbour;       //swid, cost, port

    public OSPFNode(DatapathId SWID) {
        this.swid = SWID;
        this.isSwitch = true;
        Accesible = new ArrayList<>();
    }
    public boolean addToAccesible(IPv4Address newIP, int cost, OFPort port){
        for (Triple<IPv4Address, Integer, OFPort> itr : Accesible){
            if (newIP.equals(itr.getFirst())){
                if (cost < itr.getSecond()){
                    itr.setSecond(cost);
                    itr.setThird(port);
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return Accesible.add(new Triple<>(newIP, cost, port));
    }
    public ArrayList<Triple<IPv4Address, Integer, OFPort>> getAccesible(){
        return Accesible;
    }
    public boolean isSwitch(){
        return this.isSwitch;
    }
    public ArrayList<Triple<DatapathId, Integer, OFPort>> getNeighbour(){return neighbour;}
    public void addNeighbour(Triple<DatapathId, Integer, OFPort> newNeighbour){
        neighbour.add(newNeighbour);
    }
    public DatapathId getSWID(){return swid;}

}








