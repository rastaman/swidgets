package org.tigris.swidgets;

/**
 * A property that can be displayed and edited within a PropertyTable.
 * 
 * @author Jeremy Jones
**/
public class Property implements Comparable {
    private String      name;
    private Class       valueType;
    private Object      initialValue;
    private Object      currentValue;
    private Object[]    availableValues;

    /**
     * Constructs a new Property. This version of the constructor does
     * not specify a finite set of available values.
     * 
     * @param theName          the property name
     * @param theValueType     the value type class
     * @param theInitialValue  the initial value
    **/
    public Property(String theName, Class theValueType, 
            Object theInitialValue) {
        this(theName, theValueType, theInitialValue, null);
    }

    /**
     * Constructs a new Property. This version of the constructor does
     * not specify a finite set of available values.
     * 
     * @param theName          the property name
     * @param theValueType     the value type class
     * @param theInitialValue  the initial value
     * @param values        the set of available values to choose from
    **/
    public Property(
            String theName,
            Class theValueType,
            Object theInitialValue,
            Object[] values) {
        name = theName;
        valueType = theValueType;
        initialValue = theInitialValue;
        availableValues = values;
        currentValue = initialValue;
    }

    /**
     * Returns the property name.
     * 
     * @return property name
    **/
    public String getName() {
        return name;
    }

    /**
     * Property editors should be configured to edit objects of this type.
     * 
     * @return  the property value class
    **/
    public Class getValueType() {
        return valueType;
    }

    /**
     * Returns the initial property value.
     * 
     * @return  initial property value
    **/
    public Object getInitialValue() {
        return initialValue;
    }

    /**
     * Returns the set of available property values, or null if no such
     * finite set exists.
     * 
     * @return set of available property values
    **/
    public Object[] getAvailableValues() {
        return availableValues;
    }

    /**
     * Returns the currently selected property value.
     * 
     * @return current property value
    **/
    public Object getCurrentValue() {
        return currentValue;
    }

    /**
     * Sets the currently selected property value.
     * 
     * @param value new property value
    **/
    public void setCurrentValue(Object value) {
        currentValue = value;
    }
    
    /**
     * Compares two Properties by comparing their names.
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        return name.compareTo(((Property) o).name);    
    }
}
