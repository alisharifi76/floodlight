package net.floodlightcontroller.mactracker;

import java.util.*;

import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.types.NodePortTuple;
import net.floodlightcontroller.devicemanager.IDevice;
import net.floodlightcontroller.devicemanager.IDeviceService;
import net.floodlightcontroller.devicemanager.SwitchPort;
import net.floodlightcontroller.linkdiscovery.ILinkDiscoveryService;
import net.floodlightcontroller.linkdiscovery.Link;
import net.floodlightcontroller.staticentry.IStaticEntryPusherService;
import net.floodlightcontroller.statistics.IStatisticsService;
import net.floodlightcontroller.topology.ITopologyService;
import net.floodlightcontroller.util.OFMessageUtils;
import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.*;
import net.floodlightcontroller.flowcache.*;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.routing.IRoutingService;


import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import net.floodlightcontroller.accesscontrollist.ACLRule;


import net.floodlightcontroller.core.IFloodlightProviderService;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

import net.floodlightcontroller.packet.Ethernet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.font.TrueTypeFont;

import static jdk.internal.net.http.common.Utils.stringOf;


public class MACTracker implements IOFMessageListener, IFloodlightModule {

    protected IFloodlightProviderService floodlightProvider;
    protected Set<Long> macAddresses;
    protected static Logger logger;
    protected ITopologyService topologyService;
    protected IStatisticsService iStatisticsService;
    private int cnt = 0;
    protected IDeviceService deviceManagerService;
    protected ILinkDiscoveryService iLinkDiscoveryService;
    protected IRoutingService iRoutingService;
    protected ACLRule rule;
    private ArrayList<OSPFNode> OSPFNodes;
    protected IOFSwitchService switchService;
    protected IStaticEntryPusherService sfp;

    @Override
    public String getName() {
        return MACTracker.class.getSimpleName();
    }

    @Override
    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l =
                new ArrayList<Class<? extends IFloodlightService>>();
        l.add(IFloodlightProviderService.class);
        l.add(IStatisticsService.class);
        l.add(ITopologyService.class);
        l.add(ILinkDiscoveryService.class);
        l.add(IOFSwitchService.class);
        l.add(IStaticEntryPusherService.class);

        return l;
    }

    @Override
    public void init(FloodlightModuleContext context) throws FloodlightModuleException {
        floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
        macAddresses = new ConcurrentSkipListSet<Long>();
        logger = LoggerFactory.getLogger(MACTracker.class);
        iStatisticsService = context.getServiceImpl(IStatisticsService.class);
        topologyService = context.getServiceImpl(ITopologyService.class);
        deviceManagerService = context.getServiceImpl(IDeviceService.class);
        iLinkDiscoveryService = context.getServiceImpl(ILinkDiscoveryService.class);
        OSPFNodes = new ArrayList<>();
        sfp = context.getServiceImpl(IStaticEntryPusherService.class);
        switchService = context.getServiceImpl(IOFSwitchService.class);

    }

    @Override
    public void startUp(FloodlightModuleContext context) {
        iStatisticsService.collectStatistics(true);
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
        discoverTopology();
        OSPF();


    }

    @Override
    public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
        Collection<? extends IDevice> allDevices = deviceManagerService.getAllDevices();

        if(cnt == 0) {
//            logger.info(String.valueOf(iLinkDiscoveryService.getLinks().keySet().toArray().length));
            for(DatapathId ll : topologyService.getAllLinks().keySet()) {
//                logger.info(ll.toString());
            }
//            cnt++;
        }
//        logger.info(sw.getId().toString());
//        if(cnt==0) {
            for (IDevice d : allDevices) {
                SwitchPort[] ii = d.getAttachmentPoints();
                IPv4Address[] jj = d.getIPv4Addresses();
                for (int j = 0; j < ii.length; j++) {
//                    logger.info(ii[j].getNodeId().toString());
//                logger.info(jj[j].toString());
                }

            }
//            cnt++;
//        }

//        logger.info(topologyService.getAllLinks().toString());
        Set<NodePortTuple> k = iStatisticsService.getBandwidthConsumption().keySet();
        Iterator<NodePortTuple> s = k.iterator();
//        int l = k[0];
        if(iStatisticsService.getBandwidthConsumption().keySet().size() != 0) {
            Object i = iStatisticsService.getBandwidthConsumption().keySet().toArray()[3];
//            logger.info(iStatisticsService.getBandwidthConsumption().get(i).getLinkSpeedBitsPerSec().toString());
        }
