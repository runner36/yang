package multhread;

public class MainFlyWeight {
	
	public static void main(String[] args) {
		FlyWeightFactory.getInstance().getFlyWeight("ok").getMsg();
		FlyWeightFactory.getInstance().getFlyWeight("ok").getMsg();
		FlyWeightFactory.getInstance().getFlyWeight("ok").getMsg();
		FlyWeightFactory.getInstance().getFlyWeight("no").getMsg();
	}

}
