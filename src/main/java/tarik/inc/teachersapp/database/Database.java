package tarik.inc.teachersapp.database;

import tarik.inc.teachersapp.dto.RowDTO;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Database {

    private final List<RowDTO> data = new ArrayList<>();

    private static Database instance;

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();

        return instance;
    }

    private Database() {
    }

    public void importAll(Collection<? extends RowDTO> rows) {
        data.clear();
        data.addAll(rows);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean contains(Object o) {
        return data.contains(o);
    }

    public Iterator<RowDTO> iterator() {
        return data.iterator();
    }

    public Object[] toArray() {
        return data.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    public boolean add(RowDTO rowDTO) {
        return data.add(rowDTO);
    }

    public boolean remove(Object o) {
        return data.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    public boolean addAll(Collection<? extends RowDTO> c) {
        return data.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends RowDTO> c) {
        return data.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return data.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return data.retainAll(c);
    }

    public void replaceAll(UnaryOperator<RowDTO> operator) {
        data.replaceAll(operator);
    }

    public void sort(Comparator<? super RowDTO> c) {
        data.sort(c);
    }

    public void clear() {
        data.clear();
    }

    public RowDTO get(int index) {
        return data.get(index);
    }

    public RowDTO set(int index, RowDTO element) {
        return data.set(index, element);
    }

    public void add(int index, RowDTO element) {
        data.add(index, element);
    }

    public RowDTO remove(int index) {
        return data.remove(index);
    }

    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    public List<RowDTO> subList(int fromIndex, int toIndex) {
        return data.subList(fromIndex, toIndex);
    }

    public Spliterator<RowDTO> spliterator() {
        return data.spliterator();
    }

    public <T> T[] toArray(IntFunction<T[]> generator) {
        return data.toArray(generator);
    }

    public boolean removeIf(Predicate<? super RowDTO> filter) {
        return data.removeIf(filter);
    }

    public Stream<RowDTO> stream() {
        return data.stream();
    }

    public Stream<RowDTO> parallelStream() {
        return data.parallelStream();
    }

    public void forEach(Consumer<? super RowDTO> action) {
        data.forEach(action);
    }
}
