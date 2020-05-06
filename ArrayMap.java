import java.util.*;

public class ArrayMap <K, V> extends AbstractMap<K, V> {
    class ArrayMapEntry implements Map.Entry<K, V>{
        private K key;
        private V value;

        public ArrayMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V v) {
            V oldValue = this.value;
            this.value = v;
            return oldValue;
        }
        public String toString(){
            return "[" + key + "|" + value + "]";
        }
        public boolean equals(Object o){
            if (getClass() != o.getClass())
                return false;
            Map.Entry myObj = (Map.Entry) o;

            return (key.equals(myObj.getKey())) && (value.equals(myObj.getValue()));
        }
        public int hashCode(){
            return super.hashCode();
        }

    }
    private ArrayList<ArrayMapEntry> collection;

    public ArrayList<ArrayMapEntry> getCollection() {
        return collection;
    }

    public ArrayMap() {
        collection = new ArrayList<>();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(collection);
    }

    public V put(K key, V value){
        V old;
        for(ArrayMapEntry e : collection){
            if(e.getKey().equals(key)){
                old = e.getValue();
                e.setValue(value);
                return old;
            }
        }

        collection.add(new ArrayMapEntry(key, value));
        return null;
    }
    public boolean containsKey(Object key){
        return super.containsKey(key);
    }
    public V get(Object key){
        return super.get(key);
    }
    public int size(){
        return collection.size();
    }
    public String toString(){
        StringBuilder string = new StringBuilder("{");
        for(ArrayMapEntry e : collection){
            string.append(e.toString());
        }
        string.append("}");
        return string.toString();
    }

}