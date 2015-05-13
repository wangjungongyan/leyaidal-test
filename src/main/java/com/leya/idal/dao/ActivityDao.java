package com.leya.idal.dao;

import com.leya.idal.annotation.Action;
import com.leya.idal.annotation.NameSpace;
import com.leya.idal.annotation.ParamName;
import com.leya.idal.enums.ActionType;
import com.leya.idal.model.PageModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@NameSpace("ActivityDao")
public interface ActivityDao {

    @Action(action = ActionType.QUERY_LIST)
    public List<Integer> getActivityCityId(@ParamName("cityid")
                                           int cityid);

    @Action(action = ActionType.QUERY_OBJECT, foreWrite = false)
    public Integer getActivityCityIdForObject(@ParamName("cityid")
                                              int cityid);

    @Action(action = ActionType.QUERY_OBJECT, foreWrite = false)
    public Integer getActivityCityIdCount(@ParamName("cityids")
                                          List<Integer> cityids, @ParamName("status") int status);

    @Action(action = ActionType.QUERY_LIST, foreWrite = false)
    public List<Integer> getOfflineActivityUser(@ParamName("ID")
                                                int id, @ParamName("pageNo")
                                                int pageNo, @ParamName("pageSize")
                                                int pageSize);

    @Action(action = ActionType.QUERY_LIST, foreWrite = false)
    public List<Integer> getAllActivityCityId();

    @Action(action = ActionType.INSERT)
    public void addActivityBackCity(@ParamName("CityID")
                                    int cityid, @ParamName("CityName")
                                    String CityName, @ParamName("Status")
                                    int Status);

    @Action(action = ActionType.UPDATE)
    public int updateActivityBackCity(@ParamName("CityID")
                                      int cityid, @ParamName("CityName")
                                      String CityName);

    @Action(action = ActionType.DELETE)
    public int deleteActivityBackCity(@ParamName("CityID")
                                      int cityid);

    @Action(action = ActionType.PAGE) PageModel pageOfflineActivityUserHis(@ParamName("ID")
                                                                           int ID, @ParamName("AddTime")
                                                                           Date AddTime, @ParamName("pageNo")
                                                                           int pageNo, @ParamName("pageSize")
                                                                           int pageSize);

    @Action(action = ActionType.QUERY_OBJECT)
    public int testIn(@ParamName("ids") List<String> ids);

    @Action(action = ActionType.QUERY_LIST)
    public List<Map> queryAllCodeAddress();

    @Action(action = ActionType.QUERY_LIST)
    public List<Map> queryAllCodeAddressWhereGeographyAreaIdIsZero();

    @Action(action = ActionType.QUERY_LIST)
    public List<Map> queryAllGeographyArea();

    @Action(action = ActionType.UPDATE)
    public void updateCodeAddress(@ParamName("id")
                                  double id, @ParamName("geography_Area_Id")
                                  int geography_Area_Id);

    @Action(action = ActionType.QUERY_LIST)
    public int queryAllGeographyAreaByProvinceCityDistrict(@ParamName("province")
                                                           String province, @ParamName("city")
                                                           String city, @ParamName("district")
                                                           String district);

}
