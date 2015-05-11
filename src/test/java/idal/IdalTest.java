package idal;

import com.leya.idal.dao.ActivityDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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
        //        int cityid = 1;
        //        List<Integer> cityIds = activityDao.getActivityCityId(cityid);
        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(2);

        int status = 0;

        int city = activityDao.getActivityCityIdCount(ids, status);
        System.out.print("size:" + city);

        //assertEquals(1, cityIds.get(0).intValue());
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
