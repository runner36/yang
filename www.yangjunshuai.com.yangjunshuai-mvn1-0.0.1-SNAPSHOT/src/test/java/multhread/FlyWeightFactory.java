package multhread;

import java.util.HashMap;
import java.util.Map;

public class FlyWeightFactory {
	
	private static  FlyWeightFactory instance = null;
	
	Map<String,FlyWeight> flyWeights = new HashMap<String,FlyWeight>();
	
	public FlyWeight getFlyWeight(String state){
		if(flyWeights.containsKey(state))
			return flyWeights.get(state);
		if(state.equals("ok") ){
			flyWeights.put(state, new AFlyWeight(state));
			return flyWeights.get(state);
		}
		if(state.equals("no")){
			flyWeights.put(state, new BFlyWeight(state));
			return flyWeights.get(state);
		}
		return null;
			
	}
	
	public static  FlyWeightFactory getInstance(){
		if(instance == null){
			instance = new FlyWeightFactory();
		}
		return instance;
	}
	
	

}
