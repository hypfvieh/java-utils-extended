package com.github.hypfvieh.config;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.github.hypfvieh.AbstractBaseUtilTest;

public class XmlConfigurationUtilTest extends AbstractBaseUtilTest {

    private XmlConfigurationUtil util;

    @Before
    public void before() throws FileNotFoundException {
        util = new XmlConfigurationUtil("src/test/resources/XmlConfigurationUtilTest/xmlConfigTest.xml");
    }

    @Test
    public void testGetStringProperty() {
        // correct value found
        assertEquals("Value1", util.getString("Key1", "Key2"));

        // key found, but wrong value
        assertNotEquals("fail", util.getString("Key1", "fail"));

        // wrong key, wrong value
        assertNotEquals("fail", util.getString("foo", "bar"));

        // wrong key, correct default
        assertEquals("fail", util.getString("foo", "fail"));

        // correct key, overridden by environment
        System.getProperties().setProperty("Key2.SubKey1", "Jambalaja");
        assertEquals("Jambalaja", util.getString("Key2.SubKey1", "default"));
    }

    @Test
    public void testGetStringListProperty() {

        List<String> stringList = util.getStringList("Key3.SubKeyList.SubListEntry");

        assertFalse(stringList.isEmpty());
        assertTrue(stringList.size() == 4);
        assertEquals("SubListEntry1", stringList.get(0));
    }

    @Test
    public void testGetStringSetProperty() {

        Set<String> stringList = util.getStringSet("Key3.SubKeyList.SubListEntry", TreeSet.class);

        assertFalse(stringList.isEmpty());
        assertTrue(stringList.size() == 3);
    }

    @Test
    public void testGetIntProperty() {
        // key found, but not integer
        assertEquals(-1, util.getInt("Key4.NotInt", -1));

        // key found and is integer
        assertEquals(100, util.getInt("Key4.Int", -1));
    }

    @Test
    public void testGetBooleanProperty() {
        // key found, but not bool, expect default
        assertEquals(false, util.getBoolean("Key5.NotBool", false));

        // key found and is boolean
        assertEquals(true, util.getBoolean("Key5.Bool", false));
    }

}
