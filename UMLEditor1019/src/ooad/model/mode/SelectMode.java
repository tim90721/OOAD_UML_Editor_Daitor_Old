package ooad.model.mode;

import java.awt.Graphics;
import java.util.ArrayList;

import ooad.model.DrawMode;
import ooad.model.IModel;
import ooad.model.Shape.IShape;
import ooad.model.Shape.IStringField;
import ooad.model.Shape.NoneShape;

public class SelectMode extends AbstractMode {
	private int _startX;
	private int _startY;
	private int _endX;
	private int _endY;
	private ArrayList<IShape> _selectShapes;

	public SelectMode(IModel model) {
		super(model);
		_selectShapes = new ArrayList<IShape>();
		_hasSelectShape = false;
	}

	@Override
	public void storeShape(IShape shape) {
	}

	@Override
	public boolean checkIsSelect(IShape selectArea) {
		super.checkIsSelect(selectArea);
		_hasSelectShape = false;
		_selectShapes.removeAll(_selectShapes);
		if (_model.isMouseDragging()) {
			configCoordinate(selectArea);
			for (IShape shape : _model.getStoreShapes())
				if (shape.getStartX() > _startX && shape.getStartY() > _startY
						&& shape.getEndX() < _endX && shape.getEndY() < _endY) {
					shape.setSelected(true);
					_selectShapes.add(shape);
					_hasSelectShape = true;
				}
		} else
			for (IShape shape : _model.getStoreShapes())
				if (shape.getStartX() < selectArea.getStartX()
						&& shape.getEndX() > selectArea.getStartX()
						&& shape.getStartY() < selectArea.getStartY()
						&& shape.getEndY() > selectArea.getStartY()) {
					shape.setSelected(true);
					_selectShapes.add(shape);
					_hasSelectShape = true;
				}
		if (_hasSelectShape)
			return true;
		return false;
	}

	private void configCoordinate(IShape selectArea) {
		_startX = selectArea.getStartX();
		_startY = selectArea.getStartY();
		int selectStartX = _startX;
		int selectStartY = _startY;
		_endX = selectArea.getEndX();
		_endY = selectArea.getEndY();
		int temp;
		if (_startX > _endX) {
			temp = _endX;
			_endX = selectStartX;
			selectStartX = temp;
		}
		if (_startY > _endY) {
			temp = _endY;
			_endY = selectStartY;
			selectStartY = temp;
		}
		_startX = selectStartX;
		_startY = selectStartY;
	}

	@Override
	public void isLineEnclose(IShape line, int mouseX, int mouseY,
			int closeOffset) {
		super.isLineEnclose(line, mouseX, mouseY, closeOffset);
	}

	@Override
	public void addShapeString(IStringField stringField, String name) {
	}

	@Override
	public void setCoordinate(IShape shape, int mouseX, int mouseY) {
		super.setCoordinate(shape, mouseX, mouseY);
		if (_model.isMouseDragging())
			shape.setEnd(mouseX, mouseY);
	}

	@Override
	public void setMode() {
		_model.setDrawMode(DrawMode.SELECT);
	}

	@Override
	public boolean moveSelectShape(int mouseX, int mouseY) {

		return false;
	}

	@Override
	public void drawing(Graphics g, IShape shape, int mouseX, int mouseY,
			int closeOffset) {
		if (_model.isMousePressed() && !_model.isMouseDragging()){
			boolean isClickInSelectArea = false;
			for (IShape selectShape : _selectShapes) {
				System.out.println(selectShape.getStartX());
				System.out.println(selectShape.getEndX());
				System.out.println(mouseX);
				System.out.println(mouseY);
				if (selectShape.getStartX() < mouseX
						&& selectShape.getEndX() > mouseX
						&& selectShape.getStartY() < mouseY
						&& selectShape.getEndY() > mouseY) {
					_model.setPrevMousePos(mouseX, mouseY);
					_model.setSelectShapes(_selectShapes);
					_model.setUserMode(DrawMode.MOVING);
					isClickInSelectArea = true;
					return;
				}
			}
			if(!isClickInSelectArea)
				_selectShapes = new ArrayList<IShape>();
		}
		super.drawing(g, shape, mouseX, mouseY, closeOffset);
	}
	
	private void configSelectShapePos(IShape shape, int mouseX, int mouseY){
//		int difX = mouseX - shape.getStartX();
//		int difY = mouseY - shape.getStartY();
//		shape.setStart(mouseX, mouseY);
//		shape.setEnd(shape.getEndX() + difX, shape.getEndY() + difY);
	}
}
