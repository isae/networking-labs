package ru.ifmo.ctddev.isaev.networking;

import dnl.utils.text.table.TextTable;

import java.util.Date;

/**
 * @author Ilya Isaev
 */
public class Message {

    public static final String[] columnNames = {
            "MAC address",
            "Hostname",
            "Time"};
    String mac;
    String hostname;
    long timestamp;
    boolean ok;

    public void printAsTable() {
        Object[][] data = {
                {mac, hostname, new Date(timestamp * 1000)}
        };
        TextTable tt = new TextTable(columnNames, data);
        tt.setAddRowNumbering(false);
        tt.printTable();
    }
}
