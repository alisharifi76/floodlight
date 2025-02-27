package net.floodlightcontroller.statistics;

import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.core.types.NodePortTuple;

import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFPort;

//import javafx.util.Pair;
import org.sdnplatform.sync.internal.util.Pair;

import java.util.Map;
import java.util.Set;

public interface IStatisticsService extends IFloodlightService {
	
	String setFlowStatsPeriod(int period);
	
	String setPortStatsPeriod(int period);
	
	Map<NodePortTuple, PortDesc> getPortDesc();
	
	PortDesc getPortDesc(DatapathId dpid, OFPort p);
	
	Map<Pair<Match,DatapathId>, FlowRuleStats> getFlowStats();
	
	Set<FlowRuleStats> getFlowStats(DatapathId dpid);

	SwitchPortBandwidth getBandwidthConsumption(DatapathId dpid, OFPort p);
		
	Map<NodePortTuple, SwitchPortBandwidth> getBandwidthConsumption();
	
	void collectStatistics(boolean collect);

	boolean isStatisticsCollectionEnabled();
}
