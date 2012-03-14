package org.sidor.androidapps.simpletuner;

public class CircularBuffer {
	private short [] array;
	private int head;
	private int size;
	private int availableElements;
	
	public CircularBuffer(int s) {
		size = s;
		array = new short[size];
		head = 0;
		availableElements = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	public synchronized void push(short x) {
		array[head++] = x;
		if(head>=size) head-=size;
		availableElements = Math.min(availableElements+1, size);
	}
	
	public synchronized int getElements(double [] result, int offset, int maxElements) {
		int toRead = Math.min(maxElements, availableElements);
		int current = head - 1;
		for(int i=offset+toRead-1; i>=offset; --i) {
			if(current < 0) current+=size;
			result[i]=array[current--];
		}
		return toRead;
	}
}
