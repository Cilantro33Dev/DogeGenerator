package imsmartshutup;

public class Position {

	private int x;
	private int y;
	private String s;
	
	public Position(int x, int y, String s)  {
		this.x = x;
		this.y = y;
		this.s = s;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	
}
