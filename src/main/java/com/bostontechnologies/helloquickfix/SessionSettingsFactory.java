package com.bostontechnologies.helloquickfix;

import quickfix.FixVersions;
import quickfix.SessionID;
import quickfix.SessionSettings;

import java.io.File;

public class SessionSettingsFactory {

    public static SessionSettings apply(final String senderCompId,
                                        final String targetCompId,
                                        final String host,
                                        final int port,
                                        final String fileStoreBaseDirectory) {

        SessionSettings settings = new SessionSettings();
        settings.setString("ConnectionType", "initiator");
        settings.setString("DefaultApplVerID", "FIX.5.0");
        settings.setString("StartTime", "00:00:00");
        settings.setString("EndTime", "00:00:00");
        settings.setString("SenderCompID", senderCompId);
        settings.setString("SocketUseSSL", "N");
        settings.setString("UseDataDictionary", "Y");
        settings.setString("AppDataDictionary", "integrationFIX50.xml");
        settings.setString("FileLogPath", fileStoreBaseDirectory);

        SessionID sessionId = new SessionID(FixVersions.BEGINSTRING_FIXT11, senderCompId, targetCompId);
        settings.setString(sessionId, "SocketConnectHost", host);
        settings.setLong(sessionId, "SocketConnectPort", port);
        settings.setString(sessionId, "FileStorePath", fileStoreBaseDirectory + File.separator + targetCompId);
        settings.setLong("HeartBtInt", 60);
        settings.setLong("ReconnectInterval", 5);
        return settings;
    }
}
