package com.innolux.bean;


import java.util.List;

public class TestBean {


    /**
     * bomorder : XX
     * box_type : XX
     * pro_list : [{"modelno":"XX","priceno":"XX","procount":"XX","proname":"XX","prono":"XX","spec":"XX"}]
     * reason : XX
     * result : 1
     * storage : XX
     */

    private String bomorder;
    private String box_type;
    private String reason;
    private String result;
    private String storage;
    private List<ProListBean> pro_list;

    public String getBomorder() {
        return bomorder;
    }

    public void setBomorder(String bomorder) {
        this.bomorder = bomorder;
    }

    public String getBox_type() {
        return box_type;
    }

    public void setBox_type(String box_type) {
        this.box_type = box_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public List<ProListBean> getPro_list() {
        return pro_list;
    }

    public void setPro_list(List<ProListBean> pro_list) {
        this.pro_list = pro_list;
    }

    public static class ProListBean {
        /**
         * modelno : XX
         * priceno : XX
         * procount : XX
         * proname : XX
         * prono : XX
         * spec : XX
         */

        private String modelno;
        private String priceno;
        private String procount;
        private String proname;
        private String prono;
        private String spec;

        public String getModelno() {
            return modelno;
        }

        public void setModelno(String modelno) {
            this.modelno = modelno;
        }

        public String getPriceno() {
            return priceno;
        }

        public void setPriceno(String priceno) {
            this.priceno = priceno;
        }

        public String getProcount() {
            return procount;
        }

        public void setProcount(String procount) {
            this.procount = procount;
        }

        public String getProname() {
            return proname;
        }

        public void setProname(String proname) {
            this.proname = proname;
        }

        public String getProno() {
            return prono;
        }

        public void setProno(String prono) {
            this.prono = prono;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
