package com.zhixinyisheng.user.view.mydoctor;

import java.util.List;

/**
 * 省
 * Created by CrazyPumPkin on 2016/12/13.
 */

public class Province {

    /**
     * cities : [{"areaName":"北京市","counties":[{"areaName":"东城区"},{"areaName":"西城区"},{"areaName":"朝阳区"},{"areaName":"丰台区"},{"areaName":"石景山区"},{"areaName":"海淀区"},{"areaName":"门头沟区"},{"areaName":"房山区"},{"areaName":"通州区"},{"areaName":"顺义区"},{"areaName":"昌平区"},{"areaName":"大兴区"},{"areaName":"怀柔区"},{"areaName":"平谷区"},{"areaName":"密云区"},{"areaName":"延庆区"}]}]
     * areaName : 北京市
     */

    private String areaName;
    /**
     * areaName : 北京市
     * counties : [{"areaName":"东城区"},{"areaName":"西城区"},{"areaName":"朝阳区"},{"areaName":"丰台区"},{"areaName":"石景山区"},{"areaName":"海淀区"},{"areaName":"门头沟区"},{"areaName":"房山区"},{"areaName":"通州区"},{"areaName":"顺义区"},{"areaName":"昌平区"},{"areaName":"大兴区"},{"areaName":"怀柔区"},{"areaName":"平谷区"},{"areaName":"密云区"},{"areaName":"延庆区"}]
     */

    private List<CitiesBean> cities;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        private String areaName;
        /**
         * areaName : 东城区
         */

        private List<CountiesBean> counties;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public List<CountiesBean> getCounties() {
            return counties;
        }

        public void setCounties(List<CountiesBean> counties) {
            this.counties = counties;
        }

        public static class CountiesBean {
            private String areaName;

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }
        }
    }
}
