package idal;

import com.leya.idal.dao.ActivityDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//1、监控集群状态
//
//2、分表分库查询支持
//
//3、数据源集中化配置
//
//4、项目不应该依赖spring
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/config/spring/idal-common.xml",
                                    "classpath:/config/spring/local/idal-test.xml" })
public class IdalTest {

    @Autowired
    private ActivityDao activityDao;

    @Test
    public void queryWithParameters() {
        List<Map> codeAddresses = activityDao.queryAllCodeAddress();
        List<Map> geographyAreas = activityDao.queryAllGeographyArea();

        Map<String, Object> commonCities = new HashMap<String, Object>();

        for (Map geographyArea : geographyAreas) {
            String province = (String) geographyArea.get("Province");
            String city = (String) geographyArea.get("City");
            String district = (String) geographyArea.get("District");
            int id = (Integer) geographyArea.get("ID");

            if (city.startsWith("自治区直辖县级行政单位")) {
                commonCities.put(
                        province + "_" + district, id);
            }

            if (district.length() >= 2 && city.length() >= 2 && province != city) {
                commonCities.put(
                        province.substring(0, 2) + "_" + city.substring(0, 2) + "_" + district.substring(0, 2), id);
            }

            if ("".equals(city)) {
                commonCities.put(province, id);
            }

            if ("".equals(district) || city.equals(district)) {
                commonCities.put(province + "_" + city, id);
            }

            commonCities.put(province + "_" + city + "_" + district, id);
        }

        for (Map codeAddress : codeAddresses) {
            Double originIdx = (Double) codeAddress.get("idx");
            int idx = originIdx.intValue();
            String sido = (String) codeAddress.get("sido");
            String gugun = (String) codeAddress.get("gugun");
            String dong = (String) codeAddress.get("dong");
            int geography_Area_Id = 0;

            if ("省直辖行政单位".equals(gugun)) {
                if (sido.startsWith("新疆")) {
                    sido = sido.substring(0, 2);
                }

                Object origin_geography_Area_Id = commonCities.get(sido + "_" + dong);
                if (origin_geography_Area_Id == null) {
                    continue;
                }
                geography_Area_Id = (Integer) origin_geography_Area_Id;
                activityDao.updateCodeAddress(new Double(idx), geography_Area_Id);
                continue;
            }

            if ("default".equals(dong)) {
                String key = "";
                if (sido.startsWith("新疆")) {
                    key = "新疆_" + gugun;
                } else if (sido.equals(gugun)) {
                    key = sido;
                } else {
                    key = sido + "_" + gugun;
                }

                Object origin_geography_Area_Id = commonCities.get(key);
                if (origin_geography_Area_Id == null) {
                    continue;
                }
                geography_Area_Id = (Integer) origin_geography_Area_Id;
                activityDao.updateCodeAddress(new Double(idx), geography_Area_Id);
                continue;
            }

            if (("北京市".equals(sido) || "天津市".equals(sido) || "重庆市".equals(sido) || "上海市".equals(sido))) {
                Object origin_geography_Area_Id = commonCities.get(sido + "_" + dong);
                if (origin_geography_Area_Id == null) {
                    continue;
                }
                geography_Area_Id = (Integer) origin_geography_Area_Id;
                activityDao.updateCodeAddress(new Double(idx), geography_Area_Id);
                continue;
            } else {
                Object origin_geography_Area_Id = commonCities.get(sido + "_" + gugun + "_" + dong);
                if (origin_geography_Area_Id == null) {
                    continue;
                }

                geography_Area_Id = (Integer) origin_geography_Area_Id;
            }

            System.out.println("idx:" + idx + ",geography_Area_Id:" + geography_Area_Id);

            activityDao.updateCodeAddress(new Double(idx), geography_Area_Id);

        }

        List<Map> codeAddressWhereGeographyAreaIdIsZeros = activityDao.queryAllCodeAddressWhereGeographyAreaIdIsZero();
        for (Map code : codeAddressWhereGeographyAreaIdIsZeros) {
            Double originIdx = (Double) code.get("idx");
            int idx = originIdx.intValue();
            String sido = (String) code.get("sido");
            String gugun = (String) code.get("gugun");
            String dong = (String) code.get("dong");

            if (!sido.equals(gugun)) {
                String key = sido.substring(0, 2) + "_" +
                             gugun.substring(0, 2) + "_" +
                             dong.substring(0, 2);
                Object origin_geography_Area_Id = commonCities.get(key);

                if (origin_geography_Area_Id != null) {
                    int geography_Area_Id = (Integer) origin_geography_Area_Id;
                    activityDao.updateCodeAddress(new Double(idx), geography_Area_Id);
                }

            }
        }

    }
    //
    //    @Test
    //    public void pageQuery() {
    //        PageModel actual = activityDao.pageOfflineActivityUserHis(20000000, new Date(), 5000, 50);
    //        assertEquals(9613730, actual.getTotalCount());
    //        assertEquals(50, actual.getRecords().size());
    //    }
    //
    //    @Test
    //    public void queryWithOutParameters() {
    //        List<Integer> cityIds = activityDao.getAllActivityCityId();
    //        int actualSize = cityIds.size();
    //        assertEquals(129, actualSize);
    //    }
    //
    //    @Test
    //    public void insertWithParameters() {
    //        int cityid = 10000;
    //        String cityName = "用于测试的城市";
    //        int Status = 1;
    //        activityDao.addActivityBackCity(cityid, cityName, Status);
    //    }
    //
    //    @Test
    //    public void updateWithParameters() {
    //        int cityid = 10000;
    //        String cityName = "修改后的城市";
    //        int actual = activityDao.updateActivityBackCity(cityid, cityName);
    //
    //        assertEquals(1, actual);
    //    }
    //
    //    @Test
    //    public void deleteWithParameters() {
    //        int cityid = 10000;
    //        int actual = activityDao.deleteActivityBackCity(cityid);
    //
    //        assertEquals(1, actual);
    //    }
    //
    //    @Test
    //    public void pageQueryByLimit() {
    //        long start = System.currentTimeMillis();
    //        List<Integer> actual = activityDao.getOfflineActivityUser(41515,0, 1);
    //        long end = System.currentTimeMillis();
    //        System.out.println("共花费了：" + (end - start));
    //
    //        assertEquals(1, actual.size());
    //    }

}
