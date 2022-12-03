package tarik.inc.teachersapp.database;

import tarik.inc.teachersapp.dto.Faculty;
import tarik.inc.teachersapp.dto.KPIAward;
import tarik.inc.teachersapp.dto.RowDTO;
import tarik.inc.teachersapp.dto.StateAward;

import java.time.Year;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Database {

    private static Database instance;
    private final List<RowDTO> data = new ArrayList<>();

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();

        return instance;
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

    public boolean rowExists(String name, Faculty faculty,
                             KPIAward kpiAward, StateAward stateAward,
                             String protocolNum, Year kpiDiplomaYear, Year stateDiplomaYear) {

        return findRowDTO(name, faculty, kpiAward, stateAward,
                protocolNum, kpiDiplomaYear, stateDiplomaYear).isNotEmpty();
    }

    public RowDTO findRowDTO(String name, Faculty faculty,
                             KPIAward kpiAward, StateAward stateAward,
                             String protocolNum, Year kpiDiplomaYear, Year stateDiplomaYear) {
        return data.stream()
                .filter(rowDTO -> rowDTO.getName().equals(name))
                .filter(rowDTO -> rowDTO.getFaculty().equals(faculty))
                .filter(rowDTO -> rowDTO.getKpiDiploma().equals(kpiAward))
                .filter(rowDTO -> rowDTO.getStateDiploma().equals(stateAward))
                .filter(rowDTO -> rowDTO.getProtocolNum().equals(protocolNum))
                .filter(rowDTO -> rowDTO.getKpiDiplomaYear().equals(kpiDiplomaYear))
                .filter(rowDTO -> rowDTO.getStateDiplomaYear().equals(stateDiplomaYear))
                .findFirst().orElse(RowDTO.EMPTY);
    }

    public boolean rowWithKpiYearExists(String name, Faculty faculty, Year kpiDiplomaYear) {
        RowDTO row = data.stream()
                .filter(rowDTO -> rowDTO.getName().equals(name))
                .filter(rowDTO -> rowDTO.getFaculty().equals(faculty))
                .filter(rowDTO -> rowDTO.getKpiDiplomaYear().equals(kpiDiplomaYear))
                .findFirst().orElse(RowDTO.EMPTY);

        return row.isNotEmpty();
    }

    public boolean rowWithStateYearExists(String name, Faculty faculty, Year stateDiplomaYear) {
        RowDTO row = data.stream()
                .filter(rowDTO -> rowDTO.getName().equals(name))
                .filter(rowDTO -> rowDTO.getFaculty().equals(faculty))
                .filter(rowDTO -> rowDTO.getStateDiplomaYear().equals(stateDiplomaYear))
                .findFirst().orElse(RowDTO.EMPTY);

        return row.isNotEmpty();
    }

    public RowDTO SearchByID(Integer id) {
        for (var i : data) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public List<RowDTO> SearchByName(String name) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getName().equals((name))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByFaculty(Faculty faculty) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getFaculty().equals((faculty))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByKPIDiploma(KPIAward award) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getKpiDiploma().equals((award))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByStateDiploma(StateAward award) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getStateDiploma().equals((award))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByProtocolNum(String num) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getProtocolNum().equals((num))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByKPIDiplomaYear(Year year) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getKpiDiplomaYear().equals((year))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByStateDiplomaYear(Year year) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getStateDiplomaYear().equals((year))) {
                list.add(i);
            }
        }
        return list;
    }

    public List<RowDTO> SearchByPrognostication(KPIAward award) {
        List<RowDTO> list = new ArrayList<RowDTO>();
        for (var i : data) {
            if (i.getPrognostication().equals((award))) {
                list.add(i);
            }
        }
        return list;
    }

}
