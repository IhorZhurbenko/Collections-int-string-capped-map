package com.epam.autotasks.collections;

import java.util.*;

class IntStringCappedMap extends AbstractMap<Integer, String> {

    private final long capacity;
    private final Map<Integer, String> map;

    public IntStringCappedMap(final long capacity) {
        this.capacity = capacity;
        map = new LinkedHashMap<>((int) capacity);
    }

    public long getCapacity() {
        return capacity;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<Integer, String>> iterator() {
                return new Iterator<>() {
                    final Iterator<Entry<Integer, String>> iterator = map.entrySet().iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public Entry<Integer, String> next() {
                        return iterator.next();
                    }
                };
            }

            @Override
            public int size() {
                return map.size();
            }
        };
    }

    @Override
    public String get(final Object key) {
        return map.get(key);
    }

    @Override
    public String put(final Integer key, final String value) {
        if (value.length() > capacity) throw new IllegalArgumentException();
        //Видаляю значення по ключу бо таке ж значення я можу додавати. Потрібно щоб уникнути колізії.
        String node = map.remove(key);

        while (totalSizeOfValues() + value.length() > capacity)
            map.remove(entrySet().iterator().next().getKey());

        map.put(key, value);
        return node;
    }

    private long totalSizeOfValues() {
        return map.values().stream().mapToLong(String::length).sum();
    }

    @Override
    public String remove(final Object key) {
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }
}
/* public String put(final Integer key, final String value) {
        if (value.length() > capacity) throw new IllegalArgumentException();
        // перевірка на той самий ключ
        String previousValue = null;
        if (map.containsKey(key)) {
            previousValue = map.remove(key);
        }
        while (totalSizeOfValues() + value.length() > capacity) {
            map.remove(getFirst(map).getKey());
        }

        String str = map.put(key, value);
        if (previousValue != null) return previousValue;
        return str;
    }*/