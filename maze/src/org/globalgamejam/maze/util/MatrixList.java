package org.globalgamejam.maze.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class MatrixList<Type extends Indexable> implements Collection<Type> {        
        
        private int elementSize;

        private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Type>> chunks;
        
        
        
        public MatrixList() {
                elementSize = 0;
                chunks = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Type>>();
        }
        
        @Override
        public boolean add(Type element) {
                if (chunks.containsKey(element.getX())) {
                	ConcurrentHashMap<Integer, Type> yMap = chunks.get(element
                                        .getX());
                        if (!yMap.containsKey(element.getY())) {
                                yMap.put(element.getY(), element);
                                elementSize++;
                                return true;
                        } else {
                                return false;
                        }
                } else {
                	ConcurrentHashMap<Integer, Type> yChunkMap = new ConcurrentHashMap<Integer, Type>();
                        yChunkMap.put(element.getY(), element);
                        chunks.put(element.getX(), yChunkMap);
                        elementSize++;
                        return true;
                }
        }

        @Override
        public boolean addAll(Collection<? extends Type> objects) {
                
                boolean changed = false;
                
                for (Type object : objects) {
                        if (!changed) {
                                changed = add(object);
                        }
                }
                
                return changed;
        }

        @Override
        public void clear() {
                chunks.clear();
        }

        @Override
        public boolean contains(Object object) {
                if (object instanceof Indexable) {
                        Indexable indexable = (Indexable)object;
                        return contains(indexable.getX(), indexable.getY());
                } else {
                        return false;
                }
        }

        @Override
        public boolean containsAll(Collection<?> objects) {
                for (Type elem : this) {
                        if (!objects.contains(elem)) {
                                return false;
                        }
                }
                
                return true;
        }

        @Override
        public boolean isEmpty() {
                return chunks.isEmpty();
        }

        @Override
        public Iterator<Type> iterator() {
                return new MatrixIterator<Type>(chunks.values().iterator());
        }

        @Override
        public boolean remove(Object object) {
                if (object instanceof Indexable) {
                        Indexable indexable = (Indexable)object;
                        return remove(indexable.getX(), indexable.getY());
                } else {
                        return false;
                }
        }

        @SuppressWarnings("unchecked")
		@Override
        public boolean removeAll(Collection<?> objects) {
                boolean changed = false;
                
                for (Object object : objects) {
                        if (!changed) {
                                changed = remove((Type)object);
                        }
                }
                
                return changed;
        }

        @SuppressWarnings("unchecked")
		@Override
        public boolean retainAll(Collection<?> objects) {
                
                boolean changed = false;
                
                for (Object o : this) {
                        if (!objects.contains(o)) {
                                remove((Type)o);
                                changed = true;
                        }
                }
                
                return changed;
        }

        @Override
        public int size() {
                return elementSize;
        }

        @Override
        public Object[] toArray() {
                return toArray(new Object[size()]);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] objects) {
                if (objects.length != size()) {
                        objects = (T[]) new Object[size()];
                }
                
                int index = 0;
                
                for (Type type : this) {
                        objects[index++] = (T) type;
                }
                
                return objects;
        }

        public boolean remove(int indexX, int indexY) {
        	ConcurrentHashMap<Integer, Type> yChunkMap = chunks.get(indexX);

                if (yChunkMap != null) {
                        yChunkMap.remove(indexY);
                        // X axis
                        if (yChunkMap.isEmpty()) {
                                chunks.remove(indexX);
                        } else {
                                return false;
                        }
                        elementSize--;
                        return true;
                } else {
                        return false;
                }
        }

        public MatrixList<Type> copy() {
                MatrixList<Type> copyList = new MatrixList<Type>();
                for (Type element : this) {
                        copyList.add(element);
                }
                return copyList;
        }

        public boolean contains(int indexX, int indexY) {
                return get(indexX, indexY) != null;
        }

        public Type get(int indexX, int indexY) {
        	ConcurrentHashMap<Integer, Type> yChunkMap = chunks.get(indexX);

                if (yChunkMap != null) {
                        Type element = yChunkMap.get(indexY);

                        if (element != null) {
                                return element;
                        } else {
                                return null;
                        }
                } else {
                        return null;
                }
        }

        public void set(MatrixList<Type> matrixList) {
                this.elementSize = matrixList.size();
                clear();
                for (Type elem : matrixList) {
                        add(elem);
                }
        }
}