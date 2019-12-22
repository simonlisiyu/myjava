/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lsy.knowledge.traffic.base.domain.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2017-12-27")
public class PointTraj2DistRequest implements org.apache.thrift.TBase<PointTraj2DistRequest, PointTraj2DistRequest._Fields>, java.io.Serializable, Cloneable, Comparable<PointTraj2DistRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PointTraj2DistRequest");

  private static final org.apache.thrift.protocol.TField POINT_TRAJ_VEC_FIELD_DESC = new org.apache.thrift.protocol.TField("point_traj_vec", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new PointTraj2DistRequestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new PointTraj2DistRequestTupleSchemeFactory();

  public java.util.List<point_traj_t> point_traj_vec; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POINT_TRAJ_VEC((short)1, "point_traj_vec");

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
        case 1: // POINT_TRAJ_VEC
          return POINT_TRAJ_VEC;
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
    tmpMap.put(_Fields.POINT_TRAJ_VEC, new org.apache.thrift.meta_data.FieldMetaData("point_traj_vec", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST,
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, point_traj_t.class))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PointTraj2DistRequest.class, metaDataMap);
  }

  public PointTraj2DistRequest() {
  }

  public PointTraj2DistRequest(
    java.util.List<point_traj_t> point_traj_vec)
  {
    this();
    this.point_traj_vec = point_traj_vec;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PointTraj2DistRequest(PointTraj2DistRequest other) {
    if (other.isSetPoint_traj_vec()) {
      java.util.List<point_traj_t> __this__point_traj_vec = new java.util.ArrayList<point_traj_t>(other.point_traj_vec.size());
      for (point_traj_t other_element : other.point_traj_vec) {
        __this__point_traj_vec.add(new point_traj_t(other_element));
      }
      this.point_traj_vec = __this__point_traj_vec;
    }
  }

  public PointTraj2DistRequest deepCopy() {
    return new PointTraj2DistRequest(this);
  }

  @Override
  public void clear() {
    this.point_traj_vec = null;
  }

  public int getPoint_traj_vecSize() {
    return (this.point_traj_vec == null) ? 0 : this.point_traj_vec.size();
  }

  public java.util.Iterator<point_traj_t> getPoint_traj_vecIterator() {
    return (this.point_traj_vec == null) ? null : this.point_traj_vec.iterator();
  }

  public void addToPoint_traj_vec(point_traj_t elem) {
    if (this.point_traj_vec == null) {
      this.point_traj_vec = new java.util.ArrayList<point_traj_t>();
    }
    this.point_traj_vec.add(elem);
  }

  public java.util.List<point_traj_t> getPoint_traj_vec() {
    return this.point_traj_vec;
  }

  public PointTraj2DistRequest setPoint_traj_vec(java.util.List<point_traj_t> point_traj_vec) {
    this.point_traj_vec = point_traj_vec;
    return this;
  }

  public void unsetPoint_traj_vec() {
    this.point_traj_vec = null;
  }

  /** Returns true if field point_traj_vec is set (has been assigned a value) and false otherwise */
  public boolean isSetPoint_traj_vec() {
    return this.point_traj_vec != null;
  }

  public void setPoint_traj_vecIsSet(boolean value) {
    if (!value) {
      this.point_traj_vec = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case POINT_TRAJ_VEC:
      if (value == null) {
        unsetPoint_traj_vec();
      } else {
        setPoint_traj_vec((java.util.List<point_traj_t>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case POINT_TRAJ_VEC:
      return getPoint_traj_vec();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case POINT_TRAJ_VEC:
      return isSetPoint_traj_vec();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof PointTraj2DistRequest)
      return this.equals((PointTraj2DistRequest)that);
    return false;
  }

  public boolean equals(PointTraj2DistRequest that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_point_traj_vec = true && this.isSetPoint_traj_vec();
    boolean that_present_point_traj_vec = true && that.isSetPoint_traj_vec();
    if (this_present_point_traj_vec || that_present_point_traj_vec) {
      if (!(this_present_point_traj_vec && that_present_point_traj_vec))
        return false;
      if (!this.point_traj_vec.equals(that.point_traj_vec))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPoint_traj_vec()) ? 131071 : 524287);
    if (isSetPoint_traj_vec())
      hashCode = hashCode * 8191 + point_traj_vec.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(PointTraj2DistRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPoint_traj_vec()).compareTo(other.isSetPoint_traj_vec());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPoint_traj_vec()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.point_traj_vec, other.point_traj_vec);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("PointTraj2DistRequest(");
    boolean first = true;

    sb.append("point_traj_vec:");
    if (this.point_traj_vec == null) {
      sb.append("null");
    } else {
      sb.append(this.point_traj_vec);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class PointTraj2DistRequestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PointTraj2DistRequestStandardScheme getScheme() {
      return new PointTraj2DistRequestStandardScheme();
    }
  }

  private static class PointTraj2DistRequestStandardScheme extends org.apache.thrift.scheme.StandardScheme<PointTraj2DistRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PointTraj2DistRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POINT_TRAJ_VEC
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.point_traj_vec = new java.util.ArrayList<point_traj_t>(_list40.size);
                point_traj_t _elem41;
                for (int _i42 = 0; _i42 < _list40.size; ++_i42)
                {
                  _elem41 = new point_traj_t();
                  _elem41.read(iprot);
                  struct.point_traj_vec.add(_elem41);
                }
                iprot.readListEnd();
              }
              struct.setPoint_traj_vecIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PointTraj2DistRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.point_traj_vec != null) {
        oprot.writeFieldBegin(POINT_TRAJ_VEC_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.point_traj_vec.size()));
          for (point_traj_t _iter43 : struct.point_traj_vec)
          {
            _iter43.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PointTraj2DistRequestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public PointTraj2DistRequestTupleScheme getScheme() {
      return new PointTraj2DistRequestTupleScheme();
    }
  }

  private static class PointTraj2DistRequestTupleScheme extends org.apache.thrift.scheme.TupleScheme<PointTraj2DistRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PointTraj2DistRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPoint_traj_vec()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetPoint_traj_vec()) {
        {
          oprot.writeI32(struct.point_traj_vec.size());
          for (point_traj_t _iter44 : struct.point_traj_vec)
          {
            _iter44.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PointTraj2DistRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list45 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.point_traj_vec = new java.util.ArrayList<point_traj_t>(_list45.size);
          point_traj_t _elem46;
          for (int _i47 = 0; _i47 < _list45.size; ++_i47)
          {
            _elem46 = new point_traj_t();
            _elem46.read(iprot);
            struct.point_traj_vec.add(_elem46);
          }
        }
        struct.setPoint_traj_vecIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

