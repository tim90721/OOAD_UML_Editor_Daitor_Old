package ooad.viewevent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import ooad.model.DrawMode;
import ooad.model.IModel;
import ooad.model.IPopMsgObserver;
import ooad.model.IPopMsgSubject;

public class CustomMouseEvent implements IPopMsgSubject{
	private IModel _model;
	private ArrayList<IPopMsgObserver> _observers;
	
	public CustomMouseEvent(IModel model) {
		this._model = model;
		this._observers = new ArrayList<IPopMsgObserver>();
	}
	
	public CustomMousePressedEvent getPressedEvent() {
		return new CustomMousePressedEvent(_model);
	}
	
	public CustomMouseDraggedEvent getDraggedEvent() {
		return new CustomMouseDraggedEvent(_model);
	}
	
	public CustomMouseReleaseEvent getReleasedEvent() {
		return new CustomMouseReleaseEvent(_model);
	}
	
	@Override
	public void registerPopMsgObserver(IPopMsgObserver observer) {
		_observers.add(observer);
	}

	@Override
	public void unregisterPopMsgObserver(IPopMsgObserver observer) {
		_observers.remove(observer);
	}

	@Override
	public void notifyPopMsgObserver() {
		for (IPopMsgObserver observer : _observers) 
			observer.updatePopMsg();
	}

	private class CustomMousePressedEvent extends MouseAdapter{
		private IModel _model;
		
		public CustomMousePressedEvent(IModel model) {
			this._model = model;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			_model.setMousePressed(true);
			_model.setMouseMoving(false);
			_model.newShape();
			_model.setMousePos(e.getX(), e.getY());
		}
	}

	private class CustomMouseDraggedEvent extends MouseAdapter{
		private IModel _model;
		
		public CustomMouseDraggedEvent(IModel model) {
			this._model = model;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			_model.setMousePos(e.getX(), e.getY());
			_model.setMouseDragging(true);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			super.mouseMoved(e);
			if(!_model.isMouseDragging()){
				if(!_model.isMouseMoving())
					_model.newShape(DrawMode.NONE);
				_model.setMouseMoving(true);
				_model.setMousePos(e.getX(), e.getY());
			}
		}
	}

	private class CustomMouseReleaseEvent extends MouseAdapter{
		private IModel _model;
		
		public CustomMouseReleaseEvent(IModel model) {
			this._model = model;
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			_model.setMousePressed(false);
			_model.setMousePos(e.getX(), e.getY());
			notifyPopMsgObserver();
		}
	}
}

