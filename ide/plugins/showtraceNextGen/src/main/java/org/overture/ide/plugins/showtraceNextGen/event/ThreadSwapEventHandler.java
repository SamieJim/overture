package org.overture.ide.plugins.showtraceNextGen.event;

import org.overture.ide.plugins.showtraceNextGen.data.TraceCPU;
import org.overture.ide.plugins.showtraceNextGen.data.TraceData;
import org.overture.ide.plugins.showtraceNextGen.data.TraceThread;
import org.overture.ide.plugins.showtraceNextGen.view.GenericTabItem;
import org.overture.interpreter.messages.rtlog.nextgen.INextGenEvent;
import org.overture.interpreter.messages.rtlog.nextgen.NextGenThread.ThreadType;
import org.overture.interpreter.messages.rtlog.nextgen.NextGenThreadSwapEvent;

public class ThreadSwapEventHandler extends EventHandler {

	public ThreadSwapEventHandler(TraceData data) 
	{
		super(data);
	}

	@Override
	protected void handle(INextGenEvent event, GenericTabItem tab) 
	{
		NextGenThreadSwapEvent tEvent = null;
		
		if(event instanceof NextGenThreadSwapEvent)
			tEvent = (NextGenThreadSwapEvent)event;
		else
			throw new IllegalArgumentException("ThreadSwapEventHandler expected event of type: " + NextGenThreadSwapEvent.class.getName());
		
		if(tEvent.thread.type == ThreadType.INIT)
			return; //Ignore INIT threads
		
		Long cpuId = new Long(tEvent.thread.cpu.id);
		Long threadId = new Long(tEvent.thread.id);
		TraceCPU cpu = data.getCPU(cpuId);
		TraceThread thread = data.getThread(threadId);
				
		switch(tEvent.swapType)
		{
			case SWAP_IN: 
				eventViewer.drawThreadSwapIn(tab, cpu, thread); 
				cpu.setCurrentThread(threadId);
				cpu.setIdle(false);
				break;
			case DELAYED_IN: 
				eventViewer.drawDelayedThreadSwapIn(tab, cpu, thread); 
				cpu.setCurrentThread(threadId);
				cpu.setIdle(false);
				break;
			case SWAP_OUT: 
				eventViewer.drawThreadSwapOut(tab, cpu, thread); 
				cpu.setCurrentThread(null);
				cpu.setIdle(true);
				break;
		}
				
		return;
	}
	


}
