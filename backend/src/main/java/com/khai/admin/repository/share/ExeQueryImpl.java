package com.khai.admin.repository.share;

import java.sql.*;
import java.util.ArrayList;

public class ExeQueryImpl implements ExecuteQuery{
    //Bo quan ly ket noi cua rieng Basic
    private ConnectionPool cp;

    //Ket noi de Basic su dung
    protected Connection con;

    //Doi tuong lam viec voi basic
    private String objectName;

    //Constructor
    public ExeQueryImpl(ConnectionPool cp, String objectName){
        //Xac dinh doi tuong lam viec
        this.objectName = objectName;

        //Xac dinh bo quan ly ket noi
        if (cp==null) {
            this.cp = new ConnectionPoolImpl();
        } else {
            this.cp=cp;
        }

        //Xin ket noi de lam viec
        try {
            this.con = this.cp.getConnection(this.objectName);

            if (this.con.getAutoCommit()) {
                this.con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public boolean exe(PreparedStatement pre) {
        if (pre != null) {
            //Thuc hien cap nhat vao CSDL
            try {
                int results = pre.executeUpdate();

                if (results==0) {
                    //Tro lai trang thai an toan neu ko tim duoc
                    this.con.rollback();
                    return false;
                }

                //Xac nhan Execute
                this.con.commit();
                return true;

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {
                    this.con.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } finally {
                pre = null;
            }
        }
        return false;
    }

    @Override
    public boolean add(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exe(pre);
    }

    @Override
    public boolean edit(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exe(pre);
    }

    @Override
    public boolean del(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exe(pre);
    }

    @Override
    public ResultSet get(String sql, int id) {
        // TODO Auto-generated method stub

        //Bien dich cau lenh sql
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);

            if (id>0) {
                pre.setInt(1, id);
            }

            return pre.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {

        }
        return null;
    }

    @Override
    public synchronized ResultSet get(ArrayList<String> sql, String name, String pass) {
        // TODO Auto-generated method stub

        //Bien dich cau lenh
        try {
            String str_select = sql.get(0);
            PreparedStatement preSelect = this.con.prepareStatement(str_select);
            preSelect.setString(1, name);
            preSelect.setString(2, pass);

            ResultSet rs = preSelect.executeQuery();

            if (rs!=null) {
                String str_update = sql.get(1);
                PreparedStatement preUpdate = this.con.prepareStatement(str_update);
                preUpdate.setString(1, name);
                preUpdate.setString(2, pass);

                int result = preUpdate.executeUpdate();

                if (result==0) {
                    this.con.rollback();
                    return null;
                } else {
                    if (!this.con.getAutoCommit()) {
                        this.con.commit();
                    }
                    return rs;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            name=null;
            pass=null;
        }


        return null;
    }

    @Override
    public ResultSet gets(String sql) {
        // TODO Auto-generated method stub
        return this.get(sql, 0);
    }

    @Override
    public ArrayList<ResultSet> getReList(String multiSelect) {
        // TODO Auto-generated method stub
        ArrayList<ResultSet> res = new ArrayList<>();

        try {
            PreparedStatement pre = this.con.prepareStatement(multiSelect);
            boolean result = pre.execute();

            do {
                res.add(pre.getResultSet());

                //getMoreResults: Lay resultSet tiep theo,
                //Statement.Keep_current_result: giu cho resultSet hien tai khong bi do'ng, de su dung truy van cho nhung lan khac
                result = pre.getMoreResults(Statement.KEEP_CURRENT_RESULT);
            } while (result);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return res;
    }

    @Override
    public ConnectionPool getCP() {
        // TODO Auto-generated method stub
        return this.cp;// chia se bo quan li ket noi voi nhau

    }

    @Override
    public void releaseConnection() {
        // TODO Auto-generated method stub
        try {
            this.cp.releaseConnection(this.con, this.objectName);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public boolean addListV1(PreparedStatement pre, int add_number) {

        try {

            int results = pre.executeUpdate();

            if (results==0 || results!=add_number) {
                //Tro lai trang thai an toan neu ko tim duoc
                this.con.rollback();
                return false;
            }

            //Xac nhan Execute
            this.con.commit();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return false;
    }

    private synchronized boolean exeList(PreparedStatement pre) {
        try {
            this.con.setAutoCommit(false);

            int[] results = pre.executeBatch();

            if (results.length==0) {
                //Tro lai trang thai an toan neu ko tim duoc
                this.con.rollback();
                return false;
            }

            //Xac nhan Execute
            this.con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean addList(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exeList(pre);
    }

    @Override
    public boolean editList(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exeList(pre);
    }

    @Override
    public boolean delList(PreparedStatement pre) {
        // TODO Auto-generated method stub
        return this.exeList(pre);
    }
}
