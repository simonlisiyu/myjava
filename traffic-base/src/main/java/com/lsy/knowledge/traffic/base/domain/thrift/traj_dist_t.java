/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lsy.knowledge.traffic.base.domain.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2017-12-27")
public class traj_dist_t implements org.apache.thrift.TBase<traj_dist_t, traj_dist_t._Fields>, java.io.Serializable, Cloneable, Comparable<traj_dist_t> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("traj_dist_t");

  private static final org.apache.thrift.protocol.TField USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("user_id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DIST_FIELD_DESC = new org.apache.thrift.protocol.TField("dist", org.apache.thrift.protocol.TType.DOUBLE, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new traj_dist_tStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new traj_dist_tTupleSchemeFactory();

  public java.lang.String user_id; // required
  public double dist; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_ID((short)1, "user_id"),
    DIST((short)2, "dist");

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
        case 1: // USER_ID
          return USER_ID;
        case 2: // DIST
          return DIST;
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
  private static final int __DIST_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_ID, new org.apache.thrift.meta_data.FieldMetaData("user_id", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "user_id_t")));
    tmpMap.put(_Fields.DIST, new org.apache.thrift.meta_data.FieldMetaData("dist", org.apache.thrift.TFieldRequirementType.DEFAULT,
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(traj_dist_t.class, metaDataMap);
  }

  public traj_dist_t() {
  }

  public traj_dist_t(
    java.lang.String user_id,
    double dist)
  {
    this();
    this.user_id = user_id;
    this.dist = dist;
    setDistIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public traj_dist_t(traj_dist_t other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUser_id()) {
      this.user_id = other.user_id;
    }
    this.dist = other.dist;
  }

  public traj_dist_t deepCopy() {
    return new traj_dist_t(this);
  }

  @Override
  public void clear() {
    this.user_id = null;
    setDistIsSet(false);
    this.dist = 0.0;
  }

  public java.lang.String getUser_id() {
    return this.user_id;
  }

  public traj_dist_t setUser_id(java.lang.String user_id) {
    this.user_id = user_id;
    return this;
  }

  public void unsetUser_id() {
    this.user_id = null;
  }

  /** Returns true if field user_id is set (has been assigned a value) and false otherwise */
  public boolean isSetUser_id() {
    return this.user_id != null;
  }

  public void setUser_idIsSet(boolean value) {
    if (!value) {
      this.user_id = null;
    }
  }

  public double getDist() {
    return this.dist;
  }

  public traj_dist_t setDist(double dist) {
    this.dist = dist;
    setDistIsSet(true);
    return this;
  }

  public void unsetDist() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DIST_ISSET_ID);
  }

  /** Returns true if field dist is set (has been assigned a value) and false otherwise */
  public boolean isSetDist() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DIST_ISSET_ID);
  }

  public void setDistIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DIST_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case USER_ID:
      if (value == null) {
        unsetUser_id();
      } else {
        setUser_id((java.lang.String)value);
      }
      break;

    case DIST:
      if (value == null) {
        unsetDist();
      } else {
        setDist((java.lang.Double)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_ID:
      return getUser_id();

    case DIST:
      return getDist();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case USER_ID:
      return isSetUser_id();
    case DIST:
      return isSetDist();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof traj_dist_t)
      return this.equals((traj_dist_t)that);
    return false;
  }

  public boolean equals(traj_dist_t that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_user_id = true && this.isSetUser_id();
    boolean that_present_user_id = true && that.isSetUser_id();
    if (this_present_user_id || that_present_user_id) {
      if (!(this_present_user_id && that_present_user_id))
        return false;
      if (!this.user_id.equals(that.user_id))
        return false;
    }

    boolean this_present_dist = true;
    boolean that_present_dist = true;
    if (this_present_dist || that_present_dist) {
      if (!(this_present_dist && that_present_dist))
        return false;
      if (this.dist != that.dist)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetUser_id()) ? 131071 : 524287);
    if (isSetUser_id())
      hashCode = hashCode * 8191 + user_id.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(dist);

    return hashCode;
  }

  @Override
  public int compareTo(traj_dist_t other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUser_id()).compareTo(other.isSetUser_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUser_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.user_id, other.user_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDist()).compareTo(other.isSetDist());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDist()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dist, other.dist);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("traj_dist_t(");
    boolean first = true;

    sb.append("user_id:");
    if (this.user_id == null) {
      sb.append("null");
    } else {
      sb.append(this.user_id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("dist:");
    sb.append(this.dist);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class traj_dist_tStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public traj_dist_tStandardScheme getScheme() {
      return new traj_dist_tStandardScheme();
    }
  }

  private static class traj_dist_tStandardScheme extends org.apache.thrift.scheme.StandardScheme<traj_dist_t> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, traj_dist_t struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.user_id = iprot.readString();
              struct.setUser_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DIST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.dist = iprot.readDouble();
              struct.setDistIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, traj_dist_t struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.user_id != null) {
        oprot.writeFieldBegin(USER_ID_FIELD_DESC);
        oprot.writeString(struct.user_id);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DIST_FIELD_DESC);
      oprot.writeDouble(struct.dist);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class traj_dist_tTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public traj_dist_tTupleScheme getScheme() {
      return new traj_dist_tTupleScheme();
    }
  }

  private static class traj_dist_tTupleScheme extends org.apache.thrift.scheme.TupleScheme<traj_dist_t> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, traj_dist_t struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUser_id()) {
        optionals.set(0);
      }
      if (struct.isSetDist()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetUser_id()) {
        oprot.writeString(struct.user_id);
      }
      if (struct.isSetDist()) {
        oprot.writeDouble(struct.dist);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, traj_dist_t struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.user_id = iprot.readString();
        struct.setUser_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.dist = iprot.readDouble();
        struct.setDistIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

