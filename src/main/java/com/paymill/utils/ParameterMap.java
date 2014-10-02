package com.paymill.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ParameterMap<K, V> implements Map<K, List<V>> {
  private final HashMap<K, List<V>> map;

  public ParameterMap() {
    this.map = new HashMap<K, List<V>>();
  }

  public void add( final K key, final V value ) {
    List<V> values = this.map.get( key );

    if( values == null ) {
      values = new ArrayList<V>();
    }

    values.add( value );

    this.map.put( key, values );
  }

  public V getFirst( final K key ) {
    return this.get( key ) != null ? this.get( key ).get( 0 ) : null;
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey( final Object o ) {
    return this.map.containsKey( o );
  }

  @Override
  public boolean containsValue( final Object o ) {
    return this.map.containsValue( o );
  }

  @Override
  public List<V> get( final Object o ) {
    return this.map.get( o );
  }

  @Override
  public List<V> put( final K k, final List<V> vs ) {
    return this.map.put( k, vs );
  }

  @Override
  public List<V> remove( final Object o ) {
    return this.map.remove( o );
  }

  @Override
  public void putAll( final Map<? extends K, ? extends List<V>> map ) {
    this.map.putAll( map );
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public Set<K> keySet() {
    return this.map.keySet();
  }

  @Override
  public Collection<List<V>> values() {
    return this.map.values();
  }

  @Override
  public Set<Entry<K, List<V>>> entrySet() {
    return this.map.entrySet();
  }
}
