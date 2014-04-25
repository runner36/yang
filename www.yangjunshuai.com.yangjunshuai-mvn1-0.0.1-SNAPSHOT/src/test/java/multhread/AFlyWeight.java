package multhread;

public class AFlyWeight implements FlyWeight{
	
	private String state;
	
	public AFlyWeight(String state) {
		this.state = state;
	}
	
	public void getMsg() {
		System.out.println("A 的 状态是"+state);
	}
	

}
