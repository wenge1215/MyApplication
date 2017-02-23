package com.innolux.bean;


import java.util.List;

public class TestBean {


    /**
     * matter_list : [{"bednewmatcap":"ssssss","brpurchasecap":"XXX","brpurchaseokcap":"XXX","mbmatnum":"XXX","mbproduct":"ddddd","mbstandard":"XXX","ngnum":"XXX","nlphnumber":"XXX","storage":"XXX"}]
     * reason : XX
     * result : 1
     */

    private String reason;
    private String result;
    private List<MatterListBean> matter_list;

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

    public List<MatterListBean> getMatter_list() {
        return matter_list;
    }

    public void setMatter_list(List<MatterListBean> matter_list) {
        this.matter_list = matter_list;
    }

    public static class MatterListBean {
        /**
         * bednewmatcap : ssssss
         * brpurchasecap : XXX
         * brpurchaseokcap : XXX
         * mbmatnum : XXX
         * mbproduct : ddddd
         * mbstandard : XXX
         * ngnum : XXX
         * nlphnumber : XXX
         * storage : XXX
         */

        private String bednewmatcap;
        private String brpurchasecap;
        private String brpurchaseokcap;
        private String mbmatnum;
        private String mbproduct;
        private String mbstandard;
        private String ngnum;
        private String nlphnumber;
        private String storage;

        public String getBednewmatcap() {
            return bednewmatcap;
        }

        public void setBednewmatcap(String bednewmatcap) {
            this.bednewmatcap = bednewmatcap;
        }

        public String getBrpurchasecap() {
            return brpurchasecap;
        }

        public void setBrpurchasecap(String brpurchasecap) {
            this.brpurchasecap = brpurchasecap;
        }

        public String getBrpurchaseokcap() {
            return brpurchaseokcap;
        }

        public void setBrpurchaseokcap(String brpurchaseokcap) {
            this.brpurchaseokcap = brpurchaseokcap;
        }

        public String getMbmatnum() {
            return mbmatnum;
        }

        public void setMbmatnum(String mbmatnum) {
            this.mbmatnum = mbmatnum;
        }

        public String getMbproduct() {
            return mbproduct;
        }

        public void setMbproduct(String mbproduct) {
            this.mbproduct = mbproduct;
        }

        public String getMbstandard() {
            return mbstandard;
        }

        public void setMbstandard(String mbstandard) {
            this.mbstandard = mbstandard;
        }

        public String getNgnum() {
            return ngnum;
        }

        public void setNgnum(String ngnum) {
            this.ngnum = ngnum;
        }

        public String getNlphnumber() {
            return nlphnumber;
        }

        public void setNlphnumber(String nlphnumber) {
            this.nlphnumber = nlphnumber;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }
    }
}
