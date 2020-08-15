package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem;

public class ItemDownload {
    public int Id;


    public String N_F;
    public String P_F;
    public String Code_F;
    public String Type_F;
    public long Size_F=0;
    public long Size_Down_F=0;
    public String Host_F;
    public int Status_D=0;
    public long ID_DM=0;
    public String ER_DM;
    public String F_N_S;
    public long H_Code=0;
    public ItemDownload(){}
    public ItemDownload(int id,
                        String n_F,
                        String p_F,
                        String code_F,
                        String type_F,
                        long size_F,
                        long size_Down_F,
                        String host_F,
                        int status_D,
                        long id_dm,
                        String er_dm,
                        String f_n_s,
                        long h_code

    ) {
        Id = id;
        N_F = n_F;
        P_F = p_F;
        Code_F = code_F;
        Type_F = type_F;
        Size_F = size_F;
        Size_Down_F = size_Down_F;
        Host_F = host_F;
        Status_D = status_D;
        this.ID_DM = id_dm;
        this.ER_DM = er_dm;
        this.F_N_S = f_n_s;
        this.H_Code = h_code;
    }


    public String getF_N_S() {
        return  this.F_N_S;
    }
    public String getN_F() {
        return N_F;
    }

    public String getP_F() {
        return P_F;
    }

    public String getCode_F() {
        return Code_F;
    }

    public String getType_F() {
        return Type_F;
    }

    public long getSize_F() {
        return Size_F;
    }

    public long getSize_Down_F() {
        return Size_Down_F;
    }

    public String getHost_F() {
        return Host_F;
    }

    public long getH_Code() {
        return H_Code;
    }

    public int getStatus_D() {
        return Status_D;
    }

    public long getID_DM() {
        return ID_DM;
    }
    public String getER_DM() {
        return ER_DM;
    }



    public void setId(int id) {
        Id = id;
    }

    public void setN_F(String n_F) {
        N_F = n_F;
    }

    public void setF_N_S(String f_n_s) {
        this.F_N_S = f_n_s;
    }

    public void setP_F(String p_F) {
        P_F = p_F;
    }

    public void setCode_F(String code_F) {
        Code_F = code_F;
    }

    public void setType_F(String type_F) {
        Type_F = type_F;
    }

    public void setSize_F(long size_F) {
        Size_F = size_F;
    }

    public void setH_Code(long h_c) {
        H_Code = h_c;
    }

    public void setSize_Down_F(long size_Down_F) {
        Size_Down_F = size_Down_F;
    }

    public void setHost_F(String host_F) {
        Host_F = host_F;
    }

    public void setStatus_D(int status_D) {
        Status_D = status_D;
    }

    public void setID_DM(long id_dm) {
        this.ID_DM = id_dm;
    }

    public void setER_DM(String er_dm) {
        this.ER_DM = er_dm;
    }
}
