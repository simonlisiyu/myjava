/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lsy.knowledge.traffic.base.domain.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2018-01-03")
public class MapMatchPoint implements org.apache.thrift.TBase<MapMatchPoint, MapMatchPoint._Fields>, java.io.Serializable, Cloneable, Comparable<MapMatchPoint> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MapMatchPoint");

  private static final org.apache.thrift.protocol.TField MAP_MATCH_POINT_FIELD_DESC = new org.apache.thrift.protocol.TField("map_match_point", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField UPDATE_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("update_timestamp", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new MapMatchPointStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new MapMatchPointTupleSchemeFactory();

  public com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t map_match_point; // required
  public java.lang.String update_timestamp; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MAP_MATCH_POINT((short)1, "map_match_point"),
    UPDATE_TIMESTAMP((short)2, "update_timestamp");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // MAP_MATCH_POINT
          return MAP_MATCH_POINT;
        case 2: // UPDATE_TIMESTAMP
          return UPDATE_TIMESTAMP;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MAP_MATCH_POINT, new org.apache.thrift.meta_data.FieldMetaData("map_match_point", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t.class)));
    tmpMap.put(_Fields.UPDATE_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("update_timestamp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MapMatchPoint.class, metaDataMap);
  }

  public MapMatchPoint() {
  }

  public MapMatchPoint(
    com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t map_match_point,
    java.lang.String update_timestamp)
  {
    this();
    this.map_match_point = map_match_point;
    this.update_timestamp = update_timestamp;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MapMatchPoint(MapMatchPoint other) {
    if (other.isSetMap_match_point()) {
      this.map_match_point = new com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t(other.map_match_point);
    }
    if (other.isSetUpdate_timestamp()) {
      this.update_timestamp = other.update_timestamp;
    }
  }

  public MapMatchPoint deepCopy() {
    return new MapMatchPoint(this);
  }

  @Override
  public void clear() {
    this.map_match_point = null;
    this.update_timestamp = null;
  }

  public com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t getMap_match_point() {
    return this.map_match_point;
  }

  public MapMatchPoint setMap_match_point(com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t map_match_point) {
    this.map_match_point = map_match_point;
    return this;
  }

  public void unsetMap_match_point() {
    this.map_match_point = null;
  }

  /** Returns true if field map_match_point is set (has been assigned a value) and false otherwise */
  public boolean isSetMap_match_point() {
    return this.map_match_point != null;
  }

  public void setMap_match_pointIsSet(boolean value) {
    if (!value) {
      this.map_match_point = null;
    }
  }

  public java.lang.String getUpdate_timestamp() {
    return this.update_timestamp;
  }

  public MapMatchPoint setUpdate_timestamp(java.lang.String update_timestamp) {
    this.update_timestamp = update_timestamp;
    return this;
  }

  public void unsetUpdate_timestamp() {
    this.update_timestamp = null;
  }

  /** Returns true if field update_timestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdate_timestamp() {
    return this.update_timestamp != null;
  }

  public void setUpdate_timestampIsSet(boolean value) {
    if (!value) {
      this.update_timestamp = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case MAP_MATCH_POINT:
      if (value == null) {
        unsetMap_match_point();
      } else {
        setMap_match_point((com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t)value);
      }
      break;

    case UPDATE_TIMESTAMP:
      if (value == null) {
        unsetUpdate_timestamp();
      } else {
        setUpdate_timestamp((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case MAP_MATCH_POINT:
      return getMap_match_point();

    case UPDATE_TIMESTAMP:
      return getUpdate_timestamp();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case MAP_MATCH_POINT:
      return isSetMap_match_point();
    case UPDATE_TIMESTAMP:
      return isSetUpdate_timestamp();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof MapMatchPoint)
      return this.equals((MapMatchPoint)that);
    return false;
  }

  public boolean equals(MapMatchPoint that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_map_match_point = true && this.isSetMap_match_point();
    boolean that_present_map_match_point = true && that.isSetMap_match_point();
    if (this_present_map_match_point || that_present_map_match_point) {
      if (!(this_present_map_match_point && that_present_map_match_point))
        return false;
      if (!this.map_match_point.equals(that.map_match_point))
        return false;
    }

    boolean this_present_update_timestamp = true && this.isSetUpdate_timestamp();
    boolean that_present_update_timestamp = true && that.isSetUpdate_timestamp();
    if (this_present_update_timestamp || that_present_update_timestamp) {
      if (!(this_present_update_timestamp && that_present_update_timestamp))
        return false;
      if (!this.update_timestamp.equals(that.update_timestamp))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetMap_match_point()) ? 131071 : 524287);
    if (isSetMap_match_point())
      hashCode = hashCode * 8191 + map_match_point.hashCode();

    hashCode = hashCode * 8191 + ((isSetUpdate_timestamp()) ? 131071 : 524287);
    if (isSetUpdate_timestamp())
      hashCode = hashCode * 8191 + update_timestamp.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(MapMatchPoint other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetMap_match_point()).compareTo(other.isSetMap_match_point());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMap_match_point()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.map_match_point, other.map_match_point);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUpdate_timestamp()).compareTo(other.isSetUpdate_timestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdate_timestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.update_timestamp, other.update_timestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("MapMatchPoint(");
    boolean first = true;

    sb.append("map_match_point:");
    if (this.map_match_point == null) {
      sb.append("null");
    } else {
      sb.append(this.map_match_point);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("update_timestamp:");
    if (this.update_timestamp == null) {
      sb.append("null");
    } else {
      sb.append(this.update_timestamp);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (map_match_point != null) {
      map_match_point.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MapMatchPointStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MapMatchPointStandardScheme getScheme() {
      return new MapMatchPointStandardScheme();
    }
  }

  private static class MapMatchPointStandardScheme extends org.apache.thrift.scheme.StandardScheme<MapMatchPoint> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MapMatchPoint struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MAP_MATCH_POINT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.map_match_point = new com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t();
              struct.map_match_point.read(iprot);
              struct.setMap_match_pointIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UPDATE_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.update_timestamp = iprot.readString();
              struct.setUpdate_timestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, MapMatchPoint struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.map_match_point != null) {
        oprot.writeFieldBegin(MAP_MATCH_POINT_FIELD_DESC);
        struct.map_match_point.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.update_timestamp != null) {
        oprot.writeFieldBegin(UPDATE_TIMESTAMP_FIELD_DESC);
        oprot.writeString(struct.update_timestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MapMatchPointTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public MapMatchPointTupleScheme getScheme() {
      return new MapMatchPointTupleScheme();
    }
  }

  private static class MapMatchPointTupleScheme extends org.apache.thrift.scheme.TupleScheme<MapMatchPoint> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MapMatchPoint struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetMap_match_point()) {
        optionals.set(0);
      }
      if (struct.isSetUpdate_timestamp()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetMap_match_point()) {
        struct.map_match_point.write(oprot);
      }
      if (struct.isSetUpdate_timestamp()) {
        oprot.writeString(struct.update_timestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MapMatchPoint struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.map_match_point = new com.lsy.knowledge.traffic.base.domain.thrift.map_match_point_t();
        struct.map_match_point.read(iprot);
        struct.setMap_match_pointIsSet(true);
      }
      if (incoming.get(1)) {
        struct.update_timestamp = iprot.readString();
        struct.setUpdate_timestampIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

