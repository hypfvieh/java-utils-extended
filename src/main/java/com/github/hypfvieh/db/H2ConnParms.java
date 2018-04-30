package com.github.hypfvieh.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Connection parameters to the H2 database.
 * Driver, most of database url, optionally user and password are preset in objects of this class.
 *
 * @author hypfvieh
 * @since 1.0.1
 */
public final class H2ConnParms extends DbConnParms {

    private final String dbPath;
    private final List<String> additionalUrlArgs = new ArrayList<>();

    public H2ConnParms(String _dbPath, String _user, String _password) {
        super("jdbc:h2:" + new File(_dbPath).getAbsolutePath() + ";AUTO_SERVER=TRUE;DB_CLOSE_DELAY=10", _user, _password, "org.h2.Driver");
        addAdditionalUrlArgs("AUTO_SERVER=TRUE");
        this.dbPath = _dbPath;
    }

    public H2ConnParms(String _dbPath) {
        this(_dbPath, "sa", "");
    }

    public String getDbPath() {
        return dbPath;
    }

    public void addAdditionalUrlArgs(String..._args) {
        if (_args == null) {
            return;
        }
        for (String arg : _args) {
            additionalUrlArgs.add(arg);
        }
    }

    public void removeAdditionalUrlArgs(String..._args) {
        if (_args == null) {
            return;
        }
        for (String arg : _args) {
            additionalUrlArgs.remove(arg);
        }
    }

    public List<String> getAdditionalUrlArgs() {
        return Collections.unmodifiableList(additionalUrlArgs);
    }

    @Override
    public String getUrl() {
        if (additionalUrlArgs.isEmpty()) {
            return super.getUrl();
        }

        return super.getUrl() + ";" + String.join(";", additionalUrlArgs);
    }


}
