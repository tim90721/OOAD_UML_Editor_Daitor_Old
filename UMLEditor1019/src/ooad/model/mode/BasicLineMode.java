package ooad.model.mode;

import ooad.model.IModel;
import ooad.model.Shape.IShape;

public abstract class BasicLineMode extends AbstractMode{
	
	public BasicLineMode(IModel model) {
		super(model);
	}

	@Override
	public void isLineEnclose(IShape line, int mouseX, int mouseY,
			int closeOffset) {
		super.isLineEnclose(line, mouseX, mouseY, closeOffset);
		for (IShape shape : _model.getStoreShapes())
			shape.isLineEnclose(line, mouseX, mouseY, closeOffset);
	}

	@Override
	public void setCoordinate(IShape shape, int mouseX, int mouseY) {
		super.setCoordinate(shape, mouseX, mouseY);
		if(_model.isMouseDragging())
			shape.setEnd(mouseX, mouseY);
	}

	@Override
	public void addShapeString(IShape shape, String name) {
		// TODO Auto-generated method stub
		
	}
}