//        logger.info(String.valueOf(k.size()));


            Ethernet eth =
                IFloodlightProviderService.bcStore.get(cntx,
                        IFloodlightProviderService.CONTEXT_PI_PAYLOAD);

        Long sourceMACHash = eth.getSourceMACAddress().getLong();
        if (!macAddresses.contains(sourceMACHash)) {
            macAddresses.add(sourceMACHash);
//            logger.info("MAC Address: {} seen on switch: {}",
//                    eth.getSourceMACAddress().toString(),
//                    sw.getId().toString());
        }
        return Command.CONTINUE;
    }
    private void discoverTopology(){
//        HashMap<String, Integer> deviceCheck = new HashMap<>();
//        boolean has = false;
//
//        while (true)
//            if (!topologyService.getAllLinks().isEmpty() && !(deviceManagerService.getAllDevices().size()<1))
//                break;
//        int count = 0;
//
//        Collection<? extends IDevice> allDevices = deviceManagerService.getAllDevices();
//        for (IDevice d : allDevices) {
//            SwitchPort[] ii = d.getAttachmentPoints();
//            if (d.getMACAddressString().substring(0,2).equals("00")){
//                logger.info("logggggggggggggggg " + ii[0].getNodeId());
//                for (int i = 0; i < OSPFNodes.size(); i++){
//                    if (OSPFNodes.get(i).getSWID().equals(ii[0].getNodeId())){
//                        OSPFNodes.get(i).addToAccesible(d.getIPv4Addresses()[0], 0, ii[0].getPortId());
//                        has = true;
//                    }
//                }
//                if (!has){
//                    OSPFNode newNode = new OSPFNode(ii[0].getNodeId());
//                    newNode.addToAccesible(d.getIPv4Addresses()[0], 0, ii[0].getPortId());
//                    OSPFNodes.add(newNode);
//                    has = false;
//                }
//            }
//        }
//        logger.info("sizeeeeeeeeeeeeeeeeee " + OSPFNodes.size());
//        for (int i = 0; i < OSPFNodes.get(0).getAccesible().size(); i++){
//            logger.info(OSPFNodes.get(0).getAccesible().get(i).getFirst().toString());
//        }
        OSPFNode newNode1 = new OSPFNode(DatapathId.of("01"));
        OSPFNode newNode2 = new OSPFNode(DatapathId.of("02"));
        OSPFNode newNode3 = new OSPFNode(DatapathId.of("03"));
        OSPFNode newNode4 = new OSPFNode(DatapathId.of("04"));
        newNode1.addToAccesible(IPv4Address.of("10.0.0.1/24"), 0, OFPort.of(1));
        newNode4.addToAccesible(IPv4Address.of("10.0.1.1/24"), 0, OFPort.of(1));
        newNode1.addNeighbour(new Triple<>(DatapathId.of("02"), 10, OFPort.of(2)));
        newNode1.addNeighbour(new Triple<>(DatapathId.of("03"), 15, OFPort.of(3)));
        newNode2.addNeighbour(new Triple<>(DatapathId.of("01"), 10, OFPort.of(1)));
        newNode2.addNeighbour(new Triple<>(DatapathId.of("04"), 20, OFPort.of(2)));
        newNode3.addNeighbour(new Triple<>(DatapathId.of("01"), 15, OFPort.of(1)));
        newNode3.addNeighbour(new Triple<>(DatapathId.of("04"), 30, OFPort.of(2)));
        newNode4.addNeighbour(new Triple<>(DatapathId.of("02"), 20, OFPort.of(1)));
        newNode4.addNeighbour(new Triple<>(DatapathId.of("03"), 30, OFPort.of(2)));
        OSPFNodes.add(newNode1);
        OSPFNodes.add(newNode2);
        OSPFNodes.add(newNode3);
        OSPFNodes.add(newNode4);

    }

    private void OSPF() {
        boolean update = false;
        while (true) {
            for (OSPFNode ospfNode : OSPFNodes) {
                for (int i = 0; i < ospfNode.getNeighbour().size(); i++) {
                    for (OSPFNode ospfNeighbour : OSPFNodes) {
                        if (ospfNode.getNeighbour().get(i).getFirst().equals(ospfNeighbour.getSWID())) {
                            for (int j = 0; j < ospfNeighbour.getAccesible().size(); j++) {
                                update |= ospfNode.addToAccesible(ospfNeighbour.getAccesible().get(j).getFirst(),
                                        ospfNode.getNeighbour().get(i).getSecond()
                                                + ospfNeighbour.getAccesible().get(j).getSecond(),
                                        ospfNode.getNeighbour().get(i).getThird());
                            }
                        }
                    }
                }
            }
            if (!update)
                break;
            else
                update = false;
        }
        for (OSPFNode ospfNode : OSPFNodes){
            IOFSwitch sw = switchService.getSwitch(ospfNode.getSWID());
            OFFlowAdd.Builder flow = sw.getOFFactory().buildFlowAdd();
            Match.Builder match = sw.getOFFactory().buildMatch();
            ArrayList<OFAction> actionList = new ArrayList<OFAction>();
            OFActionOutput.Builder action = sw.getOFFactory().actions().buildOutput();
            for (Triple<IPv4Address, Integer, OFPort> accesible : ospfNode.getAccesible()) {
                match.setExact(MatchField.ETH_TYPE, EthType.IPv4);
                match.setExact(MatchField.IPV4_DST, accesible.getFirst());
                action.setMaxLen(0xffFFffFF);
                action.setPort(accesible.getThird());
                actionList.add(action.build());
                flow.setBufferId(OFBufferId.NO_BUFFER);
                flow.setHardTimeout(0);
                flow.setIdleTimeout(0);
                flow.setOutPort(accesible.getThird());
                flow.setActions(actionList);
                flow.setMatch(match.build());
                sfp.addFlow("dhcp-port---" + accesible.getThird().getPortNumber() + "---(" + accesible.getThird().toString() + ")", flow.build(), sw.getId());
                flow.setPriority(1);


            }


        }

    }
}




