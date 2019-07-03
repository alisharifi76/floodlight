package net.floodlightcontroller.mactracker;

import java.util.*;

import net.floodlightcontroller.core.types.NodePortTuple;
import net.floodlightcontroller.devicemanager.IDevice;
import net.floodlightcontroller.devicemanager.IDeviceService;
import net.floodlightcontroller.devicemanager.SwitchPort;
import net.floodlightcontroller.linkdiscovery.ILinkDiscoveryService;
import net.floodlightcontroller.linkdiscovery.Link;
import net.floodlightcontroller.statistics.IStatisticsService;
import net.floodlightcontroller.topology.ITopologyService;
import net.floodlightcontroller.util.OFMessageUtils;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IPv6Address;
import org.projectfloodlight.openflow.types.MacAddress;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import net.floodlightcontroller.core.IFloodlightProviderService;

import java.util.concurrent.ConcurrentSkipListSet;

import net.floodlightcontroller.packet.Ethernet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MACTracker implements IOFMessageListener, IFloodlightModule {

    protected IFloodlightProviderService floodlightProvider;
    protected Set<Long> macAddresses;
    protected static Logger logger;
    protected ITopologyService topologyService;
    protected IStatisticsService iStatisticsService;
    private int cnt = 0;
    protected IDeviceService deviceManagerService;
    protected ILinkDiscoveryService iLinkDiscoveryService;
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
    }

    @Override
    public void startUp(FloodlightModuleContext context) {
        iStatisticsService.collectStatistics(true);
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
        floodlightProvider.addOFMessageListener(OFType.HELLO, this);
    }

    @Override
    public net.floodlightcontroller.core.IListener.Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
        Collection<? extends IDevice> allDevices = deviceManagerService.getAllDevices();
        if(cnt == 0) {
//            logger.info(String.valueOf(iLinkDiscoveryService.getLinks().keySet().toArray().length));
            for(Link ll : iLinkDiscoveryService.getLinks().keySet()) {
//                logger.info(ll.toString());
            }
            cnt++;
        }
//        logger.info(sw.getId().toString());
//        logger.info(sw.getAttributes().toString());
        for (IDevice d : allDevices) {
            SwitchPort[] ii= d.getAttachmentPoints();
            IPv4Address[] jj = d.getIPv4Addresses();
            for (int j = 0; j < d.getIPv4Addresses().length; j++) {
//                logger.info(ii[j].toString());
//                logger.info(jj[j].toString());
            }
        }


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

}