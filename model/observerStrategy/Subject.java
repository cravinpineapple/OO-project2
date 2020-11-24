package model.observerStrategy;

// Subject knows its observers and provides an interface for attaching
//		and detaching Observer objects.

public interface Subject {

	public void addListener(Observer observer);
	public void removeListener(Observer observer);
	public void notifyListener();
	
}
