package com.github.hypfvieh.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hypfvieh.util.ConverterUtil;
import com.github.hypfvieh.util.TypeUtil;

/**
 * Utility for reading XML configuration.
 * This uses commons-configuration to read XML files but provides some additional features like<br>
 * getting values with default, converting values to lists or sets instead of using arrays.
 * <br>
 * <br>
 * It also supports overriding configuration entries by setting up environment properties.<br>
 * <b>Example:</b><br>
 * You want to override a configuration key &lt;foo&gt;&lt;bar&gt;value&lt;bar&gt;&lt;foo&gt;<br>
 * so you can use: -Dfoo.bar=newValue<br><br>
 * To override lists or sets you can use a string delimited by ~|~ (tilde pipe tilde).
 * <br>
 * <br>
 * Properties overridden by the environment always takes precedence over values in configuration file.
 */
public class XmlConfigurationUtil {

    private final Logger logger = LoggerFactory.getLogger(XmlConfigurationUtil.class);

    private XMLConfiguration config;

    /**
     * Create a new configuration util instance and use the given filename as configuration file.
     *
     * @param _configFileName
     * @throws FileNotFoundException
     */
    public XmlConfigurationUtil(String _configFileName) throws FileNotFoundException {
        File configFile = new File(_configFileName);
        String fileName = configFile.getName();
        Parameters params  = new Parameters();

        FileBasedConfigurationBuilder<XMLConfiguration> builder = null;

        if (!configFile.exists()) {
            logger.info("Could not find file: " + _configFileName + ", trying to find it in classpath");

            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (resourceAsStream != null) {
                logger.info("Found config file '" + fileName + "'in class path");

                builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                        .configure(params.xml().setLocationStrategy(new ClasspathLocationStrategy()).setFileName(fileName));
            } else {
                throw new FileNotFoundException("Could not find file '" + _configFileName + "' in filesystem or classpath");
            }
        } else if (configFile.isDirectory()) {
            throw new IllegalArgumentException("Given config file has to be a file, not a directory");
        } else {
            builder = new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
                    .configure(params.xml().setFile(configFile));
        }

        try {
             config = builder.getConfiguration();
             logger.info("Loaded " + config.size() + " configuration keys from " + _configFileName);
        } catch (ConfigurationException _ex) {
               logger.warn("Problem while loading configuration from file " + _configFileName, _ex);
               throw new RuntimeException(_ex);
        }
    }

    private String getStringFromEnv(String _key, String _default) {
        if (System.getProperties().containsKey(_key)) {
            String sysProp = System.getProperties().getProperty(_key);
            logger.debug("Config-Property '" + _key + "' is overridden by environment variable, using value '" + sysProp + "' instead of '" + _default + "'");

            return sysProp;
        }
        return null;
    }

    /**
     * Get a config value as String.
     * If _key could not be found, null is returned.
     *
     * @param _key
     * @return
     */
    public String getString(String _key) {
        String cfgVal = config.getString(_key);
        return StringUtils.defaultString(getStringFromEnv(_key, cfgVal), cfgVal);
    }

    /**
     * Returns a config value as String, or the default string if _key could not be found.
     *
     * @param _key
     * @param _default
     * @return
     */
    public String getString(String _key, String _default) {
        String str = getStringFromEnv(_key, _default);
        if (str == null) {
            return config.getString(_key, _default);
        } else {
            return str;
        }
    }

    /**
     * Get a configuration value as int.
     * If _key not found, or not an integer, default is returned.
     *
     * @param _key
     * @param _default
     * @return
     */
    public int getInt(String _key, int _default) {
        String valAsStr = getString(_key, _default + "");
        if (valAsStr == null || !TypeUtil.isInteger(valAsStr)) {
            return _default;
        } else {
            return Integer.parseInt(valAsStr);
        }
    }

    /**
     * Retrieve a configuration value as double.
     * If _key could not be found, or value was not of type 'double', default is returned.
     *
     * @param _key
     * @param _default
     * @return
     */
    public double getDouble(String _key, double _default) {
        String valAsStr = getString(_key, _default + "");
        if (valAsStr == null || !StringUtils.isNumeric(valAsStr)) {
            return _default;
        } else {
            return Double.parseDouble(valAsStr);
        }
    }

    /**
     * Retrieve a configuration value as boolean.
     * If _key could not be found, default is returned.<br>
     * All other values will be tried to read as boolean.<br>
     * <br>
     * Considered true values are:<br>
     * <ul>
     *  <li>1</li>
     *  <li>y</li>
     *  <li>j</li>
     *  <li>ja</li>
     *  <li>yes</li>
     *  <li>true</li>
     *  <li>enabled</li>
     *  <li>enable</li>
     *  <li>active</li>
     * </ul>
     * All other values are considered to be false.
     * <br>
     * @param _key
     * @param _default
     * @return
     */
    public boolean getBoolean(String _key, boolean _default) {
        String valAsStr = getString(_key, _default + "");
        if (StringUtils.isBlank(valAsStr)) {
            return _default;
        } else {
            return ConverterUtil.strToBool(valAsStr);
        }
    }

    /**
     * Returns the results of key as ArrayList.
     *
     * @param _key
     * @return never null, maybe empty list
     */
    public List<String> getStringList(String _key) {

        String str = getStringFromEnv(_key, null);
        if (str != null) {
            if (str.contains("~|~")) {
                return TypeUtil.createList(str.split("~\\|~"));
            } else {
                return TypeUtil.createList(str);
            }
        }

        List<String> list = config.getList(String.class, _key);
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    /**
     * Returns a result as Set of the given Set-Subclass.
     * If given Set-Class is null or could not be instantiated, TreeSet is used.
     *
     * @param _key
     * @param _setClass
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Set<String> getStringSet(String _key, Class<? extends Set> _setClass) {
        if (_setClass == null) {
            _setClass = TreeSet.class;
        }

        List<String> list = getStringList(_key);

        Set<String> newInstance;
        try {
            newInstance = _setClass.newInstance();
            newInstance.addAll(list);

            return newInstance;
        } catch (InstantiationException | IllegalAccessException _ex) {
            return new TreeSet<String>(list);
        }
    }

}
