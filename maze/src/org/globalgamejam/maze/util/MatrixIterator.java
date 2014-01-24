package org.globalgamejam.maze.util;

import java.util.Iterator;
import java.util.Map;


public class MatrixIterator<Type> implements Iterator<Type> {
    
    private final Iterator<? extends Map<Integer, Type> > iteratorX;
    
    private Iterator<Type> iteratorY;

    
    public MatrixIterator(Iterator<? extends Map<Integer, Type> > chunks) {
            this.iteratorX = chunks;
    }


    @Override
    public boolean hasNext() {
            return iteratorY != null ? iteratorY.hasNext() || iteratorX.hasNext() : iteratorX.hasNext();
    }

    @Override
    public Type next() {
            if (iteratorY == null || !iteratorY.hasNext())
                    iteratorY = iteratorX.next().values().iterator();                
            return iteratorY.hasNext() ? iteratorY.next() : null;                
    }

    @Override
    public void remove() {
            // TODO: Not implemented yet
    }
}