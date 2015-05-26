package com.ibm.wala.mobile.test;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.ibm.wala.mobile.CallGraphService;
import com.ibm.wala.util.collections.Pair;
import com.ibm.wala.util.graph.impl.SlowSparseNumberedGraph;

public class CallGraphServiceTest extends ServiceTestCase<CallGraphService> {

	public CallGraphServiceTest() {
		super(CallGraphService.class);
	}

	private Intent makeIntent() {
		Intent intent = new Intent();
		intent.setClass(getContext(), CallGraphService.class);
		return intent;
	}

	@SmallTest  
	public void testStartable() {  
		Intent startIntent = makeIntent();  
		startService(startIntent);  
	}

	@SmallTest  
	public void testBindable() {  
		Intent startIntent = makeIntent();  
		IBinder service = bindService(startIntent); 
		assert service != null;
	}  

	@MediumTest  
	public void testCallGraph() throws RemoteException {  
		Intent startIntent = makeIntent();  
		IBinder service = bindService(startIntent); 
		
		Parcel callData = Parcel.obtain();
		Parcel returnData = Parcel.obtain();
		
		String app = "/data/test/com.ibm.wala.core.testdata_1.0.0a.dex";
		
		callData.writeString(app);
		callData.writeString("LdynamicCG/MainClass");
		service.transact(CallGraphService.MAIN_CALL_GRAPH, callData, returnData, 0);

		returnData.setDataPosition(0);
		@SuppressWarnings("unchecked")
		SlowSparseNumberedGraph<Pair<String,Integer>> CG = (SlowSparseNumberedGraph<Pair<String, Integer>>) returnData.readSerializable();
		
		Log.i("CallGraphServiceTest", CG.toString());

		callData.recycle();
		returnData.recycle();
		
		assert CG != null;
	}  

}
