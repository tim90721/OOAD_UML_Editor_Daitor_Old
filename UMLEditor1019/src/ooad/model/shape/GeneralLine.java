package ooad.model.shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GeneralLine extends BasicLine {
	private int[] _trianglePointsOrgX = new int[]{ 0, 0, 20, 0, 0 };
	private int[] _trianglePointsOrgY = new int[]{ 0, 10, 0, -10, 0 };
	private int[] _trianglePointsX = new int[5];
	private int[] _trianglePointsY = new int[5];

	public GeneralLine() {
		_name = "GeneralLine";
	}
	
	@Override
	public void drawShape(Graphics g) {
		super.drawShape(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.rotate(getAngle(), getStartX(), getStartY());
		configTrianglePoints(getEndX(), getEndY());
		g2.drawPolyline(_trianglePointsX, _trianglePointsY, 5);
		g2.rotate(-1 * getAngle(), getStartX(), getStartY());
		g2.setStroke(new BasicStroke(1));
	}

	private void configTrianglePoints(int x, int y) {
		for(int i = 0;i < _trianglePointsX.length; i++) {
			_trianglePointsX[i] = getEndX() + _trianglePointsOrgX[i];
			_trianglePointsY[i] = getEndY() - _trianglePointsOrgY[i];
		}
	}

	@Override
	public void setEnd(int endX, int endY) {
		super.setEnd(endX, endY);
		setEndX(getEndX() - 20);
	}
}