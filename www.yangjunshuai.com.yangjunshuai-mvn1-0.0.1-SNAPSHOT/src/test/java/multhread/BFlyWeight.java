package multhread;

public class BFlyWeight implements FlyWeight{
	
	private String state;
	
	public BFlyWeight(String state) {
		this.state = state;
	}
	
	public void getMsg() {
		System.out.println("B 的 状态是"+state);
	}
	

}
