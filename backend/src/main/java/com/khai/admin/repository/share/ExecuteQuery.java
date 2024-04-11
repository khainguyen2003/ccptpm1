package com.khai.admin.repository.share;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface ExecuteQuery extends ShareControl {
    //PreparedStatement pre - da duoc bien dich, da truyen gia tri
    public boolean add(PreparedStatement pre);
    public boolean edit(PreparedStatement pre);
    public boolean del(PreparedStatement pre);

    public ResultSet get(String sql, int id);
    public ResultSet get(ArrayList<String> sql, String name, String pass);
    public ResultSet gets(String sql);
    public ArrayList<ResultSet> getReList(String multiSelect);

    //Phương thức thêm đa bảng
    public boolean addListV1(PreparedStatement pre, int add_number);

    public boolean addList(PreparedStatement pre);
    public boolean editList(PreparedStatement pre);
    public boolean delList(PreparedStatement pre);
}
