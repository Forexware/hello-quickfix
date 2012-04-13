package com.bostontechnologies.helloquickfix;

import quickfix.*;
import quickfix.field.MsgType;
import quickfix.field.Password;
import quickfix.field.ResetSeqNumFlag;
import quickfix.field.Username;
import quickfix.fix50.MarketDataIncrementalRefresh;
import quickfix.fix50.MarketDataSnapshotFullRefresh;

import static java.lang.System.out;

public class BTApplication extends MessageCracker implements Application {

    private final String fixUsername;
    private final String fixPassword;

    private SessionID sessionId = null;

    public BTApplication(String fixUsername, String fixPassword) {
        super(); //initialize message cracker with this as handler

        this.fixUsername = fixUsername;
        this.fixPassword = fixPassword;
    }

    public void onCreate(final SessionID sessionID) {
        out.println("Session created: " + sessionID);
    }

    public void onLogon(final SessionID sessionID) {
        out.println("Login: " + sessionID);
        sessionId = sessionID;
    }

    public void onLogout(final SessionID sessionID) {
        out.println("Logout: " + sessionID);
    }

    public void toAdmin(final Message message, final SessionID sessionID) {
        try {
            if (message.getHeader().getField(new MsgType()).getValue().equals(MsgType.LOGON)) {
                message.setField(new Username(fixUsername));
                message.setField(new Password(fixPassword));
                message.setField(new ResetSeqNumFlag(true));
                out.println("Attempting Login: " + message);
            }
        } catch (FieldNotFound fieldNotFound) {
            fieldNotFound.printStackTrace();
        }
    }

    public void fromAdmin(final Message message, final SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        out.println("fromAdmin: " + message);
        out.println("session: " + sessionID);
    }

    public void toApp(final Message message, final SessionID sessionID) throws DoNotSend {
    }

    public void fromApp(final Message message, final SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        out.println("msg: " + message);
        crack(message, sessionID);
    }

    public void onMessage(MarketDataSnapshotFullRefresh message, SessionID sessionID) {
        out.println("MarketDataSnapshotFullRefresh received!!!");
    }

    public void onMessage(MarketDataIncrementalRefresh message, SessionID sessionID) {
        out.println("MarketDataIncrementalRefresh received!!!");
    }

    public void send(final Message message, final SessionID sessionID) {
        Session.lookupSession(sessionID).send(message);
    }

    public void send(final Message message) {
        send(message, sessionId);
    }

    public SessionID getSessionId() {
        return sessionId;
    }
}
